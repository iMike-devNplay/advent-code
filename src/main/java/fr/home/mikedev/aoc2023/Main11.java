package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main11 extends MainDay 
{
    int matrixHeight = 140;
    int matrixWidth = 140;
    char[][] puzzleMatrix = new char[matrixHeight][matrixWidth];
    
    List<Integer> emptyRowIndexes;
    List<Integer> emptyColumnIndexes;
    List<Pair<Integer>> galaxies;
    
	public Main11(String title, String year) {super(title, year, "11");}
	
	public void initData()
	{
	    emptyRowIndexes = new ArrayList<Integer>();
	    emptyColumnIndexes = new ArrayList<Integer>();
	    galaxies = new ArrayList<Pair<Integer>>();
	}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    int r = 0;
			while((line = reader.readLine()) != null)
			{
			    if (line.equals(StringUtils.rightPad("", matrixWidth, '.'))) emptyRowIndexes.add(Integer.valueOf(r));
			    puzzleMatrix[r++] = line.toCharArray();
			}
			
			for(int c = 0; c < matrixWidth; c++)
			{
			    String column = "";
			    for (int r2 = 0; r2 < matrixHeight; r2++)
			        column = column + puzzleMatrix[r2][c];
			    
			    if (column.equals(StringUtils.rightPad("", matrixHeight, '.'))) emptyColumnIndexes.add(Integer.valueOf(c));
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
	    initData();
	    retrieveData();
	    	    
	    /*char[][] expandedUniverse = expandUniverse();
	    log(MatrixUtils.matrixToString(expandedUniverse));
	    getGalaxies(expandedUniverse);*/
	    
	    getGalaxies(puzzleMatrix);
	    
	    long sum = 0;
	    for (int g1 = 0; g1 < galaxies.size(); g1++)
	        for (int g2 = g1+1; g2 < galaxies.size(); g2++)
	            sum += distanceGrid(galaxies.get(g1).getV1(), galaxies.get(g1).getV2(), galaxies.get(g2).getV1(), galaxies.get(g2).getV2(), 1);

		setResultPart1(sum); // 10313550
	}
	
	public void doPart2()
	{
	    long sum = 0;
        for (int g1 = 0; g1 < galaxies.size(); g1++)
            for (int g2 = g1+1; g2 < galaxies.size(); g2++)
                sum += distanceGrid(galaxies.get(g1).getV1(), galaxies.get(g1).getV2(), galaxies.get(g2).getV1(), galaxies.get(g2).getV2(), 999999);
        
	    setResultPart2(sum);
	    // 82000292 too low
	    // 611998701561 too high
	}
	
	/*char[][] expandUniverse()
	{
	    char[][] expanded = new char[matrixHeight][matrixWidth+emptyColumnIndexes.size()];

	    for (int r = 0; r < matrixHeight; r++)
        {
	        for (int c = 0, j = 0; c < matrixWidth; c++, j++)
	        {
	            if (emptyColumnIndexes.contains(Integer.valueOf(c))) expanded[r][j++] = '.';
	            expanded[r][j] = puzzleMatrix[r][c];
	        }
        }
	    
	    char[][] expanded2 = new char[matrixHeight+emptyRowIndexes.size()][matrixWidth+emptyColumnIndexes.size()];
	    for (int r = 0, i = 0; r < matrixHeight; r++, i++)
        {
            if (emptyRowIndexes.contains(Integer.valueOf(r))) expanded2[i++] = StringUtils.rightPad("", matrixWidth+emptyColumnIndexes.size(), '.').toCharArray();
            String s = String.valueOf(expanded[r]);
            expanded2[i] =  StringUtils.rightPad(s, matrixWidth+emptyColumnIndexes.size(), '.').toCharArray();
        }
	    return expanded2;
	}*/
	
	void getGalaxies(char[][] matrix)
	{
	    for (int r = 0; r < matrix.length; r++)
	        for (int c = 0; c < matrix[r].length; c++)
	            if (matrix[r][c] == '#') galaxies.add(Pair.<Integer>builder().v1(Integer.valueOf(r)).v2(Integer.valueOf(c)).build());
	}
	
    public long distanceGrid(int x1, int y1, int x2, int y2, long emptyValue)
    {
        int cntEmptyRows = 0;
        int cntEmptyColumns = 0;
        
        if (x1 < x2) for (int r = x1; r < x2; r++) { if (emptyRowIndexes.contains(Integer.valueOf(r))) cntEmptyRows++;}
        else if (x1 > x2) for (int r = x2; r < x1; r++) {if (emptyRowIndexes.contains(Integer.valueOf(r))) cntEmptyRows++;}
        
        if (y1 < y2) for (int c = y1; c < y2; c++) { if (emptyColumnIndexes.contains(Integer.valueOf(c))) cntEmptyColumns++;}
        else if (y1 > y2) for (int c = y2; c < y1; c++) { if (emptyColumnIndexes.contains(Integer.valueOf(c))) cntEmptyColumns++;}
                
        return Math.abs(x1-x2) + Math.abs(y1-y2) + cntEmptyColumns*emptyValue + cntEmptyRows*emptyValue;
    }
}
