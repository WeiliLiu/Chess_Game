package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.Chesspiece;
import chess.Knight;

class KnightTest {

	private Chesspiece testKnight;
	private Chesspiece[][] testBoard;
	
	/*
	 * Test if constructor works
	 */
	@Test
	void testConstructor() {
		testKnight = new Knight(2, 0, 0);
		assertEquals(2, testKnight.getOwnership());
		assertEquals(3, testKnight.getType());
	}
	
	/*
	 * Test any invalid destinations is indeed
	 * unreachable by a Knight
	 */
	@Test
	void testUnreachableDest() {
		testKnight = new Knight(1, 2, 5);
		
		this.initializeChessBoard();
		testBoard[2][5] = testKnight;
		
		/*
		 * A knight can never go to the same column
		 */
		for(int row = 0; row < 8; row++) {
			if(row != 2)
				assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, row, 5));
		}
		
		/*
		 * A knight can never go to the same row
		 */
		for(int col = 0; col < 8; col++) {
			if(col != 5)
				assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 2, col));
		}
		
		/*
		 * Other locations it can't go to
		 */
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 4));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 3));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 7));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 6));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 6, 6));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 0));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 6, 3));
	}
	
	/*
	 * Test any valid destination is indeed reachable by a king
	 */
	@Test
	void testReachableDest() {
		testKnight = new Knight(1, 2, 5);
		
		this.initializeChessBoard();
		testBoard[2][5] = testKnight;
		
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 4));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 3));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 3));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 4));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 6));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 6));
	}
	
	/*
	 * Test movements with obstacles on the board
	 */
	@Test
	void testWithObstacles() {
		testKnight = new Knight(1, 2, 5);
		Chesspiece obstacleKnight1 = new Knight(1, 2, 6);
		Chesspiece obstacleKnight2 = new Knight(1, 4, 5);
		
		this.initializeChessBoard();
		testBoard[2][5] = testKnight;
		testBoard[2][6] = obstacleKnight1;
		testBoard[4][5] = obstacleKnight2;
		
		/*
		 * The knight should be able to go where he can go despite 
		 * any obstales
		 */
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 4));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 3));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 3));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 4));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 6));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 6));
	}
	
	@Test
	void testToIconPath(){
		testKnight = new Knight(1, 0, 0);
		assertEquals("black-knight.png", testKnight.toIconPath());
		testKnight = new Knight(2, 0, 0);
		assertEquals("white-knight.png", testKnight.toIconPath());
	}
	
	@Test
	void testLocation() {
		testKnight = new Knight(1, 2, 3);
		assertEquals(2, testKnight.getX());
		assertEquals(3, testKnight.getY());
		testKnight.setX(5);
		testKnight.setY(5);
		assertEquals(5, testKnight.getX());
		assertEquals(5, testKnight.getY());
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
