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
	//创建玩家的球  
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

//	//创建FalshHole对象，最后一个参数是它隔多长时间进行闪一下
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
		
		ballshapdef.isSensor=true;//这样球可以穿过
		BodyDef  bodydef=new BodyDef();
		Vec2 v=new Vec2(x, y);//圆心坐标
		bodydef.position.set(v);
		
		Body body=world.createBody(bodydef);
		body.createShape(ballshapdef);
		body.setMassFromShapes();
		return new FalshHole(body,bitmap,timeDeadSpan,timeLiveSpan,gameview);
		
	}
//创建一个矩形的刚体   Rec extends MyBody
	public static Rec creatRec(World world,float x,float y,float halfweight,
			float halfHeight,boolean isStatic,Bitmap bitmap,GameView gameview)
	{
		PolygonDef recshap=new PolygonDef();//多边形 BODYOFF=12;//刚体与对应图片的差值
		recshap.setAsBox(halfweight+Constant.BODYOFF, halfHeight+Constant.BODYOFF);//矩形
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
		//创建一个符合要求的刚体
		Body  body=world.createBody(bodydef);
		body.createShape(recshap);
		body.setMassFromShapes();
		return new Rec(body,bitmap,gameview);
			}
//创建一个黑色的洞
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
		ballshapef.restitution=Constant.ball_restitution;//反弹补偿
	//	ballshapef.radius=radius-Constant.BODYOFF;//刚体的半径
		ballshapef.radius=radius-20;//刚体的半径
		BodyDef bodydef=new BodyDef();
		Vec2 v=new Vec2(x,y);
		bodydef.position.set(v);
		
		Body body=world.createBody(bodydef);
		body.createShape(ballshapef);
		//设了一下球的初速度
		body.setMassFromShapes();
		return new Hole(body,bitmap,gameView);
	}
	

}
