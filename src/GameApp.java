import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Application that runs the CheckersGame class
 */
public class GameApp extends Application{
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox games = new VBox();
		Text pickOne = new Text("Pick a game");
		HBox gameSelection = new HBox();
		Button snake = new Button("Snake");
			snake.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					startSnake();
				}
			});
		Button checkers = new Button("Checkers");
			checkers.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					try {
						startCheckers();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		gameSelection.getChildren().addAll(snake, checkers);
		games.getChildren().addAll(pickOne, gameSelection);
		
		Scene primScene = new Scene(games);
		primaryStage.setMaxWidth(500);
		primaryStage.setMaxHeight(500);
	       	primaryStage.setTitle("Games");
	        primaryStage.setScene(primScene);
	        primaryStage.sizeToScene();
	        primaryStage.show();
		
	}

	public void startCheckers() throws Exception {
		CheckersGame game = new CheckersGame();
		Stage stage = game.getStage();
		Scene scene = new Scene(game.getBoard());
		
		stage.setMaxWidth(500);
		stage.setMaxHeight(500);
	       	stage.setTitle("Checkers");
	        stage.setScene(scene);
	        stage.sizeToScene();
	        stage.show();
		
	}
	public void startSnake()  {
		Snake game = new Snake();
		game.start();
	}
	
	
	
	 public static void main(String[] args) {
			try {
				Application.launch(args);
			} catch (UnsupportedOperationException e) {
			    System.out.println(e);
			    System.err.println("If this is a DISPLAY problem, then your X server connection");
			    System.err.println("has likely timed out. This can generally be fixed by logging");
			    System.err.println("out and logging back in.");
			    System.exit(1);
			} // try
		    } // main

}
