package com.example.a10609516.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MenuActivity extends BackKeyActivity {

    //創建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;  //顯示HOME
            case R.id.schedule_item:
                Intent intent7 = new Intent(MenuActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent = new Intent(MenuActivity.this, CalendarActivity.class);
                startActivity(intent);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent1 = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            case R.id.pending_item:
                Intent intent2 = new Intent(MenuActivity.this, PendingActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "待處理派工", Toast.LENGTH_SHORT).show();
                break; //進入待處理派工頁面
            case R.id.record_item:
                Intent intent8 = new Intent(MenuActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent3 = new Intent(MenuActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(MenuActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            case R.id.upload_item:
                Intent intent5 = new Intent(MenuActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(MenuActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            default:
        }
        return true;
    }

    private ListView listview1;
    private String[] show_text = {"Test1", "Test2", "Test3", "Test4", "Test5"};
    private ArrayAdapter listAdapter;

    TextView name_textView;
    public static final String Userid = "";
    //String HttpURL = "http://192.168.172/Test1/MenuUserName.php";
    //String finalResult;
    //HttpParse httpParse = new HttpParse();
    //HashMap<String, String> hashMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //MenuFunction("");
        sendRequestWithOkHttp();

        name_textView = (TextView)findViewById(R.id.name_textView);
        listview1= (ListView) findViewById(R.id.announcement_listView);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,show_text);
        listview1.setAdapter(listAdapter);

        //ListView監聽器
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "點選的是"+show_text[position], //postition是指點選到的index
                        Toast.LENGTH_SHORT).show();
                /*listview1.setVisibility(view.INVISIBLE); *///隱藏ListView
            } //end onItemClick
        }); //end setOnItemClickListener

        //公告區的各部門下拉選單
        Spinner spinner = (Spinner)findViewById(R.id.announcement_spinner);
        final String[] announcement = {"--- 全 部 分 類 ---", "--- 內 部 公 告 區 ---", "--- 管 理 部 ---", "--- 財 會 部 ---",
                "--- 水 資 部 ---", "--- 管 財 部 ---", "--- 設 計/經 銷 部 ---", "--- 電 商 部 ---", "--- 技 術 部 ---",
                "--- 行 銷 部 ---", "--- 建 設 部 ---", "--- D I Y 部 ---", "--- 百 貨 部 ---", "--- 客 服 工 程 師 ---"};
        ArrayAdapter<String> announcementList = new ArrayAdapter<>(MenuActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                announcement);
        listAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(announcementList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MenuActivity.this, "你選的是" + announcement[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //與資料庫連線 藉由登入輸入的員工ID取得員工姓名
    private void sendRequestWithOkHttp(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                Intent intent = getIntent();
                String data = intent.getStringExtra(Userid);
                Log.d("MenuActivity",data);

                try{
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    /*RequestBody requestBody = new FormBody.Builder()
                            .add("User_id",data)
                            .build();*/ //POST
                    //GET
                    Request request = new Request.Builder()
                            .url("http://192.168.0.172/Test1/MenuUserName.php?User_id="+data)
                            //.post(requestBody)
                            .build(); //GET
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("MenuActivity",responseData);
                    showResponse(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //在TextView上SHOW出回傳值
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name_textView.setText(response);
            }
        });
    }

    /*public void MenuFunction(final String User_name) {

        class Menu extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                name_textView.setText(httpResponseMsg);

                Toast.makeText(MenuActivity.this, httpResponseMsg, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("User_name", params[0]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;

            }

        }

        Menu MenuClass = new Menu();

        MenuClass.execute(User_name);
    }*/


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MenuActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MenuActivity", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MenuActivity", "omRestart");
    }
}

