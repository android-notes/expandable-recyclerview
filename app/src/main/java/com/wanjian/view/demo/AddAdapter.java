package com.wanjian.view.demo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanjian.view.ExpandableAdapter;
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

import static com.wanjian.view.demo.data.Goods.TYPE_FOOD;

/**
 * Created by wanjian on 2018/1/29.
 */

public class AddAdapter extends ExpandableAdapter<GroupVH, ChildVH> {


    private List<Shop> shopList;

    public AddAdapter(List<Shop> shopList) {
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
            return new AllVH(LayoutInflater.from(context).inflate(R.layout.h_item_group_all, parent, false));
        } else if (viewType == Shop.TYPE_OFFLINE) {
            return new OffLineVH(LayoutInflater.from(context).inflate(R.layout.h_item_group_offline, parent, false));
        } else {
            return new OnLineVH(LayoutInflater.from(context).inflate(R.layout.h_item_group_online, parent, false));
        }
    }

    @Override
    public void onBindGroupViewHolder(final GroupVH holder, final int position) {
        Log.d(TAG, "onBindGroupViewHolder: " + position + " " + holder);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notifyGroupChanged(position);
//
//                notifyChildItemRangeChanged(position, 0, getChildCount(position));
//
//                notifyChildItemRangeChanged(position, 1, getChildCount(position) - 1);
//
//                notifyChildItemRangeRemoved(position, 0, getChildCount(position));
//                shopList.get(position).goods.clear();


                int count = 2;
                int start = getChildCount(position);
                notifyChildItemRangeInserted(position, start, count);
                for (int i = start; i < start + count; i++) {
                    shopList.get(position).goods.add(start, new Goods("add", i, TYPE_FOOD, 0));
                }
            }
        };


        int type = getGroupItemViewType(position);
        if (type == Shop.TYPE_ALL) {
            AllVH vh = ((AllVH) holder);
            vh.index.setText((shopList.get(position).reBindTimes++) + "");
            vh.count.setText("" + getChildCount(position));
            vh.name.setText(shopList.get(position).shopName + "  TYPE_ALL");
            vh.refresh.setOnClickListener(listener);
            vh.refresh.setText("Add");
        } else if (type == Shop.TYPE_OFFLINE) {
            OffLineVH vh = (OffLineVH) holder;
            vh.index.setText((shopList.get(position).reBindTimes++) + "");
            vh.count.setText("" + getChildCount(position));
            vh.name.setText(shopList.get(position).shopName + "  TYPE_OFFLINE");
            vh.refresh.setOnClickListener(listener);
            vh.refresh.setText("Add");
        } else {
            OnLineVH vh = (OnLineVH) holder;
            vh.index.setText((shopList.get(position).reBindTimes++) + "");
            vh.count.setText("" + getChildCount(position));
            vh.name.setText(shopList.get(position).shopName + "  TYPE_ONLINE");
            vh.refresh.setOnClickListener(listener);
            vh.refresh.setText("Add");
        }

    }

    @Override
    public ChildVH onCreateChildViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, ">>> onCreateChildViewHolder: " + viewType);
        Context context = parent.getContext();
        if (viewType == Goods.TYPE_CLOTHES) {
            return new ClothesVH(LayoutInflater.from(context).inflate(R.layout.h_item_child_clothes, parent, false));

        } else if (viewType == TYPE_FOOD) {
            return new FoodVH(LayoutInflater.from(context).inflate(R.layout.h_item_child_food, parent, false));
        } else {
            return new MilkVH(LayoutInflater.from(context).inflate(R.layout.h_item_child_milk, parent, false));

        }
    }

    @Override
    public void onBindChildViewHolder(ChildVH holder, final int groupIndex, final int childIndex) {
        Log.d(TAG, "onBindChildViewHolder: " + groupIndex + ":" + childIndex);
        int type = getChildItemViewType(groupIndex, childIndex);
        if (type == Goods.TYPE_CLOTHES) {
            ClothesVH vh = (ClothesVH) holder;
            Goods goods = shopList.get(groupIndex).goods.get(childIndex);
            vh.index.setText((goods.reBindTimes++) + "");
            vh.name.setText(goods.name);
            vh.sku.setText("TYPE_CLOTHES");
        } else if (type == TYPE_FOOD) {

            FoodVH vh = (FoodVH) holder;
            Goods goods = shopList.get(groupIndex).goods.get(childIndex);
            vh.index.setText((goods.reBindTimes++) + "");
            vh.name.setText(goods.name);
            vh.sku.setText("TYPE_FOOD");
        } else {

            MilkVH vh = (MilkVH) holder;
            Goods goods = shopList.get(groupIndex).goods.get(childIndex);
            vh.index.setText((goods.reBindTimes++) + "");
            vh.name.setText(goods.name);
            vh.sku.setText("TYPE_MILK");
        }

    }


}
