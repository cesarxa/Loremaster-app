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
import android.widget.Toast;

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
    TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        imageViewDisplayer = (ImageView) findViewById(R.id.imageView2);
        textViewDisplayer = (TextView) findViewById(R.id.textView);
        totalTextView = (TextView) findViewById(R.id.textView2);
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
        totalWorth();
    }

    public void viewCardAct(View view) {
        Intent intent = new Intent(this, Card.class);
        startActivity(intent);
    }

    public void getInventoryInfo(View view){
        String NewString = getCard.getText().toString();
        for (InventoryItem anItem: inventory) {
            if(NewString.equals(anItem.getCardInfo().getName())){
                currentCard = anItem.getCardInfo();
                currentCard.displayAll();
                quantity = anItem.getQuantity();
                showCard();
                Toast.makeText(getApplicationContext(),"Found " + quantity + " " + currentCard.getName() + " in Inventory", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Could not find " + NewString + " in Inventory", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void removeCard(View view){
        String NewString = getCard.getText().toString();
        List<InventoryItem> newInventory = new ArrayList<InventoryItem>();
        for (InventoryItem anItem: inventory) {
            if(NewString.equals(anItem.getCardInfo().getName())){
                currentCard = anItem.getCardInfo();
                currentCard.displayAll();
                anItem.setQuantity(anItem.getQuantity() - 1);
                quantity = anItem.getQuantity();
                showCard();
                if(anItem.getQuantity() > 0)
                    newInventory.add(anItem);
            }
            else
                newInventory.add(anItem);
        }
        inventory.clear();
        for (InventoryItem anItem: newInventory){
            inventory.add(anItem);
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        Gson gson2 = new Gson();
        String json = gson2.toJson(inventory);
        editor.putString("user_inventory", json); // Storing string
        editor.commit();

        Toast.makeText(getApplicationContext(),"Removed 1 " + currentCard.getName() + " from Inventory", Toast.LENGTH_SHORT).show();

        totalWorth();
    }

    public void showCard(){
        String murl = currentCard.getImageURIs().get("border_crop"); //newCard.getLargeImage(); // need to add method or url of images searched;
        Picasso.get().load(murl).into(imageViewDisplayer);
        String cInfo =  ("Name: " + currentCard.getName()) +
                //("\nOracle Text: " + newCard.getOracleText()) +
                //("\nType: " + newCard.getTypeLine()) +
                //("\nMana Cost: " + newCard.getManaCost()) +
                ("\nPrice USD: $" + currentCard.getPrices().get("usd")) +
                ("\nPrice USD Foil: $" + currentCard.getPrices().get("usd_foil")) +
                ("\nCurrent Quantity in Inventory: " + quantity);
        textViewDisplayer.setText(cInfo);
        textViewDisplayer.setMovementMethod(new ScrollingMovementMethod());
    }

    public void totalWorth() {
        float total = 0;
        for (InventoryItem anItem: inventory){
            if (anItem.getCardInfo().getPrices().get("usd") != null) {
                total += (Float.parseFloat(anItem.getCardInfo().getPrices().get("usd")) * anItem.getQuantity());
            }
            else if (anItem.getCardInfo().getPrices().get("usd_foil") != null) {
                total += (Float.parseFloat(anItem.getCardInfo().getPrices().get("usd_foil")) * anItem.getQuantity());
            }
        }
        total = (float) (Math.round(total * 100.0) / 100.0);
        String netWorth = ("Total Inventory Worth: $" + total);
        totalTextView.setText(netWorth);
    }

    @Override
    public void finish() {
        super.finish();
    }

}
