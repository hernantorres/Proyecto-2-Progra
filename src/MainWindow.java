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
   	
 // Panel del norte, contiene el nivel y el temporizador
   	private JPanel northPanel;
   	
   	// Panel del sur, contiene el campo de texto y el boton interactivo
   	private JPanel southPanel;
   	
   	//Tamanno del text field
   	private int textFieldSize = 10;
   	
   	public enum buttonState{
   		ME_RINDO, SIGUIENTE, INICIAR
   	}
   	
   	public buttonState state;
   	

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
		MaskFormatter mascara = null;
		try
		{
			mascara = new MaskFormatter("UUUUUUUUU");
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
		Font font = new Font("Courier New", Font.ITALIC, 20);
		this.currentLevelView.setFont( font );
		//Aligns to the center
		this.currentLevelView.setHorizontalAlignment(JLabel.CENTER);

		// Boton que cambia imagenes
		this.changeImageButton = new JButton("Iniciar");
		this.changeImageButton.addActionListener(this);
				
		// El panel del sur (campo de texto y boton interactivo)
		this.text = new JFormattedTextField( mascara );
		this.text.setFont(font);
		this.text.addKeyListener(this);
		this.entryPanel = new JPanel();
		this.entryPanel.add(text);
		this.southPanel = new JPanel( new GridLayout(1,2));
		this.southPanel.add(entryPanel);
		this.southPanel.add(changeImageButton);
		
		// El panel del norte (nivel y temporizador)
		this.northPanel = new JPanel( new GridLayout(1,2));
		this.timerView.setHorizontalAlignment(JLabel.CENTER);
		this.timerView.setFont ( font );
		this.northPanel.add(currentLevelView);
		this.northPanel.add(timerView);
		

		// Annade los elementos al Frame
		this.add(northPanel, BorderLayout.NORTH);
		this.add(imageView, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);

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
    	  if (this.state == buttonState.SIGUIENTE)
    	  {
    		  this.state = buttonState.INICIAR;
    		  this.changeImageButton.setText("Iniciar");
    		  this.imageView.setIcon(null);
    		  this.timerView.setText(null);
    	  }   		  
    	  else if(this.state == buttonState.INICIAR)
    	  {
    		  this.updateLevel();
    		  this.state = buttonState.ME_RINDO;
    		  this.changeImageButton.setText("Me rindo");
    	  }
    	  else
    	  {
    		  this.state = buttonState.SIGUIENTE;
    		  this.changeImageButton.setText("Siguiente");
    		  this.elapsedTime.stop();
    	  }
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
		currentLevelView.setText( characters.getCurrentLevel() );
		this.imageView.setIcon(this.characters.getImage());
		elapsedSeconds = characters.getSeconds();
		this.elapsedTime.start();
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
			  this.state = buttonState.SIGUIENTE;
			  this.changeImageButton.setText("Siguiente");
		}
		else
			this.timerView.setText(text);
	}


	@Override
	public void keyPressed(KeyEvent keyEvent) {
		
		if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
        {
            System.out.println(this.text.getText());
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
