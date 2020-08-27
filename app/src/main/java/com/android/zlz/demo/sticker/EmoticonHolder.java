package com.android.zlz.demo.sticker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.zlz.demo.R;
import com.android.zlz.demo.sticker.entity.IconInfo;

/**
 * @author zhanglingzhu
 * @date 2020/08/25
 */
public class EmoticonHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;

    private EmoticonItemClickListener emoticonItemClickListener;

    public EmoticonHolder(@NonNull View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.emojicon_icon);
    }

    public void setEmoticonItemClickListener(EmoticonItemClickListener emoticonItemClickListener) {
        this.emoticonItemClickListener = emoticonItemClickListener;
    }

    public void bindData(final IconInfo iconInfo) {
        float density = itemView.getResources().getDisplayMetrics().density;
        int width = (int)(density * iconInfo.width);
        int height = (int)(density * iconInfo.height);

        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = width;
            layoutParams.height = height;
        } else {
            layoutParams = new ViewGroup.LayoutParams(width, height);
            itemView.setLayoutParams(layoutParams);
        }
        mImageView.setImageResource(iconInfo.resId);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emoticonItemClickListener != null) {
                    emoticonItemClickListener.onItemClick(iconInfo);
                }
            }
        });
    }

}
