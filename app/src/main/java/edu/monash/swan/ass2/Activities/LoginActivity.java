package edu.monash.swan.ass2.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.monash.swan.ass2.Common.MD5Util;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.Bean.Student;
import edu.monash.swan.ass2.R;
import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.WeatherInfo.WeatherUtil;

public class LoginActivity extends Activity implements OnClickListener {
    private long exitTime = 0;
    private TextView mBtnLogin, mBtnSignup;
    private EditText mUsername, mPassword;
    private View progress;
    private View mInputLayout;
    private LinearLayout mName, mPsw;
    public static LoginActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_login);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Const.weather = WeatherUtil.requestWeather();
                //图片资源
                String requestBingPic = "http://guolin.tech/api/bing_pic";
                Const.bingPicUrl = NetworkUtil.SendGet(requestBingPic);
                Const.weatherBitmap = NetworkUtil.getHttpBitmap(Const.bingPicUrl);
            }
        }).start();

        initView();

    }

    private void initView() {
        mBtnLogin = findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = findViewById(R.id.input_layout_name);
        mPsw = findViewById(R.id.input_layout_psw);
        mBtnSignup = findViewById(R.id.main_btn_signup);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mBtnSignup.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.main_btn_login){
            if(verification()){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                TextView error = findViewById(R.id.error);
                error.setText("Account or password error, login failed!");
            }
        }
        if(v.getId() == R.id.main_btn_signup){
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        }

    }

    private boolean verification(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        Student student = NetworkUtil.findByEmail(username);
        if(student != null && MD5Util.getMD5(password).equals(student.getPassword())){
            Const.student = student;
            return true;
       }
        return false;
    }

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

