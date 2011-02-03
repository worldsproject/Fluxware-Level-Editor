package gui;

import error.CrashReport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Constructor;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import sprites.Sprite;

public class SpritePanel extends JPanel 
{
	public JList items = new JList(new DefaultListModel());

	public SpritePanel(GUI g)
	{
		super(new BorderLayout());

		items.setCellRenderer(new FWJListRenderer());
		items.addMouseListener(g);


		JScrollPane pane = new JScrollPane(items);
		this.add(pane, BorderLayout.CENTER);
	}

	public void addSprite(Sprite sprite)
	{
		((DefaultListModel)items.getModel()).addElement(sprite);
	}

	public Sprite getSelectedSprite()
	{
		Sprite sprite = (Sprite)items.getSelectedValue();
		
		if(sprite == null)
			return null;
		
		Sprite s = null;

		try
		{	
			Constructor c = sprite.getClass().getConstructor();

			s = (Sprite)c.newInstance();

			s.setSprite(sprite.print());
			s.setX(sprite.getX());
			s.setY(sprite.getY());
			s.setLayer(sprite.getLayer());
		}
		catch(Exception e)
		{
			new CrashReport(e);
		}
		
		return s;
	}
	
	public void removeSprite(Sprite sprite)
	{
		((DefaultListModel)items.getModel()).removeElement(sprite);
	}

	private class FWJListRenderer implements ListCellRenderer
	{
		DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList list, Object item, int index, boolean isSelected, boolean hasFocus) 
		{
			Color theForeground = list.getForeground();
			Icon theIcon = null;
			String theText = "";

			JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, item, index, isSelected, hasFocus);

			if (item instanceof Sprite) 
			{
				Sprite s = (Sprite)item;

				theIcon = new ImageIcon(s.print());

				String str = s.getClass().getName();
				theText = str.substring(str.lastIndexOf(".") + 1);
			}

			if (!isSelected) 
			{
				renderer.setForeground(theForeground);
			}

			if (theIcon != null) 
			{
				renderer.setIcon(theIcon);
			}

			renderer.setText(theText);

			return renderer;
		}

	}
}
