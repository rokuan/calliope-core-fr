package com.rokuan.calliopecore.fr.sentence;

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

	private String value;
	private PronounSource source = PronounSource.UNDEFINED; 

	public Pronoun(String s, PronounSource p){
		value = s;
		source = p;
	}

	@Override
	public String getValue() {
		return value;
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

		return new Pronoun(str, p);
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
			p = PronounSource.THEY;
		}

		return new Pronoun(str, p);
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
			p = PronounSource.THEY;
		}
		
		return new Pronoun(str, p);
	}
	
	public static Pronoun parsePossessivePronoun(String str){
		PronounSource p = PronounSource.UNDEFINED;
		
		if(str.equals("mon") || str.equals("ma") || str.equals("mes")){
			p = PronounSource.I;
		} else if(str.equals("ton") || str.equals("ta") || str.equals("tes")){
			p = PronounSource.YOU;
		} else if(str.equals("son") || str.equals("sa") || str.equals("ses")){
			p = PronounSource.HE_SHE;
		} else if(str.equals("notre") || str.equals("nos")){
			p = PronounSource.WE;
		} else if(str.equals("votre") || str.equals("vos")){
			p = PronounSource.YOU_;
		} else if(str.equals("leur") || str.equals("leurs")){
			p = PronounSource.THEY;
		}
		
		return new Pronoun(str, p);
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
