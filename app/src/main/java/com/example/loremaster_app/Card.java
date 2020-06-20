package com.example.loremaster_app;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import com.google.gson.Gson;
import java.io.InputStream;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;

public class Card extends AppCompatActivity {

    EditText getCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        getCard = (EditText) findViewById(R.id.getCard);

    }

    public void butGetCard(View view){
        //still need correct info request  !FIX - missing "+ getCard.getText().toString() +" in url.
        String url = "https://api.scryfall.com/bulk-data/922288cb-4bef-45e1-bb30-0c2bd3d3534f";
    }

    public class scryFall extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {
                JSONObject json= new JSONObject(progress[0]);

//            JSONObject query=json.getJSONObject("query");            !!!!!!!!! Example of info needed
//            JSONObject results=query.getJSONObject("results");
//            JSONObject channel=results.getJSONObject("channel");
//            JSONObject astronomy=channel.getJSONObject("astronomy");
//            String sunset=astronomy.getString("sunset");
//            String sunrise=astronomy.getString("sunrise");

                //display response data                                !!!!! FIX THIS
                // Toast.makeText(getApplicationContext(),"sunset:"+ sunset + ",sunrise:"+ sunrise,Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
            }

        }

        protected void onPostExecute(String  result2){

        }

        // this method convert any stream to string
        public String ConvertInputToStringNoChange(InputStream inputStream) {

            BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
            String line ;
            String linereultcal="";

            try{
                while((line=bureader.readLine())!=null) {
                    linereultcal+=line;
                }

                inputStream.close();
            }

            catch (Exception ex){}

            return linereultcal;
        }

    }
}





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