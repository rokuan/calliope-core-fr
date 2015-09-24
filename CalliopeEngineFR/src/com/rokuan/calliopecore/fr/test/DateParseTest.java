package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.Test;

import com.rokuan.calliopecore.fr.data.DateConverter;
import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.parser.SpeechParser;
import com.rokuan.calliopecore.fr.sentence.Verb;
import com.rokuan.calliopecore.fr.sentence.Verb.ConjugationTense;
import com.rokuan.calliopecore.fr.sentence.Verb.Pronoun;
import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.Action;
import com.rokuan.calliopecore.sentence.IVerbConjugation.Form;
import com.rokuan.calliopecore.sentence.TimePreposition;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject;
import com.rokuan.calliopecore.sentence.structure.content.ITimeObject;
import com.rokuan.calliopecore.sentence.structure.data.time.SingleTimeObject;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.TimeContext;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.DateDefinition;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.TimeType;
import com.rokuan.calliopecore.sentence.structure.data.time.TimePeriodObject;

public class DateParseTest {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	@Test
	public void testParseFromToDate(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("du", Word.WordType.PREPOSITION_FROM));
		words.add(new Word("1", Word.WordType.NUMBER));
		words.add(new Word("janvier", Word.WordType.DATE_MONTH));
		words.add(new Word("2012", WordType.NUMBER));
		words.add(new Word("au", Word.WordType.PREPOSITION_TO));
		words.add(new Word("7", Word.WordType.NUMBER));
		words.add(new Word("mars", Word.WordType.DATE_MONTH));
		words.add(new Word("2015", Word.WordType.NUMBER));

		ITimeObject dateObj = DateConverter.parseTimeAdverbial(words);

		assert (dateObj != null);

		assertEquals(dateObj.getTimeType(), TimeAdverbial.TimeType.PERIOD);

		TimePeriodObject period = (TimePeriodObject)dateObj;

