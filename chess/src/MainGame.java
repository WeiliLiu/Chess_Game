import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import chess.Chessboard;
import chess.Chesspiece;

/*
 * I got the template from http://www3.ntu.edu.sg/home/ehchua/programming/java/J8d_Game_Framework.html
 */
public class MainGame extends JPanel{
	Thread gameThread;
	
	// Enumeration for the states of the game
	static enum GameState{
		INITIALIZED, READY_TO_START, PLAYER1_TURN, PLAYER2_TURN, GAMEOVER, DESTROYED
	}
	static GameState state; //current state of the game
	
	// Define constants for the game
	static final String TITLE = "Chess";
	static final int CANVAS_WIDTH = 800;
	static final int CANVAS_HEIGHT = 800;
	
	// Define instance variables for the game objects
	protected Chessboard gameBoard;
	private Player player1;
	private Player player2;
	
	// Variables for controlling the game
	boolean gameOver = false;
	protected boolean isFirstButtonPressed = false;
	protected boolean isSecondButtonPressed = false;
	protected int originalX = -1;
	protected int originalY = -1;
	protected int destX = -1;
	protected int destY = -1;
	protected int doesPlayer1AgreeToRestart = 0;
	protected int doesPlayer2AgreeToRestart = 0;
	protected boolean startForfeit = false;
	protected boolean isPlayer1NameEntered = false;
	protected boolean isPlayer2NameEntered = false;
	protected boolean needComfirm = false;
	protected boolean isUndoInitiated = false;
	
	//Handle for the custom drawing panel
	private GameCanvas canvas;
	private StatusBoard statusBoard;
	
	public MainGame() {
		//Initialize the game objects
		gameInit();
		state = GameState.INITIALIZED;
		
		//UI components
		canvas = new GameCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		add(canvas);
		
		statusBoard = new StatusBoard();
		add(statusBoard);
		
		// Start the game here
		gameStart();
	}
	
	// ------ All the game related codes here ------
	
	/**
	 *  Initialize all the game objects, run only once.
	 */
	public void gameInit() {
		gameBoard = new Chessboard(8, 8);
		player1 = new Player(1);
		player2 = new Player(2);
	}
	
	/**
	 * Start and re-start the game.
	 */
	public void gameStart() {
		gameThread = new Thread() {
			@Override
			public void run() {
				gameLoop();
			}
		};
		gameThread.start();
	}
	
	/**
	 * This is the main game loop
	 */
	private void gameLoop() {
		while(isPlayer1NameEntered == false || isPlayer2NameEntered == false) {
			delayTime(50);
		}
		// Regenerate the game objects for a new game
		if(state == GameState.INITIALIZED || state == GameState.READY_TO_START || state == GameState.GAMEOVER) {
			refreshButtons();
			startForfeit = false;
			doesPlayer1AgreeToRestart = 0;
			doesPlayer2AgreeToRestart = 0;
			gameBoard = new Chessboard(8, 8);
			canvas.regenerateWindow();
			state = GameState.PLAYER1_TURN;
			isFirstButtonPressed = false;
			isSecondButtonPressed = false;
			needComfirm = false;
			isUndoInitiated = false;
		}
		
		//Game Loop
		while(state != GameState.GAMEOVER && state != GameState.READY_TO_START) {
			gameUpdate();
		}
		
		if(state == GameState.READY_TO_START) {
			gameLoop();
		}
		
		if(gameBoard.getWinner() == 1)
			statusBoard.addScoreRecord("Win", "Lose");
		else
			statusBoard.addScoreRecord("Lose", "Win");
		statusBoard.askPlayerRestart();
		while(doesPlayer1AgreeToRestart == 0 || doesPlayer2AgreeToRestart == 0) {
			delayTime(50);
		}
		
		if(doesPlayer1AgreeToRestart == 1 && doesPlayer2AgreeToRestart == 1) {
			gameLoop();
		}
	}
	
