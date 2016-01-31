import java.util.List;

/**
 * This class implements a simple shell to test the functionalities of this Othello implementation.
 * @author Delyan Nikolov
 */
public class Shell {

	/** the prompt of this shell */
	private static final String PROMPT = "othello> ";

	/** command to start a new Othello game */
	private static final String CMD_NEW_GAME = "newGame";

	/** command to add a hole area in the Othello game */
	private static final String CMD_HOLE = "hole";

	/** command to move a piece in the Othello game */
	private static final String CMD_MOVE = "move";

	/** command to print the board of the Othello game */
	private static final String CMD_PRINT = "print";

	/** command to abort the Othello game */
	private static final String CMD_ABORT = "abort";

	/** command to show the possible moves in the Othello game */
	private static final String CMD_POSSIBLE_MOVES = "possibleMoves";

	/** command to terminate the shell */
	private static final String CMD_QUIT = "quit";
	
	/** shows whether a hole are can be added */
	private static boolean possibleHoleArea = false;
	
	/** the difference between 0 and the ASCII value for an upper case letter letter */
	private static final int ASCII_VALUE = 65;
	
	/** an Othello game instance */
	private static Othello game;
	
	/**
	 * Private constructor.
	 */
	private Shell() {
	}

	/**
	 * Prints a string representation on a new line.
	 * @param s the represented string to be printed on a new line
	 */
	private static void println(String s) {
		System.out.println(s);
	}

	/**
	 * main method - implements the shell
	 * @param args command-line arguments - not used here!
	 */
	public static void main(String[] args) {
		boolean quit = false;
		while (!quit) {
			final String[] tokens = Terminal.askString(PROMPT).trim().split("\\s+");
			final String cmd = tokens[0];
			if (CMD_NEW_GAME.equals(cmd)) {
				if (game == null) {
					if (tokens.length >= 3 && tokens.length <= 4) {
						newGame(tokens);
					} else {
						error("Wrong number of arguments. Must provide integer arguments <width>, <height>.");
					}
				} else {
					error("There is already an active game.");
				}
			} else if (CMD_HOLE.equals(cmd)) {
				if (game != null) {
					if (tokens.length == 2) {
						hole(tokens);
					} else {
						error("Wrong number of arguments. Must provide <rectangle> as argument.");
					}
				} else {
					error("No active game.");
				}
			} else if (CMD_MOVE.equals(cmd)) {
				if (game != null) {
					if (tokens.length == 2) {
						move(tokens);
					} else {
						error("Wrong number of arguments. Must provide <position> as argument.");
					}
				} else {
					error("No active game.");
				}
			} else if (CMD_PRINT.equals(cmd)) {
				if (game != null) {
					if (tokens.length == 1) {
						print();
					} else {
						error("No arguments required.");
					}
				} else {
					error("No active game.");
				}
			} else if (CMD_ABORT.equals(cmd)) {
				if (game != null) {
					if (tokens.length == 1) {
						endGame();
					} else {
						error("No arguments required.");
					}
				} else {
					error("No active game.");
				}
			} else if (CMD_POSSIBLE_MOVES.equals(cmd)) {
				if (game != null) {
					if (tokens.length == 1) {
						possibleMoves();
					} else {
						error("No arguments required.");
					}
				} else {
					error("No active game.");
				}
			} else if (CMD_QUIT.equals(cmd)) {
				if (tokens.length == 1) {
					quit = true;
				} else {
					error("No arguments required.");
				}
			} else {
				error("Unknown command: '" + cmd + "'");
			}
		}
	}

