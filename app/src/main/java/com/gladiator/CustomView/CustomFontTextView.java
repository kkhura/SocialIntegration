package com.gladiator.CustomView;

import com.mphrx.fisike.R;
import com.mphrx.fisike.constant.VariableConstants;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.TextureView;
 import com.mphrx.fisike.customview.CustomFontEditTextView;
import android.widget.TextView;

import com.mphrx.fisike.customview.CustomFontTextView;

public class CustomFontTextView extends TextView {

	public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
	

	public CustomFontTextView(Context context) {
		super(context);

		applyCustomFont(context, null);

	}

	public CustomFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		applyCustomFont(context, attrs);

	}

	@Override
	public boolean isInEditMode() {
		return true;
	}

	public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		applyCustomFont(context, attrs);
	}

	private void applyCustomFont(Context context, AttributeSet attrs) {
		TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);

		String fontName = attributeArray.getString(R.styleable.CustomFontTextView_font);

		Typeface customFont = selectTypeface(context, fontName, fontName);
		setTypeface(customFont);

		attributeArray.recycle();
	}

	public Typeface selectTypeface(Context context, String fontName, String textStyle) {
		if(textStyle == null){
			return FontCache.getTypeface("sans/OpenSans-Regular.ttf", context);
		}
		if(textStyle.equals(VariableConstants.BOLD)){
			FontCache.getTypeface("sans/OpenSans-Bold.ttf", context);
		} else if(textStyle.equals(VariableConstants.MEDIUM)){
			return FontCache.getTypeface("sans/OpenSans-Semibold.ttf", context);
		} else if(textStyle.equals(VariableConstants.LIGHT)){
			return FontCache.getTypeface("sans/OpenSans-Light.ttf", context);
		} else if(textStyle.equals(VariableConstants.REGULAR)){
			return FontCache.getTypeface("sans/OpenSans-Regular.ttf", context);
		}else if(textStyle.equals(VariableConstants.ITALIC)){
			return FontCache.getTypeface("sans/OpenSans-Italic.ttf", context);
		}
		if(textStyle.equals(VariableConstants.SEMI_BOLD)){
			FontCache.getTypeface("sans/OpenSans-Bold.ttf", context);
		}
		return FontCache.getTypeface("sans/OpenSans-Semibold.ttf", context);

    }


}