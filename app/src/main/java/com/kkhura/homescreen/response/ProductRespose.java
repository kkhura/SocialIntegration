package com.kkhura.homescreen.response;

import com.kkhura.homescreen.constant.ProductConstant;
import com.kkhura.homescreen.model.ProductModel;
import com.kkhura.loginscreen.model.UserMo;
import com.kkhura.network.BaseResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kailash Khurana on 1/24/2017.
 */

public class ProductRespose extends BaseResponse {

    private ArrayList<ProductModel> productModelArrayList;

    public ProductRespose(JSONObject response, long transactionId) {
        super(response, transactionId);
        parseResponse(response);
    }

    public ProductRespose(ArrayList<ProductModel> productModelArrayList, long transactionId) {
        super(new JSONArray(), transactionId);
        this.productModelArrayList = productModelArrayList;
    }

    private void parseResponse(JSONObject response) {
        try {
            JSONArray product = response.getJSONArray("product");
            productModelArrayList = new ArrayList<>();
            for (int i = 0; i < product.length(); i++) {
                productModelArrayList.add(parseProduct(product.getJSONObject(i)));
            }
            Realm myRealm = Realm.getDefaultInstance();
            myRealm.beginTransaction();
            myRealm.copyToRealm(productModelArrayList);
            myRealm.commitTransaction();

//            ProductModel productMo = myRealm.createObject(ProductModel.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ProductModel parseProduct(JSONObject response) {
        ProductModel productModel = new ProductModel();
        try {
            productModel.setId(response.getInt(ProductConstant.ITEM_ID));
            productModel.setItemName(response.getString(ProductConstant.ITEM_NAME));
            productModel.setItemPic(response.getString(ProductConstant.ITME_PIC));
            productModel.setItemSellingPrice(response.getInt(ProductConstant.ITEM_SELLING_PRICE));
            productModel.setItemMRP(response.getInt(ProductConstant.ITEM_MRP));
            productModel.setItemDiscountInPercent(response.getInt(ProductConstant.ITEM_DISCOUNT_IN_PERCENT));
            productModel.setItemDiscountInPrice(response.getInt(ProductConstant.ITEM_DISCOUNT_IN_PRICE));
            productModel.setItemCurrency(response.getString(ProductConstant.ITEM_CURRENCY));
            productModel.setItemCurrencySymbol(response.getString(ProductConstant.ITEM_CURRENCY_SYMBOL));
            productModel.setItemDescription(response.getString(ProductConstant.ITEM_DESCRIPTION));
            productModel.setItemQuantity(response.getString(ProductConstant.ITEM_QUNTITY));
        } catch (JSONException e) {
        }
        return productModel;
    }

    public ArrayList<ProductModel> getProductModelArrayList() {
        return productModelArrayList;
    }
}
