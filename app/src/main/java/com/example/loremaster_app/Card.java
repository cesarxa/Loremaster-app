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
    String entireURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getCard = (EditText) findViewById(R.id.getCard);
    }

    public void getCardInfo(View view) {
        //entireURL = "https://api.scryfall.com/cards/named?fuzzy=" + getCard.getText().toString();
        entireURL = "https://api.scryfall.com/cards/random";
        Scryfall scryfall = new Scryfall(entireURL);
        Thread t = new Thread(scryfall);
        t.start();
    }

    public class Scryfall implements Runnable {
        private String entireURL;
        public Scryfall(String entireURL) {
            this.entireURL = entireURL;
        }
        @Override
        public void run() {
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
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                    stringBuilder.append(line);
                }
                Gson gson = new Gson();
                CardInfo currentCard = gson.fromJson(stringBuilder.toString(), CardInfo.class);
                //TEMPORARY CODE
                //displayAll() prints all string qualities of the card to the console
                currentCard.displayAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
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