package com.example.uti;

public class ScreenScaleUtil {
static final float sHpWidth=800;//原始横屏宽度
static final float sHpHeight=480;//原始横屏的高度
static final float whHpRatio=sHpWidth/sHpHeight;//原始横屏宽高比

static final float sSpWidth=480;//原始竖屏的宽度
static final float sSpHeight=800;//原始竖屏的高度
static final float whSpRatio=sSpWidth/sSpHeight;//原始竖屏的宽高比


public static ScreenScaleResult calScale(float targetWidth,float targetHeight)
{
   ScreenScaleResult result=null;
   ScreenOrien so=null;
   //判断是横屏还是竖屏
   if(targetWidth>targetHeight)
   {
	   so=ScreenOrien.HP;
   }
   else
   {
	   so=ScreenOrien.SP;
   }
   //进行横屏结果的计算
   if(so==ScreenOrien.HP)
   {
	   //计算目标的宽高比
	   float targetRadio=targetWidth/targetHeight;
	   if(targetRadio>whHpRatio)
	   {
		   float ratio=targetHeight/sHpHeight;
		   float realTargetWidth=sHpWidth*ratio;
		   float lucX=(targetWidth-realTargetWidth)/2.0f;
		   float lucY=0;
		   result=new  ScreenScaleResult((int)lucX,(int)lucY,ratio,so);
	   }
	   else {
		   float  ratio=targetWidth/sHpWidth;
		   float  realTargetHeight=sHpHeight*ratio;
		   float lucX=0;
		   float lucY=(targetHeight-realTargetHeight)/2.0f;
		   result=new ScreenScaleResult((int)lucX, (int)lucY, ratio, so);
	   }
   }
   //进行竖屏结果的计算
   if(so==ScreenOrien.SP)
   {
	   float targetRatio=targetWidth/targetHeight;
	   if(targetRatio>whSpRatio)
	   {
		   float ratio=targetHeight/sSpHeight;
		   float realTargetWidth=sSpWidth*ratio;
		   float lucX=(targetWidth-realTargetWidth)/2.0f;
		   float lucY=0;
		   result=new ScreenScaleResult((int)lucX, (int)lucY, ratio, so);
	   }
	   else
	   {
		   float ratio=targetWidth/sSpWidth;
		   float realTargetHeight=sSpHeight*ratio;
		   float lucX=0;
		   float lucY=(targetHeight-realTargetHeight)/2.0f;
		   result=new ScreenScaleResult((int)lucX, (int)lucY, ratio, so);	   
	   }
   }
   return result;
   
}
	
}


