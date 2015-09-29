package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IUnitInfo;
import com.rokuan.calliopecore.sentence.structure.data.nominal.UnitObject.UnitType;

@DatabaseTable(tableName = "units")
public class UnitInfo implements IUnitInfo {
	public static final String UNIT_FIELD_NAME = "name";
	public static final String TYPE_FIELD_NAME = "unit_type";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = UNIT_FIELD_NAME, uniqueIndex = true)
	private String name;
	
	@DatabaseField(columnName = TYPE_FIELD_NAME)
	private UnitType unitType;
	
	public UnitInfo(){
		
	}
	
	public UnitInfo(String uName, UnitType uType){
		name = uName;
		unitType = uType;
	}
	
	@Override
	public String getValue(){
		return name;
	}
	
	@Override
	public UnitType getUnitType(){
		return unitType;
	}
}
