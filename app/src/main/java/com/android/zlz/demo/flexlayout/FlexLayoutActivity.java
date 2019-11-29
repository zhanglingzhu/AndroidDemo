package com.android.zlz.demo.flexlayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.zlz.demo.BaseActivity;
import com.android.zlz.demo.R;
import com.android.zlz.demo.flexlayout.entry.Template;
import com.facebook.soloader.SoLoader;
import com.facebook.yoga.YogaNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhanglingzhu
 * @date 2019/11/22
 */
public class FlexLayoutActivity extends BaseActivity {

    private LinearLayout layout;

    private Map<String, Template> mTemplateMap = new ArrayMap<>();
    private Map<String, JSONObject> mDataJsonMap = new ArrayMap<>();

    private Map<String, YogaNode> mYogaNodeMap = new ArrayMap<>();

    private YKDynamicManager ykDynamicManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);
        ykDynamicManager = YKDynamicManager.getInstance();
        ykDynamicManager.initDeviceInfo(this);
        SoLoader.init(this, false);

        layout = (LinearLayout)findViewById(R.id.yoga_content);
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    loadTemplateData();
                    loadData();
                    Iterator<String> iterator = mDataJsonMap.keySet().iterator();
                    long now = System.currentTimeMillis();
                    while (iterator.hasNext()) {
                        String templateName = iterator.next();
                        if (mTemplateMap.get(templateName) != null && mDataJsonMap.get(templateName) != null) {
                            YogaNode yogaNode = ykDynamicManager.createTemplateYogaNode(mTemplateMap.get(templateName),
                                mDataJsonMap.get(templateName));
                            mYogaNodeMap.put(templateName, yogaNode);
                        }
                    }
                    Log.d("zlztest", "createTemplateYogaNode cost " + (System.currentTimeMillis() - now));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Iterator<String> iterator = mYogaNodeMap.keySet().iterator();
                long now = System.currentTimeMillis();
                while (iterator.hasNext()) {
                    View view = ykDynamicManager.createView(mYogaNodeMap.get(iterator.next()), FlexLayoutActivity.this);
                    if (view != null) {
                        layout.addView(view);
                    }
                }
                Log.d("zlztest", "createView cost " + (System.currentTimeMillis() - now));
            }
        };
        asyncTask.execute();
    }

    private void loadTemplateData() throws IOException {

        InputStream is = getResources().openRawResource(R.raw.dynimic_template);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String data = null;
        StringBuffer sb = new StringBuffer();
        while ((data = br.readLine()) != null) {
            sb.append(data);
        }
        JSONObject jsonObject = JSON.parseObject(sb.toString());
        if (jsonObject == null || !jsonObject.containsKey("dynamics")) {
            return;
        }
        JSONArray array = jsonObject.getJSONArray("dynamics");
        Template template = null;
        for (int i = 0; i < array.size(); i++) {
            JSONObject templateData = array.getJSONObject(i);
            template = Template.format(templateData, null);
            if (template != null) {
                mTemplateMap.put(template.name, template);
            }
        }
    }

    private void loadData() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.flex_data);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String data = null;
        StringBuffer sb = new StringBuffer();
        while ((data = br.readLine()) != null) {
            sb.append(data);
        }
        JSONArray jsonArray = JSON.parseArray(sb.toString());
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject == null || !jsonObject.containsKey("template_name")) {
                continue;
            }
            mDataJsonMap.put(jsonObject.getString("template_name"), jsonObject);
        }
    }

}
