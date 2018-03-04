package com.example.model;

import com.example.mytableball2.GameView;

public class PlayTimeCount1 extends PlayTime1{
	int countTime=1*60;//计时的时间，用1*60,1是分，60是秒
	
	public PlayTimeCount1(GameView gameview)
	{
		super(gameview);
		TimeGoThread timegothread=new TimeGoThread();
		timegothread.start();
	}
	
	private class TimeGoThread  extends Thread
	{
		public void run(){
			while(runTime<countTime&&gameview.heroislive)
			{
				if(!gameview.isGamePause)
				{
					runTime++;
					index_m0=((countTime-runTime)/60)%60/10;//练习模式下的10分位
					index_m1=((countTime-runTime)/60)%60%10;//练习模式下的分位
					index_s0=(countTime-runTime)%60/10;
					index_s1=(countTime-runTime)%60%10;//练习模式下的秒位
					}
				
				
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			
			
			
			if(runTime>=countTime)
			{
				gameview.heroislive=false;
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				gameview.DRAW_THREAD_FLAG=false;
				gameview.activity.hd.sendEmptyMessage(2);
			}
		}
	}


}
