import java.util.*;
import java.io.*;

public class StudentRecordSystem {

    static Scanner scanner = new Scanner(System.in);
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Student Record Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Sort Students");
            System.out.println("7. Export Report");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> sortStudents();
                case 7 -> exportReport();
                case 8 -> System.exit(0);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addStudent() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            System.out.print("Enter Roll No: ");
            String roll = scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Department: ");
            String dept = scanner.nextLine();
            System.out.print("Enter Semester: ");
            String sem = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Contact No: ");
            String contact = scanner.nextLine();

            fw.write(roll + "|" + name + "|" + dept + "|" + sem + "|" + email + "|" + contact + "\n");
            fw.close();
            System.out.println("Student added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    static void viewStudents() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            System.out.printf("%-15s %-15s %-10s %-10s %-25s %-15s\n",
                    "Roll No", "Name", "Dept", "Semester", "Email", "Contact");
            System.out.println("------------------------------------------------------------------------------------------");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    System.out.printf("%-15s %-15s %-10s %-10s %-25s %-15s\n",
                            parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
    }

    static void searchStudent() {
        System.out.print("Enter Roll No or Name to search: ");
        String keyword = scanner.nextLine().toLowerCase();
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(keyword)) {
                    String[] parts = line.split("\\|");
                    System.out.println("Student Found:");
                    System.out.println("Roll No: " + parts[0]);
                    System.out.println("Name: " + parts[1]);
                    System.out.println("Department: " + parts[2]);
                    System.out.println("Semester: " + parts[3]);
                    System.out.println("Email: " + parts[4]);
                    System.out.println("Contact: " + parts[5]);
                    found = true;
                    break;
                }
            }
            if (!found) System.out.println("Student not found.");
            br.close();
        } catch (IOException e) {
            System.out.println("Error searching the file.");
        }
    }

    static void updateStudent() {
        System.out.print("Enter Roll No to update: ");
        String rollToUpdate = scanner.nextLine();
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equalsIgnoreCase(rollToUpdate)) {
                    found = true;
                    System.out.println("Enter new details:");
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = scanner.nextLine();
                    System.out.print("Enter Semester: ");
                    String sem = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Contact No: ");
                    String contact = scanner.nextLine();
                    line = rollToUpdate + "|" + name + "|" + dept + "|" + sem + "|" + email + "|" + contact;
                }
                lines.add(line);
            }
            br.close();

            FileWriter fw = new FileWriter(FILE_NAME);
            for (String l : lines) fw.write(l + "\n");
            fw.close();

            if (found) System.out.println("Student updated successfully.");
            else System.out.println("Student not found.");

        } catch (IOException e) {
            System.out.println("Error updating the file.");
        }
    }

    static void deleteStudent() {
        System.out.print("Enter Roll No to delete: ");
        String rollToDelete = scanner.nextLine();
        List<String> lines = new ArrayList<>();
        boolean deleted = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(rollToDelete + "|")) {
                    lines.add(line);
                } else {
                    deleted = true;
                }
            }
            br.close();

            FileWriter fw = new FileWriter(FILE_NAME);
            for (String l : lines) fw.write(l + "\n");
            fw.close();

            if (deleted) System.out.println("Student deleted successfully.");
            else System.out.println("Student not found.");

        } catch (IOException e) {
            System.out.println("Error deleting the file.");
        }
    }

    static void sortStudents() {
        List<String> records = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
            br.close();

            Collections.sort(records);

            System.out.println("\nSorted Students:");
            for (String r : records) {
                String[] parts = r.split("\\|");
                System.out.printf("%-15s %-15s %-10s %-10s %-25s %-15s\n",
                        parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
            }
        } catch (IOException e) {
            System.out.println("Error sorting the file.");
        }
    }

    static void exportReport() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            BufferedWriter bw = new BufferedWriter(new FileWriter("report.txt"));

            String line;
            int count = 0;
            bw.write("Roll No | Name | Department | Semester | Email | Contact\n");
            bw.write("------------------------------------------------------------\n");
            while ((line = br.readLine()) != null) {
                bw.write(line + "\n");
                count++;
            }
            bw.write("\nTotal Students: " + count);
            br.close();
            bw.close();
            System.out.println("Report exported to report.txt");

        } catch (IOException e) {
            System.out.println("Error exporting report.");
        }
    }
} 
