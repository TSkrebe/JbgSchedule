package com.tvarkarastis.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.tvarkarastis.R;
import com.tvarkarastis.jbgschedule.MySqliteDatabase;
import com.tvarkarastis.jbgschedule.StudentBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class ChooseStudent extends ActionBarActivity implements View.OnClickListener {

    AutoCompleteTextView autoComplete;
    Button addNewStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_student);


        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, getNames());
        autoComplete.setAdapter(adapter);

        addNewStudent = (Button) findViewById(R.id.addNewStudentButton11_12);

        addNewStudent.setOnClickListener(this);
    }

    public ArrayList<String> getNames() {

        ArrayList<String> list = new ArrayList<String>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("namesonly.txt")));
            String name;
            while ((name = reader.readLine()) != null) {
                list.add(name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        addNewStudent.setEnabled(false);
        String studentName = autoComplete.getText().toString();
        String fileName = format(studentName);
        //tam kad nelagintu UI thread
        FinderThread thread = new FinderThread(fileName, studentName, this);
        thread.execute();

    }

    static char[][] replacement = new char[][]{
            {'ą', 'a'},
            {'č', 'c'},
            {'ę', 'e'},
            {'ė', 'e'},
            {'į', 'i'},
            {'š', 's'},
            {'ų', 'u'},
            {'ū', 'u'},
            {'ž', 'z'}};

    public static String format(String line) {
        line = line.toLowerCase(Locale.getDefault());
        for (char[] temp : replacement) {
            line = line.replace(temp[0], temp[1]);
        }
        return line.replace(" ", "");
    }

    public class FinderThread extends AsyncTask<String, Void, Boolean> {

        String fileName, studentName;
        Context context;

        public FinderThread(String fileName, String studentName, Context context) {
            super();
            this.fileName = fileName;
            this.studentName = studentName;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            //  boolean exists = false;
            try {
                // exists = Arrays.asList(getResources().getAssets().list("")).contains(fileName + ".txt");
                getAssets().open(fileName + ".txt");
            } catch (IOException e) {
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean exists) {
            if (exists) {
                MySqliteDatabase db = new MySqliteDatabase(context);
                db.addEntry(new StudentBean(fileName, studentName));
                //pranesame, kad mokinys sekmingai itrauktas
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Valio!!!");
                builder.setMessage("Mokinys vardu " + studentName + " sėkmingai įtrauktas į sąrašą.");
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.show();
                //ir
            }
            //pranesame, kad toks mokinys neegzistuoja
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Ooops!!!");
                builder.setMessage("Deja, bet mokinys vardu " + studentName + " neegzistuoja...");
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //nothing to do here...
                        addNewStudent.setEnabled(true);
                    }
                });

                builder.show();
            }
        }


    }
}
