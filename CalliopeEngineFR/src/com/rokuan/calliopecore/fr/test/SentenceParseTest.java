package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.rokuan.calliopecore.fr.parser.SpeechParser;
import com.rokuan.calliopecore.parser.WordBuffer;
import com.rokuan.calliopecore.sentence.Action;
import com.rokuan.calliopecore.sentence.CityInfo;
import com.rokuan.calliopecore.sentence.CustomObject;
import com.rokuan.calliopecore.sentence.CustomPerson;
import com.rokuan.calliopecore.sentence.PlacePreposition;
import com.rokuan.calliopecore.sentence.TransportInfo;
import com.rokuan.calliopecore.sentence.Verb;
import com.rokuan.calliopecore.sentence.Verb.Pronoun;
import com.rokuan.calliopecore.sentence.VerbConjugation;
import com.rokuan.calliopecore.sentence.WayPreposition;
import com.rokuan.calliopecore.sentence.Word;
import com.rokuan.calliopecore.sentence.Verb.ConjugationTense;
import com.rokuan.calliopecore.sentence.Verb.Form;
import com.rokuan.calliopecore.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject.RequestType;
import com.rokuan.calliopecore.sentence.structure.QuestionObject;
import com.rokuan.calliopecore.sentence.structure.QuestionObject.QuestionType;
import com.rokuan.calliopecore.sentence.structure.content.IPlaceObject;
import com.rokuan.calliopecore.sentence.structure.content.IVerbalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.AdditionalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.AdditionalPerson;
import com.rokuan.calliopecore.sentence.structure.data.nominal.CityObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.ComplementObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.PronounTarget;
import com.rokuan.calliopecore.sentence.structure.data.nominal.NominalGroup.GroupType;
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
		WordBuffer words = new WordBuffer();
		Word go = new Word("aller", Word.WordType.VERB, WordType.COMMON_NAME);
		Verb toGo = new Verb("aller", Action.VerbAction.GO, false);
		VerbConjugation toGoConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INFINITIVE, null, "aller", toGo);		
		toGoConjug.setVerb(toGo);
		go.setVerbInfo(toGoConjug);
		Word to = new Word("à", WordType.PLACE_PREPOSITION);
		to.setPlacePreposition(new PlacePreposition("à", PlaceContext.TO, PlaceType.NAMED_PLACE, PlaceType.CITY));
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

		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertEquals(obj.action, Action.VerbAction.GO);

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.HOW);

		IPlaceObject place = obj.where;
		NamedPlaceObject monument = (NamedPlaceObject)place;
		
		assertEquals(monument.name, "Mairie");
		assertEquals(((TransportObject)obj.how).transportType, TransportType.CAR);
	}

	@Test
	public void testGoTo2(){
		WordBuffer words = new WordBuffer();
		Word go = new Word("aller", Word.WordType.VERB);
		Verb toGo = new Verb("aller", Action.VerbAction.GO, false);
		VerbConjugation toGoConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INFINITIVE, null, "aller", toGo);
		Word to = new Word("à", WordType.PLACE_PREPOSITION);
		Word by = new Word("en", WordType.WAY_PREPOSITION);
		Word car = new Word("voiture", WordType.COMMON_NAME, WordType.MEAN_OF_TRANSPORT);
		
		toGoConjug.setVerb(toGo);
		go.setVerbInfo(toGoConjug);
		to.setPlacePreposition(new PlacePreposition("à", PlaceContext.TO, PlaceType.NAMED_PLACE, PlaceType.CITY));
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

		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertEquals(obj.action, Action.VerbAction.GO);

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.HOW);

		IPlaceObject place = obj.where;
		
		assertEquals(place.getPlaceType(), PlaceType.NAMED_PLACE);

		//assertEquals(((ComplementObject)obj.how).object, "voiture");
		assertEquals(((TransportObject)obj.how).transportType, TransportType.CAR);
	}
	
	@Test
	public void testGoTo3(){
		WordBuffer words = new WordBuffer();
		Word go = new Word("aller", Word.WordType.VERB, WordType.COMMON_NAME);
		Verb toGo = new Verb("aller", Action.VerbAction.GO, false);
		VerbConjugation toGoConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INFINITIVE, null, "aller", toGo);
		Word paris = new Word("Paris", WordType.CITY);
		Word to = new Word("à", WordType.PLACE_PREPOSITION);
		to.setPlacePreposition(new PlacePreposition("à", PlaceContext.TO, PlaceType.NAMED_PLACE, PlaceType.CITY));
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

		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertEquals(obj.action, Action.VerbAction.GO);

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.HOW);

		IPlaceObject place = obj.where;
		
		CityObject city = (CityObject)place;
		
		assertEquals(city.city.getName(), "Paris");
		assertEquals(((TransportObject)obj.how).transportType, TransportType.PLANE);
	}
	
	@Test
	public void testGoTo4(){
		WordBuffer words = new WordBuffer();
		Word go = new Word("aller", Word.WordType.VERB, WordType.COMMON_NAME);
		Verb toGo = new Verb("aller", Action.VerbAction.GO, false);
		VerbConjugation toGoConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INFINITIVE, null, "aller", toGo);
		Word paris = new Word("Paris", WordType.CITY);
		Word to = new Word("à", WordType.PLACE_PREPOSITION);
		to.setPlacePreposition(new PlacePreposition("à", PlaceContext.TO, PlaceType.NAMED_PLACE, PlaceType.CITY));
		Word by = new Word("à", WordType.WAY_PREPOSITION);
		Word walk = new Word("pied", WordType.COMMON_NAME, WordType.MEAN_OF_TRANSPORT);
		
		toGoConjug.setVerb(toGo);
		go.setVerbInfo(toGoConjug);
		by.setWayPreposition(new WayPreposition("à", WayContext.BY, WayType.TRANSPORT));
		walk.setTransportInfo(new TransportInfo("pied", TransportType.WALK));

		paris.setCityInfo(new CityInfo("Paris", 48.8564528, 2.3524282));

		words.add(new Word("comment", WordType.INTERROGATIVE_PRONOUN));
		words.add(go);
		words.add(to);
		words.add(paris);
		words.add(by);
		words.add(walk);

		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertEquals(obj.action, Action.VerbAction.GO);

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.HOW);

		IPlaceObject place = obj.where;
		
		CityObject city = (CityObject)place;
		
		assertEquals(city.city.getName(), "Paris");
		assertEquals(((TransportObject)obj.how).transportType, TransportType.WALK);
	}

	@Test
	public void testWhoIs(){
		WordBuffer words = new WordBuffer();

		Word be = new Word("est", Word.WordType.VERB);
		Verb toBe = new Verb("être", Action.VerbAction.BE, true);
		VerbConjugation toBeConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "être", toBe);		
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

		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);

		assertEquals(obj.getRequestType(), InterpretationObject.RequestType.QUESTION);
		assertEquals(obj.action, Action.VerbAction.BE);

		QuestionObject question = (QuestionObject)obj;
		assertEquals(question.questionType, QuestionType.WHO);
		//assertEquals(question.what.getGroupType(), GroupType.COMPLEMENT);
		assertEquals(question.what.getGroupType(), GroupType.PERSON);

		/*ComplementObject compl = (ComplementObject)question.what;
		assertEquals(compl.object, "Arnold Schwarzenegger");*/
		assertEquals(((AdditionalPerson)question.what).person.getName(), "Arnold Schwarzenegger");
	}

	@Test
	public void testDoubleDirectObject(){
		WordBuffer words = new WordBuffer();

		Word find = new Word("trouve", WordType.VERB);
		Verb toFind = new Verb("trouver", Action.VerbAction.FIND, false);
		VerbConjugation toFindConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.IMPERATIVE, Pronoun.TU, "trouver", toFind);
		toFindConjug.setVerb(toFind);
		find.setVerbInfo(toFindConjug);

		words.add(find);
		words.add(new Word("moi", WordType.TARGET_PRONOUN));
		words.add(new Word("des", WordType.INDEFINITE_ARTICLE, WordType.PREPOSITION_OF));
		words.add(new Word("vidéos", WordType.COMMON_NAME));
		words.add(new Word("de", WordType.PREPOSITION_OF));
		words.add(new Word("chats", WordType.COMMON_NAME));

		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);

		ComplementObject compl = (ComplementObject)obj.what;
		assertEquals(compl.object, "vidéos");
		assert (compl.getNominalSecondObject() != null);
		assertEquals(((ComplementObject)compl.getNominalSecondObject()).object, "chats");
	}

	@Test
	public void testResultQuestion(){
		WordBuffer words = new WordBuffer();

		Word make = new Word("fait", WordType.VERB);
		Verb toMake = new Verb("faire", Action.VerbAction.DO__MAKE, false);
		VerbConjugation toMakeConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "faire", toMake);
		toMakeConjug.setVerb(toMake);
		make.setVerbInfo(toMakeConjug);

		words.add(new Word("quelle", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(new Word("température", WordType.COMMON_NAME));
		words.add(make);
		words.add(new Word("il", WordType.PERSONAL_PRONOUN));

		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);

		ComplementObject compl = (ComplementObject)obj.what;
		assertEquals(obj.action, Action.VerbAction.DO__MAKE);
		assertEquals(((PronounTarget)obj.subject).pronoun, com.rokuan.calliopecore.sentence.Type.Pronoun.IL_ELLE_ON);
		assertEquals(compl.object, "température");
	}

	@Test
	public void testTrapQuestion(){
		WordBuffer words = new WordBuffer();

		Word make = new Word("fera", WordType.VERB);
		Verb toMake = new Verb("faire", Action.VerbAction.DO__MAKE, false);
		VerbConjugation toMakeConjug = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.IL_ELLE_ON, "faire", toMake);
		toMakeConjug.setVerb(toMake);
		make.setVerbInfo(toMakeConjug);

		words.add(new Word("quel", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(new Word("temps", WordType.COMMON_NAME));
		words.add(make);
		words.add(new Word("t", WordType.CONJUGATION_LINK));
		words.add(new Word("il", WordType.PERSONAL_PRONOUN));
		words.add(new Word("demain", WordType.DATE));

		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);

		ComplementObject compl = (ComplementObject)obj.what;
		assertEquals(obj.action, Action.VerbAction.DO__MAKE);
		assertEquals(((PronounTarget)obj.subject).pronoun, com.rokuan.calliopecore.sentence.Type.Pronoun.IL_ELLE_ON);
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
		WordBuffer words = new WordBuffer();
		String objectName = "lumières de la cuisine";
		Word light = new Word(objectName, WordType.OBJECT);
		Word switchOff = new Word("éteinds", WordType.VERB);
		Verb toSwitchOff = new Verb("éteindre", Action.VerbAction.TURN_OFF, false);
		VerbConjugation switchConjugation = new VerbConjugation(ConjugationTense.PRESENT, Form.IMPERATIVE, Pronoun.TU, "éteinds", toSwitchOff);
		
		switchOff.setVerbInfo(switchConjugation);		
		light.setCustomObject(new CustomObject(objectName, "LIGHT_KITCHEN"));
		
		words.add(switchOff);
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("4", WordType.NUMBER));
		words.add(light);
		
		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);
		
		assertEquals(obj.getRequestType(), RequestType.ORDER);
		assertEquals(obj.action, Action.VerbAction.TURN_OFF);
		
		assertEquals(obj.what.getGroupType(), GroupType.OBJECT);
		
		AdditionalObject customObject = (AdditionalObject)obj.what;
		
		assertEquals(customObject.object.getContent(), objectName);
	}
	
	@Test
	public void testAffirmativeSentence(){
		WordBuffer words = new WordBuffer();
		Word hate = new Word("détestent", WordType.VERB);
		Verb toHate = new Verb("détester", Action.VerbAction.HATE, false);
		VerbConjugation hateConjugation = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.ILS_ELLES, "détestent", toHate);
		
		hate.setVerbInfo(hateConjugation);
		
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("chiens", WordType.COMMON_NAME));
		words.add(hate);
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("chats", WordType.COMMON_NAME));
		
		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);
		
		assertEquals(obj.getRequestType(), RequestType.AFFIRMATION);
		assertEquals(obj.subject.getGroupType(), GroupType.COMPLEMENT);
	}
	
	@Test
	public void testAffirmativeSentenceWithVerbalSecondObject(){
		WordBuffer words = new WordBuffer();
		Word sont = new Word("sont", WordType.VERB, WordType.AUXILIARY);
		Word ai = new Word("ai", WordType.VERB, WordType.AUXILIARY);
		Word envoyees = new Word("envoyées", WordType.VERB, WordType.AUXILIARY);
		
		Verb avoir = new Verb("avoir", Action.VerbAction.HAVE, true);
		VerbConjugation conjugAvoir = new VerbConjugation(ConjugationTense.PRESENT, Form.INDICATIVE, Pronoun.JE, "ai", avoir);
		ai.setVerbInfo(conjugAvoir);
		Verb envoyer = new Verb("envoyer", Action.VerbAction.SEND, false);
		VerbConjugation conjugEnvoyer = new VerbConjugation(ConjugationTense.PAST, Form.PARTICIPLE, null, "envoyées", envoyer);
		envoyees.setVerbInfo(conjugEnvoyer);
		
		words.add(new Word("où", WordType.INTERROGATIVE_ADJECTIVE));
		words.add(sont);
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("lettres", WordType.COMMON_NAME));
		words.add(new Word("que", WordType.PREPOSITION));
		words.add(new Word("je", WordType.PERSONAL_PRONOUN));
		words.add(new Word("t", WordType.TARGET_PRONOUN));
		words.add(ai);
		words.add(envoyees);
		words.add(new Word("hier", WordType.DATE));
		
		InterpretationObject obj = new SpeechParser(null).parseWordBuffer(words);
		
		assertEquals(obj.getRequestType(), RequestType.QUESTION);
		assertEquals(obj.what.getGroupType(), GroupType.COMPLEMENT);
		
		ComplementObject compl = (ComplementObject)obj.what;
		
		assertEquals(compl.object, "lettres");
		assertNotNull(compl.getVerbalSecondObject());
		
		IVerbalObject verbal = compl.getVerbalSecondObject();
		
		assertEquals(verbal.getAction(), Action.VerbAction.SEND);
	}
	
	@Test
	public void testAffirmativeUnitSentence(){
		
	}
}
