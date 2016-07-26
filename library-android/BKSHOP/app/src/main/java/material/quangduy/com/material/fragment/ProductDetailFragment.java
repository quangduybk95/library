package material.quangduy.com.material.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sileria.android.view.HorzListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import material.quangduy.com.material.R;
import material.quangduy.com.material.activity.MainActivity;
import material.quangduy.com.material.adapter.ListProductAdapterHorizon;
import material.quangduy.com.material.adapter.ProductDetailImageAdapter;
import material.quangduy.com.material.entities.ProductEntity;
import material.quangduy.com.material.until.Constant;
import material.quangduy.com.material.until.GetData;
import material.quangduy.com.material.until.JSONParser;
import material.quangduy.com.material.until.getStringFromAsyn;

public class ProductDetailFragment extends Fragment {
    static ProductEntity tmp;
    private ProductEntity x;
    ArrayList<ProductEntity> list = new ArrayList<>();
    private TextView nameProduct, des, price;
    private Button btn_add;
    static String url;
    String result ="";
    // private ImageView imgV;
    private RatingBar rate1;
    private ViewPager viewPager;
    private ProductDetailImageAdapter adapter;
    private HorzListView hlist;
    ListProductAdapterHorizon rela_adapter;
    public ProductEntity getTmp() {
        return tmp;
    }
    public void setTmp(ProductEntity tmp) {
        this.tmp = tmp;
    }
    private String  url_getproduct = Constant.Constant_info.IPADDRESS+"bkshop/get-rela.php";
    JSONParser parser = new JSONParser();
    List<NameValuePair> param = new ArrayList<NameValuePair>();
    TextView check;
    public static ProductDetailFragment newInstance() {
        ProductDetailFragment a = new ProductDetailFragment();
        return a;
    }

    // private ViewPager viewImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.productdetail_layout, container,
                false);
        check = (TextView)view.findViewById(R.id.checkS);
        check.setVisibility(View.GONE);
        nameProduct = (TextView) view.findViewById(R.id.nameProduct);
        nameProduct.setText(tmp.getmProductName());
        des = (TextView) view.findViewById(R.id.des);
        tmp.setmProductDesciption(tmp.getmProductDesciption().replaceAll("\\<br\\>", "\n"));
        des.setText(tmp.getmProductDesciption());
        price = (TextView) view.findViewById(R.id.price);
        price.setText(tmp.getmProductPrice() + "$");
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerDetail);
        adapter = new ProductDetailImageAdapter(getActivity(), tmp.getList_image());
        viewPager.setAdapter(adapter);
        btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cr = MainActivity.dop.getInformationById(
                        MainActivity.dop, tmp.getmProductID());
                Log.e("Y", "Y");
                if (cr.getCount() == 0) {
                    tmp.setSoluong(1);
                    MainActivity.dop.putInfomation(tmp);
                } else {
                    MainActivity.dop.update(tmp.getmProductID(), MainActivity.dop.getSoluongById(MainActivity.dop, tmp.getmProductID()) + 1);

                }
                Toast.makeText(getActivity(), "Thêm thành công ! Bạn hãy vào giỏ hàng kiểm tra", Toast.LENGTH_SHORT).show();
            }

        });
        final GradientDrawable gdDefault1 = new GradientDrawable();
        gdDefault1.setColor(Color.parseColor("#FF9933"));
        gdDefault1.setCornerRadius(30);
        btn_add.setBackgroundDrawable(gdDefault1);


        //hlist
        rela_adapter = new ListProductAdapterHorizon(getActivity(),list);
        hlist = (HorzListView) view.findViewById(R.id.hlistview);
        hlist.setAdapter(rela_adapter);
        param.clear();
        param.add(new BasicNameValuePair("product_id", "" + Integer.toString(tmp.getmProductID())));
        final GetData getData = new GetData(getActivity(),url_getproduct,param);
        getData.execute();
        result = new getStringFromAsyn().getString((AsyncTask) getData);
        Log.e("RESULT",result);
        int count = 0;
        count = parser.parserJSONProduct(result, list);
        rela_adapter.notifyDataSetChanged();
        if(count ==0)
        {
            hlist.setVisibility(View.GONE);
            check.setVisibility(View.VISIBLE);
        }
        hlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDetailFragment a = ProductDetailFragment.newInstance();
                a.setTmp(list.get(position));
                AppCompatActivity ac = (AppCompatActivity) getActivity();
                FragmentTransaction tran = ac.getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.container, a);
                tran.addToBackStack(null);
                tran.commit();

            }
        });
        //
        return view;
    }

}
