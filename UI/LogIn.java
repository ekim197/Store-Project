package UI;

import java.util.Scanner;

import User.*;
import Modules.*; 

public class LogIn {
	
	private Scanner scanner = new Scanner(System.in);
	
	public boolean LogIn()
	{
		String uname, pass, ls;
		boolean isvalid = false; 
		boolean isemployee = false; 
		
		System.out.println("Press (1) if you need to sign up, press (2) if you want to log in");
		ls = scanner.next();
		if (ls.equals("1"))
		{
			System.out.println("**********Log In***********");
		}
		else if (ls.equals("2"))
		{
			System.out.println("**********Sign Up**********");
			SignUp();
		}
		else
		{
			System.out.println("Invalid input, Will assume you are to sign in");
		}
		
		while (!isvalid)
		{
			System.out.println("Enter your username: ");
			uname = scanner.next();
			System.out.println("Enter your password: ");
			pass = scanner.next();
			
			if (Client.verifyLogInInformation(uname, pass) || Server.verifyLogInInformation(uname, pass))
			{
				if (Client.verifyLogInInformation(uname, pass))
				{
					System.out.println("Welcome! ");
					isvalid = true;
				}
				else
				{
					System.out.println("Welcome! ");
					isvalid = true;
					isemployee = true; 
				}
			}
			else
			{
				System.out.println("Invalid Password, please retry! ");
				continue; 
			}
		}
		
		return isemployee;
	}
	
	public void SignUp()
	{
		String firstname, lastname, address, username, password, passwordconfirmation; 
		System.out.println("Firstname: " );
		firstname = scanner.next();
		System.out.println("Lastname/Family Name: ");
		lastname = scanner.next();
		System.out.println("Address: ");
		address = scanner.next();
		System.out.println("Username: ");
		username = scanner.next();
		System.out.println("Password: ");
		password = scanner.next();
		System.out.println("Confirm Password: ");
		passwordconfirmation = scanner.next();
		
		boolean isvalid = false;
		while(!isvalid)
		{
			if (password.equals(passwordconfirmation))
			{
				System.out.println("You are all set! ");
				User.adddata("h", new Customer(firstname, lastname, address, username, password));
				isvalid = true; 
			}
			else
			{
				System.out.println("Passwords do not match, please reenter your password. ");
				passwordconfirmation = scanner.next();   
			}
		}
	}
}