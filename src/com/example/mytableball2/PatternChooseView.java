package com.example.mytableball2;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.example.uti.Constant.*;
public class PatternChooseView extends SurfaceView implements SurfaceHolder.Callback{
	MainActivity activity;
	Paint paint;
	boolean moshi=false;
	public PatternChooseView(MainActivity activity) {
		super(activity);
		this.activity=activity;
		paint=new Paint();
		getHolder().addCallback(this);
		paint.setAntiAlias(true);
		
		
	}
public void draw(Canvas canvas)
{
super.draw(canvas);
canvas.translate(screenScaleResult.lucX, screenScaleResult.lucY);
canvas.scale(screenScaleResult.ratio,screenScaleResult.ratio);
canvas.drawBitmap(TP_ARRAY[0], xyoffset[0][0],xyoffset[0][1], paint);
//绘制名字
canvas.drawBitmap(TP_ARRAY[29], xyoffset[29][0], xyoffset[29][1],paint);
canvas.drawBitmap(TP_ARRAY[17],xyoffset[17][0], xyoffset[17][1],paint);
canvas.drawBitmap(TP_ARRAY[18],xyoffset[18][0], xyoffset[18][1],paint);
canvas.drawBitmap(TP_ARRAY[19],xyoffset[19][0], xyoffset[19][1],paint);
}

	@Override
public boolean onTouchEvent(MotionEvent e) {
		int currentNUm=e.getAction();
		float x=e.getX();
		float y=e.getY();		
		switch(currentNUm)
		{
			case MotionEvent.ACTION_DOWN:
				
				if(x>xyoffsetEvent[17][0]&&x<xyoffsetEvent[17][0]+TP_ARRAY[17].getWidth()*screenScaleResult.ratio&&//如果差模的是限时模式
					y>xyoffsetEvent[17][1]&&y<xyoffsetEvent[17][1]+TP_ARRAY[17].getHeight()*screenScaleResult.ratio)
				{
					PLAY_MODEL=PLAY_MODEL1;
					activity.hd.sendEmptyMessage(6);//去选关界面
				}
				else if(x>xyoffsetEvent[18][0]&&x<xyoffsetEvent[18][0]+TP_ARRAY[18].getWidth()*screenScaleResult.ratio&&//如果差模的是练习模式
					y>xyoffsetEvent[18][1]&&y<xyoffsetEvent[18][1]+TP_ARRAY[18].getHeight()*screenScaleResult.ratio)
				{
					PLAY_MODEL=PLAY_MODEL2;
					activity.hd.sendEmptyMessage(6);//去选关界面
				}
				else if(x>xyoffsetEvent[19][0]&&x<xyoffsetEvent[19][0]+TP_ARRAY[19].getWidth()*screenScaleResult.ratio&&//如果差模的是返回按钮
					y>xyoffsetEvent[19][1]&&y<xyoffsetEvent[19][1]+TP_ARRAY[19].getHeight()*screenScaleResult.ratio)
				{
					activity.hd.sendEmptyMessage(1);//返回主界面
				}
			break;
		}
		return true;
}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
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
