package com.rokuan.calliopecore.fr.sentence;

import com.google.gson.annotations.Expose;
import com.rokuan.calliopecore.sentence.IPronoun;

public class Pronoun implements IPronoun {
	public enum Person {
		JE,
		TU,
		IL_ELLE_ON,
		NOUS,
		VOUS,
		ILS_ELLES,
		UNDEFINED
	}

	public enum SourcePronoun {
		JE,
		TU,
		IL,
		ELLE,
		NOUS,
		VOUS,
		ILS,
		ELLES
	}

	@Expose
	private PronounSource source = PronounSource.UNDEFINED; 

	public Pronoun(PronounSource p){
		source = p;
	}
	
	@Override
	public PronounSource getSource() {
		return source;
	}

	public static Pronoun parseSubjectPronoun(String str){
		PronounSource p = PronounSource.UNDEFINED;

		if(str.equals("j") || str.equals("je")){
			p = PronounSource.I;
		} else if(str.equals("tu")){ 
			p = PronounSource.YOU;
		} else if(str.equals("il")){ 
			p = PronounSource.HE;
		} else if(str.equals("elle")) {
			p = PronounSource.SHE;
		} else if(str.equals("nous") || str.equals("on")){
			p = PronounSource.WE;
		} else if(str.equals("vous")){ 
			p = PronounSource.YOU_;
		} else if(str.equals("ils")){
			p = PronounSource.HE_;
		} else if(str.equals("elles")){
			p = PronounSource.SHE_;
		}

		return new Pronoun(p);
	}

	public static Pronoun parseTargetPronoun(String str){
		PronounSource p = PronounSource.UNDEFINED;

		if(str.equals("moi") || str.equals("me") || str.equals("m")){
			p = PronounSource.I;
		} else if(str.equals("toi") || str.equals("te") || str.equals("t")){
			p = PronounSource.YOU;
			//} else if(str.equals("lui") || str.equals("le") || str.equals("la") || str.equals("y")){
		} else if(str.equals("lui")){
			p = PronounSource.HE_SHE;
		} else if(str.equals("nous")){
			p = PronounSource.WE;
		} else if(str.equals("vous")){
			p = PronounSource.YOU_;
		} else if(str.equals("leur")){
			p = PronounSource.HE__SHE__;
		}

		return new Pronoun(p);
	}
	
	public static Pronoun parseDirectPronoun(String str){
		PronounSource p = PronounSource.UNDEFINED;
		
		if(str.equals("moi")){
			p = PronounSource.I;
		} else if(str.equals("toi")){
			p = PronounSource.YOU;
		} else if(str.equals("le")){ 
			p = PronounSource.HE;
		} else if(str.equals("la")){
			p = PronounSource.SHE;
		} else if(str.equals("nous")){
			p = PronounSource.WE;
		} else if(str.equals("vous")){
			p = PronounSource.YOU_;
		} else if(str.equals("les")){
			p = PronounSource.HE__SHE__;
		}
		
		return new Pronoun(p);
	}

	/*public static Pronoun parseIndirectPronoun(String str){
		if(str.equals("me") || str.equals("m")){
			return Pronoun.JE;
		} else if(str.equals("te") || str.equals("t")){
			return Pronoun.TU;
		} else if(str.equals("lui")){
			return Pronoun.IL_ELLE_ON;
		} else if(str.equals("nous")){
			return Pronoun.NOUS;
		} else if(str.equals("vous")){
			return Pronoun.VOUS;
		} else if(str.equals("leur")){
			return Pronoun.ILS_ELLES;
		}

		return Pronoun.UNDEFINED;
	}*/

	/*public static Pronoun parsePossessivePronoun(String str){
		PronounSource p = PronounSource.UNDEFINED;
		
		if(str.equals("moi")){
			return Pronoun.JE;
		} else if(str.equals("toi")){
			return Pronoun.TU;
		} else if(str.equals("lui")){
			return Pronoun.IL_ELLE_ON;
		} else if(str.equals("nous")){
			return Pronoun.NOUS;
		} else if(str.equals("vous")){
			return Pronoun.VOUS;
		} else if(str.equals("leur")){
			return Pronoun.ILS_ELLES;
		}

		return Pronoun.UNDEFINED;
	}*/

	/*public static SourcePronoun parseDirectPronoun(String str){
		if(str.equals("moi")){
			return SourcePronoun.JE;
		} else if(str.equals("toi")){
			return SourcePronoun.TU;
		} else if(str.equals("le")){ 
			return SourcePronoun.IL;
		} else if(str.equals("la")){
			return SourcePronoun.ELLE;
		} else if(str.equals("nous")){
			return SourcePronoun.NOUS;
		} else if(str.equals("vous")){
			return SourcePronoun.VOUS;
		} else {
			return SourcePronoun.ILS;
		}
	}

	public static Pronoun parseIndirectPronoun(String str){
		if(str.equals("me") || str.equals("m")){
			return Pronoun.JE;
		} else if(str.equals("te") || str.equals("t")){
			return Pronoun.TU;
		} else if(str.equals("lui")){
			return Pronoun.IL_ELLE_ON;
		} else if(str.equals("nous")){
			return Pronoun.NOUS;
		} else if(str.equals("vous")){
			return Pronoun.VOUS;
		} else if(str.equals("leur")){
			return Pronoun.ILS_ELLES;
		}

		return Pronoun.UNDEFINED;
	}

	public static Pronoun parsePossessivePronoun(String str){
		if(str.equals("moi")){
			return Pronoun.JE;
		} else if(str.equals("toi")){
			return Pronoun.TU;
		} else if(str.equals("lui")){
			return Pronoun.IL_ELLE_ON;
		} else if(str.equals("nous")){
			return Pronoun.NOUS;
		} else if(str.equals("vous")){
			return Pronoun.VOUS;
		} else if(str.equals("leur")){
			return Pronoun.ILS_ELLES;
		}

		return Pronoun.UNDEFINED;
	}*/	
}
