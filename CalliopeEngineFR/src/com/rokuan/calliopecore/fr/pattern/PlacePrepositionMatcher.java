package com.rokuan.calliopecore.fr.pattern;


import com.rokuan.calliopecore.fr.sentence.PlacePreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.IWord;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceType;

public class PlacePrepositionMatcher extends PrepositionMatcher<PlaceType> {
	public static class PlacePrepositionMatcherBuilder extends PrepositionMatcherBuilder<PlaceType> {
		protected PlacePrepositionMatcherBuilder(PrepositionMatcher<PlaceType> m) {
			super(m);
		}
	}
	
	public PlacePrepositionMatcherBuilder getBuilder(){
		return new PlacePrepositionMatcherBuilder(this);
	}

	@Override
	public boolean matches(IWord word) {
		if(matchContractedForm && !((Word)word).isOfType(WordType.CONTRACTED)){
			return false;
		}

		if(possibleFollowers != null){
			PlacePreposition prep = (PlacePreposition)word.getPlacePreposition();

			if(prep == null){
				return false;
			}
			
			for(PlaceType ty: possibleFollowers){
				if(!prep.canBeFollowedBy(ty)){
					return false;
				}
			}
		}

		return true;
	}
}
