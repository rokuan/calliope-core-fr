package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.rokuan.calliopecore.fr.data.NominalGroupConverter;
import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.sentence.CustomObject;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.structure.content.INominalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.AdditionalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.NameObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.NominalGroup.GroupType;

public class NominalGroupParseTest {
	@Test
	public void NameObjectParseTest(){
		FRWordBuffer words = new FRWordBuffer();
		
		words.add(new Word("le", WordType.DEFINITE_ARTICLE));
		words.add(new Word("chat", WordType.COMMON_NAME));
		
		INominalObject nominal = NominalGroupConverter.parseDirectObject(words);
		
		assertEquals(nominal.getGroupType(), GroupType.COMMON_NAME);
		
		NameObject compl = (NameObject)nominal;
		
		assertEquals(compl.object, "chat");
	}
	
	@Test
	public void customObjectParseTest(){
		FRWordBuffer words = new FRWordBuffer();
		String objectName = "QR code";
		Word qr = new Word(objectName, WordType.OBJECT);
		
		qr.setCustomObject(new CustomObject(objectName, "QR_CODE"));
		
		words.add(new Word("le", WordType.DEFINITE_ARTICLE));
		words.add(qr);
		
		INominalObject nominal = NominalGroupConverter.parseDirectObject(words);
		
		assertEquals(nominal.getGroupType(), GroupType.OBJECT);
		
		AdditionalObject obj = (AdditionalObject)nominal;
		
		assertEquals(obj.object.getValue(), objectName);
	}
}
