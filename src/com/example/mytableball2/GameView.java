package com.example.mytableball2;

import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.collision.AABB;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

import com.example.model.Ball;
import com.example.model.Box2DUtil;
import com.example.model.FalshHole;
import com.example.model.MyBody;
import com.example.model.PlayTime1;
import com.example.model.PlayTimeCount1;
import com.example.model.PlayTimeExercise1;
import com.example.uti.Constant;
import static com.example.uti.Constant.*;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
public MainActivity activity;
Paint paint;
World world;
AABB aabb;
Ball myball;//玩家的
DrawThread drawthread;//画的线程
PhysicsThread physicsthread; //模拟的线程
public ArrayList<MyBody> holelist;//洞的body列表,进行碰撞的处理
public ArrayList<MyBody> reclist;//挡板列表，不执行碰撞的处理
public ArrayList<MyBody> herolist;//球和过关点
public ArrayList<FalshHole> flashholelist;
PlayTime1 playtime;

//上次触控的位置
private float  previousX;
private float previousY;
private float angdegElevation=90;//仰角
private float angdegAzimuth=90;//方位角
private final float TOUCH_SCALE_FACTOR=180.0f/400;

public boolean isGamePause=false;
public boolean heroislive=false;//会动的球的初始值
public boolean DRAW_THREAD_FLAG=true;//绘制线程标志位

