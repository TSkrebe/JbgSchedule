package com.tvarkarastis.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tvarkarastis.R;
import com.tvarkarastis.adapters.MainListAdapter;
import com.tvarkarastis.jbgschedule.MySqliteDatabase;

import java.util.ArrayList;

public class Main extends ListActivity {
    MainListAdapter adapter;
    ArrayList<String> info = new ArrayList<String>();

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo infor =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final MySqliteDatabase db = new MySqliteDatabase(this);
        final String oldName = info.get(infor.position);
        switch (item.getItemId()) {
            //delete schedule
            case 0:
                db.deleteEntry(oldName);
                updateList();
                return true;
            //rename schedule
            case 1:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(oldName);
                alert.setMessage("Pervardinkite " + oldName);
                final EditText editText = new EditText(this);
                alert.setView(editText);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newName = editText.getText().toString();
                        if (!db.ifExists(newName)) {
                            db.renameEntry(oldName, newName);
                            updateList();
                        }
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                alert.show();

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo information = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(info.get(information.position));
        String[] menuItems = new String[]{"Trinti", "Pervadinti"};
        for (int i = 0; i < menuItems.length; i++) {
            menu.add(0, i, 0, menuItems[i]);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        adapter = new MainListAdapter(this, R.layout.list_view_layout, info);

        //add new student to our list
        Button addNewStudentButton = (Button) findViewById(R.id.addNewStudentButton);
        addNewStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ChooseClass.class);
                startActivity(intent);
            }
        });

        //look for information
        Button informationButton = (Button) findViewById(R.id.informationButton);
        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ScheduleTime.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(getListView());

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateList();
    }

    public void updateList() {
        MySqliteDatabase db = new MySqliteDatabase(this);
        //you know, it's a good practise to save memory so we just pass and return the same memory block
        info = db.getAllEntries(info);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        TextView text = (TextView) v.findViewById(R.id.nameSurname);
        String nameSurname = text.getText().toString();

        Intent studentSchedule = new Intent(this, Schedule.class);
        studentSchedule.putExtra("initials", nameSurname);
        startActivity(studentSchedule);
    }


}

