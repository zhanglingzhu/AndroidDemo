package com.android.zlz.demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author zhanglingzhu
 * @date 2019/11/15
 */
public class AnimatorActivity extends BaseActivity{

    private TextView mTranslateView;
    private Button mTranslateBtn;

    private TextView mAlphaView;
    private Button mAlphaBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animator);

        mTranslateView = findViewById(R.id.translate_view);
        mTranslateBtn = findViewById(R.id.translate_btn);
        mTranslateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTranslateView, "translationY", 60f, 0f);
                objectAnimator.setDuration(2000);
                objectAnimator.start();

                /*TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 60f, 0f);
                translateAnimation.setDuration(2000);
                mTranslateView.startAnimation(translateAnimation);*/
            }
        });


        mAlphaView = findViewById(R.id.alpha_view);
        mAlphaBtn = findViewById(R.id.alpha_btn);
        mAlphaBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAlphaView, "alpha", 1, 0f);
                objectAnimator.setDuration(2000);
                objectAnimator.start();
                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.d(TAG, "onAnimationStart...");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.d(TAG, "onAnimationEnd...");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });
    }

}
