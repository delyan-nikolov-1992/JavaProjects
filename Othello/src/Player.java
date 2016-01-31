/**
 * This class models a player in the Othello game. A player is represented by a name and 
 * a piece to play with.
 * @author Delyan Nikolov
 */
public class Player {

	/** name of the player */
	private String name;
	
	/** piece of the player to play with */
	private Piece piece;
	
	/**
	 * Constructs a new player with the given name and piece to play with.
	 * @param name name of the player
	 * @param piece piece of the player to play with
	 */
	public Player(String name, Piece piece) {
		if (name == null) {
			throw new IllegalArgumentException();
		} else if (piece == null || piece == Piece.FREE || piece == Piece.HOLE) {
			throw new IllegalArgumentException();
		}
		
		this.name = name;
		this.piece = piece;
	}
	
	/**
	 * Returns this player's name.
	 * @return this player's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns this player's piece to play with.
	 * @return this player's piece to play with
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Returns {@code true} if this object is the same as the obj argument, {@code false} otherwise. 
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj argument, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Player) {
			Player other = (Player) obj;
			
			if (this.getName().equals(other.getName()) && this.getPiece().equals(other.getPiece())) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this player.
	 * @return a string representation of this player
	 */
	@Override
	public String toString() {
		return name;
	}
	
}