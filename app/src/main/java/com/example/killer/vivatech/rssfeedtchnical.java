package com.example.killer.vivatech;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;
import com.pkmmte.pkrss.Article;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.DataFormatException;

public class rssfeedtchnical extends AppCompatActivity {
    private ListView mList;
    List<Article> articleList;
    String url="http://timesofindia.feedsportal.com/c/33039/f/533923/index.rss";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeedtchnical);
        String[] urlArr = {"http://feeds.bbci.co.uk/news/rss.xml"};
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
       String url="http://feeds.bbci.co.uk/news/rss.xml";


        InputStream inputStream = null;
        try {
            inputStream = new URL("http://feeds.feedburner.com/NDTV-Cricket").openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Feed feed = null;
        try {
            feed = EarlParser.parseOrThrow(inputStream, 0);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        Log.i("rss", "Processing feed: " + feed.getTitle());
        for (Item item : feed.getItems()) {
            String title = item.getTitle();
            String link=item.getLink();
            String image=item.getDescription();
            Log.i("Rss", "Item title: " + (title == null ? "N/A" : title));
            Log.i("Rss", "Item link: " + (title == null ? "N/A" : link));
            Log.i("Rss", "Item image link: " + (title == null ? "N/A" :image));

        }

    }
}
