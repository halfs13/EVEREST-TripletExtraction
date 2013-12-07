package com.nextcentury.TripletExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmu.arktweetnlp.Tagger;
import cmu.arktweetnlp.Tagger.TaggedToken;

public class ArkParser {
	protected Tagger tagger;
	
	public ArkParser() {
		String modelFilename = "/cmu/arktweetnlp/model.20120919";
		tagger = new Tagger();
		try {
			tagger.loadModel(modelFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Tagger.TaggedToken> tagSentence(String sentence) {
		return tagger.tokenizeAndTag(sentence);
	}
	
	public void tagAndPrint(String sentence) {
		printTaggedList(tagSentence(sentence));
	}
	
	public List<StringTriplet> pullTuples(String sentenceString) {
		List<Tagger.TaggedToken> sentence = tagSentence(sentenceString);
		
		ArkParseObject result = new ArkParseObject();
		result.sentence = sentence;
		result.workingSentence = pilageSentence(sentence);
		
		result = pullFirst(result);
		
		return result.completeTriplets;
	}
	
	public List<Tagger.TaggedToken> pilageSentence(List<Tagger.TaggedToken> sentence) {
		String removeList = "P~";
		
		ArrayList<Tagger.TaggedToken> result = new ArrayList<Tagger.TaggedToken>();
		for(Tagger.TaggedToken token : sentence) {
			if(!removeList.contains(token.tag)) {
				result.add(token);
			}
		}
		
		return result;
	}
	
	public ArkParseObject pullFirst(ArkParseObject working) {
		/* cases 
		 * 
		 !* @+
		 !* D N
		 !* A N
		 !* D A N
		 * L
		 * ^+
		 * 
		 * how to handle @ N
		 */
		String acceptedList = "@NL^";
		
		int i = 0;
		Tagger.TaggedToken token;
		
		while(i < working.workingSentence.size()) {
			token = working.workingSentence.get(i);
			if(!acceptedList.contains(token.tag)) {
				i++;
			} else if(!token.tag.equals("L") && nounChainEndsWithLTag(working.workingSentence, i)) {
				i++;
			} else {
				if(token.tag.equals("L")) {
					if(token.token.equalsIgnoreCase("i'm")) {
						working.workingSentence.remove(i);
						
						Tagger.TaggedToken n = new Tagger.TaggedToken();
						n.tag = "N";
						n.token = "I";
						Tagger.TaggedToken v = new Tagger.TaggedToken();
						v.tag = "V";
						v.token = "am";
						
						working.workingSentence.add(i, v);
						working.workingSentence.add(i, n);
						token = n;
						
						working.addEntity1(token.token);
					} else if(token.token.equalsIgnoreCase("he's")) {
						
					}
					
					
				} else { 
					if(i != 0) {
						//check for valid cases pre
						if(token.tag.equals("N")) {
							working = resolveNPreviousTokens(working, i);
						}
					}
				
					working.addEntity1(token.token);
					
					//check for valid cases post
					if(token.tag.equals("@")) {
						working = resolveAtPostTokens(working, i);
					} else if(token.tag.equals("^")) {
						working = resolveProperPostTokens(working, i);
					}
				}
				working.workingSentence.removeAll(working.workingSentence.subList(0, i+1));
				for(Tagger.TaggedToken t : working.workingSentence) {
					System.out.print(t.token + " ");
				}
				System.out.print("\n");
				return working;
			}
		}
		
		return null;
	}
	
	private boolean nounChainEndsWithLTag(List<Tagger.TaggedToken> sentence, int i) {
		String acceptable = "@N^";
		
		if(acceptable.contains(sentence.get(i + 1).tag)) {
			return(nounChainEndsWithLTag(sentence, i + 1));
		} else if(sentence.get(i + 1).tag.equals("L")) {
			return true;
		} else {
			return false;
		}
	}
	
	private ArkParseObject resolveProperPostTokens(ArkParseObject working, int i) {
		//FIXME
		return working;
	}
	
	private ArkParseObject resolveNPreviousTokens(ArkParseObject working, int i) {
		if(working.workingSentence.get(i-1).tag.equals("D")) {
			working.prependEntity1(working.workingSentence.get(i - 1).token);
			//return result;
		} else if(working.workingSentence.get(i-1).tag.equals("A")) {
			working.prependEntity1(working.workingSentence.get(i - 1).token);
			resolveNPreviousTokens(working, i - 1);
		}
		return working;
	}
	
	private ArkParseObject resolveAtPostTokens(ArkParseObject working, int i) {
		if(working.workingSentence.get(i + 1).tag.equals("@")) {
			working.addEntity1(working.workingSentence.get(i + 1).token);
			resolveAtPostTokens(working, i + 1);
		}
		
		return working;
	}
	
	protected void printTaggedList(List<TaggedToken> taggedList) {
		for(int i = 0; i < taggedList.size(); i++) {
			System.out.print("[" + taggedList.get(i).tag + "]" + taggedList.get(i).token + " ");
		}
		System.out.print("\n");
	}
}
