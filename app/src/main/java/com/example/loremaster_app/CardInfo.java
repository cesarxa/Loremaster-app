package com.example.loremaster_app;

import java.util.Map;

public class CardInfo {
    private String name;
    private String uri;
    private Map<String, String> image_uris;
    private String oracle_text;
    private String type_line;
    private String mana_cost;
    private Map<String, String> prices;

    private Map<String, String> getPrices(){
        return prices;
    }

    private Map<String, String> getImageURIs(){
        return image_uris;
    }

    public String getName() {
        return name;
    }
    public String getURI() {
        return uri;
    }
    public String getOracleText() {
        return oracle_text;
    }
    public String getTypeLine() {
        return type_line;
    }
    public String getManaCost() {
        return mana_cost;
    }

    public void displayAll() {
        System.out.println("Name: " + name);
        System.out.println("URI: " + uri);
        System.out.println("Oracle Text: " + oracle_text);
        System.out.println("Type: " + type_line);
        System.out.println("Mana Cost: " + mana_cost);
        System.out.println("Image URI (Large): " + image_uris.get("large"));
        System.out.println("Price USD: " + prices.get("usd"));
    }
}
