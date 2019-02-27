package chess;
public class Rook extends Chesspiece{
	private int rowOnBoard;
	private int colOnBoard;
	
	/**
	 * Constructor of the Rook class
	 * 
	 * @param player - player this Rook belongs to
	 */
	public Rook(int player, int row, int col) {
		super(player, 1);
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
			return "black-rook.png";
		else
			return "white-rook.png";
	}
	
	/**
	 * This function will be called by Chessboard class
	 * to check if the instructed move is possible for 
	 * a Rook class
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
		 * Make sure the destination is reachable by a Rook
		 */
		if(pieceCurPos_x != pieceDest_x && pieceCurPos_y != pieceDest_y)
			return -1;
		
		/*
		 * Check if there is another piece blocking the movement
		 */
		if(pieceCurPos_x == pieceDest_x) {
			int begin = Math.min(pieceCurPos_y, pieceDest_y);
			int end = Math.max(pieceCurPos_y, pieceDest_y);
			/*
			 * Note: it starts from (begin + 1) because the starting
			 * position obviously contains a piece
			 */
			for(int i = (begin + 1); i < end; i++) {
				if(inputBoard[pieceCurPos_x][i] != null)
					return -1;
			}
		}else {
			int begin = Math.min(pieceCurPos_x, pieceDest_x);
			int end = Math.max(pieceCurPos_x, pieceDest_x);
			for(int i = (begin + 1); i < end; i++) {
				if(inputBoard[i][pieceCurPos_y] != null) {
					return -1;
				}
			}
		}
		
		/*
		 * When everything checks out, return success
		 */
		return 0;
	}
	
}