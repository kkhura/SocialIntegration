package com.gladiator.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.gladiator.Constant.VariableConstants;
import com.gladiator.R;

public class CustomFontButton extends Button {
	public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

	public CustomFontButton(Context context) {
		super(context);
		applyCustomFont(context, null);

	}

	public CustomFontButton(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		applyCustomFont(context, attrs);
	}

	public CustomFontButton(Context context, AttributeSet attrs, int defStyle) {
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

	private Typeface selectTypeface(Context context, String fontName, String textStyle) {
		if(textStyle == null){
			return FontCache.getTypeface("sans/OpenSans-Semibold.ttf", context);
		}
		if(textStyle.equals(VariableConstants.BOLD)){
			FontCache.getTypeface("sans/OpenSans-Bold.ttf", context);

		} else if(textStyle.equals(VariableConstants.MEDIUM)){
			return FontCache.getTypeface("sans/OpenSans-Semibold.ttf", context);
		} else if(textStyle.equals(VariableConstants.LIGHT)){
			return FontCache.getTypeface("sans/OpenSans-Light.ttf", context);
		} else if(textStyle.equals(VariableConstants.REGULAR)){
			return FontCache.getTypeface("sans/OpenSans-Regular.ttf", context);
		}
		return FontCache.getTypeface("sans/OpenSans-Semibold.ttf", context);

	}
}
