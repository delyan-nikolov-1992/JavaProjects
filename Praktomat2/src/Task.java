import java.util.ArrayList;
import java.util.List;

/**
 * This class models a task of the praktomat. A task is represented by a text, an id 
 * and a list of solutions.
 * @author Delyan Nikolov
 */
public class Task implements AverageGrade {

	/** text of the task */
	private String text;

	/** id of the task */
	private int taskNumber;

	/** a list of the solutions for this task */
	private List<Solution> solutions = new ArrayList<Solution>();

	/**
	 * Constructs a new task with the given text.
	 * @param text text of the task
	 */
	public Task(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		
		this.text = text;
	}

	/**
	 * Returns this task id.
	 * @return this task id
	 */
	public int getTaskNumber() {
		return taskNumber;
	}

	/**
	 * Returns this task's list of solutions.
	 * @return this task's list of solutions
	 */
	public List<Solution> getSolutions() {
		return solutions;
	}

	/**
	 * Sets the id of this task.
	 * @param taskNumber the id of this task.
	 */
	public void setTaskNumber(int taskNumber) {
		if (taskNumber < 1) {
			throw new IllegalArgumentException();
		}
		
		this.taskNumber = taskNumber;
	}

	/**
	 * Adds a new solution to the list of solutions of this task.
	 * @param solution the given solution to be added
	 */
	public void addSolution(Solution solution) {
		if (solution == null) {
			throw new IllegalArgumentException();
		}
		
		solutions.add(solution);
	}

	/**
	 * Returns the amount of the solutions, which have received a correction.
	 * @return the amount of the solutions, which have received a correction
	 */
	public int reviewSolutions() {
		int count = 0;
		
		for (Solution s : solutions) {
			if (s.getCorrection() != null) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Returns the amount of the grades.
	 * @param grade the given grade to be checked
	 * @return the amount of the grades
	 */
	public int countGrade(int grade) {
		if (grade < 1 || grade > 5) {
			throw new IllegalArgumentException();
		}
		
		int count = 0;
		
		for (Solution s : solutions) {
			if (s.getCorrection() != null && s.getCorrection().getGrade() == grade) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
     * Returns the average grade of this task. The average grade is a 
     * non-negative double value. A negative value (e.g. -1) is returned,
     * if no average grade for the current task exists.  
     * @return average grade of this task, or a negative number 
     * (e.g. -1) if no average grade exists.
     */
	public double averageGrade() {
		double result = 0;
		int count = 0;
		
		for (Solution s : solutions) {
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
	 * Returns {@code true} if this object is the same as the obj argument, {@code false} otherwise. 
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj argument, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Task) {
			Task other = (Task) obj;
			
			if (this.getTaskNumber() == other.getTaskNumber()) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this task.
	 * @return a string representation of this task
	 */
	@Override
	public String toString() {
		return "task id(" + taskNumber + "): " + text;
	}

}