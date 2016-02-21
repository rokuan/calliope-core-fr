package com.rokuan.calliopecore.fr.test;

import org.junit.Test;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.parser.SpeechParser;
import com.rokuan.calliopecore.fr.sentence.Action;
import com.rokuan.calliopecore.fr.sentence.Verb;
import com.rokuan.calliopecore.fr.sentence.VerbAction;
import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Verb.ConjugationTense;
import com.rokuan.calliopecore.fr.sentence.Verb.Pronoun;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.IAction.ActionType;
import com.rokuan.calliopecore.sentence.IAction.Form;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.NominalGroup.GroupType;
import com.rokuan.calliopecore.sentence.structure.data.nominal.PersonObject;

public class PersonTest {
	@Test
	public void testMyNameIs(){
		FRWordBuffer words = new FRWordBuffer();
		Word appelle = new Word("appelle", WordType.VERB);
		
		Verb appeler = new Verb("appeler", 
				new VerbAction(null, new Action(ActionType.BE_NAMED), true));
		VerbConjugation appelleConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.JE, "appelle", appeler);
		
		Word me = new Word("Christophe", WordType.FIRSTNAME);
		
		appelle.setVerbInfo(appelleConjug);
		
		words.add(new Word("je", WordType.PERSONAL_PRONOUN));
		words.add(new Word("m", WordType.REFLEXIVE_PRONOUN));
		words.add(appelle);
		words.add(me);

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);
		
		assert(obj.getDirectObject().getGroupType() == GroupType.PERSON);
		
		System.out.println(((PersonObject)obj.getDirectObject()).name);
	}
}
