package com.example.killer.vivatech;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.pushbots.push.Pushbots;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {
    EditText login, password;
   Button loginbutton;
    UserLocalStore userLocalStore;
    Context c = this;
    public static final int CHANGE_BGCOLOR = 1;

    private LinearLayout llaLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT<20){
            setContentView(R.layout.loginapi20);
            llaLayout = (LinearLayout)findViewById(R.id.loginapi20layout);
        }else {
            setContentView(R.layout.activity_login);
            llaLayout = (LinearLayout)findViewById(R.id.loginlayout);
        }

        int colorFrom = Color.parseColor("#009688");
        int colorTo =  Color.parseColor("#E91E63");
        final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(7000);
        colorAnimation.setRepeatCount(1000);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                llaLayout.setBackgroundColor((int) animator.getAnimatedValue());

            }

        });
        colorAnimation.start();
        System.out.println("id pase");



        login = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        loginbutton = (Button) findViewById(R.id.button);
        userLocalStore = new UserLocalStore(this);


    }

    public void onClick(View view) {
        if (NetStatus.getInstance(this).isOnline()) {

            String username = login.getText().toString();
            String password12 = password.getText().toString();


            System.out.println(username + "gdfgdfgfdgdfgfdf" + password);


            if (username.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Fileds can not be empty.", Toast.LENGTH_SHORT).show();

            } else if (password12.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Fileds can not be empty.", Toast.LENGTH_SHORT).show();
            } else if (profession.endsWith("student")) {
                User user = new User(username, password12, "null", profession,"no");
                authenticate(user);
                userLocalStore.StoreUserData(user);
                userLocalStore.setUserLoggedIn(true);
            } else {

                User user = new User(username, password12, "null", profession,"no");
                authenticate(user);
                userLocalStore.StoreUserData(user);
                userLocalStore.setUserLoggedIn(true);
            }

        } else {
            Toast.makeText(Login.this, "Please connect to INTERNET,and try again !!!", Toast.LENGTH_LONG).show();
        }
    }


    public void authenticate(User user) {
        //  ServerRequests serverRequests1=new ServerRequests(profession);
        ServerRequests serverRequests = new ServerRequests(this, profession);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallbcak() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    System.out.println(returnedUser);
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);

                }
            }
        });
    }

    private void showErrorMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Incorrect User Details!")
                .show();
      //  AlertDialog.Builder dielogBuilder = new AlertDialog.Builder(Login.this);
       // dielogBuilder.setMessage("incorrect user details");
        userLocalStore.clearUserData();
       // dielogBuilder.setPositiveButton("ok", null);
       // dielogBuilder.show();
    }

    ProgressDialog progressDialog;

    private void logUserIn(User returnedUser) {
        returnedUser.profession = profession;
        userLocalStore.StoreUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Proccessing");
        progressDialog.setMessage("please wait...");
        progressDialog.show();
       // if(returnedUser.datafill.equals("yes")){
       //    Pushbots.sharedInstance().setPushEnabled(true);
        //Pushbots.sharedInstance().init(getApplicationContext());
       //     Pushbots.sharedInstance().setAlias(returnedUser.name);
       //     Pushbots.sharedInstance().tag(returnedUser.branch);
       //  }
        Pushbots.sharedInstance().init(getApplicationContext());
        Pushbots.sharedInstance().setPushEnabled(true);

        //  Pushbots.sharedInstance().setAlias("vrajesh");


        if(returnedUser.datafill.equals("no")){
            startActivity(new Intent(this,Sdetails.class));
            progressDialog.dismiss();
            finish();
           }else{

           // Pushbots.sharedInstance().regID();
           // Pushbots.sharedInstance().setPushEnabled(true);
           // System.out.println(returnedUser.name);
            // System.out.println(returnedUser.branch);


            startActivity(new Intent(this, student.class));
            progressDialog.dismiss();
            finish();
    }}

    public void gotopasswordupdate(View view) {
    }

    String profession = "student";

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.staff:
                if (checked)
                    profession = "staff";
                break;

        }
        switch (view.getId()) {
            case R.id.student:
                if (checked)
                    profession = "student";
                break;

        }
    }


}
