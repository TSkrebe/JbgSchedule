package com.tvarkarastis.jbgschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Titas on 8/26/2014.
 */
public class MySqliteDatabase extends SQLiteOpenHelper {

    public static final String KEY_ROW_ID = "_id";
    public static final String KEY_FILE_NAME = "file_name";
    public static final String KEY_USERS_NAME = "user_name";

    private static final String DATABASE_NAME = "lentele";
    private static final String TABLE_NAME = "names";


    private static final String FIRST_TABLE_NAME = "failu_vardai";
    private static final int DATABASE_VERSION = 3;

    public MySqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" +
                KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FILE_NAME + " TEXT NOT NULL, " +
                KEY_USERS_NAME + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2)
            db.execSQL("drop table if exists " + FIRST_TABLE_NAME);
        if (newVersion == 3){
            db.execSQL("drop table if exists " + FIRST_TABLE_NAME);
            db.execSQL("drop table if exists " + TABLE_NAME);
        }
        onCreate(db);
    }

    public boolean ifExists(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_USERS_NAME}, KEY_USERS_NAME + "=?",
                new String[]{userName}, null, null, null);
        return cursor.moveToNext();
    }

    public void addEntry(StudentBean bean) {
     //   Log.i("llll", "lolo");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_FILE_NAME, bean.getFileName());
        cv.put(KEY_USERS_NAME, bean.getUserName());
        db.insert(TABLE_NAME, null, cv);
    }

    public void deleteEntry(String deleteName) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_NAME, KEY_USERS_NAME + " =?", new String[]{deleteName});
    }

    public ArrayList<String> getAllEntries(ArrayList<String> list) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_USERS_NAME}, null, null, null, null, null);
        list.clear();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        return list;
    }

    public void renameEntry(String oldName, String newName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERS_NAME, newName);
        db.update(TABLE_NAME, cv, KEY_USERS_NAME + "=?", new String[]{oldName});

    }

    public String getFileName(String title) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_FILE_NAME}, KEY_USERS_NAME + "=?",
                new String[]{title}, null, null, null, null);
        //Keyusername tik vienas buna
        return cursor.moveToFirst() ? cursor.getString(0) : null;
    }
}
