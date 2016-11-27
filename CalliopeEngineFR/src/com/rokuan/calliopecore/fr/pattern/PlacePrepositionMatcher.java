package com.rokuan.calliopecore.fr.pattern;


import com.rokuan.calliopecore.fr.sentence.PlacePreposition;
import com.rokuan.calliopecore.fr.sentence.Preposition;
import com.rokuan.calliopecore.fr.sentence.Word;
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
	public Preposition<?, PlaceType> getPreposition(Word w) {
		return (PlacePreposition)w.getPlacePreposition();
	}
}
