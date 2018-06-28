package com.example.shree.jsonsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shree on 6/20/2018.
 */

public class DbHelper extends SQLiteOpenHelper{   // extends SQLiteOpenHelper

    public static final String DB_NAME = "officedb.db";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "office";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "userid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    private Context context;

    public static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE +"("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_USER_ID + " VARCHAR,"
            + COLUMN_NAME + " VARCHAR,"
            + COLUMN_EMAIL + " VARCHAR);";


    public DbHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void addData(int id,String name,String email){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);

        long statusId = db.insert(USER_TABLE,null,contentValues);
        db.close();
        Toast.makeText(context, String.valueOf(statusId), Toast.LENGTH_SHORT).show();

    }

    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + USER_TABLE + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
