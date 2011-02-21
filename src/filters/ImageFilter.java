package filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * ImageFilter is a FileFileter that only allows images.
 * The only allowed images are "PNG"
 * Extensions are case INsensitive.
 * @author atrus
 *
 */
public class ImageFilter extends FileFilter 
{
    public boolean accept(File f) 
    {
        if (f.isDirectory()) 
        {
            return true;
        }

        String extension = f.getName().toLowerCase();
        
        if (extension != null) 
        {
            if (extension.endsWith(".png")) 
            {
            	return true;
            } 
            else 
            {
                return false;
            }
        }

        return false;
    }
    
    public String getDescription() 
    {
        return "Images of type: png";
    }
}
