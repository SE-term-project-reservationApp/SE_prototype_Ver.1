package com.example.SE_project;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.SE_project.MyteamPage.MyAdapter;
import com.example.SE_project.MyteamPage.myTeamPageActivity;
import com.example.SE_project.SecondTab.Item;
import com.example.SE_project.SecondTab.ItemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends FragmentActivity{

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    //윤수 TAB 변수들 선언
    FloatingActionButton plus;
    FloatingActionButton myTeam;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1번 탭 미리 표시
        firstView();
        secondView();
        recyclerView.setVisibility(View.INVISIBLE);
        //Tab implant
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs) ;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        }) ;
    }

    private void changeView(int index) {

        switch (index) {
            case 0:
                mPager.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                plus.setVisibility(View.VISIBLE);
                break;
            case 1:
                recyclerView.setVisibility(View.VISIBLE);
                mPager.setVisibility(View.INVISIBLE);
                plus.setVisibility(View.INVISIBLE);
                break;
        }
    }

    //TODO 여기다가 매칭 페이지랑 마이팀 페이지 버튼 Implement
    private void firstView() {
        //ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(3);
        //Change in position -> change in view page
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }
        });
        //윤수 TAB
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        plus = (FloatingActionButton) findViewById(R.id.plus);
        myTeam = (FloatingActionButton) findViewById(R.id.team_btn);
        plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                anim();
            }
        });
        myTeam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                anim();
                //TODO 내 팀 페이지 만들어서 연결
                Intent myTeamIntent = new Intent(MainActivity.this, myTeamPageActivity.class);
                startActivity(myTeamIntent);
            }
        });
    }

    //TODO 여기다가 예약 페이지 만들기
    private void secondView() {
        recyclerView = findViewById(R.id.recycler_view);
        itemAdapter = new ItemAdapter();
        recyclerView.setAdapter(itemAdapter);
        //조회 전 화면 클리어
        itemAdapter.removeAllItem();
        //샘플 데이터 생성
        for(int i = 0; i < 20; i++){
            Item item = new Item();
            //데이터 등록
            itemAdapter.addItem(item);
        }
        //적용
        itemAdapter.notifyDataSetChanged();
        //애니메이션 실행
        recyclerView.startLayoutAnimation();
    }

    //TAB 눌렀을 때 애니메이션
    private void anim() {
        if (isFabOpen) {
            myTeam.startAnimation(fab_close);
            myTeam.setClickable(false);
            isFabOpen = false;
        } else {
            myTeam.startAnimation(fab_open);
            myTeam.setClickable(true);
            isFabOpen = true;
        }
    }
}

//animation
//        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
//        mPager.setPageTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float myOffset = position * -(2 * pageOffset + pageMargin);
//                if (mPager.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
//                    if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                        page.setTranslationX(-myOffset);
//                    } else {
//                        page.setTranslationX(myOffset);
//                    }
//                } else {
//                    page.setTranslationY(myOffset);
//                }
//            }
//        });
/* 줌인 아웃
 mPager.setPageTransformer((page, position) -> {
     float myOffset = position * -(2 * pageOffset + pageMargin);
     if (position < -1) {
         page.setTranslationX(-myOffset);
     } else if (position <= 1) {
         float scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f));
         page.setTranslationX(myOffset);
         page.setScaleY(scaleFactor);
         page.setAlpha(scaleFactor);
     } else {
         page.setAlpha(0);
         page.setTranslationX(myOffset);
     }
 });
*/

