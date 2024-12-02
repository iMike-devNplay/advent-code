package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main01 {
	private final String dataFileName = "data-day01.txt";
	
	public static void main(String[] args) throws Exception 
	{
		Main01 m = new Main01();
		m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		List<Long> list1 = new ArrayList<Long>();
		List<Long> list2 = new ArrayList<Long>();
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			StringTokenizer st = new StringTokenizer(line);
			list1.add(Long.parseLong(st.nextToken()));
			list2.add(Long.parseLong(st.nextToken()));
		}
		reader.close();
		Collections.sort(list1);
		Collections.sort(list2);
		
		long ecart = 0;
		for (int i = 0; i < list1.size(); i++)
		{
			ecart += Math.abs(list1.get(i)-list2.get(i));
		}
		System.out.println(ecart);
	}
	
	public void doSecond() throws Exception
	{
		List<Long> list1 = new ArrayList<Long>();
		List<Long> list2 = new ArrayList<Long>();
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			StringTokenizer st = new StringTokenizer(line);
			list1.add(Long.parseLong(st.nextToken()));
			list2.add(Long.parseLong(st.nextToken()));
		}
		reader.close();
		long ecart = 0;
		for (int i = 0; i < list1.size(); i++)
		{
			Long item = list1.get(i);
			long count = list2.stream().filter(l -> l.equals(item)).count();
			ecart += item * count;
		}
		System.out.println(ecart);
	}
}
