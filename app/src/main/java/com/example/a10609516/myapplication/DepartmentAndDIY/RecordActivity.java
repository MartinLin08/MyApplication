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
import com.example.a10609516.myapplication.R;
import com.example.a10609516.myapplication.Basic.VersionActivity;
import com.example.a10609516.myapplication.Workers.CalendarActivity;
import com.example.a10609516.myapplication.Basic.QRCodeActivity;
import com.example.a10609516.myapplication.Workers.ScheduleActivity;
import com.example.a10609516.myapplication.Workers.SearchActivity;

import java.util.Date;

public class RecordActivity extends AppCompatActivity {


    /**
     * 創建Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences user_id = getSharedPreferences("department_id" , MODE_PRIVATE);
        String department_id_data = user_id.getString("D_ID" , "");
        if (department_id_data.toString().equals("2100")) {
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("2200")) {
            getMenuInflater().inflate(R.menu.diy_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("5200")) {
            getMenuInflater().inflate(R.menu.workers_menu, menu);
            return true;
        }else{
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
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
            /*case R.id.signature_item:
                Intent intent3 = new Intent(RecordActivity.this, SignatureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面*/
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
            case R.id.about_item:
                Intent intent9 = new Intent(RecordActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(RecordActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            default:
        }
        return true;
    }


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
