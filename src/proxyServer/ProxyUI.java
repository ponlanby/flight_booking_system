package proxyServer;

import socketConstants.SocketConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ProxyUI {

	public static void main( String argv[] ) {
		ServerSocket s = null;
		try {
			s = new ServerSocket( SocketConstants.proxy_port );
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.print("The port is not available\r\n");
			System.exit(1);
		}
		System.out.print( "Proxy Server Running...\r\n" );
		
		while (true) {
			Socket incoming = null;
			try {
				incoming = s.accept();
			} catch ( IOException e ) {
				//System.out.println( e );
				System.out.print("Proxy: Connection failed\r\n");
				continue;
			}
			
			new SocketHandler(incoming).start();
		}//end while
	}
}

class SocketHandler extends Thread {

	Socket incoming;

	ProxyServerHOPP proxyServerHOPP = new ProxyServerHOPP();
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
			writer.print("welcome!\r\n"
					+ "Please enter the operation you want:\r\n"
					+ "city: find city\r\n" 
					+ "airline: find airline\r\n"
					+ "price: find flight rate\r\n" 
					+ "seat: find vacant seat\r\n"
					+ "book: book ticket\r\n" 
					+ "exit: exit\r\n\r\n");

			while (true) {
				//receive request from client
				String str = reader.readLine();

				if (str == null)
					break;
				else if (str.startsWith(SocketConstants.city)) {
					proxyServerHOPP.GetCityResult(str, writer, reader );
					//exit(incoming);
				} else if (str.startsWith(SocketConstants.airline)) {
					proxyServerHOPP.GetAirlineResult(str, writer, reader);
					//exit(incoming);
				} else if (str.startsWith(SocketConstants.price)) {
					proxyServerHOPP.GetPriceResult(str, writer, reader);
					//exit(incoming);
				} else if (str.startsWith(SocketConstants.seat)) {
					proxyServerHOPP.GetVacantSeatResult(str, writer, reader);
					//exit(incoming);
				} else if (str.startsWith(SocketConstants.book)) {
					proxyServerHOPP.GetBookResult(str, writer, reader);
					//exit(incoming);
				} else if (str.equalsIgnoreCase(SocketConstants.exit)) {
					exit(incoming);
				}

			}// end while

			incoming.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.print("Client Connection reset\r\n");
		}
		catch ( NullPointerException e)
		{
			proxyServerHOPP.ErrorMessage(writer);
			return;
		}
	}
	
	public void exit( Socket incoming )
	{
		try {
			incoming.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}