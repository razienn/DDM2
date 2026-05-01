package game.engine;

import game.engine.cards.*;
import java.util.ArrayList;
import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.monsters.Monster;
import java.util.*;
import game.engine.exceptions.*;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters; 
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		originalCards = readCards;
		cards = new ArrayList<Card>();
	}
	
	public Cell[][] getBoardCells() {
		return boardCells;
	}
	
	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}
	
	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}

	public static ArrayList<Card> getOriginalCards() {
		return originalCards;
	}
	
	public static ArrayList<Card> getCards() {
		return cards;
	}
	
	public static void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}
	 private int[] indexToRowCol(int index){
		 int row = index / Constants.BOARD_SIZE;
		 int col = index % Constants.BOARD_SIZE;
		 
		 if(row % 2 == 1){
			 col = Constants.BOARD_COLS - 1 - col;
		 }
		 return new int[] {row , col};
	 }
	 private Cell getCell(int index){
		 int[] rowCol = indexToRowCol(index);
		 return boardCells[rowCol[0]][rowCol[1]];
	 }
	 private void setCell(int index, Cell cell){
		 int[] rc = indexToRowCol(index);
		 boardCells[rc[0]][rc[1]]=cell;
	 }
	 //implemented the initializeBoard method using AI (ChatGPT)
	 public void initializeBoard(ArrayList<Cell> specialCells) {

		    ArrayList<DoorCell> doorCells = new ArrayList<>();
		    ArrayList<ConveyorBelt> conveyorBelts = new ArrayList<>();
		    ArrayList<ContaminationSock> contaminationSocks = new ArrayList<>();

		    for (int i = 0; i < specialCells.size(); i++) {
		        Cell cell = specialCells.get(i);

		        if (cell instanceof DoorCell) {
		            doorCells.add((DoorCell) cell);
		        } else if (cell instanceof ConveyorBelt) {
		            conveyorBelts.add((ConveyorBelt) cell);
		        } else if (cell instanceof ContaminationSock) {
		            contaminationSocks.add((ContaminationSock) cell);
		        }
		    }

		    int doorIndex = 0;
		    int conveyorIndex = 0;
		    int sockIndex = 0;
		    int monsterIndex = 0;

		    for (int i = 0; i < Constants.BOARD_SIZE; i++) {

		        if (contains(Constants.CARD_CELL_INDICES, i)) {
		            setCell(i, new CardCell("Card Cell"));
		            continue;
		        }

		        if (contains(Constants.MONSTER_CELL_INDICES, i)) {
		            if (monsterIndex < stationedMonsters.size()) {
		                Monster m = stationedMonsters.get(monsterIndex);
		                m.setPosition(i);
		                setCell(i, new MonsterCell(m.getName(), m));
		            } else {
		                setCell(i, new MonsterCell("Monster Cell", null));
		            }
		            monsterIndex++;
		            continue;
		        }

		        if (contains(Constants.CONVEYOR_CELL_INDICES, i)) {
		            setCell(i, conveyorBelts.get(conveyorIndex++));
		            continue;
		        }

		        if (contains(Constants.SOCK_CELL_INDICES, i)) {
		            setCell(i, contaminationSocks.get(sockIndex++));
		            continue;
		        }

		        if (i % 2 == 1) {
		            setCell(i, doorCells.get(doorIndex++));
		        } else {
		            setCell(i, new Cell("Cell"));
		        }
		    }
		}
	 private boolean contains(int[] arr, int value) {
		    for (int i = 0; i < arr.length; i++) {
		        if (arr[i] == value) {
		            return true;
		        }
		    }
		    return false;
		}
	 private void setCardsByRarity(){
		 ArrayList<Card> newCards= new ArrayList<Card>();
		 for(int i=0 ; i<originalCards.size() ; i++){
			 int rarity = originalCards.get(i).getRarity();
			 for(int j = 0 ; j<rarity ; j++){
				 newCards.add(originalCards.get(i));
			 }
		 }
		 originalCards=newCards;
	 }
	 public static void reloadCards(){
		 cards = new ArrayList<Card>(originalCards);
		 Collections.shuffle(cards);
	 }
	 public static Card drawCard(){
		 if(cards.isEmpty()){
			 reloadCards();
		 }
		 return cards.remove(0);
	 }
	 /*this is the prompt i used for 8 and 9 :I need help implementing two methods in the Board class:
		moveMonster:
		Move monster using move(roll)
		Call onLand on the cell
		If it ends on opponent → revert and throw InvalidMoveException
		Decrement confusion for both after landing
		Call updateMonsterPositions
		updateMonsterPositions:
		Clear all cells
		Place both monsters in correct cells based on position
		
		Implement both cleanly.*/
	 public void moveMonster(Monster currentMonster, int roll, Monster opponentMonster) throws InvalidMoveException {

		    int oldPosition = currentMonster.getPosition();

		    int newPosition = currentMonster.getPosition() + roll;

		    if (newPosition == opponentMonster.getPosition()) {
		        throw new InvalidMoveException();
		    }

		    currentMonster.setPosition(newPosition);

		    getCell(newPosition).onLand(currentMonster, opponentMonster);

		    if (currentMonster.isConfused()) {
		        currentMonster.decrementConfusion();
		    }

		    updateMonsterPositions(currentMonster, opponentMonster);
		}

		private void updateMonsterPositions(Monster player, Monster opponent) {
			for (int i = 0; i < Constants.BOARD_SIZE; i++) {
				Cell currentCell = getCell(i);
				if (currentCell != null) {
					currentCell.setMonster(null);
				}
			}

			Cell playerCell = getCell(player.getPosition());
			if (playerCell != null) {
				playerCell.setMonster(player);
			}

			Cell opponentCell = getCell(opponent.getPosition());
			if (opponentCell != null) {
				opponentCell.setMonster(opponent);
			}
		}
		
}
