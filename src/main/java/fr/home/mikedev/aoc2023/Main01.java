package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;

import fr.home.mikedev.common.MainDay;

public class Main01 extends MainDay 
{
	public Main01(String title, String year) {super(title, year, "01");}
	
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
		displayResultPart1(0);
	}
	
	public void doPart2()
	{
		//retrieveData();
		displayResultPart2(0);
	}
}
