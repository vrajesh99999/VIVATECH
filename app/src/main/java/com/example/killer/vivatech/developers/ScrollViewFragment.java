package com.example.killer.vivatech.developers;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.killer.vivatech.R;

public class ScrollViewFragment extends ScrollTabHolderFragment implements NotifyingScrollView.OnScrollChangedListener {

    private static final String ARG_POSITION = "position";


    private NotifyingScrollView mScrollView;

    TextView title;
    TextView titleShortDescription;
    TextView titleDescription;
    TextView textSendEmail;
    TextView textContact;
    TextView textEmail;
    LinearLayout layout1;
    LinearLayout layout2;

    ImageView titleImage;

    private int mPosition;
    private CardView cardView;

    public static Fragment newInstance(int position) {
        ScrollViewFragment f = new ScrollViewFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scrollview, null);

        mScrollView = (NotifyingScrollView) v.findViewById(R.id.scrollview_dev);
        mScrollView.setOnScrollChangedListener(this);

        layout1 = (LinearLayout) v.findViewById(R.id.layout1);
        layout2 = (LinearLayout) v.findViewById(R.id.layout2);
        titleDescription = (TextView) v.findViewById(R.id.titleDescription);
        titleShortDescription = (TextView) v.findViewById(R.id.titleShortDescription);
        title = (TextView) v.findViewById(R.id.title);

        textContact = (TextView) v.findViewById(R.id.textContact);
        textEmail = (TextView) v.findViewById(R.id.textEmail);
        textSendEmail = (TextView) v.findViewById(R.id.textSendEmail);


        textSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textEmail.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:" + email));
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(emailIntent);
            }
        });

        titleImage = (ImageView) v.findViewById(R.id.titleImage);
        cardView = (CardView) v.findViewById(R.id.cardView);
        cardView.setPadding(30, 30, 30, 30);

        if (mPosition == 0) {
            layout1.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.blue_transparent));
            titleDescription.setText("We have developed this project under the guidance of Prof. Siddhesh Doiphode.\n" +
                    "Prof. Siddhesh Doiphode had done many research in the field of Algorithms and Data Structures");
            title.setText("Mr. Sidhesh Doiphode");
            titleShortDescription.setText("Professor (VIVA Institute of Technology)");
            titleImage.setImageResource(R.drawable.sir);
            textContact.setText("Mr. Siddhesh Doiphode");
            textContact.setTextSize(21);
            textEmail.setText("doiphodesiddhesh@gmail.com");
            textEmail.setTextSize(17);
            textSendEmail.setText("Send Me Email Here <--");

            textSendEmail.setTextColor(getResources().getColor(R.color.blue_transparent));
        }
        if (mPosition == 1) {
            layout1.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.pink_transparent));
            titleDescription.setText("I am Vrajesh Akabari, currently studying in Final Year of Computer Engineering at VIVA Institute of Technology.\n" +
                    "I'm a developer and designer living in Mumbai.\n" +
                    "I love to code and build apps for Android.\n" +
                    "My first love was my computer...!");
            title.setText("Vrajesh Akabari");
            titleShortDescription.setText("B.E. Computer Engineering");
            titleImage.setImageResource(R.drawable.vraj);
            textContact.setText("Vrajesh Akabari");
            textEmail.setText("vrajesh99999@gmail.com");
            textSendEmail.setText("Send Me Email Here <--");
            textSendEmail.setTextColor(getResources().getColor(R.color.pink_transparent));

        }
        if (mPosition == 2) {
            layout1.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.green_transparent));
            titleDescription.setText("I am Divyesh Dhedhi, currently studying in Final Year of Computer Engineering at VIVA Institute of Technology.\n" +
                    "I'm a developer and designer living in Mumbai.\n" +
                    "I love to design stuff and build apps for the Web and Android.\n" +
                    "I love to spend time with my computer.");
            title.setText("Divyesh Dhedhi");
            titleShortDescription.setText("B.E. Computer Engineering");
            titleImage.setImageResource(R.drawable.div);
            textContact.setText("Divyesh Dhedhi");
            textEmail.setText("dddhedhi@gmail.com");
            textSendEmail.setText("Send Me Email Here <--");
            textSendEmail.setTextColor(getResources().getColor(R.color.green_transparent));

        }
        if (mPosition == 3) {

            layout1.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            layout2.setBackgroundColor(getResources().getColor(R.color.red_transparent));
            titleDescription.setText("I am Vishal Rabadiya, currently studying in Final Year of Computer Engineering at VIVA Institute of Technology.\n" +
                    "I'm a developer and designer living in Mumbai.\n" +
                    "I love to play video games.\n" +
                    " CS it's my life, hell yess!!");
            title.setText("Vishal Rabadiya");
            titleShortDescription.setText("B.E. Computer Engineering");
            titleImage.setImageResource(R.drawable.vish);
            textContact.setText("Vishal Rabadiya");
            textEmail.setText("rabadiya.vishal@yahoo.com");
            textSendEmail.setText("Send Me Email Here <--");
            textSendEmail.setTextColor(getResources().getColor(R.color.red_transparent));

        }


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY) {
        mScrollView.setScrollY(headerTranslationY - scrollHeight);
    }

    @Override
    public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt) {
        if (mScrollTabHolder != null)
            mScrollTabHolder.onScroll(view, l, t, oldl, oldt, mPosition);

    }


}
