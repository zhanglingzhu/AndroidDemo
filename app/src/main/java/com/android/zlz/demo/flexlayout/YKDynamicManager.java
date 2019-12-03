package com.android.zlz.demo.flexlayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.zlz.demo.R;
import com.android.zlz.demo.flexlayout.entry.Template;
import com.bumptech.glide.Glide;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaNode;

/**
 * @author zhanglingzhu
 * @date 2019/11/25
 */
public class YKDynamicManager {

    private float density = 1;

    private static YKDynamicManager ykDynamicManager = new YKDynamicManager();

    private YKDynamicManager() {

    }

    public static YKDynamicManager getInstance() {
        return ykDynamicManager;
    }

    /**
     * 创建模板虚拟视图
     *
     * @param template
     * @param jsonObject
     * @return
     */
    public YogaNode createTemplateYogaNode(Template template, JSONObject jsonObject) {
        YogaNode mRootYogaNode = new YogaNode();

        int width = getPixel(template.layout.width);
        int height = getPixel(template.layout.height);

        mRootYogaNode.setWidth(width);
        mRootYogaNode.setHeight(height);

        boolean hasChild = template.nodes != null && !template.nodes.isEmpty() ? true : false;
        formatLayoutAttr(mRootYogaNode, template.layout, hasChild);
        for (int i = 0; i < template.nodes.size(); i++) {
            Template.Node node = template.nodes.get(i);
            if (node == null) {
                continue;
            }
            YogaNode yogaNode = createYogaNode(node, jsonObject);
            mRootYogaNode.addChildAt(formatLayoutAttr(yogaNode, node.layout, node.hasChild()), i);
        }
        mRootYogaNode.calculateLayout(width, height);
        return mRootYogaNode;
    }

    public YogaNode createYogaNode(Template.Node node, JSONObject jsonObject) {

        YogaNode yogaNode = new YogaNode();
        formatWH(yogaNode, node, jsonObject);
        formatLayoutAttr(yogaNode, node.layout, node.hasChild());
        if (node.hasChild()) {
            for (int i = 0; i < node.nodes.size(); i++) {
                Template.Node childnode = node.nodes.get(i);
                yogaNode.addChildAt(createYogaNode(childnode, jsonObject), i);
            }
        }
        return yogaNode;
    }

    /**
     * 为yoga 增加宽高属性
     *
     * @param yogaNode
     * @param node
     * @param jsonObject
     * @return
     */
    public YogaNode formatWH(YogaNode yogaNode, Template.Node node, JSONObject jsonObject) {

        String type = node.type;
        if ("text".equals(type)) {

            int textsize = node.textSize;
            TextPaint paint = new TextPaint();

            paint.setTextSize(textsize * density);
            paint.setAntiAlias(true);

            int width = (int)paint.measureText(node.text);
            int height = getHeight(node.text, width, paint);
            yogaNode.setWidth(width);
            yogaNode.setHeight(height);
            node.text = ExpressionParser.getInstance().parse(node.text, jsonObject);
            yogaNode.setData(node);

        } else if ("img".equals(type)) {
            if (node.layout.width > 0) {
                int width = getPixel(node.layout.width);
                yogaNode.setWidth(width);
            }

            if (node.layout.height > 0) {
                yogaNode.setHeight(getPixel(node.layout.height));
            }

            node.img = ExpressionParser.getInstance().parse(node.img, jsonObject);
            yogaNode.setData(node);

        } else {

            if (node.layout.width > 0) {
                int width = getPixel(node.layout.width);
                yogaNode.setWidth(width);
            }

            if (node.layout.height > 0) {
                yogaNode.setHeight(getPixel(node.layout.height));
            }
            yogaNode.setData(node);
        }

        return yogaNode;
    }

