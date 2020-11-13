# Zendesk API

### Description
The project is an initial level command-line interface to connect to the zendesk project. The java code has been written in a very easy and simple language for understandability. The few aspects of the code are it performs Security by passing an API token and encrypting it using Base64 and sending it to the Zendesk API. Here, if the API token and email ID does not match, the code throws an error while using the switch Case <br>

### Installation
There are two files - Zendesk.java and ZendeskFunctions.java. You can run them in Eclipse or any other IDE of your choice <br>
1. Zendesk 
This is the main file to be executed. It calls all functions from the switch case to be executed. <br>
2. ZendeskFunctions 
This file contains all the important functions that are necessary for successful execution. 
You also need to add an external library and add a classpath to your project for a file called  "java-son.jar" to process JSON data. <br>



### Workflow
 ```sh
String username = sn.nextLine();
boolean res = EmailIDValidator(username);
if(!res)
System.out.println("Please enter a valid Email Address. Thanks!");
```
  - Step1: The code asks for the email Id. This is the one that you have used on your Zendesk API. The prior part here is Email ID verfication. Use of regex is been done to check if a valid email address has been passed. If yes , then the program runs ahead or else quits. <br> 
   ```sh
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
```
  - Step2: The code asks for API token. This is the one that you have saved and Enable on the Zendesk API token. Make sure you enable both the options on Zendesk API <br>
  - Step3: There performs an internal Base64 conversion of your API token and Email Id that acts as the base for your authentication header of HTTPRequest <br>
   ```sh
String authString = username + "/token:" + password;
String authEncBytes = Base64.getEncoder().encodeToString(authString.getBytes());
String authStringEnc = new String(authEncBytes);
```
  - Step4: It asks for the domain name. The importance of asking this is a user can access tickets of its own account. Everything is being stored and combined in different strings  so that user has no control over any links of the server. Everything has been automated making it easy for the user to handle the tickets. After all of these, it passes to switch function <br>
  

### Switch Case functions
  ```sh
System.out.println("\n Press");
		System.out.println("\t 0 - To print all tickets");
		System.out.println("\t 1 - To search ticket with an ID");
		System.out.println("\t 2 - To count number of tickets");
		System.out.println("\t 2 - Search All Open Tickets");
		System.out.println("\t 3 - QUIT");
```

  - 0 : It is used to send all the tickets on your respective account. We have tried to use pagination so that max of 25 tickets would be displayed
  - 1 : It is used to search tickets with the specified ID. All the details of the specified ID tickets would be displayed
  - 2 : It is used to give overall count of the number of tickets in the account
  - 3 : This options displays all the open tickets. 
  - 4 : This exits the code and breaks the while loop



### Understanding Pagination
```sh
System.out.println("Which page do you want to go to")
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
```
Pagination has been implemented using a switch case. At first, the code checks if the page is not 1 which means it's greater than 1. So Zendesk provides meta which stores the Boolean value called "has_more" which stores if there are any more pages. If there are, then only it will proceed with the Switch case else terminates. The JSON object contains an option called links that stores little information. We extract the value of prev or next depending upon the user input. <br>


### External Libraries 
(java-son.rar) [https://mvnrepository.com/artifact/org.json/json/20140107] <br>
Manipulate JSON data

### References
(REST API) [ https://developer.zendesk.com/embeddables/docs/connect/rest_api ] <br>
(TICKET) [https://developer.zendesk.com/rest_api/docs/support/tickets] <br>
(REQUESTS AND ENDPOINTS) [https://developer.zendesk.com/rest_api/docs/support/requests] <br>
(SECURITY AUTHENTICATION, PAGINATION, RESPONSE CODE) [https://developer.zendesk.com/rest_api/docs/support/introduction] <br>

