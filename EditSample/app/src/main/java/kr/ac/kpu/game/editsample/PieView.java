package kr.ac.kpu.game.editsample;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class PieView extends View {
    private static final String TAG = PieView.class.getSimpleName();

    public PieView(Context context) {
        super(context);
    }
    private float rectX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() != MotionEvent.ACTION_DOWN){
            return false;
        }
        ValueAnimator anim= ValueAnimator.ofFloat(100,500);
        anim.setDuration(2000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rectX = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
        return true;
    }

    
    @Override
    protected void onDraw(Canvas canvas) {
        int w=getWidth();
        int h=getHeight();
        int pl=getPaddingLeft();
        int pt=getPaddingTop();
        Log.d(TAG, "onDraw()");

        Resources res = getResources();
        Bitmap b= BitmapFactory.decodeResource(res,R.mipmap.ic_launcher);
        Matrix m=new Matrix();
        m.preScale(2.0f,1.5f);
        m.preTranslate(100f,10f);
        m.preRotate(45f);
        canvas.drawBitmap(b,m,null);
        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(5.0f);
//        canvas.drawLine(pl,pt,w,h,paint);

//        paint.setTextSize(h/2);
//        paint.setColor(0xFF1DDB16);
//        canvas.drawText("Hello",rectX,h/2,paint);

//        Path path=new Path();
//        path.moveTo(w/2,h/6*4);
//        path.lineTo(w,h/6*4);
//        path.lineTo(w/12*7,h);
//        path.lineTo(w/4*3,h/2);
//        path.lineTo(w/12*11,h);
//        path.lineTo(w/2,h/6*4);
//        paint.setColor(0xAFFFE400);
//        canvas.drawPath(path,paint);

//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//            paint.setColor(0xAF7FFF7F);
//            canvas.drawRoundRect(20,20,w/2,h/2,30,30,paint);
//
//            paint.setColor(0xFF0054FF);
//            canvas.drawOval(20,h/2,w/2,h,paint);
//        }



        super.onDraw(canvas);
    }
}
