package client;

import socketConstants.SocketConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class ClientUI {
	
	protected ClientHOPP clientHOPP;
	
	//protected Socket sock;
	protected BufferedReader reader;
	protected PrintStream writer;
	protected BufferedReader console;
	protected InputStream in;
	protected OutputStream out;
	
	public static void main( String[] args )
	{
		ClientUI client = new ClientUI();
		client.loop();
	}

	public ClientUI()
	{
		clientHOPP = null;
		try
		{
			clientHOPP = new ClientHOPP();
		} catch( Exception e ){
			e.printStackTrace();
			System.exit(1);
		}
		console = new BufferedReader( new InputStreamReader( System.in ) );
	}
	
	public void loop()
	{		
		//receive welcoming information from server
		clientHOPP.DisplayFromServer();
		
		//get command
	    while ( true ) {
	      String line = null;
	      try {
	    	  line = console.readLine();
	      } catch (IOException e) {
	    	  //e.printStackTrace();
	    	  System.out.print("No input received\r\n");
	    	  clientHOPP.exit();
	      }

	      //send request to server
	      if ( line.equalsIgnoreCase( SocketConstants.city ) ) {
	    	  FindCityRequest();
			  //clientHOPP.exit();
	      } else if ( line.equalsIgnoreCase( SocketConstants.airline ) ) {
	    	  FindAirlineRequest();
			  //clientHOPP.exit();
	      } else if ( line.equalsIgnoreCase( SocketConstants.price ) ) {
	    	  FindPriceRequest();
			  //clientHOPP.exit();
	      } else if ( line.equalsIgnoreCase( SocketConstants.seat ) ) {
	    	  FindVacantSeatRequest();
			  //clientHOPP.exit();
		  } else if ( line.equalsIgnoreCase( SocketConstants.book ) ) {
			  BookTicketRequest();
			  //clientHOPP.exit();
		  } else if ( line.equalsIgnoreCase( SocketConstants.exit ) ) {
			  clientHOPP.exit();
	      } else {
	    	  System.out.print("Unrecognised command\r\n");
	      }
	    }
	}
	
	public void FindCityRequest()
	{
		//System.out.print( "Please enter the departure city and destination city:\r\n" );
		clientHOPP.FindCityRequest();
	}
	
	public void FindAirlineRequest()
	{
		//System.out.print( "Please enter the departure city and the destination city:\r\n" );
		clientHOPP.FindAirlineRequest();
	}
	
	public void FindPriceRequest()
	{
		//System.out.print( "Please enter the departure city, the destination city and the airline you want:\r\n" );
		clientHOPP.FindPriceRequest();
	}
	
	public void FindVacantSeatRequest()
	{
		//System.out.print( "Please enter the departure city, the destination city, the airline and the departing date you want:\r\n" );
		clientHOPP.FindVacantSeatRequest();
	}
	
	public void BookTicketRequest()
	{
		//System.out.print( "Please enter the flight you choose and provide your information:\r\n" );
		clientHOPP.BookTicketRequest();
	}
}
