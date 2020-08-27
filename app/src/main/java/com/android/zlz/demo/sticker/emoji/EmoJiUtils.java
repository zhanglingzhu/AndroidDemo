package com.android.zlz.demo.sticker.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import com.android.zlz.demo.R;
import com.android.zlz.demo.sticker.entity.IconInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhanglingzhu
 * @date 2020/08/25
 */
public class EmoJiUtils {

    public static final int EMOJI_IMAGE_SIZE_DP = 18;
    private static List<IconInfo> infoList = new ArrayList<>();
    private static Map<String, IconInfo> emoJiMap = new HashMap<>();
    private static Map<Integer, WeakReference<Bitmap>> iconBitmapsCache = new HashMap<>();

    // 正则表达式匹配字符串里是否含有表情，如： 我好[开心]啊
    private static final String EMOTICON_REGULAR_EXPRESSION = "\\[[^\\]]+\\]";
    private static final Pattern mEmojiPattern = Pattern.compile(EMOTICON_REGULAR_EXPRESSION,
        Pattern.CASE_INSENSITIVE);

    public static List<IconInfo> getEmoJiList() {
        return infoList;
    }

    static {
        initDefaultEmojiList();
        for (IconInfo iconInfo : infoList) {
            emoJiMap.put(iconInfo.name, iconInfo);
        }
    }

    private static void initDefaultEmojiList() {
        infoList.add(new IconInfo("[呵呵]", R.drawable.uikit_em_1));
        infoList.add(new IconInfo("[哈哈]", R.drawable.uikit_em_2));
        infoList.add(new IconInfo("[吐舌]", R.drawable.uikit_em_3));
        infoList.add(new IconInfo("[啊]", R.drawable.uikit_em_4));
        infoList.add(new IconInfo("[酷]", R.drawable.uikit_em_5));
        infoList.add(new IconInfo("[怒]", R.drawable.uikit_em_6));
        infoList.add(new IconInfo("[开心]", R.drawable.uikit_em_7));
        infoList.add(new IconInfo("[汗]", R.drawable.uikit_em_8));
        infoList.add(new IconInfo("[泪]", R.drawable.uikit_em_9));
        infoList.add(new IconInfo("[黑线]", R.drawable.uikit_em_10));
        infoList.add(new IconInfo("[鄙视]", R.drawable.uikit_em_11));
        infoList.add(new IconInfo("[不高兴]", R.drawable.uikit_em_12));
        infoList.add(new IconInfo("[真棒]", R.drawable.uikit_em_13));
        infoList.add(new IconInfo("[钱]", R.drawable.uikit_em_14));
        infoList.add(new IconInfo("[疑问]", R.drawable.uikit_em_15));
        infoList.add(new IconInfo("[阴险]", R.drawable.uikit_em_16));
        infoList.add(new IconInfo("[吐]", R.drawable.uikit_em_17));
        infoList.add(new IconInfo("[咦]", R.drawable.uikit_em_18));
        infoList.add(new IconInfo("[委屈]", R.drawable.uikit_em_19));
        infoList.add(new IconInfo("[花心]", R.drawable.uikit_em_20));
        infoList.add(new IconInfo("[呼]", R.drawable.uikit_em_21));
        infoList.add(new IconInfo("[笑眼]", R.drawable.uikit_em_22));
        infoList.add(new IconInfo("[冷]", R.drawable.uikit_em_23));
        infoList.add(new IconInfo("[太开心]", R.drawable.uikit_em_24));
        infoList.add(new IconInfo("[滑稽]", R.drawable.uikit_em_25));
        infoList.add(new IconInfo("[勉强]", R.drawable.uikit_em_26));
        infoList.add(new IconInfo("[狂汗]", R.drawable.uikit_em_27));
        infoList.add(new IconInfo("[乖]", R.drawable.uikit_em_28));
        infoList.add(new IconInfo("[睡觉]", R.drawable.uikit_em_29));
        infoList.add(new IconInfo("[惊哭]", R.drawable.uikit_em_30));
        infoList.add(new IconInfo("[升起]", R.drawable.uikit_em_31));
        infoList.add(new IconInfo("[惊讶]", R.drawable.uikit_em_32));
        infoList.add(new IconInfo("[喷]", R.drawable.uikit_em_33));
        infoList.add(new IconInfo("[爱心]", R.drawable.uikit_em_34));
        infoList.add(new IconInfo("[心碎]", R.drawable.uikit_em_35));
        infoList.add(new IconInfo("[玫瑰]", R.drawable.uikit_em_36));
        infoList.add(new IconInfo("[礼物]", R.drawable.uikit_em_37));
        infoList.add(new IconInfo("[彩虹]", R.drawable.uikit_em_38));
        infoList.add(new IconInfo("[星星月亮]", R.drawable.uikit_em_39));
        infoList.add(new IconInfo("[太阳]", R.drawable.uikit_em_40));
        infoList.add(new IconInfo("[钱币]", R.drawable.uikit_em_41));
        infoList.add(new IconInfo("[灯泡]", R.drawable.uikit_em_42));
        infoList.add(new IconInfo("[茶杯]", R.drawable.uikit_em_43));
        infoList.add(new IconInfo("[蛋糕]", R.drawable.uikit_em_44));
        infoList.add(new IconInfo("[音乐]", R.drawable.uikit_em_45));
        infoList.add(new IconInfo("[haha]", R.drawable.uikit_em_46));
        infoList.add(new IconInfo("[胜利]", R.drawable.uikit_em_47));
        infoList.add(new IconInfo("[大拇指]", R.drawable.uikit_em_48));
        infoList.add(new IconInfo("[弱弱]", R.drawable.uikit_em_49));
        infoList.add(new IconInfo("[ok]", R.drawable.uikit_em_50));
    }

