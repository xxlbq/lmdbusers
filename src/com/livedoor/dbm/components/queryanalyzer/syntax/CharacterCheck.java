package com.livedoor.dbm.components.queryanalyzer.syntax;

public class CharacterCheck {
	CharacterKeyWords characterKeyWords;
	
	public CharacterCheck(CharacterKeyWords characterKeyWords) {
		this.characterKeyWords = characterKeyWords;
	}

	public boolean isTable(String data) {

		return characterKeyWords.TABLE_ARRAY.containsKey(data);
	}

	public boolean isColumn(String data) {

		return characterKeyWords.COLUMN_MAP.containsKey(data);
	}

	public boolean isDataType(String data) {

		return characterKeyWords.DATA_TYPE_MAP.containsKey(data);
	}

	public boolean isKeyword(String data) {

		return characterKeyWords.RESERVED_WORD_MAP.containsKey(data);
	}

}
