package com.example.a10609516.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText accountEdit;
    EditText passwordEdit;
    Button login;
    private CheckBox remember_checkBox;

    String IDEdT, PwdEdT;
    String finalResult;
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

        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginBtn);
        remember_checkBox = (CheckBox) findViewById(R.id.remember_checkBox);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

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
        });//end setOnItemClickListener

        SharedPreferencesWithLogin();

    }

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

                return finalResult;

            }

        }

        Login userLoginClass = new Login();

        userLoginClass.execute(User_id, User_password);
    }

}

              /*String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //如果帳號是martin 密碼是123456 就登入成功
                if (account.equals("martin") && password.equals("123456")){
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent); //登入成功 進入首頁
                    Toast.makeText(LoginActivity.this, "登入成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    accountEdit.setText("");
                    passwordEdit.setText("");
                    Toast.makeText(LoginActivity.this, "帳號或密碼輸入錯誤",Toast.LENGTH_SHORT).show();
                    //登入失敗 再輸入一次帳號密碼
                }

                if (remember_checkBox.isChecked()){ //檢測使用者帳號密碼
                    SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edit = remdname.edit();
                    edit.putString("account",accountEdit.getText().toString());
                    edit.putString("passeord",passwordEdit.getText().toString());
                    edit.commit();
                }
            }
        });

        SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
        //SharedPreferences將account 和 password 記錄起來 每次進去APP時 開始從中讀取資料 放入accountEdit，passwordEdit中
        String account_str = remdname.getString("account", "");
        String password_str = remdname.getString("password", "");
        accountEdit.setText(account_str);
        passwordEdit.setText(password_str);

        remember_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edit = remdname.edit();
                    edit.putString("account",accountEdit.getText().toString());
                    edit.putString("password",passwordEdit.getText().toString());
                    edit.commit();
                }
                if(!isChecked){
                    SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edit = remdname.edit();
                    edit.putString("account","");
                    edit.putString("password","");
                    edit.commit();
                }
            }
        });*/


