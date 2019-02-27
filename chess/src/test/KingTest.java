package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.Chesspiece;
import chess.King;
import chess.Knight;

/*
 * Note: It is Chessboard's job to check whether the King is in check,
 * so that will be tested in Chessboard's test cases
 */
class KingTest {
	/*
	 * Test objects that will be used later in test
	 */
	private Chesspiece testKing;
	private Chesspiece[][] testBoard;
	
	/*
	 * Test if constructor works
	 */
	@Test
	void testConstructor() {
		testKing = new King(1, 0, 0);
		assertEquals(1, testKing.getOwnership());
		assertEquals(4, testKing.getType());
	}
	
	/*
	 * Test any invalid destinations is indeed
	 * unreachable by a King
	 */
	@Test
	void testUnreachableDest() {
		testKing = new King(1, 5, 3);
		this.initializeChessBoard();
		
		/*
		 * Put the King on location (5,3)
		 */
		testBoard[5][3] = testKing;
		
		/*
		 * The function returns failure if the destination is not one square 
		 * from the starting point
		 */
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 1));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 0, 0));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 1, 7));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 2, 3));
	}
	
	/*
	 * Test any valid destination is indeed reachable by a king
	 */
	@Test 
	void testReachableDest() {
		testKing = new King(1, 5, 3);
		this.initializeChessBoard();
		
		/*
		 * Put the King on location (5,3)
		 */
		testBoard[5][3] = testKing;
		
		/*
		 * testing whether the king only moves one square in any direction
		 */
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 2));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 3));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 4));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 2));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 4));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 2));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 3));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 4));
		
	}
	
	@Test
	void testToIconPath(){
		testKing = new King(1, 0, 0);
		assertEquals("black-king.png", testKing.toIconPath());
		testKing = new King(2, 0, 0);
		assertEquals("white-king.png", testKing.toIconPath());
	}
	
	@Test
	void testLocation() {
		testKing = new King(1, 2, 3);
		assertEquals(2, testKing.getX());
		assertEquals(3, testKing.getY());
		testKing.setX(5);
		testKing.setY(5);
		assertEquals(5, testKing.getX());
		assertEquals(5, testKing.getY());
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
