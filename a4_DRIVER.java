import java.util.Random;
import java.util.Scanner;

public class a4_DRIVER {

	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("-------****-------****-------****-------****-----****-----");
		System.out.println("       Welcome to Crazy Nancy's Garden Game!		");
		System.out.println("-------****-------****-------****-------****-----****-----");
		System.out.println("\nRolls and their outcome:\n" + 
				"---------------------\n" + 
				"3: plant a tree (2x2) and a flower (1x1)\n" + 
				"6: plant 2 flowers (2 times 1x1)\n" + 
				"12: plant 2 trees (2 times 2x2)\n" + 
				"5 or 10: the rabbit will eat something that you have planted - might be a flower or\n" + 
				"part of a tree(1x1)\n" + 
				"Any other EVEN rolls: plant a tree (2x2) Any other ODD rolls: plant a flower (1x1)\n" + 
				"\nMinimum number of players: 2.\n" + 
				"Minimum garden size: 3x3.\n" + 
				"You can only plant in empty locations. To plant a tree you give the top left coordinates of the 2x2 space\n" + 
				"and I will check to make sure all 4 locations are free.\n" + 
				"Okay .. Let's start the game! May the best gardener win!!!");
		
		System.out.println("\nThe default garden size is a 3x3 square. You can use this default board size or change the size ");
	
		int userChosenGardenSize = 0;
		Player [] players;
		//user initial input
		int userAnswer;
		do {
			
			System.out.println("Enter 0 to use the default garden size or -1 to enter your own size:");
			
			userAnswer = keyboard.nextInt();
			
			if (userAnswer == 0)
				{
				Garden userGarden = new Garden(); // no need?
				userChosenGardenSize = 3;
				}
			else if (userAnswer == -1)	
				{
				System.out.print("Enter your own choice: ");
				int ownChoice = keyboard.nextInt();
				Garden userGarden = new Garden(ownChoice);  
				userChosenGardenSize = ownChoice;
				}
			else if (userAnswer != 0 && userAnswer != -1)
				{
				System.out.println("Sorry but " + userAnswer + " is not a legal choice. ");
				}
			
		} while (userAnswer != 0 && userAnswer != -1);
			
		int userAnswer2;
		//need an array of players 
		String [] nameOfPlayers;
		//Player [] players;
		
		do {
			
		System.out.println("How many gardeners will there be (minimum 2 required to play, max allowed 10)?");
			
		userAnswer2 = keyboard.nextInt();
		
		nameOfPlayers = new String [userAnswer2]; //store player names
		
		players = new Player[userAnswer2]; //convert player names to object players
		
		if (userAnswer2 >= 2 && userAnswer2 <= 10)
			{
			for (int i = 0; i < userAnswer2; i++ )
				{
				//x amount of gardens should be initiated p1, p2, p3 etc.)
				System.out.print("--> Name of player " + (i+1) + " (no space allowed) : ");
				nameOfPlayers [i] = keyboard.next();
				//players [i] = new Player(nameOfPlayers [i], userChosenGardenSize); //can put this on a loop of its own? along with userAnswer
				}
			}
		else
			System.out.println("** Sorry but " + userAnswer2 + " is not a legal number of players.");
		
		} while (userAnswer2 < 2 || userAnswer2 > 10);

		
		System.out.println("\nLet's see who goes first...");	
		//create an array of dices for initial game
		Dice [] playerDices = new Dice[userAnswer2];

		
		for (int j = 0; j< userAnswer2; j++)
		{
			playerDices [j] = new Dice();
		}
		//roll player dices and displaying them
		//should create a dice result array, and then see whos largest with scanning
		int [] diceResults = new int [userAnswer2];
		int max = 0;
		int l = 0;
		int m = 0;
		boolean duplicates;
		String diceWinner = "";
		
		do{
			
		duplicates = false;	
		max = diceResults [0];
		
		for (int k = 0; k< userAnswer2; k++)
			{
			diceResults [k] = playerDices[k].rollDice();
			System.out.println(nameOfPlayers[k] + " rolled a " + diceResults [k]);
			
			if (max < diceResults[k])
				{
				max = diceResults[k];
				diceWinner = nameOfPlayers[k]; //getting the winner stored
				}
			}
		
		//smart way of checking if 2 things in the array are the same
		for (l = 0; l < userAnswer2; l++) 
			for (m = l+1; m < userAnswer2; m++)
				if (m!=l && diceResults [l] == diceResults [m] && (diceResults [l] == max)) //m!=l && , otherwise, eventually they are the same
					{
					System.out.println("We will start over as " + diceResults[l] + " was rolled by " + nameOfPlayers[m] + " as well");
					duplicates = true;
					}	
		
		} while (duplicates == true);
		
