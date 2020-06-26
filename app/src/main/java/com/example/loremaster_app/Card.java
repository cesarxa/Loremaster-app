package com.example.loremaster_app;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.io.InputStream;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;

public class Card extends AppCompatActivity {

    EditText getCard;
    static String entireURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getCard = (EditText) findViewById(R.id.getCard);
    }

    public void getCardInfo(View view) {
        //still need correct info request  !FIX - missing "+ getCard.getText().toString() +" in url.
        //String url = "https://api.scryfall.com/bulk-data/922288cb-4bef-45e1-bb30-0c2bd3d3534f";
        //entireURL = "https://api.scryfall.com/cards/named?fuzzy=" + getCard.getText().toString();
        entireURL = "https://api.scryfall.com/cards/random";
        new Scryfall().execute();
    }

    public static class Scryfall extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(entireURL);
                //make connect with url and send request
                urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds
                int code = urlConnection.getResponseCode();
                if (code != 200) {
                    throw new IOException("Invalid response from server: " + code);
                }
                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}

//        protected void onProgressUpdate(String... progress) {
//
//            try {
//                JSONObject json= new JSONObject(progress[0]);
//
//            JSONObject query=json.getJSONObject("query");            !!!!!!!!! Example of info needed
//            JSONObject results=query.getJSONObject("results");
//            JSONObject channel=results.getJSONObject("channel");
//            JSONObject astronomy=channel.getJSONObject("astronomy");
//            String sunset=astronomy.getString("sunset");
//            String sunrise=astronomy.getString("sunrise");
//
//                //display response data                                !!!!! FIX THIS
//                // Toast.makeText(getApplicationContext(),"sunset:"+ sunset + ",sunrise:"+ sunrise,Toast.LENGTH_LONG).show();
//
//            } catch (Exception ex) {
//            }
//
//        }
//
//        protected void onPostExecute(String  result2){
//
//        }
//
//        // this method convert any stream to string
//        public String ConvertInputToStringNoChange(InputStream inputStream) {
//
//            BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
//            String line ;
//            String linereultcal="";
//
//            try{
//                while((line=bureader.readLine())!=null) {
//                    linereultcal+=line;
//                }
//
//                inputStream.close();
//            }
//
//            catch (Exception ex){}
//
//            return linereultcal;
//        }
//
//    }
//}





////   Sterling Code

//public class Card extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_card);
//    }
//
//    public static void callScryfall() {
//        Scanner scannerCity = new Scanner(System.in);  // Create a Scanner object
//        System.out.print("Please Enter City for Weather Report: ");
//
//        String scanCity = scannerCity.nextLine();  // Read user input
//
//        String url = "https://api.scryfall.com";
//        String charset = "UTF-8";
//
//        URLConnection connection = new URL(url).openConnection();
//        connection.setRequestProperty("Accept-Charset", charset);
//        InputStream response = connection.getInputStream();
//
//        try (Scanner scanner = new Scanner(response)) {
//            String responseBody = scanner.useDelimiter("\\A").next();
//            System.out.println(responseBody);
//            Gson g2 = new Gson();
//            //WeatherConditions new_p = g2.fromJson(responseBody, WeatherConditions.class);
//            //new_p.display();
//        }
//    }
//}