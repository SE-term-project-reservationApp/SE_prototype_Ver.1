package com.example.SE_project.SecondTab;

import android.widget.ImageView;
import android.widget.TextView;

public class Item {
    String name="",address="";
    String uri="";
    public Item()  {

    }

    public Item(String name,String address,String uri){
        this.name=name;
        this.address=address;
        this.uri=uri;
    }

    public void setName(String name){
        this.name=name;

    }
    public String getName(){
        return name;
    }
    public void setAddress(String address){
        this.address=address;

    }
    public String getAddress(){
        return address;
    }
    public void setUri(String uri){
        this.uri=uri;

    }
    public String getUri(){
        return uri;
    }

}