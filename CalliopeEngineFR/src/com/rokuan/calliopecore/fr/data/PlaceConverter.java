package com.rokuan.calliopecore.fr.data;

import com.rokuan.calliopecore.fr.parser.FRWordBuffer;
import com.rokuan.calliopecore.fr.pattern.FRWordPattern;
import com.rokuan.calliopecore.fr.sentence.Word.WordType;
import com.rokuan.calliopecore.pattern.WordPattern;
import com.rokuan.calliopecore.sentence.structure.content.INominalObject;
import com.rokuan.calliopecore.sentence.structure.content.IPlaceObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.CityObject;
import com.rokuan.calliopecore.sentence.structure.data.nominal.CountryObject;
import com.rokuan.calliopecore.sentence.structure.data.place.AdditionalPlace;
import com.rokuan.calliopecore.sentence.structure.data.place.AddressObject;
import com.rokuan.calliopecore.sentence.structure.data.place.LocationObject;
import com.rokuan.calliopecore.sentence.structure.data.place.NamedPlaceObject;
import com.rokuan.calliopecore.sentence.structure.data.place.PlaceAdverbial.PlaceType;

public class PlaceConverter {
	public static final WordPattern STREET_ADDRESS_PATTERN = WordPattern.sequence(WordPattern.optional(FRWordPattern.simpleWord(WordType.NUMBER)),
			FRWordPattern.simpleWord(WordType.STREET_TYPE),
			WordPattern.nonEmptyList(FRWordPattern.simpleWord(WordType.PROPER_NAME)));
	
	public static final WordPattern CITY_ONLY_PATTERN = FRWordPattern.simpleWord(WordType.CITY);

	public static final WordPattern COUNTRY_ONLY_PATTERN = FRWordPattern.simpleWord(WordType.COUNTRY);

