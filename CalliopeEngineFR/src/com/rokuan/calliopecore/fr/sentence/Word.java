package com.rokuan.calliopecore.fr.sentence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.CharacterInfo;
import com.rokuan.calliopecore.sentence.CityInfo;
import com.rokuan.calliopecore.sentence.ColorInfo;
import com.rokuan.calliopecore.sentence.CountryInfo;
import com.rokuan.calliopecore.sentence.CustomMode;
import com.rokuan.calliopecore.sentence.CustomObject;
import com.rokuan.calliopecore.sentence.CustomPerson;
import com.rokuan.calliopecore.sentence.CustomPlace;
import com.rokuan.calliopecore.sentence.IPlacePreposition;
import com.rokuan.calliopecore.sentence.IPurposePreposition;
import com.rokuan.calliopecore.sentence.ITimePreposition;
import com.rokuan.calliopecore.sentence.IVerbConjugation;
import com.rokuan.calliopecore.sentence.IWayPreposition;
import com.rokuan.calliopecore.sentence.IWord;
import com.rokuan.calliopecore.sentence.LanguageInfo;
import com.rokuan.calliopecore.sentence.NameInfo;
import com.rokuan.calliopecore.sentence.PlaceInfo;
import com.rokuan.calliopecore.sentence.TransportInfo;
import com.rokuan.calliopecore.sentence.UnitInfo;

@DatabaseTable(tableName = "words")
public class Word implements IWord {
	public enum WordType {
		PROPER_NAME,
		COMMON_NAME,
		ADVERB,
		VERB,
		ADJECTIVE,
		ONOMATOPEIA,
		PREPOSITION,
		CONJUNCTION,
		AUXILIARY,
		DEMONSTRATIVE_PRONOUN,
		DEFINITE_ARTICLE,
		INDEFINITE_ADJECTIVE,
		INDEFINITE_PRONOUN,
		INTERROGATIVE_PRONOUN,
		RELATIVE_PRONOUN,
		NUMERICAL_ADJECTIVE,
		NUMBER,
		REAL,
		NUMERICAL_POSITION,
		DEMONSTRATIVE_ADJECTIVE,
		PERSONAL_PRONOUN,
		INDEFINITE_ARTICLE,
		POSSESSIVE_PRONOUN,
		POSSESSIVE_ADJECTIVE,
		EUPHONIOUS_LINK,
		INTERROGATIVE_ADJECTIVE,
		PREPOSITION_FROM,
		PREPOSITION_TO,
		DATE_MONTH,
		DATE,
		DATE_UNIT,
		TIME,
		// New
		QUANTITY,
		PREPOSITION_BETWEEN,	// entre
		PREPOSITION_AND,	// et
		PREPOSITION_AT,	// �/au
		PREPOSITION_OF,	// de
		PREPOSITION_IN,	// en
		PREPOSITION_WITH,	// avec
		CONJUGATION_LINK,	// t
		SUPERLATIVE,	// moins/plus
		TARGET_PRONOUN,	// moi/toi/me/te/...
		DATE_UNIT_HOUR,	// midi/minuit
		MEAN_OF_TRANSPORT, // pied/voiture/bus/avion/...
		PLACE_TYPE,	// restaurant/cin�ma/...
		STREET_TYPE,	//rue/avenue/boulevard/...
		PERSON_TYPE,	// voisin/oncle/m�decin

		CITY,
		COUNTRY,
		FIRSTNAME,
		LANGUAGE,
		UNIT,
		PERSON,
		PLACE,
		MODE,
		COLOR,
		OBJECT,
		ADDITIONAL_PLACE,
		ADDRESS,

		CONTRACTED,

		TIME_PREPOSITION,
		PLACE_PREPOSITION,
		WAY_PREPOSITION,
		PURPOSE_PREPOSITION,
		
		OTHER
	}
	
	public static final String WORD_FIELD_NAME = "value";
	public static final String TYPES_FIELD_NAME = "types";

	@DatabaseField(columnName = WORD_FIELD_NAME, id = true)
	private String value;
	@DatabaseField(columnName = TYPES_FIELD_NAME, dataType = DataType.SERIALIZABLE)
	private HashSet<WordType> types = new HashSet<WordType>();
	private IVerbConjugation verbInfo;
	private NameInfo nameInfo;
	private LanguageInfo langInfo;
	private ColorInfo colorInfo;
	private CountryInfo countryInfo;
	private CityInfo cityInfo;
	private TransportInfo transportInfo;
	private UnitInfo unitInfo;
	private CharacterInfo characterInfo;
	private PlaceInfo placeInfo;
	private TimePreposition timePreposition;
	private PlacePreposition placePreposition;
	private WayPreposition wayPreposition;
	private PurposePreposition purposePreposition;
	private CustomObject customObject;
	private CustomPlace customPlace;
	private CustomPerson customPerson;
	private CustomMode customMode;
	
