import java.util.ArrayList;
import java.util.List;

/**
 * This class models the board in the othello game. A board is represented by width and height. 
 * On each position of the board there can be hole, free field, black piece and white piece
 * @author Delyan Nikolov
 */
public class Board {

	/** width of the board */
	private int width;
	
	/** height of the board */
	private int height;
	
	/** representation of the board */
	private Piece[][] board;
	
	/**
	 * Constructs a new empty board with the given width and height.
	 * @param width width of the board
	 * @param height height of the board
	 */
	public Board(int width, int height) {
		if (width < 2 || width > 26 || width % 2 != 0) {
			throw new IllegalArgumentException();
		} else if (height < 2 || height > 98 || height % 2 != 0) {
			throw new IllegalArgumentException();
		}
		
		this.width = width;
		this.height = height;
		board = new Piece[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = Piece.FREE;
			}
		}
	}
	
	/**
	 * Returns this board's width.
	 * @return this board's width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns this board's height.
	 * @return this board's height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns this board's representation.
	 * @return this board's representation
	 */
	public Piece[][] getBoard() {
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
		
		board[(height / 2) - 1][(width / 2) - 1] = Piece.WHITE;
		board[(height / 2) - 1][width / 2] = Piece.BLACK;
		board[height / 2][(width / 2) - 1] = Piece.BLACK;
		board[height / 2][width / 2] = Piece.WHITE;
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
		if (width1 < 0 || width1 > this.width - 1) {
			throw new IllegalArgumentException();
		} else if (height1 < 0 || height1 > this.height - 1) {
			throw new IllegalArgumentException();
		} else if (width2 < 0 || width2 > this.width - 1) {
			throw new IllegalArgumentException();
		} else if (height2 < 0 || height2 > this.height - 1) {
			throw new IllegalArgumentException();
		}
		
		boolean result = true;
		
		for (int i = height1; i <= height2; i++) {
			for (int j = width1; j <= width2; j++) {
				if (board[i][j] == Piece.BLACK || board[i][j] == Piece.WHITE) {
					result = false;
					
					return result;
				}
			}
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
		if (width1 < 0 || width1 > this.width - 1) {
			throw new IllegalArgumentException();
		} else if (height1 < 0 || height1 > this.height - 1) {
			throw new IllegalArgumentException();
		} else if (width2 < 0 || width2 > this.width - 1) {
			throw new IllegalArgumentException();
		} else if (height2 < 0 || height2 > this.height - 1) {
			throw new IllegalArgumentException();
		}
		
		for (int i = height1; i <= height2; i++) {
			for (int j = width1; j <= width2; j++) {
				board[i][j] = Piece.HOLE;
			}
		}
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
		if (width < 0 || width > this.width - 1) {
			throw new IllegalArgumentException();
		} else if (height < 0 || height > this.height - 1) {
			throw new IllegalArgumentException();
		} else if (current == null || other == null || current == other 
				|| (current != Piece.WHITE && current != Piece.BLACK) 
				|| (other != Piece.WHITE && other != Piece.BLACK)) {
			throw new IllegalArgumentException();
		}
		
		boolean result = false;
		
		if (board[height][width] == Piece.FREE) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (moveInDirection(width, height, j, i, current, other)) {
						result = true;
						
						return result;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Sets a piece with the color of the given current piece.
	 * @param width width of the position to be checked
	 * @param height height of the position to be checked
	 * @param current the current piece to be checked
	 * @param other the other piece to be checked
	 */
	public void setPiece(int width, int height, Piece current, Piece other) {
		if (width < 0 || width > this.width - 1) {
			throw new IllegalArgumentException();
		} else if (height < 0 || height > this.height - 1) {
			throw new IllegalArgumentException();
		} else if (current == null || other == null || current == other 
					|| (current != Piece.WHITE && current != Piece.BLACK) 
					|| (other != Piece.WHITE && other != Piece.BLACK)) {
			throw new IllegalArgumentException();
		}
		
		board[height][width] = current;
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				 if (moveInDirection(width, height, j, i, current, other)) {
					boolean result = false;
					
					for (int x = 1; x <= Math.max(this.width, this.height) && !result; x++) {
						board[height + (x * i)][width + (x * j)] = current;
						
						if (board[height + ((x + 1) * i)][width + ((x + 1) * j)] == current) {
							result = true;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns {@code true} if there is a valid path to move from a position with the first given parameters 
	 * in direction the position with the second given parameters, {@code false} otherwise. 
	 * A valid path is a path, which begins and ends with a piece with the color of the given current piece and 
	 * between these two pieces there can be one or more pieces with the color of the given other piece.
	 * @param width1 width of the first position to be checked
	 * @param height1 height of the first position to be checked
	 * @param width2 width of the second position to be checked
	 * @param height2 height of the second position to be checked
	 * @param current the current piece to be checked
	 * @param other the other piece to be checked
	 * @return {@code true} if there is a valid path to move from a position with the first given parameters 
	 * in direction the position with the second given parameters, {@code false} otherwise
	 */
	private boolean moveInDirection(int width1, int height1, int width2, int height2, Piece current, Piece other) {
		boolean result = false;
		
		if ((width1 + width2) >= 0 && (width1 + width2) <= (this.width - 1) 
					&& (height1 + height2) >= 0 && (height1 + height2) <= (this.height - 1) 
					&& board[height1 + height2][width1 + width2] == other) {
			boolean quit = false;
			
			for (int k = 2; k <= Math.max(this.width, this.height) && !quit; k++) {
				if (width1 + (k * width2) >= this.width || height1 + (k * height2) >= this.height 
							|| width1 + (k * width2) < 0 || height1 + (k * height2) < 0 
							|| board[height1 + (k * height2)][width1 + (k * width2)] == Piece.HOLE 
							|| board[height1 + (k * height2)][width1 + (k * width2)] == Piece.FREE) {
					quit = true;
				} else if (board[height1 + (k * height2)][width1 + (k * width2)] == current) {
					result = true;
				}
			}
		}
		
		return result;
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
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (possiblePositionToSetPiece(j, i, current, other)) {
					result = true;
				}
			}
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
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Integer[] position = new Integer[2];
				
				if (possiblePositionToSetPiece(i, j, current, other)) {
					position[0] = i;
					position[1] = j;
					possibleMoves.add(position);
				}
			}
		}
		
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
		
		int count = 0;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board[i][j] == piece) {
					count++;
				}
			}
		}
		
		return count;
	}
	
}