package com.example.a10609516.myapplication;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Day_Work extends AppCompatActivity {

    private TextView date_textView;
    private TableLayout date_work_TableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day__work);

        //動態取得 View 物件
        InItFunction();
        //獲取當前年月日
        DateToday();
        //
        RefreshUI();

    }

    //動態取得 View 物件
    private void InItFunction() {

        date_textView = (TextView) findViewById(R.id.date_textView);
        date_work_TableLayout = (TableLayout) findViewById(R.id.date_work_TableLayout);

    }

    //獲取當前年月日
    private void DateToday() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new java.util.Date());

        date_textView.setText(date);

    }

    private void RefreshUI() {

        try {
            Bundle bundle = getIntent().getExtras();
            //取得傳遞過來的資料
            for (int x = 0; x < 19; x++) {
                String ResponseText_Title = bundle.getString("ResponseTextSecond" + x);
                String ResponseText_Response = bundle.getString("ResponseTextThird" + x);

                //HandlerMessage更新UI
                Bundle b = new Bundle();
                b.putString("ResponseText_Title", ResponseText_Title);
                b.putString("ResponseText_Response", ResponseText_Response);
                Message msg = mHandler.obtainMessage();
                msg.setData(b);
                msg.what = 1;
                msg.sendToTarget();

                Log.i("Day_Work",ResponseText_Title);
                Log.i("Day_Work",ResponseText_Response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新UI
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    Bundle b = msg.getData();
                    String ResponseText_t = b.getString("ResponseText_Title");
                    String ResponseText_b = b.getString("ResponseText_Response");

                    //Log.i("Day_Work", ResponseText_t);
                    //Log.i("Day_Work", ResponseText_b);

                    date_work_TableLayout.setStretchAllColumns(true);

                    //設定TableRow的寬高
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    TextView TitleText;
                    TitleText = new TextView(Day_Work.this);
                    TitleText.setText(ResponseText_t);

                    TextView ResponseText;
                    ResponseText = new TextView(Day_Work.this);
                    ResponseText.setText(ResponseText_b);

                    TableRow tr = new TableRow(Day_Work.this);

                    tr.addView(TitleText,layoutParams);
                    tr.addView(ResponseText,layoutParams);

                    date_work_TableLayout.addView(tr,layoutParams);


                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
