package com.example.a10609516.myapplication;

import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class Day_Work extends AppCompatActivity {

    private TextView date_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day__work);

        date_textView = (TextView) findViewById(R.id.date_textView);

        DateToday();
    }

    //獲取當前年月日
    private void DateToday() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new java.util.Date());

        date_textView.setText(date);
    }
}
