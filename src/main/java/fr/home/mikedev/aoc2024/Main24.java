package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main24 extends MainDay 
{
	Map<String, Integer> wires;
	List<Operation> operations;
	Map<Pair<String>, Operation> operationsMapOp;
	Map<String, Operation> operationsMapResult;
	
	public Main24(String title, String year) {super(title, year, "24");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			boolean switchToOperation = false;
            while((line = reader.readLine()) != null)
            {
            	if (line.equals("")) switchToOperation = true;
            	else
            	{
            		if (!switchToOperation)
            		{
            			String[] s = line.split(":");
            			wires.put(s[0], Integer.valueOf(s[1].trim()));
            		}
            		else
            		{
            			String[] s = line.split("\\s+");
            			operations.add(new Operation(s[0], s[1], s[2], s[4]));
            			operationsMapOp.put(Pair.<String>builder().v1(s[0]).v2(s[2]).build(), new Operation(s[0], s[1], s[2], s[4]));
            			operationsMapResult.put(s[4], new Operation(s[0], s[1], s[2], s[4]));
            		}
            	}
            }
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
            ex.printStackTrace();
		}
	}
	void initData()
	{
		wires = new HashMap<String, Integer>();
		operations = new ArrayList<Operation>();
		operationsMapOp = new HashMap<Pair<String>, Operation>();
		operationsMapResult = new HashMap<String, Operation>();
	}
	public void doPart1()
	{
		initData();
		retrieveData();
		List<Operation> unsolvedOperations = new ArrayList<Operation>();
		unsolvedOperations.addAll(operations);
		
		while (unsolvedOperations.size() != 0)
		{
			for (int i = 0; i < unsolvedOperations.size(); i++)
			{
				Integer result;
				if ((result = unsolvedOperations.get(i).solve(wires)) != null)
				{
					wires.put(unsolvedOperations.get(i).result, result);
					unsolvedOperations.remove(i);
				}
			}
		}
		
		String bytes= "";
		List<String> zbits = wires.keySet().stream().filter(w -> w.startsWith("z")).sorted().toList();
		for (String z : zbits.reversed())
			bytes += wires.get(z);

		log(bytes);
		setResultPart1(Long.parseLong(bytes, 2)); //48508229772400
	}
	
	public void doPart2()
	{
		//retrieveData();
		// find wrong addition
		List<String> xbits = wires.keySet().stream().filter(w -> w.startsWith("x")).sorted().toList();
		List<String> ybits = wires.keySet().stream().filter(w -> w.startsWith("y")).sorted().toList(); 
		List<String> zbits = wires.keySet().stream().filter(w -> w.startsWith("z")).sorted().toList();
		//List<Operation> xyOp = operations.stream().filter(o -> o.val1.startsWith("x") || o.val1.startsWith("y")).sorted().toList();

		for (int i = 0 ; i < zbits.size()-1; i++)
		{
		    log(xbits.get(i) + "=" + wires.get(xbits.get(i)) + " AND " + ybits.get(i)+ "=" + wires.get(ybits.get(i)) + " => " + Operation.solve(wires, "AND", wires.get(xbits.get(i)), wires.get(ybits.get(i))) + "    (" + zbits.get(i) + "=" + wires.get(zbits.get(i)));
		}
		setResultPart2(0);
		/*log(wires);
		for (int i = 0 ; i < xbits.size(); i++)
		{
			Integer xval = wires.get(xbits.get(i));
			Integer yval = wires.get(ybits.get(i));
			Integer zval = wires.get(zbits.get(i));
			
			Operation o;
			if ((o = operationsMapOp.get(Pair.<String>builder().v1(xbits.get(i)).v2(ybits.get(i)).build())) == null ) 
				if ((o = operationsMapOp.get(Pair.<String>builder().v1(ybits.get(i)).v2(xbits.get(i)).build())) == null ) log("error");
			
			if (o.solve(wires) != wires.get(zbits.get(i))) log(zbits.get(i));
			
		}*/
		/*zbits.forEach(z -> log(z + " = " + wires.get(z)));
		for (String zOp : operationsMapResult.keySet().stream().filter(r -> r.startsWith("z")).toList())
		{
			Operation o = operationsMapResult.get(zOp);
			//log(findOpeXY(o.val1) + " " + o.operator + " " + findOpeXY(o.val2) + " -> " + o.result);
			Integer currentResult = Operation.solve(wires, o.operator, findOpeXYOp(o.val1), findOpeXYOp(o.val2));
			log(zOp + " = " + currentResult);
			if (!currentResult.equals(wires.get(zOp))) log(zOp);
		}*/
		
		String bytesX = " ";
		//List<String> xbits = wires.keySet().stream().filter(w -> w.startsWith("x")).sorted().toList();
		for (String x : xbits.reversed())
			bytesX += wires.get(x);
		
		String bytesY = " ";
		//List<String> ybits = wires.keySet().stream().filter(w -> w.startsWith("y")).sorted().toList();
		for (String y : ybits.reversed())
			bytesY += wires.get(y);
		
		String bytesZ= "";
		//List<String> zbits = wires.keySet().stream().filter(w -> w.startsWith("z")).sorted().toList();
		for (String z : zbits.reversed())
			bytesZ += wires.get(z);
		
		log(bytesX);
		log(bytesY);
		log(bytesZ);
		
		log(Long.parseLong(bytesX.trim(), 2));
		log(Long.parseLong(bytesY.trim(), 2));
		log(Long.parseLong(bytesX.trim(), 2)+Long.parseLong(bytesY.trim(), 2));
	    /*setResultPart2(0); // cqr,ncd,nfj,qnw,vkg,z15,z20,z37*/
	    
		// Store operation with wrong result
	    Map<String, String> wrongResults = new HashMap<String, String>();
	    for (Operation o : operations)
	    {
	        if (o.result.startsWith("z") 
	                //&& !o.operator.equals("XOR")
	                && o.operator.equals("AND")
	                && !o.result.equals("z45")) // exclude last result, because no corresponding operation (additional bit) 
	        	if (Operation.solve(wires, "AND", wires.get("x" + o.result.substring(1)), wires.get("y" + o.result.substring(1))) != wires.get(o.result)) 
	        		wrongResults.put(o.result, "cas1");
	        
	        /*if (o.operator.equals("XOR") 
	                && !o.val1.startsWith("x") 
	                && !o.val1.startsWith("y") 
	                && !o.val1.startsWith("z")
	                && !o.val2.startsWith("x")
	                && !o.val2.startsWith("y")
	                && !o.val2.startsWith("z")
	                && !o.result.startsWith("x")
	                && !o.result.startsWith("y")
	                && !o.result.startsWith("z")) 
	            wrongResults.put(o.result, "cas2");
	        
	        if (o.operator.equals("AND") 
	                && !o.val1.equals("x00")
	                && !o.val2.equals("x00"))
	            for (Operation o2 : operations)
	                if ((o.result.equals(o2.val1) || o.result.equals(o2.val2)) && !o2.operator.equals("OR")) wrongResults.put(o.result, "cas3");
	        
	        if (o.operator.equals("XOR"))
                for (Operation o2 : operations)
                    if ((o.result.equals(o2.val1) || o.result.equals(o2.val2)) && o2.operator.equals("OR")) wrongResults.put(o.result, "cas4");*/
	    }
	    String result = wrongResults.keySet().stream().sorted().toList().toString().replace(" ", "");
	    setResultPart2(result.substring(1, result.length()-1));
	}
	
	String findOpeXY(String val)
	{
		if (val.startsWith("x") || val.startsWith("y")) return val;
		else
		{
			Operation o = operationsMapResult.get(val);
			return "(" + findOpeXY(o.val1) + " " + o.operator + " " + findOpeXY(o.val2) + ")";
		}
	}
	
	Integer findOpeXYOp(String val)
	{
		if (val.startsWith("x") || val.startsWith("y")) return wires.get(val);
		else
		{
			Operation o = operationsMapResult.get(val);
			return Operation.solve(wires, o.operator, findOpeXYOp(o.val1), findOpeXYOp(o.val2));
			//return "(" + findOpeXY(o.val1) + " " + o.operator + " " + findOpeXY(o.val2) + ")";
		}
	}
	
	class Operation implements Comparable<Operation>
	{
		public String val1;
		public String operator;
		public String val2;
		public String result;
		
		Operation(String v1, String o, String v2, String r)
		{
			this.val1 = v1;
			this.operator = o;
			this.val2 = v2;
			this.result = r;
		}
		
		Integer solve(Map<String, Integer> values)
		{
			Integer v1 = values.get(val1);
			Integer v2 = values.get(val2);
			
			if  (v1 != null && v2 != null)
			{
				switch (operator)
				{
				case "AND":
					return v1.equals(Integer.valueOf(1)) && v2.equals(Integer.valueOf(1)) ? Integer.valueOf(1) : Integer.valueOf(0);
				case "OR":
					return v1.equals(Integer.valueOf(1)) || v2.equals(Integer.valueOf(1)) ? Integer.valueOf(1) : Integer.valueOf(0);
				case "XOR":
					return v1.equals(Integer.valueOf(1)) ^ v2.equals(Integer.valueOf(1)) ? Integer.valueOf(1) : Integer.valueOf(0);
				default:
					return null;
				}
			}
			else return null;
		}
		
		static Integer solve(Map<String, Integer> values, String operator, Integer v1, Integer v2)
		{
			if  (v1 != null && v2 != null)
			{
				switch (operator)
				{
				case "AND":
					return v1.equals(Integer.valueOf(1)) && v2.equals(Integer.valueOf(1)) ? Integer.valueOf(1) : Integer.valueOf(0);
				case "OR":
					return v1.equals(Integer.valueOf(1)) || v2.equals(Integer.valueOf(1)) ? Integer.valueOf(1) : Integer.valueOf(0);
				case "XOR":
					return v1.equals(Integer.valueOf(1)) ^ v2.equals(Integer.valueOf(1)) ? Integer.valueOf(1) : Integer.valueOf(0);
				default:
					return null;
				}
			}
			else return null;
		}
		
		public String toString()
		{
			return this.val1 + " " + this.operator + " " + this.val2 + " -> " + this.result;
		}

		@Override
		public int compareTo(Operation o) 
		{
			return this.val1.compareTo(o.val1);
		}
	}
}
