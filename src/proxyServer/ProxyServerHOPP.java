package proxyServer;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ProxyServerHOPP {

	ProxyClientHOPP proxyClientHOPP = new ProxyClientHOPP();
	
	public boolean CheckEmail( String str )
	{
		boolean is = false;
		
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		is = str.matches(regex);
		return is;
	}
	
	//only contains characters
	public boolean CheckString( String str )
	{
		boolean is = false;
		is = str.matches("^[A-Za-z]+$");
		//System.out.println(str);
		//System.out.println(is);
		return is;
	}
	
	public boolean CheckDigit( String str, int length )
	{
		boolean is = false;
		String regex = "\\d{" + length + "}$";
		is = str.matches(regex);
		return is;
	}
	
	public void GetCityResult( String str, PrintStream writer, BufferedReader reader )
	{
		//get city results
		ArrayList<String> china_easternList = new ArrayList<String>(proxyClientHOPP.QueryCityRequest(str, "China_eastern" ));
		ArrayList<String> china_southernList = new ArrayList<String>(proxyClientHOPP.QueryCityRequest(str, "China_southern" ));
		ArrayList<String> qantasList = new ArrayList<String>(proxyClientHOPP.QueryCityRequest(str, "Qantas" ));
		ArrayList<String> virgin_blueList = new ArrayList<String>(proxyClientHOPP.QueryCityRequest(str, "Virgin_blue" ));
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(china_easternList);
		result.addAll(china_southernList);
		result.addAll(qantasList);
		result.addAll(virgin_blueList);
		HashSet<String> resultSet = new HashSet<String>(result);
		Iterator<String> iter = resultSet.iterator();
		
		//print city list
		writer.print( "The cities serviced by the system are: \r\n" );
		if(iter.hasNext())
		{
			while(iter.hasNext())
				writer.print(iter.next() + "\r\n");
		}
		else
			writer.print("No city serviced by the system");
		
		//end signal
		writer.print( "|\r\n" );
	}
	
	public void GetAirlineResult( String str, PrintStream writer, BufferedReader reader )
	{
		if ( CheckString( str.replace(",", "") ) )
		{
			//get airline results
			ArrayList<String> china_easternList = new ArrayList<String>(proxyClientHOPP.QueryAirlineRequest(str, "China_eastern" ));
			ArrayList<String> china_southernList = new ArrayList<String>(proxyClientHOPP.QueryAirlineRequest(str, "China_southern" ));
			ArrayList<String> qantasList = new ArrayList<String>(proxyClientHOPP.QueryAirlineRequest(str, "Qantas" ));
			ArrayList<String> virgin_blueList = new ArrayList<String>(proxyClientHOPP.QueryAirlineRequest(str, "Virgin_blue" ));
			ArrayList<String> result = new ArrayList<String>();
			result.addAll(china_easternList);
			result.addAll(china_southernList);
			result.addAll(qantasList);
			result.addAll(virgin_blueList);
			HashSet<String> resultSet = new HashSet<String>(result);
			Iterator<String> iter = resultSet.iterator();
			
			//print airline list
			
			if( !iter.hasNext() )
				writer.print("Do not have airline\r\n");
			else
			{
				writer.print("The airlines that have flight between the given cities are:\r\n");
				if(iter.hasNext())
				{
					while(iter.hasNext())
					{
						/*String[] strarray = iter.next().split( "," );
						String airline = strarray[0];
						String departdate = strarray[1];
						writer.print( airline + departdate + "\r\n");
						System.out.println(airline + departdate);*/
						writer.print( iter.next() + "\r\n");
					}
				}
				else
					writer.print("Do not have airlines between the given cities\r\n");
			}
		}
		else
			writer.print("Please use the right city name\r\n");
		
		//end signal
		writer.print( "|\r\n" );
	}
	
	public void GetPriceResult( String str, PrintStream writer, BufferedReader reader )
	{
		if ( CheckString( str.replace(",", "").replace(" ", "") ) )
		{
			//get price results
			ArrayList<String> result = new ArrayList<String>(proxyClientHOPP.QueryPriceRequest(str));
			HashSet<String> resultSet = new HashSet<String>(result);
			Iterator<String> iter = resultSet.iterator();
			
			//print airline list
			if(iter.hasNext())
				writer.print( "The price of a round-trip flight between the given cities is: " + iter.next() + "\r\n" );
			else
				writer.print( "Do not have that flight\r\n" );
		}
		else
			writer.print("Please use the right city and airline name\r\n");
		
		//end signal
		writer.print( "|\r\n" );
	}
	
	public void GetVacantSeatResult( String str, PrintStream writer, BufferedReader reader )
	{
		String[] strarray = str.split( "," );
		String Airline = strarray[0];
		String Departure = strarray[1];
		String Arrive = strarray[2];
		//String DepartDate = strarray[4];
		//if( CheckString(Departure+Arrive+Airline.replace(" ", "")) )
		if( CheckString(Departure) && CheckString(Arrive) && CheckString(Airline.replace(" ", "")) )
		{
			//get seat results
			ArrayList<String> result = new ArrayList<String>(proxyClientHOPP.QueryVacantSeatRequest(str));
			Iterator<String> iter = result.iterator();
			
			//print airline list
			if(iter.hasNext())
				writer.print("There are " + iter.next() + " tickets left\r\n" );
			else writer.print( "Do not have that flight\r\n" );
		}
		else
			writer.print("Please use the right city and airline name\r\n");
		
		//end signal
		writer.print( "|\r\n" );
	}
	
	public void GetBookResult( String str, PrintStream writer, BufferedReader reader )
	{
		//get seat results
		String[] strarr = str.split( "," );
		String airline = strarr[0];
		String departure = strarr[1];
		String arrive = strarr[2];
		String username = strarr[6];
		String Phone = strarr[7];
		String Email = strarr[8];
		String Creditno = strarr[9];
		if( CheckString(departure+arrive+airline) )
		{
			if(CheckString(username))
			{
				if(CheckEmail(Email)&&CheckDigit(Phone,11)&&CheckDigit(Creditno,16))
				{
					ArrayList<String> result = new ArrayList<String>(proxyClientHOPP.BookTicketRequest(str));
					Iterator<String> iter = result.iterator();
					
					//print airline list
					if(iter.hasNext())
						try{
						while(iter.hasNext())
						{
							String[] strarray = iter.next().split( "," );
							String Username = strarray[0];
							String TicketNo = strarray[1];
							String DepartFlight = strarray[2];
							String ReturnFlight = strarray[3];
							String Departure = strarray[4];
							String DepartDate = strarray[5];
							String Arrive = strarray[6];
							String ReturnDate = strarray[7];
							String Airline = strarray[8];
							String DepartAirport = strarray[9];
							String ReturnAirport = strarray[10];
							String price = strarray[11];
							String phone = strarray[12];
							String email = strarray[13];
							String creditno = strarray[14];
							writer.print("Depart: From " + Departure + " To " + Arrive + " Flight: " + Airline + DepartFlight + " in " + DepartAirport + " on " + DepartDate + "\r\n");
							writer.print("Return: From " + Arrive + " To " + Departure + " Flight: " + Airline + ReturnFlight + " in " + ReturnAirport + " on " + ReturnDate + "\r\n");
							writer.print("User information: Name " + Username + " E-mail " + email + " Telephone " + phone + " CreditCardNo " + creditno + "\r\n");
							writer.print("The total price is " + price + ", and the TicketNo is " + TicketNo + "\r\n");

						}							
						}catch(Exception e){
							
						}
					else
						writer.print("Do not have the flight you want\r\n");
				}
				else
					writer.print("Please use the right E-mail address, Phone number and CreditCard number\r\n");
			}
			else 
				writer.print("Please use the right user name\r\n");
		}
		else
			writer.print("Please use the right city and airline name\r\n");
		
		//end signal
		writer.print( "|\r\n" );
	}
	
	public void ErrorMessage(PrintStream writer)
	{
		writer.print("No returning result\r\n");
	}
}