import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Scanner;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridLayout;
/**
 * Class that creates the main wondow for the game. Extends JFrame.
 * This object displays the image and text field where the game takes place.
 */
public class MainWindow extends JFrame
{
	/*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets( getGraphicsConfiguration() );
	int taskBarSize = scnMax.bottom;
	double monitorWidth = screenSize.getWidth();
	double monitorHeight = screenSize.getHeight() - (double)taskBarSize;
	
	private static int desiredHeightRatio = 20;
	private static int desiredWidthRatio = 15;
	
	private int windowHeight = (int)Math.floor( monitorHeight / desiredHeightRatio ) * desiredHeightRatio;
	private int windowWidth = ( windowHeight * desiredWidthRatio ) / desiredHeightRatio;*/
	
	private static int textLength = 30;
	private static String gameName = "Finite Sentence Machine";

	/** Constructor */
	public MainWindow()
	{
		super( gameName );
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLocation((int)(monitorWidth/2 - this.windowWidth/2), (int)(monitorHeight/2 - this.windowHeight/2) );
		GridLayout mainLayout = new GridLayout(4, 1);
		//mainLayout.setHgap(100);
		//mainLayout.setVgap(100);
		this.setLayout(mainLayout);
		displayElements();
	}
	
 

	/**
	* 
	*/
	/*private void displayImageView()
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
	}*/

   /** 
    *
    */
	private void displayElements()
	{
		ImageIcon image = new ImageIcon("../assets/images/imgSpiderman.png");
		JLabel character = new JLabel(image);
		// JPanel center = new JPanel();
		// center.setLayout(new GridLayout(2,1));
		JLabel cronometro = new JLabel("00:01", JLabel.CENTER);
		// center.add(character);
		// center.add(cronometro);
		

		// JPanel levelIndicator = new JPanel();
		// levelIndicator.setLayout(new BorderLayout());
		JLabel level = new JLabel("LEVEL 1");
		Font font = new Font("Sans", Font.ITALIC, 20);
		level.setFont( font );
		level.setHorizontalAlignment(JLabel.CENTER);
		// levelIndicator.add(level);

		JTextField text = new JTextField(10);
		text.setFont(font);
		JPanel entryField = new JPanel();
		//texto.setSize(100,100);
		entryField.add(text);

		// Añade los elementos al Frame
		this.add(level);
		this.add(character);
		this.add(cronometro);
		this.add(entryField);

	}
}
