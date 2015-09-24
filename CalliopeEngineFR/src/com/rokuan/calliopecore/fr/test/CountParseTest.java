package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.rokuan.calliopecore.fr.data.CountConverter;
import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.IPronoun;
import com.rokuan.calliopecore.sentence.structure.data.count.CountObject;
import com.rokuan.calliopecore.sentence.structure.data.count.FixedItemObject;
import com.rokuan.calliopecore.sentence.structure.data.count.LimitedItemsObject;
import com.rokuan.calliopecore.sentence.structure.data.count.MultipleItemsObject;
import com.rokuan.calliopecore.sentence.structure.data.count.CountObject.CountType;
import com.rokuan.calliopecore.sentence.structure.data.count.CountObject.Range;

public class CountParseTest {
	@Test
	public void testSimplePosition() {
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("le", WordType.DEFINITE_ARTICLE));
		words.add(new Word("premier", WordType.NUMERICAL_POSITION));

		CountObject count = CountConverter.parseCountObject(words); 

		assert (count != null);

		//assertEquals(count.count, 1);
		assertEquals(count.getType(), CountType.FIXED);

		FixedItemObject fixed = (FixedItemObject)count;

		assertEquals(fixed.position, 1);
	}

	@Test
	public void testRangeForFirst(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("5", WordType.NUMBER));
		words.add(new Word("premiers", WordType.NUMERICAL_POSITION));

		CountObject count = CountConverter.parseCountObject(words);

		assert (count != null);

		assertEquals(count.getType(), CountType.LIMIT);

		LimitedItemsObject limit = (LimitedItemsObject)count;

		assertEquals(limit.range, Range.FIRST);
		assertEquals(limit.count, 5);
	}

	@Test
	public void testRangeForLast(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("7", WordType.NUMBER));
		words.add(new Word("derniers", WordType.NUMERICAL_POSITION));

		CountObject count = CountConverter.parseCountObject(words);

		assert (count != null);

		assertEquals(count.getType(), CountType.LIMIT);

		LimitedItemsObject limit = (LimitedItemsObject)count;

		assertEquals(limit.range, Range.LAST);
		assertEquals(limit.count, 7);
	}
	
	@Test
	public void testMutiple1(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("numéros", WordType.COMMON_NAME));
		words.add(new Word("4", WordType.NUMBER));
		words.add(new Word("5", WordType.NUMBER));
		words.add(new Word("et", WordType.PREPOSITION_AND));
		words.add(new Word("15", WordType.NUMBER));
		
		CountObject count = CountConverter.parseSuffixCountObject(words);
		
		assert(count != null);
		
		assertEquals(count.getType(), CountType.MULTIPLE);
		
		MultipleItemsObject multiple = (MultipleItemsObject)count;
		
		assertEquals(multiple.items.length, 3);
		
		Integer[] trueElements = { 4, 5, 15 };
		
		assertArrayEquals(trueElements, multiple.items);
	}
	
	@Test
	public void testMutiple2(){
		FRWordBuffer words = new FRWordBuffer();
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("5ème", WordType.NUMERICAL_POSITION));
		words.add(new Word("8ème", WordType.NUMERICAL_POSITION));
		words.add(new Word("et", WordType.PREPOSITION_AND));
		words.add(new Word("19ème", WordType.NUMERICAL_POSITION));
		
		CountObject count = CountConverter.parseCountObject(words);
		
		assert(count != null);
		
		assertEquals(count.getType(), CountType.MULTIPLE);
		
		MultipleItemsObject multiple = (MultipleItemsObject)count;
		
		assertEquals(multiple.items.length, 3);
		
		Integer[] trueElements = { 5, 8, 19 };
		
		assertArrayEquals(trueElements, multiple.items);
	}
	
	@Test
	public void testQuantity(){
		FRWordBuffer words = new FRWordBuffer();
		
		words.add(new Word("les", WordType.DEFINITE_ARTICLE));
		words.add(new Word("4", WordType.NUMBER));
		
		CountObject count = CountConverter.parseCountObject(words);
		
		assertEquals(count.getType(), CountType.QUANTITY);
	}
	
	@Test
	public void testNumberRegex(){
		assertTrue("14".matches(CountConverter.NUMBER_REGEX));
	}
	
	@Test
	public void testPossessiveArticle(){
		FRWordBuffer words = new FRWordBuffer();
		
		words.add(new Word("mon", WordType.POSSESSIVE_ADJECTIVE));
		
		CountObject count = CountConverter.parseCountObject(words);
		
		assertEquals(count.getType(), CountType.FIXED);
		assertEquals(count.possessiveTarget.getSource(), IPronoun.PronounSource.I);
	}
	
	@Test
	public void testRealRegex(){
		assertTrue("32,567".matches(CountConverter.REAL_REGEX));
		assertTrue("32.567".matches(CountConverter.REAL_REGEX));
	}
}
