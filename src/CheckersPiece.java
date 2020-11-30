import javafx.scene.shape.*;
import javafx.scene.paint.Color;

/**
 * Represents a checker piece. 
 */
public class CheckersPiece extends Circle{
	Circle piece;	
	String color;
	int row, col;	//indicates current position of the checker piece.
	boolean gobble;
	boolean king;   //indicates if the piece has been kinged
	
	/**
	 * Constructor: Initializes the piece, color and king.
	 * @param int type where 0 is a black piece and 1 is a red piece
	 */
	protected CheckersPiece(String type) {
		super(40/2-4);
		
		if(type.equals("BLACK")) {
			setFill(javafx.scene.paint.Color.BLACK);
			piece = new Circle(40/2-4, Color.BLACK);
		}
		else if (type.equals("RED")) {
			setFill(javafx.scene.paint.Color.RED);
			piece = new Circle(40/2 -4, Color.RED);
		}
		color = type;
		king = false;
		gobble = false;
	}

	/**
	 * Turns the checker piece into a king.
	 */
	protected void kingMe() {
		king = true;
		//this.text
		
	}
	
	/**
	 * Getter for the piece
	 * @return piece 
	 */
	protected Circle getPiece() {
		return piece;
	}
	
	/**
	 * Indicates if a piece can gobble another piece this turn
	 * @return true if gobble gobble
	 */
	protected boolean canGobble() {
		return gobble;
	}
	
	/**
	 * Changes if a piece can gobble
	 * @param yum
	 */
	protected void gobble(boolean yum) {
		gobble = yum;
	}
	
	/**
	 * Returns the type of the piece
	 * @return String type
	 */
	protected String getType() {
		return color;
	}
	
	/**
	 * Indicates if the piece is king
	 * @return true if it's a king
	 */
	protected boolean isKing() {
		return king;
	}
	
	/**
	 * Updates the coordinates of the current piece
	 * @param x row
	 * @param y col
	 */
	protected void setPosition(int x, int y) {
		row = x;
		col = y;
	}
	
}
