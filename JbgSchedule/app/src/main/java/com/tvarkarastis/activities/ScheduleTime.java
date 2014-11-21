package com.tvarkarastis.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.ListView;

import com.tvarkarastis.R;
import com.tvarkarastis.adapters.ScheduleTimeAdapter;

public class ScheduleTime extends ActionBarActivity {

    String[] info = new String[]{"0. 8.10 - 8.55",
            "1. 9.05 - 9.50",
            "2. 10.00 - 10.45",
            "3. 11.05 - 11.50",
            "4. 12.15 - 13.00",
            "5. 13.10 - 13.55",
            "6. 14.05 - 14.50",
            "7. 15.00 - 15.45"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule_time);
        ListView list = (ListView) findViewById(R.id.schedule_time_list);
        ScheduleTimeAdapter adapter = new ScheduleTimeAdapter(this, R.layout.schedule_list_view, info);
        list.setAdapter(adapter);
    }
}
