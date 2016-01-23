package com.rokuan.calliopecore.fr.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rokuan.calliopecore.fr.data.CountConverter;
import com.rokuan.calliopecore.fr.data.DateConverter;
import com.rokuan.calliopecore.fr.data.NominalGroupConverter;
import com.rokuan.calliopecore.fr.data.PlaceConverter;
import com.rokuan.calliopecore.fr.data.VerbConverter;
import com.rokuan.calliopecore.fr.data.WayConverter;
import com.rokuan.calliopecore.fr.parser.route.RouteTree;
import com.rokuan.calliopecore.fr.sentence.AdjectiveInfo;
import com.rokuan.calliopecore.fr.sentence.CharacterInfo;
import com.rokuan.calliopecore.fr.sentence.CityInfo;
import com.rokuan.calliopecore.fr.sentence.ColorInfo;
import com.rokuan.calliopecore.fr.sentence.CountryInfo;
import com.rokuan.calliopecore.fr.sentence.CustomMode;
import com.rokuan.calliopecore.fr.sentence.CustomObject;
import com.rokuan.calliopecore.fr.sentence.CustomPerson;
import com.rokuan.calliopecore.fr.sentence.CustomPlace;
import com.rokuan.calliopecore.fr.sentence.LanguageInfo;
import com.rokuan.calliopecore.fr.sentence.NameInfo;
import com.rokuan.calliopecore.fr.sentence.PlaceInfo;
import com.rokuan.calliopecore.fr.sentence.PlacePreposition;
import com.rokuan.calliopecore.fr.sentence.Pronoun;
import com.rokuan.calliopecore.fr.sentence.PurposePreposition;
import com.rokuan.calliopecore.fr.sentence.TimePreposition;
import com.rokuan.calliopecore.fr.sentence.TransportInfo;
import com.rokuan.calliopecore.fr.sentence.UnitInfo;
import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.fr.sentence.WayPreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.fr.structure.Question;
import com.rokuan.calliopecore.parser.AbstractParser;
import com.rokuan.calliopecore.sentence.ActionObject;
import com.rokuan.calliopecore.sentence.IAction;
import com.rokuan.calliopecore.sentence.IAction.Tense;
import com.rokuan.calliopecore.sentence.structure.AffirmationObject;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject;
import com.rokuan.calliopecore.sentence.structure.OrderObject;
import com.rokuan.calliopecore.sentence.structure.QuestionObject;
import com.rokuan.calliopecore.sentence.structure.QuestionObject.QuestionType;
import com.rokuan.calliopecore.sentence.structure.data.nominal.AbstractTarget;
import com.rokuan.calliopecore.sentence.structure.data.nominal.PronounSubject;


public final class SpeechParser implements AbstractParser {
	private WordDatabase db;
	private RouteTree tree; 

	public SpeechParser(WordDatabase database){
		db = database;
		tree = new RouteTree();
	}
	
	public RouteTree getRouteTree(){
		return tree;
	}

	@Override
	public InterpretationObject parseText(String text){
		InterpretationObject result = this.parseFRWordBuffer(this.lexSpeech(text));
		
		if(result != null){
			tree.run(result);
		}
		
		return result;
	}

	private final FRWordBuffer lexSpeech(String text){
		FRWordBuffer buffer = new FRWordBuffer();
		String[] words = text.split(" ");

		for(int i=0; i<words.length; i++){
			StringBuilder wordBuilder = new StringBuilder(words[i]);
			Word currentWord = getWord(words[i]);
			boolean shouldContinue = db.wordStartsWith(words[i]);

			if(currentWord != null && !shouldContinue){

			} else {
				if (!shouldContinue) {
					int charIndex = words[i].indexOf('\'');

					if (charIndex != -1) {
						String leftPart = words[i].substring(0, charIndex);
						String rightPart = words[i].substring(charIndex + 1, words[i].length());

						Word leftWord = getWord(leftPart);

						// Mot d'une autre langue
						if (leftWord == null) {
							buffer.add(new Word(words[i], Word.WordType.OTHER));
							continue;
						} else {
							buffer.add(leftWord);
							wordBuilder = new StringBuilder(rightPart);
							shouldContinue = true;
						}
					}

					String tmpPart = wordBuilder.toString();
					Word tmpWord = getWord(tmpPart);

					if (tmpWord == null) {
						String[] parts = tmpPart.split("-");

						for(String p: parts){
							Word partWord = getWord(p);

							if(partWord == null){
								buffer.add(new Word(words[i], Word.WordType.OTHER));
							} else {
								buffer.add(partWord);
							}
						}

						continue;
					} else {
						shouldContinue = true;
					}
				}

				int lastIndex = i;

				while (shouldContinue && lastIndex < words.length) {
					Word tmpWord = getWord(wordBuilder.toString());

					if(tmpWord != null) {
						currentWord = tmpWord;
						i = lastIndex;
					}

					lastIndex++;

					if (lastIndex < words.length) {
						wordBuilder.append(' ');
						wordBuilder.append(words[lastIndex]);
						shouldContinue = db.wordStartsWith(wordBuilder.toString());

						if (!shouldContinue) {
							lastIndex--;
						}
					}
				}
			}

			buffer.add(currentWord);
		}

		System.out.println("FRWordBuffer=" + buffer);

		return buffer;
	}

