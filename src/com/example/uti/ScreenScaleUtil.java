package com.example.uti;

public class ScreenScaleUtil {
static final float sHpWidth=800;//ԭʼ�������
static final float sHpHeight=480;//ԭʼ�����ĸ߶�
static final float whHpRatio=sHpWidth/sHpHeight;//ԭʼ������߱�

static final float sSpWidth=480;//ԭʼ�����Ŀ��
static final float sSpHeight=800;//ԭʼ�����ĸ߶�
static final float whSpRatio=sSpWidth/sSpHeight;//ԭʼ�����Ŀ�߱�


public static ScreenScaleResult calScale(float targetWidth,float targetHeight)
{
   ScreenScaleResult result=null;
   ScreenOrien so=null;
   //�ж��Ǻ�����������
   if(targetWidth>targetHeight)
   {
	   so=ScreenOrien.HP;
   }
   else
   {
	   so=ScreenOrien.SP;
   }
   //���к�������ļ���
   if(so==ScreenOrien.HP)
   {
	   //����Ŀ��Ŀ�߱�
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
   //������������ļ���
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


