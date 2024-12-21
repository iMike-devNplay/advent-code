package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import fr.home.mikedev.common.MainDay;

public class Main13 extends MainDay 
{
	List<long[][]> prizes;
	
	public Main13(String title, String year) {super(title, year, "13");}
	
	public void retrieveData(boolean part1)
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null)
			{
				String buttonA = line.substring(12);
				StringTokenizer st = new StringTokenizer(buttonA, ",");
				String ax =  st.nextToken();
				String ay = st.nextToken().substring(3);
				
				String buttonB = reader.readLine().substring(12);
				st = new StringTokenizer(buttonB, ",");
				String bx =  st.nextToken();
				String by = st.nextToken().substring(3);
				
				String prize = reader.readLine().substring(9);
				st = new StringTokenizer(prize, ",");
				String px =  st.nextToken();
				String py = st.nextToken().substring(3);
				
				long[] ba = { Long.parseLong(ax), Long.parseLong(ay) };
				long[] bb = { Long.parseLong(bx), Long.parseLong(by) };
				long[] p = new long[2];
				if (part1 ) 
				{
					p[0] = Long.parseLong(px);
					p[1] = Long.parseLong(py);
				}
				else
				{
					p[0] = Long.parseLong("10000000000000")+Long.parseLong(px);
					p[1] = Long.parseLong("10000000000000")+Long.parseLong(py);
				}
				
				long[][] prizeInstruction = { p, ba, bb };
				prizes.add(prizeInstruction);
				reader.readLine();
			}
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
		
	public void doPart1()
	{
		prizes = new ArrayList<long[][]>();
		retrieveData(true);
		
		long sum = 0;
		for (long[][] prizeInstruction : prizes)
			sum += calculate(prizeInstruction);

		setResultPart1(sum);
	}
	
	public void doPart2()
	{
		prizes = new ArrayList<long[][]>();
		retrieveData(false);
		
		long sum = 0;
		for (long[][] prizeInstruction : prizes)
			sum += calculate(prizeInstruction);
		
		setResultPart2(sum);
	}
	
	// A => 3 tokens
	// B => 1 token
	public long calculate(long[][] prizeInstruction)
	{
		long determinant = prizeInstruction[1][0]*prizeInstruction[2][1] - prizeInstruction[1][1]*prizeInstruction[2][0];
		long pushA = (prizeInstruction[0][0]*prizeInstruction[2][1] - prizeInstruction[0][1]*prizeInstruction[2][0])/determinant;
		long pushB = (prizeInstruction[0][1]*prizeInstruction[1][0] - prizeInstruction[0][0]*prizeInstruction[1][1])/determinant;
		if (pushA*prizeInstruction[1][0] + pushB*prizeInstruction[2][0] == prizeInstruction[0][0]
				&& pushA*prizeInstruction[1][1] + pushB*prizeInstruction[2][1] == prizeInstruction[0][1])
			return  pushA*3 + pushB;
		else return 0;
	}
}
