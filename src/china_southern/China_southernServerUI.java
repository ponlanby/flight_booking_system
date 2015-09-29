package china_southern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import socketConstants.SocketConstants;

public class China_southernServerUI {

	public static void main( String argv[] ) {
		ServerSocket s = null;
		try {
			s = new ServerSocket( SocketConstants.china_southern_port );
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.print( "China_southern Server Running...\r\n" );
		while (true) {
			Socket incoming = null;
			try {
				incoming = s.accept();
			} catch ( IOException e ) {
				//System.out.println( e );
				System.out.print("CS: Connection failed\r\n");
				continue;
			}

			new SocketHandler(incoming).start();
		}
	}
}

class SocketHandler extends Thread {

	Socket incoming;
	China_southernServerHOPP china_southernServerHOPP = new China_southernServerHOPP();

	BufferedReader reader;
	PrintStream writer;

	SocketHandler(Socket incoming) {
		this.incoming = incoming;
	}

	public void run() {
		try {

			writer = new PrintStream( incoming.getOutputStream() );
			reader = new BufferedReader( new InputStreamReader( incoming.getInputStream() ) );

			// show operation guide
			
			//writer.print( "welcome to China_eastern server!\r\n" );

			while (true) {
				String str = reader.readLine();
				//if (str == null) {
				//	break;
				//}

				//str = reader.readLine();
				//System.out.print(str + " received in airline server\r\n");

				if (str == null)
					break;
				else if (str.startsWith(SocketConstants.city)) {
					//writer.print("city information from china_eastern\r\n");
					
					ArrayList<String> cityList= new ArrayList<String>(china_southernServerHOPP.QueryCity());
					
					for( int i = 0; i < cityList.size(); i++ )
					{
						writer.print( cityList.get(i) + "\r\n");
						//System.out.println( cityList.get(i));
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.airline)) {
					
					ArrayList<String> airlineList= new ArrayList<String>(china_southernServerHOPP.QueryAirline(str));
					
					for( int i = 0; i < airlineList.size(); i++ )
					{
						writer.print( airlineList.get(i) + "\r\n");
						//System.out.println( cityList.get(i));
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.price)) {
					
					ArrayList<String> priceList= new ArrayList<String>(china_southernServerHOPP.QueryPrice(str));
					
					for( int i = 0; i < priceList.size(); i++ )
					{
						writer.print( priceList.get(i) + "\r\n");
						//System.out.println( cityList.get(i));
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.seat)) {
					
					ArrayList<String> seatList= new ArrayList<String>(china_southernServerHOPP.QueryVacantSeat(str));
					
					for( int i = 0; i < seatList.size(); i++ )
					{
						writer.print( seatList.get(i) + "\r\n");
						//System.out.println( cityList.get(i));
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.book)) {
					ArrayList<String> ticketList= new ArrayList<String>(china_southernServerHOPP.BookTicket(str));
					
					for( int i = 0; i < ticketList.size(); i++ )
					{
						//System.out.println(ticketList.get(i));
						writer.print( ticketList.get(i) + "\r\n");
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.exit)) {
					writer.print("exit information from china_southern\r\n");
					china_southernServerHOPP.exit(incoming);
				}

			}// end while

			incoming.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.print("Connection reset\r\n");
		}// end try
	}// end run
}