package com.example.a10609516.myapplication.DepartmentAndDIY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.myapplication.Clerk.QuotationActivity;
import com.example.a10609516.myapplication.Manager.InventoryActivity;
import com.example.a10609516.myapplication.Tools.DatePickerFragment;
import com.example.a10609516.myapplication.Tools.ListViewForScrollView;
import com.example.a10609516.myapplication.Basic.MenuActivity;
import com.example.a10609516.myapplication.Basic.SignatureActivity;
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

public class CustomerActivity extends WQPServiceActivity {

    private ScrollView customer_scv;
    private Button search_btn;
    private TextView data_txt;
    private ListViewForScrollView data_listView;

    private String[] show_text = {"Test1", "Test2", "Test3", "Test4","Test5", "Test6", "Test7", "Test8","Test9", "Test10", "Test11", "Test12","Test13", "Test14", "Test15", "Test16"};
    private ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        //動態取得 View 物件
        InItFunction();
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,show_text);
        data_listView.setAdapter(listAdapter);

        data_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "點選的是"+show_text[position], //postition是指點選到的index
                        Toast.LENGTH_SHORT).show();
                data_txt.setVisibility(View.GONE);
                data_listView.setVisibility(view.GONE); //隱藏ListView
            } //end onItemClick
        }); //end setOnItemClickListener
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        customer_scv = (ScrollView) findViewById(R.id.customer_scv);
        search_btn = (Button) findViewById(R.id.search_btn);
        //show_listView= (ListViewForScrollView) findViewById(R.id.data_listView);
        data_txt = (TextView) findViewById(R.id.data_txt);
        data_listView = (ListViewForScrollView) findViewById(R.id.data_listView);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_txt.setVisibility(View.VISIBLE);
                data_listView.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 日期挑選器
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bData = new Bundle();
        bData.putInt("view",v.getId());
        Button button = (Button) v;
        bData.putString("date",button.getText().toString());
        newFragment.setArguments(bData);
        newFragment.show(getSupportFragmentManager(), "日期挑選器");
    }

    /**
     * 實現畫面置頂按鈕
     * @param view
     */
    public void GoTopBtn(View view) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                //實現畫面置頂按鈕
                customer_scv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CustomerActivity", "onDestroy");
    }
}
