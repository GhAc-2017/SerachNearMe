package com.example.actech.digitecsoln;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class signin extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    LoginButton loginButton;
    CallbackManager callbackManager;

    GoogleApiClient googleApiClient;
    SignInButton signInButton;
    Button signout;
    private static final int REQ_CODE=9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signInButton=(SignInButton)findViewById(R.id.glogin);
        signout=(Button)findViewById(R.id.gsignout);

        signInButton.setOnClickListener(this);
        signout.setOnClickListener(this);

        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        initializecontrols();

        loginfb();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.glogin:
                Gsignin();
                break;
            case R.id.gsignout:
                Gsignout();
                break;

        }
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleresult(result);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }
    private void handleresult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
         //   String name=account.getDisplayName();
        //    String email=account.getEmail();
            Intent intent2 = new Intent(this, Hotel_lists.class);
            startActivity(intent2);

            Toast.makeText(getApplicationContext(),"Signed In",Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this, picloc.class);
            //startActivity(intent);
            updateUi(true);
        }
        else {
            Toast.makeText(getApplicationContext(),"Error Signing In",Toast.LENGTH_SHORT).show();
            updateUi(false);
        }
    }

    private void updateUi(boolean b) {
        if(b){
            Toast.makeText(getApplicationContext(),"Signed In",Toast.LENGTH_SHORT).show();
            signout.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
        }
        else {
            Toast.makeText(getApplicationContext(),"Signed out",Toast.LENGTH_SHORT).show();
            signout.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.VISIBLE);

        }
    }

    private void Gsignin() {
        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    private void Gsignout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUi(false);
            }
        });
    }



    //fB Login process
    private void initializecontrols() {
        callbackManager=CallbackManager.Factory.create();

        loginButton=(LoginButton)findViewById(R.id.login_button);
    }

    private void loginfb() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(),"lOGIN SUCCESSFULL",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplicationContext(), Hotel_lists.class);
                        startActivity(intent2);

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "lOGIN CANCELLED", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(),"lOGIN ERROR",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void login(View view) {
        EditText eml=(EditText) findViewById(R.id.etMl);
        String email=eml.getText().toString();

        EditText epass=(EditText) findViewById(R.id.etPs);
        String password=epass.getText().toString();

        DbHelper myDb=new DbHelper(getApplicationContext());
        Cursor cursor=myDb.login(email,password);

        if(cursor.getCount()==1){
            Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signin.this,YourInfo.class);
            intent.putExtra("Msg1",email);
            intent.putExtra("Msg2",password);
            startActivity(intent);
        }

        else
            Toast.makeText(getApplicationContext(), "Wrong UserName/Password", Toast.LENGTH_LONG).show();
    }

    public void signup(View view) {
        Intent intent = new Intent(this, mlotp.class);
        startActivity(intent);
    }

    public void reset(View view) {
    }


    public void forgetpass(View view) {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }
}
