package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ICustomMode;

@DatabaseTable(tableName = "modes")
public class CustomMode extends CustomData implements ICustomMode {	
	public CustomMode(){
		
	}
	
	public CustomMode(String text, String code){
		super(text, code);
	}
}
