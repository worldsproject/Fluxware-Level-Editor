package dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sprites.Sprite;
import filters.ImageFilter;

public class NewSprite extends JDialog implements ActionListener
{
	private JButton PNGImage = new JButton("PNG");
	private JButton FluxwareSprite = new JButton("Fluxware Sprite");
	
	private JButton accept = new JButton("Import");
	private JButton restart = new JButton("Restart");
	
	private JLabel preview = new JLabel();
	
	private JPanel root;

	//This is the return stuff.
	private BufferedImage returnImage = null;
	private Sprite returnSprite = null;

	private boolean finished = false;

	public NewSprite(JFrame center)
	{
		super(center, true);

		this.setTitle("New Sprite");

		root = (JPanel)this.getContentPane();
		root.setLayout(new BorderLayout());

		Dimension size = new Dimension(250, 125);

		root.setMinimumSize(size);
		root.setMaximumSize(size);
		root.setPreferredSize(size);

		root.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		PNGImage.addActionListener(this);
		FluxwareSprite.addActionListener(this);
		accept.addActionListener(this);
		restart.addActionListener(this);

		FluxwareSprite.setEnabled(false);

		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(
			    new WindowAdapter() 
			    {
			        public void windowClosing(WindowEvent e) 
			        {
			            finished = true;
			            exit();
			        }
			    }
			);
		
		this.setLocationRelativeTo(center);
		changeDialog(true);
	}

	private void changeDialog(boolean firstScreen)
	{
		root.removeAll();

		if(firstScreen)
		{
			root.add(PNGImage, BorderLayout.NORTH);
			root.add(FluxwareSprite, BorderLayout.SOUTH);
		}
		else
		{
			BufferedImage prevImage = new BufferedImage(100, 100, returnImage.getType());
			AffineTransform at = AffineTransform.getScaleInstance((double)75/returnImage.getWidth(), (double)75/returnImage.getHeight());
			((Graphics2D)prevImage.getGraphics()).drawRenderedImage(returnImage, at);
			
			preview.setIcon(new ImageIcon(prevImage));
			preview.setHorizontalAlignment(JLabel.CENTER);
			root.add(preview, BorderLayout.NORTH);

			JPanel buttonHolder = new JPanel();
			buttonHolder.setLayout(new FlowLayout());

			buttonHolder.add(accept);
			buttonHolder.add(restart);

			root.add(buttonHolder, BorderLayout.SOUTH);
		}
		
		this.pack();
		this.repaint();
	}

	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();

		if(o == PNGImage)
		{
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new ImageFilter());

			int choice = jfc.showOpenDialog(null);

			if(choice == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					returnImage = ImageIO.read(jfc.getSelectedFile());
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				changeDialog(false); //This sets it to the second screen.
			}
		}
		else if(o == FluxwareSprite)
		{
			//This stuff hasn't been implemented yet anyways, so it will be blank.
			//Besides, the button is grayed out anyways.
		}
		else if(o == accept)
		{
			returnSprite = new Sprite(returnImage, 0, 0, 0);
			this.dispose();
			finished = true;
			return;
		}
		else if(o == restart)
		{
			changeDialog(true); //This sets it to the first screen.
		}
	}
	
	public void exit()
	{
		this.dispose();
	}

	public Sprite getSprite()
	{
		this.setVisible(true);
		while(!finished);
		this.setVisible(false);
		return returnSprite;
	}
}
