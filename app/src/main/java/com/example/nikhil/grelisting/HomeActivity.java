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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pref = getSharedPreferences("GRE", 0);
        days = pref.getInt("days", 0);
        temp = new ArrayList<>();
        for (int i = 0; i < Math.ceil(total / days); i++) {
            temp.add("Day " + (i + 1));
        }
        c = new CustomAdapter(this, temp, pref);
        lv = (ListView) findViewById(R.id.cardListView);
        lv.invalidate();
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
        try
        {
            read("Obscure");
        }
        catch(IOException e){}
    }
    public List<String> read(String key) throws IOException {
        List<String> resultSet = new ArrayList<String>();

        InputStream inputWorkbook = getResources().openRawResource(R.raw.book2);
        if(true){
            Workbook w;
            try {
                w = Workbook.getWorkbook(inputWorkbook);
                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                // Loop over column and lines
                for (int j = 0; j < sheet.getRows(); j++) {
                    //Cell cell = sheet.getCell(0, j);
                    //if(cell.getContents().equalsIgnoreCase(key)){
                        for (int i = 0; i < sheet.getColumns(); i++) {
                            Cell cel = sheet.getCell(i, j);
                            resultSet.add(cel.getContents());
                            System.out.print(cel.getContents()+" ");
                            //Log.i("test",cel.getContents());
                        }
                    System.out.println();
                    //}
                    //continue;
                }
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            resultSet.add("File not found..!");
        }
        if(resultSet.size()==0){
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }
    @Override
    public void onRestart(){
        super.onRestart();
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        for (int i = 0; i < Math.ceil(total / days); i++) {
            int ans = (pref.getInt("day"+i,0)/pref.getInt("days",0))*100;
            pb.setProgress(ans);
        }
    }
}
