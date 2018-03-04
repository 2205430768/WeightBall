package com.example.model;

import com.example.mytableball2.GameView;

public class PlayTimeCount1 extends PlayTime1{
	int countTime=1*60;//��ʱ��ʱ�䣬��1*60,1�Ƿ֣�60����
	
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
					index_m0=((countTime-runTime)/60)%60/10;//��ϰģʽ�µ�10��λ
					index_m1=((countTime-runTime)/60)%60%10;//��ϰģʽ�µķ�λ
					index_s0=(countTime-runTime)%60/10;
					index_s1=(countTime-runTime)%60%10;//��ϰģʽ�µ���λ
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
