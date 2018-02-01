package com.example.a10609516.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox remember_checkBox;

    String IDEdT, PwdEdT;
    String finalResult;
    //String HttpURL = "http://220.133.80.146/wqp/UserLogin.php";
    String HttpURL = "http://192.168.0.172/Test1/UserLogin.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String Userid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //動態取得 View 物件
        InItFunction();
        //記住帳密功能
        SharedPreferencesWithLogin();
        //Button.setOnClickListener監聽器
        login.setOnClickListener(login_btnListener);


    }

    //動態取得 View 物件
    private void InItFunction() {

        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginBtn);
        remember_checkBox = (CheckBox) findViewById(R.id.remember_checkBox);

    }

    //Button.setOnClickListener監聽器
    private Button.OnClickListener login_btnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            CheckEditTextIsEmptyOrNot();

            sendRequestWithOkHttpOfTokenID();

            if (CheckEditText) {

                UserLoginFunction(IDEdT, PwdEdT);

            } else {

                Toast.makeText(LoginActivity.this, "請輸入員工ID及密碼", Toast.LENGTH_LONG).show();

            }

            if (remember_checkBox.isChecked()) { //檢測使用者帳號密碼
                SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
                SharedPreferences.Editor edit = remdname.edit();
                edit.putString("account", accountEdit.getText().toString());
                edit.putString("password", passwordEdit.getText().toString());
                edit.commit();
            }

            SharedPreferences sharedPreferences = getSharedPreferences("user_id_data", MODE_PRIVATE);
            sharedPreferences.edit().putString("ID", accountEdit.getText().toString()).apply();

        }
    };//end setOnItemClickListener


    //記住帳密功能
    private void SharedPreferencesWithLogin() {

        SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
        //SharedPreferences將account 和 password 記錄起來 每次進去APP時 開始從中讀取資料 放入accountEdit，passwordEdit中
        String account_str = remdname.getString("account", "");
        String password_str = remdname.getString("password", "");
        accountEdit.setText(account_str);
        passwordEdit.setText(password_str);

        //如果remember_checkBox勾選，記住帳密   remember_checkBox不勾選，不記住帳密
        remember_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edit = remdname.edit();
                    edit.putString("account", accountEdit.getText().toString());
                    edit.putString("password", passwordEdit.getText().toString());
                    edit.commit();
                }
                if (!isChecked) {
                    SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edit = remdname.edit();
                    edit.putString("account", "");
                    edit.putString("password", "");
                    edit.commit();
                }
            }
        });
    }


    //確認accountEdit、passwordEdit是否為空值
    public void CheckEditTextIsEmptyOrNot() {

        IDEdT = accountEdit.getText().toString();
        PwdEdT = passwordEdit.getText().toString();

        if (TextUtils.isEmpty(IDEdT) || TextUtils.isEmpty(PwdEdT)) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }


    //AsyncTask非同步任務
    public void UserLoginFunction(final String User_id, final String User_password) {

        class Login extends AsyncTask<String, Void, String> {

            //執行前，一些基本設定可以在這邊做
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(LoginActivity.this, "Loading Data", null, true, true);

            }

            //執行後，最後的結果會在這邊
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                //Log.e("LoginActivity",httpResponseMsg);

                if (httpResponseMsg.equalsIgnoreCase("登入成功")) {

                    finish();

                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);

                    intent.putExtra(Userid, User_id);

                    Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();

                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                }

            }

            //執行中，在背景做任務
            @Override
            protected String doInBackground(String... params) {

                hashMap.put("User_id", params[0]);
                hashMap.put("User_password", params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);

                //Log.e("LoginActivity",params[0]);
                //Log.e("LoginActivity",params[1]);
                //Log.e("LoginActivity",finalResult);

                return finalResult;

            }

        }

        Login userLoginClass = new Login();

        userLoginClass.execute(User_id, User_password);
    }

    //與OkHttp建立連線
    private void sendRequestWithOkHttpOfTokenID() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("app_token_id", MODE_PRIVATE);
                String app_token_id = user_id.getString("token_id", "");
                Log.e("FCM", app_token_id);


                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", IDEdT)
                            .add("Token_ID", app_token_id)
                            .build();
                    Log.e("FCM", IDEdT);
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
    }

}




