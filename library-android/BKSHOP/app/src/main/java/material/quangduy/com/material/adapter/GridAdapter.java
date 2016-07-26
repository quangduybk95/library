package material.quangduy.com.material.adapter;

/**
 * Created by Quang Duy on 28-Nov-15.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import material.quangduy.com.material.R;
import material.quangduy.com.material.entities.ProductEntity;

/**
 * Created by Edwin on 28/02/2015.
 */
public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    List<ProductEntity> mItems;
    private LayoutInflater inflator;
    private Context context;
    public GridAdapter(List<ProductEntity> mItems,Context context) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        this.mItems =mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.productshow_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Log.e("GRID", " GRID");
        ProductEntity item = mItems.get(i);
        ImageLoader.getInstance().displayImage(item.getLinkImage(),viewHolder.imageView);
        viewHolder.nameProduct.setText(item.getmProductName());
        viewHolder.priceProduct.setText("" + item.getmProductPrice());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

         ImageView imageView;
         TextView nameProduct;
         TextView priceProduct;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgProduct);
            //holder.stock = (ImageView) convertView.findViewById(R.id.outStock);
            nameProduct = (TextView) itemView.findViewById(R.id.nameProduct);
            priceProduct = (TextView) itemView.findViewById(R.id.priceProduct);
        }
    }
}
