import java.util.ArrayList;
import java.util.List;

/**
 * This class models a tutor of the praktomat. A tutor is represented by a name and a list of students.
 * @author Delyan Nikolov
 */
public class Tutor implements AverageGrade, Comparable<Tutor> {

	/** name of the tutor */
	private String name;
	
	/** a list of the students, who are associated with this tutor */
	private List<Student> students = new ArrayList<Student>();
	
	/**
	 * Constructs a new tutor with the given name.
	 * @param name name of the tutor
	 */
	public Tutor(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		
		this.name = name;
	}
	
	/**
	 * Returns this tutor's name.
	 * @return this tutor's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns this tutor's list of students.
	 * @return this tutor's list of students
	 */
	public List<Student> getStudents() {
		return students;
	}
	
	/**
	 * Adds a new student to this tutor's list of students.
	 * @param currentStudent the given student to be added
	 */
	public void addStudent(Student currentStudent) {
		if (currentStudent == null) {
			throw new IllegalArgumentException();
		}
		
		students.add(currentStudent);
	}
	
	/**
	 * Returns the amount of corrections, this tutor has to review. If a student has not 
	 * submitted a solution, the tutor does not need to make a review.
	 * @return the amount of corrections, this tutor has to review
	 */
	public int missingReviews() {
		int count = 0;
		
		for (Student s : students) {
			for (Solution t : s.getTasksAndSolutions().values()) {
				if (t.getCorrection() == null) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	/**
     * Returns the average grade of this tutor. The average grade is a 
     * non-negative double value. A negative value (e.g. -1) is returned,
     * if no average grade for the current tutor exists.  
     * @return average grade of this tutor, or a negative number 
     * (e.g. -1) if no average grade exists.
     */
	public double averageGrade() {
		double result = 0;
		int count = 0;
		
		for (Student s : students) {
			for (Solution t : s.getTasksAndSolutions().values()) {
				if (t.getCorrection() != null) {
					result += t.getCorrection().getGrade();
					count++;
				}
			}
		}
		
		if (count != 0) {
			result = result / count;
		} else {
			result = -1;
		}
		
		return result;
	}	
	
	/**
	 * Compares this tutor and tutor t by their names. Returns a value less than 0 
	 * if this tutor's name is lexicographically less than the given tutor's name 
	 * and a value greater than 0 if this tutor's name is lexicographically greater 
	 * than the given tutor's name. 
	 * @param t the given tutor to be compared
	 * @return a value less than 0 if this tutor's name is lexicographically less  
	 * than the given tutor's name and a value greater than 0 if this tutor's name is 
	 * lexicographically greater than the given tutor's name
	 */
	public int compareTo(Tutor t) {
		if (t == null) {
			throw new IllegalArgumentException();
		}
		
		int result = this.name.compareTo(t.name);
		
		return result;
	}
	
	/**
	 * Returns {@code true} if this object is the same as the obj argument, {@code false} otherwise. 
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj argument, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Tutor) {
			Tutor other = (Tutor) obj;
			
			if (this.getName().equals(other.getName())) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this tutor.
	 * @return a string representation of this tutor
	 */
	@Override
	public String toString() {
		return name;
	}
	
}