import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.Timer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class that creates the main window for the game. Extends JFrame.
 * This object displays the image and text field where the game takes place.
 */
public class MainWindow extends JFrame implements ActionListener, KeyListener
{
	// <Valores para la ventana>
	
	//First obtain a reference to the Screen size via Toolkit
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//Get height of the task bar
	Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets( getGraphicsConfiguration() );
	int taskBarSize = scnMax.bottom;
	
	//Based on previous data, get the width and height 
	double monitorWidth = screenSize.getWidth();
	double monitorHeight = screenSize.getHeight() - (double)taskBarSize;
	
	//Desired ratio, this values will be blown up with the monitor height and width
	private static int desiredHeightRatio = 1;
	private static int desiredWidthRatio = 1;
	
	//In case the ratio cannot fit perfectly into the monitor height, divide and multiply
	//again to get the nearest point to monitorHeight that leaves no remainder if divided
	//by the desired ratio
	private int windowHeight = (int)Math.floor( monitorHeight / desiredHeightRatio ) * desiredHeightRatio;
	//With cross multiplication we get the width relative to the windowHeight just obtained
	private int windowWidth = ( windowHeight * desiredWidthRatio ) / desiredHeightRatio;
	// </Valores para la ventana>
	
	//default game name
	private static String gameName = "Finite Sentence Machine";
	
	// El timer para el temporizador y sus segundos
	private Timer elapsedTime = null;
   	private long elapsedSeconds = 7;
   	
   	// El label del temporizador
   	private JLabel timerView;
   	
   	// El label de la imagen
   	private JLabel imageView;
   	
   	// El label del nivel
   	private JLabel scoreView;
   	
   	// El boton para cambiar de imagen (provisional)
   	private JButton changeImageButton;
   	
   	// Array con los personajes (poseen su propia imagen, proximamente su nombre)
   	private Character characters = new Character();
   	
   	// El panel donde va el campo de texto
   	private JPanel entryPanel;
   	
   	// El campo de texto
   	private JFormattedTextField text;
   	
   	// Panel del norte, contiene el nivel y el temporizador
   	private JPanel northPanel;
   	
   	// Panel del sur, contiene el campo de texto y el boton interactivo
   	private JPanel southPanel;
   	
   	// The current level number
   	private int currentLevel = 0;
   	
   	// The number of hits of the player
   	private int score = 0;
   	
   	//To write images on screen, specifically the "Correct" and "INcorrect" images
   	private Graphics graphic = null;
   	
   	//Images of each of the signs for getting a word correct, failing it, and getting an in between score.
   	private BufferedImage hint = null;
   	private BufferedImage fail = null;
   	private BufferedImage okay = null; 
   	
   	//For measuring how close an answer is to the true characters name
   	private LevenshteinDistance distance = new LevenshteinDistance();
   	
   	//Boolean for considering when enter can have a meaning depending on the state of the button in the GUI(LISTO, ME_RINDO, 
   	//SIGUIENTE, INICIAR)
   	private boolean canEvaluate = true;
   	
   	private String NEXT_LEVEL_PROMPT = "Presiona el boton LISTO para iniciar esta ronda";
   	
   	//The width used for the images. This is agreed upon between artists and programmers
   	//The width and height is the same because the images and the game window are supposed to be squares,
   	//hence 1:1 desired ratio
   	private int IMAGE_WIDTH = 300;
   	
   	//Used to hopefully put the images of correct, fail, and okay in the center
   	private int IMAGE_OFFSET = (windowWidth / 2) - (IMAGE_WIDTH / 2);
   	
   	private int BONUS_POINTS = 50;
   	
   	private int MIN_PUNISH_SCORE = 700;
   	
   	/**
	 * An enum to recognize the state of the game in order to make actions.
	 * This is mostly important for the JButton 
	 */
   	public enum buttonState{
   		LISTO, ME_RINDO, SIGUIENTE, INICIAR
   	}
   	
   	// The "current" enum used by the game
   	public buttonState state;
   	
   	// The JFormattedTextField uses this mask to get formatted
   	MaskFormatter mask = null;
   	

