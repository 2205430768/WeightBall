package com.example.model;

import org.jbox2d.dynamics.Body;
import android.graphics.Bitmap;
import static com.example.uti.Constant.*;
import com.example.mytableball2.GameView;
import com.example.uti.Constant;

public class FalshHole extends Hole{
	int timeDeadSpan;//���պϣ��������õ�ʱ�䣬��λΪ��
	int timeLiveSpan;//���򿪣������õ�ʱ�䣬��λΪ��
	public boolean isFlashDead=true;//�ǲ��ǿ������Ķ�
	FalshHole(Body body,Bitmap bitmap,int timeDeadSpan,int timeLiveSpan,GameView gameview)
	{
		super(body,bitmap,gameview);
		this.timeDeadSpan=timeDeadSpan;
		this.timeLiveSpan=timeLiveSpan;
		//����һ����ͼ��������ײ��־λ���߳�
		FlashThread flashThread=new FlashThread();
		flashThread.start();
	}
//	//��ͼ��������ײ��־λ���߳�
	private class FlashThread extends Thread{
		
		public void run(){
			while(gameView.heroislive)
			{
				//û����ͣ
				if(!gameView.isGamePause)
				{
					isFlashDead=true;
					//���л�ͼ
					for(int i=1;i<TRUE_FLASHHOLE.length*2-1;i++)
					{
						//��ͣ
					while(gameView.isGamePause){try {Thread.sleep(1000);} catch (Exception e) {e.printStackTrace();}}
					//��ͼ
					bitmap=Constant.TRUE_FLASHHOLE[Math.abs(Constant.TP_FLASHHOLE.length-1-i)];
					
				try {Thread.sleep(timeDeadSpan*1000/(Constant.TRUE_FLASHHOLE.length*2));//���ú����˯��ʱ��
					} catch (InterruptedException e) {e.printStackTrace();}
				
					}
					
					
					isFlashDead=false;
					bitmap=Constant.TRUE_FLASHHOLE[13];//����ͼ
					//���������仯
					try {Thread.sleep(timeLiveSpan*1000);//˯��
					} catch (InterruptedException e) {e.printStackTrace();}
					
					
				}
				
				
				
				else if(gameView.isGamePause)//��������ͣ��ť��ʱ�����˯��
					{
						try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
					}
			}
			
		}
	}


}
