import java.util.HashMap;
import java.util.Map;

/**
 * This class models a student of the praktomat. A student is represented by a name, an id and a tutor. 
 * It stores the student's solutions of the different tasks in a map with key {@code Task} 
 * and value {@code Solution}. A student can submit only one solution for each task.
 * @author Delyan Nikolov
 */
public class Student implements AverageGrade, Comparable<Student> {

	/** name of the student */
	private String name;

	/** id of the student */
	private int studentNumber;
	
	/** tutor of the student */
	private Tutor tutor;
	
	/** map with key {@code Task} and value {@code Solution} */
	private Map<Task, Solution> tasksAndSolutions = new HashMap<Task, Solution>();
	
	/**
	 * Constructs a new student with the given name and id.
	 * @param name name of the student
	 * @param studentNumber id of the student
	 */
	public Student(String name, int studentNumber) {
		if (name == null) {
			throw new IllegalArgumentException();
		} else if (studentNumber < 10000 || studentNumber > 99999) {
			throw new IllegalArgumentException();
		}
		
		this.name = name;
		this.studentNumber = studentNumber;
	}
	
	/**
	 * Returns this student id.
	 * @return this student id
	 */
	public int getStudentNumber() {
		return studentNumber;
	}
	
	/**
	 * Returns this student's tutor.
	 * @return this student's tutor
	 */
	public Tutor getTutor() {
		return tutor;
	}
	
	/**
	 * Returns this map with key {@code Task} and value {@code Solution}.
	 * @return this map with key {@code Task} and value {@code Solution}
	 */
	public Map<Task, Solution> getTasksAndSolutions() {
		return tasksAndSolutions;
	}
	
	/**
	 * Sets the tutor of this student.
	 * @param tutor the tutor of this student
	 */
	public void setTutor(Tutor tutor) {
		if (tutor == null) {
			throw new IllegalArgumentException();
		}
		
		this.tutor = tutor;
	}
	
	/**
	 * Returns {@code true} if this map contains a mapping for the specified key, {@code false} otherwise.
	 * @param task the specified key in this map
	 * @return {@code true} if this map contains a mapping for the specified key, {@code false} otherwise
	 */
	public boolean containsSolution(Task task) {
		if (task == null) {
			throw new IllegalArgumentException();
		}
		
		boolean result = false;
		
		if (tasksAndSolutions.containsKey(task)) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Associates the specified value with the specified key in this map.
	 * @param task the specified key in this map
	 * @param solution the specified value in this map
	 */
	public void addSolution(Task task, Solution solution) {
		if (task == null) {
			throw new IllegalArgumentException();
		} else if (solution == null) {
			throw new IllegalArgumentException();
		}
		
		tasksAndSolutions.put(task, solution);
		task.addSolution(solution);
	}
	
	/**
     * Returns the average grade of this student. The average grade is a 
     * non-negative double value. A negative value (e.g. -1) is returned,
     * if no average grade for the current student exists.  
     * @return average grade of this student, or a negative number 
     * (e.g. -1) if no average grade exists.
     */
	public double averageGrade() {
		double result = 0;
		int count = 0;
		
		for (Solution s : tasksAndSolutions.values()) {
			if (s.getCorrection() != null) {
				result += s.getCorrection().getGrade();
				count++;
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
	 * Compares this student and the given student by their student id. 
	 * Returns a negative integer or a positive integer as the 
	 * id of this student is less than or greater than the 
	 * id of the given student. 
	 * @param s the given student to be compared
	 * @return a negative integer or a positive integer as the 
	 * id of this student is less than or greater than the 
	 * id of the given student
	 */
	public int compareTo(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		} 
		
		int result = -1;
		
		if (studentNumber > s.studentNumber) {
			result = 1;
		}
		
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
		
		if (obj instanceof Student) {
			Student other = (Student) obj;
			
			if (this.getStudentNumber() == other.getStudentNumber()) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this student.
	 * @return a string representation of this student
	 */
	@Override
	public String toString() {
		return "(" + studentNumber + "," + name + ")";
	}
	
}