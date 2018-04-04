package com.example.killer.vivatech;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pushbots.push.Pushbots;


public class Students extends AppCompatActivity {
Toolbar toolbar;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_appbar);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

       NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawerlayout),toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Pushbots.sharedInstance().init(this);
        Pushbots.sharedInstance().setPushEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_students, menu);
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
            Intent openlogin1 = new Intent(this,home.class);
            startActivity(openlogin1);
            userLocalStore=new UserLocalStore(this);
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            Pushbots.sharedInstance().setPushEnabled(false);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        userLocalStore=new UserLocalStore(this);
        if(!userLocalStore.getUserLoggedIn()){
            this.finish();

        }
    }
}
