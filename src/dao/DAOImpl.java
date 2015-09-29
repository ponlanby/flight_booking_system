package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.List;

///////////////////////////////////////////////////////////////
//
//every airline has its own server(DAOImpl), maybe airline item is not needed
//
///////////////////////////////////////////////////////////////
public class DAOImpl implements DAO{
	
	//for test use
	/*public static void main(String[] args)
	{
		DAOImpl query = new DAOImpl();
		
		//daoquery.queryCity();
		//daoquery.queryAirline( "Shanghai", "Sydney" );
		//daoquery.queryFlightRate( "Beijing", "Melbourne", "China_eastern" );
		//Date date = new Date( (2014-1900), (3-1), 13 );
		//daoquery.querySeat( "Beijing", "Melbourne", "China_eastern", date );
		query.bookTicket("Beijing", "Melbourne", "china_eastern", "2014-03-13", "2014-03-20", "tong", "12345678901", "1234@123.com", "123456789123456");
		//query.bookTicket("Guangzhou", "Melbourne", "china_southern", "2014-03-07", "2014-03-10", "tong3", "12345678901", "123@123.com", "123456789123456");
		//query.bookTicket("Wuhan", "Sydney", "qantas", "2014-03-04", "2014-03-15", "tong4", "123", "123@123.com", "123456");
		//query.bookTicket("Melbourne", "Shanghai", "virgin_blue", "2014-03-13", "2014-03-26", "tong5", "123", "123@123.com", "123456");
	}*/
		
	public Connection getConnection()
	{
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
				
			con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/id25466194", "fit5183a1", "");
			} catch ( SQLException e ){
				System.out.println( "Failed to connect DB!" );
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return con;
	}

	@Override
	//find all cities served by the system
	//as depart and arrive are the same, only need to query depart
	public ArrayList<String> queryCity(String airline) {
		// TODO Auto-generated method stub
		ArrayList<String> city_result = new ArrayList<String>();
		try{
			Connection conn = getConnection( );
			
			/*String sql = "select depart from china_eastern union " +
					"select depart from china_southern union " +
					"select depart from qantas union  " +
					"select depart from virgin_blue";*/
			String sql = "select depart from " + airline;
			Statement st = conn.createStatement( );
				
			ResultSet rs = st.executeQuery( sql );
			
			//String depart = null;
					
			while( rs.next() )
			{
				city_result.add( rs.getString( "Depart" ) );
			}
			
			conn.close();
		} catch( SQLException e){
			System.out.print( "Query city failed!\r\n" );
		}
		return city_result;
	}

	@Override
	//for a given departure city and a given destination city, regardless which departing date will be,
	//what airline have International flights between the two cities and serviced by the system
	public ArrayList<String> queryAirline(String from, String to, String airline ) {
		ArrayList<String> airline_result = new ArrayList<String>();
		// TODO Auto-generated method stub
		try{
			Connection conn = getConnection( );
			
			/*String sql = "select airline from china_eastern where depart = '" + from + "' and arrive = '" + to + "' union " + 
					"select airline from china_southern where depart = '" + from + "' and arrive = '" + to + "' union " +
					"select airline from qantas where depart = '" + from + "' and arrive = '" + to + "' union " +
					"select airline from virgin_blue where depart = '" + from + "' and arrive = '" + to + "'";*/
			
			String sql = "select airline, DepartDate from " + airline + " where depart ='" + from + "' and arrive ='" + to + "'";
			Statement st = conn.createStatement( );
				
			ResultSet rs = st.executeQuery( sql );
			
			//String airline = null;
			
			while( rs.next() )
			{
				//airline_result.add( rs.getString( "Airline" ) + "," + rs.getString("DepartDate") + "\r\n");
				airline_result.add( rs.getString( "Airline" ) + "    " + rs.getString("DepartDate") + "\r\n");
			}
							
			conn.close();
		} catch( SQLException e){
			System.out.println( "Query airline failed!\n" );
		}	
		return airline_result;
	}

