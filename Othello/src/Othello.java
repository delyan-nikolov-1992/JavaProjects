import java.util.ArrayList;
import java.util.List;

/**
 * This class models an Othello game. It stores the both players and the board of the game. 
 * It administers all of the moves of the both players and all of the changes in the board.
 * @author Delyan Nikolov
 */
public class Othello {

	/** the first player in the Othello game */
	private Player player1;
	
	/** the second player in the Othello game */
	private Player player2;
	
	/** the current player in the Othello game */
	private Player currentPlayer;
	
	/** the board in the Othello game */
	private Board board;
	
	/**
	 * Constructs a new Othello game with the given width and height. 
	 * The first player is the player, who plays with the black piece and 
	 * the second player is the player, who plays with the white piece.
	 * @param width width of the board
	 * @param height height of the board
	 */
	public Othello(int width, int height) {
		if (width < 2 || width > 26 || width % 2 != 0) {
			throw new IllegalArgumentException();
		} else if (height < 2 || height > 98 || height % 2 != 0) {
			throw new IllegalArgumentException();
		}
		
		board = new Board(width, height);
		player1 = new Player("black", Piece.BLACK);
		player2 = new Player("white", Piece.WHITE);
		currentPlayer = player1;
	}
	
	/**
	 * Returns the current player of this Othello game.
	 * @return the current player of this Othello game
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Returns the board of this Othello game.
	 * @return the board of this Othello game
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Sets a standard board with the given width and height. The middle area consists of two white pieces and 
	 * two black pieces. All other positions are free fields. 
	 * @param width the given width, which this board has
	 * @param height the given height, which this board has
	 */
	public void setStandardBoard(int width, int height) {
		if (width < 2 || width > 26 || width % 2 != 0) {
			throw new IllegalArgumentException();
		} else if (height < 2 || height > 98 || height % 2 != 0) {
			throw new IllegalArgumentException();
		}
		
		board.setStandardBoard(width, height);
	}
	
