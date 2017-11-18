/**
* 
*
*/
public class FSM
{

	private MainWindow mainWindow = null;

	/**
	* @param args
	*/
	public static void main(String[] args)
	{
		FSM guessTheCharacter = new FSM();
		guessTheCharacter.run();
	}

	public void run()
	{
		this.mainWindow = new MainWindow();
		this.mainWindow.setVisible(true);
	}
}