    private static int getHeight(String text, int width, TextPaint paint) {

        StaticLayout.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            builder = StaticLayout.Builder.obtain(text, 0,
                text.length(), paint, width);
        }
            /*.setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(b.spacingAdd, b.spacingMult)
            .setIncludePad(b.includePad)
            .setEllipsize(b.ellipsize)
            .setMaxLines(b.maxLines);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return builder.build().getHeight();
        }
        return 0;
    }

    public View createView(YogaNode yogaNode, Context context) {

        if (yogaNode == null) {
            return null;
        }
        View view = null;
        int childCount = yogaNode.getChildCount();
        if (childCount > 0) {
            ViewGroup viewGroup = createViewGroup(yogaNode, context);
            for (int i = 0; i < childCount; i++) {
                YogaNode yg = yogaNode.getChildAt(i);
                View childView = createView(yg, context);
                if (childView != null) {
                    viewGroup.addView(childView);
                }
            }
            Template.Node node = (Template.Node)yogaNode.getData();
            if (node != null && node.layout != null){
                if (!TextUtils.isEmpty(node.layout.bgColor)){
                    viewGroup.setBackgroundColor(Color.parseColor(node.layout.bgColor));
                }
            }
            view = viewGroup;
        } else {
            Template.Node node = (Template.Node)yogaNode.getData();
            if (node == null){
                return null;
            }
            if ("text".equals(node.type)) {
                TextView textView = new TextView(context);
                textView.setX(yogaNode.getLayoutX());
                textView.setY(yogaNode.getLayoutY());

                textView.setHeight((int)yogaNode.getHeight().value);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getPixel(node.textSize));
                textView.setText(node.text);
                textView.setTextColor(Color.parseColor(node.textColor));
                int maxWidth = getPixel(node.layout.maxWidth);
                if (maxWidth > 0) {
                    textView.setMaxWidth(maxWidth);
                }else {
                    textView.setWidth((int)yogaNode.getWidth().value);
                }
                view = textView;


            } else if ("img".equals(node.type)) {
                ImageView imageView = new ImageView(context);
                imageView.setX(yogaNode.getLayoutX());
                imageView.setY(yogaNode.getLayoutY());
                imageView.setLayoutParams(
                    new ViewGroup.LayoutParams((int)yogaNode.getWidth().value, (int)yogaNode.getHeight().value));

                Glide.with(context).load(node.img).placeholder(R.color.colorGrey).into(imageView);

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view = imageView;
            }
        }
        return view;
    }

    private ViewGroup createViewGroup(YogaNode yogaNode, Context context) {
        ViewGroup viewGroup = new RelativeLayout(context);
        viewGroup.setX(yogaNode.getLayoutX());
        viewGroup.setY(yogaNode.getLayoutY());
        viewGroup.setLayoutParams(
            new ViewGroup.LayoutParams((int)yogaNode.getWidth().value, (int)yogaNode.getHeight().value));
        return viewGroup;
    }

    /**
     * 为node填充属性 flex属性
     *
     * @param yogaNode
     * @param nodeLayout
     * @param hasChild
     * @return
     */
    public YogaNode formatLayoutAttr(YogaNode yogaNode, Template.NodeLayout nodeLayout, boolean hasChild) {

        if (hasChild) {
            if ("row".equals(nodeLayout.flexDirection)) {
                yogaNode.setFlexDirection(YogaFlexDirection.ROW);
            } else if ("column".equals(nodeLayout.flexDirection)) {
                yogaNode.setFlexDirection(YogaFlexDirection.COLUMN);
            }
        }
        if (nodeLayout.alignContent != null) {
            if ("flex-start".equals(nodeLayout.alignContent)) {
                yogaNode.setAlignContent(YogaAlign.FLEX_START);
            } else if ("flex-end".equals(nodeLayout.alignContent)) {
                yogaNode.setAlignContent(YogaAlign.FLEX_END);
            }
        }

        if (nodeLayout.justifyContent != null) {
            if ("flex-start".equals(nodeLayout.justifyContent)) {
                yogaNode.setJustifyContent(YogaJustify.FLEX_START);
            } else if ("flex-end".equals(nodeLayout.justifyContent)) {
                yogaNode.setJustifyContent(YogaJustify.FLEX_END);
            }
        }

        if (nodeLayout.margin != 0) {
            yogaNode.setMargin(YogaEdge.ALL, getPixel(nodeLayout.margin));
        }

        if (nodeLayout.marginLeft != 0) {
            yogaNode.setMargin(YogaEdge.LEFT, getPixel(nodeLayout.marginLeft));
        }

        if (nodeLayout.marginTop != 0) {
            yogaNode.setMargin(YogaEdge.TOP, getPixel(nodeLayout.marginTop));
        }

        if (nodeLayout.marginRight != 0) {
            yogaNode.setMargin(YogaEdge.RIGHT, getPixel(nodeLayout.marginRight));
        }

        if (nodeLayout.marginBottom != 0) {
            yogaNode.setMargin(YogaEdge.BOTTOM, getPixel(nodeLayout.marginBottom));
        }

        if (nodeLayout.maxWidth != 0) {
            yogaNode.setMaxWidth(getPixel(nodeLayout.maxWidth));
        }

        if (nodeLayout.maxHeight != 0) {
            yogaNode.setMaxHeight(getPixel(nodeLayout.maxHeight));
        }

        return yogaNode;
    }


    public void initDeviceInfo(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.widthPixels * 1.0f / 375;
    }

    public int getPixel(int dip) {
        return (int)(dip * density);
    }

}
