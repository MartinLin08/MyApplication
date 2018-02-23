package com.example.a10609516.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ScheduleActivity extends AppCompatActivity {

    private View view1, view2, view3;
    private List<View> viewList;//view陣列
    private ViewPager viewPager; //對應的viewPager
    private PagerTitleStrip pagerTitleStrip; //對應的viewPager標頭

    private List<String> titleList;  //標題列表陣列

    private TableLayout today_TableLayout, week_TableLayout, missing_TableLayout;
    private ImageView[] today_about_ImageView, week_about_ImageView, missing_about_ImageView;
    private Button more_button1, more_button2, more_button3;
    private TextView today_sql_total_textView, week_sql_total_textView, missing_sql_total_textView;

    /**
     * 創建Menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 進入Menu各個頁面
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent = new Intent(ScheduleActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //顯示行程資訊
            case R.id.calendar_item:
                Intent intent1 = new Intent(ScheduleActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent2 = new Intent(ScheduleActivity.this, SearchActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; // 進入查詢派工資料頁面
            /*case R.id.signature_item:
                Intent intent3 = new Intent(ScheduleActivity.this, SignatureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面*/
            case R.id.record_item:
                Intent intent4 = new Intent(ScheduleActivity.this, RecordActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; // 進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent5 = new Intent(ScheduleActivity.this, PictureActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳
            case R.id.customer_item:
                Intent intent6 = new Intent(ScheduleActivity.this, CustomerActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            case R.id.upload_item:
                Intent intent7 = new Intent(ScheduleActivity.this, UploadActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent8 = new Intent(ScheduleActivity.this, CorrectActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            case R.id.about_item:
                Intent intent9 = new Intent(ScheduleActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //動態取得 View 物件
        InItFunction();
        //設置ViewPager的每個頁面Title
        PagerTitle();
        //ViewPager的各項設置
        ViewPagerAdapter();

    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title);

    }

    /**
     * 動態取得 ViewPager 物件
     */
    private void InViewGroupFunction() {

        today_TableLayout = (TableLayout) findViewById(R.id.today_TableLayout);
        week_TableLayout = (TableLayout) findViewById(R.id.week_TableLayout);
        missing_TableLayout = (TableLayout) findViewById(R.id.missing_TableLayout);

        today_sql_total_textView = (TextView) findViewById(R.id.today_sql_total_textView);
        week_sql_total_textView = (TextView) findViewById(R.id.week_sql_total_textView);
        missing_sql_total_textView = (TextView) findViewById(R.id.missing_sql_total_textView);

        more_button1 = (Button) findViewById(R.id.more_button1);
        more_button2 = (Button) findViewById(R.id.more_button2);
        more_button3 = (Button) findViewById(R.id.more_button3);

    }

    /**
     * 設置ViewPager的每個頁面Title
     */
    private void PagerTitle() {

        //為標題設置字體大小
        pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
        //為標題設置字體顏色
        pagerTitleStrip.setTextColor(Color.WHITE);
        //為標題設置背景顏色
        pagerTitleStrip.setBackgroundColor(Color.BLUE);
        //設置標題位置
        pagerTitleStrip.setGravity(17);

        pagerTitleStrip.getChildAt(0).setVisibility(View.GONE);
        pagerTitleStrip.getChildAt(2).setVisibility(View.GONE);

        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.activity_day_schedule, null);
        view2 = inflater.inflate(R.layout.activity_week_schedule, null);
        view3 = inflater.inflate(R.layout.activity_missing_date, null);

        viewList = new ArrayList<>();// 將要分頁顯示的View装入陣列中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        titleList = new ArrayList<>();// 每個頁面的Title數據
        titleList.add("今日行程資訊");
        titleList.add("一周行程資訊");
        titleList.add("派工未填抵達日期");

    }

    /**
     * ViewPager的各項設置
     */
    private void ViewPagerAdapter() {

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                //根據傳來的Key，找到view,判斷與傳來的參數View arg0是不是同一個layout
                return arg0 == viewList.get(Integer.parseInt(arg1.toString()));
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));

            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));

                //動態取得 View 物件
                InViewGroupFunction();

                switch (position) {
                    case 0:
                        //每次移動回來今日行程資訊先刪除之前的TableView再動態新增回來
                        today_TableLayout.removeAllViews();
                        //建立TodayScheduleData.php OKHttp連線
                        sendRequestWithOkHttpOfToday();

                        more_button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    Intent intent1 = new Intent(ScheduleActivity.this, Day_Work.class);
                                    startActivity(intent1);
                            }
                        });
                        break;

                    case 1:
                        //每次移動回來一周行程資訊先刪除之前的TableView再動態新增回來
                        week_TableLayout.removeAllViews();
                        //建立WeekScheduleData.php OKHttp連線
                        sendRequestWithOkHttpOfWeek();

                        more_button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent2 = new Intent(ScheduleActivity.this, Week_Work.class);
                                startActivity(intent2);
                            }
                        });
                        break;

                    case 2:
                        //每次移動回來派工未填抵達日期先刪除之前的TableView再動態新增回來
                        missing_TableLayout.removeAllViews();
                        //建立MissingScheduleData.php OKHttp連線
                        sendRequestWithOkHttpOfMissing();

                        more_button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent3 = new Intent(ScheduleActivity.this, Missing_Date_Record.class);
                                startActivity(intent3);
                            }
                        });
                        break;
                    default:
                        break;
                }
                //把當前新增layout的位置（position）做為Key傳過去
                return position;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                // TODO Auto-generated method stub
                return titleList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);

    }

    /**
     * 與OkHttp(Today)建立連線
     */
    private void sendRequestWithOkHttpOfToday() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.i("ScheduleActivity", user_id_data);

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Log.i("ScheduleActivity", user_id_data);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/wqp/TodayScheduleData.php")
                            .url("http://192.168.0.172/Test1/TodayScheduleData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("ScheduleActivity", responseData);
                    parseJSONWithJSONObjectOfToday(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 獲得JSON字串並解析成String字串
     * @param jsonData
     */
    private void parseJSONWithJSONObjectOfToday(String jsonData) {

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            today_about_ImageView = new ImageView[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String work_type_name = jsonObject.getString("派工類別");
                String esvd_service_no = jsonObject.getString("派工單號");
                String esv_note = jsonObject.getString("送貨客戶");
                String time_period_name = jsonObject.getString("預約日期時段");
                String esv_contactor = jsonObject.getString("聯絡人");
                String esv_tel_no1 = jsonObject.getString("主要電話");
                String esv_tel_no2 = jsonObject.getString("次要電話");
                String esv_address = jsonObject.getString("派工地址");
                String cp_name = jsonObject.getString("付款方式");
                String esvd_is_get_money = jsonObject.getString("是否要收款");
                String esvd_is_money = jsonObject.getString("應收款金額");
                String esvd_is_eng_money = jsonObject.getString("是否已收款");
                String esvd_get_eng_money = jsonObject.getString("已收款金額");
                String esvd_begin_date = jsonObject.getString("抵達日期");
                String esvd_begin_time = jsonObject.getString("抵達時間");
                String esvd_end_time = jsonObject.getString("結束時間");
                String esvd_booking_remark = jsonObject.getString("任務說明");
                String esv_item_remark = jsonObject.getString("料品說明");
                String esvd_remark = jsonObject.getString("工作說明");
                String esvd_seq_id = jsonObject.getString("派工資料識別碼");
                String esvd_eng_points = jsonObject.getString("工務點數");
                String reserve_time = jsonObject.getString("今日派工時段");
                String work_type = jsonObject.getString("處理方式");


                Log.i("ScheduleActivity", reserve_time);
                Log.i("ScheduleActivity", work_type_name);


                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(work_type_name);
                JArrayList.add(esvd_service_no);
                JArrayList.add(esv_note);
                JArrayList.add(time_period_name);
                JArrayList.add(esv_contactor);
                JArrayList.add(esv_tel_no1);
                JArrayList.add(esv_tel_no2);
                JArrayList.add(esv_address);
                JArrayList.add(cp_name);
                JArrayList.add(esvd_is_get_money);
                JArrayList.add(esvd_is_money);
                JArrayList.add(esvd_is_eng_money);
                JArrayList.add(esvd_get_eng_money);
                JArrayList.add(esvd_begin_date);
                JArrayList.add(esvd_begin_time);
                JArrayList.add(esvd_end_time);
                JArrayList.add(esvd_booking_remark);
                JArrayList.add(esv_item_remark);
                JArrayList.add(esvd_remark);
                JArrayList.add(esvd_seq_id);
                JArrayList.add(esvd_eng_points);
                JArrayList.add(reserve_time);
                JArrayList.add(work_type);


                //HandlerMessage更新UI
                Bundle b = new Bundle();
                b.putStringArrayList("JSON_data", JArrayList);
                Message msg = today_mHandler.obtainMessage();
                msg.setData(b);
                msg.what = 1;
                msg.sendToTarget();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新UI(Today)
     */
    Handler today_mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            final String[] title_array = {"派工類別", "派工單號", "送貨客戶", "預約日期時段", "聯絡人", "主要電話",
                    "次要電話", "派工地址", "付款方式", "是否要收款", "應收款金額", "是否已收款", "已收款金額",
                    "抵達日期", "抵達時間", "結束時間", "任務說明", "料品說明", "工作說明", "派工資料識別碼",
                    "工務點數", "今日派工時段 :", "處理方式 :"};

            switch (msg.what) {
                case 1:
                    //最外層有一個大的TableLayout,再設置TableRow包住小的TableLayout
                    today_TableLayout.setStretchAllColumns(true);

                    //設置大TableLayout的TableRow
                    TableRow big_tbr = new TableRow(ScheduleActivity.this);
                    //設定big_tbr的Weight權重
                    big_tbr.setWeightSum(4);
                    //設置每筆資料的小TableLayout
                    TableLayout small_tb = new TableLayout(ScheduleActivity.this);
                    //全部列自動填充空白處
                    small_tb.setStretchAllColumns(true);
                    //設定TableRow的寬高
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    JArrayList = jb.getStringArrayList("JSON_data");

                    //設置每筆TableLayout的Button監聽器、與動態新增Button的ID
                    int loc = 0;
                    for (int j = 0; j < today_about_ImageView.length; j++) {
                        if (today_about_ImageView[j] == null) {
                            loc = j;
                            break;
                        }
                    }
                    today_about_ImageView[loc] = new ImageView(ScheduleActivity.this);
                    today_about_ImageView[loc].setImageResource(R.drawable.about);
                    today_about_ImageView[loc].setScaleType(ImageView.ScaleType.CENTER);
                    today_about_ImageView[loc].setId(loc);

                    today_about_ImageView[loc].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int a = 0; a < today_about_ImageView.length; a++) {
                                if (v.getId() == today_about_ImageView[a].getId()) {

                                    Intent intent_maintain = new Intent(ScheduleActivity.this, MaintainActivity.class);
                                    //取得大TableLayout中的大TableRow位置
                                    TableRow tb_tbr = (TableRow) today_TableLayout.getChildAt(a);
                                    //取得大TableRow中的小TableLayout位置
                                    TableLayout tb_tbr_tb = (TableLayout) tb_tbr.getChildAt(1);
                                    //派工資料的迴圈
                                    for (int x = 0; x < 21; x++) {
                                        //取得小TableLayout中的小TableRow位置
                                        TableRow tb_tbr_tb_tbr = (TableRow) tb_tbr_tb.getChildAt(x);
                                        //小TableRow中的layout_column位置
                                        TextView ThirdTextView = (TextView) tb_tbr_tb_tbr.getChildAt(1);
                                        String ResponseText = ThirdTextView.getText().toString();
                                        //將SQL裡的資料傳到MaintainActivity
                                        Bundle bundle = new Bundle();
                                        bundle.putString("ResponseText" + x, ResponseText);

                                        intent_maintain.putExtras(bundle);//可放所有基本類別
                                    }

                                    startActivity(intent_maintain);

                                }
                            }
                        }
                    });


                    for (int i = 0; i < jb.getStringArrayList("JSON_data").size(); i++) {

                        //顯示每筆TableLayout的Title
                        TextView dynamically_title;
                        dynamically_title = new TextView(ScheduleActivity.this);
                        dynamically_title.setText(title_array[i].toString());
                        dynamically_title.setPadding(30, 5, 0, 0);
                        dynamically_title.setGravity(Gravity.LEFT);
                        dynamically_title.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);


                        //顯示每筆TableLayout的SQL資料
                        TextView dynamically_txt;
                        dynamically_txt = new TextView(ScheduleActivity.this);
                        dynamically_txt.setText(JArrayList.get(i));
                        dynamically_txt.setPadding(0, 5, 0, 0);
                        dynamically_txt.setGravity(Gravity.LEFT);
                        dynamically_txt.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);


                        TableRow tr1 = new TableRow(ScheduleActivity.this);
                        tr1.setGravity(Gravity.CENTER_HORIZONTAL);
                        tr1.addView(dynamically_title, layoutParams);
                        tr1.addView(dynamically_txt, layoutParams);

                        small_tb.addView(tr1, layoutParams);

                        //隱藏不需要的SQL資料
                        if (i < 21) {
                            small_tb.getChildAt(i).setVisibility(View.GONE);
                        }

                    }

                    //about_ImageView寬為0,高為WRAP_CONTENT,權重比為1
                    big_tbr.addView(today_about_ImageView[loc], new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    //small_tb寬為0,高為WRAP_CONTENT,權重比為3
                    big_tbr.addView(small_tb, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3));


                    TableRow.LayoutParams the_param3;
                    the_param3 = (TableRow.LayoutParams) small_tb.getLayoutParams();
                    the_param3.span = 2;
                    the_param3.width = TableRow.LayoutParams.MATCH_PARENT;
                    small_tb.setLayoutParams(the_param3);


                    today_TableLayout.addView(big_tbr);

                    //如果MySQL傳回的資料超過6筆,從第7筆資料開始隱藏
                    if (loc > 5) {
                        today_TableLayout.getChildAt(loc).setVisibility(View.GONE);
                    }


                    //顯示今日件數的總數
                    int total = today_TableLayout.getChildCount();
                    today_sql_total_textView.setText(String.valueOf(total));


                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };


    /**
     * 與OkHttp(Week)建立連線
     */
    private void sendRequestWithOkHttpOfWeek() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.i("ScheduleActivity2", user_id_data);

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Log.i("ScheduleActivity2", user_id_data);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/wqp/WeekScheduleData.php")
                            .url("http://192.168.0.172/Test1/WeekScheduleData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("ScheduleActivity2", responseData);
                    parseJSONWithJSONObjectOfWeek(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 獲得JSON字串並解析成String字串
     * @param jsonData
     */
    private void parseJSONWithJSONObjectOfWeek(String jsonData) {

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            week_about_ImageView = new ImageView[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String work_type_name = jsonObject.getString("派工類別");
                String esvd_service_no = jsonObject.getString("派工單號");
                String esv_note = jsonObject.getString("送貨客戶");
                String time_period_name = jsonObject.getString("預約日期時段");
                String esv_contactor = jsonObject.getString("聯絡人");
                String esv_tel_no1 = jsonObject.getString("主要電話");
                String esv_tel_no2 = jsonObject.getString("次要電話");
                String esv_address = jsonObject.getString("派工地址");
                String cp_name = jsonObject.getString("付款方式");
                String esvd_is_get_money = jsonObject.getString("是否要收款");
                String esvd_is_money = jsonObject.getString("應收款金額");
                String esvd_is_eng_money = jsonObject.getString("是否已收款");
                String esvd_get_eng_money = jsonObject.getString("已收款金額");
                String esvd_begin_date = jsonObject.getString("抵達日期");
                String esvd_begin_time = jsonObject.getString("抵達時間");
                String esvd_end_time = jsonObject.getString("結束時間");
                String esvd_booking_remark = jsonObject.getString("任務說明");
                String esv_item_remark = jsonObject.getString("料品說明");
                String esvd_remark = jsonObject.getString("工作說明");
                String esvd_seq_id = jsonObject.getString("派工資料識別碼");
                String esvd_eng_points = jsonObject.getString("工務點數");
                String reserve_time = jsonObject.getString("派工日期時間");
                String work_type = jsonObject.getString("處理方式");

                Log.i("ScheduleActivity", reserve_time);
                Log.i("ScheduleActivity", work_type_name);


                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(work_type_name);
                JArrayList.add(esvd_service_no);
                JArrayList.add(esv_note);
                JArrayList.add(time_period_name);
                JArrayList.add(esv_contactor);
                JArrayList.add(esv_tel_no1);
                JArrayList.add(esv_tel_no2);
                JArrayList.add(esv_address);
                JArrayList.add(cp_name);
                JArrayList.add(esvd_is_get_money);
                JArrayList.add(esvd_is_money);
                JArrayList.add(esvd_is_eng_money);
                JArrayList.add(esvd_get_eng_money);
                JArrayList.add(esvd_begin_date);
                JArrayList.add(esvd_begin_time);
                JArrayList.add(esvd_end_time);
                JArrayList.add(esvd_booking_remark);
                JArrayList.add(esv_item_remark);
                JArrayList.add(esvd_remark);
                JArrayList.add(esvd_seq_id);
                JArrayList.add(esvd_eng_points);
                JArrayList.add(reserve_time);
                JArrayList.add(work_type);


                //HandlerMessage更新UI
                Bundle b = new Bundle();
                b.putStringArrayList("JSON_data", JArrayList);
                Message msg = week_mHandler.obtainMessage();
                msg.setData(b);
                msg.what = 1;
                msg.sendToTarget();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新UI(Week)
     */
    Handler week_mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            final String[] title_array = {"派工類別", "派工單號", "送貨客戶", "預約日期時段", "聯絡人", "主要電話",
                    "次要電話", "派工地址", "付款方式", "是否要收款", "應收款金額", "是否已收款", "已收款金額",
                    "抵達日期", "抵達時間", "結束時間", "任務說明", "料品說明", "工作說明", "派工資料識別碼",
                    "工務點數", "派工日期時間 :", "處理方式 :"};

            switch (msg.what) {
                case 1:
                    //最外層有一個大的TableLayout,再設置TableRow包住小的TableLayout
                    week_TableLayout.setStretchAllColumns(true);

                    //設置大TableLayout的TableRow
                    TableRow big_tbr = new TableRow(ScheduleActivity.this);
                    //設定big_tbr的Weight權重
                    big_tbr.setWeightSum(4);
                    //設置每筆資料的小TableLayout
                    TableLayout small_tb = new TableLayout(ScheduleActivity.this);
                    //全部列自動填充空白處
                    small_tb.setStretchAllColumns(true);
                    //設定TableRow的寬高
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    JArrayList = jb.getStringArrayList("JSON_data");

                    //設置每筆TableLayout的Button監聽器、與動態新增Button的ID
                    int loc = 0;
                    for (int j = 0; j < week_about_ImageView.length; j++) {
                        if (week_about_ImageView[j] == null) {
                            loc = j;
                            break;
                        }
                    }
                    week_about_ImageView[loc] = new ImageView(ScheduleActivity.this);
                    week_about_ImageView[loc].setImageResource(R.drawable.about);
                    week_about_ImageView[loc].setScaleType(ImageView.ScaleType.CENTER);
                    week_about_ImageView[loc].setId(loc);

                    week_about_ImageView[loc].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int a = 0; a < week_about_ImageView.length; a++) {
                                if (v.getId() == week_about_ImageView[a].getId()) {

                                    Intent intent_maintain = new Intent(ScheduleActivity.this, MaintainActivity.class);
                                    //取得大TableLayout中的大TableRow位置
                                    TableRow tb_tbr = (TableRow) week_TableLayout.getChildAt(a);
                                    //取得大TableRow中的小TableLayout位置
                                    TableLayout tb_tbr_tb = (TableLayout) tb_tbr.getChildAt(1);
                                    //派工資料的迴圈
                                    for (int x = 0; x < 21; x++) {
                                        //取得小TableLayout中的小TableRow位置
                                        TableRow tb_tbr_tb_tbr = (TableRow) tb_tbr_tb.getChildAt(x);
                                        //小TableRow中的layout_column位置
                                        TextView ThirdTextView = (TextView) tb_tbr_tb_tbr.getChildAt(1);
                                        String ResponseText = ThirdTextView.getText().toString();
                                        //將SQL裡的資料傳到MaintainActivity
                                        Bundle bundle = new Bundle();
                                        bundle.putString("ResponseText" + x, ResponseText);

                                        intent_maintain.putExtras(bundle);//可放所有基本類別
                                    }

                                    startActivity(intent_maintain);

                                }
                            }
                        }
                    });


                    for (int i = 0; i < jb.getStringArrayList("JSON_data").size(); i++) {

                        //顯示每筆TableLayout的Title
                        TextView dynamically_title;
                        dynamically_title = new TextView(ScheduleActivity.this);
                        dynamically_title.setText(title_array[i].toString());
                        dynamically_title.setPadding(10, 5, 0, 0);
                        dynamically_title.setGravity(Gravity.LEFT);
                        dynamically_title.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);


                        //顯示每筆TableLayout的SQL資料
                        TextView dynamically_txt;
                        dynamically_txt = new TextView(ScheduleActivity.this);
                        dynamically_txt.setText(JArrayList.get(i));
                        dynamically_txt.setPadding(0, 5, 0, 0);
                        dynamically_txt.setGravity(Gravity.LEFT);
                        dynamically_txt.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);


                        TableRow tr1 = new TableRow(ScheduleActivity.this);
                        tr1.setGravity(Gravity.CENTER_HORIZONTAL);
                        tr1.addView(dynamically_title, layoutParams);
                        tr1.addView(dynamically_txt, layoutParams);

                        small_tb.addView(tr1, layoutParams);

                        if (i < 21) {
                            small_tb.getChildAt(i).setVisibility(View.GONE);
                        }

                    }

                    //about_ImageView寬為0,高為WRAP_CONTENT,權重比為1
                    big_tbr.addView(week_about_ImageView[loc], new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    //small_tb寬為0,高為WRAP_CONTENT,權重比為3
                    big_tbr.addView(small_tb, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3));


                    TableRow.LayoutParams the_param3;
                    the_param3 = (TableRow.LayoutParams) small_tb.getLayoutParams();
                    the_param3.span = 2;
                    the_param3.width = TableRow.LayoutParams.MATCH_PARENT;
                    small_tb.setLayoutParams(the_param3);


                    week_TableLayout.addView(big_tbr);

                    //如果MySQL傳回的資料超過6筆,從第7筆資料開始隱藏
                    if (loc > 5) {
                        week_TableLayout.getChildAt(loc).setVisibility(View.GONE);
                    }

                    //顯示一周件數的總數
                    int total = week_TableLayout.getChildCount();
                    week_sql_total_textView.setText(String.valueOf(total));


                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };


    /**
     * 與OkHttp(Missing)建立連線
     */
    private void sendRequestWithOkHttpOfMissing() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.i("ScheduleActivity2", user_id_data);

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Log.i("ScheduleActivity2", user_id_data);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/wqp/MissingScheduleData.php")
                            .url("http://192.168.0.172/Test1/MissingScheduleData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("ScheduleActivity2", responseData);
                    parseJSONWithJSONObjectOfMissing(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 獲得JSON字串並解析成String字串
     * @param jsonData
     */
    private void parseJSONWithJSONObjectOfMissing(String jsonData) {

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            missing_about_ImageView = new ImageView[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String work_type_name = jsonObject.getString("派工類別");
                String esvd_service_no = jsonObject.getString("派工單號");
                String esv_note = jsonObject.getString("送貨客戶");
                String time_period_name = jsonObject.getString("預約日期時段");
                String esv_contactor = jsonObject.getString("聯絡人");
                String esv_tel_no1 = jsonObject.getString("主要電話");
                String esv_tel_no2 = jsonObject.getString("次要電話");
                String esv_address = jsonObject.getString("派工地址");
                String cp_name = jsonObject.getString("付款方式");
                String esvd_is_get_money = jsonObject.getString("是否要收款");
                String esvd_is_money = jsonObject.getString("應收款金額");
                String esvd_is_eng_money = jsonObject.getString("是否已收款");
                String esvd_get_eng_money = jsonObject.getString("已收款金額");
                String esvd_begin_date = jsonObject.getString("抵達日期");
                String esvd_begin_time = jsonObject.getString("抵達時間");
                String esvd_end_time = jsonObject.getString("結束時間");
                String esvd_booking_remark = jsonObject.getString("任務說明");
                String esv_item_remark = jsonObject.getString("料品說明");
                String esvd_remark = jsonObject.getString("工作說明");
                String esvd_seq_id = jsonObject.getString("派工資料識別碼");
                String esvd_eng_points = jsonObject.getString("工務點數");
                String reserve_time = jsonObject.getString("派工日期時間");
                String work_type = jsonObject.getString("處理方式");

                Log.i("ScheduleActivity", reserve_time);
                Log.i("ScheduleActivity", work_type_name);


                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(work_type_name);
                JArrayList.add(esvd_service_no);
                JArrayList.add(esv_note);
                JArrayList.add(time_period_name);
                JArrayList.add(esv_contactor);
                JArrayList.add(esv_tel_no1);
                JArrayList.add(esv_tel_no2);
                JArrayList.add(esv_address);
                JArrayList.add(cp_name);
                JArrayList.add(esvd_is_get_money);
                JArrayList.add(esvd_is_money);
                JArrayList.add(esvd_is_eng_money);
                JArrayList.add(esvd_get_eng_money);
                JArrayList.add(esvd_begin_date);
                JArrayList.add(esvd_begin_time);
                JArrayList.add(esvd_end_time);
                JArrayList.add(esvd_booking_remark);
                JArrayList.add(esv_item_remark);
                JArrayList.add(esvd_remark);
                JArrayList.add(esvd_seq_id);
                JArrayList.add(esvd_eng_points);
                JArrayList.add(reserve_time);
                JArrayList.add(work_type);


                //HandlerMessage更新UI
                Bundle b = new Bundle();
                b.putStringArrayList("JSON_data", JArrayList);
                Message msg = missing_mHandler.obtainMessage();
                msg.setData(b);
                msg.what = 1;
                msg.sendToTarget();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新UI(Missing)
     */
    Handler missing_mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            final String[] title_array = {"派工類別", "派工單號", "送貨客戶", "預約日期時段", "聯絡人", "主要電話",
                    "次要電話", "派工地址", "付款方式", "是否要收款", "應收款金額", "是否已收款", "已收款金額",
                    "抵達日期", "抵達時間", "結束時間", "任務說明", "料品說明", "工作說明", "派工資料識別碼",
                    "工務點數", "派工日期時間 :", "處理方式 :"};

            switch (msg.what) {
                case 1:
                    //最外層有一個大的TableLayout,再設置TableRow包住小的TableLayout
                    missing_TableLayout.setStretchAllColumns(true);

                    //設置大TableLayout的TableRow
                    TableRow big_tbr = new TableRow(ScheduleActivity.this);
                    //設定big_tbr的Weight權重
                    big_tbr.setWeightSum(4);
                    //設置每筆資料的小TableLayout
                    TableLayout small_tb = new TableLayout(ScheduleActivity.this);
                    //全部列自動填充空白處
                    small_tb.setStretchAllColumns(true);
                    //設定TableRow的寬高
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    JArrayList = jb.getStringArrayList("JSON_data");

                    //設置每筆TableLayout的Button監聽器、與動態新增Button的ID
                    int loc = 0;
                    for (int j = 0; j < missing_about_ImageView.length; j++) {
                        if (missing_about_ImageView[j] == null) {
                            loc = j;
                            break;
                        }
                    }
                    missing_about_ImageView[loc] = new ImageView(ScheduleActivity.this);
                    missing_about_ImageView[loc].setImageResource(R.drawable.about);
                    missing_about_ImageView[loc].setScaleType(ImageView.ScaleType.CENTER);
                    missing_about_ImageView[loc].setId(loc);

                    missing_about_ImageView[loc].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int a = 0; a < missing_about_ImageView.length; a++) {
                                if (v.getId() == missing_about_ImageView[a].getId()) {

                                    Intent intent_maintain = new Intent(ScheduleActivity.this, MaintainActivity.class);
                                    //取得大TableLayout中的大TableRow位置
                                    TableRow tb_tbr = (TableRow) missing_TableLayout.getChildAt(a);
                                    //取得大TableRow中的小TableLayout位置
                                    TableLayout tb_tbr_tb = (TableLayout) tb_tbr.getChildAt(1);
                                    //派工資料的迴圈
                                    for (int x = 0; x < 21; x++) {
                                        //取得小TableLayout中的小TableRow位置
                                        TableRow tb_tbr_tb_tbr = (TableRow) tb_tbr_tb.getChildAt(x);
                                        //小TableRow中的layout_column位置
                                        TextView ThirdTextView = (TextView) tb_tbr_tb_tbr.getChildAt(1);
                                        String ResponseText = ThirdTextView.getText().toString();
                                        //將SQL裡的資料傳到MaintainActivity
                                        Bundle bundle = new Bundle();
                                        bundle.putString("ResponseText" + x, ResponseText);

                                        intent_maintain.putExtras(bundle);//可放所有基本類別
                                    }

                                    startActivity(intent_maintain);

                                }
                            }
                        }
                    });


                    for (int i = 0; i < jb.getStringArrayList("JSON_data").size(); i++) {

                        //顯示每筆TableLayout的Title
                        TextView dynamically_title;
                        dynamically_title = new TextView(ScheduleActivity.this);
                        dynamically_title.setText(title_array[i].toString());
                        dynamically_title.setPadding(10, 5, 0, 0);
                        dynamically_title.setGravity(Gravity.LEFT);
                        dynamically_title.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);


                        //顯示每筆TableLayout的SQL資料
                        TextView dynamically_txt;
                        dynamically_txt = new TextView(ScheduleActivity.this);
                        dynamically_txt.setText(JArrayList.get(i));
                        dynamically_txt.setPadding(0, 5, 0, 0);
                        dynamically_txt.setGravity(Gravity.LEFT);
                        dynamically_txt.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);


                        TableRow tr1 = new TableRow(ScheduleActivity.this);
                        tr1.setGravity(Gravity.CENTER_HORIZONTAL);
                        tr1.addView(dynamically_title, layoutParams);
                        tr1.addView(dynamically_txt, layoutParams);

                        small_tb.addView(tr1, layoutParams);

                        if (i < 21) {
                            small_tb.getChildAt(i).setVisibility(View.GONE);
                        }

                    }

                    //about_ImageView寬為0,高為WRAP_CONTENT,權重比為1
                    big_tbr.addView(missing_about_ImageView[loc], new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    //small_tb寬為0,高為WRAP_CONTENT,權重比為3
                    big_tbr.addView(small_tb, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3));


                    TableRow.LayoutParams the_param3;
                    the_param3 = (TableRow.LayoutParams) small_tb.getLayoutParams();
                    the_param3.span = 2;
                    the_param3.width = TableRow.LayoutParams.MATCH_PARENT;
                    small_tb.setLayoutParams(the_param3);


                    missing_TableLayout.addView(big_tbr);

                    //如果MySQL傳回的資料超過6筆,從第7筆資料開始隱藏
                    if (loc > 5) {
                        missing_TableLayout.getChildAt(loc).setVisibility(View.GONE);
                    }

                    //顯示一周件數的總數
                    int total = missing_TableLayout.getChildCount();
                    missing_sql_total_textView.setText(String.valueOf(total));

                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ScheduleActivity", "onDestroy");
    }
}
