package level;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sprites.Sprite;

import com.sun.org.apache.xml.internal.security.utils.Base64;

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
			
			for(String str: sprites)
			{
				ob = (JSONObject)parser.parse(str);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode((String)ob.get("sprite64")));
				ObjectInputStream ois = new ObjectInputStream(bais);
				Sprite s = (Sprite)ois.readObject();
				System.out.println(s);
				System.exit(3);
				rv.addSprite(s);
			}
			
			return rv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void RoomToJSON(Room room, File f)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(f);
            ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(fos));
            
			ArrayList<String> output = new ArrayList<String>(100);
			
			JSONObject obj = new JSONObject();
			obj.put("version", "0.1");
			
			output.add(obj.toString());
			
			obj = new JSONObject();
			
			obj.put("height", room.height);
			obj.put("layers", room.layers);
			obj.put("width", room.width);
			
			if(room instanceof TiledRoom)
			{
				obj.put("type", "TiledRoom");
			}
			else
			{
				obj.put("type", "Room");
			}
			
			output.add(obj.toString());
			
			LinkedList<Sprite> sprites = room.getSprites();
			
			ByteArrayOutputStream baos = null;
			ObjectOutputStream oos = null;
			
			try
			{
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int i = 0;
			
			for(Sprite s: sprites)
			{
				//If it's a PNG type Sprite.
				obj = new JSONObject();
				obj.put("name", s.getClass().getName());
				obj.put("x", s.getX());
				obj.put("y", s.getY());
				obj.put("layer", s.getLayer());
				
				File temp = new File("" + i++ + ".png");
				temp.deleteOnExit();
				
				ImageIO.write(s.print(), "png", temp);
				
				
			}
			
			try
			{
				PrintWriter out = new PrintWriter(new FileWriter(f));
				
				for(String s: output)
				{
					out.println(s);
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
