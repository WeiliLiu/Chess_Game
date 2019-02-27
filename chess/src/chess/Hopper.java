package chess;

public class Hopper extends Chesspiece{
	private int rowOnBoard;
	private int colOnBoard;
	
	/**
	 * Constructor of the Hopper class
	 * 
	 * @param player - player this Hopper belongs to
	 */
	public Hopper(int player, int row, int col) {
		super(player, 8);
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
			return "black-hopper.png";
		else
			return "white-hopper.png";
	}
	
	/**
	 * This function will be called by Chessboard class
	 * to check if the instructed move is possible for 
	 * a Hopper class
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
			int pieceDest_y) {
		/*
		 * Make sure the destination square is reachable by a 
		 * Hopper
		 */
		int difference_x = Math.abs(pieceCurPos_x - pieceDest_x);
		int difference_y = Math.abs(pieceCurPos_y - pieceDest_y);
		
		if(difference_x == 1 || difference_y == 1 || difference_x > 2 ||difference_y >2)
			return -1;
		
		/*
		 * Then check if there is a hurdle in between the 
		 * current location and the destination
		 */
		
		if(inputBoard[(pieceCurPos_x + pieceDest_x) / 2][(pieceCurPos_y + pieceDest_y) / 2] == null)
			return -1;
		
		/*
		 * When everything checks out, return success
		 */
		return 0;
	}
}