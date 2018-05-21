package edu.monash.swan.ass2.Fragments;


import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
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

//import database.DBStructure.DBManager;
import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.DatePickerFragment1;
import edu.monash.swan.ass2.Bean.Student;
import edu.monash.swan.ass2.Common.MD5Util;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.R;
import com.dd.CircularProgressButton;


public class FriendProfileFrag extends Fragment {


    private TextView mtv_showDoB;

    private TextView mf_email;
    private TextView mf_pswd;
    private TextView mf_firstNm;
    private TextView mf_surNm;
    private TextView mf_addr;
    private TextView mf_suburb;
    private TextView mf_favouriteMv;
    private TextView mf_FavouriteUt;
    private TextView mf_CurrentJb;
    private TextView mf_favouriteSport;


    private TextView mf_gender;
    private TextView mf_studyMd;

    private TextView mf_course;
    private TextView mf_nation;
    private TextView mf_language;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
//    protected DBManager dbManager;

    View vProfileUnit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vProfileUnit = inflater.inflate(R.layout.fragment_profile_f, container, false);
        //持久化存储数据
        pref = this.getActivity().getSharedPreferences("admin", getActivity().getApplicationContext().MODE_PRIVATE);

        //获取SharedPreferences.Editor对象
        editor = pref.edit();
        final String monashEmail = pref.getString("monashEmail", "this is default value");
        Log.d("Profile", "获取到的monashEmail是：" + monashEmail);
        final String firstName = pref.getString("firstName", "this is default value");
        final String surname = pref.getString("surname", "this is default value");
        String doB = pref.getString("doB", "this is default value");
        String gender = pref.getString("gender", "this is default value");
        final String course = pref.getString("course", "this is default value");
        final String studyMode = pref.getString("studyMode", "this is default value");
        final String address = pref.getString("address", "this is default value");
        String suburb = pref.getString("suburb", "this is default value");
        final String nationality = pref.getString("nationality", "this is default value");
        String nativeLanguage = pref.getString("nativeLanguage", "this is default value");
        final String favoriteSport = pref.getString("favoriteSport", "this is default value");
        final String favoriteMovie = pref.getString("favoriteMovie", "this is default value");
        final String favouriteUnit = pref.getString("favouriteUnit", "this is default value");
        final String currentJob = pref.getString("currentJob", "this is default value");


        //  TextView element
        mtv_showDoB = (TextView) vProfileUnit.findViewById(R.id.tv_showDoB);
        //  EditText element
        mf_email = (TextView) vProfileUnit.findViewById(R.id.f_email);
        mf_firstNm = (TextView) vProfileUnit.findViewById(R.id.f_firstNm);
        mf_surNm = (TextView) vProfileUnit.findViewById(R.id.f_surNm);
        mf_addr = (TextView) vProfileUnit.findViewById(R.id.f_addr);
        mf_suburb = (TextView) vProfileUnit.findViewById(R.id.f_suburb);
        mf_favouriteMv = (TextView) vProfileUnit.findViewById(R.id.f_favoriteMovie);
        mf_FavouriteUt = (TextView) vProfileUnit.findViewById(R.id.f_favoriteUnit);
        mf_CurrentJb = (TextView) vProfileUnit.findViewById(R.id.f_currentJob);
        // RadioGroupButton
        mf_gender = (TextView) vProfileUnit.findViewById(R.id.f_gender);
        mf_studyMd = (TextView) vProfileUnit.findViewById(R.id.f_studyMode);
        // Spinner element
        mf_course = (TextView) vProfileUnit.findViewById(R.id.f_course);
        mf_nation = (TextView) vProfileUnit.findViewById(R.id.f_nationality);
        mf_language = (TextView) vProfileUnit.findViewById(R.id.f_language);
        mf_favouriteSport = (TextView) vProfileUnit.findViewById(R.id.f_favoriteSport);



        //initial form
        mf_email.setText(monashEmail);
        mf_firstNm.setText(firstName);
        mf_surNm.setText(surname);
        doB = doB.substring(0, 10);
        mtv_showDoB.setText(doB);
        mf_CurrentJb.setText(currentJob);
        mf_addr.setText(address);
        mf_suburb.setText(suburb);
        mf_FavouriteUt.setText(favouriteUnit);
        mf_favouriteMv.setText(favoriteMovie);
        mf_gender.setText(gender);
        mf_studyMd.setText(studyMode);
        mf_course.setText(course);
        mf_language.setText(nativeLanguage);
        mf_nation.setText(nationality);
        mf_favouriteSport.setText(favoriteSport);

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


    public String formatCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment1();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
