package com.example.mytableball2;

import java.util.ArrayList;

import static com.example.uti.Constant.*;
import com.example.uti.DBUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HistoryView extends SurfaceView implements SurfaceHolder.Callback{
MainActivity activity;
Paint paint;
ArrayList<String> history1;
ArrayList<String>history2;
ArrayList<int[]>history;
int position=40;
int position_1=100;
int position_2=100;

	public HistoryView(MainActivity activity) {
		super(activity);
		this.activity=activity;
		getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);
		history=DBUtil.searchAll();
	}
public void draw(Canvas canvas)
{
	super.draw(canvas);
	canvas.translate(screenScaleResult.lucX, screenScaleResult.lucY);
	canvas.scale(screenScaleResult.ratio,screenScaleResult.ratio);
	
	canvas.drawBitmap(TP_ARRAY[0],xyoffset[0][0], xyoffset[0][1],paint);
	canvas.drawBitmap(TP_ARRAY[28],xyoffset[28][0], xyoffset[28][1],paint);
	canvas.drawBitmap(TP_ARRAY[39],xyoffset[39][0], xyoffset[39][1],paint);
	canvas.drawBitmap(HISTORY_ARRAY[6], 130,60, paint);//计时
	canvas.drawBitmap(HISTORY_ARRAY[7], 430,60, paint);//练习	
	
	
	for(int i=0;i<HIS_NUM.length-5;i++)
	{
		canvas.drawBitmap(HISTORY_ARRAY[i], 130, 110+50*i, paint);//关数
		canvas.drawBitmap(HISTORY_ARRAY[i], 430, 110+50*i, paint);//关数
	}
	for(int i=0;i<2;i++)//画 各个模式  各个关的是否开锁与是否玩过
	{
		for(int j=0;j<6;j++)
		{
			if(DBUtil.getLock(i, j)==0)
			{
				
			canvas.drawBitmap(HISTORY_ARRAY[10], historypostion[i][j][0],
					historypostion[i][j][1], paint);
			}
			else if(DBUtil.getTimeplay(i,j)==0)
			{
				canvas.drawBitmap(HISTORY_ARRAY[9], 
				historypostion[i][j][0], 
				historypostion[i][j][1],
				 paint);
			}
		}
	}
for(int[] result:history)//result[0] 模式  result[1] 关数  result[2] 玩的时间  result[3]  是否被锁
{
	
 int number=result[2];
 if(number==0)
 {
	 continue;
 }
 String s=""+number;//画玩的时间
 int length=s.length();
 int num_3=number/100;
 int num_2=number%100/10;
 int num_1=number%10;
 if(length>2)
 {
	 canvas.drawBitmap(TP_NUMPIC[num_3], historypostion[result[0]][result[1]][0]-20,
			 historypostion[result[0]][result[1]][1],paint);
 }
	if(length>1)
	{
		canvas.drawBitmap(TP_NUMPIC[num_2],
			historypostion[result[0]][result[1]][0]+20-20, 
			historypostion[result[0]][result[1]][1], 
			 paint);
	}
	if(length>0)
	{
		canvas.drawBitmap(TP_NUMPIC[num_1],
			historypostion[result[0]][result[1]][0]+40-20, 
			historypostion[result[0]][result[1]][1], 
			 paint);
	}
	//  画  ‘秒’  字 
	canvas.drawBitmap(HISTORY_ARRAY[8], 
	historypostion[result[0]][result[1]][0]+70-20, 
	historypostion[result[0]][result[1]][1],
	 paint);	 
}


}

	@Override
public boolean onTouchEvent(MotionEvent e) {
		int currentNUm=e.getAction();
		float x=e.getX();
		float y=e.getY();		
		switch(currentNUm)
		{
			case MotionEvent.ACTION_DOWN:
				
				if(x>xyoffsetEvent[39][0]&&x<xyoffsetEvent[39][0]+TP_ARRAY[39].getWidth()*screenScaleResult.ratio&&//如果差模的返回按钮
						y>xyoffsetEvent[39][1]&&y<xyoffsetEvent[39][1]+TP_ARRAY[39].getHeight()*screenScaleResult.ratio)
					{
						activity.hd.sendEmptyMessage(1);//返回主界面
					}   
			break;
		}
		return true;
}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas=holder.lockCanvas();
		try
		{
			synchronized(holder)
			{
				draw(canvas);
			}
		}catch(Exception e)
		{
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