	@Override
	public ArrayList<String> queryFlightRate(String from, String to, String airline) {
		ArrayList<String> price_result = new ArrayList<String>();
		// TODO Auto-generated method stub
		try{
			Connection conn = getConnection( );
			
			String sql1 = "select price from " + airline + " where depart = '" + from + "' and arrive = '" + to + "'";
			String sql2 = "select price from " + airline + " where depart = '" + to + "' and arrive = '" + from + "'";
			Statement st = conn.createStatement( );
				
			ResultSet rs = st.executeQuery( sql1 );
			
			while( rs.next() )
			{
				String price = rs.getString("Price");
				rs = st.executeQuery( sql2 );
				while( rs.next() )
					price_result.add( String.valueOf(Integer.parseInt(rs.getString("Price")) + Integer.parseInt(price)) );
			}
			
			conn.close();
		} catch( SQLException e){
			System.out.print( "Query price failed!\r\n" );
		}
		return price_result;
	}

	
	//return type undefined
	//
	//
	@Override
	public ArrayList<String> queryTicketNum(String from, String to, String airline, String date){
		ArrayList<String> seat_result = new ArrayList<String>();
			//java.sql.Date date) {
		// TODO Auto-generated method stub
		try{
			Connection conn = getConnection( );
			
			String sql = "select TicketNum from " + airline + " where depart = '" + from + "' and arrive = '" + to + "'" + " and departdate = '" + date + "'" ;
			//String sql = "select TicketNum from " + airline + " where depart = '" + from + "' and arrive = '" + to + "'" + " and departdate = " + date ;
			Statement st = conn.createStatement( );
				
			ResultSet rs = st.executeQuery( sql );
			
			short ticketNum = 0;
				
			String str = null;
			//if ( rs.next() )
			//{
				while( rs.next() )
				{
					ticketNum = rs.getShort( "TicketNum" );
					if ( ticketNum != 0 )
						str = ticketNum + "\r\n";
					else
						str =  "No\r\n";
					seat_result.add( str );
					
					//System.out.print( ticketNum + "\r\n" );
				}
			//}
			//else
				//System.out.print( "Do not find appropriate flight!\r\n" );
							
			conn.close();
		} catch( SQLException e){
			System.out.print( "Query ticketNum failed!\r\n" );
		}		
		return seat_result;
	}

