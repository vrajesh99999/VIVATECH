package com.example.killer.vivatech;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.killer.vivatech.events.Events;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.pushbots.push.Pushbots;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class student extends AppCompatActivity {
    //save our header or result
//for pushbot spinner
    int position1;
    String branch,branch1=null,finalbranch1;
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
    Spinner spnr,spnr1;

    int color1 = Color.parseColor("#1AFFFFFF");

 Toolbar toolbar=null;


    Bitmap bitmap1,bitmapfromestorage;
    IProfile profile;
    ProgressDialog progressDialog;
    private static final String SERVER_ADDRES="http://codepedia.xyz/vivapp/";
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    UserLocalStore userLocalStore;
    PushbotsLocalstore pushbotsLocalstore;
    ImageView downloadimage;
    User u,u1;
    private Drawer resultAppended = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        pushbotsLocalstore=new PushbotsLocalstore(this);
        userLocalStore=new UserLocalStore(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        u=new User("z","z","","","","","","");
        u=userLocalStore.getLoggedInUser();

        userLocalStore=new UserLocalStore(this);
        Pushbots.sharedInstance().init(this);
       // Pushbots.sharedInstance().tag(u.branch);
       // Pushbots.sharedInstance().setAlias(u.name);
        //Handle Toolbar
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar

        getSupportActionBar().setTitle("Home");

          System.out.println(u.name);
        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        File f=new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files/profilepic.jpg");
      if(f.exists()){
           loadImageFromStorage(mediaStorageDir.getPath());
      }else {
           new DownloadImage(u.name).execute();
           loadImageFromStorage(mediaStorageDir.getPath());
       }
        Toast.makeText(getApplicationContext(),u.profession,Toast.LENGTH_LONG).show();




        //bitmap1 is image loaded from storage
        final IProfile profile = new ProfileDrawerItem().withName(u.name).withIcon(bitmapfromestorage);
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.pushbotdialog);
        dialog.setTitle("Receive Push Notification");
        Button btnSave = (Button)dialog.findViewById(R.id.save);

        TextView t= (TextView) dialog.findViewById(R.id.textview);
        spnr = (Spinner) dialog.findViewById(R.id.spinner);
        spnr1 = (Spinner) dialog.findViewById(R.id.spinner1);
        t.setText(u.name);
        //for spinner
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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pushbots.sharedInstance().init(getApplicationContext());
                Pushbots.sharedInstance().regID();
                Pushbots.sharedInstance().setAlias(u.name);
                Pushbots.sharedInstance().tag(finalbranch1);
                Pushbots.sharedInstance().setNotificationEnabled(true);
                pushbotsLocalstore.setpushbotstatus("true");
                dialog.dismiss();
            }
        });
