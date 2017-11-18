import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Font;
 
/**
 * Class that creates the main wondow for the game. Extends JFrame.
 * This object displays the image and text field where the game takes place.
 */
public class MainWindow extends JFrame
{

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