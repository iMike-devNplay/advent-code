package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;

import fr.home.mikedev.common.MainDay;

public class Main22 extends MainDay 
{

	public Main22(String title, String year) {super(title, year, "22");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			log(line);
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
		
	public void doPart1()
	{
		retrieveData();
	}
	
	public void doPart2()
	{
		//retrieveData();
	}
}
