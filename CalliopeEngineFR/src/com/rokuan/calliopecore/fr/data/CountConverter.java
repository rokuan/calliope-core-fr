package com.rokuan.calliopecore.fr.data;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.pattern.FRWordPattern;
import com.rokuan.calliopecore.fr.sentence.Pronoun;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.IPronoun;
import com.rokuan.calliopecore.sentence.structure.data.count.AllItemsObject;
import com.rokuan.calliopecore.sentence.structure.data.count.CountObject;
import com.rokuan.calliopecore.sentence.structure.data.count.CountObject.ArticleType;
import com.rokuan.calliopecore.sentence.structure.data.count.CountObject.Range;
import com.rokuan.calliopecore.sentence.structure.data.count.FixedItemObject;
import com.rokuan.calliopecore.sentence.structure.data.count.LimitedItemsObject;
import com.rokuan.calliopecore.sentence.structure.data.count.MultipleItemsObject;
import com.rokuan.calliopecore.sentence.structure.data.count.QuantityObject;


/**
 * Created by LEBEAU Christophe on 28/02/2015.
 */
public class CountConverter {
	private static final String[] NUMBER_MAP = {
		"un",
		"deux",
		"trois",
		"quatre",
		"cinq",
		"six",
		"sept",
		"huit",
		"neuf",
		"dix",
		"onze",
		"douze",
		"treize",
		"quatorze",
		"quinze",
		"seize"
	};

	private static final String[] TEN_STEP_MAP = {
		"dix",
		"vingt",
		"trente",
		"quarante",
		"cinquante",
		"soixante"
	};

	private static final String[] TEN_POWER_MAP = {
		"dix",
		"cent",
		"mille",
		"_",
		"_",
		"million",
		"_",
		"_",
		"milliard"
	};
	
	public static final String REAL_REGEX = "\\d+(\\.|\\,)\\d+";
	public static final String NUMBER_REGEX = "\\d+";
	public static final String NUMERICAL_POSITION_REGEX = "(\\d+(è|e)me$)|(\\d+e$)";
	
