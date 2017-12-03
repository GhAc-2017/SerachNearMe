package com.example.actech.digitecsoln;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class gps_ask extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener listener;
    public Double lat,lng;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gps_ask);

        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textView.setText("\n " + location.getLongitude() + " " + location.getLatitude());
                lng=location.getLongitude();
                lat=location.getLatitude();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
    }

 /*   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case 10: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
                    configure_button();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else{
                    Toast.makeText(getApplicationContext(),"Choose Manually",Toast.LENGTH_SHORT).show();
                    TextView tvw=(TextView)findViewById(R.id.loc);
                    tvw.setVisibility(View.VISIBLE);
                }
                return;
            }
            default:
                break;
            // other 'case' lines to check for other
            // permissions this app might request
        }




      switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }   */
 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     if(requestCode == 10){
         if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED ){

             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     configure_button();
                 }
             }).start();
         }else{
             Toast.makeText(getApplicationContext(), "Access Denied ! Plesae Choose Location Manually ", Toast.LENGTH_SHORT).show();
             TextView tvw=(TextView)findViewById(R.id.tval);
             tvw.setVisibility(View.VISIBLE);
         }
     }
 }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won'textView execute IF permissions are not allowed, because in the line above there is return statement.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                if ((ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    return;
                }

                locationManager.requestLocationUpdates("gps", 0, 0, listener);
                button.setText("Tap again when you see COORDINATE Data");

               if(!textView.getText().toString().equals("Coordinates: ")) {
                   Toast.makeText(getApplicationContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                   Toast.makeText(getApplicationContext(), "Choosing" +lat +" " + lng, Toast.LENGTH_SHORT).show();
                   Button bt=(Button) findViewById(R.id.button);
                   bt.setVisibility(View.INVISIBLE);
                   Button bvw = (Button) findViewById(R.id.buttongo);
                   bvw.setVisibility(View.VISIBLE);
               }

            }
        });
    }



    public void DataPick(View view) {
        LayoutInflater layoutInflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final  View vw=layoutInflater.inflate(R.layout.loc_ask,null);

        final PopupWindow popupWindow=new PopupWindow(vw, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(vw, Gravity.CENTER,0,0);

        Spinner spn=(Spinner) vw.findViewById(R.id.spn);
        final String city=spn.getSelectedItem().toString();

        Button btnPick=(Button) vw.findViewById(R.id.loc);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Choosing" + city, Toast.LENGTH_SHORT).show();
                //Spinner spn=(Spinner) vw.findViewById(R.id.spn);
                //String city=spn.getSelectedItem().toString();
                popupWindow.dismiss();
                Intent intent = new Intent(getApplicationContext(),signup.class);
                startActivity(intent);
            }
        });
    }

    public void go(View view) {
        Intent intent = new Intent(getApplicationContext(),mlotp.class);
        startActivity(intent);
    }
}