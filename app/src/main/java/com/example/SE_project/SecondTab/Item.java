package com.example.SE_project.SecondTab;

import android.widget.ImageView;
import android.widget.TextView;

public class Item {
    String name="",intro="";
    String uri="";
    public Item()  {

    }

    public Item(String name,String intro){
        this.name=name;
        this.intro=intro;

    }

    public void setName(String name){
        this.name=name;

    }
    public String getName(){
        return name;
    }
    public void setIntro(String intro){
        this.intro=intro;

    }
    public String getIntro(){
        return intro;
    }
    public void setUri(String uri){
        this.uri=uri;

    }
    public String getUri(){
        return uri;
    }

}