package material.quangduy.com.material.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import material.quangduy.com.material.R;
import material.quangduy.com.material.database.DatabaseOperations;
import material.quangduy.com.material.entities.UserEntity;
import material.quangduy.com.material.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private Toolbar mToolbar;
    private FragmentTransaction tran;
    NavigationView navigationView;
    public static final int REQUEST_CODE_INPUT = 123;
    public static DatabaseOperations dop;
    int ktLogin =0;
    public static UserEntity user = null;
    View headerView;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        username = (TextView) findViewById(R.id.username);
        //

        //
        dop = new DatabaseOperations(MainActivity.this);
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(MainActivity.this)
                .defaultDisplayImageOptions(displayImageOptions)
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);

//
        fragmentManager = getSupportFragmentManager();
        tran = fragmentManager.beginTransaction();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tran.replace(R.id.container, MainFragment.newInstance(), "MainFragment");
        tran.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home ) {
            super.onBackPressed();
            return true;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;}
//else
        if (id == R.id.action_search) {
          //  getWindow().setExitTransition(new Explode());
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);
                    //, ActivityOptions
                  //  .makeSceneTransitionAnimation(this).toBundle());
            return true;
        }
        if (id == R.id.action_cart) {
           // getWindow().setExitTransition(new Explode());
            Intent i = new Intent(MainActivity.this, CartActivity.class);
            startActivity(i);
                  //  , ActivityOptions
               //     .makeSceneTransitionAnimation(this).toBundle());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
@Override
public void onBackPressed() {
    if (fragmentManager.getBackStackEntryCount()  >= 1) {
        super.onBackPressed();
    }
    else  {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn muốn thoát ứng dụng ? ");
        builder.setPositiveButton("Có",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);

                    }
                });
        builder.setNegativeButton("Không",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //
}



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item != null) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_home) {

                Intent details = new Intent(MainActivity.this, MainActivity.class);
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(details);

                // Handle the camera action
            } else if (id == R.id.nav_gallery) {

            } else if (id == R.id.nav_slideshow) {

            } else if (id == R.id.nav_share) {

            } else if (id == R.id.nav_send) {
            }
            else if (id == R.id.nav_login) {
                //navigationView.getMenu().findItem(R.id.nav_login).setChecked(true);
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
                i.putExtra("code",1);
                startActivityForResult(i, REQUEST_CODE_INPUT);
            }
            else if (id == R.id.nav_logout) {
                navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_history).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_userinfo).setVisible(false);
                username.setText("BK SHOP");

            }
            else if(id == R.id.nav_userinfo)
            {
                Intent i1 = new Intent(MainActivity.this,InfoActivity.class);
                i1.putExtra("user_email",user.getUserEmail());
                startActivity(i1);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE_INPUT) {
                Bundle bundle = data.getBundleExtra("package");
                String result = bundle.getString("result");
                if(result.compareTo("success")==0)
                {
                    ktLogin =1;
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_history).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_userinfo).setVisible(true);
                    user = (UserEntity) data.getSerializableExtra("user");
                    username.setText(user.getUserEmail());
                    Toast.makeText(MainActivity.this,"Chào mừng bạn đã đến với BKSHOP !",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        if(user!=null)
        {
            ktLogin =1;
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_history).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_userinfo).setVisible(true);
            username.setText(user.getUserEmail());

        }
        super.onResume();
    }

}
