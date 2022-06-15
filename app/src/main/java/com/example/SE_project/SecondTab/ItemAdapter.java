package com.example.SE_project.SecondTab;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.SE_project.Booking.booking_Activity;
import com.example.SE_project.MyteamPage.myTeamPageActivity;
import com.example.SE_project.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    ArrayList<Item> items = new ArrayList<Item>();

    int lastPosition = -1;

    Context context;

    static String TAG = "Adapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_layout_lr, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row_lr);
            ((ViewHolder) viewHolder).itemView.startAnimation(animation);
            Item item = items.get(position);
            viewHolder.setItem(item);
            Log.d("확인", item.getName());

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
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent booking_link_intent = new Intent(context, booking_Activity.class);
                    booking_link_intent.putExtra("구장이름", item.getName());
                    booking_link_intent.putExtra("구장주소", item.getAddress());
                    booking_link_intent.putExtra("구장사진", item.getUri());
                    context.startActivity(booking_link_intent);
                }
            });

    }
//aa
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item){
        items.add(0,item);
    }

    public void removeAllItem(){
        items.clear();
    }

   class ViewHolder extends RecyclerView.ViewHolder{
        TextView title_view,context_text;
        Button btn;
        ImageView img;
        Uri u;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // imageView=itemView.findViewById(R.id.b_image);
            title_view = itemView.findViewById(R.id.title_text);
            context_text=itemView.findViewById(R.id.context_text);
            btn = itemView.findViewById(R.id.Booking_Gujang);
            img=itemView.findViewById(R.id.img);

            // description = itemView.findViewById(R.id.desc_text);
        }
        public void setItem(Item item){
            title_view.setText(item.getName());
            context_text.setText(item.getAddress());
            if(item.getName().equals("천마 풋살 파크")){
                img.setImageResource(R.drawable.cheonma);
            }
            else if(item.getName().equals("아차산 배수지 체육공원")){
                img.setImageResource(R.drawable.mt_acha);
            }
            else if(item.getName().equals("서울 상암 월드컵")){
                img.setImageResource(R.drawable.seoul_world_cup);
            }
            else if(item.getName().equals("가천대학교 운동장")){
                img.setImageResource(R.drawable.gachon);
            }
            else if(item.getName().equals("가양 레포츠 센터 축구장")){
                img.setImageResource(R.drawable.gayang);
            }

        }
    }
}
