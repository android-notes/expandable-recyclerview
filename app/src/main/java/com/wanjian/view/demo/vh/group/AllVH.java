package com.wanjian.view.demo.vh.group;

import android.view.View;
import android.widget.TextView;

import com.wanjian.view.demo.R;
import com.wanjian.view.demo.vh.GroupVH;

/**
 * Created by wanjian on 2018/1/29.
 */

public class AllVH extends GroupVH {
    public TextView index;
    public TextView name;
    public TextView count;

    public AllVH(View itemView) {
        super(itemView);
        index = itemView.findViewById(R.id.index);
        name = itemView.findViewById(R.id.name);
        count = itemView.findViewById(R.id.count);
    }
}
