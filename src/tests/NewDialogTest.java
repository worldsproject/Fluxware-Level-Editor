package tests;

import java.awt.Dimension;

import javax.swing.JFrame;

import dialogs.NewDialog;

public class NewDialogTest 
{
	public static void main(String args[])
	{
		JFrame frame = new JFrame("Test Frame");
		frame.setSize(new Dimension(600, 600));
		frame.setVisible(true);
		NewDialog nd = new NewDialog(frame);
		System.out.println(nd.getRoom());
	}
}
