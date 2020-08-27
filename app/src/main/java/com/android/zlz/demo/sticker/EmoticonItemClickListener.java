package com.android.zlz.demo.sticker;

import com.android.zlz.demo.sticker.entity.IconInfo;

/**
 * @author zhanglingzhu
 * @date 2020/08/25
 */
public interface EmoticonItemClickListener {

    /**
     * 表情点击回调
     * @param iconInfo
     */
    void onItemClick(IconInfo iconInfo);
}
