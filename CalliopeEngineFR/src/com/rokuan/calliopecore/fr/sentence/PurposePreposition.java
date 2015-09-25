package com.rokuan.calliopecore.fr.sentence;

import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IPurposePreposition;
import com.rokuan.calliopecore.sentence.structure.data.purpose.PurposeAdverbial.PurposeContext;
import com.rokuan.calliopecore.sentence.structure.data.purpose.PurposeAdverbial.PurposeType;

@DatabaseTable(tableName = "purpose_prepositions")
public class PurposePreposition extends Preposition implements IPurposePreposition {
	public static final String VALUE_FIELD_NAME = "value";
	public static final String PREPOSITION_FIELD_NAME = "preposition";
	public static final String FOLLOWERS_FIELD_NAME = "followers";

	@DatabaseField(generatedId = true)
	private int id;
	
	//@Expose
	@DatabaseField(columnName = VALUE_FIELD_NAME, uniqueIndex = true)
	private String name;

	//@Expose
	@DatabaseField(columnName = PREPOSITION_FIELD_NAME)
	private PurposeContext purposePreposition;
	
	@DatabaseField(columnName = FOLLOWERS_FIELD_NAME, dataType = DataType.SERIALIZABLE)
	private HashSet<PurposeType> followersTypes = new HashSet<PurposeType>();
	
	public PurposePreposition(){
		
	}
	
	public PurposePreposition(String v, PurposeContext prep){
		name = v;
		purposePreposition = prep;
	}
	
	public PurposePreposition(String v, PurposeContext prep, Set<PurposeType> types){
		this(v, prep);
		followersTypes.addAll(types);
	}
	
	public PurposePreposition(String v, PurposeContext prep, PurposeType... types){
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
	public PurposeContext getPurposeContext() {
		return purposePreposition;
	}
	
	public boolean canBeFollowedBy(PurposeType ty){
		return followersTypes.contains(ty);
	}
}
