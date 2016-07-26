package material.quangduy.com.material.adapter;


import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ProductDetailImageAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> list;

    public ProductDetailImageAdapter(Context context, ArrayList<String> list) {
        this.list = list;
        this.context = context;
    }
 
    public int getCount() {
        return list.size();
    }
 
    public Object instantiateItem(View collection, int position) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        view.setScaleType(ScaleType.FIT_CENTER);
//        new DownloadImageTask(view).execute(list.get(position));
        //view.setBackgroundResource(R.drawable.splash);
     //   ((ViewPager) collection).addView(view, 0);
        ImageLoader.getInstance().displayImage(list.get(position),view);
                ((ViewPager) collection).addView(view, 0);
        return view;
    }
 
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
 
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
 
    @Override
    public Parcelable saveState() {
        return null;
    }
}