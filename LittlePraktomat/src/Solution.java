/**
 * This class models a solution to a task of the praktomat. A solution is represented by a text 
 * and a correction, if there is one.
 * @author Delyan Nikolov
 */
public class Solution {
	
	/** text of the solution */
	private String text;
	
	/** correction of the solution */
	private Correction correction;
	
	/**
	 * Constructs a new solution with the given text.
	 * @param text text of the solution
	 */
	public Solution(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		
		this.text = text;
	}
	
	/**
	 * Returns this solution's text.
	 * @return this solution's text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Returns this soluton's correction.
	 * @return this solution's correction
	 */
	public Correction getCorrection() {
		return correction;
	}

	/**
	 * Sets the correction of this solution.
	 * @param correction the correction of this solution
	 */
	public void setCorrection(Correction correction) {
		if (correction == null) {
			throw new IllegalArgumentException();
		}
		
		this.correction = correction;
	}
	
	/**
	 * Returns {@code true} if this object is the same as the obj argument, {@code false} otherwise. 
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj argument, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Solution) {
			Solution other = (Solution) obj;
			
			if (this.getText().equals(other.getText()) && this.getCorrection().equals(other.getCorrection())) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this solution.
	 * @return a string representation of this solution
	 */
	@Override
	public String toString() {
		return text;
	}
	
}