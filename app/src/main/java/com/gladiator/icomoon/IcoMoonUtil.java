package com.gladiator.icomoon;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by xmb2nc on 11-04-2016.
 */
public class IcoMoonUtil {
    private static Typeface tf;

    public static Typeface getTypeFace(Context ctx) {
        if (tf == null) {
            tf = Typeface.createFromAsset(ctx.getAssets(), "icomoon.ttf");
        }
        return tf;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
