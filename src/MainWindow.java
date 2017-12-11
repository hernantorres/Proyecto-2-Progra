import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

	//default textLength
	private static int textLength = 30;
	
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
   	private JLabel levelAndScoreView;
   	
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
   	private int currentLevel = 1;
   	
   	// The number of hits of the player
   	private int score = 0;
   	
   	/**
	 * An enum to recognize the state of the game in order to make actions.
	 * This is mostly important for the JButton 
	 */
   	public enum buttonState{
   		ME_RINDO, SIGUIENTE, INICIAR
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
		super( gameName );
		this.setSize( windowWidth, windowHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//put in the center
		this.setLocation((int)(monitorWidth/2 - this.windowWidth/2), (int)(monitorHeight/2 - this.windowHeight/2) );
		
		BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);

		// Muestra los elementos del frame
		displayElements();
		
		
		this.state = buttonState.INICIAR;
		
		// Inicia el temporizador
		this.elapsedTime = new Timer(1000, this);		
	}
	
 


   /** 
    * Method for displaying elements in the screen. Prepares every panel and then adds to the main frame
    */
	private void displayElements()
	{
		// This is necessary to set mask Formatted, also used on the rest of the code
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
		this.levelAndScoreView = new JLabel( String.format("LEVEL %d - SCORE %d", currentLevel, score) );
		//sets font
		Font font = new Font("Courier New", Font.ITALIC, 20);
		this.levelAndScoreView.setFont( font );
		//Aligns to the center
		this.levelAndScoreView.setHorizontalAlignment(JLabel.CENTER);
		this.levelAndScoreView.setText("USA EL BOTON PARA INICIAR");

		// JButton management
		this.changeImageButton = new JButton("Iniciar");
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
		this.northPanel.add(levelAndScoreView);
		this.northPanel.add(timerView);
		

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
	   // Si es un evento del timer
      if ( event.getSource() == this.elapsedTime )
      {
         this.updateElapsedTime();
      }
      
      // Si es un evento del button
      if ( event.getSource() == this.changeImageButton )
      {
    	  if (this.state == buttonState.SIGUIENTE)
    	  {
    		  this.updateLevel();
    	  }   		  
    	  else if(this.state == buttonState.INICIAR)
    	  {
    		  this.startLevel();
    	  }
    	  else
    	  {
    		  this.stopLevel();
    	  }
      }
   }
	
	/**
	 * Updates button and resets the image and timer
	 */
	public void updateLevel()
	{
		  this.state = buttonState.INICIAR;
		  this.changeImageButton.setText("Iniciar");
		  
		  // Resets the image and the timer
		  this.imageView.setIcon(null);
		  this.timerView.setText(null);
		  // Update the level view
		  currentLevel++;
		  levelAndScoreView.setText( String.format("LEVEL %d - SCORE %d", currentLevel, score ));

	}
	
	
	/**
	 * Updates text field, button, image and starts the timer
	 */
	public void startLevel()
	{
		// This is necessary to accomplish the mask setting
		  try {
				this.mask.setMask(characters.getMaskFormat( currentLevel ));			
				} catch (ParseException e) {} 
		  DefaultFormatterFactory factory = new DefaultFormatterFactory(this.mask);
		  this.text.setFormatterFactory(factory);
		  
		  // Show the level info. (useful for the first level)
		  levelAndScoreView.setText( String.format("LEVEL %d - SCORE %d", currentLevel, score ));
		  // Ask for the image to the character class
		  this.imageView.setIcon(this.characters.getImage( currentLevel ));
		  // Update the timer
		  elapsedSeconds = characters.getSeconds( currentLevel );
		  this.elapsedTime.start();
		  // Update the button
		  this.state = buttonState.ME_RINDO;
		  this.changeImageButton.setText("Me rindo");

	}
	
	/**
	 * Stops the timer and reset the text field
	 */
	public void stopLevel()
	{
		  // The current level is stopped, get to the next
		  this.state = buttonState.SIGUIENTE;
		  this.changeImageButton.setText("Siguiente");
		  this.elapsedTime.stop();
		  
		  // This is necessary to accomplish the mask setting
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
			  this.elapsedTime.stop();
			  this.state = buttonState.SIGUIENTE;
			  this.changeImageButton.setText("Siguiente");
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
			// and update the hints counter, otherwise, show the mistake
            if(this.text.getText().equals(this.characters.getName( currentLevel )))
            {
            	System.out.println("ACERTÓ");
            	levelAndScoreView.setText(String.format("LEVEL %d - SCORE %d", currentLevel, ++score));
            	this.stopLevel();
            	
            }
            else
            {
            	System.out.println("NOMBRE EQUIVOCADO");
            	this.stopLevel();
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
