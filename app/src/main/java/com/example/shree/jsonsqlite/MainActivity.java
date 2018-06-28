package com.example.shree.jsonsqlite;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.shree.jsonsqlite.DbHelper.COLUMN_EMAIL;
import static com.example.shree.jsonsqlite.DbHelper.COLUMN_ID;
import static com.example.shree.jsonsqlite.DbHelper.COLUMN_NAME;
import static com.example.shree.jsonsqlite.DbHelper.USER_TABLE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // dbHelper = new DbHelper(MainActivity.this);

        SQLiteDataBaseBuild();

        SQLiteTableBuild();

        DeletePreviousData();
    }

    private void DeletePreviousData() {
        sqLiteDatabase.execSQL("DELETE FROM "+DbHelper.USER_TABLE+"");
    }

    private void SQLiteTableBuild() {
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+
//                DbHelper.USER_TABLE+"("+
//                DbHelper.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
//                DbHelper.COLUMN_NAME+" VARCHAR, "+
//                DbHelper.COLUMN_EMAIL+" VARCHAR);");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+
                DbHelper.USER_TABLE+"("+
                DbHelper.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                DbHelper.COLUMN_USER_ID+" VARCHAR, "+
                DbHelper.COLUMN_NAME+" VARCHAR, "+
                DbHelper.COLUMN_EMAIL+" VARCHAR);");

    }

    private void SQLiteDataBaseBuild() {
        sqLiteDatabase = openOrCreateDatabase(DbHelper.DB_NAME, Context.MODE_PRIVATE, null);
    }

    public void saveData(View view) {
        StoreJSonDataInToSQLite();
    }

    private void StoreJSonDataInToSQLite() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.androidhive.info/contacts/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse: "+response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("name");
                                String email = jsonObject1.getString("email");

                                String SQLiteDataBaseQueryHolder = "INSERT INTO "+DbHelper.USER_TABLE+" (userid,name,email) VALUES ('"+id+"', '"+name+"', '"+email+"');";

                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "Data Save Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: "+error.getMessage());
            }
        }){};

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    public void DisplayData(View view) {
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }
}