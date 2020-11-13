package Zendesk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZendeskFunctions {

	private static Scanner sn = new Scanner(System.in);
	static void printInstruction() {
		System.out.println("\n Press");
		System.out.println("\t 0 - To print all tickets");
		System.out.println("\t 1 - To search ticket with an ID");
		System.out.println("\t 2 - To count number of tickets");
		System.out.println("\t 3 - Search All Open Tickets");
		System.out.println("\t 4 - Quit");
		
	}
	public static void listTickets(String authStringEnc,String url) throws Exception
	{
		int current_page = 1; //Set default page number
		boolean not_exit = true;
		String EncodeURL = url + "tickets.json?page[size]=25";
		
		while(not_exit){ //We iterate until user demands to quit
			URL listTicketLink = new URL(EncodeURL);
			HttpsURLConnection connection = (HttpsURLConnection) listTicketLink.openConnection();
			connection.setRequestMethod("GET");
	        connection.setDoOutput(true);
	        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
	        int responseCode = connection.getResponseCode();
	        
	        if(responseCode == 200) //If successfull connection
	        {
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String inputLine= in.readLine(); //Read everything as string
	        JSONObject obj = new JSONObject(inputLine); //Convert to Json Obj
	        
	        JSONArray arr = obj.getJSONArray("tickets"); //Fetch all tickets and store them in array
	       
	        for (int i = 0; i < arr.length(); i++) { //Fetch only the subject and Id from tickets array
	            String subject = arr.getJSONObject(i).getString("subject");
	            int id = arr.getJSONObject(i).getInt("id");
	            System.out.println("|" + id + "|" + subject);
	        }
	        //Pagination
	        System.out.println("Which page do you want to go to");
	        if(current_page != 1) //If there are more than 1 pages
	        System.out.println("0: prev"); //It means we include the next page as well
	        if(obj.getJSONObject("meta").getBoolean("has_more")) //Get the metadata and fetch value of has_more
	        System.out.println("1: next");
	        System.out.println("2: exit");
	        switch(Integer.parseInt(sn.nextLine())){ //Directly ask for input
		        case 0: EncodeURL = obj.getJSONObject("links").getString("prev"); //Redirects to previous page
		        		current_page--;
		        		break;
		        case 1: EncodeURL = obj.getJSONObject("links").getString("next"); //Redirects to next page
		        		current_page++;
						break;	
		        case 2: not_exit = false; //Quits to main Menu
		        		break;
	        }
	        in.close();
	        }
		
	        else
	        {
	        	connection.disconnect();
	        	System.out.println("There has been a problem with the request. Response Code Generated is " + responseCode);
	        	System.out.println("Check results for response code here at : https://developer.zendesk.com/rest_api/docs/support/introduction#response-format ");
	        	break;
	        }
		}
        
	}
	
	public static void count(String authStringEnc,String url) throws Exception
	{
		String EncodeURL = url + "tickets/count.json";
		URL countLink = new URL(EncodeURL);
		HttpsURLConnection connection = (HttpsURLConnection) countLink.openConnection();
		connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        int responseCode = connection.getResponseCode();
        
        if(responseCode == 200)
        {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       
        String inputLine = in.readLine();
        JSONObject obj = new JSONObject(inputLine);
        System.out.println(" " + obj.getJSONObject("count").getInt("value") + " tickets are present on your account"); //Fetch only the value stored in count 
        }
        else
        {
        	System.out.println("There has been a problem with the request. Response Code Generated is " + responseCode);
        	System.out.println("Check results for response code here at : https://developer.zendesk.com/rest_api/docs/support/introduction#response-format ");
        }
        
	}
	public static void searchWithID(String authStringEnc,String url) throws IOException, JSONException {
		
		System.out.println("ENter Id number:- ");
		int id = sn.nextInt();
		String EncodeURL = url + "search.json?query=" + id;
		
		URL searchWithIdLink = new URL(EncodeURL);
		HttpsURLConnection connection = (HttpsURLConnection) searchWithIdLink.openConnection();
		connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        int responseCode = connection.getResponseCode();
        
        if(responseCode == 200)
        {
        	 BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             String inputLine = in.readLine();
             JSONObject obj = new JSONObject(inputLine);
             System.out.println(obj); //Prints all JSON data
        }
        else
        {
        	System.out.println("There has been a problem with the request. Response Code Generated is " + responseCode);
        	System.out.println("Check results for response code here at : https://developer.zendesk.com/rest_api/docs/support/introduction#response-format ");
        }
	}
	
	
	public static void searchOpenTickets(String authStringEnc, String url) throws IOException {
	
		String EncodeURL = url + "search.json?query=type%3Aticket+status%3Aopen";
		
		URL openTickets = new URL(EncodeURL);
		HttpsURLConnection connection = (HttpsURLConnection) openTickets.openConnection();
		connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        int responseCode = connection.getResponseCode();
        
        if(responseCode == 200)
        {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
        System.out.println(inputLine);
        in.close();
        }
        else
        {
        	System.out.println("There has been a problem with the request. Response Code Generated is " + responseCode);
        	System.out.println("Check results for response code here at : https://developer.zendesk.com/rest_api/docs/support/introduction#response-format ");
        }
	}
}
