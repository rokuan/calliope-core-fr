package com.rokuan.calliopecore.fr.data.nominal;

import java.util.Set;

import com.rokuan.calliopecore.fr.sentence.Pronoun;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;

public class PronounObject {
	private static final int SOURCE_PRONOUN = 0x1;
	private static final int TARGET_PRONOUN = 0x10;
	private static final int REFLEXIVE_PRONOUN = 0x100;
	private String value;
	private int types = 0;
	
	//public PronounObject(IPronoun src, Set<WordType> ts) {
	public PronounObject(String v, Set<WordType> ts) {		
		if(ts.contains(WordType.SOURCE_PRONOUN)){
			types |= SOURCE_PRONOUN;
		}
		
		if(ts.contains(WordType.TARGET_PRONOUN)){
			types |= TARGET_PRONOUN;
		}
		
		if(ts.contains(WordType.REFLEXIVE_PRONOUN)){
			types |= REFLEXIVE_PRONOUN;
		}
	}

	public boolean isSource(){
		return (types & SOURCE_PRONOUN) != 0;
	}
	
	public boolean isTarget(){
		return (types & TARGET_PRONOUN) != 0;
	}

	public boolean isReflexive(){
		return (types & REFLEXIVE_PRONOUN) != 0;
	}
	
	public Pronoun getSourceForm(){
		return Pronoun.parseDirectPronoun(value);
	}
	
	public Pronoun getTargetForm(){
		return Pronoun.parseTargetPronoun(value);
	}
	
	public Pronoun getReflexiveForm(){
		return Pronoun.parseReflexivePronoun(value);
	}
}
