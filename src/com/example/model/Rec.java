package com.example.model;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.mytableball2.GameView;

public class Rec extends MyBody{

	public Rec(Body body, Bitmap bitmap, GameView gameView) {
		super(body, bitmap, gameView);
		// TODO Auto-generated constructor stub
	}
	//��������������demo�еĹ�����һ��
		Rec(Body body, float width,float height,Bitmap bitmap) 
		{
			super(body,width,height,bitmap);
		}
		
		public  void drawself(Canvas canvas,Paint paint)
		{
			super.drawself(canvas, paint);
		}
		
		public void doAction()
		{
			
		}
}
