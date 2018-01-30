package com.wanjian.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import static android.support.v7.widget.RecyclerView.NO_ID;

/**
 * Created by wanjian on 2018/1/29.
 */

public abstract class NestedAdapter<G extends ViewHolder, C extends ViewHolder> extends RecyclerView.Adapter<ViewHolder> {
    public static final String TAG = NestedAdapter.class.getName();

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int groupCount = getSafeGroupCount();
        for (int i = 0; i < groupCount; i++) {
            int type = getGroupItemViewType(i);
            if (type == -viewType) {
                return onCreateGroupViewHolder(parent, type);
            }
            int childCount = getSafeChildCount(i);
            for (int j = 0; j < childCount; j++) {
                type = getChildItemViewType(i, j);
                if (type == viewType) {
                    return onCreateChildViewHolder(parent, type);
                }
            }
        }

        return null;
    }

    @Override
    public final long getItemId(int position) {
        int groupCount = getSafeGroupCount();
        int count = 0;
        for (int i = 0; i < groupCount; i++) {
            if (count == position) {
                return getGroupItemId(i);
            }
            count++;

            int childCount = getSafeChildCount(i);

            if (count + childCount > position) {
                return getChildItemId(position - count);
            }
            count += childCount;
        }
        return super.getItemId(position);
    }

    public long getGroupItemId(int position) {
        return NO_ID;
    }

    public long getChildItemId(int position) {
        return NO_ID;
    }

    @Override
    public final void onBindViewHolder(ViewHolder holder, int position) {
        int groupCount = getSafeGroupCount();
        int count = 0;
        for (int i = 0; i < groupCount; i++) {
            if (count == position) {
                onBindGroupViewHolder((G) holder, i);
                return;
            }
            count++;

            int childCount = getSafeChildCount(i);

            if (count + childCount > position) {
                onBindChildViewHolder((C) holder, i, position - count);
                return;
            }
            count += childCount;
        }

    }

    @Override
    public final int getItemViewType(int position) {
        int groupCount = getSafeGroupCount();
        int count = 0;
        for (int i = 0; i < groupCount; i++) {
            if (count == position) {
                int groupType = getGroupItemViewType(i);
                if (groupType <= 0) {
                    throw new IllegalArgumentException("GroupItemViewType can not be less than 1 ！");
                }
                return -groupType;
            }
            count++;

            int childCount = getSafeChildCount(i);

            if (count + childCount > position) {
                int childType = getChildItemViewType(i, position - count);
                if (childType <= 0) {
                    throw new IllegalArgumentException("ChildItemViewType can not be less than 1 ！");
                }
                return childType;
            }
            count += childCount;
        }

        return 0;
    }

    @Override
    public final int getItemCount() {
        int groupCount = getSafeGroupCount();
        int count = groupCount;
        for (int i = 0; i < groupCount; i++) {
            count += getSafeChildCount(i);
        }
        return count;
    }

    int getSafeGroupCount() {
        int count = getGroupCount();
        if (count < 0) {
            throw new IllegalArgumentException("GroupCount can not be less than 0 !");
        }
        return count;
    }

    int getSafeChildCount(int groupIndex) {
        int count = getChildCount(groupIndex);
        if (count < 0) {
            throw new IllegalArgumentException("ChildCount can not be less than 0 !");
        }
        return count;
    }

    protected abstract int getGroupCount();

    protected abstract int getChildCount(int groupIndex);

    protected int getGroupItemViewType(int groupIndex) {
        return 1;
    }

    protected int getChildItemViewType(int groupIndex, int childIndex) {
        return 1;
    }

    protected abstract G onCreateGroupViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindGroupViewHolder(G holder, int groupIndex);

    protected abstract C onCreateChildViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindChildViewHolder(C holder, int groupIndex, int childIndex);

}
