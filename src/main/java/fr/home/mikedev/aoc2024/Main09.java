package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import fr.home.mikedev.common.MainDay;

public class Main09 extends MainDay
{
	private char[] memoryBefore;
	private List<String> memory = new ArrayList<String>();
	private  Map<Integer, Long> fileIds = new HashedMap<Integer, Long>();
	private Integer maxFileId = Integer.valueOf(0);
	
	public Main09(String title, String year) {super(title, year, "09");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null) memoryBefore = line.toCharArray();
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
	
	public void doPart1()
	{
		retrieveData();
		prepareMemory();
		defragment();
		long checksum = checksum();
		setResultPart1(checksum);
	}
		
	public void doPart2()
	{
		memory = new ArrayList<String>();
		fileIds = new HashedMap<Integer, Long>();
		maxFileId = Integer.valueOf(0); 	
		retrieveData();
		prepareMemory();
		defragment2();
		long checksum = checksum();
		setResultPart2(checksum);
	}
	
	void prepareMemory()
	{
		boolean fileBlock = true;
		Integer fileId = Integer.valueOf(0);
		for (int i = 0; i < memoryBefore.length; i++)
		{
			int size = Integer.parseInt(String.valueOf(memoryBefore[i]));
			if (fileBlock)
			{
				for (int j = 0; j < size; j++) memory.add(fileId.toString());
				fileIds.put(fileId, Long.valueOf(size));
				maxFileId = fileId;
				fileId++;
			}
			else for (int j = 0; j < size; j++) memory.add(".");
			fileBlock = !fileBlock;
		}
	}
	
	void defragment()
	{
		for (int i = 0 ; i < memory.size(); i++)
		{
			if (memory.get(i).equals("."))
			{
				int j = memory.size()-1;
				boolean memoryReplaced = false;
				while (!memoryReplaced && j >= i)
				{
					String block = memory.get(j);
					if (!block.equals("."))
					{
						memory.set(i, block);
						memory.set(j, ".");
						memoryReplaced = true;
					}
					j--;
				}
			}
		}
	}
	
	void defragment2()
	{
		int currentFileId = maxFileId;
		while (currentFileId > 0)
		{
			//System.out.println("Checking for file = " + currentFileId);
			boolean fileFound = false;
			boolean stopSearch = false;
			Long fileSize = fileIds.get(currentFileId);
			for (int i = 0 ; i < memory.size() && !fileFound && !stopSearch; )
			{
				int j = i;
				int countMemoryFree = 0;
				if (memory.get(j).equals(String.valueOf(currentFileId))) stopSearch = true;
				while (memory.get(j++).equals(".")) countMemoryFree++;
				if (countMemoryFree > 0)
				{
					if (countMemoryFree >= fileSize.intValue())
					{
						int startReset = memory.indexOf(String.valueOf(currentFileId));
						for (int reset = 0; reset < fileSize; reset++) memory.set(startReset+reset, ".");
						for (int move = 0; move < fileSize; move++) memory.set(i+move, String.valueOf(currentFileId));
						fileFound = true;
					}
					j--;
				}
				i = j;
			}
			currentFileId--;
		}
	}
	
	long checksum()
	{
		long cs = 0;
		for (int i = 0 ; i < memory.size() ; i++)
			if (!memory.get(i).equals(".")) cs += i* Long.parseLong(memory.get(i));			
		return cs;
	}
}
