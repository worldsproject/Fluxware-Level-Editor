package errors;

import javax.swing.JOptionPane;


public class NoRoomException extends LevelEditorException
{

	@Override
	public String proposeSolution()
	{
		JOptionPane.showMessageDialog(null, "Defaulting to a room with 1 layer and 500px wide and tall.", "No Room Detected", JOptionPane.ERROR_MESSAGE, null);
		return "{\"height\":500,\"layers\":1,\"width\":500,\"type\":\"Room\"}";
	}

}
