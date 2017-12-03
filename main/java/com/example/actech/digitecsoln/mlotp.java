package com.example.actech.digitecsoln;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class mlotp extends AppCompatActivity implements View.OnClickListener{
    int n;

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep;
    String rec, subject, textMessage,ml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlotp);

        context = this;

        Button login = (Button) findViewById(R.id.btn_submit);
        reciep = (EditText) findViewById(R.id.et_to);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Random rand = new Random();
        n = rand.nextInt(55320) + 1;

        rec = reciep.getText().toString();
        ml=rec;
        subject = "Your OTP FOR DIGITEC Soln";
        textMessage = "YOUR OTP IS ->"+ n;

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

        pdialog = ProgressDialog.show(context, "", "Sending OTP...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("global.lib9@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            reciep.setText("");
            Toast.makeText(getApplicationContext(), "OTP sent", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(mlotp.this, signup.class);
            intent.putExtra("otp",n);
            intent.putExtra("email",ml);
            startActivity(intent);
        }
    }
}
