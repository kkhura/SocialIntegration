package com.gladiator.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gladiator.Constant.VariableConstants;
import com.gladiator.R;
import com.gladiator.Utility.Utils;

import java.lang.reflect.Field;

public class CustomFontEditTextView extends TextInputLayout {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    private TextInputEditText editText;
    private Context context;
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_RIGHT = 1;
    private static final int GRAVITY_CENTER = 2;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public CustomFontEditTextView(Context context) {
        super(context);
        this.context = context;
        editText = createEditText(context, null);
        super.addView(editText);
        setHintTextAppearance(R.style.TextInputStyle);
        applyCustomFont(context, null);
        setError("");
        //setErrorTextColor(this, ContextCompat.getColor(getContext(), R.color.red_500));

    }

    public CustomFontEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        editText = createEditText(context, attrs);
        applyFieldAttributes(attrs);
        super.addView(editText);
        setHintTextAppearance(R.style.TextInputStyle);
        applyCustomFont(context, attrs);
        setError("");
        //setErrorTextColor(this, ContextCompat.getColor(getContext(), R.color.red_500));
    }


    public void setClearableEditTextPadding(int left, int top, int right, int bottom) {
        editText.setPadding(left, top, right, bottom);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        /*        super.setOnTouchListener(l);*/
        editText.setOnTouchListener(l);

    }

    public void setFocusable(boolean focusable) {
        editText.setFocusable(focusable);
    }

    public CustomFontEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        editText = createEditText(context, attrs);
        applyFieldAttributes(attrs);
        super.addView(editText);
        setHintTextAppearance(R.style.TextInputStyle);
        applyCustomFont(context, attrs);
    }

    public void applyFieldAttributes(AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        //setting input type
        String inputType = attributeArray.getString(R.styleable.CustomFontTextView_inputType);
        setEditText_inputType(inputType);
        //setting maxlength

        // editText.setFilters(new InputFilter[]{inputfilter});

        int length = attributeArray.getInt(R.styleable.CustomFontTextView_maxLength, -1);
        setMaxLength(length);

        boolean isdrawableRight = attributeArray.getBoolean(R.styleable.CustomFontTextView_is_password_view, false);
        if (isdrawableRight && !isInEditMode()) {
            editText.setCompoundDrawablePadding(5);
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_show_password, 0);
        }

        int gravity = attributeArray.getInt(R.styleable.CustomFontTextView_gravity, -1);

        if (gravity == GRAVITY_CENTER) {
            editText.setGravity(Gravity.CENTER);
        } else if (gravity == GRAVITY_LEFT) {
            editText.setGravity(Gravity.LEFT);

        } else if (gravity == GRAVITY_RIGHT) {
            editText.setGravity(Gravity.RIGHT);
        }

        //setting number of lines
        //setting maxlength
        int linew = attributeArray.getInt(R.styleable.CustomFontTextView_lines, -1);
        if (linew != -1)
            editText.setMaxLines(linew);

        //setting single line
        boolean singleLine = attributeArray.getBoolean(R.styleable.CustomFontTextView_singleLine, true);
        editText.setSingleLine(singleLine);


        //setting text size
        editText.setTextSize(Utils.pixelsToSp(context, 14));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CustomFontEditTextView.this.setError("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CustomFontEditTextView.this.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                CustomFontEditTextView.this.setError("");
            }

        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (CustomFontEditTextView.this.getError() == null) {
                        CustomFontEditTextView.this.setError("");
                        setErrorTextColor(CustomFontEditTextView.this, context.getResources().getColor(R.color.theme_edit_text_error_color));
                    }
                }
            }
        });


        final int DRAWABLE_RIGHT = 2;

        if (editText.getCompoundDrawables()[DRAWABLE_RIGHT] != null && isdrawableRight) {
            editText.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            if (editText.getTransformationMethod() == null) {
                                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_show_password, 0);
                                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            } else {
                                editText.setTransformationMethod(null);
                                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_hide_password, 0);
                            }
                            editText.setSelection(editText.getText().length());
                            return false;
                        }
                    }
                    return false;
                }
            });
        }

    }


    void setMaxLength(int maxLength) {
        if (maxLength == -1)
            return;
        else {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
    }

    @Override
    public void setFocusableInTouchMode(boolean focusableInTouchMode) {
        super.setFocusableInTouchMode(focusableInTouchMode);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_font);
        Typeface customFont = selectTypeface(context, fontName, fontName);
        setTypeface(customFont);
        editText.setTypeface(customFont);
        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, String fontName, String textStyle) {
        if (textStyle == null) {
            return FontCache.getTypeface("sans/OpenSans-Regular.ttf", context);
        }
        if (textStyle.equals(VariableConstants.BOLD)) {
            return FontCache.getTypeface("sans/OpenSans-Bold.ttf", context);
        } else if (textStyle.equals(VariableConstants.MEDIUM)) {
            return FontCache.getTypeface("sans/OpenSans-Semibold.ttf", context);
        } else if (textStyle.equals(VariableConstants.LIGHT)) {
            return FontCache.getTypeface("sans/OpenSans-Light.ttf", context);
        } else if (textStyle.equals(VariableConstants.REGULAR)) {
            return FontCache.getTypeface("sans/OpenSans-Regular.ttf", context);
        } else if (textStyle.equals(VariableConstants.ITALIC)) {
            return FontCache.getTypeface("sans/OpenSans-Italic.ttf", context);
        }
        return FontCache.getTypeface("sans/OpenSans-Semiboldat.ttf", context);

    }

    private TextInputEditText createEditText(Context context, AttributeSet attrs) {
        editText = new TextInputEditText(context);
        editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
        editText.setHorizontallyScrolling(false);
        editText.setHint("");

        editText.setTextColor(context.getResources().getColor(R.color.dusky_blue));
        return editText;
    }

    public String getText() {
        return this.editText.getText().toString();
    }

    public void setEditText_inputType(String type) {
        if (TextUtils.isEmpty(type)) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            return;
        }
        String[] typeArr = type.trim().split("\\|");
        String finalString;
        finalString = "";
        for (String s : typeArr) {

            switch (s) {
                case "number":
                    finalString = addInputType(InputType.TYPE_CLASS_NUMBER, finalString);
                    break;
                case "decimal":
                    finalString = addInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL, finalString);
                    break;
                case "textPassword":
                case "Password":
                case "password":
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editText.setTransformationMethod(new PasswordTransformationMethod());

                    break;

                case "textCapWords":
                    finalString = addInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS, finalString);
                    break;
                case "none":
                    finalString = addInputType(InputType.TYPE_NULL, finalString);
                    break;
                case "textEmail":
                    finalString = addInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, finalString);
                    break;
                case "textEmailAddress":
                    finalString = addInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, finalString);
                    break;
                case "textMultiLine":
                    editText.setSingleLine(false);
                    break;

            }
        }
        //	editText.setInputType(Integer.parseInt(finalString));
    }


    String addInputType(int type, String list) {

        if (list.equals("")) {
            editText.setInputType(type);
            list = "" + list;
        } else {
            editText.setInputType(editText.getInputType() | type);
            list = list + "|" + type;
        }
        return list;
    }

    public void setText(String s) {
        this.editText.setText(s);
    }


    public void addTextChangedListener(TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    public void setKeyListener(KeyListener input) {
        editText.setKeyListener(input);
    }

    public void setFilters(InputFilter[] filters) {
        editText.setFilters(filters);
    }

    public TransformationMethod getTransformationMethod() {
        return editText.getTransformationMethod();
    }

    public void setTransformationMethod(TransformationMethod method) {

        if (method != null) {
            editText.setTransformationMethod(method);

        } else
            editText.setTransformationMethod(null);

//            editText.setInputType(InputType.TYPE_CLASS_TEXT);



/*
         if(editText.getText().toString().length()>0)
             editText.setSelection(0,(editText.getText().length()-1));
*/
    }

    public void setCursorVisible(boolean visible) {
        editText.setCursorVisible(visible);
    }

    public void setInputType(int type) {
        editText.setInputType(type);
    }

    public void setSelection(int index) {
        editText.setSelection(index);
    }

    public void setHint(@Nullable int RID) {
        String hint = context.getString(RID);
        setHint(hint);
    }

    public AppCompatEditText getEditText() {
        return editText;
    }

    public void setErrorTextColor(TextInputLayout textInputLayout, int color) {
        try {
            Field fErrorView = TextInputLayout.class.getDeclaredField("mErrorView");
            fErrorView.setAccessible(true);
            TextView mErrorView = (TextView) fErrorView.get(this);
            Field fCurTextColor = TextView.class.getDeclaredField("mCurTextColor");
            fCurTextColor.setAccessible(true);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            if (mErrorView != null) {
                fCurTextColor.set(mErrorView, color);
                mErrorView.setLayoutParams(params);
                mErrorView.setGravity(Gravity.RIGHT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setErrorGravity()
    {
        try{Field fErrorView = TextInputLayout.class.getDeclaredField("mErrorView");
            fErrorView.setAccessible(true);
            TextView mErrorView = (TextView) fErrorView.get(this);
            Field fCurTextColor = TextView.class.getDeclaredField("mCurTextColor");
            fCurTextColor.setAccessible(true);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            if (mErrorView != null){
                mErrorView.setLayoutParams(params);
                mErrorView.setGravity(Gravity.RIGHT);}
        } catch (Exception e) {
            e.printStackTrace();
        }}


    public Drawable[] getCompoundDrawables() {
        return editText.getCompoundDrawables();
    }

    @Override
    public void setError(@Nullable CharSequence error) {
//        super.setError("");
        /*if (!this.isErrorEnabled())a
            setErrorEnabled(true);*/

        if(error!=null && !error.equals(""))
           setErrorTextColor(this, context.getResources().getColor(R.color.theme_edit_text_error_color));
        super.setErrorEnabled(false);
        super.setError("none");
        super.setError(error);
        setErrorGravity();
    }


    public void setPasswordKeyListener() {
        NumberKeyListener PwdkeyListener = new NumberKeyListener() {

            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
            }

            @Override
            protected char[] getAcceptedChars() {
                return VariableConstants.passwordAllowedChars;
            }
        };
        editText.setKeyListener(PwdkeyListener);
    }


   /* accepts alphanumeric inputs[A-z] [a-z] [0-9]*/
    public void setAlphaNumericKeyListener(){
        NumberKeyListener aphaNumericKeyListener = new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return VariableConstants.alphaNumericAllowedChars;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }
        };
        editText.setKeyListener(aphaNumericKeyListener);
    }


    public void setMobileKeyListener() {
        NumberKeyListener PwdkeyListener = new NumberKeyListener() {

            public int getInputType() {
                return InputType.TYPE_CLASS_PHONE;
            }

            @Override
            protected char[] getAcceptedChars() {
                return VariableConstants.mobilenoAllowedChars;
            }
        };
        editText.setKeyListener(PwdkeyListener);
    }


    @Override
    public void setOnKeyListener(OnKeyListener l) {
        editText.setOnKeyListener(l);
    }

    public int length() {
        return editText.length();
    }


}
