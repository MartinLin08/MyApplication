package com.example.a10609516.myapplication.DepartmentAndDIY;

import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.myapplication.Basic.MenuActivity;
import com.example.a10609516.myapplication.Clerk.QuotationActivity;
import com.example.a10609516.myapplication.Manager.InventoryActivity;
import com.example.a10609516.myapplication.R;
import com.example.a10609516.myapplication.Basic.VersionActivity;
import com.example.a10609516.myapplication.Tools.WQPServiceActivity;
import com.example.a10609516.myapplication.Workers.CalendarActivity;
import com.example.a10609516.myapplication.Basic.QRCodeActivity;
import com.example.a10609516.myapplication.Workers.EngPointsActivity;
import com.example.a10609516.myapplication.Workers.GPSActivity;
import com.example.a10609516.myapplication.Workers.MissCountActivity;
import com.example.a10609516.myapplication.Workers.PointsActivity;
import com.example.a10609516.myapplication.Workers.ScheduleActivity;
import com.example.a10609516.myapplication.Workers.SearchActivity;

import java.util.Date;

public class RecordActivity extends WQPServiceActivity {

    private TextView time_textView1, time_textView2, time_textView3, time_textView4, time_textView5, time_textView6, time_textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        time_textView1 = (TextView) findViewById(R.id.time_textView1);
        time_textView2 = (TextView) findViewById(R.id.time_textView2);
        time_textView3 = (TextView) findViewById(R.id.time_textView3);
        time_textView4 = (TextView) findViewById(R.id.time_textView4);
        time_textView5 = (TextView) findViewById(R.id.time_textView5);
        time_textView6 = (TextView) findViewById(R.id.time_textView6);
        time_textView7 = (TextView) findViewById(R.id.time_textView7);

        WeekDate();
    }


    //獲取當前一週年月日
    private void WeekDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c0 = Calendar.getInstance();
        c0.add(Calendar.DATE, 0);
        Date result_date0 = new Date(c0.getTimeInMillis());
        String date0 = simpleDateFormat.format(result_date0);

        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, -1);
        Date result_date1 = new Date(c1.getTimeInMillis());
        String date1 = simpleDateFormat.format(result_date1);

        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.DATE, -2);
        Date result_date2 = new Date(c2.getTimeInMillis());
        String date2 = simpleDateFormat.format(result_date2);

        Calendar c3 = Calendar.getInstance();
        c3.add(Calendar.DATE, -3);
        Date result_date3 = new Date(c3.getTimeInMillis());
        String date3 = simpleDateFormat.format(result_date3);

        Calendar c4 = Calendar.getInstance();
        c4.add(Calendar.DATE, -4);
        Date result_date4 = new Date(c4.getTimeInMillis());
        String date4 = simpleDateFormat.format(result_date4);

        Calendar c5 = Calendar.getInstance();
        c5.add(Calendar.DATE, -5);
        Date result_date5 = new Date(c5.getTimeInMillis());
        String date5 = simpleDateFormat.format(result_date5);

        Calendar c6 = Calendar.getInstance();
        c6.add(Calendar.DATE, -6);
        Date result_date6 = new Date(c6.getTimeInMillis());
        String date6 = simpleDateFormat.format(result_date6);

        time_textView1.setText(date0);
        time_textView2.setText(date1);
        time_textView3.setText(date2);
        time_textView4.setText(date3);
        time_textView5.setText(date4);
        time_textView6.setText(date5);
        time_textView7.setText(date6);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("RecordActivity", "onDestroy");
    }
}