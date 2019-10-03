/**
 * The main class that has the main method. 
 * It creates a board and logic object and solves the board.
 * @author Jilles Valk
 */
public class Sudoku {
	private static Board board;
	private static Logic logic;
	
	/**
	 * Main method that creates a board and logic object and solves the board.
	 * @param args None taken.
	 */
	public static void main(String[] args) {
		board = new Board();
		board.readBoard();
		logic = new Logic(board);
		logic.doLogic();
	}

}
