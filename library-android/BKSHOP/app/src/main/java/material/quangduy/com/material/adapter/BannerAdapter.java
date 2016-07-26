package material.quangduy.com.material.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;

import material.quangduy.com.material.R;
import material.quangduy.com.material.entities.BannerEntity;
import material.quangduy.com.material.fragment.secondPageFragment;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<BannerEntity> list;
    public BannerAdapter(Context context, ArrayList<BannerEntity> list) {
        this.list = list;
        this.context = context;
    }
 
    public int getCount() {
        return list.size();
    }
 
    public Object instantiateItem(View collection, final int position) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        view.setScaleType(ScaleType.FIT_XY);
        view.setBackgroundResource(list.get(position).getImage_banner());
        ((ViewPager) collection).addView(view, 0);
        view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                AppCompatActivity ac = (AppCompatActivity) context;
                secondPageFragment a = secondPageFragment.newInstance();
                FragmentTransaction tran = ac.getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.container, a);
                tran.addToBackStack(null);
                tran.commit();

//				Intent intent = new Intent();
//				intent.setAction(Intent.ACTION_VIEW);
//				intent.setData(Uri.parse(list.get(position).getBanner_link()));
//				context.startActivity(intent);
			}
		});
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