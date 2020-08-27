package com.android.zlz.demo.tablayout;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.zlz.demo.BaseActivity;
import com.android.zlz.demo.R;
import com.android.zlz.demo.sticker.EmoticonItemClickListener;
import com.android.zlz.demo.sticker.emoji.EmoJiFragment;
import com.android.zlz.demo.sticker.emoji.EmoJiUtils;
import com.android.zlz.demo.sticker.entity.EmoticonConfig;
import com.android.zlz.demo.sticker.entity.IconInfo;

/**
 * @author zhanglingzhu
 * @date 2020/08/24
 */
public class TabLayoutTestActivity extends BaseActivity {

    private TabLayout mTabLayout;

    private EditText mEditText;

    private View mStickerView;
    private EmoJiFragment emoJiFragment;
    private View mInputBtn;
    private ViewGroup emojiconsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        mEditText = findViewById(R.id.inputText);
        mInputBtn = findViewById(R.id.input_btn);
        emojiconsContainer = findViewById(R.id.emojicons_container);

        mInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emoJiIsShow()) {
                    //showSoftInputWindow();
                    hideEmoJiView();
                } else {
                    //hideSoftInputWindow();
                    showEmoJiView();
                }
            }
        });

        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emoJiIsShow()){
                    hideEmoJiView();
                }else if (!isKeyBoardShow(TabLayoutTestActivity.this)){
                    showSoftInputWindow();
                }else{
                    //hideSoftInputWindow();
                }
            }
        });
        mStickerView = findViewById(R.id.emojicons_layout);
        initEmoJiViewLayout();
        /**安全判断 有些情况会出现异常**/
        if (savedInstanceState == null) {
            EmoticonConfig emoticonConfig = new EmoticonConfig();
            emoJiFragment = EmoJiFragment.newInstance(emoticonConfig);
            getSupportFragmentManager().beginTransaction().add(R.id.emojicons_layout, emoJiFragment,
                "EmoJiFragment").commit();
        } else {
            emoJiFragment = (EmoJiFragment)getSupportFragmentManager().findFragmentByTag(
                "EmotionFragemnt");
        }

        emoJiFragment.setEmoticonItemListener(new EmoticonItemClickListener() {
            @Override
            public void onItemClick(IconInfo iconInfo) {
                SpannableString spannableString = EmoJiUtils.convert(getApplicationContext(),
                    mEditText.getText() + iconInfo.name);
                mEditText.setText(spannableString);
            }
        });

        int emotionHeight = getKeyboardHeight(this);
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "translationY",
            getScreenHeight(this), emotionHeight).
            setDuration(transitioner.getDuration(LayoutTransition.APPEARING));
        transitioner.setAnimator(LayoutTransition.APPEARING, animIn);
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "translationY",
            emotionHeight,
            getScreenHeight(this)).
            setDuration(transitioner.getDuration(LayoutTransition.DISAPPEARING));
        transitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);

        emojiconsContainer.setLayoutTransition(transitioner);
    }

    private final LayoutTransition transitioner = new LayoutTransition();//键盘和表情切换

    private void showEmoJiView() {
        boolean showAnimation = isKeyBoardShow(this);
        if (showAnimation) {
            transitioner.setDuration(200);
        } else {
            transitioner.setDuration(0);
        }

        int statusBarHeight = getStatusBarHeight(this);
        int emotionHeight = getKeyboardHeight(this);

        hideSoftInputWindow();
        mStickerView.getLayoutParams().height = emotionHeight;
        mStickerView.setVisibility(View.VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //在5.0有navigationbar的手机，高度高了一个statusBar
        int lockHeight = getAppContentHeight(this);
        //            lockHeight = lockHeight - statusBarHeight;
        lockContainerHeight(lockHeight);
    }

    private void hideEmoJiView() {
        if (emoJiIsShow()){
            boolean showKeyBoard = isSoftShowing();
            if (!showKeyBoard) {
                LinearLayout.LayoutParams localLayoutParams
                    = (LinearLayout.LayoutParams)emojiconsContainer.getLayoutParams();
                localLayoutParams.height = mStickerView.getTop();
                localLayoutParams.weight = 0.0F;
                mStickerView.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                showKeyBoard(mEditText);
                mEditText.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        unlockContainerHeightDelayed();
                    }

                }, 200L);
            } else {
                mStickerView.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                unlockContainerHeightDelayed();
            }
        }
    }

    private void initEmoJiViewLayout() {
        //mStickerView.getLayoutParams().height = getKeyboardHeight(this);
    }

    private void showSoftInputWindow() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        imm.showSoftInput(mEditText, 0);
    }

    public static int getKeyboardHeight(Activity paramActivity) {

        int height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
            - getAppHeight(paramActivity);
        if (height <= 0) {
            height = 787;
        }
        return height;
    }

    /**
     * 屏幕分辨率高
     **/
    public static int getScreenHeight(Activity paramActivity) {
        Display display = paramActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * statusBar高度
     **/
    public static int getStatusBarHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.top;

    }

    /**
     * 可见屏幕高度
     **/
    public static int getAppHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    private boolean isSoftShowing() {
        // 获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        // 获取View可见区域的bottom
        Rect rect = new Rect();
        // DecorView即为activity的顶级view
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // 考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        // 选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom;
    }

    private boolean emoJiIsShow() {
        return mStickerView.getVisibility() == View.VISIBLE;
    }

    private void lockContainerHeight(int paramInt) {
        LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)emojiconsContainer
            .getLayoutParams();
        localLayoutParams.height = paramInt;
        localLayoutParams.weight = 0.0F;
    }

    public void unlockContainerHeightDelayed() {
        ((LinearLayout.LayoutParams)emojiconsContainer.getLayoutParams()).weight = 1.0F;
    }

    /**
     * 键盘是否在显示
     **/
    public static boolean isKeyBoardShow(Activity paramActivity) {
        int height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
            - getAppHeight(paramActivity);
        return height != 0;
    }

    /**
     * 显示键盘
     **/
    public void showKeyBoard(final View paramEditText) {
        paramEditText.requestFocus();
        paramEditText.post(new Runnable() {
            @Override
            public void run() {
                showSoftInputWindow();
            }
        });
    }

    /**
     * 关闭键盘
     **/
    private void hideSoftInputWindow() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public int getAppContentHeight(Activity paramActivity) {
        return getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
            - getActionBarHeight(paramActivity) - getKeyboardHeight(paramActivity);
    }

    /**
     * 获取actiobar高度
     **/
    public int getActionBarHeight(Activity paramActivity) {
        if (true) {
            return dip2px(56);
        }
        int[] attrs = new int[] {android.R.attr.actionBarSize};
        TypedArray ta = paramActivity.obtainStyledAttributes(attrs);
        return ta.getDimensionPixelSize(0, dip2px(56));
    }

    /**
     * dp转px
     **/
    public int dip2px(int dipValue) {
        float reSize = getResources().getDisplayMetrics().density;
        return (int)((dipValue * reSize) + 0.5);
    }

}
