package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import fr.home.mikedev.common.MainDay;

public class Main01 extends MainDay 
{
	List<Long> locationIdGroup1 = new ArrayList<Long>();
	List<Long> locationIdGroup2 = new ArrayList<Long>();
	
	public Main01(String title, String year) {super(title, year, "01");}

	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null) 
			{
				StringTokenizer st = new StringTokenizer(line);
				locationIdGroup1.add(Long.parseLong(st.nextToken()));
				locationIdGroup2.add(Long.parseLong(st.nextToken()));
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
		Collections.sort(locationIdGroup1);
		Collections.sort(locationIdGroup2);
		
		long ecart = 0;
		for (int i = 0; i < locationIdGroup1.size(); i++)
		{
			ecart += Math.abs(locationIdGroup1.get(i)-locationIdGroup2.get(i));
		}
		displayResultPart1(ecart);
	}

	public void doPart2()
	{
		long ecart = 0;
		for (int i = 0; i < locationIdGroup1.size(); i++)
		{
			Long item = locationIdGroup1.get(i);
			long count = locationIdGroup2.stream().filter(l -> l.equals(item)).count();
			ecart += item * count;
		}
		displayResultPart2(ecart);
	}
}
