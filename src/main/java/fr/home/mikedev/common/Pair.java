package fr.home.mikedev.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pair<T>
{
	T v1;
	T v2;
	String o;
}
