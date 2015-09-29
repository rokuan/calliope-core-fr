package com.rokuan.calliopecore.fr.sentence;

import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IPlacePreposition;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceContext;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceType;

@DatabaseTable(tableName = "place_prepositions")
public class PlacePreposition extends Preposition implements IPlacePreposition {	
	public static final String VALUE_FIELD_NAME = "value";
	public static final String PREPOSITION_FIELD_NAME = "preposition";
	public static final String FOLLOWERS_FIELD_NAME = "followers";

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = VALUE_FIELD_NAME, uniqueIndex = true)
	private String name;

	@DatabaseField(columnName = PREPOSITION_FIELD_NAME)
	private PlaceContext placePreposition;
	
	@DatabaseField(columnName = FOLLOWERS_FIELD_NAME, dataType = DataType.SERIALIZABLE)
	private HashSet<PlaceType> followersTypes = new HashSet<PlaceType>();
	
	public PlacePreposition(){
		
	}
	
	public PlacePreposition(String v, PlaceContext prep){
		name = v;
		placePreposition = prep;
	}
	
	public PlacePreposition(String v, PlaceContext prep, Set<PlaceType> types){
		this(v, prep);
		followersTypes.addAll(types);
	}
	
	public PlacePreposition(String v, PlaceContext prep, PlaceType... types){
		this(v, prep);
		
		for(int i=0; i<types.length; i++){
			followersTypes.add(types[i]);
		}
	}

	@Override
	public String getValue() {
		return name;
	}

	public PlaceContext getPlaceContext() {
		return placePreposition;
	}
	
	public boolean canBeFollowedBy(PlaceType ty){
		return followersTypes.contains(ty);
	}
}
