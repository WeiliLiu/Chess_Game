package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.Chesspiece;
import chess.Knight;
import chess.Queen;

class QueenTest {

	private Chesspiece testQueen;
	private Chesspiece[][] testBoard;
	
	/*
	 * Test objects that will be used later in test
	 */
	@Test
	void testConstructor() {
		testQueen = new Queen(1, 0, 0);
		assertEquals(1, testQueen.getOwnership());
		assertEquals(5, testQueen.getType());
	}
	
	/*
	 * Test any invalid destinations is indeed
	 * unreachable by a Queen
	 */
	@Test
	void testUnreachableDest() {
		testQueen = new Queen(1, 5, 3);
		this.initializeChessBoard();
		
		/*
		 * Put the Queen on location (5,3)
		 */
		testBoard[5][3] = testQueen;
		
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 4));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 0, 7));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 2, 1));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 6, 1));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 6));
	}
	
	/*
	 * Test any valid destination is indeed reachable by a queen
	 */
	@Test
	void testReachableDest() {
		testQueen = new Queen(1, 5, 3);
		this.initializeChessBoard();
		
		/*
		 * Put the Queen on location (5,3)
		 */
		testBoard[5][3] = testQueen;
		
		/*
		 * test horizontal movements
		 */
		for(int col = 0; col < 8; col++)
			if(col != 3)
				assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, col));
		
		/*
		 * test vertical movements
		 */
		for(int row = 0; row < 8; row++)
			if(row != 5)
				assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, row, 3));
		
		/*
		 * test diagonal movements
		 */
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 2));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 5));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 1));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 4));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 5));
	}
	
	/*
	 * Test queen movements with obstacles
	 */
	@Test
	void testWithObstacles() {
		testQueen = new Queen(1, 5, 3);
		Chesspiece testObstacle1 = new Queen(1, 3, 3);
		Chesspiece testObstacle2 = new Queen(2, 2, 0);
		Chesspiece testObstacle3 = new Queen(3, 4, 2);
		Chesspiece testObstacle4 = new Queen(4, 5, 4);
		this.initializeChessBoard();
		
		/*
		 * Put the Queen on location (5,3)
		 */
		testBoard[5][3] = testQueen;
		testBoard[3][3] = testObstacle1;
		testBoard[2][0] = testObstacle2;
		testBoard[4][2] = testObstacle3;
		testBoard[5][4] = testObstacle4;
		
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 2, 0));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 1));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 2));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 4, 3));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 3, 3));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 2, 3));
		assertEquals(0, testBoard[5][3].isValidMove(testBoard, 5, 3, 7, 5));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 5));
		assertEquals(-1, testBoard[5][3].isValidMove(testBoard, 5, 3, 5, 7));
		
	}
	
	@Test
	void testToIconPath(){
		testQueen = new Queen(1, 0, 0);
		assertEquals("black-queen.png", testQueen.toIconPath());
		testQueen = new Queen(2, 0, 0);
		assertEquals("white-queen.png", testQueen.toIconPath());
	}
	
	@Test
	void testLocation() {
		testQueen = new Queen(1, 2, 3);
		assertEquals(2, testQueen.getX());
		assertEquals(3, testQueen.getY());
		testQueen.setX(5);
		testQueen.setY(5);
		assertEquals(5, testQueen.getX());
		assertEquals(5, testQueen.getY());
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
