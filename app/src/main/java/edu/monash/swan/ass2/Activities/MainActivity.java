package edu.monash.swan.ass2.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.Fragments.FindNewfragment;
import edu.monash.swan.ass2.Fragments.FriendsFragment;
import edu.monash.swan.ass2.Fragments.MovieFragment;
import edu.monash.swan.ass2.Fragments.ProfileFragment;
import edu.monash.swan.ass2.Fragments.WeatherFragment;
import edu.monash.swan.ass2.Movie.MovieInfo;
import edu.monash.swan.ass2.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private CoordinatorLayout right;
    private NavigationView left;
    private boolean isDrawer=false;
    private long exitTime = 0;

    private FragmentManager FM;

    private NavigationView navigationView;

    private TextView userText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化各控件

        navigationView = findViewById(R.id.nav_view);
        userText = navigationView.getHeaderView(0).findViewById(R.id.user);

        userText.setText("Welcome " + Const.student.getFirstName());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        right = findViewById(R.id.right);
        left = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(isDrawer){
                    return left.dispatchTouchEvent(motionEvent);
                }else{
                    return false;
                }
            }
        });
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer=true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                right.layout(left.getRight(), 0, left.getRight() + display.getWidth(), display.getHeight());
            }
            @Override
            public void onDrawerOpened(View drawerView) {}
            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer=false;
            }
            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        //获取到FragmentManager，在V4包中通过getSupportFragmentManager，
        //在系统中原生的Fragment是通过getFragmentManager获得的。
        FM = getSupportFragmentManager();
        //2.开启一个事务，通过调用beginTransaction方法开启。
        FragmentTransaction mFragmentTransaction =FM.beginTransaction();

        if(Const.weatherFragment != null){
            mFragmentTransaction.replace(R.id.content_main, Const.weatherFragment);
        }else {
            WeatherFragment weatherFragment  = new WeatherFragment();
            Const.weatherFragment = weatherFragment;
            mFragmentTransaction.replace(R.id.content_main, weatherFragment);
        }
        ini();
        mFragmentTransaction.commit();
    }


    public void ini(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Const.movieInfo = MovieInfo.getMoive(Const.student.getFavouriteMovie());
                Const.movieBitmap = NetworkUtil.getHttpBitmap(Const.movieInfo.getPoster());
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction mFragmentTransaction =FM.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if(Const.weatherFragment != null){
                mFragmentTransaction.replace(R.id.content_main, Const.weatherFragment);
            }else{
                WeatherFragment weatherFragment  = new WeatherFragment();
                Const.weatherFragment = weatherFragment;
                mFragmentTransaction.replace(R.id.content_main, weatherFragment);
            }
        } else if (id == R.id.nav_locationReport) {

        } else if (id == R.id.it_favoriteMovie) {
            /*Intent intent = new Intent(MainActivity.this, MovieActivity.class);
            startActivity(intent);*/
            //向容器内加入Fragment，一般使用add或者replace方法实现，需要传入容器的id和Fragment的实例。
            //把自己创建好的fragment创建一个对象
            MovieFragment movieFragment  = MovieFragment.newInstance(Const.student.getFavouriteMovie());
            mFragmentTransaction.replace(R.id.content_main, movieFragment);
        } else if (id == R.id.profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            mFragmentTransaction.replace(R.id.content_main, profileFragment);
        } else if (id == R.id.Friends){
            FriendsFragment friendsFragment = new FriendsFragment();
            mFragmentTransaction.replace(R.id.content_main, friendsFragment);
        } else if (id == R.id.add_newFriend){
            FindNewfragment findNewFragment = new FindNewfragment();
            mFragmentTransaction.replace(R.id.content_main, findNewFragment);
        }
        mFragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //实现双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
