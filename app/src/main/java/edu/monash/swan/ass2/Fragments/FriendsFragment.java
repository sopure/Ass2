package edu.monash.swan.ass2.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import edu.monash.swan.ass2.Activities.MoiveActivity;
import edu.monash.swan.ass2.Bean.Friendship;
import edu.monash.swan.ass2.Bean.FriendshipPK;
import edu.monash.swan.ass2.Bean.MyFriend;
import edu.monash.swan.ass2.Bean.MyFriendLab;
import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.Bean.Student;
import edu.monash.swan.ass2.R;


    public class FriendsFragment extends Fragment {

        private RecyclerView mCrimeRecyclerView;
        private NewFriendsAdapter mAdapter;
        private Integer mid;
        private String mEmail;
        private String mInfo1;
        private String mInfo2;

        private SharedPreferences pref;
        private SharedPreferences.Editor editor;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
                savedInstanceState) {
            View vFriendsUnit = inflater.inflate(R.layout.fragment_friends, container, false);
            //持久化存储数据
            pref = this.getActivity().getSharedPreferences("admin", getActivity().getApplicationContext().MODE_PRIVATE);

            //获取SharedPreferences.Editor对象
            editor = pref.edit();


            mid = Const.student.getId();
            mEmail = Const.student.getEmail();


            mCrimeRecyclerView = (RecyclerView) vFriendsUnit.findViewById(R.id.result_recycler_view);
            mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            new AsyncTask<String, Void, Integer>() {
                @Override
                protected Integer doInBackground(String... params) {
                    String info1, info2;
                    Log.d("SearchResultFragment", "final myMonashEmail is：" + mEmail);
                    info1 = NetworkUtil.getFriendship1(String.valueOf(mid));
                    info2 = NetworkUtil.getFriendship2(String.valueOf(mid));
                    Log.d("FriendsFragment", "refresh info1 is：" + info1);
                    Log.d("FriendsFragment", "refresh info2 is：" + info2);
                    mInfo1 = info1;
                    mInfo2 = info2;
                    return 1;
                }

                @Override
                protected void onPostExecute(Integer array1) {
                    // wait for asynctask running
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                updateUI(mInfo1, mInfo2);
                            } catch (
                                    JSONException mE) {
                                mE.printStackTrace();
                            }
                        }
                    }, 50);

                }
            }.execute();
            return vFriendsUnit;
        }

        // initial adapter and recycleview

        private void updateUI(String info1, String info2) throws JSONException {
            MyFriendLab myFriendLab = MyFriendLab.get(getActivity(), info1, info2);
            List<MyFriend> sameAttriStuds = myFriendLab.getMyFriends();

            if (mAdapter == null) {
                mAdapter = new NewFriendsAdapter(sameAttriStuds);
                mCrimeRecyclerView.setAdapter(mAdapter);
//            mCrimeRecyclerView.getAdapter().notifyItemMoved(0, 5);
            } else {
                mAdapter.notifyDataSetChanged();
            }

        }

        // inner class  viewholder：for binding view in textview
        private class MyFriendHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

            private TextView mTitleTextView;
            private TextView mNameTextView;
            private TextView mEmailTextView;
            private TextView mGenderTextView;
            private TextView mCourseTextView;
            private TextView mFavoriteMovieTextView;
            private TextView mLineTextView;


            private MyFriend mMyFriend;

            public void bindMyFriend(MyFriend sameAttriStud) {
                mMyFriend = sameAttriStud;
                mTitleTextView.setText(mMyFriend.getTitle());
                mNameTextView.setText(mMyFriend.getFullName());
                mEmailTextView.setText(mMyFriend.getEmail());
                mGenderTextView.setText(mMyFriend.getGender());
                mCourseTextView.setText(mMyFriend.getCourse());
                mFavoriteMovieTextView.setText(mMyFriend.getFavoriteMovie());
                mLineTextView.setText("");
            }

            public MyFriendHolder(View itemView) {
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
//            Toast.makeText(getActivity(), mMyFriend.getFullName() + " is clicked!", Toast.LENGTH_SHORT).show(); // 创建退出对话框
                AlertDialog isExit = new AlertDialog.Builder(getActivity()).create();
                // 设置对话框标题
                isExit.setTitle("SYSTEM HINT");
                // 设置对话框消息
//            isExit.setMessage("Do you want to add this friend?");
                // 添加选择按钮并注册监听
                isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "DETAIL INFO", listener);
                isExit.setButton(DialogInterface.BUTTON_POSITIVE, "DELETE FRIEND", listener);
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
                        case AlertDialog.BUTTON_NEGATIVE://
                            // 删除朋友关系；
                            //create an anonymous AsyncTask
                            new AsyncTask<String, Void, Integer>() {

                                @Override
                                protected Integer doInBackground(String... params) {
                                    String myInfo, friendInfo, finalquery;
                                    Integer id = mMyFriend.getid();
                                    String Friendemail = mMyFriend.getEmail();
                                    String[] arr = Friendemail.split("\\s+");
                                    Friendemail = arr[arr.length - 1];
                                    String myMonashEmail = mEmail;
                                    Log.d("FriendsFragment", "delete myMonashEmail is：" + myMonashEmail);
                                    Student student = NetworkUtil.findByEmail(Friendemail);;
                                    editor.putString("monashEmail", student.getEmail());

                                    editor.putString("firstName", student.getFirstName());
                                    editor.putString("surname", student.getSurname());
                                    editor.putString("doB", student.getDob());
                                    editor.putString("gender", student.getGender());
                                    editor.putString("course", student.getCourse());
                                    editor.putString("studyMode", student.getStudyMode());
                                    editor.putString("address", student.getAddress());
                                    editor.putString("suburb", student.getSuburb());
                                    editor.putString("nationality", student.getNationality());
                                    editor.putString("nativeLanguage", student.getLanguage());
                                    editor.putString("favoriteSport", student.getFavouriteSport());
                                    editor.putString("favoriteMovie", student.getFavouriteMovie());
                                    editor.putString("favouriteUnit", student.getFavouriteUnit());
                                    editor.putString("currentJob", student.getCurrentJob());
                                    editor.commit();
                                    return 1;
                                }

                                @Override
                                protected void onPostExecute(Integer info) {

                                    Toast.makeText(getActivity().getApplicationContext(), "detail info", Toast.LENGTH_SHORT).show();
                                    // refresh fragment
                                    Fragment nextFragment = new FriendProfileFrag();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.content_main,
                                            nextFragment).commit();
                                }
                            }.execute();

                            break;
                        case AlertDialog.BUTTON_POSITIVE:// "取消"第二个按钮取消对话框

                            new AsyncTask<String, Void, Integer>() {

                                @Override
                                protected Integer doInBackground(String... params) {
                                    String myInfo, friendInfo, finalquery;
                                    Integer id = mMyFriend.getid();
                                    String Friendemail = mMyFriend.getEmail();
                                    String[] arr = Friendemail.split("\\s+");
                                    Friendemail = arr[arr.length - 1];
                                    String myMonashEmail = mEmail;
                                    if (myMonashEmail.compareTo(Friendemail) > 0) {
                                        String tmp = "";
                                        tmp = Friendemail;
                                        Friendemail = myMonashEmail;
                                        myMonashEmail = tmp;

                                    }
                                    Log.d("FriendsFragment", "delete myMonashEmail is：" + myMonashEmail);
                                    Log.d("FriendsFragment", "delete Friendemail is：" + Friendemail);
                                    NetworkUtil.deleteFriendship(id);

                                    return 1;
                                }

                                @Override
                                protected void onPostExecute(Integer info) {

                                    Toast.makeText(getActivity().getApplicationContext(), "Deleted Successful!", Toast.LENGTH_SHORT).show();
                                    // refresh fragment
                                    Fragment nextFragment = new FriendsFragment();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.content_main,
                                            nextFragment).commit();
                                }
                            }.execute();


                            break;
                        case AlertDialog.BUTTON_NEUTRAL:
                            new AsyncTask<String, Void, Integer>() {

                                @Override
                                protected Integer doInBackground(String... params) {
                                    String FavuriteMovie = mMyFriend.getFavoriteMovie();
                                    editor.putString("FovuriteMovie", FavuriteMovie);
                                    editor.commit();
                                    return 1;
                                }
                                @Override
                                protected void onPostExecute (Integer info) {
                                    Fragment nextFragment = MovieFragment.newInstance(mMyFriend.getFavoriteMovie());
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.content_main,
                                            nextFragment).commit();
                                }
                            }.execute();
                            break;


                        default:
                            break;
                    }
                }
            };
        }


        // inner class adpter: used to process object
        private class NewFriendsAdapter extends RecyclerView.Adapter<MyFriendHolder> {

            private List<MyFriend> mMyFriends;

            public NewFriendsAdapter(List<MyFriend> sameAttriStuds) {
                mMyFriends = sameAttriStuds;
            }

            @Override
            //produce new view for recyclerView
            public MyFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view = layoutInflater.inflate(R.layout.list_item_same_attributes_stud, parent, false);
                return new MyFriendHolder(view);
            }

            @Override
            //bind view and model(it's position)
            public void onBindViewHolder(MyFriendHolder holder, int position) {
                MyFriend sameAttriStud = mMyFriends.get(position);
                holder.bindMyFriend(sameAttriStud);
            }

            @Override
            public int getItemCount() {
                return mMyFriends.size();
            }
        }

        public String formatCurrentDate() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            return str;
        }


}
