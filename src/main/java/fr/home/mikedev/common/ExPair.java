package fr.home.mikedev.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(exclude = {"o"})
public class ExPair<T, K>
{
	T v1;
	K v2;
	String o;
	
	@Override
	public ExPair<T, K> clone() 
	{
		return ExPair.<T, K>builder().v1(v1).v2(v2).build();
	}
	
	
}
