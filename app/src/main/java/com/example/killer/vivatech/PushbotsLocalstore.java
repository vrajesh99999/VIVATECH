package com.example.killer.vivatech;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by killer on 1/26/2016.
 */
public class PushbotsLocalstore {
    public static final String SP_NAME="Pushbots";
    SharedPreferences pushbotsLocalstore;
    public PushbotsLocalstore(Context context){
        pushbotsLocalstore=context.getSharedPreferences(SP_NAME,0);
    }

    public void setpushbotstatus(String status){
        SharedPreferences.Editor spEditor=pushbotsLocalstore.edit();
        spEditor.putString("status",status);
        spEditor.commit();
    }
    public String getpushbotstatus(){
        String status= pushbotsLocalstore.getString("status", "");
        return status;
    }




}
