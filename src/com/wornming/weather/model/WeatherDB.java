package com.wornming.weather.model;

import android.database.sqlite.SQLiteDatabase;

public class WeatherDB {
	/**
	 *���ݿ��� 
	 */
	public static final String DB_NAME = "weather";
	
	/**
	 *���ݿ�汾 
	 */
	
	public static final int VERSION = 1;
	
	private static WeatherDB weatherDB;
	
	private SQLiteDatabase db; 
}
