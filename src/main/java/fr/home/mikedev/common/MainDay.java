package fr.home.mikedev.common;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import lombok.Data;

@Data
public abstract class MainDay 
{
	private String title;
	private String year;
	private String day;
	private Object resultPart1;
	private Object resultPart2;

	private Duration resultTimePart1;
    private Duration resultTimePart2;
    
	public MainDay()
	{
		this.setTitle("");
		this.setYear("");
		this.setDay("");
	}
	
	public MainDay(String title, String year, String day)
	{
		this.setTitle(title);
		this.setYear(year);
		this.setDay(day);
	}
	
	public BufferedReader getReader()
	{
		String dataFileName = String.format("%s/data-day%s.txt", year, day);
		try
		{
			return Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		}
		catch (Exception ex)
		{
			log("Unable to open file : " + dataFileName);
			return null;
		}
	}
	public void doPartAll()
	{
		log("");
		log("####################");
		log("# " + title);
		log("####################");
		log("####  " + year + " - " +  day + " ####");
		log("####################");
		
		Instant start = Instant.now();
		doPart1();
		Instant end = Instant.now();
		resultTimePart1 = Duration.between(start, end);
		displayResultPart1();
		
		start = Instant.now();
		doPart2();
		end = Instant.now();
		resultTimePart2 = Duration.between(start, end);
		
		displayResultPart2();
	}
	
	public void doPart1()
	{
		log("####################");
		log("### doPart1 to be implemented");
		log("####################");
	}
	
	public void doPart2()
	{
		log("####################");
		log("### doPart2 to be implemented");
		log("####################");
	}
	
	protected void displayResultPart1()
	{
		log("");
		log("####################");
		log("#### Result Part 1 = " + resultPart1.toString());
		log("#################### t = " + resultTimePart1.toMillis() + "ms");
	}
	
	protected void displayResultPart2()
	{
		log("");
		log("####################");
		log("#### Result Part 2 = " + resultPart2.toString());
		log("#################### t = " + resultTimePart2.toMillis() + "ms");
	}
	
	protected void log(Object message)
	{
		System.out.println(message.toString());
	}
}
