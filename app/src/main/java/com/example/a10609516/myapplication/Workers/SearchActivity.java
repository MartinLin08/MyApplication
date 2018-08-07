package com.example.a10609516.myapplication.Workers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.os.Handler;
import android.os.Message;

import com.example.a10609516.myapplication.Basic.QRCodeActivity;
import com.example.a10609516.myapplication.Basic.SignatureActivity;
import com.example.a10609516.myapplication.Clerk.QuotationActivity;
import com.example.a10609516.myapplication.DepartmentAndDIY.CorrectActivity;
import com.example.a10609516.myapplication.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.myapplication.Tools.DatePickerFragment;
import com.example.a10609516.myapplication.Basic.MenuActivity;
import com.example.a10609516.myapplication.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.myapplication.R;
import com.example.a10609516.myapplication.DepartmentAndDIY.RecordActivity;
import com.example.a10609516.myapplication.DepartmentAndDIY.UploadActivity;
import com.example.a10609516.myapplication.Basic.VersionActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private Button search_button, time_start_button, time_end_button, clean_start_button, clean_end_button;
    private Button[] dynamically_btn;
    private Spinner SelectList, ReturnsList;
    private EditText customer_editText;
    private TableLayout search_TableLayout;
    private LinearLayout search_LinearLayout, separate_linearLayout;
    private ScrollView search_scrollView;
    public int badgeCount;

    /**
     * 創建Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //接收LoginActivity傳過來的值
        SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
        String user_id_data = user_id.getString("ID", "");
        SharedPreferences department_id = getSharedPreferences("department_id", MODE_PRIVATE);
        String department_id_data = department_id.getString("D_ID", "");
        if ((user_id_data.toString().equals("9706013")) || user_id_data.toString().equals("9908023") || user_id_data.toString().equals("10010039")
                || user_id_data.toString().equals("10012043") || user_id_data.toString().equals("10101046") || user_id_data.toString().equals("10405235")) {
            getMenuInflater().inflate(R.menu.workers_manager_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("2100")) {
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
            return true;
        } else if (department_id_data.toString().equals("2200")) {
            getMenuInflater().inflate(R.menu.diy_menu, menu);
            return true;
        } else if (department_id_data.toString().equals("5200")) {
            getMenuInflater().inflate(R.menu.workers_menu, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
    }

    /**
     * 進入Menu各個頁面
     *
     * @param item
     * @return
     */
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
            case R.id.signature_item:
                Intent intent2 = new Intent(SearchActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
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
            case R.id.about_item:
                Intent intent9 = new Intent(SearchActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(SearchActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(SearchActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Intent intent12 = new Intent(SearchActivity.this, PointsActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            case R.id.miss_item:
                Intent intent14 = new Intent(SearchActivity.this, MissCountActivity.class);
                startActivity(intent14);
                Toast.makeText(this, "未回單數量", Toast.LENGTH_SHORT).show();
                break; //進入工務未回單數量頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //動態取得 View 物件
        InItFunction();
        //派工類別的Spinner下拉選單
        WorkTypeSpinner();
        //回報狀態的Spinner下拉選單
        ReturnsSpinner();
        //確認是否有最新版本，進行更新
        CheckFirebaseVersion();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        search_scrollView = (ScrollView) findViewById(R.id.search_scrollView);
        search_TableLayout = (TableLayout) findViewById(R.id.search_TableLayout);
        search_LinearLayout = (LinearLayout) findViewById(R.id.search_LinearLayout);
        separate_linearLayout = (LinearLayout) findViewById(R.id.separate_linearLayout);
        SelectList = (Spinner) findViewById(R.id.selectList);
        ReturnsList = (Spinner) findViewById(R.id.returnsList);
        time_start_button = (Button) findViewById(R.id.start2);
        time_end_button = (Button) findViewById(R.id.end2);
        clean_start_button = (Button) findViewById(R.id.clean_button1);
        clean_end_button = (Button) findViewById(R.id.clean_button2);
        customer_editText = (EditText) findViewById(R.id.customer_editText);
        search_button = (Button) findViewById(R.id.search_button);

        //Search_Button.setOnClickListener監聽器  //傳遞JSON字串與動態新增TableLayout
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讓search_tablelayout資料清空
                search_TableLayout.removeAllViews();
                //按search_button顯示物件search_linearlayout、search_tablelayout
                separate_linearLayout.setVisibility(View.VISIBLE);
                search_LinearLayout.setVisibility(View.VISIBLE);
                search_TableLayout.setVisibility(View.VISIBLE);
                //建立SearchData.php OKHttp連線
                sendRequestWithOkHttp();
                //取得未回派工數量
                sendRequestWithOkHttpForMissCount();
            }
        });//end setOnItemClickListener
        //Clean_Start_Button.setOnClickListener監聽器  //清空time_start_button內的文字
        clean_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_start_button.setText("");
            }
        });//end setOnItemClickListener
        //Clean_End_Button.setOnClickListener監聽器  //清空time_end_button內的文字
        clean_end_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_end_button.setText("");
            }
        });//end setOnItemClickListener
    }

    /**
     * 與OkHttp建立連線
     */
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.i("SearchActivity", user_id_data);
                //獲取日期挑選器的數據
                String time_start = time_start_button.getText().toString();
                String time_end = time_end_button.getText().toString();
                String customer_edt = customer_editText.getText().toString();
                String type_select = String.valueOf(SelectList.getSelectedItem());
                String returns_select = String.valueOf(ReturnsList.getSelectedItem());
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("ESVD_BEGIN_DATE1", time_start)
                            .add("ESVD_BEGIN_DATE2", time_end)
                            .add("ESV_NOTE", customer_edt)
                            .add("WORK_TYPE_NAME", type_select)
                            .add("RETURNS", returns_select)
                            .build();
                    Log.i("SearchActivity", time_start);
                    Log.i("SearchActivity", time_end);
                    Log.i("SearchActivity", returns_select);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/SearchData.php")
                            .url("http://192.168.0.172/WQP/SearchData.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("SearchActivity", responseData);
                    parseJSONWithJSONObject(responseData);
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
    private void parseJSONWithJSONObject(String jsonData) {
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
                String esvd_seq_id = jsonObject.getString("派工資料識別碼");
                String esvd_eng_points = jsonObject.getString("工務點數");
                String esvd_booking_period = jsonObject.getString("預約開始時間");
                String esvd_booking_period_end = jsonObject.getString("預約結束時間");
                String my_ontype = jsonObject.getString("狀態");

                Log.e("SearchActivity", esvd_seq_id);

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
                JArrayList.add(esvd_booking_period);
                JArrayList.add(esvd_booking_period_end);
                JArrayList.add(my_ontype);

                //HandlerMessage更新UI
                Bundle b = new Bundle();
                b.putStringArrayList("JSON_data", JArrayList);
                Message msg = mHandler.obtainMessage();
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
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final String[] title_array = {"派工類別", "派工單號", "送貨客戶", "預約日期時段", "聯絡人",
                    "主要電話", "次要電話", "派工地址", "付款方式", "是否要收款",
                    "應收款金額", "是否已收款", "已收款金額", "抵達日期", "抵達時間",
                    "結束時間", "任務說明", "料品說明", "工作說明", "派工資料識別碼",
                    "工務點數", "預約開始時間", "預約結束時間", "狀態"};
            switch (msg.what) {
                case 1:
                    //最外層有一個大的TableLayout,再設置TableRow包住小的TableLayout
                    //平均分配列的寬度
                    search_TableLayout.setStretchAllColumns(true);

                    //設置大TableLayout的TableRow
                    TableRow big_tbr = new TableRow(SearchActivity.this);
                    //設置每筆資料的小TableLayout
                    TableLayout small_tb = new TableLayout(SearchActivity.this);
                    //全部列自動填充空白處
                    small_tb.setStretchAllColumns(true);
                    small_tb.setBackgroundResource(R.drawable.titleline);
                    //設定TableRow的寬高
                    //TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    //int i = b.getStringArrayList("JSON_data").size();
                    JArrayList = jb.getStringArrayList("JSON_data");

                    for (int i = 0; i < jb.getStringArrayList("JSON_data").size(); i++) {
                        //顯示每筆TableLayout的Title
                        TextView dynamically_title;
                        dynamically_title = new TextView(SearchActivity.this);
                        dynamically_title.setText(title_array[i].toString());
                        //dynamically_title.setGravity(Gravity.CENTER);
                        //dynamically_title.setWidth(50);
                        dynamically_title.setPadding(10, 0, 0, 0);
                        dynamically_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                        dynamically_title.setMaxWidth(350);

                        //顯示每筆TableLayout的SQL資料
                        TextView dynamically_txt;
                        dynamically_txt = new TextView(SearchActivity.this);
                        dynamically_txt.setText(JArrayList.get(i));
                        dynamically_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                        dynamically_txt.setMaxWidth(350);
                        dynamically_txt.setTextIsSelectable(true);

                        TableRow tr1 = new TableRow(SearchActivity.this);
                        tr1.setWeightSum(5);
                        tr1.addView(dynamically_title, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
                        tr1.addView(dynamically_txt, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 4));
                        //search_TableLayout.addView(tr1);
                        small_tb.addView(tr1);
                        //如果日期為0000-00-00,則把該TextView改為空值
                        if (dynamically_txt.getText().toString().equals("1900-01-01")) {
                            dynamically_txt.setText("");
                        }
                    }
                    small_tb.getChildAt(19).setVisibility(View.GONE);
                    small_tb.getChildAt(20).setVisibility(View.GONE);
                    small_tb.getChildAt(21).setVisibility(View.GONE);
                    small_tb.getChildAt(22).setVisibility(View.GONE);
                    small_tb.getChildAt(23).setVisibility(View.GONE);

                    //如果My_OnType是0，隱藏抵達時間與離開時間
                    TableRow mini_tbr = (TableRow) small_tb.getChildAt(23);
                    TextView my_type = (TextView) mini_tbr.getChildAt(1);
                    if(my_type.getText().toString().equals("0")){
                        TableRow begin_tbr = (TableRow) small_tb.getChildAt(14);
                        TextView begin = (TextView) begin_tbr.getChildAt(1);
                        begin.setVisibility(View.GONE);
                        TableRow end_tbr = (TableRow) small_tb.getChildAt(15);
                        TextView end = (TextView) end_tbr.getChildAt(1);
                        end.setVisibility(View.GONE);
                    }

                    //設置每筆TableLayout的Button監聽器、與動態新增Button的ID
                    int loc = 0;
                    for (int i = 0; i < dynamically_btn.length; i++) {
                        if (dynamically_btn[i] == null) {
                            loc = i;
                            break;
                        }
                    }
                    dynamically_btn[loc] = new Button(SearchActivity.this);
                    dynamically_btn[loc].setBackgroundResource(R.drawable.button);
                    dynamically_btn[loc].setText("回報");
                    dynamically_btn[loc].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    dynamically_btn[loc].setTextColor(Color.rgb(6, 102, 219));
                    dynamically_btn[loc].setId(loc);
                    dynamically_btn[loc].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int a = 0; a < dynamically_btn.length; a++) {
                                if (v.getId() == dynamically_btn[a].getId()) {
                                    Intent intent_maintain = new Intent(SearchActivity.this, MaintainActivity.class);
                                    //取得大TableLayout中的大TableRow位置
                                    TableRow tb_tbr = (TableRow) search_TableLayout.getChildAt(a);
                                    //取得大TableRow中的小TableLayout位置
                                    TableLayout tb_tbr_tb = (TableLayout) tb_tbr.getChildAt(0);
                                    //派工資料的迴圈
                                    for (int x = 0; x < 24; x++) {
                                        //取得小TableLayout中的小TableRow位置
                                        TableRow tb_tbr_tb_tbr = (TableRow) tb_tbr_tb.getChildAt(x);
                                        //小TableRow中的layout_column位置
                                        TextView SecondTextView = (TextView) tb_tbr_tb_tbr.getChildAt(1);
                                        String ResponseText = SecondTextView.getText().toString();
                                        //將SQL裡的資料傳到MaintainActivity
                                        Bundle bundle = new Bundle();
                                        bundle.putString("ResponseText" + x, ResponseText);

                                        //intent_maintain.putExtra("TitleText" + x, TitleText);//可放所有基本類別
                                        intent_maintain.putExtras(bundle);//可放所有基本類別
                                    }
                                    startActivity(intent_maintain);
                                    //進入MaintainActivity後 清空search_TableLayout的資料
                                    search_TableLayout.removeAllViews();
                                    separate_linearLayout.setVisibility(View.GONE);
                                }
                            }
                        }
                    });

                    TableRow tr2 = new TableRow(SearchActivity.this);
                    tr2.addView(dynamically_btn[loc]);
                    //將動態新增TableRow合併 讓Button置中
                    TableRow.LayoutParams the_param;
                    the_param = (TableRow.LayoutParams) dynamically_btn[loc].getLayoutParams();
                    the_param.span = 2;
                    dynamically_btn[loc].setLayoutParams(the_param);
                    small_tb.addView(tr2);

                    //設置每筆TableLayout的分隔線
                    LinearLayout dynamically_llt = new LinearLayout(SearchActivity.this);
                    dynamically_llt.setBackgroundResource(R.drawable.table_h_divider);
                    TableRow tr3 = new TableRow(SearchActivity.this);
                    tr3.addView(dynamically_llt);
                    //將動態新增TableRow合併 讓分隔線延伸
                    TableRow.LayoutParams the_param2;
                    the_param2 = (TableRow.LayoutParams) dynamically_llt.getLayoutParams();
                    the_param2.span = 2;
                    dynamically_llt.setLayoutParams(the_param2);
                    //刪去最後一筆TableLayout的分隔線
                    if ((dynamically_btn.length - 1) != loc) {
                        small_tb.addView(tr3);
                    }
                    big_tbr.addView(small_tb);

                    TableRow.LayoutParams the_param3;
                    the_param3 = (TableRow.LayoutParams) small_tb.getLayoutParams();
                    the_param3.span = 2;
                    the_param3.width = TableRow.LayoutParams.MATCH_PARENT;
                    small_tb.setLayoutParams(the_param3);

                    search_TableLayout.addView(big_tbr);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 日期挑選器
     *
     * @param v
     */
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

    /**
     * 派工類別的Spinner下拉選單
     */
    private void WorkTypeSpinner() {
        //Spinner下拉選單
        final String[] select = {"選擇", "臨時取消", "換濾心", "換濾料", "加鹽", "末端", "小前置", "全屋", "社區保養", "檢修(一)", "檢修(二)"};
        ArrayAdapter<String> selectList = new ArrayAdapter<>(SearchActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                select);
        SelectList.setAdapter(selectList);
    }

    /**
     * 回報狀態的Spinner下拉選單
     */
    private void ReturnsSpinner() {
        //Spinner下拉選單
        final String[] returns = {"未回報", "已回報", "未結案", "已結案", "全部"};
        ArrayAdapter<String> returnsList = new ArrayAdapter<>(SearchActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                returns);
        ReturnsList.setAdapter(returnsList);
    }

    /**
     * 實現畫面置頂按鈕
     *
     * @param view
     */
    public void GoTopBtn(View view) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                //實現畫面置頂按鈕
                search_scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    /**
     * 確認是否有最新版本，進行更新
     */
    private void CheckFirebaseVersion() {
        SharedPreferences fb_version = getSharedPreferences("fb_version", MODE_PRIVATE);
        final String version = fb_version.getString("FB_VER", "");
        Log.e("SearchActivity", version);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("WQP");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d("現在在根結點上的資料是:", "Value is: " + value);
                Map<String, String> map = (Map) dataSnapshot.getValue();
                String data = map.toString().substring(9, 12);
                Log.e("SearchActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(SearchActivity.this)
                            .setTitle("更新通知")
                            .setMessage("檢測到軟體重大更新\n請更新最新版本")
                            .setIcon(R.drawable.bwt_icon)
                            .setNegativeButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            new Thread() {
                                                @Override
                                                public void run() {
                                                    super.run();
                                                    SearchActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("SearchActivity", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 下載新版本APK
     */
    public void Update() {
        try {
            URL url = new URL("http://192.168.0.201/wqp_1.7.apk");
            //URL url = new URL("http://m.wqp-water.com.tw/wqp_1.7.apk");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            //c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalStorageDirectory() + "/Download/";
            //String PATH2 = Environment.getExternalStorageDirectory().getPath() + "/Download/";
            //String PATH = System.getenv("SECONDARY_STORAGE") + "/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "wqp_1.7.apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();//till here, it works fine - .apk is download to my sdcard in download file

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "wqp_1.7.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            SearchActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            SearchActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "更新失敗!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * 與OkHttp建立連線(未回派工數量)
     */
    private void sendRequestWithOkHttpForMissCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.i("SearchActivity", user_id_data);
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/MissWorkCount.php")
                            .url("http://192.168.0.172/WQP/MissWorkCount.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("SearchActivity", responseData);
                    parseJSONWithJSONObjectForMissCount(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 取得未回出勤的數量
     *
     * @param miss_count
     */
    private void parseJSONWithJSONObjectForMissCount(final String miss_count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("SearchActivity", miss_count);
                if (miss_count.toString().equals("0")) {
                    badgeCount = 0;
                    ShortcutBadger.removeCount(SearchActivity.this);
                } else {
                    int count = Integer.valueOf(miss_count);
                    ShortcutBadger.applyCount(SearchActivity.this, count);
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SearchActivity", "onRestart");
        //取得未回派工數量
        sendRequestWithOkHttpForMissCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SearchActivity", "onDestroy");
    }
}
