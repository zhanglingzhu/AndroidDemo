package com.android.zlz.demo.sticker.emoji;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.zlz.demo.R;
import com.android.zlz.demo.sticker.BaseEmoticonFragment;
import com.android.zlz.demo.sticker.EmoticonAdapter;
import com.android.zlz.demo.sticker.EmoticonItemClickListener;
import com.android.zlz.demo.sticker.entity.EmoticonConfig;
import com.android.zlz.demo.sticker.entity.IconInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhanglingzhu
 * @date 2020/08/25
 */
public class EmoJiFragment extends BaseEmoticonFragment {

    protected EmoticonConfig emoticonConfig;
    protected EmoticonItemClickListener mItemClickListener;
    protected RecyclerView mRecyclerView;
    protected EmoticonAdapter mEmoticonAdapter;

    public static EmoJiFragment newInstance(EmoticonConfig emoticonConfig) {

        EmoJiFragment emoJiFragment = new EmoJiFragment();
        emoJiFragment.setEmoticonConfig(emoticonConfig);

        return emoJiFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayoutId(), null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.emoticon_recyclerview);
        mEmoticonAdapter = new EmoticonAdapter(emoticonItemClickListener);
        List<IconInfo> infoList =  EmoJiUtils.getEmoJiList();//new ArrayList<>();
        //Collections.copy(infoList, EmoJiUtils.getEmoJiList());
        for (IconInfo info : infoList) {
            info.width = emoticonConfig.mEmoticonWidth;
            info.height = emoticonConfig.mEmoticonHeight;
        }
        mEmoticonAdapter.setEmoticonList(infoList);

        initRecyclerViewConfig(emoticonConfig);
        mRecyclerView.setAdapter(mEmoticonAdapter);
    }

    private void setEmoticonConfig(EmoticonConfig emoticonConfig) {
        this.emoticonConfig = emoticonConfig;
    }

    private EmoticonItemClickListener emoticonItemClickListener = new EmoticonItemClickListener() {
        @Override
        public void onItemClick(IconInfo iconInfo) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(iconInfo);
            }
        }
    };

    public void setEmoticonItemListener(EmoticonItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_emoticon;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
