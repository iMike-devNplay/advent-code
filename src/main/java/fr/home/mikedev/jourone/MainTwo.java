package fr.home.mikedev.jourone;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainTwo {

	public static void main(String[] args) throws Exception 
	{
		MainTwo m = new MainTwo();
		m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		List<List<Long>> reports = new ArrayList<List<Long>>();
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource("day2-data.txt").toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			List<Long> levels = new ArrayList<Long>();
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) levels.add(Long.parseLong(st.nextToken()));
			if (checkReports(levels)) reports.add(levels);
		}
		reader.close();
		System.out.println(reports.size());
	}
	
	public boolean checkReports(List<Long> levels)
	{
		boolean increase = true;
		Long initValue = Long.valueOf(0);
		for (int i = 0 ; i < levels.size() && increase; i++)
		{
			if (initValue >= levels.get(i)) increase = false;
			if (initValue != 0)
			{
				Long interval = levels.get(i)-initValue;
				if (interval < 1 || interval > 3) increase = false;
			}
			initValue = levels.get(i);
		}
		
		boolean decrease = true;
		if (!increase)
		{
			initValue = Long.valueOf(100);
			for (int i = 0 ; i < levels.size() && decrease; i++)
			{
				if (initValue <= levels.get(i)) decrease = false;
				if (initValue != 100)
				{
					Long interval = initValue-levels.get(i);
					if (interval < 1 || interval > 3) decrease = false;
				}
				initValue = levels.get(i);
			}
		}
		else decrease = false;
		//System.out.println(levels.toString() + (increase));
		//System.out.println(levels.toString() + (decrease));
		return increase || decrease;
	}
	
	public void doSecond() throws Exception
	{
		List<List<Long>> reports = new ArrayList<List<Long>>();
		List<List<Long>> badReports = new ArrayList<List<Long>>();
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource("day2-data.txt").toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			List<Long> levels = new ArrayList<Long>();
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) levels.add(Long.parseLong(st.nextToken()));
			if (checkReports(levels)) reports.add(levels);
			else badReports.add(levels);
		}
		reader.close();
		
		for (int i = 0; i <badReports.size(); i++)
		{
			if (fixReport(badReports.get(i))) reports.add(badReports.get(i));			
		}
		
		System.out.println(reports.size());
	}
	
	public boolean fixReport(List<Long> report)
	{
		boolean fixed = false;
		for (int i = 0; i < report.size() && ! fixed; i++)
		{
			List<Long> newReport = new ArrayList<Long>(report);
			newReport.remove(i);
			fixed = checkReports(newReport);
		}
		return fixed;
	}
}
