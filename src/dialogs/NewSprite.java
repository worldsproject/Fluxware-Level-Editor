package dialogs;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import filters.NumberField;

public class NewSprite extends JPanel 
{
	private JLabel mans = new JLabel("Make a new Sprite.");
	private JButton normal = new JButton("Normal");
	private JButton animated = new JButton("Animated");
	
	private JLabel win = new JLabel("What is the name of the Sprite."); 
	private JTextField spriteName = new JTextField("Sprite Name");
	
	private JLabel wdill = new JLabel("What does it look like?");
	private JLabel normalPreview = new JLabel("Sprite Preview");
	private JButton browse = new JButton("Browse");
	
	private NumberField widthField = new NumberField("4");
	private NumberField heightField = new NumberField("4");
	
	private JButton finish = new JButton("Finish");
	private JButton reset = new JButton("Reset");
	
	private JButton back = new JButton("Back");
	private JButton next = new JButton("Next");
	
	
	public NewSprite()
	{
		
	}
}
