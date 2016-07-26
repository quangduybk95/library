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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import material.quangduy.com.material.R;
import material.quangduy.com.material.adapter.BannerAdapter;
import material.quangduy.com.material.adapter.ListProduct_imageAdapter;
import material.quangduy.com.material.another.CustomViewPager;
import material.quangduy.com.material.another.ExpandableHeightGridView;
import material.quangduy.com.material.entities.BannerEntity;
import material.quangduy.com.material.entities.ProductEntity;
import material.quangduy.com.material.until.Constant;
import material.quangduy.com.material.until.GetData;
import material.quangduy.com.material.until.JSONParser;
import material.quangduy.com.material.until.getStringFromAsyn;

/**
 * Created by Quang Duy on 16-Sep-15.
 */
public class MainPageFragment extends Fragment{
   // static TextView textView;
    CustomViewPager viewPager;
    ExpandableHeightGridView gridView;
   ArrayList<BannerEntity> list_banner;
    ArrayList<ProductEntity> list_product;
    BannerAdapter adapter_banner;
    ListProduct_imageAdapter adapter_product;
    int category_id =0 ;
    private String  url_getproduct = Constant.Constant_info.IPADDRESS+"bkshop/get-product-for-category.php";
    int limit = 6;
    int offset = 0;
    int count=0;
    String result= "";
    Button more_btn ;
    int ktStock =0;
    JSONParser parser = new JSONParser();
    List<NameValuePair> param = new ArrayList<NameValuePair>();
    int product_count =0;
    int kt=0;
    public static MainPageFragment newInstance(int category_id)
    {
        MainPageFragment main = new MainPageFragment();
        Bundle args = new Bundle();
        args.putInt("category_id", category_id);
        main.setArguments(args);
        return main;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_mainpager, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null) {
             category_id = bundle.getInt("category_id");
            Log.e("categoty_id",""+category_id);
        }
        more_btn = (Button) layout.findViewById(R.id.more_btn);
        viewPager = (CustomViewPager) layout.findViewById(R.id.viewPager1);
        gridView = (ExpandableHeightGridView) layout.findViewById(R.id.gridView1);
        gridView.setExpanded(true);
        viewPager.setFocusable(false);

        Log.e("url",url_getproduct);
        list_banner = new ArrayList<BannerEntity>();
        list_banner.add(new BannerEntity(R.drawable.hitech_banner1, "http://192.168.1.100/db.php?name=thien"));
        list_banner.add(new BannerEntity(R.drawable.hitech_banner2,"http://192.168.1.100/db.php?name=duy"));
        list_banner.add(new BannerEntity(R.drawable.mobile_phone_banner,"http://192.168.1.100/db.php?name=tu"));

        list_banner.add(new BannerEntity(R.drawable.mobitel,"http://192.168.1.100/db.php?name=tien"));
//getInfomation
        list_product = new ArrayList<ProductEntity>();
        adapter_product = new ListProduct_imageAdapter(getActivity(),list_product);
        gridView.setAdapter(adapter_product);
/////////////////////////////////////
        //////////////////////
       // if(kt==0) {
        param.clear();
        param.add(new BasicNameValuePair("limit", Integer.toString(limit)));
        param.add(new BasicNameValuePair("offset", Integer.toString(offset)));
        param.add(new BasicNameValuePair("category_id", ""+Integer.toString(category_id)));
        final GetData getData = new GetData(getActivity(),url_getproduct,param);
            getData.execute();
            result = new getStringFromAsyn().getString((AsyncTask) getData);
            count = parser.parserJSONProduct(result, list_product);
            adapter_product.notifyDataSetChanged();

      //      kt=1;
      //  }
        Log.e("size of list", "" + list_product.size());
        Log.e("result", "" + result);
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ktStock == 0) {
                    if (count == limit) {
                        limit=6;
                        offset = offset + limit;
                        param.clear();
                        param.add(new BasicNameValuePair("limit", Integer.toString(limit)));
                        param.add(new BasicNameValuePair("offset", Integer.toString(offset)));
                        param.add(new BasicNameValuePair("category_id", "" + Integer.toString(category_id)));

                        GetData getData = new GetData(getActivity(),url_getproduct,param);
                        getData.execute();
                        result = new getStringFromAsyn().getString((AsyncTask) getData);
                        count = parser.parserJSONProduct(result, list_product);
                        //product_count+=count;
                        adapter_product.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Out", Toast.LENGTH_LONG).show();
                        ktStock=1;
                    }
                } else {
                   more_btn.setVisibility(View.GONE);
                }
            }
        });
       adapter_banner = new BannerAdapter(getActivity(),list_banner);

        viewPager.setAdapter(adapter_banner);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDetailFragment a = ProductDetailFragment.newInstance();
                a.setTmp(list_product.get(position));
                AppCompatActivity ac = (AppCompatActivity) getActivity();
                FragmentTransaction tran = ac.getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.container, a);
                tran.addToBackStack("MainPageFragment");
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
