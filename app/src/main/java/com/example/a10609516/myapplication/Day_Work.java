package com.example.a10609516.myapplication;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class Day_Work extends AppCompatActivity {

    private TextView date_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day__work);
        date_textView = (TextView) findViewById(R.id.date_textView);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 0);
        Date resultdate = new Date(c.getTimeInMillis());
        String date = simpleDateFormat.format(resultdate);

        date_textView.setText(date);
    }
}
