package com.rokuan.calliopecore.fr.sentence;

import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IPurposePreposition;
import com.rokuan.calliopecore.sentence.structure.data.purpose.PurposeAdverbial.PurposeContext;
import com.rokuan.calliopecore.sentence.structure.data.purpose.PurposeAdverbial.PurposeType;

@DatabaseTable(tableName = "purpose_prepositions")
public class PurposePreposition extends Preposition<PurposeContext, PurposeType> implements IPurposePreposition {
	public PurposePreposition(){
		super();
	}
}
