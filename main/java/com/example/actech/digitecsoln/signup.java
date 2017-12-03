package com.example.actech.digitecsoln;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class signup extends AppCompatActivity{


    int eotp,sotp;

    TextView eml;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = getIntent();

        sotp = intent.getIntExtra("otp",12345);
        final String Eml = intent.getStringExtra("email");

        eml = (TextView) findViewById(R.id.etMl);
        eml.setText(Eml);
    }


   public void snup(View view) {
       EditText ename = (EditText) findViewById(R.id.etNm);
       String name = ename.getText().toString();

   /*    TextView eml = (TextView) findViewById(R.id.etMl);
      // eml.setText(Eml);
       email = Eml;//eml.getText().toString();  */
       String email=eml.getText().toString();

       EditText epass = (EditText) findViewById(R.id.etPs);
       String password = epass.getText().toString();

       EditText phn = (EditText) findViewById(R.id.etPhn);
       String phone = phn.getText().toString();

       EditText eotp = (EditText) findViewById(R.id.txotp);
       int OTP = Integer.parseInt(eotp.getText().toString());

       String city = "kolkata";

       if (OTP == sotp) {
           DbHelper myDb = new DbHelper(getApplicationContext());
           long l = myDb.dsignup(name, email, phone, password, city);
           if (l != 0) {
               Toast.makeText(getApplicationContext(), "Successfully Signed Up for "+ email +" "+password, Toast.LENGTH_LONG).show();
               Intent intent = new Intent(signup.this, signin.class);
               startActivity(intent);
           } else
               Toast.makeText(getApplicationContext(), "Error Signing Up", Toast.LENGTH_LONG).show();
       }
       else{
           Toast.makeText(getApplicationContext(), "Error Signing Up", Toast.LENGTH_LONG).show();
           Intent intent = new Intent(signup.this, mlotp.class);
           startActivity(intent);
       }


   }


    public void login(View view) {
        Intent intent = new Intent(signup.this, signin.class);
        startActivity(intent);
    }

    public void reset(View view) {
    }

}

