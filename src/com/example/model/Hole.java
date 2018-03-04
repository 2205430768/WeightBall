package com.example.model;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.example.mytableball2.GameView;
import com.example.uti.Constant;

public class Hole extends Ball{
//	/flag是标志位，标志是否画连续的背景
	boolean flag=false;
	Bitmap bitmapother=Constant.TP_ARRAY[5];

	public Hole(Body body, Bitmap bitmap, GameView gameview) {
		super(body, bitmap, gameview);
		
	}
//	//小球落洞后旋转小球
	public void drawself(Canvas canvas ,Paint paint)
	{
		super.drawself(canvas, paint);
		if(flag)//如果小球与洞相撞，画出动画中的某张图片
		{
			canvas.save();
			angle=body.getAngle();
			//Log.i("移动角度","angle="+angle);
			canvas.rotate(angle+Constant.ROTEANGLE+Constant.ROTEANGLEOFFSET,x,y);
			Matrix  m3=new Matrix();
			m3.setTranslate(x-bitmapother.getWidth()/2, y-bitmapother.getHeight()/2);
			canvas.drawBitmap(bitmapother, m3, paint);
			canvas.restore();
		}
	}
	
	//球和洞发生碰撞时发生的方法
	//只有洞才执行这个方法，球不会执行这个方法，此处洞只换一下图
		public void doAction()
		{
			BitmapChangeThread bitmapchanagethread=new BitmapChangeThread(this);//刷新小球落洞动画的线程
			bitmapchanagethread.start();
			flag=true;
			
		}
		
	private class BitmapChangeThread  extends Thread
	{
		Hole ball;
		int i=0;
		BitmapChangeThread(Hole ball)
		{
			this.ball=ball;
		}
		public void run(){
			while(i<15)
			{
				ball.bitmapother=Constant.BALL_IN_HOLE[i][0];
				i++;
				try {
					Thread.sleep(20);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ball.bitmapother=null;
			flag=false;
		}
	}

}
