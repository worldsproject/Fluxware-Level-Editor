package filters;

import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * The NumberField that only permits numbers to be entered in.
 * Numbers are determined by Character.isDigit(char);
 * 
 * @author Tim Butram
 *
 */
public class NumberField extends JTextField
{	 
	/**
	 * Creates a default NumberField.
	 */
	public NumberField()
	{
		super();
	}
	
	/**
	 * Creates a NumberField with a width of <i>cols</i>.
	 * 
	 * @param cols - The width of the NumberField.
	 */
	public NumberField(int cols)
	{
		super(cols);
	}
	
	/**
	 * Creates a NumberField with the default starting text.
	 * All non-digits will be stripped out.
	 * 
	 * @param text - Default text in a new NumberField.
	 */
	public NumberField(String text)
	{
		super(text);
	}

	protected Document createDefaultModel() 
	{
		return new NumberDocument();
	}

	private class NumberDocument extends PlainDocument 
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
				
				if(Character.isDigit(upper[i]))
				{
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
	
	public int getInt()
	{
		String s = this.getText();
		return Integer.parseInt(s);
	}
}
