package com.android.zlz.demo.sticker;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.zlz.demo.sticker.entity.EmoticonConfig;

/**
 * @author zhanglingzhu
 * @date 2020/08/25
 */
public abstract class BaseEmoticonFragment extends Fragment {

    /**
     * 获取布局layout
     *
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 定义列表view
     *
     * @return
     */
    protected RecyclerView getRecyclerView() {
        return null;
    }

    /**
     * 设置RecyclerView边距
     *
     * @param emoticonConfig
     */
    protected void initRecyclerViewConfig(EmoticonConfig emoticonConfig) {
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView == null) {
            return;
        }
        final int lineCount = emoticonConfig.mLineCount;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        float density = getResources().getDisplayMetrics().density;
        final int itemWidth = (int)(emoticonConfig.mEmoticonWidth * density);
        final int itemHeight = (int)(emoticonConfig.mEmoticonHeight * density);

        final float padding = (screenWidth - itemWidth * lineCount) * 1f / (lineCount + 1);
        final int itemMargin = (int)(padding / 2);

        EmoticonGridLayoutManager layoutManager = new EmoticonGridLayoutManager(
            recyclerView.getContext(), lineCount);
        layoutManager.setLayoutParams(new RecyclerView.LayoutParams(itemWidth, itemHeight));

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {

                int position = parent.getChildAdapterPosition(view); // 获取view 在adapter中的位置。
                if (position % lineCount == 0) {
                    outRect.left = (int)padding;
                    outRect.right = itemMargin;
                } else if (position % lineCount == lineCount - 1) {
                    outRect.left = itemMargin;
                    outRect.right = (int)padding;
                } else {
                    outRect.left = itemMargin;
                    outRect.right = itemMargin;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
    }

    class EmoticonGridLayoutManager extends GridLayoutManager {

        private RecyclerView.LayoutParams layoutParams;

        public EmoticonGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public void setLayoutParams(RecyclerView.LayoutParams layoutParams) {
            this.layoutParams = layoutParams;
        }
    }

}
