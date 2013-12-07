package com.nextcentury.TripletExtraction;

import java.util.ArrayList;
import java.util.List;

import cmu.arktweetnlp.Tagger;

public class ArkParseObject {
	
	public static final int ENTITY1 = 0;
	public static final int RELATIONSHIP = 1;
	public static final int ENTITY2 = 2;
	
	public List<Tagger.TaggedToken> sentence;
	public List<Tagger.TaggedToken> workingSentence;
	public ArrayList<StringTriplet> completeTriplets;
	public ArrayList<StringTriplet> partialTriplets;
	
	public ArkParseObject() {
		completeTriplets = new ArrayList<StringTriplet>();
		partialTriplets = new ArrayList<StringTriplet>();
	}
	
	public void addEntity1(String entity) {
		StringTriplet triplet = new StringTriplet(entity);
		partialTriplets.add(triplet);
	}
	
	public void addRelationship(String relation) {
		
	}
	
	public void addEntity2(String entity) {
		//add entity and move to complete
	}
	
	public void prependEntity1(String value) {
		for(StringTriplet triplet : partialTriplets) {
			triplet.setEntity1(value + " " + triplet.getEntity1());
		}
	}
	
	public void appendEntity1(String value) {
		for(StringTriplet triplet : partialTriplets) {
			triplet.setEntity1(triplet.getEntity1() + " " + value);
		}
	}
}
