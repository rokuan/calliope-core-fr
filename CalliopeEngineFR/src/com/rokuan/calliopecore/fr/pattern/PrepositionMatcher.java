package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.Preposition;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordMatcher;


public abstract class PrepositionMatcher<FollowerType> implements WordMatcher<Word> {
	protected boolean matchContractedForm = false;
	protected FollowerType[] possibleFollowers = null;

	public PrepositionMatcherBuilder<FollowerType> getBuilder(){
		return new PrepositionMatcherBuilder<FollowerType>(this);
	}
	
	public static class PrepositionMatcherBuilder<FType> {
		private PrepositionMatcher<FType> matcher;		
		
		protected PrepositionMatcherBuilder(PrepositionMatcher<FType> m){
			matcher = m;
		}
		
		public PrepositionMatcherBuilder<FType> setMatchContractedForm(boolean shouldMatch){
			matcher.matchContractedForm = shouldMatch;
			return this;
		}
		
		public PrepositionMatcherBuilder<FType> setPossibleFollowers(FType[] types){
			matcher.possibleFollowers = types;
			return this;
		}
		
		public PrepositionMatcher<FType> build(){
			return matcher;
		}
	}
	
	@Override
	public boolean matches(Word word) {
		if(matchContractedForm && !((Word)word).isOfType(WordType.CONTRACTED)){
			return false;
		}

		if(possibleFollowers != null){
			Preposition<?, FollowerType> prep = getPreposition(word);

			if(prep == null){
				return false;
			}
			
			for(FollowerType ty: possibleFollowers){
				if(!prep.canBeFollowedBy(ty)){
					return false;
				}
			}
		}

		return true;
	}
	
	public abstract Preposition<?, FollowerType> getPreposition(Word w);
}