		if (duplicates == false)
			System.out.println(diceWinner + " goes first.");
		
		
		//start the play time
		System.out.println("\nTime to play!!!!");
		System.out.println("--------------------");
		
		//block of code to make the winner of the dice roll go first, and assigning the other play names to player objects
		for (int n = 0; n < userAnswer2; n++)
		{	
			
			players [n] = new Player(nameOfPlayers [n], userChosenGardenSize);
		}
		
		Player temp = new Player("", userChosenGardenSize);
		temp.setName(players[0].getName()); 
		players [0] = new Player(diceWinner, userChosenGardenSize);
		
		for (int n = 1; n < userAnswer2; n++)
		{	
			if (players[n].getName() == players[0].getName()) 
				{
					nameOfPlayers[n] = temp.getName();
					players	[n].setName(temp.getName());
					
				}			
		}
		
	
		//2nd dice array to use once the game starts after initial dice roll which determined who goes first
		Dice [] playerDicesInGame = new Dice[userAnswer2];
		for (int z = 0; z < userAnswer2 ; z++)
			playerDicesInGame[z] = new Dice();
		    
		//boolean to control when stopping condition of the game i.e. when one player's garden is full
		boolean stopGameShowResults = false;
		int gameRoundsCounter = 0; 	//initializing a counter to count number of rounds of the game

				
	do{											
		stopGameShowResults = true;		
		for (int o = 0; o < userAnswer2; o++)
		{
			stopGameShowResults = false;
			
			//dice roll at the beginning of each round for each player
			int rolledDiceInGame = playerDicesInGame[o].rollDice();
	
			//display to players what they rolled
			System.out.println("\n" + players[o].getName() + " you rolled " + rolledDiceInGame + " (" + playerDicesInGame[o].toString() + ") ");	
			
			if (rolledDiceInGame == 3) 
			{
				System.out.println("You must plant a tree (2x2) and a flower (1x1)");
				System.out.println(players[o].showGarden());
				
				if (players[o].howManyTreesPossible() == 0) 
					{
					System.out.println("** Sorry no room left to plant a tree");
					System.out.println("You still have a flower (1x1) to plant.");
					System.out.println(players[o].showGarden());
					System.out.println("You now have " + players[o].howManyFlowersPossible() + " places to do this.");
					System.out.print("Enter coordinates as row column: ");
					int rowGarden1 = keyboard.nextInt(); 
					int colGarden1 = keyboard.nextInt(); 

					//while loop against out of bound user input or if user coordinate input is already taken up
					 while ((rowGarden1 >= userChosenGardenSize || colGarden1 >= userChosenGardenSize) || ( (rowGarden1 < userChosenGardenSize || colGarden1 < userChosenGardenSize) &&(players[o].whatIsPlanted(rowGarden1, colGarden1) != '-')))
							
							if ((rowGarden1 >= userChosenGardenSize || colGarden1 >= userChosenGardenSize))
							{
								
								if (rowGarden1 >= userChosenGardenSize || colGarden1 >= userChosenGardenSize)
								{
									System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your flower will fall off grid. Try again");	
									rowGarden1 = keyboard.nextInt(); 
									colGarden1 = keyboard.nextInt(); 
								}
							}
							
							
							else if ((rowGarden1 < userChosenGardenSize || colGarden1 < userChosenGardenSize))
							{	
							
								if (players[o].whatIsPlanted(rowGarden1, colGarden1) != '-')
								{
									System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden1, colGarden1) + "\nPlease enter new coordinates");
									rowGarden1 = keyboard.nextInt(); 
									colGarden1 = keyboard.nextInt();
								}
							}
					
					
					players[o].plantFlowerInGarden(rowGarden1, colGarden1);
					
					if (players[o].isGardenFull() == true) //this should be at the end too?
						{
						stopGameShowResults = true;
						break;
						}
					}
				else
					{	
					System.out.println("Let's start with the tree. You have " + players[o].howManyTreesPossible() + " places to do this.");
					System.out.print("Enter coordinates as row column: ");
					int rowGarden = keyboard.nextInt(); 
					int colGarden = keyboard.nextInt(); 
					
					while ((rowGarden > userChosenGardenSize-2 || colGarden > userChosenGardenSize-2) || ( (rowGarden <= userChosenGardenSize-2 || colGarden <= userChosenGardenSize-2) &&(players[o].whatIsPlanted(rowGarden, colGarden) != '-')))
							
							if ((rowGarden > userChosenGardenSize-2 || colGarden > userChosenGardenSize-2))
							{
								
								if ((rowGarden > userChosenGardenSize-2 || colGarden > userChosenGardenSize-2))
								{
									System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your tree will fall off grid. Try again");	
									rowGarden = keyboard.nextInt(); 
									colGarden = keyboard.nextInt(); 
								}
							}
							
							
							else if ((rowGarden <= userChosenGardenSize-2 || colGarden <= userChosenGardenSize-2))
							{	
							
								if (players[o].whatIsPlanted(rowGarden, colGarden) != '-')
								{
									System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden, colGarden) + "\nPlease enter new coordinates");
									rowGarden = keyboard.nextInt(); 
									colGarden = keyboard.nextInt();
								}
							}
					
