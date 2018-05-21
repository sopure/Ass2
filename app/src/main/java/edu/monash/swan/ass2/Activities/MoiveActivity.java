package edu.monash.swan.ass2.Activities;


import android.support.v4.app.Fragment;

import edu.monash.swan.ass2.Fragments.movieFragmentF;

public class MoiveActivity extends SingleFragmentActivity {


        @Override
        protected Fragment createFragment() {

            return new movieFragmentF();


        }
}
