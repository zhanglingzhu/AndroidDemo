package com.android.zlz.demo.sticker.entity;

/**
 * @author zhanglingzhu
 * @date 2020/08/25
 */
public class IconInfo {

    public final static int EMOJI = 0;
    public final static int GIF = 1;
    public final static int PNG = 2;

    public String name;
    public int resId;
    public int type;
    public String resPath;

    public int width;
    public int height;

    public IconInfo(String name, int resId){
        this.name = name;
        this.resId = resId;
        this.type = EMOJI;
    }

    public IconInfo(){

    }
}
