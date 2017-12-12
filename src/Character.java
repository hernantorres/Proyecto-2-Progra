import java.util.Scanner;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;


/**
 * @author Felipe and Hernan
 * Character class manages everything relating to the character's image, name and seconds it must take to kill them.
 */
public class Character {
	
	/** Array for storing images of each character */
	private ImageIcon[] images;
	
	/** Array for stpring the characters name based on the name of the .png image */
	private String[] names;
	
	/** 
	 * Array for the time given to the player to kill each character
	 * Some difficult characters have more time than others
	 * When these seconds hit 0, the character shoots and kills the player
	 */
	private long[] seconds;
	
	/** 
	 * The scanner object used to read the txt file
	 */
	private Scanner input;
	
	/**
	 * Constructor for loading characters
	 */
	public Character()            //Rute es igual a algo como ../assets/images/louisCK.png
	{
		loadCharacters();
	}
	
	/**
	 * Load characters from a text file in the assets folder. Read link
	 * and read the number representing the characters seconds
	 */
	private void loadCharacters()
	{
		// Necessary to read the document without errors
		try
		{
			URL url = this.getClass().getResource("Character_list.txt");

			try {
				File file = new File(url.toURI());
				this.input = new Scanner(file);
			} catch (Exception ex) {}
			
            
            //Array length for the amount of characters
            int arrayLength = input.nextInt();
            
            //Create arrays with the same depth
            images = new ImageIcon[arrayLength];
            names = new String[arrayLength];
            seconds = new long[arrayLength];
            
            //Next line so me can use the scanner correctly
            input.nextLine();
            
            //For loop for every character in the game
            for(int index = 0; index < arrayLength; index++)
            {
            	//Read the names and stores name and image route
            	String name = input.nextLine();
            	names[index] = name.replaceAll("_", " ");
            	images[index] = new ImageIcon(this.getClass().getResource(name + ".png"));
                
                //Read seconds before particular character shoots
                long second = input.nextLong();
                seconds[index] = second;
                input.nextLine();
                
            }
            input.close();
        }
		//Exception handling
		catch (Exception ex)
		{
        }
	
	}


	/**
	 * Get the image of the character of the particular level
	 * @return imageIcon of the character playing right now
	 */
	public ImageIcon getImage(int index)
	{
		return images[index -1];
	}

	/**
	 * Get the seconds of the character of the particular level
	 * @return imageIcon of the character playing right now
	 */
	public long getSeconds( int index )
	{
		// TODO Auto-generated method stub
		return seconds[index -1];
	}
	
	/**
	 * Get the name of the actual level character
	 * @return String the name of the character
	 */
	public String getName(int index)
	{
		// TODO Auto-generated method stub
		return names[index - 1];
	}
	
	/**
	 * Get the mask that JFormattedField uses
	 * @return String a mask, for example "####-###"
	 */
	public String getMaskFormat(int index)
	{
		// The the string to get concatenated chars
		String mask = "";
		
		// Get the name to extract the mask
		String word = this.getName(index);
		String[] words = word.split(" ");
		
		for (int indx = 0; indx < words.length; indx++)
		{
			mask += " ";
			for (int letter = 0; letter < words[indx].length(); letter++)
				mask += "U"; // The "U" char represents an UpperCase letter
		}
		
		// Remove the first whitespace
		mask = mask.substring(1);
		return mask;
	}

}
