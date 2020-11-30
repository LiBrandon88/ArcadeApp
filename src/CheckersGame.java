import javax.swing.BorderFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.color.*;


/**
 * Class representing a checkers game. Contains the methods 
 * for game's logic and provides the java fx panes.
 */
public class CheckersGame {
	GridPane board;
	BorderPane gameFrame;
	StackPane[][] checkerSquares;
	CheckersPiece[] pieces; //blackPieces players 1 redPieces player 2
	Shape[][] boardPieces;
	int p1Pieces = 12, p2Pieces = 12, turn = 1; // 1 == player1 2 == player2.
	MenuBar menuBar;
	VBox info, top;
	Text turnText, winMessage;
	Stage stage;
	
	/**
	 * Constructor: Creates a checkers game
	 * Initialize all the layouts and creates pieces for the game
	 */
	public CheckersGame() {
		board = new GridPane();
		checkerSquares = new StackPane[8][8];
		buildBoard();
		gameFrame = new BorderPane();
		buildMenuBar();
		winMessage = new Text();
		info = new VBox();
		turnText = new Text("Player 1's Turn");
		info.getChildren().add(turnText);
		info.getChildren().add(winMessage);
		top = new VBox();
		top.getChildren().add(menuBar);
		top.getChildren().add(info);
		gameFrame.setTop(top);
		gameFrame.setCenter(board);
		stage = new Stage();
	}
	
	/**
	 * Builds a checker board with all the pieces added
	 */
	private void buildBoard() {
		int pieceCount = 0;
		//Loop to add all squares into the checkerSquares array at coordinates
		//corresponding to the actual checkers board. Adds a piece to the correct squares
		buildBoardPieces();
		buildCheckerPieces();
		for(int x = 0; x < 8; x++) {			
			for(int y = 0; y < 8; y++) {
				checkerSquares[x][y] = new StackPane();
				checkerSquares[x][y].getChildren().add(boardPieces[x][y]);
				//Adds pieces to checker square.
				if(x < 3) {
					if(x % 2 == 0 && y % 2 == 0) {
						checkerSquares[x][y].getChildren().add(pieces[pieceCount]);
						pieces[pieceCount].setPosition(x, y);
						pieceCount++;
					}
					else if(x == 1 && y % 2 != 0) {
						checkerSquares[x][y].getChildren().add(pieces[pieceCount]);
						pieces[pieceCount].setPosition(x, y);
						pieceCount++;
					}
				}
				if(x > 4) {
					if(x % 2 == 0 && y % 2 == 0) {
						checkerSquares[x][y].getChildren().add(pieces[pieceCount]);
						pieces[pieceCount].setPosition(x, y);
						pieceCount++;

					}
					else if(x % 2 != 0 && y % 2 != 0) {
						checkerSquares[x][y].getChildren().add(pieces[pieceCount]);
						pieces[pieceCount].setPosition(x, y);
						pieceCount++;
					}
				}
				board.add(checkerSquares[x][y], y, x);
			} 
		} //for
	} //buildBoard
	
	/**
	 * Build all pieces of the checker board
	 */
	private void buildBoardPieces() {		
		boardPieces = new Rectangle[8][8];
		for(int x = 0; x < 8; x++) {
			if(x % 2 == 0) {
				for(int y = 0; y < 8; y++) {
					if(y % 2 == 0) {
						boardPieces[x][y] = new Rectangle(40, 40, Color.WHEAT);
					}
					else {
						boardPieces[x][y] = new Rectangle(40, 40, Color.MAROON);
					}
					boardPieces[x][y].setStroke(Color.BLACK);
				}
			}
			else {
				for(int y = 0; y < 8; y++) {
					if(y % 2 != 0) {
						boardPieces[x][y] = new Rectangle(40, 40, Color.WHEAT);
					}
					else {
						boardPieces[x][y] = new Rectangle(40, 40, Color.MAROON);
					}
					boardPieces[x][y].setStroke(Color.BLACK);
				} //for
			} //else 		
		} //for
	}
	
