package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main03 {

	private final String dataFileName = "data-day03.txt";
	
	public static void main(String[] args) throws Exception 
	{
		Main03 m = new Main03();
		m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		StringBuffer allCorruptedMemory = new StringBuffer();
		while ((line = reader.readLine()) != null) 
		{
			allCorruptedMemory.append(line);
		}
		
		Long finalResult = cleanMemory(allCorruptedMemory.toString());
	    System.out.println(finalResult);
	    	
		reader.close();
	}
		
	public void doSecond() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		StringBuffer allCorruptedMemory = new StringBuffer();
		while ((line = reader.readLine()) != null) 
		{
			allCorruptedMemory.append(line);
		}
		StringBuffer allCleanMemory = new StringBuffer();
		// Get all part between a do() and a don't()
		// 1 : get part from the beginning of file
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
	    System.out.println(finalResult);
	    
		reader.close();
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
