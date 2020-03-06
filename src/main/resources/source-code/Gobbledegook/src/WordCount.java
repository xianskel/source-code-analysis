import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

/**
 * Represents a collection of words and their count frequency.
 * 
 * @author Cian Skelly 0559377
 */

public class WordCount {
	
    ////////////////////////////////////////
    // Instance Variables
    ////////////////////////////////////////
	
	private Map<String, Integer> words;  // New map to store word and frequency count
	
    ////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////
	
	/**
	 * Constructs a new instance of wordCount
	 */
	public WordCount (){
		words = new HashMap<String, Integer>();
	}
	
    ////////////////////////////////////////
    // Public Methods
    ////////////////////////////////////////

	/**
	 * Method returns a word list with words(Key) and count(Value)
	 * 
	 * @return the wordList
	 */
	public Map<String, Integer> getWords() {
		return words;
	}
	
	/**
	 * Adds a word to the wordList. 
	 * If word already exists it increments the word count by one.
	 * 
	 * @param word is the word to be added
	 */
	public void addWord(String word){
		if(words.containsKey(word)){
			words.put(word, words.get(word)+1);
		}
		else{
			words.put(word, 1);
		}
	}	 

	/**
	 * Returns the wordList in String format. 
	 * Sets ordering of list to show words with highest count first.
	 * Secondary ordering by word alphabetically.
	 * Only returns 10 most frequent words. 
	 */
    public String toString(){
	  
        List<Map.Entry<String, Integer>> wordList = new ArrayList<Map.Entry<String ,Integer>>(words.entrySet());
     
        Collections.sort(wordList, new Comparator<Map.Entry<String ,Integer>>() {
                public int compare(Entry<String, Integer> first, Entry<String, Integer> second) {
                int diff = second.getValue()-(first.getValue());
                if(diff==0){
                	return first.getKey().compareToIgnoreCase(second.getKey());
                }
                return diff;
                }
         });
       
        int length = Math.min(10, wordList.size());
    	String result = "";
    	
        for(int i=0; i<length; i++){
    		result += "{" + wordList.get(i).getKey() + "," + wordList.get(i).getValue() + "}";
    		if(i<length-1){
    			result += ", ";
    		}
    	}
		return result;
    }
}