	/**
	 * Builds the game/checker pieces
	 */
	private void buildCheckerPieces() {		
		pieces = new CheckersPiece[24];
		for(int i = 0; i < 24; i++) {
			if(i < 12) {
				CheckersPiece piece = new CheckersPiece("BLACK");
				piece.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent movePiece) {
						System.out.println("ClickedBlack");
						if(turn == 1) { 
							unHighlight();
							waitForMove(piece);		
						}
					}});
				pieces[i] = piece;
			}
			else {
				CheckersPiece piece = new CheckersPiece("RED");
				piece.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent movePiece) {
						System.out.println("ClickedRed");
						if(turn == 2) {
							unHighlight();
							waitForMove(piece);
						}
					}});
				pieces[i] = piece;
			}
		}
	}
	

	/**
	 * Highlights all possible moves for a certain piece
	 * @param CheckersPiece piece piece to be moved
	 */
	protected void waitForMove(CheckersPiece piece) {
		//if black piece was clicked
		if(piece.isKing()) {
			highlightBMoves(piece);
			highlightRMoves(piece);
		}
		else if(piece.getType().equals("BLACK")){
			highlightBMoves(piece);
		}
		else if(piece.getType().equals("RED")) {
			highlightRMoves(piece);
		}
	}

	/**
	 * Highlights possible moves by a black piece
	 * @param CheckersPiece piece
	 */
	private void highlightBMoves(CheckersPiece piece) {
		if(downLeft(piece.row, piece.col, piece)) {
			if(piece.canGobble()) {
				piece.gobble(false);
				highlight(piece.row+2, piece.col-2, piece, true, false);
			}
			else {
				highlight(piece.row+1, piece.col-1, piece, false, false);
			}
		}
		if(downRight(piece.row, piece.col, piece)) {
			if(piece.canGobble()) {
				piece.gobble(false);
				highlight(piece.row+2, piece.col+2, piece, true, false);
				
			}
			else {
				highlight(piece.row+1, piece.col+1, piece, false,false );
			}
		}	
	}
	
	/**
	 * Highlights possible moves by a red piece
	 * @param CheckersPiece piece
	 */
	private void highlightRMoves(CheckersPiece piece) {
		if(upLeft(piece.row, piece.col, piece)) {
			if(piece.canGobble()) {
				piece.gobble(false);
				highlight(piece.row-2, piece.col-2, piece, true, false);
			}
			else {
				highlight(piece.row-1, piece.col-1, piece, false, false);
			}

		}
		if(upRight(piece.row, piece.col, piece)) {			
			if(piece.canGobble()) {
				piece.gobble(false);
				highlight(piece.row-2, piece.col+2, piece, true, false);
			}
			else {
				highlight(piece.row-1, piece.col+1, piece, false, false);
			}		
		}
	}
	
	
	/**
	 * Highlights the coordinates given for the checker piece given, 
	 * if there's a piece there, highlight the next move available
	 * @param int row to highlight
	 * @param int col to highlight
	 * @param CheckersPiece piece 
	 * @param boolean gobble indicates if a piece can be taken
	 * @param boolean doubleMove inidicates if there could be a double move
	 */
	protected void highlight(int row, int col, CheckersPiece piece, boolean gobble, boolean doubleMove) {
		boardPieces[row][col].setStroke(Color.YELLOW);
		boardPieces[row][col].setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent movePiece) {
				if(gobble) {
					gobble(row, col, piece);
				}					
				if(!piece.isKing() && checkForKing(row, col, piece)) {
						checkerSquares[row][col].getChildren().add(piece);
						piece.setPosition(row, col);
						king(row, col, piece);
						unHighlight();
						resetBoardPieces();
						nextTurn();
				}
				else {
					if(piece.isKing()) {
						updateKing(row,col, piece);
					}
					else {
						checkerSquares[row][col].getChildren().add(piece);
						piece.setPosition(row, col);
					}
					unHighlight();
					if(!doubleMove && gobble) {
						if(!checkForDoubleMove(row, col, piece)) {
							resetBoardPieces();
							nextTurn();
						}
					}
					else {
						resetBoardPieces();
						nextTurn();
					}
				}
				if(gameOver()) {
					endGame();
				}
			}
		});
	}
	
	/**
	 * 
	 * @param int row row to gobble
	 * @param int col col to gobble
	 * @param CheckersPiece piece that gobbles
	 */
	protected void gobble(int row, int col, CheckersPiece piece) {
		int rowToGobble = -1; 
		int colToGobble = -1;
		if(piece.row - 2 == row) {
			rowToGobble = piece.row - 1;
		}
		if(piece.row + 2 == row) {
			rowToGobble = piece.row + 1;
		}
		if(piece.col + 2 == col) {
			colToGobble = piece.col + 1;
		}
		if(piece.col - 2 == col) {
			colToGobble = piece.col - 1;
		}					
		if(findPiece(rowToGobble, colToGobble).isKing()) {
			checkerSquares[rowToGobble][colToGobble].getChildren().remove(2);
		}
		checkerSquares[rowToGobble][colToGobble].getChildren().remove(1);
		findPiece(rowToGobble, colToGobble).setPosition(-1, -1);			
		if(turn == 1) {
			p2Pieces--;
		}
		if(turn == 2) {
			p1Pieces--;
		}
	}
	
	/**
	 * Checks if a double move is available 
	 * @param int row coordinates
	 * @param int col coordinates
	 * @param CheckersPiece piece
	 * @return boolean true if it can double move, false otherwise
	 */
	protected boolean checkForDoubleMove(int row, int col, CheckersPiece piece) {
		boolean doubleMove = false;
		if(piece.getType().equals("BLACK") || piece.isKing()) {
			if(downLeft(row,col,piece) && piece.canGobble()) {
				doubleMove = true;
				piece.gobble(false);
				highlight(row+2, col-2, piece, true, true);
			}
			if(downRight(row,col,piece) && piece.canGobble()) {
				doubleMove = true;
				piece.gobble(false);
				highlight(row+2, col+2, piece, true, true);
				
			}
		}
		if(piece.getType().equals("RED") || piece.isKing()) {
			if(upLeft(row,col,piece) && piece.canGobble()) {
				doubleMove = true;
				piece.gobble(false);
				highlight(row-2, col-2, piece, true, true);
			}
			if(upRight(row,col,piece) && piece.canGobble()) {
				doubleMove = true;
				piece.gobble(false);
				highlight(row-2, col+2, piece, true, true);
			}
		}
		return doubleMove;
	}
	
	/**
	 * Checks if piece can go downleft from coordinates
	 * @param row coordinate
	 * @param col coordinate
	 * @param piece piece to move
	 * @return
	 */
	protected boolean downLeft(int row, int col, CheckersPiece piece) {
		boolean canDo = false;
		String type = piece.getType();
		if(checkForPiece(row+1, col-1) == true) {
			if(row+2 <= 7 && col-2 >= 0 && !type.equals(findPiece(row+1,col-1).getType())) {
				if(!checkForPiece(row+2, col-2)) {
					canDo = true;
					piece.gobble(true);
				}
			}
		}
		else if(row+1 <= 7 && col-1 >= 0 && !piece.canGobble()){
			canDo = true;
			System.out.println("highlight in dL");
		}
		return canDo;
	}
	
	/**
	 * Checks if piece can go downRight from coordinates
	 * @param row coordinate
	 * @param col coordinate
	 * @param piece piece to move
	 * @return
	 */
	protected boolean downRight(int row, int col, CheckersPiece piece) {
		boolean canDo = false;
		String type = piece.getType();
		if(checkForPiece(row+1, col+1) == true) {
			if(row+2 <= 7 && col+2 <= 7 && !type.equals(findPiece(row+1,col+1).getType())) {
				if(!checkForPiece(row+2, col+2)) {
					canDo = true;
					piece.gobble(true);
				}
			}
		}
		else if(row+1 <= 7 && col+1 <= 7 && !piece.canGobble()){
			canDo = true;
			System.out.println("highlight in dL");
		}
		return canDo;
	}
	
	/**
	 * Checks if piece can go upLeft from coordinates
	 * @param row coordinate
	 * @param col coordinate
	 * @param piece piece to move
	 * @return
	 */
	protected boolean upLeft(int row, int col, CheckersPiece piece) {
		boolean canDo = false;
		String type = piece.getType();
		if(checkForPiece(row-1, col-1) == true) {
			if(row-2 >= 0 && col-2 >= 0 && !type.equals(findPiece(row-1,col-1).getType())) {
					if(!checkForPiece(row-2, col-2)) {
						canDo = true;
						piece.gobble(true);
					}
			}			
		}
		else if(row-1 >= 0 && col-1 >= 0 && !piece.canGobble()){
			canDo = true;
			System.out.println("highlight in dL");
		}
		return canDo;
	}
	
	/**
	 * Checks if piece can go upRight from coordinates
	 * @param row coordinate
	 * @param col coordinate
	 * @param piece piece to move
	 * @return
	 */
	protected boolean upRight(int row, int col, CheckersPiece piece) {
		boolean canDo = false;
		String type = piece.getType();
		if(checkForPiece(row-1, col+1) == true) {
			if(row-2 >= 0 && col+2 <= 7 && !type.equals(findPiece(row-1,col+1).getType())) {
				if(!checkForPiece(row-2, col+2)) {
					canDo = true;
					piece.gobble(true);
				}
			}
		}
		else if(row-1 >= 0 && col+1 <= 7 && !piece.canGobble()){
			canDo = true;
			System.out.println("highlight in dL");
		}
		return canDo;
	}
	/**
	 * Resets the pieces of the board's events handlers
	 */
	protected void resetBoardPieces() {
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				boardPieces[x][y].setOnMouseClicked(null);
			}
		}
	}
	
	/**
	 * Checks the coordinates to see if a checker piece is there
	 * @param int row of square
	 * @param int col of square
	 * @return true if there is a checker piece there, false if not
	 */
	protected boolean checkForPiece(int row, int col) {
		boolean found = false;
		for(int i = 0; i < pieces.length; i++) {
			if(pieces[i].row == row && pieces[i].col == col) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	/**
	 * Finds and returns the checker piece located at coordinates
	 * @param int row
	 * @param int col
	 * @return CheckersPiece piece at coordinate
	 */
	protected CheckersPiece findPiece(int row, int col) {
		 //Only runs if checkForPiece returns true, therefore no Exception handling
		 //is needed for piece being -1.
		int piece = -1;
		for(int i = 0; i < pieces.length; i++) {
			if(pieces[i].row == row && pieces[i].col == col) {
				piece = i;
				break;
			}
		}
		return pieces[piece];
	}

	/**
	 * UnHighlights all checker squares
	 */
	protected void unHighlight() {
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				boardPieces[x][y].setStroke(Color.BLACK);
			}
		}
	}
	
	/**
	 * Displays win message
	 */
	protected void winMessage() {
		
		if(p2Pieces == 0) {
			winMessage.setText("Winner Winner Chicken Dinner: Congrats Player 1");
		}
		else if(p1Pieces == 0) {
			winMessage.setText("Winner Winner Chicken Dinner: Congrats Player 2");
		}
	}
	
	/**
	 * Builds the menu bar
	 */
	protected void buildMenuBar() {
		menuBar = new MenuBar();			
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent closeProgram) {
				closeGame();
			}});
		Menu fileMenu = new Menu("File"); //Creates file menu and then adds exit item
		fileMenu.getItems().add(exit);
		menuBar.getMenus().add(fileMenu); 
	}
	
	/**
	 * Signals its the next turn
	 */
	protected void nextTurn() {
		if(turn == 1) {
			turn = 2;
			turnText.setText("Player 2's Turn");
		}
		else {
			turn = 1;
			turnText.setText("Player 1's Turn");
		}
	}
	
	/**
	 * Checks if a piece can be kinged
	 * @param row coordinate
	 * @param col coordinate
	 * @param piece 
	 * @return true if can be kinged, false if already king or not king
	 */
	private boolean checkForKing(int row, int col, CheckersPiece piece) {
		boolean isKinged = false;
		if(!piece.isKing()) {
			if(piece.getType().equals("BLACK")) {
				if(row == 7) {
					isKinged = true;
				}
			}
			else if (piece.getType().equals("RED")) {
				if(row == 0) {
					isKinged = true;
				}
			}
		}
		return isKinged;
	}
	
	/**
	 * Function to king a piece
	 * @param piece
	 */
	private void king(int row, int col, CheckersPiece piece) {
		piece.kingMe();
		Text king = new Text("K");
		king.setFill(Color.GOLD);
		checkerSquares[piece.row][piece.col].getChildren().add(king);
	}
	
	/**
	 * Updates the king as it moves
	 * @param row coordinate
	 * @param col coordinate
	 * @param piece to move
	 */
	private void updateKing(int row, int col, CheckersPiece piece) {
		Node king = checkerSquares[piece.row][piece.col].getChildren().get(2);
		checkerSquares[row][col].getChildren().add(piece);
		piece.setPosition(row, col);
		checkerSquares[row][col].getChildren().add(king);
	}
	/**
	 * Getter for the checker board
	 * @return GridPane checker board.
	 */
	protected BorderPane getBoard() {
		return gameFrame;
	}
	
	/**
	 * Ends the current game and give option to restart
	 */
	protected void endGame() {
		winMessage();
		HBox playAgain = new HBox();
		Text play = new Text("Play again?");
			Button yes = new Button("Yes");
			yes.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					resetGame();
				}
			});
			Button no = new Button("No");
			no.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					closeGame();
				}
			});
			playAgain.getChildren().addAll(yes, no);
		top.getChildren().add(playAgain);
	}
	
	/**
	 * Resets the entire game
	 */
	protected void resetGame() {
		p1Pieces = 12;
		p2Pieces = 12;
		turn = 1;
		checkerSquares = new StackPane[8][8];
		winMessage.setText("");
		buildBoard();
		turnText.setText("Player 1's Turn");
		top.getChildren().remove(2);
		
	}
	
	/**
	 * Checks if game is over
	 * @return true if game is over
	 */
	protected boolean gameOver() {
		if(p1Pieces == 0 || p2Pieces == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Gets stage
	 * @return stage
	 */
	protected Stage getStage() {
		return stage;
	}
	
	/**
	 * Closes the stage
	 */
	protected void closeGame() {
		stage.close();
	}
}
