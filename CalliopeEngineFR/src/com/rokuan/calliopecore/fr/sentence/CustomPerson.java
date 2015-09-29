package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ICustomPerson;

@DatabaseTable(tableName = "people")
public class CustomPerson extends CustomData implements ICustomPerson {	
	public CustomPerson(){
		
	}
	
	public CustomPerson(String name, String code){
		super(name, code);
	}
}
