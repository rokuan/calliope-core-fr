package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IColorInfo;

@DatabaseTable(tableName = "colors")
public class ColorInfo implements IColorInfo {
	public static final String COLOR_FIELD_NAME = "color_name";
	public static final String HEX_FIELD_NAME = "color_hexcode";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = COLOR_FIELD_NAME, uniqueIndex = true)
	private String name;
	
	@DatabaseField(columnName = HEX_FIELD_NAME)
	private String hexColor;
	
	public ColorInfo(){
		
	}
	
	public ColorInfo(String colorName, String colorHexCode){
		name = colorName;
		hexColor = colorHexCode;
	}
	
	@Override
	public String getValue(){
		return name;
	}
	
	@Override
	public String getColorHexCode(){
		return hexColor;
	}
}
