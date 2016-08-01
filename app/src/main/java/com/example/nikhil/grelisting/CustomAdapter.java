package com.example.nikhil.grelisting;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 7/31/2016.
 */
public class CustomAdapter extends BaseAdapter {
    private List<String> from,body;
    private List<Integer> progress;
    Context c;
    private static LayoutInflater li=null;
    SharedPreferences pref;
    public CustomAdapter(HomeActivity mainActivity,List<String> from/*,List<String> body*/, List<Integer> progress)
    {
        this.from = from;
        //this.body = body;
        c = mainActivity;
        li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.progress  = progress;
        this.pref = pref;
    }

    @Override
    public int getCount() {
        return from.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv1;
        //TextView tv2;
        ProgressBar pb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = li.inflate(R.layout.list_item, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.textView);
        //holder.tv2=(TextView) rowView.findViewById(R.id.textView2);
        holder.pb = (ProgressBar) rowView.findViewById(R.id.progressBar);
        holder.tv1.setText(from.get(position));
        holder.pb.setProgress(progress.get(position));
        //holder.tv2.setText(from.get(position));
        //System.out.println("Current posi "+position);
        return rowView;
    }
    public void noti(List<String> from){
        this.from = from;
        notifyDataSetChanged();
    }
}

