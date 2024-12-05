package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import fr.home.mikedev.days.utils.Day5Rule;

public class Main05 {

	private final String dataFileName = "data-day05.txt";
	
	List<Day5Rule> rules;
	HashMap<Integer, List<Integer>> rulesBefore;
	HashMap<Integer, List<Integer>> rulesAfter;
	
	public static void main(String[] args) throws Exception 
	{
		Main05 m = new Main05();
		//m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		boolean switchPart = false;
		rules = new ArrayList<Day5Rule>();
		rulesBefore = new HashMap<Integer, List<Integer>>();
		rulesAfter = new HashMap<Integer, List<Integer>>();
		
		List<List<Integer>> updates = new ArrayList<List<Integer>>();
		while ((line = reader.readLine()) != null) 
		{
			if (line.equals("")) switchPart = true;
			else
			{
				if (!switchPart)
				{
					StringTokenizer st = new StringTokenizer(line, "|");
					Day5Rule d5r = Day5Rule.builder().v1(Integer.parseInt(st.nextToken())).v2(Integer.parseInt(st.nextToken())).build();
					rules.add(d5r);
					List<Integer> before = rulesBefore.get(d5r.getV1());
					if (before != null) before.add(d5r.getV2());
					else 
					{
						before = new ArrayList<Integer>();
						before.add(d5r.getV2());
						rulesBefore.put(d5r.getV1(), before);
					}
					
					List<Integer> after = rulesAfter.get(d5r.getV2());
					if (after != null) after.add(d5r.getV1());
					else 
					{
						after = new ArrayList<Integer>();
						after.add(d5r.getV1());
						rulesAfter.put(d5r.getV2(), after);
					}
					
				}
				else
				{
					StringTokenizer st = new StringTokenizer(line, ",");
					List<Integer> update = new ArrayList<Integer>();
					while (st.hasMoreTokens()) update.add(Integer.parseInt(st.nextToken()));
					updates.add(update);
				}
			}
		}
		reader.close();

		System.out.println(rules);
//		System.out.println(rulesBefore);
		System.out.println(rulesAfter);
		System.out.println(updates);
		
		List<Integer> medians = new ArrayList<Integer>();
		
		updates.forEach(u -> {
			Integer i = checkForValidAndMedian(u);
			if (i != -1) medians.add(i);
		});

		System.out.println(medians);
		
		Integer sumMedians = 0;
		for (int i = 0; i < medians.size(); i++) sumMedians += medians.get(i);
		System.out.println(sumMedians);
	}
		
	public void doSecond() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		boolean switchPart = false;
		rules = new ArrayList<Day5Rule>();
		rulesBefore = new HashMap<Integer, List<Integer>>();
		rulesAfter = new HashMap<Integer, List<Integer>>();
		
		List<List<Integer>> updates = new ArrayList<List<Integer>>();
		while ((line = reader.readLine()) != null) 
		{
			if (line.equals("")) switchPart = true;
			else
			{
				if (!switchPart)
				{
					StringTokenizer st = new StringTokenizer(line, "|");
					Day5Rule d5r = Day5Rule.builder().v1(Integer.parseInt(st.nextToken())).v2(Integer.parseInt(st.nextToken())).build();
					rules.add(d5r);
					List<Integer> before = rulesBefore.get(d5r.getV1());
					if (before != null) before.add(d5r.getV2());
					else 
					{
						before = new ArrayList<Integer>();
						before.add(d5r.getV2());
						rulesBefore.put(d5r.getV1(), before);
					}
					
					List<Integer> after = rulesAfter.get(d5r.getV2());
					if (after != null) after.add(d5r.getV1());
					else 
					{
						after = new ArrayList<Integer>();
						after.add(d5r.getV1());
						rulesAfter.put(d5r.getV2(), after);
					}
					
				}
				else
				{
					StringTokenizer st = new StringTokenizer(line, ",");
					List<Integer> update = new ArrayList<Integer>();
					while (st.hasMoreTokens()) update.add(Integer.parseInt(st.nextToken()));
					updates.add(update);
				}
			}
		}
		reader.close();
		
		List<List<Integer>> incorrectUpdates = new ArrayList<List<Integer>>();
		updates.forEach(u -> {if (!isValidUpdate(u)) incorrectUpdates.add(u);});
		System.out.println(incorrectUpdates);
	
		
		List<List<Integer>> fixedUpdates = new ArrayList<List<Integer>>();
		for (List<Integer> list : incorrectUpdates) 
		{
			fixedUpdates.add(fixUpdate(list));
		} 
		System.out.println(fixedUpdates);
		
		List<Integer> medians = new ArrayList<Integer>();
		fixedUpdates.forEach(u -> {
			Integer i = checkForValidAndMedian(u);
			if (isValidUpdate(u)) medians.add(i);
		});
		
		Integer sumMedians = 0;
		for (int i = 0; i < medians.size(); i++) sumMedians += medians.get(i);
		System.out.println(sumMedians);
	}
	
	public Integer checkForValidAndMedian(List<Integer> update)
	{
		if (isValidUpdate(update)) return update.get((update.size())/2);
		else return -1;
	}
	
	public boolean isValidUpdate(List<Integer> update)
	{
		boolean rulesOK = true;
		for (int i = 0; i < update.size() && rulesOK; i++)
		{
			List<Integer> authorized = rulesAfter.get(update.get(i));
			if (authorized != null)
				for (int j = i+1; j < update.size() && rulesOK; j++)
					if (authorized.contains(update.get(j))) rulesOK = false;
		}
		
		return rulesOK;
	}
	
	public List<Integer> fixUpdate(List<Integer> update)
	{
		while (!isValidUpdate(update))
		{
			if (isValidUpdate(update)) System.out.print("OK====>");
			else System.out.print("NOT OK====>");
			for (int i = 0; i < update.size(); i++)
			{
				List<Integer> authorized = rulesAfter.get(update.get(i));
				if (authorized != null)
					for (int j = i+1; j < update.size(); j++)
						if (authorized.contains(update.get(j)))
						{
							Integer tmp = update.get(i);
							update.set(i, update.get(j));
							update.set(j, tmp);
						};
			}
		}
		if (isValidUpdate(update)) System.out.println("OK");
		else System.out.println("NOT OK");
		return update;
	}
}
