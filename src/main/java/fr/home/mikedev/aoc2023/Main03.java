package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.MatrixUtils;
import fr.home.mikedev.common.Pair;

public class Main03 extends MainDay 
{
    int matrixSize = 140;
    char[][] puzzleMatrix = new char[matrixSize][matrixSize];
    
    List<String> parts;
    Map<Pair<Integer>, List<String>> gears;
    
	public Main03(String title, String year) {super(title, year, "03");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    int i = 0;
			while ((line = reader.readLine()) != null)
			{
			    puzzleMatrix[i++] = line.toCharArray();
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
		//log(MatrixUtils.matrixToString(puzzleMatrix));
		parts = new ArrayList<String>();
		
        String currentPart = "";
        boolean isAPart = false;
		for (int l = 0; l < matrixSize; l++)
		    for (int c = 0; c < matrixSize; c++)
		    {
		        String currentDigitStr = String.valueOf(puzzleMatrix[l][c]);
		        Pair<Integer> currentDigit = Pair.<Integer>builder().v1(l).v2(c).o(currentDigitStr).build();
		        
		        if (isDigit(puzzleMatrix[l][c]))
		        {
		            if (adjacentToSymbol(currentDigit)) isAPart = true;
		            currentPart += currentDigitStr;
		        }
		        else
		        {
		            if (!currentPart.equals("")) if (isAPart) parts.add(currentPart);
		            currentPart = "";
		            isAPart = false;
		        }
		    }
		
		int sum = 0;
		for (String p : parts)
		    sum += Integer.parseInt(p);
		
		setResultPart1(sum);
	}
	
	public void doPart2()
	{
		//retrieveData();
	    
	    gears = new HashMap<Pair<Integer>, List<String>>();
        
        String currentPart = "";
        Pair<Integer> currentGear = null;
        boolean isGeared = false;
        for (int l = 0; l < matrixSize; l++)
            for (int c = 0; c < matrixSize; c++)
            {
                String currentDigitStr = String.valueOf(puzzleMatrix[l][c]);
                Pair<Integer> currentDigit = Pair.<Integer>builder().v1(l).v2(c).o(currentDigitStr).build();
                
                if (isDigit(puzzleMatrix[l][c]))
                {
                    Pair<Integer> testGear = null;
                    if ((testGear = adjacentToGear(currentDigit)) != null)
                    {
                        currentGear = testGear;
                        isGeared = true;
                        List<String> gearedParts = gears.get(currentGear);
                        if (gearedParts == null) gearedParts = new ArrayList<String>();
                        gears.put(currentGear, gearedParts);
                    }
                    currentPart += currentDigitStr;
                }
                else
                {
                    if (!currentPart.equals("")) 
                        if (isGeared && currentGear != null) gears.get(currentGear).add(currentPart);
                    currentPart = "";
                    currentGear = null;
                    isGeared = false;
                }
            }

        long sum = 0;
        for (Pair<Integer> g : gears.keySet())
        {
            if (gears.get(g).size() == 2)
            {
                long ratio = 1;
                for (String p : gears.get(g))
                    ratio *= Long.parseLong(p);
                sum += ratio;
            }
        }
	    
	    
        setResultPart2(sum);
	}
	
	public boolean adjacentToSymbol(Pair<Integer> digit)
	{
	    int l = digit.getV1().intValue();
	    int c = digit.getV2().intValue();
	    
	    if (       (!MatrixUtils.isOutsideMatrix(l-1, c-1, matrixSize) &&  isSymbol(puzzleMatrix[l-1][c-1]))
	            || (!MatrixUtils.isOutsideMatrix(l-1, c, matrixSize) &&  isSymbol(puzzleMatrix[l-1][c]))
	            || (!MatrixUtils.isOutsideMatrix(l-1, c+1, matrixSize) &&  isSymbol(puzzleMatrix[l-1][c+1]))
	            || (!MatrixUtils.isOutsideMatrix(l, c+1, matrixSize) &&  isSymbol(puzzleMatrix[l][c+1]))
	            || (!MatrixUtils.isOutsideMatrix(l+1, c+1, matrixSize) &&  isSymbol(puzzleMatrix[l+1][c+1]))
	            || (!MatrixUtils.isOutsideMatrix(l+1, c, matrixSize) &&  isSymbol(puzzleMatrix[l+1][c]))
	            || (!MatrixUtils.isOutsideMatrix(l+1, c-1, matrixSize) &&  isSymbol(puzzleMatrix[l+1][c-1]))
	            || (!MatrixUtils.isOutsideMatrix(l, c-1, matrixSize) &&  isSymbol(puzzleMatrix[l][c-1]))
	       ) return true;
	       else return false;
	}
	
	   public Pair<Integer> adjacentToGear(Pair<Integer> digit)
	    {
	        int l = digit.getV1().intValue();
	        int c = digit.getV2().intValue();
	        
	             if    (!MatrixUtils.isOutsideMatrix(l-1, c-1, matrixSize) &&  isGear(puzzleMatrix[l-1][c-1])) return Pair.<Integer>builder().v1(l-1).v2(c-1).build();
	        else if    (!MatrixUtils.isOutsideMatrix(l-1, c, matrixSize) &&  isGear(puzzleMatrix[l-1][c]))     return Pair.<Integer>builder().v1(l-1).v2(c).build();
	        else if    (!MatrixUtils.isOutsideMatrix(l-1, c+1, matrixSize) &&  isGear(puzzleMatrix[l-1][c+1])) return Pair.<Integer>builder().v1(l-1).v2(c+1).build();
	        else if    (!MatrixUtils.isOutsideMatrix(l, c+1, matrixSize) &&  isGear(puzzleMatrix[l][c+1]))     return Pair.<Integer>builder().v1(l).v2(c+1).build();
	        else if    (!MatrixUtils.isOutsideMatrix(l+1, c+1, matrixSize) &&  isGear(puzzleMatrix[l+1][c+1])) return Pair.<Integer>builder().v1(l+1).v2(c+1).build();
	        else if    (!MatrixUtils.isOutsideMatrix(l+1, c, matrixSize) &&  isGear(puzzleMatrix[l+1][c]))     return Pair.<Integer>builder().v1(l+1).v2(c).build();
	        else if    (!MatrixUtils.isOutsideMatrix(l+1, c-1, matrixSize) &&  isGear(puzzleMatrix[l+1][c-1])) return Pair.<Integer>builder().v1(l+1).v2(c-1).build();
	        else if    (!MatrixUtils.isOutsideMatrix(l, c-1, matrixSize) &&  isGear(puzzleMatrix[l][c-1]))     return Pair.<Integer>builder().v1(l).v2(c-1).build();
	        else return null;
	    }
	   
	public boolean isSymbol(char c)
	{
	    if (c != '.' && c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9') return true;
	    else return false;
	}
	
	public boolean isGear(char c)
	{
	    if (c == '*') return true;
	    else return false;
	}
	   
    public boolean isDigit(char c)
    {
        if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') return true;
        else return false;
    }
}
