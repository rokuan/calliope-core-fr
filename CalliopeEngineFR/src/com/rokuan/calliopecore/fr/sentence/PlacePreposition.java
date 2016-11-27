package com.rokuan.calliopecore.fr.sentence;

import java.util.Set;

import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IPlacePreposition;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceContext;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceType;

@DatabaseTable(tableName = "place_prepositions")
public class PlacePreposition extends Preposition<PlaceContext, PlaceType> implements IPlacePreposition {
	public PlacePreposition(){
		super();
	}
	
	public PlacePreposition(String v, PlaceContext prep){
		super(v, prep);
	}
	
	public PlacePreposition(String v, PlaceContext prep, Set<PlaceType> types){
		super(v, prep, types);
	}
	
	public PlacePreposition(String v, PlaceContext prep, PlaceType... types){
		super(v, prep, types);
	}
}
