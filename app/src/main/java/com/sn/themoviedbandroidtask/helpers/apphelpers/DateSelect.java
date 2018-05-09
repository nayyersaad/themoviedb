package com.sn.themoviedbandroidtask.helpers.apphelpers;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.sn.themoviedbandroidtask.listeners.DateSelectedListener;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by TheArchitect on 5/8/2018.
 */
public class DateSelect extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    DateSelectedListener dateSelectedListener;

    public DateSelect(){}

    @SuppressLint("ValidFragment")
    public DateSelect(DateSelectedListener dateSelectedListener){
        this.dateSelectedListener = dateSelectedListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.dateSelectedListener.onDateSelected(Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(dayOfMonth));
    }
}
