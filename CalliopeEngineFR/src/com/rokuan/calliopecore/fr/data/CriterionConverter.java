package com.rokuan.calliopecore.fr.data;

import java.util.ArrayList;
import java.util.List;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.pattern.FRWordPattern;
import com.rokuan.calliopecore.fr.pattern.VerbPattern;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.structure.data.criteria.CriterionObject;
import com.rokuan.calliopecore.sentence.structure.data.criteria.FieldCriterionObject;
import com.rokuan.calliopecore.sentence.structure.data.criteria.SuperlativeCriterionObject;

public class CriterionConverter {
	/*// Le plus proche
	public static final WordPattern CRITERIA_PATTERN = WordPattern.sequence(
			WordPattern.simple(WordType.DEFINITE_ARTICLE),
			WordPattern.simple(WordType.SUPERLATIVE),
			WordPattern.simple(WordType.ADJECTIVE)
			);

	// Le plus petit homme
	//public static final WordPattern superlativePattern = WordPattern.sequence(criteriaPattern, WordPattern.optional(WordPattern.simple(WordType.COMMON_NAME)));
	public static final WordPattern SUPERLATIVE_PATTERN = WordPattern.separatedNonEmptyList(CRITERIA_PATTERN, WordPattern.optional(WordPattern.simple(WordType.PREPOSITION_AND)));

	public static final WordPattern SUPERLATIVE_ADJECTIVE_FIRST_PATTERN = WordPattern.sequence(WordPattern.simple(WordType.SUPERLATIVE), WordPattern.simple(WordType.ADJECTIVE), WordPattern.simple(WordType.COMMON_NAME));
	public static final WordPattern SUPERLATIVE_NAME_FIRST_PATTERN = WordPattern.sequence(WordPattern.simple(WordType.COMMON_NAME), WordPattern.simple(WordType.DEFINITE_ARTICLE), WordPattern.simple(WordType.SUPERLATIVE), WordPattern.simple(WordType.ADJECTIVE)); 

	// L'homme le plus riche / le plus petit homme
	public static final WordPattern FIELD_CRITERIA_PATTERN = WordPattern.sequence(
			WordPattern.simple(WordType.DEFINITE_ARTICLE),
			WordPattern.or(
					SUPERLATIVE_ADJECTIVE_FIRST_PATTERN,
					SUPERLATIVE_NAME_FIRST_PATTERN
					)
			);

	// TODO: avoir la possibilite d'ajouter un filtre sur la valeur du mot
	// qui a/ayant/avec la plus grande surface
	public static final WordPattern SPECIFICATION_HAVE_PATTERN = WordPattern.sequence(
			WordPattern.or(
					WordPattern.simple(WordType.PREPOSITION_WITH),
					WordPattern.sequence(WordPattern.optional(WordPattern.simple(WordType.RELATIVE_PRONOUN, "qui")), WordPattern.simple(WordType.VERB, null, "avoir"))
					),
					WordPattern.separatedNonEmptyList(FIELD_CRITERIA_PATTERN, WordPattern.optional(WordPattern.simple(WordType.PREPOSITION_AND)))
			);
	// qui est/etant le plus proche
	public static final WordPattern SPECIFICATION_BE_PATTERN = WordPattern.sequence(
			WordPattern.optional(WordPattern.simple(WordType.RELATIVE_PRONOUN, "qui")),
			WordPattern.simple(WordType.VERB, null, "être"),
			WordPattern.separatedNonEmptyList(CRITERIA_PATTERN, WordPattern.optional(WordPattern.simple(WordType.PREPOSITION_AND)))
			);*/

