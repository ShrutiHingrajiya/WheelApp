package test.practical.com.androidprojectsstructuredemo.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.fragment.app.DialogFragment;
import test.practical.com.androidprojectsstructuredemo.R;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private OnDateSetListener callbackListener;


    //**************************************
    //Call This
    //DatePickerFragment mDatePicker = new DatePickerFragment();
    //mDatePicker.show(getSupportFragmentManager(), "Select date");
    //**************************************
    //:)

    public static String getMonth(int month) {
        month = month + 1;

        if (month == 1) {
            return "Jan";
        } else if (month == 2) {
            return "Feb";
        } else if (month == 3) {
            return "Mar";
        } else if (month == 4) {
            return "Apr";
        } else if (month == 5) {
            return "May";
        } else if (month == 6) {
            return "Jun";
        } else if (month == 7) {
            return "Jul";
        } else if (month == 8) {
            return "Aug";
        } else if (month == 9) {
            return "Sep";
        } else if (month == 10) {
            return "Oct";
        } else if (month == 11) {
            return "Nov";
        } else return "Dec";
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            callbackListener = (OnDateSetListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDateSetListener.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year;
        int month;
        int day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        //DatePickerDialog d = new DatePickerDialog(getActivity(), this, year, month, day);
//            if (d == 0 && mo == 0 && y == 0) {
//
//            } else {
//
//                year = y;
//                month = mo;
//                day = d;
//            }
        DatePickerDialog d = new DatePickerDialog(getActivity(), R.style.DatePickerTheme, this, year, month, day);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 0);
        d.getDatePicker().setMinDate(cal.getTimeInMillis());
        d.setTitle(null);


        return d;

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        String StrMonth = getMonth(month);


        mcurrentDate.set(Calendar.YEAR, year);
        mcurrentDate.set(Calendar.MONTH, month);
        mcurrentDate.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat sdf = new SimpleDateFormat(
                "MM-dd-yyyy", Locale.US);

//            StrSelectMonth = year + "-" + StrMonth + "-" + day;
//            strMonth = day + " " + StrMonth + " " + year;
//            tvMonth.setText(strMonth);

        callbackListener.onDateSet(view, year, month, day);


    }

    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int month, int dayOfMonth);
    }
}