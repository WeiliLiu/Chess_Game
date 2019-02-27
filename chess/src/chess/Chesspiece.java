package chess;

/*
 * This class represents a generic chesspiece, and it
 * is the superclass of all specific chess types
 */
public abstract class Chesspiece{
	/**
	 * Member variables for this class
	 */
	protected int ownership;
	protected int type;
	
	/**
	 * This is the constructor for a generic chesspiece
	 * 
	 * @param player - player this piece belongs to
	 * @param type - type of the chesspiece
	 */
	public Chesspiece(int player, int type) {
		this.ownership = player;
		this.type = type;
	}
	
	/**
	 * This method will be overwritten by the same
	 * method in subclasses
	 */
	public void incrementMoveCount() {}
	
	public void decrementMoveCount() {}
	
	/**
	 * This method will be overwritten by the same
	 * method in subclasses
	 */
	public int getMoveCount() {
		return 0;
	}
	
	/**
	 * This method returns which player the chess piece
	 * belongs to
	 * 
	 * @param none
	 * 
	 * @return 1 - player 1, 2 - player 2
	 */
	public int getOwnership() {
		return this.ownership;
	}
	
	/**
	 * This method returns the type of the chesspiece
	 * 
	 * @param none
	 * 
	 * @return 1 - Rook, 2 - Bishop, 3 - Knight, 4 - King,
	 * 5 - Queen, 6 - Pawn
	 */
	public int getType() {
		return this.type;
	}
	
	public String toIconPath() {
		return "";
	}
	
	/**
	 * This method will be overwritten by the same
	 * method in subclasses
	 */
	public int isValidMove(Chesspiece[][] inputBoard,
			int pieceCurPos_x,
			int pieceCurPos_y,
			int pieceDest_x,
			int pieceDest_y) {
		return 0;
	}
	
	public abstract int getX();
	
	public abstract int getY();
	
	public abstract void setX(int pieceDest_x);

	public abstract void setY(int pieceDest_y);
	
}