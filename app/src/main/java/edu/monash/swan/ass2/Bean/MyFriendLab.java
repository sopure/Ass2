package edu.monash.swan.ass2.Bean;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyFriendLab {


        private static MyFriendLab sMyFriendLab;

        private List<MyFriend> mMyFriends;

        public static MyFriendLab get(Context context, String info1, String info2) throws JSONException {
//        if (sMyFriendLab == null) {
            sMyFriendLab = new MyFriendLab(context, info1, info2);
//        }
            return sMyFriendLab;
        }

        private MyFriendLab(Context context, String info1, String info2) throws JSONException {
            mMyFriends = new ArrayList<>();
            Log.d("MyFriend", "result shared is：" + info1);
            Log.d("MyFriend", "result shared is：" + info2);
            Gson gson = new Gson();
            JSONArray arr1 = null;
            JSONArray arr2 = null;
            int n=0;
            if (!info1.equals("[]")) {
                arr1 = new JSONArray(info1);
                for (int i = 0; i < arr1.length(); i++) {
                    n++;
                    MyFriend MyFriend = new MyFriend();
                    JSONObject jsonObj = (JSONObject) arr1.get(i);
                    //将返回的JSON数据转换为对象Student
                    Friendship fsp = gson.fromJson(String.valueOf(jsonObj), Friendship.class);
                    Integer id = fsp.getId();
                    Student stu = fsp.getFid();
                    String json = gson.toJson(stu);
                    Log.d("MyFriendLab", "stud1 json is：" + json);
                    String fullName = "name: " + stu.getFirstName() + " " + stu.getSurname();
                    String eMail = "email: " + stu.getEmail();
                    String gender = "gender: "+stu.getGender();
                    String course = "course: "+stu.getCourse();
                    String favoriteMovie = "favoriteMovie: "+stu.getFavouriteMovie();
                    String title = "FRIEND " + String.valueOf(i + 1);
                    MyFriend.setId(id);
                    MyFriend.setFullName(fullName);
                    MyFriend.setTitle(title);
                    MyFriend.setEmail(eMail);
                    MyFriend.setGender(gender);
                    MyFriend.setCourse(course);
                    MyFriend.setFavoriteMovie(favoriteMovie);
                    mMyFriends.add(MyFriend);
                }
            }
            if (!info2.equals("[]")) {
                arr2 = new JSONArray(info2);
                for (int i = 0; i < arr2.length(); i++) {
                    MyFriend MyFriend = new MyFriend();
                    JSONObject jsonObj = (JSONObject) arr2.get(i);
                    //将返回的JSON数据转换为对象Student
                    Friendship fsp = gson.fromJson(String.valueOf(jsonObj), Friendship.class);
                    Integer id = fsp.getId();
                    Student stu = fsp.getSid();
                    String json = gson.toJson(stu);
                    Log.d("MyFriendLab", "stud2 json is：" + json);
                    String fullName = "name: " + stu.getFirstName() + " " + stu.getSurname();
                    String eMail = "email: " + stu.getEmail();
                    String gender = "gender: "+stu.getGender();
                    String course = "course: "+stu.getCourse();
                    String favoriteMovie = "favoriteMovie: "+stu.getFavouriteMovie();
                    String title = "FRIEND " + String.valueOf(n++ + 1);
                    MyFriend.setId(id);
                    MyFriend.setFullName(fullName);
                    MyFriend.setTitle(title);
                    MyFriend.setEmail(eMail);
                    MyFriend.setGender(gender);
                    MyFriend.setCourse(course);
                    MyFriend.setFavoriteMovie(favoriteMovie);
                    mMyFriends.add(MyFriend);
                }
            }
        }

        public List<MyFriend> getMyFriends() {
            return mMyFriends;
        }


}
