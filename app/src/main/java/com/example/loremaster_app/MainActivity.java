package com.example.loremaster_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText getCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCard = (EditText) findViewById(R.id.getCard);
    }

    private static class HTTPReqTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            HttpURLConnection urlConnection = null;

            // GET
            try
            {
                URL url = new URL("https://reqres.in/api/users?page=2");
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();
                if (code != 200)
                {
                    throw new IOException("Invalid response from server: " + code);
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null)
                {
                    Log.i("data", line);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}