package com.example.model;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.mytableball2.GameView;

public abstract class MyBody {
public Bitmap bitmap;
public Body body;
GameView gameView;
float width;//Ŀ����
float height;//Ŀ��߶�
float x;
float y;
float angle;
public MyBody(Body body,Bitmap bitmap ,GameView gameView)
{
	this.body=body;
    this.bitmap=bitmap;
    this.gameView=gameView;
    }

//��������������demo�еĹ�����һ��
	public MyBody(Body body, float width,float height,Bitmap bitmap) 
	{
		this.body = body;
		this.bitmap = bitmap;
		this.height=height;
		this.width=width;
	}
	
	
	
	
//	//���ݴ����ͼƬ���滭�����Լ�����
	public void drawself(Canvas canvas,Paint paint)
	{
		x=body.getPosition().x;
		y=body.getPosition().y;
		if(bitmap==null)
		{
			return;
		}
		
		canvas.save();
		Matrix m3=new Matrix();
		m3.setTranslate(x-bitmap.getWidth()/2, y-bitmap.getHeight()/2);
		canvas.drawBitmap(bitmap, m3, paint);
		canvas.restore();
		
	}
	public void doAction()
	{
		
	}

}
