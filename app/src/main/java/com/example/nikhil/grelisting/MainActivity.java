package com.example.nikhil.grelisting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText days = (EditText)findViewById(R.id.days);
        Button submit = (Button)findViewById(R.id.submit);
        SharedPreferences pref = getSharedPreferences("GRE", 0);
        final SharedPreferences.Editor editor = pref.edit();
        if(!pref.getBoolean("loggedin", false)){
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day = Integer.parseInt(days.getText().toString());
                    editor.putInt("days", day);
                    editor.putBoolean("loggedin", true);
                    editor.commit();
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
        else {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }
}
