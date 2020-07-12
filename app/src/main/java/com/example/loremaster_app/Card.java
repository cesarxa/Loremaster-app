package com.example.loremaster_app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import javax.net.ssl.HttpsURLConnection;

public class Card extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2;
    public PageAdapter pageradapter;



    EditText getCard;
    ImageView imageViewDisplayer;
    String entireURL;
    CardInfo currentCard;
    List<InventoryItem> inventory;
    TextView textViewDisplayer;
    Integer quantity;
    EditText quantfromactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        //Tab Info
        TabLayout tabLayout= findViewById(R.id.tablayout);
        TabItem tab1 =findViewById(R.id.tab1);
        TabItem tab2 = findViewById(R.id.tab2);
        ViewPager viewPager = findViewById(R.id.viewpager);

       PagerAdapter pageradapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageradapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0)
                {
                    pageradapter.notifyDataSetChanged();
                } else if  (tab.getPosition() == 1)
                {
                    pageradapter.notifyDataSetChanged();
                } else if  (tab.getPosition() == 2)
                {
                    pageradapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));





        getCard = (EditText) findViewById(R.id.getCard);
        imageViewDisplayer = (ImageView) findViewById(R.id.image_View);
        textViewDisplayer = (TextView) findViewById(R.id.cardInfoView);
        quantfromactivity = (EditText) findViewById(R.id.txtQuantity);
        inventory = new ArrayList<InventoryItem>();
        quantity = 0;
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
        Scryfall scryfall = new Scryfall(entireURL, imageViewDisplayer, textViewDisplayer);
        Thread t = new Thread(scryfall);
        t.start();
    }

    public void upQuantity(View view) {
        System.out.println("We got here");
        quantity++;
        String newText = Integer.toString(quantity);
        quantfromactivity.setText(newText);
    }

    public void downQuantity(View view) {
        if (quantity > 0) {
            quantity--;
            String newText = Integer.toString(quantity);
            quantfromactivity.setText(newText);
        }
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

                InventoryItem newItem = new InventoryItem();
                newItem.setCardInfo(currentCard);
                inventory.add(newItem);
                newItem.getCardInfo().displayAll();

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                Gson gson2 = new Gson();
                String json = gson2.toJson(inventory);
                editor.putString("user_inventory", json); // Storing string
                System.out.println("WE TRY TO PRINT THE JSON STRING HERE");
                editor.commit();
                System.out.println(json);

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