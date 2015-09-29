package dao;

import java.sql.Date;
import java.util.ArrayList;

public interface DAO {
	
	//void query( String table );
	//void insert( String table );
	//void update( String table );
	//void delete( String table );
	
	ArrayList<String> queryCity(String airline);
	ArrayList<String> queryAirline( String from, String to, String airline );
	ArrayList<String> queryFlightRate( String from, String to , String airline );
	ArrayList<String> queryTicketNum( String from, String to, String airline, String date );
	ArrayList<String> bookTicket( String from, String to, String airline, String departdate, String returndate, String username, String phone, String email, String creditno );
	
}