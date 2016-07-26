package material.quangduy.com.material.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import material.quangduy.com.material.R;
import material.quangduy.com.material.another.Validation;
import material.quangduy.com.material.entities.LoginDialog;
import material.quangduy.com.material.entities.UserEntity;
import material.quangduy.com.material.until.Constant;
import material.quangduy.com.material.until.GetData;
import material.quangduy.com.material.until.JSONParser;
import material.quangduy.com.material.until.getStringFromAsyn;

public class LoginActivity extends AppCompatActivity {
    private TextView forget;
    private Button btn_signin;
    private EditText editEmail, editPassword;
    String url_login = Constant.Constant_info.IPADDRESS+"bkshop/login.php";
    JSONParser parser = new JSONParser();
    UserEntity user = new UserEntity();
    String result;
    String kq="";
    Button btn_create;
    List<NameValuePair> param = new ArrayList<NameValuePair>();
    public static final int REQUEST_CODE_SIGNUP = 108;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forget = (TextView) findViewById(R.id.textForget);
        SpannableString content = new SpannableString("Forgot your password ?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forget.setText(content);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText)findViewById(R.id.editPassword);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        final GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Color.parseColor("#666666"));
        btn_signin.setEnabled(false);

        TextWatcher textWatcher1 = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editEmail.getText().toString().compareTo("") != 0
                        && editPassword.getText().toString().compareTo("") != 0) {
                    gdDefault.setColor(Color.parseColor("#FF9933"));
                    btn_signin.setEnabled(true);
                } else {
                    gdDefault.setColor(Color.parseColor("#666666"));
                    btn_signin.setEnabled(false);
                }
            }

        };
        editEmail.addTextChangedListener(textWatcher1);
        TextWatcher textWatcher2 = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editEmail.getText().toString().compareTo("") != 0
                        && editPassword.getText().toString().compareTo("") != 0) {
                    gdDefault.setColor(Color.parseColor("#FF9933"));
                    btn_signin.setEnabled(true);
                } else {
                    gdDefault.setColor(Color.parseColor("#666666"));
                    btn_signin.setEnabled(false);
                }
            }

        };
        editPassword.addTextChangedListener(textWatcher2);
        gdDefault.setCornerRadius(30);
        btn_signin.setBackgroundDrawable(gdDefault);
        btn_signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                OnSignInClick();
            }
        });
//creat
        final GradientDrawable gdDefault1 = new GradientDrawable();
        gdDefault1.setColor(Color.parseColor("#FF9933"));
        gdDefault1.setCornerRadius(30);
        btn_create = (Button) findViewById(R.id.btn_creat);
        btn_create.setBackgroundDrawable(gdDefault1);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivityForResult(i,REQUEST_CODE_SIGNUP);

            }
        });

    }
    public void OnSignInClick() {
        Boolean required = true;
        user.setUserEmail(editEmail.getText().toString());
        user.setPassword(editPassword.getText().toString());
        param.add(new BasicNameValuePair("username", user.getUserEmail()));
        param.add(new BasicNameValuePair("password", user.getPassword()));
        GetData getData = new GetData(LoginActivity.this,url_login,param);
        getData.execute();
        result = new getStringFromAsyn().getString((AsyncTask) getData);
        try {
            JSONObject json = new JSONObject(result);
            if(json.has("status"))
            {
                kq = json.getString("status");

            }
            if(json.has("user_id"))
            {
                user.setUserId(Integer.parseInt(json.getString("user_id")));
            }
        }
        catch (JSONException e) {
        }
        if (Validation.isEmailAddress(editEmail, required)
                && Validation.hasText(editPassword)) {
            if (kq.compareTo("fail")==0) {
                LoginDialog cdd = new LoginDialog(LoginActivity.this,
                        "Sai mật khẩu hoặc email ");
                cdd.show();
                cdd.setCancelable(false);
            } else {
                int i=0;
                Bundle bundle = new Bundle();
                bundle.putString("result", "success");
                if(getIntent()!=null) {
                    i = getIntent().getExtras().getInt("code");
                }
                if(i==1) {
                    Intent intent = new Intent();
                    intent.putExtra("package", bundle);
                    intent.putExtra("user", user);
                    setResult(MainActivity.REQUEST_CODE_INPUT, intent);
                    finish();

                    InputMethodManager a = (InputMethodManager) LoginActivity.this
                            .getSystemService(Activity.INPUT_METHOD_SERVICE);
                    a.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus()
                            .getWindowToken(), 0);
                }
                else
                    if(i==2)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("package", bundle);
                        intent.putExtra("user", user);
                        setResult(CartActivity.REQUEST_CODE_INPUT_CART, intent);
                        finish();

                        InputMethodManager a = (InputMethodManager) LoginActivity.this
                                .getSystemService(Activity.INPUT_METHOD_SERVICE);
                        a.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus()
                                .getWindowToken(), 0);

                    }
                else
                    {

                    }
            }
        }
//        else
//        {
//            LoginDialog cdd = new LoginDialog(LoginActivity.this,
//                    "Bạn cần điền đầy đủ thông tin");
//            cdd.show();
//            cdd.setCancelable(false);
//
//        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE_SIGNUP) {
                Bundle bundle = data.getBundleExtra("package");
                String result = bundle.getString("result");
                if(result.compareTo("success")==0)
                {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("result", "success");
                    UserEntity user1 = (UserEntity)data.getSerializableExtra("user");
                    Intent intent = new Intent();
                    intent.putExtra("package", bundle1);
                    intent.putExtra("user",user1);
                    setResult(MainActivity.REQUEST_CODE_INPUT, intent);
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forall, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ) {
            super.onBackPressed();
            finish();
            return true;
        }
        // other menu select events may be present here

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
