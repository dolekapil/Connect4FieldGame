/**
 * This program is two player connect4field game, where each player
 * gets alternate turn in which they can drop their respective
 * pieces and the first player to get 4 consecutive pieces in horizontal,
 * vertical or slant manner wins the game.
 *
 * @author      Kapil Dole
 *
 */
public class Connect4Field implements Connect4FieldInterface {
	PlayerInterface A, B;
	static int move, noOfRows = 9, noOfColumns = 25, dropRow, dropColumn;
	static char[][] board = new char[9][25];

	public Connect4Field() {
		int counter = noOfColumns;
		// Setting up unusual board shape.
		for (int row = 0; row < noOfRows; row++) {
			for (int column = 0; column < noOfColumns; column++) {
				board[row][column] = ' ';
			}
		}

		for (int row = 0; row < noOfRows; row++) {
			for (int column = row; column < counter; column++) {
				board[row][column] = 'o';
			}
			counter--;
		}
	}

	public void init(PlayerInterface playerA, PlayerInterface playerB) {
		A = playerA;
		B = playerB;
	}

	public boolean checkIfPiecedCanBeDroppedIn(int column) {
		try {
			if (board[0][column - 1] == 'o') {
				return true;
			} else {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public boolean dropPieces(int column, char gamePiece) {
		if (board[0][column - 1] != 'o') {
			System.out.println("Column full, Can not drop piece here, try another column");
			return false;
		} else {
			for (int count = noOfRows - 1; count >= 0; count--) {
				if (board[count][column - 1] == 'o') {
					board[count][column - 1] = gamePiece;
					dropRow = count;
					dropColumn = column - 1;
					break;
				}
			}
			return true;
		}
	}

	public boolean didLastMoveWin() {
		char currentPiece;
		StringBuffer strVertical = new StringBuffer();
		StringBuffer strHorizontal = new StringBuffer();
		StringBuffer strSlant = new StringBuffer();
		StringBuffer strSlantRight = new StringBuffer();
		currentPiece = board[dropRow][dropColumn];
		String pattern = "(.*)\\" + currentPiece + "\\" + currentPiece + "\\" + currentPiece + "\\" + currentPiece
				+ "(.*)";

		// checking for column
		for (int counterVertical = 0; counterVertical < noOfRows; counterVertical++) {
			strVertical.append(board[counterVertical][dropColumn]);
		}
		if (String.valueOf(strVertical).matches(pattern)) {
			return true;
		}

		// Checking for row
		for (int counterHorizontal = 0; counterHorizontal < noOfColumns; counterHorizontal++) {
			strHorizontal.append(board[dropRow][counterHorizontal]);
		}
		if (String.valueOf(strHorizontal).matches(pattern)) {
			return true;
		}

		// Checking left inclined columns
		int xRow, xCol;
		if (dropRow > dropColumn) {
			xRow = dropRow - dropColumn;
			xCol = 0;
		} else if (dropColumn > dropRow) {
			xRow = 0;
			xCol = dropColumn;
		} else {
			xRow = 0;
			xCol = 0;
		}
		while (xRow < noOfRows && xCol < noOfColumns) {
			strSlant.append(board[xRow][xCol]);
			xRow++;
			xCol++;
		}
		if (String.valueOf(strSlant).matches(pattern)) {
			return true;
		}

		// Checking right inclined columns.
		int xRow1 = dropRow, xCol1 = dropColumn;
		while (xRow1 > 0 && xCol1 < noOfColumns - 1) {
			xRow1--;
			xCol1++;
		}
		strSlantRight.append(board[xRow1][xCol1]);
		while (xRow1 < noOfRows - 1 && xCol1 > 0) {
			xRow1++;
			xCol1--;
			strSlantRight.append(board[xRow1][xCol1]);
		}
		if (String.valueOf(strSlantRight).matches(pattern)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isItaDraw() {
		for (int row = 0; row < noOfRows; row++) {
			for (int column = 0; column < noOfColumns; column++) {
				if (board[row][column] == 'o') {
					return false;
				}

			}
		}
		return true;
	}

	public boolean isValid() {
		if (move < 0 || move > noOfColumns) {
			System.out.println("Invalid input. Please enter valid column number.");
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void playTheGame() {
		int turns = 0;
		System.out.println("The board size is bit unusual, enjoy the game!!!\n");
		System.out.println(this);
		while (true) {
			if (turns % 2 == 0) {
				System.out.println("Player " + A.getName() + "'s turn: ");
				move = A.nextMove();
				if (!isValid()) {
					System.out.println(this);
					continue;
				}
				if (!dropPieces(move, A.getGamePiece())) {
					System.out.println(this);
					continue;
				}

			} else {
				System.out.println("Player " + B.getName() + "'s turn: ");
				move = B.nextMove();
				if (!isValid()) {
					System.out.println(this);
					continue;
				}
				if (!dropPieces(move, B.getGamePiece())) {
					System.out.println(this);
					continue;
				}
			}
			System.out.println(this);
			if (didLastMoveWin()) {
				if (turns % 2 == 0) {
					System.out.println("Winner is " + A.getName());
				} else {
					System.out.println("Winner is " + B.getName());
				}
				break;
			} else if (isItaDraw()) {
				System.out.println("Match draw.");
				break;
			}
			turns++;
		}

	}

	public String toString() {
		for (int row = 0; row < noOfRows; row++) {
			for (int column = 0; column < noOfColumns; column++) {
				System.out.print(board[row][column]);
			}
			System.out.println();
		}
		return "";
	}

	public static void main(String[] args) {
		Connect4Field connect4Field = new Connect4Field();
		Player A = new Player(connect4Field, "A", '#');
		Player B = new Player(connect4Field, "B", '+');

		connect4Field.init(A, B);
		connect4Field.playTheGame();

	}
}
