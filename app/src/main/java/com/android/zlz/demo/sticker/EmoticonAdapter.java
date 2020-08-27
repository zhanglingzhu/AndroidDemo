package com.android.zlz.demo.sticker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.zlz.demo.R;
import com.android.zlz.demo.sticker.entity.IconInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglingzhu
 * @date 2020/08/25
 */
public class EmoticonAdapter extends RecyclerView.Adapter<EmoticonHolder> {

    private EmoticonItemClickListener mItemClickListener;
    private List<IconInfo> infoList = new ArrayList<>();

    public EmoticonAdapter(EmoticonItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public EmoticonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        EmoticonHolder baseEmoticonHolder = new EmoticonHolder(
            LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_emoticon, null));
        baseEmoticonHolder.setEmoticonItemClickListener(mItemClickListener);

        return baseEmoticonHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmoticonHolder baseEmoticonHolder, int i) {
        IconInfo info = infoList.get(i);
        baseEmoticonHolder.bindData(info);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public void setEmoticonList(List<IconInfo> iconInfos) {
        this.infoList = iconInfos;
    }

}
