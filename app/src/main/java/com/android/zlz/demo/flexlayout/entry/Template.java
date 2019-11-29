package com.android.zlz.demo.flexlayout.entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglingzhu
 * @date 2019/11/27
 */
public class Template implements Serializable {

    public String name;

    public List<Node> nodes;

    public NodeLayout layout;

    public String bgColor;
    public String bgImg;


    public static Template format(JSONObject jsonObject, Template template) {
        if (template == null) {
            template = new Template();
        }
        if (jsonObject == null) {
            return template;
        }
        template.name = getStringFromJsonObject("name", jsonObject);

        if (jsonObject.containsKey("layout")) {
            template.layout = NodeLayout.format(jsonObject.getJSONObject("layout"), null);
        }

        if (jsonObject.containsKey("nodes")) {
            JSONArray jsonArray = jsonObject.getJSONArray("nodes");
            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<Node> nodes = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    nodes.add(Node.format(jsonArray.getJSONObject(i), null));
                }
                template.nodes = nodes;
            }
        }
        template.bgColor = getStringFromJsonObject("background-color", jsonObject);
        return template;
    }
    public static class Node implements Serializable {

        public String type;
        public String img;
        public String text;
        public int textSize;
        public String textColor;
        public String bgColor;


        public NodeLayout layout;
        public List<Node> nodes;


        public static Node format(JSONObject jsonObject, Node node) {
            if (node == null){
                node = new Node();
            }
            if (jsonObject == null) {
                return null;
            }
            if (jsonObject.containsKey("layout")){
                JSONObject layout = jsonObject.getJSONObject("layout");
                node.layout = NodeLayout.format(layout, null);
            }
            node.type = getStringFromJsonObject("type", jsonObject);
            node.img = getStringFromJsonObject("img", jsonObject);
            node.text = getStringFromJsonObject("text", jsonObject);
            node.textColor = getStringFromJsonObject("textcolor", jsonObject);
            node.textSize = getIntFromJsonObject("textsize", jsonObject);
            node.bgColor = getStringFromJsonObject("background-color", jsonObject);

            return node;
        }

        public boolean hasChild(){
            return nodes != null && !nodes.isEmpty();
        }

    }
    public static class NodeLayout implements Serializable{

        public int margin;
        public int border;
        public String flexDirection;
        public String alignContent;
        public String justifyContent;

        public int marginLeft;
        public int marginRight;
        public int marginTop;
        public int marginBottom;

        public int maxWidth;
        public int maxHeight;

        public int minWidth;
        public int minHeight;

        public int width;
        public int height;

        public int padding;
        public int paddingLeft;
        public int paddingTop;
        public int paddingBottom;
        public int paddingRight;


        public static NodeLayout format(JSONObject jsonObject, NodeLayout layout){
            if (layout == null){
                layout = new NodeLayout();
            }
            if (jsonObject == null){
                return layout;
            }
            layout.flexDirection = getStringFromJsonObject("flex-direction", jsonObject);
            layout.alignContent = getStringFromJsonObject("align-content", jsonObject);
            layout.justifyContent = getStringFromJsonObject("justify-content", jsonObject);
            layout.border = getIntFromJsonObject("border", jsonObject);
            layout.margin = getIntFromJsonObject("margin",jsonObject);

            layout.marginLeft = getIntFromJsonObject("margin-left", jsonObject);
            layout.marginTop = getIntFromJsonObject("margin-top", jsonObject);
            layout.marginRight = getIntFromJsonObject("margin-right", jsonObject);
            layout.marginBottom = getIntFromJsonObject("margin-bottom", jsonObject);

            layout.padding = getIntFromJsonObject("padding", jsonObject);
            layout.paddingLeft = getIntFromJsonObject("padding-left", jsonObject);
            layout.paddingTop = getIntFromJsonObject("padding-top", jsonObject);
            layout.paddingRight = getIntFromJsonObject("padding-right", jsonObject);
            layout.paddingBottom = getIntFromJsonObject("padding-bottom", jsonObject);

            layout.width = getIntFromJsonObject("width", jsonObject);
            layout.height = getIntFromJsonObject("height", jsonObject);

            layout.maxHeight = getIntFromJsonObject("max-height", jsonObject);
            layout.maxWidth = getIntFromJsonObject("max-width", jsonObject);

            layout.minHeight = getIntFromJsonObject("min-height", jsonObject);
            layout.minWidth = getIntFromJsonObject("min-width", jsonObject);

            return layout;
        }

    }
    private static int getIntFromJsonObject(String key, JSONObject jsonObject){
        if (jsonObject.containsKey(key)){
            return jsonObject.getInteger(key);
        }
        return 0;
    }

    public static String getStringFromJsonObject(String key, JSONObject jsonObject){
        if (jsonObject.containsKey(key)){
            return jsonObject.getString(key);
        }
        return null;
    }

}
