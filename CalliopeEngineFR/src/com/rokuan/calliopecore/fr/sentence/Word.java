package com.rokuan.calliopecore.fr.sentence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.rokuan.calliopecore.sentence.IAction;
import com.rokuan.calliopecore.sentence.IAdjectiveInfo;
import com.rokuan.calliopecore.sentence.ICharacterInfo;
import com.rokuan.calliopecore.sentence.ICityInfo;
import com.rokuan.calliopecore.sentence.IColorInfo;
import com.rokuan.calliopecore.sentence.ICountryInfo;
import com.rokuan.calliopecore.sentence.ICustomMode;
import com.rokuan.calliopecore.sentence.ICustomObject;
import com.rokuan.calliopecore.sentence.ICustomPerson;
import com.rokuan.calliopecore.sentence.ICustomPlace;
import com.rokuan.calliopecore.sentence.ILanguageInfo;
import com.rokuan.calliopecore.sentence.INameInfo;
import com.rokuan.calliopecore.sentence.IPlaceInfo;
import com.rokuan.calliopecore.sentence.IPlacePreposition;
import com.rokuan.calliopecore.sentence.IPurposePreposition;
import com.rokuan.calliopecore.sentence.ITimePreposition;
import com.rokuan.calliopecore.sentence.ITransportInfo;
import com.rokuan.calliopecore.sentence.IUnitInfo;
import com.rokuan.calliopecore.sentence.IWayPreposition;
import com.rokuan.calliopecore.sentence.IWord;
import com.rokuan.calliopecore.sentence.structure.common.IVerbalContent;

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
	//private IAction verbInfo;
	private VerbConjugation verbInfo;
	private NameInfo nameInfo;
	private AdjectiveInfo adjectiveInfo;
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

	public IAction getVerbInfo() {
		return verbInfo;
	}

	public void setVerbInfo(VerbConjugation verbInfo) {
		this.verbInfo = verbInfo;
	}
	
	@Override
	public INameInfo getNameInfo(){
		return nameInfo;
	}
	
	public void setNameInfo(NameInfo nameInfo){
		this.nameInfo = nameInfo;
	}
	
	@Override
	public IAdjectiveInfo getAdjectiveInfo(){
		return adjectiveInfo;
	}
	
	public void setAdjectiveInfo(AdjectiveInfo adjectiveInfo){
		this.adjectiveInfo = adjectiveInfo;
	}

	@Override
	public ILanguageInfo getLanguageInfo() {
		return langInfo;
	}

	public void setLanguageInfo(LanguageInfo langInfo) {
		this.langInfo = langInfo;
	}

	@Override
	public IColorInfo getColorInfo() {
		return colorInfo;
	}

	public void setColorInfo(ColorInfo colorInfo) {
		this.colorInfo = colorInfo;
	}

	@Override
	public ICountryInfo getCountryInfo() {
		return countryInfo;
	}

	public void setCountryInfo(CountryInfo countryInfo) {
		this.countryInfo = countryInfo;
	}

	@Override
	public ICityInfo getCityInfo() {
		return cityInfo;
	}

	public void setCityInfo(CityInfo cityInfo) {
		this.cityInfo = cityInfo;
	}

	@Override
	public ITransportInfo getTransportInfo() {
		return transportInfo;
	}

	public void setTransportInfo(TransportInfo transportInfo) {
		this.transportInfo = transportInfo;
	}

	@Override
	public IUnitInfo getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(UnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}

	@Override
	public ICharacterInfo getCharacterInfo() {
		return characterInfo;
	}

	public void setCharacterInfo(CharacterInfo characterInfo) {
		this.characterInfo = characterInfo;
	}

	@Override
	public IPlaceInfo getPlaceInfo() {
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
	public ICustomObject getCustomObject() {
		return customObject;
	}

	public void setCustomObject(CustomObject customObject) {
		this.customObject = customObject;
	}

	@Override
	public ICustomPlace getCustomPlace() {
		return customPlace;
	}

	public void setCustomPlace(CustomPlace customPlace) {
		this.customPlace = customPlace;
	}

	@Override
	public ICustomPerson getCustomPerson() {
		return customPerson;
	}

	public void setCustomPerson(CustomPerson customPerson) {
		this.customPerson = customPerson;
	}

	@Override
	public ICustomMode getCustomMode() {
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
