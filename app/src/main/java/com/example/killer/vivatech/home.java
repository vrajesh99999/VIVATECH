package com.example.killer.vivatech;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.killer.vivatech.events.Events;

public class home extends AppCompatActivity {
    EditText username, name, age;
    Button logoutbutton;
    UserLocalStore userLocalStore;
    int color2 = Color.parseColor("#FF9800");
    int color1 = Color.parseColor("#4CAF50");
    int color0 = Color.parseColor("#F44336");
    Toolbar toolbar = null;
    ViewPager pager;
    AnimationDrawable rocketAnimation;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Home", "events", "Contact"};
    int Numboftabs = 3;
    Animation alpha = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userLocalStore = new UserLocalStore(this);


        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        //login= (ImageView) findViewById(R.id.login);


        ImageView imageView = (ImageView) findViewById(R.id.login);
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        int colorFrom = Color.parseColor("#D32F2F");;
        int colorTo =  Color.parseColor("#CDDC39");
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(10000);
        colorAnimation.setRepeatCount(1000);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                toolbar.setBackgroundColor((int) animator.getAnimatedValue());
                tabs.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {
            //displayUserdetail();
            ProgressDialog p = new ProgressDialog(this);
            p.setTitle("Processing");
            p.show();
            // Pushbots.sharedInstance().init(getApplicationContext());
            //  Pushbots.sharedInstance().setPushEnabled(true);
            //  Pushbots.sharedInstance().setAlias("vrajesh");
            startActivity(new Intent(this, student.class));
            p.dismiss();
            finish();
        }
        // else {
        //  startActivity(new Intent(this,Login.class));
        //  finish();
        // }
    }

    public boolean authenticate() {
        return userLocalStore.getUserLoggedIn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startlogin(View view) {
        Intent openlogin = new Intent(home.this, Login.class);
        startActivity(openlogin);

    }

    public void courses(View view) {
        if (NetStatus.getInstance(this).isOnline()) {
            Intent openlogin = new Intent(home.this, courses.class);
            startActivity(openlogin);
        } else {
            Toast.makeText(home.this, "Please connect to INTERNET,and try again !!!", Toast.LENGTH_LONG).show();

        }
    }

    public void events(View view) {
        Intent openlogin = new Intent(home.this,Events.class);
        startActivity(openlogin);
    }

    public void achievements(View view) {
        Intent openlogin = new Intent(home.this,com.example.killer.vivatech.achievements.Achievements.class);
        startActivity(openlogin);
    }

    public void fees(View view) {
        if (NetStatus.getInstance(this).isOnline()) {
            Intent openlogin = new Intent(home.this, fees.class);
            startActivity(openlogin);
        } else {
            Toast.makeText(home.this, "Please connect to INTERNET,and try again !!!", Toast.LENGTH_LONG).show();
        }
    }

    public void directory(View view) {
        Intent openlogin = new Intent(home.this, Directory.class);
        startActivity(openlogin);
    }

    public void aboutus(View view) {
        Intent openlogin = new Intent(home.this, Aboutus.class);
        startActivity(openlogin);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        System.out.println("done here aala dsgfefdsfdsfsdfsdf");
    }

    public void social(View view) {

            Intent openlogin = new Intent(home.this, Social.class);
            startActivity(openlogin);

    }

    public void maps(View view) {
        Intent openmaps = new Intent(home.this, Maps.class);
        startActivity(openmaps);
    }

    public void developers(View view) {
        Intent opendevs = new Intent(home.this, com.example.killer.vivatech.developers.Developers.class);
        startActivity(opendevs);
    }
    public void gallery(View view) {
        Intent opendevs = new Intent(home.this, com.example.killer.vivatech.gallery.gallery.class);
        startActivity(opendevs);
    }
}

