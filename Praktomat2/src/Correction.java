/**
 * This class models a correction to a solution of the praktomat. A correction is represented by a grade and a text. 
 * @author Delyan Nikolov
 */
public class Correction {
	
	/** grade of the correction */
	private int grade;

	/** text of the correction */
	private String text;
	
	/**
	 * Constructs a new correction with the given grade and text.
	 * @param grade grade of the correction
	 * @param text text of the correction
	 */
	public Correction(int grade, String text) {
		if (grade < 1 || grade > 5) {
			throw new IllegalArgumentException();
		} else if (text == null) {
			throw new IllegalArgumentException();
		}
		
		this.grade = grade;
		this.text = text;
	}
	
	/**
	 * Returns this correction's grade.
	 * @return this correction's grade
	 */
	public int getGrade() {
		return grade;
	}
	
	/**
	 * Returns this correction's text.
	 * @return this correction's text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Returns {@code true} if this object is the same as the obj argument, {@code false} otherwise. 
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj argument, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Correction) {
			Correction other = (Correction) obj;
			
			if (this.getGrade() == other.getGrade() && this.getText().equals(other.getText())) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this correction.
	 * @return a string representation of this correction
	 */
	@Override
	public String toString() {
		return "" + grade;
	}
	
}