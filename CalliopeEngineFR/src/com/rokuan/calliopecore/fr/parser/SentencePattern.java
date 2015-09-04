package com.rokuan.calliopecore.fr.parser;

import com.rokuan.calliopecore.fr.data.NominalGroupConverter;
import com.rokuan.calliopecore.fr.data.VerbConverter;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.Word.WordType;

public class SentencePattern {
	public static final WordPattern YES_NO_QUESTION_PATTERN = WordPattern.sequence(
			WordPattern.optional(WordPattern.simpleWord(WordType.PERSONAL_PRONOUN)),	// m', t'
			WordPattern.simpleWord(WordType.AUXILIARY),
			WordPattern.optional(WordPattern.simpleWord(WordType.CONJUGATION_LINK)),
			WordPattern.simpleWord(WordType.PERSONAL_PRONOUN),
			WordPattern.simpleWord(WordType.VERB)
			);

	public static final WordPattern IS_ARE_PATTERN = WordPattern.sequence(
			WordPattern.simpleWord("est-ce"),
			WordPattern.simpleWord("que")
			);
	public static final WordPattern INDIRECT_ORDER_PATTERN = WordPattern.sequence(
			WordPattern.or(
					// Est-ce que tu peux/pourrais
					WordPattern.sequence(IS_ARE_PATTERN, WordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "tu"), WordPattern.simpleVerb("pouvoir")),
					// (Peux/pourrais)-tu
					WordPattern.sequence(WordPattern.simpleVerb("pouvoir"), WordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "tu"))
					),
					WordPattern.optional(WordPattern.simpleWord(WordType.PERSONAL_PRONOUN)),
					WordPattern.simpleWord(WordType.VERB)
			);
	
	public static final String ORDER_VERB_REGEX = "ordonner|demander|souhaiter|vouloir|exiger|aimer|sommer";
	// Je t'ordonne de ... || Je souhaite que tu (me/te/...)...
	// TODO: voir les autres cas
	public static final WordPattern AFFIRMATIVE_ORDER = WordPattern.sequence(
			WordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "je"),
			WordPattern.optional(WordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "t|te")),
			WordPattern.simpleVerb(ORDER_VERB_REGEX),
			WordPattern.or(
					WordPattern.sequence(WordPattern.simpleWord("que"), WordPattern.simpleWord(WordType.PERSONAL_PRONOUN, "tu"), WordPattern.optional(WordPattern.simpleWord(WordType.PERSONAL_PRONOUN))),
					WordPattern.simpleWord("de"))
					);

	// Affiche-moi
	public static final WordPattern ORDER_PATTERN = WordPattern.sequence(
			WordPattern.simpleWord(WordType.VERB), 
			WordPattern.optional(WordPattern.sequence(
					WordPattern.optional(WordPattern.simpleWord(WordType.DEFINITE_ARTICLE)),
					WordPattern.optional(WordPattern.simpleWord(WordType.TARGET_PRONOUN))
			)));

	// Quelle temperature fait-il
	public static final WordPattern RESULT_QUESTION_PATTERN = WordPattern.sequence(
			WordPattern.or(WordPattern.simpleWord(WordType.INTERROGATIVE_ADJECTIVE, "quel.*"), WordPattern.simpleWord(WordType.INTERROGATIVE_ADJECTIVE, "quel.*")),
			// TODO: remplacer par un groupe nominal en prenant en compte les adjectifs (ex: quel petit chat)
			//WordPattern.nonEmptyList(WordPattern.simple(WordType.COMMON_NAME)),
			NominalGroupConverter.NAME_PATTERN,
			VerbConverter.QUESTION_VERB_PATTERN
			);

	// Quel(s/le(s)) est/sont
	public static final WordPattern INTERROGATIVE_PATTERN = WordPattern.sequence(
			WordPattern.or(WordPattern.simpleWord(WordType.INTERROGATIVE_PRONOUN), WordPattern.simpleWord(WordType.INTERROGATIVE_ADJECTIVE)),
			VerbConverter.CONJUGATED_VERB_PATTERN
			);
	
	public static final WordPattern AFFIRMATIVE_SENTENCE_PATTERN = WordPattern.sequence(
			NominalGroupConverter.SUBJECT_PATTERN,
			VerbConverter.CONJUGATED_VERB_PATTERN
			/*,
			WordPattern.optional(NominalGroupConverter.DIRECT_OBJECT_PATTERN)*/);
			
}
