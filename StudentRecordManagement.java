import java.io.*;
import java.util.*;

public class StudentRecordManagement {

    private static final String FILE_NAME = "students.txt";
    private static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        loadRecords();
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== Student Record Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> searchStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> saveRecords();
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
        sc.close();
    }

    private static void addStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        int rollNo = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Course: ");
        String course = sc.nextLine();
        students.add(new Student(rollNo, name, course));
        System.out.println("Student added successfully!");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("\n--- Student Records ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to search: ");
        int rollNo = sc.nextInt();
        boolean found = false;
        for (Student s : students) {
            if (s.getRollNo() == rollNo) {
                System.out.println("Record found: " + s);
                found = true;
                break;
            }
        }
        if (!found) System.out.println("No record found with Roll No: " + rollNo);
    }

    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No to delete: ");
        int rollNo = sc.nextInt();
        boolean removed = students.removeIf(s -> s.getRollNo() == rollNo);
        if (removed) {
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("No record found with Roll No: " + rollNo);
        }
    }

    private static void saveRecords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Records saved successfully. Exiting...");
        } catch (IOException e) {
            System.out.println("Error saving records: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadRecords() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading records: " + e.getMessage());
        }
    }
}
