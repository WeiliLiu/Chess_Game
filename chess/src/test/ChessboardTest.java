package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Chessboard;
import chess.Pawn;
import chess.Chesspiece;

class ChessboardTest {
	/*
	 * Test objects that will be used later in test
	 */
	@Test
	void testConstructor() {
		Chessboard testBoard = new Chessboard(8, 8);
		assertEquals(6, testBoard.getPiece(1, 1).getType());
		assertEquals(6, testBoard.getPiece(6, 4).getType());
		assertEquals(5, testBoard.getPiece(0, 3).getType());
		assertEquals(5, testBoard.getPiece(7, 3).getType());
		assertEquals(4, testBoard.getPiece(0, 4).getType());
		assertEquals(4, testBoard.getPiece(7, 4).getType());
		
		assertEquals(1, testBoard.getPiece(1, 1).getOwnership());
		assertEquals(2, testBoard.getPiece(7, 1).getOwnership());
		
		assertEquals(null, testBoard.getPiece(2, 2));
		assertEquals(null, testBoard.getPiece(3, 4));
	}
	
	/*
	 * Test the scenario where one player tries to move
	 * another player's piece
	 */
	@Test
	void testMoveOtherPlayerPiece() {
		Chessboard testBoard = new Chessboard(8, 8);
		/*
		 * Player1 tries to move piece belongs to player 2
		 */
		assertEquals(-1, testBoard.movePiece(1, 6, 1, 6, 2));
		assertEquals(-1, testBoard.movePiece(1, 7, 4, 5, 4));
		
		/*
		 * Player2 tries to move piece belongs to player 1
		 */
		assertEquals(-1, testBoard.movePiece(2, 1, 1, 2, 1));
		assertEquals(-1, testBoard.movePiece(2, 1, 5, 2, 5));
	}
	
	/*
	 * Test the scenario where one player moves from a 
	 * location that does not actually contains any piece
	 */
	@Test
	void testMoveNullPiece() {
		Chessboard testBoard = new Chessboard(8, 8);
		/*
		 * The piece being moved does not exist
		 */
		assertEquals(-1, testBoard.movePiece(1, 4, 4, 5, 4));
		assertEquals(-1, testBoard.movePiece(1, 2, 4, 5, 4));
	}
	
	/*
	 * Test the scenario where either the starting position or
	 * the destination is out of bound
	 */
	@Test
	void testOutOfBoundLocation() {
		Chessboard testBoard = new Chessboard(8, 8);
		
		assertEquals(-1, testBoard.movePiece(1, -1, 10, 5, 4));
		assertEquals(-1, testBoard.movePiece(1, 2, 4, 8, 8));
	}
	
	/*
	 * Test the scenario where the destination has a piece
	 * that belongs to the same player
	 */
	@Test
	void testDestWithOwnPiece() {
		Chessboard testBoard = new Chessboard(8, 8);
		
		/*
		 * (0,4) contains player1's King and (1,4) contains
		 * player1's pawn
		 */
		assertEquals(-1, testBoard.movePiece(1, 0, 4, 1, 4));
		
		/*
		 * (7,3) contains player2's queen and (7,2) contains 
		 * player2's bishop
		 */
		assertEquals(-1, testBoard.movePiece(1, 7, 3, 7, 2));
	}
	
	/*
	 * Test with some movements on the board with both success and failure
	 */
	@Test
	void testChessboardMovements() {
		Chessboard testBoard = new Chessboard(8, 8);
		
		/*
		 * Movin player1's pawn at (1,1) to (4,1) should fail because
		 * it can't move 3 squares
		 */
		assertEquals(-1, testBoard.movePiece(1, 1, 1, 4, 1));
		
		/*
		 * Movin player1's pawn at (1,1) to (3,1) should succeed
		 */
		assertEquals(0, testBoard.movePiece(1, 1, 1, 3, 1));
		
		/*
		 * Check if the board has been modified
		 */
		assertEquals(null, testBoard.getPiece(1,1));
		assertEquals(6, testBoard.getPiece(3,1).getType());
		
		/*
		 * Move Pawn at (3,1) to (5,1) should fail now because it is
		 * already the second it is moved
		 */
		assertEquals(-1, testBoard.movePiece(1, 3, 1, 5, 1));
		
		/*
		 * Move Pawn at (3,1) to (4,1) should succeed
		 */
		assertEquals(0, testBoard.movePiece(1, 3, 1, 4, 1));
		
		/*
		 * Player2's Pawn at (6,1) can not move to (4,1) to capture
		 * that pawn because it is not allowed
		 */
		assertEquals(-1, testBoard.movePiece(2, 6, 1, 4, 1));
		
		/*
		 * Player 2's Pawn at (6,2) can move to (5,2)
		 */
		assertEquals(0, testBoard.movePiece(2, 6, 2, 5, 2));
		
		/*
		 * This pawn can't move to (4,3) because there is nothing to be captured,
		 * but it can move to (4,1) because there is a piece belongs to player 1
		 */
		assertEquals(-1, testBoard.movePiece(2, 5, 2, 4, 3));
		assertEquals(0, testBoard.movePiece(2, 5, 2, 4, 1));
		
		/*
		 * (4,1) should now contain player2's pawn and player1's pawn should disappear
		 * from the board
		 */
		assertEquals(2, testBoard.getPiece(4, 1).getOwnership());
		assertEquals(null, testBoard.getPiece(5, 1));
	}
	
