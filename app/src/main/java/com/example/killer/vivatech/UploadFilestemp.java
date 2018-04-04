package com.example.killer.vivatech;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alexbbb.uploadservice.BinaryUploadRequest;
import com.alexbbb.uploadservice.MultipartUploadRequest;
import com.alexbbb.uploadservice.UploadNotificationConfig;
import com.alexbbb.uploadservice.UploadService;

import java.util.UUID;

public class UploadFilestemp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_filestemp);
        // setup the broadcast action namespace string which will
        // be used to notify upload status.
        // Gradle automatically generates proper variable as below.
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Or, you can define it manually.
        UploadService.NAMESPACE = "com.example.killer.vivatech";
    }

    public void uploadMultipart(final Context context) {

        final String uploadID = UUID.randomUUID().toString();
        final String serverUrlString = "http://www.yoursite.com/yourscript";

        try {
            new MultipartUploadRequest(context, uploadID, serverUrlString)
                    .addFileToUpload("/absolute/path/to/your/file", "your-param-name")
                    .addHeader("your-custom-header-name", "your-custom-value")
                    .addParameter("your-param-name", "your-param-value")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }

    public void uploadBinary(final Context context) {

        final String uploadID = UUID.randomUUID().toString();
        final String serverUrlString = "http://www.yoursite.com/yourscript";

        try {
            new BinaryUploadRequest(context, uploadID, serverUrlString)
                    .addHeader("your-custom-header-name", "your-custom-value")
                    .setFileToUpload("/absolute/path/to/your/file")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }
}
