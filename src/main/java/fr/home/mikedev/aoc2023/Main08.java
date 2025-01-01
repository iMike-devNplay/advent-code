package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.ArithmeticUtils;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main08 extends MainDay 
{
    char[] directions;
    Map<String, Pair<String>> nodes;
            
	public Main08(String title, String year) {super(title, year, "08");}
	
	public void initData()
	{
	    nodes = new HashMap<String, Pair<String>>();
	}
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    line = reader.readLine(); // first line is directions
		    directions = line.toCharArray();
		    
		    line = reader.readLine(); // 2nd line is blank
		    
			while((line = reader.readLine()) != null)
			{
			    nodes.put(line.substring(0, 3), Pair.<String>builder().v1(line.substring(7, 10)).v2(line.substring(12, 15)).build());
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
	    
	    int step = 0;
	    String currentNode = "AAA";
	    
	    while(!currentNode.equals("ZZZ"))
	    {
	        if (directions[step%directions.length] == 'R') currentNode = nodes.get(currentNode).getV2();
	        else currentNode = nodes.get(currentNode).getV1();
	        step++;
	    }
	    
		setResultPart1(step); // 14257
	}
	
	public void doPart2()
	{
	    List<String> currentNodes = nodes.keySet().stream().filter(n -> n.endsWith("A")).toList();
	    List<Long> steps = new ArrayList<Long>();
	    for (String n : currentNodes)
	    {
	        String currentNode = n;
	        int step = 0;
	        while(!currentNode.endsWith("Z"))
	        {
                if (directions[step%directions.length] == 'R') currentNode = nodes.get(currentNode).getV2();
                else currentNode = nodes.get(currentNode).getV1();
                step++;
	        }
	        steps.add(Long.valueOf(step));
	    }
	    
	    Collections.sort(steps);
	    long lcm = steps.get(0);
	    
	    // Get least common multiple for all steps values
	    for (int i = 1; i < steps.size(); i++)
	        lcm = ArithmeticUtils.lcm(lcm, steps.get(i));
	    
	    setResultPart2(lcm); // 16187743689077
	}
}
