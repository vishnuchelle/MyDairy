package com.example.vishnuchelle.mydairy;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


/**
 * Created by Vishnu Chelle on 5/1/2015.
 */
public class DayGraphView extends RelativeLayout implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private Context _context;
    private float gap;
    private Paint mPaint;
    private Paint mLinePaint;
    private Paint mGraphPaint;
    private Paint textPaint;
    private Paint scorePaint;
    private Path mPath;
    //  private float[] dayPoints;
    private int[] dayValues;
    private int[] dateValues;
    private int goal = 1000;
    private int max;
    private int currentDay;

    private GestureDetectorCompat mDetector;
    private IDaySelectedListner mDaySelectedListner;

    public DayGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _context = context;
        //paint to draw rect bars
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#c7e1e0"));
        mLinePaint = new Paint();

        //paint to draw graphLine
        mGraphPaint = new Paint();
        mGraphPaint.setColor(Color.BLACK);
        mGraphPaint.setStyle(Paint.Style.STROKE);
        mGraphPaint.setAntiAlias(true);
        mGraphPaint.setStrokeWidth(pxFromDp(_context,1.65f));


        //paint to draw score text
        scorePaint = new Paint();
        scorePaint.setTextSize(pxFromDp(_context,13.3f));
        scorePaint.setStrokeWidth(15);
        Log.i("density", "10 px -->" + dpFromPx(_context, 10));
        Log.i("density","50 px -->"+dpFromPx(_context,50));



        //paint to draw the goal line
        mLinePaint.setColor(Color.parseColor("#008167"));
        mLinePaint.setStrokeWidth(pxFromDp(_context,1.95f));
        mPath = new Path();
        mPath.moveTo(0,50);

        //paint to draw weekday text
        textPaint = new Paint();

        textPaint.setTextSize(pxFromDp(_context,16.6f));
        textPaint.setStrokeWidth(15);
        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

//       textPaint.setTypeface(Utils.getTypeFace(_context, Utils.Fonts.brandon_blk));
//        scorePaint.setTypeface(Utils.getTypeFace(_context, Utils.Fonts.brandon_med));

