package proxyServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import socketConstants.SocketConstants;

public class ProxyClientHOPP {
	
	public static String temp;
	
	protected static Socket sock;
	protected static BufferedReader reader;
	protected static PrintStream writer;
	protected static BufferedReader console;
	protected static InputStream in;
	protected static OutputStream out;
	
	
	public synchronized ArrayList<String> BookTicketRequest( String str )
	{
		String airline = GetAirline(str);
		//connect to certain port
		int port;
		port = ChoosePort( airline );
		ConnectToServer( port );
		
		//send request
		writer.print( str + "\r\n" );
		ArrayList<String> ticketList = new ArrayList<String>( ReadFromServer() );

		return ticketList;		
	}
	
	public ArrayList<String> QueryVacantSeatRequest( String str )
	{
		String airline = GetAirline(str);
		//System.out.println(airline);
		//connect to certain port
		int port;
		port = ChoosePort( airline );
		ConnectToServer( port );
		
		//send request
		writer.print( str + "\r\n" );
		ArrayList<String> seatList = new ArrayList<String>( ReadFromServer() );

		return seatList;
	}
	
	public ArrayList<String> QueryPriceRequest( String str)
	{
		String airline = GetAirline(str);
		int port;
		port = ChoosePort( airline );
		ConnectToServer( port );
		
		//send request
		writer.print( str + "\r\n" );
		ArrayList<String> priceList = new ArrayList<String>( ReadFromServer() );

		return priceList;
	}
	
	public ArrayList<String> QueryAirlineRequest( String str, String airline )
	{
		int port;
		port = ChoosePort(airline);
		ConnectToServer( port );
		//DisplayFromServer();
		//writer.print( SocketConstants.airline + "\r\n" );
		writer.print( str + "\r\n" );
		ArrayList<String> airlineList = new ArrayList<String>( ReadFromServer() );
		return airlineList;
	}
	
	public ArrayList<String> QueryCityRequest( String str, String airline )
	{
		int port = 0;
		port = ChoosePort( airline );
		
		//connect to all data servers and query, get info
		ConnectToServer( port );
		//DisplayFromServer();
		//send request to data server
		writer.print( str + "\r\n" );
		
		ArrayList<String> cityList = new ArrayList<String>( ReadFromServer() );
		return cityList;
	}

	public ArrayList<String> ReadFromServer()
	{
		ArrayList<String> list = new ArrayList<String>();
		String str = null;
		try
		{
			while(true)
			{
				str = reader.readLine();
				if( str.equals("|") )
					break;
				else
					list.add(str);
			}
	    } catch ( IOException e ) {
		      e.printStackTrace();
		      //System.exit(1);
		}
		return list;
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
		      e.printStackTrace();
		      return;
		}
	}

	public static void ConnectToServer( int port )
	{
		try
		{
			InetAddress address = InetAddress.getByName( "LocalHost" );
			
			sock = new Socket( address, port );
			in = sock.getInputStream();
			out = sock.getOutputStream();
			
			reader = new BufferedReader( new InputStreamReader( in ) );
			writer = new PrintStream( out );
	    } catch ( NullPointerException e ) {
	    	//e.printStackTrace();
	    	writer.print("Cannot access to that airline\r\n");
	    	return;
	    } catch( IOException e)
	    {
	    	writer.print("Failed to connect to airline server\r\n");
	    	return;
	    }
	}
	
	public String GetAirline( String str )
	{
		String[] strarray = str.split( "," );	//do not use "|"
		String Airline = strarray[3];
		return Airline;
	}
	
	public int ChoosePort( String airline )
	{
		int port = 0;
		if( airline.equalsIgnoreCase( "China_eastern" ) || airline.equalsIgnoreCase( "China eastern" ) ) 
			port = SocketConstants.china_eastern_port;
		else if( airline.equalsIgnoreCase( "China_southern" ) || airline.equalsIgnoreCase( "China southern" ) )
			port = SocketConstants.china_southern_port;
		else if( airline.equalsIgnoreCase( "Qantas" ) )
			port = SocketConstants.qantas_port;
		else if( airline.equalsIgnoreCase( "Virgin_blue" ) || airline.equalsIgnoreCase( "Virgin blue" ) )
			port = SocketConstants.virgin_blue_port;
		return port; 
	}
}