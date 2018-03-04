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
	//遍历的时候是对非herohall的判断遍历，因为每次碰撞都是有hero ball参与的，所以每次有碰撞的时候都是有heroball参与的
	//应该是对非heroball的东西
	
	if(!gameview.heroislive)
	return;
	for(MyBody mytempbody:gameview.reclist)//对撞墙（箱子）的监听
	{
		if(mytempbody.body==body1||mytempbody.body==body2)
		{//dot(angle, velocity)向量的点乘，求出的是两向量的余弦值
		  float  jiaoduYZ=Vec2.dot(angle, velocity)/(angle.length()*velocity.length());
		  if(Constant.YINXIAO_OPEN&&//音效打开
				  velocity.length()>Constant.COLLISIONVELOCITY&&//对碰撞速度限制
					Math.abs(jiaoduYZ)>0.717//对碰撞角度限制	
				  )
		  {
				//Log.i("移动角度","angle.x="+angle.x+",  angle.y="+angle.y);
				//Log.i("移动角度","velocity.x="+velocity.x+",  velocity.y="+velocity.y);
			 gameview.activity.soundutil.playEffectsSound(1, 0);//播放撞墙的声音 
		  }
		  return;
		  
		}
	}
	for(MyBody mytempbody:gameview.holelist)//判断小球是否与洞相撞
	{
		/*if(mytempbody.body==body1||mytempbody.body==body2)
		{//左边为0度
			Constant.ROTEANGLE=(float)Math.toDegrees(Math.atan2(angle.y, angle.x));
			if(ZHENDONG_OPEN)
			{
				gameview.activity.vibrator.vibrate(Constant.COLLISION_SOUND_PATTERN,-1);
			}
			if(YINXIAO_OPEN)
			{
				gameview.activity.soundutil.playEffectsSound(0, 0);//播放进洞声音
			}
			gameview.heroislive=false;//会动的小球停止
			mytempbody.doAction();
			gameview.herolist.get(1).bitmap=null;//让小球消失
			//gameview接受,暂停1秒进入nextview界面
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			gameview.DRAW_THREAD_FLAG=false;//画的线程停止
			gameview.activity.hd.sendEmptyMessage(2);
		}*/
	}
	for(FalshHole mytempbody:gameview.flashholelist)
	{
		//对会闪的洞添加碰撞监听
		if(mytempbody.isFlashDead&&(mytempbody.body==body1||mytempbody.body==body2))
		{
			Constant.ROTEANGLE=(float)Math.toDegrees(Math.atan2(angle.y, angle.x));
			if(ZHENDONG_OPEN)
			{
				gameview.activity.vibrator.vibrate(Constant.COLLISION_SOUND_PATTERN,-1);//震动
			}
			
			if(YINXIAO_OPEN)
			{
				gameview.activity.soundutil.playEffectsSound(0, 0);//播放进洞音
			}
			gameview.heroislive=false;//会动小球停止
			mytempbody.doAction();//执行动画方法
			gameview.herolist.get(1).bitmap=null;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			gameview.DRAW_THREAD_FLAG=false;//gameview 画的线程停止
			//gameview接受，暂停1秒进入nextview界面
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
		//时间比上一次用的时间用的少，或者是第一次玩
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
