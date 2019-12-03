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
        return template;
    }

    public Template copy(){
        Template template = new Template();
        template.name = this.name;
        if (nodes != null){
            List<Node> list = new ArrayList<>();
            for (Node n : nodes){
                list.add(n.copy());
            }
            template.nodes = list;
        }

        if (this.layout != null){
            template.layout = this.layout.copy();
        }
        return template;
    }
    public static class Node implements Serializable {

        public String type;
        public String img;
        public String text;
        public int textSize;
        public String textColor;


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
            if (jsonObject.containsKey("nodes")) {
                JSONArray jsonArray = jsonObject.getJSONArray("nodes");
                if (jsonArray != null && !jsonArray.isEmpty()) {
                    List<Node> nodes = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        nodes.add(Node.format(jsonArray.getJSONObject(i), null));
                    }
                    node.nodes = nodes;
                }
            }
            node.type = getStringFromJsonObject("type", jsonObject);
            node.img = getStringFromJsonObject("img", jsonObject);
            node.text = getStringFromJsonObject("text", jsonObject);
            node.textColor = getStringFromJsonObject("textcolor", jsonObject);
            node.textSize = getIntFromJsonObject("textsize", jsonObject);

            return node;
        }

        public boolean hasChild(){
            return nodes != null && !nodes.isEmpty();
        }

        public Node copy(){
            Node node = new Node();
            node.type = this.type;
            node.img = this.img;
            node.text = this.text;
            node.textSize = this.textSize;
            node.textColor = this.textColor;
            if (this.layout != null){
                node.layout = this.layout.copy();
            }
            if (nodes != null){
                List<Node> list = new ArrayList<>();
                for (Node n : nodes){
                    list.add(n.copy());
                }
                node.nodes = list;
            }

            return node;
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

        public String bgColor;
        public String bgImg;


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

            layout.bgColor = getStringFromJsonObject("background-color", jsonObject);

            return layout;
        }

        public NodeLayout copy(){
            NodeLayout nodeLayout = new NodeLayout();
            nodeLayout.margin = this.margin;
            nodeLayout.border = this.border;
            nodeLayout.flexDirection = this.flexDirection;
            nodeLayout.alignContent = this.alignContent;
            nodeLayout.justifyContent = this.justifyContent;

            nodeLayout.marginLeft = this.marginLeft;
            nodeLayout.marginRight = this.marginRight;
            nodeLayout.marginTop = this.marginTop;
            nodeLayout.marginBottom = this.marginBottom;

            nodeLayout.maxWidth = this.maxWidth;
            nodeLayout.maxHeight = this.maxHeight;

            nodeLayout.minWidth = this.minWidth;
            nodeLayout.minHeight = this.minHeight;

            nodeLayout.width = this.width;
            nodeLayout.height = this.height;

            nodeLayout.padding = this.padding;
            nodeLayout.paddingLeft = this.paddingLeft;
            nodeLayout.paddingTop = this.paddingTop;
            nodeLayout.paddingBottom = this.paddingBottom;
            nodeLayout.paddingRight = this.paddingRight;

            nodeLayout.bgColor = this.bgColor;
            nodeLayout.bgImg = this.bgImg;
            return nodeLayout;
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
