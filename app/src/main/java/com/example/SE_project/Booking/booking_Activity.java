package com.example.SE_project.Booking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.SE_project.Local;
import com.example.SE_project.MyteamPage.ReservationItem;
import com.example.SE_project.R;
import com.example.SE_project.SecondTab.Item;
import com.example.SE_project.SecondTab.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class booking_Activity extends Activity {

    //Context로 다음 액티비티에서 정보 사용
    public static Context DayContext;
    DatePicker dPicker;
    Spinner tPicker;
    private FirebaseStorage storage;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Local local = (Local) getApplication();
    // String arrays
    String[] timeTable = {"8AM - 10AM", "10AM - 12AM", "12AM - 2PM", "2PM - 4PM", "4PM - 6PM", "6PM - 8PM", "8PM - 10PM"};



    Integer Year, Month, Day, FromHour=0, ToHour=0, check=1;
    String Hour,information;
    Button btnToTable;
    Button btnDayOk;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);
        //컨텍스트 설정
        DayContext = this;
        String name=getIntent().getStringExtra("구장이름");
        String adress=getIntent().getStringExtra("구장주소");
        // FrameLayout의 2개 위젯
        dPicker = (DatePicker) findViewById(R.id.datePicker1);
        btnDayOk = (Button)findViewById(R.id.BtnDayOk);
        btnToTable = (Button) findViewById(R.id.BtnToTable);
        btnToTable.setVisibility(View.INVISIBLE);
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
                switch (position){
                    case 0:
                        ToHour = 8;
                        FromHour = 10;
                        Hour=ToHour+"시~"+FromHour+"시";
                        break;
                    case 1:
                        ToHour = 10;
                        FromHour = 12;
                        Hour=ToHour+"시~"+FromHour+"시";
                        break;
                    case 2:
                        ToHour = 12;
                        FromHour = 14;
                        Hour=ToHour+"시~"+FromHour+"시";
                        break;
                    case 3:
                        ToHour = 14;
                        FromHour = 16;
                        Hour=ToHour+"시~"+FromHour+"시";
                        break;
                    case 4:
                        ToHour = 16;
                        FromHour = 18;
                        Hour=ToHour+"시~"+FromHour+"시";
                        break;
                    case 5:
                        ToHour = 18;
                        FromHour = 20;
                        Hour=ToHour+"시~"+FromHour+"시";
                        break;
                    case 6:
                        ToHour = 20;
                        FromHour = 22;
                        Hour=ToHour+"시~"+FromHour+"시";
                        break;
                }

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


        btnDayOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = dPicker.getYear();
                Month = 1 + dPicker.getMonth();
                Day = dPicker.getDayOfMonth();
                information=Year+Month+Day+Hour;
                db.collection("SF").document(name).collection("예약자").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                Reservation reser=document.toObject(Reservation.class);
                                if(reser.getTime().equals(information)){
                                    Toast.makeText(booking_Activity.this, "다른 팀이 예약중입니다ㅠㅠ", Toast.LENGTH_SHORT).show();
                                    check=0;
                                    break;
                                }
                                else
                                {
                                    check=1;
                                }
                            }
                            if(check == 1) {
                                btnToTable.setVisibility(View.VISIBLE);
                            }
                            else{
                                btnToTable.setVisibility(View.INVISIBLE);
                            }
                        }
                        else
                        {
                            Log.d("실패","응 실패야",task.getException());
                        }
                    }
                });


                //TODO 이거 되는 시간인지 확인!
            }
        });
        btnToTable = (Button) findViewById(R.id.BtnToTable);
        btnToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다음 액티비티로 가는 것
                //Intent
                Local local = (Local) getApplication();
                Reservation a=new Reservation(local.getUsername(),information);
                ReservationItem b=new ReservationItem(name,adress,information,local.getNickname());
                db.collection("SF").document(name).collection(local.getUsername()).document(information).set(a);
                db.collection("User").document(local.getNickname()).collection(local.getUsername()).document(name).set(b);
                Intent intent = new Intent(booking_Activity.this, bookingComplete.class); //다음 Table클래스 정보 입력
                startActivity(intent);//다음 액티비티 화면에 출력
            }
        });
    }
}
