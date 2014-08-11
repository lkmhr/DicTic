package com.lkmhr.dictic.utils;

public class Word {
	private String word;
	private String partOfSpeech;
	private String definition;
	
	public Word() {
	}
	
	public Word(String word, String pos, String def) {
		this.word = word;
		this.partOfSpeech = pos;
		this.definition = def;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getPartOfSpeech() {
		return partOfSpeech;
	}
	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
}
