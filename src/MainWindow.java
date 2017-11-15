import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
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

      this.displayImage();
      //this.displayTextField();
   }

   /**
    * 
    *
    */
   private void displayImage()
   {
      ImageIcon image = new ImageIcon("../images/Spiderman.png");

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
   private void displayTextField()
   {
	   JTextField textField = new JTextField(textLength);
	   
	   JPanel jp = new JPanel();
	   JLabel jl = new JLabel();
	   
	   jp.add(textField);
	   this.add(jp);
	   
   }
 
}