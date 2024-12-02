package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main02 {
	private final String dataFileName = "data-day02.txt";
	
	public static void main(String[] args) throws Exception 
	{
		Main02 m = new Main02();
		m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		List<List<Long>> reports = new ArrayList<List<Long>>();
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			List<Long> levels = new ArrayList<Long>();
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) levels.add(Long.parseLong(st.nextToken()));
			if (checkReport(levels)) reports.add(levels);
		}
		reader.close();
		System.out.println(reports.size());
	}
	
	public void doSecond() throws Exception
	{
		List<List<Long>> reports = new ArrayList<List<Long>>();
		List<List<Long>> badReports = new ArrayList<List<Long>>();
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			List<Long> report = new ArrayList<Long>();
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) report.add(Long.parseLong(st.nextToken()));
			if (checkReport(report)) reports.add(report);
			else badReports.add(report);
		}
		reader.close();
		
		for (int i = 0; i < badReports.size(); i++)
		{
			if (fixReport(badReports.get(i))) reports.add(badReports.get(i));			
		}
		
		System.out.println(reports.size());
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
