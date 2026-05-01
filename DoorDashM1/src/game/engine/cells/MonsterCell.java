 	package game.engine.cells;

import game.engine.monsters.*;

public class MonsterCell extends Cell {
	private Monster cellMonster;

	public MonsterCell(String name, Monster cellMonster) {
		super(name);
		this.cellMonster = cellMonster;
	}
	

	public Monster getCellMonster() {
		return cellMonster;
	}
	public void onLand(Monster landingMonster, Monster opponentMonster) {
	    super.onLand(landingMonster, opponentMonster);
	    if (landingMonster.getRole() == this.getCellMonster().getRole()) {
	        landingMonster.executePowerupEffect(opponentMonster);
	    } 
	    else {
	        if (landingMonster.getEnergy() > this.getCellMonster().getEnergy()) {
	            int Lenergy = landingMonster.getEnergy();
	            int Cenergy = this.getCellMonster().getEnergy();
	            int d = Lenergy - Cenergy;
	            if (d <= 0) return;
	            landingMonster.alterEnergy(-d);
	            this.getCellMonster().alterEnergy(d);
	        }
	    }
	}
}