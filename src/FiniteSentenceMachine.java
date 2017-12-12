/**
* A game that shows you an image of a famous character, so you must guess his name,
* and type, within the given time (seconds)
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