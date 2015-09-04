package com.rokuan.calliopecore.fr.data;

import com.rokuan.calliopecore.parser.WordBuffer;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.structure.content.IPurposeObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.VerbalGroup;
import com.rokuan.calliopecore.sentence.structure.data.purpose.PurposeAdverbial.PurposeType;

public class PurposeConverter {
	// TODO: mettre le bon pattern pour le verb + rajouter le pattern pour les sujets
	private static final WordPattern INFINITIVE_VERB_PATTERN = WordPattern.sequence(
			WordPattern.simplePurposePrep(PurposeType.INFINITIVE_VERB),
			VerbConverter.INFINITIVE_PATTERN);
	
	private static final WordPattern CONJUGATED_VERB_PATTERN = WordPattern.sequence(
			WordPattern.simplePurposePrep(PurposeType.CONJUGATED_VERB),
			VerbConverter.CONJUGATED_VERB_PATTERN);
	
	public static final WordPattern VERBAL_PURPOSE_PATTERN = WordPattern.sequence(
			INFINITIVE_VERB_PATTERN,
			CONJUGATED_VERB_PATTERN
			);
	
	public static boolean isAPurposeAdverbial(WordBuffer words){
		return words.syntaxStartsWith(VERBAL_PURPOSE_PATTERN);
	}
	
	public static IPurposeObject parsePurposeAdverbial(WordBuffer words){
		IPurposeObject result = null;
		
		if(words.syntaxStartsWith(INFINITIVE_VERB_PATTERN)){
			VerbalGroup verbal = new VerbalGroup();
			
			verbal.setPurposePreposition(words.getCurrentElement().getPurposePreposition().getValue());
			words.consume();
			
			VerbConverter.parseInfinitiveVerb(words, verbal);
			
			result = verbal;
		} else if(words.syntaxStartsWith(CONJUGATED_VERB_PATTERN)){
			VerbalGroup verbal = new VerbalGroup();
			
			verbal.setPurposePreposition(words.getCurrentElement().getPurposePreposition().getValue());
			words.consume();
			
			VerbConverter.parseConjugatedVerb(words, verbal);
			
			result = verbal;
		}
		
		return result;
	}
}
