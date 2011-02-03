package tests;

import gui.Render;
import test.room.LargeRoomKeyboard;

public class RenderTest 
{
	public static void main(String args[])
	{
		LargeRoomKeyboard lkb = new LargeRoomKeyboard();
		
		Render r = new Render(lkb);
	}
}
