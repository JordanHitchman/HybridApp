package com.example.jordanhitchman.fitapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;

import org.json.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;


public class fitbit extends Activity implements View.OnClickListener {

    private EditText pin;
    private oauth oauth;

    public static final String CONSUMER_KEY = "b6eebd633edc40eb8ebd433655bc438b";
    public static final String CONSUMER_SECRET = "045f72d66ae54ccbb9a783f23f1600cf";
    public static final String APPLICATION_NAME = "signpost-test";
    public static final String API_CALL_URL = "https://api.fitbit.com/1/user/-/profile.json";
    public static final String FITBIT_REQUEST_TOKEN_URL = "https://api.fitbit.com/oauth/request_token";
    public static final String FITBIT_ACCESS_TOKEN_URL = "https://api.fitbit.com/oauth/access_token";
    public static final String FITBIT_AUTHORIZE_URL = "https://www.fitbit.com/oauth/authorize";
    private static OAuthConsumer consumer2;
    private static OAuthProvider provider2;
    private static String USER_ACCESS_TOKEN;
    private static String USER_TOKEN_SECRET;
    private static String authURL;

    private Button beginB, submitB;
    private TextView jsonDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitbit);
        prompt p = new prompt();


       pin = (EditText)findViewById(R.id.pin);

        //setup buttons
        beginB = (Button)findViewById(R.id.begin);
        submitB = (Button)findViewById(R.id.button);
        jsonDisplay= (TextView)findViewById(R.id.textView8);

        //register listeners
       beginB.setOnClickListener(this);
        submitB.setOnClickListener(this);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fitbit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.begin:


                try {

                    Intent i = new Intent(this, fitbitWeb.class);
                    startActivity(i);


                    break;

                } catch(Exception e){

                }

            case R.id.button:



                try{

                    URL url = new URL("https://api.fitbit.com/1/user/-/profile.json");
                    HttpURLConnection request = (HttpURLConnection) url.openConnection();
                    request.setRequestMethod("GET");

                    consumer2.setTokenWithSecret("27d5c6a9efabdefdb5c53ba593b95276", "df4b93e6005b707971c86dacf005d99b");

                    consumer2.sign(request);

                    System.out.println("Sending request...");
                    request.connect();

                    //HttpResponse response = request.getInputStream().;
                    InputStream data = request.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
                    String responseLine;
                    StringBuilder responseBuilder = new StringBuilder();

                    while ((responseLine = bufferedReader.readLine()) != null) {
                        responseBuilder.append(responseLine);
                    }
                    JSONObject obj = new JSONObject(responseBuilder.toString());
                    jsonDisplay.setText(responseBuilder.toString());



                } catch(Exception e){
                    e.printStackTrace();
                }









        }
    }

    public static class prompt extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Step 2 of the registration phase involves synching your fitbit account to our application. /n" +
                    "Please enter your fitbit login details on the next screen. /n" +
                    "This will then lead your to a webpage which you will need to click authorize. /n" +
                    "Once you click authorize you will be provided with a code. /n" +
                    "Simply copy and paste this code into the box provided and press submit. /n" +
                    "Click continue when you're ready ")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
