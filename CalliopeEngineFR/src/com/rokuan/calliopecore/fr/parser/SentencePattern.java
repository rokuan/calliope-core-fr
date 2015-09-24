package com.rokuan.calliopecore.fr.parser;

import com.rokuan.calliopecore.fr.data.NominalGroupConverter;
import com.rokuan.calliopecore.fr.data.VerbConverter;
import com.rokuan.calliopecore.fr.pattern.FRWordPattern;
import com.rokuan.calliopecore.fr.pattern.VerbPattern;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordPattern;

public class SentencePattern {
	public static final WordPattern YES_NO_QUESTION_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN)),	// m', t'
			FRWordPattern.simpleWord(WordType.AUXILIARY),
			WordPattern.optional(FRWordPattern.simpleWord(WordType.CONJUGATION_LINK)),
			FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN),
			FRWordPattern.simpleWord(WordType.VERB)
			);

	public static final WordPattern IS_ARE_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord("est-ce"),
			FRWordPattern.simpleWord("que")
			);
	public static final WordPattern INDIRECT_ORDER_PATTERN = WordPattern.sequence(
			WordPattern.or(
					// Est-ce que tu peux/pourrais
					WordPattern.sequence(IS_ARE_PATTERN, FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "tu"), VerbPattern.simple("pouvoir")),
					// (Peux/pourrais)-tu
					WordPattern.sequence(VerbPattern.simple("pouvoir"), FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "tu"))
					),
					WordPattern.optional(FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN)),
					FRWordPattern.simpleWord(WordType.VERB)
			);
	
	public static final String ORDER_VERB_REGEX = "ordonner|demander|souhaiter|vouloir|exiger|aimer|sommer";
	// Je t'ordonne de ... || Je souhaite que tu (me/te/...)...
	// TODO: voir les autres cas
	public static final WordPattern AFFIRMATIVE_ORDER = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "je"),
			WordPattern.optional(FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "t|te")),
			VerbPattern.simple(ORDER_VERB_REGEX),
			WordPattern.or(
					WordPattern.sequence(FRWordPattern.simpleWord("que"), FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "tu"), WordPattern.optional(FRWordPattern.simpleWord(WordType.PERSONAL_PRONOUN))),
					FRWordPattern.simpleWord("de"))
					);

	// Affiche-moi
	public static final WordPattern ORDER_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.VERB), 
			WordPattern.optional(WordPattern.sequence(
					WordPattern.optional(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE)),
					WordPattern.optional(FRWordPattern.simpleWord(WordType.TARGET_PRONOUN))
			)));

	// Quelle temperature fait-il
	public static final WordPattern RESULT_QUESTION_PATTERN = WordPattern.sequence(
			WordPattern.or(FRWordPattern.simpleWord(WordType.INTERROGATIVE_ADJECTIVE, "quel.*"), FRWordPattern.simpleWord(WordType.INTERROGATIVE_ADJECTIVE, "quel.*")),
			// TODO: remplacer par un groupe nominal en prenant en compte les adjectifs (ex: quel petit chat)
			//WordPattern.nonEmptyList(WordPattern.simple(WordType.COMMON_NAME)),
			NominalGroupConverter.NAME_PATTERN,
			VerbConverter.QUESTION_VERB_PATTERN
			);

	// Quel(s/le(s)) est/sont
	/*public static final WordPattern INTERROGATIVE_PATTERN = WordPattern.sequence(
			WordPattern.or(FRWordPattern.simpleWord(WordType.INTERROGATIVE_PRONOUN), FRWordPattern.simpleWord(WordType.INTERROGATIVE_ADJECTIVE)),
			//VerbConverter.CONJUGATED_VERB_PATTERN
			);*/
	public static final WordPattern INTERROGATIVE_PATTERN = WordPattern.or(FRWordPattern.simpleWord(WordType.INTERROGATIVE_PRONOUN), FRWordPattern.simpleWord(WordType.INTERROGATIVE_ADJECTIVE));
	
	public static final WordPattern AFFIRMATIVE_SENTENCE_PATTERN = WordPattern.sequence(
			NominalGroupConverter.SUBJECT_PATTERN,
			VerbConverter.CONJUGATED_VERB_PATTERN
			/*,
			WordPattern.optional(NominalGroupConverter.DIRECT_OBJECT_PATTERN)*/);
			
}
