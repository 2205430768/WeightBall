package com.example.mytableball2;

public class DrawThread extends Thread{
	GameView gameview;
	public DrawThread(GameView gameview)
	{
		this.gameview=gameview;
	}
	public void run(){
     while(gameview.DRAW_THREAD_FLAG)
     {
    	 if(!gameview.isGamePause)
    	 {
    		 gameview.repaint();
    	 }
    	 
    	 
    	 try {
			Thread.sleep(15);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 
    	 
     }
     gameview.DRAW_THREAD_FLAG=true;

	}

}
