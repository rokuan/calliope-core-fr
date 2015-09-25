package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.WayPreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.IWord;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayType;

public class WayPrepositionMatcher extends PrepositionMatcher<WayType> {
	public static class WayPrepositionMatcherBuilder extends PrepositionMatcherBuilder<WayType> {
		protected WayPrepositionMatcherBuilder(PrepositionMatcher<WayType> m) {
			super(m);
		}
	}

	public WayPrepositionMatcherBuilder getBuilder(){
		return new WayPrepositionMatcherBuilder(this);
	}	

	@Override
	public boolean matches(IWord word) {
		if(matchContractedForm && !((Word)word).isOfType(WordType.CONTRACTED)){
			return false;
		}

		if(possibleFollowers != null){
			WayPreposition prep = (WayPreposition)word.getWayPreposition();

			if(prep == null){
				return false;
			}
			
			for(WayType ty: possibleFollowers){
				if(!prep.canBeFollowedBy(ty)){
					return false;
				}
			}
		}

		return true;
	}
}
