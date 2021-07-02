import java.util.*;
import java.io.*;
import java.util.Scanner; 
import java.util.Random;

//Supreyo Atonu
//Pokémon Project

public class Main {
	static ArrayList<Pokemon> enemy = new ArrayList<Pokemon>();
	
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(new File("C:\\Users\\Supreyo Atonu\\eclipse-workspace\\Pokemon Project\\src\\Pokemon.txt")); //Reading from Pokémon file
		Scanner stdin = new Scanner(System.in);
		Random rand = new Random();
		
		String line = scan.nextLine();
		int numPoke = Integer.parseInt(line);
		
		for (int i = 0; i < numPoke; i++) {
			line = scan.nextLine();
			enemy.add(new Pokemon(line));
		}
		scan.close();
		
		ArrayList<Pokemon> playerPoke = new ArrayList<Pokemon>();
		ArrayList<Pokemon> oppoPoke = (ArrayList) enemy.clone(); 
		
		ArrayList<Integer> pokeEnergy = new ArrayList<Integer>();
		ArrayList<Integer> pokeHP = new ArrayList<Integer>();
	
		boolean playerTurn = true;          //Used to switch between Player and Enemy turn
		boolean enemyFaint = false;
		boolean playerFaint = false;
		boolean gameEnd = false;			//Used to keep looping Player and Enemy turns 
		boolean disabled= false;
		boolean enemyDisabled = false;
		
		int pokeCount = 4;
		
		int disableCount = 0;
		int wildStormCount = 0;
		
		int enemydisableCount = 0;
		int enemywildStormCount = 0;
	
		String status = "";
		String enemyStatus = "";
		String[] actions = {"Attack", "Retreat", "Pass"};
	
		Pokemon playerCurrentPoke = enemy.get(0); //Added enemy.get(0) so variable can be initialized
		Pokemon enemyCurrentPoke;
			
		System.out.println("<====================================== Pokémon Arena ==========================================================>");
		
		for(int i = 0; i < enemy.size(); i++) {
			System.out.printf(i + ". %-15s",(enemy.get(i).getName()));
			if ((i-6)%7 == 0) {
				System.out.println();
			}
		}
		
		System.out.println("<================================================================================================================>");
		System.out.println("Welcome to Pokémon Arena Trainer! Its time to choose your 4 Pokémon!");
				
