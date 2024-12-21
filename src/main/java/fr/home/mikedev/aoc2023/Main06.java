package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;

import fr.home.mikedev.common.MainDay;

public class Main06 extends MainDay 
{
	public Main06(String title, String year) {super(title, year, "06");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while((line = reader.readLine()) != null)
			{
			    log(line);
			}
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
			ex.printStackTrace();
		}
	}
		
	public void doPart1()
	{
	    retrieveData();
		setResultPart1(0);
	}
	
	public void doPart2()
	{
		//retrieveData();
	    setResultPart2(0);
	}
}
