package chess;


public class Chessboard
{
	/**
	 * Member variables for this class
	 */
	private int boardWidth;
	private int boardHeight;
	private Chesspiece[][] board;
	private Boolean isGameEnd;
	private Boolean isKingInCheck;
	private Chesspiece[] player1Pawns;
	private Chesspiece[] player2Pawns;
	private Chesspiece[] player1Rooks;
	private Chesspiece[] player2Rooks;
	private Chesspiece[] player1Knights;
	private Chesspiece[] player2Knights;
	private Chesspiece[] player1Bishops;
	private Chesspiece[] player2Bishops;
	private Chesspiece player1Queen;
	private Chesspiece player2Queen;
	private Chesspiece player1King;
	private Chesspiece player2King;
	private Chesspiece player1Empress;
	private Chesspiece player2Empress;
	private Chesspiece player1Hopper;
	private Chesspiece player2Hopper;
	private Chesspiece lastDestPiece = null;
	private int winner;
	
	public Chesspiece reverseMove(int origX, int origY, int destX, int destY) {
		board[origX][origY] = board[destX][destY];
		board[destX][destY] = lastDestPiece;
		isGameEnd = false;
		if(board[origX][origY].getType() == 6)
			board[origX][origY].decrementMoveCount();
		return lastDestPiece;
	}
	
	/**
	 * This function gets the width of the chess board
	 *
	 * @param none
	 * @return width of the board
	**/
	public int getWidth() {
		return this.boardWidth;
	}
	
	/**
	 * This function gets the height of the chess board
	 *
	 * @param none
	 * @return height of the board
	**/
	public int getHeight() {
		return this.boardHeight;
	}
	
	public int getWinner() {
		return this.winner;
	}
	
	/**
	 * This function is the constructor of the class Chessboard
	 * 
	 * Note: This constructor makes sure all pieces of both players
	 * are placed in their initial positions according to chess
	 * rule
	 * 
	 * @param width - width of the chess board
	 * @param height - height of the chess board
	 */
	public Chessboard(int width, int height) 
	{
		this.boardWidth = width;
		this.boardHeight = height;
		this.isGameEnd = false;
		this.isKingInCheck = false;
		
		this.board = new Chesspiece[height][];
		for(int row = 0; row < height; row++)
			this.board[row] = new Chesspiece[width];
		
		/*
		 * Set up the initial board based on chess rules
		 * 1. Put pawns for player 1 on row 1 and those for player 2 on row 6
		 */
		player1Pawns = new Pawn[width];
		player2Pawns = new Pawn[width];

		for(int col = 0; col < width; col++) {
			player1Pawns[col] = new Pawn(1, 1, col);
			player2Pawns[col] = new Pawn(2, 6, col);
			this.board[1][col] = player1Pawns[col];
			this.board[6][col] = player2Pawns[col];
		}
		
		player1Hopper = new Hopper(1, 1, 4);
		player2Hopper = new Hopper(2, 6, 3);
		this.board[1][4] = player1Hopper;
		this.board[6][3] = player2Hopper;
		
		/*
		 * 2. Put Rooks in the four corners
		 */
		player1Rooks = new Rook[2];
		player2Rooks = new Rook[2];
		
		player1Rooks[0] = new Rook(1, 0, 0);
		player1Rooks[1] = new Rook(1, 0, width - 1);
		player2Rooks[0] = new Rook(2, 7, 0);
		player2Rooks[1] = new Rook(2, 7, width - 1);
		
		this.board[0][0] = player1Rooks[0];
		this.board[0][width - 1] = player1Rooks[1];
		this.board[7][0] = player2Rooks[0];
		this.board[7][width - 1] = player2Rooks[1];
		
		/*
		 * 3. Place the knights next to the rooks. 
		 */
		player1Knights = new Knight[2];
		player2Knights = new Knight[2];
		
		player1Knights[0] = new Knight(1, 0, 1);
		player1Knights[1] = new Knight(1, 0, width - 2);
		player2Knights[0] = new Knight(2, height - 1, 1);
		player2Knights[1] = new Knight(2, height - 1, width - 2);
		
		player1Empress = new Empress(1, 0, 1);
		player2Empress = new Empress(2, height - 1, width - 2);
		
		this.board[0][1] = player1Empress;
		this.board[0][width - 2] = player1Knights[1];
		this.board[height - 1][1] = player2Knights[0];
		this.board[height - 1][width - 2] = player2Empress;
		
		/*
		 * 4. Place Bishops next to Knights
		 */
		player1Bishops = new Bishop[2];
		player2Bishops = new Bishop[2];
		
		player1Bishops[0] = new Bishop(1, 0, 2);
		player1Bishops[1] = new Bishop(1, 0, width - 3);
		player2Bishops[0] = new Bishop(2, height - 1, 2);
		player2Bishops[1] = new Bishop(2, height - 1, width - 3);
		
		this.board[0][2] = player1Bishops[0];
		this.board[0][width - 3] = player1Bishops[1];
		this.board[height - 1][2] = player2Bishops[0];
		this.board[height - 1][width - 3] = player2Bishops[1];
		
		/*
		 * 5. Place the two queens
		 */
		player1Queen = new Queen(1, 0, 3);
		player2Queen = new Queen(2, height - 1, 3);
		this.board[0][3] = player1Queen;
		this.board[height - 1][3] = player2Queen;
		
		/*
		 * 6. Place the two kings 
		 */
		player1King = new King(1, 0, 4);
		player2King = new King(2, height - 1, 4);
		this.board[0][4] = player1King;
		this.board[height - 1][4] = player2King;

	}
	
