package com.example.uti;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;

import com.example.mytableball2.MainActivity;
import com.example.mytableball2.R;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundUtil {
	public MediaPlayer mp;
	SoundPool  soundPool;//声音池
	HashMap<Integer,Integer> soundPoolMap;
	MainActivity  tableBallActivity;
	public SoundUtil(MainActivity activity){
		this.tableBallActivity=activity;
	}
//	//声音缓冲池的初始化
	public void initSounds(){
		//创建声音缓冲池
		soundPool=new SoundPool(6, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap=new HashMap<Integer,Integer>();
		//将加载的声音资源的id放进此Map
	    soundPoolMap.put(0, soundPool.load(tableBallActivity, R.raw.hole_sounds, 1));//进洞
	    soundPoolMap.put(1, soundPool.load(tableBallActivity, R.raw.wall_sound, 1));//撞木头
	    soundPoolMap.put(2, soundPool.load(tableBallActivity, R.raw.ping_sounds, 1));//过关
	}
	public void playEffectsSound(int sound,int loop)
	{
		
	
			AudioManager mgr=(AudioManager)tableBallActivity.getSystemService(Context.AUDIO_SERVICE);
			float streamVolumeCurrent=mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
			float streamVolumeMax=mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float  volume=streamVolumeCurrent/streamVolumeMax;
			soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
		
	}
public void stop_bg_sound()
{
   if(mp!=null)
   {
	   mp.stop();
	   mp.release();
	  
   }
   if(!Constant.YINYUE_CLOSE)
   {
	   Constant.YINYUE_CLOSE=true; 
   }
}
public void play_bg_sound()
{
   if(Constant.YINYUE_CLOSE)
   {
	   AssetManager  assetManager=tableBallActivity.getAssets();
	   try {
		mp=new MediaPlayer();
		String s;
		if(Constant.YINYUE1)
		{
			s="main.mp3";
		}
		else
		{
			s="game.mp3";
		}
		AssetFileDescriptor  fileDescriptor=assetManager.openFd(s);
		mp.setDataSource(fileDescriptor.getFileDescriptor(),
		fileDescriptor.getStartOffset(),
		fileDescriptor.getLength());
		mp.setLooping(true);
		mp.prepare();
		mp.start();
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	   Constant.YINYUE_CLOSE=false;
   }
}





}
