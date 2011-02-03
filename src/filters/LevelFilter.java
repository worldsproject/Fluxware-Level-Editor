package filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * LevelFilter is a FileFilter that only permits
 * Fluxware Level Files (either .ZIP or .LVL).
 * Extensions are case INsensitive.
 * @author atrus
 *
 */
public class LevelFilter extends FileFilter 
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
            if (extension.endsWith(".zip") ||
                extension.endsWith(".lvl")) 
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
        return "Level Files.";
    }
}
