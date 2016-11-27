package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.Preposition;
import com.rokuan.calliopecore.fr.sentence.WayPreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
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
	public Preposition<?, WayType> getPreposition(Word w) {
		return (WayPreposition)w.getWayPreposition();
	}
}