	private void refreshButtons() {
		this.isFirstButtonPressed = false;
		this.isSecondButtonPressed = false;
	}
	
	/**
	 * Updating the GUI as well as the data structures of the game
	 */
	public void gameUpdate() {
		if(state == GameState.PLAYER1_TURN) {
			player1TurnUpdate();
		}
		
		if(state == GameState.PLAYER2_TURN) {
			player2TurnUpdate();
		}
		
		if(startForfeit) {
			while(doesPlayer1AgreeToRestart == 0 || doesPlayer2AgreeToRestart == 0) {
				delayTime(50);
			}
			
			if(doesPlayer1AgreeToRestart == 1 && doesPlayer2AgreeToRestart == 1) {
				state = GameState.READY_TO_START;
				statusBoard.addScoreRecord("Tie", "Tie");
				refreshButtons();
			}
			startForfeit = false;
		}
		
	}

	/**
	 * This is a helper function to update the game when it is
	 * player2's turn
	 */
	private void player2TurnUpdate() {
		statusBoard.switchMessageBoard("Player 2 Turn!");
		while(!this.isFirstButtonPressed ) {
			if(startForfeit)
				break;
			delayTime(50);
		}
		while(!this.isSecondButtonPressed ) {
			if(startForfeit)
				break;
			delayTime(50);
		}
		
		if(startForfeit == false) {
			if(gameBoard.movePiece(2, originalX, originalY, destX, destY) == 0) {
				canvas.moveTileIcon(originalX, originalY, destX, destY);
				statusBoard.switchMessageBoard("Confirm your move!");
				needComfirm = true;
				while(needComfirm == true && isUndoInitiated == false) {
					delayTime(50);
				}
				
				if(isUndoInitiated == true) {
					state = GameState.PLAYER2_TURN;
					Chesspiece destPiece = gameBoard.reverseMove(originalX, originalY, destX, destY);
					canvas.reverseTileIcon(originalX, originalY, destX, destY, destPiece);
					isUndoInitiated = false;
				}else {
					if(gameBoard.hasGameEnded()) {
						state = GameState.GAMEOVER;
						statusBoard.switchMessageBoard("Game Over! Restart?");
					}
					else
						state = GameState.PLAYER1_TURN;
				}
				refreshButtons();
			}else {
				state = GameState.PLAYER2_TURN;
				refreshButtons();
				statusBoard.switchMessageBoard("Bad Move! Try again!");
				delayTime(1000);
			}
		}
	}

	/**
	 * This is a helper function to update the game when it is
	 * player1's turn
	 */
	private void player1TurnUpdate() {
		statusBoard.switchMessageBoard("Player 1 Turn!");
		while(!this.isFirstButtonPressed) {
			if(startForfeit)
				break;
			delayTime(50);
		}
		while(!this.isSecondButtonPressed) {
			if(startForfeit)
				break;
			delayTime(50);
		}
		
		if(startForfeit == false) {
			if(gameBoard.movePiece(1, originalX, originalY, destX, destY) == 0) {
				canvas.moveTileIcon(originalX, originalY, destX, destY);
				needComfirm = true;
				statusBoard.switchMessageBoard("Confirm your move!");
				while(needComfirm == true && isUndoInitiated == false) {
					delayTime(50);
				}
				
				if(isUndoInitiated == true) {
					state = GameState.PLAYER1_TURN;
					Chesspiece destPiece = gameBoard.reverseMove(originalX, originalY, destX, destY);
					canvas.reverseTileIcon(originalX, originalY, destX, destY, destPiece);
					isUndoInitiated = false;
				}else {
					if(gameBoard.hasGameEnded()) {
						state = GameState.GAMEOVER;
						statusBoard.switchMessageBoard("Game Over! Restart?");
					}
					else
						state = GameState.PLAYER2_TURN;
				}
				refreshButtons();
			}else {
				state = GameState.PLAYER1_TURN;
				refreshButtons();
				statusBoard.switchMessageBoard("Bad Move! Try again!");
				delayTime(1000);
			}
		}
	}
	
