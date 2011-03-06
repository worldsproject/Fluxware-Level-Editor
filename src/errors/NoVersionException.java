package errors;

import javax.swing.JOptionPane;

public class NoVersionException extends LevelEditorException
{

	@Override
	public String proposeSolution()
	{
		JOptionPane.showMessageDialog(null, "No version has been detected, defaulting to 0.1", "No Version", JOptionPane.ERROR_MESSAGE, null);
		
		return "{\"version\":\"0.1\"}";
	}

}