	/**
	 * Starts a new Othello game.
	 * @param tokens command and parameters
	 */
	private static void newGame(String[] tokens) {
		assert tokens != null;
		assert tokens.length >= 3;
		assert tokens.length <= 4;
		
		int width = 0;
		int height = 0;
		boolean result = false;
			
		try {
			width = Integer.parseInt(tokens[1]);
			height = Integer.parseInt(tokens[2]);
			result = true;
		} catch (NumberFormatException e) {
			error("Must provide integer arguments <widht>, <height>.");
		}
			
		if (width >= 2 && width <= 26 && width % 2 == 0) {
			if (height >= 2 && height <= 98 && height % 2 == 0) {
				if (tokens.length == 3) {
					game = new Othello(width, height);
					game.setStandardBoard(width, height);
					possibleHoleArea = true;
					validMoves();
				} else if (tokens.length == 4) {
					final String[] positions = tokens[3].split(",");
						
					if (validBoard(width, height, positions)) {
						game = new Othello(width, height);
							
						for (int i = 0; i < positions.length; i++) {
							for (int j = 0; j < positions[i].length(); j++) {
								if (positions[i].charAt(j) == '#') {
									game.getBoard().getBoard()[i][j] = Piece.HOLE;
								} else if (positions[i].charAt(j) == '-') {
									game.getBoard().getBoard()[i][j] = Piece.FREE;
								} else if (positions[i].charAt(j) == 'B') {
									game.getBoard().getBoard()[i][j] = Piece.BLACK;
								} else if (positions[i].charAt(j) == 'W') {
									game.getBoard().getBoard()[i][j] = Piece.WHITE;
								}
							}
						}
							
						possibleHoleArea = true;
						validMoves();
					} else {
						error("The board is not valid.");
					}
				}
			} else {
				if (result) {
					error("The height of the board must be an even integer between 2 and 98.");
				}
			}
		} else {
			if (result) {
				error("The widht of the board must be an even integer between 2 and 26.");
			}
		}
	}

	/**
	 * Adds a hole area in the Othello game if it is possible.
	 * @param tokens command and parameters
	 */
	private static void hole(String[] tokens) {
		assert tokens != null;
		assert tokens.length == 2;
		
		if (possibleHoleArea) {
			final String[] position = tokens[1].split(":");
			
			if (position.length == 2 && position[0].matches("[A-Z][0-9]*") && position[1].matches("[A-Z][0-9]*")) {
				int width1 = position[0].codePointAt(0) - ASCII_VALUE;
				int height1 = Integer.parseInt(position[0].substring(1)) - 1;
				int width2 = position[1].codePointAt(0) - ASCII_VALUE;
				int height2 = Integer.parseInt(position[1].substring(1)) - 1;
					
				if (width1 >= 0 && width1 < game.getBoard().getWidth() && height1 >= 0 
								&& height1 < game.getBoard().getHeight() && width2 >= 0 
								&& width2 < game.getBoard().getWidth() && height2 >= 0 
								&& height2 < game.getBoard().getHeight() && width1 <= width2 
								&& height1 <= height2) {
					if (game.possibleHoleArea(width1, height1, width2, height2)) {
						game.addHoleArea(width1, height1, width2, height2);
						validMoves();
					} else {
						error("Cannot add hole area.");
					}
				} else {
					error("Provide valid positions to add hole area.");
				}
			} else {
				error("Provide valid positions to add hole area.");
			}
		} else {
			error("Cannot add hole area. Game has already started!");
		}
	}

	/**
	 * Moves a piece in the Othello game if it is possible.
	 * @param tokens command and parameters
	 */
	private static void move(String[] tokens) {
		assert tokens != null;
		assert tokens.length == 2;
		
		final String position = tokens[1];
			
		if (tokens[1].matches("[A-Z][0-9]*")) {
			int width = position.codePointAt(0) - ASCII_VALUE;
			int height = Integer.parseInt(position.substring(1)) - 1;
				
			if (width >= 0 && width < game.getBoard().getWidth() && height >= 0 
																	&& height < game.getBoard().getHeight()) {
				if (game.possiblePositionToSetPiece(width, height, game.getCurrentPlayer().getPiece(), 
														game.getOtherPlayer().getPiece())) {
					game.setPiece(width, height, game.getCurrentPlayer().getPiece(), game.getOtherPlayer().getPiece());
					possibleHoleArea = false;
					validMoves();
				} else {
					System.out.println("Move not possible.");
				}
			} else {
				error("Provide a valid position to move a piece!");
			}	
		} else {
			error("Provide a valid position as argument!");
		}
	}

