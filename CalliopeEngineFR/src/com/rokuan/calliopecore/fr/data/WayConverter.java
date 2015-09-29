package com.rokuan.calliopecore.fr.data;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.pattern.FRWordPattern;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.structure.content.INominalObject;
import com.rokuan.calliopecore.sentence.structure.content.IWayObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.ColorObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.LanguageObject;
import com.rokuan.calliopecore.sentence.structure.data.way.AdditionalMode;
import com.rokuan.calliopecore.sentence.structure.data.way.TransportObject;
import com.rokuan.calliopecore.sentence.structure.data.way.WayAdverbial.WayType;

public class WayConverter {
	public static final WordPattern MEANS_OF_TRANSPORT_PATTERN = WordPattern.sequence(
			//FRWordPattern.simpleWord("�|en|par"),
			FRWordPattern.simpleWayPrep(WayType.TRANSPORT),
			FRWordPattern.simpleWord(WordType.MEAN_OF_TRANSPORT));
	
	public static final WordPattern LANGUAGE_ONLY_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE), 
			FRWordPattern.simpleWord(WordType.LANGUAGE));
	
	public static final WordPattern LANGUAGE_PATTERN = WordPattern.sequence(
			//FRWordPattern.simpleWord(WordType.WAY_PREPOSITION, "en"),
			FRWordPattern.simpleWayPrep(WayType.LANGUAGE),
			FRWordPattern.simpleWord(WordType.LANGUAGE)
			);
	
	public static final WordPattern MODE_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.WAY_PREPOSITION),
			FRWordPattern.simpleWord(WordType.MODE));
	
	public static final WordPattern COLOR_PATTERN = WordPattern.sequence(
			//FRWordPattern.simpleWord(WordType.WAY_PREPOSITION, "en"),
			FRWordPattern.simpleWayPrep(WayType.COLOR),
			FRWordPattern.simpleWord(WordType.COLOR)); 

	public static boolean isAWayAdverbial(FRWordBuffer words){
		return words.syntaxStartsWith(MEANS_OF_TRANSPORT_PATTERN)
				|| words.syntaxStartsWith(LANGUAGE_PATTERN)
				|| words.syntaxStartsWith(MODE_PATTERN)
				|| words.syntaxStartsWith(COLOR_PATTERN);
	}
	
	public static boolean isANominalGroup(FRWordBuffer words){
		return words.syntaxStartsWith(LANGUAGE_ONLY_PATTERN);
	}

	public static IWayObject parseWayAdverbial(FRWordBuffer words){
		IWayObject result = null;
		
		if(words.syntaxStartsWith(MODE_PATTERN)){
			AdditionalMode custom = new AdditionalMode();
			
			words.consume();
			custom.mode = words.getCurrentElement().getCustomMode();
			words.consume();
			
			result = custom;
		} else if(words.syntaxStartsWith(MEANS_OF_TRANSPORT_PATTERN)){
			TransportObject transport = new TransportObject();
			
			transport.setWayPreposition(words.getCurrentElement().getWayPreposition());
			words.consume();
			transport.transportType = words.getCurrentElement().getTransportInfo().getTransportType();			
			words.consume();
			
			result = transport;
		} else if(words.syntaxStartsWith(LANGUAGE_PATTERN)){
			LanguageObject lang = new LanguageObject();
			
			words.consume();	// "en"
			lang.language = words.getCurrentElement().getLanguageInfo();
			words.consume();
			
			result = lang;
		} else if(words.syntaxStartsWith(COLOR_PATTERN)){
			ColorObject col = new ColorObject();
			
			words.consume(); // "en"
			col.color = words.getCurrentElement().getColorInfo();
			words.consume();
			
			result = col;
		}
		
		return result;
	}
	
	public static INominalObject parseNominalWayObject(FRWordBuffer words){
		INominalObject result = null;
		
		if(words.syntaxStartsWith(LANGUAGE_ONLY_PATTERN)){
			LanguageObject language = new LanguageObject();
			
			if(words.getCurrentElement().isOfType(WordType.DEFINITE_ARTICLE)){
				words.consume();
			}
			
			language.language = words.getCurrentElement().getLanguageInfo();
			words.consume();
			
			result = language;
		}
		
		return result;
	}
}
