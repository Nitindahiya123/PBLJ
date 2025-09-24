import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    double marks;

    Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Marks: " + marks);
    }
}

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Salary: " + salary;
    }
}

public class Experiment {
    static final String studentFile = "student.ser";
    static final String employeeFile = "employees.dat";

    public static void sumOfIntegers(Scanner sc) {
        System.out.print("Enter total numbers: ");
        int n = sc.nextInt();
        Integer sum = 0;
        for (int i = 0; i < n; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            int num = sc.nextInt();
            sum += num;
        }
        System.out.println("Sum of numbers = " + sum);
    }

    public static void serializeStudent() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(studentFile))) {
            Student s = new Student(101, "Alice", 89.5);
            oos.writeObject(s);
            System.out.println("Student object serialized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deserializeStudent() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(studentFile))) {
            Student s = (Student) ois.readObject();
            System.out.println("Deserialized Student object:");
            s.display();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void addEmployee(Employee emp) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(employeeFile, true)) {
            @Override
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        }) {
            oos.writeObject(emp);
            System.out.println("Employee added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(employeeFile))) {
            while (true) {
                Employee emp = (Employee) ois.readObject();
                System.out.println(emp);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            System.out.println("No employees found or error reading file.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== PBLJ Experiment 2.2 Menu ===");
            System.out.println("1. Part A - Sum of Integers (Autoboxing/Unboxing)");
            System.out.println("2. Part B - Serialize Student");
            System.out.println("3. Part B - Deserialize Student");
            System.out.println("4. Part C - Add Employee");
            System.out.println("5. Part C - View Employees");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    sumOfIntegers(sc);
                    break;
                case 2:
                    serializeStudent();
                    break;
                case 3:
                    deserializeStudent();
                    break;
                case 4:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    addEmployee(new Employee(id, name, salary));
                    break;
                case 5:
                    viewEmployees();
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 6);
        sc.close();
    }
}
