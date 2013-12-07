package com.nextcentury.TripletExtraction;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import cmu.arktweetnlp.Tagger;
import cmu.arktweetnlp.Tagger.TaggedToken;
import cmu.arktweetnlp.Twokenize;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
//import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class ArkTestDriver {

	public static String twitterTestString1 = "Is it bad that I'm really jealous of my brother because he's going to the concert tonight.";
	public static String twitterTestString2 = "@AledSMorris dove kills the doves. Try bleach!";
	public static String twitterTestString3 = "@realwilliamlees @DMolyneux1 I'm not mate I'm afraid. It will be the Derby game now for me";
	public static String twitterTestString4 = "Google Plans To Rival The Apple TV - Fast Company http://t.co/OeMvnB80YK";
	public static String twitterTestString5 = "RT @DavidRoads: Why compare yourself with others? No one in the entire world can do a better job of being you than you.";
	public static String testString1 = "Now is the time for all good men to come to the aid of their country";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Define test strings */
		
		//testTokenize();
		
		//testTokenizeAndTag();
		
		//compareTokenizations();
		
		testPilage();
	}
	
	private static void testTokenize() {
		
		List<String> tokenizedList1 = Twokenize.tokenizeRawTweetText(twitterTestString1);
		System.out.println(tokenizedList1);
		
		List<String> tokenizedList2 = Twokenize.tokenizeRawTweetText(twitterTestString2);
		System.out.println(tokenizedList2);
		
		List<String> tokenizedList3 = Twokenize.tokenizeRawTweetText(twitterTestString3);
		System.out.println(tokenizedList3);
		
		List<String> tokenizedList4 = Twokenize.tokenizeRawTweetText(twitterTestString4);
		System.out.println(tokenizedList4);
		
		List<String> tokenizedList5 = Twokenize.tokenizeRawTweetText(twitterTestString5);
		System.out.println(tokenizedList5);
		
		System.out.println("\n");
	}
	
	private static void testTokenizeAndTag() {
		ArkParser arkparser = new ArkParser();
		
		arkparser.tagAndPrint(twitterTestString1);
		arkparser.tagAndPrint(twitterTestString2);
		arkparser.tagAndPrint(twitterTestString3);
		arkparser.tagAndPrint(twitterTestString4);
		arkparser.tagAndPrint(twitterTestString5);
		System.out.println("\n");
	}
	
	private static void compareTokenizations() {
		//Stanford setup
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		//Ark setup
		ArkParser arkparser = new ArkParser();
		
		System.out.println("1:");
		Annotation document1 = new Annotation(twitterTestString1);
		pipeline.annotate(document1);
		List<CoreMap> sentences1 = document1.get(SentencesAnnotation.class);
		for (CoreLabel token: sentences1.get(0).get(TokensAnnotation.class)) {
			System.out.print("["+token.get(PartOfSpeechAnnotation.class)+ "]");
			System.out.print(token.toString() + " ");
		}
		System.out.print("\n");
		arkparser.tagAndPrint(twitterTestString1);
		
		System.out.println("2:");
		Annotation document2 = new Annotation(twitterTestString2);
		pipeline.annotate(document2);
		List<CoreMap> sentences2 = document2.get(SentencesAnnotation.class);
		for (CoreLabel token: sentences2.get(0).get(TokensAnnotation.class)) {
			System.out.print("["+token.get(PartOfSpeechAnnotation.class)+ "]");
			System.out.print(token.toString() + " ");
		}
		System.out.print("\n");
		arkparser.tagAndPrint(twitterTestString2);
		
		System.out.println("3:");
		Annotation document3 = new Annotation(twitterTestString3);
		pipeline.annotate(document3);
		List<CoreMap> sentences3 = document3.get(SentencesAnnotation.class);
		for (CoreLabel token: sentences3.get(0).get(TokensAnnotation.class)) {
			System.out.print("["+token.get(PartOfSpeechAnnotation.class)+ "]");
			System.out.print(token.toString() + " ");
		}
		System.out.print("\n");
		arkparser.tagAndPrint(twitterTestString3);
		
		System.out.println("4:");
		Annotation document4 = new Annotation(twitterTestString4);
		pipeline.annotate(document4);
		List<CoreMap> sentences4 = document4.get(SentencesAnnotation.class);
		for (CoreLabel token: sentences4.get(0).get(TokensAnnotation.class)) {
			System.out.print("["+token.get(PartOfSpeechAnnotation.class)+ "]");
			System.out.print(token.toString() + " ");
		}
		System.out.print("\n");
		arkparser.tagAndPrint(twitterTestString4);
		
		System.out.println("5:");
		Annotation document5 = new Annotation(twitterTestString5);
		pipeline.annotate(document5);
		List<CoreMap> sentences5 = document5.get(SentencesAnnotation.class);
		for (CoreLabel token: sentences5.get(0).get(TokensAnnotation.class)) {
			System.out.print("["+token.get(PartOfSpeechAnnotation.class)+ "]");
			System.out.print(token.toString() + " ");
		}
		System.out.print("\n");
		arkparser.tagAndPrint(twitterTestString5);
	}
	
	public static void testPilage() {
		ArkParser arkparser = new ArkParser();
		
		System.out.println(arkparser.pullTuples(twitterTestString1));
		System.out.println("\n");
		
		System.out.println(arkparser.pullTuples(twitterTestString2));
		System.out.println("\n");
		
		System.out.println(arkparser.pullTuples(twitterTestString3));
		System.out.println("\n");
		
		System.out.println(arkparser.pullTuples(twitterTestString4));
		System.out.println("\n");
		
		System.out.println(arkparser.pullTuples(twitterTestString5));
		System.out.println("\n");
		
		System.out.println(arkparser.pullTuples(testString1));
		System.out.println("\n");	
	}
}
