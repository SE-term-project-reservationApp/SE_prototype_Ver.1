package com.example.tabmaking.FirstTab;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.tabmaking.R;


public class fragment_3 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_3, container, false);

        ImageButton imgbtn = (ImageButton) rootView.findViewById(R.id.imgBanner3);
        imgbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getActivity().startActivity(new Intent(getActivity(), MatchActivity.class));
            }
        });
        return rootView;
    }
}
