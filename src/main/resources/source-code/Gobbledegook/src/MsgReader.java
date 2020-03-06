import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;

/**
 * Represents a message reader which reads messages from a text file.
 * Separates messages from each other and adds proper words to appropriate wordCount in emailList.
 * 
 * @author Cian Skelly 0559377
 */
public class MsgReader {
	
    ////////////////////////////////////////
    // Instance Variables
    ////////////////////////////////////////
	
	public String gPostMessages;    // File path of Message file
	private String noiseWords;       // File path of Noise Words file
	
    ////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////

	/**
	 * Constructs a new instance of msgReader
	 * 
	 * @param gPostMessages is file path of Messages file
	 * @param noiseWords is file path of noise words file
	 */
	public MsgReader(String gPostMessages, String noiseWords) {
		this.gPostMessages=gPostMessages;
		this.noiseWords=noiseWords;
		GetNoise();
		analyseMessages();
	}
	
    ////////////////////////////////////////
    // Private Methods
    ////////////////////////////////////////
	
	/**
	 * Fetches all noise words from NoiseWord file and places them in an ArrayList.
	 * Converts all words to lower case
	 * 
	 * @return ArrayList of noise words.
	 * @throws IOException
	 */
	private List<String> GetNoise(){
		BufferedReader textReader = null;
		List<String> noiseList = null;
		String line;
		try{
			textReader = new BufferedReader(new FileReader(noiseWords));
			noiseList = new ArrayList<String>();
			line = textReader.readLine();
			while(line != null){
				noiseList.addAll(Arrays.asList(line.toLowerCase().split(" ")));
				line = textReader.readLine();
			}
         }
        catch (IOException e){
        	System.out.println("An IOException was caught :"+e.getMessage());
         }
        finally{
        	try{
            	textReader.close();
        	}
        	catch(IOException e){
        		e.printStackTrace();
        	}
         }
		return noiseList;
	}
	
	/**
	 * Fetches messages from the message file and returns them as an Array of individual messages.
	 * 
	 * @return Array of Messages
	 * @throws IOException
	 */
	private String[] getMessages(){
		BufferedReader textReader = null;
		String messages = "";
		String line;
		try{
			textReader = new BufferedReader(new FileReader(gPostMessages));
			line = textReader.readLine();
			while(line != null){
				messages += line;
				line = textReader.readLine();
			}
         }
        catch (IOException e){
        	System.out.println("An IOException was caught :"+e.getMessage());
         }
        finally{
        	try{
            	textReader.close();
        	}
        	catch(IOException e){
        		e.printStackTrace();
        	}
         }
		return messages.split("gPostEnd");
	}
	
    ////////////////////////////////////////
    // Public Methods
    ////////////////////////////////////////
	
	/**
	 * Analyses individual messages.
	 * Splits each message into address and message body 
	 * Checks if words only contain letters and dont exist in NoiseWords file.
	 * Adds proper words to users wordCount.
	 * 
	 * @throws IOException
	 */
	public void analyseMessages() throws IOException{
		EmailList tempList = new EmailList();
		List<String> noise = getNoise();
		String[] messages = getMessages();
		for(String msg: messages){
			String [] MsgParts = msg.split("gPostBegin");
			String email = MsgParts[0].trim();
			String [] words = MsgParts[1].trim().toLowerCase().split(" ");
			for(String word: words){
				if(word.matches("[a-z]+") && !noise.contains(word)){
					tempList.addMessage(email, word);
				}
			}
		}
		System.out.println(tempList);
	}
}
