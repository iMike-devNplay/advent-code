package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import fr.home.mikedev.common.MainDay;

public class Main19 extends MainDay 
{
    List<String> patterns;
    List<String> designs;
    Map<String, Boolean> cache; // memoization part1
    Map<String, Long> cache2;   // memoization part2
    
    List<String> validDesigns;
    
	public Main19(String title, String year) {super(title, year, "19");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    line = reader.readLine();
		    StringTokenizer st = new StringTokenizer(line.replace(" ", ""), ",");
		    while (st.hasMoreTokens())
		    {
		        String p = st.nextToken();
		        patterns.add(p);
		    }
		    line = reader.readLine();
		    
            while((line = reader.readLine()) != null)
            {
                designs.add(line);
            }
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
	
	public void doPart1()
	{
	    patterns = new ArrayList<String>();
	    designs = new ArrayList<String>();
	    validDesigns = new ArrayList<String>();
	    
		retrieveData();
		
		
		cache = new HashMap<String, Boolean>();
		for (int i = 0; i < designs.size(); i++)
        {
            String d = designs.get(i);
            if (checkDesign(d)) validDesigns.add(d);
        }
		
		setResultPart1(validDesigns.size());  //206
	}
	   
	public void doPart2()
	{
		//retrieveData();
	    
        cache2 = new HashMap<String, Long>();
        long count = 0;
        for (int i = 0; i < designs.size(); i++)
        {
            String d = designs.get(i);
            count += checkDesignCount(d);
        }
        
        setResultPart2(count); //622121814629343
	}
	
    boolean checkDesign(String design)
    {
        Boolean val;
        if ((val = cache.get(design)) != null) return val;
        
        if (StringUtils.isEmpty(design))
        {
            cache.put(design, true);
            return true;
        }
        
        for (int i = 1 ; i < design.length()+1; i++)
        {
            String prefix = design.substring(0, i);
            String suffix = design.substring(i);
            if (patterns.contains(prefix) && checkDesign(suffix))
            {
                cache.put(design, true);
                return true;
            }
        }
        
        cache.put(design, false);
        return false;
    }
    
    long checkDesignCount(String design)
    {
        Long val;
        if ((val = cache2.get(design)) != null) return val;
        
        if (StringUtils.isEmpty(design))
        {
            //cache2.put(design, Long.valueOf(1));
            return 1;
        }
        
        long count = 0;
        for (int i = 1 ; i < design.length()+1; i++)
        {
            String prefix = design.substring(0, i);
            String suffix = design.substring(i);
            if (patterns.contains(prefix))
            {
                count += checkDesignCount(suffix);
            }
        }
        
        cache2.put(design, Long.valueOf(count));
        return count;
    }
}
