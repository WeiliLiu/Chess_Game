package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.Chesspiece;
import chess.Knight;

class BishopTest {
	/*
	 * Test objects that will be used later in test
	 */
	private Chesspiece testBishop;
	private Chesspiece[][] testBoard;
	
	/*
	 * Test if constructor works
	 */
	@Test
	void testConstructor() {
		testBishop = new Bishop(1, 0, 0);
		assertEquals(1, testBishop.getOwnership());
		assertEquals(2, testBishop.getType());
	}
	
	/*
	 * Test any invalid destinations is indeed
	 * unreachable by a Bishop
	 */
	@Test 
	void testUnreachableDest(){
		testBishop = new Bishop(1, 2, 5);
		
		this.initializeChessBoard();
		testBoard[2][5] = testBishop;
		
		/*
		 * horizontal movements should fail
		 */
		for(int col = 0; col < 8; col++) {
			if(col != 5)
				assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 2, col));
		}
		
		/*
		 * vertical movements should fail
		 */
		for(int row = 0; row < 8; row++) {
			if(row != 2)
				assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, row, 5));
		}
		
		/*
		 * any other location that is not diagonal should also fail
		 */
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 5));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 7, 7));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 4));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 6));
	}
	
	/*
	 * Test any valid destination is indeed reachable by a bishop
	 */
	@Test
	void testReachableDest() {
		testBishop = new Bishop(1, 2, 5);
		
		this.initializeChessBoard();
		testBoard[2][5] = testBishop;
		
		/*
		 * all diagonals should be reachable from current location
		 */
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 6));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 3));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 3));
	}
	
	/*
	 * Test with a single obstacle on the board
	 */
	@Test
	void testWithSingleBlockedPiece() {
		testBishop = new Bishop(1, 2, 5);
		Chesspiece testBishop1 = new Bishop(1, 3, 6);
		
		this.initializeChessBoard();
		testBoard[2][5] = testBishop;
		testBoard[3][6] = testBishop1;
		
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 6));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 3));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 3));
	}
	
	/*
	 * Test with multiple obstacles on the board
	 */
	@Test
	void testWithmultipleBlockedpiece() {
		testBishop = new Bishop(1, 4, 4);
		Chesspiece testBishop1 = new Bishop(1, 6, 6);
		Chesspiece testBishop2 = new Bishop(1, 2, 2);
		Chesspiece testBishop3 = new Bishop(1, 1, 7);
		
		this.initializeChessBoard();
		testBoard[4][4] = testBishop;
		testBoard[6][6] = testBishop1;
		testBoard[2][2] = testBishop2;
		testBoard[1][7] = testBishop3;
		
		assertEquals(0, testBoard[4][4].isValidMove(testBoard, 4, 4, 3, 3));
		assertEquals(0, testBoard[4][4].isValidMove(testBoard, 4, 4, 5, 5));
		assertEquals(-1, testBoard[4][4].isValidMove(testBoard, 4, 4, 0, 0));
		assertEquals(-1, testBoard[4][4].isValidMove(testBoard, 4, 4, 1, 1));
		assertEquals(0, testBoard[4][4].isValidMove(testBoard, 4, 4, 6, 6));
		assertEquals(-1, testBoard[4][4].isValidMove(testBoard, 4, 4, 7, 7));
		assertEquals(0, testBoard[4][4].isValidMove(testBoard, 4, 4, 3, 5));
		assertEquals(0, testBoard[4][4].isValidMove(testBoard, 4, 4, 2, 6));
		
	}
	
	@Test
	void testToIconPath(){
		testBishop = new Bishop(1, 0, 0);
		assertEquals("black-bishop.png", testBishop.toIconPath());
		testBishop = new Bishop(2, 0, 0);
		assertEquals("white-bishop.png", testBishop.toIconPath());
	}
	
	@Test
	void testLocation() {
		testBishop = new Bishop(1, 2, 3);
		assertEquals(2, testBishop.getX());
		assertEquals(3, testBishop.getY());
		testBishop.setX(5);
		testBishop.setY(5);
		assertEquals(5, testBishop.getX());
		assertEquals(5, testBishop.getY());
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
