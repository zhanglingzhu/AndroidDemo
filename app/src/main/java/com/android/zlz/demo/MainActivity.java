package com.android.zlz.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.zlz.demo.adapter.ListAdapter;
import com.android.zlz.demo.entity.ListItemData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mMainRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainRecyclerView = findViewById(R.id.recycler_view);
        ListAdapter mainAdapter = new ListAdapter();
        mainAdapter.setItemClickCallback(new IItemClickCallback() {
            @Override
            public void onItemClick(ListItemData itemData) {
                try {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(itemData.action));
                    MainActivity.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mMainRecyclerView.setAdapter(mainAdapter);
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<ListItemData> itemDataList = createDataList();
        mainAdapter.setData(itemDataList);
        mainAdapter.notifyDataSetChanged();
    }

    private List<ListItemData> createDataList() {
        List<ListItemData> itemDataList = new ArrayList<>();
        ListItemData itemData = new ListItemData();
        itemData.title = "基础UI组件";
        itemData.action = "demo://android/views";
        itemDataList.add(itemData);

        itemData = new ListItemData();
        itemData.title = "动画相关";
        itemData.action = "demo://android/animators";
        itemDataList.add(itemData);

        return itemDataList;
    }

}
