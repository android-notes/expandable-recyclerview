# expandable-recyclerview
可以展开、折叠分组，支持添加多种divider的adapter

`compile 'com.wanjian:expandable-recyclerview:0.0.1'`

### 分组RecyclerView

支持多种group布局和多种child布局

![group](https://github.com/android-notes/expandable-recyclerview/blob/master/group.jpg?raw=true)

继承`NestedAdapter`,实现下列方法即可，跟使用`ExpandableListview`类似

```java

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


```



### 为NestedAdapter添加divider

![divider](https://github.com/android-notes/expandable-recyclerview/blob/master/divider.jpg?raw=true)

支持添加头部，尾部，group间，child间，group和child间添加自定义divider

```java

 public NestedAdapterDivider setDividerBetweenGroup(Drawable dividerBetweenGroup)  

    public NestedAdapterDivider setDividerBetweenChild(Drawable dividerBetweenChild)  

    public NestedAdapterDivider setDividerBetweenGroupAndChild(Drawable dividerBetweenGroupAndChild)  

    public NestedAdapterDivider setDividerBeforeFirstGroup(Drawable dividerBeforeFirstGroup) 

    public NestedAdapterDivider setDividerAfterLastGroup(Drawable dividerAfterLastGroup)  


```

### 展开折叠Recyclerview

![group](https://github.com/android-notes/expandable-recyclerview/blob/master/expan.gif?raw=true)


继承自ExpandableAdapter即可，调用如下方法实现展开折叠，同样支持多种group布局和多种child布局

```java


    public void collapseGroup(int groupIndex) 

    public void expandGroup(int groupIndex)  

    public boolean isExpand(int groupIndex) 
    
    public void collapseAllGroup()  
    

```


### 支持局部刷新，局部移除添加

相关方法如下

```java

    public void notifyGroupItemChanged(int groupIndex)  

    public void notifyGroupChanged(int groupIndex)  

    public final void notifyChildItemChanged(int groupIndex, int childIndex)  

    public final void notifyChildItemRangeChanged(int groupIndex, int childIndex, int itemCount)  

    public final void notifyChildItemInserted(int groupIndex, int childIndex)  
    
    public final void notifyChildItemRangeInserted(int groupIndex, int childIndex, int itemCount)  

    public final void notifyChildItemRemoved(int groupIndex, int childIndex)  

    public final void notifyChildItemRangeRemoved(int groupIndex, int childIndex, int itemCount) 

```
