package com.example.killer.vivatech;

/**
 * Created by vrajesh on 5/2/2015.
 */
public class User {
    String name,username,password,a,branch,mobno,pic,year,datafill,email,profession;

    int age,ab;
    public User(String name,int age,String username,String password,String datafill,String branch){
        this.name=name;
        this.age=age;
        this.username=username;
        this.password=password;
        this.datafill=datafill;
        this.branch=branch;
    }

    public User(String username,String password){
        this.name="";
        this.age=-1;
        this.username=username;
        this.password=password;

    }

    public User(String username,String password,String branch,String mobno,String pic,String year) {
        this.name = "";
        this.age = -1;
        this.username = username;
        this.password = password;
        this.branch=branch;
        this.mobno=mobno;
        this.pic=pic;
        this.year=year;
        this.datafill=datafill;
    }

    public User(String username,String password,String email,String profession,String datafill){
        this.username = username;
        this.password = password;
        this.email=email;
        this.profession=profession;
        this.datafill=datafill;

    }
    public User(String username,String password,String email,String profession,String name,String mobno,String year,String datafill){
        this.name=name;
        this.mobno=mobno;
        this.year=year;
        this.username = username;
        this.password = password;
        this.email=email;
        this.profession=profession;
        this.datafill=datafill;
    }
}
