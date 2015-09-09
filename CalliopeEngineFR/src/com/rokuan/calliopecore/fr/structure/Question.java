package com.rokuan.calliopecore.fr.structure;

import com.rokuan.calliopecore.sentence.structure.QuestionObject.QuestionType;

public class Question {
	public static QuestionType parseInterrogativePronoun(String value){
    	// TODO: s'assurer qu'on ne tente pas de parser le mot "quelque(s)"
    	String lowerValue = value.toLowerCase();
    	
    	if(lowerValue.startsWith("quel") || lowerValue.equals("que") || lowerValue.equals("qu")){
    		return QuestionType.WHAT;
    	} else if(lowerValue.equals("quand")){
    		return QuestionType.WHEN;
    	} else if(lowerValue.equals("où")){
    		return QuestionType.WHERE;
    	} else if(lowerValue.equals("comment")){
    		return QuestionType.HOW;
    	} else if(lowerValue.equals("pourquoi")){
    		return QuestionType.WHY;
    	} else if(lowerValue.equals("qui")){
    		return QuestionType.WHO;
    	} else if(lowerValue.equals("combien")){
    		return QuestionType.HOW_MANY;
    	} else if(lowerValue.startsWith("lequel") || lowerValue.startsWith("lesquel")){
    		return QuestionType.WHICH;
    	} else if(lowerValue.equals("est-ce que") || lowerValue.equals("est-ce qu")){
    		return QuestionType.YES_NO;
    	}
    	
    	return QuestionType.UNDEFINED;
    }
}
