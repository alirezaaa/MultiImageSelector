package me.nereo.multi_image_selector.view;

import android.content.Context;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * 支持手势的ImageView
 * Created by Nereo on 2015/4/10.
 */
public class GestureImageView extends ImageView {

    private int mCenterX, mCenterY;
    private GestureDetectorCompat mGestureDetector;
    private Matrix                mImageMatrix;
    private ScaleGestureDetector  mScaleGesture;

    public GestureImageView(Context context) {
        super(context);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context Context
     */
    private void init(final Context context) {

        setScaleType(ScaleType.MATRIX);

        mImageMatrix = new Matrix();

        mScaleGesture = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float factor = detector.getScaleFactor();
                mImageMatrix.postScale(factor, factor, mCenterX, mCenterY);
                setImageMatrix(mImageMatrix);
                return true;
            }
        });

        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1,
                                    MotionEvent e2,
                                    float distanceX,
                                    float distanceY) {
                //mImageMatrix.setTranslate(0, 0);
                //setImageMatrix(mImageMatrix);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mImageMatrix.postScale(1.f, 1.f, mCenterX, mCenterY);
                setImageMatrix(mImageMatrix);
                return true;
            }
        });

    }

    public GestureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {

        boolean retValue = mScaleGesture.onTouchEvent(event);

        retValue = mGestureDetector.onTouchEvent(event) || retValue;

        return retValue || super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            int cx = (w - getDrawable().getIntrinsicWidth()) / 2;
            int cy = (h - getDrawable().getIntrinsicHeight()) / 2;
            mImageMatrix.setTranslate(cx, cy);
            setImageMatrix(mImageMatrix);

            mCenterX = w / 2;
            mCenterY = h / 2;
        }
    }

}
