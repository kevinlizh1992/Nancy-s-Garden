
public class Garden 
{

	private char [][] garden = new char [0][0];
	
	public Garden() 
	{
	 garden = new char [3][3]; 	
	 initializeGarden();
	}
	
	public Garden(int sizeOfGarden)
	{
	 garden = new char [sizeOfGarden][sizeOfGarden];
	 initializeGarden();
	}
	
	private void initializeGarden() 
		{
		for (int row = 0; row<garden.length; row++)
			for (int col = 0; col<garden[row].length; col++)
				{
					garden [row][col] = '-'; 
				}

		}
	
	public char getInLocation(int row, int col)
	{
		return (garden [row][col]);
	}
	
	public void plantFlower(int row, int col)
	{
		garden [row][col] = 'f';
	}
	
	public void plantTree(int row, int col) 
	{
		garden [row][col] = 't';
		garden [row+1][col] = 't';
		garden [row][col+1] = 't';
		garden [row+1][col+1] = 't';
	}
	
	public void removeFlower(int row, int col)
	{
		garden [row][col] = '-';
	}
	
	
	public int countPossibleTrees() 
	{
		int count_t = 0;
		for (int row = 0; row<garden.length-1; row++)
			for (int col = 0; col<garden[row].length-1; col++)
			{
				if ((garden [row][col] == '-') && (garden [row+1][col] == '-') && (garden [row][col+1] == '-') && (garden [row+1][col+1] =='-'))
				{
					count_t ++;
				}
			}
		return count_t;
	}
	
	public int countPossibleFlowers()
	{
	int count_f = 0;
	for (int row = 0; row<garden.length; row++)
		for (int col = 0; col<garden[row].length; col++)
		{
			if (garden [row][col] == '-')
			{
				count_f++;
			}
		}
		return count_f;
	}
	
	//maybe my gardenfull isnt working
	public boolean gardenFull()
	{
		boolean valid = true;
	
		for (int row = 0; row<garden.length; row++)
			for (int col = 0; col<garden[row].length; col++)
				{
				if (garden [row][col] == '-')
					{
					return false;
					}
				}	
		return (valid);
	}			
	
	// need to also print | and 0 1 2 3...
	
	public String toString()
	{
		int row;
		int col;
		String stringGarden = "";	
		
		System.out.print(" |");
		
		for (col = 0; col<garden.length; col++)
			{
			System.out.print("\t" + col);
			}
		
		System.out.println();
			
		for (row = 0; row<garden.length; row++)
			for (col = 0; col<garden[row].length; col++)
				
				{
					if (col == 0)
						{
						stringGarden += (row + "|");
						}
					
					stringGarden += ("\t" + garden [row][col]);
				
					if (col == garden.length-1)
						{
						stringGarden += "\n";
						}
				}
		
		return (stringGarden);
		
	}
				
				
	
}
	
	

