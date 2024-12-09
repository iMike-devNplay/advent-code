package fr.home.mikedev.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatrixCoordinate 
{
	int c;
	int l;
	char direction;
}
