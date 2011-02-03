package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import level.Room;
import sprites.Sprite;
import error.CrashReport;

/**
 * This class handles the details of creating and showing a png representation of
 * what the level currently looks like.
 * @author Tim Butram
 *
 */
public class Render extends JFrame implements ActionListener
{
	private JMenuBar menu = new JMenuBar();
	
	private JMenu File = new JMenu("File");
	
	private JMenuItem Save = new JMenuItem("Save");
	private JMenuItem Close = new JMenuItem("Close");
	
	private Room r = null;
	
	private BufferedImage image = null;
	
	private BufferedImage saveImage = null;
	
	public Render(Room r)
	{
		super("Rendering Your Level...");
		
		this.r = r;
		
		image = createImage();
		
		ImageIcon i = new ImageIcon(image);
		JLabel label = new JLabel(i);
		
		setupMenuBar();
		
		this.setJMenuBar(menu);
		
		JPanel root = (JPanel)this.getContentPane();
		
		JScrollPane sp = new JScrollPane(label);
		
		root.add(sp);

		String q = "/resources/save.png";
		String s = this.getClass().getResource(q).getPath();
		s = s.replaceAll("%20", " ");
		
		String q1 = "/resources/render.png";
		String s1 = this.getClass().getResource(q1).getPath();
		s1 = s1.replaceAll("%20", " ");
		
		BufferedImage renderImage = null;

		try
		{
			saveImage = ImageIO.read(new File(s));
			renderImage = ImageIO.read(new File(s1));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		this.setIconImage(renderImage);
		
		this.setSize(new Dimension(600, 600));
		this.setVisible(true);
	}
	
	private void setupMenuBar()
	{
		//Add the ActionListeners to the MenuItems.
		Save.addActionListener(this);
		Close.addActionListener(this);
		
		//Add the Mnemonics to the MenuItems.
		Save.setMnemonic(KeyEvent.VK_S);
		Close.setMnemonic(KeyEvent.VK_C);
		
		//Add the Keyboard Accelerators to the Menu stuff.
		Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		Close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		File.add(Save);
		File.addSeparator();
		File.add(Close);
		
		menu.add(File);
	}
	
	private BufferedImage createImage()
	{
		BufferedImage rv = new BufferedImage(r.getWidth(), r.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics g = rv.getGraphics();
		
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, r.getWidth(), r.getHeight());
		
		LinkedList<Sprite> as = r.getSprites();
		
		for(Sprite s : as)
		{
			g.drawImage(s.print(), s.getX(), s.getY(), null);
		}
		
		return rv;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		
		if(o == Save)
		{
			JFileChooser jfc = new JFileChooser();
			
			jfc.setApproveButtonMnemonic(KeyEvent.VK_ENTER);
			int which = jfc.showSaveDialog(this);
			
			if(which == JFileChooser.APPROVE_OPTION)
			{
				try 
				{
					ImageIO.write(image, "png", jfc.getSelectedFile());
				} catch (IOException e1) 
				{
					new CrashReport(e1);
				}
			}
		}
		else if(o == Close)
		{
			this.dispose();
		}
	}
}