	public Word(){
		
	}
	
	private Word(String v){
		value = v;
	}

	public Word(String v, WordType t){
		this(v);
		types.add(t);
	}

	public Word(String v, List<WordType> ts){
		this(v);
		
		if(ts != null){
			types.addAll(ts);
		}
	}
	
	public Word(String v, Set<WordType> ts){
		this(v);
		
		if(ts != null){
			types.addAll(ts);
		}
	}

	public Word(String v, WordType... ts){
		this(v);
		
		for(WordType t: ts){
			types.add(t);
		}
	}

	public boolean isOfType(WordType type){
		return types.contains(type);
	}
	
	public void addType(WordType t){
		types.add(t);
	}

	public Set<WordType> getTypes(){
		return types;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public IVerbConjugation getVerbInfo() {
		return verbInfo;
	}

	public void setVerbInfo(VerbConjugation verbInfo) {
		this.verbInfo = verbInfo;
	}
	
	@Override
	public NameInfo getNameInfo(){
		return nameInfo;
	}
	
	public void setNameInfo(NameInfo nameInfo){
		this.nameInfo = nameInfo;
	}

	@Override
	public LanguageInfo getLanguageInfo() {
		return langInfo;
	}

	public void setLanguageInfo(LanguageInfo langInfo) {
		this.langInfo = langInfo;
	}

	@Override
	public ColorInfo getColorInfo() {
		return colorInfo;
	}

	public void setColorInfo(ColorInfo colorInfo) {
		this.colorInfo = colorInfo;
	}

	@Override
	public CountryInfo getCountryInfo() {
		return countryInfo;
	}

	public void setCountryInfo(CountryInfo countryInfo) {
		this.countryInfo = countryInfo;
	}

	@Override
	public CityInfo getCityInfo() {
		return cityInfo;
	}

	public void setCityInfo(CityInfo cityInfo) {
		this.cityInfo = cityInfo;
	}

	@Override
	public TransportInfo getTransportInfo() {
		return transportInfo;
	}

	public void setTransportInfo(TransportInfo transportInfo) {
		this.transportInfo = transportInfo;
	}

	@Override
	public UnitInfo getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(UnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}

	@Override
	public CharacterInfo getCharacterInfo() {
		return characterInfo;
	}

	public void setCharacterInfo(CharacterInfo characterInfo) {
		this.characterInfo = characterInfo;
	}

	@Override
	public PlaceInfo getPlaceInfo() {
		return placeInfo;
	}

	public void setPlaceInfo(PlaceInfo placeInfo) {
		this.placeInfo = placeInfo;
	}

	@Override
	public ITimePreposition getTimePreposition() {
		return timePreposition;
	}

	public void setTimePreposition(TimePreposition timePreposition) {
		this.timePreposition = timePreposition;
	}

	@Override
	public IPlacePreposition getPlacePreposition() {
		return placePreposition;
	}

	public void setPlacePreposition(PlacePreposition placePreposition) {
		this.placePreposition = placePreposition;
	}

	@Override
	public IWayPreposition getWayPreposition() {
		return wayPreposition;
	}

	public void setWayPreposition(WayPreposition wayPreposition) {
		this.wayPreposition = wayPreposition;
	}

	@Override
	public IPurposePreposition getPurposePreposition() {
		return purposePreposition;
	}

	public void setPurposePreposition(PurposePreposition purposePreposition) {
		this.purposePreposition = purposePreposition;
	}

	@Override
	public CustomObject getCustomObject() {
		return customObject;
	}

	public void setCustomObject(CustomObject customObject) {
		this.customObject = customObject;
	}

	@Override
	public CustomPlace getCustomPlace() {
		return customPlace;
	}

	public void setCustomPlace(CustomPlace customPlace) {
		this.customPlace = customPlace;
	}

	@Override
	public CustomPerson getCustomPerson() {
		return customPerson;
	}

	public void setCustomPerson(CustomPerson customPerson) {
		this.customPerson = customPerson;
	}

	@Override
	public CustomMode getCustomMode() {
		return customMode;
	}

	public void setCustomMode(CustomMode customMode) {
		this.customMode = customMode;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder(value);
		
		builder.append('(');
		
		for(WordType ty: types){
			builder.append(ty);
			builder.append(',');
		}
		
		builder.deleteCharAt(builder.length() - 1);
		builder.append(')');
		
		return builder.toString();
	}
}
