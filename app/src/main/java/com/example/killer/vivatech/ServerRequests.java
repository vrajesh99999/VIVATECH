package com.example.killer.vivatech;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by vrajesh on 5/2/2015.
 */
public class ServerRequests extends Activity {

    ProgressDialog progressDialog;
    String htmlResponse;
    public static final int CONNECTION_TIMEOUT=100*15;
    public static final String SERVER_ADDRESS="http://codepedia.xyz/vivapp/";
    Context c;
    String profession,profession1;
    UserLocalStore userLocalStore;
   // public ServerRequests(String profession){
     //   this.profession=profession;
     //   System.out.println("yfkevvbjhdvjbkdshbjdshjdsfghdshjgdsf sdchgydsghds 0"+this.profession);
   // }

    public ServerRequests(Context context,String profession){
      progressDialog=new ProgressDialog(context);
        c=context;
        Toast to=new Toast(context);
        System.out.println("yfkevvbjhdvjbkdshbjdshjdsfghdshjgdsf sdchgydsghds 0"+this.profession);
        this.profession=profession;
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Proccessing");
        progressDialog.setMessage("please wait...");
    }
    public void  storeUserDataInBackground(User user,GetUserCallbcak usercallback){

        progressDialog.show();
          new StoreUserDataAsyncTask(user,usercallback).execute();
    }
    SweetAlertDialog sweetAlertDialog;
    public void  fetchUserDataInBackground(User user,GetUserCallbcak callback){
        sweetAlertDialog=new SweetAlertDialog(c,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setTitleText("Proccessing...");
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();
        new fetchUserDataAsyncTask(user,callback).execute();
    }
    public void  StoreUserDetailsAsyncTask(User user){
        progressDialog.show();
        new StoreUserDetailsAsyncTask(user).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void,Void,Void> {
        User user;
        GetUserCallbcak userCallbcak;
        public StoreUserDataAsyncTask(User user,GetUserCallbcak userCallbcak){
            this.user=user;
            this.userCallbcak=userCallbcak;
        }
        @Override
        public Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend=new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name",user.name));
            dataToSend.add(new BasicNameValuePair("age",user.age+""));
            dataToSend.add(new BasicNameValuePair("username",user.username));
            dataToSend.add(new BasicNameValuePair("password",user.password));

            HttpParams httpRequestParams=new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client=new DefaultHttpClient(httpRequestParams);
            HttpPost post=new HttpPost(SERVER_ADDRESS+"vishal.php");
            HttpResponse httpResponse;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                httpResponse= client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                 htmlResponse = EntityUtils.toString(entity);
                System.out.println(htmlResponse);
                if("user exist".equals(htmlResponse)){

                    progressDialog.dismiss();

               }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //progressDialog.dismiss();
            if("user exist".equals(htmlResponse)) {
                Toast.makeText(c, "Username has been already taken", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(c, "Account created successfully Enjoy.", Toast.LENGTH_SHORT).show();
                userCallbcak.done(null);
                super.onPostExecute(aVoid);
            }

        }

    }


    public class fetchUserDataAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        String profession1=profession;
        GetUserCallbcak  userCallbcak;
        public fetchUserDataAsyncTask(User user,GetUserCallbcak  userCallbcak){
            this.user=user;
            this.userCallbcak= userCallbcak;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend=new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username",user.username));
            dataToSend.add(new BasicNameValuePair("password",user.password));
            dataToSend.add(new BasicNameValuePair("profession",profession1));
            System.out.println("yfkevvbjhdvjbkdshbjdshjdsfghdshjgdsf sdchgydsghds 0"+profession1);
            HttpParams httpRequestParams=new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client=new DefaultHttpClient(httpRequestParams);
            HttpPost post=new HttpPost(SERVER_ADDRESS+"getUserData.php");
            User returnedUser=null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse= client.execute(post);
                HttpEntity entity=httpResponse.getEntity();
                String result= EntityUtils.toString(entity);
                System.out.println(result);
                System.out.println(user.username);

                JSONObject jsonObject=new JSONObject(result);
                if (jsonObject.length()==0){
                    returnedUser=null;

                }
                else{
                    Log.v("happened", "2");
                    String name=jsonObject.getString("username");
                    String datafill=jsonObject.getString("datafill");
                    int age=0;
                    System.out.println(datafill);
                   // int age= jsonObject.getInt("age");
                    returnedUser=new User(name,age,user.username,user.password,datafill,"");
                    System.out.println(returnedUser);
                   // Pushbots.sharedInstance().debug(true);
                    return returnedUser;


                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            super.onPostExecute(returnedUser);
           // progressDialog.dismiss();
            sweetAlertDialog.dismiss();
            userCallbcak.done(returnedUser);

        }
    }


    public class fetchUsermarksAsyncTask extends AsyncTask<Void,Void,Void> {
        public fetchUsermarksAsyncTask(){

        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend=new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username","vrajesh"));
            dataToSend.add(new BasicNameValuePair("tablename","sem1"));

            HttpParams httpRequestParams=new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client=new DefaultHttpClient(httpRequestParams);
            HttpPost post=new HttpPost(SERVER_ADDRESS+"getusermarks.php");
            User returnedUser=null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse= client.execute(post);
                HttpEntity entity=httpResponse.getEntity();
                String result= EntityUtils.toString(entity);
                JSONObject jsonObject=new JSONObject(result);
                if (jsonObject.length()==0){
                    returnedUser=null;

                }
                else{
                    Log.v("happened", "2");
                    String sooad=jsonObject.getString("sooad");
                    int sooad1= jsonObject.getInt("term1sooad");
                   //returnedUser=new User(name,age,user.username,user.password);
                    System.out.println(sooad+""+sooad1);


                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
           return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(null);
            sweetAlertDialog.dismiss();


        }


    }



    public class StoreUserDetailsAsyncTask extends AsyncTask<Void,Void,Void> {
        User user;
        GetUserCallbcak userCallbcak;
        public StoreUserDetailsAsyncTask(User user ){
            this.user=user;

        }
        @Override
        public Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend=new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("branch",user.branch));
            dataToSend.add(new BasicNameValuePair("username",user.username));
            dataToSend.add(new BasicNameValuePair("password",user.password));
            dataToSend.add(new BasicNameValuePair("pic",user.pic));
            dataToSend.add(new BasicNameValuePair("year",user.year));
            dataToSend.add(new BasicNameValuePair("datafill",user.datafill));
            dataToSend.add(new BasicNameValuePair("mobno",user.mobno+""));

            HttpParams httpRequestParams=new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client=new DefaultHttpClient(httpRequestParams);
            HttpPost post=new HttpPost(SERVER_ADDRESS+"storeuserdetailnew.php");
            HttpResponse httpResponse;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                httpResponse= client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                htmlResponse = EntityUtils.toString(entity);
                System.out.println(htmlResponse);
                if("user exist".equals(htmlResponse)){

                    progressDialog.dismiss();

                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            if("user exist".equals(htmlResponse)) {
                Toast.makeText(c, "Username has been already taken", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(c, "details storedsuccessfully Enjoy.", Toast.LENGTH_SHORT).show();
                super.onPostExecute(aVoid);
            }

        }

    }

}