	/**
	 * This is a helper function to help delay the 
	 * excution of the program for a certain
	 * amount of time
	 * @param time - amount of time to be delayed
	 */
	private void delayTime(int time) {
		try {
		    Thread.sleep(time);
		}
		catch (InterruptedException e) {}
	}
	
	/*
	 * This is a jpanel that will display all game related information
	 * on the GUI
	 */
	class StatusBoard extends JPanel implements ActionListener{
		JPanel messageBoard;
		JPanel playerBoard;
		JPanel player1Board;
		JPanel player2Board;
		JPanel buttonPanel;
		JPanel buttonCard1;
		JPanel buttonCard2;
		JPanel buttonCard3;
		JButton forfeitButton;
		JButton player1AgreeButton;
		JButton player1DisagreeButton;
		JButton player2AgreeButton;
		JButton player2DisagreeButton;
		JButton player1EnterNameButton;
		JButton player2EnterNameButton;
		JPanel player2Name;
		JTextField player2TextField;
		JPanel player1Name;
		JTextField player1TextField;
		JButton undoButton;
		JButton confirmButton;
		
		public StatusBoard(){
			super();
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			this.setPreferredSize(new Dimension(500, CANVAS_HEIGHT));
			
			/*
			 * Setting up the 3 sub-boards
			 */
			setupMessageBoard();
			
			setupPlayerBoard();
			
			setupButtonPanel();
		}

		/**
		 * helper function to setup the JPanel that contains the buttons
		 */
		private void setupButtonPanel() {
			buttonPanel = new JPanel(new CardLayout());
			buttonPanel.setBackground(Color.yellow);
			buttonPanel.setMaximumSize(new Dimension(500, 100));
			this.add(buttonPanel);
			
			setupButtonCard1();
			
			setupButtonCard2();
			
			setupButtonCard3();
			
			buttonPanel.add(buttonCard1, "card 1");
			buttonPanel.add(buttonCard2, "card 2");
			buttonPanel.add(buttonCard3, "card 3");
		}

		/**
		 * Set up the panel that ask for approval from player2
		 */
		private void setupButtonCard3() {
			buttonCard3 = new JPanel();
			player2AgreeButton = new JButton("Yes");
			player2DisagreeButton = new JButton("No");
			player2AgreeButton.addActionListener(this);
			player2DisagreeButton.addActionListener(this);
			JLabel queryPlayer2 = new JLabel();
			queryPlayer2.setText("Does player 2 agree to restart?");
			queryPlayer2.setFont(new Font("Arial", 0, 20));
			buttonCard3.add(queryPlayer2);
			buttonCard3.add(player2AgreeButton);
			buttonCard3.add(player2DisagreeButton);
		}

		/**
		 * Set up the panel that ask for approval from player1
		 */
		private void setupButtonCard2() {
			buttonCard2 = new JPanel();
			player1AgreeButton = new JButton("Yes");
			player1DisagreeButton = new JButton("No");
			player1AgreeButton.addActionListener(this);
			player1DisagreeButton.addActionListener(this);
			JLabel queryPlayer1 = new JLabel();
			queryPlayer1.setText("Does player 1 agree to restart?");
			queryPlayer1.setFont(new Font("Arial", 0, 20));
			buttonCard2.add(queryPlayer1);
			buttonCard2.add(player1AgreeButton);
			buttonCard2.add(player1DisagreeButton);
		}

		/**
		 * Set up the panel that contains the forfeit, undo and confirm buttons
		 */
		private void setupButtonCard1() {
			buttonCard1 = new JPanel();
			forfeitButton = new JButton("Forfeit");
			forfeitButton.addActionListener(this);
			undoButton = new JButton("Undo");
			undoButton.addActionListener(this);
			confirmButton = new JButton("Confirm");
			confirmButton.addActionListener(this);
			buttonCard1.add(forfeitButton);
			buttonCard1.add(undoButton);
			buttonCard1.add(confirmButton);
		}

