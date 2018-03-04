package com.example.mytableball2;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.util.Log;

import com.example.model.FalshHole;
import com.example.model.MyBody;
import com.example.uti.Constant;
import com.example.uti.DBUtil;

import static  com.example.uti.Constant.*;

public class CollisionAction {

public static void doAction(GameView  gameview,Body  body1,Body body2,float x,float y,Vec2 angle ,Vec2 velocity)
{
	//������ʱ���ǶԷ�herohall���жϱ�������Ϊÿ����ײ������hero ball����ģ�����ÿ������ײ��ʱ������heroball�����
	//Ӧ���ǶԷ�heroball�Ķ���
	
	if(!gameview.heroislive)
	return;
	for(MyBody mytempbody:gameview.reclist)//��ײǽ�����ӣ��ļ���
	{
		if(mytempbody.body==body1||mytempbody.body==body2)
		{//dot(angle, velocity)�����ĵ�ˣ��������������������ֵ
		  float  jiaoduYZ=Vec2.dot(angle, velocity)/(angle.length()*velocity.length());
		  if(Constant.YINXIAO_OPEN&&//��Ч��
				  velocity.length()>Constant.COLLISIONVELOCITY&&//����ײ�ٶ�����
					Math.abs(jiaoduYZ)>0.717//����ײ�Ƕ�����	
				  )
		  {
				//Log.i("�ƶ��Ƕ�","angle.x="+angle.x+",  angle.y="+angle.y);
				//Log.i("�ƶ��Ƕ�","velocity.x="+velocity.x+",  velocity.y="+velocity.y);
			 gameview.activity.soundutil.playEffectsSound(1, 0);//����ײǽ������ 
		  }
		  return;
		  
		}
	}
	for(MyBody mytempbody:gameview.holelist)//�ж�С���Ƿ��붴��ײ
	{
		/*if(mytempbody.body==body1||mytempbody.body==body2)
		{//���Ϊ0��
			Constant.ROTEANGLE=(float)Math.toDegrees(Math.atan2(angle.y, angle.x));
			if(ZHENDONG_OPEN)
			{
				gameview.activity.vibrator.vibrate(Constant.COLLISION_SOUND_PATTERN,-1);
			}
			if(YINXIAO_OPEN)
			{
				gameview.activity.soundutil.playEffectsSound(0, 0);//���Ž�������
			}
			gameview.heroislive=false;//�ᶯ��С��ֹͣ
			mytempbody.doAction();
			gameview.herolist.get(1).bitmap=null;//��С����ʧ
			//gameview����,��ͣ1�����nextview����
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			gameview.DRAW_THREAD_FLAG=false;//�����߳�ֹͣ
			gameview.activity.hd.sendEmptyMessage(2);
		}*/
	}
	for(FalshHole mytempbody:gameview.flashholelist)
	{
		//�Ի����Ķ������ײ����
		if(mytempbody.isFlashDead&&(mytempbody.body==body1||mytempbody.body==body2))
		{
			Constant.ROTEANGLE=(float)Math.toDegrees(Math.atan2(angle.y, angle.x));
			if(ZHENDONG_OPEN)
			{
				gameview.activity.vibrator.vibrate(Constant.COLLISION_SOUND_PATTERN,-1);//��
			}
			
			if(YINXIAO_OPEN)
			{
				gameview.activity.soundutil.playEffectsSound(0, 0);//���Ž�����
			}
			gameview.heroislive=false;//�ᶯС��ֹͣ
			mytempbody.doAction();//ִ�ж�������
			gameview.herolist.get(1).bitmap=null;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			gameview.DRAW_THREAD_FLAG=false;//gameview �����߳�ֹͣ
			//gameview���ܣ���ͣ1�����nextview����
			gameview.activity.hd.sendEmptyMessage(2);
			return;
		}
	}
	if(gameview.herolist.get(0).body==body1||gameview.herolist.get(0).body==body2)
	{
		if(YINXIAO_OPEN)
		{
			gameview.activity.soundutil.playEffectsSound(2, 0);
			
		}
		gameview.heroislive=false;
		gameview.activity.vibrator.vibrate(COLLISION_SOUND_PATTERN,-1);
		gameview.herolist.get(0).doAction();
		gameview.herolist.get(1).bitmap=null;
		//ʱ�����һ���õ�ʱ���õ��٣������ǵ�һ����
		if(gameview.playtime.getRunTime()<DBUtil.getTimeplay(PLAY_MODEL, LEVEL)||
				DBUtil.getTimeplay(PLAY_MODEL, LEVEL)==0)
		{
			DBUtil.upDateTime(PLAY_MODEL, LEVEL, gameview.playtime.getRunTime());
		}
		if(LEVEL!=5&&DBUtil.getLock(PLAY_MODEL, LEVEL+1)==0)
		{
			DBUtil.insert(PLAY_MODEL, LEVEL+1,0 ,1);
		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		gameview.DRAW_THREAD_FLAG=false;
		gameview.activity.hd.sendEmptyMessage(2);
		return;
	}
	
}

}
