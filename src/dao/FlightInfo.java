package dao;

import java.sql.Date;

public class FlightInfo {
	public String FlightNo= null;
	public String Airline = null;
	public String Depart = null;
	public Date DepartDate = null;
	public String Arrive = null;
	public Date ArriveDate = null;
	public String Airport = null;
	public String Price = null;
	public short TicketNo = 0;
	public short TicketNum = 0;
	
	public String getFlightNo()
	{
		return FlightNo;
	}
	public String getAirline()
	{
		return Airline;
	}
	public String getDepart()
	{
		return Depart;
	}
	public Date getDepartDate()
	{
		return DepartDate;
	}
	public String getArrive()
	{
		return Arrive;
	}
	public Date getArriveDate()
	{
		return ArriveDate;
	}
	public String getAirport()
	{
		return Airport;
	}
	public String getPrice()
	{
		return Price;
	}
	public short getTicketNo()
	{
		return TicketNo;
	}
	public short getTicketNum()
	{
		return TicketNum;
	}
	
	public void setFlightNo(String str)
	{
		this.FlightNo = str;
		//System.out.println(str);
		//System.out.println(FlightNo);
	}
	public void setAirline(String str)
	{
		Airline = str;
	}
	public void setDepart(String str)
	{
		Depart = str;
	}
	public void setDepartDate(Date str)
	{
		DepartDate = str;
	}
	public void setArrive(String str)
	{
		Arrive = str;
	}
	public void setArriveDate(Date str)
	{
		ArriveDate = str;
	}
	public void setAirport(String str)
	{
		Airport = str;
	}
	public void setPrice( String digit )
	{
		Price = digit;
	}
	public void setTicketNo(short digit)
	{
		TicketNo = digit;
	}
	public void setTicketNum(short digit)
	{
		TicketNum = digit;
	}
}
