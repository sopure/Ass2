package edu.monash.swan.ass2.Activities;

import android.support.v4.app.Fragment;

import edu.monash.swan.ass2.Fragments.FriendProfileFrag;

public class ProfileActivity extends SingleFragmentActivity {


        @Override
        protected Fragment createFragment() {

            return new FriendProfileFrag();


        }
}



