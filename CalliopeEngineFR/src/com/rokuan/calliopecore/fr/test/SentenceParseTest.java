package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.parser.SpeechParser;
import com.rokuan.calliopecore.fr.sentence.PlacePreposition;
import com.rokuan.calliopecore.fr.sentence.Verb;
import com.rokuan.calliopecore.fr.sentence.Verb.ConjugationTense;
import com.rokuan.calliopecore.fr.sentence.Verb.Pronoun;
import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.fr.sentence.WayPreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.Action.ActionType;
import com.rokuan.calliopecore.sentence.CityInfo;
import com.rokuan.calliopecore.sentence.CustomObject;
import com.rokuan.calliopecore.sentence.CustomPerson;
import com.rokuan.calliopecore.sentence.IPronoun.PronounSource;
import com.rokuan.calliopecore.sentence.IVerbConjugation.Form;
import com.rokuan.calliopecore.sentence.TransportInfo;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject.RequestType;
import com.rokuan.calliopecore.sentence.structure.QuestionObject;
import com.rokuan.calliopecore.sentence.structure.QuestionObject.QuestionType;
import com.rokuan.calliopecore.sentence.structure.content.IPlaceObject;
import com.rokuan.calliopecore.sentence.structure.content.IVerbalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.AdditionalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.AdditionalPerson;
import com.rokuan.calliopecore.sentence.structure.data.nominal.CityObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.NameObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.NominalGroup.GroupType;
import com.rokuan.calliopecore.sentence.structure.data.nominal.PronounSubject;
import com.rokuan.calliopecore.sentence.structure.data.place.NamedPlaceObject;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceContext;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceType;
import com.rokuan.calliopecore.sentence.structure.data.time.SingleTimeObject;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial;
import com.rokuan.calliopecore.sentence.structure.data.way.TransportObject;
import com.rokuan.calliopecore.sentence.structure.data.way.TransportObject.TransportType;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayContext;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayType;

public class SentenceParseTest {
	@Test
	public void testGoTo(){
		FRWordBuffer words = new FRWordBuffer();
		Word go = new Word("aller", Word.WordType.VERB, WordType.COMMON_NAME);
		Verb toGo = new Verb("aller", false, ActionType.GO);
		VerbConjugation toGoConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INFINITIVE, null, "aller", toGo);		
		toGoConjug.setVerb(toGo);
		go.setVerbInfo(toGoConjug);
		Word to = new Word("�", WordType.PLACE_PREPOSITION);
		to.setPlacePreposition(new PlacePreposition("�", PlaceContext.TO, PlaceType.NAMED_PLACE, PlaceType.CITY));
		Word by = new Word("en", WordType.WAY_PREPOSITION);
		Word car = new Word("voiture", WordType.COMMON_NAME, WordType.MEAN_OF_TRANSPORT);

		by.setWayPreposition(new WayPreposition("en", WayContext.BY, WayType.TRANSPORT));
		car.setTransportInfo(new TransportInfo("voiture", TransportType.CAR));		

