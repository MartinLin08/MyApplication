package com.example.a10609516.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScheduleActivity extends AppCompatActivity {

    private View view1, view2, view3;
    private List<View> viewList;// view陣列
    private ViewPager viewPager; // 對應的viewPager

    private List<String> titleList;  //標題列表陣列

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent = new Intent(ScheduleActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //顯示行程資訊
            case R.id.calendar_item:
                Intent intent1 = new Intent(ScheduleActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent2 = new Intent(ScheduleActivity.this, SearchActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; // 進入查詢派工資料頁面
            case R.id.pending_item:
                Intent intent3 = new Intent(ScheduleActivity.this, PendingActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "待處理派工", Toast.LENGTH_SHORT).show();
                break; //進入待處理派工頁面
            case R.id.record_item:
                Intent intent4 = new Intent(ScheduleActivity.this, RecordActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; // 進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent5 = new Intent(ScheduleActivity.this, PictureActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳
            case R.id.customer_item:
                Intent intent6 = new Intent(ScheduleActivity.this, CustomerActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            case R.id.upload_item:
                Intent intent7 = new Intent(ScheduleActivity.this, UploadActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent8 = new Intent(ScheduleActivity.this, CorrectActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title);
        //為標題設置字體大小
        pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_PX, 70);
        //為標題設置字體樣式
        pagerTitleStrip.setTextColor(Color.WHITE);
        //為標題設置背景顏色
        pagerTitleStrip.setBackgroundColor(Color.BLUE);
        //設置標題位置
        pagerTitleStrip.setGravity(17);

        pagerTitleStrip.getChildAt(0).setVisibility(View.GONE);
        pagerTitleStrip.getChildAt(2).setVisibility(View.GONE);

        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.activity_day__schedule, null);
        view2 = inflater.inflate(R.layout.activity_week__schedule, null);
        view3 = inflater.inflate(R.layout.activity_missing__date, null);

        viewList = new ArrayList<View>();// 將要分頁顯示的View装入陣列中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        titleList = new ArrayList<String>();// 每個頁面的Title數據
        titleList.add("今日行程資訊");
        titleList.add("一周行程資訊");
        titleList.add("派工未填抵達日期");

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                //根據傳來的Key，找到view,判斷與傳來的參數View arg0是不是同一個layout
                return arg0 == viewList.get((int) Integer.parseInt(arg1.toString()));
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));

                ImageView about_imageView1 = (ImageView) findViewById(R.id.about_imageView1);
                ImageView about_imageView2 = (ImageView) findViewById(R.id.about_imageView2);
                ImageView about_imageView3 = (ImageView) findViewById(R.id.about_imageView3);
                ImageView about_imageView4 = (ImageView) findViewById(R.id.about_imageView4);
                ImageView about_imageView5 = (ImageView) findViewById(R.id.about_imageView5);
                ImageView about_imageView6 = (ImageView) findViewById(R.id.about_imageView6);

                about_imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent4 = new Intent(ScheduleActivity.this, MaintainActivity.class);
                        startActivity(intent4);
                    }
                });
                about_imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent5 = new Intent(ScheduleActivity.this, MaintainActivity.class);
                        startActivity(intent5);
                    }
                });
                about_imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent6 = new Intent(ScheduleActivity.this, MaintainActivity.class);
                        startActivity(intent6);
                    }
                });
                about_imageView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent7 = new Intent(ScheduleActivity.this, MaintainActivity.class);
                        startActivity(intent7);
                    }
                });
                about_imageView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent8 = new Intent(ScheduleActivity.this, MaintainActivity.class);
                        startActivity(intent8);
                    }
                });
                about_imageView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent9 = new Intent(ScheduleActivity.this, MaintainActivity.class);
                        startActivity(intent9);
                    }
                });

                Button more_button1 = (Button) findViewById(R.id.more_button1);
                Button more_button2 = (Button) findViewById(R.id.more_button2);
                Button more_button3 = (Button) findViewById(R.id.more_button3);

                switch (position) {
                    case 0:
                    more_button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1 = new Intent(ScheduleActivity.this, Day_Work.class);
                            startActivity(intent1);
                        }
                    });
                        break;

                    case 1:
                    more_button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent2 = new Intent(ScheduleActivity.this, Week_Work.class);
                            startActivity(intent2);
                        }
                    });
                        break;

                    case 2:
                    more_button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent3 = new Intent(ScheduleActivity.this, Missing_Date_Record.class);
                            startActivity(intent3);
                        }
                    });
                        break;
                    default:
                        break;
                }
                //把當前新增layout的位置（position）做為Key傳過去
                return position;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                // TODO Auto-generated method stub
                return titleList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ScheduleActivity", "onDestroy");
    }
}
