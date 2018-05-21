package edu.monash.swan.ass2.Fragments;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.dd.CircularProgressButton;

import edu.monash.swan.ass2.Activities.SearchResultActivity;
import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.Bean.Student;
import edu.monash.swan.ass2.R;


public class FindNewfragment extends Fragment {
    View vNewFellowUnit;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vNewFellowUnit = inflater.inflate(R.layout.fragment_findnew, container, false);

        //持久化存储数据
        pref = this.getActivity().getSharedPreferences("admin", getActivity().getApplicationContext().MODE_PRIVATE);

        //获取SharedPreferences.Editor对象
        editor = pref.edit();
        //获取SharedPreferences值
        final String monashEmail = Const.student.getEmail();
        final Integer sid = Const.student.getId();
        // checkBox
        final CheckBox mcb_doB = (CheckBox) vNewFellowUnit.findViewById(R.id.cb1);
        final CheckBox mcb_gender = (CheckBox) vNewFellowUnit.findViewById(R.id.cb2);
        final CheckBox mcb_course = (CheckBox) vNewFellowUnit.findViewById(R.id.cb3);
        final CheckBox mcb_studyMode = (CheckBox) vNewFellowUnit.findViewById(R.id.cb4);
        final CheckBox mcb_nationality = (CheckBox) vNewFellowUnit.findViewById(R.id.cb5);
        final CheckBox mcb_nativeLanguage = (CheckBox) vNewFellowUnit.findViewById(R.id.cb6);
        final CheckBox mcb_address = (CheckBox) vNewFellowUnit.findViewById(R.id.cb7);
        final CheckBox mcb_suburb = (CheckBox) vNewFellowUnit.findViewById(R.id.cb8);
        final CheckBox mcb_favouriteUnit = (CheckBox) vNewFellowUnit.findViewById(R.id.cb9);
        final CheckBox mcb_favoriteSport = (CheckBox) vNewFellowUnit.findViewById(R.id.cb10);
        final CheckBox mcb_favoriteMovie = (CheckBox) vNewFellowUnit.findViewById(R.id.cb11);

        // button
        final CircularProgressButton mbtn_search = (CircularProgressButton) vNewFellowUnit.findViewById(R.id.btn_search);
        mbtn_search.setText("SEARCH");
        mbtn_search.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               mbtn_search.setProgress(20);
                                               String attributes = "";
                                               if (mcb_doB.isChecked()) {
                                                   attributes += "dob ";
                                               }
                                               if (mcb_gender.isChecked()) {
                                                   attributes += "gender ";
                                               }
                                               if (mcb_course.isChecked()) {
                                                   attributes += "course ";
                                               }
                                               if (mcb_studyMode.isChecked()) {
                                                   attributes += "studyMode ";
                                               }
                                               if (mcb_nationality.isChecked()) {
                                                   attributes += "nationality ";
                                               }
                                               if (mcb_nativeLanguage.isChecked()) {
                                                   attributes += "language ";
                                               }
                                               if (mcb_address.isChecked()) {
                                                   attributes += "address ";
                                               }
                                               if (mcb_suburb.isChecked()) {
                                                   attributes += "suburb ";
                                               }
                                               if (mcb_favouriteUnit.isChecked()) {
                                                   attributes += "favouriteUnit ";
                                               }
                                               if (mcb_favoriteSport.isChecked()) {
                                                   attributes += "favouriteSport ";
                                               }
                                               if (mcb_favoriteMovie.isChecked()) {
                                                   attributes += "favouriteMovie ";
                                               }
                                               if (attributes.length() > 0) {
                                                   attributes = attributes.substring(0, attributes.length() - 1);
                                               }
                                               Log.d("FindNewFragment", "属性：" + attributes);
                                               if (attributes.isEmpty()) {
                                                   Toast.makeText(getActivity().getApplicationContext(), "please select attributes!", Toast.LENGTH_SHORT).show();
                                                   mbtn_search.setProgress(0);
                                                   return;
                                               }

//create an anonymous AsyncTask
                                               final String finalAttributes = attributes;
                                               new AsyncTask<String, Void, String>() {
                                                   @Override
                                                   protected String doInBackground(String... params) {
                                                       String result = NetworkUtil.searchFriends(String.valueOf(sid), finalAttributes);
                                                       editor.putString("searchResult", result);
                                                       editor.commit();
                                                       return result;
                                                   }

                                                   @Override
                                                   protected void onPostExecute(String result) {
                                                       mbtn_search.setProgress(0);
                                                       if(!result.equals("[]")){
                                                           mbtn_search.setCompleteText("Successful!");
                                                           Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                                                           startActivity(intent);}
                                                       else{Toast.makeText(getActivity().getApplicationContext(), "No matched student!", Toast.LENGTH_SHORT).show();}
                                                   }
                                               }.execute();
                                           }
                                       }
        );
        return vNewFellowUnit;
    }
}

