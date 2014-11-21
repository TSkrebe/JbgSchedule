package com.tvarkarastis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tvarkarastis.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Titas on 9/2/2014.
 */
public class ScheduleTimeAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> objects = new ArrayList<String>();

    public ScheduleTimeAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects.addAll(Arrays.asList(objects));

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_list_view, parent, false);
        TextView text = (TextView) view.findViewById(R.id.time);
        text.setText(objects.get(position));

        return view;
    }
}
