package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import fr.home.mikedev.common.MainDay;

public class Main07 extends MainDay 
{

	public Main07(String title, String year) {super(title, year, "07");}
	
	private List<List<Long>> calibrations = new ArrayList<List<Long>>();
	private List<List<Long>> notValidcalibrations = new ArrayList<List<Long>>();
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null) 
			{
				List<Long> calibration = new ArrayList<Long>();
				
				StringTokenizer st = new StringTokenizer(line, ":");
				calibration.add(Long.valueOf(st.nextToken()));
				
				String values = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(values, " ");
				while (st2.hasMoreTokens()) calibration.add(Long.valueOf(st2.nextToken()));
				
				calibrations.add(calibration);
			}
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
	
	public void doPart1()
	{
		retrieveData();
		
		List<List<Long>> validCalibrations = calibrations.stream().filter(c -> isCalibrationValid(c) ).toList();
		Long result = Long.valueOf(0);
		for (int i = 0; i < validCalibrations.size(); i++)
			result += validCalibrations.get(i).get(0);
		
		setResultPart1(result);
	}

	public void doPart2()
	{
		Long result = Long.valueOf(0);
		List<List<Long>> validCalibrations = calibrations.stream().filter(c -> isCalibrationValid(c) ).toList();
		for (int i = 0; i < validCalibrations.size(); i++) result += validCalibrations.get(i).get(0);
		notValidcalibrations = calibrations.stream().filter(c -> !isCalibrationValid(c) ).toList();
		result += calculation(0, 100);
		result += calculation(100, 200);
		result += calculation(200, 300);
		result += calculation(300, 400);
		result += calculation(400, 420);
		setResultPart2(result);
	}
	
	public Long calculation(int startIndex, int endIndex)
	{
		calibrations = new ArrayList<List<Long>>();
		for(int occ = startIndex; occ < endIndex; occ++)
			calibrations.add(notValidcalibrations.get(occ));
		
		//System.out.println("all calibrations=>" + calibrations);
		//System.out.println("Etape 1");
		List<List<List<String>>> operations = calibrations.stream().map(c -> buildOperation(c) ).toList();
		//System.out.println("Etape 2");
		List<List<List<String>>> results = operations.stream().map(o -> doOperations(o)).toList();
		//System.out.println("Etape 3");
		Long result = Long.valueOf(0);
		for (int i = 0; i < results.size(); i++)
		{
			if (isOneValid(results.get(i))) result += Long.parseLong(results.get(i).get(0).get(0));
		}
		//log(result);
		return result;
	}
	
	public boolean isOneValid(List<List<String>> c)
	{
		for (int i = 0; i < c.size(); i++)
			if (c.get(i).get(0).equals(c.get(i).get(1))) return true;
			
		return false;
	}
	
	public boolean isCalibrationValid(List<Long> calibration)
	{
		if (calibration.size() == 2) return calibration.get(0).equals(calibration.get(1));
		Long finalResult = calibration.get(0);
		int nbOperandPosition = calibration.size()-2; // remove first item which is result and -1 to have the number of operand
		
		String s = "";
		for (int i = 0; i < nbOperandPosition; i++) s += "1";
		int combinaisonPossible = Integer.parseInt(s, 2) + 1;
		
		for (int j = 0; j < combinaisonPossible; j ++)
		{
			String possible = StringUtils.leftPad(Integer.toBinaryString(j), nbOperandPosition, "0");			
			Long intermResult = Long.valueOf(0);		
			for (int i = 1; i < calibration.size()-1; i++)
			{
				char operand = possible.charAt(i-1);
				if (i == 1)
				{
					if (operand == '0') intermResult += calibration.get(i) + calibration.get(i+1);
					else if (operand == '1') intermResult = calibration.get(i) * calibration.get(i+1);
				}
				else
				{
					if (operand == '0') intermResult += calibration.get(i+1);
					else if (operand == '1') intermResult *= calibration.get(i+1);					
				}
			}
			if (intermResult.equals(finalResult)) return true;
		}
		return false;
	}
	
	public List<List<String>> buildOperation(List<Long> calibration)
	{
		List<List<String>> operations = new ArrayList<List<String>>();
		
		int nbOperandPosition = calibration.size()-2; // remove first item which is result and -1 to have the number of operand
		
		int[] v = {0, 1, 2};
		List<List<Integer>> l = Main07.generateCombinations(nbOperandPosition, v);
		
		for (int j = 0; j < l.size(); j ++)
		{
			List<String> op = new ArrayList<String>();
			String operation = calibration.get(0).toString();
			op.add(operation);
			
			List<Integer> permut = l.get(j);
			for (int i = 0; i < permut.size(); i++)
			{
				Integer operand = permut.get(i);
				if (i == 0)
				{
					if (operand == 0)
					{
						op.add(calibration.get(i+1).toString());
						op.add("+");
						op.add(calibration.get(i+2).toString());
					}
					else if (operand == 1) 
					{
						op.add(calibration.get(i+1).toString());
						op.add("*");
						op.add(calibration.get(i+2).toString());
					}
					else if (operand == 2) 
					{
						op.add(calibration.get(i+1).toString());
						op.add("||");
						op.add(calibration.get(i+2).toString());
					}
				}
				else
				{
					if (operand == 0)
					{
						op.add("+");
						op.add(calibration.get(i+2).toString());
					}
					else if (operand == 1)
					{
						op.add("*");
						op.add(calibration.get(i+2).toString());					
					}
					else if (operand == 2)
					{
						op.add("||");
						op.add(calibration.get(i+2).toString());					
					}
				}
			}
			operations.add(op);
		}
		return operations;
	}
	
	public List<List<String>> doOperations(List<List<String>> operations)
	{
		//System.out.println(operations);
		List<List<String>> result = new ArrayList<List<String>>();
		for (int i = 0; i < operations.size(); i++)
			result.add(doOperation(operations.get(i)));
			
		return result;
		//List<List<String>>         l       = operations.stream().map(os -> os.doOperation(os)).toList();
	}

	public List<String> doOperation(List<String> operation)
	{
		List<String> result = new ArrayList<String>();
		result.add(operation.get(0));
		
		//System.out.println("Operations=" + operation);
		Long value = Long.valueOf(0);
		boolean doAdd = false;
		boolean doMul = false;
		boolean doConcat = false;
		
		
		for (int i = 1; i < operation.size(); i++)
		{
			String currVal = operation.get(i);
			//if (i == 1) value = Long.parseLong(currVal);
			
			if (currVal.equals("+")) doAdd = true;
			else if (currVal.equals("*")) doMul = true;
			else if (currVal.equals("||")) doConcat = true;
			else
			{
				if (!doAdd && !doMul && !doConcat) value = Long.parseLong(currVal);
				else if(doAdd) 
				{
					value = value +  Long.parseLong(currVal);
					doAdd = false;
				}
				else if(doMul)
				{
					value = value *  Long.parseLong(currVal);
					doMul = false;
				}
				else if (doConcat)
				{
					value = Long.parseLong(value.toString() + currVal);
					doConcat = false;
				}
			}
		}
		result.add(Long.valueOf(value).toString());
		return result;
	}
	
    public static List<List<Integer>> generateCombinations(int occurrences, int[] values) {
        List<List<Integer>> combinations = new ArrayList<>();
        generateHelper(occurrences, values, new ArrayList<>(), combinations);
        return combinations;
    }

    private static void generateHelper(int occurrences, int[] values, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == occurrences) {
            result.add(new ArrayList<>(current)); // Ajouter une copie de la combinaison courante
            return;
        }
        
        for (int value : values) {
            current.add(value); // Ajouter la valeur courante
            generateHelper(occurrences, values, current, result); // Appel récursif
            current.remove(current.size() - 1); // Backtracking : enlever le dernier élément
        }
    }
    
	/*public void generateNewCalibrations(List<Long> calibration)
	{
		calibrationsAdded.add(calibration);
		int nbDigit = calibration.size() - 2;

		
		for (int i = 0; i < nbDigit; i++)
		{
			List<Long> newCalibration = new ArrayList<Long>();
			newCalibration.add(calibration.get(0));
			
			for (int j = 1; j < 1+i; j++) newCalibration.add(calibration.get(j));
			newCalibration.add(Long.parseLong(calibration.get(i+1).toString() + calibration.get(i+2).toString()));
			for (int j = i+3; j < calibration.size(); j++) newCalibration.add(calibration.get(j));
			
			calibrationsAdded.add(newCalibration);
		}
	}*/
}
