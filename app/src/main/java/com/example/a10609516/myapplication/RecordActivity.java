package com.example.a10609516.myapplication;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class RecordActivity extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent = new Intent(RecordActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent4 = new Intent(RecordActivity.this, ScheduleActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(RecordActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent2 = new Intent(RecordActivity.this, SearchActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; // 進入查詢派工資料頁面
            case R.id.pending_item:
                Intent intent3 = new Intent(RecordActivity.this, PendingActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "待處理派工", Toast.LENGTH_SHORT).show();
                break; //進入待處理派工頁面
            case R.id.record_item:
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; // 顯示上傳日報紀錄
            case R.id.picture_item:
                Intent intent5 = new Intent(RecordActivity.this, PictureActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳
            case R.id.customer_item:
                Intent intent6 = new Intent(RecordActivity.this, CustomerActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            case R.id.upload_item:
                Intent intent7 = new Intent(RecordActivity.this, UploadActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent8 = new Intent(RecordActivity.this, CorrectActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            default:
        }
        return true;
    }





    private TextView time_textView1,time_textView2,time_textView3,time_textView4,time_textView5,time_textView6,time_textView7;


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


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c0 = Calendar.getInstance();
        c0.add(Calendar.DATE, 0);
        Date resultdate0 = new Date(c0.getTimeInMillis());
        String date0 = simpleDateFormat.format(resultdate0);

        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, -1);
        Date resultdate1 = new Date(c1.getTimeInMillis());
        String date1 = simpleDateFormat.format(resultdate1);

        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.DATE, -2);
        Date resultdate2 = new Date(c2.getTimeInMillis());
        String date2 = simpleDateFormat.format(resultdate2);

        Calendar c3 = Calendar.getInstance();
        c3.add(Calendar.DATE, -3);
        Date resultdate3 = new Date(c3.getTimeInMillis());
        String date3 = simpleDateFormat.format(resultdate3);

        Calendar c4 = Calendar.getInstance();
        c4.add(Calendar.DATE, -4);
        Date resultdate4 = new Date(c4.getTimeInMillis());
        String date4 = simpleDateFormat.format(resultdate4);

        Calendar c5 = Calendar.getInstance();
        c5.add(Calendar.DATE, -5);
        Date resultdate5 = new Date(c5.getTimeInMillis());
        String date5 = simpleDateFormat.format(resultdate5);

        Calendar c6 = Calendar.getInstance();
        c6.add(Calendar.DATE, -6);
        Date resultdate6 = new Date(c6.getTimeInMillis());
        String date6 = simpleDateFormat.format(resultdate6);

        time_textView1.setText(date0);
        time_textView2.setText(date1);
        time_textView3.setText(date2);
        time_textView4.setText(date3);
        time_textView5.setText(date4);
        time_textView6.setText(date5);
        time_textView7.setText(date6);

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("RecordActivity", "onDestroy");
    }
}

