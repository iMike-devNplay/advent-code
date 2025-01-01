package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import fr.home.mikedev.common.ExPair;
import fr.home.mikedev.common.MainDay;

public class Main07 extends MainDay 
{
	List<ExPair<String, Integer>> hands;
	Map<ExPair<String, Integer>, HandType> handTypes;
	
	public Main07(String title, String year) {super(title, year, "07");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while((line = reader.readLine()) != null)
			{
			    String[] s = line.split("\\s+");
			    hands.add(ExPair.<String, Integer>builder().v1(s[0]).v2(Integer.valueOf(s[1])).build());
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
		hands = new ArrayList<ExPair<String,Integer>>();
		handTypes = new HashMap<ExPair<String, Integer>, HandType>();
	}
	
	public void doPart1()
	{
		initData();
	    retrieveData();
	    
	    List<HandOfCards> hc = new ArrayList<HandOfCards>();
	    hands.forEach(h -> hc.add(new HandOfCards(h, getHandType(h.getV1(), false), false)));
	    
	    log(hc);
	    Collections.sort(hc);
	    log(hc);
	    
	    Long winnings = Long.valueOf(0);
	    int i = 1;
	    for (HandOfCards h : hc) winnings += h.cards.getV2()*i++;
	    
		setResultPart1(winnings); // 250370104
	}
	
	public void doPart2()
	{
		//retrieveData();
	    List<HandOfCards> hc = new ArrayList<HandOfCards>();
        hands.forEach(h -> hc.add(new HandOfCards(h, getHandType(h.getV1(), true), true)));
        
        log(hc);
        Collections.sort(hc);
        log(hc);
        
        Long winnings = Long.valueOf(0);
        int i = 1;
        for (HandOfCards h : hc) winnings += h.cards.getV2()*i++;
	    setResultPart2(winnings); //251735672
	}
	
	HandType getHandType(String cards, boolean withJoker)
	{
        char [] c = cards.toCharArray();
        Arrays.sort(c);
        String sortedCards = new String(c);
        int startCard = 0;
        int count = 1;
        for(int i = 0; i < sortedCards.length(); i++)
            if (startCard != i) if (sortedCards.charAt(startCard) == sortedCards.charAt(i)) count++;

	    if (!withJoker)
	    {
    		if (count == 5) return HandType.FiveOfKind;
    		else if (count == 4) return HandType.FourOfKind;
    		else if (count == 2)
    		{
    			startCard = 2;
    			count = 1;
    			for(int i = 0; i < sortedCards.length(); i++)
    				if (startCard != i) if (sortedCards.charAt(startCard) == sortedCards.charAt(i)) count++;
    			if (count == 3) return HandType.FullHouse;
    			else if (count == 2) return HandType.TwoPair;
    			else 
    			{
    				if (sortedCards.charAt(3) == sortedCards.charAt(4)) return HandType.TwoPair;
    				else return HandType.OnePair;
    			}
    		}
    		else if (count == 3)
    			if (sortedCards.charAt(3) == sortedCards.charAt(4)) return HandType.FullHouse;
    			else return HandType.ThreeOfKind;
    		else
    		{
    			startCard = 1;
    			count = 1;
    			for(int i = 0; i < sortedCards.length(); i++)
    				if (startCard != i) if (sortedCards.charAt(startCard) == sortedCards.charAt(i)) count++;
    			if (count == 4) return HandType.FourOfKind;
    			else if (count == 3) return HandType.ThreeOfKind;
    			else if (count == 2)
    			{
    				if (sortedCards.charAt(3) == sortedCards.charAt(4)) return HandType.TwoPair;
    				else return HandType.OnePair;
    			}
    			else
    			{
    				startCard = 2;
    				count = 1;
    				for(int i = 0; i < sortedCards.length(); i++)
    					if (startCard != i) if (sortedCards.charAt(startCard) == sortedCards.charAt(i)) count++;
    				if (count == 2) return HandType.OnePair;
    				else if (count == 3) return HandType.ThreeOfKind;
    				else if (sortedCards.charAt(3) == sortedCards.charAt(4)) return HandType.OnePair;
    			}
    		}
    
    		return HandType.HighCard;
	    }
	    else
	    {
	        HandType ht = getHandType(cards, false);
	        int countJ = StringUtils.countMatches(sortedCards, 'J');
	        if (countJ == 0) return ht;
	        else
	        {
	            if (ht.equals(HandType.FiveOfKind)) return ht;
	            else if (ht.equals(HandType.FourOfKind))
	            {
	                if (countJ == 1) return HandType.FiveOfKind;
	                else if (countJ == 4) return HandType.FiveOfKind;
	                else return ht;
	            }
	            else if (ht.equals(HandType.FullHouse))
	            {
	                if (countJ == 1) return HandType.FourOfKind;
	                else if (countJ == 2) return HandType.FiveOfKind;
	                else if (countJ == 3) return HandType.FiveOfKind;
                    else return ht;
	            }
	            else if (ht.equals(HandType.ThreeOfKind))
	            {
                    if (countJ == 1) return HandType.FourOfKind;
                    else if (countJ == 2) return HandType.FiveOfKind;
                    else if (countJ == 3) return HandType.FourOfKind;
                    else return ht;
	            }
	            else if (ht.equals(HandType.TwoPair))
	            {
	                if (countJ == 1) return HandType.FullHouse;
	                else if (countJ == 2) return HandType.FourOfKind;
                    else return ht;
	            }
	            else if (ht.equals(HandType.OnePair))
                {
                    if (countJ == 1) return HandType.ThreeOfKind;
                    else if (countJ == 2) return HandType.ThreeOfKind;
                    else return ht;
                }
	            else if (ht.equals(HandType.HighCard))
                {
                    if (countJ == 1) return HandType.OnePair;
                    else return ht;
                }
                else return ht;
	        }
	    }
	}
	
	public class HandOfCards implements Comparable<HandOfCards>
	{
		ExPair<String, Integer> cards;
		HandType handType;
		boolean withJoker;
		
		public HandOfCards(ExPair<String, Integer> c, HandType t, boolean j)
		{
			this.cards = c;
			this.handType = t;
			this.withJoker = j;
		}
		
		@Override
		public int compareTo(HandOfCards o) 
		{
			if (o.handType.equals(this.handType)) 
			{
				int i = 0;
				while (this.cards.getV1().charAt(i) == o.cards.getV1().charAt(i) && i < 5) i++;
				if (i == 5) return 0;
				else 
				{
					return CardValue.translate(this.cards.getV1().charAt(i), withJoker).compareTo(CardValue.translate(o.cards.getV1().charAt(i), withJoker));
				}
			}
			else return this.handType.compareTo(o.handType);
		}
		
		public String toString()
		{
		    if (this.handType == HandType.FiveOfKind) return this.cards.getV1() + "(" + this.cards.getV2() + ")" + " (5K)";
		    else if (this.handType == HandType.FourOfKind) return this.cards.getV1() + "(" + this.cards.getV2() + ")" + " (4K)";
		    else if (this.handType == HandType.FullHouse) return this.cards.getV1() + "(" + this.cards.getV2() + ")" + " (FH)";
		    else if (this.handType == HandType.ThreeOfKind) return this.cards.getV1() + "(" + this.cards.getV2() + ")" + " (3K)";
		    else if (this.handType == HandType.TwoPair) return this.cards.getV1() + "(" + this.cards.getV2() + ")" + " (2P)";
		    else if (this.handType == HandType.OnePair) return this.cards.getV1() + "(" + this.cards.getV2() + ")" + " (1P)";
		    else if (this.handType == HandType.HighCard) return this.cards.getV1() + "(" + this.cards.getV2() + ")" + " (HC)";
		    else return this.cards.getV1() + "(" + this.cards.getV2() + ")" + " (" + this.handType + ")";
		}
	}
}

enum HandType {
	HighCard,
	OnePair,
	TwoPair,
	ThreeOfKind,
	FullHouse,
	FourOfKind,
	FiveOfKind;
}

enum CardValue {
	 joker, two, three, four, five, six, seven, eight, nine, ten, 
	 jester, queen, king, as;
	
	public static CardValue translate(char c, boolean withJoker)
	{
		switch (c)
		{
			case 'A': return CardValue.as;
			case 'K': return CardValue.king;
			case 'Q': return CardValue.queen;
			case 'J': return withJoker ? CardValue.joker : CardValue.jester;
			case 'T': return CardValue.ten;
			case '9': return CardValue.nine;
			case '8': return CardValue.eight;
			case '7': return CardValue.seven;
			case '6': return CardValue.six;
			case '5': return CardValue.five;
			case '4': return CardValue.four;
			case '3': return CardValue.three;
			case '2': return CardValue.two;
			default: return null;
		}
	}
 }

