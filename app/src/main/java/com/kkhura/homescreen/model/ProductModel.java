package com.kkhura.homescreen.model;

import io.realm.RealmObject;

/**
 * Created by Kailash Khurana on 3/21/2017.
 */

public class ProductModel extends RealmObject {
    private int id;
    private String itemName;
    private String itemPic;
    private int itemSellingPrice;
    private int itemMRP;
    private float itemDiscountInPercent;
    private int itemDiscountInPrice;
    private String itemCurrency;
    private String itemCurrencySymbol;
    private String itemDescription;
    private String itemQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }

    public int getItemSellingPrice() {
        return itemSellingPrice;
    }

    public void setItemSellingPrice(int itemSellingPrice) {
        this.itemSellingPrice = itemSellingPrice;
    }

    public int getItemMRP() {
        return itemMRP;
    }

    public void setItemMRP(int itemMRP) {
        this.itemMRP = itemMRP;
    }

    public float getItemDiscountInPercent() {
        return itemDiscountInPercent;
    }

    public void setItemDiscountInPercent(float itemDiscountInPercent) {
        this.itemDiscountInPercent = itemDiscountInPercent;
    }

    public int getItemDiscountInPrice() {
        return itemDiscountInPrice;
    }

    public void setItemDiscountInPrice(int itemDiscountInPrice) {
        this.itemDiscountInPrice = itemDiscountInPrice;
    }

    public String getItemCurrency() {
        return itemCurrency;
    }

    public void setItemCurrency(String itemCurrency) {
        this.itemCurrency = itemCurrency;
    }

    public String getItemCurrencySymbol() {
        return itemCurrencySymbol;
    }

    public void setItemCurrencySymbol(String itemCurrencySymbol) {
        this.itemCurrencySymbol = itemCurrencySymbol;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
