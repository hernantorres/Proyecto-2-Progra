import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that creates the main window for the game. Extends JFrame.
 * This object displays the image and text field where the game takes place.
 */
public class MainWindow extends JFrame implements ActionListener
{
	// <Valores para la ventana>
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets( getGraphicsConfiguration() );
	int taskBarSize = scnMax.bottom;
	double monitorWidth = screenSize.getWidth();
	double monitorHeight = screenSize.getHeight() - (double)taskBarSize;
	
	private static int desiredHeightRatio = 1;
	private static int desiredWidthRatio = 1;
	
	private int windowHeight = (int)Math.floor( monitorHeight / desiredHeightRatio ) * desiredHeightRatio;
	private int windowWidth = ( windowHeight * desiredWidthRatio ) / desiredHeightRatio;
	
	private static int textLength = 30;
	private static String gameName = "Finite Sentence Machine";
	// </Valores para la ventana>
	
	// El timer para el temporizador y sus segundos
	private Timer elapsedTime = null;
   	private long elapsedSeconds = 30;
   	
   	// El label del temporizador
   	private JLabel timerView;
   	
   	// El label de la imagen
   	private JLabel imageView;
   	
   	// El label del nivel
   	private JLabel currentLevelView;
   	
   	// El boton para cambiar de imagen (provisional)
   	private JButton changeImageButton;
   	
   	// Array con los personajes (poseen su propia imagen, proximamente su nombre)
   	private Character[] characters;

   	private Random random;
   	
   	// El panel donde va el campo de texto
   	private JPanel entryPanel;
   	
   	// El campo de texto
   	private JTextField text;
   	
   	// Panel del sur, contiene el temporizador y el camp ode texto
   	private JPanel southPanel;
   	

	/** Constructor */
	public MainWindow()
	{
		super( gameName );
		// this.setSize(getWindowWidth(), getWindowHeight());
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation((int)(monitorWidth/2 - this.windowWidth/2), (int)(monitorHeight/2 - this.windowHeight/2) );
		
		BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);
		
		random = new Random();
		characters = new Character[5];
		
		// Carga el array con las rutas de las imagenes
		this.loadCharacters();
		
		displayElements();
		
		// Inicia el temporizador
		this.elapsedTime = new Timer(1000, this);
   		this.elapsedTime.start();
	}
	
 


   /** 
    *
    */
	private void displayElements()
	{
		// La iamgen del centro
		this.imageView = new JLabel();
		this.imageView.setHorizontalAlignment(JLabel.CENTER);
		this.timerView = new JLabel();
		
		// el label del nivel
		this.currentLevelView = new JLabel("LEVEL 1");
		Font font = new Font("Sans", Font.ITALIC, 20);
		this.currentLevelView.setFont( font );
		this.currentLevelView.setHorizontalAlignment(JLabel.CENTER);

		// El panel del sur (temporizador y campo de texto)
		this.text = new JTextField(10);
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
		

		// AÃ±ade los elementos al Frame
		this.add(currentLevelView, BorderLayout.NORTH);
		this.add(imageView, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(changeImageButton, BorderLayout.EAST);

	}

   @Override
   public void actionPerformed(ActionEvent event)
   {
	   // Si es un evento del timer
      if ( event.getSource() == this.elapsedTime )
      {
         this.updateElapsedTime();
      }
      
      // Si es un evento del button
      if ( event.getSource() == this.changeImageButton )
      {
         this.imageView.setIcon(characters[random.nextInt(5)].getImage());
      }
   }
 
   private void updateElapsedTime()
   {
      --this.elapsedSeconds;
      long minutes = this.elapsedSeconds / 60;
      long seconds = this.elapsedSeconds % 60;
      
      String text = String.format("%02d:%02d"
            , minutes
            , seconds);
      if (this.elapsedSeconds == 0)
      {
    	  this.timerView.setText("Se acabó el tiempo");
    	  this.elapsedTime.stop();
      }
      else
    	  this.timerView.setText(text);
   }
   
   public void loadCharacters()
   {
	   characters[0] = new Character( "../assets/images/louisCK.png");
	   characters[1] = new Character( "../assets/images/imgOscarLopez.png");
	   characters[2] = new Character( "../assets/images/deadpool.png");
	   characters[3] = new Character( "../assets/images/forrestGump.png");
	   characters[4] = new Character( "../assets/images/imgJoseFigueres.png");
   }
}
