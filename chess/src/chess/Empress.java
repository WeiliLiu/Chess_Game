package chess;
/*
 * Empress combines the power of a Rook and a Knight
 */
public class Empress extends Chesspiece{
	private int rowOnBoard;
	private int colOnBoard;
	
	/**
	 * Constructor of the Empress class
	 * 
	 * @param player - player this Empress belongs to
	 */
	public Empress(int player, int row, int col) {
		super(player, 7);
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
			return "black-empress.png";
		else
			return "white-empress.png";
	}
	
	/**
	 * This function will be called by Chessboard class
	 * to check if the instructed move is possible for 
	 * an Empress class
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
		if(this.isValidMoveAsRook(inputBoard, pieceCurPos_x, 
				pieceCurPos_y, pieceDest_x, pieceDest_y) == 0)
			return 0;
		
		if(this.isValidMoveAsKnight(inputBoard, pieceCurPos_x,
				pieceCurPos_y, pieceDest_x, pieceDest_y) == 0)
			return 0;
		
		return -1;
	}
	
	/**
	 * Checking the rook functionality of an empress
	 * 
	 * @param inputBoard
	 * @param pieceCurPos_x
	 * @param pieceCurPos_y
	 * @param pieceDest_x
	 * @param pieceDest_y
	 * @return 0 - indicate success, -1 - indicate failure
	 */
	private int isValidMoveAsRook(Chesspiece[][] inputBoard,
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
	
	/**
	 * Checking the knight functionality of an empress
	 * 
	 * @param inputBoard
	 * @param pieceCurPos_x
	 * @param pieceCurPos_y
	 * @param pieceDest_x
	 * @param pieceDest_y
	 * @return 0 - indicate success, -1 - indicate failure
	 */
	private int isValidMoveAsKnight(Chesspiece[][] inputBoard,
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