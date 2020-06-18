package com.example.loremaster_app;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Scanner;
import java.net.URLConnection;
import com.google.gson.Gson;
import java.io.InputStream;
import java.net.URL;
import android.os.Bundle;

public class Card extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
    }

    public static void callScryfall() {
        Scanner scannerCity = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Please Enter City for Weather Report: ");

        String scanCity = scannerCity.nextLine();  // Read user input

        String url = "https://api.scryfall.com";
        String charset = "UTF-8";

        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();

        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
            Gson g2 = new Gson();
            //WeatherConditions new_p = g2.fromJson(responseBody, WeatherConditions.class);
            //new_p.display();
        }
    }
}