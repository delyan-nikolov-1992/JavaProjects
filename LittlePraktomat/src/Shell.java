import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * This class implements a simple shell to test the functionalities of the praktomat.
 * @author Delyan Nikolov
 */
public class Shell {

	/** the prompt of this shell */
	private static final String PROMPT = "praktomat> ";

	/** command to add a new tutor to the praktomat */
	private static final String CMD_TUT = "tut";

	/** command to add a new student to the praktomat */
	private static final String CMD_STUD = "stud";

	/** command to add a new task to the praktomat */
	private static final String CMD_TASK = "task";

	/** command to list all the students of the praktomat */
	private static final String CMD_LIST_STUDENTS = "list-students";

	/** command to submit a solution for a task from a student of the praktomat */
	private static final String CMD_SUBMIT = "submit";

	/** command to review a solution for a task from a student of the praktomat */
	private static final String CMD_REVIEW = "review";

	/** command to list all the solutions for a task of the praktomat */
	private static final String CMD_LIST_SOLUTIONS = "list-solutions";

	/** command to list all the tasks and the existing solutions of the students to these tasks of the pratkomat */
	private static final String CMD_RESULTS = "results";

	/** command to list the summary of the tasks of the praktomat */
	private static final String CMD_SUMMARY_TASK = "summary-task";

	/** command to list the summary of the tutors of the praktomat */
	private static final String CMD_SUMMARY_TUTOR = "summary-tutor";

	/** command to list the summary of the students of the praktomat */
	private static final String CMD_SUMMARY_STUDENT = "summary-student";

	/** command to reset the praktomat */
	private static final String CMD_RESET = "reset";

	/** command to terminate the shell */
	private static final String CMD_QUIT = "quit";
	
