package com.example.a10609516.myapplication.Workers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.a10609516.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Day_Work extends AppCompatActivity {

    private TextView date_textView;
    private TableLayout date_work_TableLayout;
    private Button[] dynamically_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_work);
        //動態取得 View 物件
        InItFunction();
        //獲取當前年月日
        DateToday();
        //建立TodayScheduleData.php OKHttp連線
        sendRequestWithOkHttpOfToday();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        date_textView = (TextView) findViewById(R.id.date_textView);
        date_work_TableLayout = (TableLayout) findViewById(R.id.date_work_TableLayout);
    }

    /**
     * 獲取當前年月日
     */
    private void DateToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new java.util.Date());
        date_textView.setText(date);
    }

    /**
     * 與OkHttp建立連線
     */
    private void sendRequestWithOkHttpOfToday() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.i("Day_Work", user_id_data);
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Log.i("Day_Work", user_id_data);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/TodayScheduleData.php")
                            .url("http://192.168.0.172/WQP/TodayScheduleData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("Day_Work", responseData);
                    parseJSONWithJSONObjectOfToday(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 獲得JSON字串並解析成String字串
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObjectOfToday(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            dynamically_btn = new Button[jsonArray.length()];
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
                String my_ontype = jsonObject.getString("狀態");
                String reserve_time = jsonObject.getString("今日派工時段");
                String work_type = jsonObject.getString("處理方式");

                Log.i("Day_Work", reserve_time);
                Log.i("Day_Work", work_type_name);

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
                JArrayList.add(my_ontype);
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
     * 更新UI
     */
    Handler today_mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final String[] title_array = {"派工類別", "派工單號", "送貨客戶", "派工日期時段", "聯絡人",
                    "主要電話", "次要電話", "派工地址", "付款方式", "是否要收款",
                    "應收款金額", "是否已收款", "已收款金額", "抵達日期", "抵達時間",
                    "結束時間", "任務說明", "料品說明", "工作說明", "狀態",
                    "今日派工時段 :", "處理方式 :"};
            switch (msg.what) {
                case 1:
                    //最外層有一個大的TableLayout,再設置TableRow包住小的TableLayout
                    date_work_TableLayout.setStretchAllColumns(true);
                    //設置大TableLayout的TableRow
                    TableRow big_tbr = new TableRow(Day_Work.this);
                    //設置每筆資料的小TableLayout
                    final TableLayout small_tb = new TableLayout(Day_Work.this);
                    //全部列自動填充空白處
                    small_tb.setStretchAllColumns(true);
                    small_tb.setBackgroundResource(R.drawable.whiteline);
                    //設定TableRow的寬高
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    JArrayList = jb.getStringArrayList("JSON_data");
                    //設置每筆派工的派工單號、送貨客戶
                    LinearLayout dynamically_llt = new LinearLayout(Day_Work.this);
                    TextView dynamically_title2;
                    dynamically_title2 = new TextView(Day_Work.this);
                    dynamically_title2.setText(title_array[1].toString() + " : " + JArrayList.get(1));
                    dynamically_title2.setPadding(40, 10, 0, 10);
                    dynamically_title2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    dynamically_title2.setTextColor(Color.rgb(6, 102, 219));
                    dynamically_title2.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

                    dynamically_llt.addView(dynamically_title2);
                    TableRow tr1 = new TableRow(Day_Work.this);
                    tr1.addView(dynamically_llt);
                    //將動態新增TableRow合併 讓分隔線延伸
                    TableRow.LayoutParams the_param1;
                    the_param1 = (TableRow.LayoutParams) dynamically_llt.getLayoutParams();
                    the_param1.span = 2;
                    dynamically_llt.setLayoutParams(the_param1);

                    LinearLayout dynamically_llt2 = new LinearLayout(Day_Work.this);
                    TextView dynamically_title3;
                    dynamically_title3 = new TextView(Day_Work.this);
                    dynamically_title3.setText(title_array[2].toString() + " : " + JArrayList.get(2));
                    dynamically_title3.setPadding(40, 10, 0, 10);
                    dynamically_title3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    dynamically_title3.setTextColor(Color.rgb(6, 102, 219));
                    dynamically_title3.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

                    dynamically_llt2.addView(dynamically_title3);
                    TableRow tr2 = new TableRow(Day_Work.this);
                    tr2.addView(dynamically_llt2);
                    //將動態新增TableRow合併 讓分隔線延伸
                    TableRow.LayoutParams the_param2;
                    the_param2 = (TableRow.LayoutParams) dynamically_llt2.getLayoutParams();
                    the_param2.span = 2;
                    dynamically_llt2.setLayoutParams(the_param2);

                    for (int i = 0; i < jb.getStringArrayList("JSON_data").size(); i++) {
                        //顯示每筆TableLayout的Title
                        TextView dynamically_title;
                        dynamically_title = new TextView(Day_Work.this);
                        dynamically_title.setText(title_array[i].toString());
                        dynamically_title.setPadding(40, 10, 0, 10);
                        dynamically_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                        dynamically_title.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_title.setMaxWidth(350);
                        dynamically_title.setMaxHeight(150);

                        //顯示每筆TableLayout的SQL資料
                        TextView dynamically_txt;
                        dynamically_txt = new TextView(Day_Work.this);
                        dynamically_txt.setText(JArrayList.get(i));
                        dynamically_txt.setPadding(0, 10, 10, 10);
                        dynamically_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                        dynamically_txt.setTextColor(Color.rgb(6, 102, 219));
                        dynamically_txt.setMaxWidth(350);
                        dynamically_txt.setTextIsSelectable(true);

                        TableRow tr3 = new TableRow(Day_Work.this);
                        tr3.setGravity(Gravity.CENTER_HORIZONTAL);
                        tr3.addView(dynamically_title, layoutParams);
                        tr3.addView(dynamically_txt, layoutParams);

                        small_tb.addView(tr3, layoutParams);
                        //隱藏不需要的SQL資料
                        if (i > 18 || 7 < i && i < 16 || 0 < i && i < 3) {
                            small_tb.getChildAt(i).setVisibility(View.GONE);
                        }

                    }
                    /*//設置每筆TableLayout的Button監聽器、與動態新增Button的ID
                    int loc = 0;
                    for (int a = 0; a < dynamically_btn.length; a++) {
                        if (dynamically_btn[a] == null) {
                            loc = a;
                            break;
                        }
                    }
                    dynamically_btn[loc] = new Button(Day_Work.this);
                    dynamically_btn[loc].setBackgroundResource(R.drawable.button);
                    dynamically_btn[loc].setText("回報");
                    dynamically_btn[loc].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    dynamically_btn[loc].setTextColor(Color.rgb(6, 102, 219));
                    dynamically_btn[loc].setId(loc);
                    dynamically_btn[loc].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int a = 0; a < dynamically_btn.length; a++) {
                                if (v.getId() == dynamically_btn[a].getId()) {
                                    Intent intent_maintain = new Intent(Day_Work.this, MaintainActivity.class);
                                    //取得大TableLayout中的大TableRow位置
                                    TableRow tb_tbr = (TableRow) date_work_TableLayout.getChildAt(a);
                                    //取得大TableRow中的小TableLayout位置
                                    TableLayout tb_tbr_tb = (TableLayout) tb_tbr.getChildAt(0);
                                    //派工資料的迴圈
                                    for (int x = 0; x < 22; x++) {
                                        //取得小TableLayout中的小TableRow位置
                                        TableRow tb_tbr_tb_tbr = (TableRow) tb_tbr_tb.getChildAt(x);
                                        //小TableRow中的layout_column位置
                                        TextView SecondTextView = (TextView) tb_tbr_tb_tbr.getChildAt(1);
                                        String ResponseText = SecondTextView.getText().toString();
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

                    TableRow tr4 = new TableRow(Day_Work.this);
                    tr4.addView(dynamically_btn[loc]);
                    //將動態新增TableRow合併 讓Button置中
                    TableRow.LayoutParams the_param;
                    the_param = (TableRow.LayoutParams) dynamically_btn[loc].getLayoutParams();
                    the_param.span = 2;
                    dynamically_btn[loc].setLayoutParams(the_param);
                    small_tb.addView(tr4);*/
                    big_tbr.addView(small_tb);

                    TableRow.LayoutParams the_param3;
                    the_param3 = (TableRow.LayoutParams) small_tb.getLayoutParams();
                    the_param3.span = 2;
                    the_param3.width = TableRow.LayoutParams.MATCH_PARENT;
                    small_tb.setLayoutParams(the_param3);

                    date_work_TableLayout.addView(tr1);
                    date_work_TableLayout.addView(tr2);
                    date_work_TableLayout.addView(big_tbr);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

}