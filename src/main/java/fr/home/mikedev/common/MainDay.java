package fr.home.mikedev.common;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.Data;

@Data
public abstract class MainDay 
{
	private String title;
	private String year;
	private String day;
	private String resultPart1;
	private String resultPart2;

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
		log("#####################");
		log("# " + title);
		log("#####################");
		log("####  " + year + " - " + day  + " ####");
		log("#####################");
		doPart1();
		doPart2();
	}
	
	public void doPart1()
	{
		log("#####################");
		log("### doPart1 to be implemented");
		log("#####################");
	}
	
	public void doPart2()
	{
		log("#####################");
		log("### doPart2 to be implemented");
		log("#####################");
	}
	
	protected void displayResultPart1(Object o)
	{
		log("");
		log("#####################");
		log("#### Result Part 1 = " + o.toString());
		log("#####################");
	}
	
	protected void displayResultPart2(Object o)
	{
		log("");
		log("#####################");
		log("#### Result Part 2 = " + o.toString());
		log("#####################");
	}
	
	protected void log(Object message)
	{
		System.out.println(message.toString());
	}
}
