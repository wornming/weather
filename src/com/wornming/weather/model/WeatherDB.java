package com.wornming.weather.model;

import java.util.ArrayList;
import java.util.List;

import com.wornming.weather.db.WeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherDB {
	/**
	 * ���ݿ���
	 */
	public static final String DB_NAME = "weather";

	/**
	 * ���ݿ�汾
	 */

	public static final int VERSION = 1;

	private static WeatherDB weatherDB;

	private SQLiteDatabase db;

	/**
	 * ���췽��˽�л�
	 */
	private WeatherDB(Context context) {
		WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * ��ȡWeatherDBʵ�� ����ģʽ
	 */
	public synchronized static WeatherDB getInstance(Context context) {
		if (weatherDB == null) {
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;
	}

	/**
	 * ��Provinceʵ���洢�����ݿ���
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();

			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * �����ݿ��ж�ȡʡ����Ϣ
	 */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();

		Cursor cursor = db.query(DB_NAME, null, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				// �������ڴ洢�����ݿ��ȡ����Ϣ
				Province province = new Province();
				// ��ȡʡ�ݱ��id
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				// ��ȡʡ������province_name
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				// ��ȡʡ�ݵ�province_name
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * �洢������Ϣ
	 */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();

			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	/**
	 * ��ȡ������Ϣ
	 */
	public List<City> loadCities(int province_id) {
		List<City> list = new ArrayList<City>();
		
		Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(province_id)}, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(province_id);
				
				list.add(city);
			} while (cursor.moveToNext());
			
		}
		return list;
	}
}
