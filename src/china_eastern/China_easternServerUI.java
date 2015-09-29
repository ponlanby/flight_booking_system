package china_eastern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import socketConstants.SocketConstants;

public class China_easternServerUI {

	public static void main( String argv[] ) {
		ServerSocket s = null;
		try {
			s = new ServerSocket( SocketConstants.china_eastern_port );
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.print( "China_eastern Server Running...\r\n" );
		while (true) {
			Socket incoming = null;
			try {
				incoming = s.accept();
			} catch ( IOException e ) {
				//System.out.println( e );
				System.out.print("CE: Connection failed\r\n");
				continue;
			}

			new SocketHandler(incoming).start();
		}
	}
}

class SocketHandler extends Thread {

	Socket incoming;
	China_easternServerHOPP china_easternServerHOPP = new China_easternServerHOPP();

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

			while (true) {
				String str = reader.readLine();

				if (str == null)
					break;
				else if (str.startsWith(SocketConstants.city)) {
					
					ArrayList<String> cityList= new ArrayList<String>(china_easternServerHOPP.QueryCity());
					
					for( int i = 0; i < cityList.size(); i++ )
					{
						writer.print( cityList.get(i) + "\r\n");
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.airline)) {
					
					ArrayList<String> airlineList= new ArrayList<String>(china_easternServerHOPP.QueryAirline(str));
					
					for( int i = 0; i < airlineList.size(); i++ )
					{
						writer.print( airlineList.get(i) + "\r\n");
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.price)) {
					
					ArrayList<String> priceList= new ArrayList<String>(china_easternServerHOPP.QueryPrice(str));
					
					for( int i = 0; i < priceList.size(); i++ )
					{
						writer.print( priceList.get(i) + "\r\n");
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.seat)) {
					
					ArrayList<String> seatList= new ArrayList<String>(china_easternServerHOPP.QueryVacantSeat(str));
					
					for( int i = 0; i < seatList.size(); i++ )
					{
						writer.print( seatList.get(i) + "\r\n");
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.book)) {
					ArrayList<String> ticketList= new ArrayList<String>(china_easternServerHOPP.BookTicket(str));
					
					for( int i = 0; i < ticketList.size(); i++ )
					{
						writer.print( ticketList.get(i) + "\r\n");
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.exit)) {
					writer.print("exit information from china_eastern\r\n");
					china_easternServerHOPP.exit(incoming);
				}

			}// end while

			incoming.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.print("Proxy Connection reset\r\n");
		}// end try
	}// end run
}