package com.tvarkarastis.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tvarkarastis.jbgschedule.DayFragment;


public class ScheduleTabsAdapter extends FragmentPagerAdapter {
    DayFragment[] days;

    public ScheduleTabsAdapter(FragmentManager fm, DayFragment days[]) {
        super(fm);
        this.days = days;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return days[0];
            case 1:
                return days[1];
            case 2:
                return days[2];
            case 3:
                return days[3];
            case 4:
                return days[4];

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
