package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.pattern.WordPattern;

public class VerbPattern {
	public static WordPattern simple(String verbRegex){
		return WordPattern.simple(new VerbMatcher().getBuilder().setVerbRegex(verbRegex).build());
	}

	public static WordPattern simple(String verbRegex, String conjugationRegex){
		return WordPattern.simple(new VerbMatcher().getBuilder()
				.setVerbRegex(verbRegex)
				.setConjugatedVerbRegex(conjugationRegex)
				.build());
	}

	public static WordPattern simple(boolean auxiliary, String verbRegex, String conjugationRegex){
		return WordPattern.simple(new VerbMatcher().getBuilder()
				.setAuxiliary(auxiliary)
				.setVerbRegex(verbRegex)
				.setConjugatedVerbRegex(conjugationRegex)
				.build());
	}
}
