import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Scanner;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Font;
/**
 * Class that creates the main wondow for the game. Extends JFrame.
 * This object displays the image and text field where the game takes place.
 */
public class MainWindow extends JFrame
{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double monitorWidth = screenSize.getWidth();
	double monitorHeight = screenSize.getHeight();
	
	private static int desiredHeightRatio = 20;
	private static int desiredWidthRatio = 15;
	
	private int windowHeight = (int)Math.floor( monitorHeight / desiredHeightRatio ) * desiredHeightRatio;
	private int windowWidth = ( windowHeight * desiredWidthRatio ) / desiredHeightRatio;
	
	private static int textLength = 30;
	private static String gameName = "Finite Sentence Machine";

	/** Constructor */
	public MainWindow()
	{
		super( gameName );
		this.setSize(windowWidth, windowHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		BorderLayout mainLayout = new BorderLayout();
//	    this.setLayout( mainLayout );
		this.displayImageView();
//		this.displayTimerView();
		this.displayTextFieldView();
//		this.displayCurrentLevelView();
	}

	/**
	* 
	*/
	private void displayImageView()
	{

		System.out.printf("monitor height = %.2f %n", monitorHeight);
		System.out.printf("monitor width = %.2f %n", monitorWidth);
		System.out.printf("window height = %d %n", windowHeight);
		System.out.printf("window width = %d %n", windowWidth);
		
		ImageIcon image = new ImageIcon("../images/imgJoseFigueres.png");

		JLabel character = new JLabel("Adivina el personaje:", image, JLabel.CENTER);

		// Coloca el Label arriba
		character.setVerticalAlignment(JLabel.TOP);

		// Coloca el texto encima de la imagen
		character.setVerticalTextPosition(JLabel.TOP);
		character.setHorizontalTextPosition(JLabel.CENTER);

		// Añade el Label al Frame
		this.add(character);
	}
   
	/**
	* 
	*
	*/
	private void displayTextFieldView()
	{
		JTextField textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.TRAILING);
		this.add(textField);
		}
	}

	private int textLength = 30;
	private int windowWidth = 640;
	private int windowLength = 480;

	public MainWindow()
	{
	super("Guess The Character");
	this.setSize(windowWidth, windowLength);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	BorderLayout mainLayout = new BorderLayout();
	this.setLayout(mainLayout);

	this.displayElements();
   }

   /**
    * 
    *
    */
   private void displayElements()
   {
		ImageIcon image = new ImageIcon("../images/imgSpiderman.png");
		JLabel character = new JLabel(image, JLabel.CENTER);

		JPanel levelIndicator = new JPanel();
		levelIndicator.setLayout(new BorderLayout());
		JLabel level = new JLabel("LEVEL 1");
		Font font = new Font("Sans", Font.ITALIC, 20);
		level.setFont( font );
		level.setHorizontalAlignment(JLabel.CENTER);
		levelIndicator.add(level);

		JTextField text = new JTextField(10);
		text.setHorizontalAlignment(JTextField.CENTER);







		// Añade los elementos al Frame
		this.add(character, BorderLayout.CENTER);
		this.add(level, BorderLayout.NORTH);
		this.add(text, BorderLayout.SOUTH);

   }
   
 
}
