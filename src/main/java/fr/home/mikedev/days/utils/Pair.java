package fr.home.mikedev.days.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pair<T>
{
	T v1;
	T v2;
}