String status=pushbotsLocalstore.getpushbotstatus();
        if(status.equals("false")){
            dialog.show();
        }









        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //create the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
        crossfadeDrawerLayout = new CrossfadeDrawerLayout(this);
         crossfadeDrawerLayout.setBackgroundColor(color1);
        //crossfadeDrawerLayout.setStatusBarBackgroundColor(color1);
        //Create the drawer
        if(u.profession.endsWith("student")){
            result = new DrawerBuilder()
                    .withActivity(this)

                    .withToolbar(toolbar)
                    .withDrawerLayout(crossfadeDrawerLayout)
                    .withHasStableIds(true)
                    .withDrawerWidthDp(72)
                    .withGenerateMiniDrawer(true)
                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                    .addDrawerItems(
                            //new PrimaryDrawerItem().withName("Timetable").withIcon(GoogleMaterial.Icon.gmd_sun).withIdentifier(1),
                           // new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                           // new PrimaryDrawerItem().withName("Details").withIcon(FontAwesome.Icon.faw_info_circle).withIdentifier(2),
                            new PrimaryDrawerItem().withName("Notices For All").withIcon(FontAwesome.Icon.faw_newspaper_o).withIdentifier(1),

                            new PrimaryDrawerItem().withName("Updates").withIcon(FontAwesome.Icon.faw_hacker_news).withIdentifier(2),

                            new PrimaryDrawerItem().withName("Calculate CGPI").withIcon(FontAwesome.Icon.faw_calculator).withIdentifier(3),
                            new PrimaryDrawerItem().withName("Result").withIcon(FontAwesome.Icon.faw_smile_o).withIdentifier(4),
                            new PrimaryDrawerItem().withName("Send Email").withIcon(FontAwesome.Icon.faw_mail_forward).withIdentifier(5),
                                  new PrimaryDrawerItem().withName("Logout").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(6),
                            new PrimaryDrawerItem().withName("Assignment HUB").withIcon(FontAwesome.Icon.faw_book).withIdentifier(7)
                          //  new SectionDrawerItem().withName("1"),
                          //  new SecondaryDrawerItem().withName("2").withIcon(FontAwesome.Icon.faw_github),
                         //   new SecondaryDrawerItem().withName("Logout").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withIdentifier(7)

                    ) // add the items we want to use with our Drawer
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            if (position == 0) {
                                startActivity(new Intent(getApplicationContext(), home.class));
                            } else if (position == 0) {
                                startActivity(new Intent(getApplicationContext(), home.class));
                            }else if (position == 0) {
                                startActivity(new Intent(getApplicationContext(),Sdetails.class));
                            }else if (position == 1) {
                                startActivity(new Intent(getApplicationContext(),Notice.class));
                            }else if (position == 2) {
                                startActivity(new Intent(getApplicationContext(),AdvanceNotices22.class));
                            }
                            else if (position == 3) {
                                startActivity(new Intent(getApplicationContext(), cgpi.class));
                            }
                            else if (position == 4) {
                                startActivity(new Intent(getApplicationContext(),tempuniversity.class));
                            }
                            else if (position == 5) {
                                startActivity(new Intent(getApplicationContext(),sendemail.class));
                            }
                            else if (position == 6) {
                                delete(mediaStorageDir.getPath());
                                Intent openlogin1 = new Intent(getApplicationContext(),home.class);


                                userLocalStore.clearUserData();
                                userLocalStore.setUserLoggedIn(false);
                                Pushbots.sharedInstance().setPushEnabled(false);
                                pushbotsLocalstore.setpushbotstatus("false");
                                 finish();
                                startActivity(openlogin1);
                            } else if (position == 7) {
                                startActivity(new Intent(getApplicationContext(),com.example.killer.vivatech.drive.MainActivity.class));
                            }
                            //we do not consume the event and want the Drawer to continue with the event chain
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .withShowDrawerOnFirstLaunch(true)
                    .build();
        }else if(u.profession.endsWith("staff")){
            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withDrawerLayout(crossfadeDrawerLayout)
                    .withHasStableIds(true)
                    .withDrawerWidthDp(72)
                    .withGenerateMiniDrawer(true)
                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                    .addDrawerItems(
                        //    new PrimaryDrawerItem().withName("Timetable").withIcon(GoogleMaterial.Icon.gmd_sun).withIdentifier(1),
                         //   new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                         //   new PrimaryDrawerItem().withName("Details").withIcon(FontAwesome.Icon.faw_info_circle).withIdentifier(2),
                            new PrimaryDrawerItem().withName("Notices For All").withIcon(FontAwesome.Icon.faw_newspaper_o).withIdentifier(1),

                            new PrimaryDrawerItem().withName("Updates").withIcon(FontAwesome.Icon.faw_hacker_news).withIdentifier(2),


                         //   new SectionDrawerItem().withName("Staff"),
                         //   new SecondaryDrawerItem().withName("2").withIcon(FontAwesome.Icon.faw_github),
                            new PrimaryDrawerItem().withName("Send Push").withIcon(GoogleMaterial.Icon.gmd_notifications).withIdentifier(3),
                            new PrimaryDrawerItem().withName("Send Notice").withIcon(GoogleMaterial.Icon.gmd_notifications_active).withIdentifier(4),
                            new PrimaryDrawerItem().withName("Send Email").withIcon(FontAwesome.Icon.faw_mail_forward).withIdentifier(5),
                            new PrimaryDrawerItem().withName("Send Advance Notice").withIcon(GoogleMaterial.Icon.gmd_mail_send).withIdentifier(6),
                                   new PrimaryDrawerItem().withName("Calculate CGPI").withIcon(FontAwesome.Icon.faw_calculator).withIdentifier(7),
                                    new PrimaryDrawerItem().withName("Result").withIcon(FontAwesome.Icon.faw_smile_o).withIdentifier(8),
                            new PrimaryDrawerItem().withName("Logout").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(9)

                    ) // add the items we want to use with our Drawer
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            if (position == 0) {
                                startActivity(new Intent(getApplicationContext(), home.class));
                            } else if (position == 0) {
                                startActivity(new Intent(getApplicationContext(), home.class));
                            } else if (position == 0) {
                                startActivity(new Intent(getApplicationContext(), Sdetails.class));
                            } else if (position == 1) {
                                startActivity(new Intent(getApplicationContext(), Notice.class));
                            } else if (position == 2) {
                                startActivity(new Intent(getApplicationContext(), AdvanceNotices22.class));
                            }  else if (position == 3) {
                                startActivity(new Intent(getApplicationContext(), Staffsendpush.class));
                              //  Toast.makeText(getApplicationContext(), "qwe", Toast.LENGTH_LONG).show();
                            }
                            else if (position == 4) {
                                startActivity(new Intent(getApplicationContext(), Staffsendnotice.class));
                                //Toast.makeText(getApplicationContext(), "qwe", Toast.LENGTH_LONG).show();
                            }
                            else if (position == 5) {
                                startActivity(new Intent(getApplicationContext(), sendemail.class));
                                //Toast.makeText(getApplicationContext(), "qe", Toast.LENGTH_LONG).show();
                            }
                            else if (position == 6) {
                                startActivity(new Intent(getApplicationContext(), AdvanceNotice.class));
                                //Toast.makeText(getApplicationContext(), "qwe", Toast.LENGTH_LONG).show();
                            }else if (position == 7) {
                                startActivity(new Intent(getApplicationContext(),cgpi.class));
                            }
                            else if (position == 8) {
                                startActivity(new Intent(getApplicationContext(),tempuniversity.class));
                            }
                            else if (position == 9) {
                                delete(mediaStorageDir.getPath());
                                Intent openlogin1 = new Intent(getApplicationContext(), home.class);

                                userLocalStore.clearUserData();
                                userLocalStore.setUserLoggedIn(false);
                                Pushbots.sharedInstance().setPushEnabled(false);
                                pushbotsLocalstore.setpushbotstatus("false");
                                finish();
                                startActivity(openlogin1);
                            }
                            //we do not consume the event and want the Drawer to continue with the event chain
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .withShowDrawerOnFirstLaunch(true)
                    .build();
        }else if(false){
            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withDrawerLayout(crossfadeDrawerLayout)
                    .withHasStableIds(true)
                    .withDrawerWidthDp(72)
                    .withGenerateMiniDrawer(true)
                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName("Timetable").withIcon(GoogleMaterial.Icon.gmd_sun).withIdentifier(1),
                            new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home).withIdentifier(2),
                            new PrimaryDrawerItem().withName("Details").withIcon(FontAwesome.Icon.faw_info_circle).withIdentifier(3),
                            new PrimaryDrawerItem().withName("Notices").withIcon(FontAwesome.Icon.faw_newspaper_o).withIdentifier(4),

                            new PrimaryDrawerItem().withName("Updates").withIcon(FontAwesome.Icon.faw_hacker_news).withIdentifier(5),
                            new PrimaryDrawerItem().withName("Logout").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(6),
                            new SectionDrawerItem().withName("1"),
                            new SecondaryDrawerItem().withName("2").withIcon(FontAwesome.Icon.faw_github),
                            new SecondaryDrawerItem().withName("Logout").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withIdentifier(7)

                    ) // add the items we want to use with our Drawer
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            if (position == 1) {
                                startActivity(new Intent(getApplicationContext(), result.class));
                            } else if (position == 2) {
                                startActivity(new Intent(getApplicationContext(), home.class));
                            }else if (position == 3) {
                                startActivity(new Intent(getApplicationContext(),Sdetails.class));
                            }else if (position == 4) {
                                startActivity(new Intent(getApplicationContext(),Notice.class));
                            }else if (position == 5) {
                                startActivity(new Intent(getApplicationContext(),Notices.class));
                            }
                            else if (position == 6) {
                                delete(mediaStorageDir.getPath());
                                Intent openlogin1 = new Intent(getApplicationContext(),home.class);
                                startActivity(openlogin1);

                                userLocalStore.clearUserData();
                                userLocalStore.setUserLoggedIn(false);
                                Pushbots.sharedInstance().setPushEnabled(false);
                                pushbotsLocalstore.setpushbotstatus("false");
                            }
                            //we do not consume the event and want the Drawer to continue with the event chain
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .withShowDrawerOnFirstLaunch(true)
                    .build();
        }


        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
            this.finish();
        }
    }



    //download image
    private  class DownloadImage extends AsyncTask<Void,Void,Bitmap> {
        String name;

        public DownloadImage(String name){
            this.name=name;

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            name=name.replaceAll("\\s+","%20");
            String url=SERVER_ADDRES + "pictures/" +name +".JPG";

            System.out.println("after"+name);
            try{
                URLConnection connection = new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);

                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(student.this);
            progressDialog.setTitle("Image Downloading");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
           // progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                bitmap1=bitmap;
                //downloadimage.setImageBitmap(bitmap1);
                //Intent openlogin13 = new Intent(getApplicationContext(),uploadpic.class);
                // startActivity(openlogin13);
                storeImage(bitmap);
            }else{
                //Toast.makeText(getApplicationContext(),"NO particular image",Toast.LENGTH_SHORT).show();
            }
           // progressDialog.dismiss();
        }
    }




    private File getOutputMediaFile(){
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

    private boolean loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profilepic.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            bitmapfromestorage = b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return true;
    }
    private void delete(String path){
        try {
            File f=new File(path, "profilepic.jpg");
            f.delete();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Toast.makeText(getApplicationContext(),"Error creating media file, check storage permissions: ",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            bitmapfromestorage=bitmap1;
            Toast.makeText(getApplicationContext(),"File done: ",Toast.LENGTH_SHORT).show();
            bitmapfromestorage=bitmap1;
            fos.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"File not found: ",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Error accessing file: ",Toast.LENGTH_SHORT).show();

        }
    }



    public void courses(View view) {
        if (NetStatus.getInstance(this).isOnline()) {
            Intent openlogin = new Intent(student.this, courses.class);
            startActivity(openlogin);
        } else {
            Toast.makeText(student.this, "Please connect to INTERNET,and try again !!!", Toast.LENGTH_LONG).show();

        }
    }

    public void events(View view) {
        Intent openlogin = new Intent(student.this,Events.class);
        startActivity(openlogin);
    }

    public void fees(View view) {
        if (NetStatus.getInstance(this).isOnline()) {
            Intent openlogin = new Intent(student.this, fees.class);
            startActivity(openlogin);
        } else {
            Toast.makeText(student.this, "Please connect to INTERNET,and try again !!!", Toast.LENGTH_LONG).show();
        }
    }

    public void directory(View view) {
        Intent openlogin = new Intent(student.this, Directory.class);
        startActivity(openlogin);
    }

    public void aboutus(View view) {
        Intent openlogin = new Intent(student.this, Aboutus.class);
        startActivity(openlogin);
    }




    public void social(View view) {

        Intent openlogin = new Intent(student.this, Social.class);
        startActivity(openlogin);

    }

    public void maps(View view) {
        Intent openmaps = new Intent(student.this, Maps.class);
        startActivity(openmaps);
    }

    public void developers(View view) {
        Intent opendevs = new Intent(student.this, com.example.killer.vivatech.developers.Developers.class);
        startActivity(opendevs);
    }
    public void achievements(View view) {
        Intent openlogin = new Intent(student.this,com.example.killer.vivatech.achievements.Achievements.class);
        startActivity(openlogin);
    }
    public void gallery(View view) {
        Intent openlogin = new Intent(student.this,com.example.killer.vivatech.gallery.gallery.class);
        startActivity(openlogin);
    }
}