	public Chesspiece getKing(int player) {
		if(player == 1)
			return this.player1King;
		else
			return this.player2King;
	}
	
	public Boolean getCheckCondition() {
		return this.isKingInCheck;
	}
	
	/**
	 * This function is used to access a specific chess piece 
	 * on the chess board
	 * 
	 * @param row - row number of the chess piece
	 * @param col - col number of the chess piece
	 * @return the chesspiece at location (row, col)
	 */
	public Chesspiece getPiece(int row, int col) {
		return this.board[row][col];
	}
	
	/**
	 * This function allows a player to move one of his own
	 * piece on the board to a location allowed by the chess
	 * rule
	 * 
	 * @param fromPlayer - player that makes the move
	 * @param pieceCurPos_x - original row of the piece
	 * @param pieceCurPos_y - original col of the piece
	 * @param pieceDest_x - destination row of the piece
	 * @param pieceDest_y - destination col of the piece
	 * @return 0 - success, -1 - failure
	 * Note: if the function returns success, it will also make
	 * changes to the chess board
	 */
	public int movePiece(int fromPlayer,
			int pieceCurPos_x, 
			int pieceCurPos_y,
			int pieceDest_x,
			int pieceDest_y) 
	{
		/*
		 * First check if the move instructed by the player is valid
		 */
		if(this.isValidMove(fromPlayer, pieceCurPos_x, pieceCurPos_y, 
				pieceDest_x, pieceDest_y) == -1)
			return -1;
		
		if(this.board[pieceCurPos_x][pieceCurPos_y].getType() == 4)
			if(this.isKingInCheck(3 - fromPlayer, pieceCurPos_x, pieceCurPos_y, 
					pieceDest_x, pieceDest_y) == -1)
			{
				return -1;
			}else {
				this.board[pieceCurPos_x][pieceCurPos_y].setX(pieceDest_x);
				this.board[pieceCurPos_x][pieceCurPos_y].setY(pieceDest_y);
			}
		

		/*
		 * if valid, then start modifying the board:
		 * step 1: setting the original position to be null
		 * step 2: check if the destination has a piece that belongs
		 * 		   to the opponent, if yes, check if it is the king,
		 * 		   set the game end condition to be true if yes, 
		 * 		   continue to step 3 if it is not the king
		 * step 3: set the destination to contain the chess piece
		 * 		   that is being moved
		 */
		if(this.board[pieceCurPos_x][pieceCurPos_y].getType() == 6)
			this.board[pieceCurPos_x][pieceCurPos_y].incrementMoveCount();
		
		if(this.board[pieceDest_x][pieceDest_y] != null)
			if(this.board[pieceDest_x][pieceDest_y].getType() == 4) {
				this.isGameEnd = true;
				this.winner = fromPlayer;
			}
		
		lastDestPiece = this.board[pieceDest_x][pieceDest_y];
		this.board[pieceDest_x][pieceDest_y] = this.board[pieceCurPos_x][pieceCurPos_y];
		this.board[pieceCurPos_x][pieceCurPos_y] = null;
		
		/*
		 * return 0 to indicate piece sucessfully moved
		 */
		return 0;
	}
	
	/**
	 * This function allows other class to know whether 
	 * the game has ended
	 * 
	 * @param none
	 * @return the member variable of the class isGameEnd
	 */
	public Boolean hasGameEnded() {
		return this.isGameEnd;
	}
	
	/*
	 * Helper Functions
	 */
	
