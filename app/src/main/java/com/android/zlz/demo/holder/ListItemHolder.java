package com.android.zlz.demo.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.zlz.demo.IItemClickCallback;
import com.android.zlz.demo.R;
import com.android.zlz.demo.entity.ListItemData;

/**
 * @author zhanglingzhu
 * @date 2019/03/20
 */
public class ListItemHolder extends RecyclerView.ViewHolder {

    private TextView mTitleView;
    private View itemView;
    private IItemClickCallback itemClickCallback;

    public ListItemHolder(@NonNull View itemView, IItemClickCallback iItemClickCallback) {
        super(itemView);
        this.itemClickCallback = iItemClickCallback;
        initView(itemView);
    }

    private void initView(View itemView) {
        this.itemView = itemView;
        mTitleView = itemView.findViewById(R.id.title_view);
    }

    public void bindData(final ListItemData itemData) {
        mTitleView.setText(itemData.title);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickCallback != null){
                    itemClickCallback.onItemClick(itemData);
                }
            }
        });
    }
}
