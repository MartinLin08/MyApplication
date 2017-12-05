package com.example.a10609516.myapplication;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;

public class MaintainActivity extends AppCompatActivity {

    private Button cancel_button;
    private TextView work_type_name_txt ,svd_service_no_txt ,esv_note_txt ,time_period_name_txt, cp_name_txt, esvd_is_money_txt;
    private TableLayout maintain_tablelayot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);

        InItFunction();

        GetResponseData();


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void InItFunction() {

        maintain_tablelayot = (TableLayout) findViewById(R.id.maintain_tablelayot);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        work_type_name_txt = (TextView) findViewById(R.id.work_type_name_txt);
        svd_service_no_txt = (TextView) findViewById(R.id.svd_service_no_txt);
        esv_note_txt = (TextView) findViewById(R.id.esv_note_txt);
        time_period_name_txt = (TextView) findViewById(R.id.time_period_name_txt);
        cp_name_txt = (TextView) findViewById(R.id.cp_name_txt);
        esvd_is_money_txt = (TextView) findViewById(R.id.esvd_is_money_txt);

    }

    private void GetResponseData(){

        Intent intent = this.getIntent();
        maintain_tablelayot.setStretchAllColumns(true);
        String ResponseText0 = intent.getStringExtra("ResponseText" + 0);
        work_type_name_txt.setText(ResponseText0);
        String ResponseText1 = intent.getStringExtra("ResponseText" + 1);
        svd_service_no_txt.setText(ResponseText1);
        String ResponseText2 = intent.getStringExtra("ResponseText" + 2);
        esv_note_txt.setText(ResponseText2);
        String ResponseText3 = intent.getStringExtra("ResponseText" + 3);
        time_period_name_txt.setText(ResponseText3);
        String ResponseText10 = intent.getStringExtra("ResponseText" + 10);
        cp_name_txt.setText(ResponseText10);
        String ResponseText11 = intent.getStringExtra("ResponseText" + 11);
        esvd_is_money_txt.setText(ResponseText11);


    }

}
