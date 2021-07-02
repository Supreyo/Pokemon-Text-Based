import java.util.*;

//Supreyo Atonu
//Pokémon Project

public class Pokemon {
	private String name;
	private int hp;
	private String type;
	private String resistance;
	private String weakness;
	private int numAttacks;

	private String[] attackInfo; //Separate list that holds all attack info
	private ArrayList<String> pokeAttacks = new ArrayList<String>();
	private ArrayList<Integer> pokeEnergy = new ArrayList<Integer>();
	private ArrayList<Integer> pokeDamage = new ArrayList<Integer>();
	private ArrayList<String> pokeSpecial = new ArrayList<String>();
	
	public Pokemon(String pokeList) {
		String[] info = pokeList.split(","); 
		
		this.name = info[0];
		this.hp = Integer.parseInt(info[1]);
		this.type = info[2];
		this.resistance = info[3];
		this.weakness = info[4];
		this.numAttacks = Integer.parseInt(info[5]);
		
		attackInfo = Arrays.copyOfRange(info, 5, info.length); //Copying only attack info from original list
		
		for (int i = 1; i < attackInfo.length; i += 4) { //Every 4th index to get the info for different attacks
			pokeAttacks.add(attackInfo[i]); 						//Attacks are at the first index for a different attack
			pokeEnergy.add(Integer.parseInt(attackInfo[i + 1])); 	//Energy are at the second index for a different attack
			pokeDamage.add(Integer.parseInt(attackInfo[i + 2]));	//Damage are at the third index for a different attack
			pokeSpecial.add(attackInfo[i + 3]); 					//Specials are at the fourth index for a different attack
		}
	}
	
	public String getName() {  
		return name;
	}
	
	public int getHP() {
		return hp;
	}
	
	public String getType() {
		return type;
	}
	
	public String getResistance() {
		return resistance;
	}
	
	public String getWeakness() {
		return weakness;
	}
	
	public int getNumAttacks() {
		return numAttacks;
	}
	
	public ArrayList<String> getAttacks() {
		return pokeAttacks;
	}
	
	public ArrayList<Integer> getEnergyCost() {
		return pokeEnergy;
	}
	
	public ArrayList<Integer> getDamage() {
		return pokeDamage;
	}
	
	public ArrayList<String> getSpecial() {
		return pokeSpecial;
	}
}
