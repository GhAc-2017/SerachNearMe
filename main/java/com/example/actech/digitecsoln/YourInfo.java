package com.example.actech.digitecsoln;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class YourInfo extends AppCompatActivity {
    String mail;
    String pswd;

    String name;
    String Email;
    String password;
    String Phone;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_info);
        Intent intent = getIntent();

        mail = intent.getStringExtra("Msg1");
        pswd = intent.getStringExtra("Msg2");

        DbHelper myDb = new DbHelper(getApplicationContext());
        Cursor cursor = myDb.login(mail, pswd);
        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(DbHelper.col_name));
            TextView tvNm = (TextView) findViewById(R.id.tvNm);
            tvNm.setText(name);


            Email = cursor.getString(cursor.getColumnIndex(DbHelper.col_email));
            TextView tveMl = (TextView) findViewById(R.id.tvMl);
            tveMl.setText(Email);


            Phone = cursor.getString(cursor.getColumnIndex(DbHelper.col_phn));
            TextView tvPhn = (TextView) findViewById(R.id.tvphn);
            tvPhn.setText(Phone);

            password=cursor.getString(cursor.getColumnIndex(DbHelper.col_pswd));



            city = cursor.getString(cursor.getColumnIndex(DbHelper.col_city));
            TextView tvct = (TextView) findViewById(R.id.tvCity);
            tvct.setText(city);
        }
    }

    public void hotels(View view) {
        Intent intent = new Intent(this, Hotel_lists.class);
        startActivity(intent);
    }

  /*  public void goUpd(View view) {
        Intent intent1 = new Intent(YourInfo.this, UpdateInfo.class);
        intent1.putExtra("msgNm",name);
        intent1.putExtra("msgeMl", mail);
        intent1.putExtra("msgPswd", password);
        intent1.putExtra("msgGndr", Gndr);
        intent1.putExtra("msgDob", dob);
        intent1.putExtra("msgCity", city);
        startActivity(intent1);
    }


    public void del(View view) {
        DbHelper myDb=new DbHelper(getApplicationContext());
        int d=myDb.delete(mail);
        if(d>0){
            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(YourInfo.this, signin.class);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "Error Deleting", Toast.LENGTH_LONG).show();
    }
*/

}