	public InterpretationObject parseFRWordBuffer(FRWordBuffer words){
		InterpretationObject inter = null;

		//if(words.syntaxStartsWith(Word.WordType.AUXILIARY, Word.WordType.PERSONAL_PRONOUN, Word.WordType.VERB)){
		if(words.syntaxStartsWith(SentencePattern.YES_NO_QUESTION_PATTERN)){
			// TODO: les Yes/No question au present (ex: Aimes-tu ...)
			QuestionObject qObj = new QuestionObject();

			qObj.questionType = QuestionType.YES_NO;

			if(words.getCurrentElement().isOfType(WordType.PERSONAL_PRONOUN)){
				qObj.target = new PronounSubject(Pronoun.parseTargetPronoun(words.getCurrentElement().getValue()));
				words.consume();
			}

			words.consume();	// AUXILIARY
			qObj.subject = new PronounSubject(Pronoun.parseSubjectPronoun(words.getCurrentElement().getValue()));
			words.consume();

			if(words.getCurrentElement().isOfType(WordType.CONJUGATION_LINK)){
				words.consume();
			}

			qObj.action = new ActionObject(Tense.PRESENT, words.getCurrentElement().getVerbInfo());
			words.consume();

			//qObj.what = parseNameObject(words);
			parseObject(words, qObj);

			inter = qObj;			
			//return inter;			
		} else if(words.syntaxStartsWith(SentencePattern.INDIRECT_ORDER_PATTERN)){
			OrderObject oObject = new OrderObject();
			List<IAction> additionalVerbs = null;

			if(words.syntaxStartsWith(SentencePattern.IS_ARE_PATTERN)){
				words.consume();	// est-ce
				words.consume();	// que
				words.consume();	// tu

				additionalVerbs = new ArrayList<IAction>();
				additionalVerbs.add(words.getCurrentElement().getVerbInfo());
				words.consume();	// peux/pourrais
			} else {
				//words.consume();	
				additionalVerbs = new ArrayList<IAction>();
				additionalVerbs.add(words.getCurrentElement().getVerbInfo());
				words.consume();	// peux/pourrais
				words.consume();	// tu
			}

			if(words.getCurrentElement().isOfType(WordType.PERSONAL_PRONOUN)){
				oObject.target = new PronounSubject(Pronoun.parseTargetPronoun(words.getCurrentElement().getValue()));
				words.consume();
			}

			IAction verb = words.getCurrentElement().getVerbInfo();
			ActionObject action = additionalVerbs == null ? new ActionObject(Tense.PRESENT, verb) 
			: new ActionObject(Tense.PRESENT, verb, additionalVerbs);

			//oObject.action = getActionFromVerb(words.getCurrentElement().getVerbInfo());
			oObject.setAction(action);
			words.consume();
			//oObject.what = parseNameObject(words);
			parseObject(words, oObject);

			inter = oObject;
		} else if(words.syntaxStartsWith(SentencePattern.ORDER_PATTERN)){
			// Ordre
			// TODO: verifier que le verbe est a l'imperatif present
			// db.findConjugatedVerb(words.get(0)).form == IMPERATIVE
			OrderObject order = new OrderObject();

			order.action = new ActionObject(Tense.PRESENT, words.getCurrentElement().getVerbInfo());
			words.consume();

			if(NominalGroupConverter.isADirectObject(words)){

			} else if(words.isIntoBounds() && words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN) && !NominalGroupConverter.isADirectObject(words)){
				words.next();

				if(NominalGroupConverter.isADirectObject(words)){
					words.previous();
					order.target = new PronounSubject(Pronoun.parseTargetPronoun(words.getCurrentElement().getValue()));
					words.consume();
				} else {
					words.previous();
					order.what = new PronounSubject(Pronoun.parseDirectPronoun(words.getCurrentElement().getValue()));
					words.consume();
				}
			} else if(words.isIntoBounds() && words.getCurrentElement().isOfType(WordType.DEFINITE_ARTICLE) && !NominalGroupConverter.isADirectObject(words)){
				order.what = new AbstractTarget(Pronoun.parseDirectPronoun(words.getCurrentElement().getValue()));
				words.consume();

				if(words.isIntoBounds() && words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
					order.target = new PronounSubject(Pronoun.parseTargetPronoun(words.getCurrentElement().getValue()));
					words.consume();
				}
			}

			parseObject(words, order);
			inter = order;
		} else if(words.syntaxStartsWith(SentencePattern.INTERROGATIVE_PATTERN)){
			QuestionObject qObject = new QuestionObject();

			// TODO: gerer les cas du style "est-ce que/qu'est ce que"
			qObject.questionType = Question.parseInterrogativePronoun(words.getCurrentElement().getValue());
			words.consume();

			switch(qObject.questionType){
			case HOW:
				if(VerbConverter.isAnInfinitiveVerb(words)){
					// Comment aller � la Tour Eiffel
					VerbConverter.parseInfinitiveVerb(words, qObject);
				} else if(VerbConverter.isAConjugatedVerb(words)){
					// Comment est la maison du Dr. Watson
					VerbConverter.parseConjugatedVerb(words, qObject);
				}
				break;

			case HOW_MANY:
				if(NominalGroupConverter.isADirectObject(words)){
					// Combien de filles a le Dr. March
					// TODO: affecter les elements aux bons attributs (=> Combien le Dr. March a-t-il de filles)
					qObject.what = NominalGroupConverter.parseDirectObject(words);

					if(VerbConverter.isAConjugatedVerb(words)){
						VerbConverter.parseConjugatedVerb(words, qObject);

						if(NominalGroupConverter.isASubject(words)){
							qObject.subject = NominalGroupConverter.parseSubject(words);
						}
					}
				} else if(VerbConverter.isAQuestionVerbalForm(words)){
					// Combien reste-t-il de temps
					VerbConverter.parseQuestionVerbalGroup(words, qObject);
				}
				break;

			case WHAT:
				if(VerbConverter.isAConjugatedVerb(words)){
					// Quelle est la hauteur du Mt Everest / Que mangent les chiens
					VerbConverter.parseConjugatedVerb(words, qObject);
				} else if(NominalGroupConverter.isADirectObject(words)){
					qObject.what = NominalGroupConverter.parseDirectObject(words);

					if(VerbConverter.isAQuestionVerbalForm(words)){
						// Quel train va � Bordeaux
						VerbConverter.parseQuestionVerbalGroup(words, qObject);
					} else if(VerbConverter.isAConjugatedVerb(words)){
						// Quel temps fera-t-il demain
						VerbConverter.parseConjugatedVerb(words, qObject);
					}
				}
				break;

			case WHEN:
				if(VerbConverter.isAQuestionVerbalForm(words)){
					// Quand viendra-t-il
					VerbConverter.parseQuestionVerbalGroup(words, qObject);
				} else if(VerbConverter.isAConjugatedVerb(words)){
					// Quand arrivera le train
					VerbConverter.parseConjugatedVerb(words, qObject);
				}
				break;

			case WHERE:
				if(VerbConverter.isAQuestionVerbalForm(words)){
					// O� ira-t-il
					VerbConverter.parseQuestionVerbalGroup(words, qObject);
				} else if(VerbConverter.isAnInfinitiveVerb(words)){
					// O� trouver des cadeaux
					VerbConverter.parseInfinitiveVerb(words, qObject);
				} else if(VerbConverter.isAConjugatedVerb(words)){
					// O� est n� John Smith
					VerbConverter.parseConjugatedVerb(words, qObject);
				}
				break;

			case WHICH:
				if(NominalGroupConverter.isADirectObject(words)){
					// Lequel des 7 nains aime les pommes
					qObject.what = NominalGroupConverter.parseDirectObject(words);

					if(VerbConverter.isAConjugatedVerb(words)){
						VerbConverter.parseConjugatedVerb(words, qObject);
					}
				}
				break;

			case WHO:
				if(VerbConverter.isAQuestionVerbalForm(words)){
					// Qui est-il
					VerbConverter.parseQuestionVerbalGroup(words, qObject);
				} else if(VerbConverter.isAConjugatedVerb(words)){
					// Qui est Arnold Schwarzenegger
					VerbConverter.parseConjugatedVerb(words, qObject);
				}
				break;

			case WHY:
				if(VerbConverter.isAQuestionVerbalForm(words)){
					// Pourquoi est-il venu
					VerbConverter.parseQuestionVerbalGroup(words, qObject);
				} else if(VerbConverter.isAnInfinitiveVerb(words)){
					// Pourquoi avoir mang� la pomme
					VerbConverter.parseInfinitiveVerb(words, qObject);
				}
				break;

			case YES_NO:
				// TODO: ajouter une methode pour parser une phrase affirmative complete 
				if(NominalGroupConverter.isASubject(words)){
					// Est-ce que les chats aiment les chiens
					qObject.subject = NominalGroupConverter.parseSubject(words);

					if(VerbConverter.isAConjugatedVerb(words)){
						VerbConverter.parseConjugatedVerb(words, qObject);
					}
				}
				break;

			default:
				break;
			}

			parseObject(words, qObject);

			inter = qObject;
		}/* else if(words.syntaxStartsWith(SentencePattern.RESULT_QUESTION_PATTERN)){
			QuestionObject qObject = new QuestionObject();

			qObject.questionType = QuestionObject.parseInterrogativePronoun(words.getCurrentElement().getValue());			
			words.consume();

			NameObject complement = new NameObject();
			StringBuilder whatString = new StringBuilder();

			// TODO: modifier pour parser un groupe nominal entier
			while(!VerbConverter.isAQuestionVerbalForm(words)){
				whatString.append(words.getCurrentElement().getValue());
				whatString.append(' ');
				words.consume();
			}

			complement.object = whatString.toString().trim();
			qObject.what = complement;	

			VerbConverter.parseQuestionVerbalGroup(words, qObject);

			parseObject(words, qObject);

			inter = qObject;
		}*/ else if(words.syntaxStartsWith(SentencePattern.AFFIRMATIVE_SENTENCE_PATTERN)){
			AffirmationObject affirm = new AffirmationObject();

			affirm.subject = NominalGroupConverter.parseSubject(words);

			VerbConverter.parseConjugatedVerb(words, affirm);

			parseObject(words, affirm);
			inter = affirm;
		} else {
			// TODO: Le sujet est un groupe nominal ?
			// NameObject
		}

