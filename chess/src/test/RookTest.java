package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.Chesspiece;
import chess.Knight;
import chess.Rook;

class RookTest {
	private Chesspiece testRook;
	private Chesspiece[][] testBoard;
	
	/*
	 * Test if constructor works
	 */
	@Test
	void testConstructor() {
		testRook = new Rook(1, 0, 0);
		assertEquals(1, testRook.getOwnership());
		assertEquals(1, testRook.getType());
	}
	
	/*
	 * Test any invalid destinations is indeed
	 * unreachable by a Rook
	 */
	@Test
	void testUnreachableDestination() {
		testRook = new Rook(1, 2, 5);
		
		this.initializeChessBoard();
		
		/*
		 * Putting the testRook object on row 2 and col 5
		 */
		testBoard[2][5] = testRook;
		
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 4));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 0));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 3));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 1));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 7, 9));
	}
	
	/*
	 * Test any valid destination is indeed reachable by a Rook
	 */
	@Test
	void testReachableDestination() {
		testRook = new Rook(1, 2, 5);
		this.initializeChessBoard();
		
		/*
		 * Putting the testRook object on row 2 and col 5
		 */
		testBoard[2][5] = testRook;
		
		/*
		 * test horizontal movements
		 */
		for(int col = 0; col < 8; col++)
			assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 2, col));
		
		/*
		 * test vertical movements
		 */
		for(int row = 0; row < 8; row++)
			assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, row, 5));
	}
	
	/*
	 * Test Rook movements with a single obstacle
	 */
	@Test
	void testWithSingleBlockedPiece() {
		testRook = new Rook(1, 2, 3);
		Chesspiece testBlockRook1 = new Rook(1, 2, 5);
		Chesspiece testBlockRook2 = new Rook(1, 1, 3);
		this.initializeChessBoard();
		
		/*
		 * Place all pieces on the board, the are all rooks
		 * since the type does not matter in this test
		 */
		testBoard[2][3] = testRook;
		testBoard[2][5] = testBlockRook1;
		testBoard[1][3] = testBlockRook2;
		
		/*
		 * test horizontal movements
		 */
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 2, 0));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 2, 1));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 2, 2));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 2, 4));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 2, 5));
		assertEquals(-1, testBoard[2][3].isValidMove(testBoard, 2, 3, 2, 6));
		assertEquals(-1, testBoard[2][3].isValidMove(testBoard, 2, 3, 2, 7));
		
		/*
		 * test vertical movements
		 */
		assertEquals(-1, testBoard[2][3].isValidMove(testBoard, 2, 3, 0, 3));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 1, 3));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 3, 3));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 4, 3));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 5, 3));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 6, 3));
		assertEquals(0, testBoard[2][3].isValidMove(testBoard, 2, 3, 7, 3));
		
	}
	
	/*
	 * Test Rook movements with multiple obstacles
	 */
	@Test
	void testWithMultipleBlockPieces() {
		testRook = new Rook(1, 3, 3);
		Chesspiece testBlockRook1 = new Rook(1, 3, 5);
		Chesspiece testBlockRook2 = new Rook(1, 3, 1);
		this.initializeChessBoard();
		
		/*
		 * Place all pieces on the board, the are all rooks
		 * since the type does not matter in this test
		 */
		testBoard[3][3] = testRook;
		testBoard[3][5] = testBlockRook1;
		testBoard[3][1] = testBlockRook2;
		
		/*
		 * test horizontal movements
		 */
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 0));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 1));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 2));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 4));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 5));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 6));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 3, 7));
		
		/*
		 * test vertical movements
		 */
		testBoard[2][3] = testBlockRook1;
		testBoard[6][3] = testBlockRook2;
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 0, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 1, 3));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 2, 3));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 4, 3));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 5, 3));
		assertEquals(0, testBoard[3][3].isValidMove(testBoard, 3, 3, 6, 3));
		assertEquals(-1, testBoard[3][3].isValidMove(testBoard, 3, 3, 7, 3));
	}
	
	@Test
	void testToIconPath(){
		testRook = new Rook(1, 0, 0);
		assertEquals("black-rook.png", testRook.toIconPath());
		testRook = new Rook(2, 0, 0);
		assertEquals("white-rook.png", testRook.toIconPath());
	}
	
	@Test
	void testLocation() {
		testRook = new Rook(1, 2, 3);
		assertEquals(2, testRook.getX());
		assertEquals(3, testRook.getY());
		testRook.setX(5);
		testRook.setY(5);
		assertEquals(5, testRook.getX());
		assertEquals(5, testRook.getY());
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
