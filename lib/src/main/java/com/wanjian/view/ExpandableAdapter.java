package com.wanjian.view;

import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanjian on 2018/1/31.
 */

public abstract class ExpandableAdapter<G extends RecyclerView.ViewHolder, C extends RecyclerView.ViewHolder> extends NestedAdapter<G, C> {

    private Map<Integer, Boolean> isExpanded = new HashMap<>();

    @Override
    int getSafeChildCount(int groupIndex) {
        if (isExpand(groupIndex)) {
            return super.getSafeChildCount(groupIndex);
        }
        return 0;
    }

    public void collapseGroup(int groupIndex) {
        if (isExpand(groupIndex)) {
            notifyChildItemRangeRemoved(groupIndex, 0, getSafeChildCount(groupIndex));
            isExpanded.put(groupIndex, false);
        }
    }

    public void expandGroup(int groupIndex) {
        if (isExpand(groupIndex) == false) {
            isExpanded.put(groupIndex, true);
            notifyChildItemRangeInserted(groupIndex, 0, getSafeChildCount(groupIndex));
        }
    }

    public boolean isExpand(int groupIndex) {
        Boolean state = isExpanded.get(groupIndex);
        return state == null || state;
    }

    public void collapseAllGroup() {
        int groupCount = getSafeGroupCount();
        isExpanded.clear();
        for (int i = 0; i < groupCount; i++) {
            isExpanded.put(i, false);
        }
        notifyDataSetChanged();
    }

    public void expandAllGroup() {
        isExpanded.clear();
        notifyDataSetChanged();
    }

}
