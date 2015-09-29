package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ILanguageInfo;

@DatabaseTable(tableName = "languages")
public final class LanguageInfo implements ILanguageInfo {
	public static final String CODE_FIELD_NAME = "language_code";
	public static final String LANGUAGE_FIELD_NAME = "language_name";

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = LANGUAGE_FIELD_NAME, uniqueIndex = true)
	private String name;

	@DatabaseField(columnName = CODE_FIELD_NAME)
	private String code;
	
	public LanguageInfo(){
		
	}
	
	public LanguageInfo(String n, String c){
		name = n;
		code = c;
	}
	
	@Override
	public String getValue(){
		return name;
	}
	
	@Override
	public String getLanguageCode(){
		return code;
	}
}
