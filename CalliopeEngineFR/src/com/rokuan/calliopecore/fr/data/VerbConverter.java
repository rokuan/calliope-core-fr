package com.rokuan.calliopecore.fr.data;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.pattern.FRWordPattern;
import com.rokuan.calliopecore.fr.pattern.VerbMatcher;
import com.rokuan.calliopecore.fr.pattern.VerbPattern;
import com.rokuan.calliopecore.fr.sentence.Pronoun;
import com.rokuan.calliopecore.fr.sentence.Verb;
import com.rokuan.calliopecore.fr.sentence.VerbAction;
import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.IAction.ActionType;
import com.rokuan.calliopecore.sentence.ActionObject;
import com.rokuan.calliopecore.sentence.IPronoun.PronounSource;
import com.rokuan.calliopecore.sentence.IAction.Form;
import com.rokuan.calliopecore.sentence.IAction.Tense;
import com.rokuan.calliopecore.sentence.structure.content.IVerbalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.AbstractTarget;
import com.rokuan.calliopecore.sentence.structure.data.nominal.PronounSubject;

public class VerbConverter {	
	// existe-t-il / suis-je / m'envoie-t-il
	public static final WordPattern PRESENT_QUESTION_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.TARGET_PRONOUN)),
			FRWordPattern.simpleWord(WordType.VERB), 
			WordPattern.optional(FRWordPattern.simpleWord(WordType.CONJUGATION_LINK)), 
			FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN)); 

	// a-t-il mang� / suis-je venu / TODO: m'a-t-il donn�
	public static final WordPattern PAST_QUESTION_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.TARGET_PRONOUN)), 
			FRWordPattern.simpleWord(WordType.AUXILIARY), 
			WordPattern.optional(FRWordPattern.simpleWord(WordType.CONJUGATION_LINK)), 
			FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN), 
			FRWordPattern.simpleWord(WordType.VERB));

	// y avait-il
	public static final WordPattern IS_THERE_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord("y"),
			VerbPattern.simple("avoir"),
			WordPattern.optional(FRWordPattern.simpleWord(WordType.CONJUGATION_LINK, "t")),
			FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "il")
			);

	// TODO: prendre en compte les COD entre le verbe et le TARGET_PRONOUN
	public static final WordPattern CONJUGATED_VERB_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.TARGET_PRONOUN)),
			WordPattern.or(
					WordPattern.sequence(FRWordPattern.simpleWord(WordType.AUXILIARY), FRWordPattern.simpleWord(WordType.VERB)),
					FRWordPattern.simpleWord(WordType.VERB)));

	// (me) donner
	public static final WordPattern INFINITIVE_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.TARGET_PRONOUN)),
			WordPattern.or(
					WordPattern.sequence(VerbPattern.simple("avoir"), FRWordPattern.simpleWord(WordType.VERB)),
					WordPattern.simple(new VerbMatcher().getBuilder().setForm(Form.INFINITIVE).build())));

	public static final WordPattern QUESTION_VERB_PATTERN = WordPattern.or(
			PAST_QUESTION_PATTERN,
			PRESENT_QUESTION_PATTERN,
			IS_THERE_PATTERN
			//infinitivePattern
			);

	// TODO: ajouter les negations
	public static final WordPattern AFFIRMATIVE_VERB_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.TARGET_PRONOUN)),
			CONJUGATED_VERB_PATTERN
			);

	public static boolean isAQuestionVerbalForm(FRWordBuffer words){
		return words.syntaxStartsWith(QUESTION_VERB_PATTERN);
	}

	public static boolean isAConjugatedVerb(FRWordBuffer words){
		return words.syntaxStartsWith(CONJUGATED_VERB_PATTERN);
	}
	
	public static boolean isAnInfinitiveVerb(FRWordBuffer words){
		return words.syntaxStartsWith(INFINITIVE_PATTERN);
	}

	public static void parseQuestionVerbalGroup(FRWordBuffer words, IVerbalObject object){
		if(words.syntaxStartsWith(PAST_QUESTION_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				object.setTarget(new PronounSubject(Pronoun.parseTargetPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}

			words.consume();	// AUXILIARY

			if(words.getCurrentElement().isOfType(WordType.CONJUGATION_LINK)){
				words.consume();
			}

			object.setSubject(new PronounSubject(Pronoun.parseSubjectPronoun(words.getCurrentElement().getValue())));
			words.consume();

			object.setAction(new ActionObject(Tense.PRESENT, words.getCurrentElement().getVerbInfo()));
			words.consume();
		} else if(words.syntaxStartsWith(PRESENT_QUESTION_PATTERN)){			
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				object.setTarget(new PronounSubject(Pronoun.parseTargetPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}		

			object.setAction(new ActionObject(Tense.PRESENT, words.getCurrentElement().getVerbInfo()));
			words.consume();

			if(words.getCurrentElement().isOfType(WordType.CONJUGATION_LINK)){
				words.consume();
			}

			object.setSubject(new PronounSubject(Pronoun.parseSubjectPronoun(words.getCurrentElement().getValue())));
			words.consume();
		} else if(words.syntaxStartsWith(IS_THERE_PATTERN)){
			words.consume();	// y
			
			/*VerbConjugation conjug = new VerbConjugation("y " + words.getCurrentElement().getValue(), (VerbConjugation)words.getCurrentElement().getVerbInfo(), 
					new Verb("y avoir", ActionType.THERE_IS));*/
			VerbConjugation conjug = new VerbConjugation("y " + words.getCurrentElement().getValue(), (VerbConjugation)words.getCurrentElement().getVerbInfo(), 
					new Verb("y avoir", false, new VerbAction(ActionType.THERE_IS)));
			
			object.setAction(new ActionObject(conjug.getTense(), conjug));
			words.consume();

			if(words.getCurrentElement().isOfType(WordType.CONJUGATION_LINK)){
				words.consume();
			}

			// TODO: trouver le sujet correct
			object.setSubject(new PronounSubject(new Pronoun("", PronounSource.UNDEFINED)));
			words.consume();
		}
	}

	public static void parseAffirmativeConjugatedVerb(FRWordBuffer words, IVerbalObject object){
		if(words.syntaxStartsWith(AFFIRMATIVE_VERB_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				object.setTarget(new PronounSubject(Pronoun.parseTargetPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}

			parseConjugatedVerb(words, object);
		}
	}

	public static void parseConjugatedVerb(FRWordBuffer words, IVerbalObject object){
		if(words.syntaxStartsWith(CONJUGATED_VERB_PATTERN)){
			boolean past = false;
			
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				// TODO: affecter le bon attribut selon le verbe
				object.setDirectObject(new AbstractTarget(Pronoun.parseDirectPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}
			
			if(words.getCurrentElement().isOfType(WordType.AUXILIARY)){
				if(words.hasNext()){
					words.next();

					if(words.getCurrentElement().isOfType(WordType.VERB)){
						words.previous();
						words.consume();
						past = true;
					} else {
						words.previous();
					}
				}
			}

			if(words.getCurrentElement().isOfType(WordType.VERB)){
				Tense verbTense = words.getCurrentElement().getVerbInfo().getTense();
				
				object.setAction(new ActionObject(past ? Tense.PAST : verbTense, words.getCurrentElement().getVerbInfo()));
				words.consume();
			}
			
			if(NominalGroupConverter.isADirectObject(words)){
				object.setTarget(object.getDirectObject());
				object.setDirectObject(NominalGroupConverter.parseDirectObject(words));
			}
		}
	}

	public static void parseInfinitiveVerb(FRWordBuffer words, IVerbalObject object){
		boolean withAux = false;
		
		if(words.syntaxStartsWith(INFINITIVE_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				object.setDirectObject(new AbstractTarget(Pronoun.parseDirectPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}
			
			if(words.getCurrentElement().isOfType(WordType.AUXILIARY)){
				if(words.hasNext()){
					words.next();

					if(words.getCurrentElement().isOfType(WordType.VERB)){
						words.previous();
						words.consume();
						withAux = true;
					} else {
						words.previous();
					}
				}
			}

			if(words.getCurrentElement().isOfType(WordType.VERB)){				
				object.setAction(new ActionObject(withAux ? Tense.PAST : words.getCurrentElement().getVerbInfo().getTense(), 
						words.getCurrentElement().getVerbInfo()));
				words.consume();
			}
		}
	}
}
