package edu.monash.swan.ass2.Bean;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class SameAttriStudLab {

    private static SameAttriStudLab sSameAttriStudLab;

    private List<SameAttriStud> mSameAttriStuds;

    public static SameAttriStudLab get(Context context, String result) throws JSONException {
//        if (sSameAttriStudLab == null) {
        sSameAttriStudLab = new SameAttriStudLab(context,result);
//        }
        return sSameAttriStudLab;
    }

    private SameAttriStudLab(Context context, String result) throws JSONException {
        mSameAttriStuds = new ArrayList<>();
        Log.d("SameAttriLab", "result shared is：" + result);
        JSONArray arr = new JSONArray(result);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonObj = (JSONObject) arr.get(i);
            SameAttriStud sameAttriStud = new SameAttriStud();

            String fullName = "name: "+jsonObj.getString("firstnm")+" "+jsonObj.getString("surname");
            String eMail = "email: "+jsonObj.getString("email");
            String gender = "gender: "+jsonObj.getString("gender");
            String course = "course: "+jsonObj.getString("course");
            String favoriteMovie = "favouriteMovie: "+jsonObj.getString("favouriteMovie");
            String title = "STUDENT "+ String.valueOf(i+1);
            sameAttriStud.setFullName(fullName);
            sameAttriStud.setTitle(title);
            sameAttriStud.setEmail(eMail);
            sameAttriStud.setGender(gender);
            sameAttriStud.setCourse(course);
            sameAttriStud.setFavoriteMovie(favoriteMovie);
            mSameAttriStuds.add(sameAttriStud);
        }
    }

    public List<SameAttriStud> getSameAttriStuds() {
        return mSameAttriStuds;
    }

    public SameAttriStud getCrime(UUID id) {
        for (SameAttriStud sameAttriStud : mSameAttriStuds) {
            if (sameAttriStud.getId().equals(id)) {
                return sameAttriStud;
            }
        }
        return null;
    }
}
