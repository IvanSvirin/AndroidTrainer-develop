package ru.extremefitness.fitness_trainer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import ru.extremefitness.fitness_trainer.R;

/**
 * Created by Osipova Ekaterina on 22.03.2016.
 */
public class WorkoutTimer extends View {

    public static final int ARC_STROKE_WIDTH = 10;
    public static final String ARG_PROGRESS = "progress";
    public static final String ARG_SUPER_STATE = "super_state";
    public static final int TEXT_SIZE = 48;

    int mTimerColor;
    int mArcColor;
    int mPointerColor;

    Paint mTimerPaint;
    Paint mArcPaint;
    Paint mPointPaint;
    Paint mTextPaint;

    Path mTimerPath;
    Path mArcPath;
    Path mPointPath;

    int progress;
    int max;
    private int timerPad;
    private int radius;

    RectF timerBounds;

    public WorkoutTimer(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WorkoutTimer,
                0, 0);

        try {
            mTimerColor = a.getColor(R.styleable.WorkoutTimer_timerColor, Color.BLACK);
            mArcColor = a.getColor(R.styleable.WorkoutTimer_arcColor, Color.WHITE);
            max = a.getInt(R.styleable.WorkoutTimer_max, 0);
            progress = a.getInt(R.styleable.WorkoutTimer_progress, 0);
            mPointerColor = a.getColor(R.styleable.WorkoutTimer_pointerColor, Color.BLACK);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        mTimerPaint = new Paint();
        mTimerPaint.setStyle(Paint.Style.FILL);
        mTimerPaint.setAntiAlias(true);
        mTimerPaint.setColor(mTimerColor);

        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(ARC_STROKE_WIDTH);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(mArcColor);

        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(mPointerColor);

        mTextPaint = new Paint();
        mTextPaint.setColor(mPointerColor);
        mTextPaint.setTextSize(TEXT_SIZE * getResources().getDisplayMetrics().density);

        mTimerPath = new Path();
        mArcPath = new Path();
        mPointPath = new Path();

        timerBounds = new RectF();
    }

    public int getTimerColor() {
        return mTimerColor;
    }

    public void setTimerColor(int mTimerColor) {
        this.mTimerColor = mTimerColor;
        invalidate();
    }

    public int getArcColor() {
        return mArcColor;
    }

    public void setArcColor(int mArcColor) {
        this.mArcColor = mArcColor;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getPointerColor() {
        return mPointerColor;
    }

    public void setPointerColor(int pointerColor) {
        this.mPointerColor = pointerColor;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int width = w - getPaddingLeft() - getPaddingRight();
        int height = h - getPaddingTop() - getPaddingBottom();

        int diameter = Math.min(width, height);
        radius = diameter / 2;
        timerPad = radius / 6;

        timerBounds.set(timerPad, timerPad, diameter - timerPad, diameter - timerPad);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop()
                + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTimerPath.reset();
        mTimerPath.addCircle(radius, radius, radius, Path.Direction.CW);

        int progressAngle;
        if (max == 0)
            progressAngle = 360;
        else
            progressAngle = 360 - progress * 360 / max;

        mArcPath.reset();
        mArcPath.addArc(timerBounds, progressAngle, 360 - progressAngle);

        mPointPath.reset();
        mPointPath.addCircle((float) (radius + (radius - timerPad) * Math.cos(Math.toRadians
                (progressAngle))), (float) (radius + (radius - timerPad)
                * Math.sin(Math.toRadians(progressAngle))), 30, Path.Direction.CW);

        String text = String.format("%02d", progress/60) + ":" + String.format("%02d", progress%60);
        float textHeight = - mTextPaint.ascent();
        float textWidth = mTextPaint.measureText(text);

        canvas.translate(getPaddingLeft(), getPaddingTop());
        canvas.drawPath(mTimerPath, mTimerPaint);
        canvas.drawPath(mArcPath, mArcPaint);
        canvas.drawPath(mPointPath, mPointPaint);
        canvas.drawText(text, radius - textWidth/2, radius + textHeight/2, mTextPaint);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PROGRESS, progress);
        bundle.putParcelable(ARG_SUPER_STATE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            setProgress(bundle.getInt(ARG_PROGRESS));
            state = bundle.getParcelable(ARG_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }
}
