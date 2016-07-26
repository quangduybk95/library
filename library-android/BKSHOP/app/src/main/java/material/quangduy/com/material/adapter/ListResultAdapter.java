package material.quangduy.com.material.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import material.quangduy.com.material.R;

public class ListResultAdapter  extends BaseAdapter{
    private Context context;
    private ArrayList<String> list;
    private LayoutInflater mInflater;
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    public ListResultAdapter(Context context, ArrayList<String> list) {

        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
        //this.c = a;
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
                    .inflate(R.layout.result_item_layout, null);
            holder = new ViewHolder();

            holder.result = (TextView) convertView.findViewById(R.id.result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item = list.get(position);
        holder.result.setText(item.toString());
        return convertView;
    }

    static class ViewHolder {
        TextView result;
    }
}