		/**
		 * Set up the board that contains the player information
		 * and scoreboard
		 */
		private void setupPlayerBoard() {
			playerBoard = new JPanel(new GridLayout(0, 2));
			setupPlayer1Board();
		    
			setupPlayer2Board();
			this.add(playerBoard);
		}

		/**
		 * Set up the sub-board for player2's information and
		 * scores
		 */
		private void setupPlayer2Board() {
			player2Board = new JPanel(new FlowLayout());
			player2Board.setLayout(new BoxLayout(player2Board, BoxLayout.Y_AXIS));
			JLabel player2info = new JLabel();
			player2info.setText("Player 2:");
		    player2info.setFont(new Font("Arial", 0, 40));
		    player2Board.add(player2info);
		    
		    player2Name = new JPanel();
		    player2Name.setMaximumSize(new Dimension(500,50));
		    player2Name.setLayout(new BoxLayout(player2Name, BoxLayout.X_AXIS));
		    player2TextField = new JTextField();
		    player2TextField.setText("Enter name");
		    player2TextField.setFont(new Font("Arial", 0, 20));
		    player2Name.add(player2TextField);
		    player2EnterNameButton = new JButton("Submit");
		    player2EnterNameButton.addActionListener(this);
		    player2Name.add(player2EnterNameButton);

		    player2Board.add(player2Name);
			playerBoard.add(player2Board);
		}

		/**
		 * Set up the sub-board for player1's information and
		 * scores
		 */
		private void setupPlayer1Board() {
			player1Board = new JPanel();
			player1Board.setLayout(new BoxLayout(player1Board, BoxLayout.Y_AXIS));
			JLabel player1info = new JLabel();
			player1info.setText("Player 1:");
		    player1info.setFont(new Font("Arial", 0, 40));
		    player1Board.add(player1info);

		    player1Name = new JPanel();
		    player1Name.setMaximumSize(new Dimension(500,50));
		    player1Name.setLayout(new BoxLayout(player1Name, BoxLayout.X_AXIS));
		    player1TextField = new JTextField();
		    player1TextField.setText("Enter name");
		    player1TextField.setFont(new Font("Arial", 0, 20));
		    player1Name.add(player1TextField);
		    player1EnterNameButton = new JButton("Submit");
		    player1EnterNameButton.addActionListener(this);
		    player1Name.add(player1EnterNameButton);
		    
		    player1Board.add(player1Name);
		    playerBoard.add(player1Board);
		}

		/**
		 * Set up the message board that will give the players
		 * useful instructions
		 */
		private void setupMessageBoard() {
			messageBoard = new JPanel();
			messageBoard.setBackground(Color.white);
			JLabel label4 = new JLabel();
		    label4.setText("Player 1 turn!");
		    label4.setFont(new Font("Arial", 0, 50));
		    messageBoard.add(label4);
		    messageBoard.setMaximumSize(new Dimension(500, 100));
			this.add(messageBoard);
		}
		
		/**
		 * This function is used to update the scoreboard when a 
		 * game has ended
		 * 
		 * @param player1Record - outcome of the game for player1
		 * @param player2Record - outcome of the game for player2
		 */
		public void addScoreRecord(String player1Record, String player2Record) {
			JLabel player1Result = new JLabel();
			player1Result.setLayout(new FlowLayout());
			player1Result.setText(player1Record);
		    player1Result.setFont(new Font("Arial", 0, 30));
		    this.player1Board.add(player1Result);
		    
		    JLabel player2Result = new JLabel();
		    player2Result.setLayout(new FlowLayout());
			player2Result.setText(player2Record);
		    player2Result.setFont(new Font("Arial", 0, 30));
		    this.player2Board.add(player2Result);
		}
		
