package com.android.zlz.demo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.zlz.demo.IItemClickCallback;
import com.android.zlz.demo.MainActivity;
import com.android.zlz.demo.R;
import com.android.zlz.demo.entity.ListItemData;
import com.android.zlz.demo.holder.ListItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglingzhu
 * @date 2019/03/20
 */
public class ListAdapter extends RecyclerView.Adapter<ListItemHolder> {

    private List<ListItemData> itemDatas = new ArrayList<>();
    private IItemClickCallback itemClickCallback;

    public void setItemClickCallback(IItemClickCallback clickCallback){
        this.itemClickCallback = clickCallback;
    }

    public void setData(List<ListItemData> dataList) {
        itemDatas.clear();
        itemDatas.addAll(dataList);
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ListItemHolder(
            LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_list_item, viewGroup, false), itemClickCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder itemHolder, int i) {
        ListItemData itemData = itemDatas.get(i);
        itemHolder.bindData(itemData);
    }

    @Override
    public int getItemCount() {
        return itemDatas.size();
    }
}
