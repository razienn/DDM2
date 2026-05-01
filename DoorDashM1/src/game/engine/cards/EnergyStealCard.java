package game.engine.cards;
import game.engine.monsters.Monster;
import game.engine.interfaces.CanisterModifier;

public class EnergyStealCard extends Card implements CanisterModifier {
	private int energy;

	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name, description, rarity, true);
		this.energy = energy;
	}
	
	public int getEnergy() {
		return energy;
	}
	public void performAction(Monster player,Monster opponent){
		int stealAmount=0;
	    if(this.getEnergy()>opponent.getEnergy())
	    	stealAmount=opponent.getEnergy();
	    else
	    	stealAmount=this.getEnergy();
		if (opponent.isShielded()) {
	        opponent.setShielded(false);
	        stealAmount = 0;
	    } else {
	        opponent.setEnergy(opponent.getEnergy()-stealAmount);
	    }
	    player.setEnergy(player.getEnergy()+stealAmount);

	    modifyCanisterEnergy(player,stealAmount);
	   
	    modifyCanisterEnergy(opponent,stealAmount);
	}
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
	    monster.setEnergy(monster.getEnergy()+canisterValue);
}
}