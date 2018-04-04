package com.example.killer.vivatech.achievements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.killer.vivatech.R;
import com.example.killer.vivatech.events.GalleryActivity;
import com.example.killer.vivatech.events.Webncrnb;


public class FragmentSix extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_six_events,container,false);
        Button start = (Button) v.findViewById(R.id.start);
        Button image = (Button) v.findViewById(R.id.image);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), Webncrnb.class);
                startActivity(intent);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), com.example.killer.vivatech.achievements.GalleryActivity.class);
                intent.putExtra(com.example.killer.vivatech.achievements.GalleryActivity.INTENT_EXTRAS_FOLDER, "file:///android_asset/ncrenb");
                startActivity(intent);


            }
        });


        return v;

    }

}
