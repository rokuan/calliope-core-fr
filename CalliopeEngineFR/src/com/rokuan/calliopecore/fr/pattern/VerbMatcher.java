package com.rokuan.calliopecore.fr.pattern;

import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordMatcher;
import com.rokuan.calliopecore.sentence.IAction.Form;

public class VerbMatcher implements WordMatcher<Word> {
	private boolean auxiliary = false;
	private String infiniteVerb = null;
	private String conjugatedVerb = null;
	private Form form = null;

	public static class VerbMatcherBuilder {
		private VerbMatcher matcher;

		private VerbMatcherBuilder(){
			matcher = new VerbMatcher();
		}

		private VerbMatcherBuilder(VerbMatcher m){
			matcher = m;
		}

		public VerbMatcherBuilder setVerbRegex(String verb){
			matcher.infiniteVerb = verb;
			return this;
		}

		public VerbMatcherBuilder setAuxiliary(boolean aux){
			matcher.auxiliary = aux;
			return this;
		}

		public VerbMatcherBuilder setConjugatedVerbRegex(String conjugatedForm){
			matcher.conjugatedVerb = conjugatedForm;
			return this;
		}

		public VerbMatcherBuilder setForm(Form form){
			matcher.form = form;
			return this;
		}

		public VerbMatcher build(){
			return matcher;
		}
	}
	
	public VerbMatcherBuilder getBuilder(){
		return new VerbMatcherBuilder(this);
	}

	@Override
	public boolean matches(Word w) {		
		if(!w.isOfType(WordType.VERB)){
			return false;
		}

		if(auxiliary && !w.isOfType(WordType.AUXILIARY)){
			return false;
		}

		if(conjugatedVerb != null && !w.getValue().matches(conjugatedVerb)){
			return false;
		}

		try{
			if(infiniteVerb != null
					//&& (w.getVerbInfo() == null || !((Verb)(w.getVerbInfo().getVerb())).getValue().matches(infiniteVerb))){
					&& (w.getVerbInfo() == null || !((VerbConjugation)w.getVerbInfo()).getVerb().getValue().matches(infiniteVerb))){
				return false;
			}
		}catch(Exception e){

		}

		if(form != null 
				&& (w.getVerbInfo() == null || w.getVerbInfo().getForm() != form)){
			return false;
		}

		return true;
	}
}
