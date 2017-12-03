package com.example.actech.digitecsoln;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Hotel_lists extends AppCompatActivity {
    String jsonStr,JSONSTR;
    JSONObject jsonObject;
    JSONArray jsonArray;
    HotelAdapter hotelAdapter;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_lists);
    /*    listView=findViewById(R.id.HotelListView);

        hotelAdapter=new HotelAdapter(this,R.layout.row_layout);

        listView.setAdapter(hotelAdapter);

        getJson();

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

        parseJson();

*/
    }

    public void getJson(View view){
        new BackTask().execute();

    }

    class BackTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           jsonStr="https://maps.googleapis.com/maps/api/place/radarsearch/json?location=20.296059,85.824540&radius=500&type=hotel&key=AIzaSyCXo_ZZqyIjxpHMeXroLpkP1K_abWnwmOo";

        }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(jsonStr);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader((new InputStreamReader(inputStream)));

                StringBuilder stringBuilder=new StringBuilder();
                while((jsonStr=bufferedReader.readLine())!=null){
                    stringBuilder.append(jsonStr+"\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView textView=(TextView)findViewById(R.id.locText);
            textView.setText(result);
            JSONSTR=result;
            Toast.makeText(getApplicationContext(),JSONSTR,Toast.LENGTH_SHORT).show();
        }
    }

    public void parseJson(View view){
        if(JSONSTR==null){
            Toast.makeText(getApplicationContext(),"error gettingg result",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getApplicationContext()," gettingg result",Toast.LENGTH_SHORT).show();
           Intent intent=new Intent(this,DisplayListView.class);

           intent.putExtra("JSONmsg",JSONSTR.trim());
           startActivity(intent);

        }
    }

}
