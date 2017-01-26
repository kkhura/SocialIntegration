package com.gladiator.icomoon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * Created by xmb2nc on 10-04-2016.
 */
public class IconButton  extends Button {

    public IconButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setTypeface(IcoMoonUtil.getTypeFace(getContext()));
    }

    public IconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setTypeface(IcoMoonUtil.getTypeFace(getContext()));
    }

    public IconButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        setTypeface(IcoMoonUtil.getTypeFace(getContext()));
    }

}