    /**
     * 转换对应emoJi到图标
     * @param context
     * @param charSequence
     * @return
     */
    public static SpannableString convert(Context context, CharSequence charSequence) {
        SpannableString spannableString;
        if (charSequence instanceof SpannableString) {
            spannableString = (SpannableString)charSequence;
        } else if (charSequence != null && charSequence.length() > 0) {
            spannableString = new SpannableString(charSequence);
        } else {
            return null;
        }

        // 通过传入的正则表达式来生成一个pattern
        try {
            doConvertEmoji(context, spannableString);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return spannableString;
    }

    private static void doConvertEmoji(Context context, SpannableString spannableString)
        throws Exception {
        int width = (int)(EMOJI_IMAGE_SIZE_DP * context.getResources().getDisplayMetrics().density);
        Matcher matcher = mEmojiPattern.matcher(spannableString);
        int displayHeight = 0;
        int displayWidth = 0;
        while (matcher.find()) {
            IconInfo emoji = emoJiMap.get(matcher.group());
            if (emoji != null) {
                displayHeight = width;
                displayWidth = width;
                convertEmoji(context, spannableString, matcher, emoji, displayWidth, displayHeight);
            }
        }
    }

    private static void convertEmoji(Context context, SpannableString spannableString,
                                     Matcher matcher, IconInfo emoji,
                                     int displayWidth, int displayHeight) {
        Bitmap iconBitmap = getIconBitmap(context, emoji, displayWidth, displayHeight);
        if (iconBitmap != null && !iconBitmap.isRecycled()) {
            doConvertEmoji(context, spannableString, matcher, iconBitmap);
        }
    }

    private static Bitmap getIconBitmap(Context context, IconInfo emoji, int displayWidth,
                                        int displayHeight) {

        WeakReference<Bitmap> weakReference = iconBitmapsCache.get(emoji.resId);
        if (weakReference != null && weakReference.get() != null && !weakReference.get()
            .isRecycled()) {
            return weakReference.get();
        }

        Drawable drawable = context.getResources().getDrawable(emoji.resId);
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
            : Bitmap.Config.RGB_565;
        Bitmap cachedIconBitmap = Bitmap.createBitmap(displayWidth, displayHeight, config);
        Canvas canvas = new Canvas(cachedIconBitmap);
        drawable.setBounds(0, 0, displayWidth, displayHeight);
        drawable.draw(canvas);
        iconBitmapsCache.put(emoji.resId, new WeakReference<Bitmap>(cachedIconBitmap));

        return cachedIconBitmap;
    }

    private static void doConvertEmoji(Context context, SpannableString spannableString,
                                       Matcher matcher, Bitmap iconBitmap) {
        ImageSpan imageSpan = new EmojiImageSpan(context, iconBitmap);
        // 计算该图片名字的长度，也就是要替换的字符串的长度
        // 将该图片替换字符串中规定的位置中
        spannableString.setSpan(imageSpan, matcher.start(), matcher.end(),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
