package com.tvarkarastis.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.tvarkarastis.R;
import com.tvarkarastis.adapters.ScheduleTabsAdapter;
import com.tvarkarastis.jbgschedule.DayFragment;
import com.tvarkarastis.jbgschedule.LessonBean;
import com.tvarkarastis.jbgschedule.MySqliteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class Schedule extends ActionBarActivity implements ActionBar.TabListener {

    Timer timer;
    DayFragment[] days = new DayFragment[5];
    String[] dayNames = new String[]{"Pirm", "Antr", "Treč", "Ketv", "Penk"};

    ViewPager pager;
    static TextView timeInfo;
    ActionBar actionBar;
    //data to send to timer
    ArrayList<ArrayList<String>> lessons = new ArrayList<ArrayList<String>>();

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        String title = getIntent().getExtras().getString("initials");
        setTitle(title);

        pager = (ViewPager) findViewById(R.id.sliding_tabs);
        timeInfo = (TextView) findViewById(R.id.infoAboutTime);

        //initiate action bar
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //give names to tabs
        for (String d : dayNames) {
            actionBar.addTab(actionBar.newTab().setTabListener(this).setText(d));
        }

        //collect schedule info
        MySqliteDatabase db = new MySqliteDatabase(this);
        String fileName = db.getFileName(title);

        try {
            //checks if file exists. new versions do not contain old schedules NEEEDD TO MAKE
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getResources().getAssets().open(fileName + ".txt")));
            for (int i = 0; i < 5; i++) {
                ArrayList<String> temp = new ArrayList<String>();
                ArrayList<LessonBean> bean = new ArrayList<LessonBean>();
                for (int j = 0; j <= 7; j++) {
                    String[] info = reader.readLine().split("\\^");
                    LessonBean bloke = new LessonBean();
                    if (info.length == 0) {
                        bloke.setLessonName("");
                        bloke.setClassNumber("");
                    } else if (info.length == 1) {
                        bloke.setLessonName(info[0]);
                        bloke.setClassNumber("");
                    } else {
                        bloke.setLessonName(info[0]);
                        bloke.setClassNumber(info[1]);
                    }
                    temp.add(bloke.getLessonName());
                    bean.add(bloke);
                }
                lessons.add(temp);
                //add data to fragment
                DayFragment day = new DayFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("array", bean);
                bundle.putInt("id", i);
                day.setArguments(bundle);
                days[i] = day;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //set up pager view
        ScheduleTabsAdapter adapter = new ScheduleTabsAdapter(getSupportFragmentManager(), days);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //nustatome dabartine diena
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
            pager.setCurrentItem(0);
        } else {
            pager.setCurrentItem(day - 2);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         *
         * cia nuspalviname pamokas; parasome kiek liko laiko iki pamokos, pertraukos.
         * intervalas 30 sec
         *
         */

        timer = new Timer();
        timer.schedule(new MyTimerTask(Schedule.this, lessons), 0, 30000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    /**
     * implementing tabs listener
     */

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        pager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }


    private static class MyTimerTask extends TimerTask {

        String infoText;
        Activity context;
        ArrayList<ArrayList<String>> lessons;
        final static int MINUTES_IN_DAY = 60 * 24;
        int[] intervals = new int[]
                {8 * 60 + 10, 8 * 60 + 55,
                        9 * 60 + 5, 9 * 60 + 50,
                        10 * 60, 10 * 60 + 45,
                        11 * 60 + 5, 11 * 60 + 50,
                        12 * 60 + 15, 13 * 60,
                        13 * 60 + 10, 13 * 60 + 55,
                        14 * 60 + 5, 14 * 60 + 50,
                        15 * 60, 15 * 60 + 45};

        protected MyTimerTask(Activity context, ArrayList<ArrayList<String>> lessons) {
            super();
            this.lessons = lessons;
            this.context = context;
        }

        @Override
        public void run() {

            Calendar calendar = Calendar.getInstance();

            int day = calendar.get(Calendar.DAY_OF_WEEK);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);

            int timeInMinutes = hour * 60 + minutes;

            infoText = "Dabar: ";
            //jei savaitgalis
            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                infoText += "laisvas laikas";
            } else {

                int firstLessonTime = getFirstLessonTime(day - 2),
                        lastLessonTime = getLastLessonTime(day - 2);

                //jei pirma dienos pamoka dar neprasidejo
                if (timeInMinutes < firstLessonTime) {
                    infoText += "laisvas laikas\nPo " + MyTimerTask.timeBetween(timeInMinutes, firstLessonTime, 0) +
                            " " + getFirstLesson(day - 2);
                }
                //jei vyksta pamokos
                else if (timeInMinutes >= firstLessonTime && timeInMinutes < lastLessonTime) {
                    infoText += getNowAction(day - 2, timeInMinutes);

                }
                //jei dienos pamokos pasibaige
                else {
                    infoText += "laisvas laikas";
                    if (day != Calendar.FRIDAY) {
                        infoText += "\nPo " + MyTimerTask.timeBetween(0, getFirstLessonTime(day - 1), MINUTES_IN_DAY - timeInMinutes) +
                                " " + getFirstLesson(day - 1);
                    }

                }
            }

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timeInfo.setText(infoText);
                }
            });

        }

        private String getNowAction(int day, int nowTime) {
            for (int i = 0; i < intervals.length - 1; i++) {
                if (nowTime >= intervals[i] && nowTime < intervals[i + 1]) {
                    String info;
                    if (i % 2 == 0) {
                        String nowLesson = lessons.get(day).get(i / 2);
                        if (nowLesson.equals(""))
                            info = "laisva pamoka";
                        else {
                            info = nowLesson;
                        }
                        return info + "\nPo " + MyTimerTask.timeBetween(nowTime, intervals[i + 1], 0) + " pertrauka";
                    } else {
                        info = "pertrauka\nPo " + MyTimerTask.timeBetween(nowTime, intervals[i + 1], 0) + " ";
                        String lesson = lessons.get(day).get((i / 2) + 1);
                        if (lesson.equals("")) {
                            info += "laisva pamoka";
                        } else {
                            info += lesson;
                        }
                        return info;
                    }
                }
            }
            return null;
        }

        private String getFirstLesson(int day) {
            for (String lesson : lessons.get(day)) {
                if (!lesson.equals("")) {
                    return lesson;
                }
            }
            return null;
        }

        private static String timeBetween(int firstTime, int secondTime, int additional) {
            int timeInMinutes = secondTime - firstTime + additional;
            int hours = timeInMinutes / 60;
            int minutes = timeInMinutes % 60;
            if (hours == 0)
                return minutes + "min.";
            return hours + "h. " + minutes + "min.";
        }

        private int getFirstLessonTime(int pos) {
            ArrayList<String> day = lessons.get(pos);
            for (int i = 0; i < day.size(); i++) {
                if (!day.get(i).equals("")) {
                    return intervals[i * 2];
                }
            }
            return 0;
        }

        private int getLastLessonTime(int pos) {

            ArrayList<String> day = lessons.get(pos);
            for (int i = day.size() - 1; i >= 0; i--) {
                if (!day.get(i).equals("")) {
                    return intervals[i * 2 + 1];
                }
            }
            return 0;
        }

    }
}