	@Override
	public ArrayList<String> bookTicket(String from, String to, String airline, String departdate, String returndate, String username, String phone, String email, String creditno ){
			//java.util.Date date) {
		// TODO Auto-generated method stub
		
		ArrayList<String> book_result = new ArrayList<String>();
		
		FlightInfo[] flight = new FlightInfo[2];
		

		try{
			//check and update airline DB
			if( !CheckTicket(from, to, airline, departdate, returndate) )
			{
				//book_result.add( "Do not have the flight you want\r\n" );
				book_result.add( "No ticket available\r\n" );
				//System.out.println("No ticket available\r\n");
			}
			else if( CheckUser(username, email, from, to, airline ) )
			{
				book_result.add("You have already booked a ticket of this flight\r\n");
				//System.out.println("You have already booked a ticket of this flight\r\n");
			}
			/*else if( !CheckTicketNum( from, to, airline, departdate ) )
			{
				book_result.add("No ticket available\r\n");
			}*/
			else
			{
				//System.out.println("check passed");
				flight = UpdateAirline(from, to, airline, departdate, returndate);

				//update userinfo DB
				Connection conn = getConnection( );

				String sql1 = "insert into userinfo( UserName, DepartFlightNo, ReturnFlightNo, Depart, DepartDate, Arrive, ReturnDate, Airline, DepartAirport, ReturnAirport, Price, Phone, Email, Creditno) " +
						"values( "		
						+ "'" + username + "', "
						+ "'" + flight[0].getFlightNo() + "', "
						+ "'" + flight[1].getFlightNo() + "', "
						+ "'" + flight[0].getDepart() + "', "
						+ "'" + flight[0].getDepartDate() + "', "
						+ "'" + flight[0].getArrive() + "', "
						+ "'" + flight[1].getDepartDate() + "', "
						+ "'" + flight[0].getAirline() + "', "
						+ "'" + flight[0].getAirport() + "', "
						+ "'" + flight[1].getAirport() + "', "
						+ "'" + (Integer.parseInt(flight[0].getPrice()) + Integer.parseInt(flight[1].getPrice())) + "', "
						+ "'" + phone + "', "
						+ "'" + email + "', "
						+ "'" + creditno + "' )";
				
				String sql2 = "select * from userinfo where UserName = '" + username + "'";
	
				Statement st = conn.createStatement( );
					
				st.executeUpdate(sql1);

				ResultSet rs = st.executeQuery( sql2 );
				
				while( rs.next())
					book_result.add( rs.getString("UserName") + "," + 
				rs.getString("TicketNo") + "," +
				rs.getString("DepartFlightNo") + "," +
				rs.getString("ReturnFlightNo") + "," +
				rs.getString("Depart") + "," +
				rs.getString("DepartDate") + "," +
				rs.getString("Arrive") + "," +
				rs.getString("ReturnDate") + "," +
				rs.getString("Airline") + "," +
				rs.getString("DepartAirport") + "," +
				rs.getString("ReturnAirport") + "," +
				rs.getString("Price") + "," +
				rs.getString("Phone") + "," +
				rs.getString("Email") + "," +
				rs.getString("CreditNo") + "\r\n");
				
				conn.close();
			}
			
		} catch( SQLException e){
			//e.printStackTrace();
			System.out.print( "Book ticket failed!\r\n" );
		}
		return book_result;
		
	}
	
	/*public boolean CheckTicketNum( String from, String to, String airline, String date )
	{
		boolean a = false;
		
		try{
			Connection conn = getConnection( );
			
			String sql1 = "select TicketNum from " + airline + " where Depart = '" + from + "' and Arrive = '" + to + "' and DepartDate = '" + date + "'";
			String sql2 = "select TicketNum from " + airline + " where Depart = '" + to + "' and Arrive = '" + from + "' and DepartDate = '" + date + "'";
			
			Statement st = conn.createStatement( );
			
			ResultSet rs = st.executeQuery( sql1 );
			if( rs.next() && (Integer.parseInt( rs.getString("TicketNum") ) > 0) )
			{
				rs = st.executeQuery(sql2);
				if( rs.next() && (Integer.parseInt( rs.getString("TicketNum") ) > 0) )
					a = true;
				else
					a = false;
			}
			else
				a = false;

			conn.close();
		} catch( SQLException e){
			System.out.print( "Check ticket number failed!\r\n" );
		}
		
		return a;
	}*/
	
	public boolean CheckUser(String username, String email, String from, String to, String airline )
	{
		boolean a = false;
		
		if(airline.equalsIgnoreCase("china_eastern"))
			airline = "China eastern";
		else if(airline.equalsIgnoreCase("china_southern"))
			airline = "China southern";
		else if(airline.equalsIgnoreCase("virgin_blue"))
			airline = "Virgin blue";
		
		try{
			Connection conn = getConnection( );
			
			String sql = "select * from userinfo" + " where Depart = '" + from + "' and Arrive = '" + to + "' and Airline = '" + airline + "' and UserName = '" + username + "' and Email = '" + email + "'";
			
			Statement st = conn.createStatement( );
			
			ResultSet rs = st.executeQuery( sql );
			if( rs.next() )
			{
				a =  true;
				//System.out.print("You are in the DB\r\n");
			}
			else
			{
				a = false;
				//System.out.print("You are not in the DB\r\n");
			}

			conn.close();
		} catch( SQLException e){
			System.out.print( "Check user information failed!\r\n" );
		}
		return a;
	}
	
