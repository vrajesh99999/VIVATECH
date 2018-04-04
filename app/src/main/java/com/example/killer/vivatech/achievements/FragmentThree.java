package com.example.killer.vivatech.achievements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.example.killer.vivatech.R;
import com.example.killer.vivatech.events.*;
import com.example.killer.vivatech.events.GalleryActivity;

/**
 * Created by Divyesh on 06-01-2016.
 */
public class FragmentThree extends Fragment {
    WebView myWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_three_achie,container,false);
        Button start = (Button) v.findViewById(R.id.start);
        Button image = (Button) v.findViewById(R.id.image);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(),WebHitaishi.class);
                startActivity(intent);


            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), com.example.killer.vivatech.events.GalleryActivity.class);
                intent.putExtra(GalleryActivity.INTENT_EXTRAS_FOLDER, "file:///android_asset/achie");
                startActivity(intent);
            }
        });




      //  myWebView = (WebView) v.findViewById(R.id.webView_csi);
        //myWebView.loadUrl("http://vrajesh.pixub.com/vivawebsite/techchase-15.html");
        return v;

    }
}
