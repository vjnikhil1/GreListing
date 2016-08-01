package com.example.nikhil.grelisting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class HomeActivity extends AppCompatActivity {
    private final static int total = 200;
    float days;
    SharedPreferences pref;
    ListView lv;
    CustomAdapter c;
    List<String> temp;
    List<Integer> progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pref = getSharedPreferences("GRE", 0);
        days = pref.getInt("days", 0);
        temp = new ArrayList<>();
        progress = new ArrayList<>();
        for (int i = 0; i < Math.ceil(total / days); i++) {
            temp.add("Day " + (i + 1));
            float per = pref.getInt("day"+i,0)%pref.getInt("days",0);
            per/=pref.getInt("days",0);
            per*=100;
            progress.add((int)per);
        }
        c = new CustomAdapter(this, temp, progress);
        //c.notifyDataSetChanged();
        lv = (ListView) findViewById(R.id.cardListView);
        lv.setAdapter(c);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(HomeActivity.this, MeaningsActivity.class);
                i.putExtra("listNumber", position);
                Log.i("position", position+"");
                startActivity(i);
            }
        });
    }

    @Override
    public void onRestart(){
        super.onRestart();
        //days = pref.getInt("days", 0);
        progress.clear();
        for (int i = 0; i < Math.ceil(total / days); i++) {
            float per = pref.getInt("day"+i,0)%pref.getInt("days",0);
            per/=pref.getInt("days",0);
            per*=100;
            progress.add((int)per);
            Log.i("percent",per+"");
            c.notifyDataSetChanged();
        }
    }
}
