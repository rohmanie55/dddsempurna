package com.mr.rohmani.diadiasempurna;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    private Switch switch_screen, switch_mode;
    private Spinner sp_size, sp_color;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        boolean mScreen, mMode;
        int mSize, mColor;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        switch_screen = (Switch) findViewById(R.id.switch_screen);
        switch_mode = (Switch) findViewById(R.id.switch_mode);
        sp_size = (Spinner) findViewById(R.id.spinner_size);
        sp_color = (Spinner) findViewById(R.id.spinner_warna);

        settings = getSharedPreferences("com.mr.rohmani.ddds",MODE_PRIVATE);

        editor = settings.edit();

        mScreen = settings.getBoolean("screen", false);
        mMode = settings.getBoolean("mode", false);
        mSize = settings.getInt("size", 16);
        mColor= settings.getInt("color", 1);

        if (mScreen) {
            switch_screen.setChecked(true);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }else{
            switch_screen.setChecked(false);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if (mMode){
            switch_mode.setChecked(true);
        }else {
            switch_mode.setChecked(false);
        }

        switch_screen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("screen", true);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }else {
                    editor.putBoolean("screen", false);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                editor.commit();
            }
        });

        switch_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("mode", true);
                }else {
                    editor.putBoolean("mode", false);
                }
                editor.commit();
            }
        });

        setupSpinnerSize(mSize);
        setupSpinnerColor(mColor);
    }

    private void setupSpinnerColor(int color){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_color_font, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp_color.setAdapter(adapter);

        sp_color.setSelection(color);

        sp_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer data_color = position;
                editor.putInt("color", data_color);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSpinnerSize(int size){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_size_font, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp_size.setAdapter(adapter);

        sp_size.setSelection(getFontSize(size));

        sp_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data_size = (String) parent.getItemAtPosition(position);
                editor.putInt("size", Integer.parseInt(data_size));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Integer getFontSize(Integer size){
        Integer real=0;
        switch (size){
            case 16 : real = 0;
                break;
            case 17 : real = 1;
                break;
            case 18 : real = 2;
                break;
            case 19 : real = 3;
                break;
            case 20 : real = 4;
                break;
        }
        return real;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
