package com.example.mytableball2;

import com.example.uti.Constant;

public class PhysicsThread extends Thread{
GameView gameview;
public PhysicsThread(GameView gameview)
{
	super();
    this.gameview=gameview;	
}
public void run(){
	
	while(gameview.heroislive)
	{
		if(!gameview.isGamePause)
		{
			gameview.world.setGravity(Constant.GRAVITYTEMP);
			gameview.world.step(Constant.TIME_STEP, Constant.ITERA);//Ä£ÄâÆµÂÊ
		}
		try {
			Thread.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

}
