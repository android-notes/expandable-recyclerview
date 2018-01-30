package com.wanjian.view.demo.vh.child;

import android.view.View;
import android.widget.TextView;

import com.wanjian.view.demo.R;
import com.wanjian.view.demo.vh.ChildVH;

/**
 * Created by wanjian on 2018/1/29.
 */

public class ClothesVH extends ChildVH {

    public TextView index;
    public TextView name;
    public TextView sku;

    public ClothesVH(View itemView) {
        super(itemView);
        index = itemView.findViewById(R.id.index);
        name = itemView.findViewById(R.id.name);
        sku = itemView.findViewById(R.id.sku);
    }
}
