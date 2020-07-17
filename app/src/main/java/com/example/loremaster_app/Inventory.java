package com.example.loremaster_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "iventory.java is doing stuff";
    CardInfo currentCard;
    List<InventoryItem> inventory;
    Integer quantity;
    EditText getCard;
    ImageView imageViewDisplayer;
    TextView textViewDisplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        imageViewDisplayer = (ImageView) findViewById(R.id.imageView2);
        textViewDisplayer = (TextView) findViewById(R.id.textView);
        getCard = (EditText) findViewById(R.id.searchPrefInventory);
        inventory = new ArrayList<InventoryItem>();
        quantity = 1;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String book = pref.getString("user_inventory", null);
        if (book != null)
        {
            Gson gson = new Gson();
            TypeToken<List<InventoryItem>> token = new TypeToken<List<InventoryItem>>(){};
            List<InventoryItem> prefInventory = gson.fromJson(book, token.getType());
            for (InventoryItem anItem: prefInventory) {
                inventory.add(anItem);
                anItem.getCardInfo().displayAll();
            }
        }
    }

    public void viewCardAct(View view) {
        Intent intent = new Intent(this, Card.class);
        startActivity(intent);
    }

    public void getInventoryInfo(View view){
        String NewString = getCard.getText().toString();
        for (InventoryItem anItem: inventory) {
            if(NewString.equals(anItem.getCardInfo().getName())){
                System.out.println("WE GOT INSIDE THE IF STATEMENT");
                currentCard = anItem.getCardInfo();
                currentCard.displayAll();
                showCard();
            }
        }
    }

    public void showCard(){
        String murl = currentCard.getImageURIs().get("border_crop"); //newCard.getLargeImage(); // need to add method or url of images searched;
        Picasso.get().load(murl).into(imageViewDisplayer);

        String cInfo =  ("Name: " + currentCard.getName()) +
                //("\nOracle Text: " + newCard.getOracleText()) +
                //("\nType: " + newCard.getTypeLine()) +
                //("\nMana Cost: " + newCard.getManaCost()) +
                ("\nPrice USD: $" + currentCard.getPrices().get("usd")) +
                ("\nPrice USD Foil: $" + currentCard.getPrices().get("usd_foil"));
        textViewDisplayer.setText(cInfo);
        textViewDisplayer.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void finish() {
        super.finish();
    }

}
