package com.kkhura.icomoon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by xmb2nc on 10-04-2016.
 */
public class IconTextView extends TextView {

    public IconTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setTypeface(IcoMoonUtil.getTypeFace(getContext()));
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setTypeface(IcoMoonUtil.getTypeFace(getContext()));
    }


    public IconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        setTypeface(IcoMoonUtil.getTypeFace(getContext()));
    }

}

