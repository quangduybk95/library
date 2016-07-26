package material.quangduy.com.material.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import material.quangduy.com.material.R;
import material.quangduy.com.material.activity.CartActivity;
import material.quangduy.com.material.activity.MainActivity;
import material.quangduy.com.material.entities.ProductEntity;

public class ListCartAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ProductEntity> list;
	private LayoutInflater mInflater;



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public ListCartAdapter(Context context, ArrayList<ProductEntity> list) {
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
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listcartitem, parent,
					false);
			holder = new ViewHolder();
			holder.delete = (Button) convertView.findViewById(R.id.btn_delete);
			holder.soluong = (TextView) convertView.findViewById(R.id.soluong);
			holder.img1 = (ImageView) convertView.findViewById(R.id.image1);
			holder.stock = (TextView) convertView.findViewById(R.id.stock);
			//holder.rate = (RatingBar) convertView.findViewById(R.id.rate);
			holder.nameProduct = (TextView) convertView.findViewById(R.id.name);
			holder.priceProduct = (TextView) convertView
					.findViewById(R.id.price);
			holder.up = (Button) convertView.findViewById(R.id.up);
			holder.down = (Button) convertView.findViewById(R.id.down);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ProductEntity item = list.get(position);
//		if (item.getmProductStock() == true)
//			holder.stock.setText("In Stock");
//		else
//			holder.stock.setText("Out Stock");
//		holder.rate.setRating(item.getRate());
		holder.soluong.setText(String.valueOf(item.getSoluong()));
	//	holder.rate.setEnabled(false);
//		LayerDrawable stars = (LayerDrawable) holder.rate.getProgressDrawable();
//		stars.getDrawable(2).setColorFilter(Color.BLUE,
//				PorterDuff.Mode.SRC_ATOP);
		ImageLoader.getInstance().displayImage(item.getLinkImage(), holder.img1);
		holder.nameProduct.setText(item.getmProductName());
		holder.priceProduct.setText("$" + item.getmProductPrice() * item.getSoluong());
		final GradientDrawable gdDefault = new GradientDrawable();
		gdDefault.setColor(Color.parseColor("#0099FF"));
		gdDefault.setCornerRadius(30);
		holder.delete.setBackgroundDrawable(gdDefault);
//		gdDefault.setCornerRadius(15);
//		holder.up.setBackgroundDrawable(gdDefault);
//		holder.down.setBackgroundDrawable(gdDefault);
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.dop.delete(item.getmProductID());
				CartActivity.all = CartActivity.all - item.getmProductPrice() * item.getSoluong();
				CartActivity.amount.setText(String.valueOf(CartActivity.all));
				list.remove(position);
				ListCartAdapter.this.notifyDataSetChanged();
			}
		});
		holder.up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                item.setSoluong(item.getSoluong() + 1);
                list.get(position).setSoluong(item.getSoluong());
				MainActivity.dop.update(item.getmProductID(), item.getSoluong());
				holder.priceProduct.setText("$"+String.valueOf(item.getSoluong()*item.getmProductPrice()));
				holder.soluong.setText(String.valueOf(item.getSoluong()));
				CartActivity.all = CartActivity.all + item.getmProductPrice();
				CartActivity.amount.setText("$"+String.valueOf(CartActivity.all));
			}
		});
		holder.down.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getSoluong() == 1) {
                    Toast.makeText(context, "Số lượng đã là nhỏ nhất", Toast.LENGTH_SHORT).show();
                } else {
                    item.setSoluong(item.getSoluong() - 1);
                    list.get(position).setSoluong(item.getSoluong());
                    MainActivity.dop.update(item.getmProductID(), item.getSoluong());
					holder.priceProduct.setText("$" + String.valueOf(item.getSoluong() * item.getmProductPrice()));
					holder.soluong.setText(String.valueOf(item.getSoluong()));
                    CartActivity.all = CartActivity.all - item.getmProductPrice();
                    CartActivity.amount.setText("$"+String.valueOf(CartActivity.all));
                }
            }
        });
//        IconicsDrawable a = new IconicsDrawable(context, FontAwesome.Icon.faw_minus).sizeDp(30);
//        IconicsDrawable b = new IconicsDrawable(context, FontAwesome.Icon.faw_plus).sizeDp(30);
//
//        holder.down.setBackgroundDrawable(a);
//        holder.up.setBackgroundDrawable(b);

        return convertView;
	}

	static class ViewHolder {
		ImageView img1;
		TextView stock;
        Button up,down;
		//RatingBar rate;
		TextView nameProduct;
		TextView priceProduct;
		TextView soluong;
		Button delete;
	}

}
