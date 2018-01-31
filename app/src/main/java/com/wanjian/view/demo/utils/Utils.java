package com.wanjian.view.demo.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wanjian.view.NestedAdapter;
import com.wanjian.view.demo.data.Goods;
import com.wanjian.view.demo.data.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjian on 2018/1/31.
 */

public class Utils {

    public static void showJson(List<Shop> shopList) {
        String s = new Gson().toJson(shopList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(s);
        s = gson.toJson(je);
        Log.d(NestedAdapter.TAG, "showJson: \n" + s);
    }

    public static List<Shop> geneRandomData() {
        List<Shop> shopList = new ArrayList<>();
        int count = (int) (Math.random() * 30);
        for (int i = 0; i < count; i++) {
            Shop shop = new Shop();
            shop.index = i;

            double random = Math.random();
            if (random < 0.3) {
                shop.shopType = Shop.TYPE_ONLINE;
                shop.shopName = "Amazon NO:" + i;
            } else if (random < 0.6) {
                shop.shopType = Shop.TYPE_OFFLINE;
                shop.shopName = "Suning NO:" + i;
            } else {
                shop.shopType = Shop.TYPE_ALL;
                shop.shopName = "Shop NO:" + i;
            }

            shop.goods = geneGoodsInfo(i);

            shopList.add(shop);
        }

        return shopList;

    }

    private static List<Goods> geneGoodsInfo(int i) {
        List<Goods> goodsList = new ArrayList<>();

        int count = (int) (Math.random() * 5);
        for (int j = 0; j < count; j++) {
            Goods goods = new Goods();

            goods.index = j;

            double random = Math.random();
            if (random < 0.3) {
                goods.goodsType = Goods.TYPE_CLOTHES;
                goods.name = "Clothes NO:" + j;

            } else if (random < 0.6) {
                goods.goodsType = Goods.TYPE_FOOD;
                goods.name = "Food NO:" + j;

            } else {
                goods.goodsType = Goods.TYPE_MILK;
                goods.name = "Milk NO:" + j;

            }
            goodsList.add(goods);
        }

        return goodsList;
    }
}
