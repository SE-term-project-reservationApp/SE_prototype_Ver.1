package com.example.SE_project.Booking;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.SE_project.MainActivity;
import com.example.SE_project.R;

public class bookingComplete extends Activity {

    //daytime 에서 가져올 변수
    TextView tvYear, tvMonth, tvDay, tvToHour, tvFromHour,Hour;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_complete);

        //예약 날 정보
        //이전 day time 변수들
        tvYear = (TextView) findViewById(R.id.tvYear3);
        tvMonth = (TextView) findViewById(R.id.tvMonth3);
        tvDay = (TextView) findViewById(R.id.tvDay3);
        Hour= (TextView) findViewById(R.id.Hour);

        tvYear.setText((((booking_Activity)booking_Activity.DayContext).Year).toString());
        tvMonth.setText((((booking_Activity)booking_Activity.DayContext).Month).toString());
        tvDay.setText((((booking_Activity)booking_Activity.DayContext).Day).toString());
        Hour.setText((((booking_Activity)booking_Activity.DayContext).Hour).toString());

        btnFinish = (Button)findViewById(R.id.BtnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bookingComplete.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
