package com.zsw.colorfulcloudslibrary.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.ColorInt;

public class SharedPUtils {
	public final static String TAG = "SharedPreferencesUtils";

	public final static String THEME_ID = "theme_id_";
	public final static String THEME_COLOR = "theme_color_";
	public final static String LANGUAGE = "language_";

	/**
	 * 设置默认主题和状态栏颜色
	 * @param con
	 * @param themeId
	 * @param statusColor
	 * @return
	 */
	public static void saveNormalTheme(Context con,int themeId,@ColorInt int statusColor){
		SharedPUtils.saveInt(con, SharedPUtils.THEME_ID, themeId);
		SharedPUtils.saveInt(con, SharedPUtils.THEME_COLOR, statusColor);
	}

	public static String saveLanguageSetting(Context con,String values){
		SharedPreferences.Editor sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE).edit();
		if (Verifier.isNull(values)) {
			sp.putString(LANGUAGE, "");
			sp.commit();
			return "";
		} else {
			sp.putString(LANGUAGE, values);
			sp.commit();
		}
		return values;
	}

	public static String saveString(Context con, String key, String values) {
		SharedPreferences.Editor sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE).edit();
		if (Verifier.isNull(values)) {
			sp.putString(key, "");
			sp.commit();
			return "";
		} else {
			sp.putString(key, values);
		}
		sp.commit();
		return values;
	}

	public static void saveBoolean(Context con, String key, boolean value) {
		SharedPreferences.Editor sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE).edit();
		sp.putBoolean(key, value).commit();
	}

	public static void saveLong(Context con, String key, Long value) {
		SharedPreferences.Editor sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE).edit();
		sp.putLong(key, value).commit();
	}
	
	public static void saveInt(Context con, String key, int value) {
		SharedPreferences.Editor sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE).edit();
		sp.putInt(key, value).commit();
	}

	public static String getString(Context con, String key) {
		SharedPreferences sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		return sp.getString(key, "");

	}

	public static int getInt(Context con, String key) {
		SharedPreferences sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		return sp.getInt(key, 0);

	}
	
	public static Long getLong(Context con, String key) {
		SharedPreferences sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		return sp.getLong(key, 1000);

	}

	public static boolean getBoolean(Context con, String key) {
		SharedPreferences sp = con.getSharedPreferences(TAG,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}




}
