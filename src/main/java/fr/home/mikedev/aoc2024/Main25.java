package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import fr.home.mikedev.common.MainDay;

public class Main25 extends MainDay 
{
    int size = 5;
    List<int[]> keys;
    List<int[]> locks;
    
	public Main25(String title, String year) {super(title, year, "25");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    boolean keymode = false;
		    boolean lockmode = false;
            while((line = reader.readLine()) != null)
            {
                if (!keymode && !lockmode && line.equals("#####")) lockmode = true;
                else if (!keymode && !lockmode && line.equals(".....")) keymode = true;
                else if (keymode)
                {
                    int digit0 = 0;
                    int digit1 = 0;
                    int digit2 = 0;
                    int digit3 = 0;
                    int digit4 = 0;
                    int cnt = 0;
                    while(cnt < 5)
                    {
                        if (line.charAt(0) == '#') digit0++;
                        if (line.charAt(1) == '#') digit1++;
                        if (line.charAt(2) == '#') digit2++;
                        if (line.charAt(3) == '#') digit3++;
                        if (line.charAt(4) == '#') digit4++;
                        line = reader.readLine();
                        cnt++;
                    }
                    line = reader.readLine();
                    int[] key = {digit0, digit1, digit2, digit3, digit4}; 
                    keys.add(key);
                    keymode = false;
                }
                else if (lockmode)
                {
                    int digit0 = 0;
                    int digit1 = 0;
                    int digit2 = 0;
                    int digit3 = 0;
                    int digit4 = 0;
                    int cnt = 0;
                    while(cnt < 5)
                    {
                        if (line.charAt(0) == '#') digit0++;
                        if (line.charAt(1) == '#') digit1++;
                        if (line.charAt(2) == '#') digit2++;
                        if (line.charAt(3) == '#') digit3++;
                        if (line.charAt(4) == '#') digit4++; 
                        line = reader.readLine();
                        cnt++;
                    }
                    line = reader.readLine(); 
                    int[] lock = {digit0, digit1, digit2, digit3, digit4}; 
                    locks.add(lock);
                    lockmode = false;
                }
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
	    keys = new ArrayList<int[]>();
	    locks = new ArrayList<int[]>();
	}
	public void doPart1()
	{
	    initData();
		retrieveData();
		
		int matchingKey = 0;
		
		for(int[] key : keys)
		    for(int[] lock : locks)
		    {
		        boolean ok = true;
		        for (int i = 0; i < key.length; i++)
		            if(key[i] + lock[i] > 5) ok = false;
		        if (ok) matchingKey++;
		    }

		setResultPart1(matchingKey); // 3508
	}
	
	public void doPart2()
	{
		//retrieveData();
	    setResultPart2(0);
	}
}
