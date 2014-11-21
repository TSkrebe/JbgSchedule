package com.tvarkarastis.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.tvarkarastis.R;
import com.tvarkarastis.jbgschedule.MySqliteDatabase;
import com.tvarkarastis.jbgschedule.StudentBean;

public class ChooseClassLetter10 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_class_letter10);
    }

    //called in xml
    public void onClickAction(View view) {
        String letter = ((Button) view).getText().toString();
        MySqliteDatabase db = new MySqliteDatabase(this);
        if (!db.ifExists(10 + letter)) {
            db.addEntry(new StudentBean(String.valueOf(letter + 2).toLowerCase(), 10 + letter));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Valio!!!");
            builder.setMessage(10 + letter + " klasės tvarkaraštis sėkmingai įtrauktas į sąrašą.");
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ooops!!!");
            builder.setMessage(10 + letter + " klasės tvarkaraštis jau yra sąraše.");
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //nothing to do
                }
            });
            builder.show();
        }
    }

}
