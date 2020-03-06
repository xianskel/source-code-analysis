import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Represents a collection users email addresses and their associated word counts.
 * 
 * @author Cian Skelly 0559377
 */

public class emailList{
	
    ////////////////////////////////////////
    // Instance Variables
    ////////////////////////////////////////

	private Map<String, WordCount> users;// New map to store email addresses and associated word counts
	
    ////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////
	
	/**
	 * Constructs new emailList
	 */
	public emailList(){
		users = new TreeMap<String, WordCount>(String.CASE_INSENSITIVE_ORDER);
	}
	
    ////////////////////////////////////////
    // Public Methods
    ////////////////////////////////////////

	/**
	 * Gets emailList
	 * 
	 * @return the emailList 
	 */
	public Map<String, WordCount> getEmails() {
		return users;
	}
	
	/**
	 * Adds a new message to the emailList
	 * 
	 * @param email is the email of user
	 * @param message is the message to be added
	 */
	public void addMessage(String email, String message){
		WordCount wordList;
		if(users.containsKey(email)){
			wordList = users.get(email);
		}
		else{
			wordList = new WordCount();
		}
		wordList.addWord(message);
		users.put(email, wordList);
	}
	
	/**
	 * Returns an emailList in String format. 
	 * Shows email address followed by top 10 word counts.
	 * Prints each email on a new line.
	 */
	public String toString(){
	    Set<Entry<String, WordCount>> tempSet = users.entrySet();
	    Iterator<Entry<String, WordCount>> it = tempSet.iterator();
	    String result = "";
	    
	    while(it.hasNext()) {
	        Map.Entry<String, WordCount> list = (Map.Entry<String, WordCount>)it.next();
	        result += list.getKey().substring(0, list.getKey().indexOf('@')) + " -> " + list.getValue() + "\n";
	      }
	   
		return result;
	}
	
	
}
