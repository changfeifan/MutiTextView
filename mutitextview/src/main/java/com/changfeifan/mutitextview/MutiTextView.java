package com.changfeifan.mutitextview;

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
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by changfeifan on 2017/5/12.
 */

public class MutiTextView extends View {

    TextPaint textPaintLeft;
    TextPaint textPaintRight;

    String textLeft = "";
    String textRight = "";

    Rect rectLeft;
    Rect rectRight;
    ViewParent viewParent;

    int ChineseDeviation = 3;//中文输入时会有问题,right和bottom边缘被裁切。加3px.

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
    }

    /**
    * 重构页面时计算边缘大小。
    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        rectLeft = new Rect();
        textPaintLeft.getTextBounds(textLeft, 0, textLeft.length(), rectLeft);
        rectRight = new Rect();
        textPaintRight.getTextBounds(textRight, 0, textRight.length(), rectRight);

        int width = rectLeft.width() + rectRight.width() + getPaddingLeft() + getPaddingRight();
        int height = Math.max(rectLeft.height(), rectRight.height()) + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(width + ChineseDeviation, height + ChineseDeviation);

    }

    /**
     * draw texts
     * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectLeft = new Rect();
        textPaintLeft.getTextBounds(textLeft, 0, textLeft.length(), rectLeft);
        rectRight = new Rect();
        textPaintRight.getTextBounds(textRight, 0, textRight.length(), rectRight);

        if (textPaintLeft.getTextSize() > textPaintRight.getTextSize()) {
            canvas.drawText(textLeft, -rectLeft.left, -rectLeft.top, textPaintLeft);
            canvas.drawText(textRight, rectLeft.right - rectLeft.left, -rectLeft.top, textPaintRight);
        } else {
            canvas.drawText(textLeft, -rectLeft.left, -rectRight.top, textPaintLeft);
            canvas.drawText(textRight, rectLeft.right - rectLeft.left, -rectRight.top, textPaintRight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.e("onLayout", "onLayout");
    }

    public String getTextLeft() {
        return textLeft;
    }

    public void setTextLeft(String textLeft) {
        this.textLeft = textLeft;
        resetView();
    }

    public String getTextRight() {
        return textRight;
    }

    public void setTextRight(String textRight) {
        this.textRight = textRight;
        resetView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 文字更改的时候调用此方法
     * reset all view
     * 默认居中在父中间。下版本进行布局gravity适配。
     * */
    public void resetView() {
        rectLeft = new Rect();
        rectRight = new Rect();
        textPaintLeft.getTextBounds(textLeft, 0, textLeft.length(), rectLeft);
        textPaintRight.getTextBounds(textRight, 0, textRight.length(), rectRight);

        //获取父容器view
        viewParent = getParent();

        int leftParent = getLeft();
        int rightParent = getRight();
        int topParent = getTop();
        int bottomParent = getBottom();

        if (viewParent != null) {

            leftParent = ((ViewGroup) viewParent).getLeft();
            rightParent = ((ViewGroup) viewParent).getRight();
            topParent = ((ViewGroup) viewParent).getTop();
            bottomParent = ((ViewGroup) viewParent).getBottom();
        }

        int leftNew = ((rightParent - leftParent) - (rectLeft.width() + rectRight.width())) / 2;
        int topNew = getTop();
        int rightNew = leftNew + (rectLeft.width() + rectRight.width());
        int bottomNew = getBottom();

        measure(rectLeft.width() + rectRight.width() + ChineseDeviation, Math.max(rectLeft.height(), rectRight.height()) + ChineseDeviation);

        layout(leftNew, topNew, rightNew, bottomNew);

        invalidate();
    }
}


