package com.zsw.rainbowlibrary.utils;

import android.util.Log;
/**
 * author  z.sw
 * time  2016/8/2.
 * email  zhusw@visionet.com.cn
 * Description-添加filter ranbowL
 */
public class L {
/**
 * 项目打印日志
 */
	private static boolean deBug = true;
	private static String str = "";

	public static  void setDeBug(boolean isDebug){
		deBug = isDebug;
	}

	public static void runAtTAG(String TAG){
		if(deBug){
			str = "rainbowL--runing>>>>>"+TAG+">>>>>>)";
			Log.d(TAG, str);
		}
	}
	public static void printD(String TAG,String MSG){
		if(deBug){
		str = "rainbowL--runing>>"+TAG+">>)"+MSG;
		Log.d(TAG, str);	
		}
	}
	public static void printI(String TAG,String MSG){
		if(deBug){
		str = "rainbowL--runing>>"+TAG+">>)"+MSG;
		Log.i(TAG, str);	
		}
	}
	public static void printE(String TAG,String MSG){
		if(deBug){
		str = "rainbowL--runing>>"+TAG+">>)"+MSG;
		Log.e(TAG, str);	
		}
	}


}
