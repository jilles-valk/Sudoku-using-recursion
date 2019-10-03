import java.util.ArrayList;
/**
 * The Logic class holds all the methods that are needed to solve the Sudoku.
 * @author Jilles Valk
 */
public class Logic {
	private Board board;
	private static ArrayList<Integer> possValues = new ArrayList<Integer>() {
		{
			add(1);
			add(2);
			add(3);
			add(4);
			add(5);
			add(6);
			add(7);
			add(8);
			add(9);
		}
	};
	
	/**
	 * Constructor taking a board that needs to be solved.
	 * @param board A board with empty spots that needs to be solved.
	 */
	public Logic(Board board) {
		this.board = board;
	}
	
	/**
	 * Main call to start the solving of the board.
	 */
	public void doLogic() {
		System.out.println("The old board");
		board.printBoard();
		long startTime = System.nanoTime();
		board = logicLoop(board, board.findNextEmpty(), getPossVals(board, board.findNextEmpty()));
		long finishTime = System.nanoTime();
		System.out.println("The solved board");
		board.printBoard();
		System.out.println("Solved in " + (finishTime - startTime)/1000000.0 + " milliseconds");
	}

	/**
	 * Main recursive method that will solve the board and return it.
	 * @param board Board with empty spots to be solved.
	 * @param nextEmpty Position [x,y] of first spot that needs to be filled.
	 * @param possVals Possible values that can be put at nextEmpty.
	 * @return The board, solved if solvable.
	 */
	private Board logicLoop(Board board, int[] nextEmpty, ArrayList<Integer> possVals) {
		Board boardCopy = new Board(board);
		int[] newNextEmpty;
		for (int possVal : possVals) {
			
			if (boardCopy.findNextEmpty().length != 1) {
				boardCopy.setValue(nextEmpty, possVal); 

				newNextEmpty = boardCopy.findNextEmpty();
				if (newNextEmpty.length != 1 ) {
					ArrayList<Integer> newPossVals = getPossVals(boardCopy, newNextEmpty);
					if (newPossVals.size() == 0 && possVals.indexOf(possVal) == possVals.size()-1) {
						return board;
					}
					else {
						boardCopy = logicLoop(new Board(boardCopy), newNextEmpty, newPossVals);
						if (boardCopy.getBoard() == board.getBoard()) {
							return boardCopy;
						}
					}
				}
				else {
					return boardCopy;
				}
			}
			else {
				return boardCopy;
			}
		}
		if (boardCopy.findNextEmpty().length == 1) {
			return boardCopy;
		}
		return board;
	}
	

	/**
	 * Finds the next possible values that can be put in the specified position.
	 * @param boardCopy Board where possible values are found.
	 * @param rowCol Spot on board where possible values are generated for.
	 * @return A list of all the possible values that can be put on the specified position.
	 */
	private ArrayList<Integer> getPossVals(Board boardCopy, int[] rowCol) {
		ArrayList<Integer> tempPossValues = new ArrayList<Integer>(possValues);
		ArrayList<Integer> removed = new ArrayList<Integer>();
		
		for (int value : boardCopy.getRow(rowCol[0])) {
			if (removed.contains(value)) {
				return new ArrayList<Integer>();
			}
			if (tempPossValues.contains(value)) {
				tempPossValues.remove(Integer.valueOf(value));
				removed.add(value);
			}
		}
		removed.clear();
		
		for(int value : boardCopy.getColumn(rowCol[1])) {
			if (removed.contains(value)) {
				return new ArrayList<Integer>();
			}
			if (tempPossValues.contains(value)) {
				tempPossValues.remove(Integer.valueOf(value));
				removed.add(value);
			}
		}
		removed.clear();
		
		for(int value : boardCopy.getPartition(rowCol)) {
			if (removed.contains(value)) {
				return new ArrayList<Integer>();
			}
			if (tempPossValues.contains(value)) {
				tempPossValues.remove(Integer.valueOf(value));
				removed.add(value);
			}
		}
		return tempPossValues;
	}
}
