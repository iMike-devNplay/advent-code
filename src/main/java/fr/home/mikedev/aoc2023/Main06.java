package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main06 extends MainDay 
{
    List<Pair<Integer>> races;
    Pair<Long> theRace;
    
	public Main06(String title, String year) {super(title, year, "06");}
	
	public void retrieveData()
	{	
		try(BufferedReader reader = this.getReader())
		{
		    String lineTime = reader.readLine();
		    String lineDistance = reader.readLine();
			
		    String[] times = lineTime.substring(5).trim().split("\\s+");
		    String[] distances = lineDistance.substring(9).trim().split("\\s+");
		    
		    for (int i = 0; i < times.length; i++)
		        races.add(Pair.<Integer>builder().v1(Integer.valueOf(times[i])).v2(Integer.valueOf(distances[i])).build());
		    
		    // For part 2
		    theRace = Pair.<Long>builder()   .v1(Long.valueOf(lineTime.substring(5).replace(" ", "")))
		                                     .v2(Long.valueOf(lineDistance.substring(9).replace(" ", ""))).build();
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
			ex.printStackTrace();
		}
	}
	
	public void initData()
	{
	    races = new ArrayList<Pair<Integer>>();
	}
	
	public void doPart1()
	{
	    initData();
	    retrieveData();
	    
	    log(races);
	    
	    int total = 1;
	    for (Pair<Integer> race : races)
	    {
	        int count = 0;
	        for (int push = 1; push < race.getV1(); push++)
	        {
	            int timeAfterRelease = race.getV1()-push;
	            int d = push*timeAfterRelease;
	            if (d > race.getV2()) count++;
	        }
	        total *= count;
	    }
	    
	    
		setResultPart1(total);
	}
	
	public void doPart2()
	{
		//retrieveData();
	    log(theRace);
        int count = 0;
        for (long push = 1; push < theRace.getV1(); push++)
        {
            long timeAfterRelease = theRace.getV1()-push;
            long d = push*timeAfterRelease;
            if (d > theRace.getV2()) count++;
        }
	    setResultPart2(count);
	}
}
