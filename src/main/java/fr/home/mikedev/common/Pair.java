package fr.home.mikedev.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(exclude = {"o"})
public class Pair<T>
{
	T v1;
	T v2;
	String o;
	
	@Override
	public Pair<T> clone() 
	{
		return Pair.<T>builder().v1(v1).v2(v2).build();
	}
	
	
}
