package com.example.a10609516.myapplication.DepartmentAndDIY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.a10609516.myapplication.Clerk.QuotationActivity;
import com.example.a10609516.myapplication.Manager.InventoryActivity;
import com.example.a10609516.myapplication.Tools.DatePickerFragment;
import com.example.a10609516.myapplication.Basic.MenuActivity;
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

public class CorrectActivity extends WQPServiceActivity {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CorrectActivity", "onDestroy");
    }
}

