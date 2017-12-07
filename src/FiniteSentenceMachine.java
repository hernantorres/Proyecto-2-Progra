/**
* 
*
*/
public class FiniteSentenceMachine
{

	private MainWindow mainWindow = null;

	/**
	* @param args
	*/
	public static void main(String[] args)
	{
		FiniteSentenceMachine guessTheCharacter = new FiniteSentenceMachine();
		guessTheCharacter.run();
	}

	public void run()
	{
		this.mainWindow = new MainWindow();
		this.mainWindow.setVisible(true);
	}
}