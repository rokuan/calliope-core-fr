package com.rokuan.calliopecore.fr.sentence;

import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IWayPreposition;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayContext;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayType;

@DatabaseTable(tableName = "way_prepositions")
public class WayPreposition extends Preposition implements IWayPreposition {
	public static final String VALUE_FIELD_NAME = "value";
	public static final String PREPOSITION_FIELD_NAME = "preposition";
	public static final String FOLLOWERS_FIELD_NAME = "followers";

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = VALUE_FIELD_NAME, uniqueIndex = true)
	private String name;

	@DatabaseField(columnName = PREPOSITION_FIELD_NAME)
	private WayContext wayPreposition;
	
	@DatabaseField(columnName = FOLLOWERS_FIELD_NAME, dataType = DataType.SERIALIZABLE)
	private HashSet<WayType> followersTypes = new HashSet<WayType>();
	
	public WayPreposition(){
		
	}
	
	public WayPreposition(String v, WayContext prep){
		name = v;
		wayPreposition = prep;
	}
	
	public WayPreposition(String v, WayContext prep, Set<WayType> types){
		this(v, prep);
		followersTypes.addAll(types);
	}
	
	public WayPreposition(String v, WayContext prep, WayType... types){
		this(v, prep);

		for(int i=0; i<types.length; i++){
			followersTypes.add(types[i]);
		}
	}

	@Override
	public String getValue() {
		return name;
	}

	@Override
	public WayContext getWayContext() {
		return wayPreposition;
	}

	public boolean canBeFollowedBy(WayType ty) {
		return followersTypes.contains(ty);
	}
}
