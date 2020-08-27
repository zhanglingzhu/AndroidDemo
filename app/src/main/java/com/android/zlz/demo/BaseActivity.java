package com.android.zlz.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author zhanglingzhu
 * @date 2019/11/15
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        if (hasActionBar()) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppTheme_NoActionBar);
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.yk_title_back_dark);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public boolean hasActionBar(){
        return true;
    }
}
