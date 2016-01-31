import java.util.Comparator;

/**
 * This class models a comparator for students of the praktomat. It compares students by their average 
 * grades. If two students have the same average grade, it compares these students by their student id.
 * @author Delyan Nikolov
 */
public class SortStudentsByAverageGrade implements Comparator<Student> {

	/**
	 * Compares two students by their average grade. If they are equal, compares them by their 
	 * student id. Returns a positive integer, if the first student's average grade is -1 or 
	 * it is greater than the second student's average grade and a negative integer, if the second 
	 * student's average grade is -1 or it is greater than the first student's average grade. If 
	 * they are both equal, returns a negative integer or a positive integer as the id of the first 
	 * student is less than or greater than the id of the second student.
	 * @param s1 the first student to be compared
	 * @param s2 the second student to be compared
	 * @return a positive integer, if the first student's average grade is -1 or it is greater 
	 * than the second student's average grade and a negative integer, if the second student's 
	 * average grade is -1 or it is greater than the first student's average grade. If they are 
	 * both equal, returns a negative integer or a positive integer as the id of the first student is 
	 * less than or greater than the id of the second student.
	 */
	public int compare(Student s1, Student s2) {
		if (s1 == null) {
			throw new IllegalArgumentException();
		} else if (s2 == null) {
			throw new IllegalArgumentException();
		}
		
		int result = 0;
		
		if (s1.averageGrade() == -1 && s2.averageGrade() == -1) {
			result = s1.compareTo(s2);
		} else if (s1.averageGrade() == -1) {
			result = 1;
		} else if (s2.averageGrade() == -1) {
			result = -1;
		} else {
			double s1Grade = (int) 100 * (s1.averageGrade() + 0.005);
			double s2Grade = (int) 100 * (s2.averageGrade() + 0.005);
			
			if (s1Grade != s2Grade) {
				result = (int) (s1Grade - s2Grade);
			} else {
				result = s1.compareTo(s2);
			}
		}	
		
		return result;
	}
	
	/**
	 * Returns {@code true} only if the specified object is also a comparator and 
	 * it imposes the same ordering as this comparator, {@code false} otherwise. 
	 * @param obj the given object to be checked
	 * @return {@code true} only if the specified object is also a comparator and 
	 * it imposes the same ordering as this comparator, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof SortStudentsByAverageGrade) {
			SortStudentsByAverageGrade other = (SortStudentsByAverageGrade) obj;
			
			if (this == other) {
				result = true;
			}
		}
		
		return result;
	}
	
}