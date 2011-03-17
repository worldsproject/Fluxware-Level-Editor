package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager;

import level.Room;
import listener.bounding.BoundingBox;
import sprites.Sprite;
import undo.UndoableSprite;
import util.LevelConversions;
import util.Point2D;
import collision.Collision;
import dialogs.NewDialog;
import dialogs.NewSprite;

public class GUI extends Game implements ActionListener, MouseListener
{	
	private Room room = new Room(600, 600, 2);

	//Create the MenuItems in the File menu.
	private JMenuItem news = new JMenuItem("New...");
	private JMenuItem open = new JMenuItem("Open...");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem saveAs = new JMenuItem("Save As...");
	private JMenuItem render = new JMenuItem("Render");
	private JMenuItem exit = new JMenuItem("Exit");

	//Create the MenuItems in the Edit menu.
	private JMenuItem roomDim = new JMenuItem("Room Dimensions");
	private JMenuItem undo = new JMenuItem("Undo");
	private JMenuItem redo = new JMenuItem("Redo");
	private JMenuItem preferences = new JMenuItem("Preferences...");

	//Create the MenuItems in the Sprites menu.
	private JMenuItem addSprite = new JMenuItem("Add Sprite");
	private JMenuItem removeSprite = new JMenuItem("Remove Sprite");
	private JMenuItem saveSpriteLibrary = new JMenuItem("Save Sprite Library");
	private JMenuItem loadSpriteLibrary = new JMenuItem("Load Sprite Library");

	//Creates the MenuItems in the Help menu.
	private JMenuItem tutorial = new JMenuItem("Tutorial");
	private JMenuItem help = new JMenuItem("Help");
	private JMenuItem about = new JMenuItem("About");

	//Items used to facilitate saving.
	private File saveLocation = null;

	//Sprite Panel, and dragging variables.
	private SpritePanel spritePanel;
	private Sprite dragged = null;
	private DragListener dl = new DragListener();
	private int xdiff = 0;
	private int ydiff = 0;
	private int initialXDiff = 0;
	private int initialYDiff = 0;

	//Right Click menu variable.s
	private JPopupMenu popup = new JPopupMenu("Sprite Options");
	private JMenuItem popRemoveSprite = new JMenuItem("Remove");
	private PopupListener pl = new PopupListener();
	
	//Undo items.
	private UndoManager undoManager = new UndoManager();
	private SpriteUndoActionListener ual = new SpriteUndoActionListener();

	public GUI()
	{
		setRoom(room);
		setupGUI();
		setupMenu();
		setupPopupMenu();
		run();
	}

