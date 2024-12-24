package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.home.mikedev.common.MainDay;

public class Main23 extends MainDay 
{
	Map<String, List<String>> connections;
	Map<String, List<String>> uniConnections;
	Map<String, List<String>> tripletConnection;
	
	public Main23(String title, String year) {super(title, year, "23");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
            while((line = reader.readLine()) != null)
            {
                String[] computers = line.split("-");
                connections.put(line, Arrays.asList(computers));
                
                List<String> cs;
                if ((cs = uniConnections.get(computers[0])) == null) cs = new ArrayList<String>();
                cs.add(computers[1]);
                uniConnections.put(computers[0], cs);
                
                if ((cs = uniConnections.get(computers[1])) == null) cs = new ArrayList<String>();
                cs.add(computers[0]);
                uniConnections.put(computers[1], cs);
            }
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
            ex.printStackTrace();
		}
	}
	
	void initData()
	{
		connections = new HashMap<String, List<String>>();
		uniConnections = new HashMap<String, List<String>>();
		tripletConnection = new HashMap<String, List<String>>();
	}
	public void doPart1()
	{
		initData();
		retrieveData();
				
		for(String c1 : uniConnections.keySet())
		{
			for (String c2 : uniConnections.get(c1))
			{
				List<String> intersect = uniConnections.get(c1).stream().filter(uniConnections.get(c2)::contains).collect(Collectors.toList());
				for (String i : intersect)
				{
					List<String> t = new ArrayList<String>();
					t.add(c1);
					t.add(c2);
					t.add(i);
					Collections.sort(t);
					tripletConnection.put(t.toString(), t);
				}
			}	
		}
		
		// filter by Chief Historian computer (start by t)
		int count = 0;
		for (String triplet : tripletConnection.keySet())
		{
			boolean found = false;
			for (String c : tripletConnection.get(triplet))
				if (c.startsWith("t") && !found) 
				{
					count++;
					found = true;
				}
		}
		
		setResultPart1(count); //1154
	}
	
	public void doPart2()
	{
		//retrieveData();
		List<String> biggestOne = new ArrayList<String>();
		int maxSize = 0;
		Map<String, Map<Integer, List<String>>> interconnectedGroups = new HashMap<String, Map<Integer, List<String>>>();
		// for each computer
		for (String c1 : uniConnections.keySet())
		{
			// Create the groups list of all connected of connected(minus the computer)
			List<List<String>> listOfRelations = new ArrayList<List<String>>();
			for (String c2 : uniConnections.get(c1))
			{
				List<String> tmp = new ArrayList<String>();
				tmp.add(c2);
				for (String c : uniConnections.get(c2))
					if (!c.equals(c1)) tmp.add(c);
				listOfRelations.add(tmp);
			}

			// now count numof occurence of a computer for each connected groups
			// and keep this max size value
			
			Map<Integer, List<String>> countGroups = new HashMap<Integer, List<String>>();
			for (List<String> l : listOfRelations)
			{
				int count = 0;
				for (String c : l)
				{
					for (List<String> lsearch : listOfRelations)
						if (lsearch.contains(c)) count++;
				}
				if (maxSize < count) maxSize = count;
				countGroups.put(count, l);
			}
			interconnectedGroups.put(c1, countGroups);
		}
			
		// work only groups with the maxSize as key
		// then keep only computer which appear most
		Map<String, Integer> computerGroups = new HashMap<String, Integer>();
		int maxComputer = 0;
		for (String grp : interconnectedGroups.keySet())
		{
			List<String> l = interconnectedGroups.get(grp).get(maxSize);
			if (l != null) 
			{
				for (String c : l)
				{
					Integer count;
					if ((count = computerGroups.get(c)) != null) count++;
					else count = 1;
					if (maxComputer < count) maxComputer = count;
					computerGroups.put(c, count);
				}
			}
		}
		
		// and finally keep only computers with the max values
		final Integer max = Integer.valueOf(maxComputer);
		biggestOne = computerGroups.keySet().stream().filter(count -> max == computerGroups.get(count)).sorted().toList();
		String password = biggestOne.toString().substring(1, biggestOne.toString().length()-1).replace(" ", "");
	    setResultPart2(password);
	}
}
