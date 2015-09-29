package com.rokuan.calliopecore.fr.sentence;

import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ITimePreposition;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.TimeContext;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.TimeType;

@DatabaseTable(tableName = "time_prepositions")
public class TimePreposition extends Preposition implements ITimePreposition {
	public static final String VALUE_FIELD_NAME = "value";
	public static final String PREPOSITION_FIELD_NAME = "preposition"; 
	public static final String FOLLOWERS_FIELD_NAME = "followers";

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = VALUE_FIELD_NAME, uniqueIndex = true)
	private String name;

	@DatabaseField(columnName = PREPOSITION_FIELD_NAME)
	private TimeContext timePreposition;
	
	@DatabaseField(columnName = FOLLOWERS_FIELD_NAME, dataType = DataType.SERIALIZABLE)
	private HashSet<TimeType> followersTypes = new HashSet<TimeType>();
	
	public TimePreposition(){
		
	}
	
	public TimePreposition(String v, TimeContext prep){
		name = v;
		timePreposition = prep;
	}
	
	public TimePreposition(String v, TimeContext prep, Set<TimeType> types){
		this(v, prep);
		followersTypes.addAll(types);
	}
	
	public TimePreposition(String v, TimeContext prep, TimeType... types){
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
	public TimeContext getTimeContext() {
		return timePreposition;
	}

	public boolean canBeFollowedBy(TimeType ty) {
		return followersTypes.contains(ty);
	}
}