	private void setupMenu()
	{
		JMenuBar menuBar = new JMenuBar(); //Create the JMenuBar

		JMenu File = new JMenu("File");  //Create the actual JMenus that we see.
		JMenu Edit = new JMenu("Edit");
		JMenu Sprites = new JMenu("Sprites");
		JMenu Help = new JMenu("Help");

		//Add the items to the File menu.
		File.add(news);
		File.add(open);
		File.add(save);
		File.add(saveAs);
		File.add(render);
		File.add(exit);

		//Add the items to the Edit menu.
		Edit.add(roomDim);
		Edit.add(undo);
		Edit.add(redo);
		Edit.add(preferences);

		//Add the items to the Sprites menu.
		Sprites.add(addSprite);
		Sprites.add(removeSprite);
		Sprites.add(saveSpriteLibrary);
		Sprites.add(loadSpriteLibrary);

		//Add the items to the Help menu.
		Help.add(tutorial);
		Help.add(help);
		Help.add(about);

		//Add all of the actionlisteners to the JMenuItems.
		news.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		saveAs.addActionListener(this);
		render.addActionListener(this);
		exit.addActionListener(this);

		roomDim.addActionListener(this);
		undo.addActionListener(ual);
		redo.addActionListener(ual);
		preferences.addActionListener(this);

		addSprite.addActionListener(this);
		removeSprite.addActionListener(this);
		saveSpriteLibrary.addActionListener(this);
		loadSpriteLibrary.addActionListener(this);

		tutorial.addActionListener(this);
		help.addActionListener(this);
		about.addActionListener(this);

		//Adding all of the Mnemonics
		news.setMnemonic(KeyEvent.VK_N);
		open.setMnemonic(KeyEvent.VK_O);
		save.setMnemonic(KeyEvent.VK_S);
		saveAs.setMnemonic(KeyEvent.VK_A);
		render.setMnemonic(KeyEvent.VK_R);
		exit.setMnemonic(KeyEvent.VK_X);

		roomDim.setMnemonic(KeyEvent.VK_D);
		undo.setMnemonic(KeyEvent.VK_U);
		redo.setMnemonic(KeyEvent.VK_R);
		preferences.setMnemonic(KeyEvent.VK_P);

		addSprite.setMnemonic(KeyEvent.VK_A);
		removeSprite.setMnemonic(KeyEvent.VK_R);
		saveSpriteLibrary.setMnemonic(KeyEvent.VK_S);
		loadSpriteLibrary.setMnemonic(KeyEvent.VK_L);

		tutorial.setMnemonic(KeyEvent.VK_T);
		help.setMnemonic(KeyEvent.VK_H);
		about.setMnemonic(KeyEvent.VK_A);

		//Adding all of the Keyboard Accelerators.
		news.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, (ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK)));
		render.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		roomDim.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

		addSprite.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		removeSprite.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		saveSpriteLibrary.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		loadSpriteLibrary.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));

		tutorial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		
		//Disabling undo and redo as they can't really do anything as of now.
		undo.setEnabled(false);
		redo.setEnabled(false);

		//Now we add the JMenu to the JMenuBar.
		menuBar.add(File);
		menuBar.add(Edit);
		menuBar.add(Sprites);
		menuBar.add(Help);

		//Now add the JMenuBar to the JFrame.
		this.setJMenuBar(menuBar);
	}

	protected void setupGUI()
	{
		this.setTitle("Fluxware Level Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setupDP(room);

		BufferedImage image = null;

		String q = "/resources/icon.png";
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

		root = (JPanel)this.getContentPane();
		root.setLayout(new BorderLayout());

		JScrollPane pane = new JScrollPane(dp);

		root.add(pane, BorderLayout.CENTER);

		spritePanel = new SpritePanel(this);

		root.add(spritePanel, BorderLayout.EAST);

		this.setSize(new Dimension(800, 640));

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		this.addKeyListener(room);
		dp.addMouseMotionListener(dl);
		dp.addMouseListener(dl);
		dp.addMouseListener(this);
	}

	private void setupPopupMenu()
	{
		popup.add(popRemoveSprite);

		spritePanel.items.addMouseListener(pl);
		dp.addMouseListener(pl);
		popRemoveSprite.addActionListener(pl);
	}

	private void save()
	{
		if(saveLocation != null)
		{
			LevelConversions.RoomToJSON(room, saveLocation);
		}
		else
		{
			JFileChooser jfc = new JFileChooser();

			int which = jfc.showSaveDialog(this);

			if(which == JFileChooser.APPROVE_OPTION)
			{
				saveLocation = jfc.getSelectedFile();

				level.LevelConversions.RoomToJSON(room, saveLocation);
			}
		}
	}

	private void open()  //TODO Warning, does not check to see if a file has been edited.
	{
		File loadLocation = null;

		JFileChooser jfc = new JFileChooser();

		int which = jfc.showOpenDialog(this);

		if(which == JFileChooser.APPROVE_OPTION)
		{
			
			loadLocation = jfc.getSelectedFile();

			Room r = level.LevelConversions.JSONToRoom(loadLocation);
			this.setRoom(r);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();

		if(o == news)
		{
			NewDialog nd = new NewDialog(this);
			Room r = nd.getRoom();
			this.setRoom(r);
		}
		else if(o == open)
		{
			open();
		}
		else if( o == save)
		{
			save();
		}
		else if(o == saveAs)
		{
			saveLocation = null;
			save();
		}
		else if(o == render)
		{
			new Render(room);
		}
		else if(o == exit)
		{
			int which = JOptionPane.showConfirmDialog(this, "Would you like to save before quitting?", "Save?", JOptionPane.YES_NO_CANCEL_OPTION);

			if(which == JOptionPane.OK_OPTION)
			{
				save();
				System.exit(0);
			}
			else if(which == JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
		}
		else if(o == addSprite)
		{
			NewSprite ns = new NewSprite(this);
			spritePanel.addSprite(ns.getSprite());
		}
		else if(o == removeSprite)
		{
			Sprite s = spritePanel.getSelectedSprite();

			spritePanel.removeSprite(s);
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getSource() == dp)
		{
			Sprite temp = new Sprite(new BufferedImage(10, 10, 10), e.getX(), e.getY(), 0);

			LinkedList<Sprite> li = Collision.hasCollided(temp, room.getSprites(), false);
			
			if(li.isEmpty() == false)
			{
				dragged = li.getLast();
				xdiff = e.getX() - dragged.getX();
				ydiff = e.getY() - dragged.getY();
			}
		}
	}
	
	public void MP(MouseEvent e)
	{
		this.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

		if(e.getSource() == spritePanel.items)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				Sprite s = spritePanel.getSelectedSprite();

				if(s != null)
				{
					room.addSprite(s);
					undoManager.undoableEditHappened(new UndoableEditEvent(dp, new UndoableSprite(room, s)));
					
					undo.setText(undoManager.getUndoPresentationName());
			        redo.setText(undoManager.getRedoPresentationName());
					undo.setEnabled(undoManager.canUndo());
			        redo.setEnabled(undoManager.canRedo());
				}
			}
		}
	}

	private class PopupListener extends MouseAdapter implements ActionListener
	{
		private Object mouseSource = null;
		private Sprite temp = null;

		public void mouseReleased(MouseEvent e)
		{
			mouseSource = e.getSource();
			
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				if(e.getSource() == spritePanel.items)
				{
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
				
				Sprite temp = new Sprite(new BufferedImage(1, 1, 1), e.getX(), e.getY(), 0);
				LinkedList<Sprite> as = Collision.hasCollided(temp, room.getSprites(), false);

				if(as.isEmpty() == false)
				{
					temp = as.getLast();

					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{	
			Object o = e.getSource();

			if(o == popRemoveSprite)
			{
				if(mouseSource == spritePanel.items)
				{
					spritePanel.removeSprite(spritePanel.getSelectedSprite());
					return;
				}
				
				room.removeSprite(temp);
			}
		}
	}
	
	private class SpriteUndoActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
	        
			if(e.getSource() == undo)
			{
				if(undoManager.canUndo())
				{
					undoManager.undo();
				}
			}
			else if(e.getSource() == redo)
			{
				if(undoManager.canRedo())
				{
					undoManager.redo();
				}
			}
			
			undo.setText(undoManager.getUndoPresentationName());
	        redo.setText(undoManager.getRedoPresentationName());
			undo.setEnabled(undoManager.canUndo());
	        redo.setEnabled(undoManager.canRedo());
		}
		
	}
	
	private class DragListener extends MouseAdapter
	{
		private int xs = 0;
		private int ys = 0;
		
		public void mouseDragged(MouseEvent e) 
		{
			if(dragged != null)
			{
				dragged.setX(e.getX() - xdiff);
				dragged.setY(e.getY() - ydiff);
			}
		}
		
		public void mousePressed(MouseEvent e)
		{	
			MP(e);
			xs = e.getX() - xdiff;
			ys = e.getY() - ydiff;
		}
		
		public void mouseReleased(MouseEvent e)
		{
			undoManager.undoableEditHappened(new UndoableEditEvent(dp, new UndoableSpriteMovement(dragged, xs, ys, e.getX(), e.getY())));
			
			undo.setText(undoManager.getUndoPresentationName());
	        redo.setText(undoManager.getRedoPresentationName());
			undo.setEnabled(undoManager.canUndo());
	        redo.setEnabled(undoManager.canRedo());
		}
	}

	public static void main(String argsp[])
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch (Exception e1) 
		{
			try 
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				new GUI();
			}
		});
	}
}
