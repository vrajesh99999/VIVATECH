package com.example.killer.vivatech;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class VrAdapter extends RecyclerView.Adapter<VrAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ClickListner clicklistner;
    List<Information> data = Collections.emptyList();

    Context context1;
    public VrAdapter(Context context,List<Information> data){
        inflater= LayoutInflater.from(context);
        context1=context;
        this.data=data;

    }
public void setClicklistener(ClickListner clicklistener){
    this.clicklistner=clicklistener;
}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view=inflater.inflate(R.layout.custom_row, parent, false);
        Log.d("vra", "oncreateholder called");
        MyViewHolder holder =new MyViewHolder(view);
        return holder;
    }
    Intent intent;
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Information current=data.get(position);
        Log.d("vra", "onbindeholder called" + position);
    holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
     /*   holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        intent = new Intent(context1, result.class);
                        break;
                }
                context1.startActivity(intent);
                Toast.makeText(context1, "clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        intent = new Intent(context1, result.class);
                        break;
                }
                context1.startActivity(intent);
                Toast.makeText(context1, "clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title= (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View v) {

            if (clicklistner!=null ) {
                clicklistner.itemClicked(v,getPosition());
            }
        }
    }
public interface ClickListner{
    public void itemClicked(View view, int position);
}
    }

