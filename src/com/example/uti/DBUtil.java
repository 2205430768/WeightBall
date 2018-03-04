package com.example.uti;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {
	public static SQLiteDatabase createOrOpenDatabase()
	{
		SQLiteDatabase  sld=null;
		try {
			sld=SQLiteDatabase.openDatabase("/data/data/com.example.mytableball2/gamedb",
					null,SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
			String sql="create table if not exists timerecord(model integer,gate integer,timeplay integer,lock integer);";
			sld.execSQL(sql);
			sql="create table if not exists setting(yinyue integer,yinxiao integer,zhendong integer);";
			sld.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  sld;
	}
	 public static void updateSetting(int yinyue,int yinxiao,int zhendong)
	    {
	    	SQLiteDatabase sld=null;
	    	try
	    	{
	    		sld=createOrOpenDatabase();//打开数据库
	    		String sql="update setting set yinyue="+yinyue+",yinxiao="+yinxiao+",zhendong="+zhendong+";";
	    		sld.execSQL(sql);
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	finally
	    	{
	    		try{closeDatabase(sld);}catch(Exception e){e.printStackTrace();}
	    	}
	    }
//	//关闭数据库的方法
	public static void closeDatabase(SQLiteDatabase sld)
	{
		try {
			sld.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	//将未看过通知的标志为已看过通知
	public static void upDateTime(int model,int gate,int timeplay)
	{
		SQLiteDatabase sld=null;
		try {
			sld=createOrOpenDatabase();
			String sql="update timerecord set timeplay="+timeplay+" where model="+model+" and gate="+gate+";";
			sld.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				closeDatabase(sld);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	  //获取锁的状态
	public static Integer getLock(int model,int gate)
	{
		SQLiteDatabase sld=null;
		int timeplay=0;
		try {
			sld=createOrOpenDatabase();//打开数据库
			String sql="select lock from timerecord where model = "+model+"  and gate ="+gate;
			Cursor cur=sld.rawQuery(sql, new String[]{});
			while(cur.moveToNext())
			{
				timeplay=cur.getInt(0);
			}
			cur.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				closeDatabase(sld);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return timeplay;
	}
//	//从数据库获取游戏每关所用的时间 没有玩过返回0，玩过返回以前所用时间最少的那次游戏所用的时间
	public static Integer getTimeplay(int model,int gate)
	{
		SQLiteDatabase sld=null;
		int timeplay=0;
		try {
			sld=createOrOpenDatabase();
			String sql="select timeplay from timerecord where model = "+model+"  and gate ="+gate;
			Cursor cur=sld.rawQuery(sql, new String[]{});
			while(cur.moveToNext())
			{
				timeplay=cur.getInt(0);
			}
			cur.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				closeDatabase(sld);
				
			} catch (Exception e) {
			e.printStackTrace();
			}
		}
		return timeplay;
	}


	public static int[] SearchSetting()
	{
		SQLiteDatabase sld=null;
		int result[]=new int[3];
		try {
			sld=createOrOpenDatabase();
			String sql="select * from setting";
			Cursor cur=sld.rawQuery(sql, new String[]{});
			while(cur.moveToNext())
			{
				result[0]=cur.getInt(0);
				result[1]=cur.getInt(1);
				result[2]=cur.getInt(2);
				
			}
			cur.close();
					
		} catch (Exception e) {
		e.printStackTrace();
		}
		finally
		{
			try {
				closeDatabase(sld);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	 public static void insertSetting(int yinyue,int yinxiao,int zhendong)
	    {
	    	SQLiteDatabase sld=null;
	    	try
	    	{
	    		sld=createOrOpenDatabase();//打开数据库
	    		String sql="insert into setting values("+yinyue+","+yinxiao+","+zhendong+");";
	        	sld.execSQL(sql);            	
			}  
			catch(Exception e)
			{
				e.printStackTrace(); 
			}
			finally
			{
				try{closeDatabase(sld);}catch(Exception e){e.printStackTrace();}
			}
		}
//	 //插入获取的通知信息
	public static void insert(int model,int gate,int timeplay,int lock)
	{
		SQLiteDatabase sld=null;
		try {
			sld=createOrOpenDatabase();
			String sql="insert into timerecord values("+model+","+gate+","+timeplay+","+lock+");";
			sld.execSQL(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				closeDatabase(sld);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
   
//	 //获取数据库中全部信息
	public static ArrayList<int[]> searchAll()
	{
		SQLiteDatabase  sld=null;
		ArrayList<int[]> list=new ArrayList<int[]>();
		try {
			sld=createOrOpenDatabase();
			String sql="select * from timerecord";
			Cursor cur=sld.rawQuery(sql, new String[]{});
			while(cur.moveToNext())
			{
				int result[]=new int[4];
				result[0]=cur.getInt(0);
				result[1]=cur.getInt(1);
				result[2]=cur.getInt(2);
				result[3]=cur.getInt(3);
				list.add(result);
				
			}
			cur.close();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				closeDatabase(sld);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}


}
