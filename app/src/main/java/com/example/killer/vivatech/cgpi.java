package com.example.killer.vivatech;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class cgpi extends AppCompatActivity {
    EditText cgpi, percentage;
    Button convert;
    double cgp,result;
    Context c = this;
    private LinearLayout llaLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpi);
        llaLayout = (LinearLayout)findViewById(R.id.lcgpi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int colorFrom = Color.parseColor("#009688");;
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

        cgpi = (EditText) findViewById(R.id.editText);
        percentage = (EditText) findViewById(R.id.editText2);
        convert = (Button) findViewById(R.id.button);

    }


        public void onClick(View v) {
            if(cgpi.getText().toString().equals("")){
                new SweetAlertDialog(this)
                        .setContentText("Field can not be Empty..")
                        .show();
            }else{
                cgp = Double.parseDouble(cgpi.getText().toString());
                //percent = Double.parseDouble(percentage.getText().toString());


                if(cgp<7){
                    result=7.1*cgp+12;
                    result=Math.round( result * 100.0 ) / 100.0;
                    // percentage.setText(Double.toString(result));
                    String total = String.valueOf(result);

                    new SweetAlertDialog(this)
                            .setTitleText(total)
                            .setContentText("It's pretty, isn't it?")
                            .show();

                } else if (cgp >= 7) {
                    result=7.4*cgp+12;
                    result=Math.round( result * 100.0 ) / 100.0;
                    // percentage.setText(Double.toString(result));
                    String total = String.valueOf(result);
                    new SweetAlertDialog(this)
                            .setTitleText(total)
                            .setContentText("It's pretty, isn't it?")
                            .show();

                }
            }

        }
    }

