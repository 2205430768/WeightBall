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
//	/flag�Ǳ�־λ����־�Ƿ������ı���
	boolean flag=false;
	Bitmap bitmapother=Constant.TP_ARRAY[5];

	public Hole(Body body, Bitmap bitmap, GameView gameview) {
		super(body, bitmap, gameview);
		
	}
//	//С���䶴����תС��
	public void drawself(Canvas canvas ,Paint paint)
	{
		super.drawself(canvas, paint);
		if(flag)//���С���붴��ײ�����������е�ĳ��ͼƬ
		{
			canvas.save();
			angle=body.getAngle();
			//Log.i("�ƶ��Ƕ�","angle="+angle);
			canvas.rotate(angle+Constant.ROTEANGLE+Constant.ROTEANGLEOFFSET,x,y);
			Matrix  m3=new Matrix();
			m3.setTranslate(x-bitmapother.getWidth()/2, y-bitmapother.getHeight()/2);
			canvas.drawBitmap(bitmapother, m3, paint);
			canvas.restore();
		}
	}
	
	//��Ͷ�������ײʱ�����ķ���
	//ֻ�ж���ִ������������򲻻�ִ������������˴���ֻ��һ��ͼ
		public void doAction()
		{
			BitmapChangeThread bitmapchanagethread=new BitmapChangeThread(this);//ˢ��С���䶴�������߳�
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
