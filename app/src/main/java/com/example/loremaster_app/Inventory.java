package com.example.loremaster_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Inventory extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "iventory.java is doing stuff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
    }

    public void viewCardAct(View view) {
        Intent intent = new Intent(this, Card.class);
        startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    /*
    // get the card the user searches for frim shared preferences and display it
    public class getInventoryItem implements Runnable {
        //private String inventoryObject;

        // search list of inventory items for cards and display in logcat
        public void getInventoryInfo(View view) {
            Intent InventorySearch = new Intent(this, Inventory.class);
            EditText searchPrefInventory = (EditText) findViewById(R.id.searchPrefInventory);
            String message = searchPrefInventory.getText().toString();
            InventorySearch.putExtra(EXTRA_MESSAGE, message);
            startActivity(InventorySearch);

            @Override
            public void run() {

            }
        }
    }
    */
}