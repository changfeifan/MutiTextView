package com.changfeifan.mutitextview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.changfeifan.mutitextview.R;


/**
 * Created by changfeifan on 2017/5/12.
 */

public class MutiTextView extends View {

    TextPaint textPaintLeft;
    TextPaint textPaintRight;

    String textLeft = "";
    String textRight = "";

    public MutiTextView(Context context) {
        this(context, null);
    }

    public MutiTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MutiTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MutiTextView, defStyleAttr, 0);

        textPaintLeft = new TextPaint();
        textPaintRight = new TextPaint();

        textPaintLeft.setTextAlign(Paint.Align.LEFT);
        textPaintRight.setTextAlign(Paint.Align.LEFT);

        if (a != null) {
            textPaintRight.setColor(a.getColor(R.styleable.MutiTextView_rightTextColor, Color.BLACK));
            textPaintRight.setTextSize(a.getDimension(R.styleable.MutiTextView_rightTextSize, 30));
            textPaintLeft.setColor(a.getColor(R.styleable.MutiTextView_leftTextColor, Color.BLACK));
            textPaintLeft.setTextSize(a.getDimension(R.styleable.MutiTextView_leftTextSize, 30));

            textLeft = a.getString(R.styleable.MutiTextView_leftText);
            textRight = a.getString(R.styleable.MutiTextView_rightText);
        }

//        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Rect rectLeft = new Rect();
        textPaintLeft.getTextBounds(textLeft, 0, textLeft.length(), rectLeft);
        Rect rectRight = new Rect();
        textPaintRight.getTextBounds(textRight, 0, textRight.length(), rectRight);

        setMeasuredDimension(rectLeft.width() + rectRight.width(), Math.max(rectLeft.height(), rectRight.height()));

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Rect rectLeft = new Rect();
        textPaintLeft.getTextBounds(textLeft, 0, textLeft.length(), rectLeft);
        Rect rectRight = new Rect();
        textPaintRight.getTextBounds(textRight, 0, textRight.length(), rectRight);

        if (textPaintLeft.getTextSize() > textPaintRight.getTextSize()) {
            canvas.drawText(textLeft, -rectLeft.left, -rectLeft.top, textPaintLeft);
            canvas.drawText(textRight, rectLeft.right - rectLeft.left, -rectLeft.top, textPaintRight);
        } else {
            canvas.drawText(textLeft, -rectLeft.left, -rectRight.top, textPaintLeft);
            canvas.drawText(textRight, rectLeft.right - rectLeft.left, -rectRight.top, textPaintRight);
        }

    }
}
