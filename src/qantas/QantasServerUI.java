package qantas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//import proxyServer.ProxyServerHOPP;

//import proxyServer.ProxyServerHOPP;
//import proxyServer.SocketHandler;
import socketConstants.SocketConstants;

public class QantasServerUI {

	public static void main( String argv[] ) {
		ServerSocket s = null;
		try {
			s = new ServerSocket( SocketConstants.qantas_port );
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.print( "Qantas Server Running...\r\n" );
		while (true) {
			Socket incoming = null;
			try {
				incoming = s.accept();
			} catch ( IOException e ) {
				//System.out.println( e );
				System.out.print("QT: Connection failed\r\n");
				continue;
			}

			new SocketHandler(incoming).start();
		}
	}
}

class SocketHandler extends Thread {

	Socket incoming;
	QantasServerHOPP qantasServerHOPP = new QantasServerHOPP();

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
					
					ArrayList<String> cityList= new ArrayList<String>(qantasServerHOPP.QueryCity());
					
					for( int i = 0; i < cityList.size(); i++ )
					{
						writer.print( cityList.get(i) + "\r\n");
						//System.out.println( cityList.get(i));
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.airline)) {
					
					ArrayList<String> airlineList= new ArrayList<String>(qantasServerHOPP.QueryAirline(str));
					
					for( int i = 0; i < airlineList.size(); i++ )
					{
						writer.print( airlineList.get(i) + "\r\n");
						//System.out.println( cityList.get(i));
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.price)) {
					
					ArrayList<String> PriceList= new ArrayList<String>(qantasServerHOPP.QueryPrice(str));
					
					for( int i = 0; i < PriceList.size(); i++ )
					{
						writer.print( PriceList.get(i) + "\r\n");
						//System.out.println( cityList.get(i));
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.seat)) {
					
					ArrayList<String> seatList= new ArrayList<String>(qantasServerHOPP.QueryVacantSeat(str));
					
					for( int i = 0; i < seatList.size(); i++ )
					{
						writer.print( seatList.get(i) + "\r\n");
						//System.out.println( cityList.get(i));
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.book)) {
					ArrayList<String> ticketList= new ArrayList<String>(qantasServerHOPP.BookTicket(str));
					
					for( int i = 0; i < ticketList.size(); i++ )
					{
						System.out.println(ticketList.get(i));
						writer.print( ticketList.get(i) + "\r\n");
					}
					//end signal
					writer.print( "|\r\n" );
				} else if (str.startsWith(SocketConstants.exit)) {
					writer.print("exit information from Qantas\r\n");
					qantasServerHOPP.exit(incoming);
				}

			}// end while

			incoming.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.print("Connection reset\r\n");
		}// end try
	}// end run
}