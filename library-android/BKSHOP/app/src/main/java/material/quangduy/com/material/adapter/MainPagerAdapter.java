package material.quangduy.com.material.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import material.quangduy.com.material.entities.CategoryEntity;
import material.quangduy.com.material.fragment.MainPageFragment;

/**
 * Created by Quang Duy on 16-Sep-15.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
   static ArrayList<CategoryEntity> tabName;
    int NumbOfTabs;

    public static ArrayList<CategoryEntity> getTabName() {
        return tabName;
    }

    public static void setTabName(ArrayList<CategoryEntity> tabName) {
        MainPagerAdapter.tabName = tabName;
    }

    public MainPagerAdapter(FragmentManager fm,Context context,int mNumbOfTabsumb) {
        super(fm);
       // tabName = context.getResources().getStringArray(R.array.tabs);
        this.NumbOfTabs = mNumbOfTabsumb;
    }
    @Override
    public Fragment getItem(int position) {
       // if(position!=0)

      //  {
            MainPageFragment fragment = MainPageFragment.newInstance(tabName.get(position).getmCategoryID());
            return fragment;
      //  }
//        else
//        {
//            secondPageFragment f = secondPageFragment.newInstance();
//            return  f;
//        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabName.get(position).getmCategoryName();
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
