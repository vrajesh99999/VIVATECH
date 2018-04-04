package com.example.killer.vivatech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class openIMage extends AppCompatActivity {
    SimpleDraweeView draweeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
//The second parameter below is the default string returned if the value is not there.
        String imagelink = i.getExtras().getString("link","");
        super.onCreate(savedInstanceState);
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .build();
        Fresco.initialize(this, config);
        setContentView(R.layout.activity_open_image);

        Uri uri = Uri.parse(imagelink);
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);

        int width = 4096, height = 4096;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .build();
        draweeView.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
        draweeView.setController(controller);

    }

}
