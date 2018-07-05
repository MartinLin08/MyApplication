package com.example.a10609516.myapplication.DepartmentAndDIY;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a10609516.myapplication.Basic.MenuActivity;
import com.example.a10609516.myapplication.Clerk.QuotationActivity;
import com.example.a10609516.myapplication.Basic.SignatureActivity;
import com.example.a10609516.myapplication.R;
import com.example.a10609516.myapplication.Basic.VersionActivity;
import com.example.a10609516.myapplication.Workers.CalendarActivity;
import com.example.a10609516.myapplication.Basic.QRCodeActivity;
import com.example.a10609516.myapplication.Workers.PointsActivity;
import com.example.a10609516.myapplication.Workers.ScheduleActivity;
import com.example.a10609516.myapplication.Workers.SearchActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PictureActivity extends AppCompatActivity {

    private Button upload_btn, camera_btn, save_btn;
    private EditText customer_edt, phone_edt, address_edt, note_edt;
    private ImageView upload_imageView;
                 //第一個下拉選單 //第二個下拉選單
    private Spinner type_spinner, store_spinner;
    private Context context;
    private Uri imageUri, uri;
    private Bitmap bmp;
    private String[] type = new String[]{"請選擇", "百貨通路", "特力屋", "普來利"};
    private String[] empty = new String[]{""};
    private String[] diy = new String[]{};
    /*private String[][] store = new String[][]{{""},
            {"台北新光信義A9 (02)2723-9721", "新北環球中和 (02)7731-6890", "台北美麗華 櫃位無電話",
                    "忠孝SOGO (02)7711-5151", "台北新光站前 (02)2371-9306", "中壢SOGO",
                    "新竹SOGO (03)526-2828", "新竹遠百 (03)523-8453", "新竹新光 (03)515-1056",
                    "台中中友百貨 (04)2229-3568", "台中大遠百 (04)2254-8583", "台中廣三 (04)2328-2630",
                    "台中新光", "嘉義遠百 (05) 283-5705", "台南新天地 (06)303-0045",
                    "南紡夢時代 (07)973-3888", "台南遠東", "高雄新光三多店 (07)336-6100",
                    "高雄夢時代 (07)973-3888", "高雄漢神巨蛋 (07)555-9696", "大江購物中心",
                    "高雄 新光左營店 (07)346-9930", "高雄大遠百(07)537-3364", "高雄SOGO (07)335-8076",
                    "集雅社", "嘉義耐斯百貨 (05)276-7888", "台北天母SOGO (02)2834-5000",
                    "高雄成功漢神百貨 (07)226-3855", "三多旗艦門市 (07)725-6606"},
            {"特力屋桃園南崁店 (03)321-1000", "特力屋台北新莊店 (02)2906-1212", "特力屋高雄大順店 (07)225-9111",
                    "特力屋台中復興店 (04)2262-0300", "特力屋嘉義店 (05)271-6568", "特力屋台南文賢店 (06)358-5656",
                    "特力屋台北內湖店 (02)8791-8896", "特力屋桃園平鎮店 (03)428-7111", "特力屋台北士林店 (02)2889-1000",
                    "特力屋彰化彰化店 (04)736-9999", "特力屋台中北屯店 (04)2247-1000", "特力屋高雄鳳山店 (07)766-9000",
                    "特力屋台南仁德店 (06)249-2888", "特力屋台北土城店 (02)8262-6000", "特力屋新竹店 (03)575-1234",
                    "特力屋台北中和店 (02)2240-1888", "特力屋屏東店 (08)721-6000", "特力屋台北新店店 (02)2910-9988",
                    "特力屋高雄左營店 (07)310-4000", "特力屋宜蘭羅東店 (03)9552000", "特力屋花蓮店 (03)833-0098",
                    "特力屋台中豐原店 (04)2513-2200", "特力屋台北三峽店 (02)8970-5089", "特力屋台中西屯店 (04)2462-9333",
                    "特力屋網路店", "特力屋桃園八德店 (03)375-3666"},
            {"普來利桃園店 (03)392-1100", "普來利新竹店 (03)526-9966",
                    "普來利竹北店 (03)555-8086", "普來利竹南店 (037)550095", "普來利頭份店 (037)669900",
                    "普來利太平店 (04)2391-9555", "普來利竹科店 (03)666-9598"}};*/

    public static final int GET_PHOTO = 0;
    public static final int TAKE_PHOTO = 1;

    private DisplayMetrics mPhone;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;

    /**
     * 創建Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences department_id = getSharedPreferences("department_id" , MODE_PRIVATE);
        String department_id_data = department_id.getString("D_ID" , "");
        if (department_id_data.toString().equals("2100")) {
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("2200")) {
            getMenuInflater().inflate(R.menu.diy_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("5200")) {
            getMenuInflater().inflate(R.menu.workers_menu, menu);
            return true;
        }else{
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
                Intent intent = new Intent(PictureActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(PictureActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(PictureActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent2 = new Intent(PictureActivity.this, SearchActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            case R.id.signature_item:
                Intent intent3 = new Intent(PictureActivity.this, SignatureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
            case R.id.record_item:
                Intent intent8 = new Intent(PictureActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //顯示客戶訂單照片上傳
            case R.id.customer_item:
                Intent intent4 = new Intent(PictureActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            case R.id.upload_item:
                Intent intent5 = new Intent(PictureActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(PictureActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            case R.id.about_item:
                Intent intent9 = new Intent(PictureActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(PictureActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(PictureActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Intent intent12 = new Intent(PictureActivity.this, PointsActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            default:
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        //動態取得 View 物件
        InItFunction();
        //載入分店下拉選單
        SpinnerAdapter();
        //讀取手機解析度
        mPhone = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        upload_btn = (Button) findViewById(R.id.upload_btn);
        camera_btn = (Button) findViewById(R.id.camera_btn);
        save_btn = (Button) findViewById(R.id.save_btn);
        customer_edt = (EditText) findViewById(R.id.customer_edt);
        phone_edt = (EditText) findViewById(R.id.phone_edt);
        address_edt = (EditText) findViewById(R.id.address_edt);
        note_edt = (EditText) findViewById(R.id.note_edt);
        type_spinner = (Spinner) findViewById(R.id.type);
        store_spinner = (Spinner) findViewById(R.id.store);
        upload_imageView = (ImageView) findViewById(R.id.upload_imageView);

        //上傳照片
        upload_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //TODO Aito-generated method stub
                //開啟相簿相片集，須由startActivityForResult且帶入requestCode進行呼叫，原因為點選相片後返回程式呼叫onActivityResult
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GET_PHOTO);
            }
        });

        //開啟相機
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //開啟相機功能，並將拍照後的圖片存入SD卡相片集內，須由startActivityForResult且帶入requestCode進行呼叫，原因為拍照完畢後返回程式後則呼叫onActivityResult
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(PictureActivity.this,
                            "com.example.a10609516.myapplication", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //啟動相機程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        //上傳客戶資料與訂單照片
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttpForUpload();
            }
        });

        /*upload_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageView轉Bitmap
                upload_imageView.buildDrawingCache();
                bmp = upload_imageView.getDrawingCache();

                //轉換為圖片指定大小
                //獲得圖片的寬高
                int width = bmp.getWidth();
                int height = bmp.getHeight();

                //放大為1.2倍
                float scaleWidth = (float) 2;
                float scaleHeight = (float) 2;

                // 取得想要缩放的matrix參數
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                // 得到新的圖片
                final Bitmap new_bm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
                //重新載入 imageView
                final ImageView new_iv = new ImageView(context);
                //new_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                new_iv.setImageBitmap(new_bm);

                new AlertDialog.Builder(PictureActivity.this)
                        .setTitle("選擇圖片")
                        .setView(new_iv)
                        .setPositiveButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        upload_imageView.setImageResource(0);
                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                        .setNegativeButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        *//*if(bmp != null && !bmp.isRecycled()){
                                            //回收並且設置為null
                                            //bmp.recycle();
                                            bmp = null;
                                        }
                                        System.gc();
                                        new_iv.setImageDrawable(null);
                                        new_iv.setImageBitmap(null);*//*
                                    }
                                }).show();
            }
        });*/
    }

    /**
     * 載入分店下拉選單
     */
    private void SpinnerAdapter() {
        context = this;
        //程式剛啟始時載入第一個下拉選單
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);
        type_spinner.setOnItemSelectedListener(selectListener);
        //因為下拉選單第一個為請選擇，所以不載入
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, empty);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        store_spinner.setAdapter(adapter2);
    }

    /**
     * 第一個下拉類別的監看式
     */
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            //讀取第一個下拉選單是選擇第幾個
            //int pos = type_spinner.getSelectedItemPosition();
            //與DIYStore.PHP取得連線
            sendRequestWithOkHttpForStore();
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    };

    /**
     * 與OkHttp建立連線(DIYStore)
     */
    private void sendRequestWithOkHttpForStore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String spinner_select = String.valueOf(type_spinner.getSelectedItem());
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("STORE", spinner_select)
                            .build();
                    Log.e("PictureActivity", spinner_select);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/DIYStore.php")
                            .url("http://192.168.0.172/WQP/DIYStore.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PictureActivity", responseData);
                    parseJSONWithJSONObjectForStore(responseData);
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
    private void parseJSONWithJSONObjectForStore(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            ArrayList<String> JArrayList = new ArrayList<String>();
            diy = new String[]{};
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String DIY_store = jsonObject.getString("分店");
                //JSONArray加入SearchData資料
                JArrayList.add(DIY_store);
                diy = JArrayList.toArray(new String[i]);
                //Log.e("PictureActivity3", diy[i]);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (String.valueOf(type_spinner.getSelectedItem()).equals("請選擇")) {
                        adapter2 = new ArrayAdapter<String>(PictureActivity.this, android.R.layout.simple_spinner_item, empty);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        store_spinner.setAdapter(adapter2);
                    } else {
                        //重新產生新的Adapter/*，用的是二維陣列type2[pos]*/
                        adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, diy);
                        //載入第二個下拉選單Spinner
                        store_spinner.setAdapter(adapter2);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 與OkHttp建立連線(DIYStore)
     */
    private void sendRequestWithOkHttpForUpload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String store = String.valueOf(store_spinner.getSelectedItem()).replaceAll("\\p{P}","").replaceAll("\\d+","").trim();
                if(store.equals("台北新光信義A")){
                    store = "台北新光信義A9";
                }
                String customer = customer_edt.getText().toString();
                String phone = phone_edt.getText().toString();
                String address = address_edt.getText().toString();
                String note = note_edt.getText().toString();
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("STORE", store)
                            .add("CUST_NAME", customer)
                            .add("MOBILE", phone)
                            .add("ADDRESS", address)
                            .add("NOTE", note)
                            .build();
                    Log.e("PictureActivity4", store);
                    Request request = new Request.Builder()
                            //.url("http://220.133.80.146/WQP/DIYStore.php")
                            //.url("http://192.168.0.172/WQP/.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PictureActivity", responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 取得手機上傳照片
     *
     * @param requestCode
     * @param resultCode
     * @param data 拍照完畢或選取圖片後呼叫此函式
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //藉由requestCode判斷是否為開啟相機或開啟相簿而呼叫的，且data不為null
        if ((requestCode == TAKE_PHOTO || requestCode == GET_PHOTO) && data != null) {
            //取得照片路徑uri
            uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                //讀取照片，型態為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if (bitmap.getWidth() > bitmap.getHeight()) ScalePic(bitmap,
                        mPhone.heightPixels);
                else ScalePic(bitmap, mPhone.widthPixels);
            } catch (FileNotFoundException e) {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 調整照片縮放比例
     *
     * @param bitmap
     * @param phone
     */
    private void ScalePic(Bitmap bitmap, int phone) {
        //縮放比例預設為1
        float mScale = 1;
        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if (bitmap.getWidth() > phone) {
            //判斷縮放比例
            mScale = (float) phone / (float) bitmap.getWidth();
            Matrix mMat = new Matrix();
            mMat.setScale(mScale, mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mMat, false);
            upload_imageView.setImageBitmap(mScaleBitmap);
        } else upload_imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("PictureActivity", "onDestroy");
    }

}
