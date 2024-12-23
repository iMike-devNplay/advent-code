package fr.home.mikedev;

import fr.home.mikedev.aoc2023.MainAoc2023;
import fr.home.mikedev.aoc2024.MainAoc2024;

public class MainAoc 
{
	public static void main(String[] args) 
	{
		new MainAoc2023("07");
		new MainAoc2024("24");
	}

	public static void log(String message)
	{
		System.out.println(message);
	}
}
