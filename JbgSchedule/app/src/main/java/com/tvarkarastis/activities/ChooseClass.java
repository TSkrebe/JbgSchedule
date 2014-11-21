package com.tvarkarastis.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;

import com.tvarkarastis.R;


public class ChooseClass extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_class);

    }

    public void onClass9Clicked(View v) {
        Intent intent = new Intent(this, ChooseClassLetter9.class);
        startActivity(intent);
        finish();
    }

    public void onClass10Clicked(View v) {
        Intent intent = new Intent(this, ChooseClassLetter10.class);
        startActivity(intent);
        finish();
    }

    public void onClass1112Clicked(View v) {
        Intent intent = new Intent(this, ChooseStudent.class);
        startActivity(intent);
        finish();
    }

}
