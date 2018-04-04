package com.example.killer.vivatech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Divyesh on 04-01-2016.
 */
public class Social extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Social");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void fb(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/CvWqwUY68VF"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/viva.technology?fref=ts")));
        }
    }

    public void twitter(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://plus.google.com/111285929308665134666/posts"));
            startActivity(intent);

        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://plus.google.com/111285929308665134666/posts")));
        }
    }

    public void youtube(View view) {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:3wsOJVXU6JI"));
            startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/user/VIVARND"));
            startActivity(intent);
        }
    }


    public void website(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.viva-technology.org")));

    }
}
