package com.example.model;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import com.example.mytableball2.GameView;
import com.example.uti.Constant;

import android.graphics.Bitmap;

public class Box2DUtil {
	//������ҵ���  
	public static Ball createBall(World world,float x,float y,float radius,boolean isStatic,Bitmap bitmap,GameView gameview)
	{
		CircleDef  ballshapdef=new CircleDef();
		if(isStatic)
		{
			ballshapdef.density=0f;
		}
		else
		{
			ballshapdef.density=1.0f;
		}
		ballshapdef.friction=0f;
		ballshapdef.restitution=Constant.ball_restitution;
		ballshapdef.radius=radius-Constant.BODYOFF;
		BodyDef  bodydef=new BodyDef();
		Vec2 v=new Vec2(x,y);
		bodydef.position.set(v);
		Body body=world.createBody(bodydef);
		body.createShape(ballshapdef);
		body.setMassFromShapes();
		return new Ball(body,bitmap,gameview);
	}

//	//����FalshHole�������һ�������������೤ʱ�������һ��
	public static FalshHole createFlashHole(World world,float x,float y,float radius,
			boolean isStatic,Bitmap bitmap,int timeDeadSpan,int timeLiveSpan,GameView gameview)
	{
		CircleDef  ballshapdef=new CircleDef();
		if(isStatic)
		{
			ballshapdef.density=0f;
		}
		else
		{
			ballshapdef.density=1.0f;
		}
		ballshapdef.friction=0f;
		ballshapdef.restitution=Constant.ball_restitution;
		ballshapdef.radius=radius-Constant.BODYOFF;
		
		ballshapdef.isSensor=true;//��������Դ���
		BodyDef  bodydef=new BodyDef();
		Vec2 v=new Vec2(x, y);//Բ������
		bodydef.position.set(v);
		
		Body body=world.createBody(bodydef);
		body.createShape(ballshapdef);
		body.setMassFromShapes();
		return new FalshHole(body,bitmap,timeDeadSpan,timeLiveSpan,gameview);
		
	}
//����һ�����εĸ���   Rec extends MyBody
	public static Rec creatRec(World world,float x,float y,float halfweight,
			float halfHeight,boolean isStatic,Bitmap bitmap,GameView gameview)
	{
		PolygonDef recshap=new PolygonDef();//����� BODYOFF=12;//�������ӦͼƬ�Ĳ�ֵ
		recshap.setAsBox(halfweight+Constant.BODYOFF, halfHeight+Constant.BODYOFF);//����
		if(isStatic)
		{
			recshap.density=0f;
		}
		else
		{
			recshap.density=1.0f;
		}
		recshap.friction=0f;
		recshap.restitution=0.2f;
		BodyDef  bodydef=new BodyDef();
		Vec2 v=new Vec2(x,y);
		bodydef.position.set(v);
		//����һ������Ҫ��ĸ���
		Body  body=world.createBody(bodydef);
		body.createShape(recshap);
		body.setMassFromShapes();
		return new Rec(body,bitmap,gameview);
			}
//����һ����ɫ�Ķ�
	public static Hole createHole(World world,float x,float y,float radius,boolean isStatic,Bitmap  bitmap,GameView gameView)
	{
		CircleDef ballshapef=new CircleDef();
		if(isStatic)
		{
			ballshapef.density=0;
		}
		else
		{
			ballshapef.density=1.0f;	
		}
		ballshapef.friction=0f;
		ballshapef.restitution=Constant.ball_restitution;//��������
	//	ballshapef.radius=radius-Constant.BODYOFF;//����İ뾶
		ballshapef.radius=radius-20;//����İ뾶
		BodyDef bodydef=new BodyDef();
		Vec2 v=new Vec2(x,y);
		bodydef.position.set(v);
		
		Body body=world.createBody(bodydef);
		body.createShape(ballshapef);
		//����һ����ĳ��ٶ�
		body.setMassFromShapes();
		return new Hole(body,bitmap,gameView);
	}
	

}
