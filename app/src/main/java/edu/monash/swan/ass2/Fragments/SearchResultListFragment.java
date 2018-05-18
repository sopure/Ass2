package edu.monash.swan.ass2.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import database.DBStructure.DBManager;
import edu.monash.swan.ass2.Bean.Friendship;
import edu.monash.swan.ass2.Bean.FriendshipPK;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.Bean.SameAttriStud;
import edu.monash.swan.ass2.Bean.SameAttriStudLab;
import edu.monash.swan.ass2.Bean.Student;
import edu.monash.swan.ass2.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by owliz on 2017/5/10.
 */

public class SearchResultListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private NewFriendsAdapter mAdapter;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String monashEmail;
    private String currentDate;

    @Override
    // 配置视图
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        //持久化存储数据
        pref = this.getActivity().getSharedPreferences("admin", getActivity().getApplicationContext().MODE_PRIVATE);
        //获取SharedPreferences.Editor对象
        editor = pref.edit();
        String result = pref.getString("searchResult", "this is default value");
        Log.d("SearchResultFragment", "result shared is：" + result);
        monashEmail = pref.getString("monashEmail", "this is default value");
        Log.d("SearchResultFragment", "monashEmail shared is：" + result);

        currentDate = formatCurrentDate();
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.result_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            updateUI(result);
        } catch (JSONException mE) {
            mE.printStackTrace();
        }

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        //持久化存储数据
//        pref = this.getActivity().getSharedPreferences("admin", getActivity().getApplicationContext().MODE_PRIVATE);
//        //获取SharedPreferences.Editor对象
//        editor = pref.edit();
//        String result = pref.getString("searchResult", "this is default value");
//        Log.d("SearchResultFragment", "result in onresume is：" + result);
//        try {
//            updateUI(result);
//        } catch (JSONException mE) {
//            mE.printStackTrace();
//        }
//    }

    // initial adapter and recycleview
    private void updateUI(String result) throws JSONException {
        SameAttriStudLab sameAttriStudLab = SameAttriStudLab.get(getActivity(), result);
        List<SameAttriStud> sameAttriStuds = sameAttriStudLab.getSameAttriStuds();

        if (mAdapter == null) {
            mAdapter = new NewFriendsAdapter(sameAttriStuds);
            mCrimeRecyclerView.setAdapter(mAdapter);
//            mCrimeRecyclerView.getAdapter().notifyItemMoved(0, 5);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    // inner class  viewholder：for binding view in textview
    private class SameAttriStudHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mNameTextView;
        private TextView mEmailTextView;
        private TextView mGenderTextView;
        private TextView mCourseTextView;
        private TextView mFavoriteMovieTextView;
        private TextView mLineTextView;


        private SameAttriStud mSameAttriStud;

        public void bindSameAttriStud(SameAttriStud sameAttriStud) {
            mSameAttriStud = sameAttriStud;
            mTitleTextView.setText(mSameAttriStud.getTitle());
            mNameTextView.setText(mSameAttriStud.getFullName());
            mEmailTextView.setText(mSameAttriStud.getEmail());
            mGenderTextView.setText(mSameAttriStud.getGender());
            mCourseTextView.setText(mSameAttriStud.getCourse());
            mFavoriteMovieTextView.setText(mSameAttriStud.getFavoriteMovie());
            mLineTextView.setText("");
        }

        public SameAttriStudHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_same_attri_title_tv);
            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_same_attri_name_tv);
            mEmailTextView = (TextView) itemView.findViewById(R.id.list_item_same_attri_email_tv);
            mGenderTextView = (TextView) itemView.findViewById(R.id.list_item_same_attri_gender_tv);
            mCourseTextView = (TextView) itemView.findViewById(R.id.list_item_same_attri_course_tv);
            mFavoriteMovieTextView = (TextView) itemView.findViewById(R.id.list_item_same_attri_favoriteMv_tv);
            mLineTextView = (TextView) itemView.findViewById(R.id.list_item_same_attri_line_tv);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), mSameAttriStud.getFullName() + " is clicked!", Toast.LENGTH_SHORT).show(); // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(getActivity()).create();
            // 设置对话框标题
            isExit.setTitle("SYSTEM HINT");
            // 设置对话框消息
