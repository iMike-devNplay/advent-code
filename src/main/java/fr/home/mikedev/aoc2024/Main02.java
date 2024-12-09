package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import fr.home.mikedev.common.MainDay;

public class Main02 extends MainDay 
{
	List<List<Long>> reports = new ArrayList<List<Long>>();
	List<List<Long>> badReports = new ArrayList<List<Long>>();
	
	public Main02(String title, String year) {super(title, year, "02");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null) 
			{
				List<Long> levels = new ArrayList<Long>();
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) levels.add(Long.parseLong(st.nextToken()));
				if (checkReport(levels)) reports.add(levels);
				else badReports.add(levels);
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
		displayResultPart1(reports.size());
	}
	
	public void doPart2()
	{
		for (int i = 0; i < badReports.size(); i++)
		{
			if (fixReport(badReports.get(i))) reports.add(badReports.get(i));			
		}
		displayResultPart2(reports.size());
	}
	
	public boolean checkReport(List<Long> report)
	{
		boolean increase = true;
		Long initValue = Long.valueOf(0);
		for (int i = 0 ; i < report.size() && increase; i++)
		{
			if (initValue >= report.get(i)) increase = false;
			if (initValue != 0)
			{
				Long interval = report.get(i)-initValue;
				if (interval < 1 || interval > 3) increase = false;
			}
			initValue = report.get(i);
		}
		
		boolean decrease = true;
		if (!increase)
		{
			initValue = Long.valueOf(100);
			for (int i = 0 ; i < report.size() && decrease; i++)
			{
				if (initValue <= report.get(i)) decrease = false;
				if (initValue != 100)
				{
					Long interval = initValue-report.get(i);
					if (interval < 1 || interval > 3) decrease = false;
				}
				initValue = report.get(i);
			}
		}
		else decrease = false;
		return increase || decrease;
	}
	
	public boolean fixReport(List<Long> report)
	{
		boolean fixed = false;
		for (int i = 0; i < report.size() && ! fixed; i++)
		{
			List<Long> newReport = new ArrayList<Long>(report);
			newReport.remove(i);
			fixed = checkReport(newReport);
		}
		return fixed;
	}
}
