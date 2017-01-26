package com.gladiator.CustomView;

import java.util.HashMap;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;

public class FontCache {
	private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();
	
	public static Typeface getTypeface(String fontName, Context context){
		Typeface typeface = fontCache.get(fontName);
		if(typeface == null){
			 try {
		            typeface = Typeface.createFromAsset(context.getAssets(), fontName);
		        } catch (Exception e) {
		            return null;
		        }

		        fontCache.put(fontName, typeface);
		    }

		    return typeface;
		}
		
	}
