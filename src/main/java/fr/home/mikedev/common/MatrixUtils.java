package fr.home.mikedev.common;

public class MatrixUtils 
{
	public static String matrixToString(char[] a)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i++)
			sb.append(a[i]);
		return sb.toString();
	}
	
    public static String matrixToString(int[] a)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++)
            sb.append(a[i]);
        return sb.toString();
    }
    
	public static String matrixToString(char[][] matrix)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
				sb.append(matrix[i][j]);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static void rotate90 (char arr[][])
	{
		transpose (arr);
		reverseRows (arr);
	}
  
	public static void reverseRows (char mat[][])
	{
		int n = mat.length;
		for (int i = 0; i < mat.length; i++){
			for (int j = 0; j <  mat.length/ 2; j++)
			{
				char temp = mat[i][j];
				mat[i][j] = mat[i][n - j - 1];
				mat[i][n - j - 1] = temp;
			}
		}    
	}

	public static void transpose (char arr[][])
	{
		for (int i = 0; i < arr.length; i++)
			for (int j = i; j < arr[0].length; j++)
			{
				char temp = arr[j][i];
				arr[j][i] = arr[i][j];
				arr[i][j] = temp;
			}
	}

    public static boolean isOutsideMatrix(Pair<Integer> plot, int matrixSize)
    {
        if (plot.getV1() < 0 || plot.getV1() > matrixSize-1 || plot.getV2() < 0 || plot.getV2() > matrixSize-1) return true;
        else return false;
    }
    
    public static boolean isOutsideMatrix(int l, int c, int matrixSize)
    {
        if (l < 0 || l > matrixSize-1 || c < 0 || c > matrixSize-1) return true;
        else return false;
    }
}
