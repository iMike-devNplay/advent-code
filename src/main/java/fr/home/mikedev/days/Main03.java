package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main03 {

	private final String dataFileName = "data-day03.txt";
	
	public static void main(String[] args) throws Exception 
	{
		Main03 m = new Main03();
		m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			System.out.println(line);
		}
		reader.close();
	}
		
	public void doSecond() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			System.out.println(line);
		}
		reader.close();
	}
}
