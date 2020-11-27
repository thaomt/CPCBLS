package tuan6;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;



public class aprio{

	//HashMap, Die alle Sätze (als Hashmap) beinhaltet, die aus deren Wörtern bestehen
	static HashMap<Integer, HashMap<Integer, String>> allLinesMap = new HashMap<Integer, HashMap<Integer, String>>();

	//Kombinationen, die alle derzeit erlaubten Kombinationen enthält.
	//Nach z.B. der 3. Iteration enthält es ausschließlich Sets mit 3 Wörtern.
	static HashMap<Integer, Set<String>> currentAllowedCombination = new HashMap<Integer, Set<String>>();
	static int currentAllowedCombinationKey=0;

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		int minThreshold = 3000;
		String wordSeparator = ";";
		String lineSeparator = "\n";

		String fileContent="";
		try {
			fileContent = readFile(System.getProperty("user.dir")+"\\src\\tuan6\\adult.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] lines = fileContent.split(lineSeparator);
		String[] words = fileContent.replace(lineSeparator, wordSeparator).split(wordSeparator);

		//wordSet, in dem jedes Wort 1x vorkommt (kann schneller durchiteriert werden; bei adult.txt nur 100 Einträge)
		Set<String> wordSet = new HashSet<String>();
		for(String word : words){
			wordSet.add(word);
		}

		//HashMap, die Hashmaps mit Wörtern einer Zeile enthält
		//Wird später durchiteriert, um festzustellen, wie oft ein Wort in den Zeilen vorkommt.
		int iterator0=0;
		for (String line : lines){
			String[] lineWords = line.split(wordSeparator);
			//Map mit Satz erstellen
			int tempIterator0=0;
			HashMap<Integer, String> sentenceWithWordMap = new HashMap<Integer, String>();
			for(String lineWord : lineWords){
				sentenceWithWordMap.put(tempIterator0, lineWord);
				tempIterator0++;
			}
			//Satz zu allLinesMap hinzufügen
			allLinesMap.put(iterator0, sentenceWithWordMap);
			iterator0++;
		}

		//Set, das die Wort-Kombinationen enthält
		HashMap<Integer, Set<String>> wordMap = new HashMap<Integer, Set<String>>();

		boolean hasReturnedResults=true;
		int sizeOfWordCombinations=1; //1=Ein Wort, 2=Zwei Wörter, etc.

		//Output für Part 1 (“oneItems.txt”)
		String outputForOneItemsTxt="";
		String outputForPatternsTxt="";

		//Alle Iterationen abarbeiten, bis es in einer Iteration keine Resultate mehr gibt
		while(hasReturnedResults){
			long startTime2 = System.currentTimeMillis();
			outputForPatternsTxt += ("Iteration " + sizeOfWordCombinations + ":\n");
			System.out.print("Iteration " + sizeOfWordCombinations + ":\n");

			hasReturnedResults=false;

			wordMap = fillMap(sizeOfWordCombinations, wordSet);
			outputForPatternsTxt += ("Possible word combinations for this iteration: " + wordMap.size() + "\n");
			System.out.print("Possible word combinations for this iteration: " + wordMap.size() + "\n");

			currentAllowedCombination.clear();
			currentAllowedCombinationKey=0;

			if(sizeOfWordCombinations==1)
				wordSet.clear();

			for(Set<String> wordCombination : wordMap.values()){

				//Zähle, in wie vielen Zeilen die Kombination vorkommt
				int occurences = getNumOfOccurences(wordCombination);

				//Alle, die über Threshold liegen, werden eingetragen
				if(occurences > minThreshold){

					//Zu text file hinzufügen (vorerst nur zum String dafür)
					if(sizeOfWordCombinations==1){
						for(String singleWord : wordCombination)
							wordSet.add(singleWord);
						outputForOneItemsTxt += (occurences + ":" + wordCombination+"\n");
					}
					outputForPatternsTxt += (occurences + ":" + wordCombination+"\n");
					System.out.print(occurences + ":" + wordCombination+"\n");

					//Kombination hinzufügen
					hasReturnedResults=true;
					currentAllowedCombination.put(currentAllowedCombinationKey, wordCombination);
					currentAllowedCombinationKey++;
				}
			}

			long estimatedTime2 = System.currentTimeMillis() - startTime2;
			outputForPatternsTxt += (" -> Iteration " + sizeOfWordCombinations + " took " + estimatedTime2/1000 + "." + estimatedTime2%1000 + " seconds to complete and yielded " + currentAllowedCombination.size() + " combinations as a result.\n\n");
			System.out.print(" -> Iteration " + sizeOfWordCombinations + " took " + estimatedTime2/1000 + "." + estimatedTime2%1000 + " seconds to complete and yielded " + currentAllowedCombination.size() + " combinations as a result.\n\n");

			sizeOfWordCombinations++;
		}

		long estimatedTime = System.currentTimeMillis() - startTime;
		outputForPatternsTxt += ("\nThe Apriori Algorithm finished after " + estimatedTime/1000 + "." + estimatedTime%1000 + " seconds!\n");
		System.out.print("\nThe Apriori Algorithm finished after " + estimatedTime/1000 + "." + estimatedTime%1000 + " seconds!\n");

		printTxt("oneItems", outputForOneItemsTxt);
		printTxt("patterns", outputForPatternsTxt);
	}

	//Alle möglichen Kombinationen für eine Iteration erstellen
	private static HashMap<Integer, Set<String>> fillMap(int iteration, Set<String> words){
		int iterator=0;
		HashMap<Integer, Set<String>> returnMap = new HashMap<Integer, Set<String>>();

		if(iteration==1){
			for(String word : words){
				Set<String> currentSet = new HashSet<String>();
				currentSet.add(word);

				returnMap.put(iterator, currentSet);
				currentAllowedCombination.put(iterator, currentSet);
				iterator++;
			}
		}
		else
			for(Set<String> wordCombo : currentAllowedCombination.values()){
				for(String word : words){
					Set<String> currentSet = new HashSet<String>();
					currentSet.addAll(wordCombo);
					currentSet.add(word);

					if(currentSet.size() == iteration && !returnMap.containsValue(currentSet)){
						returnMap.put(iterator, currentSet);
						iterator++;
					}
				}
			}
		return returnMap;
	}

	private static String readFile(String path) throws IOException {
		File file = new File(path);
		StringBuilder contents = new StringBuilder((int)file.length());
		Scanner sc = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");
		try {
			while(sc.hasNextLine()) {
				contents.append(sc.nextLine() + lineSeparator);
			}
			return contents.toString();
		} finally {
			sc.close();
		}
	}

	//Retourniert den Key zu einem passenden Value in einer HashMap
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	//Überprüfe, wie oft ein oder mehrere Wörter in adult.txt vorkommen
	private static int getNumOfOccurences(Set<String> wordsToCheck){
		int occurences=0;
		boolean occuredInSentence = false;
		//Zeilen (von adult.txt) durchiterieren
		for(HashMap<Integer, String> oneLineMap : allLinesMap.values()){
			//Check, ob gesuchte Wörter vorkommen
			for(String wordToCheck : wordsToCheck){
				if(oneLineMap.containsValue(wordToCheck)){
					occuredInSentence=true;
				}
				else{
					//mind ein Wort kommt nicht vor -> diesen Satz nicht weiter überprüfen
					occuredInSentence=false;
					break;
				}

			}
			if(occuredInSentence){
				occuredInSentence=false;
				occurences++;
			}
		}

		//Zurückgeben, wie oft das Wort/die Wörter in adult.txt vorkommt.
		return occurences;
	}

	//Speichere txt file
	private static void printTxt(String filename, String content){
		filename+=".txt";
		content=content.replace("\n]", "]");
		try(PrintWriter pr = new PrintWriter(filename)){
			pr.println(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}