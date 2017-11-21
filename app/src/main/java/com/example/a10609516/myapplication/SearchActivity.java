package com.example.a10609516.myapplication;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    //創建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home_item:
                Intent intent = new Intent(SearchActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME",Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(SearchActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊",Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(SearchActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆",Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Toast.makeText(this, "查詢派工資料",Toast.LENGTH_SHORT).show();
                break; //顯示查詢派工資料
            case R.id.pending_item:
                Intent intent2 = new Intent(SearchActivity.this, PendingActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "待處理派工",Toast.LENGTH_SHORT).show();
                break; //進入待處理派工頁面
            case R.id.record_item:
                Intent intent8 = new Intent(SearchActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄",Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent3 = new Intent(SearchActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(SearchActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            case R.id.upload_item:
                Intent intent5 = new Intent(SearchActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(SearchActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            default:
        }
        return true;
    }

    private Button search_button;
    private TableLayout search_tablelayout;
    private LinearLayout search_linearlayout;
    //public static final String Userid = "";

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*TableLayout tableLayout = (TableLayout)findViewById(R.id.search_tablelayot);
        //全部列自动填充空白处
        tableLayout.setStretchAllColumns(true);
        //生成10行，8列的表格
        for(int row=0;row<10;row++) {
            TableRow tableRow = new TableRow(this);
            for (int col = 0; col < 8; col++) {
                //tv用于显示
                TextView tv = new TextView(this);
                tv.setText("(" + col + "," + row + ")");
                tableRow.addView(tv);
            }
            //新建的TableRow添加到TableLayout
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(MP, WC));
        }*/

        /*Intent intent = getIntent();
        String data = intent.getStringExtra(Userid);
        Log.d("SearchActivity",data);*/

        //Spinner下拉選單
        Spinner spinner = (Spinner)findViewById(R.id.selectList);
        final String[] select = {"選擇","臨時取消","換濾心","換濾料","加鹽","末端","小前置","全屋","社區保養","檢修(一)","檢修(二)"};
        ArrayAdapter<String> selectList = new ArrayAdapter<>(SearchActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                select);
        spinner.setAdapter(selectList);

        //按搜尋Button前 TableLayout先隱藏
        search_tablelayout = (TableLayout) findViewById(R.id.search_tablelayot);
        search_linearlayout = (LinearLayout) findViewById(R.id.search_linearlayout);
        search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_linearlayout.setVisibility(View.VISIBLE);
                search_tablelayout.setVisibility(View.VISIBLE);

                sendRequestWithOkHttp();
            }
        });
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.0.172/Test1/SearchData.php?User_id=10311184")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private TextView work_type_name_txt;
    private TextView svd_service_no_txt;
    private TextView esv_note_txt;
    private TextView time_period_name_txt;
    private TextView esv_contactor_txt;
    private TextView esv_tel_no1_txt;
    private TextView esv_tel_no2_txt;
    private TextView esv_address_txt;
    private TextView esvd_is_get_money_txt;
    private TextView esvd_is_eng_money_txt;
    private TextView cp_name_txt;
    private TextView esvd_begin_date_txt;
    private TextView esvd_begin_time_txt;
    private TextView esvd_end_time_txt;
    private TextView esvd_booking_remark_txt;
    private TextView esv_item_remark_txt;
    private TextView  esvd_remark_txt;
    private TextView esvd_is_money_txt;
    private TextView  esvd_get_eng_money_txt;


    private void parseJSONWithJSONObject(String jsonData){
        search_tablelayout.setStretchAllColumns(true);
        //TableLayout.LayoutParams row_layout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        //TableRow.LayoutParams view_layout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        try{
            Thread.sleep(1000);
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++){
                /*if(i==1)
                    break;*/

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                /*String work_type_name = jsonObject.getString("派工類別");
                String svd_service_no = jsonObject.getString("派工單號");
                String esv_note = jsonObject.getString("送貨客戶");
                String time_period_name = jsonObject.getString("預約日期時段");
                String esv_contactor = jsonObject.getString("聯絡人");
                String esv_tel_no1 = jsonObject.getString("主要電話");
                String esv_tel_no2 = jsonObject.getString("次要電話");
                String esv_address = jsonObject.getString("派工地址");
                String esvd_is_get_money = jsonObject.getString("是否要收款");
                String esvd_is_eng_money = jsonObject.getString("是否已收款");
                String cp_name = jsonObject.getString("付款方式");
                String esvd_begin_date = jsonObject.getString("抵達日期");
                String esvd_begin_time = jsonObject.getString("抵達時間");
                String esvd_end_time = jsonObject.getString("結束時間");
                String esvd_booking_remark = jsonObject.getString("任務說明");
                String esv_item_remark = jsonObject.getString("料品說明");
                String esvd_remark = jsonObject.getString("工作說明");
                String esvd_is_money = jsonObject.getString("單據金額");
                String esvd_get_eng_money = jsonObject.getString("已收款金額");*/

                /*if(i==1)
                {Log.i("SearchActivity","派工類別 :"+" "+work_type_name+",派工單號 :"+" "+svd_service_no+",送貨客戶 :"+" "+esv_note
                        +",預約日期時段 :"+" "+time_period_name+",聯絡人 :"+" "+esv_contactor+",主要電話 :"+" "+esv_tel_no1
                        +",次要電話 :"+" "+esv_tel_no2 +",派工地址 :"+" " +esv_address+",是否要收款 :"+" "+esvd_is_get_money
                        +",是否已收款 :"+" "+esvd_is_eng_money +",付款方式 :"+" "+cp_name +",抵達日期 :"+" "+esvd_begin_date
                        +",抵達時間 :"+" "+esvd_begin_time +",結束時間 :"+" "+esvd_end_time+",任務說明 :"+" "+esvd_booking_remark
                        +",料品說明 :"+" "+esv_item_remark +",工作說明 :"+" "+esvd_remark+",單據金額 :"+" "+esvd_is_money
                        +",已收款金額 :"+" "+esvd_get_eng_money);

                    TextView work_type_name_txt = (TextView)findViewById(R.id.work_type_name_txt);
                    work_type_name_txt.setText(work_type_name);

                    TextView svd_service_no_txt = (TextView)findViewById(R.id.svd_service_no_txt);
                    svd_service_no_txt.setText(svd_service_no);

                    TextView esv_note_txt = (TextView)findViewById(R.id.esv_note_txt);
                    esv_note_txt.setText(esv_note);

                    TextView esv_item_remark_txt = (TextView)findViewById(R.id.esv_item_remark_txt);
                    esv_item_remark_txt.setText(esv_item_remark);


                    break;}*/

                /*for(int row=0;row<19;row++) {*/

                    TableRow tr = new TableRow(SearchActivity.this);
                    //tr.setLayoutParams(row_layout);
                    //tr.setGravity(Gravity.CENTER_VERTICAL);

                    work_type_name_txt = new TextView(SearchActivity.this);
                    work_type_name_txt.setText(jsonObject.getString("派工類別"));
                    //work_type_name_txt.setLayoutParams(view_layout);

                    svd_service_no_txt = new TextView(SearchActivity.this);
                    svd_service_no_txt.setText(jsonObject.getString("派工單號"));
                    //svd_service_no_txt.setLayoutParams(view_layout);

                    esv_note_txt = new TextView(SearchActivity.this);
                    esv_note_txt.setText(jsonObject.getString("送貨客戶"));
                    //esv_note_txt.setLayoutParams(view_layout);

                    time_period_name_txt = new TextView(SearchActivity.this);
                    time_period_name_txt.setText(jsonObject.getString("預約日期時段"));
                    //time_period_name_txt.setLayoutParams(view_layout);

                    esv_contactor_txt = new TextView(SearchActivity.this);
                    esv_contactor_txt.setText(jsonObject.getString("聯絡人"));
                    //esv_contactor_txt.setLayoutParams(view_layout);

                    esv_tel_no1_txt = new TextView(SearchActivity.this);
                    esv_tel_no1_txt.setText(jsonObject.getString("主要電話"));
                    //esv_tel_no1_txt.setLayoutParams(view_layout);

                    esv_tel_no2_txt = new TextView(SearchActivity.this);
                    esv_tel_no2_txt.setText(jsonObject.getString("次要電話"));
                    //esv_tel_no2_txt.setLayoutParams(view_layout);

                    esv_address_txt = new TextView(SearchActivity.this);
                    esv_address_txt.setText(jsonObject.getString("派工地址"));
                    //esv_address_txt.setLayoutParams(view_layout);

                    esvd_is_get_money_txt = new TextView(SearchActivity.this);
                    esvd_is_get_money_txt.setText(jsonObject.getString("是否要收款"));
                    //esvd_is_get_money_txt.setLayoutParams(view_layout);

                    esvd_is_eng_money_txt = new TextView(SearchActivity.this);
                    esvd_is_eng_money_txt.setText(jsonObject.getString("是否已收款"));
                    //esvd_is_eng_money_txt.setLayoutParams(view_layout);

                    cp_name_txt = new TextView(SearchActivity.this);
                    cp_name_txt.setText(jsonObject.getString("付款方式"));
                    //cp_name_txt.setLayoutParams(view_layout);

                    esvd_begin_date_txt = new TextView(SearchActivity.this);
                    esvd_begin_date_txt.setText(jsonObject.getString("抵達日期"));
                    //esvd_begin_date_txt.setLayoutParams(view_layout);

                    esvd_begin_time_txt = new TextView(SearchActivity.this);
                    esvd_begin_time_txt.setText(jsonObject.getString("抵達時間"));
                    //esvd_begin_time_txt.setLayoutParams(view_layout);

                    esvd_end_time_txt = new TextView(SearchActivity.this);
                    esvd_end_time_txt.setText(jsonObject.getString("結束時間"));
                    //esvd_end_time_txt.setLayoutParams(view_layout);

                    esvd_booking_remark_txt = new TextView(SearchActivity.this);
                    esvd_booking_remark_txt.setText(jsonObject.getString("任務說明"));
                    //esvd_booking_remark_txt.setLayoutParams(view_layout);

                    esv_item_remark_txt = new TextView(SearchActivity.this);
                    esv_item_remark_txt.setText(jsonObject.getString("料品說明"));
                    //esv_item_remark_txt.setLayoutParams(view_layout);

                    esvd_remark_txt = new TextView(SearchActivity.this);
                    esvd_remark_txt.setText(jsonObject.getString("工作說明"));
                    //esvd_remark_txt.setLayoutParams(view_layout);

                    esvd_is_money_txt = new TextView(SearchActivity.this);
                    esvd_is_money_txt.setText(jsonObject.getString("單據金額"));
                    //esvd_is_money_txt.setLayoutParams(view_layout);

                    esvd_get_eng_money_txt = new TextView(SearchActivity.this);
                    esvd_get_eng_money_txt.setText(jsonObject.getString("已收款金額"));
                    //esvd_get_eng_money_txt.setLayoutParams(view_layout);

                    //---------------------------------------------------------------------------------------------------------------------

                /*TextView work_type_name_txt = (TextView)findViewById(R.id.work_type_name_txt);
                //TextView work_type_name_txt = new TextView(SearchActivity.this);
                work_type_name_txt.setText(work_type_name);
                //work_type_name_txt.setLayoutParams(view_layout);

                TextView svd_service_no_txt = (TextView)findViewById(R.id.svd_service_no_txt);
                //TextView svd_service_no_txt = new TextView(SearchActivity.this);
                svd_service_no_txt.setText(svd_service_no);
                //svd_service_no_txt.setLayoutParams(view_layout);

                TextView esv_note_txt = (TextView)findViewById(R.id.esv_note_txt);
                //TextView esv_note_txt = new TextView(SearchActivity.this);
                esv_note_txt.setText(esv_note);
                //esv_note_txt.setLayoutParams(view_layout);

                TextView time_period_name_txt = (TextView)findViewById(R.id.time_period_name_txt);
                //TextView time_period_name_txt = new TextView(SearchActivity.this);
                time_period_name_txt.setText(time_period_name);
                //time_period_name_txt.setLayoutParams(view_layout);

                TextView esv_contactor_txt = (TextView)findViewById(R.id.esv_contactor_txt);
                //TextView esv_contactor_txt = new TextView(SearchActivity.this);
                esv_contactor_txt.setText(esv_contactor);
                //esv_contactor_txt.setLayoutParams(view_layout);

                TextView esv_tel_no1_txt = (TextView)findViewById(R.id.esv_tel_no1_txt);
                //TextView esv_tel_no1_txt = new TextView(SearchActivity.this);
                esv_tel_no1_txt.setText(esv_tel_no1);
                //esv_tel_no1_txt.setLayoutParams(view_layout);

                TextView esv_tel_no2_txt = (TextView)findViewById(R.id.esv_tel_no2_txt);
                //TextView esv_tel_no2_txt = new TextView(SearchActivity.this);
                esv_tel_no2_txt.setText(esv_tel_no2);
                //esv_tel_no2_txt.setLayoutParams(view_layout);

                TextView esv_address_txt = (TextView)findViewById(R.id.esv_address_txt);
                //TextView esv_address_txt = new TextView(SearchActivity.this);
                esv_address_txt.setText(esv_address);
                //esv_address_txt.setLayoutParams(view_layout);

                TextView esvd_is_get_money_txt = (TextView)findViewById(R.id.esvd_is_get_money_txt);
                //TextView esvd_is_get_money_txt = new TextView(SearchActivity.this);
                esvd_is_get_money_txt.setText(esvd_is_get_money);
                //esvd_is_get_money_txt.setLayoutParams(view_layout);

                TextView esvd_is_eng_money_txt = (TextView)findViewById(R.id.esvd_is_eng_money_txt);
                //TextView esvd_is_eng_money_txt = new TextView(SearchActivity.this);
                esvd_is_eng_money_txt.setText(esvd_is_eng_money);
                //esvd_is_eng_money_txt.setLayoutParams(view_layout);

                TextView cp_name_txt = (TextView)findViewById(R.id.cp_name_txt);
                //TextView cp_name_txt = new TextView(SearchActivity.this);
                cp_name_txt.setText(cp_name);
                //cp_name_txt.setLayoutParams(view_layout);

                TextView esvd_begin_date_txt = (TextView)findViewById(R.id.esvd_begin_date_txt);
                //TextView esvd_begin_date_txt = new TextView(SearchActivity.this);
                esvd_begin_date_txt.setText(esvd_begin_date);
                //esvd_begin_date_txt.setLayoutParams(view_layout);

                TextView esvd_begin_time_txt = (TextView)findViewById(R.id.esvd_begin_time_txt);
                //TextView esvd_begin_time_txt = new TextView(SearchActivity.this);
                esvd_begin_time_txt.setText(esvd_begin_time);
                //esvd_begin_time_txt.setLayoutParams(view_layout);

                TextView esvd_end_time_txt = (TextView)findViewById(R.id.esvd_end_time_txt);
                //TextView esvd_end_time_txt = new TextView(SearchActivity.this);
                esvd_end_time_txt.setText(esvd_end_time);
                //esvd_end_time_txt.setLayoutParams(view_layout);

                TextView esvd_booking_remark_txt = (TextView)findViewById(R.id.esvd_booking_remark_txt);
                //TextView esvd_booking_remark_txt = new TextView(SearchActivity.this);
                esvd_booking_remark_txt.setText(esvd_booking_remark);
                //esvd_booking_remark_txt.setLayoutParams(view_layout);

                TextView esv_item_remark_txt = (TextView)findViewById(R.id.esv_item_remark_txt);
                //TextView esv_item_remark_txt = new TextView(SearchActivity.this);
                esv_item_remark_txt.setText(esv_item_remark);
                //esv_item_remark_txt.setLayoutParams(view_layout);

                TextView esvd_remark_txt = (TextView)findViewById(R.id.esvd_remark_txt);
                //TextView esvd_remark_txt = new TextView(SearchActivity.this);
                esvd_remark_txt.setText(esvd_remark);
                //esvd_remark_txt.setLayoutParams(view_layout);

                TextView esvd_is_money_txt = (TextView)findViewById(R.id.esvd_is_money_txt);
                //TextView esvd_is_money_txt = new TextView(SearchActivity.this);
                esvd_is_money_txt.setText(esvd_is_money);
                //esvd_is_money_txt.setLayoutParams(view_layout);

                TextView esvd_get_eng_money_txt = (TextView)findViewById(R.id.esvd_get_eng_money_txt);
                //TextView esvd_get_eng_money_txt = new TextView(SearchActivity.this);
                esvd_get_eng_money_txt.setText(esvd_get_eng_money);
                //esvd_get_eng_money_txt.setLayoutParams(view_layout);*/

                    tr.addView(work_type_name_txt);
                    /*tr.addView(svd_service_no_txt);
                    tr.addView(esv_note_txt);
                    tr.addView(time_period_name_txt);
                    tr.addView(esv_contactor_txt);
                    tr.addView(esv_tel_no1_txt);
                    tr.addView(esv_tel_no2_txt);
                    tr.addView(esv_address_txt);
                    tr.addView(esvd_is_get_money_txt);
                    tr.addView(esvd_is_eng_money_txt);
                    tr.addView(cp_name_txt);
                    tr.addView(esvd_begin_date_txt);
                    tr.addView(esvd_begin_time_txt);
                    tr.addView(esvd_end_time_txt);
                    tr.addView(esvd_booking_remark_txt);
                    tr.addView(esv_item_remark_txt);
                    tr.addView(esvd_remark_txt);
                    tr.addView(esvd_is_money_txt);
                    tr.addView(esvd_get_eng_money_txt);*/
                    search_tablelayout.addView(tr);
                    //search_tablelayout.addView(tr, new TableLayout.LayoutParams(MP, WC));
                //}

                /*TextView work_type_name_txt = (TextView)findViewById(R.id.work_type_name_txt);
                work_type_name_txt.setText(work_type_name);

                TextView svd_service_no_txt = (TextView)findViewById(R.id.svd_service_no_txt);
                svd_service_no_txt.setText(svd_service_no);

                TextView esv_note_txt = (TextView)findViewById(R.id.esv_note_txt);
                esv_note_txt.setText(esv_note);*/

                /*Log.i("SearchActivity","派工類別 :"+" "+work_type_name+",派工單號 :"+" "+svd_service_no+",送貨客戶 :"+" "+esv_note
                        +",預約日期時段 :"+" "+time_period_name+",聯絡人 :"+" "+esv_contactor+",主要電話 :"+" "+esv_tel_no1
                        +",次要電話 :"+" "+esv_tel_no2 +",派工地址 :"+" " +esv_address+",是否要收款 :"+" "+esvd_is_get_money
                        +",是否已收款 :"+" "+esvd_is_eng_money +",付款方式 :"+" "+cp_name +",抵達日期 :"+" "+esvd_begin_date
                        +",抵達時間 :"+" "+esvd_begin_time +",結束時間 :"+" "+esvd_end_time+",任務說明 :"+" "+esvd_booking_remark
                        +",料品說明 :"+" "+esv_item_remark +",工作說明 :"+" "+esvd_remark+",單據金額 :"+" "+esvd_is_money
                        +",已收款金額 :"+" "+esvd_get_eng_money);*/

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //日期挑選器
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
        Log.d("SearchActivity", "onDestroy");
    }
}


