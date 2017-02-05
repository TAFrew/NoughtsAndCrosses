import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application{

	private Stage _stage;

	private ArrayList<Node> _squares = new ArrayList<>();

	private ArrayList<Integer> _playersMoves = new ArrayList<>();
	private ArrayList<Integer> _AIMoves = new ArrayList<>();

	private Pane _app = new Pane();
	private Pane _game = new Pane();

	private boolean _playersTurn = true;
	private int _turn = 1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize();

		Scene scene = new Scene(_app);
		scene.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				handleMouseClick(event);
			}
		});

		// stage
		_stage = primaryStage;
		_stage.setTitle("Noughts and Crosses");
		_stage.setScene(scene);
		_stage.show();

		// animation timer
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(!(_playersTurn)){
					AITurn();
				}
			}
		};

		timer.start();
	}

	protected void AITurn() {

		int location = whereAICanWin();

		if(!(location == -1)){
			// go in location
			((Rectangle)_squares.get(location)).setFill(Color.BLUE);
			_turn++;
			_playersTurn = true;
			return;
		}
		else{
			if(_turn == 2){
				if(scenario1()){
					((Rectangle)_squares.get(4)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
				else if(scenario2()){
					((Rectangle)_squares.get(4)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
				else if(scenario3()){
					((Rectangle)_squares.get(0)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
			}
			else if(_turn == 4){
				if(scenario1a()){
					((Rectangle)_squares.get(5)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
				else if(scenario1b()){
					location = wherePlayerCanWinNextTurn();
					if(location != -1){
						((Rectangle)_squares.get(location)).setFill(Color.BLUE);
						_turn++;
						_playersTurn = true;
						return;
					}
					else{
						System.out.println("You done fucked up");
					}
				}
				else if(scenario1c()){
					location = getAIForScenario1c();
					if(location != -1){
						((Rectangle)_squares.get(location)).setFill(Color.BLUE);
						_turn++;
						_playersTurn = true;
						return;
					}
					else{
						System.out.println("You done fucked up");
					}
				}
				else if(scenario2a()){
					location = wherePlayerCanWinNextTurn();
					if(location != -1){
						((Rectangle)_squares.get(location)).setFill(Color.BLUE);
						_turn++;
						_playersTurn = true;
						return;
					}
					else{
						System.out.println("You done fucked up");
					}
				}
				else if(scenario2b()){
					// same as 1c so can't happen
				}
				else if(scenario2c() != -1){
					location = scenario2c() - 4;
					((Rectangle)_squares.get(location)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
				else if(scenario2d()){
					location = 0;
					((Rectangle)_squares.get(location)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
				else if(scenario3a()){
					location = wherePlayerCanWinNextTurn();
					if(location != -1){
						((Rectangle)_squares.get(location)).setFill(Color.BLUE);
						_turn++;
						_playersTurn = true;
						return;
					}
					else{
						System.out.println("You done fucked up");
					}
				}
				else if(scenario3b()){
					location = 6;
					((Rectangle)_squares.get(location)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
			}
			else if(_turn == 6){
				location = wherePlayerCanWinNextTurn();
				if(location != -1){
					((Rectangle)_squares.get(location)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
				else{
					for(int i = 0; i < 9; i++){
						if(!(isSelectedByAnyone(i))){
							((Rectangle)_squares.get(i)).setFill(Color.BLUE);
							_turn++;
							_playersTurn = true;
							return;
						}
					}
				}
			}
			else if(_turn == 8){
				location = wherePlayerCanWinNextTurn();
				if(location != -1){
					((Rectangle)_squares.get(location)).setFill(Color.BLUE);
					_turn++;
					_playersTurn = true;
					return;
				}
				else{
					for(int i = 0; i < 9; i++){
						if(!(isSelectedByAnyone(i))){
							((Rectangle)_squares.get(i)).setFill(Color.BLUE);
							_turn++;
							_playersTurn = true;
							return;
						}
					}
				}
			}
		}
	}

	private boolean scenario3b() {
		if(_playersMoves.contains(4) && _playersMoves.contains(8)){
			return true;
		}
		return false;
	}

	private boolean scenario3a() {
		if(_playersMoves.contains(4) && (!_playersMoves.contains(8))){
			return true;
		}
		return false;
	}

	private boolean scenario2d() {
		if(_playersMoves.contains(1) && _playersMoves.contains(7)){
			return true;
		}
		else if(_playersMoves.contains(3) && _playersMoves.contains(5)){
			return true;
		}
		return false;
	}

	private int scenario2c() {
		if(_playersMoves.contains(1) && _playersMoves.contains(3)){
			return 4;
		}
		else if(_playersMoves.contains(1) && _playersMoves.contains(5)){
			return 6;
		}
		else if(_playersMoves.contains(3) && _playersMoves.contains(7)){
			return 10;
		}
		else if(_playersMoves.contains(5) && _playersMoves.contains(7)){
			return 12;
		}
		return -1;
	}

	private boolean scenario2b() {
		// same as 1c
		return false;
	}

	private boolean scenario2a() {
		if(_playersMoves.contains(1) && (_playersMoves.contains(0) || _playersMoves.contains(2))){
			return true;
		}
		if(_playersMoves.contains(3) && (_playersMoves.contains(0) || _playersMoves.contains(6))){
			return true;
		}
		if(_playersMoves.contains(5) && (_playersMoves.contains(2) || _playersMoves.contains(8))){
			return true;
		}
		if(_playersMoves.contains(7) && (_playersMoves.contains(6) || _playersMoves.contains(8))){
			return true;
		}
		return false;
	}

	private int getAIForScenario1c() {
		if(_playersMoves.contains(0) && _playersMoves.contains(7)){
			return 6;
		}
		else if(_playersMoves.contains(0) && _playersMoves.contains(5)){
			return 2;
		}
		else if(_playersMoves.contains(2) && _playersMoves.contains(3)){
			return 0;
		}
		else if(_playersMoves.contains(2) && _playersMoves.contains(7)){
			return 8;
		}
		else if(_playersMoves.contains(6) && _playersMoves.contains(1)){
			return 0;
		}
		else if(_playersMoves.contains(6) && _playersMoves.contains(5)){
			return 8;
		}
		else if(_playersMoves.contains(8) && _playersMoves.contains(1)){
			return 2;
		}
		else if(_playersMoves.contains(8) && _playersMoves.contains(3)){
			return 6;
		}
		return -1;
	}

	private boolean scenario1c() {
		if(_playersMoves.contains(0) && (_playersMoves.contains(5) || _playersMoves.contains(7))){
			return true;
		}
		if(_playersMoves.contains(2) && (_playersMoves.contains(3) || _playersMoves.contains(7))){
			return true;
		}
		if(_playersMoves.contains(6) && (_playersMoves.contains(1) || _playersMoves.contains(5))){
			return true;
		}
		if(_playersMoves.contains(8) && (_playersMoves.contains(1) || _playersMoves.contains(3))){
			return true;
		}
		return false;
	}

	private boolean scenario1b() {
		if(_playersMoves.contains(0) && (_playersMoves.contains(1) || _playersMoves.contains(2) || _playersMoves.contains(3) || _playersMoves.contains(6))){
			return true;
		}
		if(_playersMoves.contains(2) && (_playersMoves.contains(0) || _playersMoves.contains(1) || _playersMoves.contains(5) || _playersMoves.contains(8))){
			return true;
		}
		if(_playersMoves.contains(6) && (_playersMoves.contains(0) || _playersMoves.contains(3) || _playersMoves.contains(7) || _playersMoves.contains(8))){
			return true;
		}
		if(_playersMoves.contains(8) && (_playersMoves.contains(2) || _playersMoves.contains(5) || _playersMoves.contains(6) || _playersMoves.contains(7))){
			return true;
		}
		return false;
	}

	private boolean scenario1a() {
		if(_playersMoves.contains(0) && _playersMoves.contains(8)){
			return true;
		}
		if(_playersMoves.contains(2) && _playersMoves.contains(6)){
			return true;
		}
		return false;
	}

	private boolean scenario3() {
		if(_playersMoves.contains(4)){
			return true;
		}
		return false;
	}

	private boolean scenario2() {
		if(_playersMoves.contains(1) || _playersMoves.contains(3) || _playersMoves.contains(5) || _playersMoves.contains(7)){
			return true;
		}
		return false;
	}

	private boolean scenario1() {
		if(_playersMoves.contains(0) || _playersMoves.contains(2) || _playersMoves.contains(6) || _playersMoves.contains(8)){
			return true;
		}
		return false;
	}

	private int whereAICanWin() {
		// horizontal
		if(isSelectedByAI(0) && isSelectedByAI(1) && (!isSelectedByAnyone(2))){
			return 2;
		}
		if(isSelectedByAI(1) && isSelectedByAI(2) && (!isSelectedByAnyone(0))){
			return 0;
		}
		if(isSelectedByAI(0) && isSelectedByAI(2) && (!isSelectedByAnyone(1))){
			return 1;
		}
		if(isSelectedByAI(3) && isSelectedByAI(4) && (!isSelectedByAnyone(5))){
			return 5;
		}
		if(isSelectedByAI(4) && isSelectedByAI(5) && (!isSelectedByAnyone(3))){
			return 3;
		}
		if(isSelectedByAI(3) && isSelectedByAI(5) && (!isSelectedByAnyone(4))){
			return 4;
		}
		if(isSelectedByAI(6) && isSelectedByAI(7) && (!isSelectedByAnyone(8))){
			return 8;
		}
		if(isSelectedByAI(7) && isSelectedByAI(8) && (!isSelectedByAnyone(6))){
			return 6;
		}
		if(isSelectedByAI(6) && isSelectedByAI(8) && (!isSelectedByAnyone(7))){
			return 7;
		}

		// vertical
		if(isSelectedByAI(0) && isSelectedByAI(3) && (!isSelectedByAnyone(6))){
			return 6;
		}
		if(isSelectedByAI(3) && isSelectedByAI(6) && (!isSelectedByAnyone(0))){
			return 0;
		}
		if(isSelectedByAI(0) && isSelectedByAI(6) && (!isSelectedByAnyone(3))){
			return 3;
		}
		if(isSelectedByAI(1) && isSelectedByAI(4) && (!isSelectedByAnyone(7))){
			return 7;
		}
		if(isSelectedByAI(4) && isSelectedByAI(7) && (!isSelectedByAnyone(1))){
			return 1;
		}
		if(isSelectedByAI(1) && isSelectedByAI(7) && (!isSelectedByAnyone(4))){
			return 4;
		}
		if(isSelectedByAI(2) && isSelectedByAI(5) && (!isSelectedByAnyone(8))){
			return 8;
		}
		if(isSelectedByAI(5) && isSelectedByAI(8) && (!isSelectedByAnyone(2))){
			return 2;
		}
		if(isSelectedByAI(2) && isSelectedByAI(8) && (!isSelectedByAnyone(5))){
			return 5;
		}

		// diagonal
		if(isSelectedByAI(0) && isSelectedByAI(4) && (!isSelectedByAnyone(8))){
			return 8;
		}
		if(isSelectedByAI(4) && isSelectedByAI(8) && (!isSelectedByAnyone(0))){
			return 0;
		}
		if(isSelectedByAI(0) && isSelectedByAI(8) && (!isSelectedByAnyone(4))){
			return 4;
		}
		if(isSelectedByAI(2) && isSelectedByAI(4) && (!isSelectedByAnyone(6))){
			return 8;
		}
		if(isSelectedByAI(4) && isSelectedByAI(6) && (!isSelectedByAnyone(2))){
			return 2;
		}
		if(isSelectedByAI(2) && isSelectedByAI(6) && (!isSelectedByAnyone(4))){
			return 4;
		}

		return -1;
	}

	private int wherePlayerCanWinNextTurn() {
		// horizontal
		if(isSelectedByPlayer(0) && isSelectedByPlayer(1) && (!isSelectedByAnyone(2))){
			return 2;
		}
		if(isSelectedByPlayer(1) && isSelectedByPlayer(2) && (!isSelectedByAnyone(0))){
			return 0;
		}
		if(isSelectedByPlayer(0) && isSelectedByPlayer(2) && (!isSelectedByAnyone(1))){
			return 1;
		}
		if(isSelectedByPlayer(3) && isSelectedByPlayer(4) && (!isSelectedByAnyone(5))){
			return 5;
		}
		if(isSelectedByPlayer(4) && isSelectedByPlayer(5) && (!isSelectedByAnyone(3))){
			return 3;
		}
		if(isSelectedByPlayer(3) && isSelectedByPlayer(5) && (!isSelectedByAnyone(4))){
			return 4;
		}
		if(isSelectedByPlayer(6) && isSelectedByPlayer(7) && (!isSelectedByAnyone(8))){
			return 8;
		}
		if(isSelectedByPlayer(7) && isSelectedByPlayer(8) && (!isSelectedByAnyone(6))){
			return 6;
		}
		if(isSelectedByPlayer(6) && isSelectedByPlayer(8) && (!isSelectedByAnyone(7))){
			return 7;
		}

		// vertical
		if(isSelectedByPlayer(0) && isSelectedByPlayer(3) && (!isSelectedByAnyone(6))){
			return 6;
		}
		if(isSelectedByPlayer(3) && isSelectedByPlayer(6) && (!isSelectedByAnyone(0))){
			return 0;
		}
		if(isSelectedByPlayer(0) && isSelectedByPlayer(6) && (!isSelectedByAnyone(3))){
			return 3;
		}
		if(isSelectedByPlayer(1) && isSelectedByPlayer(4) && (!isSelectedByAnyone(7))){
			return 7;
		}
		if(isSelectedByPlayer(4) && isSelectedByPlayer(7) && (!isSelectedByAnyone(1))){
			return 1;
		}
		if(isSelectedByPlayer(1) && isSelectedByPlayer(7) && (!isSelectedByAnyone(4))){
			return 4;
		}
		if(isSelectedByPlayer(2) && isSelectedByPlayer(5) && (!isSelectedByAnyone(8))){
			return 8;
		}
		if(isSelectedByPlayer(5) && isSelectedByPlayer(8) && (!isSelectedByAnyone(2))){
			return 2;
		}
		if(isSelectedByPlayer(2) && isSelectedByPlayer(8) && (!isSelectedByAnyone(5))){
			return 5;
		}

		// diagonal
		if(isSelectedByPlayer(0) && isSelectedByPlayer(4) && (!isSelectedByAnyone(8))){
			return 8;
		}
		if(isSelectedByPlayer(4) && isSelectedByPlayer(8) && (!isSelectedByAnyone(0))){
			return 0;
		}
		if(isSelectedByPlayer(0) && isSelectedByPlayer(8) && (!isSelectedByAnyone(4))){
			return 4;
		}
		if(isSelectedByPlayer(2) && isSelectedByPlayer(4) && (!isSelectedByAnyone(6))){
			return 8;
		}
		if(isSelectedByPlayer(4) && isSelectedByPlayer(6) && (!isSelectedByAnyone(2))){
			return 2;
		}
		if(isSelectedByPlayer(2) && isSelectedByPlayer(6) && (!isSelectedByAnyone(4))){
			return 4;
		}

		return -1;
	}

	private boolean isSelectedByAI(int i) {
		if(((Rectangle)_squares.get(i)).getFill().equals(Color.BLUE)){
			return true;
		}
		else{
			return false;
		}
	}

	private boolean isSelectedByPlayer(int i) {
		if(((Rectangle)_squares.get(i)).getFill().equals(Color.RED)){
			return true;
		}
		else{
			return false;
		}
	}

	private boolean isSelectedByAnyone(int i) {
		if(!(((Rectangle)_squares.get(i)).getFill().equals(Color.WHITE))){
			return true;
		}
		else{
			return false;
		}
	}

	protected void handleMouseClick(Event event) {
		if(_playersTurn){
			MouseEvent me = (MouseEvent) event;
			double horizontal = me.getSceneX();
			double vertical = me.getSceneY();

			Integer num = -1;

			// first row
			Rectangle r = new Rectangle();
			if(horizontal <= 200 && vertical <= 200){
				r = (Rectangle)_squares.get(0);
				num = 0;
			}

			else if(horizontal <= 400 && vertical <= 200){
				r = (Rectangle)_squares.get(1);
				num = 1;
			}

			else if(horizontal <= 600 && vertical <= 200){
				r = (Rectangle)_squares.get(2);
				num = 2;
			}

			else if(horizontal <= 200 && vertical <= 400){
				r = (Rectangle)_squares.get(3);
				num = 3;
			}

			else if(horizontal <= 400 && vertical <= 400){
				r = (Rectangle)_squares.get(4);
				num = 4;
			}

			else if(horizontal <= 600 && vertical <= 400){
				r = (Rectangle)_squares.get(5);
				num = 5;
			}

			// third row
			else if(horizontal <= 200 && vertical <= 600){
				r = (Rectangle)_squares.get(6);
				num = 6;
			}

			else if(horizontal <= 400 && vertical <= 600){
				r = (Rectangle)_squares.get(7);
				num = 7;
			}

			else if(horizontal <= 600 && vertical <= 600){
				r = (Rectangle)_squares.get(8);
				num = 8;
			}
			if(r.getFill().equals(Color.WHITE)){
				r.setFill(Color.RED);
				_playersMoves.add(num);
				_turn++;
			}
			_playersTurn = false;
		}
	}

	private void initialize() {
		_app.getChildren().add(_game);


		Rectangle rect = new Rectangle(600,600, Color.GREY);
		_game.getChildren().add(rect);

		_squares.add(createEntity(10, 10, 180, 180, Color.WHITE));
		_squares.add(createEntity(210, 10, 180, 180, Color.WHITE));
		_squares.add(createEntity(410, 10, 180, 180, Color.WHITE));
		_squares.add(createEntity(10, 210, 180, 180, Color.WHITE));
		_squares.add(createEntity(210, 210, 180, 180, Color.WHITE));
		_squares.add(createEntity(410, 210, 180, 180, Color.WHITE));
		_squares.add(createEntity(10, 410, 180, 180, Color.WHITE));
		_squares.add(createEntity(210, 410, 180, 180, Color.WHITE));
		_squares.add(createEntity(410, 410, 180, 180, Color.WHITE));

	}

	private Node createEntity(int x, int y, int w, int h, Color colour) {
		Rectangle entity = new Rectangle(w, h);
		entity.setTranslateX(x);
		entity.setTranslateY(y);
		entity.setFill(colour);

		_game.getChildren().add(entity);
		return entity;
	}

}
