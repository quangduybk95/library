package material.quangduy.com.material.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import material.quangduy.com.material.R;
import material.quangduy.com.material.entities.ProductEntity;

/**
 * Created by Quang Duy on 19-Nov-15.
 */
public  class ListProduct_imageAdapter extends BaseAdapter {

    private ArrayList<ProductEntity> list;

    private LayoutInflater inflater;
    ImageLoaderConfiguration config;
    private DisplayImageOptions options;
    Context context;
    public ListProduct_imageAdapter(Context context, ArrayList<ProductEntity> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.productshow_layout, parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.imgProduct);
            //holder.stock = (ImageView) convertView.findViewById(R.id.outStock);
            holder.nameProduct = (TextView) view.findViewById(R.id.nameProduct);
            holder.priceProduct = (TextView) view.findViewById(R.id.priceProduct);
          //  holder.progressBar = (ProgressBar) view.findViewById(R.id.progres);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ProductEntity item = list.get(position);
        ImageLoader.getInstance().displayImage(item.getLinkImage(),holder.imageView);
        holder.nameProduct.setText(item.getmProductName());
        holder.priceProduct.setText("$" + item.getmProductPrice());

        return view;

    }

    static class ViewHolder {
        ImageView imageView;
       // ImageView stock;
        TextView nameProduct;
        TextView priceProduct;
        ProgressBar progressBar;
    }
}