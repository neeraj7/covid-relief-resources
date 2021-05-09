package com.covid.relief.open.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.covid.relief.init.AppInitializer;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

@Component
public class SentenceDetectorNLP {

	/**
	 * Method will extract the location of cities from a text.
	 * 
	 * @param text
	 * @return list of cities found in a text or null.
	 */
	public Set<String> extractLocationData(String text) {
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
		// Tokenize the tweet
		String[] tokens = tokenizer.tokenize(text);
		Set<String> locations = new HashSet<>();

		// Load the data model of locations
		InputStream inputStreamNameFinder = getClass().getResourceAsStream("/models/en-ner-location.bin");
		try {
			locations = Arrays.asList(new NameFinderME(new TokenNameFinderModel(inputStreamNameFinder)).find(tokens))
					.stream().map(s -> tokens[s.getStart()]).map(location -> location.toLowerCase())
					.filter(location -> AppInitializer.getCitiesMapping().containsKey(location))
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locations;
	}

	/**
	 * Method will extract the list of resources from a text.
	 * 
	 * @param text
	 * @return list of cities found in a text or null.
	 */
	public Set<String> extractResources(String text) {
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
		// Tokenize the tweet
		String[] tokens = tokenizer.tokenize(text);
		Set<String> locations = new HashSet<>();

		// Load the data model of locations
		InputStream inputStreamNameFinder = getClass().getResourceAsStream("/models/en-ner-person.bin");
		try {

			List<Span> asList = Arrays
					.asList(new NameFinderME(new TokenNameFinderModel(inputStreamNameFinder)).find(tokens));
			
			asList.stream().forEach(a -> System.out.println("Resources: " + tokens[a.getStart()]));
			
			locations = asList.stream().map(s -> tokens[s.getStart()]).map(location -> location.toLowerCase())
					.filter(location -> AppInitializer.getResourcesList().stream()
							.filter(qh -> qh.getResource().contains(location.toLowerCase())).findAny().isPresent())
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locations;
	}

}