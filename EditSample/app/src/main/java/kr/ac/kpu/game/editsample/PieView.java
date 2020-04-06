package kr.ac.kpu.game.editsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.Log;
import android.view.View;

public class PieView extends View {
    private static final String TAG = PieView.class.getSimpleName();

    public PieView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w=getWidth();
        int h=getHeight();
        int pl=getPaddingLeft();
        int pt=getPaddingTop();
        Log.d(TAG, "onDraw()");
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5.0f);
        canvas.drawLine(pl,pt,w,h,paint);

        paint.setTextSize(h/2);
        paint.setColor(0xFF1DDB16);
        canvas.drawText("Hello",w/2,h/2,paint);

        Path path=new Path();
        path.moveTo(w/2,h/6*4);
        path.lineTo(w,h/6*4);
        path.lineTo(w/12*7,h);
        path.lineTo(w/4*3,h/2);
        path.lineTo(w/12*11,h);
        path.lineTo(w/2,h/6*4);
        paint.setColor(0xAFFFE400);
        canvas.drawPath(path,paint);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            paint.setColor(0xAF7FFF7F);
            canvas.drawRoundRect(20,20,w/2,h/2,30,30,paint);

            paint.setColor(0xFF0054FF);
            canvas.drawOval(20,h/2,w/2,h,paint);
        }



        super.onDraw(canvas);
    }
}
