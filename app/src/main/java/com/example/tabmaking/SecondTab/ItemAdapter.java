package com.example.tabmaking.SecondTab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabmaking.Booking.booking_Activity;
import com.example.tabmaking.R;

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

        viewHolder.btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent booking_link_intent = new Intent(context, booking_Activity.class);
                context.startActivity(booking_link_intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeAllItem(){
        items.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title_view;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView=itemView.findViewById(R.id.b_image);
            title_view = itemView.findViewById(R.id.title_text);
            btn = itemView.findViewById(R.id.Booking_Gujang);
            // description = itemView.findViewById(R.id.desc_text);
        }
        public void setItem(Item item){
            item.setText(title_view);
        }
    }
}
