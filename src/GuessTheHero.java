/**
 * 
 *
 */
public class GuessTheHero
{
 
   private MainWindow mainWindow = null;

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      GuessTheHero guessTheHero = new GuessTheHero();
      guessTheHero.run();
   }
   
   public void run()
   {
      this.mainWindow = new MainWindow();
      this.mainWindow.setVisible(true);
   }
 
}