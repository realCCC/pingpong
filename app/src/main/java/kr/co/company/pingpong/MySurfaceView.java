package kr.co.company.pingpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;


class Ball{
    int x,y,xInc = 1, yInc = 1;
    int diameter;
    static int WIDTH=1080,HEIGHT=1920;

    public Ball(int d){
        this.diameter=d;

        x=(int)(Math.random()*(WIDTH-d)+3);
        y=(int)(Math.random()*(HEIGHT-d)+3);

        xInc=(int)(Math.random()*5+1);
        yInc=(int)(Math.random()*5+1);
    }

    public void paint(Canvas g){
        Paint paint = new Paint();

        if(x<0||x>(WIDTH-diameter))
            xInc=-xInc;
        if(y<0||y>(HEIGHT-diameter))
            yInc=-yInc;

        x +=xInc;
        y +=yInc;

        paint.setColor(Color.RED);
        g.drawCircle(x,y,diameter,paint);
    }
    public void ballaction1(){
        xInc *= -1;
        yInc *= -1;
    }
}
class Rect{
    int x,y,xInc = 1, yInc = 1;
    int width,height;
    static int WIDTH=1080,HEIGHT=1920;


    public Rect(int width, int height){
        this.width=width;
        this.height=height;

        x=(int)(Math.random()*(WIDTH-width)+3);
        y=(int)(Math.random()*(HEIGHT-height)+3);

        xInc=(int)(Math.random()*5+1);
        yInc=(int)(Math.random()*5+1);
    }

    public void paint(Canvas g){
        Paint paint = new Paint();

        if(x<0||x>(WIDTH-width))
            xInc=-xInc;
        if(y<0||y>(HEIGHT-height))
            yInc=-yInc;

        x +=xInc;
        y +=yInc;

        paint.setColor(Color.BLUE);
        g.drawRect(x,y,x+width,y+height,paint);
    }
    public void ballaction2(){
        xInc *= -1;
        yInc *= -1;
    }
}


public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback{
    public static Ball basket[] = new Ball[10];  //빨강
    public static Rect basket1[] = new Rect[10];  //파랑
    private MyThread thread;

    public MySurfaceView(Context context){
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new MyThread(holder);

        for (int i =0; i<10; i++) {
            basket[i] = new Ball(40);
            basket1[i]= new Rect(80,80);
        }
    }

    public MyThread getThread() {
        return thread;
    }
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);

        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;

        thread.setRunning(false);
        while (retry){
            try {
                thread.join();
                retry = false;
            }catch (InterruptedException e){
        }
    }
}

public class MyThread extends Thread{

        private boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;

        public MyThread(SurfaceHolder surfaceHolder){
            mSurfaceHolder = surfaceHolder;
        }
        @Override
    public void run(){
            while (mRun){
                Canvas c=null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    c.drawColor(Color.BLACK);
                    synchronized (mSurfaceHolder){
                        for(Ball b:basket){
                            b.paint(c);
                        }
                        for(Rect b:basket1){
                            b.paint(c);
                        }
                    }
                }finally {
                    if (c!=null){
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                /*try{Thread.sleep(100)}catch (InterruptedException e){}*/
            }
        }
        public void setRunning(boolean b){
        mRun = b;
        }
    }
}
