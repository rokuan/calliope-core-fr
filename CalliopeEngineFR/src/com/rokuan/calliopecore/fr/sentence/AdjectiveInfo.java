package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IAdjectiveInfo;

@DatabaseTable(tableName = "adjectives")
public class AdjectiveInfo implements IAdjectiveInfo {
	public static final String TYPE_FIELD_NAME = "adjective_type";
	public static final String VALUE_FIELD_NAME = "adjective_value";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = TYPE_FIELD_NAME)
	private AdjectiveValue adjectiveType = AdjectiveValue.UNDEFINED;
	
	@DatabaseField(columnName = VALUE_FIELD_NAME, uniqueIndex = true)
	private String text;
	
	public AdjectiveInfo(String aText, AdjectiveValue aType){
		text = aText;
		adjectiveType = aType;
	}
	
	@Override
	public String getValue(){
		return text;
	}
	
	@Override
	public AdjectiveValue getAdjectiveType(){
		return adjectiveType;
	}	
}
