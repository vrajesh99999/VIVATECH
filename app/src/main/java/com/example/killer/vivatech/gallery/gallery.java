package com.example.killer.vivatech.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;

import java.util.ArrayList;


/**
 * Created by killer on 2/8/2016.
 */
public class gallery extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onViewGalleryClicked();
    }
    public void onViewGalleryClicked() {
        Intent intent = new Intent(gallery.this, ImageGalleryActivity.class);

        ArrayList<String> images = new ArrayList<>();
        images.add("file:///android_asset/gallery/photo.jpg");
        images.add("file:///android_asset/gallery/photo1.jpg");
        images.add("file:///android_asset/gallery/photo4.jpg");
        images.add("file:///android_asset/gallery/library.jpg");
        images.add("file:///android_asset/hitaishi/hit1.jpg");
        images.add("file:///android_asset/hitaishi/hit2.jpg");
        images.add("file:///android_asset/hitaishi/hit3.jpg");
        images.add("file:///android_asset/hitaishi/hit6.jpg");
        images.add("file:///android_asset/techchase/tech1.jpg");
        images.add("file:///android_asset/techchase/tech2.jpg");
        images.add("file:///android_asset/techchase/tech3.jpg");
        images.add("file:///android_asset/techchase/tech4.jpg");


        intent.putStringArrayListExtra("images", images);
        // optionally set background color using Palette
        intent.putExtra("palette_color_type", PaletteColorType.VIBRANT);

        startActivity(intent);
        finish();
    }
    // endregion

    // region Lifecycle Methods

    // endregion
}