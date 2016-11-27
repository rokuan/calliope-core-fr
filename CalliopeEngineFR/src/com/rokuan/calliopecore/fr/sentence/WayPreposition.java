package com.rokuan.calliopecore.fr.sentence;

import java.util.Set;

import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IWayPreposition;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayContext;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayType;

@DatabaseTable(tableName = "way_prepositions")
public class WayPreposition extends Preposition<WayContext, WayType> implements IWayPreposition {
	public WayPreposition(){
		
	}
	
	public WayPreposition(String v, WayContext prep){
		super(v, prep);
	}
	
	public WayPreposition(String v, WayContext prep, Set<WayType> types){
		super(v, prep, types);
	}
	
	public WayPreposition(String v, WayContext prep, WayType... types){
		super(v, prep, types);
	}
}
