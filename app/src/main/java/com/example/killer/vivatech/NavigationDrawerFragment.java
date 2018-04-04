package com.example.killer.vivatech;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushbots.push.Pushbots;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements VrAdapter.ClickListner {
     private RecyclerView recyclerview;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstantState;
    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private View containerView;
    private  VrAdapter adapter;
    UserLocalStore userLocalStore;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer= Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if(savedInstanceState!=null){
            mFromSavedInstantState=true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerview= (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter =new VrAdapter(getActivity(),getData());
        adapter.setClicklistener(this);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }
public static List<Information> getData(){
    List<Information> data=new ArrayList<>();
    int[] icons={R.drawable.grade,R.drawable.grade,R.drawable.grade,R.drawable.grade};
    String[] titles={"vrajesh","Details","Logout","notices"};
    for(int i=0;i<titles.length && i<icons.length;i++){
        Information current=new Information();
        current.iconId=icons[i%icons.length];
        current.title=titles[i%titles.length];
        data.add(current);
    }
    return data;
}

    public void setUp(int id, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView =getActivity().findViewById(id);

        mDrawerLayout =drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer&&!mFromSavedInstantState){
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }
    public static void saveToPreferences(Context context,String preferenceName,String preferenceValue){
        SharedPreferences sharedPrefences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPrefences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();

    }
    public static String readFromPreferences(Context context,String preferenceName,String defaultValue) {
        SharedPreferences sharedPrefences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPrefences.getString(preferenceName,defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {
        switch (position){
            case 0:startActivity(new Intent(getActivity(),result.class));
                break;
            case 1:startActivity(new Intent(getActivity(),Sdetails.class));
                break;
            case 2:logout(view);
                Intent openlogin1 = new Intent(getActivity(),home.class);
                startActivity(openlogin1);
            case 3:System.out.println("3");
                Intent openlogin13 = new Intent(getActivity(),Notices.class);
                startActivity(openlogin13);
        }
 }
    public void logout(View view){
        userLocalStore=new UserLocalStore(getActivity());
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);
        Pushbots.sharedInstance().setPushEnabled(false);

    }
}