		return inter;
	}

	public void parseObject(FRWordBuffer words, InterpretationObject obj){
		while(words.isIntoBounds()){
			if(DateConverter.isATimeAdverbial(words)){
				obj.when = DateConverter.parseTimeAdverbial(words);
			} else if(WayConverter.isAWayAdverbial(words)){ 
				obj.how = WayConverter.parseWayAdverbial(words);
			} else if(PlaceConverter.isAPlaceAdverbial(words)){
				obj.where = PlaceConverter.parsePlaceAdverbial(words);
			} else if(NominalGroupConverter.isADirectObject(words)){
				obj.what = NominalGroupConverter.parseDirectObject(words);
			} else if(NominalGroupConverter.isAnIndirectObject(words)){ 
				obj.target = NominalGroupConverter.parseIndirectObject(words);
			}/* else if(CountConverter.isACountData(words)){
				obj.count = CountConverter.parseCountObject(words);
			}*/ else {
				break;
			}
		}
	}

	private final Word getWord(String q){
		// TODO: PROPER_NAME, NUMBER
		if(q.matches(DateConverter.FULL_TIME_REGEX) || q.matches(DateConverter.HOUR_ONLY_REGEX)){
			return new Word(q, Word.WordType.TIME);
		}

		if(q.matches(CountConverter.REAL_REGEX)){
			return new Word(q, WordType.REAL);
		}

		if(q.matches(CountConverter.NUMBER_REGEX)){
			return new Word(q, WordType.NUMBER);
		}

		/*if(Character.isDigit(w.charAt(0))){
            return new Word(Word.WordType.NUMBER, w);
        }*/
		/*if(Character.isDigit(q.charAt(0))) {
            try {
                return new Word(q, Word.WordType.NUMBER);
            } catch (Exception e) {
                Matcher matcher = Pattern.compile("[0-9]+e").matcher(w);

                if (matcher.find()) {
                    String matchingValue = matcher.group(0);
                    long longValue = Long.parseLong(matchingValue.substring(0, matchingValue.length() - 1));
                    return new Word(String.valueOf(longValue), Word.WordType.NUMERICAL_POSITION);
                }
            }
        }*/

		try{
			Integer.parseInt(q);
			return new Word(q, Word.WordType.NUMBER);
		}catch(Exception e){
			// TODO: les positions de la forme [0-9]eme
			Matcher matcher = Pattern.compile("[0-9]+e").matcher(q);

			if (matcher.find()) {
				String matchingValue = matcher.group(0);
				long longValue = Long.parseLong(matchingValue.substring(0, matchingValue.length() - 1));
				return new Word(String.valueOf(longValue), Word.WordType.NUMERICAL_POSITION);
			}
		}

		Set<WordType> types = new HashSet<WordType>();
		Word result = db.findWord(q);
		NameInfo name = db.findNameInfo(q);
		AdjectiveInfo adjective = db.findAdjectiveInfo(q);
		LanguageInfo language = db.findLanguageInfo(q);
		ColorInfo color = db.findColorInfo(q);
		CityInfo city = db.findCityInfo(q);
		CountryInfo country = db.findCountryInfo(q);
		TransportInfo transport = db.findTransportInfo(q);
		UnitInfo unit = db.findUnitInfo(q);
		CharacterInfo character = db.findCharacterInfo(q);
		PlaceInfo place = db.findPlaceInfo(q);
		CustomObject cObject = db.findCustomObject(q);
		CustomPlace cPlace = db.findCustomPlace(q);
		CustomMode cMode = db.findCustomMode(q);
		CustomPerson cPerson = db.findCustomPerson(q);
		PlacePreposition placePreposition = db.findPlacePreposition(q);
		TimePreposition timePreposition = db.findTimePreposition(q);
		WayPreposition wayPreposition = db.findWayPreposition(q);
		PurposePreposition purposePreposition = db.findPurposePreposition(q);
		VerbConjugation conjugation = db.findConjugation(q);

		if(Character.isUpperCase(q.charAt(0))){
			if(city == null && country == null) {
				types.add(Word.WordType.PROPER_NAME);
			}
		} else {
			if(CountConverter.isAPosition(q)){
				types.add(Word.WordType.NUMERICAL_POSITION);
			}
		}

		if(name != null){
			types.add(WordType.COMMON_NAME);
		}
		
		if(adjective != null){
			types.add(WordType.ADJECTIVE);
		}
		
		if(language != null){
			types.add(Word.WordType.LANGUAGE);
		}

		if(color != null){
			types.add(Word.WordType.COLOR);
		}

		if(city != null){
			types.add(Word.WordType.CITY);
		}

		if(country != null){
			types.add(Word.WordType.COUNTRY);
		}

		if(transport != null){
			types.add(WordType.MEAN_OF_TRANSPORT);
		}

		if(unit != null){
			types.add(WordType.UNIT);
		}

		if(character != null){
			types.add(WordType.PERSON_TYPE);
		}

		if(place != null){
			types.add(WordType.PLACE_TYPE);
		}

		if(cObject != null){
			types.add(Word.WordType.OBJECT);
		}

		if(cPlace != null){
			types.add(Word.WordType.ADDITIONAL_PLACE);
		}

		if(cMode != null){
			types.add(WordType.MODE);
		}

		if(cPerson != null){
			types.add(WordType.PERSON);
		}

		if(placePreposition != null){
			types.add(Word.WordType.PLACE_PREPOSITION);
		}

		if(timePreposition != null){
			types.add(Word.WordType.TIME_PREPOSITION);
		}

		if(wayPreposition != null){
			types.add(Word.WordType.WAY_PREPOSITION);
		}

		if(purposePreposition != null){
			types.add(Word.WordType.PURPOSE_PREPOSITION);
		}

		if(conjugation != null){
			types.add(Word.WordType.VERB);

			try{
				if(conjugation.getVerb().isAuxiliary()){
					types.add(Word.WordType.AUXILIARY);
				}
			}catch(Exception e){

			}
		}

		if (result == null){
			if(!types.isEmpty()) {
				result = new Word(q, types);
			} else {
				return null;
			}
		} else {
			for(WordType t: types) {
				result.addType(t);
			}
		}

		if(result != null) {
			result.setNameInfo(name);
			result.setAdjectiveInfo(adjective);
			result.setLanguageInfo(language);
			result.setColorInfo(color);
			result.setCityInfo(city);
			result.setCountryInfo(country);
			result.setTransportInfo(transport);
			result.setUnitInfo(unit);
			result.setCharacterInfo(character);
			result.setPlaceInfo(place);

			result.setCustomObject(cObject);
			result.setCustomPlace(cPlace);
			result.setCustomMode(cMode);
			result.setCustomPerson(cPerson);

			result.setVerbInfo(conjugation);

			result.setPlacePreposition(placePreposition);
			result.setTimePreposition(timePreposition);
			result.setWayPreposition(wayPreposition);
			result.setPurposePreposition(purposePreposition);
		}

		return result;
	}
}
