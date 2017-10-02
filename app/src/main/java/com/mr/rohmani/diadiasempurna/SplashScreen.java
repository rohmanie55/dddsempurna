package com.mr.rohmani.diadiasempurna;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SplashScreen extends AppCompatActivity {

    private SQLiteDatabase db;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progress = (ProgressBar) findViewById(R.id.progress);

    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences settings = getSharedPreferences("com.mr.rohmani.ddds",MODE_PRIVATE);
        boolean first = settings.getBoolean("first", true);

        if (first){
            try {
                initData();
            }catch (Exception e){
                startActivity(new Intent(SplashScreen.this, Home.class));
                finish();
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("first", false)
                    .putBoolean("screen", false)
                    .putBoolean("mode", false)
                    .putInt("size", 16)
                    .putInt("color", 1)
                    .putInt("last", 0);
            editor.commit();
        } else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, Home.class));
                    finish();
                }
            }, 3000);
        }

    }

    private void initData() {
        dbHelper dbhelper = new dbHelper(this);
        db = dbhelper.getWritableDatabase();
        new doBackground().execute();
    }

    class doBackground extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            insertFromFile(SplashScreen.this, R.raw.ddds);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, Home.class));
                    finish();
                }
            }, 3000);
        }
    }

    public void insertFromFile(Context context, int resourceId) {
        try {
            InputStream insertsStream = context.getResources().openRawResource(resourceId);
            BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

            db.beginTransaction();

            while (insertReader.ready()) {
                String insertStmt = insertReader.readLine();
                db.execSQL(insertStmt);
            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

            insertReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
