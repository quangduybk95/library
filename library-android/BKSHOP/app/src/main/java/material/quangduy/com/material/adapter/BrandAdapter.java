package material.quangduy.com.material.adapter;

/**
 * Created by Quang Duy on 18-Nov-15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import material.quangduy.com.material.R;
import material.quangduy.com.material.entities.CategoryEntity;

public class BrandAdapter  extends BaseAdapter{
    private Context context;
    private ArrayList<CategoryEntity> list;
    private LayoutInflater mInflater;
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    public BrandAdapter(Context context, ArrayList<CategoryEntity> list) {

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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater
                    .inflate(R.layout.productshow_layout, null);
            holder = new ViewHolder();

            holder.img = (ImageView) convertView.findViewById(R.id.imgProduct);
          //  holder.stock = (ImageView) convertView.findViewById(R.id.outStock);
            holder.nameProduct = (TextView) convertView.findViewById(R.id.nameProduct);
            holder.priceProduct = (TextView) convertView.findViewById(R.id.priceProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CategoryEntity item = list.get(position);
        holder.img.setImageResource(R.drawable.splash);
       // new DownloadImageTask(holder.img).execute(item.getmCategoryImage());
        holder.nameProduct.setText(item.getmCategoryName());
     //   holder.priceProduct.setText(""+item.getmProductPrice());

        return convertView;
    }

    static class ViewHolder {
        ImageView img;
        ImageView stock;
        TextView nameProduct;
        TextView priceProduct;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
