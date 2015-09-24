package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.parser.SpeechParser;
import com.rokuan.calliopecore.fr.sentence.Verb;
import com.rokuan.calliopecore.fr.sentence.Verb.ConjugationTense;
import com.rokuan.calliopecore.fr.sentence.Verb.Pronoun;
import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.Action;
import com.rokuan.calliopecore.sentence.Action.ActionType;
import com.rokuan.calliopecore.sentence.IVerbConjugation.Form;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject;
import com.rokuan.calliopecore.sentence.structure.QuestionObject;
import com.rokuan.calliopecore.sentence.structure.QuestionObject.QuestionType;
import com.rokuan.calliopecore.sentence.structure.data.nominal.ComplementObject;
import com.rokuan.calliopecore.sentence.structure.data.time.SingleTimeObject;

public class QuestionParseTest {
	@Test
	public void howManyTimeTest(){
		FRWordBuffer words = new FRWordBuffer();
		Word have = new Word("a", WordType.VERB, WordType.AUXILIARY);
		Verb toHave = new Verb("avoir", true, Action.ActionType.HAVE);
		VerbConjugation toHaveConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "avoir", toHave);
		toHaveConjug.setVerb(toHave);
		have.setVerbInfo(toHaveConjug);
		
		words.add(new Word("combien", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(new Word("de", WordType.PREPOSITION_OF));
		words.add(new Word("minutes"));
		words.add(new Word("y", WordType.PERSONAL_PRONOUN));
		words.add(have);
		words.add(new Word("t", WordType.CONJUGATION_LINK));
		words.add(new Word("il", WordType.PERSONAL_PRONOUN));
		words.add(new Word("dans", WordType.PREPOSITION_IN));
		words.add(new Word("une", WordType.DEFINITE_ARTICLE));
		words.add(new Word("heure", WordType.DATE_UNIT_HOUR, WordType.COMMON_NAME));

		// TODO:
		//InterpretationObject obj = new Parser().parseInterpretationObject(words);		
		//assertTrue(obj != null);
	}
	
	@Test
	public void whatIsTest(){
		FRWordBuffer words = new FRWordBuffer();
		Word is = new Word("est", WordType.AUXILIARY, WordType.VERB, WordType.COMMON_NAME);
		Verb toBe = new Verb("être", true, Action.ActionType.BE);
		VerbConjugation toBeConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "être", toBe);
		toBeConjug.setVerb(toBe);
		is.setVerbInfo(toBeConjug);
		
		words.add(new Word("quelle", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(is);
		words.add(new Word("la", WordType.DEFINITE_ARTICLE));
		words.add(new Word("température", WordType.COMMON_NAME));
		
		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);
		
		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		
		QuestionObject question = (QuestionObject)obj;
		
		assertEquals(question.questionType, QuestionType.WHAT);
	}
	
	@Test
	public void whatWillBeTest(){
		FRWordBuffer words = new FRWordBuffer();
		Word willBe = new Word("fera", WordType.VERB);
		Verb toBe = new Verb("faire", false, Action.ActionType.DO, ActionType.MAKE);
		VerbConjugation toBeConjug = new VerbConjugation(ConjugationTense.FUTURE, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "faire", toBe);
		toBeConjug.setVerb(toBe);
		willBe.setVerbInfo(toBeConjug);
		
		words.add(new Word("quel", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(new Word("temps", WordType.COMMON_NAME));
		words.add(willBe);
		words.add(new Word("t", WordType.CONJUGATION_LINK));
		words.add(new Word("il", WordType.PERSONAL_PRONOUN));
		words.add(new Word("demain", WordType.DATE));
		
		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);
		
		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		
		QuestionObject question = (QuestionObject)obj;
		
		assertEquals(question.questionType, QuestionType.WHAT);
		
		ComplementObject complement = (ComplementObject)question.what;

		assertTrue(question.action.does(ActionType.DO));
		assertEquals(complement.object, "temps");
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		
		Date tomorrow = calendar.getTime();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(format.format(tomorrow), format.format(((SingleTimeObject)obj.when).date));
	}
	
	@Test
	public void whatTimeIsItTest(){
		FRWordBuffer words = new FRWordBuffer();
		Word is = new Word("est", WordType.AUXILIARY, WordType.VERB);
		Verb toBe = new Verb("être", true, Action.ActionType.BE);
		VerbConjugation toBeConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "être", toBe);
		toBeConjug.setVerb(toBe);
		is.setVerbInfo(toBeConjug);
		
		words.add(new Word("quelle", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(new Word("heure", WordType.COMMON_NAME));
		words.add(is);
		words.add(new Word("il", WordType.PERSONAL_PRONOUN));
		
		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);
		
		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		
		QuestionObject question = (QuestionObject)obj;
		
		assertEquals(question.questionType, QuestionType.WHAT);
	}
}
