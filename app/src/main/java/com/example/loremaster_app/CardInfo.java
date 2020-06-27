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
        System.out.println(name);
        System.out.println(uri);
        System.out.println(oracle_text);
        System.out.println(type_line);
        System.out.println(mana_cost);
    }
}
