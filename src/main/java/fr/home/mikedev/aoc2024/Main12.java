package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;

import fr.home.mikedev.common.MainDay;

public class Main12 extends MainDay 
{
	private char[][] puzzleMatrix = new char[10][10];
	
	public Main12(String title, String year) {super(title, year, "12");}
	
	public void retrieveData()
	{
		String line = null;	
		int i = 0;
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null)
			{
				puzzleMatrix[i++] = line.toCharArray();
				log(line);
			}
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
