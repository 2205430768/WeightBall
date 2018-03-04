package com.example.model;

import org.jbox2d.dynamics.Body;
import android.graphics.Bitmap;
import static com.example.uti.Constant.*;
import com.example.mytableball2.GameView;
import com.example.uti.Constant;

public class FalshHole extends Hole{
	int timeDeadSpan;//洞闭合，不起作用的时间，单位为秒
	int timeLiveSpan;//洞打开，起作用的时间，单位为秒
	public boolean isFlashDead=true;//是不是可以闪的洞
	FalshHole(Body body,Bitmap bitmap,int timeDeadSpan,int timeLiveSpan,GameView gameview)
	{
		super(body,bitmap,gameview);
		this.timeDeadSpan=timeDeadSpan;
		this.timeLiveSpan=timeLiveSpan;
		//开启一个换图，控制碰撞标志位的线程
		FlashThread flashThread=new FlashThread();
		flashThread.start();
	}
//	//换图，控制碰撞标志位的线程
	private class FlashThread extends Thread{
		
		public void run(){
			while(gameView.heroislive)
			{
				//没有暂停
				if(!gameView.isGamePause)
				{
					isFlashDead=true;
					//进行换图
					for(int i=1;i<TRUE_FLASHHOLE.length*2-1;i++)
					{
						//暂停
					while(gameView.isGamePause){try {Thread.sleep(1000);} catch (Exception e) {e.printStackTrace();}}
					//换图
					bitmap=Constant.TRUE_FLASHHOLE[Math.abs(Constant.TP_FLASHHOLE.length-1-i)];
					
				try {Thread.sleep(timeDeadSpan*1000/(Constant.TRUE_FLASHHOLE.length*2));//设置合理的睡眠时间
					} catch (InterruptedException e) {e.printStackTrace();}
				
					}
					
					
					isFlashDead=false;
					bitmap=Constant.TRUE_FLASHHOLE[13];//洞的图
					//洞不发生变化
					try {Thread.sleep(timeLiveSpan*1000);//睡眠
					} catch (InterruptedException e) {e.printStackTrace();}
					
					
				}
				
				
				
				else if(gameView.isGamePause)//当按了暂停按钮的时侯进行睡眠
					{
						try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
					}
			}
			
		}
	}


}
