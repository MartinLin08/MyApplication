package com.example.a10609516.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

public class CorrectActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home_item:
                Intent intent = new Intent(CorrectActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME",Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(CorrectActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊",Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(CorrectActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆",Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent2 = new Intent(CorrectActivity.this, SearchActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "查詢派工資料",Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            case R.id.pending_item:
                Intent intent3 = new Intent(CorrectActivity.this, PendingActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "待處理派工", Toast.LENGTH_SHORT).show();
                break; //進入待處理派工頁面
            case R.id.record_item:
                Intent intent8 = new Intent(CorrectActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄",Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent4 = new Intent(CorrectActivity.this, PictureActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent5 = new Intent(CorrectActivity.this, CustomerActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            case R.id.upload_item:
                Intent intent6 = new Intent(CorrectActivity.this, UploadActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //顯示日報修正
            default:
        }
        return true;
    }

    private Button searchbutton;
    private TableLayout record_tablelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);

        record_tablelayout = (TableLayout) findViewById(R.id.record_tablelayot);
        searchbutton = (Button) findViewById(R.id.searchbutton);


        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_tablelayout.setVisibility(View.VISIBLE);

                /*SharedPreferences user_id = getSharedPreferences("user_id_data" , MODE_PRIVATE);
                String user_id_data = user_id.getString("ID" , "");
                Log.i("CorrectActivity",user_id_data);*/

            }
        });
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bData = new Bundle();
        bData.putInt("view", v.getId());
        Button button = (Button) v;
        bData.putString("date", button.getText().toString());
        newFragment.setArguments(bData);
        newFragment.show(getSupportFragmentManager(), "日期挑選器");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CorrectActivity", "onDestroy");
    }
}

