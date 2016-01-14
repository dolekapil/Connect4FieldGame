import java.util.Scanner;

public class Player implements PlayerInterface {

	public String name;
	public char gamePiece;
	public Connect4FieldInterface connect4FieldInterface;

	Player(Connect4FieldInterface theField, String name, char gamePiece) {
		this.name = name;
		this.gamePiece = gamePiece;
		this.connect4FieldInterface = theField;
	}

	public char getGamePiece() {
		return this.gamePiece;
	}

	public String getName() {
		return this.name;
	}

	public int nextMove() {
		int move;
		Scanner sc = new Scanner(System.in);
		System.out.println("please enter the column number where you want to drop your piece.");
		move = sc.nextInt();
		return move;

	}
}