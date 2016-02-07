package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IValue;

@DatabaseTable(tableName = "firstnames")
public class FirstnameInfo implements IValue {
	public static final String FIRSTNAME_FIELD_NAME = "firstname";
	
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = FIRSTNAME_FIELD_NAME, uniqueIndex = true)
	private String value;

	protected FirstnameInfo() {
		
	}
	
	public FirstnameInfo(String n){
		value = n;
	}
	
	@Override
	public String getValue() {
		return value;
	}
}
