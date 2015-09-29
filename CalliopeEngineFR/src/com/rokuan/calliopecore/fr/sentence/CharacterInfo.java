package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ICharacterInfo;
import com.rokuan.calliopecore.sentence.structure.data.nominal.CharacterObject.CharacterType;

@DatabaseTable(tableName = "characters")
public class CharacterInfo implements ICharacterInfo {
	public static final String CHARACTER_FIELD_NAME = "character_value";
	public static final String TYPE_FIELD_NAME = "character_type";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = CHARACTER_FIELD_NAME, uniqueIndex = true)
	private String name;
	
	@DatabaseField(columnName = TYPE_FIELD_NAME)
	private CharacterType characterType;
	
	public CharacterInfo(){
		
	}
	
	public CharacterInfo(String cName, CharacterType cType){
		name = cName;
		characterType = cType;
	}
	
	@Override
	public String getValue(){
		return name;
	}
	
	@Override
	public CharacterType getCharacterType(){
		return characterType;
	}
}
