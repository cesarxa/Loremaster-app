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


// SCRIPTURE CODE NOTES BOTH MAIN ACTIVTY AND DISPLAY !!!!!!!!!!!!!!!!!!!

//public class MainActivity extends AppCompatActivity {
//
//    // These are the constant values we'll be using for keys throughout the application
//    // The're put here for simplicity, but in RealLife™ we'd store them as string resources
//    // See: https://developer.android.com/guide/topics/resources/string-resource.html
//    public static final String BOOK_PART = "BOOK_PART";
//    public static final String CHAPTER_PART = "CHAPTER_PART";
//    public static final String VERSE_PART = "VERSE_PART";
//
//    public static final String APP_PREFS = "APPLICATION_PREFERENCES";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        setTitle("Favorite Scripture");
//    }
//
//    // Some of you might have used OnClickListener rather than binding the click handler
//    // directly in the XML layout. See this SO post for an explanation of the differences:
//    // http://stackoverflow.com/questions/21319996/android-onclick-in-xml-vs-onclicklistener
//    public void onDisplayScripture(View theButton) {
//
//        EditText txtBook = (EditText) findViewById(R.id.txtBook);
//        EditText txtChapter = (EditText) findViewById(R.id.txtChapter);
//        EditText txtVerse = (EditText) findViewById(R.id.txtVerse);
//
//        String book = txtBook.getText().toString();
//        String chapter = txtChapter.getText().toString();
//        String verse = txtVerse.getText().toString();
//
//        String scripture = String.format("%s %s:%s", book, chapter, verse);
//        Log.d(getClass().getName(), String.format("About to create intent with %s", scripture));
//
//        // Some of you might have used a bundle rather than putExtra(). You can see a discussion
//        // of the differences here:
//        // http://stackoverflow.com/questions/15243798/advantages-of-using-bundle-instead-of-direct-intent-putextra-in-android
//        Intent scriptureIntent = new Intent(this, DisplayActivity.class);
//        scriptureIntent.putExtra(BOOK_PART, book);
//        scriptureIntent.putExtra(CHAPTER_PART, chapter);
//        scriptureIntent.putExtra(VERSE_PART, verse);
//
//        startActivity(scriptureIntent);
//    }
//
//    public void onLoadScripture(View theButton) {
//
//        // See https://developer.android.com/training/basics/data-storage/shared-preferences.html
//        SharedPreferences sharedPref = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
//
//        // Get the book, or return null if it isn't there.
//        String book = sharedPref.getString(BOOK_PART, null);
//        String chapter = sharedPref.getString(CHAPTER_PART, null);
//        String verse = sharedPref.getString(VERSE_PART, null);
//
//        if(book == null) {
//            Toast.makeText(this, "No saved scripture found.", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            EditText txtBook = (EditText) findViewById(R.id.txtBook);
//            EditText txtChapter = (EditText) findViewById(R.id.txtChapter);
//            EditText txtVerse = (EditText) findViewById(R.id.txtVerse);
//
//            txtBook.setText(book);
//            txtChapter.setText(chapter);
//            txtVerse.setText(verse);
//
//            // See https://developer.android.com/guide/topics/ui/notifiers/toasts.html
//            Toast.makeText(this, "Loaded scripture.", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//}

// DISPLAY SCRIPTURE NOTES

//public class DisplayActivity extends AppCompatActivity {
//
//    String _book;
//    String _chapter;
//    String _verse;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display);
//
//        Intent intent = getIntent();
//
//        // By using static constants as the key names rather than bare strings,
//        // we guard against typos. If I had accidentally typed:
//        //
//        // getStringExtra("CHAPTR") or
//        // getStringExtra("chapter")
//        //
//        // the program would have compiled and run fine, but we would have failed
//        // to retrieve the chapter string. However, by using constant names, if I type:
//        //
//        // getStringExtra(MainActivity.CHAPTR_PART)
//        //
//        // The compiler would catch that error, because there is no const named CHAPTR_PART.
//        //
//        // It's always better to let the compiler catch errors whenever possible.
//        _book = intent.getStringExtra(MainActivity.BOOK_PART);
//        _chapter = intent.getStringExtra(MainActivity.CHAPTER_PART);
//        _verse = intent.getStringExtra(MainActivity.VERSE_PART);
//
//        String scripture = String.format("%s %s:%s", _book, _chapter, _verse);
//
//        Log.d(getClass().getName(), String.format("Received intent with %s", scripture));
//
//        TextView scriptureLabel = (TextView) findViewById(R.id.lblScripture);
//        scriptureLabel.setText(scripture);
//    }
//
//    public void onSaveScripture(View theButton) {
//
//        // See https://developer.android.com/training/basics/data-storage/shared-preferences.html
//        SharedPreferences sharedPrefs = getSharedPreferences(MainActivity.APP_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//
//        editor.putString(MainActivity.BOOK_PART, _book);
//        editor.putString(MainActivity.CHAPTER_PART, _chapter);
//        editor.putString(MainActivity.VERSE_PART, _verse);
//
//        // Why apply() and not commit() ?
//        // See: http://stackoverflow.com/questions/5960678/whats-the-difference-between-commit-and-apply-in-shared-preference
//        editor.apply();
//
//        // See https://developer.android.com/guide/topics/ui/notifiers/toasts.html
//        Toast.makeText(this, "Scripture has been saved.", Toast.LENGTH_SHORT).show();
//    }
//}
