package com.example.SE_project.MyteamPage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.SE_project.Local;
import com.example.SE_project.Login.LoginActivity;
import com.example.SE_project.R;
import com.example.SE_project.SecondTab.Item;
import com.example.SE_project.SecondTab.ItemAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.IOException;

public class myTeamPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private RecyclerView my_album;
    private FirebaseAuth mAuth;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private Uri mImageCaptureUri;
    private ImageView mPressProfileImg;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private com.example.SE_project.MyteamPage.reserve_ItemAdapter reserve_ItemAdapter;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myteam_page);
        mAuth = FirebaseAuth.getInstance();
        Local local = (Local) getApplication();
        //프로필 이미지 띄우기
        Uri a=Uri.parse(local.getUri());
        mPressProfileImg = findViewById(R.id.profile_image);
        Glide.with(myTeamPageActivity.this).load(a).into(mPressProfileImg);
        mPressProfileImg.setOnClickListener(this);
        logout = findViewById(R.id.logoutB);
//        RoundImageView riv = findViewById(R.id.round_profile_image);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.profile_img);
//        riv.setImageBitmap(bm);

        //팀명 띄우기
        TextView nameSlot = findViewById(R.id.name);
        nameSlot.setText(local.getUsername());
        mContext = this;
        //밑에 사진 띄우기
        init();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Intent intent3 = new Intent(myTeamPageActivity.this, LoginActivity.class);
                //로그아웃누르면  로그인화면으로 이동
                startActivity(intent3);
            }
        });

    }
    //프로필 이미지 설정 methods/////////////////////////////////////////////////////////////////
    /**
     * 앨범에서 이미지 가져오기
     */
    private void doTakeAlbumAction()
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {

            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

                mImageCaptureUri = data.getData();
            }

            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.

                if (mImageCaptureUri != null) {
                    Bitmap photo = null;
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mPressProfileImg.setImageBitmap(photo);
                }

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

                break;
            }
        }
    }
    //프로필 이미지 눌렀을 때
    @Override
    public void onClick(View view) {
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                doTakeAlbumAction();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }
    //밑에 사진 그리드 띄우기/////////////////////////////////////////////////////////////////////////////////////////
    private void init() {
        Local local = (Local) getApplication();
        recyclerView = findViewById(R.id.reserved_layout);
        reserve_ItemAdapter = new reserve_ItemAdapter();
        recyclerView.setAdapter(reserve_ItemAdapter);
        //조회 전 화면 클리어
        //샘플 데이터 생성
        db.collection("User").document(local.getNickname()).collection(local.getUsername()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document:task.getResult()){
                        ReservationItem item=document.toObject(ReservationItem.class);
                        reserve_ItemAdapter.addItem(item);
                        reserve_ItemAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Log.d("실패","응 실패야",task.getException());
                }
            }
        });
    }
    ///로그아웃

    private void signOut() {
        mAuth.signOut();
    }
}

