package com.example.a10609516.myapplication;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;


public class MaintainActivity extends AppCompatActivity {

    private Button cancel_button, arrive_button;
    private TextView work_type_name_txt, svd_service_no_txt, esv_note_txt, time_period_name_txt, cp_name_txt,
            esvd_is_money_txt, esvd_booking_remark_txt, have_get_money_txt;
    private TableLayout maintain_tablelayot;
    private CheckBox is_get_money_checkBox, have_get_money_checkBox, not_get_money_checkBox, not_get_all_checkBox;
    private EditText have_get_money_edt, not_get_money_edt, not_get_all_edt, not_get_all_reason_edt;
    private Spinner arrive_spinner, leave_spinner, useless_spinner;
    private ArrayAdapter<String> time_listAdapter, work_listAdapter;
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
        //判斷SearchActivity的是否要收款傳遞過來的值為(是/否)
        CheckYesOrNo();
        /*//確認是否要收款的CheckBox勾選與否 決定是否以收款的顯示
        IsGetMoneyCheckOrNot();*/
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
        have_get_money_txt = (TextView) findViewById(R.id.have_get_money_txt);
        arrive_button = (Button) findViewById(R.id.arrive_button);
        is_get_money_checkBox = (CheckBox) findViewById(R.id.is_get_money_checkBox);
        have_get_money_checkBox = (CheckBox) findViewById(R.id.have_get_money_checkBox);
        not_get_money_checkBox = (CheckBox) findViewById(R.id.not_get_money_checkBox);
        not_get_all_checkBox = (CheckBox) findViewById(R.id.not_get_all_checkBox);
        have_get_money_edt = (EditText) findViewById(R.id.have_get_money_edt);
        not_get_money_edt = (EditText) findViewById(R.id.not_get_money_edt);
        not_get_all_edt = (EditText) findViewById(R.id.not_get_all_edt);
        not_get_all_reason_edt = (EditText) findViewById(R.id.not_get_all_reason_edt);
        arrive_spinner = (Spinner) findViewById(R.id.arrive_spinner);
        leave_spinner = (Spinner) findViewById(R.id.leave_spinner);
        useless_spinner = (Spinner) findViewById(R.id.useless_spinner);

    }

    //取得SearchActivity傳遞過來的值
    private void GetResponseData() {

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

        //如果日期為0000-00-00,則把該TextView改為空值
        if (arrive_button.getText().toString().equals("0000-00-00")){
            arrive_button.setText("");
        }

    }

    //判斷SearchActivity的是否要收款傳遞過來的值為(是/否)
    private void CheckYesOrNo(){
        Bundle bundle = getIntent().getExtras();
        String ResponseText9 = bundle.getString("ResponseText" + 9);
        //如果傳遞過來的值為"是" 則預設is_get_money_checkBox為被勾選
        if (ResponseText9.equals("是")){
            is_get_money_checkBox.setChecked(true);
            //如果is_get_money_checkBox被勾選 則是否已收款相關欄位能被修改
            have_get_money_txt.setEnabled(true);
            have_get_money_checkBox.setEnabled(true);
            have_get_money_edt.setEnabled(true);
            not_get_money_checkBox.setEnabled(true);
            not_get_money_edt.setEnabled(true);
            not_get_all_checkBox.setEnabled(true);
            not_get_all_edt.setEnabled(true);
            not_get_all_reason_edt.setEnabled(true);
        }
        //如果傳遞過來的值為"否" 則預設is_get_money_checkBox為不被勾選
        else{
            is_get_money_checkBox.setChecked(false);
            //如果is_get_money_checkBox不被勾選 則是否已收款相關欄位不能被修改
            have_get_money_txt.setEnabled(false);
            have_get_money_checkBox.setEnabled(false);
            have_get_money_edt.setEnabled(false);
            not_get_money_checkBox.setEnabled(false);
            not_get_money_edt.setEnabled(false);
            not_get_all_checkBox.setEnabled(false);
            not_get_all_edt.setEnabled(false);
            not_get_all_reason_edt.setEnabled(false);
        }
    }

    /*//確認是否要收款的CheckBox勾選與否 決定是否以收款的顯示
    private void IsGetMoneyCheckOrNot() {
        is_get_money_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果is_get_money_checkBox被勾選 則是否已收款相關欄位能被修改
                    have_get_money_txt.setEnabled(true);
                    have_get_money_checkBox.setEnabled(true);
                    have_get_money_edt.setEnabled(true);
                    not_get_money_checkBox.setEnabled(true);
                    not_get_money_edt.setEnabled(true);
                    not_get_all_checkBox.setEnabled(true);
                    not_get_all_edt.setEnabled(true);
                    not_get_all_reason_edt.setEnabled(true);
                    //顯示是否已收款相關欄位
                    *//*have_get_money_txt.setVisibility(View.VISIBLE);
                    have_get_money_checkBox.setVisibility(View.VISIBLE);
                    have_get_money_edt.setVisibility(View.VISIBLE);
                    not_get_money_checkBox.setVisibility(View.VISIBLE);
                    not_get_money_edt.setVisibility(View.VISIBLE);
                    not_get_all_checkBox.setVisibility(View.VISIBLE);
                    not_get_all_edt.setVisibility(View.VISIBLE);
                    not_get_all_reason_edt.setVisibility(View.VISIBLE);*//*
                }
                if (!isChecked) {
                    //如果is_get_money_checkBox不被勾選 則是否已收款相關欄位不能被修改
                    have_get_money_txt.setEnabled(false);
                    have_get_money_checkBox.setEnabled(false);
                    have_get_money_edt.setEnabled(false);
                    not_get_money_checkBox.setEnabled(false);
                    not_get_money_edt.setEnabled(false);
                    not_get_all_checkBox.setEnabled(false);
                    not_get_all_edt.setEnabled(false);
                    not_get_all_reason_edt.setEnabled(false);
                    //隱藏是否已收款相關欄位
                    *//*have_get_money_txt.setVisibility(View.INVISIBLE);
                    have_get_money_checkBox.setVisibility(View.INVISIBLE);
                    have_get_money_edt.setVisibility(View.INVISIBLE);
                    not_get_money_checkBox.setVisibility(View.INVISIBLE);
                    not_get_money_edt.setVisibility(View.INVISIBLE);
                    not_get_all_checkBox.setVisibility(View.INVISIBLE);
                    not_get_all_edt.setVisibility(View.INVISIBLE);
                    not_get_all_reason_edt.setVisibility(View.INVISIBLE);*//*
                }
            }
        });
    }*/

    //設置是否已收款的三個CheckBox只能一個被勾選
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.have_get_money_checkBox:
                if (checked) {
                    not_get_money_checkBox.setEnabled(false);
                    not_get_money_edt.setEnabled(false);
                    not_get_all_checkBox.setEnabled(false);
                    not_get_all_edt.setEnabled(false);
                    not_get_all_reason_edt.setEnabled(false);
                } else {
                    not_get_money_checkBox.setEnabled(true);
                    not_get_money_edt.setEnabled(true);
                    not_get_all_checkBox.setEnabled(true);
                    not_get_all_edt.setEnabled(true);
                    not_get_all_reason_edt.setEnabled(true);
                }
                break;

            case R.id.not_get_money_checkBox:
                if (checked) {
                    have_get_money_checkBox.setEnabled(false);
                    have_get_money_edt.setEnabled(false);
                    not_get_all_checkBox.setEnabled(false);
                    not_get_all_edt.setEnabled(false);
                    not_get_all_reason_edt.setEnabled(false);
                }else {
                    have_get_money_checkBox.setEnabled(true);
                    have_get_money_edt.setEnabled(true);
                    not_get_all_checkBox.setEnabled(true);
                    not_get_all_edt.setEnabled(true);
                    not_get_all_reason_edt.setEnabled(true);
                }
                break;

            case R.id.not_get_all_checkBox:
                if (checked) {
                    have_get_money_checkBox.setEnabled(false);
                    have_get_money_edt.setEnabled(false);
                    not_get_money_checkBox.setEnabled(false);
                    not_get_money_edt.setEnabled(false);
                }else{
                    have_get_money_checkBox.setEnabled(true);
                    have_get_money_edt.setEnabled(true);
                    not_get_money_checkBox.setEnabled(true);
                    not_get_money_edt.setEnabled(true);
                }
                break;

        }
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
        for (int i = 0; i < time.length; i++) {
            if (time[i].equals(ResponseText14)) {
                arrive_spinner.setSelection(i, true);
                break;
            }
        }
        leave_spinner.setAdapter(time_listAdapter);
        for (int i = 0; i < time.length; i++) {
            if (time[i].equals(ResponseText15)) {
                leave_spinner.setSelection(i, true);
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
