package material.quangduy.com.material.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import material.quangduy.com.material.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            finish();
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
        }
    },1000);


    }

}
