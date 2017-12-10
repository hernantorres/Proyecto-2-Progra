import java.util.Scanner;
import java.io.File;
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
	
	/** First level is level 0, reserved for the menu part of the game (click to play) */
	private int currentLevel = 1;
	
	
	/***
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
		/*try
		{
            File file = new File("/resources/Character_list.txt");
            // Proyecto-2-Progra\assets\textFields

            //Create scanner for the text file
            Scanner input = new Scanner(file);
            
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
            	//Read paths for the image and create an ImageIcon
            	String path = input.nextLine();
            	images[index] = new ImageIcon( path );
                System.out.println("I read path: " + path);
                
                //Read seconds before particular character shoots
                long second = input.nextLong();
                seconds[index] = second;
                System.out.println("I read second: " + second);
                input.nextLine();
                
                //Assign names based on path String. The picture contains the name of the character
                String name = path.replace("../assets/images/", "");
                name = name.replaceAll(".png", "");
                names[index] = name;
                System.out.println("I get name: " + name);
            }
            input.close();
        }
		//Exception handling
		catch (Exception ex)
		{
            ex.printStackTrace();
        }*/
		images = new ImageIcon[5];
		images[0] = new ImageIcon(getClass().getResource("/resources/deadpool.png"));
		images[1] = new ImageIcon(getClass().getResource("/resources/forrest_gump.png"));
		images[2] = new ImageIcon(getClass().getResource("/resources/jose_figueres.png"));
		images[3] = new ImageIcon(getClass().getResource("/resources/louis_ck.png"));
		images[4] = new ImageIcon(getClass().getResource("/resources/oscar_lopez.png"));
		seconds = new long[5];
		seconds[0] = 10;
		seconds[1] = 11;
		seconds[2] = 12;
		seconds[3] = 13;
		seconds[4] = 14;
		names = new String[5];
		names[0] = "DEADPOOL";
		names[1] = "FORREST GUMP";
		names[2] = "JOSE FIGUERES";
		names[3] = "LOUIS CK";
		names[4] = "OSCAR LOPEZ";

		
	}


	/**
	 * Get the image of the character of the particular level
	 * @return imageIcon of the character playing right now
	 */
	public ImageIcon getImage()
	{
		return images[currentLevel];
	}
	
	public void levelGoToNext()
	{
		// TODO Auto-generated method stub
		currentLevel = (currentLevel + 1) % getAmountOfCharacters();
		
	}

	
	private int getAmountOfCharacters()
	{
		// TODO Auto-generated method stub
		return images.length;
	}


	/**
	 * Get the string of the number of the particular level
	 * @return current level name.
	 */
	public String getCurrentLevel()
	{
			return String.format("LEVEL %d", currentLevel);
	}


	/**
	 * Get the seconds of the character of the particular level
	 * @return imageIcon of the character playing right now
	 */
	public long getSeconds()
	{
		// TODO Auto-generated method stub
		return seconds[currentLevel];
	}
	
	/**
	 * Get the name of the actual level character
	 * @return String the name of the character
	 */
	public String getName()
	{
		// TODO Auto-generated method stub
		return names[currentLevel];
	}
	
	/**
	 * Get the mask that JFormattedField uses
	 * @return String a mask, for example "####-###"
	 */
	public String getMask()
	{
		String mask = "";
		String word = this.getName();
		String[] words = word.split(" ");
		for (int index = 0; index < words.length; index++)
		{
			mask += " ";
			for (int letter = 0; letter < words[index].length(); letter++)
				mask += "U";
		}
		mask = mask.substring(1);
		return mask;
	}

}
