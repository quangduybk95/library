package material.quangduy.com.material.until;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import material.quangduy.com.material.entities.CategoryEntity;
import material.quangduy.com.material.entities.ProductEntity;
import material.quangduy.com.material.entities.UserEntity;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public String makeHttpRequest(String url, String method,
                                  List<NameValuePair> params) {

        // Making HTTP request
        try {

            // check for request method
            if (method == "POST") {
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if (method == "GET") {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            return json;
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return json;
        // try parse the string to a JSON object
//		try {
//			jObj = new JSONObject(json);
//		} catch (JSONException e) {
//			Log.e("JSON Parser", "Error parsing data " + e.toString());
//		}
//
//		// return JSON String
//		return jObj;

    }

    public void parserJSONCategory(String result, ArrayList<CategoryEntity> list) {
        try {
            JSONObject json = new JSONObject(result);
            String status = null;
            if (json.has("status")) {
                status = json.getString("status");
            }
            JSONArray arr = null;
            if (json.has("data")) {
                arr = json.getJSONArray("data");
                if (null != arr && arr.length() > 0) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject json_i = arr.getJSONObject(i);
                        CategoryEntity tmp = parse_CategoryJSONI(json_i);
                        Log.e("Add a category", "Success");
                        list.add(tmp);
                    }
                }
            }

        } catch (JSONException e) {
        }
        // return list;
    }

    public CategoryEntity parse_CategoryJSONI(JSONObject json) throws JSONException {
        CategoryEntity category = new CategoryEntity();
        if (json.has("category_id")) {
            category.setmCategoryID(Integer.parseInt(json.getString("category_id")));
        }
        if (json.has("category_title")) {
            category.setmCategoryName(json.getString("category_title"));
        }

        return category;


//		if(json.has("product_images"))
//		{
//			ArrayList<String> s = new ArrayList<String>();
//			JSONArray j= json.getJSONArray("product_images");
//			for(int i=0;i<j.length();i++)
//			{
//				s.add(j.get(i).toString());
//			}
//			product.setList_image(s);
//		}
    }

    public int parserJSONProduct(String result, ArrayList<ProductEntity> list) {
        int count = 0;
        try {
            JSONObject json = new JSONObject(result);
            String status = null;
            if (json.has("status")) {
                status = json.getString("status");
            }
            JSONArray arr = null;
            if (json.has("data")) {
                arr = json.getJSONArray("data");
                if (null != arr && arr.length() > 0) {
                    count = arr.length();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject json_i = arr.getJSONObject(i);
                        ProductEntity tmp = parse_ProductJSONI(json_i);
                        Log.e("Add a pr", "Success");
                        list.add(tmp);
                    }
                }
            }

        } catch (JSONException e) {
        }
        // return list;
        return count;
    }

    public ProductEntity parse_ProductJSONI(JSONObject json) throws JSONException {
        ProductEntity pr = new ProductEntity();
        if (json.has("product_id")) {
            pr.setmProductID(json.getInt("product_id"));
        }
        if (json.has("product_title")) {
            pr.setmProductName(json.getString("product_title"));
        }
        if (json.has("product_price")) {
            pr.setmProductPrice((float) json.getDouble("product_price"));
        }
        if (json.has("product_thumb")) {
            pr.setLinkImage(Constant.Constant_info.domain1+json.getString("product_thumb"));
            Log.e(pr.getLinkImage(),"LINK IMAGE");
        }
        if (json.has("product_desc")) {
            pr.setmProductDesciption(json.getString("product_desc"));
        }
        if (json.has("product_image")) {
            String list_image = json.getString("product_image");
            String[] list = list_image.split("\\|");
            ArrayList<String> a = new ArrayList<>();
            for (int i = 0; i < list.length; i++) {
                a.add(Constant.Constant_info.domain1+list[i]);
            }
            pr.setList_image(a);
        }

        return pr;


//		if(json.has("product_images"))
//		{
//			ArrayList<String> s = new ArrayList<String>();
//			JSONArray j= json.getJSONArray("product_images");
//			for(int i=0;i<j.length();i++)
//			{
//				s.add(j.get(i).toString());
//			}
//			product.setList_image(s);
//		}
    }

    public void parse_User(String result, ArrayList<UserEntity> list) {
        try {
            JSONObject json = new JSONObject(result);
            String status = null;
            if (json.has("status")) {
                status = json.getString("status");
            }
            JSONArray arr = null;
            if (json.has("data")) {
                arr = json.getJSONArray("data");
                if (null != arr && arr.length() > 0) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject json_i = arr.getJSONObject(i);
                        UserEntity tmp = parse_UserJSONI(json_i);
                        list.add(tmp);
                        Log.e("Add a pr", "Success");
                    }
                }
            }

        } catch (JSONException e) {
        }
        // return list;
    }

    public UserEntity parse_UserJSONI(JSONObject us) throws JSONException {
        UserEntity userEntity = new UserEntity();
        if (us.has("user_email")) {
            userEntity.setUserEmail(us.getString("user_email"));
        }
        if (us.has("user_pass")) {
            userEntity.setPassword(us.getString("user_pass"));
        }
        if (us.has("user_address")) {
            userEntity.setUserAddress(us.getString("user_address"));
        }
        if (us.has("user_phone")) {
            userEntity.setUserPhone(us.getString("user_phone"));
        }
        if (us.has("user_fullname")) {
            userEntity.setUserFullName(us.getString("user_fullname"));
        }
        if (us.has("user_id")) {
            userEntity.setUserId(Integer.parseInt(us.getString("user_id")));
        }
        return userEntity;
    }
}
