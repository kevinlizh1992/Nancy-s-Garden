import java.util.Random;

public class Dice 
{

	private int dice1;
	private int dice2;
	
	public Dice() 
		{
			dice1 = 0;
			dice1 = 0;
		}
	
	public int getDice1() 
		{
			return dice1;
		}
	
	public int getDice2() 
		{
			return dice2;
		}
	
	public int rollDice() 
		{
			Random r1 = new Random();
			Random r2 = new Random();
		
			dice1 = r1.nextInt(6) + 1;
			dice2 = r2.nextInt(6) + 1;
		
			return (dice1 + dice2);
		}
	
	//How does it return the dice result O_o
	public String toString()
	{
		return ("Die 1: " + dice1 + " Die 2: " + dice2);
	}
	
	
}