		/**
		 * This function is used to initiate the query for restart
		 * for both players
		 */
		public void askPlayerRestart() {
			CardLayout cardLayout = (CardLayout) buttonPanel.getLayout();
			cardLayout.show(buttonPanel, "card 2");
		}
		
		/**
		 * This function is used to change the message to be shown
		 * on the messageboard
		 * 
		 * @param message - message to be displayed
		 */
		public void switchMessageBoard(String message) {
			this.messageBoard.removeAll();
			repaintBoard(message);
		}
		
		/**
		 * This is a helper function to help repaint the message board
		 * 
		 * @param message - message to be displayed
		 */
		private void repaintBoard(String message) {
			JLabel messageLabel = new JLabel();
			messageLabel.setText(message);
			messageLabel.setFont(new Font("Arial", 0, 50));
		    messageBoard.add(messageLabel);
		    messageBoard.revalidate();
		    messageBoard.repaint();
		}
		
		/**
		 * This function is used to add an additional message to the message board
		 * 
		 * @param message - message to be added
		 */
		public void addToMessageBoard(String message) {
			repaintBoard(message);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) buttonPanel.getLayout();
			if(e.getSource() == forfeitButton) {
				startForfeit = true;
				cardLayout.show(buttonPanel, "card 2");
			}
			
			if(e.getSource() == player1AgreeButton) {
				doesPlayer1AgreeToRestart = 1;
				cardLayout.show(buttonPanel, "card 3");
			}
			
			if(e.getSource() == player1DisagreeButton) {
				doesPlayer1AgreeToRestart = -1;
				cardLayout.show(buttonPanel, "card 3");
			}
			
			if(e.getSource() == player2AgreeButton) {
				doesPlayer2AgreeToRestart = 1;
				cardLayout.show(buttonPanel, "card 1");
			}
			
			if(e.getSource() == player2DisagreeButton) {
				doesPlayer2AgreeToRestart = -1;
				cardLayout.show(buttonPanel, "card 1");
			}
			
			if(e.getSource() == player2EnterNameButton) {
				String name = player2TextField.getText();
				player2.setName(name);
				player2Board.remove(player2Name);
				JLabel newName = new JLabel();
				newName.setText(name);
				newName.setFont(new Font("Arial", 0, 40));
				player2Board.add(newName);
				player2Board.revalidate();
				player2Board.repaint();
				isPlayer2NameEntered = true;
			}
			
			if(e.getSource() == player1EnterNameButton) {
				String name = player1TextField.getText();
				player1.setName(name);
				player1Board.remove(player1Name);
				JLabel newName = new JLabel();
				newName.setText(name);
				newName.setFont(new Font("Arial", 0, 40));
				player1Board.add(newName);
				player1Board.revalidate();
				player1Board.repaint();
				isPlayer1NameEntered = true;
			}
			
			if(e.getSource() == confirmButton) {
				needComfirm = false;
			}
			
