package com.rokuan.calliopecore.fr.sentence;

import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.rokuan.calliopecore.sentence.IPreposition;


public abstract class Preposition<ContextType, FollowerType> implements IPreposition<ContextType> {
	public static final String VALUE_FIELD_NAME = "value";
	public static final String PREPOSITION_FIELD_NAME = "preposition";
	public static final String FOLLOWERS_FIELD_NAME = "followers";	

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = VALUE_FIELD_NAME, uniqueIndex = true)
	private String name;
	
	@DatabaseField(columnName = PREPOSITION_FIELD_NAME)
	protected ContextType preposition;
	
	@DatabaseField(columnName = FOLLOWERS_FIELD_NAME, dataType = DataType.SERIALIZABLE)
	protected HashSet<FollowerType> followersTypes = new HashSet<FollowerType>();
	
	public Preposition(){
		
	}
	
	public Preposition(String v, ContextType prep){
		name = v;
		preposition = prep;
	}
	
	public Preposition(String v, ContextType prep, Set<FollowerType> types){
		this(v, prep);
		followersTypes.addAll(types);
	}
	
	public Preposition(String v, ContextType prep, FollowerType... types){
		this(v, prep);
		
		for(int i=0; i<types.length; i++){
			followersTypes.add(types[i]);
		}
	}

	@Override
	public ContextType getContext() {
		return preposition;
	}
	
	@Override
	public String getValue() {
		return name;
	}
	
	public boolean canBeFollowedBy(FollowerType ty){
		return followersTypes.contains(ty);
	}
}
