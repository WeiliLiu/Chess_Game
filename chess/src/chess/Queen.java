package chess;
public class Queen extends Chesspiece{
	private int rowOnBoard;
	private int colOnBoard;
	
	/**
	 * Constructor of the Queen class
	 * 
	 * @param player - player this Queen belongs to
	 */
	public Queen(int player, int row, int col) {
		super(player, 5);
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
			return "black-queen.png";
		else
			return "white-queen.png";
	}
	
	/**
	 * This function will be called by Chessboard class
	 * to check if the instructed move is possible for 
	 * a Queen class
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
		 * Make sure the destination is reachable by a Queen
		 */
		int difference_x = Math.abs(pieceDest_x - pieceCurPos_x);
		int difference_y = Math.abs(pieceDest_y - pieceCurPos_y);
		if(difference_x != difference_y && difference_x != 0 && difference_y != 0)
			return -1;
		
		/*
		 * Check if there is another piece blocking the movement
		 */
		int beginX = Math.min(pieceCurPos_x, pieceDest_x);
		int endX = Math.max(pieceCurPos_x, pieceDest_x);
		int beginY = Math.min(pieceCurPos_y, pieceDest_y);
		int endY = Math.max(pieceCurPos_y, pieceDest_y);
		
		/*
		 * Case 1: Move diagonally
		 */
		if(difference_x == difference_y) {
			for(int row = (beginX + 1); row < endX; row++) {
				for(int col = (beginY + 1); col < endY; col++) {
					if(inputBoard[row][col] != null)
						return -1;
				}
			}
		}
		/*
		 * Case 2: Move horizontally
		 */
		if(difference_x == 0) {
			for(int col = (beginY + 1); col < endY; col++) {
				if(inputBoard[pieceCurPos_x][col] != null)
					return -1;
			}
		}
		
		/*
		 * Case 3: Move vertically
		 */
		if(difference_y == 0) {
			for(int row = (beginX + 1); row < endX; row++) {
				if(inputBoard[row][pieceCurPos_y] != null)
					return -1;
			}
		}
		
		/*
		 *  When everything checks out, return success
		 */
		return 0;
	}
}