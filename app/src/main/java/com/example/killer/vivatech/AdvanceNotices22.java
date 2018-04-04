package com.example.killer.vivatech;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.killer.vivatech.adapter.FeedListAdapter2;
import com.example.killer.vivatech.app.AppController;

import com.example.killer.vivatech.data.FeedItem2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class AdvanceNotices22 extends Activity {
    Toolbar toolbar;
    private static final String TAG = AdvanceNotices.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter2 listAdapter;
    private List<FeedItem2> feedItems;
    private String URL_FEED = "http://codepedia.xyz/vivapp/getnoticecard.php";
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_notices22);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Notices");
        //   setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem2>();

        listAdapter = new FeedListAdapter2(this,feedItems);
        listView.setAdapter(listAdapter);



        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
        // getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F1433C")));
        // getActionBar().setIcon(
        // new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);




        int color=Color.parseColor("#F1433C");

        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.swipe_container);
            mWaveSwipeRefreshLayout.setWaveARGBColor(255,241, 67, 60);
            mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
                @Override public void onRefresh() {
                    // Do work to refresh the list here.

                    JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                            URL_FEED, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            VolleyLog.d(TAG, "Response: " + response.toString());
                            if (response != null) {
                                parseJsonFeed(response);
                                mWaveSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                        }
                    });

                    // Adding request to volley request queue
                    AppController.getInstance().addToRequestQueue(jsonReq);

                }
            });
        }



        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.swipe_container);

        mWaveSwipeRefreshLayout.setWaveARGBColor(255, 241, 67, 60);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.

                JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                        URL_FEED, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG, "Response: " + response.toString());
                        if (response != null) {
                            parseJsonFeed(response);
                            mWaveSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });

                // Adding request to volley request queue
                AppController.getInstance().addToRequestQueue(jsonReq);

            }
        });
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            feedItems.clear();
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem2 item = new FeedItem2();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
                item.setsubject(feedObj.getString("subject"));
                String year=feedObj.getString("class");
                System.out.println(year);
                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);
                // if(year.equals("se comps")){
                feedItems.add(item);
                //}


            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
