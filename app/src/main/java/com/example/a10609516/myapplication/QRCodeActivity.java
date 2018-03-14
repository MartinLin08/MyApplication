package com.example.a10609516.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class QRCodeActivity extends AppCompatActivity {

    private Button QRCode_btn;
    private TextView date_txt,result_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        //動態取得 View 物件
        InItFunction();
        //獲取當天日期
        GetDate();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        QRCode_btn = (Button)findViewById(R.id.QRCode_btn);
        date_txt = (TextView)findViewById(R.id.date_txt);
        result_txt = (TextView)findViewById(R.id.result_txt);

        QRCode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                if(getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size()==0)
                {
                    //未安裝
                    Toast.makeText(QRCodeActivity.this, "請至 Play商店 安裝 ZXing 條碼掃描器", Toast.LENGTH_LONG).show();
                }else{
                    //SCAN_MODE, 可判別所有支援條碼
                    //QR_CODE_MODE, 只判別QR_CODE
                    //PRODUCT_MODE, UPC and EAN條碼
                    //ONE_D_MODE, 1維條碼
                    intent.putExtra("SCAN_MODE","SCAN_MODE");

                    //呼叫ZXing Scanner,完成動作後回傳1給onActivityResult的requestCode參數
                    startActivityForResult(intent,1);
                }
            }
        });
    }

    /**
     * 取回掃描回傳值或取消掃描
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");
                result_txt.setText(contents.toString());
            }else{
                if(resultCode==RESULT_CANCELED){
                    Toast.makeText(QRCodeActivity.this, "取消掃描", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 獲取當天日期
     */
    private void GetDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new java.util.Date());
        date_txt.setText(date);
    }

}