	/**
	 * Prints the board of the Othello game.
	 */
	private static void print() {
		for (int i = 0; i < game.getBoard().getHeight(); i++) {
			String s = "";
				
			for (int j = 0; j < game.getBoard().getWidth(); j++) {
				if (game.getBoard().getBoard()[i][j] == Piece.HOLE) {
					s += "#";
				} else if (game.getBoard().getBoard()[i][j] == Piece.FREE) {
					s += "-";
				} else if (game.getBoard().getBoard()[i][j] == Piece.BLACK) {
					s += "B";
				} else if (game.getBoard().getBoard()[i][j] == Piece.WHITE) {
					s += "W";
				}
			}
				
			System.out.println(s);
		}
			
		System.out.println("turn: " + game.getCurrentPlayer().toString());
	}

	/**
	 * Shows the possible moves in the Othello game
	 */
	private static void possibleMoves() {
		List<Integer[]> possibleMoves = game.allPossibleMoves(game.getCurrentPlayer().getPiece(), 
																	game.getOtherPlayer().getPiece());
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < possibleMoves.size(); i++) {
			int width = possibleMoves.get(i)[0] + ASCII_VALUE;
			int height = possibleMoves.get(i)[1] + 1;
			buffer.append((char) width + "" + height + ",");
		}
		
		System.out.println("Possible moves: " + buffer.substring(0, buffer.length() - 1));
	}
	
	/**
	 * Returns {@code true} if the board with the given parameters is valid, {@code false} otherwise.
	 * @param width the given width of the Othello game
	 * @param height the given height of the Othello game
	 * @param positions the different positions in the Othello game
	 * @return {@code true} if the board with the given parameters is valid, {@code false} otherwise
	 */
	private static boolean validBoard(int width, int height, String[] positions) {
		boolean result = true;
		
		if (positions.length == height) {
			for (int i = 0; i < positions.length; i++) {
				if (positions[i].length() == width) {
					for (int j = 0; j < positions[i].length(); j++) {
						if (positions[i].charAt(j) != '#' && positions[i].charAt(j) != '-' 
													&& positions[i].charAt(j) != 'B' && positions[i].charAt(j) != 'W') {
							result = false;
						}
					}
				} else {
					result = false;
				}
			}
		} else {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Checks whether the both players have no valid moves or whether only the current player 
	 * has no valid moves. If the both players have no valid moves, ends the Othello game. 
	 * If only the current player has no valid moves, the other player becomes the current player.
	 */
	private static void validMoves() {
		if (!game.possibleMove(game.getCurrentPlayer().getPiece(), game.getOtherPlayer().getPiece()) 
						&& !game.possibleMove(game.getOtherPlayer().getPiece(), game.getCurrentPlayer().getPiece())) {
			endGame();
		} else if (!game.possibleMove(game.getCurrentPlayer().getPiece(), game.getOtherPlayer().getPiece())) {
			System.out.println(game.getCurrentPlayer().toString() + " passes.");
			game.switchPlayers();
		}
	}
	
	/**
	 * Ends the Othello game. The winner is the player with the most pieces on the board. 
	 * If the both players have the same amount of pieces, the game ends in a draw.
	 */
	private static void endGame() {
		if (game.numberOfPieces(game.getCurrentPlayer().getPiece()) 
															> game.numberOfPieces(game.getOtherPlayer().getPiece())) {
			System.out.println("Game Over! " + game.getCurrentPlayer().toString() + " has won (" 
													+ game.numberOfPieces(game.getCurrentPlayer().getPiece()) + ":" 
													+ game.numberOfPieces(game.getOtherPlayer().getPiece()) + ")!");
		} else if (game.numberOfPieces(game.getCurrentPlayer().getPiece()) 
															< game.numberOfPieces(game.getOtherPlayer().getPiece())) {
			System.out.println("Game Over! " + game.getOtherPlayer().toString() + " has won (" 
													+ game.numberOfPieces(game.getOtherPlayer().getPiece()) + ":" 
													+ game.numberOfPieces(game.getCurrentPlayer().getPiece()) + ")!");
		} else {
			System.out.println("Game has ended in a draw.");
		}
		
		game = null;
	}
	
	/**
	 * Prints an error message.
	 * @param err error message to print
	 */
	private static void error(String err) {
		println("Error! " + err);
	}

}