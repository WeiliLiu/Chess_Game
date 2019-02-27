package chess;
public class Pawn extends Chesspiece{
	private int rowOnBoard;
	private int colOnBoard;
	
	/*
	 * Member variable that keeps track of 
	 * whether a Pawn has been moved before
	 */
	private int moveCount;
	
	/**
	 * Constructor of the Pawn class
	 * 
	 * @param player - player this Pawn belongs to
	 */
	public Pawn(int player, int row, int col) {
		super(player, 6);
		this.rowOnBoard = row;
		this.colOnBoard = col;
	}
	
	/**
	 * Getting the row of the piece on the board
	 */
	public int getX() {
		return this.rowOnBoard;
	}
	
	/**
	 * Getting the col of the piece on the board
	 */
	public int getY() {
		return this.colOnBoard;
	}
	
	/**
	 * Setting the row of the piece on the board
	 */
	public void setX(int row) {
		this.rowOnBoard = row;
	}
	
	/**
	 * Setting the col of the piece on the board
	 */
	public void setY(int col) {
		this.colOnBoard = col;
	}
	
	/**
	 * Return the relative path to the images corresponding to this chesspiece
	 */
	public String toIconPath() {
		if(this.getOwnership() == 1)
			return "black-pawn.png";
		else
			return "white-pawn.png";
	}
	
	/**
	 * This function sets hasMoved to 1
	 * 
	 * @param none
	 * @return none
	 */
	public void incrementMoveCount() {
		this.moveCount += 1;
	}
	
	public void decrementMoveCount() {
		this.moveCount -= 1;
	}
	
	/**
	 * This function returns the value of hasMoved
	 * 
	 * @param none
	 * @return none
	 */
	public int getMoveCount() {
		return this.moveCount; 
	}
	
	/**
	 * This function will be called by Chessboard class
	 * to check if the instructed move is possible for 
	 * a Pawn class
	 * 
	 * @param inputboard - the current chess board
	 * @param pieceCurPos_x - original row of the piece
	 * @param pieceCurPos_y - original col of the piece
	 * @param pieceDest_x - destination row of the piece
	 * @param pieceDest_y - destination col of the piece
	 * 
	 * @return 0 - indicate success, -1 - indicate failure
	 */
	public int isValidMove(Chesspiece[][] inputBoard,
			int pieceCurPos_x,
			int pieceCurPos_y,
			int pieceDest_x,
			int pieceDest_y)
	{
		/*
		 * Make sure the destination is reachable by a Pawn
		 */
		int difference_y = pieceDest_y - pieceCurPos_y;
		
		/*
		 * The only scenario that difference_y is not 0 is when 
		 * then pawn captures a piece diagonally
		 */
		if(difference_y != 0)
			return checkDiagonal(this.getOwnership(), inputBoard, pieceCurPos_x, 
					pieceCurPos_y, pieceDest_x, pieceDest_y);
		
		else			
			return checkForward(this.getOwnership(), inputBoard, pieceCurPos_x, 
					pieceCurPos_y, pieceDest_x, pieceDest_y);
		
	}
	
	/*
	 * Helper functions
	 */
	
	/**
	 * This function checks if the movement is valid 
	 * for any forward movements
	 * 
	 * @param player - player the piece belongs to
	 * @param inputboard - the current chess board
	 * @param pieceCurPos_x - original row of the piece
	 * @param pieceCurPos_y - original col of the piece
	 * @param pieceDest_x - destination row of the piece
	 * @param pieceDest_y - destination col of the piece
	 * @return 0 - success, -1 - failure
	 */
	private int checkForward(int player, 
			Chesspiece[][] inputBoard,
			int pieceCurPos_x,
			int pieceCurPos_y,
			int pieceDest_x,
			int pieceDest_y)
	{
		int difference_x = pieceDest_x - pieceCurPos_x;
		
		/*
		 * First time the pawn has moved
		 */
		if(this.moveCount == 0) {
			/*
			 * For Player 1
			 */
			if(player == 1) {
				/*
				 * Not allowed to move more than 2 squares for first move
				 */
				if(difference_x != 2 && difference_x != 1)
					return -1;
				
				else if(difference_x == 1) {
					if(inputBoard[pieceDest_x][pieceDest_y] != null)
						return -1;
				}
				
				else {
					if(inputBoard[pieceDest_x][pieceDest_y] != null || 
					   inputBoard[pieceDest_x - 1][pieceDest_y] != null)
						return -1;
				}
			}
			
			/*
			 * For Player 2
			 */
			else {
				/*
				 * Not allowed to move more than 2 squares for first move
				 */
				if(difference_x != -2 && difference_x != -1)
					return -1;
				
				else if(difference_x == -1) {
					if(inputBoard[pieceDest_x][pieceDest_y] != null)
						return -1;
				}
				
				else {
					if(inputBoard[pieceDest_x][pieceDest_y] != null || 
					   inputBoard[pieceDest_x + 1][pieceDest_y] != null)
						return -1;
				}
			}
		}
		
		/*
		 * Not the first time
		 */
		else {
			/*
			 * For player 1
			 */
			if(player == 1) {
				if(difference_x != 1)
					return -1;
				else if(inputBoard[pieceDest_x][pieceDest_y] != null)
					return -1;
			}
			
			/*
			 * For Player 2
			 */
			else {
				if(difference_x != -1)
					return -1;
				else if(inputBoard[pieceDest_x][pieceDest_y] != null)
					return -1;
			}
		}
		
		/*
		 * If everything checks out, return 0 to indicate success
		 */
		return 0;
	}
	
	/**
	 * This function checks if the movement is valid 
	 * for any diagonal movements
	 * 
	 * @param player - player the piece belongs to
	 * @param inputboard - the current chess board
	 * @param pieceCurPos_x - original row of the piece
	 * @param pieceCurPos_y - original col of the piece
	 * @param pieceDest_x - destination row of the piece
	 * @param pieceDest_y - destination col of the piece
	 * @return 0 - success, -1 - failure
	 */
	private int checkDiagonal(int player, 
			Chesspiece[][] inputBoard,
			int pieceCurPos_x,
			int pieceCurPos_y,
			int pieceDest_x,
			int pieceDest_y) 
	{
		int difference_x = pieceDest_x - pieceCurPos_x;
		int difference_y = pieceDest_y - pieceCurPos_y;
		
		/*
		 * First check if the destination is a valid diagonal
		 * movement based on player number
		 */
		if(player == 1) {
			if(!(difference_x == 1 && Math.abs(difference_y) == 1))
				return -1;
		}
		
		else {
			if(!(difference_x == -1 && Math.abs(difference_y) == 1))
				return -1;
		}
		
		/*
		 * Check if the destination has a piece that belongs to 
		 * the opponent
		 */
		if(inputBoard[pieceDest_x][pieceDest_y] == null ||
				inputBoard[pieceDest_x][pieceDest_y].getOwnership() == player)
			return -1;
		
		return 0;
	}
	
}