package com.rokuan.calliopecore.fr.sentence;

import java.util.Set;

import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ITimePreposition;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.TimeContext;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.TimeType;

@DatabaseTable(tableName = "time_prepositions")
public class TimePreposition extends Preposition<TimeContext, TimeType> implements ITimePreposition {	
	public TimePreposition(){
		super();
	}
	
	public TimePreposition(String v, TimeContext prep){
		super(v, prep);
	}
	
	public TimePreposition(String v, TimeContext prep, Set<TimeType> types){
		super(v, prep, types);
	}
	
	public TimePreposition(String v, TimeContext prep, TimeType... types){
		super(v, prep, types);
	}
}
