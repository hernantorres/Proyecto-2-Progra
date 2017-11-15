import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

 
/**
 * 
 *
 */
public class MainWindow extends JFrame
{
 
   public MainWindow()
   {
      super("Guess The Character");
      this.setSize(640, 480);
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
      ImageIcon image = new ImageIcon("../spiderman.png");

      JLabel character = new JLabel("Adivina el personaje:", image, JLabel.CENTER);

      // Coloca el Label arriba
      character.setVerticalAlignment(JLabel.TOP);

      // Coloca el texto encima de la imagen
      character.setVerticalTextPosition(JLabel.TOP);
      character.setHorizontalTextPosition(JLabel.CENTER);

      // Añade el Label al Frame
      this.add(character);
   }
 
}