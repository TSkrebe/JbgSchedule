package com.tvarkarastis.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tvarkarastis.R;

import java.util.ArrayList;


public class MainListAdapter extends ArrayAdapter<String> {

    Context c;

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    ArrayList<String> namesSurnames;

    public MainListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        c = context;
        namesSurnames = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(c).inflate(R.layout.list_view_layout, parent, false);
        TextView text = (TextView) view.findViewById(R.id.nameSurname);
        text.setText(namesSurnames.get(position));
        return view;
    }
}
