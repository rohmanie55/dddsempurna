package com.mr.rohmani.diadiasempurna;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mr.rohmani.diadiasempurna.model.storyModel;

public class Pages extends AppCompatActivity {

    private int part;
    private String title, body;
    private TextView tv_content;
    private SharedPreferences settings;
    private dbHelper dbhelper;
    private storyModel story = new storyModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);

        settings = getSharedPreferences("com.mr.rohmani.ddds",MODE_PRIVATE);
        dbhelper = new dbHelper(Pages.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.cardview_light_background));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        if(i.getExtras()!=null){
            part = i.getIntExtra("part",0);
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("last", part);
        editor.commit();

        story = dbhelper.getStory(part);

        body = story.getBody();
        title = story.getTitle();

        body = body.replace("\\n", System.getProperty("line.separator"));

        tv_content = (TextView) findViewById(R.id.tv_konten);
        tv_content.setText(body);
        setTitle(title);
    }

    @Override
    public void onStart() {
        super.onStart();
        int mSize = settings.getInt("size", 16);
        int mColor= settings.getInt("color", 1);
        boolean mScreen = settings.getBoolean("screen", false);
        if(mScreen){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        tv_content.setTextSize(mSize);
        tv_content.setTextColor(getMyColor(mColor));
    }

    private int getMyColor(int color){
        int colorId=0;
        switch (color){
            case 0 : colorId=getResources().getColor( R.color.colorWhite);
                break;
            case 1 : colorId=getResources().getColor(R.color.colorBlack);
                break;
            case 2 : colorId=getResources().getColor(R.color.colorPrimary);
                break;
            case 3 : colorId=getResources().getColor(R.color.colorYelow);
                break;
            case 4 : colorId=getResources().getColor(R.color.colorRed);

        }
        return colorId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Pages.this, Settings.class));
            return true;
        }else if (id == R.id.action_add_bookmark){
            new AlertDialog.Builder(this)
                    .setMessage("Tambahkan "+ title +" Ke Penanda?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbhelper.insertBookmark(part, title);
                            Toast.makeText(Pages.this, "Berhasil..", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
