package com.rokuan.calliopecore.fr.parser;

import com.rokuan.calliopecore.fr.sentence.AdjectiveInfo;
import com.rokuan.calliopecore.fr.sentence.CharacterInfo;
import com.rokuan.calliopecore.fr.sentence.CityInfo;
import com.rokuan.calliopecore.fr.sentence.ColorInfo;
import com.rokuan.calliopecore.fr.sentence.CountryInfo;
import com.rokuan.calliopecore.fr.sentence.CustomMode;
import com.rokuan.calliopecore.fr.sentence.CustomObject;
import com.rokuan.calliopecore.fr.sentence.CustomPerson;
import com.rokuan.calliopecore.fr.sentence.CustomPlace;
import com.rokuan.calliopecore.fr.sentence.FirstnameInfo;
import com.rokuan.calliopecore.fr.sentence.LanguageInfo;
import com.rokuan.calliopecore.fr.sentence.NameInfo;
import com.rokuan.calliopecore.fr.sentence.PlaceInfo;
import com.rokuan.calliopecore.fr.sentence.PlacePreposition;
import com.rokuan.calliopecore.fr.sentence.PurposePreposition;
import com.rokuan.calliopecore.fr.sentence.TimePreposition;
import com.rokuan.calliopecore.fr.sentence.TransportInfo;
import com.rokuan.calliopecore.fr.sentence.UnitInfo;
import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.fr.sentence.WayPreposition;
import com.rokuan.calliopecore.fr.sentence.Word;

public interface WordDatabase {
	boolean wordStartsWith(String q);

	Word findWord(String q);
	
	NameInfo findNameInfo(String q);
	AdjectiveInfo findAdjectiveInfo(String q);
	LanguageInfo findLanguageInfo(String q);
	ColorInfo findColorInfo(String q);
	CityInfo findCityInfo(String q);
	CountryInfo findCountryInfo(String q);
	TransportInfo findTransportInfo(String q);
	UnitInfo findUnitInfo(String q);
	FirstnameInfo findFirstnameInfo(String q);
	CharacterInfo findCharacterInfo(String q);
	PlaceInfo findPlaceInfo(String q);
	
	CustomObject findCustomObject(String q);
	CustomPlace findCustomPlace(String q);
	CustomMode findCustomMode(String q);
	CustomPerson findCustomPerson(String q);
	
	PlacePreposition findPlacePreposition(String q);
	TimePreposition findTimePreposition(String q);
	WayPreposition findWayPreposition(String q);
	PurposePreposition findPurposePreposition(String q);
	
	VerbConjugation findConjugation(String q);
}
