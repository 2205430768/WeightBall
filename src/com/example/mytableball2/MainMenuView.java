package com.example.mytableball2;

import com.example.uti.Constant;
import static com.example.uti.Constant.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainMenuView extends SurfaceView  implements SurfaceHolder.Callback{
MainActivity activity;
Paint paint;
	public MainMenuView(MainActivity activity) {
		super(activity);
		this.activity=activity;
		this.getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);

	}
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int currentNum=e.getAction();
		float x=e.getX();
		float y=e.getY();
		switch(currentNum)
		{
		case MotionEvent.ACTION_DOWN:
			//开始游戏
			if(x>xyoffsetEvent[1][0]&&x<xyoffsetEvent[1][0]+TP_ARRAY[1].getWidth()*screenScaleResult.ratio&&
					y>xyoffsetEvent[1][1]&&y<xyoffsetEvent[1][1]+TP_ARRAY[1].getHeight()*screenScaleResult.ratio)
			{
				activity.hd.sendEmptyMessage(3);//去模式选择界面
			}
			//历史记录
			else if(x>xyoffsetEvent[15][0]&&x<xyoffsetEvent[15][0]+TP_ARRAY[15].getWidth()*screenScaleResult.ratio&&
					y>xyoffsetEvent[15][1]&&y<xyoffsetEvent[15][1]+TP_ARRAY[15].getHeight()*screenScaleResult.ratio)
			{
				activity.hd.sendEmptyMessage(5);//去历史记录界面
			}
			//设置按钮
			else if(x>xyoffsetEvent[16][0]&&x<xyoffsetEvent[16][0]+TP_ARRAY[16].getWidth()*Constant.screenScaleResult.ratio&&
				y>xyoffsetEvent[16][1]&&y<xyoffsetEvent[16][1]+TP_ARRAY[16].getHeight()*Constant.screenScaleResult.ratio)
			{
				activity.hd.sendEmptyMessage(4);//去设置界面
				
			}
			
		break;
		}



		return true;
		
	}
	public void draw(Canvas canvas)
	{
		super.draw(canvas);
		canvas.translate(screenScaleResult.lucX, screenScaleResult.lucY);
		canvas.scale(screenScaleResult.ratio, screenScaleResult.ratio);
		//绘制背景
		canvas.drawBitmap(TP_ARRAY[0],xyoffset[0][0] ,xyoffset[0][1], paint);
		//绘制名字
		canvas.drawBitmap(TP_ARRAY[29], xyoffset[29][0], xyoffset[29][1],paint);
		//play按钮
		canvas.drawBitmap(TP_ARRAY[1], xyoffset[1][0], xyoffset[1][1],paint);
		//历史记录按钮
		canvas.drawBitmap(TP_ARRAY[15], xyoffset[15][0], xyoffset[15][1],paint);
		//设置按钮
		canvas.drawBitmap(TP_ARRAY[16], xyoffset[16][0], xyoffset[16][1],paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(Constant.bg_music_sound)//如果声音按钮打开
		{
			if(Constant.from_nextview)//如果是从跳转界面而来
			{
				YINYUE1=true;
				this.activity.soundutil.stop_bg_sound();//停止播放背景音乐
				this.activity.soundutil.play_bg_sound();
			}
		}
		Constant.from_nextview=false;
		//绘画
		Canvas canvas=holder.lockCanvas();
		try {
			synchronized (canvas) {
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
