package dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import level.Room;
import level.TiledRoom;
import filters.NumberField;

public class NewDialog extends JDialog implements ActionListener
{
	//Sets which of the 3 states the window should be displaying.
	private final int FIRST = 0;
	private final int SECOND = 1;
	private final int THIRD = 2;
	private int position = FIRST;
	
	//Navigation, for moving forwards and backwards in the menu.
	private JButton back = new JButton("Back");
	private JButton forward = new JButton("Forward");
	private JButton finish = new JButton("Finish");
	
	//Components in the first state.
	private JLabel which = new JLabel("What kind of level do you want to make?");
	private JButton plain = new JButton("Plain");
	private JButton tiled = new JButton("Tiled");
	
	//Components in the second state.
	private JLabel title = new JLabel("Room Details");
	private JLabel width = new JLabel("Width");
	private JLabel height = new JLabel("Height");
	private JLabel layers = new JLabel("Layers");
	private NumberField widthField = new NumberField();
	private NumberField heightField = new NumberField();
	private NumberField layerField = new NumberField();
	
	//Components in the third state.
	private JLabel allSet = new JLabel("You're all set!");
	
	//General components
	JPanel root = null;
	
	//Return components
	private Room room = null;
	private boolean plainRoom = false;
	private boolean isFinished = false;
	
	public NewDialog(JFrame center)
	{
		super(center, true);
		
		BufferedImage image = null;

		String q = "/resources/new.png";
		String s = this.getClass().getResource(q).getPath();
		s = s.replaceAll("%20", " ");

		try
		{
			image = ImageIO.read(new File(s));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		this.setIconImage(image);
		
		this.setTitle("New Level");
		root = (JPanel)this.getContentPane();
		
		//Set the size of the root panel so that the dialog isn't shifting sizes all the time.
		Dimension size = new Dimension(250, 125);
		root.setMinimumSize(size);
		root.setPreferredSize(size);
		root.setMaximumSize(size);
		
		//Setting JLabel properties.
		which.setHorizontalAlignment(JLabel.CENTER);
		title.setHorizontalAlignment(JLabel.CENTER);
		width.setHorizontalAlignment(JLabel.CENTER);
		height.setHorizontalAlignment(JLabel.CENTER);
		layers.setHorizontalAlignment(JLabel.CENTER);
		allSet.setHorizontalAlignment(JLabel.CENTER);
		allSet.setFont(new Font("Serif", Font.PLAIN, 20));
		
		
		//Create a border so there is some spacing between the outer edges and the text.
		root.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		
		changeDialog(FIRST);
		
		//Adding the ActionListeners.
		back.addActionListener(this);
		forward.addActionListener(this);
		plain.addActionListener(this);
		tiled.addActionListener(this);
		finish.addActionListener(this);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(center);
		
		changeDialog(FIRST);

	}
	
	private void changeDialog(int w)
	{
		root.removeAll();
		position = w;
		
		if(w == SECOND)
		{
			JPanel middle = new JPanel(new GridLayout(3, 4));
			middle.add(new JLabel(" "));
			middle.add(width);
			middle.add(widthField);
			middle.add(new JLabel(" "));
			middle.add(new JLabel(" "));
			middle.add(height);
			middle.add(heightField);
			middle.add(new JLabel(" "));
			middle.add(new JLabel(" "));
			middle.add(layers);
			middle.add(layerField);
			middle.add(new JLabel(" "));
			
			root.add(title, BorderLayout.NORTH);
			root.add(middle, BorderLayout.CENTER);
			root.add(bottomNav(false, false), BorderLayout.SOUTH);
		}
		else if(w == THIRD)
		{		
			root.add(allSet, BorderLayout.CENTER);
			root.add(bottomNav(true, false), BorderLayout.SOUTH);
		}
		else  //First, which is the default.
		{
			JPanel middle = new JPanel(new GridLayout(2, 1, 0, 3));
			middle.add(plain);
			middle.add(tiled);
			
			root.add(which, BorderLayout.NORTH);
			root.add(middle, BorderLayout.CENTER);
			root.add(new JLabel(" "), BorderLayout.SOUTH);
		}

		this.pack();
		this.repaint();
	}
	
	private JPanel bottomNav(boolean finished, boolean front)
	{
		JPanel rv = new JPanel(new FlowLayout(FlowLayout.CENTER));
		rv.add(back);
		
		if(finished)
		{
			rv.add(finish);
		}
		else
		{
			rv.add(forward);
		}
		
		return rv;
	}

	public Room getRoom()
	{
		this.setVisible(true);
		while(!isFinished);
		return room;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		
		if(o == back)
		{
			changeDialog(--position);
		}
		else if(o == forward)
		{
			changeDialog(++position);
		}
		else if(o == plain)
		{
			plainRoom = true;
			changeDialog(++position);
		}
		else if(o == tiled)
		{
			plainRoom = false;
			changeDialog(++position);
		}
		else if(o == finish)
		{
			int w = widthField.getInt();
			int h = heightField.getInt();
			int l = layerField.getInt();
			
			if(plainRoom)
			{
				room = new Room(w, h, l);
			}
			else
			{
				room = new TiledRoom(w, h, l);
			}
			
			this.dispose();
			
			isFinished = true;
		}
	}
}
