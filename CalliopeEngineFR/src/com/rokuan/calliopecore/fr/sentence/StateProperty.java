package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "states")
public class StateProperty {
	public static final String STATE_FIELD_NAME = "name";
	public static final String ATTRIBUTE_FIELD_NAME = "state";
	public static final String VALUE_FIELD_NAME = "state_value";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = STATE_FIELD_NAME, uniqueIndex = true)
	private String name;
	
	@DatabaseField(columnName = ATTRIBUTE_FIELD_NAME)
	private String field;
	
	@DatabaseField(columnName = VALUE_FIELD_NAME)
	private String value;
	
	protected StateProperty(){
		
	}
	
	public StateProperty(String n, String f, String v){
		name = n;
		field = f;
		value = v;
	}
	
	public String getName(){
		return name;
	}
	
	public String getField(){
		return field;
	}
	
	public String getValue(){
		return value;
	}
}
