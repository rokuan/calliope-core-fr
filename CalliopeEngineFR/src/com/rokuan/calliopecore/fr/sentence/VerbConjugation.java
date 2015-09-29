package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.Action.ActionType;
import com.rokuan.calliopecore.sentence.IVerbConjugation;

/**
 * Created by LEBEAU Christophe on 19/02/2015.
 */
@DatabaseTable(tableName = "conjugations")
public class VerbConjugation implements IVerbConjugation {
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
	private IVerbConjugation.Form form;

	@DatabaseField(columnName = PERSON_FIELD_NAME, uniqueCombo = true)
	private Verb.Pronoun pronoun;

	public VerbConjugation(){

	}

	public VerbConjugation(Verb.ConjugationTense conjugTense, IVerbConjugation.Form conjugForm, Verb.Pronoun conjugPerson, String conjugValue, Verb conjugVerb){
		tense = conjugTense;
		form = conjugForm;
		pronoun = conjugPerson;
		name = conjugValue;
		verb = conjugVerb;
	}
	
	public VerbConjugation(String conjugValue, VerbConjugation conjug, Verb v){
		this(conjug.tense, conjug.form, conjug.pronoun, conjugValue, v);
	}
	
	@Override
	public String getValue(){
		return name;
	}

	@Override
	public Verb getVerb() {
		return verb;
	}

	public void setVerb(Verb verb) {
		this.verb = verb;
	}

	@Override
	public Form getForm() {
		return form;
	}

	@Override
	public boolean does(ActionType action){
		return verb != null && verb.hasAction(action);
	}

	@Override
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
}
