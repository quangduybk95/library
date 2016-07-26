package material.quangduy.com.material.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import material.quangduy.com.material.R;
import material.quangduy.com.material.adapter.ListCartAdapter;
import material.quangduy.com.material.database.DatabaseOperations;
import material.quangduy.com.material.entities.ProductEntity;
import material.quangduy.com.material.entities.UserEntity;
import material.quangduy.com.material.until.Constant;
import material.quangduy.com.material.until.GetData;
import material.quangduy.com.material.until.getStringFromAsyn;

public class CartActivity extends AppCompatActivity {
    private ListView listCart;
    private ArrayList<ProductEntity> list = new ArrayList<ProductEntity>();
    private ListCartAdapter adapter;
    Button btn_pay, btn_deleteall;
    public static TextView amount;
    List<NameValuePair> param = new ArrayList<NameValuePair>();
    String url_insertOrder = Constant.Constant_info.IPADDRESS + "bkshop/insert-order.php";
    public static float all = 0;
    public static final int REQUEST_CODE_INPUT_CART = 111;
    String rs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listCart = (ListView) findViewById(R.id.listViewCart);
        DatabaseOperations dop = new DatabaseOperations(CartActivity.this);
        Cursor cr = dop.getAllData();

        if (cr.getCount() != 0) {
            cr.moveToFirst();
            do {
                ProductEntity tmp = new ProductEntity(cr.getInt(0),
                        cr.getString(1), cr.getString(4), cr.getFloat(2), cr.getInt(3));

                list.add(tmp);
                all += tmp.getmProductPrice() * tmp.getSoluong();
            }
            while (cr.moveToNext());
        }

        adapter = new ListCartAdapter(CartActivity.this, list);
        listCart.setAdapter(adapter);
        //
        amount = (TextView) findViewById(R.id.amout);
        amount.setText("$"+String.valueOf(all));
        //
        btn_deleteall = (Button) findViewById(R.id.btn_deleteall);
        btn_pay = (Button) findViewById(R.id.pay_btn);
        final GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Color.parseColor("#0099FF"));
        gdDefault.setCornerRadius(30);
        btn_deleteall.setBackgroundDrawable(gdDefault);
        btn_pay.setBackgroundDrawable(gdDefault);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.user != null) {
                    String order = "";
                    if (list.size() == 0) {
                        Toast.makeText(CartActivity.this, "Đơn hàng cần có ít nhất 1 sản phẩm", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            if (i != list.size() - 1)
                                order += list.get(i).getmProductID() + "," + list.get(i).getSoluong() + "|";
                            else {
                                order += list.get(i).getmProductID() + "," + list.get(i).getSoluong();
                            }
                        }
                        Log.e("ORDER", order);
                        param.clear();
                        param.add(new BasicNameValuePair("user_id", Integer.toString(MainActivity.user.getUserId())));
                        param.add(new BasicNameValuePair("order", order));
                        GetData getData = new GetData(CartActivity.this, url_insertOrder, param);
                        getData.execute();
                        rs = new getStringFromAsyn().getString((AsyncTask) getData);
                        Log.e(rs, "AAAAAAAAAAAAAAAAAAAAAAa");
                        try {
                            Log.e("AAAAA", "AAAAAAAA");
                            JSONObject json = new JSONObject(rs);
                            if (json.has("status")) {
                                String a = json.getString("status");
                                Log.e("BBBBBBB", "BBBBBBBB");
                                if (a.compareTo("success") == 0) {
                                   // Toast.makeText(CartActivity.this, "Bạn đã đặt hàng thành công , chúng tôi sẽ liên hệ với bạn trong thời gian ngắn nhất", Toast.LENGTH_LONG).show();
                                    Log.e("INSERT ORDER", "OK");
                                    MainActivity.dop.delete_all();
                                    list.clear();
                                    amount.setText("$0");
                                    CartActivity.all = 0;
                                    adapter.notifyDataSetChanged();
                                    //
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                    builder.setTitle("Thông báo");
                                    builder.setMessage("Bạn đã đặt hàng thành công , chúng tôi sẽ liên hệ với bạn trong thời gian ngắn nhất \n Bạn hãy kiểm tra trong lịch sử");
                                    builder.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                } else {
                                    Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    Log.e("INSERT ORDER", "FAIL");
                                }
                            }
                        } catch (JSONException e) {
                        }
                    }
                } else {
                    //Yeu cau dang nhap
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Yêu cầu đăng nhập ");
                    builder.setPositiveButton("Có",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(CartActivity.this, LoginActivity.class);
                                    i.putExtra("code", 2);
                                    startActivityForResult(i, REQUEST_CODE_INPUT_CART);

                                }
                            });
                    builder.setNegativeButton("Không",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(true);
                    dialog.show();

                }
            }

        });
        btn_deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dop.delete_all();
                list.clear();
                amount.setText("$0");
                CartActivity.all = 0;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forall, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            finish();
            return true;
        }
        // other menu select events may be present here

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE_INPUT_CART) {
                Bundle bundle = data.getBundleExtra("package");
                String result = bundle.getString("result");
                if(result.compareTo("success")==0)
                {
                    MainActivity.user = (UserEntity) data.getSerializableExtra("user");
                    Toast.makeText(CartActivity.this,"Chào mừng bạn đã đến với BKSHOP !",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        all=0;
        super.onStop();
    }
}
