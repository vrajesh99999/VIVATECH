package com.example.killer.vivatech;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class Notices extends AppCompatActivity {
    Toolbar toolbar;
    String[] s1;
    String[] ss;
    String[] datea;
    String[] timea;
    String url = "http://www.codepedia.xyz/vivapp/getnotice.php/";
    ProgressDialog progressDialog;
    TextView newsd;
    private ListView listView;
    public Context c=this;
    CustomList customList;
    TextView mTxtDisplay;
    Context c1=this;
    SwipeRefreshLayout swipeLayout;
    int color0= Color.parseColor("#009688");
    int color1= Color.parseColor("#E91E63");
    int color2= Color.parseColor("#2196F3");
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.swipe_container);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setWaveARGBColor(255,241, 67, 60);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean b=false;
                b=haveNetworkConnection();
                if(b) {
                    new Title(c1).execute();
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Working Internet ", Toast.LENGTH_LONG).show();
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        String url ="http://www.vrajesh.pixub.com/getnotice.php/";
       // swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
      //  swipeLayout.setOnRefreshListener(this);
      //  swipeLayout.setColorSchemeColors(color0,color1,color2);
        boolean b=false;
        b=haveNetworkConnection();
        if(b) {
            new Title(this).execute();
        }
        else{
            Toast.makeText(getApplicationContext(), "No Working Internet ", Toast.LENGTH_LONG).show();
        }


    }



    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notices, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

















    String result=null;


    private class Title extends AsyncTask<Void, Void, Void> {
        String title;
        private Context mContext;
        public Title (Context context){
            mContext = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressDialog = new ProgressDialog(Notices.this);
           // progressDialog.setTitle("Welcome to VIVA Institute of Technology");
          //  progressDialog.setMessage("Loading...");
          //  progressDialog.setIndeterminate(false);
          //  progressDialog.show();
            mWaveSwipeRefreshLayout.setRefreshing(true);
        }
        public void set(String s){
            newsd.setText(s);
        }

        @Override
        protected Void doInBackground(Void... params) {
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://www.vrajesh.pixub.com/getnotice.php/");


            try{
                HttpResponse response = client.execute(httpGet);
                    HttpEntity entity = response.getEntity();
                //System.out.print("bvbvbvbv");
                result= EntityUtils.toString(entity);
                //System.out.println(result);

                //JSONObject jsonObject=new JSONObject(result);
               // String name=jsonObject.getString("notice");
                //System.out.print(name);
                JSONArray jsonarray = new JSONArray(result);
                ss=new String[jsonarray.length()];
                s1=new String[jsonarray.length()];
                datea=new String[jsonarray.length()];
                timea=new String[jsonarray.length()];

                int j=jsonarray.length()-1;
                for(int i=0; i<jsonarray.length(); i++){
                    JSONObject obj = jsonarray.getJSONObject(i);

                    String name = obj.getString("notice");
                    String url = obj.getString("link");
                    String date = obj.getString("date");
                    String time = obj.getString("time");

                    System.out.println(name);
                    System.out.println(url);

                    ss[j]=obj.getString("link");
                    s1[j]=obj.getString("notice");
                    datea[j]=obj.getString("date");
                    timea[j]=obj.getString("time");
                    j--;
                }

            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.print("json error");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
         //   TextView txtTitle = (TextView) findViewById(R.id.titletxt);

            // newsd.setMovementMethod(new ScrollingMovementMethod());
           // txtTitle.setText(title);
            // newsd.setText("fdegsfdgs");
            int j=0;
            //  while(ss[j]!=null && s1[j]!=null){
            //newsd.setText("Notice="+s1[j]+"\t"+"link="+ss[j]+"\n");
            // System.out.println("post" + ss[j]);
            // j++;
            // }
            // while(){}


            customList = new CustomList((Activity) mContext,s1,ss,datea);
            listView = (ListView) findViewById(R.id.listView);listView.setAdapter(customList);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Toast.makeText(getApplicationContext(),"You Clicked "+ss[i], Toast.LENGTH_SHORT).show();
                    String link = ss[i];
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                    System.out.println(ss[i]);
                }
            });

           // progressDialog.dismiss();
            mWaveSwipeRefreshLayout.setRefreshing(false);

        }
    }



    public class CustomList extends ArrayAdapter<String> {
        private String[] names;
        private String[] desc;
        private String[] date;

        private Activity context;

        public CustomList(Activity context, String[] names, String[] desc,String[] date) {
            super(context, R.layout.list_layout, names);
            this.context=context;
            this.names = names;
            this.desc = desc;
            this.date=date;


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.update_list_layout, null, true);
            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
            TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
            TextView textViewDate = (TextView) listViewItem.findViewById(R.id.date);
            textViewName.setText(names[position]);
            textViewDesc.setText(desc[position]);
            textViewDate.setText(date[position]);




            return  listViewItem;
        }
    }
}
