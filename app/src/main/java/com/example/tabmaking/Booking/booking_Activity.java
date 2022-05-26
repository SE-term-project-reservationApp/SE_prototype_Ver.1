package com.example.tabmaking.Booking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tabmaking.R;

public class booking_Activity extends Activity {

    //Context로 다음 액티비티에서 정보 사용
    public static Context DayContext;
    DatePicker dPicker;
    TimePicker tPicker;
    Integer Year, Month, Day, Hour, Minute;

    Button btnToTable;
    Button btnDayOk;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);

        //컨텍스트 설정
        DayContext = this;

        // FrameLayout의 2개 위젯
        dPicker = (DatePicker) findViewById(R.id.datePicker1);
        tPicker = (TimePicker) findViewById(R.id.timePicker1);

//        // 텍스트뷰 중에서 연,월,일,시,분 숫자
//        tvYear = (TextView) findViewById(R.id.tvYear);
//        tvMonth = (TextView) findViewById(R.id.tvMonth);
//        tvDay = (TextView) findViewById(R.id.tvDay);
//        tvHour = (TextView) findViewById(R.id.tvHour);
//        tvMinute = (TextView) findViewById(R.id.tvMinute);

        btnDayOk = (Button)findViewById(R.id.BtnDayOk);
        btnDayOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = dPicker.getYear();
                Month = 1 + dPicker.getMonth();
                Day = dPicker.getDayOfMonth();

                Hour = tPicker.getCurrentHour();
                Minute = tPicker.getCurrentMinute();

                //TODO 이거 되는 시간인지 확인!

            }
        });

        btnToTable = (Button) findViewById(R.id.BtnToTable);
        btnToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다음 액티비티로 가는 것
                //Intent
                Intent intent = new Intent(booking_Activity.this, bookingComplete.class); //다음 Table클래스 정보 입력
                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
    }
}
