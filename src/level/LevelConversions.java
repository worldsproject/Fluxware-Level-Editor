package level;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import errors.NoRoomException;
import errors.NoVersionException;


public class LevelConversions 
{
	public static Room JSONToRoom(File f)
	{
		Room rv = null;
		
		try 
		{
			BufferedReader fr = new BufferedReader(new FileReader(f));
			
			String version = null;
			try
			{
				version = fr.readLine();
				
				if(version == null)
					throw new NoVersionException();
			}
			catch(NoVersionException ex)
			{
				version = ex.proposeSolution();
			}
			
			String room = null;
			
			try
			{
				room = fr.readLine();
				
				if(room == null)
					throw new NoRoomException();
			}
			catch(NoRoomException ex)
			{
				room = ex.proposeSolution();
			}
			
			LinkedList<String> sprites = new LinkedList<String>();
			String line = null;
			
			while((line = fr.readLine()) != null)
			{
				sprites.add(line);
			}
			
			JSONParser parser = new JSONParser();
			JSONObject ob = (JSONObject)parser.parse(room);
			
			String type = (String)ob.get("type");
			
			if(type.equals("Tiled Room"))
			{
				rv = new TiledRoom(((Long)ob.get("width")).intValue(), ((Long)ob.get("height")).intValue(), ((Long)ob.get("layers")).intValue());
			}
			else
			{
				rv = new Room(((Long)ob.get("width")).intValue(), ((Long)ob.get("height")).intValue(), ((Long)ob.get("layers")).intValue());
			}
		}
		catch (Exception e) 
		{
			//TODO
		}
	}
}
