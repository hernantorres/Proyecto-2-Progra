/**
 * 
 *
 */
public class GuessTheCharacter
{
 
   private MainWindow mainWindow = null;

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      GuessTheCharacter guessTheCharacter = new GuessTheCharacter();
      guessTheCharacter.run();
   }
   
   public void run()
   {
      this.mainWindow = new MainWindow();
      this.mainWindow.setVisible(true);
   }
 
}