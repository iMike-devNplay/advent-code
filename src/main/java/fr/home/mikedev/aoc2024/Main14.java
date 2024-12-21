package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main14 extends MainDay 
{
    int matrixWidth = 101;
    int matrixHeight = 103;
    List<Pair<Pair<Integer>>> robots;
    String dataFileName = "output4.txt";
    BufferedWriter  w;
    
    Map<Pair<Integer>, String> robotsList;
    
	public Main14(String title, String year) {super(title, year, "14");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while((line = reader.readLine()) != null)
			{
			    StringTokenizer st = new StringTokenizer(line, " ");
			    String position = st.nextToken();
			    String velocity = st.nextToken();
			    
			    st = new StringTokenizer(position.substring(2), ",");
			    Pair<Integer> pPosition = Pair.<Integer>builder().v1(Integer.parseInt(st.nextToken())).v2(Integer.parseInt(st.nextToken())).build();
			    st = new StringTokenizer(velocity.substring(2), ",");
			    Pair<Integer> pVelocity = Pair.<Integer>builder().v1(Integer.parseInt(st.nextToken())).v2(Integer.parseInt(st.nextToken())).build();
			    robots.add(Pair.<Pair<Integer>>builder().v1(pPosition).v2(pVelocity).build());
			}
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
		
	public void doPart1()
	{
	    robots = new ArrayList<Pair<Pair<Integer>>>();
		retrieveData();
		
		// Moves calculation
		for (int seconds = 0; seconds < 100; seconds++)
		{
		    for (Pair<Pair<Integer>> r : robots)
		    {
		        moveRobot(r);
		    }
		}
		
		log(robots);
		
		// Quadrant calculation
		int q1 = 0;
		int q2 = 0;
		int q3 = 0;
		int q4 = 0;
		
		robotsList = new HashMap<Pair<Integer>, String>();
		for (Pair<Pair<Integer>> r : robots)
		{
		    int q = getQuadrant(r.getV1());
		    if (q == 1) q1++;
		    else if (q == 2) q2++;
		    else if (q == 3) q3++;
		    else if (q == 4) q4++;
		    robotsList.put(r.getV1(), "");
		}

		int safety = q1*q2*q3*q4;
		setResultPart1(safety); //218295000
	}
	
	public void doPart2()
	{
	    try 
	    {
            w = new BufferedWriter(new FileWriter(dataFileName));
        }
	    catch (IOException e) 
	    {
            e.printStackTrace();
        }
		//retrieveData();
        for (int seconds = 100; seconds < 7000; seconds++)
        {
            for (Pair<Pair<Integer>> r : robots)
            {
                robotsList = new HashMap<Pair<Integer>, String>();
                moveRobot(r);
                for (Pair<Pair<Integer>> r1 : robots)
                    robotsList.put(r1.getV1(), "");
            }
            if (seconds % 500 == 0) log("====> .");
            if (seconds > 6850 && seconds < 6900) display(seconds+1);
        }
        try 
        {
            w.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        setResultPart2(6870);
	}
	
	public void moveRobot(Pair<Pair<Integer>> robot)
	{
	    int x = robot.getV1().getV1().intValue();
        int y = robot.getV1().getV2().intValue();
        
        int movex = robot.getV2().getV1().intValue();
        int movey = robot.getV2().getV2().intValue();
        
        int newx = move(x, movex, matrixWidth);
        int newy = move(y, movey, matrixHeight);
        robot.getV1().setV1(newx);
        robot.getV1().setV2(newy);
	}
	
	public int move(int pos, int m, int size)
	{
	    int newpos = pos;
	    if (m > 0)
	    {
	        if (pos > size-1-m) newpos = m-(size-pos);
	        else newpos = pos+m;
	    }
	    else
	    {
	        if (pos < Math.abs(m)) newpos = size-(Math.abs(m)-pos);
	        else newpos = pos+m;
	    }
	    
	    return newpos;
	}
	   
	public int getQuadrant(Pair<Integer> robotPosition)
	{
	    int x = robotPosition.getV1().intValue();
	    int y = robotPosition.getV2().intValue();
	    
	    if (x < 50 && y < 51) return 1;
	    if (x > 50 && y < 51) return 2;
	    if (x < 50 && y > 51) return 3;
	    if (x > 50 && y > 51) return 4;
	    return 0;
	}
	public void display(int seconds)
	{
        try 
        {
            w.write("######################### " + seconds + "#####################\n");
    	    for (int x = 0 ; x < matrixHeight; x++)
    	    {
    	        for (int y = 0 ; y < matrixWidth; y++)
    	        {
    	            String a = robotsList.get(Pair.<Integer>builder().v1(Integer.valueOf(y)).v2(Integer.valueOf(x)).build());
    	            if (a != null) w.write("X");
    	            else w.write("."); 
    	        }
    	        w.write("\n");
    	        w.flush();
    	    }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
