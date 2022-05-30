package com.example.SE_project.FirstTab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SE_project.Local;
import com.example.SE_project.MyteamPage.ReservationItem;
import com.example.SE_project.MyteamPage.reserve_ItemAdapter;
import com.example.SE_project.R;
import com.example.SE_project.SecondTab.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends RecyclerView.Adapter<ViewpagerAdapter.ViewHolder>{
    ArrayList<ReservationItem> items = new ArrayList<ReservationItem>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int lastPosition = -1;

    Context context;

    static String TAG = "Adapter";

    @NonNull
    @Override
    public ViewpagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.match_info, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewpagerAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        Local local = (Local)context.getApplicationContext();
        ReservationItem item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reservation a=new Reservation(local.getUsername(),items.get(position).getTime());
                ReservationItem b=new ReservationItem(items.get(position).getName(),items.get(position).getAdress(),items.get(position).getTime(),items.get(position).getTeam());
                db.collection("SF").document(items.get(position).getName()).collection("예약정보").document(items.get(position).getTime()).set(a);
                db.collection("User").document(local.getNickname()).collection(local.getUsername()).document(items.get(position).getName()+items.get(position).getTime()).set(b);
               items.remove(position);
               notifyDataSetChanged();
                db.collection("need").document(item.getTeam())
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
                Toast.makeText(context, "예약 정보에 추가되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
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

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title_view,text_view,text_time,match_team;
        Button match;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView=itemView.findViewById(R.id.b_image);
            title_view = itemView.findViewById(R.id.match_rule);
            text_view = itemView.findViewById(R.id.match_ground);
            text_time = itemView.findViewById(R.id.match_time);
            match_team=itemView.findViewById(R.id.match_team);
            match=itemView.findViewById(R.id.MATCH_btn);
        }

        public void setItem(ReservationItem item){
            title_view.setText(item.getName());
            text_view.setText(item.getAdress());
            text_time.setText(item.getTime());
            match_team.setText(item.getTeam());
        }
    }

}