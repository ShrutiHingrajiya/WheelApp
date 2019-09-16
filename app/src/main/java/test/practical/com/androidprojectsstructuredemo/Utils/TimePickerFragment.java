package test.practical.com.androidprojectsstructuredemo.Utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;
import test.practical.com.androidprojectsstructuredemo.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {



    //**************************************
    //Call This
    //TimePickerFragment timePickerFragment=new TimePickerFragment();
    //timePickerFragment.show(getSupportFragmentManager(),"Select Time");
    //**************************************
    //:)


    OnTimeSetListener onDateSetListener;
    String StrTime;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour;
        int minute;

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog t = new TimePickerDialog(getActivity(), R.style.TimePickerTheme
                , this, hour, minute, false);

//        //t.getWindow().setNavigationBarDividerColor(R.color.colorAccent);
//        t.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        t.setTitle("Select Time");
        return t;

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            onDateSetListener = (OnTimeSetListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDateSetListener.");
        }
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Log.e("date", hourOfDay + "-" + minute + "-" + "00");

        StrTime = String.format("%02d:%02d:%02d", hourOfDay, minute, 0);

        onDateSetListener.onDateSet(view, hourOfDay, minute, StrTime);
    }


    public interface OnTimeSetListener {
        void onDateSet(android.widget.TimePicker view, int hourOfDay, int minute, String timevalue);
    }
}