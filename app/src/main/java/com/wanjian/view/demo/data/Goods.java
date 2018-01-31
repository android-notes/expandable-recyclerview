package com.wanjian.view.demo.data;

/**
 * Created by wanjian on 2018/1/29.
 */

public class Goods {


    public static final int TYPE_FOOD = 1;
    public static final int TYPE_CLOTHES = 2;
    public static final int TYPE_MILK = 3;

    public String name;
    public int index;
    public int goodsType;

    public int reBindTimes = 0;

    public Goods() {
    }

    public Goods(String name, int index, int goodsType, int reBindTimes) {
        this.name = name;
        this.index = index;
        this.goodsType = goodsType;
        this.reBindTimes = reBindTimes;
    }
}
