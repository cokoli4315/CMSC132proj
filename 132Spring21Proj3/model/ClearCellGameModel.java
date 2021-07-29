package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game,
 * specifically.
 * 
 * @author Dept of Computer Science, UMCP
 */

public class ClearCellGameModel extends GameModel {

	/* Include whatever instance variables you think are useful. */
	private int playerScores;
	private Random random;
	final int SUB = 2;

	/**
	 * Defines a board with empty cells. It relies on the super class
	 * constructor to define the board.
	 * 
	 * @param rows   number of rows in board
	 * @param cols   number of columns in board
	 * @param random random number generator to be used during game when rows
	 *               are randomly created
	 */
	public ClearCellGameModel(int rows, int cols, Random random) {
		super(rows, cols);
		this.random = random;
		playerScores = 0;
	}

	/**
	 * The game is over when the last row (the one with index equal to
	 * board.length-1) contains at least one cell that is not empty.
	 */
	public boolean isGameOver() {
		int count = 0;
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				if (board[board.length - 1][col] != BoardCell.EMPTY) {
					count++;
				}
			}
		}
		// This method returns true if the last row has atleast a cell that is
		// not empty
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the player's score. The player should be awarded one point for
	 * each cell that is cleared.
	 * 
	 * @return player's score
	 */
	public int getScore() {

		return playerScores;
	}

	/**
	 * This method must do nothing in the case where the game is over.
	 * 
	 * As long as the game is not over yet, this method will do the following:
	 * 
	 * 1. Shift the existing rows down by one position. 2. Insert a row of
	 * random BoardCell objects at the top of the board. The row will be filled
	 * from left to right with cells obtained by calling
	 * BoardCell.getNonEmptyRandomBoardCell(). (The Random number generator
	 * passed to the constructor of this class should be passed as the argument
	 * to this method call.)
	 */
	public void nextAnimationStep() {
		if (this.isGameOver() == false) {
			for (int row = getRows() - SUB; row >= 0; row--) {
				for (int cols = 0; cols < getCols(); cols++) {
					if (getBoardCell(row, cols) != BoardCell.EMPTY) {
						setBoardCell(row + 1, cols,
								this.getBoardCell(row, cols));
					}
					if (row == 0) {
						setBoardCell(row, cols,
								BoardCell.getNonEmptyRandomBoardCell(random));
					}
				}
			}
		}
	}
	/*
	 * This method determines whwther there is a row in the index that is empty
	 * 
	 * @row returns the row in the index
	 */

	private boolean Isempty(int row) {
		int empty = 0;

		for (int col = 0; col < getCols(); col++) {
			if (getBoardCell(row, col) == BoardCell.EMPTY) {
				empty++;
			}
		}
		// if one of the rows is completely empty return true
		if (empty == getCols()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is called when the user clicks a cell on the board. If the
	 * selected cell is not empty, it will be set to BoardCell.EMPTY, along with
	 * any adjacent cells that are the same color as this one. (This includes
	 * the cells above, below, to the left, to the right, and all in all four
	 * diagonal directions.)
	 * 
	 * If any rows on the board become empty as a result of the removal of cells
	 * then those rows will "collapse", meaning that all non-empty rows beneath
	 * the collapsing row will shift upward.
	 * 
	 * @throws IllegalArgumentException with message "Invalid row index" for
	 *                                  invalid row or "Invalid column index"
	 *                                  for invalid column. We check for row
	 *                                  validity first.
	 */
	public void processCell(int rowIndex, int colIndex) {
		int up = rowIndex - 1;
		int down = rowIndex + 1;
		int right = colIndex + 1;
		int left = colIndex - 1;
		if (rowIndex > getRows() || rowIndex < 0) {
			throw new IllegalArgumentException("Invalid row Index");
		} else if (colIndex > getCols() || colIndex < 0) {
			throw new IllegalArgumentException("Invalid column index");
		}
		// This first checks if the colIndex and RowIndex not equals to empty
		if (board[rowIndex][colIndex] != BoardCell.EMPTY) {
			// if the top isnt less than 0
			if (up >= 0) {
				if (board[up][colIndex] == board[rowIndex][colIndex]) {
					board[up][colIndex] = BoardCell.EMPTY;
					playerScores++;
				}
				// diagonal to the left top left
				if (left >= 0) {
					if (board[up][left] == board[rowIndex][colIndex]) {
						board[up][left] = BoardCell.EMPTY;
						playerScores++;
					}
				}
				// Diagonal to the right, the top right
				if (right < getCols()) {
					if (board[up][right] == board[rowIndex][colIndex]) {
						board[up][right] = BoardCell.EMPTY;
						playerScores++;
					}
				}
			}
			// checks for the bottom index and the diagonal the bottom right and
			// left
			if (down < getRows()) {
				if (board[down][colIndex] == board[rowIndex][colIndex]) {
					board[down][colIndex] = BoardCell.EMPTY;
					playerScores++;
				}
				if (left >= 0) {
					if (board[down][left] == board[rowIndex][colIndex]) {
						board[down][left] = BoardCell.EMPTY;
						playerScores++;
					}
				}
				if (right < getCols()) {
					if (board[down][right] == board[rowIndex][colIndex]) {
						board[down][right] = BoardCell.EMPTY;
						playerScores++;
					}
				}
			}
			// These methods check if the index left and right are equals to
			// colIndex and row Index
			if (left > 0) {
				if (board[rowIndex][left] == board[rowIndex][colIndex]) {
					board[rowIndex][left] = BoardCell.EMPTY;
					playerScores++;
				}
			}
			if (right < getCols()) {
				if (board[rowIndex][right] == board[rowIndex][colIndex]) {
					board[rowIndex][right] = BoardCell.EMPTY;
					playerScores++;
				}
			}
			if (board[rowIndex][colIndex] == board[rowIndex][colIndex]) {
				board[rowIndex][colIndex] = BoardCell.EMPTY;
				playerScores++;

			}
		}
		// This method shifts the later row up and sets the later row to empty
		// if the previous row is empty
		for (int index = getRows() - SUB; index > 0; index--) {
			if (Isempty(index) == true) {
				for (int rows = index; rows < getRows() - 1; rows++) {
					for (int cols = 0; cols < getCols(); cols++) {
						setBoardCell(rows, cols,
								this.getBoardCell(rows + 1, cols));
						setBoardCell(rows + 1, cols, BoardCell.EMPTY);

					}
				}
			}
		}
	}
}

/**
 * @return
 */
