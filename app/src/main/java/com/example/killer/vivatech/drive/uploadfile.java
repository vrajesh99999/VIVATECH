package com.example.killer.vivatech.drive;

import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by killer on 1/30/2016.
 */



public class uploadfile extends BaseDemoActivity{

    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_RESOLUTION = 3;

    private GoogleApiClient mGoogleApiClient;
    private Bitmap mBitmapToSave;


    private void saveFileToDrive() {
        // Start by creating a new contents, and setting a callback.

        final Bitmap image = mBitmapToSave;
        Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {

                    @Override
                    public void onResult(DriveApi.DriveContentsResult result) {
                        // If the operation was not successful, we cannot do anything
                        // and must
                        // fail.
                        if (!result.getStatus().isSuccess()) {

                            return;
                        }
                        // Otherwise, we can write our data to the new contents.

                        // Get an output stream for the contents.
                        OutputStream outputStream = result.getDriveContents().getOutputStream();
                        // Write the bitmap data from it.
                        ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
                        File file=new File("");
                        try {
                            outputStream.write(bitmapStream.toByteArray());
                        } catch (IOException e1) {

                        }
                        // Create the initial metadata - MIME type and title.
                        // Note that the user will be able to change the title later.
                        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                                .setMimeType("image/jpeg").setTitle("new.png").build();
                        // Create an intent for the file chooser, and start it.
                        IntentSender intentSender = Drive.DriveApi
                                .newCreateFileActivityBuilder()
                                .setInitialMetadata(metadataChangeSet)
                                .setInitialDriveContents(result.getDriveContents())
                                .build(mGoogleApiClient);
                        try {
                            startIntentSenderForResult(
                                    intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {

                        }
                    }
                });
    }

    private static final String TAG = "PickFileWithOpenerActivity";

    private static final int REQUEST_CODE_OPENER = 1;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
      //  Intent filePickerIntent = new Intent(this, FilePickerActivity.class);
       // filePickerIntent.putExtra(FilePickerActivity.REQUEST_CODE, FilePickerActivity.REQUEST_FILE);
       // startActivityForResult(filePickerIntent, FilePickerActivity.REQUEST_FILE);
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //if(requestCode == FilePickerActivity.REQUEST_FILE
             //   && resultCode == RESULT_OK) {

           // String filePath = data.
                 //   getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            // Now we need to set the GUI ImageView data with data read from the picked file.
            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();


            if(filePath != null) {
                //do something with filePath...
                File file = new File(imagePath);
                FileInputStream fileInputStream=null;
                final byte[] bFile = new byte[(int) file.length()];

                try {
                    //convert file into array of bytes
                    fileInputStream = new FileInputStream(file);
                    fileInputStream.read(bFile);
                    fileInputStream.close();

                    for (int i = 0; i < bFile.length; i++) {
                        System.out.print((char)bFile[i]);
                    }

                    System.out.println("Done");
                }catch(Exception e){
                    e.printStackTrace();
                }
                if (mGoogleApiClient == null) {
                    // Create the API client and bind it to an instance variable.
                    // We use this instance as the callback for connection and connection
                    // failures.
                    // Since no account name is passed, the user is prompted to choose.
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Drive.API)
                            .addScope(Drive.SCOPE_FILE)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                }
                // Connect the client. Once connected, the camera is launched.
                mGoogleApiClient.connect();
                Drive.DriveApi.newDriveContents(mGoogleApiClient)
                        .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {

                            @Override
                            public void onResult(DriveApi.DriveContentsResult result) {
                                // If the operation was not successful, we cannot do anything
                                // and must
                                // fail.
                                if (!result.getStatus().isSuccess()) {
                                //    Log.i(TAG, "Failed to create new contents.");
                                    return;
                                }
                                // Otherwise, we can write our data to the new contents.
                             //   Log.i(TAG, "New contents created.");
                                // Get an output stream for the contents.
                                OutputStream outputStream = result.getDriveContents().getOutputStream();
                                // Write the bitmap data from it.
                               // ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
                               // image.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
                               // File file=new File("");
                                try {
                                    outputStream.write(bFile);
                                } catch (IOException e1) {
                                    //Log.i(TAG, "Unable to write file contents.");
                                }
                                // Create the initial metadata - MIME type and title.
                                // Note that the user will be able to change the title later.
                                MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                                        .setTitle("new").build();
                                // Create an intent for the file chooser, and start it.
                                IntentSender intentSender = Drive.DriveApi
                                        .newCreateFileActivityBuilder()
                                        .setInitialMetadata(metadataChangeSet)
                                        .setInitialDriveContents(result.getDriveContents())
                                        .build(mGoogleApiClient);
                                try {
                                    startIntentSenderForResult(
                                            intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                                } catch (IntentSender.SendIntentException e) {
                                  //  Log.i(TAG, "Failed to launch file chooser.");
                                }
                            }
                        });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
       // startActivity(new Intent(getApplicationContext(), MainActivity.class));
       finish();
    }
}