		words.add(new Word("comment", WordType.INTERROGATIVE_PRONOUN));
		words.add(go);
		words.add(to);
		words.add(new Word("la", WordType.DEFINITE_ARTICLE));
		words.add(new Word("Mairie", WordType.PLACE_TYPE, WordType.PROPER_NAME, WordType.COMMON_NAME));
		words.add(new Word("de", WordType.PREPOSITION_OF));
		words.add(new Word("Paris", WordType.PROPER_NAME, WordType.CITY));
		words.add(by);
		words.add(car);

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertTrue(obj.action.does(ActionType.GO));

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.HOW);

		IPlaceObject place = obj.where;
		NamedPlaceObject monument = (NamedPlaceObject)place;
		
		assertEquals(monument.name, "Mairie");
		assertEquals(((TransportObject)obj.how).transportType, TransportType.CAR);
	}

	@Test
	public void testGoTo2(){
		FRWordBuffer words = new FRWordBuffer();
		Word go = new Word("aller", Word.WordType.VERB);
		Verb toGo = new Verb("aller", false, ActionType.GO);
		VerbConjugation toGoConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INFINITIVE, null, "aller", toGo);
		Word to = new Word("�", WordType.PLACE_PREPOSITION);
		Word by = new Word("en", WordType.WAY_PREPOSITION);
		Word car = new Word("voiture", WordType.COMMON_NAME, WordType.MEAN_OF_TRANSPORT);
		
		toGoConjug.setVerb(toGo);
		go.setVerbInfo(toGoConjug);
		to.setPlacePreposition(new PlacePreposition("�", PlaceContext.TO, PlaceType.NAMED_PLACE, PlaceType.CITY));
		by.setWayPreposition(new WayPreposition("en", WayContext.BY, WayType.TRANSPORT));
		car.setTransportInfo(new TransportInfo("voiture", TransportType.CAR));

		words.add(new Word("comment", WordType.INTERROGATIVE_PRONOUN));
		words.add(go);
		words.add(to);
		words.add(new Word("la", WordType.DEFINITE_ARTICLE));
		words.add(new Word("Tour", WordType.PROPER_NAME, WordType.COMMON_NAME));
		words.add(new Word("Eiffel", WordType.PROPER_NAME));
		words.add(by);
		words.add(car);

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertTrue(obj.action.does(ActionType.GO));

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.HOW);

		IPlaceObject place = obj.where;
		
		assertEquals(place.getPlaceType(), PlaceType.NAMED_PLACE);

		//assertEquals(((NameObject)obj.how).object, "voiture");
		assertEquals(((TransportObject)obj.how).transportType, TransportType.CAR);
	}
	
	@Test
	public void testGoTo3(){
		FRWordBuffer words = new FRWordBuffer();
		Word go = new Word("aller", Word.WordType.VERB, WordType.COMMON_NAME);
		Verb toGo = new Verb("aller", false, ActionType.GO);
		VerbConjugation toGoConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INFINITIVE, null, "aller", toGo);
		Word paris = new Word("Paris", WordType.CITY);
		Word to = new Word("�", WordType.PLACE_PREPOSITION);
		to.setPlacePreposition(new PlacePreposition("�", PlaceContext.TO, PlaceType.NAMED_PLACE, PlaceType.CITY));
		Word by = new Word("par", WordType.WAY_PREPOSITION);
		Word plane = new Word("avion", WordType.COMMON_NAME, WordType.MEAN_OF_TRANSPORT);
		
		toGoConjug.setVerb(toGo);
		go.setVerbInfo(toGoConjug);
		by.setWayPreposition(new WayPreposition("en", WayContext.BY, WayType.TRANSPORT));
		plane.setTransportInfo(new TransportInfo("avion", TransportType.PLANE));

		paris.setCityInfo(new CityInfo("Paris", 48.8564528, 2.3524282));

		words.add(new Word("comment", WordType.INTERROGATIVE_PRONOUN));
		words.add(go);
		words.add(to);
		words.add(paris);
		words.add(by);
		words.add(plane);

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertTrue(obj.action.does(ActionType.GO));

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.HOW);

		IPlaceObject place = obj.where;
		
		CityObject city = (CityObject)place;
		
		assertEquals(city.city.getName(), "Paris");
		assertEquals(((TransportObject)obj.how).transportType, TransportType.PLANE);
	}
	
	@Test
	public void testGoTo4(){
		FRWordBuffer words = new FRWordBuffer();
		Word go = new Word("aller", Word.WordType.VERB, WordType.COMMON_NAME);
		Verb toGo = new Verb("aller", false, ActionType.GO);
		VerbConjugation toGoConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INFINITIVE, null, "aller", toGo);
		Word paris = new Word("Paris", WordType.CITY);
		Word to = new Word("�", WordType.PLACE_PREPOSITION);
		to.setPlacePreposition(new PlacePreposition("�", PlaceContext.TO, PlaceType.NAMED_PLACE, PlaceType.CITY));
		Word by = new Word("�", WordType.WAY_PREPOSITION);
		Word walk = new Word("pied", WordType.COMMON_NAME, WordType.MEAN_OF_TRANSPORT);
		
		toGoConjug.setVerb(toGo);
		go.setVerbInfo(toGoConjug);
		by.setWayPreposition(new WayPreposition("�", WayContext.BY, WayType.TRANSPORT));
		walk.setTransportInfo(new TransportInfo("pied", TransportType.WALK));

		paris.setCityInfo(new CityInfo("Paris", 48.8564528, 2.3524282));

		words.add(new Word("comment", WordType.INTERROGATIVE_PRONOUN));
		words.add(go);
		words.add(to);
		words.add(paris);
		words.add(by);
		words.add(walk);

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertTrue(obj.action.does(ActionType.GO));

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.HOW);

		IPlaceObject place = obj.where;
		
		CityObject city = (CityObject)place;
		
		assertEquals(city.city.getName(), "Paris");
		assertEquals(((TransportObject)obj.how).transportType, TransportType.WALK);
	}

	@Test
	public void testWhoIs(){
		FRWordBuffer words = new FRWordBuffer();

		Word be = new Word("est", Word.WordType.VERB);
		Verb toBe = new Verb("�tre", true, ActionType.BE);
		VerbConjugation toBeConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "�tre", toBe);		
		toBeConjug.setVerb(toBe);
		be.setVerbInfo(toBeConjug);
		Word person = new Word("Arnold Schwarzenegger", WordType.PERSON);
		CustomPerson schwarzy = new CustomPerson("Arnold Schwarzenegger", "SCHWARZY");
		person.setCustomPerson(schwarzy);
		
		
		words.add(new Word("qui", WordType.INTERROGATIVE_PRONOUN));
		words.add(be);
		words.add(person);
		
		/*words.add(new Word("Arnold", WordType.FIRSTNAME));
		words.add(new Word("Schwarzenegger", WordType.PROPER_NAME));*/

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertTrue(obj.action.does(ActionType.BE));

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.WHO);
		//assertEquals(question.what.getGroupType(), GroupType.COMPLEMENT);
		assertEquals(question.what.getGroupType(), GroupType.PERSON);

		/*NameObject compl = (NameObject)question.what;
		assertEquals(compl.object, "Arnold Schwarzenegger");*/
		assertEquals(((AdditionalPerson)question.what).person.getName(), "Arnold Schwarzenegger");
	}

	@Test
	public void testDoubleDirectObject(){
		FRWordBuffer words = new FRWordBuffer();

		Word find = new Word("trouve", WordType.VERB);
		Verb toFind = new Verb("trouver", false, ActionType.FIND);
		VerbConjugation toFindConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.IMPERATIVE, Pronoun.TU, "trouver", toFind);
		toFindConjug.setVerb(toFind);
		find.setVerbInfo(toFindConjug);

		words.add(find);
		words.add(new Word("moi", WordType.TARGET_PRONOUN));
		words.add(new Word("des", WordType.INDEFINITE_ARTICLE, WordType.PREPOSITION_OF));
		words.add(new Word("vid�os", WordType.COMMON_NAME));
		words.add(new Word("de", WordType.PREPOSITION_OF));
		words.add(new Word("chats", WordType.COMMON_NAME));

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);

		NameObject compl = (NameObject)obj.what;
		assertEquals(compl.object, "vid�os");
		assert (compl.getNominalSecondObject() != null);
		assertEquals(((NameObject)compl.getNominalSecondObject()).object, "chats");
	}

	@Test
	public void testResultQuestion(){
		FRWordBuffer words = new FRWordBuffer();

		Word make = new Word("fait", WordType.VERB);
		Verb toMake = new Verb("faire", false, ActionType.DO, ActionType.MAKE);
		VerbConjugation toMakeConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "faire", toMake);
		toMakeConjug.setVerb(toMake);
		make.setVerbInfo(toMakeConjug);

		words.add(new Word("quelle", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(new Word("temp�rature", WordType.COMMON_NAME));
		words.add(make);
		words.add(new Word("il", WordType.PERSONAL_PRONOUN));

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);

		NameObject compl = (NameObject)obj.what;
		assertTrue(obj.action.does(ActionType.DO) && obj.action.does(ActionType.MAKE));
		assertEquals(((PronounSubject)obj.subject).pronoun.getSource(), PronounSource.HE);
		assertEquals(compl.object, "temp�rature");
	}

	@Test
	public void testTrapQuestion(){
		FRWordBuffer words = new FRWordBuffer();

		Word make = new Word("fera", WordType.VERB);
		Verb toMake = new Verb("faire", false, ActionType.DO, ActionType.MAKE);
		VerbConjugation toMakeConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "faire", toMake);
		toMakeConjug.setVerb(toMake);
		make.setVerbInfo(toMakeConjug);

		words.add(new Word("quel", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(new Word("temps", WordType.COMMON_NAME));
		words.add(make);
		words.add(new Word("t", WordType.CONJUGATION_LINK));
		words.add(new Word("il", WordType.PERSONAL_PRONOUN));
		words.add(new Word("demain", WordType.DATE));

		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);

		NameObject compl = (NameObject)obj.what;
		assertTrue(obj.action.does(ActionType.DO) && obj.action.does(ActionType.MAKE));
		assertEquals(((PronounSubject)obj.subject).pronoun.getSource(), PronounSource.HE);
		assertEquals(compl.object, "temps");
		assertEquals(obj.when.getTimeType(), TimeAdverbial.TimeType.SINGLE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date tomorrow = calendar.getTime();
		Date sentenceDate = ((SingleTimeObject)obj.when).date;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		assertEquals(formatter.format(tomorrow), formatter.format(sentenceDate));
	}
	
	@Test
	public void testObjectSentence(){
		FRWordBuffer words = new FRWordBuffer();
		String objectName = "lumi�res de la cuisine";
		Word light = new Word(objectName, WordType.OBJECT);
		Word switchOff = new Word("�teinds", WordType.VERB);
		Verb toSwitchOff = new Verb("�teindre", false, ActionType.TURN_OFF);
		VerbConjugation switchConjugation = new VerbConjugation(ConjugationTense.PRESENT, Form.IMPERATIVE, Pronoun.TU, "�teinds", toSwitchOff);
		
		switchOff.setVerbInfo(switchConjugation);		
		light.setCustomObject(new CustomObject(objectName, "LIGHT_KITCHEN"));
		
		words.add(switchOff);
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("4", WordType.NUMBER));
		words.add(light);
		
		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);
		
		assertEquals(obj.getRequestType(), RequestType.ORDER);
		assertTrue(obj.action.does(ActionType.TURN_OFF));
		
		assertEquals(obj.what.getGroupType(), GroupType.OBJECT);
		
		AdditionalObject customObject = (AdditionalObject)obj.what;
		
		assertEquals(customObject.object.getName(), objectName);
	}
	
	@Test
	public void testAffirmativeSentence(){
		FRWordBuffer words = new FRWordBuffer();
		Word hate = new Word("d�testent", WordType.VERB);
		Verb toHate = new Verb("d�tester", false, ActionType.HATE);
		VerbConjugation hateConjugation = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.ILS_ELLES, "d�testent", toHate);
		
		hate.setVerbInfo(hateConjugation);
		
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("chiens", WordType.COMMON_NAME));
		words.add(hate);
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("chats", WordType.COMMON_NAME));
		
		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);
		
		assertEquals(obj.getRequestType(), RequestType.AFFIRMATION);
		assertEquals(obj.subject.getGroupType(), GroupType.COMPLEMENT);
	}
	
	@Test
	public void testAffirmativeSentenceWithVerbalSecondObject(){
		FRWordBuffer words = new FRWordBuffer();
		Word sont = new Word("sont", WordType.VERB, WordType.AUXILIARY);
		Word ai = new Word("ai", WordType.VERB, WordType.AUXILIARY);
		Word envoyees = new Word("envoy�es", WordType.VERB, WordType.AUXILIARY);
		
		Verb avoir = new Verb("avoir", true, ActionType.HAVE);
		VerbConjugation conjugAvoir = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.JE, "ai", avoir);
		ai.setVerbInfo(conjugAvoir);
		Verb envoyer = new Verb("envoyer", false, ActionType.SEND);
		VerbConjugation conjugEnvoyer = new VerbConjugation(ConjugationTense.PAST, Form.PARTICIPLE, null, "envoy�es", envoyer);
		envoyees.setVerbInfo(conjugEnvoyer);
		Verb etre = new Verb("�tre", true, ActionType.BE);
		VerbConjugation conjugEtre = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.ILS_ELLES, "sont", etre);
		sont.setVerbInfo(conjugEtre);
		
		words.add(new Word("o�", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(sont);
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("lettres", WordType.COMMON_NAME));
		words.add(new Word("que", WordType.PREPOSITION));
		words.add(new Word("je", WordType.PERSONAL_PRONOUN));
		words.add(new Word("t", WordType.TARGET_PRONOUN));
		words.add(ai);
		words.add(envoyees);
		words.add(new Word("hier", WordType.DATE));
		
		InterpretationObject obj = new SpeechParser(null).parseFRWordBuffer(words);
		
		assertEquals(obj.getRequestType(), RequestType.QUESTION);
		assertEquals(obj.what.getGroupType(), GroupType.COMPLEMENT);
		
		NameObject compl = (NameObject)obj.what;
		
		assertEquals(compl.object, "lettres");
		assertNotNull(compl.getVerbalSecondObject());
		
		IVerbalObject verbal = compl.getVerbalSecondObject();
		
		assertTrue(verbal.getAction().does(ActionType.SEND));
	}
	
	@Test
	public void testAffirmativeUnitSentence(){
		
	}
}
