package com.example.actech.digitecsoln;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity {

    JSONObject jsonObject;
    JSONArray jsonArray;
    HotelAdapter hotelAdapter;
    ListView listView;

    String JSONSTR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_view);

       this.JSONSTR=getIntent().getStringExtra("JSONmsg");
        Toast.makeText(getApplicationContext(),JSONSTR,Toast.LENGTH_SHORT).show();

        listView=(ListView)findViewById(R.id.HotelListView);

        hotelAdapter=new HotelAdapter(this,R.layout.row_layout);

        listView.setAdapter(hotelAdapter);

        try {
            jsonObject=new JSONObject(JSONSTR);
            jsonArray=jsonObject.getJSONArray("results");
            int count=0;

            String name,rating,types,vicinity;

            while (count<jsonArray.length()){
                JSONObject jo=jsonArray.getJSONObject(count);
                name=jo.getString("name");
                rating=jo.getString("rating");
                types=jo.getJSONArray("types").toString();
                vicinity=jo.getString("vicinity");

                Hotels hotels=new Hotels(name,rating,types,vicinity);
                hotelAdapter.add(hotels);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
