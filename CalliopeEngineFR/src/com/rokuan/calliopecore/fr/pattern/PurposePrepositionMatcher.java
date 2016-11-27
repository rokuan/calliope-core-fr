package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.Preposition;
import com.rokuan.calliopecore.fr.sentence.PurposePreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.sentence.structure.data.purpose.PurposeAdverbial.PurposeType;

public class PurposePrepositionMatcher extends PrepositionMatcher<PurposeType> {
	public static class PurposePrepositionMatcherBuilder extends PrepositionMatcherBuilder<PurposeType> {
		protected PurposePrepositionMatcherBuilder(
				PrepositionMatcher<PurposeType> m) {
			super(m);
		}
	}

	public PurposePrepositionMatcherBuilder getBuilder(){
		return new PurposePrepositionMatcherBuilder(this);
	}

	@Override
	public Preposition<?, PurposeType> getPreposition(Word w) {
		return (PurposePreposition)w.getPurposePreposition();
	}
}
