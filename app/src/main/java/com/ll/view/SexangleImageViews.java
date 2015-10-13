package com.ll.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.ll.wsn.MainActivity;
import com.ll.wsn.R;

/**
 * Created by dell on 2015/10/13.
 */
public class SexangleImageViews extends ImageView {

    private int mWidth;
    private int mHeight;
    private int mLenght;
    private Paint paint;

    private int color;
    private float textsize=26;

    private int home;
    private String texts;
    private Bitmap home_flight;
    long down,up=0;
    private float lastDownX, lastDownY;
    private OnSexangleImageClickListener listener;
    private OnLongSexangleImageClickListener longListener;
    public SexangleImageViews(Context context) {
        super(context);

    }
    public SexangleImageViews(Context context,MainActivity.ViewBean bean) {
        super(context);
        setCustomAttributes(bean);
    }

    private void setCustomAttributes(MainActivity.ViewBean bean) {
        // TODO Auto-generated method stub
        //color = bean.getColor();
        textsize = bean.getTextsize();
        home = bean.getHome();
        texts = bean.getTexts();

        home_flight = bitmaps[home];
        color=colors[bean.getColor()];
    }


//    public SexangleImageViews(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.sexangleImageView);
//        color = typedArray.getInt(R.styleable.sexangleImageView_backcolor, 0);
//        textsize = typedArray.getDimension(R.styleable.sexangleImageView_textSize, 24);
//        home = typedArray.getInt(R.styleable.sexangleImageView_home, 0);
//        texts = typedArray.getString(R.styleable.sexangleImageView_texts);
//
//        home_flight = bitmaps[home];
//        typedArray.recycle();
//    }

    private int[] colors = { getResources().getColor(R.color.color_flight),
            getResources().getColor(R.color.color_train),
            getResources().getColor(R.color.color_setting),
            getResources().getColor(R.color.color_sales),
            getResources().getColor(R.color.color_hotel),
            getResources().getColor(R.color.color_user),
            getResources().getColor(R.color.color_remind)};

    private Bitmap[] bitmaps = {
            BitmapFactory.decodeResource(getResources(), R.drawable.icon_flight),
            BitmapFactory.decodeResource(getResources(),R.drawable.train),
            BitmapFactory.decodeResource(getResources(),R.drawable.icon_hotel),
            BitmapFactory.decodeResource(getResources(),R.drawable.icon_user),
            BitmapFactory.decodeResource(getResources(),R.drawable.icon_cx),
            BitmapFactory.decodeResource(getResources(),R.drawable.icon_hbtx),
            BitmapFactory.decodeResource(getResources(),R.drawable.icon_setting)

    };

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();

        mLenght = mWidth / 2;

        double radian30 = 30 * Math.PI / 180;
        float a = (float) (mLenght * Math.sin(radian30));
        float b = (float) (mLenght * Math.cos(radian30));
        float c = (mHeight - 2 * b) / 2;



        //int color=Color.parseColor("#FFD700");//十六进制颜色代码,转为int类型
        if (null == paint) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            //paint.setColor(Color.BLACK);
            paint.setColor(color);
            paint.setAlpha(100);
        }
        //画六边形
        Path path = new Path();
        path.moveTo(getWidth(), getHeight() / 2);
        path.lineTo(getWidth() - a, getHeight() - c);
        path.lineTo(getWidth() - a - mLenght, getHeight() - c);
        path.lineTo(0, getHeight() / 2);
        path.lineTo(a, c);
        path.lineTo(getWidth() - a, c);
        path.close();
        canvas.drawPath(path, paint);


        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(textsize);
        // 去锯齿
        paint.setAntiAlias(true);

        //画背景
        Matrix matrix = new Matrix();
        matrix.postTranslate(this.getWidth() / 2 - home_flight.getWidth() / 2,
                this.getHeight() / 2 - home_flight.getHeight() / 2);
        canvas.drawBitmap(home_flight, matrix, paint);
        canvas.drawText(texts, getWidth()/2-18, getHeight()-30, paint);
        //canvas.drawText(texts,centreX,centreY, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }


    /**
     * XY轴同时缩放
     * @param v
     */
    public  static void loadscaleDown(View v){
        PropertyValuesHolder propertyValues1=PropertyValuesHolder.ofFloat("scaleX", 1.0f,0.9f);
        PropertyValuesHolder propertyValues2=PropertyValuesHolder.ofFloat("scaleY", 1.0f,0.9f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, propertyValues1,propertyValues2);
        animator.setDuration(200);
        animator.start();
    }

    public  static void loadscaleUp(View v){
        PropertyValuesHolder propertyValues3=PropertyValuesHolder.ofFloat("scaleX", 0.9f,1.0f);
        PropertyValuesHolder propertyValues4=PropertyValuesHolder.ofFloat("scaleY", 0.9f,1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,propertyValues3,propertyValues4);
        animator.setDuration(200);
        animator.start();
    }

    private boolean isFinish;//是否不响应点击时间
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                loadscaleDown(this);
                lastDownX = event.getX();
                lastDownY = event.getY();

                //上次按下的时间与本次按下的时间在20~350毫秒之间时，响应双击事件
                if((System.currentTimeMillis()-down)>20&&(System.currentTimeMillis()-down)<=350&&longListener!=null){
                    longListener.onDoubleClick(this);
                }
                down=System.currentTimeMillis();
                break;

            case MotionEvent.ACTION_UP:

                if (isFinish) {
                    isFinish = false;
                } else {
                    loadscaleUp(this);
                    //响应点击事件
                    if(listener!=null){
                        listener.onClick(this);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (isFinish) {
                    return true;
                }
                //按下后，如果移动的距离大于当前view 1/3 距离 则不响应点击时间
                float moveX = event.getX() - lastDownX;
                float moveY = event.getY() - lastDownY;
                if (Math.abs(moveX) > getWidth() / 3
                        || Math.abs(moveY) > getHeight() / 3) {
                    isFinish = true;
                    loadscaleUp(this);
                }
                break;

            // 滑动出去不会调用action_up,调用action_cancel
            case MotionEvent.ACTION_CANCEL:
                loadscaleUp(this);
//			paint.setColor(Color.BLACK);
//			paint.setAlpha(60);
//			invalidate();
                break;

        }

        return true;
    }

    public void setOnSexangleImageClick(OnSexangleImageClickListener listener ){
        this.listener=listener;
    }

    public void setOnLongSexangleImageClick(OnLongSexangleImageClickListener longListener) {
        this.longListener = longListener;
    }

    public interface  OnSexangleImageClickListener {
        public void onClick(View view);
    }

    public interface OnLongSexangleImageClickListener{
        public void onDoubleClick(View view);
    }
}

