package com.rokuan.calliopecore.fr.data;

import com.rokuan.calliopecore.parser.WordBuffer;
import com.rokuan.calliopecore.pattern.VerbMatcher;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.Action;
import com.rokuan.calliopecore.sentence.Type;
import com.rokuan.calliopecore.sentence.VerbConjugation;
import com.rokuan.calliopecore.sentence.Verb.Form;
import com.rokuan.calliopecore.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.structure.content.IVerbalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.AbstractTarget;
import com.rokuan.calliopecore.sentence.structure.data.nominal.PronounTarget;

public class VerbConverter {	
	// existe-t-il / suis-je / m'envoie-t-il
	public static final WordPattern PRESENT_QUESTION_PATTERN = WordPattern.sequence(
			WordPattern.optional(WordPattern.simpleWord(WordType.TARGET_PRONOUN)),
			WordPattern.simpleWord(WordType.VERB), 
			WordPattern.optional(WordPattern.simpleWord(WordType.CONJUGATION_LINK)), 
			WordPattern.simpleWord(WordType.PERSONAL_PRONOUN)); 

	// a-t-il mangé / suis-je venu / TODO: m'a-t-il donné
	public static final WordPattern PAST_QUESTION_PATTERN = WordPattern.sequence(
			WordPattern.optional(WordPattern.simpleWord(WordType.TARGET_PRONOUN)), 
			WordPattern.simpleWord(WordType.AUXILIARY), 
			WordPattern.optional(WordPattern.simpleWord(WordType.CONJUGATION_LINK)), 
			WordPattern.simpleWord(WordType.PERSONAL_PRONOUN), 
			WordPattern.simpleWord(WordType.VERB));

