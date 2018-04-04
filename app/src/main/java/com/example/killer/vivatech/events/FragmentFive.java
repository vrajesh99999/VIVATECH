package com.example.killer.vivatech.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.killer.vivatech.R;


public class FragmentFive extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_five_events,container,false);
        Button start = (Button) v.findViewById(R.id.start);
        Button image = (Button) v.findViewById(R.id.image);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(),WebIIIE.class);
                startActivity(intent);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GalleryActivity.class);
                intent.putExtra(GalleryActivity.INTENT_EXTRAS_FOLDER, "file:///android_asset/iiie");
                startActivity(intent);


            }
        });


        return v;

    }

}
