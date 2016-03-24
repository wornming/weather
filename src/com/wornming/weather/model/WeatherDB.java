package com.wornming.weather.model;

import android.database.sqlite.SQLiteDatabase;

public class WeatherDB {
	/**
	 *数据库名 
	 */
	public static final String DB_NAME = "weather";
	
	/**
	 *数据库版本 
	 */
	
	public static final int VERSION = 1;
	
	private static WeatherDB weatherDB;
	
	private SQLiteDatabase db; 
}
