import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
/**
 * Class that creates the main wondow for the game. Extends JFrame.
 * This object displays the image and text field where the game takes place.
 */
public class MainWindow extends JFrame
{
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

	/** Constructor */
	public MainWindow()
	{
		super( gameName );
		this.setSize(windowWidth, windowHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation((int)(monitorWidth/2 - this.windowWidth/2), (int)(monitorHeight/2 - this.windowHeight/2) );
		BorderLayout mainLayout = new BorderLayout();
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
		ImageIcon image = new ImageIcon( "../assets/images/louisCK.png");
		JLabel imageView = new JLabel(image);
		// String imagePathName = "../assets/images/louisCK.png";
		// ImagePanel imagePanel = new ImagePanel( imagePathName );
		JLabel timerView = new JLabel("00:01", JLabel.CENTER);
		

		// JPanel levelIndicator = new JPanel();
		// levelIndicator.setLayout(new BorderLayout());
		JLabel currentLevelView = new JLabel("LEVEL 1");
		Font font = new Font("Sans", Font.ITALIC, 20);
		currentLevelView.setFont( font );
		currentLevelView.setHorizontalAlignment(JLabel.CENTER);
		// levelIndicator.add(level);

		JTextField text = new JTextField(10);
		text.setFont(font);
		JPanel entryPanel = new JPanel();
		entryPanel.add(text);
		JPanel southPanel = new JPanel( new GridLayout(2,1));
		southPanel.add(timerView);
		southPanel.add(entryPanel);

		// Añade los elementos al Frame
		this.add(currentLevelView, BorderLayout.NORTH);
		// this.add(imagePanel);
		this.add(imageView, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);

	}
}
