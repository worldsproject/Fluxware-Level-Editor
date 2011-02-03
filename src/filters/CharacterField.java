package filters;

import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * This is an extension of a JTextField that ONLY 
 * allows characters to be entered in.  The
 * characters that are entered in are to be entered
 * in the UTF format.  EX: \\uXXXX
 * 
 * @author Tim Butram
 *
 */
public class CharacterField extends JTextField
{	 
	/**
	 * Creates a new default CharacterField.
	 */
	public CharacterField()
	{
		super();
	}
	
	/**
	 * Creates a new CharacterField of width <i>cols</i>.
	 * 
	 * @param cols - The width of the CharacterField.
	 */
	public CharacterField(int cols)
	{
		super(cols);
	}
	
	/**
	 * Creates a new CharacterField with default text.
	 * Even if the given text includes non-characters
	 * they will not display.
	 * @param text
	 */
	public CharacterField(String text)
	{
		super(text);
	}

	protected Document createDefaultModel() 
	{
		return new CharacterDocument();
	}

	static class CharacterDocument extends PlainDocument 
	{
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException 
		{

			if (str == null) 
			{
				return;
			}
			
			char[] upper = str.toCharArray();
			ArrayList<Character> list = new ArrayList<Character>();
			
			for (int i = 0; i < upper.length; i++) 
			{
				Character temp = upper[i];
				
				if((offs + i) > 5)
				{
					continue;
				}
				
				if(Character.isDigit(upper[i]))
				{
					if((offs + i) > 1)
						list.add(upper[i]);
				}
				else if(upper[i] == 'a' || upper[i] == 'b' || upper[i] == 'c' || upper[i] == 'd' || upper[i] == 'e' || upper[i] == 'f')
				{
					if((offs + i) > 1)
						list.add(upper[i]);
				}
				else if(upper[i] == 'A' || upper[i] == 'B' || upper[i] == 'C' || upper[i] == 'D' || upper[i] == 'E' || upper[i] == 'F')
				{
					if((offs + i) > 1)
						list.add(upper[i]);
				}
				else if(upper[i] == '\\')
				{
					if((offs + i) == 0)
						list.add(upper[i]);
				}
				else if(upper[i] == 'u')
				{
					if((offs + i) == 1)
						list.add(upper[i]);
				}
			}
			
			char[] rValue = new char[list.size()];
			
			for(int i = 0; i < list.size(); i++)
			{
				rValue[i] = list.get(i);
			}
			
			super.insertString(offs, new String(rValue), a);
			
		}
	}
}
