package com.example.killer.vivatech;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class sendemail extends Activity {
    Spinner spnr,spnr1;
    int position1;
    String url = "http://codepedia.xyz/vivapp/getemails.php";
    String emails="hello";
  public   StringBuilder builder = new StringBuilder();
String emaillist=null;

    String branch,branch1=null;
    String finalbranch,finalbranch1;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendemail);
        spnr = (Spinner) findViewById(R.id.spinner);
        spnr1 = (Spinner) findViewById(R.id.spinner1);
        mehdi.sakout.fancybuttons.FancyButton startBtn = (FancyButton) findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail(finalbranch);
            }
        });



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
                        position1 = position;
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

    protected void sendEmail(String finalbranch) {
       // System.out.println(emaillist);
        Log.i("Send email", "");

        String[] TO = {emaillist};
        String[] CC = {"vrajesh99999@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("vrajesh99999@gmail.com"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Message?");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(sendemail.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public void getmaillist(View view) {
        //final ProgressDialog pd=new ProgressDialog(this);
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading Email list");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Toast.makeText(sendemail.this,response,Toast.LENGTH_LONG).show();
                        JSONArray array = null;
                        try {
                            array = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for(int i=0; i<array.length(); i++){
                            JSONObject jsonObj  = null;
                            try {
                                jsonObj = array.getJSONObject(i);
                                String tempemail=jsonObj.getString("email");
                                //System.out.println(jsonObj.getString("email"));
                                String array1[]=new String[array.length()];
                                array1[i]=jsonObj.getString("email");
                                //  emails=emails.concat("," + array1[i]);
                                System.out.println(array1[i]);
                                builder.append(array1[i] + ",");
                                //System.out.println(builder);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        emaillist=builder.toString();
                        System.out.println(emaillist);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(sendemail.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("year",finalbranch1);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

       // System.out.println(emaillist);



        finalbranch = emaillist;
    }
}