	/**
	 * Constructor. Sets window width and height. 
	 * Opens window in the middle of the screen sets layout and displays elements. 
	 */
	public MainWindow()
	{
		//Set size and close Op
		super( gameName );
		this.setSize( windowWidth, windowHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//put in the center
		this.setLocation((int)(monitorWidth/2 - this.windowWidth/2), (int)(monitorHeight/2 - this.windowHeight/2) );
		
		//Create main layout
		BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);

		//Shows elements in frame
		displayElements();
		
		//Starting state
		this.state = buttonState.INICIAR;
		
		//Starts timer
		this.elapsedTime = new Timer(1000, this);		
	}
	
 


   /** 
    * Method for displaying elements in the screen. Prepares every panel and then adds to the main frame
    */
	private void displayElements()
	{
		// Using a Try-Catch is necessary to set mask Formatted, also used on the rest of the code
		// What is a mask? A way of limiting the space of a JTextField, we can even set spaces in between
		// This is normaly used for setting dates like so: ##/##/#### and ###-####
		this.mask = null;
		try
		{
			mask = new MaskFormatter("");
			mask.setPlaceholderCharacter('_');
		}
		catch ( Exception ex)
		{
			//
		}
		
		
		// Image management
		//Create a label for the imageView
		this.imageView = new JLabel();
		//Sets alignment of the image to the center so that it doesnt go down to the sides
		this.imageView.setHorizontalAlignment(JLabel.CENTER);
		
		// Time management
		//New label for the timer view
		this.timerView = new JLabel();
		
		// Level view management
		//Gets the current level name
		this.scoreView = new JLabel( String.format("SCORE %d", score) );
		//sets font
		Font font = new Font("Courier New", Font.ITALIC, 20);
		this.scoreView.setFont( font );
		//Aligns to the center
		this.scoreView.setHorizontalAlignment(JLabel.CENTER);

		// JButton management
		this.changeImageButton = new JButton("INICIAR");
		this.changeImageButton.addActionListener(this);
		
		// JFormattedTextField management
		this.text = new JFormattedTextField();
		this.text.setFont(font);
		this.text.addKeyListener(this);
		// The field goes in to a JPanel
		this.entryPanel = new JPanel();
		this.entryPanel.add(text);
		
		// The southPanel elements
		this.southPanel = new JPanel( new GridLayout(1,2));
		this.southPanel.add(entryPanel);
		this.southPanel.add(changeImageButton);
		
		// The northPanel elements
		this.northPanel = new JPanel( new GridLayout(1,2));
		this.timerView.setHorizontalAlignment(JLabel.CENTER);
		this.timerView.setFont ( font );
		this.northPanel.add(scoreView);
		this.northPanel.add(timerView);
		this.imageView.setFont(font);
		this.imageView.setText("Presiona el boton INICIAR para jugar");
		
		//A bunch of obligatory try-catches for the images to show after every answer
		try
        {
           this.hint = ImageIO.read( this.getClass().getResource("AnsCorrect.png") );
        }
        catch ( IOException exception )
        {
        }
		
		try
        {
           this.fail = ImageIO.read( this.getClass().getResource("AnsFail.png") );
        }
        catch ( IOException exception )
        {
        }
		
		try
        {
           this.okay = ImageIO.read( this.getClass().getResource("AnsOkay.png") );
        }
        catch ( IOException exception )
        {
        }
		
		// Add the "main" elements to the frame
		this.add(northPanel, BorderLayout.NORTH);
		this.add(imageView, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);

	}

