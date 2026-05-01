package game.engine.cells;
import game.engine.monsters.*;
import game.engine.cards.*;
import game.engine.Board;
public class CardCell extends Cell {
	
	public CardCell(String name) {
        super(name);
    }
   public void onLand(Monster landingMonster, Monster opponentMonster){
	   super.onLand(landingMonster, opponentMonster);
	   Card card = Board.drawCard();
	   //erga3hlha 
	   card.performAction(landingMonster, opponentMonster);
   }
   
}
