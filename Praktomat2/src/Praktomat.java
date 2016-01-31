import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the praktomat itself. It adds and stores students, tutors 
 * and tasks to this praktomat. It sorts students by their student id and their 
 * average grades and it sorts tutors by their names.
 * @author Delyan Nikolov
 */
public class Praktomat {

	/** the last tutor, added to the praktomat */
	private Tutor currentTutor;
	
	/** a list of the students, who are represented by this praktomat */
	private List<Student> students = new ArrayList<Student>();
	
	/** a list of the tutors, who are represented by this praktomat */
	private List<Tutor> tutors = new ArrayList<Tutor>();
	
	/** a list of the tasks, which are represented by this praktomat */
	private List<Task> tasks = new ArrayList<Task>();
	
	/**
	 * Constructs a new praktomat.
	 */
	public Praktomat() {
	}
	
	/**
	 * Returns this praktomat's current tutor.
	 * @return this praktomat's current tutor
	 */
	public Tutor getCurrentTutor() {
		return currentTutor;
	}
	
	/**
	 * Returns this praktomat's list of students.
	 * @return this praktomat's list of students
	 */
	public List<Student> getStudents() {
		return students;
	}
	
	/**
	 * Returns this praktomat's list of tutors.
	 * @return this praktomat's list of tutors
	 */
	public List<Tutor> getTutors() {
		return tutors;
	}
	
	/**
	 * Returns this praktomat's list of tasks.
	 * @return this praktomat's list of tasks
	 */
	public List<Task> getTasks() {
		return tasks;
	}
	
	/**
	 * Adds the given student to this praktomat's list of students. The current tutor 
	 * of this praktomat becomes the tutor of the given student.
	 * @param currentStudent the given student to be added
	 */
	public void addStudent(Student currentStudent) {
		if (currentStudent == null) {
			throw new IllegalArgumentException();
		}
		
		students.add(currentStudent);
		currentStudent.setTutor(currentTutor);
		currentTutor.addStudent(currentStudent);
	}
	
	/**
	 * Adds a new tutor with the given name to this praktomat's list  
	 * of tutors and he becomes a current tutor. If there is a 
	 * tutor with the given name, he becomes the current tutor 
	 * and there is added no new tutor to the praktomat.
	 * @param tutorName the given name to be checked
	 */
	public void addTutor(String tutorName) {
		if (tutorName == null) {
			throw new IllegalArgumentException();
		}
		
		if (findTutor(tutorName) == null) {
			Tutor tutor = new Tutor(tutorName);
			tutors.add(tutor);
			this.currentTutor = tutor;
		} else {
			this.currentTutor = findTutor(tutorName);
		}
	}
	
	/**
	 * Adds the given task to this praktomat and returns the id of the given task.
	 * @param currentTask the given task to be added
	 * @return the id of the given task
	 */
	public int addTask(Task currentTask) {
		if (currentTask == null) {
			throw new IllegalArgumentException();
		}
		
		tasks.add(currentTask);
		int taskNumber = tasks.size();
		currentTask.setTaskNumber(taskNumber);
		
		return taskNumber;
	}
	
	/**
	 * Finds and returns a student of the praktomat with the given id, if existent.
	 * If no student with the given id exists, {@code null} is returned.
	 * @param studentNumber student id to search in praktomat
	 * @return a student of the praktomat with the given id. If no student 
	 * with the given id exists, {@code null} is returned.
	 */
	public Student findStudent(int studentNumber) {
		if (studentNumber < 10000 || studentNumber > 99999) {
			throw new IllegalArgumentException();
		}
		
		Student sameStudent = null;
		
		for (Student s : students) {
			if (s.getStudentNumber() == studentNumber) {
				sameStudent = s;
			}
		}
		
		return sameStudent;
	}
	
	/**
	 * Finds and returns a tutor of the praktomat with the given name, if existent.
	 * If no tutor with the given name exists, {@code null} is returned.
	 * @param tutorName tutor's name to search in praktomat
	 * @return a tutor of the praktomat with the given name. If no tutor 
	 * with the given name exists, {@code null} is returned.
	 */
	private Tutor findTutor(String tutorName) {
		assert tutorName != null;
		
		Tutor sameTutor = null;
		
		for (Tutor t : tutors) {
			if (t.getName().equals(tutorName)) {
				sameTutor = t;
			}
		}
		
		return sameTutor;
	}
	
	/**
	 * Finds and returns a task of the praktomat with the given id, if existent.
	 * If no task with the given id exists, {@code null} is returned.
	 * @param taskNumber task id to search in praktomat
	 * @return a task of the praktomat with the given id. If no task 
	 * with the given id exists, {@code null} is returned.
	 */
	public Task findTask(int taskNumber) {
		if (taskNumber < 1) {
			throw new IllegalArgumentException();
		}
		
		Task sameTask = null;
		
		for (Task t : tasks) {
			if (t.getTaskNumber() == taskNumber) {
				sameTask = t;
			}
		}
		
		return sameTask;
	}
	
	/**
	 * Submits a solution to a task with the given id for a student with the given id. 
	 * The solution is represented by the given text.
	 * @param taskNumber the given task id to be checked
	 * @param studentNumber the given student id to be checked
	 * @param text the given text
	 */
	public void submitSolution(int taskNumber, int studentNumber, String text) {
		if (taskNumber < 1 || taskNumber > tasks.size()) {
			throw new IllegalArgumentException();
		} else if (studentNumber < 10000 || studentNumber > 99999) {
			throw new IllegalArgumentException();
		} else if (text == null) {
			throw new IllegalArgumentException();
		}
		
		Solution solution = new Solution(text);
		findStudent(studentNumber).addSolution(findTask(taskNumber), solution);
	}
	
	/**
	 * Reviews the solution to the task with the given id, which is submitted by the student 
	 * with the given id with a correction.
	 * @param taskNumber the given task id to be checked
	 * @param studentNumber the given student id to be checked
	 * @param correction the given correction
	 */
	public void reviewSolution(int taskNumber, int studentNumber, Correction correction) {
		if (taskNumber < 1 || taskNumber > tasks.size()) {
			throw new IllegalArgumentException();
		} else if (studentNumber < 10000 || studentNumber > 99999) {
			throw new IllegalArgumentException();
		} else if (correction == null) {
			throw new IllegalArgumentException();
		}
		
		findStudent(studentNumber).getTasksAndSolutions().get(findTask(taskNumber)).setCorrection(correction);
	}
	
	/**
	 * Sorts the list of students to this praktomat into ascending order by their student id.
	 */
	public void sortStudents() {
		Collections.sort(students);
	}
	
	/**
	 * Sorts the list of students to this praktomat into ascending order by their average grades. 
	 * If their average grade is equal, sorts them into ascending order by their student id.
	 */
	public void sortStudentsAverageGrade() {
		Collections.sort(students, new SortStudentsByAverageGrade());
	}
	
	/**
	 * Sorts the list of tutors to this praktomat into ascending order by their names.
	 */
	public void sortTutors() {
		Collections.sort(tutors);
	}
	
}