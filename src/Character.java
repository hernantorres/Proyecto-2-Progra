import javax.swing.ImageIcon;

public class Character {
	
	private ImageIcon image;
	
	public Character(String rute)
	{
		this.image = new ImageIcon(rute);
	}
	
	public ImageIcon getImage()
	{
		return this.image;
	}

}
