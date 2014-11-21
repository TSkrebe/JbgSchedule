package com.tvarkarastis.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tvarkarastis.R;
import com.tvarkarastis.jbgschedule.LessonBean;

import java.util.ArrayList;


public class LessonsListAdapter extends ArrayAdapter<LessonBean> {

    Context c;
    ArrayList<LessonBean> info;
    int id;

    public LessonsListAdapter(Context context, int resource, ArrayList<LessonBean> info, int id) {
        super(context, resource, info);
        c = context;
        this.id = id;
        this.info = info;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(c).inflate(R.layout.list_view_lessons_layout, parent, false);
        TextView lesson = (TextView) view.findViewById(R.id.lesson);
        TextView classNr = (TextView) view.findViewById(R.id.classNr);

        lesson.setText(position + ". " + info.get(position).getLessonName());
        classNr.setText(info.get(position).getClassNumber());

        if (id % 2 == 1) {
            int colorId = Color.WHITE;
            lesson.setTextColor(colorId);
            classNr.setTextColor(colorId);
        }
        return view;
    }

}
