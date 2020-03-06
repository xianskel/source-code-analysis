import java.io.IOException;

/**
 * Driver method used for testing program.
 * 
 * @author Cian Skelly 0559377
 */
public class Driver {
	
    // Replace with appropriate file path for gPostMessages.txt
	private static String gPostMessages = "./resc/gPostMessages.txt";
    // Replace with appropriate file path for NoiseWords.txt
	private static String NoiseWords = "./resc/NoiseWords.txt";
	
	public static void main(String[] args) throws IOException{
		MsgReader test = new MsgReader(gPostMessages, NoiseWords);
		test.analyseMessages();
	  }	
}
