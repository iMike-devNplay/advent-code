package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main21 extends MainDay 
{
    String[] codes = new String[5];
    
    char[][] numpad = { {'7', '8', '9'}, 
                        {'4', '5', '6'}, 
                        {'1', '2', '3'}, 
                        {'X', '0', 'A'} };
    
    char[][] dirpad = { {'X', '^', 'A'}, 
                        {'<', 'v', '>'} };
    
    Map<Character, Pair<Integer>> numPadMap;
    Map<Pair<Integer>, Character> numPadMapR;
    Map<Character, Pair<Integer>> dirPadMap;
    Map<Pair<Integer>, Character> dirPadMapR;
    Map<Pair<Character>, String>   allPadDico;
    
    Map<String, String> convertCache;
    Map<String, Long>   countCache;
    
	public Main21(String title, String year) {super(title, year, "21");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    int i = 0;
            while((line = reader.readLine()) != null)
            {
                codes[i++] = line;
            }
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
            ex.printStackTrace();
		}
	}
	
	public void doPart1()
	{
	    initData();
		retrieveData();
		
		int sum = 0;
		for(int c = 0; c < codes.length; c++)
		{
		    // 1 robotnumpad <- 1 robotdirpad <- 1 robotdirpad <- myself
		    //  1 robotnumpad <- 2 robotdirpad <- myself
		    String moves = codes[c];
		    for(int i = 0; i < 3; i++)
		    {
		        moves = "A" + moves;
		        moves = getMoveSequenceMemo(moves);
		       //log(moves);
		    }
            
            sum += moves.length()*Integer.parseInt(codes[c].substring(0, codes[c].length()-1));
		}
		
		setResultPart1(sum); // 202648
	}
	
	public void doPart2()
	{
		//retrieveData();
	    
        long sum = 0;
        for(int c = 0; c < codes.length; c++)
        {
            String moves = codes[c];
            moves = "A" + moves;
            moves = getMoveSequenceMemo(moves);
        
            moves = "A" + moves;
            long len = 0;
            for (int i = 0; i < moves.length()-1; i++)
                len += countMove(moves.charAt(i), moves.charAt(i+1), 26);
            
            sum += len * Integer.parseInt(codes[c].substring(0, codes[c].length()-1));
        }
                
	    setResultPart2(sum);
	}
	
	String getMoveSequence(String code)
    {
        
        String newCode = "";
        for (int i = 0; i <code.length()-1; i++)
            newCode += decompMove(code.charAt(i), code.charAt(i+1)) + 'A';
        
        return newCode;
    }
	
	String getMoveSequenceMemo(String code)
	{
	    String newCode;
	    if ((newCode = convertCache.get(code)) != null) return newCode;
	    else newCode = "";
	    for (int i = 0; i < code.length()-1; i++)
            newCode += decompMove(code.charAt(i), code.charAt(i+1)) + 'A';
            
        convertCache.put(code, newCode);
        return newCode;
	}

	String decompMove(char origine, char destination)
	{
	    if (origine == destination) return "";
	    return allPadDico.get(Pair.<Character>builder().v1(origine).v2(destination).build());
	}
	
	long countMove(char origine, char destination, int level)
    {
	    String key = String.valueOf(origine) + String.valueOf(destination) + String.valueOf(level);
	    Long count;
        if ((count = countCache.get(key)) != null) return count.longValue();
        
        if (level == 1) return 1; //decompMove(origine, destination).length();
        
        String s = "A" + decompMove(origine, destination) + "A";
        long sum = 0;
        for (int i = 0; i < s.length()-1; i++)
            sum += countMove(s.charAt(i), s.charAt(i+1), level-1);
        
        countCache.put(key, sum);
        return sum;
    }
	
    public void initData()
    {
        convertCache = new HashMap<String, String>();
        countCache   = new HashMap<String, Long>();
        
        numPadMap = new HashMap<Character, Pair<Integer>>();
        numPadMap.put('7', Pair.<Integer>builder().v1(0).v2(0).o("7").build());
        numPadMap.put('8', Pair.<Integer>builder().v1(0).v2(1).o("8").build());
        numPadMap.put('9', Pair.<Integer>builder().v1(0).v2(2).o("9").build());
        numPadMap.put('4', Pair.<Integer>builder().v1(1).v2(0).o("4").build());
        numPadMap.put('5', Pair.<Integer>builder().v1(1).v2(1).o("5").build());
        numPadMap.put('6', Pair.<Integer>builder().v1(1).v2(2).o("6").build());
        numPadMap.put('1', Pair.<Integer>builder().v1(2).v2(0).o("1").build());
        numPadMap.put('2', Pair.<Integer>builder().v1(2).v2(1).o("2").build());
        numPadMap.put('3', Pair.<Integer>builder().v1(2).v2(2).o("3").build());
        numPadMap.put('X', Pair.<Integer>builder().v1(3).v2(0).o("X").build());
        numPadMap.put('0', Pair.<Integer>builder().v1(3).v2(1).o("0").build());
        numPadMap.put('A', Pair.<Integer>builder().v1(3).v2(2).o("A").build());
        
        numPadMapR = new HashMap<Pair<Integer>, Character>();
        numPadMapR.put(Pair.<Integer>builder().v1(0).v2(0).o("7").build(), '7');
        numPadMapR.put(Pair.<Integer>builder().v1(0).v2(1).o("8").build(), '8');
        numPadMapR.put(Pair.<Integer>builder().v1(0).v2(2).o("9").build(), '9');
        numPadMapR.put(Pair.<Integer>builder().v1(1).v2(0).o("4").build(), '4');
        numPadMapR.put(Pair.<Integer>builder().v1(1).v2(1).o("5").build(), '5');
        numPadMapR.put(Pair.<Integer>builder().v1(1).v2(2).o("6").build(), '6');
        numPadMapR.put(Pair.<Integer>builder().v1(2).v2(0).o("1").build(), '1');
        numPadMapR.put(Pair.<Integer>builder().v1(2).v2(1).o("2").build(), '2');
        numPadMapR.put(Pair.<Integer>builder().v1(2).v2(2).o("3").build(), '3');
        numPadMapR.put(Pair.<Integer>builder().v1(3).v2(0).o("X").build(), 'X');
        numPadMapR.put(Pair.<Integer>builder().v1(3).v2(1).o("0").build(), '0');
        numPadMapR.put(Pair.<Integer>builder().v1(3).v2(2).o("A").build(), 'A');
        
        dirPadMap = new HashMap<Character, Pair<Integer>>();
        numPadMap.put('X', Pair.<Integer>builder().v1(0).v2(0).o("X").build());
        numPadMap.put('^', Pair.<Integer>builder().v1(0).v2(1).o("^").build());
        numPadMap.put('A', Pair.<Integer>builder().v1(0).v2(2).o("A").build());
        numPadMap.put('<', Pair.<Integer>builder().v1(1).v2(0).o("<").build());
        numPadMap.put('v', Pair.<Integer>builder().v1(1).v2(1).o("v").build());
        numPadMap.put('>', Pair.<Integer>builder().v1(1).v2(2).o(">").build());
        
        dirPadMapR = new HashMap<Pair<Integer>, Character>();
        numPadMapR.put(Pair.<Integer>builder().v1(0).v2(0).o("X").build(), 'X');
        numPadMapR.put(Pair.<Integer>builder().v1(0).v2(1).o("^").build(), '^');
        numPadMapR.put(Pair.<Integer>builder().v1(0).v2(2).o("A").build(), 'A');
        numPadMapR.put(Pair.<Integer>builder().v1(1).v2(0).o("<").build(), '<');
        numPadMapR.put(Pair.<Integer>builder().v1(1).v2(1).o("v").build(), 'v');
        numPadMapR.put(Pair.<Integer>builder().v1(1).v2(2).o(">").build(), '>');
        
        allPadDico = new HashMap<Pair<Character>, String>();
        allPadDico.put(Pair.<Character>builder().v1('A').v2('0').o("A0").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('1').o("A1").build(), "^<<");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('2').o("A2").build(), "<^");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('3').o("A3").build(), "^");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('4').o("A4").build(), "^^<<");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('5').o("A5").build(), "<^^");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('6').o("A6").build(), "^^");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('7').o("A7").build(), "^^^<<");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('8').o("A8").build(), "<^^^");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('9').o("A9").build(), "^^^");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('^').o("A^").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('v').o("Av").build(), "<v");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('<').o("A<").build(), "v<<");
        allPadDico.put(Pair.<Character>builder().v1('A').v2('>').o("A>").build(), "v");
        
        allPadDico.put(Pair.<Character>builder().v1('0').v2('A').o("0A").build(), ">");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('1').o("01").build(), "^<");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('2').o("02").build(), "^");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('3').o("03").build(), "^>");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('4').o("04").build(), "^^<");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('5').o("05").build(), "^^");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('6').o("06").build(), "^^>");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('7').o("07").build(), "^^^<");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('8').o("08").build(), "^^^");
        allPadDico.put(Pair.<Character>builder().v1('0').v2('9').o("09").build(), "^^^>");
        
        allPadDico.put(Pair.<Character>builder().v1('1').v2('0').o("10").build(), "v>");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('A').o("1A").build(), "v>>");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('2').o("12").build(), ">");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('3').o("13").build(), ">>");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('4').o("14").build(), "^");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('5').o("15").build(), "^>");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('6').o("16").build(), "^>>");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('7').o("17").build(), "^^");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('8').o("18").build(), "^^>");
        allPadDico.put(Pair.<Character>builder().v1('1').v2('9').o("19").build(), "^^>>");

        allPadDico.put(Pair.<Character>builder().v1('2').v2('0').o("20").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('1').o("21").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('A').o("2A").build(), "v>");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('3').o("23").build(), ">");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('4').o("24").build(), "<^");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('5').o("25").build(), "^");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('6').o("26").build(), "^>");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('7').o("27").build(), "<^^");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('8').o("28").build(), "^^");
        allPadDico.put(Pair.<Character>builder().v1('2').v2('9').o("29").build(), "^^>");

        allPadDico.put(Pair.<Character>builder().v1('3').v2('0').o("30").build(), "<v");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('1').o("31").build(), "<<");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('2').o("32").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('A').o("3A").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('4').o("34").build(), "<<^");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('5').o("35").build(), "<^");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('6').o("36").build(), "^");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('7').o("37").build(), "<<^^");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('8').o("38").build(), "<^^");
        allPadDico.put(Pair.<Character>builder().v1('3').v2('9').o("39").build(), "^^");

        allPadDico.put(Pair.<Character>builder().v1('4').v2('0').o("40").build(), ">vv");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('1').o("41").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('2').o("42").build(), "v>");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('3').o("43").build(), "v>>");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('A').o("4A").build(), ">>vv");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('5').o("45").build(), ">");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('6').o("46").build(), ">>");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('7').o("47").build(), "^");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('8').o("48").build(), "^>");
        allPadDico.put(Pair.<Character>builder().v1('4').v2('9').o("49").build(), "^>>");
        
        allPadDico.put(Pair.<Character>builder().v1('5').v2('0').o("50").build(), "vv");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('1').o("51").build(), "<v");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('2').o("52").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('3').o("53").build(), "v>");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('4').o("54").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('A').o("5A").build(), "vv>");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('6').o("56").build(), ">");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('7').o("57").build(), "<^");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('8').o("58").build(), "^");
        allPadDico.put(Pair.<Character>builder().v1('5').v2('9').o("59").build(), "^>");

        allPadDico.put(Pair.<Character>builder().v1('6').v2('0').o("60").build(), "<vv");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('1').o("61").build(), "<<v");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('2').o("62").build(), "<v");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('3').o("63").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('4').o("64").build(), "<<");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('5').o("65").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('A').o("6A").build(), "vv");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('7').o("67").build(), "<<^");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('8').o("68").build(), "<^");
        allPadDico.put(Pair.<Character>builder().v1('6').v2('9').o("69").build(), "^");

        allPadDico.put(Pair.<Character>builder().v1('7').v2('0').o("70").build(), ">vvv");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('1').o("71").build(), "vv");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('2').o("72").build(), "vv>");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('3').o("73").build(), "vv>>");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('4').o("74").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('5').o("75").build(), "v>");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('6').o("76").build(), "v>>");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('A').o("7A").build(), ">>vvv");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('8').o("78").build(), ">");
        allPadDico.put(Pair.<Character>builder().v1('7').v2('9').o("79").build(), ">>");

        allPadDico.put(Pair.<Character>builder().v1('8').v2('0').o("80").build(), "vvv");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('1').o("81").build(), "<vv");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('2').o("82").build(), "vv");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('3').o("83").build(), "vv>");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('4').o("84").build(), "<v");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('5').o("85").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('6').o("86").build(), "v>");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('7').o("87").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('A').o("8A").build(), "vvv>");
        allPadDico.put(Pair.<Character>builder().v1('8').v2('9').o("89").build(), ">");

        allPadDico.put(Pair.<Character>builder().v1('9').v2('0').o("90").build(), "<vvv");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('1').o("91").build(), "<<vv");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('2').o("92").build(), "<vv");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('3').o("93").build(), "vv");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('4').o("94").build(), "<<v");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('5').o("95").build(), "<v");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('6').o("96").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('7').o("97").build(), "<<");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('8').o("98").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('9').v2('A').o("9A").build(), "vvv");
        

        allPadDico.put(Pair.<Character>builder().v1('^').v2('A').o("^A").build(), ">");
        allPadDico.put(Pair.<Character>builder().v1('^').v2('v').o("^v").build(), "v");
        allPadDico.put(Pair.<Character>builder().v1('^').v2('<').o("^<").build(), "v<");
        allPadDico.put(Pair.<Character>builder().v1('^').v2('>').o("^>").build(), "v>");
        

        allPadDico.put(Pair.<Character>builder().v1('v').v2('^').o("v^").build(), "^");
        allPadDico.put(Pair.<Character>builder().v1('v').v2('A').o("vA").build(), "^>");
        allPadDico.put(Pair.<Character>builder().v1('v').v2('<').o("v<").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('v').v2('>').o("v>").build(), ">");
        

        allPadDico.put(Pair.<Character>builder().v1('<').v2('^').o("<^").build(), ">^");
        allPadDico.put(Pair.<Character>builder().v1('<').v2('v').o("<v").build(), ">");
        allPadDico.put(Pair.<Character>builder().v1('<').v2('A').o("<A").build(), ">>^");
        allPadDico.put(Pair.<Character>builder().v1('<').v2('>').o("<>").build(), ">>");
        

        allPadDico.put(Pair.<Character>builder().v1('>').v2('^').o(">^").build(), "<^");
        allPadDico.put(Pair.<Character>builder().v1('>').v2('v').o(">v").build(), "<");
        allPadDico.put(Pair.<Character>builder().v1('>').v2('<').o("><").build(), "<<");
        allPadDico.put(Pair.<Character>builder().v1('>').v2('A').o(">A").build(), "^");
    }
}