	// Le plus proche
	public static final WordPattern CRITERIA_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE),
			FRWordPattern.simpleWord(WordType.SUPERLATIVE),
			FRWordPattern.simpleWord(WordType.ADJECTIVE)
			);

	// Le plus petit homme
	//public static final WordPattern superlativePattern = WordPattern.sequence(criteriaPattern, WordPattern.optional(WordPattern.simple(WordType.COMMON_NAME)));
	public static final WordPattern SUPERLATIVE_PATTERN = WordPattern.separatedNonEmptyList(CRITERIA_PATTERN, WordPattern.optional(FRWordPattern.simpleWord(WordType.PREPOSITION_AND)));

	public static final WordPattern SUPERLATIVE_ADJECTIVE_FIRST_PATTERN = WordPattern.sequence(FRWordPattern.simpleWord(WordType.SUPERLATIVE), FRWordPattern.simpleWord(WordType.ADJECTIVE), FRWordPattern.simpleWord(WordType.COMMON_NAME));
	public static final WordPattern SUPERLATIVE_NAME_FIRST_PATTERN = WordPattern.sequence(FRWordPattern.simpleWord(WordType.COMMON_NAME), FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE), FRWordPattern.simpleWord(WordType.SUPERLATIVE), FRWordPattern.simpleWord(WordType.ADJECTIVE)); 

	// L'homme le plus riche / le plus petit homme
	public static final WordPattern FIELD_CRITERIA_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE),
			WordPattern.or(
					SUPERLATIVE_ADJECTIVE_FIRST_PATTERN,
					SUPERLATIVE_NAME_FIRST_PATTERN
					)
			);

	// TODO: avoir la possibilite d'ajouter un filtre sur la valeur du mot
	// qui a/ayant/avec la plus grande surface
	public static final WordPattern SPECIFICATION_HAVE_PATTERN = WordPattern.sequence(
			WordPattern.or(
					FRWordPattern.simpleWord(WordType.PREPOSITION_WITH),
					WordPattern.sequence(WordPattern.optional(FRWordPattern.simpleWord(WordType.RELATIVE_PRONOUN, "qui")), VerbPattern.simple("avoir"))
					),
					WordPattern.separatedNonEmptyList(FIELD_CRITERIA_PATTERN, WordPattern.optional(FRWordPattern.simpleWord(WordType.PREPOSITION_AND)))
			);
	// qui est/etant le plus proche
	public static final WordPattern SPECIFICATION_BE_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.RELATIVE_PRONOUN, "qui")),
			VerbPattern.simple("être"),
			WordPattern.separatedNonEmptyList(CRITERIA_PATTERN, WordPattern.optional(FRWordPattern.simpleWord(WordType.PREPOSITION_AND)))
			);

	public static boolean isACriterionData(FRWordBuffer words){
		return words.syntaxStartsWith(SPECIFICATION_HAVE_PATTERN)
				|| words.syntaxStartsWith(SPECIFICATION_BE_PATTERN)
				|| words.syntaxStartsWith(SUPERLATIVE_PATTERN);
	}

	public static List<CriterionObject> parseCriterionData(FRWordBuffer words){
		List<CriterionObject> criteria = new ArrayList<CriterionObject>();

		if(words.syntaxStartsWith(SPECIFICATION_HAVE_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.PREPOSITION_WITH)){
				words.consume();	// avec
			} else {
				if(words.getCurrentElement().isOfType(WordType.RELATIVE_PRONOUN)){
					words.consume();	// qui
				}

				words.consume();	// avoir
			}

			do {
				FieldCriterionObject fCriterion = new FieldCriterionObject();
				words.consume();	// DEFINITE_ARTICLE

				if(words.syntaxStartsWith(SUPERLATIVE_NAME_FIRST_PATTERN)){
					fCriterion.field = words.getCurrentElement().getValue();
					words.consume();
					fCriterion.compare = CriterionObject.parseComparisonType(words.getCurrentElement().getValue());
					words.consume();
					fCriterion.criterion = words.getCurrentElement().getValue();
					words.consume();
				} else {
					// Adjective comes before common name
					fCriterion.compare = CriterionObject.parseComparisonType(words.getCurrentElement().getValue());
					words.consume();
					fCriterion.criterion = words.getCurrentElement().getValue();
					words.consume();
					fCriterion.field = words.getCurrentElement().getValue();
					words.consume();
				}

				criteria.add(fCriterion);

				if(words.hasNext() && words.getCurrentElement().isOfType(WordType.PREPOSITION_AND)){
					words.consume();
				}
			} while(words.syntaxStartsWith(FIELD_CRITERIA_PATTERN));
		} else if(words.syntaxStartsWith(SPECIFICATION_BE_PATTERN)){
			if(words.getCurrentElement().isOfType(WordType.RELATIVE_PRONOUN)){
				words.consume();
			}

			words.consume();	// être

			do {
				SuperlativeCriterionObject sCriterion = new SuperlativeCriterionObject();

				words.consume();	// DEFINITE_ARTICLE
				sCriterion.compare = CriterionObject.parseComparisonType(words.getCurrentElement().getValue());
				words.consume();
				sCriterion.criterion = words.getCurrentElement().getValue();
				words.consume();

				criteria.add(sCriterion);

				if(words.hasNext() && words.getCurrentElement().isOfType(WordType.PREPOSITION_AND)){
					words.consume();
				}
			} while(words.syntaxStartsWith(CRITERIA_PATTERN));
		} else if(words.syntaxStartsWith(SUPERLATIVE_PATTERN)){
			// TODO: fusionner avec le cas specificationBePattern ?
			do {
				SuperlativeCriterionObject sCriterion = new SuperlativeCriterionObject();

				words.consume();	// DEFINITE_ARTICLE
				sCriterion.compare = CriterionObject.parseComparisonType(words.getCurrentElement().getValue());
				words.consume();
				sCriterion.criterion = words.getCurrentElement().getValue();
				words.consume();

				criteria.add(sCriterion);

				if(words.hasNext() && words.getCurrentElement().isOfType(WordType.PREPOSITION_AND)){
					words.consume();
				}
			} while(words.syntaxStartsWith(CRITERIA_PATTERN));
		}

		return criteria;
	}
}
