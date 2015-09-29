package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ICustomObject;

@DatabaseTable(tableName = "objects")
public class CustomObject extends CustomData implements ICustomObject {
	public CustomObject(){
		
	}
	
	public CustomObject(String name, String code){
		super(name, code);
	}
}
