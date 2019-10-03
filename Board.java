import java.io.*;
/**
 * The board object holding a 2D array of all of the entered values. 
 * The board has methods for reading a board from a file, 
 * getting a partition, row or column and a method to print the board.
 * @author Jilles Valk
 *
 */
public class Board{
	private int[][] board;
	private int boardSize = 9;
	
	/**
	 * Default constructor creating an empty board.
	 */
	public Board() {
		board = new int[boardSize][boardSize];
	}
	
	/**
	 * Alternative constructor that can be used to make a copy of a present board.
	 * @param oldBoard Board to make a copy of.
	 */
	public Board(Board oldBoard) {
		this.boardSize = oldBoard.getBoardSize();
		this.board = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				this.board[i][j] = oldBoard.getRow(i)[j];
			}
		}
	}

	/**
	 * Read the board from a file "input.txt" which has the whole board in a single line
	 * with a zero meaning a spot that has not been filled.
	 */
	public void readBoard() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					new File(ClassLoader.getSystemResource("input.txt").getFile())));
			String line = bufferedReader.readLine();
			
			if (line.length() == boardSize*boardSize) {
				for (int i = 0; i < boardSize; i++) {
					for (int j = 0; j < boardSize; j++) {
						board[i][j] = Character.getNumericValue(line.charAt(i*boardSize+j));
					}
				}
			}
			
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("Cannot open file.");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Prints the board to the console.
	 */
	public void printBoard() {
		for (int i = 0; i < boardSize; i++) {
			if (i%3 == 0) {
				System.out.println(new String(new char[boardSize]).replace("\0", "--"));
			}
			for (int j = 0; j < boardSize; j++) {
				if (j%3 == 0) {
					if (board[i][j] == 0) {
						System.out.print("| ");
					}
					else {
						System.out.print("|" + board[i][j]);
					}
				}
				else {
					if (board[i][j] == 0) {
						System.out.print("  ");
					}
					else {
						System.out.print(" " + board[i][j]);
					}
				}
			}
			System.out.print("|\n");
		}
		System.out.println(new String(new char[boardSize]).replace("\0", "--"));
	}
	
	/**
	 * Get the specified row of the board.
	 * @param row A number pointing to the row to be returned.
	 * @return An array of the values in the row.
	 */
	public int[] getRow(int row) {
		return board[row];
	}
	
	/**
	 * Get the specified column of the board.
	 * @param col A number pointing to the column to be returned.
	 * @return An array of the values in the column.
	 */
	public int[] getColumn(int col) {
		int[] column = new int[boardSize];
		for (int i = 0; i < boardSize; i++) {
			column[i] = board[i][col];
		}
		return column;
	}
	
	/**
	 * Get the specified partition (3x3) of the board.
	 * @param rowCol A two value array pointing to the row and column 
	 * of the partition to be returned.
	 * @return The partition in the form of an array that contains the value at rowCol.
	 */
	public int[] getPartition(int[] rowCol) {
		int[] partition = new int[boardSize];
		int sqrtBoardSize = (int)Math.sqrt(boardSize);
		for (int i = 0; i < sqrtBoardSize; i++) {
			for (int j = 0; j < sqrtBoardSize; j++) {
				partition[i*sqrtBoardSize+j] = 
						board[(rowCol[0]/sqrtBoardSize)*sqrtBoardSize+i]
								[(rowCol[1]/sqrtBoardSize)*sqrtBoardSize+j];
			}
		}
		return partition;
	}

	/**
	 * Returns the size of a side of the board.
	 * @return The size of a side of the board.
	 */
	public int getBoardSize() {
		return boardSize;
	}
	
	/**
	 * Sets the value of the specified position on the board.
	 * @param rowCol A two value array pointing to the row and column 
	 * of value to be placed..
	 * @param val The value to be placed (1-9).
	 */
	public void setValue(int[] rowCol, int val) {
		board[rowCol[0]][rowCol[1]] = val;
	}
	
	/**
	 * Find the next empty spot on the board.
	 * @return The next empty spot on the board as a two value array [x,y] 
	 * or an single value array with a zero if there is no empty spot.
	 */
	public int[] findNextEmpty() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board[i][j] == 0) {
					return new int[] {i,j};
				}
			}
		}
		return new int[] {0};
	}

	public int[][] getBoard() {
		return board;
	}
}
