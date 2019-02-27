package test;

import static org.junit.jupiter.api.Assertions.*;
import chess.Hopper;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.Chesspiece;
import chess.Knight;
import chess.Pawn;

class HopperTest {
	
	private Chesspiece testHopper;
	private Chesspiece[][] testBoard;

	/*
	 * Test if constructor works
	 */
	@Test
	void testConstructor() {
		testHopper = new Hopper(2, 0, 0);
		assertEquals(2, testHopper.getOwnership());
		assertEquals(8, testHopper.getType());
	}
	
	/*
	 * Test any invalid destinations is indeed
	 * unreachable by a Hopper
	 */
	@Test
	void testUnreachableDest() {
		testHopper = new Hopper(1, 2, 5);
		
		this.initializeChessBoard();
		testBoard[2][5] = testHopper;
		/*
		 * Without an obstacle it cannot go anywhere
		 */
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 6, 6));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 5));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 0));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 5));
		
		
	}
	
	/*
	 * Test any valid destinations together with obstacles
	 */
	@Test 
	void testDestWithObstacles() {
		testHopper = new Hopper(1, 2, 5);
		Hopper testObstacle1 = new Hopper(1, 3, 5);
		Hopper testObstacle2 = new Hopper(1, 2, 6);
		Hopper testObstacle3 = new Hopper(1, 1, 4);
		
		this.initializeChessBoard();
		testBoard[2][5] = testHopper;
		testBoard[3][5] = testObstacle1;
		testBoard[2][6] = testObstacle2;
		testBoard[1][4] = testObstacle3;
		
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 5));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 2, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 3));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 5, 5));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 2));
	}
	
	@Test
	void testToIconPath(){
		testHopper = new Hopper(1, 0, 0);
		assertEquals("black-hopper.png", testHopper.toIconPath());
		testHopper = new Hopper(2, 0, 0);
		assertEquals("white-hopper.png", testHopper.toIconPath());
	}
	
	@Test
	void testLocation() {
		testHopper = new Hopper(1, 2, 3);
		assertEquals(2, testHopper.getX());
		assertEquals(3, testHopper.getY());
		testHopper.setX(5);
		testHopper.setY(5);
		assertEquals(5, testHopper.getX());
		assertEquals(5, testHopper.getY());
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
