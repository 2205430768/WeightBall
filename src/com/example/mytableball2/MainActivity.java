package com.example.mytableball2;

import java.util.HashMap;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import org.jbox2d.common.Vec2;

import com.example.uti.Constant;
import com.example.uti.DBUtil;
import com.example.uti.SoundUtil;
enum WhichView{WELCOME_VIEW,GAME_VIEW,MAIN_MENU_VIEW,GOTO_NEXT_VIEW,After_Play_View,
	After_Settings_View,After_History_View,After_Play_Choose_View}
public class MainActivity extends Activity {
	WhichView curr;//��ǰ����
	SharedPreferences.Editor editor;//��SharePreferences��д����
	GameView gv;//��Ϸ����
	NextView gotoNextView;//��ת����
	
	PatternChooseView apv;//ģʽѡ�����
	SettingsView asv;//���ý���
	HistoryView ahv;//��ʷ��¼����
	LevelChooseView apcv;//ѡ�ؽ���
	WelcomeView wcv;
	Vibrator vibrator;//��
	AudioManager audio;
	public Handler hd=new Handler(){
	
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
			case 0:
				gotoGameView();		//ȥgameview			
				break;
			case 1:
				gotoMainMenuView();//ȥ������
				break;
			case 2:
				gotoNextView();//ȥ��ת����
				break;
			case 3:
				gotoPatternChooseView();//ȥģʽѡ�����
				break;
				case 4:
				gotoSettingsView();//ȥ���ý���
				break;
			case 5:
				gotoHistoryView();//ȥ��ʷ��¼����
				break;	
			case 6:
				gotoLevelChooseView();//ȥѡ�ؽ���
				break;
			case 7:
				gotoWelcomeView();
     			break;
			}

		}
		
		
	};
	MainMenuView mmv;//���˵�����
	HashMap<Integer,Integer> soundPoolMap;
	public SoundUtil soundutil;//������
	SharedPreferences sp;//��ȡSharedPreferences
	SensorManager mySensorManager;
	Sensor accelerometerSensor;
	private SensorEventListener  mySensorListener=new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			float[] values=event.values;
			//�������������Ļ��ͶӰ����
			//Log.i("����","value[1]="+values[1]+",value[0]"+values[0]);
		Constant.GRAVITYTEMP=new Vec2(Constant.GRAVITY*values[1],Constant.GRAVITY*values[0]);
		if(gv!=null){
		gv.ballActivate();
		}
				}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			 //����Ϊȫ��
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,   
	        WindowManager.LayoutParams. FLAG_FULLSCREEN); 
	        //����Ϊ����
	    	setVolumeControlStream(AudioManager.STREAM_MUSIC);//��������
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        audio=(AudioManager)getSystemService(Service.AUDIO_SERVICE);
	        //�����Ļ�ߴ�
	        DisplayMetrics  dm=new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(dm);
	        if(dm.widthPixels>dm.heightPixels)
	        {
	        	Constant.SCREEN_WIDTH=dm.widthPixels;
	        	Constant.SCREEN_HEIGHT=dm.heightPixels;
	        }
	        else
	        {
	        	Constant.SCREEN_WIDTH=dm.heightPixels;
	        	Constant.SCREEN_HEIGHT=dm.widthPixels;
	        }
	        Constant.scaleCL();
	        Constant.initDB();
	        new Thread(){
	        	public void run(){
	        		Constant.initBitmap(getResources());
	        		Constant.LOAD_FINISH=true;
	        	}
	        }.start();
	 
	         
	         soundutil=new SoundUtil(this);
	         soundutil.initSounds(); 
	         mySensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
	         accelerometerSensor=mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	         vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
	         Constant.from_nextview=true;
	         hd.sendEmptyMessage(7);

	        
	        
		}

protected void gotoNextView() {
	this.gv.heroislive=false;
	this.gv.DRAW_THREAD_FLAG=false;
	curr=WhichView.GOTO_NEXT_VIEW;
	gotoNextView=new NextView(this);   
	setContentView(gotoNextView);
			
		}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
switch(keyCode)
{
case KeyEvent.KEYCODE_VOLUME_UP:
	audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, 
			AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_SHOW_UI);
	return true;
case KeyEvent.KEYCODE_VOLUME_DOWN:
	audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,
			AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_SHOW_UI);
	return true;
	case 4:
	if(curr==WhichView.MAIN_MENU_VIEW)
	{
		int a,b,c;
		if(Constant.YINYUE_CLOSE==true)
		{
			a=2;
		}
		else
		{
			a=1;
		}
		if(Constant.YINXIAO_OPEN==true)
		{
			b=1;
		}
		else
		{
			b=2;
		}
		if(Constant.ZHENDONG_OPEN==true)
		{
			c=1;
		}
		else
		{
			c=2;
		}
		DBUtil.updateSetting(a, b, c);
		System.exit(0);
	}
	else if(curr==WhichView.GAME_VIEW)
	{
		hd.sendEmptyMessage(2);
	}
	else if(curr==WhichView.GOTO_NEXT_VIEW)
	{
		Constant.from_nextview=true;
		hd.sendEmptyMessage(1);
	}
	else if(curr==WhichView.After_History_View||curr==WhichView.After_Settings_View
			||curr==WhichView.After_Play_View)
	{
		hd.sendEmptyMessage(1);
	}
	else if(curr==WhichView.After_Play_Choose_View)
	{
		hd.sendEmptyMessage(3);
	}
	return true;
	default:
		break;
}

	return super.onKeyDown(keyCode, event);
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	mySensorManager.unregisterListener(mySensorListener);
}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	mySensorManager.registerListener
	(
			mySensorListener, 
			accelerometerSensor, 
			SensorManager.SENSOR_DELAY_GAME
	);
}
protected void gotoGameView() {
	//���߳�
	//Constant.DRAW_THREAD_FLAG=true;
	curr=WhichView.GAME_VIEW; 
	gv=new GameView(this);   
    setContentView(gv);
			
		}
protected void gotoLevelChooseView() {
	curr=WhichView.WELCOME_VIEW;
	apcv=new LevelChooseView(this);   
	setContentView(apcv);
		}
protected void gotoHistoryView() {
			// TODO Auto-generated method stub
	curr=WhichView.After_History_View;
	ahv=new HistoryView(this);   
	setContentView(ahv);
		}
protected void gotoSettingsView() {
	curr=WhichView.After_Settings_View;
	asv=new SettingsView(this);   
	setContentView(asv);
			
		}
//		  //���밴�¿�ʼ��Ϸ��ť��Ľ���
protected void gotoPatternChooseView() {
		curr=WhichView.After_Play_View;
		apv=new PatternChooseView(this); 
			setContentView(apv);
		}
//		 //���뿪ʼ����
protected void gotoMainMenuView() {
			curr=WhichView.MAIN_MENU_VIEW;
			mmv=new MainMenuView(this);
			setContentView(mmv);
			
		}

//		//����ѡ�ؽ���
public void gotoWelcomeView() {
			// TODO Auto-generated method stub
			curr=WhichView.After_Play_Choose_View;
			wcv=new WelcomeView(this);
			setContentView(wcv);
		}

		
	}