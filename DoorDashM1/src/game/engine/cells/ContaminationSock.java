package game.engine.cells;
import game.engine.*;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.*;
public class ContaminationSock extends TransportCell implements CanisterModifier {

	public ContaminationSock(String name, int effect) {
		super(name, effect);
	}
	public void onLand(Monster landingMonster, Monster opponentMonster) {
        super.onLand(landingMonster, opponentMonster);
        landingMonster.setPosition(landingMonster.getPosition() - this.getEffect());
        modifyCanisterEnergy(landingMonster, Constants.SLIP_PENALTY);
    }
    public void modifyCanisterEnergy(Monster monster, int canisterValue) {
        monster.alterEnergy(-canisterValue);
    }

}

