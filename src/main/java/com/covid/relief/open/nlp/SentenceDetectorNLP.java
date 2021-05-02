package com.covid.relief.open.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

public class SentenceDetectorNLP {

	public void extractLocationData(String tweet) {
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
		
		String[] tokens = tokenizer.tokenize(tweet);

		InputStream inputStreamNameFinder = getClass().getResourceAsStream("/models/en-ner-location.bin");
		TokenNameFinderModel model;
		try {
			model = new TokenNameFinderModel(inputStreamNameFinder);
			NameFinderME nameFinderME = new NameFinderME(model);
			List<Span> spans = Arrays.asList(nameFinderME.find(tokens));

			spans.forEach(s -> {
				
				System.out.println("Locations: " + s.toString() + " -> " + tokens[s.getStart()]);	
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
