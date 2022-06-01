package com.example.SE_project.MyteamPage;

public class ReservationItem {
    String name="",adress="";
    String time="",team="";
    String uri="";
    public ReservationItem()  {

    }

    public ReservationItem(String name,String adress,String time,String team,String uri){
        this.name=name;
        this.adress=adress;
        this.time=time;
        this.team=team;
        this.uri=uri;
    }

    public void setName(String name){
        this.name=name;

    }
    public String getName(){
        return name;
    }
    public void setAdress(String adress){
        this.adress=adress;

    }
    public String getAdress(){
        return adress;
    }
    public void setTime(String time){
        this.time=time;

    }
    public String getTime(){
        return time;
    }
    public void setTeam(String team){
        this.team=team;

    }
    public String getTeam(){
        return team;
    }
    public void setUri(String uri){
        this.uri=uri;

    }
    public String getUri(){
        return uri;
    }




}