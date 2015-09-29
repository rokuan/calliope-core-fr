package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ICustomPlace;

@DatabaseTable(tableName = "places")
public class CustomPlace extends CustomData implements ICustomPlace {
	public CustomPlace(){
		
	}
	
	public CustomPlace(String name, String code){
		super(name, code);
	}
}
