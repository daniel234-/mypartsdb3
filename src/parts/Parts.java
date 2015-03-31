package parts;

//CS 4743 Assignment 5 by Daniel Gardner and Stephen Leija

import java.awt.Dimension;

import javax.swing.JFrame;

public class Parts 
{
	public static void main(String[] args) 
	{
		Dimension minimumSize = new Dimension(500, 300);
		AuthenticationView authView = new AuthenticationView();	
		UserModel userModel = new UserModel();
		Authenticator a = new Authenticator(userModel, authView);
		authView.registerListeners(a);
	
		authView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		authView.setSize(500, 300);
		authView.setMinimumSize(minimumSize);
		authView.setResizable(true);
		authView.setVisible(true);
	
	}
}