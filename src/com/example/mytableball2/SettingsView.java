package com.example.mytableball2;

import static com.example.uti.Constant.*;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SettingsView extends SurfaceView implements SurfaceHolder.Callback{
MainActivity activity;
Paint paint;
	public SettingsView(MainActivity activity) {
		super(activity);
		this.activity=activity;
		getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent e) {
	int currentNUm=e.getAction();
	float x=e.getX();
	float y=e.getY();
	switch(currentNUm)
	{
	case MotionEvent.ACTION_DOWN:
		if(x>xyoffsetEvent[26][0]&&x<xyoffsetEvent[26][0]+TP_ARRAY[26].getWidth()*screenScaleResult.ratio&&//������ִ򿪰�ť
				y>xyoffsetEvent[26][1]&&y<xyoffsetEvent[26][1]+TP_ARRAY[26].getHeight()*screenScaleResult.ratio)
			{
				if(!YINYUE_CLOSE)//������ֿ�
				{
					this.activity.soundutil.stop_bg_sound();
					bg_music_sound=false;
				}
				else if(YINYUE_CLOSE)//������ֹ�
				{
					this.activity.soundutil.play_bg_sound();
					bg_music_sound=true;
				}
				this.repaint();
			}
		else if(x>xyoffsetEvent[26][0]&&x<xyoffsetEvent[26][0]+TP_ARRAY[26].getWidth()*screenScaleResult.ratio&&//�����Ч�򿪰�ť
		y>xyoffsetEvent[26][1]-(xyoffsetEvent[23][1]-xyoffsetEvent[24][1])&&y<xyoffsetEvent[26][1]-(xyoffsetEvent[23][1]-xyoffsetEvent[24][1])+TP_ARRAY[26].getHeight()*screenScaleResult.ratio)
	{
		YINXIAO_OPEN=!YINXIAO_OPEN;
		this.repaint();
	}
	else if(x>xyoffsetEvent[26][0]&&x<xyoffsetEvent[26][0]+TP_ARRAY[26].getWidth()*screenScaleResult.ratio&&//����𶯴򿪰�ť
		y>xyoffsetEvent[26][1]-2*(xyoffsetEvent[23][1]-xyoffsetEvent[24][1])&&y<xyoffsetEvent[26][1]-2*(xyoffsetEvent[23][1]-xyoffsetEvent[24][1])+TP_ARRAY[26].getHeight()*screenScaleResult.ratio)
	{
		ZHENDONG_OPEN=!ZHENDONG_OPEN;
		this.repaint();
	}
	else if(x>xyoffsetEvent[19][0]&&x<xyoffsetEvent[19][0]+TP_ARRAY[19].getWidth()*screenScaleResult.ratio&&//�����ģ���Ƿ��ذ�ť
		y>xyoffsetEvent[19][1]&&y<xyoffsetEvent[19][1]+TP_ARRAY[19].getHeight()*screenScaleResult.ratio)
	{
		activity.hd.sendEmptyMessage(1);
	}
break;
	}





		return true;
	
	}

	private void repaint() {
		SurfaceHolder holder=this.getHolder();
		Canvas canvas=holder.lockCanvas();
		try {
			synchronized (holder) {
			draw(canvas);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				holder.unlockCanvasAndPost(canvas);
			}
		}


		
	}

	public void draw(Canvas canvas)
	{
		super.draw(canvas);
		canvas.translate(screenScaleResult.lucX, screenScaleResult.lucY);
		canvas.scale(screenScaleResult.ratio, screenScaleResult.ratio);	
		canvas.drawBitmap(TP_ARRAY[0],xyoffset[0][0], xyoffset[0][1],paint);
		canvas.drawBitmap(TP_ARRAY[23],xyoffset[23][0], xyoffset[23][1],paint);
		canvas.drawBitmap(TP_ARRAY[24],xyoffset[24][0], xyoffset[24][1],paint);
		canvas.drawBitmap(TP_ARRAY[25],xyoffset[25][0], xyoffset[25][1],paint);
		if(!YINYUE_CLOSE)//������ֿ�
		{
			canvas.drawBitmap(TP_ARRAY[26],xyoffset[26][0], xyoffset[26][1],paint);
		}
		else
		{
			canvas.drawBitmap(TP_ARRAY[27],xyoffset[26][0], xyoffset[26][1],paint);
		}
		if(YINXIAO_OPEN)//�����Ч��
		{
			canvas.drawBitmap(TP_ARRAY[26],xyoffset[26][0] , xyoffset[26][1]-(xyoffset[23][1]-xyoffset[24][1]),paint);
		}
		else
		{
			canvas.drawBitmap(TP_ARRAY[27],xyoffset[26][0], xyoffset[26][1]-(xyoffset[23][1]-xyoffset[24][1]),paint);
		}
		if(ZHENDONG_OPEN)//����𶯴�
		{
			canvas.drawBitmap(TP_ARRAY[26],xyoffset[26][0], xyoffset[26][1]-2*(xyoffset[23][1]-xyoffset[24][1]),paint);
		}
		else
		{
			canvas.drawBitmap(TP_ARRAY[27],xyoffset[26][0], xyoffset[26][1]-2*(xyoffset[23][1]-xyoffset[24][1]),paint);
		}
		
		canvas.drawBitmap(TP_ARRAY[19],xyoffset[19][0], xyoffset[19][1],paint);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	Canvas canvas=holder.lockCanvas();
	try {
		synchronized (holder) {
		draw(canvas);	
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
   finally
   {
	   if(canvas!=null)
	   {
		   holder.unlockCanvasAndPost(canvas);
	   }
   }

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
