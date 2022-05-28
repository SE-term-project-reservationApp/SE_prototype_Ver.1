package com.example.SE_project.MyteamPage;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

import com.example.SE_project.R;
import com.example.SE_project.SecondTab.Item;

import java.util.ArrayList;

public class reserve_ItemAdapter extends RecyclerView.Adapter<reserve_ItemAdapter.ViewHolder>{

    ArrayList<Item> items = new ArrayList<Item>();

    int lastPosition = -1;

    Context context;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row_lr);
        ((ViewHolder) viewHolder).itemView.startAnimation(animation);

        Item item = items.get(position);
        viewHolder.setItem(item);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView=itemView.findViewById(R.id.b_image);
            title_view = itemView.findViewById(R.id.reserve_name);
            // description = itemView.findViewById(R.id.desc_text);
        }

        public void setItem(Item item){
            //item.setText(title_view);
        }
    }
}
