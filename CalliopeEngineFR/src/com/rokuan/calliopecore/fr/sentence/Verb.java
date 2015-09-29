package com.rokuan.calliopecore.fr.sentence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.Action;
import com.rokuan.calliopecore.sentence.Action.ActionType;
import com.rokuan.calliopecore.sentence.IVerb;

/**
 * Created by LEBEAU Christophe on 19/02/2015.
 */
@DatabaseTable(tableName = "verbs")
public class Verb implements IVerb {
    public enum ConjugationTense {
        PRESENT,
        PAST,
        PERFECT, //PASSE_COMPOSE,
        IMPERFECT, //IMPARFAIT,
        PAST_PERFECT, //PLUS_QUE_PARFAIT,
        SIMPLE_PAST, //PASSE_SIMPLE,
        PLUPERFECT, //PASSE_ANTERIEUR
        FUTURE,
        FUTURE_ANTERIOR //FUTUR_ANTERIEUR
    }

    public enum Form {
        INDICATIVE,
        CONDITIONAL,
        SUBJUNCTIVE,
        IMPERATIVE,
        INFINITIVE,
        PARTICIPLE,
    }

    public enum Pronoun {
        JE,
        TU,
        IL_ELLE_ON,
        NOUS,
        VOUS,
        ILS_ELLES,
        UNDEFINED
    }
    
    public static final String ID_FIELD_NAME = "verb";
    public static final String ACTIONS_FIELD_NAME = "actions";
    public static final String AUXILIARY_FIELD_NAME = "auxiliary";
    
    @DatabaseField(columnName = ID_FIELD_NAME, id = true)
    private String verb = "";
    
    @DatabaseField(columnName = ACTIONS_FIELD_NAME, dataType = DataType.SERIALIZABLE)
    private HashSet<Action.ActionType> actions = new HashSet<Action.ActionType>(); //VerbAction.UNDEFINED;
    
    @DatabaseField(columnName = AUXILIARY_FIELD_NAME)
    private boolean auxiliary = false;
    
    public Verb(){
    	
    }
    
    public Verb(String infinitiveForm, boolean aux, Action.ActionType... verbActions){
    	this(infinitiveForm, new HashSet<Action.ActionType>(Arrays.asList(verbActions)), aux);
    }
    
    public Verb(String infinitiveForm, Set<Action.ActionType> verbActions, boolean aux){
    	verb = infinitiveForm;
    	actions.addAll(verbActions);
    	auxiliary = aux;
    }
    
    public boolean isAuxiliary() {
        return auxiliary;
    }

	@Override
	public String getValue() {
		return verb;
	}

	@Override
	public boolean hasAction(ActionType act) {
		return actions.contains(act);
	}
	
	@Override
	public Set<ActionType> getActions(){
		return actions;
	}
}
