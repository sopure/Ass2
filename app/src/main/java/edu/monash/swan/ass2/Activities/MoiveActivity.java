package edu.monash.swan.ass2.Activities;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Fragments.MovieFragment;
import edu.monash.swan.ass2.Fragments.movieFragmentF;

public class MoiveActivity extends SingleFragmentActivity {


        @Override
        protected Fragment createFragment() {

            return new movieFragmentF();


        }
}
