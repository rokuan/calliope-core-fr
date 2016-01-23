package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.ITransportInfo;
import com.rokuan.calliopecore.sentence.structure.data.way.TransportObject.TransportType;

@DatabaseTable(tableName = "transports")
public class TransportInfo implements ITransportInfo {
	public static final String TRANSPORT_FIELD_NAME = "transport_name";
	public static final String TYPE_FIELD_NAME = "transport_type";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = TRANSPORT_FIELD_NAME, uniqueIndex = true)
	private String name;
	
	@DatabaseField(columnName = TYPE_FIELD_NAME)
	private TransportType transportType;
	
	public TransportInfo(){
		
	}
	
	public TransportInfo(String tName, TransportType tType){
		name = tName;
		transportType = tType;
	}
	
	@Override
	public String getValue(){
		return name;
	}
	
	@Override
	public TransportType getTransportType(){
		return transportType;
	}
}
