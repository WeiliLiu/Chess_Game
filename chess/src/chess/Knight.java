package chess;
public class Knight extends Chesspiece{
	private int rowOnBoard;
	private int colOnBoard;
	
	/**
	 * Constructor of the Knight class
	 * 
	 * @param player - player this Knight belongs to
	 */
	public Knight(int player, int row, int col) {
		super(player, 3);
		this.rowOnBoard = row;
		this.colOnBoard = col;
	}
	
	public String toIconPath() {
		if(this.getOwnership() == 1)
			return "black-knight.png";
		else
			return "white-knight.png";
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
	
	/**
	 * This function will be called by Chessboard class
	 * to check if the instructed move is possible for 
	 * a Knight class
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
		 * Make sure the destination is reachable by a Knight
		 */
		int difference_x = Math.abs(pieceDest_x - pieceCurPos_x);
		int difference_y = Math.abs(pieceDest_y - pieceCurPos_y);
		if(!((difference_x == 2 && difference_y == 1) || 
				(difference_x == 1 && difference_y == 2)))
			return -1;
		
		/*
		 * When everything checks out, return success
		 * Note: Since Knight can move over pieces, there is no 
		 * need to check blocking pieces
		 */
		return 0;
	}
}