			if(e.getSource() == undoButton && needComfirm == true) {
				isUndoInitiated = true;
			}
			
		}
	}
	
	/*
	 * This is the class that contains the game board
	 */
	class GameCanvas extends JPanel{
		BoardTiles[][] gameCanvas;
		boolean buttonPressed = false;
		// Constructor
		public GameCanvas() {
			super(new GridLayout(8, 8));
			this.setBackground(Color.red);
			this.gameCanvas = new BoardTiles[8][];
			for(int i = 0; i < 8; i++) {
				this.gameCanvas[i] = new BoardTiles[8];
			}
			GridBagConstraints c = new GridBagConstraints();
			for(int i = 0; i < 64; i++) {
				int row = i / 8;
				int col = i % 8;
				final BoardTiles gameTile = new BoardTiles(row, col);
				this.gameCanvas[row][col] = gameTile;
				add(gameTile, c);
			}
		}
		
		/**
		 * This is the function to help change the tile icons after
		 * a successful movement
		 * 
		 * @param origX - original row of the piece that was moved
		 * @param origY - original col of the piece that was moved
		 * @param destX - new row  of the piece that was moved
		 * @param destY - new col of the piece that was moved
		 */
		public void moveTileIcon(int origX, int origY, int destX, int destY) {
			BoardTiles originalTile = gameCanvas[origX][origY];
			BoardTiles destTile = gameCanvas[destX][destY];
			Icon origIcon = originalTile.getIcon();
			originalTile.setIcon(null);
			destTile.setIcon(origIcon);
		}
		
		/**
		 * This is a function that helps to reverse the icon change
		 * for an undo operation
		 * 
		 * @param origX - original row of the piece that was moved
		 * @param origY - original col of the piece that was moved
		 * @param destX - new row  of the piece that was moved
		 * @param destY - new col of the piece that was moved
		 * @param destPiece - original chesspiece that was on the destination
		 */
		public void reverseTileIcon(int origX, int origY, int destX, int destY, Chesspiece destPiece) {
			BoardTiles originalTile = gameCanvas[origX][origY];
			BoardTiles destTile = gameCanvas[destX][destY];
			Icon origIcon = destTile.getIcon();
			originalTile.setIcon(origIcon);
			if(destPiece == null) {
				destTile.setIcon(null);
			}else {
				try {
					Image image = ImageIO.read(new File("images/" + destPiece.toIconPath()));
					destTile.setIcon(new ImageIcon(image));
				}catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		
		/**
		 * This function is used to regenerate a new chessboard GUI
		 * when the game is restarted
		 */
		public void regenerateWindow() {
			for(int i = 0; i < 64; i++) {
				int row = i / 8;
				int col = i % 8;
				gameCanvas[row][col].setIcon(null);
			}
			
			for(int i = 0; i < 64; i++) {
				int row = i / 8;
				int col = i % 8;
				gameCanvas[row][col].setTileIcon(row, col, gameBoard);
			}
		}
	}
	
	public class BoardTiles extends JButton implements ActionListener{
		private int row;
		private int col;
		private boolean isButtonPressed = false;
		
		public BoardTiles(int row, int col) {
			this.row = row;
			this.col = col;
			this.setTileBackground(row, col);
			this.setTileIcon(row, col, gameBoard);
			this.addActionListener(this);
			this.setPreferredSize(new Dimension(100, 100));
			this.validate();
		}
		
		public int getCol() {
			return this.col;
		}
		
		public int getRow() {
			return this.row;
		}
		
		/**
		 * This function is used to set up icon for each tile on the
		 * game board
		 * 
		 * @param row - row of the tile on the game board
		 * @param col - col of the tile on the game board
		 * @param inputBoard - the game board
		 */
		public void setTileIcon(int row, int col, Chessboard inputBoard) {
			this.removeAll();
			if(inputBoard.getPiece(row, col) != null) {
				try {
					Image image = ImageIO.read(new File("images/" + inputBoard.getPiece(row, col).toIconPath()));
					this.setIcon(new ImageIcon(image));
				}catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		
		/**
		 * This function is used to give the tile a background color
		 * based on its position on the game board
		 * 
		 * @param row - row of the tile on the game board
		 * @param col - col of the tile on the game board
		 */
		public void setTileBackground(int row, int col) {
			if(row % 2 == 0) {
				this.setBackground(col % 2 == 0 ? new Color(255,206,158) : new Color(209,139,71));
			}else {
				this.setBackground(col % 2 == 0 ? new Color(209,139,71) : new Color(255,206,158));
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(isFirstButtonPressed == false) {
				isFirstButtonPressed = true;
				originalX = this.row;
				originalY = this.col;
			}
			else {
				isSecondButtonPressed = true;
				destX = this.row;
				destY = this.col;
			}
		}
	}
	
	//main
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame(TITLE);
				// Set the content-pane of the JFrame to an instance of main JPanel
				frame.setContentPane(new MainGame());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}