package com.example.jordanhitchman.fitapp;



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
        import oauth.signpost.http.HttpResponse;



        import org.apache.*;


//This class is used to get authorisation from fitbit for a given user
//After authorisation it will grab the users main JSON file, store it then parse it to separate the values
//Their are various methods that can be called after authorisation to grab the required data from Fitbit

/**
 * Class for authorisation with Fitbit using oauth
 *@author Jordan Hitchman
 *@version 2.0
 */

public class oauth {


//Setting various values required for OAUTH

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


    public oauth() {

        //Initialising consumer and provider variables to get authorisation from Fitbit

       try {
           consumer2 = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

           provider2 = new DefaultOAuthProvider(FITBIT_REQUEST_TOKEN_URL,
                   FITBIT_ACCESS_TOKEN_URL, FITBIT_AUTHORIZE_URL);
           authURL = provider2.retrieveRequestToken(consumer2, OAuth.OUT_OF_BAND);
           authURL = OAuth.addQueryParameters(authURL, OAuth.OAUTH_CONSUMER_KEY, CONSUMER_KEY,
                   "application_name", APPLICATION_NAME);

       } catch(Exception e){

       }

    }



    /**
     * @since 1.0
     */

    public static String initialOauth() throws Exception{

        try {



            USER_ACCESS_TOKEN = consumer2.getToken();
            USER_TOKEN_SECRET = consumer2.getTokenSecret();


            URL url = new URL("https://api.fitbit.com/1/user/2XQQY7/profile.json");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");

            consumer2.sign(request);

           // System.out.println("Sending request...");
            request.connect();

            //HttpResponse response = request.getInputStream().;
            InputStream data = request.getInputStream();

            //System.out.println("/n" + "Response is"  + "/n" + "InputStream is" + data.toString());


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
            String responseLine;
            StringBuilder responseBuilder = new StringBuilder();

            while ((responseLine = bufferedReader.readLine()) != null) {
                responseBuilder.append(responseLine);
            }

            return responseBuilder.toString();





        } catch(Exception e){


        }

        return null;




    }



    public static void oauth() throws Exception{
        OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        OAuthProvider provider = new DefaultOAuthProvider(FITBIT_REQUEST_TOKEN_URL,
                FITBIT_ACCESS_TOKEN_URL, FITBIT_AUTHORIZE_URL);
        System.out.println("Fetching request token from Fitbit...");
        String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);
        authUrl = OAuth.addQueryParameters(authUrl, OAuth.OAUTH_CONSUMER_KEY, CONSUMER_KEY,
                "application_name", APPLICATION_NAME);
        System.out.println("Request token: " + consumer.getToken());
        System.out.println("Token secret: " + consumer.getTokenSecret());

        System.out.println("Now visit:\n" + authUrl + "\n... and grant this app authorization");
        System.out.println("Enter the PIN code and hit ENTER when you're done:");






        System.out.println("Fetching access token from Fitbit...");

       // provider.retrieveAccessToken(consumer, pin);






        System.out.println("Access token: " + consumer.getToken());
        System.out.println("Token secret: " + consumer.getTokenSecret());

        USER_ACCESS_TOKEN = consumer.getToken();
        USER_TOKEN_SECRET = consumer.getTokenSecret();


        URL url = new URL("https://api.fitbit.com/1/user/2XQQY7/profile.json");
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");

        consumer.sign(request);

        System.out.println("Sending request...");
        request.connect();

        //HttpResponse response = request.getInputStream().;
        InputStream data = request.getInputStream();

        System.out.println("/n" + "Response is"  + "/n" + "InputStream is" + data.toString());


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
        String responseLine;
        StringBuilder responseBuilder = new StringBuilder();

        while ((responseLine = bufferedReader.readLine()) != null) {
            responseBuilder.append(responseLine);
        }

        JSONObject obj = new JSONObject(responseBuilder.toString());
        System.out.println(responseBuilder.toString());

    }


    public static void getProfile() {

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

            System.out.println("/n" + "Response is"  + "/n" + "InputStream is" + responseBuilder.toString());

        } catch(Exception e){
            e.printStackTrace();
        }




    }


    public static void getSleep(){


        try{

            URL url = new URL("https://api.fitbit.com/1/user/2XQQY7/profile.json");
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

            System.out.println("/n" + "Response is"  + "/n" + "InputStream is" + responseBuilder.toString());

        } catch(Exception e){
            e.printStackTrace();
        }


    }



    public static void getActivities(){

        try{

            URL url = new URL("https://api.fitbit.com/1/user/-/activities/date/2010-02-25.json");
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

            System.out.println("/n" + "Response is"  + "/n" + "InputStream is" + responseBuilder.toString());


            if(obj != null){


                //	CloseableHttpClient httpClient = HttpClientBuilder.create().build();

                try {
                    //    HttpPost request = new HttpPost("http://yoururl");
                    //  StringEntity params = new StringEntity(json.toString());
                    //   request.addHeader("content-type", "application/json");
                    // request.setEntity(params);
                    //httpClient.execute(request);
                    // handle response here...
                } catch (Exception ex) {
                    // handle exception here
                } finally {
                    //   httpClient.close();
                }

                URL phpUrl = new URL("www.php.com/sendJson.php");





            }



        } catch(Exception e){
            e.printStackTrace();
        }



    }


}






