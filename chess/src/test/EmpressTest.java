package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.Chesspiece;
import chess.Empress;
import chess.Pawn;
import chess.Rook;

class EmpressTest {
	/*
	 * Test objects that will be used later in test
	 */
	private Chesspiece testEmpress;
	private Chesspiece[][] testBoard;
	
	/*
	 * Test if constructor works
	 */
	@Test
	void testConstructor() {
		testEmpress = new Empress(1, 0, 0);
		assertEquals(1, testEmpress.getOwnership());
		assertEquals(7, testEmpress.getType());
	}
	
	/*
	 * Test any invalid destinations is indeed
	 * unreachable by an Empress
	 */
	@Test
	void testUnreachableDestination() {
		testEmpress = new Empress(1, 2, 5);
		
		this.initializeChessBoard();
		
		testBoard[2][5] = testEmpress;
		
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 6));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 1));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 6, 6));
	}
	
	/*
	 * Test any valid destination is indeed reachable by a Rook
	 */
	@Test
	void testReachableDestination() {
		testEmpress = new Empress(1, 2, 5);
		
		this.initializeChessBoard();
		
		testBoard[2][5] = testEmpress;
		
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 2, 7));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 2, 1));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 6, 5));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 3));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 3, 3));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 4));
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 0, 6));
	}
	
	/*
	 * Test with obstacles
	 */
	@Test
	void testWithObstacles() {
		testEmpress = new Empress(1, 2, 5);
		Empress testObstacle1 = new Empress(1, 2, 4);
		Empress testObstacle2 = new Empress(1, 4, 5);
		
		this.initializeChessBoard();
		
		testBoard[2][5] = testEmpress;
		testBoard[2][4] = testObstacle1;
		
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 1, 3));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 2, 2));
		
		testBoard[4][5] = testObstacle2;
		assertEquals(0, testBoard[2][5].isValidMove(testBoard, 2, 5, 4, 6));
		assertEquals(-1, testBoard[2][5].isValidMove(testBoard, 2, 5, 6, 5));
	}
	
	@Test
	void testToIconPath(){
		testEmpress = new Empress(1, 0, 0);
		assertEquals("black-empress.png", testEmpress.toIconPath());
		testEmpress = new Empress(2, 0, 0);
		assertEquals("white-empress.png", testEmpress.toIconPath());
	}
	
	@Test
	void testLocation() {
		testEmpress = new Empress(1, 2, 3);
		assertEquals(2, testEmpress.getX());
		assertEquals(3, testEmpress.getY());
		testEmpress.setX(5);
		testEmpress.setY(5);
		assertEquals(5, testEmpress.getX());
		assertEquals(5, testEmpress.getY());
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