package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import game.engine.exceptions.*;
import game.engine.dataloader.DataLoader;
import game.engine.monsters.*;
import game.engine.cells.*;
public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters; 
	private Monster player;
	private Monster opponent;
	private Monster current;
	
	public Game(Role playerRole) throws IOException {
		this.board = new Board(DataLoader.readCards());
		
		this.allMonsters = DataLoader.readMonsters();
		
		this.player = selectRandomMonsterByRole(playerRole);
		this.opponent = selectRandomMonsterByRole(playerRole == Role.SCARER ? Role.LAUGHER : Role.SCARER);
		this.current = player;
		
		allMonsters.remove(player);
		allMonsters.remove(opponent);
		Board.setStationedMonsters(allMonsters);
		ArrayList<Cell> specialCells=DataLoader.readCells();
		board.initializeBoard(specialCells);
		
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Monster> getAllMonsters() {
		return allMonsters; 
	}
	
	public Monster getPlayer() {
		return player;
	}
	
	public Monster getOpponent() {
		return opponent;
	}
	
	public Monster getCurrent() {
		return current;
	}
	
	public void setCurrent(Monster current) {
		this.current = current;
	}
	
	private Monster selectRandomMonsterByRole(Role role) {
		Collections.shuffle(allMonsters);
	    return allMonsters.stream()
	    		.filter(m -> m.getRole() == role)
	    		.findFirst()
	    		.orElse(null);
	}
	private Monster getCurrentOpponent(){
		if(current==player)
			return opponent;
		else
			return player;
	}
	private int rollDice(){
		int dice=(int)(Math.random()*6)+1;
		return dice;
	}
	// come back to usePowerup() method
	private void usePowerup() throws OutOfEnergyException {
		if(current.getEnergy()< Constants.POWERUP_COST){
			throw new OutOfEnergyException();
		}
		else{
			current.setEnergy(current.getEnergy() - Constants.POWERUP_COST);
		    current.executePowerupEffect(getCurrentOpponent());
		}
	}
	private void playTurn() throws InvalidMoveException{
		if (current.isFrozen()){
			current.setFrozen(false);
		}else{
			int dice = rollDice();
			current.move(dice);
		}
		current = getCurrentOpponent();
	}
	
	private void switchTurn(){
		current = getCurrentOpponent();
	}
	private boolean checkWinCondition(Monster monster){
		if(current.getPosition()==99 && current.getEnergy()>=Constants.WINNING_ENERGY){
			return true;
		}else {
		return false;
		}
	}
	private Monster getWinner(){
		if(checkWinCondition(current)&& checkWinCondition(getCurrentOpponent())!=true){
			return current;
		}else if (checkWinCondition(current)!=true && checkWinCondition(getCurrentOpponent())){
			return getCurrentOpponent();
		}else{
			return null;
		}
	}
}