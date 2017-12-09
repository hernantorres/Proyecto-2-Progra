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
	private int currentLevel = 0;
	
	
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
		try
		{
            File file = new File("../assets/textFields/Character_list.txt");
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
        }
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
		// If current level is 0, we are in the menu, therefore, display an instructions to play
		if(currentLevel == 0)
		{
			return("USA EL BOTON PARA INICIAR");
		}
		else
		{
			return String.format("LEVEL %d", currentLevel);
		}
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

}
