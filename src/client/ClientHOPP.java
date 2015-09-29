package client;

import socketConstants.SocketConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientHOPP {

	protected Socket sock;
	protected BufferedReader reader;
	protected PrintStream writer;
	protected BufferedReader console;
	protected InputStream in;
	protected OutputStream out;
	protected Scanner scanner;
	
	public ClientHOPP()
	{
		ConnectToServer();
	}

	public void BookTicketRequest()
	{
		scanner = new Scanner( System.in );
		System.out.print( "Please enter the departure city:\r\n" );
		String departure = scanner.nextLine();
		System.out.print( "Please enter the destination city:\r\n" );
		String destination = scanner.nextLine();
		System.out.print( "Please enter the airline you want to take:\r\n" );
		String airline = scanner.nextLine();
		System.out.print( "Please enter the departing date(YYYY-MM-DD):\r\n" );
		String departDate = scanner.nextLine();
		System.out.print( "Please enter the returning date(YYYY-MM-DD):\r\n" );
		String returnDate = scanner.nextLine();
		System.out.print( "Please enter your name:\r\n" );
		String username = scanner.nextLine();
		System.out.print( "Please enter your telephone number:\r\n" );
		String phone = scanner.nextLine();
		System.out.print( "Please enter your E-mail address:\r\n" );
		String email = scanner.nextLine();
		System.out.print( "Please enter your credit card number:\r\n" );
		String creditno = scanner.nextLine();
		writer.print( SocketConstants.book + "," + departure + "," + destination + "," + airline + "," + departDate + "," + returnDate + "," + username + "," + phone + "," + email + "," + creditno + "\r\n" );
		ReadFromServer();
	}
	
	public void FindVacantSeatRequest()
	{
		scanner = new Scanner( System.in );
		System.out.print( "Please enter the departure city:\r\n" );
		String departure = scanner.nextLine();
		System.out.print( "Please enter the destination city:\r\n" );
		String destination = scanner.nextLine();
		System.out.print( "Please enter the airline you want to take:\r\n" );
		String airline = scanner.nextLine();
		System.out.print( "Please enter the departing date(YYYY-MM-DD):\r\n" );
		String departDate = scanner.nextLine();
		writer.print( SocketConstants.seat + "," + departure + "," + destination + "," + airline + "," + departDate + "\r\n" );
		ReadFromServer();
	}
	
	public void FindPriceRequest()
	{
		scanner = new Scanner( System.in );
		System.out.print( "Please enter the departure city:\r\n" );
		String departure = scanner.nextLine();
		System.out.print( "Please enter the destination city:\r\n" );
		String destination = scanner.nextLine();
		System.out.print( "Please enter the airline you want to take:\r\n" );
		String airline = scanner.nextLine();
		writer.print( SocketConstants.price + "," + departure + "," + destination + "," + airline + "\r\n" );
		ReadFromServer();
	}
	
	public void FindAirlineRequest()
	{
		scanner = new Scanner( System.in );
		System.out.print( "Please enter the departure city:\r\n" );
		String departure = scanner.nextLine();
		System.out.print( "Please enter the destination city:\r\n" );
		String destination = scanner.nextLine();
		writer.print( SocketConstants.airline + "," + departure + "," + destination + "\r\n" );
		ReadFromServer();
	}
	
	public void FindCityRequest()
	{
		writer.print( SocketConstants.city + "\r\n" );
		ReadFromServer();
	}
	
	public void ConnectToServer()
	{		
		try
		{
			InetAddress address = InetAddress.getByName( "LocalHost" );
			
			sock = new Socket( address, SocketConstants.proxy_port );
			in = sock.getInputStream();
			out = sock.getOutputStream();
			
			reader = new BufferedReader( new InputStreamReader( in ) );
			writer = new PrintStream( out );
	    } catch ( IOException e ) {
	    	//e.printStackTrace();
	    	System.out.print("Failed to connect to proxy server\r\n");
	    	return;
	    }	
	}
	
	public void ReadFromServer()
	{
		try
		{
		String str = null;
		while(true)
		{
			str = reader.readLine();
			if( str.equals("|") )
				break;
			else
				System.out.print( str + "\r\n" );
		}
		} catch(IOException e){
			//e.printStackTrace();
		}
	}
	
	public void DisplayFromServer()
	{
		try 
		{
			do
	    	{
		      System.out.print( reader.readLine() + "\r\n" );
	    	} while ( reader.ready() );
	    } catch ( IOException e ) {
		      //e.printStackTrace();
		}
	}
	
	public void exit()
	{
	    try 
	    {
	    	writer.print( "exiting...\r\n" );
		    //reader.close();
		    //writer.close();
		    sock.close();
		} catch (Exception e) {
			//e.printStackTrace();
		    System.exit(1);
		}
	}
}