	/**
	 * Returns {@code true} if a hole area can be added in the area between the
	 * two positions with the given parameters , {@code false} otherwise. A hole area 
	 * can be added only if there are no black and white pieces in the given area.
	 * @param width1 width of the first position to be checked
	 * @param height1 height of the first position to be checked
	 * @param width2 width of the second position to be checked
	 * @param height2 height of the second position to be checked
	 * @return {@code true} if a hole area can be added in the area between the
	 * two positions with the given parameters , {@code false} otherwise
	 */
	public boolean possibleHoleArea(int width1, int height1, int width2, int height2) {
		if (width1 < 0 || width1 > board.getWidth() - 1) {
			throw new IllegalArgumentException();
		} else if (height1 < 0 || height1 > board.getHeight() - 1) {
			throw new IllegalArgumentException();
		} else if (width2 < 0 || width2 > board.getWidth() - 1) {
			throw new IllegalArgumentException();
		} else if (height2 < 0 || height2 > board.getHeight() - 1) {
			throw new IllegalArgumentException();
		}
		
		boolean result = true;
		
		if (!board.possibleHoleArea(width1, height1, width2, height2)) {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Adds a hole area between the two positions with the given parameters.
	 * @param width1 width of the first position to be checked
	 * @param height1 height of the first position to be checked
	 * @param width2 width of the second position to be checked
	 * @param height2 height of the second position to be checked
	 */
	public void addHoleArea(int width1, int height1, int width2, int height2) {
		if (width1 < 0 || width1 > board.getWidth() - 1) {
			throw new IllegalArgumentException();
		} else if (height1 < 0 || height1 > board.getHeight() - 1) {
			throw new IllegalArgumentException();
		} else if (width2 < 0 || width2 > board.getWidth() - 1) {
			throw new IllegalArgumentException();
		} else if (height2 < 0 || height2 > board.getHeight() - 1) {
			throw new IllegalArgumentException();
		}
		
		board.addHoleArea(width1, height1, width2, height2);
	}
	
	/**
	 * Returns {@code true} if there can be set a piece with the color of the given 
	 * current piece on the position with the given parameters, {@code false} otherwise.
	 * @param width width of the position to be checked
	 * @param height height of the position to be checked
	 * @param current the current piece to be checked
	 * @param other the other piece to be checked
	 * @return {@code true} if there can be set a piece with the color of the given 
	 * current piece on the position with the given parameters, {@code false} otherwise
	 */
	public boolean possiblePositionToSetPiece(int width, int height, Piece current, Piece other) {
		if (width < 0 || width > board.getWidth() - 1) {
			throw new IllegalArgumentException();
		} else if (height < 0 || height > board.getHeight() - 1) {
			throw new IllegalArgumentException();
		} else if (current == null || other == null || current == other 
				|| (current != Piece.WHITE && current != Piece.BLACK) 
				|| (other != Piece.WHITE && other != Piece.BLACK)) {
			throw new IllegalArgumentException();
		}
		
		boolean result = false;
		
		if (board.possiblePositionToSetPiece(width, height, current, other)) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Sets a piece with the color of the given current piece and switches the current player with the other player.
	 * @param width width of the position to be checked
	 * @param height height of the position to be checked
	 * @param current the current piece to be checked
	 * @param other the other piece to be checked
	 */
	public void setPiece(int width, int height, Piece current, Piece other) {
		if (width < 0 || width > board.getWidth() - 1) {
			throw new IllegalArgumentException();
		} else if (height < 0 || height > board.getHeight() - 1) {
			throw new IllegalArgumentException();
		} else if (current == null || other == null || current == other 
					|| (current != Piece.WHITE && current != Piece.BLACK) 
					|| (other != Piece.WHITE && other != Piece.BLACK)) {
			throw new IllegalArgumentException();
		}
		
		board.setPiece(width, height, current, other);
		switchPlayers();
	}
	
	/**
	 * Returns {@code true} if there can be set a piece with the color of the 
	 * given current piece anywhere on the board, {@code false} otherwise.
	 * @param current the current piece to be checked
	 * @param other the other piece to be checked
	 * @return {@code true} if there can be set a piece with the color of the 
	 * given current piece anywhere on the board, {@code false} otherwise
	 */
	public boolean possibleMove(Piece current, Piece other) {
		if (current == null || other == null || current == other 
				|| (current != Piece.WHITE && current != Piece.BLACK) 
				|| (other != Piece.WHITE && other != Piece.BLACK)) {
			throw new IllegalArgumentException();
		}
		
		boolean result = false;
		
		if (board.possibleMove(current, other)) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Returns the possible moves for the piece with the color of the given piece. 
	 * Each move is saved in a array of integers and all of these arrays are saved in a list.
	 * @param current the current piece to be checked
	 * @param other the other piece to be checked
	 * @return the possible moves for the piece with the color of the given piece
	 */
	public List<Integer[]> allPossibleMoves(Piece current, Piece other) {
		if (current == null || other == null || current == other 
				|| (current != Piece.WHITE && current != Piece.BLACK) 
				|| (other != Piece.WHITE && other != Piece.BLACK)) {
			throw new IllegalArgumentException();
		}
		
		List<Integer[]> possibleMoves = new ArrayList<Integer[]>();
		possibleMoves = board.allPossibleMoves(current, other);
		
		return possibleMoves;
	}
	
	/**
	 * Returns the amount of all the pieces on this board, which have the color of the given piece.
	 * @param piece the given piece to be checked
	 * @return the amount of all the pieces on this board, which have the color of the given piece
	 */
	public int numberOfPieces(Piece piece) {
		if (piece != Piece.WHITE && piece != Piece.BLACK) {
			throw new IllegalArgumentException();
		}
		
		int count = board.numberOfPieces(piece);
		
		return count;
	}
	
	/**
	 * Switches the current player with the other player.
	 */
	public void switchPlayers() {
		if (currentPlayer == player1) {
			currentPlayer = player2;
		} else {
			currentPlayer = player1;
		}
	}
	
	/**
	 * Returns the other player, who is not the current player.
	 * @return the other player, who is not the current player
	 */
	public Player getOtherPlayer() {		
		Player other = null;
		
		if (currentPlayer == player1) {
			other = player2;
		} else {
			other = player1;
		}
		
		return other;
	}
	
}