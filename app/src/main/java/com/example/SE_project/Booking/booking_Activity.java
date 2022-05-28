package com.example.SE_project.Booking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.SE_project.R;


public class booking_Activity extends Activity {

    //Context로 다음 액티비티에서 정보 사용
    public static Context DayContext;
    DatePicker dPicker;
    Spinner tPicker;

    // String arrays
    String[] timeTable = {"8AM - 10AM", "10AM - 12AM", "12AM - 2PM", "2PM - 4PM", "4PM - 6PM", "6PM - 8PM", "8PM - 10PM"};



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

        tPicker = (Spinner) findViewById(R.id.timePicker1);
        ArrayAdapter <String> menuAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,timeTable);
        menuAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tPicker.setAdapter(menuAdapter);
//
//        tPicker.setSelection(6);

        tPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //선택 시 작동기능
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Hour = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



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
