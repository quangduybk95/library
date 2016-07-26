package material.quangduy.com.material.fragment;

/**
 * Created by Quang Duy on 17-Sep-15.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import material.quangduy.com.material.R;
import material.quangduy.com.material.adapter.MainPagerAdapter;
import material.quangduy.com.material.entities.CategoryEntity;
import material.quangduy.com.material.entities.SlidingTabLayout;
import material.quangduy.com.material.until.Constant;
import material.quangduy.com.material.until.GetData;
import material.quangduy.com.material.until.JSONParser;
import material.quangduy.com.material.until.getStringFromAsyn;

/**
 * Created by Quang Duy on 16-Sep-15.
 */
public class MainFragment extends Fragment {
    String result = "";
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private FragmentManager fragmentManager;
    private FragmentTransaction tran;
    AppCompatActivity activity;
    JSONParser parser = new JSONParser();
    private String url_category = Constant.Constant_info.IPADDRESS+"bkshop/get-category-level-0.php";
    List<NameValuePair> param = new ArrayList<NameValuePair>();

    public static MainFragment newInstance() {
        MainFragment main = new MainFragment();
        return main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // keep the fragment and all its data across screen rotation
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.main_fragment_layout, container, false);

        activity = (AppCompatActivity) getActivity();
        fragmentManager = activity.getSupportFragmentManager();
        tran = fragmentManager.beginTransaction();
        mPager = (ViewPager) layout.findViewById(R.id.pagers);
        mTabs = (SlidingTabLayout) layout.findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        GetData getData = new GetData(getActivity(),url_category,param);
        getData.execute();
        //get result from asyntask
        result = new getStringFromAsyn().getString((AsyncTask) getData);
        ArrayList<CategoryEntity> list_category = new ArrayList<>();
        parser.parserJSONCategory(result, list_category);
        //setup SlingdingTab
        MainPagerAdapter adapter = new MainPagerAdapter(getChildFragmentManager(), getActivity(), list_category.size());
        adapter.setTabName(list_category);
        mPager.setAdapter(adapter);
        mTabs.setViewPager(mPager);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();
    }
}

