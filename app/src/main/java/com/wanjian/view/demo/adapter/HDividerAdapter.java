package com.wanjian.view.demo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wanjian.view.NestedAdapter;
import com.wanjian.view.demo.R;
import com.wanjian.view.demo.data.Goods;
import com.wanjian.view.demo.data.Shop;
import com.wanjian.view.demo.vh.ChildVH;
import com.wanjian.view.demo.vh.GroupVH;
import com.wanjian.view.demo.vh.child.ClothesVH;
import com.wanjian.view.demo.vh.child.FoodVH;
import com.wanjian.view.demo.vh.child.MilkVH;
import com.wanjian.view.demo.vh.group.AllVH;
import com.wanjian.view.demo.vh.group.OffLineVH;
import com.wanjian.view.demo.vh.group.OnLineVH;

import java.util.List;

/**
 * Created by wanjian on 2018/1/29.
 */

public class HDividerAdapter extends NestedAdapter<GroupVH, ChildVH> {


    private List<Shop> shopList;

    public HDividerAdapter(List<Shop> shopList) {
        super();
        this.shopList = shopList;
    }

    @Override
    public int getGroupCount() {
        return shopList.size();
    }

    @Override
    public int getChildCount(int groupIndex) {
        return shopList.get(groupIndex).goods.size();
    }

    @Override
    public int getGroupItemViewType(int groupIndex) {
        return shopList.get(groupIndex).shopType;
    }

    @Override
    public int getChildItemViewType(int groupIndex, int childIndex) {
        return shopList.get(groupIndex).goods.get(childIndex).goodsType;
    }

    @Override
    public GroupVH onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, ">>> onCreateGroupViewHolder: " + viewType);
        Context context = parent.getContext();
        if (viewType == Shop.TYPE_ALL) {
            return new AllVH(LayoutInflater.from(context).inflate(R.layout.v_item_group_all, parent, false));
        } else if (viewType == Shop.TYPE_OFFLINE) {
            return new OffLineVH(LayoutInflater.from(context).inflate(R.layout.v_item_group_offline, parent, false));
        } else {
            return new OnLineVH(LayoutInflater.from(context).inflate(R.layout.v_item_group_online, parent, false));
        }
    }

    @Override
    public void onBindGroupViewHolder(GroupVH holder, int position) {
        Log.d(TAG, "onBindGroupViewHolder: " + position + " " + holder);
        int type = getGroupItemViewType(position);
        if (type == Shop.TYPE_ALL) {
            AllVH vh = ((AllVH) holder);
            vh.index.setText("" + position);
            vh.count.setText("" + getChildCount(position));
            vh.name.setText(shopList.get(position).shopName + "\nTYPE_ALL");
        } else if (type == Shop.TYPE_OFFLINE) {
            OffLineVH vh = (OffLineVH) holder;
            vh.index.setText("" + position);
            vh.count.setText("" + getChildCount(position));
            vh.name.setText(shopList.get(position).shopName + "\nTYPE_OFFLINE");
        } else {
            OnLineVH vh = (OnLineVH) holder;
            vh.index.setText("" + position);
            vh.count.setText("" + getChildCount(position));
            vh.name.setText(shopList.get(position).shopName + "\nTYPE_ONLINE");

        }
    }

    @Override
    public ChildVH onCreateChildViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, ">>> onCreateChildViewHolder: " + viewType);
        Context context = parent.getContext();
        if (viewType == Goods.TYPE_CLOTHES) {
            return new ClothesVH(LayoutInflater.from(context).inflate(R.layout.v_item_child_clothes, parent, false));

        } else if (viewType == Goods.TYPE_FOOD) {
            return new FoodVH(LayoutInflater.from(context).inflate(R.layout.v_item_child_food, parent, false));
        } else {
            return new MilkVH(LayoutInflater.from(context).inflate(R.layout.v_item_child_milk, parent, false));

        }
    }

    @Override
    public void onBindChildViewHolder(ChildVH holder, int groupIndex, int childIndex) {
        Log.d(TAG, "onBindChildViewHolder: " + groupIndex + ":" + childIndex);
        int type = getChildItemViewType(groupIndex, childIndex);
        if (type == Goods.TYPE_CLOTHES) {
            ClothesVH vh = (ClothesVH) holder;
            vh.index.setText(groupIndex + ":" + childIndex);
            vh.name.setText(shopList.get(groupIndex).goods.get(childIndex).name);
            vh.sku.setText("TYPE_CLOTHES");
        } else if (type == Goods.TYPE_FOOD) {

            FoodVH vh = (FoodVH) holder;
            vh.index.setText(groupIndex + ":" + childIndex);
            vh.name.setText(shopList.get(groupIndex).goods.get(childIndex).name);
            vh.sku.setText("TYPE_FOOD");
        } else {

            MilkVH vh = (MilkVH) holder;
            vh.index.setText(groupIndex + ":" + childIndex);
            vh.name.setText(shopList.get(groupIndex).goods.get(childIndex).name);
            vh.sku.setText("TYPE_MILK");
        }
    }


}
