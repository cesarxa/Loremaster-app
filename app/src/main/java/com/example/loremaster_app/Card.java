package com.example.loremaster_app;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import javax.net.ssl.HttpsURLConnection;

public class Card extends AppCompatActivity {

    EditText getCard;
    ImageView imageViewDisplayer;
    String entireURL;
    CardInfo currentCard;
    List<InventoryItem> inventory;
    TextView textViewDisplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getCard = (EditText) findViewById(R.id.getCard);
        imageViewDisplayer = (ImageView) findViewById(R.id.image_View);
        textViewDisplayer = (TextView) findViewById(R.id.cardInfoView);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String book = pref.getString("user_inventory", null);
        System.out.println(book);
    }

    public void addCard(View view) {
        InventoryItem newItem = null;
        newItem.setCardInfo(currentCard);
        inventory.add(newItem);
        newItem.getCardInfo().displayAll();
    }

    public void getCardInfo(View view) {
        String NewString = getCard.getText().toString();
        NewString = NewString.replaceAll(" ", "+");

        entireURL = "https://api.scryfall.com/cards/named?fuzzy=" + NewString;
        //entireURL = "https://api.scryfall.com/cards/random";
        Scryfall scryfall = new Scryfall(entireURL, imageViewDisplayer, textViewDisplayer);
        Thread t = new Thread(scryfall);
        t.start();

        //sending inventory to our firebase database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/fireblog");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(inventory);
        editor.putString("user_inventory", json); // Storing string
        editor.commit();
        System.out.println(json);

        //test saving a user to the database
        /*
        public static class User {

            public String date_of_birth;
            public String full_name;
            public String nickname;

            public User(String dateOfBirth, String fullName) {
                // ...
            }

            public User(String dateOfBirth, String fullName, String nickname) {
                // ...
            }

        }

        DatabaseReference usersRef = ref.child("users");

        Map<String, User> users = new HashMap<>();
        users.put("alanisawesome", new User("June 23, 1912", "Alan Turing"));
        users.put("gracehop", new User("December 9, 1906", "Grace Hopper"));

        usersRef.setValueAsync(users);
        */
    }

    public class Scryfall implements Runnable {
        private String entireURL;
        private ImageView theImageView;
        private TextView thetextView;

        public Scryfall(String entireURL, ImageView anImageView, TextView atextView) {
            this.entireURL = entireURL;
            this.theImageView = anImageView;
            this.thetextView = atextView;
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
                final CardInfo newCard = gson.fromJson(stringBuilder.toString(), CardInfo.class);
                currentCard = newCard;
                currentCard.displayAll();
                //TEMPORARY CODE
                //displayAll() prints all string qualities of the card to the console
                newCard.displayAll();

                // display image in uithread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String murl = newCard.getImageURIs().get("border_crop"); //newCard.getLargeImage(); // need to add method or url of images searched;
                        Picasso.get().load(murl).into(theImageView);

                        String cInfo =  ("Name: " + newCard.getName()) +
                                        //("\nOracle Text: " + newCard.getOracleText()) +
                                        //("\nType: " + newCard.getTypeLine()) +
                                        //("\nMana Cost: " + newCard.getManaCost()) +
                                        ("\nPrice USD: $" + newCard.getPrices().get("usd")) +
                                        ("\nPrice USD Foil: $" + newCard.getPrices().get("usd_foil"));
                        textViewDisplayer.setText(cInfo);
                        textViewDisplayer.setMovementMethod(new ScrollingMovementMethod());

                    }
                });

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