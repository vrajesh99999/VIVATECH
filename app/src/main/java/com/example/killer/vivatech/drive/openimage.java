package com.example.killer.vivatech.drive;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.example.killer.vivatech.R;
import com.example.killer.vivatech.samples.zoomable.ZoomableDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class openimage extends Activity {
    final File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
            + "/Android/data/"
            +"com.example.killer.vivatech"
            + "/Files");
    SimpleDraweeView draweeView;
   // PhotoViewAttacher mAttacher;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .build();
        Fresco.initialize(this, config);
        setContentView(R.layout.activity_openimage);


        File f=new File(mediaStorageDir.getPath(), "Temp.jpg");

        Uri uri = Uri.fromFile(f);
        ZoomableDraweeView view = (ZoomableDraweeView) findViewById(R.id.zoomable);
        int width = 4096, height = 4096;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController ctrl = Fresco.newDraweeControllerBuilder().setUri(
                uri).setTapToRetryEnabled(true)
                .setImageRequest(request)
                .build();
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();

        view.setController(ctrl);
        view.setHierarchy(hierarchy);
       // loadImageFromStorage(mediaStorageDir.getPath());

    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "Temp.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            imageView.setImageBitmap(b);
           // mAttacher = new PhotoViewAttacher(imageView);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
