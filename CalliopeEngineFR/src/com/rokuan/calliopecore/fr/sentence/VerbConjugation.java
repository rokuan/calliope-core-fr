package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IAction;

/**
 * Created by LEBEAU Christophe on 19/02/2015.
 */
@DatabaseTable(tableName = "conjugations")
public class VerbConjugation implements IAction {
	public static final String VALUE_FIELD_NAME = "value";
	public static final String VERB_FIELD_NAME = "verb";
	public static final String TENSE_FIELD_NAME = "tense";
	public static final String FORM_FIELD_NAME = "form";
	public static final String PERSON_FIELD_NAME = "pronoun"; 

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = VALUE_FIELD_NAME, index = true)
	private String name;

	@DatabaseField(columnName = VERB_FIELD_NAME, uniqueCombo = true, foreign = true, foreignAutoRefresh = true)
	private Verb verb;

	@DatabaseField(columnName = TENSE_FIELD_NAME, uniqueCombo = true)
	private Verb.ConjugationTense tense;

	@DatabaseField(columnName = FORM_FIELD_NAME, uniqueCombo = true)
	private IAction.Form form;

	@DatabaseField(columnName = PERSON_FIELD_NAME, uniqueCombo = true)
	private Verb.Pronoun pronoun;

	protected VerbConjugation(){

	}

	public VerbConjugation(Verb.ConjugationTense conjugTense, IAction.Form conjugForm, Verb.Pronoun conjugPerson, String conjugValue, Verb conjugVerb){
		tense = conjugTense;
		form = conjugForm;
		pronoun = conjugPerson;
		name = conjugValue;
		verb = conjugVerb;
	}
	
	public VerbConjugation(String conjugValue, VerbConjugation conjug, Verb v){
		this(conjug.tense, conjug.form, conjug.pronoun, conjugValue, v);
	}
	
	public String getValue(){
		return name;
	}

	public Verb getVerb() {
		return verb;
	}

	public void setVerb(Verb verb) {
		this.verb = verb;
	}

	public Form getForm() {
		return form;
	}

	public Tense getTense() {
		switch(tense){
		case PRESENT:
			return Tense.PRESENT;

		case PAST:
		case PERFECT: //PASSE_COMPOSE,
		case IMPERFECT: //IMPARFAIT,
		case PAST_PERFECT: //PLUS_QUE_PARFAIT,
		case SIMPLE_PAST: //PASSE_SIMPLE,
		case PLUPERFECT: //PASSE_ANTERIEUR
			return Tense.PAST;

		case FUTURE:
		case FUTURE_ANTERIOR: //FUTUR_ANTERIEUR
			return Tense.FUTURE;
		}

		return Tense.PRESENT;
	}

	@Override
	public ActionType getAction() {
		return verb.getMainAction().getAction();
	}

	@Override
	public String getBoundField() {
		return verb.getMainAction().getBoundField();
	}

	@Override
	public boolean isFieldBound() {
		return verb.getMainAction().isFieldBound();
	}

	@Override
	public String getBoundState() {
		return verb.getMainAction().getBoundState();
	}

	@Override
	public String getState() {
		return verb.getMainAction().getState();
	}

	@Override
	public boolean isStateBound() {
		return verb.getMainAction().isStateBound();
	}
}
