package com.example.shree.jsonsqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.shree.jsonsqlite.DbHelper.COLUMN_ID;

public class SecondActivity extends AppCompatActivity {

    DbHelper dbHelper;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private ArrayList<Data> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        dbHelper = new DbHelper(SecondActivity.this);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getData();
    }

    private void getData() {
        arrayList.clear();
        Cursor cursor = dbHelper.getNames();
        if (cursor.moveToFirst()) {
            do {
                Data name = new Data(
                        cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_EMAIL))
                );
                arrayList.add(name);
            } while (cursor.moveToNext());
        }

        recyclerAdapter = new RecyclerAdapter(arrayList,this);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
