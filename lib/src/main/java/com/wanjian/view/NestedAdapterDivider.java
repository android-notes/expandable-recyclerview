package com.wanjian.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wanjian on 2018/1/30.
 * <br/>
 * <p>
 * 分隔条。支持为 {@link NestedAdapter}添加头部，尾部，group间，child间，group和child间添加自定义divider
 *
 * @see NestedAdapterDivider#setDividerBeforeFirstGroup(Drawable)
 * @see NestedAdapterDivider#setDividerAfterLastGroup(Drawable)
 * @see NestedAdapterDivider#setDividerBetweenChild(Drawable)
 * @see NestedAdapterDivider#setDividerBetweenGroupAndChild(Drawable)
 * @see NestedAdapterDivider#setDividerBetweenGroup(Drawable)
 */

public class NestedAdapterDivider extends DividerItemDecoration {

    protected final int orientation;
    private final Rect mBounds = new Rect();
    private Drawable dividerBetweenGroup;
    private Drawable dividerBetweenChild;
    private Drawable dividerBetweenGroupAndChild;
    private Drawable dividerBeforeFirstGroup;
    private Drawable dividerAfterLastGroup;

    public NestedAdapterDivider(Context context, int orientation) {
        super(context, orientation);
        this.orientation = orientation;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (isNeedIntercept(parent) == false) {
            super.onDraw(c, parent, state);
            return;
        }

        if (parent.getLayoutManager() == null) {
            return;
        }
        NestedAdapter adapter = (NestedAdapter) parent.getAdapter();
        if (orientation == VERTICAL) {
            drawVertical(c, parent, adapter);
        } else {
            drawHorizontal(c, parent, adapter);
        }
    }


    private void drawVertical(Canvas canvas, RecyclerView parent, NestedAdapter adapter) {
        canvas.save();
        final int left;
        final int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            getDecoratedBoundsWithMarginsInt(child, mBounds);
            mBounds.bottom -= child.getHeight();

            int position = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewAdapterPosition();
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            Drawable divider = getDrawableForChild(position, adapter);
            if (divider != null) {
                final int top = bottom - divider.getIntrinsicHeight();
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
        }
        if (childCount > 0 && dividerAfterLastGroup != null) {
            View child = parent.getChildAt(childCount - 1);
            int position = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewAdapterPosition();
            if (position == adapter.getItemCount() - 1) {
                getDecoratedBoundsWithMarginsInt(child, mBounds);

                final int top = mBounds.bottom + Math.round(child.getTranslationY());
                Drawable divider = dividerAfterLastGroup;
                final int bottom = top + divider.getIntrinsicHeight();
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
        }

        canvas.restore();
    }

    private void getDecoratedBoundsWithMarginsInt(View view, Rect outBounds) {
        final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
        outBounds.set(view.getLeft() - lp.leftMargin,
                view.getTop() - lp.topMargin,
                view.getRight() + lp.rightMargin,
                view.getBottom() + lp.bottomMargin);
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent, NestedAdapter adapter) {
        canvas.save();
        final int top;
        final int bottom;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            getDecoratedBoundsWithMarginsInt(child, mBounds);
            mBounds.right -= child.getWidth();
            int position = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewAdapterPosition();

            final int right = mBounds.right + Math.round(child.getTranslationX());
            Drawable divider = getDrawableForChild(position, adapter);
            if (divider != null) {
                final int left = right - divider.getIntrinsicWidth();
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
        }

        if (childCount > 0 && dividerAfterLastGroup != null) {
            View child = parent.getChildAt(childCount - 1);
            int position = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewAdapterPosition();
            if (position == adapter.getItemCount() - 1) {
                getDecoratedBoundsWithMarginsInt(child, mBounds);
                final int left = mBounds.right + Math.round(child.getTranslationX());
                Drawable divider = dividerAfterLastGroup;
                final int right = left + divider.getIntrinsicWidth();
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
        }

        canvas.restore();
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isNeedIntercept(parent) == false) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }
        NestedAdapter adapter = ((NestedAdapter) parent.getAdapter());
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();

        int lastPosition = adapter.getItemCount() - 1;
        if (position == 0) {
            int l = 0, t = 0, r = 0, b = 0;
            if (dividerBeforeFirstGroup != null) {
                if (orientation == VERTICAL) {
                    t = dividerBeforeFirstGroup.getIntrinsicHeight();
                } else {
                    l = dividerBeforeFirstGroup.getIntrinsicWidth();
                }
            }
            setRightBottom(outRect, position, lastPosition, l, t, r, b);
            return;
        }

        int groupCount = adapter.getSafeGroupCount();
        int count = 0;
        for (int i = 0; i < groupCount; i++) {
            int l = 0, t = 0, r = 0, b = 0;
            if (count == position) {
                if (dividerBetweenGroup != null) {
                    if (orientation == VERTICAL) {
                        t = dividerBetweenGroup.getIntrinsicHeight();
                    } else {
                        l = dividerBetweenGroup.getIntrinsicWidth();
                    }
                }
                setRightBottom(outRect, position, lastPosition, l, t, r, b);
                return;
            }
            count++;

            int childCount = adapter.getSafeChildCount(i);

            if (count + childCount > position) {
                int childIndex = position - count;

                if (childIndex == 0) {
                    if (dividerBetweenGroupAndChild != null) {
                        if (orientation == VERTICAL) {
                            t = dividerBetweenGroupAndChild.getIntrinsicHeight();
                        } else {
                            l = dividerBetweenGroupAndChild.getIntrinsicWidth();
                        }
                    }

                    setRightBottom(outRect, position, lastPosition, l, t, r, b);
                    return;
                } else {
                    if (dividerBetweenChild != null) {
                        if (orientation == VERTICAL) {
                            t = dividerBetweenChild.getIntrinsicHeight();
                        } else {
                            l = dividerBetweenChild.getIntrinsicWidth();
                        }

                    }
                    setRightBottom(outRect, position, lastPosition, l, t, r, b);
                    return;
                }

            }
            count += childCount;
        }


    }

