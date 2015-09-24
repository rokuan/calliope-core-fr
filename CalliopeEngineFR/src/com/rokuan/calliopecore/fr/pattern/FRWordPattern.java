package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceType;
import com.rokuan.calliopecore.sentence.structure.data.purpose.PurposeAdverbial.PurposeType;
import com.rokuan.calliopecore.sentence.structure.data.time.TimeAdverbial.TimeType;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayType;


public class FRWordPattern {
	public static WordPattern simpleWord(WordType ty){
		return WordPattern.simple(SimpleWordMatcher.builder().setTypes(ty).build());
	}

	public static WordPattern simpleWord(WordType[] ty){
		return WordPattern.simple(SimpleWordMatcher.builder().setTypes(ty).build());
	}

	public static WordPattern simpleWord(String regex){
		return WordPattern.simple(SimpleWordMatcher.builder().setWordRegex(regex).build());
	}

	public static WordPattern simpleWord(WordType ty, String regex){
		return WordPattern.simple(SimpleWordMatcher.builder().setTypes(ty).setWordRegex(regex).build());
	}

	public static WordPattern simpleWord(WordType[] ty, String regex){
		return WordPattern.simple(SimpleWordMatcher.builder().setTypes(ty).setWordRegex(regex).build());
	}

	/*public static WordPattern simple(Word.WordType ty, String valueRegex, String verbRegex){
		return new WordSimplePattern(null);
	}*/

	/*public static WordPattern simpleVerb(String verbRegex){
		return new WordSimplePattern(new VerbMatcher().getBuilder().setVerbRegex(verbRegex).build());
	}

	public static WordPattern simpleVerb(String verbRegex, String conjugationRegex){
		return new WordSimplePattern(new VerbMatcher().getBuilder()
				.setVerbRegex(verbRegex)
				.setConjugatedVerbRegex(conjugationRegex)
				.build());
	}

	public static WordPattern simpleVerb(boolean auxiliary, String verbRegex, String conjugationRegex){
		return new WordSimplePattern(new VerbMatcher().getBuilder()
				.setAuxiliary(auxiliary)
				.setVerbRegex(verbRegex)
				.setConjugatedVerbRegex(conjugationRegex)
				.build());
	}*/

	public static WordPattern simplePlacePrep(PlaceType next){
		return simplePlacePrep(next, false);
	}

	public static WordPattern simplePlacePrep(PlaceType follower, boolean contracted){
		return WordPattern.simple(new PlacePrepositionMatcher().getBuilder()
		.setMatchContractedForm(contracted)
		.setPossibleFollowers(follower)
		.build()); 
	}

	public static WordPattern simpleTimePrep(TimeType next){
		return simpleTimePrep(next, false);
	}

	public static WordPattern simpleTimePrep(TimeType next, boolean contracted){
		return WordPattern.simple(new TimePrepositionMatcher().getBuilder()
		.setMatchContractedForm(contracted)
		.setPossibleFollowers(next)
		.build());
	}
	
	public static WordPattern simpleWayPrep(WayType next){
		return simpleWayPrep(next, false);
	}

	public static WordPattern simpleWayPrep(WayType next, boolean contracted){
		return WordPattern.simple(new WayPrepositionMatcher().getBuilder()
		.setMatchContractedForm(contracted)
		.setPossibleFollowers(next)
		.build());
	}
	
	public static WordPattern simplePurposePrep(PurposeType next){
		return simplePurposePrep(next, false);
	}

	public static WordPattern simplePurposePrep(PurposeType next, boolean contracted){
		return WordPattern.simple(new PurposePrepositionMatcher().getBuilder()
		.setMatchContractedForm(contracted)
		.setPossibleFollowers(next)
		.build());
	}
}
