package gui;

import javax.swing.undo.AbstractUndoableEdit;

import level.Room;
import sprites.Sprite;

public class UndoableSpriteMovement extends AbstractUndoableEdit 
{
	private Sprite sprite;
	
	private int xStart = 0;
	private int yStart = 0;
	private int xEnd = 0;
	private int yEnd = 0;
	
	public UndoableSpriteMovement(Sprite s, int xStart, int yStart, int xEnd, int yEnd)
	{
		sprite = s;
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}
	
	public String getPresentationName()
	{
		return "Sprite Movement";
	}
	
	public void undo()
	{
		sprite.setX(xStart);
		sprite.setY(yStart);
	}
	
	public void redo()
	{
		sprite.setX(xEnd);
		sprite.setY(yEnd);
	}
}
