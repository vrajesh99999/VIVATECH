package com.example.killer.vivatech.achievements;

/**
 * Created by Divyesh on 17-01-2016.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.killer.vivatech.R;

//import com.etsy.android.grid.StaggeredGridView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new StaggeredGridView.GridLayoutParams(100,400));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.photo1,R.drawable.photo10,R.drawable.blur,R.drawable.clg,
             };
}
