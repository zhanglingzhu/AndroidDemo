package com.android.zlz.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author zhanglingzhu
 * @date 2020/08/24
 *
 */
public class UiActivity extends BaseActivity implements View.OnClickListener{


    private TextView mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mTabLayout)){
            try {
                Intent intent = new Intent();
                intent.setData(Uri.parse("demo://android/tabLayout"));
                UiActivity.this.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
