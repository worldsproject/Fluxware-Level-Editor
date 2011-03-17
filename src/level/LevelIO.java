package level;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	public void LevelIO(File io)
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
	public String addPlainSprites(Sprite[] sprites)
	{	
		byte[] buf = new byte[1024];
		
		try
		{
			zip.putNextEntry(new ZipEntry("images/")); //Open the entry for the images dir.
			
			for (int i = 0; i < sprites.length; i++) //Now we iterate through all of the given sprites.
			{
				File temp = new File("" + imageCount++ + ".png"); //Create a temp image.
				temp.deleteOnExit(); //Make sure to delete this image when we are done.
				
				ImageIO.write(sprites[i].print(), "png", temp); //Write out the image to the temp file.
				
				FileInputStream in = new FileInputStream(temp); //The stream to write into the Zip.
				
				zip.putNextEntry(new ZipEntry("images/" + temp.getName())); //Open the entry for the image.
				
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
		return null;
	}
	
	
}
