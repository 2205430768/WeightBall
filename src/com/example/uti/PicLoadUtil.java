package com.example.uti;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class PicLoadUtil {
	public static Bitmap loadBM(Resources res,String fname)//通过io得到图片的Bitmap
	{
		Bitmap result=null;
		try {
			InputStream in=res.getAssets().open(fname);
			int ch=0;
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			while((ch=in.read())!=-1)
			{
				baos.write(ch);
			}
			byte[] buff=baos.toByteArray();
			baos.close();
			in.close();
			result=BitmapFactory.decodeByteArray(buff, 0, buff.length);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
//	//根据行列切割图片
	public static Bitmap[][]  splitPic(int cols,int rows,Bitmap  srcPic,int  dstWidth,int dstHeight)
	{
	final float width=srcPic.getWidth();
	final float height=srcPic.getHeight();
	final int tempWidth=(int)(width/cols);
	final int tempHeight=(int)(height/rows);
	Bitmap[][] result=new Bitmap[rows][cols];
	for(int i=0;i<rows;i++)
	{
		for(int j=0;j<cols;j++)
		{
			Bitmap   tempBm=Bitmap.createBitmap(srcPic,j*tempWidth,i*tempHeight,tempWidth,tempHeight);
			result[i][j]=scaleToFit(tempBm,dstWidth,dstHeight);
		}
	}
	return result;
	}
public static Bitmap combineRec(Bitmap srcPic,float dstWidth,float dstHeight)
{
final float width=srcPic.getWidth();
final float height=srcPic.getHeight();
Bitmap[] result=new Bitmap[4];//假设dstWidth=20  dstHeight=30
result[0]=Bitmap.createBitmap(srcPic,0,0,(int)dstWidth-4,(int)dstHeight-7);
result[1]=Bitmap.createBitmap(srcPic,(int)(width-7),0,7,(int)dstHeight-7);
result[2]=Bitmap.createBitmap(srcPic,0,(int)(height-7),(int)dstWidth-4,7);
result[3]=Bitmap.createBitmap(srcPic, (int)width-7, (int)height-7, 7, 7);
Bitmap bm=Bitmap.createBitmap((int)dstWidth, (int)dstHeight,Config.ARGB_8888 );
Canvas canvas= new Canvas(bm);
Paint paint;
paint=new Paint();
paint.setAntiAlias(true);

canvas.drawBitmap(result[3], dstWidth-7, dstHeight-7, paint);
canvas.drawBitmap(result[2], 0, dstHeight-7, paint);
canvas.drawBitmap(result[1], dstWidth-7, 0, paint);
canvas.drawBitmap(result[0], 0, 0, paint);

return bm;
}


//	 //缩放旋转图片的方法
	public static Bitmap scaleToFit(Bitmap bm,int dstWidth,int dstHeight)
	{
		float width=bm.getWidth();
		float height=bm.getHeight();
		float wRatio=dstWidth/height;
		float hRatio=dstHeight/width;
		Matrix ml=new Matrix();
		ml.postScale(wRatio, hRatio);
		Matrix m2=new Matrix();
		m2.setRotate(45, dstWidth/2,dstHeight/2);
		Matrix mz=new Matrix();
		mz.setConcat(ml, m2);
		Bitmap bmResult=Bitmap.createBitmap(bm,0,0,(int)width,(int)height,mz,true);
		return bmResult;
		
	}


//	//缩放图片
	public static Bitmap scaleBitmap(Bitmap bm,int width,int height)
	{
		Bitmap bmResult =Bitmap.createScaledBitmap(bm, width, height,true);
		return bmResult;
	}


}
