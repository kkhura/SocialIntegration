package com.kkhura.icomoon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;


/**
 * Created by xmb2nc on 10-04-2016.
 */
public class IconDrawable extends Drawable {
    private TextPaint paint;
    private int size = 0;
    private String text;
    private int alpha = 255;
    private Rect textBounds;
    private int color = Color.BLACK;

    public IconDrawable(Context context, String txt) {
        initView(context, txt, 50);
    }

    public IconDrawable(Context context, String txt, int textSize) {
        initView(context, txt, textSize);
    }

    public IconDrawable(Context context, int txtId) {
        initView(context, context.getString(txtId),50);
    }


    public void initView(Context ctx, String txt, int textSize) {
        text = txt;
        paint = new TextPaint();
        paint.setTypeface(IcoMoonUtil.getTypeFace(ctx));
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(color);
        paint.setUnderlineText(false);
        paint.setAntiAlias(true);
        paint.getTextBounds(txt, 0, 1, textBounds);

    }


    public void setColor(int c) {
        // TODO Auto-generated method stub
        this.color = c;
        paint.setColor(c);
        invalidateSelf();
    }


    @Override
    public int getIntrinsicHeight() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public int getIntrinsicWidth() {
        // TODO Auto-generated method stub
        return size;
    }

    public void setPXSize(Context ctx, int s) {
        // TODO Auto-generated method stub
        this.size = s;
        paint.setTextSize(size / 2);
        invalidateSelf();
    }

    public void setDPSize(Context ctx, int s) {
        // TODO Auto-generated method stub
        this.size = IcoMoonUtil.dip2px(ctx, s);
        paint.setTextSize(size / 2);
        invalidateSelf();
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, -15, 15, paint);
    }

    @Override
    public int getOpacity() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void setAlpha(int alpha) {
        // TODO Auto-generated method stub
        this.alpha = alpha;
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // TODO Auto-generated method stub

    }

}