//            isExit.setMessage("Do you want to add this friend?");
            // 添加选择按钮并注册监听
//            isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "back", listener);
            isExit.setButton(DialogInterface.BUTTON_POSITIVE, "ADD FRIEND", listener);
            isExit.setButton(DialogInterface.BUTTON_NEUTRAL, "SEE HIS/HER FAVORITE MOVIE", listener);
            // 显示对话框
            isExit.show();
        }

        /**
         * 监听对话框里面的button点击事件
         */
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                        // 插入朋友关系；
                        //create an anonymous AsyncTask
                        new AsyncTask<String, Void, Integer>() {

                            @Override
                            protected Integer doInBackground(String... params) {
                                String myInfo, friendInfo, finalquery;
                                String Friendemail = mSameAttriStud.getEmail();
                                String[] arr = Friendemail.split("\\s+");
                                Friendemail = arr[arr.length - 1];
                                String myMonashEmail = monashEmail;
                                if (myMonashEmail.compareTo(Friendemail) > 0) {
                                    String tmp = "";
                                    tmp = Friendemail;
                                    Friendemail = myMonashEmail;
                                    myMonashEmail = tmp;

                                }
                                Log.d("SearchResultFragment", "final myMonashEmail is：" + myMonashEmail);
                                Log.d("SearchResultFragment", "final Friendemail is：" + Friendemail);
                                myInfo = NetworkUtil.getId(myMonashEmail);
                                friendInfo = NetworkUtil.getId(Friendemail);

                                // get json str by reducing '['and ']'
                                myInfo = myInfo.substring(1, myInfo.length() - 1);
                                friendInfo = friendInfo.substring(1, friendInfo.length() - 1);
                                Log.d("SearchResultFragment", "final myInfo is：" + myInfo);
                                Log.d("SearchResultFragment", "final friendInfo is：" + friendInfo);
                                Gson gson = new Gson();
                                //将返回的JSON数据转换为对象Student
                                Student stu1 = gson.fromJson(myInfo, Student.class);
                                Student stu2 = gson.fromJson(friendInfo, Student.class);

                                FriendshipPK fspPK = new FriendshipPK(myMonashEmail, Friendemail);
                                Friendship fsp = new Friendship(fspPK, currentDate, "", stu1, stu2);
                                if (NetworkUtil.createFriendship(fsp) == 204) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }

                            @Override
                            protected void onPostExecute(Integer info) {
                                if (info == 1) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Successful Added!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "You are friends already!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute();
                        break;
                    case AlertDialog.BUTTON_NEUTRAL:// SEE FAVORITEMOVIE

                        break;
                    default:
                        break;
                }
            }
        };
    }


    // inner class adpter: used to process object
    private class NewFriendsAdapter extends RecyclerView.Adapter<SameAttriStudHolder> {

        private List<SameAttriStud> mSameAttriStuds;

        public NewFriendsAdapter(List<SameAttriStud> sameAttriStuds) {
            mSameAttriStuds = sameAttriStuds;
        }

        @Override
        //produce new view for recyclerView
        public SameAttriStudHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_same_attributes_stud, parent, false);
            return new SameAttriStudHolder(view);
        }

        @Override
        //bind view and model(it's position)
        public void onBindViewHolder(SameAttriStudHolder holder, int position) {
            SameAttriStud sameAttriStud = mSameAttriStuds.get(position);
            holder.bindSameAttriStud(sameAttriStud);
        }

        @Override
        public int getItemCount() {
            return mSameAttriStuds.size();
        }
    }

    public String formatCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}