    /**
     * Check for actions on a specific event (timer or button events)
     */
	@Override public void actionPerformed(ActionEvent event)
	{
	   // If Timer event
      if ( event.getSource() == this.elapsedTime )
      {
         this.updateElapsedTime();
      }
      
      // If button event. If button is pressed do something depending on the current state
      if ( event.getSource() == this.changeImageButton )
      {
    	  //State SIGUIENTE
    	  if (this.state == buttonState.SIGUIENTE || this.state == buttonState.INICIAR)
    	  {
    		  this.imageView.setText(null);
    		  this.updateLevel();
    		  this.imageView.setText( NEXT_LEVEL_PROMPT );
    	  }   		  
    	  //State LISTO!
    	  else if(this.state == buttonState.LISTO)
    	  {
    		  this.startLevel();
    	  }
    	  else //state "ME RINDO"
    	  {
    		  this.graphic = this.getGraphics();
          	  graphic.drawImage(fail, IMAGE_OFFSET, IMAGE_OFFSET, null);
    		  this.subtractScore();
    		  this.text.setText("");
    		  this.stopLevel();
    	  }
      }
   }
	
	private void subtractScore() {
		// This min() ensures that characters with a high score (example: Rorshach gives over 1600 points)
		// aren't too damaging for the general score of the player. With this, failing Rorshach will not
		// subtract 1600 points, but 300.
    	long toSubtract = Math.min(MIN_PUNISH_SCORE, characters.getScore( currentLevel ));
		score = (int) (score - toSubtract);
    	scoreView.setText(String.format("SCORE %d", score ));
	}

	/**
	 * Updates button and resets the image and timer
	 */
	public void updateLevel()
	{
		if(currentLevel == characters.getArrayLength())
		{
			this.remove(northPanel);
			this.remove(southPanel);
			this.remove(imageView);
			JLabel end = new JLabel(String.format("Fin del juego, tu puntaje es: %d", this.score));
			end.setHorizontalAlignment(JLabel.CENTER);
			end.setFont(new Font("Courier New", Font.BOLD, 20));
			this.add(end, BorderLayout.CENTER);

		}
		  this.state = buttonState.LISTO;
		  this.changeImageButton.setText("LISTO!");
		  
		  // Resets the image and the timer
		  this.imageView.setIcon(null);
		  this.timerView.setText(null);
		  // Update the level view
		  currentLevel++;
		  
		  scoreView.setText( String.format("SCORE %d", score ));
		  try {
				this.mask.setMask(characters.getMaskFormat( currentLevel ));			
				} catch (ParseException e) {} 
		  DefaultFormatterFactory factory = new DefaultFormatterFactory(this.mask);
		  this.text.setFormatterFactory(factory);
		  this.text.setEditable(false);
		  this.text.setBackground(Color.RED);
	}
	
	
	/**
	 * Updates text field, button, image and starts the timer
	 */
	public void startLevel()
	{
		canEvaluate = true;
		this.imageView.setText(null);
		// This is necessary to accomplish the mask setting
		  try {
				this.mask.setMask(characters.getMaskFormat( currentLevel ));			
				} catch (ParseException e) {} 
		  DefaultFormatterFactory factory = new DefaultFormatterFactory(this.mask);
		  this.text.setFormatterFactory(factory);
		  
		  this.text.setEditable(true);
		  this.text.setBackground(Color.WHITE);
		  this.text.requestFocus();
		  
		  // Show the level info. (useful for the first level)
		  scoreView.setText( String.format("SCORE %d", score ));
		  // Ask for the image to the character class
		  this.imageView.setIcon(this.characters.getImage( currentLevel ));
		  // Update the timer
		  elapsedSeconds = characters.getSeconds( currentLevel );
		  this.elapsedTime.start();
		  // Update the button
		  this.state = buttonState.ME_RINDO;
		  this.changeImageButton.setText("ME RINDO");

	}
	
	/**
	 * Stops the timer and reset the text field
	 */
	public void stopLevel()
	{
		canEvaluate = false;  
		// The current level is stopped, get to the next
		  this.state = buttonState.SIGUIENTE;
		  this.changeImageButton.setText("SIGUIENTE");
		  this.elapsedTime.stop();
		  
		  this.text.setEditable(false);
		  this.text.setBackground(Color.RED);
		  try {
				this.mask.setMask(characters.getMaskFormat( currentLevel ));			
				} catch (ParseException e) {} 
		  DefaultFormatterFactory factory = new DefaultFormatterFactory(this.mask);
		  this.text.setFormatterFactory(factory);
	}
 
