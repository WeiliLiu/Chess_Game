package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.Chesspiece;
import chess.Knight;
import chess.Pawn;

class PawnTest {

	private Chesspiece testPawn;
	private Chesspiece[][] testBoard;
	
	/*
	 * Test if constructor works
	 */
	@Test
	void testConstructor() {
		testPawn = new Pawn(1, 0, 0);
		assertEquals(1, testPawn.getOwnership());
		assertEquals(6, testPawn.getType());
	}
	
	/*
	 * Test any invalid destinations is indeed
	 * unreachable by a Pawn if it is its first move
	 */
	@Test
	void testFirstMoveUnreachableDest() {
		/*
		 * Test for player 1 Movements
		 */
		testPawn = new Pawn(1, 5, 3);
		this.initializeChessBoard();
		testBoard[5][3] = testPawn;
		
		/*
		 * Can't move backward
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 3));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 1, 3));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 2, 3));
		
		/*
		 * Can't move to the sides
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 0));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 4));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 6));
		
		/*
		 * Can't move diagonally backward
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 4));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 2));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 1));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 5));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 2, 0));
		
		/*
		 * Can't move diagonally forward when there's no piece to capture
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 4));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 2));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 1));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 5));
		
		/*
		 * Can't move forward by more than two squares
		 */
		testBoard[2][6] = testPawn;
		assertEquals(-1, testBoard[2][6].isValidMove(testBoard, 2, 6, 5, 6));
		assertEquals(-1, testBoard[2][6].isValidMove(testBoard, 2, 6, 6, 6));
		assertEquals(-1, testBoard[2][6].isValidMove(testBoard, 2, 6, 7, 6));
		
		/*
		 * Can't move to some faraway squares
		 */
		assertEquals(-1, testBoard[2][6].isValidMove(testBoard, 2, 6, 0, 0));
		assertEquals(-1, testBoard[2][6].isValidMove(testBoard, 2, 6, 3, 4));
		assertEquals(-1, testBoard[2][6].isValidMove(testBoard, 2, 6, 2, 7));
		
		/*
		 * Test for player 2 movements
		 */
		testPawn = new Pawn(2, 5, 3);
		this.initializeChessBoard();
		testBoard[5][3] = testPawn;
		
		/*
		 * Can't move backward
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 3));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 3));
		
		/*
		 * Can't move to the sides
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 0));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 4));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 6));
		
		/*
		 * Can't move diagonally backward when there is no obstables
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 4));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 2));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 1));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 5));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 2, 0));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 4));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 2));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 1));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 5));
		
		/*
		 * Can't move forward by more than two squares
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 0, 3));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 1, 3));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 2, 3));
		
		/*
		 * Can't move to some faraway squares
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 2, 6, 0, 0));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 2, 6, 3, 4));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 2, 6, 2, 7));
		
	}
	
	/*
	 * Test any valid destination is indeed reachable by a pawn 
	 * on its first move
	 */
	@Test
	void testFirstMoveReachableDest() {
		/*
		 * Player 1
		 */
		testPawn = new Pawn(1, 5, 3);
		this.initializeChessBoard();
		testBoard[5][3] = testPawn;
		
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 3));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 3));
		
		/*
		 * Player 2
		 */
		testPawn = new Pawn(2, 5, 3);
		this.initializeChessBoard();
		testBoard[5][3] = testPawn;
		
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 3));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 3));
	}
	
	/*
	 * Test with obstacles on a pawn's first move
	 */
	@Test
	void testFirstMoveWithObstacles() {
		testPawn = new Pawn(1, 3, 3);
		Chesspiece testObstacle1 = new Pawn(1, 4, 4);
		Chesspiece testObstacle2 = new Pawn(2, 4, 2);
		this.initializeChessBoard();
		testBoard[3][3] = testPawn;
		testBoard[4][2] = testObstacle2;
		
		/*
		 * (4,2) has an opponent piece, so testPawn should be able to
		 * move to (4,2) now, but (4,4) is still unreachable
		 */
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 2));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 4));
		
		/*
		 * Now we put a piece belongs to player 1 on (4,4), (4,4) should
		 * remain unreachable
		 */
		testBoard[4][4] = testObstacle1;
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 4));
		
		/*
		 * Instead put a piece of player 2 on (4,4), which should make 
		 * (4,4) reachable now
		 */
		testBoard[4][4] = testObstacle2;
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 4));
		
		/*
		 * Put an obstacle on (4,3), both (4,3) and (5,3) should become unreachable
		 */
		testBoard[4][3] = testObstacle1;
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 5, 3));
		
		/*
		 * Remove the obstacle on (4,3) and put an obstacle on (5,3), (4,3) should 
		 * become reachable and (5,3) unreachable
		 */
		testBoard[4][3] = null;
		testBoard[5][3] = testObstacle1;
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 5, 3));
		
		/*
		 * Put obstacles on both (4,3) and (5,3), both locations should be unreachable
		 */
		testBoard[4][3] = testObstacle1;
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 5, 3));
	}
	
	/*
	 * Test any subsequence movements of a pawn
	 */
	@Test
	void testSubsequenceMoves() {
		/*
		 * player 1
		 */
		testPawn = new Pawn(1, 3, 3);
		this.initializeChessBoard();
		testBoard[3][3] = testPawn;
		testPawn.incrementMoveCount();
		
		/*
		 * Test vertical movements
		 */
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 1, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 2, 3));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 5, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 6, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 7, 3));
		
		/*
		 * Test Horizontal movements
		 */
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 2));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 4));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 5));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 7));
		
		/*
		 * player 2
		 */
		testPawn = new Pawn(2, 3, 3);
		this.initializeChessBoard();
		testBoard[3][3] = testPawn;
		testPawn.incrementMoveCount();
		
		/*
		 * Test vertical movements
		 */
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 1, 3));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 2, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 5, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 6, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 7, 3));
		
		/*
		 * Test Horizontal movements
		 */
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 2));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 4));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 5));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 7));
	}
	
	@Test
	void testToIconPath(){
		testPawn = new Pawn(1, 0, 0);
		assertEquals("black-pawn.png", testPawn.toIconPath());
		testPawn = new Pawn(2, 0, 0);
		assertEquals("white-pawn.png", testPawn.toIconPath());
	}
	
	@Test
	void testLocation() {
		testPawn = new Pawn(1, 2, 3);
		assertEquals(2, testPawn.getX());
		assertEquals(3, testPawn.getY());
		testPawn.setX(5);
		testPawn.setY(5);
		assertEquals(5, testPawn.getX());
		assertEquals(5, testPawn.getY());
	}
	
	@Test
	void testMoveCount() {
		testPawn = new Pawn(1, 2, 3);
		assertEquals(0, testPawn.getMoveCount());
		testPawn.decrementMoveCount();
		assertEquals(-1, testPawn.getMoveCount());
	}
	
	/*
	 * Helper Functions
	 */
	
	/*
	 * Initialize the chess board with null objects
	 */
	private void initializeChessBoard(){
		int height = 8;
		int width = 8;
		testBoard = new Chesspiece[height][];
		for(int row = 0; row < height; row++)
			testBoard[row] = new Chesspiece[width];
	}
	
	
}
