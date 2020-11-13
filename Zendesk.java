package Zendesk;

import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Zendesk
{

	private static Scanner sn = new Scanner(System.in);

	
	public static void main(String args[]) throws Exception
	{
		System.out.println("Welcome to Zendesk API. Lets Begin the journey");
		System.out.println("Enter your username/Email Id: "); //Asks for User Email ID	
		String username = sn.nextLine();
		boolean resultFromValidation = EmailIDValidator(username); //Sends for Email ID validation
		if(!resultFromValidation)
			System.out.println("Please enter a valid Email Address. Thanks!"); //Error in Email Format
		else
		{
			
		System.out.println("Enter your API Token: "); //Asks for API token
	    String apitoken = sn.nextLine();
	    System.out.println("Performing Encoding");
		String authString = username + "/token:" + apitoken;
		String authEncBytes = Base64.getEncoder().encodeToString(authString.getBytes());
		String authStringEnc = new String(authEncBytes); //Final result for Encoding
		System.out.println("Encoding Done");
		System.out.println("Enter your domain name before .zendesk.com"); //Asks for your domain
		String domain = sn.nextLine();
		String url = "https://" + domain + ".zendesk.com/api/v2/"; //Ready made URL to your zendesk account
		System.out.println("Obtaining results for user with domain " + domain + ".zendesk.com");
		boolean quit = false;
		int choice = 0;
		
		while(!quit)
		{
			ZendeskFunctions.printInstruction();
			System.out.println("Enter your choice: ");
			choice = sn.nextInt();
			sn.nextLine();
			switch(choice)
			
			{
				case 0:
					ZendeskFunctions.listTickets(authStringEnc,url); //Calls functions that lists tickets
					break;
					
				case 1:
					ZendeskFunctions.searchWithID(authStringEnc,url); //Calls functions that searches with UserID
					break;
				
				case 2:
					ZendeskFunctions.count(authStringEnc,url); //Calls functions that counts number of tickets in your account
					break;
				case 3:
					ZendeskFunctions.searchOpenTickets(authStringEnc, url); //Displays all Open tickets in your account
			
					break;
				case 4:
					System.out.println("Successfully Exiting"); //Exit to code
					quit = true;
					break;
				
					
			}
		
		}
		}
		
	}
	private static boolean EmailIDValidator(String id)
	{
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(id);	
		if(matcher.matches())
			return true;
		else
			return false;
	}
	


}
