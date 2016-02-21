package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IAdjectiveInfo;

@DatabaseTable(tableName = "adjectives")
public class AdjectiveInfo implements IAdjectiveInfo {
	public static final String TYPE_FIELD_NAME = "adjective_type";
	public static final String VALUE_FIELD_NAME = "adjective_value";
	public static final String ATTRIBUTE_FIELD_NAME = "attribute";
	public static final String STATE_FIELD_NAME = "state";
	public static final String STATE_VALUE_FIELD_NAME = "state_value";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = TYPE_FIELD_NAME)
	private AdjectiveValue adjectiveType = AdjectiveValue.UNDEFINED;
	
	@DatabaseField(columnName = VALUE_FIELD_NAME, uniqueIndex = true)
	private String text;
	
	@DatabaseField(columnName = ATTRIBUTE_FIELD_NAME)
	private String field;
	
	@DatabaseField(columnName = STATE_FIELD_NAME)
	private String state;
	
	@DatabaseField(columnName = STATE_VALUE_FIELD_NAME)
	private String stateValue;
	
	public AdjectiveInfo(){
		
	}
	
	public AdjectiveInfo(String aText, AdjectiveValue aType){
		text = aText;
		adjectiveType = aType;
	}
	
	public AdjectiveInfo(String aText, FieldProperty f, StateProperty s){
		text = aText;
		
		if(f != null){
			field = f.getField();
		}
		
		if(s != null){
			state = s.getField();
			stateValue = s.getValue();
		}
	}
	
	@Override
	public String getValue(){
		return text;
	}
	
	@Override
	public AdjectiveValue getAdjectiveType(){
		return adjectiveType;
	}

	@Override
	public String getBoundField() {
		return field;
	}

	@Override
	public boolean isFieldBound() {
		return field != null;
	}

	@Override
	public String getBoundState() {
		return state;
	}

	@Override
	public String getState() {
		return stateValue;
	}

	@Override
	public boolean isStateBound() {
		return state != null;
	}	
}