	// y avait-il
	public static final WordPattern IS_THERE_PATTERN = WordPattern.sequence(
			WordPattern.simpleWord("y"),
			WordPattern.simpleVerb("avoir"),
			WordPattern.optional(WordPattern.simpleWord(WordType.CONJUGATION_LINK, "t")),
			WordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "il")
			);

	// TODO: prendre en compte les COD entre le verbe et le TARGET_PRONOUN
	public static final WordPattern CONJUGATED_VERB_PATTERN = WordPattern.sequence(
			WordPattern.optional(WordPattern.simpleWord(WordType.TARGET_PRONOUN)),
			WordPattern.or(
					WordPattern.sequence(WordPattern.simpleWord(WordType.AUXILIARY), WordPattern.simpleWord(WordType.VERB)),
					WordPattern.simpleWord(WordType.VERB)));

	// (me) donner
	public static final WordPattern INFINITIVE_PATTERN = WordPattern.sequence(
			WordPattern.optional(WordPattern.simpleWord(WordType.TARGET_PRONOUN)),
			WordPattern.or(
					WordPattern.sequence(WordPattern.simpleVerb("avoir"), WordPattern.simpleWord(WordType.VERB)),
					WordPattern.simple(new VerbMatcher().getBuilder().setForm(Form.INFINITIVE).build())));

	public static final WordPattern QUESTION_VERB_PATTERN = WordPattern.or(
			PAST_QUESTION_PATTERN,
			PRESENT_QUESTION_PATTERN,
			IS_THERE_PATTERN
			//infinitivePattern
			);

	// TODO: ajouter les negations
	public static final WordPattern AFFIRMATIVE_VERB_PATTERN = WordPattern.sequence(
			WordPattern.optional(WordPattern.simpleWord(WordType.TARGET_PRONOUN)),
			CONJUGATED_VERB_PATTERN
			);

	public static boolean isAQuestionVerbalForm(WordBuffer words){
		return words.syntaxStartsWith(QUESTION_VERB_PATTERN);
	}

	public static boolean isAConjugatedVerb(WordBuffer words){
		return words.syntaxStartsWith(CONJUGATED_VERB_PATTERN);
	}

	public static void parseQuestionVerbalGroup(WordBuffer words, IVerbalObject object){
		if(words.syntaxStartsWith(PAST_QUESTION_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				object.setTarget(new PronounTarget(Type.parseTargetPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}

			words.consume();	// AUXILIARY

			if(words.getCurrentElement().isOfType(WordType.CONJUGATION_LINK)){
				words.consume();
			}

			object.setSubject(new PronounTarget(Type.parseSubjectPronoun(words.getCurrentElement().getValue())));
			words.consume();

			object.setAction(getActionFromVerb(words.getCurrentElement().getVerbInfo()));
			words.consume();
		} else if(words.syntaxStartsWith(PRESENT_QUESTION_PATTERN)){			
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				object.setTarget(new PronounTarget(Type.parseTargetPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}		

			object.setAction(getActionFromVerb(words.getCurrentElement().getVerbInfo()));
			words.consume();

			if(words.getCurrentElement().isOfType(WordType.CONJUGATION_LINK)){
				words.consume();
			}

			object.setSubject(new PronounTarget(Type.parseSubjectPronoun(words.getCurrentElement().getValue())));
			words.consume();
		} else if(words.syntaxStartsWith(IS_THERE_PATTERN)){
			words.consume();	// y

			object.setAction(Action.VerbAction.THERE_IS);
			words.consume();

			if(words.getCurrentElement().isOfType(WordType.CONJUGATION_LINK)){
				words.consume();
			}

			// TODO: trouver le sujet correct
			object.setSubject(new PronounTarget(Type.Pronoun.UNDEFINED));
			words.consume();
		}
	}

	public static void parseAffirmativeConjugatedVerb(WordBuffer words, IVerbalObject object){
		if(words.syntaxStartsWith(AFFIRMATIVE_VERB_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				object.setTarget(new PronounTarget(Type.parseTargetPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}

			parseConjugatedVerb(words, object);
		}
	}

	public static void parseConjugatedVerb(WordBuffer words, IVerbalObject object){
		if(words.syntaxStartsWith(CONJUGATED_VERB_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				// TODO: affecter le bon attribut selon le verbe
				object.setDirectObject(new AbstractTarget(Type.parseDirectPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}
			
			if(words.getCurrentElement().isOfType(WordType.AUXILIARY)){
				if(words.hasNext()){
					words.next();

					if(words.getCurrentElement().isOfType(WordType.VERB)){
						words.previous();
						words.consume();
					} else {
						words.previous();
					}
				}
			}

			if(words.getCurrentElement().isOfType(WordType.VERB)){
				object.setAction(getActionFromVerb(words.getCurrentElement().getVerbInfo()));
				words.consume();
			}
			
			if(NominalGroupConverter.isADirectObject(words)){
				object.setTarget(object.getDirectObject());
				object.setDirectObject(NominalGroupConverter.parseDirectObject(words));
			}
		}
	}

	public static void parseInfinitiveVerb(WordBuffer words, IVerbalObject object){
		if(words.syntaxStartsWith(INFINITIVE_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.TARGET_PRONOUN)){
				object.setDirectObject(new AbstractTarget(Type.parseDirectPronoun(words.getCurrentElement().getValue())));
				words.consume();
			}
			
			if(words.getCurrentElement().isOfType(WordType.AUXILIARY)){
				if(words.hasNext()){
					words.next();

					if(words.getCurrentElement().isOfType(WordType.VERB)){
						words.previous();
						words.consume();
					} else {
						words.previous();
					}
				}
			}

			if(words.getCurrentElement().isOfType(WordType.VERB)){
				object.setAction(getActionFromVerb(words.getCurrentElement().getVerbInfo()));
				words.consume();
			}
		}
	}

	private static Action.VerbAction getActionFromVerb(VerbConjugation conjug){
		if(conjug == null || conjug.getVerb() == null){
			return Action.VerbAction.UNDEFINED;
		}

		return (Action.VerbAction)conjug.getVerb().getAction();
	}
}
