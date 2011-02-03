package tests;


import java.io.File;
import java.util.LinkedList;

import level.Room;
import sprites.Sprite;
import test.room.RandomSprite;
import util.LevelConversions;

public class RoomSaveAndLoad 
{
	public static void main(String args[])
	{
		Room r = new Room(500, 500, 1);
		
		for(int i = 0; i < 10; i++)
		{
			r.addSprite(new RandomSprite(250, 250));
		}
		
		LevelConversions.RoomToJSON(r, new File("bwahah.txt"));
		
		Room x = LevelConversions.JSONToRoom(new File("bwahah.txt"));
		
		System.out.println(x);
		LinkedList<Sprite> as = x.getSprites();
		
		System.out.println(as.size());
	}
}
