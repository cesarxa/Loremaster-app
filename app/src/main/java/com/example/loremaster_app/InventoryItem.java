package com.example.loremaster_app;

public class InventoryItem {
    private CardInfo card;
    private int quantity;

    public CardInfo getCardInfo() { return card; }
    public int getQuantity() { return quantity; }

    public void setCardInfo(CardInfo card) { this.card = card; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
