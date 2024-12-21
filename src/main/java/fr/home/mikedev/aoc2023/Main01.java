package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.home.mikedev.common.MainDay;

public class Main01 extends MainDay 
{
    List<String> calibrations;
    
	public Main01(String title, String year) {super(title, year, "01");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while((line = reader.readLine()) != null)
			{
			    calibrations.add(line);
			}
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
		
	public void doPart1()
	{
	    calibrations = new ArrayList<String>();
		retrieveData();
		
		int sum = 0;
		for(String calibration : calibrations)
		    sum += Integer.parseInt(getFirstAndLastDigit(calibration));
		
		setResultPart1(sum); //54159
	}
	
	public void doPart2()
	{
		//retrieveData();
	    
	    int sum = 0;
	    for(String calibration : calibrations)
	        sum += Integer.parseInt(getFirstAndLastDigitStr(calibration));
	        
	    setResultPart2(sum); //53866
	}
	
	private String getFirstAndLastDigit(String calibration)
	{
	    String digitFirst = "X";
	    String digitLast  = "X";

	    Pattern pattern = Pattern.compile("(\\d)");
	    Matcher matcher = pattern.matcher(calibration);
	    if (matcher.find()) digitFirst = translate(matcher.group());
	    
	    pattern = Pattern.compile("(?:\\d)(?!.*(?:\\d))");
        matcher = pattern.matcher(calibration);
        if (matcher.find()) digitLast = translate(matcher.group());
        
        return digitFirst + digitLast;
	}
	
    private String getFirstAndLastDigitStr(String calibration)
    {
        String digitFirst = "X";
        String digitLast  = "X";
        
        Pattern pattern = Pattern.compile("(\\d|one|two|three|four|five|six|seven|eight|nine)");
        Matcher matcher = pattern.matcher(calibration);
        if (matcher.find()) digitFirst = matcher.group();
        
        pattern = Pattern.compile("(?:\\d|one|two|three|four|five|six|seven|eight|nine)(?!.*(?:\\d|one|two|three|four|five|six|seven|eight|nine))");
        matcher = pattern.matcher(calibration);
        if (matcher.find()) digitLast = matcher.group();
        //log(translate(digitFirst) + translate(digitLast));
        return translate(digitFirst) + translate(digitLast);
    }
    
    private String translate(String s)
    {
        if (s.equals("one")) return "1";
        else if (s.equals("two")) return "2";
        else if (s.equals("three")) return "3";
        else if (s.equals("four")) return "4";
        else if (s.equals("five")) return "5";
        else if (s.equals("six")) return "6";
        else if (s.equals("seven")) return "7";
        else if (s.equals("eight")) return "8";
        else if (s.equals("nine")) return "9";
        else return s;
    }
}
