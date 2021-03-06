package com.example.a10609516.myapplication.Workers;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.a10609516.myapplication.Tools.CirclePgBar;
import com.example.a10609516.myapplication.R;
import com.example.a10609516.myapplication.Tools.WQPServiceActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PointsActivity extends WQPServiceActivity {

    private CirclePgBar money_pgr, a_points_pgr, b_points_pgr, d_points_pgr, ab_points_pgr;

    private TextView local_txt, id_txt, user_txt;
    private String a_points, b_points, d_points, ab_points, money;

    //private int a_points_count, b_points_count, d_points_count, ab_points_count, money_count;
    private Float a_points_count, b_points_count, d_points_count, ab_points_count, money_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        //動態取得 View 物件
        InItFunction();
        //與OKHttp連線(UserName.php)
        sendRequestWithOkHttpForUserName();
        //與OKHttp連線(UserLocal.php)
        sendRequestWithOkHttpForUserLocal();
        //與OKHttp連線(WorkAllPoints.php)
        sendRequestWithOkHttpForWorkAllPoints();
        //確認是否有最新版本，進行更新
        //CheckFirebaseVersion();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        local_txt = (TextView) findViewById(R.id.local_txt);
        id_txt = (TextView) findViewById(R.id.id_txt);
        user_txt = (TextView) findViewById(R.id.user_txt);
        money_pgr = (CirclePgBar) findViewById(R.id.money_pgr);
        a_points_pgr = (CirclePgBar) findViewById(R.id.a_points_pgr);
        b_points_pgr = (CirclePgBar) findViewById(R.id.b_points_pgr);
        d_points_pgr = (CirclePgBar) findViewById(R.id.d_points_pgr);
        ab_points_pgr = (CirclePgBar) findViewById(R.id.ab_points_pgr);

        //接收LoginActivity傳過來的值
        SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
        String user_id_data = user_id.getString("ID", "");
        Log.i("PointsActivity", user_id_data);
        id_txt.setText("員編 : " + user_id_data);
    }

    /**
     * 與資料庫連線 藉由登入輸入的員工ID取得員工姓名
     */
    private void sendRequestWithOkHttpForUserName() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/UserName.php")
                            .url("http://192.168.0.172/WQP/UserName.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("PointsActivity", responseData);
                    showResponseForUserName(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 在TextView上SHOW出回傳的員工姓名
     *
     * @param response
     */
    private void showResponseForUserName(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user_txt.setText("工務 : " + response);
            }
        });
    }

    /**
     * 與資料庫連線 藉由登入輸入的員工ID取得員工所在地區
     */
    private void sendRequestWithOkHttpForUserLocal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/UserLocal.php")
                            .url("http://192.168.0.172/WQP/UserLocal.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("PointsActivity", responseData);
                    showResponseForUserLocal(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 在TextView上SHOW出回傳的員工所在區域
     *
     * @param response
     */
    private void showResponseForUserLocal(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                local_txt.setText("地區別 : " + response.substring(0,2));
            }
        });
    }

    /**
     * 與OkHttp建立連線 取得工務點數
     */
    private void sendRequestWithOkHttpForWorkAllPoints() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .build();
                    Log.e("PointsActivity", user_id_data);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/WorkAllPoints.php")
                            .url("http://192.168.0.172/WQP/WorkAllPoints.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PointsActivity1", responseData);
                    parseJSONWithJSONObjectForWorkAllPoints(responseData);
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
    private void parseJSONWithJSONObjectForWorkAllPoints(String jsonData) {
        Log.e("ENTER", "YES");
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                a_points = jsonObject.getString("A點數");
                b_points = jsonObject.getString("B點數");
                d_points = jsonObject.getString("D點數");
                ab_points = jsonObject.getString("AB點數");
                money = jsonObject.getString("分配金額");

                Log.e("PointsActivity", a_points);
                Log.e("PointsActivity", b_points);
                Log.e("PointsActivity", d_points);
                Log.e("PointsActivity", ab_points);
                Log.e("PointsActivity", money);

                a_points_count = Float.parseFloat(a_points);
                b_points_count = Float.parseFloat(b_points);
                d_points_count = Float.parseFloat(d_points.trim());
                ab_points_count = Float.parseFloat(ab_points);
                money_count = Float.parseFloat(money.trim());

                money_pgr.setmCirclePgBar(0, money_count, 6000, Color.rgb(244, 164, 96));
                money_pgr.invalidate();
                a_points_pgr.setmCirclePgBar(0, a_points_count, 5000, Color.rgb(227, 38, 54));
                a_points_pgr.invalidate();
                b_points_pgr.setmCirclePgBar(0, b_points_count, 5000, Color.rgb(115, 230, 140));
                b_points_pgr.invalidate();
                d_points_pgr.setmCirclePgBar(0, d_points_count, 200, Color.rgb(30, 144, 255));
                d_points_pgr.invalidate();
                ab_points_pgr.setmCirclePgBar(0, ab_points_count, 8000, Color.rgb(255, 255, 0));
                ab_points_pgr.invalidate();
                Log.e("OVER", "YES");
            }
        } catch (Exception e) {
            Log.e("CATCH", "YES");
            e.printStackTrace();
        }
    }
}
