package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IPlaceInfo;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceObject.PlaceCategory;

@DatabaseTable(tableName = "places")
public class PlaceInfo implements IPlaceInfo {
	public static final String PLACE_FIELD_NAME = "place_name";
	public static final String TYPE_FIELD_NAME = "place_type";
	
	@DatabaseField(columnName = PLACE_FIELD_NAME, id = true)
	private String name;
	
	@DatabaseField(columnName = TYPE_FIELD_NAME)
	private PlaceCategory placeCategory;
	
	public PlaceInfo() {

	}
	
	public PlaceInfo(String pName, PlaceCategory pCategory){
		name = pName;
		placeCategory = pCategory;
	}
	
	@Override
	public String getValue(){
		return name;
	}
	
	@Override
	public PlaceCategory getPlaceCategory(){
		return placeCategory;
	}
}
