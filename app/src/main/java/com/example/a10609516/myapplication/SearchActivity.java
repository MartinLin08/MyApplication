package com.example.a10609516.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    private Button search_button;
    private Button time_start_button;
    private Button time_end_button;
    private Button clean_start_button;
    private Button clean_end_button;
    private Spinner spinner;
    private EditText customer_editText;
    private TableLayout search_tablelayout;
    private LinearLayout search_linearlayout;

    //創建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent = new Intent(SearchActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(SearchActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(SearchActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //顯示查詢派工資料
            case R.id.pending_item:
                Intent intent2 = new Intent(SearchActivity.this, PendingActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "待處理派工", Toast.LENGTH_SHORT).show();
                break; //進入待處理派工頁面
            case R.id.record_item:
                Intent intent8 = new Intent(SearchActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
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

    //更新UI
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            final String[] title_array = {"  派工類別", "  派工單號", "  送貨客戶", "  預約日期時段", "  聯絡人", "  主要電話",
                    "  次要電話", "  派工地址", "  是否要收款", "  是否已收款", "  付款方式", "  單據金額", "  已收款金額",
                    "  抵達日期", "  抵達時間", "  結束時間", "  任務說明", "  料品說明", "  工作說明"};

            switch (msg.what) {
                case 1:
                    search_tablelayout.setStretchAllColumns(true);
                    Bundle b = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    //int i = b.getStringArrayList("Jdata").size();
                    JArrayList = b.getStringArrayList("Jdata");

                    for (int i = 0; i < b.getStringArrayList("Jdata").size(); i++) {

                        //顯示每筆TableLayout的Title
                        TextView dynamically_title;
                        dynamically_title = new TextView(SearchActivity.this);
                        dynamically_title.setText(title_array[i].toString());
                        //dynamically_title.setGravity(Gravity.CENTER);
                        dynamically_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                        dynamically_title.setMaxWidth(200);

                        //顯示每筆TableLayout的SQL資料
                        TextView dynamically_txt;
                        dynamically_txt = new TextView(SearchActivity.this);
                        dynamically_txt.setText(JArrayList.get(i));
                        dynamically_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                        dynamically_txt.setMaxWidth(200);
                        TableRow tr = new TableRow(SearchActivity.this);

                        tr.addView(dynamically_title);
                        tr.addView(dynamically_txt);
                        search_tablelayout.addView(tr);
                    }

                    //設置每筆TableLayout的Button
                    Button dynamically_btn = new Button(SearchActivity.this);
                    dynamically_btn.setBackgroundResource(R.drawable.button);
                    dynamically_btn.setText("修改");
                    dynamically_btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    dynamically_btn.setTextColor(0xff0000ff);

                    TableRow tr1 = new TableRow(SearchActivity.this);
                    tr1.addView(dynamically_btn);
                    //將動態新增TableRow合併 讓Button置中
                    TableRow.LayoutParams the_param;
                    the_param = (TableRow.LayoutParams) dynamically_btn.getLayoutParams();
                    the_param.span = 2;
                    dynamically_btn.setLayoutParams(the_param);

                    search_tablelayout.addView(tr1);

                    //設置每筆TableLayout的分隔線
                    LinearLayout dynamically_llt = new LinearLayout(SearchActivity.this);
                    dynamically_llt.setBackgroundResource(R.drawable.table_h_divider);
                    TableRow tr2 = new TableRow(SearchActivity.this);
                    tr2.addView(dynamically_llt);
                    //將動態新增TableRow合併 讓分隔線延伸
                    TableRow.LayoutParams the_param2;
                    the_param2 = (TableRow.LayoutParams) dynamically_llt.getLayoutParams();
                    the_param2.span = 2;
                    dynamically_llt.setLayoutParams(the_param2);

                    search_tablelayout.addView(tr2);

                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_tablelayout = (TableLayout) findViewById(R.id.search_tablelayot);
        search_linearlayout = (LinearLayout) findViewById(R.id.search_linearlayout);
        spinner = (Spinner) findViewById(R.id.selectList);
        time_start_button = (Button) findViewById(R.id.start2);
        time_end_button = (Button) findViewById(R.id.end2);
        clean_start_button = (Button) findViewById(R.id.clean_button1);
        clean_end_button = (Button) findViewById(R.id.clean_button2);
        customer_editText = (EditText) findViewById(R.id.customer_editText);
        search_button = (Button) findViewById(R.id.search_button);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讓search_tablelayout資料清空
                search_tablelayout.removeAllViews();
                //按search_button顯示物件search_linearlayout、search_tablelayout
                search_linearlayout.setVisibility(View.VISIBLE);
                search_tablelayout.setVisibility(View.VISIBLE);

                sendRequestWithOkHttp();

            }
        });//end setOnItemClickListener

        //清空time_start_button內的文字
        clean_start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time_start_button.setText("");
            }
        });

        //清空time_end_button內的文字
        clean_end_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time_end_button.setText("");
            }
        });

        WorkTypeSpinner();

    }



    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data" , MODE_PRIVATE);
                String user_id_data = user_id.getString("ID" , "");
                Log.i("SearchActivity",user_id_data);

                //獲取日期挑選器的數據
                String time_start = time_start_button.getText().toString();
                String time_end = time_end_button.getText().toString();
                String customer_edt = customer_editText.getText().toString();
                String spinner_select = String.valueOf(spinner.getSelectedItem());

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id",user_id_data)
                            .add("ESVD_BEGIN_DATE1",time_start)
                            .add("ESVD_BEGIN_DATE2",time_end)
                            .add("ESV_NOTE",customer_edt)
                            .add("WORK_TYPE_NAME",spinner_select)
                            .build();
                    //Log.i("SearchActivity",);
                    Request request = new Request.Builder()
                            .url("http://192.168.0.172/Test1/SearchData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //Log.i("SearchActivity",responseData);
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        search_tablelayout.setStretchAllColumns(true);
        try {

            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {

                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String work_type_name = jsonObject.getString("派工類別");
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
                String esvd_is_money = jsonObject.getString("單據金額");
                String esvd_get_eng_money = jsonObject.getString("已收款金額");
                String esvd_begin_date = jsonObject.getString("抵達日期");
                String esvd_begin_time = jsonObject.getString("抵達時間");
                String esvd_end_time = jsonObject.getString("結束時間");
                String esvd_booking_remark = jsonObject.getString("任務說明");
                String esv_item_remark = jsonObject.getString("料品說明");
                String esvd_remark = jsonObject.getString("工作說明");


                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(work_type_name);
                JArrayList.add(svd_service_no);
                JArrayList.add(esv_note);
                JArrayList.add(time_period_name);
                JArrayList.add(esv_contactor);
                JArrayList.add(esv_tel_no1);
                JArrayList.add(esv_tel_no2);
                JArrayList.add(esv_address);
                JArrayList.add(esvd_is_get_money);
                JArrayList.add(esvd_is_eng_money);
                JArrayList.add(cp_name);
                JArrayList.add(esvd_is_money);
                JArrayList.add(esvd_get_eng_money);
                JArrayList.add(esvd_begin_date);
                JArrayList.add(esvd_begin_time);
                JArrayList.add(esvd_end_time);
                JArrayList.add(esvd_booking_remark);
                JArrayList.add(esv_item_remark);
                JArrayList.add(esvd_remark);


                //HandlerMessage更新UI
                Bundle b = new Bundle();
                b.putStringArrayList("Jdata", JArrayList);
                Message msg = mHandler.obtainMessage();
                msg.setData(b);
                msg.what = 1;
                msg.sendToTarget();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //日期挑選器
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bData = new Bundle();
        bData.putInt("view", v.getId());
        Button button = (Button) v;
        bData.putString("date", button.getText().toString());
        newFragment.setArguments(bData);
        newFragment.show(getSupportFragmentManager(), "日期挑選器");
    }

    private void WorkTypeSpinner() {
        //Spinner下拉選單
        final String[] select = {"選擇", "臨時取消", "換濾心", "換濾料", "加鹽", "末端", "小前置", "全屋", "社區保養", "檢修(一)", "檢修(二)"};
        ArrayAdapter<String> selectList = new ArrayAdapter<>(SearchActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                select);
        spinner.setAdapter(selectList);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SearchActivity", "onDestroy");
    }
}




