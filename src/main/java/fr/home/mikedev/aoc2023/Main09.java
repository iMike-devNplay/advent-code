package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import fr.home.mikedev.common.MainDay;

public class Main09 extends MainDay 
{
    List<List<Long>> puzzleData;
    long    sumOfLastValue;
    
	public Main09(String title, String year) {super(title, year, "09");}
	
	public void initData()
	{
	    puzzleData = new ArrayList<List<Long>>();
	    sumOfLastValue = 0;
	}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while((line = reader.readLine()) != null)
			{
			    String[] s = line.split("\\s+");
			    List<Long> report = new ArrayList<Long>();
			    for (int i = 0; i < s.length; i++)
			        report.add(Long.valueOf(s[i]));
			    puzzleData.add(report);
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
	    
	    for (List<Long> report : puzzleData)
	        downToZero(report);
		setResultPart1(sumOfLastValue); // 2043183816
	}
	
	public void doPart2()
	{
	    sumOfLastValue = 0;
        for (List<Long> report : puzzleData)
            downToZero(report.reversed());
	    setResultPart2(sumOfLastValue); // 1118
	}
	
	private boolean downToZero(List<Long> report)
	{
	    if (report.stream().allMatch(v -> v.equals(Long.valueOf(0)))) return true;
	    Long current = report.get(0);
	    List<Long> newReport = new ArrayList<Long>();
	    for (int i = 1 ; i < report.size(); i++)
	    {
	        newReport.add(report.get(i)-current);
	        current = report.get(i);
	    }
	    sumOfLastValue += current;
	    if (downToZero(newReport)) return true;
	    else return false;
	}
}
