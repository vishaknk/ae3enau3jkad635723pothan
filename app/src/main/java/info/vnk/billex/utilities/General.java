package info.vnk.billex.utilities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Visak on 27-12-2016.
 */

public class General {

    public static EditText editText;

    public static void setCalendar(Context context) {
        // TODO Auto-generated method stub
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                getEditText().setText(setDate(myCalendar));
            }
        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(getCurrentDate());

        datePickerDialog.show();
    }

    public static void setCalendar(Context context, long minDate, long maxDate) {
        // TODO Auto-generated method stub
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                getEditText().setText(setDate(myCalendar));
            }
        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        /*if(minDate != 0)
            datePickerDialog.getDatePicker().setMinDate(minDate);
        else*/
            datePickerDialog.getDatePicker().setMinDate(getMinDate());

        /*if(maxDate != 0)
            datePickerDialog.getDatePicker().setMaxDate(maxDate);
        else*/
            datePickerDialog.getDatePicker().setMaxDate(getCurrentDate());

        datePickerDialog.show();
    }

    public static EditText getEditText() {
        return editText;
    }

    public static void setEditText(EditText mEditText) {
        editText = mEditText;
    }

    public static String setDate(Calendar myCalendar) {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(myCalendar.getTime());
    }

    //convert dd MMM yyyy to yyyy MM dd format  from server
    public static String DateFormatter(String dateValue) {

        String dateInString = null;
        try {
            SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = sdfs.parse(dateValue);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateInString = sdf.format(date);
            Log.e("dateInString", "" + dateInString);
        } catch (Exception e) {

        }
        return dateInString;
    }

    //convert yyyy MM dd to dd MMM yyyy format  from server
    public static String DateFormatYearToDay(String dateValue) {

        String dateInString = null;
        try {
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdfs.parse(dateValue);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            dateInString = sdf.format(date);
            Log.e("dateInString", "" + dateInString);
        } catch (Exception e) {

        }
        return dateInString;
    }

    public static long getStringToDate(String string_date){
        long milliseconds = 0;
        if(string_date != null || !string_date.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date d = f.parse(string_date);
                milliseconds = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return milliseconds;
    }

    public static long getMinDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -8);
        return cal.getTimeInMillis();
    }

    public static long getCurrentDate(){
        return System.currentTimeMillis() - 1000;
    }

    //method for checking internet connected or not
    public static boolean isInternetConnected(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    //method for displaying short toast
    public static void showToast(Context context, String msg) {
        float scale = context.getResources().getDisplayMetrics().density;
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        int offsetY = (int) (-100 * scale);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, offsetY);
        toast.show();
    }


}
