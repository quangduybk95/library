package material.quangduy.com.material.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import material.quangduy.com.material.R;
import material.quangduy.com.material.another.Validation;
import material.quangduy.com.material.entities.UserEntity;
import material.quangduy.com.material.until.Constant;
import material.quangduy.com.material.until.GetData;
import material.quangduy.com.material.until.getStringFromAsyn;

public class SignupActivity extends AppCompatActivity {
    Button btn_dk;
    EditText email, password1, password2, fullName, address, phone, hitCapcha;
    TextView capcha;
    List<NameValuePair> paramCheckEmail = new ArrayList<NameValuePair>();
    List<NameValuePair> paramInserttUser = new ArrayList<NameValuePair>();

    private String urlCheckEmail = Constant.Constant_info.IPADDRESS + "bkshop/checkEmail.php";
    private String urlInsertUser = Constant.Constant_info.IPADDRESS+"bkshop/insert-user.php";

    String kqCheckEmail = "";
    String mabv = "";
    UserEntity user = new UserEntity();
    int kt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//

        btn_dk = (Button) findViewById(R.id.submit_btn);
        final GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Color.parseColor("#0099FF"));
        gdDefault.setCornerRadius(30);
        btn_dk.setBackgroundDrawable(gdDefault);
        btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSubmitClick();
            }
        });

        //
        address = (EditText) findViewById(R.id.userAddress);
        email = (EditText) findViewById(R.id.userEmail);
        password1 = (EditText) findViewById(R.id.userPass1);
        password2 = (EditText) findViewById(R.id.userPass2);
        fullName = (EditText) findViewById(R.id.userFullName);
        phone = (EditText) findViewById(R.id.userPhone);
        hitCapcha = (EditText) findViewById(R.id.hitCapcha);
        capcha = (TextView) findViewById(R.id.showCapcha);
        mabv += String.valueOf(Character.toChars(getRandomNumberFrom(48, 57)));
        for (int i = 0; i < 2; i++) {
            mabv += String.valueOf(Character.toChars(getRandomNumberFrom(65, 90)));
        }
        mabv += String.valueOf(Character.toChars(getRandomNumberFrom(48, 57)));
        for (int i = 0; i < 2; i++) {
            mabv += String.valueOf(Character.toChars(getRandomNumberFrom(97, 122)));
        }
        capcha.setText(mabv);
    }

    public int getRandomNumberFrom(int min, int max) {
        Random foo = new Random();
        int randomNumber = foo.nextInt((max + 1) - min) + min;
        return randomNumber;

    }

    public void OnSubmitClick() {
        Boolean required = true;
        if (Validation.isEmailAddress(email, required) && Validation.hasText(password1)&& Validation.hasText(address) && Validation.hasText(password2)
                 && Validation.hasText(hitCapcha) && Validation.hasText(phone) && Validation.hasText(fullName)) {
            if (password1.getText().toString().compareTo(password2.getText().toString()) != 0) {
                password2.setError("Đánh lại mật khẩu sai");
                Toast.makeText(SignupActivity.this, "Đánh lại mật khẩu sai", Toast.LENGTH_LONG).show();
            } else if (hitCapcha.getText().toString().compareTo(capcha.getText().toString()) != 0) {
                hitCapcha.setError("Sai mã bảo vệ");
            } else {
                //checkemail
                paramCheckEmail.add(new BasicNameValuePair("username", email.getText().toString()));
                GetData getData = new GetData(SignupActivity.this, urlCheckEmail, paramCheckEmail);
                getData.execute();
                String result = "";
                result = new getStringFromAsyn().getString((AsyncTask) getData);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.has("status")) {
                        kqCheckEmail = json.getString("status");
                    }
                } catch (JSONException e) {
                }

                if (kqCheckEmail.compareTo("exists") == 0) {
                    email.setError("Email đã sử dụng");
                } else {

                    //gui len sever
                    kt = 1;

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc chắn muốn tạo tài khoản này ?");
                    builder.setNegativeButton("Không",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.setPositiveButton("Có",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SubmitClick(dialog);
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                    kt=0;
                }

            }
        } else if (!Validation.hasText(password1) || !Validation.hasText(password2) || !Validation.hasText(phone)
                || !Validation.hasText(hitCapcha) || !Validation.hasText(fullName)|| !Validation.hasText(address)) {
            Toast.makeText(SignupActivity.this, "Bạn cần nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
        }
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
        if (kt != 1)
            super.onBackPressed();
        finish();
    }
    private void SubmitClick(DialogInterface dialog)
    {
        user.setUserEmail(email.getText().toString());
        user.setPassword(password1.getText().toString());
        user.setUserAddress(address.getText().toString());
        user.setUserFullName(fullName.getText().toString());
        user.setUserPhone(phone.getText().toString());
        paramInserttUser.add(new BasicNameValuePair("email", user.getUserEmail()));
        paramInserttUser.add(new BasicNameValuePair("pass",user.getPassword()));
        paramInserttUser.add(new BasicNameValuePair("address", user.getUserAddress()));
        paramInserttUser.add(new BasicNameValuePair("fullname", user.getUserFullName()));
        paramInserttUser.add(new BasicNameValuePair("phone", user.getUserPhone()));
        GetData getData = new GetData(SignupActivity.this,urlInsertUser,paramInserttUser);
        getData.execute();
        String result = new getStringFromAsyn().getString((AsyncTask) getData);
        String kq= "";
        try {
            JSONObject json = new JSONObject(result);
            if(json.has("status"))
            {
            kq = json.getString("status");
            }
        }
        catch (JSONException e) {
        }
        if(kq.compareTo("success")==0) {
            Bundle bundle = new Bundle();
            bundle.putString("result", "success");
            Intent intent = new Intent();
            intent.putExtra("package", bundle);
            intent.putExtra("user", user);
            setResult(LoginActivity.REQUEST_CODE_SIGNUP, intent);
            finish();
        }
        else
        {
        dialog.cancel();
            Toast.makeText(SignupActivity.this,"Lỗi tạo tài khoản",Toast.LENGTH_LONG).show();
        }
    }
}
