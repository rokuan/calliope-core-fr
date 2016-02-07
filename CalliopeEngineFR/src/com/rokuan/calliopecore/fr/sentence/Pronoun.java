package com.rokuan.calliopecore.fr.sentence;

import com.rokuan.calliopecore.sentence.IPronoun;
import com.rokuan.calliopecore.sentence.structure.content.INominalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.PronounSubject;

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
	
	private static final int JE_MASK = 			0x1;
	private static final int TU_MASK = 			0x10;
	private static final int IL_ELLE_ON_ILS_ELLES_MASK = 	0x100;
	private static final int NOUS_MASK = 		0x1000;
	private static final int VOUS_MASK = 		0x10000;

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
	
	public static Pronoun parseReflexivePronoun(String str){
		PronounSource p = PronounSource.UNDEFINED;
		
		if(str.equals("me") || str.equals("m")){
			p = PronounSource.I;
		} else if(str.equals("tu") || str.equals("t")){
			p = PronounSource.YOU;
		} else if(str.equals("se")){
			p = PronounSource.HE_SHE;
		} else if(str.equals("nous")){
			p = PronounSource.WE;
		} else if(str.equals("vous")){
			p = PronounSource.YOU_;
		}
		
		return new Pronoun(str, p);
	}
	
	public static boolean sameSource(INominalObject subject, Pronoun pronoun){
		int subjectMask = 0;
		int pronounMask = getMaskFromReflexivePronoun(pronoun);
		
		switch(subject.getGroupType()){
		/*case ABSTRACT:
			clazz = AbstractTarget.class;
			break;*/
		case COLOR:
		case LANGUAGE:
		case NUMBER:		
		case PHONE_NUMBER:
		case UNIT:
		case DATE:
		case CITY:
		case COUNTRY:
		case LOCATION:
		case NAMED_PLACE:
		case CHARACTER:
		case PLACE_TYPE:
		case COMMON_NAME:
		case QUANTITY:
			subjectMask = IL_ELLE_ON_ILS_ELLES_MASK;
			break;
		/*case OBJECT:
			clazz = AdditionalObject.class;
			break;
		case PERSON:
			clazz = AdditionalPerson.class;
			break;*/				
		case PRONOUN:
			subjectMask = getMaskFromPronounSubject((PronounSubject)subject);
			break;
			// TODO: check if such a sentence does exist (subject verb + reflexive)
		/*case VERB:
			clazz = VerbalGroup.class;
			break;*/
		/*case ADDITIONAL_PLACE:
			clazz = AdditionalPlace.class;
			break;*/
		default:
			break;
		}
		
		return (subjectMask & pronounMask) > 0;
	}
	
	private static int getMaskFromPronounSubject(PronounSubject pronoun){
		String value = pronoun.pronoun.getValue().toLowerCase();
		
		switch(value.charAt(0)){
		case 'j':
			return JE_MASK;
		case 't':
			return TU_MASK;
		case 'i':
		case 'e':
		case 'o':
			return IL_ELLE_ON_ILS_ELLES_MASK;
		case 'n':
			return NOUS_MASK;
		case 'v':
			return VOUS_MASK;
		}
		
		return 0;
	}
	
	private static int getMaskFromReflexivePronoun(Pronoun pronoun){
		String value = pronoun.getValue().toLowerCase();
		
		switch(value.charAt(0)){
		case 'm':
			return JE_MASK;
		case 't':
			return TU_MASK;
		case 's':
			return IL_ELLE_ON_ILS_ELLES_MASK;
		case 'n':
			return NOUS_MASK;
		case 'v':
			return VOUS_MASK;
		}
		
		return 0;
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
