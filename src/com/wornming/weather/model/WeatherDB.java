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
	 * 数据库名
	 */
	public static final String DB_NAME = "weather";

	/**
	 * 数据库版本
	 */

	public static final int VERSION = 1;

	private static WeatherDB weatherDB;

	private SQLiteDatabase db;

	/**
	 * 构造方法私有化
	 */
	private WeatherDB(Context context) {
		WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 获取WeatherDB实例 单例模式
	 */
	public synchronized static WeatherDB getInstance(Context context) {
		if (weatherDB == null) {
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;
	}

	/**
	 * 将Province实例存储到数据库中
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
	 * 从数据库中读取省份信息
	 */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();

		Cursor cursor = db.query(DB_NAME, null, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				// 创建用于存储从数据库读取的信息
				Province province = new Province();
				// 获取省份编号id
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				// 获取省份名字province_name
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				// 获取省份的province_name
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * 存储市区信息
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
	 * 读取市区信息
	 */
	public List<City> loadCities(int province_id) {
		List<City> list = new ArrayList<City>();

		Cursor cursor = db.query("City", null, "province_id = ?", new String[] { String.valueOf(province_id) }, null,
				null, null, null);

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

	/**
	 * 存储县区信息
	 */
	public void saveCountry(Country country) {
		if (country != null) {
			ContentValues values = new ContentValues();

			values.put("country_name", country.getCountryName());
			values.put("country_code", country.getCountryCode());
			values.put("city_id", country.getCityId());
			db.insert("Country", null, values);
		}
	}

	/**
	 * 读取县区信息
	 */
	public List<Country> loadCountries(int city_id) {
		List<Country> list = new ArrayList<Country>();

		Cursor cursor = db.query("Country", null, "city_id = ?", new String[] { String.valueOf(city_id) }, null, null,
				null);
		if (cursor.moveToFirst()) {
			do {
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
				country.setCountryName(cursor.getString(cursor.getColumnIndex("country_code")));
				country.setCityId(city_id);
				list.add(country);
			} while (cursor.moveToNext());
		}
		return list;

	}
}