    private void setRightBottom(Rect outRect, int position, int lastPosition, int l, int t, int r, int b) {
        if (position == lastPosition) {
            if (dividerAfterLastGroup != null) {
                if (orientation == VERTICAL) {
                    b = dividerAfterLastGroup.getIntrinsicHeight();
                } else {
                    r = dividerAfterLastGroup.getIntrinsicWidth();
                }
            }
        }
        outRect.set(l, t, r, b);
    }

    private Drawable getDrawableForChild(int position, NestedAdapter adapter) {
        if (position == 0) {
            return dividerBeforeFirstGroup;
        }
        int groupCount = adapter.getSafeGroupCount();
        int count = 0;
        for (int i = 0; i < groupCount; i++) {
            if (count == position) {
                return dividerBetweenGroup;
            }
            count++;

            int childCount = adapter.getSafeChildCount(i);

            if (count + childCount > position) {
                int childIndex = position - count;

                if (childIndex == 0) {
                    return dividerBetweenGroupAndChild;
                } else {
                    return dividerBetweenChild;
                }

            }
            count += childCount;
        }

        return null;
    }

    public NestedAdapterDivider setDividerBetweenGroup(Drawable dividerBetweenGroup) {
        this.dividerBetweenGroup = dividerBetweenGroup;
        return this;
    }

    public NestedAdapterDivider setDividerBetweenChild(Drawable dividerBetweenChild) {
        this.dividerBetweenChild = dividerBetweenChild;
        return this;
    }

    public NestedAdapterDivider setDividerBetweenGroupAndChild(Drawable dividerBetweenGroupAndChild) {
        this.dividerBetweenGroupAndChild = dividerBetweenGroupAndChild;
        return this;
    }

    public NestedAdapterDivider setDividerBeforeFirstGroup(Drawable dividerBeforeFirstGroup) {
        this.dividerBeforeFirstGroup = dividerBeforeFirstGroup;
        return this;
    }

    public NestedAdapterDivider setDividerAfterLastGroup(Drawable dividerAfterLastGroup) {
        this.dividerAfterLastGroup = dividerAfterLastGroup;
        return this;
    }


    private boolean isNeedIntercept(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        return recyclerView.getAdapter() instanceof NestedAdapter;
    }

}
