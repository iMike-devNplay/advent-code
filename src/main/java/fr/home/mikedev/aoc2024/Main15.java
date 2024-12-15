package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.MatrixUtils;

public class Main15 extends MainDay 
{
    int matrixSize = 10;
    char[][] puzzlMatrix = new char[matrixSize][matrixSize];
    
    String instructions;
	public Main15(String title, String year) {super(title, year, "15");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    int i = 0;
            while((line = reader.readLine()) != null)
            {
                if (i < 10) puzzlMatrix[i] = line.toCharArray();
                else if (i > 10) instructions += line;
                i++;
            }
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
		
	public void doPart1()
	{
	    instructions = "";
		retrieveData();
		log(MatrixUtils.matrixToString(puzzlMatrix));
		log(instructions);
		displayResultPart1(0);
	}
	
	public void doPart2()
	{
		//retrieveData();
		displayResultPart2(0);
	}
}
