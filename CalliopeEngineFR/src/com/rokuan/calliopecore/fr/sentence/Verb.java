package com.rokuan.calliopecore.fr.sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IAction.ActionType;

/**
 * Created by LEBEAU Christophe on 19/02/2015.
 */
@DatabaseTable(tableName = "verbs")
public class Verb {
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
    //public static final String ATTRIBUTE_FIELD_NAME = "attribute";
    
    @DatabaseField(columnName = ID_FIELD_NAME, id = true)
    private String verb = "";
    
    @ForeignCollectionField(columnName = ACTIONS_FIELD_NAME, eager = true)
    private Collection<VerbAction> actions = new ArrayList<VerbAction>();
    
    @DatabaseField(columnName = AUXILIARY_FIELD_NAME)
    private boolean auxiliary = false;
    
    public Verb(){
    	
    }
    
    public Verb(String infinitiveForm, boolean aux, VerbAction... verbActions){
    	this(infinitiveForm, Arrays.asList(verbActions), aux);
    }
    
    public Verb(String infinitiveForm, Collection<VerbAction> verbActions, boolean aux){
    	verb = infinitiveForm;
    	actions.addAll(verbActions);
    	auxiliary = aux;
    }
    
    public boolean isAuxiliary() {
        return auxiliary;
    }

	public String getValue() {
		return verb;
	}

	public boolean hasAction(ActionType action){
		for(VerbAction a: actions){
			if(a.getAction() == action){
				return true;
			}
		}
		
		return false;
	}
	
	public VerbAction getAction(ActionType action){
		for(VerbAction a: actions){
			if(a.getAction() == action){
				return a;
			}
		}
		
		return null;
	}
	
	public VerbAction getMainAction(){
		return actions.iterator().next();
	}
}
