package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.home.mikedev.common.MainDay;

public class Main03 extends MainDay 
{
	StringBuffer allCorruptedMemory = new StringBuffer();
	
	public Main03(String title, String year) {super(title, year, "03");}
	
	public void retrieveData()
	{
		String line = null;
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null) 
			{
				allCorruptedMemory.append(line);
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
		Long finalResult = cleanMemory(allCorruptedMemory.toString());
		setResultPart1(finalResult);

	}
		
	public void doPart2()
	{
		StringBuffer allCleanMemory = new StringBuffer();
		// Get all part between a do() and a don't()
		// 1 : get part from the beginning of file until first don't
		int i = allCorruptedMemory.indexOf("don't()");
		if (i > 0) allCleanMemory.append(allCorruptedMemory.substring(0, i));
		
		boolean endOfMemory = false;
		int start = i;
		while(!endOfMemory)
		{
			start = allCorruptedMemory.indexOf("do()", start);
			int end = allCorruptedMemory.indexOf("don't()", start);
			if (start > 0)
			{
				if (end > 0) allCleanMemory.append(allCorruptedMemory.substring(start, end));
				else
				{
					allCleanMemory.append(allCorruptedMemory.substring(start));
					endOfMemory = true;
				}
			}
			else endOfMemory = true;
			start = end;
		}
		Long finalResult = cleanMemory(allCleanMemory.toString());
		setResultPart2(finalResult);
	}
	
	public Long cleanMemory(String corruptedMemory)
	{
		Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
	    Matcher matcher = pattern.matcher(corruptedMemory);
	    
	    List<Long> results = new ArrayList<Long>();
	    
	    matcher.results().forEach(m -> {
	    	Pattern pdigit = Pattern.compile("\\d{1,3}");
	    	Matcher mdigit = pdigit.matcher(m.group());
	    	mdigit.find();
	    	Long d1 = Long.valueOf(mdigit.group());
	    	mdigit.find();
	    	Long d2 = Long.valueOf(mdigit.group());
	    	results.add(d1*d2);
	    });

	    Long finalResult = Long.valueOf(0);
	    for (int i = 0; i < results.size(); i++) finalResult += results.get(i);
	    
	    return finalResult;
	}
}
