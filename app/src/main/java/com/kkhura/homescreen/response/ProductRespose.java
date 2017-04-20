package com.kkhura.homescreen.response;

import com.kkhura.homescreen.constant.ProductConstant;
import com.kkhura.homescreen.model.ProductModel;
import com.kkhura.loginscreen.model.UserMo;
import com.kkhura.network.BaseResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kailash Khurana on 1/24/2017.
 */

public class ProductRespose extends BaseResponse {

    public ProductRespose(JSONObject response, long transactionId) {
        super(response, transactionId);
        parseResponse(response);
    }

    private void parseResponse(JSONObject response) {
        try {
            JSONArray product = response.getJSONArray("product");
            for (int i = 0; i < product.length(); i++) {
                parseProduct(product.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseProduct(JSONObject response) {
        Realm myRealm = Realm.getDefaultInstance();
        myRealm.beginTransaction();

        // Create an object
        ProductModel productMo = myRealm.createObject(ProductModel.class);
        try {
            productMo.setId(response.getInt(ProductConstant.ITEM_ID));
            productMo.setItemName(response.getString(ProductConstant.ITEM_NAME));
            productMo.setItemPic(response.getString(ProductConstant.ITME_PIC));
            productMo.setItemSellingPrice(response.getInt(ProductConstant.ITEM_SELLING_PRICE));
            productMo.setItemMRP(response.getInt(ProductConstant.ITEM_MRP));
            productMo.setItemDiscountInPercent(response.getInt(ProductConstant.ITEM_DISCOUNT_IN_PERCENT));
            productMo.setItemDiscountInPrice(response.getInt(ProductConstant.ITEM_DISCOUNT_IN_PRICE));
            productMo.setItemCurrency(response.getString(ProductConstant.ITEM_CURRENCY));
            productMo.setItemCurrencySymbol(response.getString(ProductConstant.ITEM_CURRENCY_SYMBOL));
            productMo.setItemDescription(response.getString(ProductConstant.ITEM_DESCRIPTION));
            productMo.setItemQuantity(response.getString(ProductConstant.ITEM_QUNTITY));
            myRealm.commitTransaction();
        } catch (JSONException e) {
        }
    }
}
