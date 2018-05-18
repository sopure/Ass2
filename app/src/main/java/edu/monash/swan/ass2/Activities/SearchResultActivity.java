package edu.monash.swan.ass2.Activities;



import android.support.v4.app.Fragment;


import edu.monash.swan.ass2.Fragments.SearchResultListFragment;


public class SearchResultActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SearchResultListFragment();
    }
}

