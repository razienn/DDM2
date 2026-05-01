package game.engine.cells;

import game.engine.*;
import game.engine.monsters.*;
import game.engine.interfaces.CanisterModifier;
import game.engine.dataloader.*;
import java.util.*;
public class DoorCell extends Cell implements CanisterModifier {
	private Role role;
	private int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		this.activated = false;
	}
	
	public Role getRole() {
		return role;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean isActivated) {
		this.activated = isActivated;
	}
	public void onLand(Monster landingMonster,Monster opponentMonster){
		super.onLand(landingMonster, opponentMonster);
		if(isActivated())
			return;
		int e=getEnergy();
		if (landingMonster.isShielded() && landingMonster.getRole() != this.getRole()) {
	        modifyCanisterEnergy(landingMonster, e);
	        return;
	    }
	    modifyCanisterEnergy(landingMonster, e);
	    ArrayList<Monster> ms = Board.getStationedMonsters();
	    if (ms != null) {
	        for (Monster m : ms) {
	            if (m != null && m.getRole() == landingMonster.getRole()) {
	                modifyCanisterEnergy(m, e);
	            }
	        }
	    }
	}
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
	    if (monster.getRole() == this.getRole()) {
	        monster.alterEnergy(canisterValue);
	        return;
	    }
	    monster.alterEnergy(-canisterValue);
	}
}
