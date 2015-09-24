package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.pattern.FRWordPattern;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordPattern;

public class PatternTest {
	@Test
	public void testSequence(){
		FRWordBuffer words = new FRWordBuffer();
		WordPattern sequence = WordPattern.sequence(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE), FRWordPattern.simpleWord(WordType.COMMON_NAME));

		assertFalse(words.syntaxStartsWith(sequence));

		words.add(new Word("le", WordType.DEFINITE_ARTICLE));
		words.add(new Word("chocolat", WordType.COMMON_NAME));
		words.add(new Word("est", WordType.VERB));
		words.add(new Word("bon", WordType.ADJECTIVE));

		assertTrue(words.syntaxStartsWith(sequence));
	}

	@Test
	public void testOptional(){
		FRWordBuffer words = new FRWordBuffer();
		WordPattern optional = WordPattern.optional(FRWordPattern.simpleWord(WordType.QUANTITY));

		assertFalse(words.syntaxStartsWith(optional));

		words.add(new Word("toutes", WordType.QUANTITY));
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("oranges", WordType.COMMON_NAME));
		words.add(new Word("sont", WordType.VERB));
		words.add(new Word("oranges", WordType.ADJECTIVE));

		assertTrue(words.syntaxStartsWith(optional));
	}

	@Test
	public void testSimple(){
		FRWordBuffer words = new FRWordBuffer();
		WordPattern simple = FRWordPattern.simpleWord(WordType.PROPER_NAME);

		assertFalse(words.syntaxStartsWith(simple));

		words.add(new Word("Calliope", WordType.PROPER_NAME));
		words.add(new Word("est", WordType.VERB));
		words.add(new Word("un", WordType.INDEFINITE_ARTICLE));
		words.add(new Word("programme", WordType.COMMON_NAME));

		assertTrue(words.syntaxStartsWith(simple));
	}
	
	@Test
	public void testOr(){
		FRWordBuffer words = new FRWordBuffer();
		WordPattern or = WordPattern.or(FRWordPattern.simpleWord(WordType.PROPER_NAME), FRWordPattern.simpleWord(WordType.COMMON_NAME));
		
		words.add(new Word("programme", WordType.COMMON_NAME));
		
		assertTrue(words.syntaxStartsWith(or));
	}

	@Test
	public void testList(){
		FRWordBuffer words = new FRWordBuffer();
		WordPattern list = WordPattern.nonEmptyList(
				WordPattern.sequence(FRWordPattern.simpleWord(WordType.INDEFINITE_ARTICLE), FRWordPattern.simpleWord(WordType.COMMON_NAME)));

		assertFalse(words.syntaxStartsWith(list));

		words.add(new Word("un", WordType.INDEFINITE_ARTICLE));
		words.add(new Word("oiseau", WordType.COMMON_NAME));
		words.add(new Word("un", WordType.INDEFINITE_ARTICLE));
		words.add(new Word("enfant", WordType.COMMON_NAME));
		words.add(new Word("une", WordType.INDEFINITE_ARTICLE));
		words.add(new Word("chèvre", WordType.COMMON_NAME));

		assertTrue(words.syntaxStartsWith(list));
	}

	@Test
	public void testSeparatedList(){
		FRWordBuffer words = new FRWordBuffer();
		WordPattern separatedList = WordPattern.separatedNonEmptyList(
				WordPattern.sequence(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE), FRWordPattern.simpleWord(WordType.COMMON_NAME)),
				WordPattern.optional(FRWordPattern.simpleWord(WordType.PREPOSITION_AND))
				);

		assertFalse(words.syntaxStartsWith(separatedList));

		words.add(new Word("la", WordType.DEFINITE_ARTICLE));
		words.add(new Word("farine", WordType.COMMON_NAME));
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("oeufs", WordType.COMMON_NAME));
		words.add(new Word("et", WordType.PREPOSITION_AND));
		words.add(new Word("le", WordType.DEFINITE_ARTICLE));
		words.add(new Word("sucre", WordType.COMMON_NAME));

		assertTrue(words.syntaxStartsWith(separatedList));
	}
}
