package material.quangduy.com.material.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import material.quangduy.com.material.R;
import material.quangduy.com.material.another.Validation;
import material.quangduy.com.material.entities.UserEntity;
import material.quangduy.com.material.until.Constant;
import material.quangduy.com.material.until.GetData;
import material.quangduy.com.material.until.JSONParser;
import material.quangduy.com.material.until.getStringFromAsyn;

public class InfoActivity extends AppCompatActivity {
    TextView user_email, user_pass;
    Button btn_changepass, btn_update;
    EditText user_fullname, user_phone, user_address;
    String url_getInfo = Constant.Constant_info.IPADDRESS + "bkshop/get-info.php";
    String url_update = Constant.Constant_info.IPADDRESS + "bkshop/update-info.php";
    String url_updatePass = Constant.Constant_info.IPADDRESS + "bkshop/updatePass.php";

    String url_checkPass = Constant.Constant_info.IPADDRESS + "bkshop/checkPass.php";
    List<NameValuePair> param = new ArrayList<>();
    String result = "";
    ArrayList<UserEntity> list;
    UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user_email = (TextView) findViewById(R.id.userEmails);
        user_pass = (TextView) findViewById(R.id.userPasss);
        btn_changepass = (Button) findViewById(R.id.btn_changepass);
        btn_update = (Button) findViewById(R.id.btn_update);
        user_fullname = (EditText) findViewById(R.id.userFullNames);
        user_phone = (EditText) findViewById(R.id.userPhones);
        user_address = (EditText) findViewById(R.id.userAddresss);
        final String email = getIntent().getExtras().getString("user_email");
        Log.e("Email", email);
        param.add(new BasicNameValuePair("user_email", email));
        GetData getData = new GetData(InfoActivity.this, url_getInfo, param);
        getData.execute();
        result = new getStringFromAsyn().getString((AsyncTask) getData);
        JSONParser jsonParser = new JSONParser();
        list = new ArrayList<UserEntity>();
        jsonParser.parse_User(result, list);
        Log.e("List.size", list.size() + "");
        if (list.size() != 0) {
            userEntity = list.get(0);
            user_fullname.setText(userEntity.getUserFullName());
            user_address.setText(userEntity.getUserAddress());
            user_pass.setText(userEntity.getPassword());
            user_email.setText(userEntity.getUserEmail());
            user_phone.setText(userEntity.getUserPhone());
        }
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update("Cập nhật thành công");
            }
        });

        btn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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

    public void Dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                InfoActivity.this);
        alertDialog.setTitle("Đổi mật khẩu");
        LinearLayout layout = new LinearLayout(InfoActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

//        final EditText titleBox = new EditText(context);
//        titleBox.setHint("Title");
//        layout.addView(titleBox);
//
//        final EditText descriptionBox = new EditText(context);
//        descriptionBox.setHint("Description");
//        layout.addView(descriptionBox);
//
//        dialog.setView(layout);

        final EditText inputPass1 = new EditText(InfoActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        inputPass1.setLayoutParams(lp);
        inputPass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPass1.setTransformationMethod(PasswordTransformationMethod.getInstance());

        inputPass1.setHint("Nhập mật khẩu hiện tại");
        layout.addView(inputPass1);
        final EditText inputPass2 = new EditText(InfoActivity.this);
        inputPass2.setLayoutParams(lp);
        inputPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        inputPass2.setHint("Nhập mật khẩu mới");
        layout.addView(inputPass2);
        final EditText inputPass3 = new EditText(InfoActivity.this);
        inputPass3.setLayoutParams(lp);
        inputPass3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPass3.setTransformationMethod(PasswordTransformationMethod.getInstance());
        inputPass3.setHint("Nhập lại mật khẩu mới");
        layout.addView(inputPass3);
        alertDialog.setView(layout);
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!Validation.hasText(inputPass1)||!Validation.hasText(inputPass2)||!Validation.hasText(inputPass3))
                        {
                            Toast.makeText(InfoActivity.this,"Hãy điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                        }
                        else
                        if (inputPass2.getText().toString().length() < 8 || inputPass2.getText().toString().length() > 16) {
                         //   inputPass2.setError("Mật khẩu phải có 8 đến 16 kí tự");
                            Toast.makeText(InfoActivity.this, "Mật khẩu phải có 8 đến 16 kí tự", Toast.LENGTH_SHORT).show();
                            Dialog();
                        } else if (checkPass(inputPass1.getText().toString())!=1) {
                          //  inputPass1.setError("Nhập sai mật khẩu");
                            Toast.makeText(InfoActivity.this, "Nhập sai mật khẩu", Toast.LENGTH_SHORT).show();
                            Dialog();
                        } else if (inputPass2.getText().toString().compareTo(inputPass3.getText().toString()) == 0) {
                            //user_pass.setText(inputPass2.getText().toString());
                            userEntity.setPassword(inputPass2.getText().toString());
                            updatePass(inputPass2.getText().toString());
                           // Toast.makeText(InfoActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        } else if (inputPass2.getText().toString().compareTo(inputPass3.getText().toString()) != 0) {
                           // inputPass3.setError("Nhập lại mật khẩu sai");
                            Toast.makeText(InfoActivity.this, "Nhập lại mật khẩu sai", Toast.LENGTH_SHORT).show();

                            Dialog();
                        }

                    }
                });

        alertDialog.setNegativeButton("Quay lại",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
        alertDialog.setCancelable(true);
    }

    private void update(String b) {
        param.clear();
        param.add(new BasicNameValuePair("email", user_email.getText().toString()));
        param.add(new BasicNameValuePair("phone", user_phone.getText().toString()));
        param.add(new BasicNameValuePair("address", user_address.getText().toString()));
        param.add(new BasicNameValuePair("fullname", user_fullname.getText().toString()));
        GetData getData = new GetData(InfoActivity.this, url_update, param);
        getData.execute();
        String rs = new getStringFromAsyn().getString((AsyncTask) getData);
        try {
            JSONObject json = new JSONObject(rs);
            if (json.has("status")) {
                String a = json.getString("status");
                if (a.compareTo("success") == 0) {
                    Toast.makeText(InfoActivity.this, b, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InfoActivity.this, "Error", Toast.LENGTH_LONG).show();

                }
            }
        } catch (JSONException e) {
        }

    }
    private void updatePass(String newpass) {
        param.clear();
        param.add(new BasicNameValuePair("pass", newpass));
        param.add(new BasicNameValuePair("email", userEntity.getUserEmail()));
        GetData getData = new GetData(InfoActivity.this, url_updatePass, param);
        getData.execute();
        String rs = new getStringFromAsyn().getString((AsyncTask) getData);
        try {
            JSONObject json = new JSONObject(rs);
            if (json.has("status")) {
                String a = json.getString("status");
                if (a.compareTo("success") == 0) {
                    Toast.makeText(InfoActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InfoActivity.this, "Error", Toast.LENGTH_LONG).show();

                }
            }
        } catch (JSONException e) {
        }

    }

public int checkPass(String pass)
{
    param.clear();
    param.add(new BasicNameValuePair("email", userEntity.getUserEmail()));
    param.add(new BasicNameValuePair("pass", pass));
    GetData getData = new GetData(InfoActivity.this,url_checkPass,param);
    getData.execute();
    String rs = new getStringFromAsyn().getString((AsyncTask) getData);
    try {
        JSONObject json = new JSONObject(rs);
        if (json.has("status")) {
            String a = json.getString("status");
            if (a.compareTo("success") == 0) {
                return 1;
            } else {
            return 0;
            }
        }
    } catch (JSONException e) {
    }
    return 0;
}
}
