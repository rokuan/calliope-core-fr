package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IAction.ActionType;

@DatabaseTable(tableName = "verb_actions")
public class VerbAction {
	public static final String ACTION_FIELD_NAME = "action";
	public static final String ATTRIBUTE_FIELD_NAME = "attribute";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = ACTION_FIELD_NAME, uniqueIndex = true)
	private ActionType action = ActionType.UNDEFINED;
	
	@DatabaseField(columnName = ATTRIBUTE_FIELD_NAME)
	private String field;
	
	protected VerbAction(){
		
	}
	
	public VerbAction(ActionType a){
		action = a;
	}
	
	public VerbAction(ActionType a, String f){
		this(a);
		field = f;
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
}
