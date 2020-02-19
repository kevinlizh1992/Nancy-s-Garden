
public class Player 
{
	private String name;
	//private char [][] garden = new char [0][0];
	private Garden garden;
	
	public Player(String _name, int _garden)
	{
		name = _name;
		garden = new Garden(_garden);
	}
	
	public void setName(String _name)
	{
		name = _name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int howManyFlowersPossible()
	{
		return garden.countPossibleFlowers();
	}
	
	public int howManyTreesPossible()
	{
		return garden.countPossibleTrees();
	}
	
	public char whatIsPlanted(int row, int col)
	{
		return garden.getInLocation(row, col);
	}
	
	public void plantFlowerInGarden(int row, int col)
	{
		garden.plantFlower(row, col);
	}
	
	public void plantTreeInGarden(int row, int col)
	{
		garden.plantTree(row, col);
	}
	
	public void eatHere(int row, int col)
	{
		garden.removeFlower(row, col);
	}
	
	public boolean isGardenFull()
	{
		return garden.gardenFull();
	}
	
	public String showGarden()
	{
		return garden.toString();
	}
	
}