		while (playerPoke.size() < 4) { 
			int chosenPoke = stdin.nextInt();
			
			if (chosenPoke > enemy.size() || chosenPoke <= 0) {      //Checks to see if valid index so program does not crash
				System.out.println("Invalid Input!");
				System.out.println("Choose a valid option!");
			}
			
			else {
			
				if (playerPoke.size() > 0 && playerPoke.contains(enemy.get(chosenPoke - 1))) {   //Checking for duplicates
					System.out.println("You already chose " + enemy.get(chosenPoke - 1).getName() + "!");
					System.out.println("Please choose another Pokémon you haven't picked yet");
				}
				
				else {
					System.out.println("======================== Choose Your Pokémon =========================");
					
					System.out.println("\n" + "    ======================== Pokémon Info ========================");
					System.out.println("         		You chose: " + enemy.get(chosenPoke - 1).getName());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " HP:              " + enemy.get(chosenPoke - 1).getHP());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " Type:            " + enemy.get(chosenPoke - 1).getType());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " Resistance:      " + enemy.get(chosenPoke - 1).getResistance());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " Weakness:        " + enemy.get(chosenPoke - 1).getWeakness());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " Num of Attacks:  " + enemy.get(chosenPoke - 1).getNumAttacks());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " Attacks:         " + enemy.get(chosenPoke - 1).getAttacks());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " Energy Cost:     " + enemy.get(chosenPoke - 1).getEnergyCost());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " Damage:          " + enemy.get(chosenPoke - 1).getDamage());
					System.out.println("            " + enemy.get(chosenPoke - 1).getName() + " Specials:        " + enemy.get(chosenPoke - 1).getSpecial());
					System.out.println("    ==============================================================" + "\n");
					
					System.out.println("Are you sure you want to choose " + enemy.get(chosenPoke - 1).getName() + "? Yes(1) No(2)");
							
					int check = stdin.nextInt();     //Checking if Player is sure he/she wants to choose this Pokémon
					
					if (check == 1) {
						pokeCount--;				
						System.out.println(enemy.get(chosenPoke - 1).getName() + " is now on your team!");
						
						if (playerPoke.size() == 3) {
							System.out.println("======================================================================" + "\n");
						}
						
						pokeEnergy.add(50);
						pokeHP.add(enemy.get(chosenPoke - 1).getHP());
						
						if (pokeCount != 0) {
							System.out.println("You have " + pokeCount + " more Pokémon to choose from!");
							System.out.println("======================================================================");
						}
						
						playerPoke.add(enemy.get(chosenPoke - 1));
						oppoPoke.remove(enemy.get(chosenPoke - 1));
						
					}
					
					else if (check == 2) {
						System.out.println("You did not choose " + enemy.get(chosenPoke - 1).getName());
						System.out.println("You have " + pokeCount + " more Pokémon to choose from!");
						System.out.println("======================================================================");
						
					}
					else {
						System.out.println("Invalid input!");
						System.out.println("Choose a valid option!");
					}
				}
			}
		}
			
		int pokeIndex = 0;
		int chosenFirst = 0;
		int checkFirst = 0;
		int energyCount = 0;
		int enemyEnergyCount = 0;
		
		
		while (checkFirst != 1) {
			System.out.println("========================== Pre Battle ================================");
			System.out.println("Choose which Pokémon goes first!");
			for (int i = 0; i < playerPoke.size(); i++) {
				System.out.println(playerPoke.get(i).getName());
			}
			
			chosenFirst = stdin.nextInt();
			
			if (chosenFirst > playerPoke.size() || chosenFirst <= 0) {   //Checks to see if valid index so program does not crash
				System.out.println("\n" + "Invalid Input!");
				System.out.println("Choose a valid option!");
			}
			
			else {
				System.out.println("\n" + "    ======================== Pokémon Info ========================");
				System.out.println("         		You chose: " + enemy.get(chosenFirst - 1).getName());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " HP:              " + enemy.get(chosenFirst - 1).getHP());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " Type:            " + enemy.get(chosenFirst - 1).getType());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " Resistance:      " + enemy.get(chosenFirst - 1).getResistance());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " Weakness:        " + enemy.get(chosenFirst - 1).getWeakness());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " Num of Attacks:  " + enemy.get(chosenFirst - 1).getNumAttacks());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " Attacks:         " + enemy.get(chosenFirst - 1).getAttacks());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " Energy Cost:     " + enemy.get(chosenFirst - 1).getEnergyCost());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " Damage:          " + enemy.get(chosenFirst - 1).getDamage());
				System.out.println("            " + enemy.get(chosenFirst - 1).getName() + " Specials:        " + enemy.get(chosenFirst - 1).getSpecial());
				System.out.println("    ==============================================================" + "\n");
				System.out.println("Are you sure you want " + playerPoke.get(chosenFirst - 1).getName() + " to go first? Yes(1) No(2)");
				
				checkFirst = stdin.nextInt();
				
				if (checkFirst == 1) {
					System.out.println("\n" + "========================== Pre Battle ================================");
					System.out.println(playerPoke.get(chosenFirst - 1).getName() + " I choose you!");
					playerCurrentPoke = playerPoke.get(chosenFirst - 1);   //Keeping track of current Pokémon, index and number of attacks
					pokeIndex = chosenFirst - 1;
					energyCount = playerCurrentPoke.getNumAttacks();
				}
				
				else if (checkFirst == 2){
					System.out.println("Choose which Pokémon goes first!");
					System.out.println("======================================================================" + "\n");
				}
				
				else {
					System.out.println("Invalid Input!");
					System.out.println("Choose a valid option!");
				}
			}
		}
			
		int  randomEnemyPoke = rand.nextInt(oppoPoke.size()) + 0;  //Randomly decides which enemy Pokémon goes first
		enemyCurrentPoke = oppoPoke.get(randomEnemyPoke);      //Keeping track of current enemy Pokémon and number of attacks
		enemyEnergyCount = enemyCurrentPoke.getNumAttacks();
	
		int enemyHP = enemyCurrentPoke.getHP();  //Setting enemy HP and Energy
		int enemyEnergy = 50;
		
		System.out.println("Enemy chooses " + enemyCurrentPoke.getName() + " to go up against " + playerCurrentPoke.getName() + "!");
		System.out.println("======================================================================" + "\n");
		
		System.out.println("============================= Randomizer =============================");
		
		System.out.println("Now its time to randomly decide who goes first!");

		int  randomTurn = rand.nextInt(2) + 0; //Randomly decides who goes first
		if (randomTurn == 0) {
			System.out.println("Player goes first!");
			System.out.println("======================================================================" + "\n");
			playerTurn = true;
		}
		
		else if (randomTurn == 1){
			System.out.println("Enemy goes first!");
			System.out.println("======================================================================" + "\n");
			playerTurn = false;
		}
				
		while (gameEnd != true) {
			if (playerTurn) {
				if (playerFaint == false) {
					
					if (enemyStatus == "stun") {    //Checking if current Pokémon is stunned so it cannot do any actions
						playerTurn = false;
					}
					else {						
						int action = 0;					//Checking if Pokémon has no energy to do any moves
						int actionCheck = 0;
						
						energyCount = playerCurrentPoke.getNumAttacks();  //Resetting variable to be subtracted again
						
						for (int i = 0; i < playerCurrentPoke.getNumAttacks(); i++) {
							if (playerCurrentPoke.getEnergyCost().get(i) > pokeEnergy.get(pokeIndex)) {
								energyCount--;  //Subtracting to check how many attacks are available
							}					//If equals 0, no attacks have enough energy			
						}
						
						while (actionCheck != 1) {
							System.out.println("============================ Player Turn =============================");
					
							System.out.println("Choose an Action: ");
							System.out.println("Attack(1)   Retreat(2)   Pass(3)");
									
							System.out.println("======================================================================");
							
							action = stdin.nextInt();
							
							if (action > 3 || action <= 0) {   //Checking for valid index so program does not crash
								System.out.println("\n" + "Invalid Input!");
								System.out.println("Choose a valid option!");
							}
							
							else {
								if (energyCount <= 0) { //Checks if attacks are available to use
									if (action == 1) {
										System.out.println("Player " + playerCurrentPoke.getName() + " has no energy to do any attack!");
										System.out.println("Player " + playerCurrentPoke.getName() + " has to pass his/her turn or retreat");
									}
									
									else {
										System.out.println("Are you sure you want " + actions[action - 1] + " to be your action? Yes(1) No(2)");
										actionCheck = stdin.nextInt();
									}
									
								}
								
								else {
									System.out.println("Are you sure you want " + actions[action - 1] + " to be your action? Yes(1) No(2)");
									actionCheck = stdin.nextInt();
								}
							}
						}
											
						if (action == 1) {
							System.out.println("============================ Player Turn =============================");						
							
							System.out.println(playerCurrentPoke.getName() + " Attacks:           " + playerCurrentPoke.getAttacks());
							System.out.println(playerCurrentPoke.getName() + " Energy Costs:      " + playerCurrentPoke.getEnergyCost());
							System.out.println(playerCurrentPoke.getName() + " Attack Damages:    " + playerCurrentPoke.getDamage());
							System.out.println(playerCurrentPoke.getName() + " Specials:          " + playerCurrentPoke.getSpecial());

							System.out.println("\n" + "Choose an attack from the above Attack List");
							System.out.println("======================================================================");
						
							int attack = 0;
							int attackCheck = 0;
							
							while (attackCheck != 1) {
								attack = stdin.nextInt(); 
								
								if (attack > playerCurrentPoke.getNumAttacks() || attack <= 0) {
									System.out.println("Invalid Input!");
									System.out.println("Choose a valid option!");
								}
								
								else {
									if (playerCurrentPoke.getEnergyCost().get(attack -1) > pokeEnergy.get(pokeIndex)) { 	//Checks to see if Pokemon has enough energy to use the attack player chose
										System.out.println("Not enough energy!");											//If not enough energy, forces player to use a different attack
										System.out.println("Please use a different attack");
									}
											
									else {
										System.out.println("Are you sure you want " + playerCurrentPoke.getName() + " to use " + playerCurrentPoke.getAttacks().get(attack - 1) + "? Yes(1) No(2)");
										attackCheck = stdin.nextInt(); 
												
										if (attackCheck == 1) {
				
										}
												
										else if (attackCheck == 2){
											System.out.println("============================ Player Turn =============================");						
													
											System.out.println(playerCurrentPoke.getName() + " Attacks:           " + playerCurrentPoke.getAttacks());
											System.out.println(playerCurrentPoke.getName() + " Energy Costs:      " + playerCurrentPoke.getEnergyCost());
											System.out.println(playerCurrentPoke.getName() + " Attack Damages:    " + playerCurrentPoke.getDamage());
											System.out.println(playerCurrentPoke.getName() + " Specials:          " + playerCurrentPoke.getSpecial());
				
											System.out.println("\n" + "Choose an attack from the above Attack List");
											System.out.println("======================================================================");
										}
											
										else {
											System.out.println("\n" + "Invalid Input!");
											System.out.println("Choose a valid option!");
										}
									}
								}
							}
						
							if (playerCurrentPoke.getType().equals(enemyCurrentPoke.getResistance())) {	  //Checking if enemy Pokemon resists player Pokemon and if it does all attacks cut in half 
								System.out.println("============================ Player Turn =============================");

								System.out.println("Player's " + playerCurrentPoke.getName() + " " + playerCurrentPoke.getAttacks().get(attack - 1) + " was not very effective!");

								if (playerCurrentPoke.getSpecial().get(attack - 1).equals("stun")) {	//Checking for Special stun
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Stun");
									int  stun = rand.nextInt(2) + 0;  //Randomly deciding if enemy Pokemon gets stunned (50% chance of stun)
									
									if (stun == 0) {
										status = "stun";
										
										System.out.println("Player's " + playerCurrentPoke.getName() + " has stunned enemy " + enemyCurrentPoke.getName());
										
										if (enemyDisabled) {  //If player Pokemon disabled, they will do 10 less damage
											enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) / 2) - 10); //Subtrcting enemy HP by the damage of Player Pokemon attack
											System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) / 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokémon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokémon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokémon Energy: " + enemyEnergy);
											
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) / 2);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) / 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
											System.out.println("Current Player Pokémon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokémon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokémon Energy: " + enemyEnergy);

										}
									}
									
									else if (stun == 1) {  //If failed stun, does normal amount of damage
										status = "";
										
										System.out.println("Player's " + playerCurrentPoke.getName() + " failed his/her stun to the enemies " + enemyCurrentPoke.getName());
										
										if (enemyDisabled) {
											enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) / 2) - 10);
											System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) / 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) / 2);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) / 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
									}
								}
										
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("disable")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Disable");
									status = "disable";
									disabled = true;
									disableCount++;
									
									if (disabled && disableCount <= 1) { //Checks to see if its the first enemy Pokemon got diabled
										System.out.println("Enemy " + enemyCurrentPoke.getName() + " attack damage lowered by 10");
									}
									
									else if (disabled && disableCount >= 1){  //Checks to see if enemy Pokemon is already disabled
										System.out.println("Enemy " + enemyCurrentPoke.getName() + " already disabled | Attack damage wont be lowered");										
									}
									
									if (enemyDisabled) {
										enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) / 2) - 10);
										System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) / 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) / 2);
										System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) / 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
								}	
									
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("wild card")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Wild Card");
									status = "wild card"; //Randomly decides if player Pokemon lands an attack or not (50% chance of hitting). If fails to hit no damage is done
									
									int  wildCard = rand.nextInt(2) + 0;
									
									if (wildCard == 0) {
										if (enemyDisabled) {
											enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) / 2) - 10);
											System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) / 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) / 2);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) / 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
									}
									
									else if (wildCard == 1){
										System.out.println("Player's" + playerCurrentPoke.getName() + " has failed to attack his/her " + playerCurrentPoke.getAttacks().get(attack - 1));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
								}	
									
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("wild storm")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Wild Storm");
									status = "wild storm";  //Randomly decides how many times a Pokemon hits his attack by checking how many times random = 2 in a row
									
									int random = 0;
									
									while (random != 1) {
										random = rand.nextInt(2) + 1;
										
										if (random == 2) {
											wildStormCount++;
										}
									}
									
									System.out.println("Wild Storm Count: " + wildStormCount);
									
									if (enemyDisabled) {
										enemyHP = enemyHP - ((((playerCurrentPoke.getDamage().get(attack - 1)) * wildStormCount) / 2) - 10);
										System.out.println(playerCurrentPoke.getName() + " dealt " + (((playerCurrentPoke.getDamage().get(attack - 1) * wildStormCount) / 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) * wildStormCount) / 2);
										System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) * wildStormCount) / 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
								}	
										
								else {
									status = "";  //Checks to if an attack has no special
						
									if (enemyDisabled) {
										enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) / 2) - 10);
										System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) / 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1) / 2));
										System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) / 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
								}
							}
										
							else if (playerCurrentPoke.getType().equals(enemyCurrentPoke.getWeakness())) {  //Same as checking resistance and Specials but now Player Pokemon attacks do x2 damage
								System.out.println("============================ Player Turn =============================");
								
								System.out.println("Player's " + playerCurrentPoke.getName() + " " + playerCurrentPoke.getAttacks().get(attack - 1) + " was super effective!");
								
								if (playerCurrentPoke.getSpecial().get(attack - 1).equals("stun")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Stun");
									int  stun = rand.nextInt(2) + 0;
									
									if (stun == 0) {
										status = "stun";

										System.out.println("Player's " + playerCurrentPoke.getName() + " has stunned enemy " + enemyCurrentPoke.getName());
										
										if (enemyDisabled) {
											enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) * 2) - 10);
											System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) * 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) * 2);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) * 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
									}
									
									else if (stun == 1) {
										status = "";
										
										System.out.println("Player's " + playerCurrentPoke.getName() + " failed his/her stun to the enemies " + enemyCurrentPoke.getName());
										
										if (enemyDisabled) {
											enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) * 2) - 10);
											System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) * 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) * 2);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) * 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
									}
								}
								
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("disable")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Disable");
									status = "disable";
									disabled = true;
									disableCount++;
									
									if (disabled && disableCount <= 1) {
										System.out.println("Enemy " + enemyCurrentPoke.getName() + " attack damage lowered by 10");
									}
									
									else if (disabled && disableCount >= 1){
										System.out.println("Enemy " + enemyCurrentPoke.getName() + " already disabled/Attack damage wont be lowered");
									}
									
									if (enemyDisabled) {
										enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) * 2) - 10);
										System.out.println(playerCurrentPoke.getName() + " did " + ((playerCurrentPoke.getDamage().get(attack - 1) * 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) * 2);
										System.out.println(playerCurrentPoke.getName() + " did " + (playerCurrentPoke.getDamage().get(attack - 1) * 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
								}
								
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("wild card")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Wild Card");
									status = "wild card";
									
									int  wildCard = rand.nextInt(2) + 0;
									
									if (wildCard == 0) {
										if (enemyDisabled) {
											enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) * 2) - 10);
											System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) * 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) * 2);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) * 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
									}
									
									else if (wildCard == 1){
										System.out.println(playerCurrentPoke.getName() + " has failed to attack his " + playerCurrentPoke.getAttacks().get(attack - 1));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
								}
								
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("wild storm")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Wild Storm");
									status = "wild storm";
									
									int random = 0;
									
									while (random != 1) {
										random = rand.nextInt(2) + 1;
										
										if (random == 2) {
											wildStormCount++;
										}
									}
									
									System.out.println("Wild Storm Count: " + wildStormCount);
									
									if (enemyDisabled) {
										enemyHP = enemyHP - ((((playerCurrentPoke.getDamage().get(attack - 1)) * wildStormCount) * 2) - 10);
										System.out.println(playerCurrentPoke.getName() + " dealt " + (((playerCurrentPoke.getDamage().get(attack - 1) * wildStormCount) * 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) * wildStormCount) * 2);
										System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) * wildStormCount) * 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
								}	
							
								else {
									status = "";
								
									if (enemyDisabled) {
										enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) * 2) - 10);
										
										System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) * 2) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1) * 2));
										System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) * 2) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
	
								}
							}
										
							else {		//Same as checking resistance/weakness and Specials but now Player Pokemon attacks have no multiplier or divider. Attacks do base damage to enemy Pokemon
										//Checks for no resistance/weakness instead
								System.out.println("============================ Player Turn =============================");
								
								System.out.println("Player's " + playerCurrentPoke.getName() + " used " + playerCurrentPoke.getAttacks().get(attack - 1));
								
								if (playerCurrentPoke.getSpecial().get(attack - 1).equals("stun")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Stun");
									
									int  stun = rand.nextInt(2) + 0;
								
									if (stun == 0) {
										status = "stun";
		
										System.out.println("Player's " + playerCurrentPoke.getName() + " has stunned enemy " + enemyCurrentPoke.getName());
										
										if (enemyDisabled) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) - 10);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - (playerCurrentPoke.getDamage().get(attack - 1));
											System.out.println(playerCurrentPoke.getName() + " dealt " + playerCurrentPoke.getDamage().get(attack - 1) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
									}
									
									else if (stun == 1) {
										status = "";
			
										System.out.println("Player's " + playerCurrentPoke.getName() + " failed his/her stun to the enemies " + enemyCurrentPoke.getName());

										if (enemyDisabled) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) - 10);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - (playerCurrentPoke.getDamage().get(attack - 1));
											System.out.println(playerCurrentPoke.getName() + " dealt " + playerCurrentPoke.getDamage().get(attack - 1) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
									}
								}
																
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("disable")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Disable");
									status = "disable";
									disabled = true;
									disableCount++;
									
									if (disabled && disableCount <= 1) {
										System.out.println(enemyCurrentPoke.getName() + " attack damage lowered by 10");
									}
									
									else if (disabled && disableCount >= 1){
										System.out.println(enemyCurrentPoke.getName() + " already disabled/Attack damage wont be lowered");
									}
									
									if (enemyDisabled) {
										enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) - 10);
										System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - (playerCurrentPoke.getDamage().get(attack - 1));
										System.out.println(playerCurrentPoke.getName() + " dealt " + playerCurrentPoke.getDamage().get(attack - 1) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
								}
								
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("wild card")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Wild Card");
									status = "wild card";
									
									int  wildCard = rand.nextInt(2) + 0;
									
									if (wildCard == 0) {
										if (enemyDisabled) {
											enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) - 10);
											System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
										
										else if(enemyDisabled == false) {
											enemyHP = enemyHP - (playerCurrentPoke.getDamage().get(attack - 1));
											System.out.println(playerCurrentPoke.getName() + " dealt " + playerCurrentPoke.getDamage().get(attack - 1) + " attack damage to the opponents " + enemyCurrentPoke.getName());
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
										}
									}
									
									else if (wildCard == 1){
										System.out.println(playerCurrentPoke.getName() + " has failed to attack his " + playerCurrentPoke.getAttacks().get(attack - 1));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
								}	
								
								else if (playerCurrentPoke.getSpecial().get(attack - 1).equals("wild storm")) {
									System.out.println("Player's " + playerCurrentPoke.getName() + " special: Wild Storm");
									status = "wild storm";
									
									int random = 0;
									
									while (random != 1) {
										random = rand.nextInt(2) + 1;
										
										if (random == 2) {
											wildStormCount++;
										}
									}
									
									System.out.println("Wild Storm Count: " + wildStormCount);
									
									if (enemyDisabled) {
										enemyHP = enemyHP - (((playerCurrentPoke.getDamage().get(attack - 1)) * wildStormCount) - 10);
										System.out.println(playerCurrentPoke.getName() + " dealt " + ((playerCurrentPoke.getDamage().get(attack - 1) * wildStormCount) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) * wildStormCount);
										System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) * wildStormCount) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
								}	
								
								else {
									status = "";
									
									if (enemyDisabled) {
										enemyHP = enemyHP - ((playerCurrentPoke.getDamage().get(attack - 1)) - 10);
										System.out.println(playerCurrentPoke.getName() + " dealt " + (playerCurrentPoke.getDamage().get(attack - 1) - 10) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
									
									else if(enemyDisabled == false) {
										enemyHP = enemyHP - (playerCurrentPoke.getDamage().get(attack - 1));
										System.out.println(playerCurrentPoke.getName() + " dealt " + playerCurrentPoke.getDamage().get(attack - 1) + " attack damage to the opponents " + enemyCurrentPoke.getName());
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
									}
								}
							}
							
							if (playerCurrentPoke.getSpecial().get(attack - 1).equals("recharge")) {   //Checks to see if player Pokemon attack has recharge special and if it does +20 more energy
								System.out.println("Player's " + playerCurrentPoke.getName() + " special: Recharge");	//every time player chooses the same attack
								System.out.println("Pokemon Special recharge allows " + playerCurrentPoke.getName() + " to regain extra 20 energy"); 
								pokeEnergy.set(pokeIndex, pokeEnergy.get(pokeIndex) - playerCurrentPoke.getEnergyCost().get(attack - 1));
								pokeEnergy.set(pokeIndex, pokeEnergy.get(pokeIndex) + 20);
	
								for (int i = 0; i < pokeEnergy.size(); i++) { //Adds +10 energy to all Pokemon that is awake
									pokeEnergy.set(i, pokeEnergy.get(i) + 10);
									
									if (pokeEnergy.get(i) > 50) {
										pokeEnergy.set(i, 50);
									}
								}
								
								System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
							}	
												
							else {		//If Pokemon attack does not have recharge ability, then only adds 10 
								pokeEnergy.set(pokeIndex, pokeEnergy.get(pokeIndex) - playerCurrentPoke.getEnergyCost().get(attack - 1));
								
								for (int i = 0; i < pokeEnergy.size(); i++) { //Adds +10 energy to all Pokemon that is awake
									pokeEnergy.set(i, pokeEnergy.get(i) + 10);
									
									if (pokeEnergy.get(i) > 50) {
										pokeEnergy.set(i, 50);
									}
								}
								
								System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
								
								if (enemyHP > 0) {
									System.out.println("======================================================================" + "\n");
								}
							}				
											
							if (enemyHP <= 0) {
								System.out.println(enemyCurrentPoke.getName() + " Has Fainted!");
								System.out.println("======================================================================" + "\n");
								enemyFaint = true;  //Enemy is forced to switch out a Pokemon
							}
						
							playerTurn = false; //Enemy turn now
						}					
						
						else if (action == 2) {
							int switchPoke = 0;
							int retreatCheck = 0;
							
							for (int i = 0; i < pokeEnergy.size(); i++) { //Adds +10 energy to all Pokemon that is awake
								pokeEnergy.set(i, pokeEnergy.get(i) + 10);
								
								if (pokeEnergy.get(i) > 50) {
									pokeEnergy.set(i, 50);
								}
							}

							while (retreatCheck != 1) { //Menu to switch out a Pokemon with the current Pokemon that is fighting
								System.out.println("============================== Retreat Menu ==========================");
								System.out.println("Your Pokemon Retreated" + "\nChoose who fights next!");
								
								for (int i = 0; i < playerPoke.size(); i++) {
									System.out.println(playerPoke.get(i).getName() + "   HP: " + pokeHP.get(i) + "   Energy:" + pokeEnergy.get(i));
									
								}
								switchPoke = stdin.nextInt();
								
								if (switchPoke > playerPoke.size() || switchPoke <= 0) { //Checks to see if valid index was enetered so program does not crash
									System.out.println("Invalid Input!");
									System.out.println("Choose a valid option!");
								}
								
								else {
									System.out.println("Are you sure you want to switch to " + playerPoke.get(switchPoke - 1).getName() + "? Yes(1) No(2)");
									
									retreatCheck = stdin.nextInt();
									
									if (checkFirst == 1) {
										playerCurrentPoke = playerPoke.get(switchPoke - 1);  //Switching all info such as current Pokemon, index, and number of attacks to the Pokemon that has been
										pokeIndex = switchPoke - 1;							 //switched in
										energyCount = playerCurrentPoke.getNumAttacks();
										System.out.println(playerCurrentPoke.getName() + " I choose you!");
										System.out.println("======================================================================" + "\n");
										
										playerTurn = false;
										
									}
									else if (checkFirst == 2) {
										
									}
								}
							}
						}
						
						else if (action == 3) {   //Pass menu that only adds +10 energy to all Pokemon that is awake
							System.out.println("============================== Pass Menu =============================");
							System.out.println(playerCurrentPoke.getName() + " passed his/her turn.");
							
							for (int i = 0; i < playerPoke.size(); i++) {
								pokeEnergy.set(pokeIndex, pokeEnergy.get(i) + 10);
								
								if (pokeEnergy.get(i) > 50) {
									pokeEnergy.set(i, 50);
								}
							}

							System.out.println("Current HP: " + pokeHP.get(pokeIndex));
							System.out.println("Current Energy: " + pokeEnergy.get(pokeIndex));
							System.out.println("======================================================================" + "\n");
							
							playerTurn = false;
						}
					}
				}
					
				else if (playerFaint) { //Checks if player Pokemon has fainted and allows you to choose which Pokemon you want to fight next
					enemyStatus = " "; //Resetting enemy status to begin new turn for Player
					
					if (playerPoke.size() > 0) {
						System.out.println("======================== Active Pokemon Select =======================");
					}
					
					playerPoke.remove(playerCurrentPoke);  //Removing Pokemon and its HP and energy info so you cant choose knocked out Pokemon again
					pokeEnergy.remove(pokeIndex);
					pokeHP.remove(pokeIndex);
					
					enemydisableCount = 0;
					enemyDisabled = false;

					if (playerPoke.size() > 0) {
						
						int faintSwitch = 0;
						int faintSwitchCheck = 0;
						
						while (faintSwitchCheck != 1) {
							System.out.println("Please choose another Pokemon to fight");
							for  (int i = 0; i < playerPoke.size(); i++) {
								System.out.println(playerPoke.get(i).getName() + "   HP: " + pokeHP.get(i) + "   Energy:" + pokeEnergy.get(i));
							}
							
							faintSwitch = stdin.nextInt();
							
							if (faintSwitch > playerPoke.size() || faintSwitch <=0) {
								System.out.println("Invalid Input!");
								System.out.println("Choose a valid option!");
								System.out.println("======================================================================");
							}
							
							else {
								System.out.println("Are you sure you want to switch to " + playerPoke.get(faintSwitch - 1).getName() + "? Yes(1) No(2)");
								
								faintSwitchCheck = stdin.nextInt();
								
								if (faintSwitchCheck == 1) {
								
									pokeIndex = faintSwitch - 1;
									playerCurrentPoke = playerPoke.get(faintSwitch - 1);
									energyCount = playerCurrentPoke.getNumAttacks();
							
									System.out.println(playerCurrentPoke.getName() + " I choose you!");
									System.out.println("======================================================================" + "\n");
									playerFaint = false;
								}
								
								else if (faintSwitchCheck == 1) {
									
								}
							}
						}
					}
					
					else {
						System.out.println("No more player Pokemon!"); //playerPoke list is now empty which means all player Pokemon have fainted which means game is over						
						gameEnd = true;
					
					}
				}	
			}	
			
			if (playerTurn == false) {  
				if (status != "stun") {
					System.out.println("============================= Enemy Turn =============================");
				}
				
				if (enemyFaint == false) {
					
					if (status == "stun") { //Checks to see if enemy Pokemon is stunned so it can skip a turn
						playerTurn = true;
					}
					
					else {
						
						enemyEnergyCount = enemyCurrentPoke.getNumAttacks();  //Same method of checking if a Pokemon has no energy to use any attacks as before
						
						for (int i = 0; i < enemyCurrentPoke.getNumAttacks(); i++) {
							if (enemyCurrentPoke.getEnergyCost().get(i) > enemyEnergy) {
								enemyEnergyCount--;
							}							
						}
						
						if (enemyEnergyCount <= 0) {
							System.out.println("Enemy " + enemyCurrentPoke.getName() + " has no energy to do any attack!");
							System.out.println("Enemy " + enemyCurrentPoke.getName() + " has to pass his/her turn");
							
							enemyEnergy = enemyEnergy + 10;
							System.out.println("Current Enemy Pokemon Energy: " + enemyEnergy);
							
							System.out.println("======================================================================" + "\n");
							playerTurn = true;
						}
						
						
						else {
							
							int randomAttack = rand.nextInt(enemyCurrentPoke.getNumAttacks()) + 0;  //Enemy Pokemon may have attacks that have enough energy. This while loop makes sure
																									//it randomly chooses the attack that has enough energy
							while (enemyCurrentPoke.getEnergyCost().get(randomAttack) > enemyEnergy) {
								randomAttack = rand.nextInt(enemyCurrentPoke.getNumAttacks()) + 0;  //Keeps choosing random attacks until an attacks has enough energy to use
								
							}
					
							if (enemyCurrentPoke.getEnergyCost().get(randomAttack) > enemyEnergy) {
								System.out.println("NOT ENOUGH ENERGY");
							}
							
							if (enemyCurrentPoke.getType().equals(playerCurrentPoke.getResistance())) { //SAME AS PLAYER POKEMON WHEN CHECKING FOR RESISTANCES/WEAKNESSES AND SPECIALS
								System.out.println("Enemies " + enemyCurrentPoke.getName() + " " + enemyCurrentPoke.getAttacks().get(randomAttack) + " was not very effective!");
								
								if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("stun")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Stun");
									int  enemystun = rand.nextInt(2) + 0;
									
									if (enemystun == 0) {
										enemyStatus = "stun";
										System.out.println("Enemies " + enemyCurrentPoke.getName() + " has stunned Player's " + playerCurrentPoke.getName());
										
										if (disabled) {
											System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
										
										else if (disabled == false){
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) / 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) / 2));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
									
									else if (enemystun == 1) {
										enemyStatus = "";
										System.out.println("Enemies " + enemyCurrentPoke.getName() + " failed his/her stun to the Player's " + playerCurrentPoke.getName());
										
										if (disabled) {
											System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
										
										else if (disabled == false){
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) / 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) / 2));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("disable")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Disable");
									enemyStatus = "disable";
									enemyDisabled = true;
									enemydisableCount++;
									
									if (enemyDisabled && enemydisableCount <= 1) {
										System.out.println("Player's " + playerCurrentPoke.getName() + " attack damage lowered by 10");
									
									}
									
									else if (enemyDisabled && enemydisableCount >= 1){
										System.out.println("Player's " + playerCurrentPoke.getName() + " already disabled/Attack damage wont be lowered");
										
									}
									
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false){
										System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) / 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) / 2));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("wild card")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Wild Card");
									int  enemyWildCard = rand.nextInt(2) + 0;
									
									if (enemyWildCard == 0) {
										if (disabled) {
											enemyStatus = "wild card";
											System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
										
										else if (disabled == false){
											enemyStatus = "wild card";
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) / 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) / 2));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
									
									else if (enemyWildCard == 1) {
										enemyStatus = "";
										System.out.println(enemyCurrentPoke.getName() + " has failed to attack his " + enemyCurrentPoke.getAttacks().get(randomAttack));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("wild storm")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Wild Storm");
									enemyStatus = "wild storm";
									
									int random = 0;
									
									while (random != 1) {
										random = rand.nextInt(2) + 1;
										
										if (random == 2) {
											enemywildStormCount++;
										}
									}
									
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + (((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) / 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) / 2) - 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) / 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) / 2));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
								else {
									enemyStatus = "";
									
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) / 2) - 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false){
										System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) / 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) / 2));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
	
							}
							
							else if (enemyCurrentPoke.getType().equals(playerCurrentPoke.getWeakness())) {
								System.out.println("Enemies " + enemyCurrentPoke.getName() + " " + enemyCurrentPoke.getAttacks().get(randomAttack) + " was super effective!");
								
								if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("stun")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Stun");
									
									int  enemystun = rand.nextInt(2) + 0;
									
									if (enemystun == 0) {
										enemyStatus = "stun";
										System.out.println("Enemies " + enemyCurrentPoke.getName() + " has stunned Player's " + playerCurrentPoke.getName());
										
										if (disabled) {
											System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
										
										else if (disabled == false){
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) * 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) * 2));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
									
									else if (enemystun == 1) {
										enemyStatus = "";
										System.out.println("Enemies " + enemyCurrentPoke.getName() + " failed his/her stun to the Player's " + playerCurrentPoke.getName());
										
										if (disabled) {
											System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
										
										else if (disabled == false){
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) * 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) * 2));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("disable")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Disable");
									enemyStatus = "disable";
									enemyDisabled = true;
									enemydisableCount++;
									
									if (enemyDisabled && enemydisableCount <= 1) {
										System.out.println("Player's " + playerCurrentPoke.getName() + " attack damage lowered by 10");
									
									}
									
									else if (enemyDisabled && enemydisableCount >= 1){
										System.out.println("Player's " + playerCurrentPoke.getName() + " already disabled/Attack damage wont be lowered");
										
									}
									
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false){
										System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) * 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) * 2));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("wild card")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Wild Card");
									int  enemyWildCard = rand.nextInt(2) + 0;
									
									if (enemyWildCard == 0) {
										if (disabled) {
											enemyStatus = "wild card";
											System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
										
										else if (disabled == false){
											enemyStatus = "wild card";
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) * 2)  + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) * 2));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
									
									else if (enemyWildCard == 1) {
										enemyStatus = "";
										System.out.println(enemyCurrentPoke.getName() + " has failed to attack his " + enemyCurrentPoke.getAttacks().get(randomAttack));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("wild storm")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Wild Storm");
									enemyStatus = "wild storm";
									
									int random = 0;
									
									while (random != 1) {
										random = rand.nextInt(2) + 1;
										
										if (random == 2) {
											enemywildStormCount++;
										}
									}
									
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + (((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) * 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) * 2) - 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) * 2) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) * 2));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
									
								else {
									enemyStatus = "";
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) * 2) - 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false){
										System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) * 2)  + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) * 2));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
								
							}
							
							else {
								System.out.println("Enemies " + enemyCurrentPoke.getName() + " used " + enemyCurrentPoke.getAttacks().get(randomAttack));
								
								if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("stun")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Stun");
									
									int  enemystun = rand.nextInt(2) + 0;
									
									if (enemystun == 0) {
										enemyStatus = "stun";
										System.out.println("Enemies " + enemyCurrentPoke.getName() + " has stunned Player's " + playerCurrentPoke.getName());
										
										if (disabled) {
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack)- 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
											
										}
										
										else if (disabled == false){
											System.out.println(enemyCurrentPoke.getName() + " dealt " + enemyCurrentPoke.getDamage().get(randomAttack) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - enemyCurrentPoke.getDamage().get(randomAttack));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
									
									else if (enemystun == 1) {
										enemyStatus = "";
										System.out.println("Enemies " + enemyCurrentPoke.getName() + " has stunned Player's " + playerCurrentPoke.getName());
										
										if (disabled) {
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack)- 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
										
										else if (disabled == false){
											System.out.println(enemyCurrentPoke.getName() + " dealt " + enemyCurrentPoke.getDamage().get(randomAttack) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - enemyCurrentPoke.getDamage().get(randomAttack));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("disable")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Disable");
									enemyStatus = "disable";
									enemyDisabled = true;
									enemydisableCount++;
									
									if (enemyDisabled && enemydisableCount <= 1) {
										System.out.println("Player's " + playerCurrentPoke.getName() + " attack damage lowered by 10");
									
									}
									
									else if (enemyDisabled && enemydisableCount >= 1){
										System.out.println("Player's " + playerCurrentPoke.getName() + " already disabled/Attack damage wont be lowered");
										
									}
									
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) - 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false){
										System.out.println(enemyCurrentPoke.getName() + " dealt " + enemyCurrentPoke.getDamage().get(randomAttack) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - enemyCurrentPoke.getDamage().get(randomAttack));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("wild card")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Wild Card");
									int  enemyWildCard = rand.nextInt(2) + 0;
									
									if (enemyWildCard == 0) {
										if (disabled) {
											enemyStatus = "wild card";
											System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) - 10));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
										
										else if (disabled == false){
											enemyStatus = "wild card";
											System.out.println(enemyCurrentPoke.getName() + " dealt " + enemyCurrentPoke.getDamage().get(randomAttack) + " attack damage to the Player's " + playerCurrentPoke.getName());
											pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - enemyCurrentPoke.getDamage().get(randomAttack));
											
											System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
											System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
											System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
										}
									}
									
									else if (enemyWildCard == 1) {
										enemyStatus = "";
										System.out.println(enemyCurrentPoke.getName() + " has failed to attack his " + enemyCurrentPoke.getAttacks().get(randomAttack));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
								
								else if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("wild storm")) {
									System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Wild Storm");
									enemyStatus = "wild storm";
									
									int random = 0;
									
									while (random != 1) {
										random = rand.nextInt(2) + 1;
										
										if (random == 2) {
											enemywildStormCount++;
										}
									}
			
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + ((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount)- 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - ((enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount) - 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack) * enemywildStormCount));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
								
								else {
									enemyStatus = "";
									if (disabled) {
										System.out.println(enemyCurrentPoke.getName() + " dealt " + (enemyCurrentPoke.getDamage().get(randomAttack) - 10) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - (enemyCurrentPoke.getDamage().get(randomAttack)- 10));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
									
									else if (disabled == false){
										System.out.println(enemyCurrentPoke.getName() + " dealt " + enemyCurrentPoke.getDamage().get(randomAttack) + " attack damage to the Player's " + playerCurrentPoke.getName());
										pokeHP.set(pokeIndex, pokeHP.get(pokeIndex) - enemyCurrentPoke.getDamage().get(randomAttack));
										
										System.out.println("Current Player Pokemon HP: " + pokeHP.get(pokeIndex));
										System.out.println("Current Enemy Pokemon HP: " + enemyHP + "\n");
										System.out.println("Current Player Pokemon Energy: " + pokeEnergy.get(pokeIndex));
									}
								}
													
							}
						
						
							if (enemyCurrentPoke.getSpecial().get(randomAttack).equals("recharge")) {
								System.out.println("Enemies " + enemyCurrentPoke.getName() + " special: Recharge");
								System.out.println("Enemy Pokemon Special recharge allows " + enemyCurrentPoke.getName() + " to regain extra 20 energy");
								enemyEnergy = enemyEnergy - enemyCurrentPoke.getEnergyCost().get(randomAttack);
								enemyEnergy = enemyEnergy + 10 + 20;
								
								if (enemyEnergy > 50) {
									enemyEnergy = 50;
								}
								
								System.out.println("Current enemy energy: " + enemyEnergy);
								
								System.out.println("======================================================================" + "\n");
							}
							
							else {
								enemyEnergy = enemyEnergy - enemyCurrentPoke.getEnergyCost().get(randomAttack);
								enemyEnergy = enemyEnergy + 10;
								
								if (enemyEnergy > 50) {
									enemyEnergy = 50;
								}
								
								System.out.println("Current enemy energy: " + enemyEnergy);
								
								if (pokeHP.get(pokeIndex) > 0) {
									System.out.println("======================================================================" + "\n");
								}
							}
							
							if (pokeHP.get(pokeIndex) <= 0) {
								playerFaint = true;
								System.out.println("Your " + playerCurrentPoke.getName() + " has fainted and is unable to fight!");
								System.out.println("======================================================================" + "\n");
							}
						}
					}
				}
				
				else if (enemyFaint) {
					oppoPoke.remove(enemyCurrentPoke); //Removing fainted Pokemon
					disableCount = 0;
					disabled = false;
					
					status = " ";
					
					if (oppoPoke.size() > 0) {
						int  enemyPokeSwitch = rand.nextInt(oppoPoke.size()) + 0;
						
						enemyCurrentPoke = oppoPoke.get(enemyPokeSwitch);
						enemyHP = enemyCurrentPoke.getHP(); //Resetting enemy Pokemon HP and energy
						enemyEnergy = 50;

						System.out.println("Enemy chooses " + enemyCurrentPoke.getName() + "!");
						System.out.println("======================================================================" + "\n");
						enemyFaint = false;
					}
					
					else {
						System.out.println("No more enemy Pokemon!"); //Checks if enemyPoke list is empty and if it is, game has ended
						
						for (int i = 0; i < playerPoke.size(); i++) { //Restoring + 20 HP for woke Pokemon
							pokeHP.set(i, pokeHP.get(i) + 20);
						}
	
						System.out.println("Your Pokemon will be healed plus 20 HP");
						System.out.println("======================================================================" + "\n");
						
						gameEnd = true;
					
					}
					
				}
				
				playerTurn = true;
			}				
		}
		
		if (playerPoke.size() == 0) { //Checking to see who has won the battle by determining which list is empty
			System.out.println("=============================== You Lost =============================");
			System.out.println("You have been defeated trainer!");
			System.out.println("Try again next time to be crowned Trainer Supreme!");
			System.out.println("======================================================================" + "\n");
		}
		
		else if (oppoPoke.size() == 0) {
			System.out.println("=============================== You Won ==============================");
			System.out.println("Congratulations Trainer!");
			System.out.println("You and your Pokemon have defeated the opponent!");
			System.out.println("You have been crowned Trainer Supreme!");
			System.out.println("======================================================================" + "\n");
		}
	}
}
