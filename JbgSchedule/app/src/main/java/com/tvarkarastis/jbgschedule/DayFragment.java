package com.tvarkarastis.jbgschedule;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tvarkarastis.R;
import com.tvarkarastis.adapters.LessonsListAdapter;

import java.util.ArrayList;


public class DayFragment extends Fragment {
    LessonsListAdapter lessonsAdapter = null;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            @SuppressWarnings("unchecked")
            ArrayList<LessonBean> array = (ArrayList<LessonBean>) bundle.getSerializable("array");
            id = bundle.getInt("id");
            lessonsAdapter = new LessonsListAdapter((Context) getActivity(), R.layout.list_view_lessons_layout, array, id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //skirtingos spalvos
        View view = inflater.inflate(R.layout.day_layout, container, false);

        if (id % 2 == 0) {
            view.setBackgroundColor(getResources().getColor(R.color.back_color_yellow));

        } else {
            view.setBackgroundColor(getResources().getColor(R.color.back_color_green));
        }

        ListView list = (ListView) view.findViewById(R.id.lessonslist);
        list.setAdapter(lessonsAdapter);

        return view;
    }
}