	/*
	 * Test the scenario where a king tries to move to a 
	 * dangerous position
	 */
	@Test
	void testKingInCheck1() {
		Chessboard testBoard = new Chessboard(8, 8);
		
		/*
		 * Move player2's pawn at (6,4) to (4,4)
		 */
		assertEquals(0, testBoard.movePiece(2, 6, 4, 4, 4));
		
		/*
		 * Move player1's pawn at (1,3) to (5,3)
		 */
		assertEquals(0, testBoard.movePiece(1, 1, 3, 3, 3));
		assertEquals(0, testBoard.movePiece(1, 3, 3, 4, 3));
		assertEquals(0, testBoard.movePiece(1, 4, 3, 5, 3));
		
		/*
		 * Moving player2's king at (7,4) to (6,4)
		 * is not allowed because it can be captured by player1's
		 * pawn at (5,3)
		 */
		assertEquals(-1, testBoard.movePiece(2, 7, 4, 6, 4));
	}
	
	/*
	 * A second test that test the scenario if the king
	 * tries to move a dangerous position
	 */
	@Test
	void testKingInCheck2() {
		Chessboard testBoard = new Chessboard(8, 8);
		
		/*
		 * Move player2's pawn at (6,4) to (4,4)
		 */
		assertEquals(0, testBoard.movePiece(2, 6, 4, 4, 4));

		assertEquals(0, testBoard.movePiece(2, 4, 4, 3, 4));

		
		/*
		 * Move Player1's Knight at (0,6) to (2,5)
		 */
		assertEquals(0, testBoard.movePiece(1, 0, 6, 2, 5));
	
		
		/*
		 * Move Player2's King at (7,4) to (4,4)
		 */
		assertEquals(0, testBoard.movePiece(2, 7, 4, 6, 4));
		assertEquals(0, testBoard.movePiece(2, 6, 4, 5, 4));
		assertEquals(-1, testBoard.movePiece(2, 5, 4, 4, 4));
		
	}
	
	@Test
	void testGameEnd() {
		/*
		 * call this test to move king to a position where it can be captured
		 */
		Chessboard testBoard = new Chessboard(8, 8);
		
		/*
		 * Move player1's knight at (0,1) to (5,3)
		 */
		assertEquals(0, testBoard.movePiece(1, 0, 1, 2, 2));
		assertEquals(0, testBoard.movePiece(1, 2, 2, 4, 1));
		assertEquals(0, testBoard.movePiece(1, 4, 1, 5, 3));
		/*
		 * Then use this knight to capture player2's king
		 */
		assertEquals(0, testBoard.movePiece(1, 5, 3, 7, 4));
		
		/*
		 * Make sure the chessboard object knows the game has ended
		 */
		assertEquals(true, testBoard.hasGameEnded());
	}
	
	@Test
	void testCheckMate() {
		Chessboard testBoard = new Chessboard(8, 8);
		
		assertEquals(0, testBoard.movePiece(2, 6, 4, 4, 4));
		assertEquals(0, testBoard.movePiece(2, 4, 4, 3, 4));
		assertEquals(0, testBoard.movePiece(2, 3, 4, 2, 4));
		
		/*
		 * Move Player2's King at (7,4) to (3,4)
		 */
		assertEquals(0, testBoard.movePiece(2, 7, 4, 6, 4));
		assertEquals(0, testBoard.movePiece(2, 6, 4, 5, 4));
		assertEquals(0, testBoard.movePiece(2, 5, 4, 4, 4));

		assertEquals(true, testBoard.isCheckmate(2, 3, 4));
		
	}
	
	@Test
	void testReverseMove() {
		Chessboard testBoard = new Chessboard(8, 8);
		assertEquals(0, testBoard.movePiece(2, 6, 4, 4, 4));
		testBoard.reverseMove(6, 4, 4, 4);
		assertEquals(null, testBoard.getPiece(4, 4));
		assertEquals(6, testBoard.getPiece(6, 4).getType());
	}
	
}
