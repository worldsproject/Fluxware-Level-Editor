package level;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import sprites.Sprite;

/**
 * This class handles all of the lower level implementation of reading
 * and writing to text files.
 * 
 * @author Tim Butram
 *
 */
public class LevelIO
{	
	private int imageCount = 0;
	
    private ZipOutputStream zip = null;
	
	public LevelIO(File io)
	{
		try
		{
			zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(io)));
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds all the plain Sprites to the Zip directory. This will place the images
	 * into the images sub-directory of the level file. It will then return
	 * the names of all the images, in the order that was given.
	 * 
	 * @param s - The Sprites to be added.
	 * @return A string name of the image files.
	 */
	public LinkedList<String> addPlainSprites(LinkedList<Sprite> sprites)
	{	
		byte[] buf = new byte[1024];
		LinkedList<String> rv = new LinkedList<String>();
		
		try
		{
			zip.putNextEntry(new ZipEntry("images/")); //Open the entry for the images dir.
			
			for (Sprite s: sprites) //Now we iterate through all of the given sprites.
			{
				File temp = new File("" + imageCount++ + ".png"); //Create a temp image.
				temp.deleteOnExit(); //Make sure to delete this image when we are done.
				
				ImageIO.write(s.print(), "png", temp); //Write out the image to the temp file.
				
				FileInputStream in = new FileInputStream(temp); //The stream to write into the Zip.
				
				zip.putNextEntry(new ZipEntry("images/" + temp.getName())); //Open the entry for the image.
				rv.add(temp.getName());
				
				//Write the image.
				int len;				
			    while ((len = in.read(buf)) > 0)
			    {
			        zip.write(buf, 0, len);
			    }
			    
			    zip.closeEntry();//Close the image entry.
			}
			
			zip.closeEntry(); //Close the image dir entry.
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rv;
	}
	
	public void addLevelFile(LinkedList<String> output)
	{
		try
		{
			File temp = new File("level.txt");
			temp.deleteOnExit();
			
			PrintWriter out = new PrintWriter(new FileWriter(temp));
			
			for(String s: output)
			{
				out.println(s);
			}
			
			zip.putNextEntry(new ZipEntry("level.txt")); //Open the entry for the image.
			
			//Write the image.
			int len;
			byte[] buf = new byte[1024];
			FileInputStream in = new FileInputStream(temp); //The stream to write into the Zip.
			
		    while ((len = in.read(buf)) > 0)
		    {
		        zip.write(buf, 0, len);
		    }
		    
		    zip.closeEntry();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finish()
	{
		try
		{
			zip.flush();
			zip.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
