package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rokuan.calliopecore.sentence.structure.data.CountConverter;

public class NumberParseTest {
	@Test
	public void testNth(){
		assertEquals(2, CountConverter.parsePosition("deuxième"));
		assertEquals(5, CountConverter.parsePosition("cinquième"));
		assertEquals(10, CountConverter.parsePosition("dixième"));
		//assertEquals(32, NumberConverter.parsePosition("trente-deuxième"));
		//assertEquals(41, NumberConverter.parsePosition("quarante-et-unième"));
	}
	
	@Test
	public void testNumberString(){
		//assertEquals(42, NumberConverter.parsePosition("quarante-deuxième"));
		//assertEquals(42051, NumberConverter.parsePosition("quarante-deux-mille-cinquante-et-unième"));
	}
}
