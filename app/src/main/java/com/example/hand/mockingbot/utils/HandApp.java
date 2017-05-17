package com.example.hand.mockingbot.utils;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;

import com.example.hand.mockingbot.entity.LoginEntity;


public class HandApp extends Application {
    //handler
    public static Handler mainHandler;

    public static Context context;

    private static String Language;

    private static LoginEntity loginEntity;


    @Override
    public void onCreate() {
        super.onCreate();

        mainHandler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
//        checkFirstIn();
    }

    public static void setLanguage(String language){
        Language = language;
    }

    public static String getLanguage(){
        return Language;
    }

    public static LoginEntity getLoginEntity() {
        return loginEntity;
    }

    public static void setLoginEntity(LoginEntity loginEntity) {
        HandApp.loginEntity = loginEntity;
    }


}