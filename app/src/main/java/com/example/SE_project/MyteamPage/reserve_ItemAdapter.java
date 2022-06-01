package com.example.SE_project.MyteamPage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.SE_project.Local;
import com.example.SE_project.Login.SignupActivity;
import com.example.SE_project.MainActivity;
import com.example.SE_project.R;
import com.example.SE_project.SecondTab.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class reserve_ItemAdapter extends RecyclerView.Adapter<reserve_ItemAdapter.ViewHolder>{

    ArrayList<ReservationItem> items = new ArrayList<ReservationItem>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int lastPosition = -1;

    Context context;
    AlertDialog dialog;
    static String TAG = "Adapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.reserve_item, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        Local local = (Local)context.getApplicationContext();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row_lr);
        ((ViewHolder) viewHolder).itemView.startAnimation(animation);
        ReservationItem item = items.get(position);
        viewHolder.setItem(item,local.getNickname());

        //TODO 사진 파일에서 데이터 베이스 아이템으로 바꾸기!
//        Bitmap pic = null;
//        try{
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
//                pic = ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.getContentResolver(), imageUri넣으셈));
//            }else{
//                pic = MediaStore.Images.Media.getBitmap(context.getContentResolver(),imageUri넣어);
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        Bitmap pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.b);
        Bitmap scale_pic = Bitmap.createScaledBitmap( pic, 50, 75, true);
        //

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupXml(items.get(position).getName(),items.get(position).getAdress(),items.get(position).getTime(),position);
            }
        });
    }

    public void popupXml(String name,String address,String time, int position ) {
        //Log.d(TAG, "okay");
        Local local = (Local)context.getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        TextView a=view.findViewById(R.id.nickname);
        TextView b=view.findViewById(R.id.name);
        TextView c=view.findViewById(R.id.name2);
        TextView d=view.findViewById(R.id.adress);
        TextView e=view.findViewById(R.id.time);
        a.setText(local.getNickname());
        b.setText(local.getUsername());
        c.setText(name);
        d.setText(address);
        e.setText(time);
        Button upload=view.findViewById(R.id.upload);
        Button cancel=view.findViewById(R.id.cancel);
        upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Local local = (Local)context.getApplicationContext();
                ReservationItem a = new ReservationItem(name,address,time,local.getNickname(),"");
                db.collection("need").document(local.getNickname()+name+time).set(a);
                Toast.makeText(context, "업로드되었습니다.", Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                db.collection("SF").document(name).collection("예약정보").document(time)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                db.collection("User").document(local.getNickname()).collection(local.getUsername()).document(name+time)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                Toast.makeText(context, "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        builder.setTitle("팀구하기").setView(view);
        builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ReservationItem item){
        items.add(0,item);
    }

    public void removeAllItem(){
        items.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title_view,text_view,text_time,info;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView=itemView.findViewById(R.id.b_image);
            title_view = itemView.findViewById(R.id.reserve_name);
            text_view = itemView.findViewById(R.id.reserve_text);
            text_time = itemView.findViewById(R.id.reserve_time);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            info=itemView.findViewById(R.id.info);
        }

        public void setItem(ReservationItem item,String teamname){
            if(teamname.equals(item.getTeam()))
            {

            }
            else
            {
                info.setText(teamname+" VS "+item.getTeam());
            }
            title_view.setText(item.getName());
            text_view.setText(item.getAdress());
            text_time.setText(item.getTime());

        }
    }
}
