package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ICountryInfo;

@DatabaseTable(tableName = "countries")
public final class CountryInfo implements ICountryInfo {
	public static final String COUNTRY_FIELD_NAME = "country_name";
	public static final String CODE_FIELD_NAME = "country_code";
	
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = COUNTRY_FIELD_NAME, uniqueIndex = true)
	private String name;

	@DatabaseField(columnName = CODE_FIELD_NAME, unique = true)
	private String code;

	public CountryInfo(){
		
	}
	
	public CountryInfo(String n, String c){
		name = n;
		code = c;
	}
	
	@Override
	public String getValue() {
		return name;
	}
	
	@Override
	public String getCountryCode() {
		return code;
	}
}
