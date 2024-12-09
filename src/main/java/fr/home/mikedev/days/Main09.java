package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

public class Main09 {

	private final String dataFileName = "data-day09.txt";
	
	private char[] memoryBefore;
	private List<String> memory = new ArrayList<String>();
	private  Map<Integer, Long> fileIds = new HashedMap<Integer, Long>();
	private Integer maxFileId = Integer.valueOf(0); 
			
	public static void main(String[] args) throws Exception 
	{
		Main09 m = new Main09();
		//m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			memoryBefore = line.toCharArray();
		}
		reader.close();
		System.out.println(memoryBefore);
		
		prepareMemory();
		System.out.println(memory);
		
		defragment();
		System.out.println(memory);
		
		long checksum = checksum();
		System.out.println(checksum);
	}
		
	public void doSecond() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			memoryBefore = line.toCharArray();
		}
		reader.close();
		System.out.println(memoryBefore);
		
		prepareMemory();
		System.out.println(memory);
		System.out.println(fileIds);
		System.out.println("maxfileId = " + maxFileId);
		
		defragment2();
		System.out.println(memory);
		
		long checksum = checksum();
		System.out.println(checksum);
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
			System.out.println("Checking for file = " + currentFileId);
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
		
		
		
		/*for (int id = currentFileId ; id > 0; id--)
		{*/
			/*for (int i = 0 ; i < memory.size(); )
			{
				int j = i;
				int countMemoryFree = 0;
				while (memory.get(j++).equals(".")) countMemoryFree++;
				System.out.println("empty = " + countMemoryFree);
				// search file with same size
				if (countMemoryFree > 0)
				{
					boolean fileFound = false;
					while (!fileFound && currentFileId > 0)
					{
						System.out.println("Check = " + currentFileId);
						Long fileSize = fileIds.get(currentFileId);
						if (countMemoryFree >= fileSize.intValue()) fileFound = true;
						else currentFileId--;
					}
					if (fileFound)
					{
						System.out.println("Found file id = " + currentFileId);
						Long fileSize = fileIds.get(currentFileId);
						int startReset = memory.indexOf(String.valueOf(currentFileId));
						for (int reset = 0; reset < fileSize; reset++) memory.set(startReset+reset, ".");
						for (int move = 0; move < fileSize; move++) memory.set(i+move, String.valueOf(currentFileId));
						currentFileId--;
					}
					System.out.println(memory);
				}
				i = j;
			}*/
		/*}*/
		/*
		for (int i = 0 ; i < memory.size(); i++)
		{
			for (int j = 0; j < memory.size(); j++)
			if (memory.get(i).equals(".")) countMemoryFree++;
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
		}*/
	}
	
	long checksum()
	{
		long cs = 0;
		for (int i = 0 ; i < memory.size() ; i++)
			if (!memory.get(i).equals(".")) cs += i* Long.parseLong(memory.get(i));			
		return cs;
	}
}
