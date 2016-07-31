package com.example.nikhil.grelisting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MeaningsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meanings);
        final SharedPreferences pref = getSharedPreferences("GRE", 0);
        final SharedPreferences.Editor editor = pref.edit();
        Intent intent = getIntent();
        final int val = intent.getIntExtra("listNumber", 0);
        Button next = (Button) findViewById(R.id.next);
        InputStream inputWorkbook = getResources().openRawResource(R.raw.book2);
            Workbook w;
            try {
                w = Workbook.getWorkbook(inputWorkbook);
                // Get the first sheet
                final Sheet sheet = w.getSheet(0);
                // Loop over column and lines
                TextView name = (TextView) findViewById(R.id.name);
                TextView meaning = (TextView) findViewById(R.id.meaning);
                Cell cel1 = sheet.getCell(0, pref.getInt("day"+val,val*pref.getInt("days",0)));
                Cell cel11 = sheet.getCell(1, pref.getInt("day"+val,val*pref.getInt("days",0)));
                editor.putInt("day"+val, pref.getInt("day"+val,val*pref.getInt("days",0)));
                editor.commit();
                name.setText(cel1.getContents());
                meaning.setText(cel11.getContents());
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putInt("day"+val,pref.getInt("day"+val,0)+1);
                        editor.commit();
                        TextView name = (TextView) findViewById(R.id.name);
                        TextView meaning = (TextView) findViewById(R.id.meaning);
                        if(val==0){
                            Cell cel = sheet.getCell(0, pref.getInt("day"+val,0));
                            Cell cel1 = sheet.getCell(1, pref.getInt("day"+val,0));
                            name.setText(cel.getContents());
                            meaning.setText(cel1.getContents());
                        }
                        else {
                            Cell cel = sheet.getCell(0, pref.getInt("day" + val, val*pref.getInt("days", 0)));
                            Cell cel1 = sheet.getCell(1,pref.getInt("day" + val, val*pref.getInt("days", 0)));
                            //editor.putInt("day" + val, pref.getInt("day" + val, 0) + 1);
                            //editor.commit();
                            name.setText(cel.getContents());
                            meaning.setText(cel1.getContents());
                        }
                        //Log.i("counter",pref.getInt("day"+val,0)+"");
                    }
                });
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
