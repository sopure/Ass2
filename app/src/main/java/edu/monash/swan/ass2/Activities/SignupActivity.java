package edu.monash.swan.ass2.Activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import edu.monash.swan.ass2.Common.DatePickerFragment;
import edu.monash.swan.ass2.Common.MD5Util;
import edu.monash.swan.ass2.Common.RestClient;
import edu.monash.swan.ass2.Common.Student;
import edu.monash.swan.ass2.R;
import edu.monash.swan.ass2.Common.Const;

// implement interface[DatePickerFragment.OnDateSetListener] declared in fragment
public class SignupActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener {

    private TextView mtv_showDoB;
    private TextView mtv_login;

    private EditText met_email;
    private EditText met_pswd;
    private EditText met_repswd;
    private EditText met_firstNm;
    private EditText met_surNm;
    private EditText met_addr;
    private EditText met_suburb;
    private EditText met_favoriteMv;
    private EditText met_FavoriteUt;
    private EditText met_CurrentJb;
    private EditText msp_favoriteSport;


    private RadioGroup mrdg_gender;
    private RadioGroup mrdg_studyMd;

    private Spinner msp_course;
    private Spinner msp_nation;
    private Spinner msp_nativeLanguage;
    private Button mbtn_signUp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //  TextView element
        mtv_showDoB = (TextView) findViewById(R.id.tv_showDoB);
        mtv_login = (TextView) findViewById(R.id.tv_login);
        //  EditText element
        met_email = (EditText) findViewById(R.id.et_newID);
        met_pswd = (EditText) findViewById(R.id.et_newPswd);
        met_repswd = (EditText) findViewById(R.id.et_rePswd);
        met_firstNm = (EditText) findViewById(R.id.et_firstNm);
        met_surNm = (EditText) findViewById(R.id.et_surNm);
        met_addr = (EditText) findViewById(R.id.et_addr);
        met_suburb = (EditText) findViewById(R.id.et_suburb);
        met_favoriteMv = (EditText) findViewById(R.id.et_favoriteMovie);
        met_FavoriteUt = (EditText) findViewById(R.id.et_favoriteUnit);
        met_CurrentJb = (EditText) findViewById(R.id.et_currentJob);
        msp_favoriteSport = (EditText) findViewById(R.id.et_favoriteSports);

        // RadioGroupButton
        mrdg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        mrdg_studyMd = (RadioGroup) findViewById(R.id.rg_studyMode);
        // Spinner element
        msp_course = (Spinner) findViewById(R.id.sp_course);
        msp_nation = (Spinner) findViewById(R.id.sp_nationality);
        msp_nativeLanguage = (Spinner) findViewById(R.id.sp_nativeLanguage);

        // CircularProgressButton element
        mbtn_signUp = findViewById(R.id.btn_signup);

        // Spinner click listener
        msp_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_nation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        msp_nativeLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mbtn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myId = met_email.getText().toString();
                String myPswd = met_pswd.getText().toString();
                String rePswd = met_repswd.getText().toString();
                String firstNm = met_firstNm.getText().toString();
                String surNm = met_surNm.getText().toString();
                String addr = met_addr.getText().toString();
                String suburb = met_suburb.getText().toString();
                String favoriteMv = met_favoriteMv.getText().toString();
                String favoriteUt = met_FavoriteUt.getText().toString();
                String currentJb = met_CurrentJb.getText().toString();
                String favoriteSpt = msp_favoriteSport.getText().toString();
                String doB = mtv_showDoB.getText().toString() + "T00:00:00+08:00";
                RadioButton rb1 = findViewById(mrdg_gender.getCheckedRadioButtonId());
                String gender = rb1.getText().toString();
                RadioButton rb2 = findViewById(mrdg_studyMd.getCheckedRadioButtonId());
                String studyMd = rb2.getText().toString();
                String course = msp_course.getSelectedItem().toString();
                String nativeLanguage = msp_nativeLanguage.getSelectedItem().toString();
                String nation = msp_nation.getSelectedItem().toString();

                // Validate user input
                if (myId.isEmpty()) {
                    met_email.setError("email address is required!");
                    return;
                }
                if(RestClient.findByEmail(myId) != null){
                    met_email.setError("email address has been registered");
                    return;
                }
                String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
                if(!myId.matches(regex)){
                    met_email.setError("please input correct email");
                    return;
                }
                regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
                if(!myPswd.matches(regex)){
                    met_pswd.setError("password must contain both numbers and letters, and must be 8-16 digits in length!");
                    return;
                }
                if (myPswd.isEmpty()) {
                    met_pswd.setError("password is required!");
                    return;
                }
                if (rePswd.isEmpty()) {
                    met_repswd.setError("re-password is required!");
                    return;
                }
                if (firstNm.isEmpty()) {
                    met_firstNm.setError("firstNm is required!");
                    return;
                }
                if (surNm.isEmpty()) {
                    met_surNm.setError("surtNm is required!");
                    return;
                }
                if (!myPswd.equals(rePswd)) {
                    met_repswd.setError("Two different password input!");
                    return;
                }
                if (doB.equals("click to selectT00:00:00+08:00")) {
                    Toast.makeText(getApplicationContext(), "please input your birth!", Toast.LENGTH_SHORT).show();
                    return;
                }
                myPswd = MD5Util.getMD5(myPswd);
                Student stu = new Student(firstNm, surNm, doB, gender, course, studyMd, addr, suburb, nation, nativeLanguage, favoriteSpt, favoriteMv, favoriteUt, currentJb, myId, myPswd);
                String student = stu.convert().toString();
                String result = RestClient.Create(student);
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getInt("result") == 200){
                        stu.setId(stu.getEmail().hashCode());
                        Const.student = stu;
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // jump to login UI
        mtv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //
        int keyCode=0;
        if (keyCode == KeyEvent.KEYCODE_BACK) {}
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    // 4. Implement method in interface
    public void onDateSet(int year, int month, int day) {
        String s_year;
        String s_month;
        String s_day;
        if (year < 100) {
            s_year = Integer.toString(year);
            s_year = "00" + s_year;
        } else if (year < 1000) {
            s_year = Integer.toString(year);
            s_year = "0" + s_year;
        } else {
            s_year = Integer.toString(year);
        }

        if (month < 9) {
            s_month = Integer.toString(++month);
            s_month = '0' + s_month;
        } else {
            s_month = Integer.toString(++month);
        }

        if (day < 10) {
            s_day = Integer.toString(day);
            s_day = '0' + s_day;
        } else {
            s_day = Integer.toString(day);
        }

        mtv_showDoB.setText(s_year + "-" + s_month + "-" + s_day);
    }

}
