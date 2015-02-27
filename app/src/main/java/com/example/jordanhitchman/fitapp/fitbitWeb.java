package com.example.jordanhitchman.fitapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;


public class fitbitWeb extends Activity implements View.OnClickListener {


    public static final String CONSUMER_KEY = "b6eebd633edc40eb8ebd433655bc438b";
    public static final String CONSUMER_SECRET = "045f72d66ae54ccbb9a783f23f1600cf";
    public static final String APPLICATION_NAME = "signpost-test";
    public static final String API_CALL_URL = "https://api.fitbit.com/1/user/-/profile.json";
    public static final String FITBIT_REQUEST_TOKEN_URL = "https://api.fitbit.com/oauth/request_token";
    public static final String FITBIT_ACCESS_TOKEN_URL = "https://api.fitbit.com/oauth/access_token";
    public static final String FITBIT_AUTHORIZE_URL = "https://www.fitbit.com/oauth/authorize";
    private static OAuthConsumer consumer2;
    private static OAuthProvider provider2;
    public static String USER_ACCESS_TOKEN;
    public static String USER_TOKEN_SECRET;
    private static String authURL;
    private EditText pin;
    private Button done;
    private static SharedPreferences preferences;
    private static final String TAG = "MyActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitbit_web);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        }
       pin = (EditText)findViewById(R.id.pin);

        done = (Button)findViewById(R.id.done);
        done.setOnClickListener(this);



        try {
            consumer2 = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

            provider2 = new DefaultOAuthProvider(FITBIT_REQUEST_TOKEN_URL,
                    FITBIT_ACCESS_TOKEN_URL, FITBIT_AUTHORIZE_URL);
            String url;
            authURL = provider2.retrieveRequestToken(consumer2, OAuth.OUT_OF_BAND);
            authURL = OAuth.addQueryParameters(authURL, OAuth.OAUTH_CONSUMER_KEY, CONSUMER_KEY,
                    "application_name", APPLICATION_NAME);

            final WebView browser = (WebView) findViewById(R.id.webView2);
            browser.getSettings().setJavaScriptEnabled(true);
            browser.setWebViewClient(new WebViewClient());
            browser.loadUrl(authURL);
            Log.v(TAG, "I do work");
        } catch(Exception e){
            Log.v(TAG, "I don't work");
            Log.e(TAG,"error" ,e);

        }

        Log.v(TAG, ""+authURL);



            //String url = "https://www.fitbit.com/oauth/authorize?oauth_token=f57b072025a201213dc2f3f06fde36c5&oauth_consumer_key=b6eebd633edc40eb8ebd433655bc438b&application_name=signpost-test";















    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {




            case R.id.done:

               try {

                   //Get user token and secret, and store them to the users preferences

                   provider2.retrieveAccessToken(consumer2, pin.getText().toString());
                   USER_ACCESS_TOKEN = consumer2.getToken();
                   USER_TOKEN_SECRET = consumer2.getTokenSecret();

                   preferences = getPreferences(MODE_PRIVATE);
                   SharedPreferences.Editor preferencesEditor = preferences.edit();
                   preferencesEditor.putString("USER_ACCESS_TOKEN", USER_ACCESS_TOKEN );
                   preferencesEditor.apply();
                   preferencesEditor.putString("USER_TOKEN_SECRET", USER_TOKEN_SECRET );
                   preferencesEditor.apply();

                   //Make a request for the profile json file

                   URL url = new URL("https://api.fitbit.com/1/user/2XQQY7/profile.json");
                   HttpURLConnection request = (HttpURLConnection) url.openConnection();
                   request.setRequestMethod("GET");
                   consumer2.sign(request);
                   request.connect();
                   InputStream data = request.getInputStream();
                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
                   String responseLine;
                   StringBuilder responseBuilder = new StringBuilder();
                   while ((responseLine = bufferedReader.readLine()) != null) {
                       responseBuilder.append(responseLine);
                   }
                   JSONObject obj = new JSONObject(responseBuilder.toString());
                   //Store the json file in the sharedpreferences
                   Gson gson = new Gson();
                   String json = gson.toJson(obj);
                   preferencesEditor.putString("Profile", json );
                   preferencesEditor.apply();
                   if(!json.isEmpty()){
                       Log.v(TAG, json);
                       Intent i = new Intent(this, fp.class);
                       startActivity(i);

                   }
                   //AlertDialog.Builder builder1 = new AlertDialog.Builder(fitbitWeb.this);
                   //builder1.setMessage("USER_ACCESS_TOKEN /n" + "USER_TOKEN_SECRET");
                   //builder1.setCancelable(true);
                   //builder1.setPositiveButton("Yes",
                     //      new DialogInterface.OnClickListener() {
                         //      public void onClick(DialogInterface dialog, int id) {
                       //            dialog.cancel();
                          //     }
                          // });
                   //builder1.setNegativeButton("No",
                     //      new DialogInterface.OnClickListener() {
                         //      public void onClick(DialogInterface dialog, int id) {
                       //            dialog.cancel();
                             //  }
                           //});

                   //AlertDialog alert11 = builder1.create();
                   //alert11.show();

               } catch(Exception e){

               }



        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fitbit_web, menu);
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

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
