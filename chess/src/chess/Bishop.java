package chess;

public class Bishop extends Chesspiece{
	private int rowOnBoard;
	private int colOnBoard;
	
	/**
	 * Constructor of the Bishop class
	 * 
	 * @param player - player this Bishop belongs to
	 */
	public Bishop(int player, int row, int col) {
		super(player, 2);
		this.rowOnBoard = row;
		this.colOnBoard = col;
	}
	
	public int getX() {
		return this.rowOnBoard;
	}
	
	public int getY() {
		return this.colOnBoard;
	}
	
	public void setX(int row) {
		this.rowOnBoard = row;
	}
	
	public void setY(int col) {
		this.colOnBoard = col;
	}
	
	public String toIconPath() {
		if(this.getOwnership() == 1)
			return "black-bishop.png";
		else
			return "white-bishop.png";
	}
	
	/**
	 * This function will be called by Chessboard class
	 * to check if the instructed move is possible for 
	 * a Bishop class
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
		 * Make sure the destination is reachable by a Bishop
		 * Note: Since the bishop can only move diagonally, the 
		 * difference in x and the difference in y should be
		 * equal in magnitude
		 */
		int difference_x = pieceDest_x - pieceCurPos_x;
		int difference_y = pieceDest_y - pieceCurPos_y;
		if(Math.abs(difference_x) != Math.abs(difference_y))
			return -1;
					
		/*
		 * Check if there is another piece blocking the movement
		 */
		int x_direction, y_direction;
		if(difference_x < 0)
			x_direction = -1;
		else
			x_direction = 1;
		
		if(difference_y < 0)
			y_direction = -1;
		else
			y_direction = 1;
		
		int curX = pieceCurPos_x;
		int curY = pieceCurPos_y;
		for(int i = 0; i < Math.abs(difference_y) - 1; i++) {
			curX += x_direction;
			curY += y_direction;
			if(inputBoard[curX][curY] != null)
				return -1;
		}
		
		/*
		 * When everything checks out, return success
		 */
		return 0;
	}
}