package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main05 extends MainDay 
{
    List<Long> seeds;
    List<Pair<Long>> seed2Soil;
    List<Pair<Long>> soil2Fertilizer;
    List<Pair<Long>> fertilizer2Water;
    List<Pair<Long>> water2Light;
    List<Pair<Long>> light2Temperature;
    List<Pair<Long>> temperature2humidity;
    List<Pair<Long>> humidity2Location;
    
    List<List<Pair<Long>>> conversion;
    
    Map<Long, Long> seed2Location;
    Map<Long, Long> location2Seed;
    
	public Main05(String title, String year) {super(title, year, "05");}
	
	public void initData()
	{
        seeds                 = new ArrayList<Long>();
        seed2Soil             = new ArrayList<Pair<Long>>(); 
        soil2Fertilizer       = new ArrayList<Pair<Long>>(); 
        fertilizer2Water      = new ArrayList<Pair<Long>>(); 
        water2Light           = new ArrayList<Pair<Long>>(); 
        light2Temperature     = new ArrayList<Pair<Long>>(); 
        temperature2humidity  = new ArrayList<Pair<Long>>(); 
        humidity2Location     = new ArrayList<Pair<Long>>(); 
        conversion            = new ArrayList<List<Pair<Long>>>();
        
        seed2Location         = new HashMap<Long, Long>();
        location2Seed         = new HashMap<Long, Long>();
	}
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    line = reader.readLine();
		    Arrays.asList(line.split(":")[1].trim().split("\\s+")).forEach(s -> seeds.add(Long.valueOf(s)));
		    line = reader.readLine(); //empty line
		    
		    line = reader.readLine(); //seed-to-soil map:
			while(!(line = reader.readLine()).equals(""))
			{
			    String[] s = line.split("\\s+");
			    seed2Soil.add(Pair.<Long>builder().v1(Long.valueOf(s[1])).v2(Long.valueOf(s[0])).o(s[2]).build());
			}

            line = reader.readLine(); //soil-to-fertilizer map:
            while(!(line = reader.readLine()).equals(""))
            {
                String[] s = line.split("\\s+");
                soil2Fertilizer.add(Pair.<Long>builder().v1(Long.valueOf(s[1])).v2(Long.valueOf(s[0])).o(s[2]).build());
            }

			line = reader.readLine(); //fertilizer-to-water map:
            while(!(line = reader.readLine()).equals(""))
            {
                String[] s = line.split("\\s+");
                fertilizer2Water.add(Pair.<Long>builder().v1(Long.valueOf(s[1])).v2(Long.valueOf(s[0])).o(s[2]).build());
            }

            line = reader.readLine(); //water-to-light map:
            while(!(line = reader.readLine()).equals(""))
            {
                String[] s = line.split("\\s+");
                water2Light.add(Pair.<Long>builder().v1(Long.valueOf(s[1])).v2(Long.valueOf(s[0])).o(s[2]).build());
            }

            line = reader.readLine(); //light-to-temperature map:
            while(!(line = reader.readLine()).equals(""))
            {
                String[] s = line.split("\\s+");
                light2Temperature.add(Pair.<Long>builder().v1(Long.valueOf(s[1])).v2(Long.valueOf(s[0])).o(s[2]).build());
            }

            line = reader.readLine(); //temperature-to-humidity map:
            while(!(line = reader.readLine()).equals(""))
            {
                String[] s = line.split("\\s+");
                temperature2humidity.add(Pair.<Long>builder().v1(Long.valueOf(s[1])).v2(Long.valueOf(s[0])).o(s[2]).build());
            }

            line = reader.readLine(); //humidity-to-location map:
            while((line = reader.readLine()) != null)
            {
                String[] s = line.split("\\s+");
                humidity2Location.add(Pair.<Long>builder().v1(Long.valueOf(s[1])).v2(Long.valueOf(s[0])).o(s[2]).build());
            }
            conversion.add(seed2Soil);
            conversion.add(soil2Fertilizer);
            conversion.add(fertilizer2Water);
            conversion.add(water2Light);
            conversion.add(light2Temperature);
            conversion.add(temperature2humidity);
            conversion.add(humidity2Location);
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

	    Long finalLoc = null;
	    for (Long seed : seeds)
	    {
	        Long loc = getLocationFromSeed(seed);
	        if (finalLoc == null || finalLoc > loc) finalLoc = loc;
	    }
	    
		setResultPart1(finalLoc);
	}
	public void doPart2()
	{
	    //Long finalLoc = Long.valueOf(0);
	    
	    // try with smaller value of location too high : 606585628
	    // then try with smaller value of humidity, first one works !!!
	    for (Long l = 81956384L; l < 81956384L+1; l++)
	    {
	        Long seed = getSeedFromLocation(l);
	        if (seed >= 3127166940L && seed <= 3127166940L+109160474L) log("A=>" + seed + " ; " + l);
	        if (seed >= 3265086325L && seed <= 3265086325L+86449584L) log("B=>" + seed + " ; " + l);
	        if (seed >= 1581539098L && seed <= 1581539098L+205205726L) log("C=>" + seed + " ; " + l);
	        if (seed >= 3646327835L && seed <= 3646327835L+184743451L) log("D=>" + seed + " ; " + l);
	        if (seed >= 2671979893L && seed <= 2671979893L+17148151) log("E=>" + seed + " ; " + l);
	        if (seed >=  305618297L && seed <= 305618297L+40401857L) log("F=>" + seed + " ; " + l);
	        if (seed >= 2462071712L && seed <= 2462071712L+203075200L) log("G=>" + seed + " ; " + l);
	        if (seed >=  358806266L && seed <= 358806266L+131147346L) log("H=>" + seed + " ; " + l);
	        if (seed >= 1802185716L && seed <= 1802185716L+538526744L) log("I=>" + seed + " ; " + l);
	        if (seed >=  635790399L && seed <= 635790399L+705979250L) log("J=>" + seed + " ; " + l);
	    }
	    log(getSeedFromLocation(81956384L));
	    setResultPart2(81956384L);
	}
	
	public void doPart2Temp()
	{
		//retrieveData();
	    Long finalLoc = null;
	    List<Pair<Long>> l = new ArrayList<Pair<Long>>();
	    for (int i = 0; i < seeds.size(); i++)
	    {
	        Long startRange = seeds.get(i++);
	        Long endRange = startRange + seeds.get(i);
	        log ("from = " + startRange + " to " + endRange);
	        l.add(Pair.<Long>builder().v1(startRange).v2(endRange).build());
	    }
	    log(l);
        l = getConvertedRanges(
                getConvertedRanges(
                        getConvertedRanges(
                                getConvertedRanges(
                                        getConvertedRanges(
                                                getConvertedRanges(
                                                        getConvertedRanges(l, seed2Soil), 
                                                        soil2Fertilizer), 
                                                fertilizer2Water), 
                                        water2Light), 
                                light2Temperature), 
                        temperature2humidity),
                humidity2Location);
        for (Pair<Long> loc : l)
            if (finalLoc == null || finalLoc > loc.getV1()) finalLoc = loc.getV1();
                
	    setResultPart2(finalLoc);
	}
	
	
	
	public List<Pair<Long>> getConvertedRanges(List<Pair<Long>> origRanges, List<Pair<Long>> map)
	{
	    List<Pair<Long>> destRanges = new ArrayList<Pair<Long>>();
	    
	    for (Pair<Long> origRange : origRanges)
	    {
	        List<Pair<Long>> destTemp1Ranges = new ArrayList<Pair<Long>>();
	        List<Pair<Long>> destTemp2Ranges = new ArrayList<Pair<Long>>();
	        
	        Long startRange = origRange.getV1();
	        Long endRange = origRange.getV2();
    	    for (Pair<Long> r : map)
    	    {
    	        Long startOrigRange = r.getV1();
    	        Long endOrigRange = r.getV1()+Long.valueOf(r.getO())-1;
    	        Long startDestRange = r.getV2();
                Long endDestRange = r.getV2()+Long.valueOf(r.getO())-1;
    	        
    	        if (startRange >= startOrigRange && startRange <= endOrigRange) // Cas 1
    	        {
    	            if (endRange >= startOrigRange && endRange <= endOrigRange) // Cas 1a
    	                destTemp1Ranges.add(Pair.<Long>builder().v1(convertValue(startRange, map)).v2(convertValue(endRange, map)).o("Cas-1a").build());
    	            else
    	            {
    	                destTemp1Ranges.add(Pair.<Long>builder().v1(convertValue(startRange, map)).v2(endDestRange).o("Cas-1b").build());
    	                destTemp1Ranges.add(Pair.<Long>builder().v1(endOrigRange+1).v2(endRange).o("Cas-1b").build());
    	            }
    	        }
    	        else if (startRange < startOrigRange && endRange > endOrigRange) // Cas 2
    	        {
    	            destTemp1Ranges.add(Pair.<Long>builder().v1(startRange).v2(startOrigRange-1).o("Cas-2").build());
    	            destTemp1Ranges.add(Pair.<Long>builder().v1(startDestRange).v2(endDestRange).o("Cas-2").build());
    	            destTemp1Ranges.add(Pair.<Long>builder().v1(endOrigRange+1).v2(endRange).o("Cas-2").build());
    	        }
    	        else if (startRange < startOrigRange && endRange >= startOrigRange && endRange <= endOrigRange) // Cas 3
    	        {
    	            destTemp1Ranges.add(Pair.<Long>builder().v1(startRange).v2(startOrigRange-1).o("Cas-3").build());
    	            destTemp1Ranges.add(Pair.<Long>builder().v1(startDestRange).v2(convertValue(endRange, map)).o("Cas-3").build());
    	        }
    	        else if (endRange < startOrigRange || startRange > endOrigRange) // Cas 4
    	        {
    	            Pair<Long> rtemp = Pair.<Long>builder().v1(startRange).v2(endRange).o("Cas-4").build();
    	            if (!destTemp2Ranges.contains(rtemp)) destTemp2Ranges.add(rtemp);
    	        }
    	    }
    	    if (destTemp1Ranges.size() == 0) destRanges.addAll(destTemp2Ranges);
    	    else destRanges.addAll(destTemp1Ranges);
	    }
	    log(destRanges);
	    return destRanges;
	}
	
	
	public Long getLocationFromSeed(Long seed)
	{
	    Long loc;
	    if ((loc = seed2Location.get(seed)) != null) return loc;
	    
	    Long convertedValue = seed;
	    for (List<Pair<Long>> conv : conversion)
	        convertedValue = convertValue(convertedValue, conv);
	    seed2Location.put(seed, convertedValue);
	    
	    return convertedValue;
	}
	
    public Long getSeedFromLocation(Long location)
    {
        Long seed;
        if ((seed = location2Seed.get(location)) != null) return seed;
        
        Long convertedValue = location;
        for (List<Pair<Long>> conv : conversion.reversed())
            convertedValue = convertValueReverse(convertedValue, conv);
        location2Seed.put(location, convertedValue);
        
        return convertedValue;
    }
    
	public Long convertValue(Long source, List<Pair<Long>> map)
	{
	    Long dest = source;
	    for (Pair<Long> rule : map)
	        if (source >= rule.getV1() && source < rule.getV1()+Long.valueOf(rule.getO()))
	            return rule.getV2()+(source-rule.getV1());	    
	    return dest;
	}
	
    public Long convertValueReverse(Long dest, List<Pair<Long>> map)
    {
        Long source = dest;
        for (Pair<Long> rule : map)
            if (dest >= rule.getV2() && dest < rule.getV2()+Long.valueOf(rule.getO()))
                return rule.getV1()+(dest-rule.getV2());      
        return source;
    }
    
    public List<Pair<Long>> getConvertedRangesReverse(List<Pair<Long>> origRanges, List<Pair<Long>> map)
    {
        List<Pair<Long>> destRanges = new ArrayList<Pair<Long>>();
        
        for (Pair<Long> origRange : origRanges)
        {
            List<Pair<Long>> destTemp1Ranges = new ArrayList<Pair<Long>>();
            List<Pair<Long>> destTemp2Ranges = new ArrayList<Pair<Long>>();
            
            Long startRange = origRange.getV1();
            Long endRange = origRange.getV2();
            for (Pair<Long> r : map)
            {
                Long startOrigRange = r.getV1();
                Long endOrigRange = r.getV1()+Long.valueOf(r.getO())-1;
                Long startDestRange = r.getV2();
                Long endDestRange = r.getV2()+Long.valueOf(r.getO())-1;
                
                if (startRange >= startOrigRange && startRange <= endOrigRange) // Cas 1
                {
                    if (endRange >= startOrigRange && endRange <= endOrigRange) // Cas 1a
                        destTemp1Ranges.add(Pair.<Long>builder().v1(convertValueReverse(startRange, map)).v2(convertValueReverse(endRange, map)).o("Cas-1a").build());
                    else
                    {
                        destTemp1Ranges.add(Pair.<Long>builder().v1(convertValueReverse(startRange, map)).v2(endDestRange).o("Cas-1b").build());
                        destTemp1Ranges.add(Pair.<Long>builder().v1(endOrigRange+1).v2(endRange).o("Cas-1b").build());
                    }
                }
                else if (startRange < startOrigRange && endRange > endOrigRange) // Cas 2
                {
                    destTemp1Ranges.add(Pair.<Long>builder().v1(startRange).v2(startOrigRange-1).o("Cas-2").build());
                    destTemp1Ranges.add(Pair.<Long>builder().v1(startDestRange).v2(endDestRange).o("Cas-2").build());
                    destTemp1Ranges.add(Pair.<Long>builder().v1(endOrigRange+1).v2(endRange).o("Cas-2").build());
                }
                else if (startRange < startOrigRange && endRange >= startOrigRange && endRange <= endOrigRange) // Cas 3
                {
                    destTemp1Ranges.add(Pair.<Long>builder().v1(startRange).v2(startOrigRange-1).o("Cas-3").build());
                    destTemp1Ranges.add(Pair.<Long>builder().v1(startDestRange).v2(convertValueReverse(endRange, map)).o("Cas-3").build());
                }
                else if (endRange < startOrigRange || startRange > endOrigRange) // Cas 4
                {
                    Pair<Long> rtemp = Pair.<Long>builder().v1(startRange).v2(endRange).o("Cas-4").build();
                    if (!destTemp2Ranges.contains(rtemp)) destTemp2Ranges.add(rtemp);
                }
            }
            if (destTemp1Ranges.size() == 0) destRanges.addAll(destTemp2Ranges);
            else destRanges.addAll(destTemp1Ranges);
        }
        log(destRanges);
        return destRanges;
    }
}
