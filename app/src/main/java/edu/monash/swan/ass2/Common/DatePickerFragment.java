package edu.monash.swan.ass2.Common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;

/**
 * Created by owliz on 2017/5/8.
 */


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    OnDateSetListener mListener;

    @Override

    // 2. onAttach() detect whether the Activity has inherited the interface,
    // if not it will throw an exception
    // Called when a fragment is first attached to its context
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDateSetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDateSetListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        // 3. call method in interface to send values
        mListener.onDateSet(year, month, day);
    }

    // 1. Define a callback interface
    public interface OnDateSetListener{
        // method to be implemented in activity
        void onDateSet(int year, int month, int day);
    }

}
