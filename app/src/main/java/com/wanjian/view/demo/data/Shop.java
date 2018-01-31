package com.wanjian.view.demo.data;

import java.util.List;

/**
 * Created by wanjian on 2018/1/29.
 */

public class Shop {

    public static final int TYPE_ONLINE = 1;
    public static final int TYPE_OFFLINE = 2;
    public static final int TYPE_ALL = 3;

    public String shopName;
    public int index;
    public int shopType;

    public int reBindTimes = 0;
    public List<Goods> goods;

}
