package game.engine.monsters;

import game.engine.Role;
import game.engine.Constants;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	//RETURN BACK HERE IF ERROR OCCURS+could be missing the stationary monsters 
	private int stealEnergyFrom(Monster target){
		int stolenAmount = Math.min(Constants.SCHEMER_STEAL, target.getEnergy());
	    target.setEnergy(target.getEnergy() - stolenAmount);
	    return stolenAmount;
	}
	public void executePowerupEffect(Monster opponentMonster) {
	    int stolen = stealEnergyFrom(opponentMonster);
	    this.setEnergy(this.getEnergy() + stolen);
	}
	public void setEnergy(int energy) {
	    int diff = energy - getEnergy();
	    diff += 10;
	    super.setEnergy(getEnergy() + diff);
	}
	
}
