package com.wanjian.view.demo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wanjian.view.NestedAdapter;
import com.wanjian.view.NestedAdapterDivider;
import com.wanjian.view.demo.data.Goods;
import com.wanjian.view.demo.data.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjian on 2018/1/29.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NestedAdapterDivider divider = new NestedAdapterDivider(this, NestedAdapterDivider.VERTICAL);

        divider
                .setDividerBeforeFirstGroup(getDividerDrawable(R.drawable.divider_before_first))
                .setDividerBetweenGroup(getDividerDrawable(R.drawable.divider_between_group))
                .setDividerAfterLastGroup(getDividerDrawable(R.drawable.divider_after_list))
                .setDividerBetweenChild(getDividerDrawable(R.drawable.divider_between_child))
                .setDividerBetweenGroupAndChild(getDividerDrawable(R.drawable.divider_between_group_child))
        ;

//        DividerItemDecoration divider=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        divider.setDrawable(getDividerDrawable(R.drawable.divider_before_first));
        recyclerView.addItemDecoration(divider);


        final List<Shop> shopList = initData();
        showJson(shopList);
        final RecyclerView.Adapter adapter = new MyAdapter(shopList);
        recyclerView.setAdapter(adapter);


        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Shop> newData = initData();
                shopList.clear();

                shopList.addAll(newData);
                showJson(shopList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Drawable getDividerDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    private void showJson(List<Shop> shopList) {
        String s = new Gson().toJson(shopList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(s);
        s = gson.toJson(je);
        Log.d(NestedAdapter.TAG, "showJson: \n" + s);
    }

//    private List<Shop> initData() {
//        List<Shop> shopList = new ArrayList<>();
//        int count = 3;
//        for (int i = 0; i < count; i++) {
//            Shop shop = new Shop();
//            shop.index = i;
//            shop.shopName = "NO:" + i;
//            double random = 1f * i / count;
//            if (random < 0.3) {
//                shop.shopType = Shop.TYPE_ONLINE;
//            } else if (random < 0.6) {
//                shop.shopType = Shop.TYPE_OFFLINE;
//            } else {
//                shop.shopType = Shop.TYPE_ALL;
//            }
//
//            shop.goods = geneGoodsInfo(i);
//
//            shopList.add(shop);
//        }
//
//        return shopList;
//
//    }
//
//    private List<Goods> geneGoodsInfo(int i) {
//        List<Goods> goodsList = new ArrayList<>();
//
//        int count = 2;
//        for (int j = 0; j < count; j++) {
//            Goods goods = new Goods();
//
//            goods.index = j;
//            char c = (char) ('A' + Math.random() * 26);
//            goods.name = "Goods NO:" + String.valueOf(c);
//
//            double random = 1f * j / count;
//            if (random < 0.3) {
//                goods.goodsType = Goods.TYPE_CLOTHES;
//            } else if (random < 0.6) {
//                goods.goodsType = Goods.TYPE_FOOD;
//            } else {
//                goods.goodsType = Goods.TYPE_MILK;
//            }
//            goodsList.add(goods);
//        }
//
//        return goodsList;
//    }
    private List<Shop> initData() {
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

    private List<Goods> geneGoodsInfo(int i) {
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
