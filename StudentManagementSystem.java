import java.io.*;
import java.util.*;

class Student implements Serializable {
    private int rollNumber;
    private String name;
    private int age;
    private String grade;

    public Student(int rollNumber, String name, int age, String grade) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Age: " + age + ", Grade: " + grade;
    }
}

public class StudentManagementSystem {
    private static final String FILE_NAME = "students.dat";
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added successfully!");
    }

    public void updateStudent(int rollNumber, String name, int age, String grade) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                student.setName(name);
                student.setAge(age);
                student.setGrade(grade);
                System.out.println("Student details updated successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void deleteStudent(int rollNumber) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getRollNumber() == rollNumber) {
                iterator.remove();
                System.out.println("Student deleted successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records available!");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    public void searchStudent(String query) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(query) || Integer.toString(student.getRollNumber()).equals(query)) {
                System.out.println(student);
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(new ArrayList<>(students)); // Safely casted to ArrayList
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
            System.out.println("Data loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.loadData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Search Student");
            System.out.println("6. Save and Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll Number: ");
                    int rollNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Grade: ");
                    String grade = scanner.nextLine();
                    sms.addStudent(new Student(rollNumber, name, age, grade));
                    break;

                case 2:
                    System.out.print("Enter Roll Number of Student to Update: ");
                    rollNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter New Name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter New Age: ");
                    age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter New Grade: ");
                    grade = scanner.nextLine();
                    sms.updateStudent(rollNumber, name, age, grade);
                    break;

                case 3:
                    System.out.print("Enter Roll Number of Student to Delete: ");
                    rollNumber = scanner.nextInt();
                    sms.deleteStudent(rollNumber);
                    break;

                case 4:
                    sms.displayStudents();
                    break;

                case 5:
                    System.out.print("Enter Roll Number or Name to Search: ");
                    String query = scanner.nextLine();
                    sms.searchStudent(query);
                    break;

                case 6:
                    sms.saveData();
                    System.out.println("Exiting system. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again!");
            }
        }
    }
}
