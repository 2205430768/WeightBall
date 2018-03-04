package com.example.mytableball2;

import com.example.uti.PicLoadUtil;
import static com.example.uti.Constant.*;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback{
MainActivity tableBallActivity;
Paint paint;//����
int currentAlpha;//��ǰ��͸��ֵ
int sleepSpan=150;//��������ʱʱ��
Bitmap logo;//logoͼƬ������
float  currentX;//ͼƬλ��
float currentY;

	public WelcomeView(MainActivity activity) {
		super(activity);
		this.tableBallActivity=activity;
		this.getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);
		logo=PicLoadUtil.loadBM(getResources(), "androidheli.png");

	}
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
//		//���ƺ��������屳��
		paint.setColor(Color.BLACK);
		paint.setAlpha(255);
		canvas.drawRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT,paint);
		//����ƽ����ͼ
		if(logo==null)
			return;
		paint.setAlpha(currentAlpha);
		canvas.drawBitmap(logo,currentX,currentY, paint);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		currentX=SCREEN_WIDTH/2-logo.getWidth()/2;
		currentY=SCREEN_HEIGHT/2-logo.getHeight()/2;
		new Thread(){
			@SuppressLint("WrongCall")
			public void run(){
				SurfaceHolder  mholder=WelcomeView.this.getHolder();
				for(int i=255;i>-10;i-=10)
				{
					if(i<0)
					{
						i=0;
					}
					currentAlpha=i;
					Canvas canvas=mholder.lockCanvas();
					try {
						synchronized (mholder) {
						onDraw(canvas);	
						}
						Thread.sleep(20);		
					} catch (Exception e) {
						e.printStackTrace();
                       
					}
					finally
					{
						if(canvas!=null)
                       	{
                       		mholder.unlockCanvasAndPost(canvas);
                       	}
					}
				}
				
				
				
			}
		}.start();

  while(!LOAD_FINISH)
  {
	  try {
		Thread.sleep(10);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	  tableBallActivity.hd.sendEmptyMessage(1);
  }

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