	public static final WordPattern FIXED_ITEM_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE), 
			FRWordPattern.simpleWord(Word.WordType.NUMERICAL_POSITION));

	public static final WordPattern FIXED_RANGE_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE), 
			FRWordPattern.simpleWord(Word.WordType.NUMBER), 
			FRWordPattern.simpleWord(Word.WordType.NUMERICAL_POSITION));

	public static final WordPattern ALL_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(Word.WordType.QUANTITY), 
			FRWordPattern.simpleWord(Word.WordType.DEFINITE_ARTICLE));
	
	public static final WordPattern QUANTITY_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE),
			FRWordPattern.simpleWord(WordType.NUMBER));

	public static final WordPattern SIMPLE_ARTICLE_PATTERN = WordPattern.or(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE),
			FRWordPattern.simpleWord(WordType.INDEFINITE_ARTICLE));

	private static final WordPattern SINGLE_ITEM_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.PREPOSITION_AND)),
			WordPattern.optional(FRWordPattern.simpleWord("numéro(s?)")), 
			FRWordPattern.simpleWord(WordType.NUMBER));

	private static final WordPattern SINGLE_POSITION_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.PREPOSITION_AND)),
			FRWordPattern.simpleWord(WordType.NUMERICAL_POSITION));

	// TODO: ajouter la quantite
	private static final WordPattern POSSESSIVE_PATTERN = FRWordPattern.simpleWord(WordType.POSSESSIVE_ADJECTIVE);
	
	public static final WordPattern MULTIPLE_ITEMS_PATTERN = WordPattern.sequence(
			WordPattern.nonEmptyList(SINGLE_ITEM_PATTERN));

	public static final WordPattern MULTIPLE_POSITIONS_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE), 
			FRWordPattern.simpleWord(WordType.NUMERICAL_POSITION),
			WordPattern.nonEmptyList(SINGLE_POSITION_PATTERN));

	public static final WordPattern COUNT_PATTERN = WordPattern.or(FIXED_ITEM_PATTERN, FIXED_RANGE_PATTERN, ALL_PATTERN, QUANTITY_PATTERN, SIMPLE_ARTICLE_PATTERN, POSSESSIVE_PATTERN);


	// TODO: les intervalles (du 3eme au 5eme)

	public static long parsePosition(String posStr){
		if(posStr.equals("premier") || posStr.equals("première") || posStr.equals("1er") || posStr.equals("1ère") || posStr.equals("1ere")){
			return 1;
		}

		if(posStr.endsWith("ième")){
			String base = posStr.substring(0, posStr.length() - 4);

			if(base.length() == 0){
				// TODO: erreur
				return 0;
			}

			char lastChar = base.charAt(base.length() - 1);

			// Voyelle supplementaire pour conserver la sonorite
			if(lastChar == 'u'){
				base = base.substring(0, base.length() - 1);
			}
			
			return parseCount(base);
		}

		if(Pattern.compile("\\d+(è|e)me$").matcher(posStr).find()){
			return Long.parseLong(posStr.substring(0, posStr.length() - 3));
		} else if(Pattern.compile("\\d+e$").matcher(posStr).find()){
			return Long.parseLong(posStr.substring(0, posStr.length() - 1));
		}

		// TODO: trouver les autres cas
		return -1;
	}

	private static long parseCount(String countStr){        
		for(int i=0; i<TEN_POWER_MAP.length; i++){
			if(Math.abs(TEN_POWER_MAP[i].length() - countStr.length()) <=1 && TEN_POWER_MAP[i].startsWith(countStr)){
				return (int)Math.pow(10, (i+1));
			}
		}

		for(int i=0; i<TEN_STEP_MAP.length; i++){
			if(Math.abs(TEN_STEP_MAP[i].length() - countStr.length()) <=1 && TEN_STEP_MAP[i].startsWith(countStr)){
				return (i+1) * 10;
			}
		}

		for(int i=0; i<NUMBER_MAP.length; i++){
			if(Math.abs(NUMBER_MAP[i].length() - countStr.length()) <=1 && NUMBER_MAP[i].startsWith(countStr)){
				return (i+1);
			}
		}

		return 0;
	}

	public static boolean isACountData(FRWordBuffer words){
		// TODO:
		return words.syntaxStartsWith(COUNT_PATTERN);
	}

	public static boolean isASuffixCountData(FRWordBuffer words){
		return words.syntaxStartsWith(MULTIPLE_ITEMS_PATTERN);
	}

	private static boolean isSingular(String article){
		String[] parts = article.split("-");

		for(String word: parts){
			char lastChar = word.charAt(word.length() - 1);

			if(lastChar == 's' || lastChar == 'x'){
				return false;
			}
		}

		return true;
	}
	
	public static boolean isAPosition(String posStr){
		if(posStr.equals("premier") || posStr.equals("premiers") || posStr.equals("première") || posStr.equals("premières")
				|| posStr.equals("1er") || posStr.equals("1ère") || posStr.equals("1ere")
				|| posStr.equals("dernier") || posStr.equals("derniers") || posStr.equals("dernière") || posStr.equals("dernières")){
			return true;
		}

		if(posStr.endsWith("ième")){
			String base = posStr.substring(0, posStr.length() - 4);

			if(base.length() == 0){
				// TODO: erreur
				return false;
			}

			char lastChar = base.charAt(base.length() - 1);

			// Voyelle supplementaire pour conserver la sonorite
			if(lastChar == 'u'){
				base = base.substring(0, base.length() - 1);
			}

			return parseCount(base) >= 0;
		}

		if(Pattern.compile("\\d+(è|e)me$").matcher(posStr).find()){
			return true;
		} else if(Pattern.compile("\\d+e$").matcher(posStr).find()){
			return true;
		}
		
		return false;
	}

	public static CountObject parseCountObject(FRWordBuffer words){
		if(words.getCurrentIndex() > words.size()){
			// TODO: error
			return null;
		}

		CountObject result = null;

		if(words.syntaxStartsWith(MULTIPLE_POSITIONS_PATTERN)){
			Set<Integer> numbers = new HashSet<Integer>();

			words.consume();	// DEFINITE_ARTICLE

			do {
				if(words.getCurrentElement().isOfType(WordType.PREPOSITION_AND)){
					words.consume();
				}

				numbers.add((int)parsePosition(words.getCurrentElement().getValue()));
				words.consume();
			} while(words.syntaxStartsWith(SINGLE_POSITION_PATTERN));

			result = new MultipleItemsObject(numbers);
		} else if (words.syntaxStartsWith(FIXED_ITEM_PATTERN)) {        	
			words.consume();
			result = new FixedItemObject(parsePosition(words.getCurrentElement().getValue()));
			words.consume();
		} else if(words.syntaxStartsWith(FIXED_RANGE_PATTERN)) {
			words.consume();

			try{
				Range r = Range.FIRST;
				long count = Long.parseLong(words.getCurrentElement().getValue());        		

				words.consume();

				String posValue = words.getCurrentElement().getValue();

				if(posValue.startsWith("premi")){
					r = CountObject.Range.FIRST;
				} else if(posValue.startsWith("derni")){
					r = CountObject.Range.LAST;
				} else {
					// TODO: error
				}

				words.consume();

				result = new LimitedItemsObject(r, count);                
			} catch(Exception e) {

			}
		} else if(words.syntaxStartsWith(ALL_PATTERN)) {
			result = new AllItemsObject();
			words.consume();
			words.consume();
		} else if(words.syntaxStartsWith(QUANTITY_PATTERN)){
			words.consume();			
			result = new QuantityObject((int)parseCount(words.getCurrentElement().getValue()));
			words.consume();
		} else if(words.syntaxStartsWith(SIMPLE_ARTICLE_PATTERN)) {        	
			boolean singular = isSingular(words.getCurrentElement().getValue());

			words.consume();

			if(singular){
				result = new FixedItemObject(1);
			} else {
				result = new AllItemsObject();
			}
		} else if(words.syntaxStartsWith(POSSESSIVE_PATTERN)){
			// TODO: integrer le possessif aux autres patterns
			ArticleType articleType = ArticleType.POSSESSIVE;
			IPronoun pronoun = Pronoun.parsePossessivePronoun(words.getCurrentElement().getValue());
			boolean singular = isSingular(words.getCurrentElement().getValue());
			
			words.consume();
			
			if(singular){
				result = new FixedItemObject(1);
			} else {
				result = new AllItemsObject();
			}
			
			result.definition = articleType;
			result.possessiveTarget = pronoun;
		}

		return result;
	}

	public static CountObject parseSuffixCountObject(FRWordBuffer words){
		CountObject result = null;

		if(words.syntaxStartsWith(MULTIPLE_ITEMS_PATTERN)){
			Set<Integer> numbers = new HashSet<Integer>();

			while(words.syntaxStartsWith(SINGLE_ITEM_PATTERN)){
				if(words.getCurrentElement().isOfType(WordType.PREPOSITION_AND)){
					words.consume();
				}

				if(words.getCurrentElement().getValue().startsWith("numéro")){
					words.consume();
				}

				numbers.add(Integer.parseInt(words.getCurrentElement().getValue()));
				words.consume();
			}

			result = new MultipleItemsObject(numbers);
		}

		return result;
	}
}
