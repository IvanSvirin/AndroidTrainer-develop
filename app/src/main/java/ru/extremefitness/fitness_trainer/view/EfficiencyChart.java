package ru.extremefitness.fitness_trainer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.models.BodyPartEfficiency;
import ru.extremefitness.fitness_trainer.models.Exercise;

/**
 * Created by Osipova Ekaterina on 14.03.2016.
 */
public class EfficiencyChart extends View {

    // TODO: sizes must depend from density
    public static final int LINE_STROKE_WIDTH = 2;
    public static final int CHART_LINE_STROKE_WIDTH = 8;
    public static final int TEXT_SIZE = 16;
    public static final int CHART_POINT_RADIUS = 15;

    List<BodyPartEfficiency> data;

    int mChartColor;
    int mLineColor;

    Paint mChartPaint;
    Paint mLinePaint;
    Paint mPointPaint;
    Paint mChartLinesPaint;
    Paint mTextPaint;

    Path mChartPath;
    Path mLinePath;
    Path mPointsPath;
    Path mChartLinesPath;

    Point[] mTextPoints;
    private int chartH;
    private int chartW;
    private int chartPadX;
    private int chartPadH;
    private float textPaddingTop;
    private float textPaddingBottom;
    private float textPaddingX;

    public EfficiencyChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .EfficiencyChart, 0, 0);

        try {
            mChartColor = a.getColor(R.styleable.EfficiencyChart_chartColor, Color.BLACK);
            mLineColor = a.getColor(R.styleable.EfficiencyChart_lineColor, Color.WHITE);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        mChartPaint = new Paint();
        mChartPaint.setStyle(Paint.Style.FILL);
        mChartPaint.setAntiAlias(true);
        mChartPaint.setColor(mChartColor);

        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(LINE_STROKE_WIDTH);
        mLinePaint.setAntiAlias(true);

        mPointPaint = new Paint();
        mPointPaint.setColor(mLineColor);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setAntiAlias(true);

        mChartLinesPaint = new Paint();
        mChartLinesPaint.setColor(mLineColor);
        mChartLinesPaint.setStyle(Paint.Style.STROKE);
        mChartLinesPaint.setStrokeWidth(CHART_LINE_STROKE_WIDTH);
        mChartLinesPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(mChartColor);
        mTextPaint.setTextSize(TEXT_SIZE * getResources().getDisplayMetrics().density);

        mChartPath = new Path();
        mLinePath = new Path();
        mPointsPath = new Path();
        mChartLinesPath = new Path();
    }

    public int getChartColor() {
        return mChartColor;
    }

    public void setChartColor(int chartColor) {
        this.mChartColor = chartColor;
        invalidate();
    }

    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
        invalidate();
    }

    public List<BodyPartEfficiency> getData() {
        return data;
    }

    public void setData(List<BodyPartEfficiency> data) {
        // TODO: бросить исключение, если элементов не 6
        this.data = data;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // width match_parent, height wrap_content
        float textPaddingLeft = Math.max(mTextPaint.measureText(data.get(0).getBodyPart().getName
                        ()), mTextPaint.measureText(data.get(1).getBodyPart().getName()));
        float textPaddingRight = Math.max(mTextPaint.measureText(data.get(3).getBodyPart().getName
                        ()), mTextPaint.measureText(data.get(4).getBodyPart().getName()));
        textPaddingX = Math.max(textPaddingLeft, textPaddingRight);
        textPaddingTop = -mTextPaint.ascent();
        textPaddingBottom = mTextPaint.descent() - mTextPaint.ascent();

        int size = Math.min((int) (getMeasuredWidth() - getPaddingLeft() - getPaddingRight() -
                textPaddingX * 2), (int) (getMeasuredHeight() - getPaddingTop() -
                getPaddingBottom() - textPaddingTop - textPaddingBottom));
        setMeasuredDimension((int) (size + getPaddingLeft() + getPaddingRight() + textPaddingX * 2),
                (int) (size + getPaddingTop() + getPaddingBottom() +
                textPaddingTop + textPaddingBottom));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int width = (int) (w - getPaddingLeft() - getPaddingRight() - textPaddingX * 2);
        int height = (int) (h - getPaddingTop() - getPaddingBottom() - textPaddingTop -
                textPaddingBottom);

        int diameter = Math.min(width, height);

        double cos = Math.cos(Math.toRadians(30));
        double sin = Math.sin(Math.toRadians(30));

        chartPadH = diameter / 10;
        chartPadX = (int) (chartPadH * cos);
        int chartPadY = (int) (chartPadH * sin);
        chartH = diameter - chartPadH * 2;
        chartW = (int) (cos * chartH);

        Point[] points = new Point[6];
        points[0] = new Point(0, chartH /4);
        points[1] = new Point(0, 3* chartH /4);
        points[2] = new Point(chartW /2, chartH);
        points[3] = new Point(chartW, 3* chartH /4);
        points[4] = new Point(chartW, chartH /4);
        points[5] = new Point(chartW /2, 0);

        mTextPoints = new Point[6];
        mTextPoints[0] = new Point((int)(points[0].x - chartPadX - mTextPaint.measureText(data
                .get(0).getBodyPart().getName())), points[0].y - chartPadY);
        mTextPoints[1] = new Point((int)(points[1].x - chartPadX - mTextPaint.measureText(data
                .get(1).getBodyPart().getName())), (int) (points[1].y + chartPadY - mTextPaint
                .ascent()));
        mTextPoints[2] = new Point((int) (points[2].x - mTextPaint.measureText(data.get(2)
                .getBodyPart().getName())/2), (int) (points[2].y + chartPadH - mTextPaint
                .ascent()));
        mTextPoints[3] = new Point(points[3].x + chartPadX, (int) (points[3].y + chartPadY -
                mTextPaint.ascent()));
        mTextPoints[4] = new Point(points[4].x + chartPadX, points[4].y - chartPadY);
        mTextPoints[5] = new Point((int) (points[5].x - mTextPaint.measureText(data.get(5)
                .getBodyPart().getName())/2), points[5].y - chartPadH);

        mChartPath.reset();
        mChartPath.moveTo(points[points.length - 1].x, points[points.length - 1].y);
        for (int i = 0; i < points.length; i++) {
            mChartPath.lineTo(points[i].x, points[i].y);
        }

        mLinePath.reset();
        for (int i = 0; i < points.length / 2; i++) {
            mLinePath.moveTo(points[i].x, points[i].y);
            mLinePath.lineTo(points[i + 3].x, points[i + 3].y);
        }

        Point[] mChartPoints = new Point[data.size()];
        mPointsPath.reset();

        for (int i = 0; i < data.size() / 2; i++) {
            mChartPoints[i] = new Point(points[i].x + (points[i + 3].x - points[i].x) / 20 * (10 - data.get
                    (i).getEfficiency()), points[i].y + (points[i + 3].y - points[i].y) / 20 *
                    (10 - data.get(i).getEfficiency()));
            mChartPoints[i + 3] = new Point(points[i].x + (points[i + 3].x - points[i].x) / 20 * (10 + data.get
                    (i + 3).getEfficiency()), points[i].y + (points[i + 3].y - points[i].y) / 20 *
                    (10 + data.get(i + 3).getEfficiency()));
            mPointsPath.addCircle(mChartPoints[i].x, mChartPoints[i].y, CHART_POINT_RADIUS, Path.Direction.CW);
            mPointsPath.addCircle(mChartPoints[i + 3].x, mChartPoints[i + 3].y, CHART_POINT_RADIUS,
                    Path.Direction.CW);
        }
        mChartLinesPath.reset();
        mChartLinesPath.moveTo(mChartPoints[mChartPoints.length-1].x, mChartPoints[mChartPoints
                .length-1].y);
        for (int i = 0; i < mChartPoints.length; i++) {
            mChartLinesPath.lineTo(mChartPoints[i].x, mChartPoints[i].y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getPaddingLeft() + (chartH - chartW)/2 + chartPadX + textPaddingX,
                getPaddingTop() + chartPadH + textPaddingTop);
        canvas.drawPath(mChartPath, mChartPaint);
        canvas.drawPath(mLinePath, mLinePaint);
        canvas.drawPath(mPointsPath, mPointPaint);
        canvas.drawPath(mChartLinesPath, mChartLinesPaint);
        for (int i = 0; i < mTextPoints.length; i++) {
            canvas.drawText(data.get(i).getBodyPart().getName(), mTextPoints[i].x, mTextPoints[i]
                    .y, mTextPaint);
        }
    }
}
