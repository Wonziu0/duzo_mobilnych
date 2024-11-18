package com.example.megasort;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SortingView extends View {

    private int[] array = new int[0];
    private Paint paint = new Paint();

    public SortingView(Context context) {
        super(context);
    }

    public SortingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SortingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setArray(int[] array) {
        this.array = array;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (array.length == 0) return;

        int width = getWidth();
        int height = getHeight();
        float barWidth = (float) width / array.length;

        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            float barHeight = ((float) value / getMaxValue()) * height;
            paint.setColor(Color.BLUE);
            canvas.drawRect(
                    i * barWidth,
                    height - barHeight,
                    (i + 1) * barWidth,
                    (float) height,
                    paint
            );
        }
    }

    private int getMaxValue() {
        int max = 1;
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}