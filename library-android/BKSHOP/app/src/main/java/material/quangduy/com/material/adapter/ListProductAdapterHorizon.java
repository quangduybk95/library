package material.quangduy.com.material.adapter;


import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import material.quangduy.com.material.R;
import material.quangduy.com.material.entities.ProductEntity;

public class ListProductAdapterHorizon extends BaseAdapter {
	private Context context;
	private ArrayList<ProductEntity> list;
	private LayoutInflater mInflater;
	private FragmentTransaction tran;


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public ListProductAdapterHorizon(Context context, ArrayList<ProductEntity> list) {
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.itemhorizon,
					parent, false);
			holder = new ViewHolder();

			holder.img = (ImageView) convertView.findViewById(R.id.image);
			holder.nameProduct = (TextView) convertView.findViewById(R.id.name);
			holder.priceProduct = (TextView) convertView
					.findViewById(R.id.price);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ProductEntity item = list.get(position);
		
		holder.img.setFocusable(false);
		holder.img.setFocusableInTouchMode(false);
		holder.nameProduct.setFocusable(false);
		holder.nameProduct.setFocusableInTouchMode(false);
		holder.priceProduct.setFocusable(false);
		holder.priceProduct.setFocusableInTouchMode(false);
        ImageLoader.getInstance().displayImage(item.getLinkImage(), holder.img);
//		new DownloadImageTask(holder.img).execute(item.getLinkImage());
		holder.nameProduct.setText(item.getmProductName());
		holder.priceProduct.setText("$" + item.getmProductPrice());
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
		TextView nameProduct;
		TextView priceProduct;
	}

}

