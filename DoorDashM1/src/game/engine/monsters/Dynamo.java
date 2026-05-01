package game.engine.monsters;

import game.engine.Role;

public class Dynamo extends Monster {
	
	public Dynamo(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	public void executePowerupEffect(Monster opponentMonster){
		opponentMonster.setFrozen(true);
	}
	public void setEnergy(int energy) {
	    int diff = energy - getEnergy();
	    diff *= 2;
	    super.setEnergy(getEnergy() + diff);
	}
	
}