		System.out.println("--- testParseFromToDate");
		System.out.println(dateFormat.format(period.from));
		System.out.println(dateFormat.format(period.to));
		System.out.println();
	}

	@Test
	public void testParseBetweenDate(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("entre", Word.WordType.PREPOSITION_BETWEEN));
		words.add(new Word("le", Word.WordType.DEFINITE_ARTICLE));
		words.add(new Word("1er", Word.WordType.NUMERICAL_POSITION));
		words.add(new Word("mai", Word.WordType.DATE_MONTH));
		words.add(new Word("2012", WordType.NUMBER));
		words.add(new Word("et", Word.WordType.PREPOSITION_AND));
		words.add(new Word("le", Word.WordType.DEFINITE_ARTICLE));
		words.add(new Word("4", Word.WordType.NUMBER));
		words.add(new Word("septembre", Word.WordType.DATE_MONTH));
		words.add(new Word("2013", Word.WordType.NUMBER));

		ITimeObject dateObj = DateConverter.parseTimeAdverbial(words);

		assert (dateObj != null);

		assertEquals(dateObj.getTimeType(), TimeAdverbial.TimeType.PERIOD);

		TimePeriodObject period = (TimePeriodObject)dateObj;

		System.out.println("--- testParseBetweenDate");
		System.out.println(dateFormat.format(period.from));
		System.out.println(dateFormat.format(period.to));
		System.out.println();
	}
	
	@Test
	public void testParseTimeFull(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("à", Word.WordType.PREPOSITION_AT));
		words.add(new Word("5h30", Word.WordType.TIME));

		ITimeObject dateObj = DateConverter.parseTimeAdverbial(words);

		assertEquals(dateObj.getTimeType(), TimeAdverbial.TimeType.SINGLE);

		SingleTimeObject fixedTime = (SingleTimeObject)dateObj;

		assertEquals(fixedTime.dateDefinition, DateDefinition.TIME_ONLY);

		System.out.println("--- testParseTimeFull");
		System.out.println(dateFormat.format(fixedTime.date));
		System.out.println();
	}

	@Test
	public void testParseTimeHourOnly(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("à", Word.WordType.PREPOSITION_AT));
		words.add(new Word("5h", Word.WordType.TIME));

		ITimeObject dateObj = DateConverter.parseTimeAdverbial(words);

		assertEquals(dateObj.getTimeType(), TimeAdverbial.TimeType.SINGLE);

		SingleTimeObject fixedTime = (SingleTimeObject)dateObj;

		assertEquals(fixedTime.dateDefinition, DateDefinition.TIME_ONLY);

		System.out.println("--- testParseTimeHourOnly");
		System.out.println(dateFormat.format(fixedTime.date));
		System.out.println();
	}

	@Test
	public void testParseTimeRelativeMinutesQuarter(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("à", Word.WordType.PREPOSITION_AT));
		words.add(new Word("5", Word.WordType.NUMBER));
		words.add(new Word("heures", Word.WordType.DATE_UNIT_HOUR));
		words.add(new Word("moins", Word.WordType.SUPERLATIVE));
		words.add(new Word("le", WordType.DEFINITE_ARTICLE));
		words.add(new Word("quart", WordType.TIME));

		ITimeObject dateObj = DateConverter.parseTimeAdverbial(words);

		assertEquals(dateObj.getTimeType(), TimeAdverbial.TimeType.SINGLE);

		SingleTimeObject fixedTime = (SingleTimeObject)dateObj;

		assertEquals(fixedTime.dateDefinition, DateDefinition.TIME_ONLY);

		System.out.println("--- testParseTimeRelativeMinutesQuarter");
		System.out.println(dateFormat.format(fixedTime.date));
		System.out.println();
	}

	@Test
	public void testParseTimeRelativeMinutesHalf(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("à", Word.WordType.PREPOSITION_AT));
		words.add(new Word("5h", Word.WordType.NUMBER));
		words.add(new Word("et", Word.WordType.PREPOSITION_AND));
		words.add(new Word("demi", Word.WordType.TIME));

		ITimeObject dateObj = DateConverter.parseTimeAdverbial(words);

		assertEquals(dateObj.getTimeType(), TimeAdverbial.TimeType.SINGLE);

		SingleTimeObject fixedTime = (SingleTimeObject)dateObj;

		assertEquals(fixedTime.dateDefinition, DateDefinition.TIME_ONLY);

		System.out.println("--- testParseTimeRelativeMinutesHalf");
		System.out.println(dateFormat.format(fixedTime.date));
		System.out.println();
	}

	@Test
	public void testParseDateAndTime(){
		FRWordBuffer words = new FRWordBuffer();

		words.add(new Word("le", Word.WordType.DEFINITE_ARTICLE));
		words.add(new Word("1er", Word.WordType.NUMERICAL_POSITION));
		words.add(new Word("mai", Word.WordType.DATE_MONTH));
		words.add(new Word("2012", WordType.NUMBER));		
		words.add(new Word("à", Word.WordType.PREPOSITION_AT));
		words.add(new Word("midi", Word.WordType.DATE_UNIT_HOUR));
		words.add(new Word("moins", Word.WordType.PREPOSITION_AND));
		words.add(new Word("10", Word.WordType.NUMBER));

		ITimeObject dateObj = DateConverter.parseTimeAdverbial(words);

		assertEquals(dateObj.getTimeType(), TimeAdverbial.TimeType.SINGLE);

		SingleTimeObject fixedTime = (SingleTimeObject)dateObj;

		assertEquals(fixedTime.dateDefinition, DateDefinition.DATE_AND_TIME);

		System.out.println("--- testParseDateAndTime");
		System.out.println(dateFormat.format(fixedTime.date));
		System.out.println();
	}
	
	@Test
	public void testParseWhenTime(){
		FRWordBuffer words = new FRWordBuffer();

		Word futureBe = new Word("sera", Word.WordType.VERB);
		Verb toBeVerb = new Verb("être", true, Action.ActionType.BE);
		VerbConjugation toBeConjug = new VerbConjugation(ConjugationTense.FUTURE, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "sera", toBeVerb);		
		toBeConjug.setVerb(toBeVerb);
		futureBe.setVerbInfo(toBeConjug);
		
		Word imperativeAlert = new Word("préviens", Word.WordType.VERB);
		Verb toAlert = new Verb("prévenir", false, Action.ActionType.ALERT);
		VerbConjugation alertConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.IMPERATIVE, Pronoun.TU, "préviens", toAlert);		
		alertConjug.setVerb(toAlert);
		imperativeAlert.setVerbInfo(alertConjug);		
		
		words.add(imperativeAlert);
		words.add(new Word("moi", WordType.TARGET_PRONOUN));
		words.add(new Word("quand", Word.WordType.TIME_PREPOSITION));
		words.add(new Word("il", Word.WordType.PERSONAL_PRONOUN));		
		words.add(futureBe);
		words.add(new Word("15h47", WordType.TIME));
		
		InterpretationObject object = new SpeechParser(null).parseFRWordBuffer(words);
		//ITimeObject dateObj = DateConverter.parseTimeAdverbial(words);
		ITimeObject dateObj = object.when;
		
		assertEquals(dateObj.getTimeType(), TimeAdverbial.TimeType.SINGLE);
		
		SingleTimeObject fixedHour = (SingleTimeObject)dateObj;
		
		assertEquals(fixedHour.dateDefinition, DateDefinition.TIME_ONLY);
		
		System.out.println("--- testParseWhenTime");
		System.out.println(timeFormat.format(fixedHour.date));
		System.out.println();
	}
	
	@Test
	public void testParseWithPreposition(){
		FRWordBuffer words = new FRWordBuffer();
		Word before = new Word("avant", WordType.TIME_PREPOSITION);
		before.setTimePreposition(new TimePreposition("avant", TimeContext.BEFORE));
		
		words.add(before);
		words.add(new Word("le", Word.WordType.DEFINITE_ARTICLE));
		words.add(new Word("1er", Word.WordType.NUMERICAL_POSITION));
		words.add(new Word("mai", Word.WordType.DATE_MONTH));
		words.add(new Word("2012", WordType.NUMBER));

		ITimeObject time = DateConverter.parseTimeAdverbial(words);
		
		assertEquals(time.getTimeType(), TimeType.SINGLE);
		
		SingleTimeObject single = (SingleTimeObject)time;
		
		assertEquals(single.getTimePreposition(), TimeContext.BEFORE);		
	}
	
	@Test
	public void testParseWithContractedPreposition(){
		FRWordBuffer words = new FRWordBuffer();
		Word until = new Word("jusqu'au", WordType.TIME_PREPOSITION, WordType.CONTRACTED);
		until.setTimePreposition(new TimePreposition("jusqu'au", TimeContext.UNTIL));
		
		words.add(until);
		//words.add(new Word("le", Word.WordType.DEFINITE_ARTICLE));
		words.add(new Word("1er", Word.WordType.NUMERICAL_POSITION));
		words.add(new Word("mai", Word.WordType.DATE_MONTH));
		words.add(new Word("2012", WordType.NUMBER));

		ITimeObject time = DateConverter.parseTimeAdverbial(words);
		
		assertEquals(time.getTimeType(), TimeType.SINGLE);
		
		SingleTimeObject single = (SingleTimeObject)time;
		
		assertEquals(single.getTimePreposition(), TimeContext.UNTIL);
	}
	
	@Test
	public void testParseVerbalDate(){
		// TODO:
	}
}
