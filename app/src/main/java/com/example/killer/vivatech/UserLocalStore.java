package com.example.killer.vivatech;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vrajesh on 5/2/2015.
 */
public class UserLocalStore {
    public static final String SP_NAME="userdetails";
    SharedPreferences userLocalDatabase;


    public  UserLocalStore(Context context){
        userLocalDatabase=context.getSharedPreferences(SP_NAME,0);
    }
    public void StoreUserData(User user){
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.putString("username",user.username);
        spEditor.putString("password",user.password);
        spEditor.putString("name",user.name);
        spEditor.putInt("age", user.age);
        spEditor.putString("profession", user.profession);
        spEditor.putString("email", user.email);
        spEditor.putString("year",user.year);
        spEditor.putString("mobno",user.mobno);
        spEditor.putString("datafill",user.datafill);
        spEditor.commit();
    }
    public User getLoggedInUser(){
        String name=userLocalDatabase.getString("name", "");
        String username=userLocalDatabase.getString("username","");
        String password=userLocalDatabase.getString("password","");
        int age=userLocalDatabase.getInt("age", -1);
        String email=userLocalDatabase.getString("email", "");
        String profession=userLocalDatabase.getString("profession","");
        String year=userLocalDatabase.getString("year","");
        String mobno=userLocalDatabase.getString("mobno","");
        String datafill=userLocalDatabase.getString("datafill","");
        User storedUser =new User(username,password,email,profession,name,mobno,year,datafill);
        return storedUser;
    }

    public User getLoggedInUser1(){
        String name=userLocalDatabase.getString("name","");
        String username=userLocalDatabase.getString("username","");
        String password=userLocalDatabase.getString("password", "");
        int age=userLocalDatabase.getInt("age", -1);
        User storedUser =new User(name,age,username,password,"","");
        return storedUser;
    }
    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.putBoolean("loggedin",loggedIn);
        spEditor.commit();

    }
    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedin",false)==true){
            return  true;
        }else {
            return false;
        }
       }
    public boolean getUserLoggedIn1(){
        if(userLocalDatabase.getBoolean("loggedin",false)==true){
            return  true;
        }else {
            return false;
        }
    }
    public void clearUserData(){
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
