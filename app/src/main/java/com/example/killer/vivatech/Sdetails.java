package com.example.killer.vivatech;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pushbots.push.Pushbots;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sdetails extends AppCompatActivity implements View.OnClickListener{
    private static final String SERVER_ADDRES="http://codepedia.xyz/vivapp/";
    Spinner spnr,spnr1;
    int position1;
    String branch,branch1=null,finalbranch1;
  String username;
    EditText password,mobilno,year,name,email;
    UserLocalStore userLocalStore;
    PushbotsLocalstore PushbotsLocalstore;
    ImageView profilepic;
String url="http://codepedia.xyz/vivapp/filldetails.php";
ProgressDialog progressDialog; private static final int RESULT_LOAD_IMAGE=1;
    String[] branchtype = {
            "Select Branch",
            " Comps",
            " Mech",
            " Extc",
            " Elct",
            " Civil",
    };
    String[] branchtype1 = {
            "Select Class",
            " BE",
            " TE",
            " SE",
    };
    SharedPreferences sharedpreferences;
     LinearLayout llaLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(Build.VERSION.SDK_INT<20){
            setContentView(R.layout.sdetailsapi20);
            llaLayout = (LinearLayout)findViewById(R.id.slayout);
        }else {
            setContentView(R.layout.activity_sdetails);
            llaLayout = (LinearLayout)findViewById(R.id.slayout);
        }
        llaLayout = (LinearLayout)findViewById(R.id.slayout);

        int colorFrom = Color.parseColor("#009688");;
        int colorTo =  Color.parseColor("#E91E63");
        final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(7000);
        colorAnimation.setRepeatCount(1000);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                llaLayout.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
        userLocalStore=new UserLocalStore(this);
        PushbotsLocalstore=new PushbotsLocalstore(this);
        User user1=userLocalStore.getLoggedInUser();
        username=user1.name;
        Pushbots.sharedInstance().init(this);
        password= (EditText) findViewById(R.id.newpassword);
        mobilno= (EditText) findViewById(R.id.mobileno);
        year= (EditText) findViewById(R.id.year);
        name= (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        spnr = (Spinner) findViewById(R.id.spinner);
        spnr1 = (Spinner) findViewById(R.id.spinner1);
        profilepic= (ImageView) findViewById(R.id.profilepic);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, branchtype);
        spnr.setAdapter(adapter);
        spnr.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnr.getSelectedItemPosition();
                       // Toast.makeText(getApplicationContext(), "You have selected " + branchdetails[+position], Toast.LENGTH_SHORT).show();
                        switch (position) {

                            case 0:
                                branch = "Select Class";
                                break;
                            case 1:
                                branch = " Comps";
                                break;
                            case 2:
                                branch = " Mech";
                                break;
                            case 3:
                                branch = " Extc";
                                break;
                            case 4:
                                branch = " Elect";
                                break;
                            case 5:
                                branch = " Civil";
                                break;
                        }
                        System.out.println("branch " + branch);
                        position1=position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        Toast.makeText(getApplicationContext(), "select branch ", Toast.LENGTH_LONG).show();

                    }

                }
        );


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, branchtype1);

        spnr1.setAdapter(adapter1);
        spnr1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnr1.getSelectedItemPosition();
                        branch1 = null;
                        // Toast.makeText(getApplicationContext(), "You have selected " + branchdetails[+position], Toast.LENGTH_SHORT).show();
                        switch (position) {

                            case 0:
                                branch1 = "Select Class";
                                break;
                            case 1:
                                branch1 = "BE";
                                break;
                            case 2:
                                branch1 = "TE";
                                break;
                            case 3:
                                branch1 = "SE";
                                break;

                        }
                        System.out.println("branch " + branch1 + branch);
                        position1 = position;
                        finalbranch1 = branch1 + branch;
                        System.out.println("branch " + finalbranch1);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        Toast.makeText(getApplicationContext(), "select branch ", Toast.LENGTH_LONG).show();

                    }

                }
        );



    }
public void storeuserdetail(View view){
    if(position1==0){
        Toast.makeText(getApplicationContext(), "Select Class ", Toast.LENGTH_SHORT).show();
    }
    else {
        System.out.println("branch " + finalbranch1);
        Pushbots.sharedInstance().tag(finalbranch1);
        Pushbots.sharedInstance().setAlias(name.getText().toString());
        String email1,newpassword, years, names, mobno, datafill = "true";
        newpassword = password.getText().toString();
        years = year.getText().toString();
        names = name.getText().toString();

        mobno = mobilno.getText().toString();
        email1 =email.getText().toString();
        try{
            Bitmap image=((BitmapDrawable)profilepic.getDrawable()).getBitmap();
            storeImage(image);

            new UploadImage(image,names).execute();

        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"please l IMAGE UPLOADED",Toast.LENGTH_SHORT).show();
        }
        User user1=userLocalStore.getLoggedInUser();
        user1.name=names;
        user1=new User(user1.username,newpassword,email1,user1.profession,names,mobno,years,"yes");
        userLocalStore.StoreUserData(user1);
       // userLocalStore.StoreUserData(user1);
if (user1.profession.equals("staff")){
    filldetails(user1.username, names, newpassword, email1, mobno, branch,user1.profession);
    PushbotsLocalstore.setpushbotstatus("true");
}else {
    filldetails(user1.username, names, newpassword, email1, mobno, finalbranch1, user1.profession);
    PushbotsLocalstore.setpushbotstatus("true");
}


       // User user = new User(username, newpassword, branch, mobno, names, years, datafill);
      //  ServerRequests serverRequests = new ServerRequests(this,"profession");
       // serverRequests.StoreUserDetailsAsyncTask(user);
    }

}

public void storeImage1(View v){
    Intent galleryIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
}




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode== RESULT_OK && data !=null){
            Uri selecterImage=data.getData();
            profilepic.setImageURI(selecterImage);
        }
    }

    @Override
    public void onClick(View v) {

    }


    private class UploadImage extends AsyncTask<Void,Void,Void> {
        Bitmap image;
        String name;

        public UploadImage(Bitmap image,String name){
            this.image=image;
            this.name=name;
        }
        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            String encodedImage= Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend=new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name",name));

            HttpParams httpRequestParams=getHttpRequestParams();

            HttpClient client=new DefaultHttpClient(httpRequestParams);
            HttpPost post=new HttpPost(SERVER_ADDRES+"Savepic.php");

            try{

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);


                //saving in internal storage




            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Sdetails.this);
            progressDialog.setTitle("Image Uploading");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"IMAGE UPLOADED",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), student.class));
            finish();
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
            HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
            return httpRequestParams;
        }

    }








    //image saving function
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Toast.makeText(getApplicationContext(),"Error creating media file, check storage permissions: ",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(getApplicationContext(),"File done: ",Toast.LENGTH_SHORT).show();
            fos.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"File not found: ",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Error accessing file: ",Toast.LENGTH_SHORT).show();

        }
    }


    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage Directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());

        //String mImageName="profilepic"+ timeStamp +".jpg";
        File mediaFile;
        String mImageName="profilepic"+".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profilepic.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

           // downloadedImage.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }




    public void filldetails(final String username, final String fullname, final String password, final String email, final String mobno, final String year,final String profession) {
        System.out.println(username+fullname+password+email+mobno+year);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response.toString());
                        Toast.makeText(Sdetails.this, response, Toast.LENGTH_LONG).show();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(Sdetails.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("fullname",fullname);
                params.put("password",password);
                params.put("email",email);
                params.put("mobno",mobno);
                params.put("year", year);
                params.put("profession",profession);
                params.put("datafill","yes");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);




    }

}
