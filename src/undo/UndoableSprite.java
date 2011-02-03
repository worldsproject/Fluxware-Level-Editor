package undo;

import javax.swing.undo.AbstractUndoableEdit;

import level.Room;
import sprites.Sprite;

public class UndoableSprite extends AbstractUndoableEdit 
{
	protected Room room;
	protected Sprite sprite;
	
	public UndoableSprite(Room r, Sprite s)
	{
		room = r;
		sprite = s;
	}
	
	public String getPresentationName()
	{
		return "Sprite Addition";
	}
	
	public void undo()
	{
		super.undo();
		
		room.removeSprite(sprite);
	}
	
	public void redo()
	{
		super.redo();
		
		room.addSprite(sprite);
	}
}