	/**
	 * This is a helper function used by move piece to check
	 * the destination instructed by the player is valid
	 * from the original position
	 * 
	 * @param fromPlayer - player that makes the move
	 * @param pieceCurPos_x - original row of the piece
	 * @param pieceCurPos_y - original col of the piece
	 * @param pieceDest_x - destination row of the piece
	 * @param pieceDest_y - destination col of the piece
	 * @return 0 - success, -1 - failure
	 */
	private int isValidMove(int fromPlayer,
			int pieceCurPos_x,
			int pieceCurPos_y,
			int pieceDest_x,
			int pieceDest_y)
	{
		/*
		 * Check if the starting position is actually on the board
		 */
		if(pieceCurPos_x < 0 || pieceCurPos_x >= this.boardWidth || 
				   pieceCurPos_y < 0 || pieceCurPos_y >= this.boardHeight)
					return -1;
		
		/*
		 * Check if the destination position is actually on the board
		 */
		if(pieceDest_x < 0 || pieceDest_x >= this.boardWidth || 
		   pieceDest_y < 0 || pieceDest_y >= this.boardHeight)
			return -1;
		
		/*
		 * Check if there is a piece on the current position
		 */
		if(this.board[pieceCurPos_x][pieceCurPos_y] == null)
			return -1;
		
		/*
		 * Then check if the piece on the current position belong to the 
		 * current player
		 */
		if(fromPlayer != this.board[pieceCurPos_x][pieceCurPos_y].getOwnership())
			return -1;
		
		/*
		 * Make sure the destination is not the same as the
		 * original position
		 */
		if(pieceCurPos_x == pieceDest_x && pieceCurPos_y == pieceDest_y)
			return -1;
		
		/*
		 * Check if the destination position has a piece of the same player, 
		 * not valid move if yes
		 */
		if(this.board[pieceDest_x][pieceDest_y] != null && 
				this.board[pieceCurPos_x][pieceCurPos_y].getOwnership() == 
				this.board[pieceDest_x][pieceDest_y].getOwnership())
			return -1;
		
		/*
		 * Check if the destination is possible based on the type of 
		 * the chess piece being moved
		 */
		if(board[pieceCurPos_x][pieceCurPos_y].isValidMove(this.board, pieceCurPos_x, 
				pieceCurPos_y, pieceDest_x, pieceDest_y) == -1) {
			return -1;
		}
		
		/*
		 * When everything checks out, return 0 to indicate success
		 */
		return 0;
	}
	
	/**
	 * This is a helper function used by movePiece to check whether 
	 * the destination the king is going to will make itself in
	 * danger
	 * 
	 * @param fromPlayer - player making the instruction
	 * @param kingCurX - king's original row
	 * @param kingCurY - king's original column
	 * @param kingDestX - king's destination row
	 * @param kingDestY - king's destination column
	 * @return 0 - king is not in danger, -1 - king is in danger
	 */
	private int isKingInCheck(int fromPlayer, 
			int kingCurX, 
			int kingCurY, 
			int kingDestX, 
			int kingDestY) {
		
		/*
		 * This for loop is doing an exhaustive check on the board
		 * if it is possible for any piece on the board to reach
		 * the king's destination position next turn
		 */
		Chesspiece oldPiece = this.board[kingDestX][kingDestY];
		this.board[kingDestX][kingDestY] = this.board[kingCurX][kingCurY];
		this.board[kingCurX][kingCurY] = null;
		
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if(row == kingCurX && col == kingCurY)
					continue;
				if(row == kingDestX && col == kingDestY)
					continue;
				if(this.isValidMove(fromPlayer, row, col, kingDestX, kingDestY) == 0) {
					this.board[kingCurX][kingCurY] = this.board[kingDestX][kingDestY];
					this.board[kingDestX][kingDestY] = oldPiece;
					return -1;
				}
			}
		}
		
		this.board[kingCurX][kingCurY] = this.board[kingDestX][kingDestY];
		this.board[kingDestX][kingDestY] = oldPiece;
		return 0;
	}
	
	/**
	 * This is function for the main game loop to check whether any player 
	 * is being checkmate
	 * 
	 * @param player
	 * @param kingCurPosX
	 * @param kingCurPosY
	 * @return Boolean indicating whether the player is being checkmate
	 */
	public Boolean isCheckmate(int player, int kingCurPosX, int kingCurPosY) {
		for(int row = kingCurPosX - 1; row <= kingCurPosX + 1; row++) {
			for(int col = kingCurPosY - 1; col <= kingCurPosY + 1; col++) {
				if(this.isValidMove(player, kingCurPosX, kingCurPosY, 
						row, col) == 0 && this.isKingInCheck(3 - player, kingCurPosX, 
								kingCurPosY, row, col) == 0) {
					return false;
				}
				
			}
		}
		return true;
	}
	
}