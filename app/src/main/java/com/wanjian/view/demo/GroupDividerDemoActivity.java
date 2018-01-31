package com.wanjian.view.demo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wanjian.view.NestedAdapterDivider;
import com.wanjian.view.demo.data.Shop;

import java.util.List;

import static com.wanjian.view.demo.utils.Utils.geneRandomData;
import static com.wanjian.view.demo.utils.Utils.showJson;


/**
 * Created by wanjian on 2018/1/29.
 */
public class GroupDividerDemoActivity extends AppCompatActivity {
    NestedAdapterDivider divider;
    List<Shop> shopList;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_divider);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        shopList = geneRandomData();
        showJson(shopList);

        vertical(recyclerView);


        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Shop> newData = geneRandomData();
                shopList.clear();

                shopList.addAll(newData);
                showJson(shopList);
                adapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.orientation).setOnClickListener(new View.OnClickListener() {
            boolean vertical = true;

            @Override
            public void onClick(View v) {
                vertical = !vertical;
                recyclerView.getRecycledViewPool().clear();
                if (vertical) {
                    vertical(recyclerView);
                } else {
                    horizontal(recyclerView);
                }

            }
        });
    }


    private void horizontal(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (divider != null) {
            recyclerView.removeItemDecoration(divider);
        }
        divider = new NestedAdapterDivider(this, NestedAdapterDivider.HORIZONTAL);

        divider
                .setDividerBeforeFirstGroup(getDividerDrawable(R.drawable.v_divider_before_first))
                .setDividerBetweenGroup(getDividerDrawable(R.drawable.v_divider_between_group))
                .setDividerAfterLastGroup(getDividerDrawable(R.drawable.v_divider_after_last))
                .setDividerBetweenChild(getDividerDrawable(R.drawable.v_divider_between_child))
                .setDividerBetweenGroupAndChild(getDividerDrawable(R.drawable.v_divider_between_group_child))
        ;

        recyclerView.addItemDecoration(divider);

        adapter = new HMyAdapter(shopList);
        recyclerView.setAdapter(adapter);
    }

    private void vertical(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (divider != null) {
            recyclerView.removeItemDecoration(divider);
        }
        divider = new NestedAdapterDivider(this, NestedAdapterDivider.VERTICAL);

        divider
                .setDividerBeforeFirstGroup(getDividerDrawable(R.drawable.h_divider_before_first))
                .setDividerBetweenGroup(getDividerDrawable(R.drawable.h_divider_between_group))
                .setDividerAfterLastGroup(getDividerDrawable(R.drawable.h_divider_after_last))
                .setDividerBetweenChild(getDividerDrawable(R.drawable.h_divider_between_child))
                .setDividerBetweenGroupAndChild(getDividerDrawable(R.drawable.h_divider_between_group_child))
        ;

        recyclerView.addItemDecoration(divider);

        adapter = new VMyAdapter(shopList);
        recyclerView.setAdapter(adapter);

    }

    public Drawable getDividerDrawable(int resId) {
        return getResources().getDrawable(resId);
    }


}
