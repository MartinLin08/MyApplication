package com.example.a10609516.myapplication.Basic;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.myapplication.Clerk.QuotationActivity;
import com.example.a10609516.myapplication.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.myapplication.Element.HttpParse;
import com.example.a10609516.myapplication.R;
import com.example.a10609516.myapplication.Workers.ScheduleActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout login_llt;
    private EditText accountEdit, passwordEdit;
    private Button login;
    private CheckBox remember_checkBox;
    private TextView version_no_txt, department_txt;
    public int badgeCount;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    String ver_no;
    String IDEdT, PwdEdT;
    String finalResult;
    //String HttpURL = "http://220.133.80.146/WQP/UserLogin.php";
    String HttpURL = "http://192.168.0.172/WQP/UserLogin.php";
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
        //請求開啟權限
        UsesPermission();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        login_llt = (LinearLayout) findViewById(R.id.login_llt);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginBtn);
        remember_checkBox = (CheckBox) findViewById(R.id.remember_checkBox);
        version_no_txt = (TextView) findViewById(R.id.version_no_txt);
        department_txt = (TextView) findViewById(R.id.department_txt);
        //Button.setOnClickListener監聽器
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                //取得TokenID的OKHttp
                sendRequestWithOkHttpOfTokenID();
                //取得版本號的OKHttp
                sendRequestWithOkHttpOfVersion();
                //判斷部門別的OKHttp
                sendRequestWithOkHttpOfDepartment();
                //取得未回派工數量
                sendRequestWithOkHttpOfMissCount();

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
    }

    /**
     * 記住帳密功能
     */
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

    /**
     * 確認accountEdit、passwordEdit是否為空值
     */
    public void CheckEditTextIsEmptyOrNot() {
        IDEdT = accountEdit.getText().toString();
        PwdEdT = passwordEdit.getText().toString();
        if (TextUtils.isEmpty(IDEdT) || TextUtils.isEmpty(PwdEdT)) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }

    /**
     * AsyncTask非同步任務
     */
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
                if (version_no_txt.getText().toString().equals(ver_no)) {
                    if (httpResponseMsg.equalsIgnoreCase("登入成功")) {
                        if (department_txt.getText().toString().equals("8888")) {
                            Toast.makeText(LoginActivity.this, "無使用權限", Toast.LENGTH_SHORT).show();
                        } else {
                            finish();
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            intent.putExtra(Userid, User_id);
                            Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    new AlertDialog.Builder(LoginActivity.this)
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
                                                    LoginActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();

                    /**
                     *動態跑出安裝APK網址
                     */
                    /*TextView Hyperlink_txt = new TextView(LoginActivity.this);
                    Hyperlink_txt.setText("http://m.wqp-water.com.tw/APP");
                    Hyperlink_txt.setAutoLinkMask(Linkify.WEB_URLS);
                    Hyperlink_txt.setMovementMethod(LinkMovementMethod.getInstance());
                    Hyperlink_txt.setGravity(Gravity.CENTER);
                    Hyperlink_txt.setPadding(0,10,0,3);
                    Hyperlink_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    login_llt.addView(Hyperlink_txt);*/
                }
            }

            //執行中，在背景做任務
            @Override
            protected String doInBackground(String... params) {
                hashMap.put("User_id", params[0]);
                hashMap.put("User_password", params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                Log.e("LoginActivity", params[0]);
                Log.e("LoginActivity", params[1]);
                Log.e("LoginActivity", finalResult);
                return finalResult;
            }
        }
        Login userLoginClass = new Login();
        userLoginClass.execute(User_id, User_password);
    }

    /**
     * 與OkHttp建立連線(TokenID)
     */
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
                            //.url("http://220.133.80.146/WQP/TokenID.php")
                            .url("http://192.168.0.172/WQP/TokenID.php")
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

    /**
     * 與OkHttp建立連線(版本)
     */
    private void sendRequestWithOkHttpOfVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("version", "wqp-water")
                            .build();
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/Version.php")
                            .url("http://192.168.0.172/WQP/Version.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("LoginActivity", responseData);
                    parseJSONWithJSONObjectOfVersion(responseData);
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
    private void parseJSONWithJSONObjectOfVersion(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ver_no = jsonObject.getString("版本");
                Log.e("LoginActivity", ver_no);

                SharedPreferences sharedPreferences = getSharedPreferences("fb_version", MODE_PRIVATE);
                sharedPreferences.edit().putString("FB_VER", version_no_txt.getText().toString()).apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 請求開啟儲存、相機權限
     */
    private void UsesPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("我真的沒有要做壞事, 給我權限吧?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                                                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * 與OkHttp建立連線(UserLogin判斷部門別)
     */
    private void sendRequestWithOkHttpOfDepartment() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", IDEdT)
                            .build();
                    Log.e("LoginActivity", IDEdT);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/DepartmentID.php")
                            .url("http://192.168.0.172/WQP/DepartmentID.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("LoginActivity", responseData);
                    showResponse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 在TextView上SHOW出回傳的所屬部門
     *
     * @param response
     */
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                department_txt.setText(response);

                SharedPreferences sharedPreferences = getSharedPreferences("department_id", MODE_PRIVATE);
                sharedPreferences.edit().putString("D_ID", department_txt.getText().toString()).apply();
            }
        });
    }

    /**
     * 確認是否有最新版本，進行更新
     */
    private void CheckFirebaseVersion() {
        SharedPreferences fb_version = getSharedPreferences("fb_version", MODE_PRIVATE);
        final String version = fb_version.getString("FB_VER", "");
        Log.e("LoginActivity", version);

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
                Log.e("LoginActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(LoginActivity.this)
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
                                                    LoginActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("LoginActivity", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 下載新版本APK
     */
    public void Update() {
        try {
            URL url = new URL("http://192.168.0.201/wqp_1.4.apk");
            //URL url = new URL("http://m.wqp-water.com.tw/wqp_1.4.apk");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            //c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();

            //String PATH = Environment.getExternalStorageDirectory() + "/download/";
            String PATH = Environment.getExternalStorageDirectory().getPath() + "/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "wqp_1.4.apk");
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
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "wqp_1.4.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            LoginActivity.this.runOnUiThread(new Runnable() {
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
    private void sendRequestWithOkHttpOfMissCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("User_id", IDEdT)
                            .build();
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/MissWorkCount.php")
                            .url("http://192.168.0.172/WQP/MissWorkCount.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("LoginActivity", responseData);
                    parseJSONWithJSONObjectOfMissCount(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 取得未回出勤的數量
     * @param miss_count
     */
    private void parseJSONWithJSONObjectOfMissCount(final String miss_count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("LoginActivity", miss_count);
                if(miss_count.toString().equals("0")){
                    badgeCount = 0;
                    ShortcutBadger.removeCount(LoginActivity.this);
                }else{
                    int count = Integer.valueOf(miss_count);
                    ShortcutBadger.applyCount(LoginActivity.this, count);
                }
            }
        });
    }
}