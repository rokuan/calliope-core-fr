package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.TimePreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.TimeType;

public class TimePrepositionMatcher extends PrepositionMatcher<TimeType> {
	public static class TimePrepositionMatcherBuilder extends PrepositionMatcherBuilder<TimeType> {
		protected TimePrepositionMatcherBuilder(PrepositionMatcher<TimeType> m) {
			super(m);
		}
	}

	public TimePrepositionMatcherBuilder getBuilder(){
		return new TimePrepositionMatcherBuilder(this);
	}	
	
	@Override
	public boolean matches(Word word) {
		if(matchContractedForm && !((Word)word).isOfType(WordType.CONTRACTED)){
			return false;
		}
		
		if(possibleFollowers != null){
			TimePreposition prep = (TimePreposition)word.getTimePreposition();

			if(prep == null){
				return false;
			}
			
			for(TimeType ty: possibleFollowers){
				if(!prep.canBeFollowedBy(ty)){
					return false;
				}
			}
		}
		
		return true;
	}
}
