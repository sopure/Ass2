package edu.monash.swan.ass2.Fragments;

import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import database.DBStructure.DBManager;
import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.DatePickerFragment;
import edu.monash.swan.ass2.Bean.Student;
import edu.monash.swan.ass2.Common.MD5Util;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.R;



public class ProfileFragment extends Fragment {


    private TextView mtv_showDoB;

    private EditText met_email;
    private EditText met_pswd;
    private EditText met_firstNm;
    private EditText met_surNm;
    private EditText met_addr;
    private EditText met_suburb;
    private EditText met_favouriteMv;
    private EditText met_FavouriteUt;
    private EditText met_CurrentJb;
    private EditText msp_favouriteSport;


    private RadioGroup mrdg_gender;
    private RadioGroup mrdg_studyMd;

    private Spinner msp_course;
    private Spinner msp_nation;
    private Spinner msp_language;
    private Button mbtn_update;

    protected DBManager dbManager;

    View vProfileUnit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vProfileUnit = inflater.inflate(R.layout.fragment_profile, container, false);

        // 初始化dbManager
        dbManager = new DBManager(getActivity().getApplicationContext());


        //获取SharedPreferences值

        final String email = Const.student.getEmail();
        Log.d("Profile", "获取到的email是：" + email);
        String password = "";
        final String firstName = Const.student.getFirstName();
        final String surname = Const.student.getSurname();
        String doB = Const.student.getDob();
        String gender = Const.student.getGender();
        final String course = Const.student.getCourse();
        final String studyMode = Const.student.getStudyMode();
        final String address = Const.student.getAddress();
        String suburb = Const.student.getSuburb();
        final String nationality = Const.student.getNationality();
        String language = Const.student.getLanguage();
        final String favouriteSport = Const.student.getFavouriteSport();
        final String favouriteMovie = Const.student.getFavouriteMovie();
        final String favouriteUnit = Const.student.getFavouriteUnit();
        final String currentJob = Const.student.getCurrentJob();


        //  TextView element
        mtv_showDoB = (TextView) vProfileUnit.findViewById(R.id.tv_showDoB);
        met_email = (EditText) vProfileUnit.findViewById(R.id.et_email);
        met_pswd = (EditText) vProfileUnit.findViewById(R.id.et_newPswd);
        met_firstNm = (EditText) vProfileUnit.findViewById(R.id.et_firstNm);
        met_surNm = (EditText) vProfileUnit.findViewById(R.id.et_surNm);
        met_addr = (EditText) vProfileUnit.findViewById(R.id.et_addr);
        met_suburb = (EditText) vProfileUnit.findViewById(R.id.et_suburb);
        met_favouriteMv = (EditText) vProfileUnit.findViewById(R.id.et_favoriteMovie);
        met_FavouriteUt = (EditText) vProfileUnit.findViewById(R.id.et_favoriteUnit);
        met_CurrentJb = (EditText) vProfileUnit.findViewById(R.id.et_currentJob);
        // RadioGroupButton
        mrdg_gender = (RadioGroup) vProfileUnit.findViewById(R.id.rg_gender);
        mrdg_studyMd = (RadioGroup) vProfileUnit.findViewById(R.id.rg_studyMode);
        // Spinner element
        msp_course = (Spinner) vProfileUnit.findViewById(R.id.sp_course);
        msp_nation = (Spinner) vProfileUnit.findViewById(R.id.sp_nationality);
        msp_language = (Spinner) vProfileUnit.findViewById(R.id.sp_nativeLanguage);
        msp_favouriteSport = (EditText) vProfileUnit.findViewById(R.id.et_favoriteSport);
        // CircularProgressButton element
        mbtn_update = (Button) vProfileUnit.findViewById(R.id.btn_update);

        // Loading spinner data from database
        loadNationSpinnerData();
        loadNativeLanguageSpinnerData();

        // Loading spinner data from stringArry
        loadCourseSpinnerData();
        //initial form
        met_email.setText(email);
        met_pswd.setText(password);
        met_firstNm.setText(firstName);
        met_surNm.setText(surname);
        doB = doB.substring(0, 10);
        mtv_showDoB.setText(doB);
        met_CurrentJb.setText(currentJob);
        met_addr.setText(address);
        met_suburb.setText(suburb);
        met_FavouriteUt.setText(favouriteUnit);
        met_favouriteMv.setText(favouriteMovie);
        //initial gender
        switch (gender) {
            case "female":
                ((RadioButton) mrdg_gender.getChildAt(0)).setChecked(true);
                break;
            case "male":
                ((RadioButton) mrdg_gender.getChildAt(1)).setChecked(true);
                break;
            default:
                ((RadioButton) mrdg_gender.getChildAt(2)).setChecked(true);
                break;
        }
        //initial studymode
        switch (studyMode) {
            case "full-time":
                ((RadioButton) mrdg_studyMd.getChildAt(0)).setChecked(true);
                break;
            case "part-time":
                ((RadioButton) mrdg_studyMd.getChildAt(1)).setChecked(true);
                break;
            default:
                break;
        }

//        Log.d("Profile", "获取到的course是：" + course);
        if (!course.equals("")) {
            setSpinnerItemSelectedByValue(msp_course, course);
        }
        if (!language.equals("")) {
            setSpinnerItemSelectedByValue(msp_language, language);
        }
        if (!nationality.equals("")) {
            setSpinnerItemSelectedByValue(msp_nation, nationality);
        }

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
        msp_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        /*mbtn_signup.setProgress(1); // set progress > 0 & < 100 to display indeterminate progress
        mbtn_signup.setProgress(100); // set progress to 100 or -1 to indicate complete or error state*/