	public static final WordPattern ADDRESS_ONLY_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE),
			STREET_ADDRESS_PATTERN);			

	public static final WordPattern PLACE_ONLY_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE),
			WordPattern.optional(FRWordPattern.simpleWord(WordType.PLACE_TYPE)),
			WordPattern.optional(WordPattern.nonEmptyList(FRWordPattern.simpleWord(WordType.PROPER_NAME))),
			WordPattern.optional(WordPattern.sequence(FRWordPattern.simpleWord(WordType.PREPOSITION_OF), 
					WordPattern.optional(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE)),
					WordPattern.optional(WordPattern.or(
							FRWordPattern.simpleWord(WordType.COUNTRY),
							FRWordPattern.simpleWord(WordType.CITY),
							WordPattern.nonEmptyList(FRWordPattern.simpleWord(WordType.PROPER_NAME))
							))
					))
			);

	public static final WordPattern ADDITIONAL_PLACE_ONLY_PATTERN = WordPattern.sequence(
			WordPattern.optional(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE)),
			FRWordPattern.simpleWord(WordType.ADDITIONAL_PLACE)
			);	
	
	private static final WordPattern COUNTRY_PREPOSITION_PATTERN = WordPattern.or(
			WordPattern.sequence(FRWordPattern.simplePlacePrep(PlaceType.COUNTRY), WordPattern.optional(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE))),
			FRWordPattern.simplePlacePrep(PlaceType.COUNTRY, true));
	private static final WordPattern CITY_PREPOSITION_PATTERN = WordPattern.or(
			WordPattern.sequence(FRWordPattern.simplePlacePrep(PlaceType.CITY), WordPattern.optional(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE))),
			FRWordPattern.simplePlacePrep(PlaceType.CITY, true));
	private static final WordPattern ADDITIONAL_PLACE_PREPOSITION_PATTERN = WordPattern.or(
			WordPattern.sequence(FRWordPattern.simplePlacePrep(PlaceType.CUSTOM), WordPattern.optional(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE))),
			FRWordPattern.simplePlacePrep(PlaceType.CUSTOM, true));
	private static final WordPattern NAMED_PLACE_PREPOSITION_PATTERN = WordPattern.or(
			WordPattern.sequence(FRWordPattern.simplePlacePrep(PlaceType.NAMED_PLACE), WordPattern.optional(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE))),
			FRWordPattern.simplePlacePrep(PlaceType.NAMED_PLACE, true));
	private static final WordPattern ADDRESS_PREPOSITION_PATTERN = WordPattern.or(
			WordPattern.sequence(FRWordPattern.simplePlacePrep(PlaceType.ADDRESS), WordPattern.optional(FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE))),
			FRWordPattern.simplePlacePrep(PlaceType.ADDRESS, true));
	private static final WordPattern PLACE_TYPE_PREPOSITION_PATTERN = WordPattern.or(
			WordPattern.sequence(FRWordPattern.simplePlacePrep(PlaceType.PLACE_TYPE), FRWordPattern.simpleWord(WordType.DEFINITE_ARTICLE)),
			FRWordPattern.simplePlacePrep(PlaceType.PLACE_TYPE, true));
	
	private static final WordPattern COUNTRY_PATTERN = WordPattern.sequence(
			COUNTRY_PREPOSITION_PATTERN, 
			FRWordPattern.simpleWord(WordType.COUNTRY));

	private static final WordPattern CITY_PATTERN = WordPattern.sequence(
			CITY_PREPOSITION_PATTERN, 
			FRWordPattern.simpleWord(WordType.CITY));			

	private static final WordPattern WORLD_LOCATION_PATTERN = WordPattern.sequence(CITY_PATTERN, COUNTRY_PATTERN);

	// devant l'Opera de Wasawakini
	// jusqu'aux Chutes Fanis
	private static final WordPattern ADDITIONAL_PLACE_PATTERN = WordPattern.sequence(
			ADDITIONAL_PLACE_PREPOSITION_PATTERN,
			FRWordPattern.simpleWord(WordType.ADDITIONAL_PLACE)
			);

	// le mus�e du Louvre, la Grande Muraille de Chine 
	private static final WordPattern NAMED_PLACE_PATTERN = WordPattern.sequence(
			NAMED_PLACE_PREPOSITION_PATTERN,
			WordPattern.or(
					FRWordPattern.simpleWord(WordType.PLACE_TYPE),
					WordPattern.nonEmptyList(FRWordPattern.simpleWord(WordType.PROPER_NAME)))
			// TODO: pouvoir parser les noms composes (ex: musee du Louvre)
					);
	
	private static final WordPattern ADDRESS_PATTERN = WordPattern.sequence(
			ADDRESS_PREPOSITION_PATTERN,
			STREET_ADDRESS_PATTERN);
	
	private static final WordPattern PLACE_TYPE_PATTERN = WordPattern.sequence(
			PLACE_TYPE_PREPOSITION_PATTERN,
			FRWordPattern.simpleWord(WordType.PLACE_TYPE));

	private static final WordPattern CITY_SECOND_OBJECT_PATTERN = WordPattern.sequence(
			FRWordPattern.simpleWord(WordType.PREPOSITION_OF), PlaceConverter.CITY_ONLY_PATTERN);

	private static final WordPattern PLACE_SECOND_OBJECT_PATTERN = WordPattern.or(
			CITY_SECOND_OBJECT_PATTERN);

	


	// A Paris en France
	// A Mexico au Mexique
	// A ... a la ...

	public static boolean isAPlaceAdverbial(FRWordBuffer words){
		return words.syntaxStartsWith(ADDITIONAL_PLACE_PATTERN)
				|| words.syntaxStartsWith(WORLD_LOCATION_PATTERN)
				|| words.syntaxStartsWith(CITY_PATTERN)
				|| words.syntaxStartsWith(COUNTRY_PATTERN)
				|| words.syntaxStartsWith(ADDRESS_PATTERN)
				|| words.syntaxStartsWith(NAMED_PLACE_PATTERN)
				|| words.syntaxStartsWith(PLACE_TYPE_PATTERN);
	}

	public static IPlaceObject parsePlaceAdverbial(FRWordBuffer words){
		IPlaceObject result = null;

		// TODO: gerer les locations pleines (Le musee du Louvre a Paris en France)
		if(words.syntaxStartsWith(ADDITIONAL_PLACE_PATTERN)){
			AdditionalPlace additional = new AdditionalPlace();

			if(words.getCurrentElement().isOfType(WordType.PLACE_PREPOSITION)){
				additional.setPlacePreposition(words.getCurrentElement().getPlacePreposition().getValue());
				words.consume();
			}

			parseAdditionalPlace(additional, words);
			result = additional;
		} else if(words.syntaxStartsWith(WORLD_LOCATION_PATTERN)){
			LocationObject state = new LocationObject();

			if(words.syntaxStartsWith(CITY_PATTERN)){
				while(!words.getCurrentElement().isOfType(WordType.CITY)){
					words.consume();					
				}

				state.city = words.getCurrentElement().getCityInfo();
				words.consume();
			}

			if(words.syntaxStartsWith(COUNTRY_PATTERN)){
				while(!words.getCurrentElement().isOfType(WordType.COUNTRY)){
					words.consume();
				}

				state.country = words.getCurrentElement().getCountryInfo();
				words.consume();
			}

			result = state;
		} else if(words.syntaxStartsWith(CITY_PATTERN)){
			CityObject city = new CityObject();

			if(words.getCurrentElement().isOfType(WordType.PLACE_PREPOSITION)){
				city.setPlacePreposition(words.getCurrentElement().getPlacePreposition().getValue());
				words.consume();
			}

			city.city = words.getCurrentElement().getCityInfo();
			words.consume();

			result = city;
		} else if(words.syntaxStartsWith(COUNTRY_PATTERN)){
			CountryObject country = new CountryObject();

			if(words.getCurrentElement().isOfType(WordType.PLACE_PREPOSITION)){
				country.setPlacePreposition(words.getCurrentElement().getPlacePreposition().getValue());
				words.consume();
			}

			country.country = words.getCurrentElement().getCountryInfo();
			words.consume();

			result = country;
		} else if(words.syntaxStartsWith(ADDRESS_PATTERN)){ 
			AddressObject address = new AddressObject();
			// TODO:
			result = address;
		} else if(words.syntaxStartsWith(NAMED_PLACE_PATTERN)){
			NamedPlaceObject place = new NamedPlaceObject();

			if(words.getCurrentElement().isOfType(WordType.PLACE_PREPOSITION)){
				place.setPlacePreposition(words.getCurrentElement().getPlacePreposition().getValue());
				words.consume();
			}

			parseNamedPlace(place, words);
			result = place;
		}

		return result;
	}

	public static boolean isANominalGroup(FRWordBuffer words){
		return words.syntaxStartsWith(ADDITIONAL_PLACE_ONLY_PATTERN)
				|| words.syntaxStartsWith(CITY_ONLY_PATTERN)
				|| words.syntaxStartsWith(COUNTRY_ONLY_PATTERN)
				|| words.syntaxStartsWith(PLACE_ONLY_PATTERN);
	}

	public static INominalObject parseNominalPlaceObject(FRWordBuffer words){
		INominalObject result = null;

		if(words.syntaxStartsWith(ADDITIONAL_PLACE_ONLY_PATTERN)){
			AdditionalPlace custom = new AdditionalPlace();
			parseAdditionalPlace(custom, words);
			result = custom;
		} else if(words.syntaxStartsWith(PLACE_ONLY_PATTERN)){
			NamedPlaceObject place = new NamedPlaceObject();			
			parseNamedPlace(place, words);
			result = place;
		}

		return result;
	}

	public static boolean isAPlaceSecondObject(FRWordBuffer words){
		return words.syntaxStartsWith(PLACE_SECOND_OBJECT_PATTERN);
	}

	public static IPlaceObject parsePlaceSecondObject(FRWordBuffer words){
		IPlaceObject result = null;

		if(words.syntaxStartsWith(CITY_SECOND_OBJECT_PATTERN)){
			words.consume();
			result = parseCityObject(words);
		}

		return result;
	}

	private static void parseAdditionalPlace(AdditionalPlace additional, FRWordBuffer words){
		if(words.getCurrentElement().isOfType(WordType.DEFINITE_ARTICLE)){
			words.consume();
		}		

		additional.place = words.getCurrentElement().getCustomPlace();
		words.consume();
	}

	private static CityObject parseCityObject(FRWordBuffer words){
		CityObject city = new CityObject();

		city.city = words.getCurrentElement().getCityInfo();
		words.consume();

		return city;
	}

	private static void parseNamedPlace(NamedPlaceObject place, FRWordBuffer words){
		StringBuilder buffer = new StringBuilder();

		if(words.getCurrentElement().isOfType(WordType.DEFINITE_ARTICLE)){
			words.consume();
		}

		if(words.getCurrentElement().isOfType(WordType.PLACE_TYPE)){
			buffer.append(words.getCurrentElement().getValue());
			buffer.append(' ');
			words.consume();
		}

		if(words.getCurrentElement().isOfType(WordType.PROPER_NAME)){
			do {
				buffer.append(words.getCurrentElement().getValue());
				buffer.append(' ');
				words.consume();
			} while(words.isIntoBounds() && words.getCurrentElement().isOfType(WordType.PROPER_NAME));
		}

		if(words.isIntoBounds() && words.getCurrentElement().isOfType(WordType.PREPOSITION_OF)){
			words.consume();	// PREPOSITION_OF				

			if(words.getCurrentElement().isOfType(WordType.CITY)){ 
				place.city = words.getCurrentElement().getValue();
				words.consume();
			} else if(words.getCurrentElement().isOfType(WordType.COUNTRY)){
				place.city = words.getCurrentElement().getValue();
				words.consume();
			} else {
				buffer.append("de ");

				do {
					buffer.append(words.getCurrentElement().getValue());
					buffer.append(' ');
					words.consume();
				} while(words.isIntoBounds() && words.getCurrentElement().isOfType(WordType.PROPER_NAME));
			}
		}

		place.name = buffer.toString().trim();
	}
}
