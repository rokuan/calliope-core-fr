package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IAction.ActionType;

@DatabaseTable(tableName = "actions")
public class Action {
	public static final String ACTION_FIELD_NAME = "action";
	public static final String ATTRIBUTE_FIELD_NAME = "attribute";
	public static final String REFLEXIVE_FIELD_NAME = "reflexive";
	public static final String VERBS_FIELD_NAME = "verbs";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = ACTION_FIELD_NAME, uniqueIndex = true)
	//private ActionType action = ActionType.UNDEFINED;
	private ActionType action;
	
	@DatabaseField(columnName = ATTRIBUTE_FIELD_NAME)
	private String field;

	@ForeignCollectionField(columnName = VERBS_FIELD_NAME, eager = false)
	private ForeignCollection<VerbAction> verbs;
	
	@DatabaseField(columnName = REFLEXIVE_FIELD_NAME)
	private boolean reflexive = false;
	
	protected Action(){
		
	}
	
	public Action(ActionType a){
		action = a;
	}
	
	public Action(ActionType a, boolean r){
		this(a);
		reflexive = r;
	}
	
	public Action(ActionType a, String f){
		this(a);
		field = f;
	}
	
	public Action(ActionType a, String f, boolean r){
		this(a, f);
		reflexive = r;
	}
	
	public Action(ActionType a, boolean r, String f){
		this(a, f, r);
	}
	
	public String getBoundField() {
		return field;
	}

	public boolean isAFieldAction() {
		return field != null;
	}
	
	public ActionType getAction(){
		return action;
	}
	
	public boolean isReflexive(){
		return reflexive;
	}
}
