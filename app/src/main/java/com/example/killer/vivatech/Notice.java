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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Notice extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    Toolbar toolbar;
    String[] s1;
    String[] ss;
    String url = "http://www.viva-technology.org/New/";
    ProgressDialog progressDialog;
    TextView newsd;
    int color0= Color.parseColor("#009688");
    int color1= Color.parseColor("#E91E63");
    int color2= Color.parseColor("#2196F3");
    private ListView listView;
    public Context c=this;
    CustomList customList;
    SwipeRefreshLayout swipeLayout;
    SweetAlertDialog sweetAlertDialog,sdilog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        sweetAlertDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
         swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(color0, color1, color2);
        sdilog=new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        boolean b=false;
        b=haveNetworkConnection();
        if(b) {
            new Title(this).execute();
        }
        else{
            sdilog.setTitleText("No working Internet");
            sdilog.setConfirmText("Go back!");
            sdilog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();
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
    private Context mContext;

    @Override
    public void onRefresh() {
        boolean b=false;
        b=haveNetworkConnection();
        if(b) {
            new Title(this).execute();
        }
        else{
            Toast.makeText(getApplicationContext(),"No Working Internet ", Toast.LENGTH_LONG).show();
            swipeLayout.setRefreshing(false);
        }

    }


    private class Title extends AsyncTask<Void, Void, Void> {
        String title;
        private Context mContext;
        public Title (Context context){
            mContext = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            sweetAlertDialog.setTitleText("Loading Notices");
            sweetAlertDialog.setCancelable(true);
            sweetAlertDialog.show();
        }
        public void set(String s){
            newsd.setText(s);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document document;
                document = Jsoup.connect(url).timeout(0).get();
                title = document.title();


                Elements news=document.select("marquee");
                String html="null";
                for(Element lan:news){
                    html = lan.html();

                    //Element link = doc.select("a").first();
                    // String text = doc.body().text(); // "An example link"
                    // String linkHref = link.attr("href"); // "http://example.com/"
                    //String linkText = link.text(); // "example""
                    // System.out.println(text+"link "+linkHref);

                    String word = lan.text();
                    // System.out.println(html);
                    //int i=0;
                    // System.out.println(" "+lan.text());
                    //  String as=lan.toString();
                    // ss[i]=as;
                    String asd =lan.attr("href");
                    //System.out.println("asdasdasdsadasdasdsad"+asd);
                    //   i++;
                }
                Document doc = Jsoup.parse(html);
                Elements link = doc.select("a");
                int i=0;
                for(Element e:link) {
                    String text = doc.body().text(); // "An example link"
                    String linkHref = e.attr("href"); // "http://example.com/"
                    String linkText = e.text(); // "example"
//                    s1[i] = e.text();
                    //  ss[i]=e.attr("href");
                    // System.out.println(text);

                    i++;

                }
                s1=new String[i];
                ss=new String[i];
                i=0;
                for(Element e:link) {

                    s1[i] = e.text();
                    ss[i]=e.attr("href");
                    i++;
                    // System.out.println("notice=" + s1[i]+"\nlink=" + ss[i]);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
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



            customList = new CustomList((Activity) mContext,s1,ss);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(customList);

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
           sweetAlertDialog.dismiss();
            swipeLayout.setRefreshing(false);
        }

    }



    public class CustomList extends ArrayAdapter<String> {
        private String[] names;
        private String[] desc;

        private Activity context;

        public CustomList(Activity context, String[] names, String[] desc) {
            super(context, R.layout.list_layout, names);
            this.context=context;
            this.names = names;
            this.desc = desc;


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
            TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
            textViewName.setText(names[position]);
            textViewDesc.setText(desc[position]);

            return  listViewItem;
        }
    }


}