static int z=0;

	public GameView(MainActivity activity) {
		super(activity);
		this.activity=activity;
		getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);
		holelist=new ArrayList<MyBody>();
		reclist=new ArrayList<MyBody>();
		herolist=new ArrayList<MyBody>();
		flashholelist=new ArrayList<FalshHole>();
		
		loadGameData();
       initContactListener();
		z++;
		physicsthread=new PhysicsThread(this);
		drawthread=new DrawThread(this);
		drawthread.start();
	    physicsthread.start();

	}

	private void initContactListener() {
		ContactListener contactlistener=new ContactListener() {
			
			@Override
			public void result(ContactResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void remove(ContactPoint arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void persist(ContactPoint arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void add(ContactPoint arg0) {
				// TODO Auto-generated method stub
				CollisionAction.doAction(GameView.this, arg0.shape1.getBody(), arg0.shape2.getBody(),
						arg0.position.x, arg0.position.y, arg0.normal,arg0.velocity);
			}
		};
 	world.setContactListener(contactlistener);
	}

	private void loadGameData() {
		heroislive=true;//初始化heroball的状态
		DRAW_THREAD_FLAG=true;
		isGamePause=false;
		aabb=new AABB();
		aabb.lowerBound.set(-100.0f,-100.0f);
		aabb.upperBound.set(1000.0f, 1000.0f);
		boolean doSleep=true;
		world=new World(aabb, Constant.GRAVITYTEMP, doSleep);
		if(LEVEL!=TEMPLEVEL)
		{
			Constant.initBoxBitmap1(getResources());
		}
		TEMPLEVEL=LEVEL;
	//绘制洞口  加载洞口数据
     for(int i=0;i<bhoffset[LEVEL].length;i++)
{
 	holelist.add(Box2DUtil.createHole(world, Constant.bhoffset[Constant.LEVEL][i][0],
 	Constant.bhoffset[Constant.LEVEL][i][1], Constant.R, true,Constant.TP_ARRAY[5],this));
 	
}
  //box刚体参数   World world,float x,float y,float halfweight,
  //float halfHeight,boolean isStatic,Bitmap bitmap,GameView gameview
      for(int i=0;i<xyScale[Constant.LEVEL].length;i++)
{
    reclist.add(Box2DUtil.creatRec(world, xycenter[LEVEL][i][0],xycenter[LEVEL][i][1],
   xyScale[LEVEL][i][0]*50, xyScale[LEVEL][i][1]*50, true, BOX_TP_ARRAY1[i], this));	
 }
		//添加会闪的洞
     Random random=new Random();
     for(int i=0;i<flashHolePositon[LEVEL].length;i++)
{
	flashholelist.add(Box2DUtil.createFlashHole(world, flashHolePositon[LEVEL][i][0], flashHolePositon[LEVEL][i][1],
			R, true, TP_FLASHHOLE[1][0], 1+random.nextInt(10),1+random.nextInt(10),this));
}

//		//绘制heroball，它是最后加的，为了保证对其进行处理是直接从列表中找	
//		//目标球，他放在herolist的第一个个位置
     myball=Box2DUtil.createHole(world,ballendplace[LEVEL][0], ballendplace[LEVEL][1],
		R, true,TP_ARRAY[10], this);
    herolist.add(myball);
    myball=Box2DUtil.createBall(world, ballstartplace[LEVEL][0],ballstartplace[LEVEL][1], R, false, TP_ARRAY[6], this);
    herolist.add(myball);		
//		//加载计时器
   if(PLAY_MODEL==Constant.PLAY_MODEL1)
  {
   playtime=new PlayTimeCount1(this);	
  }
   else
  {
  playtime=new PlayTimeExercise1(this);	
  }

	}
  public void onDraw(Canvas canvas)
  {
   super.onDraw(canvas);
  if(canvas==null)
  {
	return;
  }

canvas.save();
canvas.translate(screenScaleResult.lucX, screenScaleResult.lucY);
canvas.scale(screenScaleResult.ratio, screenScaleResult.ratio);
canvas.drawBitmap(Constant.TP_ARRAY[3], 0.0f,0f, paint);
for(MyBody mytempbody:holelist)
{
  mytempbody.drawself(canvas, paint);	
}
for(MyBody mytempbody:flashholelist)
{
 	mytempbody.drawself(canvas, paint);
}

for(MyBody mybodytemp:reclist)
{
	mybodytemp.drawself(canvas, paint);
}
for(MyBody mybodytemp:herolist)
{
	mybodytemp.drawself(canvas, paint);
}
//画边框
canvas.drawBitmap(TP_ARRAY[40], 0, 0, paint);
canvas.drawBitmap(TP_ARRAY[41], 0, 457, paint);
canvas.drawBitmap(TP_ARRAY[42], 0, 0, paint);
canvas.drawBitmap(TP_ARRAY[43], 779, 3, paint);
playtime.drawself(canvas, paint);//画时间
if(isGamePause)
{
canvas.drawBitmap(TP_ARRAY[30], 1, 1,paint);
}
else
{
	canvas.drawBitmap(Constant.TP_ARRAY[36], 1, 1, paint);
}
canvas.restore();



}

@Override
public boolean onTouchEvent(MotionEvent e) {
	int currentNum=e.getAction();
	float x=e.getX();
	float y=e.getY();
	switch(currentNum)
	{
	case MotionEvent.ACTION_DOWN:
		//添加暂停按钮的监听
		if(x>xyoffsetEvent[30][0]&&x<xyoffsetEvent[30][0]+TP_ARRAY[19].getWidth()*screenScaleResult.ratio&&
				y>xyoffsetEvent[30][1]&&y<xyoffsetEvent[30][1]+TP_ARRAY[19].getHeight()*screenScaleResult.ratio)
		
		{
			isGamePause=!isGamePause;
			repaint();
		}
		break;
	case MotionEvent.ACTION_MOVE:
		float dy=y-previousY;
		float dx=x-previousX;
		angdegAzimuth+=dx*TOUCH_SCALE_FACTOR;//TOUCH_SCALE_FACTOR=180.0f/400;
		angdegElevation+=dy*TOUCH_SCALE_FACTOR;
		if(angdegElevation>90)
			angdegElevation=90;
		else if(angdegElevation<0)
		{
			angdegElevation=0;
		}
		
	}
	previousY=y;
	previousX=x;
	


return true;
	
}

public void ballActivate()
{
	myball.body.wakeUp();
}
	@SuppressLint("WrongCall")
	public void repaint(){
		SurfaceHolder holder=this.getHolder();
		Canvas canvas=holder.lockCanvas();
		try {
			synchronized (holder) {
			onDraw(canvas);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if(Constant.bg_music_sound)
		{
			Constant.YINYUE1=false;
			this.activity.soundutil.stop_bg_sound();
			this.activity.soundutil.play_bg_sound();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
