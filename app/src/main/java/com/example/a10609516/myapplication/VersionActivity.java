package com.example.a10609516.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VersionActivity extends AppCompatActivity {

    private TextView detail_txt1;
    private LinearLayout detail_llt1;
    private Button version_btn1;
    private Button version_up_btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        //動態取得 View 物件
        InItFunction();
        //版本詳細資訊
        DetailOfVersion();
        //查看版本
        CheckWhatDetail();
    }

    //動態取得 View 物件
    private void InItFunction(){
        detail_txt1 = (TextView)findViewById(R.id.detail_txt1);
        detail_llt1 = (LinearLayout)findViewById(R.id.detail_llt1);
        version_btn1 = (Button)findViewById(R.id.version_btn1);
        version_up_btn1 = (Button)findViewById(R.id.version_up_btn1);
    }
    //版本詳細資訊
    private void DetailOfVersion(){
        detail_txt1.setText("1.新增工務 - 行程資訊、查詢派工資料 \n" +
                            "2.新增工務 - 出勤維護回報功能 \n" +
                            "3.新增推播功能(新派工、更新派工、取消派工)");
    }
    //查看版本
    private void CheckWhatDetail() {
        version_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt1.setVisibility(View.VISIBLE);
                version_up_btn1.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt1.setVisibility(View.GONE);
                version_up_btn1.setVisibility(View.GONE);
            }
        });
    }
}
