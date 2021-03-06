package com.rokuan.calliopecore.fr.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rokuan.calliopecore.fr.data.PlaceConverter;
import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.sentence.CityInfo;
import com.rokuan.calliopecore.fr.sentence.CountryInfo;
import com.rokuan.calliopecore.fr.sentence.CustomPlace;
import com.rokuan.calliopecore.fr.sentence.PlacePreposition;
import com.rokuan.calliopecore.fr.sentence.Word;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.sentence.structure.content.IPlaceObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.CityObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.CountryObject;
import com.rokuan.calliopecore.sentence.structure.data.place.AdditionalPlace;
import com.rokuan.calliopecore.sentence.structure.data.place.LocationObject;
import com.rokuan.calliopecore.sentence.structure.data.place.NamedPlaceObject;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceContext;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceType;


public class PlaceParseTest {
	@Test
	public void testCityParse(){
		FRWordBuffer words = new FRWordBuffer();
		Word paris = new Word("Paris", WordType.CITY, WordType.PROPER_NAME);
		Word in = new Word("�", WordType.PLACE_PREPOSITION);

		paris.setCityInfo(new CityInfo("Paris", 48.8564528, 2.3524282));
		in.setPlacePreposition(new PlacePreposition("�", PlaceContext.IN, PlaceType.CITY));
		
		words.add(in);
		words.add(paris);

		IPlaceObject place = PlaceConverter.parsePlaceAdverbial(words);

		assertEquals(place.getPlaceType(), PlaceType.CITY);

		CityObject c = (CityObject)place;

		assertEquals(c.city.getValue(), "Paris");
	}
	
	@Test
	public void testCountryParse(){
		FRWordBuffer words = new FRWordBuffer();
		Word france = new Word("France", WordType.COUNTRY);
		Word in = new Word("en", WordType.PREPOSITION_IN, WordType.PLACE_PREPOSITION);
		
		france.setCountryInfo(new CountryInfo("France", "FR"));
		in.setPlacePreposition(new PlacePreposition("en", PlaceContext.IN, PlaceType.COUNTRY));
		
		words.add(in);
		words.add(france);

		IPlaceObject place = PlaceConverter.parsePlaceAdverbial(words);

		assertEquals(place.getPlaceType(), PlaceType.COUNTRY);

		CountryObject c = (CountryObject)place;

		assertEquals(c.country.getValue(), "France");
		assertEquals(c.country.getCountryCode(), "FR");
	}

	@Test
	public void testCityAndCountryParse(){
		FRWordBuffer words = new FRWordBuffer();
		Word paris = new Word("Paris", WordType.CITY);
		Word france = new Word("France", WordType.COUNTRY);
		Word in1 = new Word("�", WordType.PREPOSITION_AT, WordType.PLACE_PREPOSITION);
		Word in2 = new Word("en", WordType.PREPOSITION_IN, WordType.PLACE_PREPOSITION); 

		paris.setCityInfo(new CityInfo("Paris", 48.8564528, 2.3524282));
		france.setCountryInfo(new CountryInfo("France", "FR"));
		in1.setPlacePreposition(new PlacePreposition("�", PlaceContext.IN, PlaceType.CITY));
		in2.setPlacePreposition(new PlacePreposition("en", PlaceContext.IN, PlaceType.COUNTRY));
		
		words.add(in1);
		words.add(paris);
		words.add(in2);
		words.add(france);

		IPlaceObject place = PlaceConverter.parsePlaceAdverbial(words);

		assertEquals(place.getPlaceType(), PlaceType.LOCATION);

		LocationObject state = (LocationObject)place;

		assertEquals(state.city.getValue(), "Paris");
		assertEquals(state.country.getValue(), "France");
	}
	
	@Test
	public void testMonumentParse(){
		FRWordBuffer words = new FRWordBuffer();
		Word at = new Word("�", WordType.PLACE_PREPOSITION);		

		at.setPlacePreposition(new PlacePreposition("�", PlaceContext.AT, PlaceType.NAMED_PLACE));
		
		words.add(at);
		words.add(new Word("la", WordType.DEFINITE_ARTICLE));
		words.add(new Word("Tour", WordType.PROPER_NAME));
		words.add(new Word("Eiffel", WordType.PROPER_NAME));
		
		IPlaceObject place = PlaceConverter.parsePlaceAdverbial(words);		
		NamedPlaceObject monument = (NamedPlaceObject)place;
		
		assertEquals(monument.name, "Tour Eiffel");
	}
	
	@Test
	public void testCommonPlaceParse(){
		FRWordBuffer words = new FRWordBuffer();
		Word at = new Word("�", WordType.PLACE_PREPOSITION);
		
		at.setPlacePreposition(new PlacePreposition("�", PlaceContext.AT, PlaceType.NAMED_PLACE));
		
		words.add(at);
		words.add(new Word("la", WordType.DEFINITE_ARTICLE));
		words.add(new Word("Mairie", WordType.PLACE_TYPE, WordType.PROPER_NAME, WordType.COMMON_NAME));
		words.add(new Word("de", WordType.PREPOSITION_OF));
		words.add(new Word("Paris", WordType.PROPER_NAME, WordType.CITY));
		
		IPlaceObject place = PlaceConverter.parsePlaceAdverbial(words);		
		NamedPlaceObject monument = (NamedPlaceObject)place;
		
		assertEquals(monument.name, "Mairie");
		assertEquals(monument.city, "Paris");
	}

	@Test
	public void testPlaceWithPreposition(){
		FRWordBuffer words = new FRWordBuffer();
		String placeName = "Mont Compote Energie";
		Word near = new Word("� proximit� de", WordType.PLACE_PREPOSITION);
		near.setPlacePreposition(new PlacePreposition("� proximit� de", PlaceContext.NEAR, PlaceType.CUSTOM));
		Word mountCompoteEnergie = new Word(placeName, WordType.CUSTOM_PLACE);
		mountCompoteEnergie.setCustomPlace(new CustomPlace(placeName, "MOUNT_COMP"));
		
		words.add(near);
		words.add(new Word("le", WordType.DEFINITE_ARTICLE));
		words.add(mountCompoteEnergie);
		
		IPlaceObject place = PlaceConverter.parsePlaceAdverbial(words);
		
		assertEquals(place.getPlaceType(), PlaceType.CUSTOM);
		
		AdditionalPlace customPlace = (AdditionalPlace)place;
		
		assertEquals(customPlace.getPlacePreposition().getContext(), PlaceContext.NEAR);
		assertEquals(customPlace.place.getValue(), placeName);
	}	
}
