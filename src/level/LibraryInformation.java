package level;

import java.util.ArrayList;

import sprites.Sprite;

public class LibraryInformation 
{
	private ArrayList<Sprite> list = new ArrayList<Sprite>(20);
	
	public LibraryInformation()
	{
		
	}
	
	public void addSprite(Sprite s)
	{
		list.add(s);
	}
	
	public void removeSprite(Sprite s)
	{
		list.remove(s);
	}
	
	public void removeSprite(int index)
	{
		list.remove(index);
	}
	
	public ArrayList<Sprite> getList()
	{
		return list;
	}
	
	public void setList(ArrayList<Sprite> l)
	{
		list = l;
	}
	
	public void saveList()
	{
		
	}
	
	public void loadList()
	{
		
	}
}
