package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.Preposition;
import com.rokuan.calliopecore.fr.sentence.TimePreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
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
	public Preposition<?, TimeType> getPreposition(Word w) {
		return (TimePreposition)w.getTimePreposition();
	}
}
