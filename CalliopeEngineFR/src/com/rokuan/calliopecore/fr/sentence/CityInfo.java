package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ICityInfo;

@DatabaseTable(tableName = "cities")
public final class CityInfo implements ICityInfo {
	public static final String CITY_FIELD_NAME = "city_name";
	public static final String LATITUDE_FIELD_NAME = "city_lat";
	public static final String LONGITUDE_FIELD_NAME = "city_lng";	
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = CITY_FIELD_NAME, uniqueCombo = true, index = true)
	private String name;
	
	@DatabaseField(columnName = LATITUDE_FIELD_NAME, uniqueCombo = true)
	private double latitude;
	
	@DatabaseField(columnName = LONGITUDE_FIELD_NAME, uniqueCombo = true)
	private double longitude;
	
	public CityInfo(){
		
	}
	
	public CityInfo(String n, double lat, double lng){
		name = n;
		latitude = lat;
		longitude = lng;
	}
	
	@Override
	public String getValue() {
		return name;
	}
	
	@Override
	public Location getLocation(){
		return new Location(latitude, longitude);
	}
}
