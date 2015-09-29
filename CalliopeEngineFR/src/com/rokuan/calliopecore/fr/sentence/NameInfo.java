package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.INameInfo;

@DatabaseTable(tableName = "names")
public class NameInfo implements INameInfo {
	public static final String VALUE_FIELD_NAME = "name_value";
	public static final String TAG_FIELD_NAME = "name_tag";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = VALUE_FIELD_NAME, uniqueIndex = true)
	private String value;
	
	@DatabaseField(columnName = TAG_FIELD_NAME)
	private String tag;
	
	public NameInfo(){
		
	}
	
	public NameInfo(String nameValue, String nameTag){
		value = nameValue;
		tag = nameTag;
	}
	
	@Override
	public String getValue(){
		return value;
	}
	
	@Override
	public String getNameTag(){
		return tag;
	}
}
