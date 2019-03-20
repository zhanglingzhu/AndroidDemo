package com.android.zlz.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.zlz.demo.adapter.ListAdapter;
import com.android.zlz.demo.entity.ListItemData;
import com.android.zlz.demo.view.ViewTypeConstant;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private View mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);
        mRecyclerView = findViewById(R.id.recycler_view);
        mContainerView = findViewById(R.id.fragment_container);

        ListAdapter mainAdapter = new ListAdapter();
        mainAdapter.setItemClickCallback(new IItemClickCallback() {
            @Override
            public void onItemClick(ListItemData itemData) {
                switch (itemData.itemId) {
                    case ViewTypeConstant.VIEW_TYPE_SINGLE_LIST:
                        break;
                    case ViewTypeConstant.VIEW_TYPE_TITLE_TAGS:
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(mainAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<ListItemData> itemDataList = createDataList();
        mainAdapter.setData(itemDataList);
        mainAdapter.notifyDataSetChanged();
    }

    private List<ListItemData> createDataList() {
        List<ListItemData> itemDataList = new ArrayList<>();
        ListItemData itemData = new ListItemData();
        itemData.title = "标签组件";
        itemData.itemId = ViewTypeConstant.VIEW_TYPE_TITLE_TAGS;
        itemDataList.add(itemData);

        itemData = new ListItemData();
        itemData.title = "单行列表组件";
        itemData.itemId = ViewTypeConstant.VIEW_TYPE_SINGLE_LIST;
        itemDataList.add(itemData);

        return itemDataList;
    }

    private void showItemFragment(int itemId){
        mContainerView.setVisibility(View.VISIBLE);
    }

}
