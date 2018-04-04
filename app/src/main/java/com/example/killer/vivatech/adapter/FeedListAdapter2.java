package com.example.killer.vivatech.adapter;

/**
 * Created by killer on 1/31/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.killer.vivatech.R;
import com.example.killer.vivatech.ZoomableActivity;
import com.example.killer.vivatech.app.AppController;
import com.example.killer.vivatech.data.FeedItem2;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;


public class FeedListAdapter2 extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem2> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter2(Activity activity, List<FeedItem2> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.imageview, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView subject = (TextView) convertView.findViewById(R.id.subject);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        mehdi.sakout.fancybuttons.FancyButton button= (FancyButton) convertView.findViewById(R.id.feedImage1);

        final FeedItem2 item = feedItems.get(position);

        name.setText(item.getName());
        subject.setText(item.getsubject());
        // Converting timestamp into x ago format
        //CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
        //	Long.parseLong(item.getTimeStamp()),
        //	System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(item.getTimeStamp());

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }
        if(!item.getImge().equals("")){
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    Intent myIntent = new Intent(activity,ZoomableActivity.class);
                    String imagelink = item.getImge();
                    myIntent.putExtra("link", imagelink);
                    activity.startActivity(myIntent);
                }
            });
        }else{
            button.setVisibility(View.GONE);
        }


        // Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Feed image


        return convertView;
    }

}