//       dayPoints = new float[]{0.1f,0.3f,0.5f,0.4f,0.1f};
        dayValues = new int[0];

        max = getMax(dayValues,goal);

        mDetector = new GestureDetectorCompat(this.getContext(),this);
        mDetector.setOnDoubleTapListener(this);
    }

    private int getMax(int[] dayValues, int goal) {
        int max = goal;
        for (int i =0;i<dayValues.length;i++){
            if(max < dayValues[i]){
                max = dayValues[i];
            }
        }
        return max;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        gap = w/33;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return true;//super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, 50);
        mLinePaint.setColor(Color.parseColor("#008167"));
        canvas.drawLine(0, this.getHeight() * getHeightRatio(goal, max), this.getWidth(), this.getHeight() * getHeightRatio(goal, max), mLinePaint);
        float startX = gap;

        for (int i =0;i<dayValues.length;i++){

            float height = this.getHeight()*getHeightRatio(dayValues[i],max);
            canvas.drawRect(startX,height,startX + 32*gap/9,this.getHeight()*0.75f,mPaint);
            mPath.lineTo((startX + 16 * gap / 9), height);
            String scoreText = (dayValues[i])+"";

            mLinePaint.setColor(Color.BLACK);
            scorePaint.setColor(Color.parseColor("#c3c3c3"));




//            textPaint.setTypeface(Utils.getTypeFace(_context,Utils.Fonts.brandon_blk));
            float textWidth = textPaint.measureText(getDay(i));
            float scoreWidth = scorePaint.measureText(scoreText);
            // measure text height
            float textHeight = textPaint.descent() - textPaint.ascent();

            textPaint.setColor(Color.BLACK);

            if(dateValues[i]!=0) {
                canvas.drawText(dateValues[i] + "", (startX + 16 * gap / 9 - textWidth / 2), this.getHeight() * 0.75f - textHeight / 2, textPaint);
            }else{
                canvas.drawText("N/A", (startX + 16 * gap / 9 - textWidth / 2), this.getHeight() * 0.75f - textHeight / 2, textPaint);
            }
            canvas.drawText(scoreText, (startX + 16 * gap / 9 - scoreWidth * 0.5f), this.getHeight() * 0.75f + textHeight, scorePaint);
//            if(i == currentDay)
//                canvas.drawRect((startX + 16*gap/9 - textWidth),this.getHeight()*0.75f-textHeight*1.3f,(startX + 16*gap/9 + textWidth),this.getHeight()*0.75f-textHeight*0.3f,mGraphPaint);
            startX = startX + gap + 32*gap/9;

        }

        for(int i = dayValues.length  ; i < 7;i++){
            float textWidth = textPaint.measureText(getDay(i));
            // measure text height
            float textHeight = textPaint.descent() - textPaint.ascent();
            textPaint.setColor(Color.parseColor("#c3c3c3"));
            canvas.drawText(getDay(i),(startX + 16*gap/9 - textWidth / 2), this.getHeight()*0.75f-textHeight/2,textPaint);
            startX = startX + gap + 32*gap/9;
        }
        canvas.drawPath(mPath, mGraphPaint);
        startX = gap;
        for (int i =0;i<dayValues.length;i++) {
            float height = this.getHeight()*getHeightRatio(dayValues[i],max);
            if(height < this.getHeight()*getHeightRatio(goal,max)){
                mLinePaint.setColor(Color.parseColor("#008167"));

            }else{
                mLinePaint.setColor(Color.BLACK);

            }
            canvas.drawCircle((startX + 16 * gap / 9), height, pxFromDp(_context, 3.3f), mLinePaint);
            startX = startX + gap + 32*gap/9;
        }
    }

    public void setDaySelectedListner(IDaySelectedListner listner){
        mDaySelectedListner = listner;
    }

    //equation of line joining two points (0,0.75)  and (max,0.08)
    private float getHeightRatio(int x, int max) {
        float y = x*(0.08f-0.75f)/max + 0.75f;
        return y;
    }

    public static float dpFromPx(Context context, float px)
    {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(Context context, float dp)
    {
        return dp * context.getResources().getDisplayMetrics().density;
    }


    private String getDay(int i){
        switch(i){
            case 0:
                return "S";

            case 1:
                return "M";

            case 2:
                return "T";

            case 3:
                return "W";

            case 4:
                return "R";
            case 5:
                return "F";

            case 6:
                return "S";

            default:
                return "M";
        }

    }

    public void setDayValues(int[] dayValues ,int[] dateValues, int goal,int currentDay){
        this.dayValues = dayValues;
        this.dateValues = dateValues;
        this.goal = goal;
        max = getMax(dayValues,goal);
        this.currentDay = currentDay;
        invalidate();
    }


    public void setDailyGoal(int goal){
        this.goal = goal;

        max = getMax(dayValues,goal);
        //        max = max > goal ? max : goal;
        invalidate();
    }


    public void setCurrentDay(int day){
        currentDay = day;

        invalidate();
    }

    public void setNextDay(){
        currentDay = currentDay + 1;
        if(currentDay == 7){
            currentDay = 0;
        }
        invalidate();
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
       
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float startX = gap;
        for (int i =0;i<dayValues.length;i++) {

            float height = this.getHeight() * getHeightRatio(dayValues[i], max);
            RectF rect = new RectF(startX, height, startX + 32 * gap / 9, this.getHeight() * 0.75f);
//            canvas.drawRect(startX, height, startX + 32 * gap / 9, this.getHeight() * 0.75f, mPaint);
            if(rect.contains(e.getX(),e.getY()) && mDaySelectedListner!=null){
                mDaySelectedListner.onDaySelected(i);
//                Toast.makeText(DayGraphView.this.getContext(),"clicked on Day "+i,Toast.LENGTH_SHORT).show();
            }
            startX = startX + gap + 32 * gap / 9;
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(mDaySelectedListner == null)
            return false;
        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            mDaySelectedListner.onWeekSelected();
            return true; // Bottom to top
        }else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

            mDaySelectedListner.onNextDay();
            return true; // Right to left
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

            mDaySelectedListner.onPreviousDay();
            return true; // Left to right
        }else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            //Nothing to here
            return true; //  top to bottom
        }else{
            return false;
        }
    }

    public interface IDaySelectedListner{

        public void onDaySelected(int day);

        public void onWeekSelected();

        public void onNextDay();

        public void onPreviousDay();

    }
}