	/**
	 * Updates the timer to get an animation
	 */
   private void updateElapsedTime()
   {
		//Subtract from elapsed seconds because counter goes backwards
	   --this.elapsedSeconds;
		//Get seconds by moduling the amount of seconds in a minute (60)
	   long seconds = this.elapsedSeconds % 60;
		  
		//Format string to show on screen
	   String text = String.format("%02d", seconds);
		if (this.elapsedSeconds == 0)
		{
			  this.timerView.setText("Se acabó el tiempo");
			  this.graphic = this.getGraphics();
			  graphic.drawImage(fail, IMAGE_OFFSET, IMAGE_OFFSET, null);
			  this.subtractScore();
			  this.stopLevel();
		}
		else
			this.timerView.setText(text);
	}


	@Override
	/**
	 * This event is used to recognize the ENTER key press to match the name given by the user
	 */
	public void keyPressed(KeyEvent keyEvent) {
		
		if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
        {
			// If the name matches with the correct character name, advice to the user
			// and update the 'correct answers' counter, otherwise, show the mistake
            long difference = distance.apply(this.text.getText(), this.characters.getName( currentLevel ));
            //This canEvaluate variable makes sure that the ENTER key is not spamed in every level 
            //and keep doing the checks for the key press below
            if( canEvaluate )
            {
            	//Set the boolean false to prevent repeating checks in a single event
            	canEvaluate = false;
            	//If we got the word correct
            	if(this.text.getText().equals(this.characters.getName( currentLevel )))
	            {
	            	//Draw a green checkmark
            		this.graphic = this.getGraphics();
	            	graphic.drawImage(hint, 200, 200, null);
	            	
	            	//println for debugging
	            	System.out.println("ACERTÓ");
	            	//Sum the characters entire score to the total score.
	            	score = (int) (score + characters.getScore( currentLevel ));
	            	
	            	//Beta feature: give bonus points for extra points
	            	score = (int) (score + (elapsedSeconds*BONUS_POINTS));
	            	
	            	//Update text
	            	scoreView.setText(String.format("SCORE %d", score ));
	            	this.stopLevel();
	            	
	            }
	            // If we didn't fail the word completely, we got at least a word right
	            else if ( difference < this.characters.getLongestLevenshteinDistance( currentLevel ) )
	            {
	            	//Draw a curvy line indicating okay score. It's not the worst nor the best.
	            	this.graphic = this.getGraphics();
	            	graphic.drawImage(okay, IMAGE_OFFSET, IMAGE_OFFSET, null);
	            	
	            	System.out.println("CASI ACIERTA");
	            	// The closest the answer, the closest the value of this variable (multiplier) to 1.
	            	// multiplier gets the percentage of what the player got right, depending on the 
	            	// longest Levenshtein distance
	            	
	            	// The reason we put a 1.0 is because levenshtein.apply returns the changes the string needs
	            	// to reach the correct answer, and not the percentage of the correct answer. What we need
	            	// here is percentage which is its inverse.
	            	double multiplier = 1.0 - ( (double)(difference) / this.characters.getLongestLevenshteinDistance(currentLevel) );
	            	// multiplier alters the characters score in a range of 0 to 100%
	            	score = (int) (score + (characters.getScore( currentLevel ) * multiplier));
	            	scoreView.setText(String.format("SCORE %d", score ));
	            	//Debugging prints
	            	System.out.printf("This rounds lehvenstein max is %d%n", this.characters.getLongestLevenshteinDistance( currentLevel ));
	            	System.out.printf("Your distance is %d%n", difference);
	            	System.out.printf("This rounds multiplier is %f%n", multiplier);
	            	this.stopLevel();
	            }
	            // The word was guessed incorrectly
	            else
	            {
	            	this.graphic = this.getGraphics();
	            	graphic.drawImage(fail, 200, 200, null);
	            	
	            	System.out.println("FALLO");
	            	this.subtractScore();
	            	this.stopLevel();
	            }
        	}
        }
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub	
	}
	


}
