package com.example.killer.vivatech;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Person> persons;
    private Activity activity;

    public RecyclerAdapter(Activity activity, List<Person> persons) {
        this.persons = persons;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_directory, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        viewHolder.name.setText(persons.get(position).getName());
        viewHolder.number.setText(persons.get(position).getNumber());

    }


    @Override
    public int getItemCount() {
        return (null != persons ? persons.size() : 0);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView number;
        private View container;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            number = (TextView) view.findViewById(R.id.number);
            container = view.findViewById(R.id.card_view);
        }
    }
}