package com.example.a10609516.myapplication;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("FCM", "Token:"+token);

        sendRegistrationToServer(token);

        SendTokenID();

        //sendRequestWithOkHttp();
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    //傳遞TokenID到LoginActivity做儲存
    private void SendTokenID(){
        String token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedPreferences = getSharedPreferences("app_token_id", MODE_PRIVATE);
        sharedPreferences.edit().putString("token_id", token).apply();
    }

    /*//與OkHttp建立連線
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("FCM", user_id_data);

                String token = FirebaseInstanceId.getInstance().getToken();

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", user_id_data)
                            .add("Token_ID", token)
                            .build();
                    Log.e("FCM", token);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/wqp/TokenID.php")
                            .url("http://192.168.0.172/Test1/TokenID.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("FCM", responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/

}

