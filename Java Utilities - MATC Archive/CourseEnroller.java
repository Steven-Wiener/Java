/**
 * This program creates a class called Course with parameters for courseName, students (a string array)
 * 	and numberOfStudents, an addStudent method that adds a student to the array, a dropStudent that
 * 	removes a student from the array, three get methods that return students, numberOfStudents, and
 * 	courseName, and a clear method that clears the array.
 * The program first displays its function, creates a course called Course, adds three students to
 * 	Course, displays the three students, drops a student, and displays the two students after one has
 * 	been deleted.
 * @author Steven Wiener
 * @Version 1
 */

import java.util.Arrays;

public class CourseEnroller {
	public static void main(String[] args) {
		// Describe program
		System.out.println("This program allows you to add and drop students to a course.");
		
		// Create course named "Course"
		Course course = new Course("Course");
		
		// Add three students to Course
		course.addStudent("Larry");
		course.addStudent("Moe");
		course.addStudent("Curly");
		
		// Display array
		System.out.println("List of students after adding three:");
		System.out.println(Arrays.toString(course.getStudents()));
		
		// Drop student from course
		course.dropStudent("Moe");
		
		// Display array
		System.out.println("List of students after dropping one:");
		System.out.println(Arrays.toString(course.getStudents()));
	}
}

/**
 * This class produces a course and allows you to add and drop students from the course
 * @param courseName Name of course
 * @param students Array containing names of students
 * @param numberOfStudents Number of students in array
 */
class Course	{
	private String courseName;
	private String[] students = new String[1];
	private int numberOfStudents;
	
	// Specify course name
	public Course(String courseName) {
		this.courseName = courseName;
	}
	
	// Add student by copying current array into larger array
	public void addStudent(String student)	{
		students = Arrays.copyOf(students, numberOfStudents + 1);
		students[numberOfStudents] = student;
		numberOfStudents++;
	}
	
	// Return students array
	public String[] getStudents()	{
		return students;
	}
	
	// Return number of students
	public int getNumberOfStudents()	{
		return numberOfStudents;
	}
	
	// Return course name
	public String getCourseName()	{
		return courseName;
	}
	
	// Drop student by finding name, deleting, sorting, and copying to a new array without the empty space 
	public void dropStudent(String student)	{
		for (int i = numberOfStudents - 1; i >=0; i--)	{
			if (students[i] == student)	{
				students[i] = "";
				Arrays.sort(students);
				students = Arrays.copyOfRange(students, 1, numberOfStudents);
				numberOfStudents--;					
			}
		}
	}
	
	// Clear array
	public void clear()	{
		students = new String[1];
		numberOfStudents = 0;
	}
}