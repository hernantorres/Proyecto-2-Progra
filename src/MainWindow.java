import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Random;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import javax.swing.text.MaskFormatter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that creates the main window for the game. Extends JFrame.
 * This object displays the image and text field where the game takes place.
 */
public class MainWindow extends JFrame implements ActionListener
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
   	private JLabel currentLevelView;
   	
   	// El boton para cambiar de imagen (provisional)
   	private JButton changeImageButton;
   	
   	// Array con los personajes (poseen su propia imagen, proximamente su nombre)
   	private Character characters = new Character();

   	private Random random;
   	
   	// El panel donde va el campo de texto
   	private JPanel entryPanel;
   	
   	// El campo de texto
   	private JFormattedTextField text;
   	
   	// Panel del sur, contiene el temporizador y el camp ode texto
   	private JPanel southPanel;
   	
   	//Tamanno del text field
   	private int textFieldSize = 10;
   	

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
		
		// Hernan que hace esto?
		random = new Random();
		
		displayElements();
		
		// Inicia el temporizador
		this.elapsedTime = new Timer(1000, this);
   		this.elapsedTime.start();
	}
	
 


   /** 
    * Method for displaying elements in the screen. Prepares every panel and then adds to the main frame
    */
	private void displayElements()
	{
		MaskFormatter mascara = null;
		try
		{
			mascara = new MaskFormatter("#### #####");
			mascara.setPlaceholderCharacter('_');
		}
		catch ( Exception ex)
		{
			//
		}
		
		// La iamgen del centro
		//Create a label for the imageview
		this.imageView = new JLabel();
		//Sets alignment of the image to the center so that it doesnt go down to the sides
		this.imageView.setHorizontalAlignment(JLabel.CENTER);
		
		//New label for the timer view
		this.timerView = new JLabel();
		
		//Gets the current level name
		this.currentLevelView = new JLabel( characters.getCurrentLevel() );
		//sets font
		Font font = new Font("Sans", Font.ITALIC, 20);
		this.currentLevelView.setFont( font );
		//Aligns to the center
		this.currentLevelView.setHorizontalAlignment(JLabel.CENTER);

		// El panel del sur (temporizador y campo de texto)
		
		this.text = new JFormattedTextField( mascara );
		this.text.setFont(font);
		this.entryPanel = new JPanel();
		this.entryPanel.add(text);
		this.southPanel = new JPanel( new GridLayout(2,1));
		this.timerView.setHorizontalAlignment(JLabel.CENTER);
		this.timerView.setFont ( font );
		this.southPanel.add(timerView);
		this.southPanel.add(entryPanel);
		
		// Boton que cambia imagenes
		this.changeImageButton = new JButton("Siguiente");
		this.changeImageButton.addActionListener(this);
		

		// Annade los elementos al Frame
		this.add(currentLevelView, BorderLayout.NORTH);
		this.add(imageView, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(changeImageButton, BorderLayout.EAST);

	}

    /**
     * Check for actions on a specific event
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
    	  this.updateLevel()
      }
   }
	
	/**
	 * Updated level counter on object characters and goes to the next level.
	 * Updates picture
	 * Updates seconds
	 * Updates level name
	 */
	public void updateLevel()
	{
		characters.levelGoToNext();
		this.imageView.setIcon( characters.getImage() );
		currentLevelView.setText( characters.getCurrentLevel() );
		elapsedSeconds = characters.getSeconds();
	}
 
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
		}
		else
			this.timerView.setText(text);
	}
}
