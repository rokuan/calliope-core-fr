package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordMatcher;

public class SimpleWordMatcher implements WordMatcher<Word> {
	private WordType[] types;
	private String wordRegex;

	public static class SimpleWordMatcherBuilder {
		private SimpleWordMatcher matcher;
		
		private SimpleWordMatcherBuilder(SimpleWordMatcher m){
			matcher = m;
		}
		
		public SimpleWordMatcherBuilder setTypes(WordType... wordTypes){
			matcher.types = wordTypes;
			return this;
		}
		
		public SimpleWordMatcherBuilder setWordRegex(String word){
			matcher.wordRegex = word;
			return this;
		}
		
		public SimpleWordMatcher build(){
			return matcher;
		}
	}
	
	public SimpleWordMatcherBuilder getBuilder(){
		return new SimpleWordMatcherBuilder(this);
	}
	
	@Override
	public boolean matches(Word word) {
		if(types != null){
			for(WordType ty: types){
				if(!word.isOfType(ty)){
					return false;
				}
			}
		}
		
		if(wordRegex != null && !word.getValue().matches(wordRegex)){
			return false;
		}
		
		return true;
	}
}
