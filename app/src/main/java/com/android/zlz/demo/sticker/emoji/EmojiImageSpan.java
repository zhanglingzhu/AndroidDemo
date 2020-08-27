package com.android.zlz.demo.sticker.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-3-3
 */
public class EmojiImageSpan extends ImageSpan {
    /**
     * 图片居中
     */
    public final static int ALIGN_GONT_CENTER = 2;

    /**
     * 默认改为居中展示
     * @param context
     * @param b
     */
    public EmojiImageSpan(Context context, Bitmap b) {
        super(context, b, ALIGN_GONT_CENTER);
    }

    public EmojiImageSpan(Context context, Bitmap b, int verticalAlignment) {
        super(context, b, verticalAlignment);
    }

    public EmojiImageSpan(Drawable d) {
        super(d);
    }

    public EmojiImageSpan(Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
    }

    public EmojiImageSpan(Drawable d, String source) {
        super(d, source);
    }

    public EmojiImageSpan(Drawable d, String source, int verticalAlignment) {
        super(d, source, verticalAlignment);
    }

    public EmojiImageSpan(Context context, Uri uri) {
        super(context, uri);
    }

    public EmojiImageSpan(Context context, Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public EmojiImageSpan(Context context, int resourceId) {
        super(context, resourceId);
    }

    public EmojiImageSpan(Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        //算法 https://stackoverflow.com/questions/25628258/align-text-around-imagespan-center-vertical
        //http://www.jb51.net/article/107941.htm
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            //字体高度
            int fontHeight = fmPaint.descent - fmPaint.ascent;
            //图片高度
            int drHeight = rect.bottom - rect.top;
            //文字的中间坐标
            int centerY = fmPaint.ascent + fontHeight / 2;
            //设置图片显示区域
            fontMetricsInt.ascent = centerY - drHeight / 2;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = centerY + drHeight / 2;
            fontMetricsInt.descent = fontMetricsInt.bottom;
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {
        //draw 方法是重写的ImageSpan父类 DynamicDrawableSpan中的方法，在DynamicDrawableSpan类中，虽有getCachedDrawable()，
        // 但是私有的，不能被调用，所以调用ImageSpan中的getrawable()方法，该方法中 会根据传入的drawable ID ，获取该id对应的
        // drawable的流对象，并最终获取drawable对象
        Drawable drawable = getDrawable(); //调用imageSpan中的方法获取drawable对象
        canvas.save();

        //获取画笔的文字绘制时的具体测量数据
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();

        //系统原有方法，默认是Bottom模式)
        int transY = bottom - drawable.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY -= fm.descent;
        } else if (mVerticalAlignment == ALIGN_GONT_CENTER) {
            // 算法 blog： https://www.cnblogs.com/huolongluo/p/7057973.html
            //此处加入判断， 如果是自定义的居中对齐
            //与文字的中间线对齐（这种方式不论是否设置行间距都能保障文字的中间线和图片的中间线是对齐的）
            // y+ascent得到文字内容的顶部坐标，y+descent得到文字的底部坐标，（顶部坐标+底部坐标）/2=文字内容中间线坐标
            transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;
        }

        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();

        //Drawable drawable = getDrawable();
        //canvas.save();
        //float extraSpace = 0;
        //float transY = ((bottom - top) - drawable.getBounds().bottom - extraSpace) / 2 + top;
        //canvas.translate(x, transY);
        //drawable.draw(canvas);
        //canvas.restore();
    }
}