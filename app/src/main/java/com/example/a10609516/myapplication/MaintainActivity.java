package com.example.a10609516.myapplication;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MaintainActivity extends AppCompatActivity {

    private Button arrive_button, check_button, cancel_button;
    private TextView work_type_name_txt, svd_service_no_txt, esv_note_txt, time_period_name_txt, cp_name_txt, esvd_eng_points_txt,
            esvd_is_money_txt, esvd_booking_remark_txt, have_get_money_txt, esvd_remark_txt, reason_txt, esvd_is_eng_money_txt;
    private TableLayout maintain_tablelayot;
    private CheckBox is_get_money_checkBox, have_get_money_checkBox, not_get_money_checkBox, not_get_all_checkBox;
    private EditText have_get_money_edt, not_get_money_edt, not_get_all_edt, not_get_all_reason_edt;
    private Spinner arrive_spinner, leave_spinner, reason_spinner, useless_spinner;
    private ArrayAdapter<String> time_listAdapter, work_listAdapter, reason_listAdapter;
    private String[] time = new String[]{"選擇", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30"
            , "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"
            , "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"
            , "23:00", "23:30", "00:00"};
    /*private String[] time2 = new String[]{"", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30"
            , "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"
            , "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"
            , "23:00", "23:30", "00:00"};*/
    private String[] reason_spinnerprivate = new String[]{"請選擇", "產品瑕疵", "安裝不當", "使用不良", "安裝收尾", "零件老化", "其他", "現場勘查"};
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
        //設置收款金額的代入與取消
        HaveGetMoney();
        //抵達時間與離開時間的Spinner
        TimeSpinner();
        //檢修(一)(二)的主因Spinner
        ReasonSpinner();
        //無效派工的Spinner
        UselessWorkSpinner();
        //Button.setOnClickListener監聽器
        check_button.setOnClickListener(check_btnListener);
        cancel_button.setOnClickListener(cancel_btnListener);

    }

    //動態取得 View 物件
    private void InItFunction() {

        maintain_tablelayot = (TableLayout) findViewById(R.id.maintain_tablelayot);
        check_button = (Button) findViewById(R.id.check_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        work_type_name_txt = (TextView) findViewById(R.id.work_type_name_txt);
        svd_service_no_txt = (TextView) findViewById(R.id.svd_service_no_txt);
        esv_note_txt = (TextView) findViewById(R.id.esv_note_txt);
        time_period_name_txt = (TextView) findViewById(R.id.time_period_name_txt);
        cp_name_txt = (TextView) findViewById(R.id.cp_name_txt);
        esvd_is_money_txt = (TextView) findViewById(R.id.esvd_is_money_txt);
        esvd_booking_remark_txt = (TextView) findViewById(R.id.esvd_booking_remark_txt);
        have_get_money_txt = (TextView) findViewById(R.id.have_get_money_txt);
        esvd_remark_txt = (TextView) findViewById(R.id.esvd_remark_txt);
        reason_txt = (TextView) findViewById(R.id.reason_txt);
        esvd_is_eng_money_txt = (TextView) findViewById(R.id.esvd_is_eng_money_txt);
        esvd_eng_points_txt = (TextView) findViewById(R.id.esvd_eng_points_txt);
        arrive_button = (Button) findViewById(R.id.arrive_button);
        is_get_money_checkBox = (CheckBox) findViewById(R.id.is_get_money_checkBox);
        have_get_money_checkBox = (CheckBox) findViewById(R.id.have_get_money_checkBox);
        not_get_money_checkBox = (CheckBox) findViewById(R.id.not_get_money_checkBox);
        not_get_all_checkBox = (CheckBox) findViewById(R.id.not_get_all_checkBox);
        have_get_money_edt = (EditText) findViewById(R.id.have_get_money_edt);
        //not_get_money_edt = (EditText) findViewById(R.id.not_get_money_edt);
        not_get_all_edt = (EditText) findViewById(R.id.not_get_all_edt);
        //not_get_all_reason_edt = (EditText) findViewById(R.id.not_get_all_reason_edt);
        arrive_spinner = (Spinner) findViewById(R.id.arrive_spinner);
        leave_spinner = (Spinner) findViewById(R.id.leave_spinner);
        reason_spinner = (Spinner) findViewById(R.id.reason_spinner);
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
        String ResponseText18 = bundle.getString("ResponseText" + 18);
        esvd_remark_txt.setText(ResponseText18);
        String ResponseText20 = bundle.getString("ResponseText" + 20);
        esvd_eng_points_txt.setText(ResponseText20);

        //如果日期為0000-00-00,則把該TextView改為空值
        if (arrive_button.getText().toString().equals("1900-01-01")) {
            arrive_button.setText("");
        }
    }

    //判斷SearchActivity的是否要收款傳遞過來的值為(是/否)
    private void CheckYesOrNo() {
        Bundle bundle = getIntent().getExtras();
        String ResponseText9 = bundle.getString("ResponseText" + 9);
        //如果傳遞過來的值為"是" 則預設is_get_money_checkBox為被勾選
        if (ResponseText9.equals("是")) {
            is_get_money_checkBox.setChecked(true);
            //如果is_get_money_checkBox被勾選 則是否已收款相關欄位能被修改
            have_get_money_txt.setEnabled(true);
            have_get_money_checkBox.setEnabled(true);
            have_get_money_edt.setEnabled(true);
            not_get_money_checkBox.setEnabled(true);
            //not_get_money_edt.setEnabled(true);
            not_get_all_checkBox.setEnabled(true);
            not_get_all_edt.setEnabled(true);
            //not_get_all_reason_edt.setEnabled(true);
        }
        //如果傳遞過來的值為"否" 則預設is_get_money_checkBox為不被勾選
        else {
            is_get_money_checkBox.setChecked(false);
            //如果is_get_money_checkBox不被勾選 則是否已收款相關欄位不能被修改
            have_get_money_txt.setEnabled(false);
            have_get_money_checkBox.setEnabled(false);
            have_get_money_edt.setEnabled(false);
            not_get_money_checkBox.setEnabled(false);
            //not_get_money_edt.setEnabled(false);
            not_get_all_checkBox.setEnabled(false);
            not_get_all_edt.setEnabled(false);
            //not_get_all_reason_edt.setEnabled(false);
            //have_get_money_edt.setText("0");//如果is_get_money_checkBox不被勾選 have_get_money_edt設置顯示0
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
                    //not_get_money_edt.setEnabled(false);
                    not_get_all_checkBox.setEnabled(false);
                    not_get_all_edt.setEnabled(false);
                    //not_get_all_reason_edt.setEnabled(false);
                    esvd_is_eng_money_txt.setText("1");
                } else {
                    not_get_money_checkBox.setEnabled(true);
                    //not_get_money_edt.setEnabled(true);
                    not_get_all_checkBox.setEnabled(true);
                    not_get_all_edt.setEnabled(true);
                    //not_get_all_reason_edt.setEnabled(true);
                }
                break;

            case R.id.not_get_all_checkBox:
                if (checked) {
                    have_get_money_checkBox.setEnabled(false);
                    have_get_money_edt.setEnabled(false);
                    not_get_money_checkBox.setEnabled(false);
                    //not_get_money_edt.setEnabled(false);
                    esvd_is_eng_money_txt.setText("1");
                } else {
                    have_get_money_checkBox.setEnabled(true);
                    have_get_money_edt.setEnabled(true);
                    not_get_money_checkBox.setEnabled(true);
                    //not_get_money_edt.setEnabled(true);
                }
                break;

            case R.id.not_get_money_checkBox:
                if (checked) {
                    have_get_money_checkBox.setEnabled(false);
                    have_get_money_edt.setEnabled(false);
                    not_get_all_checkBox.setEnabled(false);
                    not_get_all_edt.setEnabled(false);
                    //not_get_all_reason_edt.setEnabled(false);
                    esvd_is_eng_money_txt.setText("");
                } else {
                    have_get_money_checkBox.setEnabled(true);
                    //have_get_money_edt.setEnabled(true);
                    not_get_all_checkBox.setEnabled(true);
                    not_get_all_edt.setEnabled(true);
                    //not_get_all_reason_edt.setEnabled(true);
                }
                break;

        }
    }

    //設置收款金額的代入與取消
    private void HaveGetMoney() {
        have_get_money_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果have_get_money_checkBox被勾選 則收款金額直接帶入應收金額
                    have_get_money_edt.setText(esvd_is_money_txt.getText().toString());
                }
                if (!isChecked) {
                    //如果have_get_money_checkBox被取消勾選 則收款金額清空
                    have_get_money_edt.setText("");
                }
            }
        });

        not_get_all_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //如果not_get_all_checkBox被取消勾選 則收款金額清空
                    not_get_all_edt.setText("");
                }
            }
        });
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

    ////檢修(一)(二)的主因Spinner
    private void ReasonSpinner() {

        reason_listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, reason_spinnerprivate);
        reason_listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reason_spinner.setAdapter(reason_listAdapter);

        if (work_type_name_txt.getText().equals("檢修(一)")) {
            reason_txt.setVisibility(View.VISIBLE);
            reason_spinner.setVisibility(View.VISIBLE);
        }
        if (work_type_name_txt.getText().equals("檢修(二)")) {
            reason_txt.setVisibility(View.VISIBLE);
            reason_spinner.setVisibility(View.VISIBLE);
        }

    }

    //無效派工的Spinner
    private void UselessWorkSpinner() {

        work_listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, useless_work);
        work_listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        useless_spinner.setAdapter(work_listAdapter);

    }

    //check_button.setOnClickListener監聽器
    private Button.OnClickListener check_btnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (arrive_button.getText().toString().equals("") || arrive_spinner.getSelectedItem().equals("選擇") || leave_spinner.getSelectedItem().equals("選擇")) {

                if (arrive_button.getText().toString().equals("")) {
                    Toast.makeText(MaintainActivity.this, "【請選擇抵達日期!!!】", Toast.LENGTH_SHORT).show();
                }
                if (arrive_spinner.getSelectedItem().equals("選擇")) {
                    Toast.makeText(MaintainActivity.this, "【請選擇抵達時間!!!】", Toast.LENGTH_SHORT).show();
                }
                if (leave_spinner.getSelectedItem().equals("選擇")) {
                    Toast.makeText(MaintainActivity.this, "【請選擇離開時間!!!】", Toast.LENGTH_SHORT).show();
                }

            } else {
                String arrive_txt = String.valueOf(arrive_spinner.getSelectedItem());
                String leave_txt = String.valueOf(leave_spinner.getSelectedItem());
                String start_txt = arrive_txt.replace(":", "");
                String end_txt = leave_txt.replace(":", "");
                int time1 = Integer.parseInt(start_txt);
                int time2 = Integer.parseInt(end_txt);
                int result = time1 - time2;
                if (result > 0) {
                    Toast.makeText(MaintainActivity.this, "【離開時間不能小於抵達時間!!!】", Toast.LENGTH_SHORT).show();
                } else {
                    if (is_get_money_checkBox.isChecked()) {
                        if (have_get_money_checkBox.isChecked()) {
                            if ((work_type_name_txt.getText().equals("檢修(一)") && reason_spinner.getSelectedItem().equals("請選擇")) || (work_type_name_txt.getText().equals("檢修(二)") && reason_spinner.getSelectedItem().equals("請選擇"))) {
                                Toast.makeText(MaintainActivity.this, "【請選擇檢修主因!!!】", Toast.LENGTH_SHORT).show();
                            } else {
                                //金額已收齊的OKHttp(工務收錢)
                                sendRequestWithOkHttpOfAll();
                                finish();
                            }
                        }
                        if (not_get_all_checkBox.isChecked()) {
                            if ((work_type_name_txt.getText().equals("檢修(一)") && reason_spinner.getSelectedItem().equals("請選擇")) || (work_type_name_txt.getText().equals("檢修(二)") && reason_spinner.getSelectedItem().equals("請選擇"))) {
                                Toast.makeText(MaintainActivity.this, "【請選擇檢修主因!!!】", Toast.LENGTH_SHORT).show();
                            } else {
                                if (not_get_all_edt.getText().toString().equals("")) {
                                    Toast.makeText(MaintainActivity.this, "【請輸入收款金額!!!】", Toast.LENGTH_SHORT).show();
                                } else {
                                    //金額未收齊的OKHttp(工務收錢)
                                    sendRequestWithOkHttpOfNotAll();
                                    finish();
                                }
                            }
                        }
                        if (not_get_money_checkBox.isChecked()) {
                            if ((work_type_name_txt.getText().equals("檢修(一)") && reason_spinner.getSelectedItem().equals("請選擇")) || (work_type_name_txt.getText().equals("檢修(二)") && reason_spinner.getSelectedItem().equals("請選擇"))) {
                                Toast.makeText(MaintainActivity.this, "【請選擇檢修主因!!!】", Toast.LENGTH_SHORT).show();
                            } else {
                                //金額未收的OKHttp(工務收錢)
                                sendRequestWithOkHttpOfNotGet();
                                finish();
                            }
                        }
                        if (have_get_money_checkBox.isChecked() || not_get_all_checkBox.isChecked() || not_get_money_checkBox.isChecked()) {
                        } else {
                            Toast.makeText(MaintainActivity.this, "【請勾選是否已收款!!!】", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if ((work_type_name_txt.getText().equals("檢修(一)") && reason_spinner.getSelectedItem().equals("請選擇")) || (work_type_name_txt.getText().equals("檢修(二)") && reason_spinner.getSelectedItem().equals("請選擇"))) {
                            Toast.makeText(MaintainActivity.this, "【請選擇檢修主因!!!】", Toast.LENGTH_SHORT).show();
                        } else {
                            //金額已收齊的OKHttp(業務收錢)
                            sendRequestWithOkHttpOfIsGet();
                            finish();
                        }
                    }
                }

            }
        }
    };//end setOnItemClickListener

    //cancel_button.setOnClickListener監聽器
    private Button.OnClickListener cancel_btnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };//end setOnItemClickListener

    //與OkHttp建立連線(收齊)
    private void sendRequestWithOkHttpOfAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("MaintainActivity", user_id_data);

                Bundle bundle = getIntent().getExtras();
                String seq_id = bundle.getString("ResponseText" + 19);
                Log.e("MaintainActivity", seq_id);

                //獲取出勤維護的數據
                String arrive_date = arrive_button.getText().toString();
                String get_money = have_get_money_edt.getText().toString();
                String arrive_time = String.valueOf(arrive_spinner.getSelectedItem());
                String leave_time = String.valueOf(leave_spinner.getSelectedItem());
                String check_reason = String.valueOf(reason_spinner.getSelectedItem());
                String useless_work = String.valueOf(useless_spinner.getSelectedItem());
                String work_remark = esvd_remark_txt.getText().toString();
                String get_money_type = esvd_is_eng_money_txt.getText().toString();
                String work_points = esvd_eng_points_txt.getText().toString();

                /*if(arrive_date.toString().equals("") ){
                    arrive_date = "1900-01-01";
                }
                if(arrive_time.toString().equals("選擇")){
                    arrive_time = "";
                }
                if(leave_time.toString().equals("選擇")){
                    leave_time = "";
                }*/
                if (check_reason.toString().equals("請選擇")) {
                    check_reason = "";
                }
                if (useless_work.toString() != "無") {
                    work_points = "0.5";
                }


                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("ESVD_SEQ_ID", seq_id)
                            .add("ESVD_GET_ENG_MONEY", get_money)
                            .add("ESVD_BEGIN_DATE", arrive_date)
                            .add("ESVD_BEGIN_TIME", arrive_time)
                            .add("ESVD_END_TIME", leave_time)
                            .add("ESVD_CENSON_TYPE", check_reason)
                            .add("ESVD_INVALID_FLAG", useless_work)
                            .add("ESVD_REMARK", work_remark)
                            .add("ESVD_IS_ENG_MONEY", get_money_type)
                            .add("ESVD_ENG_POINTS", work_points)
                            .build();
                    Log.e("MaintainActivity", user_id_data);
                    Log.e("MaintainActivity", seq_id);
                    Log.e("MaintainActivity", get_money);
                    Log.e("MaintainActivity", arrive_date);
                    Log.e("MaintainActivity", arrive_time);
                    Log.e("MaintainActivity", leave_time);
                    Log.e("MaintainActivity", check_reason);
                    Log.e("MaintainActivity", useless_work);
                    Log.e("MaintainActivity", work_remark);
                    Log.e("MaintainActivity", get_money_type);
                    Log.e("MaintainActivity", work_points);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/wqp/UpdateMaintainData.php")
                            .url("http://192.168.0.172/Test1/UpdateMaintainData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("MaintainActivity", responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //與OkHttp建立連線(未收齊)
    private void sendRequestWithOkHttpOfNotAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("MaintainActivity", user_id_data);

                Bundle bundle = getIntent().getExtras();
                String seq_id = bundle.getString("ResponseText" + 19);
                Log.e("MaintainActivity", seq_id);

                //獲取出勤維護的數據
                String arrive_date = arrive_button.getText().toString();
                String get_money = not_get_all_edt.getText().toString();
                String arrive_time = String.valueOf(arrive_spinner.getSelectedItem());
                String leave_time = String.valueOf(leave_spinner.getSelectedItem());
                String check_reason = String.valueOf(reason_spinner.getSelectedItem());
                String useless_work = String.valueOf(useless_spinner.getSelectedItem());
                String work_remark = esvd_remark_txt.getText().toString();
                String get_money_type = esvd_is_eng_money_txt.getText().toString();
                String work_points = esvd_eng_points_txt.getText().toString();

                /*if(arrive_date.toString().equals("") ){
                    arrive_date = "1900-01-01";
                }
                if(arrive_time.toString().equals("選擇")){
                    arrive_time = "";
                }
                if(leave_time.toString().equals("選擇")){
                    leave_time = "";
                }*/
                if (check_reason.toString().equals("請選擇")) {
                    check_reason = "";
                }
                if (useless_work.toString() != "無") {
                    work_points = "0.5";
                }

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("ESVD_SEQ_ID", seq_id)
                            .add("ESVD_GET_ENG_MONEY", get_money)
                            .add("ESVD_BEGIN_DATE", arrive_date)
                            .add("ESVD_BEGIN_TIME", arrive_time)
                            .add("ESVD_END_TIME", leave_time)
                            .add("ESVD_CENSON_TYPE", check_reason)
                            .add("ESVD_INVALID_FLAG", useless_work)
                            .add("ESVD_REMARK", work_remark)
                            .add("ESVD_IS_ENG_MONEY", get_money_type)
                            .add("ESVD_ENG_POINTS", work_points)
                            .build();
                    Log.e("MaintainActivity", user_id_data);
                    Log.e("MaintainActivity", seq_id);
                    Log.e("MaintainActivity", get_money);
                    Log.e("MaintainActivity", arrive_date);
                    Log.e("MaintainActivity", arrive_time);
                    Log.e("MaintainActivity", leave_time);
                    Log.e("MaintainActivity", check_reason);
                    Log.e("MaintainActivity", useless_work);
                    Log.e("MaintainActivity", work_remark);
                    Log.e("MaintainActivity", get_money_type);
                    Log.e("MaintainActivity", work_points);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/wqp/UpdateMaintainData.php")
                            .url("http://192.168.0.172/Test1/UpdateMaintainData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("MaintainActivity", responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //與OkHttp建立連線(未收)
    private void sendRequestWithOkHttpOfNotGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("MaintainActivity", user_id_data);

                Bundle bundle = getIntent().getExtras();
                String seq_id = bundle.getString("ResponseText" + 19);
                Log.e("MaintainActivity", seq_id);

                //獲取出勤維護的數據
                String arrive_date = arrive_button.getText().toString();
                String get_money = not_get_all_edt.getText().toString();
                String arrive_time = String.valueOf(arrive_spinner.getSelectedItem());
                String leave_time = String.valueOf(leave_spinner.getSelectedItem());
                String check_reason = String.valueOf(reason_spinner.getSelectedItem());
                String useless_work = String.valueOf(useless_spinner.getSelectedItem());
                String work_remark = esvd_remark_txt.getText().toString();
                //String get_money_type = esvd_is_eng_money_txt.getText().toString();
                String work_points = esvd_eng_points_txt.getText().toString();

                /*if(arrive_date.toString().equals("") ){
                    arrive_date = "1900-01-01";
                }
                if(arrive_time.toString().equals("選擇")){
                    arrive_time = "";
                }
                if(leave_time.toString().equals("選擇")){
                    leave_time = "";
                }*/
                if (check_reason.toString().equals("請選擇")) {
                    check_reason = "";
                }
                if (useless_work.toString() != "無") {
                    work_points = "0.5";
                }

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("ESVD_SEQ_ID", seq_id)
                            .add("ESVD_GET_ENG_MONEY", get_money)
                            .add("ESVD_BEGIN_DATE", arrive_date)
                            .add("ESVD_BEGIN_TIME", arrive_time)
                            .add("ESVD_END_TIME", leave_time)
                            .add("ESVD_CENSON_TYPE", check_reason)
                            .add("ESVD_INVALID_FLAG", useless_work)
                            .add("ESVD_REMARK", work_remark)
                            //.add("ESVD_IS_ENG_MONEY", get_money_type)
                            .add("ESVD_ENG_POINTS", work_points)
                            .build();
                    Log.e("MaintainActivity", user_id_data);
                    Log.e("MaintainActivity", seq_id);
                    Log.e("MaintainActivity", get_money);
                    Log.e("MaintainActivity", arrive_date);
                    Log.e("MaintainActivity", arrive_time);
                    Log.e("MaintainActivity", leave_time);
                    Log.e("MaintainActivity", check_reason);
                    Log.e("MaintainActivity", useless_work);
                    Log.e("MaintainActivity", work_remark);
                    //Log.e("MaintainActivity", get_money_type);
                    Log.e("MaintainActivity", work_points);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/wqp/UpdateMaintainData.php")
                            .url("http://192.168.0.172/Test1/UpdateMaintainData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("MaintainActivity", responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //與OkHttp建立連線(不需收)
    private void sendRequestWithOkHttpOfIsGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("MaintainActivity", user_id_data);

                Bundle bundle = getIntent().getExtras();
                String seq_id = bundle.getString("ResponseText" + 19);
                Log.e("MaintainActivity", seq_id);

                //獲取出勤維護的數據
                String arrive_date = arrive_button.getText().toString();
                String get_money = have_get_money_edt.getText().toString();
                String arrive_time = String.valueOf(arrive_spinner.getSelectedItem());
                String leave_time = String.valueOf(leave_spinner.getSelectedItem());
                String check_reason = String.valueOf(reason_spinner.getSelectedItem());
                String useless_work = String.valueOf(useless_spinner.getSelectedItem());
                String work_remark = esvd_remark_txt.getText().toString();
                //String get_money_type = esvd_is_eng_money_txt.getText().toString();
                String work_points = esvd_eng_points_txt.getText().toString();

                /*if(arrive_date.toString().equals("") ){
                    arrive_date = "1900-01-01";
                }
                if(arrive_time.toString().equals("選擇")){
                    arrive_time = "";
                }
                if(leave_time.toString().equals("選擇")){
                    leave_time = "";
                }*/
                if (check_reason.toString().equals("請選擇")) {
                    check_reason = "";
                }
                if (useless_work.toString() != "無") {
                    work_points = "0.5";
                }

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("ESVD_SEQ_ID", seq_id)
                            .add("ESVD_GET_ENG_MONEY", get_money)
                            .add("ESVD_BEGIN_DATE", arrive_date)
                            .add("ESVD_BEGIN_TIME", arrive_time)
                            .add("ESVD_END_TIME", leave_time)
                            .add("ESVD_CENSON_TYPE", check_reason)
                            .add("ESVD_INVALID_FLAG", useless_work)
                            .add("ESVD_REMARK", work_remark)
                            //.add("ESVD_IS_ENG_MONEY", get_money_type)
                            .add("ESVD_ENG_POINTS", work_points)
                            .build();
                    Log.e("MaintainActivity", user_id_data);
                    Log.e("MaintainActivity", seq_id);
                    Log.e("MaintainActivity", get_money);
                    Log.e("MaintainActivity", arrive_date);
                    Log.e("MaintainActivity", arrive_time);
                    Log.e("MaintainActivity", leave_time);
                    Log.e("MaintainActivity", check_reason);
                    Log.e("MaintainActivity", useless_work);
                    Log.e("MaintainActivity", work_remark);
                    //Log.e("MaintainActivity", get_money_type);
                    Log.e("MaintainActivity", work_points);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/wqp/UpdateMaintainData.php")
                            .url("http://192.168.0.172/Test1/UpdateMaintainData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("MaintainActivity", responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
