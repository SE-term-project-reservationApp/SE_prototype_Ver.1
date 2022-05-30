package com.example.SE_project;

import android.app.Application;

public class Local extends Application {

    String name = "";
    String uri = "";
    String intro="";
    String nickname="";

    @Override
    public void onCreate() {
//변수 초기화
        super.onCreate();
    }

    @Override
    public void onTerminate() {
//프로세스 소멸 시 호출
        super.onTerminate();
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name =name;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname =nickname;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri=uri;
    }
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro=intro;
    }
}