	public FlightInfo[] UpdateAirline(String from, String to, String airline, String departdate, String returndate)
	{
		FlightInfo[] flight = new FlightInfo[2];
		flight[0] = new FlightInfo();
		flight[1] = new FlightInfo();
		
		try{
			Connection conn = getConnection( );
			
			//String sql = "select * from " + airline + " where depart = '" + from + "' and arrive = '" + to + "' and DepartDate = '" + departdate + "' union "
			//		 + "select * from " + airline + " where depart = '" + to + "' and arrive = '" + from + "' and DepartDate = '" + returndate + "'";
			String sql1 = "update " + airline + " set TicketNum = TicketNum-1 where depart = '" + from + "' and arrive = '" + to + "' and DepartDate = '" + departdate + "'";
			String sql2 = "update " + airline + " set TicketNum = TicketNum-1 where depart = '" + to + "' and arrive = '" + from + "' and DepartDate = '" + returndate + "'";
			
			Statement st = conn.createStatement( );
			
			//update info
			st.executeUpdate(sql1);
			
			st.executeUpdate( sql2 );
			
			ResultSet rs1 = st.executeQuery(
					"select * from " + airline + " where depart = '" + from + "' and arrive = '" + to + "' and DepartDate = '" + departdate + "'");
			//System.out.println(rs1.getString("FlightNo"));
			while( rs1.next() )
			{
				flight[0].setFlightNo(rs1.getString("FlightNo"));
				flight[0].setAirline(rs1.getString("Airline"));
				flight[0].setDepart(rs1.getString("Depart"));
				flight[0].setDepartDate( rs1.getDate("DepartDate"));
				flight[0].setArrive( rs1.getString("Arrive"));
				flight[0].setAirport( rs1.getString("Airport"));
				flight[0].setPrice( rs1.getString("Price"));
			}

			ResultSet rs2 = st.executeQuery(
					"select * from " + airline + " where depart = '" + to + "' and arrive = '" + from + "' and DepartDate = '" + returndate + "'");
			
			while( rs2.next() )
			{
				flight[1].setFlightNo(rs2.getString("FlightNo"));
				flight[1].setAirline(rs2.getString("Airline"));
				flight[1].setDepart(rs2.getString("Depart"));
				flight[1].setDepartDate( rs2.getDate("DepartDate"));
				flight[1].setArrive( rs2.getString("Arrive"));
				flight[1].setAirport( rs2.getString("Airport"));
				flight[1].setPrice( rs2.getString("Price"));
			}
			
			//System.out.println("success");
			
			conn.close();
		} catch( SQLException e){
			//e.printStackTrace();
			System.out.print( "Update airline informaion failed!\r\n" );
		}
		return flight;
	}
	
	public boolean CheckTicket(String from, String to, String airline, String departdate, String returndate)
	{
		boolean a = false;
		try{
			Connection conn = getConnection( );
			
			String sql1 = "select TicketNum from " + airline + " where depart = '" + from + "' and arrive = '" + to + "' and DepartDate = '" + departdate + "'";
			String sql2 = "select TicketNum from " + airline + " where depart = '" + to + "' and arrive = '" + from + "' and DepartDate = '" + returndate + "'";
			Statement st = conn.createStatement( );
			
			ResultSet rs1 = st.executeQuery( sql1 );
			if( rs1.next() && (Integer.parseInt( rs1.getString("TicketNum") ) > 0) )
			{
				//System.out.println((Integer.parseInt( rs1.getString("TicketNum") ) > 0));
				ResultSet rs2 = st.executeQuery( sql2 );
				if( rs2.next() && (Integer.parseInt( rs2.getString("TicketNum") ) > 0) )
				{
					//System.out.println((Integer.parseInt( rs2.getString("TicketNum") ) > 0));
					a =  true;
				}else
				{
					a = false;
				}
			}
			else
			{
				a = false;
			}
			conn.close();
		} catch( SQLException e){
			System.out.print( "Check ticket information failed!\r\n" );
		}
		return a;
	}
}