        mbtn_update.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               final Integer myId = Const.student.getId();
                                               final String myPswd = met_pswd.getText().toString().isEmpty()?Const.student.getPassword():MD5Util.getMD5(met_pswd.getText().toString());;
                                               final String firstNm = met_firstNm.getText().toString();
                                               final String surNm = met_surNm.getText().toString();
                                               final String addr = met_addr.getText().toString();
                                               final String suburb = met_suburb.getText().toString();
                                               final String favoriteMv = met_favouriteMv.getText().toString();
                                               final String favoriteUt = met_FavouriteUt.getText().toString();
                                               final String currentJb = met_CurrentJb.getText().toString();
                                               final String doB = mtv_showDoB.getText().toString() + "T00:00:00+08:00";

                                               RadioButton rb1 = (RadioButton) vProfileUnit.findViewById(mrdg_gender.getCheckedRadioButtonId());
                                               final String gender = rb1.getText().toString();
                                               RadioButton rb2 = (RadioButton) vProfileUnit.findViewById(mrdg_studyMd.getCheckedRadioButtonId());
                                               final String studyMd = rb2.getText().toString();
                                               final String course = msp_course.getSelectedItem().toString();
                                               final String language = msp_language.getSelectedItem().toString();
                                               final String nation = msp_nation.getSelectedItem().toString();
                                               final String favoriteSpt = msp_favouriteSport.toString();

// Validate user input

                                               if (firstNm.isEmpty()) {
                                                   met_firstNm.setError("firstNm is required!");
                                                   return;
                                               }
                                               if (surNm.isEmpty()) {
                                                   met_surNm.setError("surtNm is required!");
                                                   return;
                                               }

                                               if (doB.equals("click to selectT00:00:00+08:00")) {
                                                   Toast.makeText(getActivity().getApplicationContext(), "please input your birth!", Toast.LENGTH_SHORT).show();
                                                   return;
                                               }

//create an anonymous AsyncTask
                                               new AsyncTask<String, Void, Integer>() {
                                                   @Override
                                                   protected Integer doInBackground(String... params) {
                                                       Student stu = new Student(firstNm, surNm, doB, gender, course, studyMd, addr, suburb, nation, language, favoriteSpt, favoriteMv, favoriteUt, currentJb, email, myPswd);

                                                       NetworkUtil.updateProfile(myId, stu);
                                                       return 1;
                                                   }

                                                   @Override
                                                   protected void onPostExecute(Integer info) {
                                                       Const.student.setEmail(email);
                                                       Const.student.setPassword(myPswd);
                                                       Const.student.setFirstName(firstNm);
                                                       Const.student.setSurname(surNm);
                                                       Const.student.setDob(doB);
                                                       Const.student.setGender(gender);
                                                       Const.student.setCourse(course);
                                                       Const.student.setStudyMode(studyMd);
                                                       Const.student.setAddress(addr);
                                                       Const.student.setSuburb(suburb);
                                                       Const.student.setNationality(nation);
                                                       Const.student.setLanguage(language);
                                                       Const.student.setFavouriteSport(favoriteSpt);
                                                       Const.student.setFavouriteMovie(favoriteMv);
                                                       Const.student.setFavouriteUnit(favoriteUt);
                                                       Const.student.setCurrentJob(currentJb);
                                                       new Handler().postDelayed(new Runnable() {
                                                           public void run() {
                                                           }
                                                       }, 1000);

                                                   }
                                               }.execute();
                                           }
                                       }
        );

        return vProfileUnit;
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

    /**
     * Function to load the Nationality spinner data from SQLite database
     */
    public void loadNationSpinnerData() {

        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Spinner Drop down elements
        List<String> lables = dbManager.getAllNation();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        msp_nation.setAdapter(dataAdapter);

        dbManager.close();
    }

    public void loadNativeLanguageSpinnerData() {

        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Spinner Drop down elements
        List<String> lables = dbManager.getAllNativeLanguage();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        msp_language.setAdapter(dataAdapter);

        dbManager.close();
    }

    /**
     * Function to load the Course spinner data from SQLite database
     */
    public void loadCourseSpinnerData() {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.courses, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        msp_course.setAdapter(adapter);
    }





    public String formatCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 根据值, 设置spinner默认选中:
     *
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter(); //得到SpinnerAdapter对象
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
//            Log.d("Profile", "获取到的spinner_value是：" + value);
            //use regex to match entry
            if (apsAdapter.getItem(i).toString().matches(value + "+")) {
                spinner.setSelection(i, true);// 默认选中项
                break;
            }
        }
    }
}
