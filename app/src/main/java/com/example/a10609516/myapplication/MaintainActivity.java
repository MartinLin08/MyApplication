package com.example.a10609516.myapplication;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;


public class MaintainActivity extends AppCompatActivity {

    private Button cancel_button ,arrive_button;
    private TextView work_type_name_txt ,svd_service_no_txt ,esv_note_txt ,time_period_name_txt, cp_name_txt, esvd_is_money_txt ,esvd_booking_remark_txt;
    private TableLayout maintain_tablelayot;
    private Spinner arrive_spinner ,leave_spinner ,useless_spinner;
    private ArrayAdapter<String> time_listAdapter ,work_listAdapter;
    private String[] time = new String[]{"選擇", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30"
            , "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"
            , "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"
            , "23:00", "23:30", "00:00"};
    /*private String[] time2 = new String[]{"", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30"
            , "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"
            , "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"
            , "23:00", "23:30", "00:00"};*/
    private String[] useless_work = new String[]{"無", "客人不在場", "現場無法施工", "材料有異", "其他", "業務取消"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);

        //動態取得 View 物件
        InItFunction();
        //取得SearchActivity傳遞過來的值
        GetResponseData();
        //抵達時間與離開時間的Spinner
        TimeSpinner();
        //無效派工的Spinner
        UselessWorkSpinner();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //動態取得 View 物件
    private void InItFunction() {

        maintain_tablelayot = (TableLayout) findViewById(R.id.maintain_tablelayot);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        work_type_name_txt = (TextView) findViewById(R.id.work_type_name_txt);
        svd_service_no_txt = (TextView) findViewById(R.id.svd_service_no_txt);
        esv_note_txt = (TextView) findViewById(R.id.esv_note_txt);
        time_period_name_txt = (TextView) findViewById(R.id.time_period_name_txt);
        cp_name_txt = (TextView) findViewById(R.id.cp_name_txt);
        esvd_is_money_txt = (TextView) findViewById(R.id.esvd_is_money_txt);
        esvd_booking_remark_txt = (TextView) findViewById(R.id.esvd_booking_remark_txt);
        arrive_button = (Button) findViewById(R.id.arrive_button);
        arrive_spinner = (Spinner) findViewById(R.id.arrive_spinner);
        leave_spinner = (Spinner) findViewById(R.id.leave_spinner);
        useless_spinner = (Spinner) findViewById(R.id.useless_spinner);

    }

    //取得SearchActivity傳遞過來的值
    private void GetResponseData(){

        Bundle bundle = getIntent().getExtras();
        maintain_tablelayot.setStretchAllColumns(true);
        String ResponseText0 = bundle.getString("ResponseText" + 0);
        work_type_name_txt.setText(ResponseText0);
        String ResponseText1 = bundle.getString("ResponseText" + 1);
        svd_service_no_txt.setText(ResponseText1);
        String ResponseText2 = bundle.getString("ResponseText" + 2);
        esv_note_txt.setText(ResponseText2);
        String ResponseText3 = bundle.getString("ResponseText" + 3);
        time_period_name_txt.setText(ResponseText3);
        String ResponseText8 = bundle.getString("ResponseText" + 8);
        cp_name_txt.setText(ResponseText8);
        String ResponseText10 = bundle.getString("ResponseText" + 10);
        esvd_is_money_txt.setText(ResponseText10);
        String ResponseText16 = bundle.getString("ResponseText" + 16);
        esvd_booking_remark_txt.setText(ResponseText16);
        String ResponseText13 = bundle.getString("ResponseText" + 13);
        arrive_button.setText(ResponseText13);
        /*String ResponseText14 = bundle.getString("ResponseText" + 14);
        for( int i=0;i< time.length ; i++ )
        {
            if ( time[i].equals(ResponseText14) )
            {
                arrive_spinner.setSelection(i);
                break;
            }
        }
        String ResponseText15 = bundle.getString("ResponseText" + 15);
        leave_spinner.setPrompt(ResponseText15);*/



    }

    //日期挑選器
    public void showDatePickerDialog(View v) {
        //日期挑選器
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bData = new Bundle();
        bData.putInt("view", v.getId());
        Button button = (Button) v;
        bData.putString("date", button.getText().toString());
        newFragment.setArguments(bData);
        newFragment.show(getSupportFragmentManager(), "日期挑選器");
    }

    //抵達時間與離開時間的Spinner
    private void TimeSpinner() {

        Bundle bundle = getIntent().getExtras();
        String ResponseText14 = bundle.getString("ResponseText" + 14);
        String ResponseText15 = bundle.getString("ResponseText" + 15);

        /*time1[0]= ResponseText14;
        Log.i("MaintainActivity",ResponseText14);
        Log.i("MaintainActivity",ResponseText15);*/

        time_listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time);
        time_listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrive_spinner.setAdapter(time_listAdapter);
        //當迴圈與ResponseText內容一致時跳出迴圈 並顯示該ResponseText的Spinner位置
        for( int i = 0 ;i< time.length ;i++ )
        {
            if ( time[i].equals(ResponseText14) )
            {
                arrive_spinner.setSelection(i,true);
                break;
            }
        }
        leave_spinner.setAdapter(time_listAdapter);
        for( int i = 0 ;i< time.length ;i++ )
        {
            if ( time[i].equals(ResponseText15) )
            {
                leave_spinner.setSelection(i,true);
                break;
            }
        }

        /*time2[0]= ResponseText15;
        time_listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time2);
        time_listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leave_spinner.setAdapter(time_listAdapter);*/

    }

    //無效派工的Spinner
    private void UselessWorkSpinner() {

        work_listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, useless_work);
        work_listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        useless_spinner.setAdapter(work_listAdapter);


    }

}
