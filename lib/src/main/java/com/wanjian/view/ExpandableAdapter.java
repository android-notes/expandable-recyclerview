package com.wanjian.view;

import android.support.v7.widget.RecyclerView;

import java.util.BitSet;

/**
 * Created by wanjian on 2018/1/31.
 */

public abstract class ExpandableAdapter<G extends RecyclerView.ViewHolder, C extends RecyclerView.ViewHolder> extends NestedAdapter<G, C> {

    private BitSet isCollapsed = new BitSet();

    @Override
    int getSafeChildCount(int groupIndex) {
        if (isExpanded(groupIndex)) {
            return super.getSafeChildCount(groupIndex);
        }
        return 0;
    }

    public void collapseGroup(int groupIndex) {
        if (groupIndex < 0 || groupIndex >= getSafeGroupCount()) {
            return;
        }
        if (isExpanded(groupIndex)) {
            notifyChildItemRangeRemoved(groupIndex, 0, getSafeChildCount(groupIndex));
            isCollapsed.set(groupIndex);
        }
    }

    public void expandGroup(int groupIndex) {
        if (groupIndex < 0 || groupIndex >= getSafeGroupCount()) {
            return;
        }
        if (isExpanded(groupIndex) == false) {
            isCollapsed.clear(groupIndex);
            notifyChildItemRangeInserted(groupIndex, 0, getSafeChildCount(groupIndex));
        }
    }

    public boolean isExpanded(int groupIndex) {
        if (groupIndex < 0 || groupIndex >= getSafeGroupCount()) {
            return false;
        }
        return !isCollapsed.get(groupIndex);
    }

    public void collapseAllGroup() {
        int groupCount = getSafeGroupCount();
        isCollapsed.set(0, groupCount, true);
        notifyDataSetChanged();
    }

    public void expandAllGroup() {
        isCollapsed.clear();
        notifyDataSetChanged();
    }

}
