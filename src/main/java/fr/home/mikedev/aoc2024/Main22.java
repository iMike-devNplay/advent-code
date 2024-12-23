package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;

public class Main22 extends MainDay 
{
    //int size = 2154;
    int size = 4;
    long[] secrets = new long[size];
    List<long[]> buyersPriceChanges;
    
    Map<String, Long> sequences;
    
	public Main22(String title, String year) {super(title, year, "22");}
	
	public void retrieveData()
	{
		String line = null;
		int i = 0;
		try(BufferedReader reader = this.getReader())
		{
            while((line = reader.readLine()) != null)
            {
                secrets[i++] = Long.parseLong(line);
            }
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
            ex.printStackTrace();
		}
	}
	
	public void initData()
	{
	    buyersPriceChanges = new ArrayList<long[]>();
	    sequences = new HashMap<String, Long>();
	}
	
	public void doPart1()
	{
		retrieveData();
		
		long sum = 0;
		for(int i = 0; i < size; i++)
		{
		    long newSecretNumber = secrets[i];
		    for (int process = 0; process < 2000; process++)
		    {
		        
    		    newSecretNumber = operation0(newSecretNumber);
    		    newSecretNumber = operation1(newSecretNumber);
    		    newSecretNumber = operation2(newSecretNumber);
		    }
		    sum += newSecretNumber;
		}
		setResultPart1(sum); //18261820068
	}
	
	public void doPart2()
	{
        initData();
        
        long sum = 0;
        for(int i = 0; i < size; i++)
        {
            int iter = 2000;
            long[] lastDigitsSecret = new long[iter+1];
            long[] priceChanges = new long[iter];
            long newSecretNumber = secrets[i];
            String newSecretString = String.valueOf(newSecretNumber);
            lastDigitsSecret[0] = Long.parseLong(newSecretString.substring(newSecretString.length()-1, newSecretString.length()));
            
            for (int process = 0; process < iter; process++)
            {   
                newSecretNumber = operation0(newSecretNumber);
                newSecretNumber = operation1(newSecretNumber);
                newSecretNumber = operation2(newSecretNumber);
                newSecretString = String.valueOf(newSecretNumber);
                
                lastDigitsSecret[process+1] = Long.parseLong(newSecretString.substring(newSecretString.length()-1, newSecretString.length()));
                if (process != 0) priceChanges[process] = lastDigitsSecret[process]-lastDigitsSecret[process-1];
                else priceChanges[process] = 0;
            }
            log(newSecretNumber);
            sum += newSecretNumber;
            buyersPriceChanges.add(priceChanges);
        }
	    for (long[] c : buyersPriceChanges)
	    {
	        for (int i = 0; i < c.length; i++)
	            System.out.print(c[i] + " ; ");
	        log("######");
	    }
	    
	    // Data are set, now getting each sequence of 4 before a positive number with at least 4 changes before
	    // sequences  : index = list of 4 changes (the sequence to find)
	    //              value = sum of values thourgh all buyers
	    
	    setResultPart2(sum);
	}
	
	public long operation0(long secretNumber)
	{
	    return prune(mix(secretNumber, secretNumber*64));
	}
	
	public long operation1(long secretNumber)
    {
        return prune(mix(secretNumber, secretNumber/32));
    }
	
	public long operation2(long secretNumber)
    {
        return prune(mix(secretNumber, secretNumber*2048));
    }
	
	public long mix(long secretNumber, long tempNumber)
    {
        return tempNumber ^ secretNumber;
    }
	
	public long prune(long tempNumber)
    {
        return tempNumber % 16777216;
    }
}