	/** creates a DecimalFormat using the given pattern and symbols */
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00", 
																		DecimalFormatSymbols.getInstance(Locale.US));

	/**
	 * Private constructor.
	 */
	private Shell() {
		DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);
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
		Praktomat praktomat = new Praktomat();
		
		while (!quit) {
			final String[] tokens = Terminal.askString(PROMPT).trim().split("\\s+");
			final String cmd = tokens[0].toLowerCase();
			
			if (CMD_TUT.equals(cmd)) {
				if (tokens.length == 2) {
					tut(praktomat, tokens);
				} else {
					error("Wrong number of arguments. Must provide string argument <name>.");
				}
			} else if (CMD_STUD.equals(cmd)) {
				if (tokens.length == 3) {
					stud(praktomat, tokens);
				} else {
					error("Wrong number of arguments. Must provide string argument <name> and " 
																	+ "integer argument <student_id>.");
				}
			} else if (CMD_TASK.equals(cmd)) {
				if (tokens.length == 2) {
					task(praktomat, tokens);
				} else {
					error("Wrong number of arguments. Must provide string argument <text>.");
				}
			} else if (CMD_LIST_STUDENTS.equals(cmd)) {
				if (tokens.length == 1) {
					listStudents(praktomat);
				} else {
					error("No arguments required.");
				}
			} else if (CMD_SUBMIT.equals(cmd)) {
				if (tokens.length == 4) {
					submit(praktomat, tokens);
				} else {
					error("Wrong number of arguments. Must provide integer arguments <task_id>, <student_id> and " 
																	+ "string argument <text>.");
				}
			} else if (CMD_REVIEW.equals(cmd)) {
				if (tokens.length == 5) {
					review(praktomat, tokens);
				} else {
					error("Wrong number of arguments. Must provide integer arguments <task_id>, <student_id>, " 
																	+ "<grade> and string argument <text>.");
				}
			} else if (CMD_LIST_SOLUTIONS.equals(cmd)) {
				if (tokens.length == 2) {
					listSolutions(praktomat, tokens);
				} else {
					error("Wrong number of arguments. Must provide integer argument <task_id>.");
				}
			} else if (CMD_RESULTS.equals(cmd)) {
				if (tokens.length == 1) {
					results(praktomat);
				} else {
					error("No arguments required.");
				}
			} else if (CMD_SUMMARY_TASK.equals(cmd)) {
				if (tokens.length == 1) {
					summaryTask(praktomat);
				} else {
					error("No arguments required.");
				}
			} else if (CMD_SUMMARY_TUTOR.equals(cmd)) {
				if (tokens.length == 1) {
					summaryTutor(praktomat);
				} else {
					error("No arguments required.");
				}
			} else if (CMD_SUMMARY_STUDENT.equals(cmd)) {
				if (tokens.length == 1) {
					summaryStudent(praktomat);
				} else {
					error("No arguments required.");
				}
			} else if (CMD_RESET.equals(cmd)) {
				if (tokens.length == 1) {
					praktomat = new Praktomat();
				} else {
					error("No arguments required.");
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
	 * Adds a new tutor with the given string as name to the praktomat. 
	 * If there is a tutor with the given string as name, he is selected 
	 * and there is added no new tutor to the praktomat.
	 * @param praktomat praktomat to operate on
	 * @param tokens command and parameters
	 */
	private static void tut(Praktomat praktomat, String[] tokens) {
		assert praktomat != null;
		assert tokens != null;
		assert tokens.length == 2;
		
		final String tutorName = tokens[1];
		
		if (tutorName.matches("[a-z]+")) {
			praktomat.addTutor(tutorName);
		} else {
			error("Name of tutor must consist of lower case letters.");
		}
	}
	
	/**
	 * Adds a new student to the praktomat. If there is a student 
	 * with the given id, there is added no new student.
	 * @param praktomat praktomat to operate on
	 * @param tokens command and parameters
	 */
	private static void stud(Praktomat praktomat, String[] tokens) {
		assert praktomat != null;
		assert tokens != null;
		assert tokens.length == 3;
		
		if (!praktomat.getTutors().isEmpty()) {
			final String studentName = tokens[1];
			int studentNumber = 0;
			boolean result = false;
			
			try {
				studentNumber = Integer.parseInt(tokens[2]);
				result = true;
			} catch (NumberFormatException e) {
				error("Must provide integer argument <student_id>.");
			}
			
			if (studentName.matches("[a-z]+")) {
				if (studentNumber >= 10000 && studentNumber <= 99999) {
					if (praktomat.findStudent(studentNumber) == null) {
						final Student currentStudent = new Student(studentName, studentNumber);
						praktomat.addStudent(currentStudent);
					} else {
						error("a student with id " + studentNumber + " already exists.");
					}
				} else {
					if (result) {
						error("Student id must be between 10000 and 99999.");
					}
				}
			} else {
				error("Name of student must consist of lower case letters.");
			}
		} else {
			error("There are added no tutors in praktomat.");
		}
	}
	
	/**
	 * Adds a new task to the praktomat.
	 * @param praktomat praktomat to operate on
	 * @param tokens command and parameters
	 */
	private static void task(Praktomat praktomat, String[] tokens) {
		assert praktomat != null;
		assert tokens != null;
		assert tokens.length == 2;
		
		final String taskText = tokens[1];
		final Task currentTask = new Task(taskText);
		System.out.println("task id(" + praktomat.addTask(currentTask) + ")");
	}
	
	/**
	 * Lists all the students into ascending order by their id of the praktomat.
	 * @param praktomat praktomat to operate on
	 */
	private static void listStudents(Praktomat praktomat) {
		assert praktomat != null;
		
		if (!praktomat.getStudents().isEmpty()) {
			praktomat.sortStudents();
			
			for (Student s : praktomat.getStudents()) {
				System.out.println(s.toString() + ": " + s.getTutor().toString());
			}
		} else {
			error("There are added no students in praktomat.");
		}
	}
	
	/**
	 * Submits a solution for a task from a student of the praktomat.
	 * @param praktomat praktomat to operate on
	 * @param tokens command and parameters
	 */
	private static void submit(Praktomat praktomat, String[] tokens) {
		assert praktomat != null;
		assert tokens != null;
		assert tokens.length == 4;
		
		int taskNumber = 0;
		int studentNumber = 0;
		final String text = tokens[3];
		boolean result = false;
		
		try {
			taskNumber = Integer.parseInt(tokens[1]);
			studentNumber = Integer.parseInt(tokens[2]);
			result = true;
		} catch (NumberFormatException e) {
			error("Must provide integer arguments <task_id>, <student_id>.");
		}
		
		if (taskNumber > 0 && studentNumber >= 10000 && studentNumber <= 99999) {
			if (praktomat.findTask(taskNumber) != null) {
				if (praktomat.findStudent(studentNumber) != null) {
					if (!praktomat.findStudent(studentNumber).containsSolution(praktomat.findTask(taskNumber))) {
						praktomat.submitSolution(taskNumber, studentNumber, text);
					} else {
						error("A solution for student with id " + studentNumber + " for task with id " 
												+ taskNumber + " already exists.");
					}
				} else {
					error("a student with id " + studentNumber + " doesn't exist.");
				}
			} else {
				error("A task with id " + taskNumber + " doesn't exist.");
			}
		} else {
			if (result) {
				error("Task id must be positive and student id must be between 10000 and 99999.");
			}
		}
	}
	
	/**
	 * Reviews a solution for a task from a student of the praktomat.
	 * @param praktomat praktomat to operate on
	 * @param tokens command and parameters
	 */
	private static void review(Praktomat praktomat, String[] tokens) {
		assert praktomat != null;
		assert tokens != null;
		assert tokens.length == 5;
		
		int taskNumber = 0;
		int studentNumber = 0;
		int grade = 0;
		final String text = tokens[4];
		boolean result = false;
			
		try {
			taskNumber = Integer.parseInt(tokens[1]);
			studentNumber = Integer.parseInt(tokens[2]);
			grade = Integer.parseInt(tokens[3]);
			result = true;
		} catch (NumberFormatException e) {
			error("Must provide integer arguments <task_id>, <student_id>, <grade>.");
		}
		
		if (taskNumber > 0 && studentNumber >= 10000 && studentNumber <= 99999 && grade >= 1 && grade <= 5) {
			if (praktomat.findTask(taskNumber) != null) {
				if (praktomat.findStudent(studentNumber) != null) {
					if (praktomat.findStudent(studentNumber).containsSolution(praktomat.findTask(taskNumber))) {
						Correction correction = new Correction(grade, text);
						praktomat.reviewSolution(taskNumber, studentNumber, correction);
						System.out.println(praktomat.findStudent(studentNumber).getTutor().toString() 
									+ " reviewed " + praktomat.findStudent(studentNumber).toString() 
									+ " with grade " + grade);
					} else {
						error(praktomat.findStudent(studentNumber).toString() + " has not submitted a solution to " 
									+ "task id(" + praktomat.findTask(taskNumber).getTaskNumber() + ")");
					}
				} else {
					error("a student with id " + studentNumber + " doesn't exist.");
				}
			} else {
				error("A task with id " + taskNumber + " doesn't exist.");
			}
		} else {
			if (result) {
				error("Task id must be positive, student id must be between 10000 and 99999 " 
								+ "and grade must be between 1 and 5.");
			}
		}
	}
	
	/**
	 * Lists all the solutions of the students for a task of the praktomat.   
	 * The list is sorted into ascending order by the student id.
	 * @param praktomat praktomat to operate on
	 * @param tokens command and parameters
	 */
	private static void listSolutions(Praktomat praktomat, String[] tokens) {
		assert praktomat != null;
		assert tokens != null;
		assert tokens.length == 2;
			
		int taskNumber = 0;
		boolean result = false;
		
		try {
			taskNumber = Integer.parseInt(tokens[1]);
			result = true;
		} catch (NumberFormatException e) {
			error("Must provide integer argument <task_id>.");
		}
		
		if (taskNumber > 0) {
			if (praktomat.findTask(taskNumber) != null) {
				if (!praktomat.findTask(taskNumber).getSolutions().isEmpty()) {
					praktomat.sortStudents();
						
					for (Student s : praktomat.getStudents()) {
						if (s.containsSolution(praktomat.findTask(taskNumber))) {
							System.out.println(s.toString() + ": " 
											+ s.getTasksAndSolutions().get(praktomat.findTask(taskNumber)).toString());
						}
					}
				} else {
					error("There are no solutions for task with id " + taskNumber + ".");
				}
			} else {
				error("A task with id " + taskNumber + " doesn't exist.");
			}
		} else {
			if (result) {
				error("Task id must be positive and student id must be between 10000 and 99999.");
			}
		}
	}
	
	/**
	 * Lists all the tasks and the solutions of the students to these tasks. The list is  
	 * sorted into ascending order by the task id of the pratkomat. The students, who have  
	 * submitted solutions for each task, are sorted into ascending order by the their id.
	 * @param praktomat praktomat to operate on
	 */
	private static void results(Praktomat praktomat) {
		assert praktomat != null;
		
		praktomat.sortStudents();
		
		for (Task t : praktomat.getTasks()) {
			System.out.println(t.toString());
				
			for (Student s : praktomat.getStudents()) {
				if (s.containsSolution(t) && s.getTasksAndSolutions().get(t).getCorrection() != null) {
					System.out.println(s.getStudentNumber() + ": " 
											+ s.getTasksAndSolutions().get(t).getCorrection().toString());
				}
			}
		}
	}
	
	/**
	 * Lists the summary of the tasks of the praktomat. The list is sorted into ascending order by their id.
	 * @param praktomat praktomat to operate on
	 */
	private static void summaryTask(Praktomat praktomat) {
		assert praktomat != null;
		
		if (!praktomat.getTasks().isEmpty()) {
			for (Task t : praktomat.getTasks()) {
				System.out.println(t.toString());
				System.out.println("submitted: " + t.getSolutions().size());
				System.out.println("reviewed: " + t.reviewSolutions());
				String averageGrade = "-";
				   
				if (t.averageGrade() != -1) {
				    averageGrade = DECIMAL_FORMAT.format(t.averageGrade());
				}
				
				System.out.println("average grade: " + averageGrade);
				System.out.println("distribution: " + t.countGrade(1) + "x1, " + t.countGrade(2) + "x2, " 
			    						+ t.countGrade(3) + "x3, " + t.countGrade(4) + "x4, " 
			    						+ t.countGrade(5) + "x5");
			}
		} else {
			error("There are added no tasks in praktomat.");
		} 
	}
	
	/**
	 * Lists the summary of the tutors of the praktomat. The list is sorted into ascending order by their names.
	 * @param praktomat praktomat to operate on
	 */
	private static void summaryTutor(Praktomat praktomat) {
		assert praktomat != null;
			
		if (!praktomat.getTutors().isEmpty()) {
			praktomat.sortTutors();
				
			for (Tutor t : praktomat.getTutors()) {
				String averageGrade = "-";
					
				if (t.averageGrade() != -1) {
					averageGrade = DECIMAL_FORMAT.format(t.averageGrade());
				}
				
				System.out.println(t.toString() + ": " + t.getStudents().size() + " students, " + t.missingReviews()
						+ " missing review(s), average grade " + averageGrade);
			}
		} else {
			error("There are added no tutors in praktomat.");
		}	
	}
	
	/**
	 * Lists the summary of the students of the praktomat. The list is sorted into ascending order by their id.
	 * @param praktomat praktomat to operate on
	 */
	private static void summaryStudent(Praktomat praktomat) {
		assert praktomat != null;
			
		if (!praktomat.getStudents().isEmpty()) {
			praktomat.sortStudentsAverageGrade();
				
			for (Student s : praktomat.getStudents()) {
				String averageGrade = "-";
					
				if (s.averageGrade() != -1) {
					averageGrade = DECIMAL_FORMAT.format(s.averageGrade());
				}
				
				System.out.println(s.toString() + ": " + averageGrade);
			}
		} else {
			error("There are added no students in praktomat.");
		}
	}
	
	/**
	 * Prints an error message.
	 * @param err error message to print
	 */
	private static void error(String err) {
		println("Error! " + err);
	}

}