package com.example.lastommg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.lastommg.MyPageFile.myItem;

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> implements Serializable {
    Context mContext;
    ArrayList<Item> myItems = new ArrayList<Item>();

    public OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, myItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.uploaded_items, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ViewHolder(itemView);
    }



    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = myItems.get(position);
        myItem myItem= new myItem(item.getId(),item.getName(), item.getUri(), item.getPhoneNumber(), item.getLat(),item.getLon(),item.getAddress(),item.getDistance());
        Glide.with(mContext)
                .load(item.getUri())
                .thumbnail(0.5f)
                .into(holder.img_thumb);

        holder.txt_artist.setText(item.getName());
        holder.txt_title.setText(item.getAddress());
        holder.layout_album_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, myItem);
            }
        });
    }



    @Override

    public int getItemCount() {
        return myItems.size();
    }

    public void addItem(Item item) {
        myItems.add(0,item);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements Serializable {
        private LinearLayout layout_album_panel;
        private ImageView img_thumb;
        private TextView txt_artist;
        private TextView txt_title;


        public ViewHolder(View convertView) {
            super(convertView);

            layout_album_panel = (LinearLayout) convertView.findViewById(R.id.layout_album_panel);
            img_thumb = (ImageView) convertView.findViewById(R.id.img_thumb);
            txt_artist = (TextView) convertView.findViewById(R.id.txt_artist);
            txt_title = (TextView) convertView.findViewById(R.id.txt_title);
        }
    }
}