package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "verb_actions")
public class VerbAction {
	public static final String ACTION_FIELD_NAME = "action";
	public static final String VERB_FIELD_NAME = "verb";
	public static final String REFLEXIVE_FIELD_NAME = "reflexive";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = VERB_FIELD_NAME, foreign = true, uniqueCombo = true)
	private Verb verb;
	
	@DatabaseField(columnName = ACTION_FIELD_NAME, foreign = true, foreignAutoRefresh = true, uniqueCombo = true)
	private Action action;
	
	@DatabaseField(columnName = REFLEXIVE_FIELD_NAME)
	private boolean reflexive = false;
	
	public VerbAction(){
		
	}
	
	public VerbAction(Verb v, Action a){
		verb = v;
		action = a;
	}
	
	public VerbAction(Verb v, Action a, boolean r){
		this(v, a);
		reflexive = r;
	}
	
	public Verb getVerb(){
		return verb;
	}
	
	public Action getAction(){
		return action;
	}
	
	public boolean isReflexive(){
		return reflexive;
	}
}
