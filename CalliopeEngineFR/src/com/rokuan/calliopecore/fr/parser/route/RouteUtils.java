package com.rokuan.calliopecore.fr.parser.route;

import com.rokuan.calliopecore.sentence.structure.content.INominalObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.NameObject;

public class RouteUtils {
	public static String getValueFromNominalGroup(INominalObject nominal){
		if(nominal == null){
			return "";
		}
		
		switch(nominal.getGroupType()){
		case COMMON_NAME:
			return ((NameObject)nominal).object.getValue();
		default:
			// TODO:
			return "";
		}
	}
}
