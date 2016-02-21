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
	public static final String STATE_FIELD_NAME = "state";
	public static final String STATE_VALUE_FIELD_NAME = "state_value";
	public static final String VERBS_FIELD_NAME = "verbs";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = ACTION_FIELD_NAME, uniqueIndex = true)
	private ActionType action = ActionType.UNDEFINED;
	
	@DatabaseField(columnName = ATTRIBUTE_FIELD_NAME)
	private String field;
	
	@DatabaseField(columnName = STATE_FIELD_NAME)
	private String state;
	
	@DatabaseField(columnName = STATE_VALUE_FIELD_NAME)
	private String stateValue;

	@ForeignCollectionField(columnName = VERBS_FIELD_NAME, eager = false)
	private ForeignCollection<VerbAction> verbs;
	
	protected Action(){
		
	}
	
	public Action(ActionType a){
		action = a;
	}
	
	public Action(ActionType a, String f){
		this(a);
		field = f;
	}	
	
	public Action(ActionType a, FieldProperty f, StateProperty s){
		this(a);
		
		if(f != null){
			field = f.getField();
		}
		
		if(s != null){
			state = s.getField();
			stateValue = s.getValue();
		}
	}
	
	public ActionType getAction(){
		return action;
	}

	public boolean isFieldBound() {
		return field != null;
	}
	
	public String getBoundField() {
		return field;
	}

	public String getBoundState() {
		return state;
	}

	public String getState() {
		return stateValue;
	}

	public boolean isStateBound() {
		return state != null;
	}
}