					players[o].plantTreeInGarden(rowGarden, colGarden);		
					
					if (players[o].isGardenFull() == true) 
						{
						stopGameShowResults = true;
						break;
						}	
					else
						{
						
						System.out.println("You still have a flower (1x1) to plant.");
						System.out.println(players[o].showGarden());
						System.out.println("You now have " + players[o].howManyFlowersPossible() + " places to do this.");
						System.out.print("Enter coordinates as row column: ");
						int rowGarden1 = keyboard.nextInt(); 
						int colGarden1 = keyboard.nextInt(); 
						
						while ((rowGarden1 >= userChosenGardenSize || colGarden1 >= userChosenGardenSize) || ( (rowGarden1 < userChosenGardenSize || colGarden1 < userChosenGardenSize) &&(players[o].whatIsPlanted(rowGarden1, colGarden1) != '-')))
								
								if ((rowGarden1 >= userChosenGardenSize || colGarden1 >= userChosenGardenSize))
								{
									
									if (rowGarden1 >= userChosenGardenSize || colGarden1 >= userChosenGardenSize)
									{
										System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your flower will fall off grid. Try again");	
										rowGarden1 = keyboard.nextInt(); 
										colGarden1 = keyboard.nextInt(); 
									}
								}
								
								
								else if ((rowGarden1 < userChosenGardenSize || colGarden1 < userChosenGardenSize))
								{	
								
									if (players[o].whatIsPlanted(rowGarden1, colGarden1) != '-')
									{
										System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden1, colGarden1) + "\nPlease enter new coordinates");
										rowGarden1 = keyboard.nextInt(); 
										colGarden1 = keyboard.nextInt(); //its here thats fucked
									}
								}
						
							
						
						
						
						players[o].plantFlowerInGarden(rowGarden1, colGarden1);
						
						if (players[o].isGardenFull() == true) //this should be at the end too?
							{
							stopGameShowResults = true;
							break;
							}
						}
					}
			}
				
			
			if (rolledDiceInGame == 6) 
			{
				System.out.println("You must plant 2 flowers (1x1)");
				
				if (players[o].isGardenFull() == true) 
					{
					stopGameShowResults = true;
					break;
					}
				else
					{
					System.out.println("You have " + players[o].howManyFlowersPossible() + " places to do this.");
					System.out.println(players[o].showGarden());
					System.out.println("First flower - Enter coordinates as row column: ");
					int rowGarden2 = keyboard.nextInt(); 
					int colGarden2 = keyboard.nextInt(); 
					
					while ((rowGarden2 >= userChosenGardenSize || colGarden2 >= userChosenGardenSize) || ( (rowGarden2 < userChosenGardenSize || colGarden2 < userChosenGardenSize) &&(players[o].whatIsPlanted(rowGarden2, colGarden2) != '-')))
							
							if ((rowGarden2 >= userChosenGardenSize || colGarden2 >= userChosenGardenSize))
							{
								
								if (rowGarden2 >= userChosenGardenSize || colGarden2 >= userChosenGardenSize)
								{
									System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your flower will fall off grid. Try again");	
									rowGarden2 = keyboard.nextInt(); 
									colGarden2 = keyboard.nextInt(); 
								}
							}
							
							
							else if ((rowGarden2 < userChosenGardenSize || colGarden2 < userChosenGardenSize))
							{	
							
								if (players[o].whatIsPlanted(rowGarden2, colGarden2) != '-')
								{
									System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden2, colGarden2) + "\nPlease enter new coordinates");
									rowGarden2 = keyboard.nextInt(); 
									colGarden2 = keyboard.nextInt(); //its here thats fucked
								}
							}
		
					players[o].plantFlowerInGarden(rowGarden2, colGarden2);
					System.out.println(players[o].showGarden());	
					
					if (players[o].isGardenFull() == true) 
						{
						stopGameShowResults = true;
						break;
						}
					
					else
						{
						System.out.println("Second flower - Enter coordinates as row column: ");
						int rowGarden3 = keyboard.nextInt(); 
						int colGarden3 = keyboard.nextInt(); 
						
						while ((rowGarden3 >= userChosenGardenSize || colGarden3 >= userChosenGardenSize) || ( (rowGarden3 < userChosenGardenSize || colGarden3 < userChosenGardenSize) &&(players[o].whatIsPlanted(rowGarden3, colGarden3) != '-')))
								
								if ((rowGarden3 >= userChosenGardenSize || colGarden3 >= userChosenGardenSize))
								{
									
									if (rowGarden3 >= userChosenGardenSize || colGarden3 >= userChosenGardenSize)
									{
										System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your flower will fall off grid. Try again");	
										rowGarden3 = keyboard.nextInt(); 
										colGarden3 = keyboard.nextInt(); 
									}
								}
								
								
								else if ((rowGarden3 < userChosenGardenSize || colGarden3 < userChosenGardenSize))
								{	
								
									if (players[o].whatIsPlanted(rowGarden3, colGarden3) != '-')
									{
										System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden3, colGarden3) + "\nPlease enter new coordinates");
										rowGarden3 = keyboard.nextInt(); 
										colGarden3 = keyboard.nextInt(); //its here thats fucked
									}
								}
						
							players[o].plantFlowerInGarden(rowGarden3, colGarden3);
						
						if (players[o].isGardenFull() == true) 
							{
							stopGameShowResults = true;
							break;
							}
						}
					}
			}
			
			
			if (rolledDiceInGame == 12) 
			{
				System.out.println("You must plant 2 tree (2x2)");
			
				
				if (players[o].howManyTreesPossible() == 0) 
					{
					System.out.println(players[o].showGarden());
					System.out.println("** Sorry no room left to plant a tree and a flower - You miss a turn");
					}
				else
					{
					System.out.println("You have " + players[o].howManyTreesPossible() + " places to do this.");
					System.out.println(players[o].showGarden());
					System.out.println("First tree - Enter coordinates as row column: ");
					int rowGarden4 = keyboard.nextInt(); 
					int colGarden4 = keyboard.nextInt(); 
					
					while ((rowGarden4 > userChosenGardenSize-2 || colGarden4 > userChosenGardenSize-2) || ( (rowGarden4 <= userChosenGardenSize-2 || colGarden4 <= userChosenGardenSize-2) &&(players[o].whatIsPlanted(rowGarden4, colGarden4) != '-')))
							
							if ((rowGarden4 > userChosenGardenSize-2 || colGarden4 > userChosenGardenSize-2))
							{
								
								if ((rowGarden4 > userChosenGardenSize-2 || colGarden4 > userChosenGardenSize-2))
								{
									System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your tree will fall off grid. Try again");	
									rowGarden4 = keyboard.nextInt(); 
									colGarden4 = keyboard.nextInt(); 
								}
							}
							
							else if ((rowGarden4 <= userChosenGardenSize-2 || colGarden4 <= userChosenGardenSize-2))
							{	
							
								if (players[o].whatIsPlanted(rowGarden4, colGarden4) != '-')
								{
									System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden4, colGarden4) + "\nPlease enter new coordinates");
									rowGarden4 = keyboard.nextInt(); 
									colGarden4 = keyboard.nextInt();
								}
							}
					
					players[o].plantTreeInGarden(rowGarden4, colGarden4);
					System.out.println(players[o].showGarden());
					
				
					if (players[o].isGardenFull() == true) 
						{
						stopGameShowResults = true;
						break;
						}
					if (players[o].howManyTreesPossible() == 0 && players[o].howManyFlowersPossible() != 0)
						{
						System.out.println("** Sorry no room left to plant another tree");
						}
					else
						{
						System.out.println("Second tree - Enter coordinates as row column: ");
						int rowGarden5 = keyboard.nextInt(); 
						int colGarden5 = keyboard.nextInt(); 
						
						while ((rowGarden5 > userChosenGardenSize-2 || colGarden5 > userChosenGardenSize-2) || ( (rowGarden5 <= userChosenGardenSize-2 || colGarden5 <= userChosenGardenSize-2) &&(players[o].whatIsPlanted(rowGarden5, colGarden5) != '-')))
								
								if ((rowGarden5 > userChosenGardenSize-2 || colGarden5 > userChosenGardenSize-2))
								{
									
									if ((rowGarden5 > userChosenGardenSize-2 || colGarden5 > userChosenGardenSize-2))
									{
										System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your tree will fall off grid. Try again");	
										rowGarden5 = keyboard.nextInt(); 
										colGarden5 = keyboard.nextInt(); 
									}
								}
								
								else if ((rowGarden5 <= userChosenGardenSize-2 || colGarden5 <= userChosenGardenSize-2))
								{	
								
									if (players[o].whatIsPlanted(rowGarden5, colGarden5) != '-')
									{
										System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden5, colGarden5) + "\nPlease enter new coordinates");
										rowGarden5 = keyboard.nextInt(); 
										colGarden5 = keyboard.nextInt();
									}
								}

						players[o].plantTreeInGarden(rowGarden5, colGarden5);
							
						if (players[o].isGardenFull() == true) 
							{
							stopGameShowResults = true;
							break;
							}
						}
					}
			}
			
			if (rolledDiceInGame == 5 || rolledDiceInGame == 10) 
			{
				System.out.println(players[o].showGarden());
				//generate a random location with something planted
				int rabbitEatsRow;
				int rabbitEatsCol;
				boolean exitRabbitLoop;
			
				
			 do {
					exitRabbitLoop = true;
					
					Random r_r = new Random();
					Random r_c = new Random();
		
					rabbitEatsRow = r_r.nextInt(userChosenGardenSize); 
					rabbitEatsCol = r_c.nextInt(userChosenGardenSize); 
					
				if (players[o].whatIsPlanted(rabbitEatsRow, rabbitEatsCol) != '-')
					{
					players[o].eatHere(rabbitEatsRow, rabbitEatsCol);
					System.out.println("The rabbit ate whatever was planted in location (" + rabbitEatsRow + ", " + rabbitEatsCol + ")" );
					System.out.println(players[o].showGarden());
					exitRabbitLoop = true;
					}
				
				else if (players[o].howManyFlowersPossible() == userChosenGardenSize*userChosenGardenSize) 
					{
					System.out.println("The rabbit has nothing to eat yet, your garden is empty");
					exitRabbitLoop = true;
					}
				
				else if (players[o].whatIsPlanted(rabbitEatsRow, rabbitEatsCol) == '-' && players[o].howManyFlowersPossible() < userChosenGardenSize*userChosenGardenSize)
					{
					exitRabbitLoop = false;
					}
				else
					{
					exitRabbitLoop = true;
					System.out.println("error");
					}
				
				} while (exitRabbitLoop == false);
			}
				
			if (rolledDiceInGame == 7 || rolledDiceInGame == 9 || rolledDiceInGame == 11)
			{
				System.out.println("You must plant a flower (1x1)");
				System.out.println(players[o].showGarden());
				
				if (players[o].isGardenFull() == true) //this should be at the end too?
					{
					stopGameShowResults = true;
					break;
					}
				else 
					{
					System.out.println("You have " + players[o].howManyFlowersPossible() + " places to do this.");
					System.out.print("Enter coordinates as row column: ");
					int rowGarden7 = keyboard.nextInt(); 
					int colGarden7 = keyboard.nextInt(); 
					
					while ((rowGarden7 >= userChosenGardenSize || colGarden7 >= userChosenGardenSize) || ( (rowGarden7 < userChosenGardenSize || colGarden7 < userChosenGardenSize) &&(players[o].whatIsPlanted(rowGarden7, colGarden7) != '-')))
							
							if ((rowGarden7 >= userChosenGardenSize || colGarden7 >= userChosenGardenSize))
							{
								
								if (rowGarden7 >= userChosenGardenSize || colGarden7 >= userChosenGardenSize)
								{
									System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your flower will fall off grid. Try again");	
									rowGarden7 = keyboard.nextInt(); 
									colGarden7 = keyboard.nextInt(); 
								}
							}
							
							
							else if ((rowGarden7 < userChosenGardenSize || colGarden7 < userChosenGardenSize))
							{	
							
								if (players[o].whatIsPlanted(rowGarden7, colGarden7) != '-')
								{
									System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden7, colGarden7) + "\nPlease enter new coordinates");
									rowGarden7 = keyboard.nextInt(); 
									colGarden7 = keyboard.nextInt(); //its here thats fucked
								}
							}
			
					players[o].plantFlowerInGarden(rowGarden7, colGarden7);
					
					if (players[o].isGardenFull() == true)
						{
						stopGameShowResults = true;
						break;
						}
					}
			}
				
			if (rolledDiceInGame == 2 || rolledDiceInGame == 4 || rolledDiceInGame == 8)	
			{
				System.out.println("You must plant a tree (2x2)");
				System.out.println(players[o].showGarden());
				
				if (players[o].howManyTreesPossible() == 0 && players[o].howManyFlowersPossible() != 0) 
					{
					System.out.println("** Sorry no room left to plant a tree - You miss a turn");
					}
				else if (players[o].isGardenFull() == true) 
					{
					stopGameShowResults = true;
					break;
					}
				else
					{
					System.out.println("You have " + players[o].howManyTreesPossible() + " places to do this.");
					System.out.print("Enter coordinates as row column: ");
					int rowGarden6 = keyboard.nextInt(); 
					int colGarden6 = keyboard.nextInt(); 
					
					while ((rowGarden6 > userChosenGardenSize-2 || colGarden6 > userChosenGardenSize-2) || ( (rowGarden6 <= userChosenGardenSize-2 || colGarden6 <= userChosenGardenSize-2) &&(players[o].whatIsPlanted(rowGarden6, colGarden6) != '-')))
							
							if ((rowGarden6 > userChosenGardenSize-2 || colGarden6 > userChosenGardenSize-2))
							{
								
								if ((rowGarden6 > userChosenGardenSize-2 || colGarden6 > userChosenGardenSize-2))
								{
									System.out.println("** Sorry either the row or column is not the range of 0 to " + (userChosenGardenSize-1) + " or your tree will fall off grid. Try again");	
									rowGarden6 = keyboard.nextInt(); 
									colGarden6 = keyboard.nextInt(); 
								}
							}
							
							else if ((rowGarden6 <= userChosenGardenSize-2 || colGarden6 <= userChosenGardenSize-2))
							{	
							
								if (players[o].whatIsPlanted(rowGarden6, colGarden6) != '-')
								{
									System.out.println("** Sorry that location is already taken up by a " + players[o].whatIsPlanted (rowGarden6, colGarden6) + "\nPlease enter new coordinates");
									rowGarden6 = keyboard.nextInt(); 
									colGarden6 = keyboard.nextInt();
								}
							}	
				
					players[o].plantTreeInGarden(rowGarden6, colGarden6);
					
					if (players[o].isGardenFull() == true) 
						{
						stopGameShowResults = true;
						break;
						}					
					}
			}
		
			gameRoundsCounter++;
			
		} 
			
		} while (stopGameShowResults == false);
	
		
		//RESULT SECTION 
		System.out.println("\nFINAL RESULTS");
		System.out.println("------------------\n");
		System.out.println("Here are the gardens after " + (gameRoundsCounter+1) + " rounds.");
		
		for (int r = 0; r < userAnswer2; r++)
		{
			System.out.println(players[r].getName() + " garden");
			System.out.println(players[r].showGarden());
		}
	 
		String winnerOfTheGame = "";
		
		for (int s = 0; s < userAnswer2; s++)
		{
			if (players[s].isGardenFull() == true)
				winnerOfTheGame = players[s].getName();
		}
		
		System.out.println("And the winner is ..... " + winnerOfTheGame);
		System.out.println("What a beautiful garden you have.");
		System.out.println("\nHope you had fun!!!!");
    
		//2. use switch replace if statement

		}
		
		
	}



