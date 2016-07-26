package material.quangduy.com.material.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import material.quangduy.com.material.R;
import material.quangduy.com.material.adapter.ListProduct_imageAdapter;
import material.quangduy.com.material.another.ExpandableHeightGridView;
import material.quangduy.com.material.entities.ProductEntity;
import material.quangduy.com.material.until.Constant;
import material.quangduy.com.material.until.GetData;
import material.quangduy.com.material.until.JSONParser;
import material.quangduy.com.material.until.getStringFromAsyn;

/**
 * Created by Quang Duy on 16-Dec-15.
 */
public class Search_Fragment extends Fragment {
    public static Search_Fragment newInstance() {
        Search_Fragment main = new Search_Fragment();
        return main;
    }

    ExpandableHeightGridView gridView;
    ArrayList<ProductEntity> list_product;
    ListProduct_imageAdapter adapter_product;
    int category_id = 0;
    private String url_getproduct = Constant.Constant_info.IPADDRESS + "bkshop/search.php";
    int limit = 6;
    int offset = 0;
    int count = 0;
    String result = "";
    Button more_btn;
    int ktStock = 0;
    JSONParser parser = new JSONParser();
    List<NameValuePair> param = new ArrayList<NameValuePair>();
    EditText box;
    Button search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.search_layout_fragment, container, false);
        search = (Button) layout.findViewById(R.id.search_btn);
        box = (EditText) layout.findViewById(R.id.search_box);
        more_btn = (Button) layout.findViewById(R.id.more_btn);
        more_btn.setVisibility(View.INVISIBLE);
        gridView = (ExpandableHeightGridView) layout.findViewById(R.id.gridView1);
        gridView.setExpanded(true);
        list_product = new ArrayList<>();
        adapter_product = new ListProduct_imageAdapter(getActivity(),list_product);
        gridView.setVisibility(View.INVISIBLE);
        gridView.setAdapter(adapter_product);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_product.size()==limit)
                more_btn.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.VISIBLE);
                String key = box.getText().toString();
                key = key.toLowerCase();
                list_product.clear();
                param.clear();
                param.add(new BasicNameValuePair("limit", Integer.toString(limit)));
                param.add(new BasicNameValuePair("offset", Integer.toString(offset)));
                param.add(new BasicNameValuePair("key", key));
                final GetData getData = new GetData(getActivity(),url_getproduct,param);
                getData.execute();
                result = new getStringFromAsyn().getString((AsyncTask) getData);
                Log.e("AAAAA", result);
                count = parser.parserJSONProduct(result, list_product);
                adapter_product.notifyDataSetChanged();
            }
        });

        //      kt=1;
        //  }
        Log.e("size of list", "" + list_product.size());
        Log.e("result", "" + result);
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ktStock == 0) {
                    if (count == limit) {
                        limit = 6;
                        offset = offset + limit;
                        param.clear();
                        param.add(new BasicNameValuePair("limit", Integer.toString(limit)));
                        param.add(new BasicNameValuePair("offset", Integer.toString(offset)));
                        param.add(new BasicNameValuePair("category_id", "" + Integer.toString(category_id)));

                        GetData getData = new GetData(getActivity(), url_getproduct, param);
                        getData.execute();
                        result = new getStringFromAsyn().getString((AsyncTask) getData);
                        count = parser.parserJSONProduct(result, list_product);
                        //product_count+=count;
                        adapter_product.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Out", Toast.LENGTH_LONG).show();
                        ktStock = 1;
                    }
                } else {
                    more_btn.setVisibility(View.GONE);
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDetailFragment a = ProductDetailFragment.newInstance();
                a.setTmp(list_product.get(position));
                AppCompatActivity ac = (AppCompatActivity) getActivity();
                FragmentTransaction tran = ac.getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.container_search, a);
                tran.addToBackStack(null);
                tran.commit();

            }
        });
        gridView.setFocusable(false);


//        final Handler handler = new Handler();
//        final Timer swipeTimer;
//        swipeTimer = new Timer();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if(currentPage < adapter_banner.getCount()-1)
//                {
//                    viewPager.setCurrentItem(currentPage, true);
//                    currentPage++;
//                }
//                else
//                    if (currentPage == adapter_banner.getCount()-1) {
//                        viewPager.setCurrentItem(currentPage, true);
//                        currentPage = 0;
//                    }
//            }
//        };
//
//
//        swipeTimer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                handler.postDelayed(Update, 2500);
//            }
//        }, 2000, 1500);
        //   viewPager.setPagingEnabled(false);
        final GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Color.parseColor("#0099FF"));
        gdDefault.setCornerRadius(30);
        more_btn.setBackgroundDrawable(gdDefault);
        search.setBackgroundDrawable(gdDefault);

        return layout;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        offset=0;
        limit=list_product.size();
        super.onPause();

    }

}
