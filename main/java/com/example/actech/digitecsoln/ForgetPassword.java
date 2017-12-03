package com.example.actech.digitecsoln;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {
    int n;

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep;
    String rec, subject, textMessage, ml;

    String Name,Email,Phone,Password,City;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        context = this;

        Button login = (Button) findViewById(R.id.btn_submit);
        reciep = (EditText) findViewById(R.id.et_to);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        rec = reciep.getText().toString();

        resetpass();


        rec = reciep.getText().toString();
        ml = rec;
        subject = "Password RESET Request FOR DIGITEC Soln Account";
        textMessage = "YOUR new Password IS ->" + n;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("global.lib9@gmail.com", "gl@admin@ac#97");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Resetting Password...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }

    private void resetpass() {


        Random rand = new Random();
        n = rand.nextInt(55320) + 1;
        Password= String.valueOf(n);
        EditText eml=(EditText) findViewById(R.id.et_to);
        String mail=eml.getText().toString();

        DbHelper myDb = new DbHelper(getApplicationContext());
        Cursor cursor = myDb.duplicate_login(mail);
        while (cursor.moveToNext()) {

            String nm = cursor.getString(cursor.getColumnIndex(DbHelper.col_name));
            Name=nm;
            String lme = cursor.getString(cursor.getColumnIndex(DbHelper.col_email));
            Email=lme;
            String nhp = cursor.getString(cursor.getColumnIndex(DbHelper.col_phn));
            Phone=nhp;
            String psd=cursor.getString(cursor.getColumnIndex(DbHelper.col_pswd));

            String ct= cursor.getString(cursor.getColumnIndex(DbHelper.col_city));
            City=ct;
        }

        DbHelper myDb2=new DbHelper(getApplicationContext());
        int rt=myDb2.update(Name, Email, Phone, Password, City);
        if(rt>0){
            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ForgetPassword.this,signin.class);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "Error Updating!!!", Toast.LENGTH_LONG).show();


    }



    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("global.lib9@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            reciep.setText("");
            Toast.makeText(getApplicationContext(), "OTP sent", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(ForgetPassword.this, signin.class);
            startActivity(intent);
        }
    }
}