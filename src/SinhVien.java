import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// --- CLASS ENTITY: STUDENT ---
class Student {
    protected String studentId;
    protected String studentName;
    protected int age;
    protected double gpa;

    public Student(String studentId, String studentName, int age, double gpa) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.age = age;
        this.gpa = gpa;
    }

    public Student() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void inputData(Scanner sc) {
        studentId = inputStudentId(sc);
        studentName = inputStudentName(sc);
        age = inputStudentAge(sc);
        gpa = inputStudentGpa(sc);
    }

    public void displayInfo() {
        System.out.println("--------------------------");
        System.out.println("Student ID: " + studentId);
        System.out.println("Student Name: " + studentName);
        System.out.println("Student Age: " + age);
        System.out.println("Student GPA: " + gpa);
        System.out.println("--------------------------");
    }

    public static String inputStudentId(Scanner sc) {
        do {
            System.out.println("Nhap ma sinh vien:");
            String studentId = sc.nextLine().trim();
            if (studentId.isEmpty()) {
                System.out.println("Ma sinh vien khong duoc de trong. Vui long nhap lai.");
            } else {
                return studentId;
            }
        } while (true);
    }

    public static String inputStudentName(Scanner sc) {
        do {
            System.out.println("Nhap ten sinh vien:");
            String studentName = sc.nextLine().trim();
            if (studentName.isEmpty()) {
                System.out.println("Ten sinh vien khong duoc de trong. Vui long nhap lai.");
            } else {
                return studentName;
            }
        } while (true);
    }

    public static int inputStudentAge(Scanner sc) {
        do {
            System.out.println("Nhap tuoi sinh vien:");
            try {
                int age = Integer.parseInt(sc.nextLine().trim());
                if (age < 0) {
                    System.out.println("Tuoi khong duoc am. Vui long nhap lai.");
                } else {
                    return age;
                }
            } catch (Exception e) {
                System.out.println("Tuoi phai la mot so nguyen. Vui long nhap lai.");
            }
        } while (true);
    }

    public static double inputStudentGpa(Scanner sc) {
        do {
            System.out.println("Nhap diem trung binh sinh vien:");
            try {
                double gpa = Double.parseDouble(sc.nextLine().trim());
                if (gpa < 0 || gpa > 10) {
                    System.out.println("Diem trung binh phai tu 0 den 10. Vui long nhap lai.");
                } else {
                    return gpa;
                }
            } catch (Exception e) {
                System.out.println("Diem trung binh phai la mot so thuc. Vui long nhap lai.");
            }
        } while (true);
    }
}

// --- CLASS LOGIC: STUDENT BUSINESS ---
class StudentBusiness {
    protected static StudentBusiness instance;
    protected final List<Student> studentList;

    protected StudentBusiness() {
        studentList = new ArrayList<>();
    }

    public static StudentBusiness getInstance() {
        if (instance == null) {
            instance = new StudentBusiness();
        }
        return instance;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void displayStudent() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sach sinh vien rong!");
            return;
        }
        System.out.println("Danh sach sinh vien: ");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Ma sinh vien |    Ten sinh vien  |   Tuoi   | Diem trung binh");
        System.out.println("-------------------------------------------------------------");
        for (Student student : studentList) {
            System.out.printf("%-12s | %-17s | %-8d | %.2f%n", student.getStudentId(), student.getStudentName(), student.getAge(), student.getGpa());
        }
        System.out.println("-------------------------------------------------------------");
    }

    // them sinh vien moi
    public boolean addStudent(Student student) {
        boolean isExist = studentList.stream().anyMatch(
                s -> s.getStudentId().equalsIgnoreCase(student.getStudentId())
        );
        if (isExist) {
            System.out.println("Ma sinh vien da ton tai. Vui long nhap ma khac!");
            return false;
        }
        studentList.add(student);
        System.out.println("Them sinh vien moi thanh cong!");
        return true;
    }

    // cap nhat
    public void updateStudent(Scanner sc) {
        System.out.println("Nhap ma sinh vien can cap nhat: ");
        String studentId = sc.nextLine().trim();

        for (Student student : studentList) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                System.out.println("Tim thay sinh vien! Vui long nhap thong tin moi:");
                student.setStudentName(Student.inputStudentName(sc));
                student.setAge(Student.inputStudentAge(sc));
                student.setGpa(Student.inputStudentGpa(sc));
                System.out.println("Cap nhat thong tin thanh cong!");
                return;
            }
        }
        System.out.println("Khong tim thay sinh vien voi ma: " + studentId);
    }

    // xoa sinh vien
    public void deleteStudent(String studentId) {
        int oldSize = studentList.size();
        studentList.removeIf(
                s -> s.getStudentId().equalsIgnoreCase(studentId)
        );
        if (studentList.size() == oldSize) {
            System.out.println("Ma sinh vien khong ton tai!");
        } else {
            System.out.println("Xoa sinh vien thanh cong!");
        }
    }

    // tim kiem sinh vien theo ten
    public void searchStudentByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getStudentName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        if (result.isEmpty()) {
            System.out.println("Khong tim thay sinh vien nao co ten chua '" + name + "' !");
        } else {
            System.out.println("Danh sach sinh vien tim duoc: ");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Ma sinh vien |    Ten sinh vien  |   Tuoi   | Diem trung binh");
            System.out.println("-------------------------------------------------------------");
            for (Student student : result) {
                System.out.printf("%-12s | %-17s | %-8d | %.2f%n", student.getStudentId(), student.getStudentName(), student.getAge(), student.getGpa());
            }
            System.out.println("-------------------------------------------------------------");
        }
    }

    // sap xep giam dan theo GPA
    public void sortStudentByGpaDesc() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sach sinh vien rong!");
            return;
        }
        studentList.sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()));
        System.out.println("Da sap xep danh sach giam dan theo diem trung binh!");
        displayStudent(); // In ra luôn cho tiện theo dõi
    }

    // loc sinh vien gioi
    public void filterSchoolarStudent() {
        List<Student> result = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getGpa() >= 8.0) {
                result.add(student);
            }
        }
        if (result.isEmpty()) {
            System.out.println("Khong tim thay sinh vien nao co diem trung binh >= 8.0 !");
        } else {
            System.out.println("Danh sach sinh vien gioi: ");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Ma sinh vien |    Ten sinh vien  |   Tuoi   | Diem trung binh");
            System.out.println("-------------------------------------------------------------");
            for (Student student : result) {
                System.out.printf("%-12s | %-17s | %-8d | %.2f%n", student.getStudentId(), student.getStudentName(), student.getAge(), student.getGpa());
            }
            System.out.println("-------------------------------------------------------------");
        }
    }
}

// --- CLASS MAIN: STUDENT MANAGEMENT ---
public class SinhVien {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentBusiness studentBusiness = StudentBusiness.getInstance();
        int choice = 0;

        do {
            System.out.println("\n------------- Quan ly sinh vien -------------");
            System.out.println("1.  Hien thi toan bo danh sach snh vien");
            System.out.println("2.  Them moi sinh vien");
            System.out.println("3.  Cap nhat thong tin sinh vien theo ma sinh vien");
            System.out.println("4.  Xoa sinh vien theo ma sinh vien");
            System.out.println("5.  Tim kiem sinh vien theo ten");
            System.out.println("6.  Loc danh sach sinh vien Gioi");
            System.out.println("7.  Sap xep danh sach sinh vien giam dan theo diem");
            System.out.println("8.  Thoat chuong trinh");
            System.out.print("Nhap lua chon cua ban: ");

            // Xử lý chống crash khi người dùng nhập chữ thay vì số
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so nguyen tu 1 den 8!");
                continue;
            }

            switch (choice) {
                case 1:
                    studentBusiness.displayStudent();
                    break;
                case 2:
                    do {
                        Student student = new Student();
                        student.inputData(sc);

                        boolean isAdded = studentBusiness.addStudent(student);
                        if (!isAdded) {
                            System.out.println("Vui long thu lai voi ma sinh vien khac.");
                            continue;
                        }
                        System.out.println("Ban co muon them sinh vien khac khong? (Y/N)");
                        String continueChoice = sc.nextLine();
                        if (!continueChoice.equalsIgnoreCase("Y")) {
                            break;
                        }
                    } while (true);
                    break;
                case 3:
                    // Đã hoàn thiện chức năng cập nhật
                    studentBusiness.updateStudent(sc);
                    break;
                case 4:
                    System.out.println("Nhap ma sinh vien can xoa : ");
                    String deleteId = sc.nextLine().trim();
                    studentBusiness.deleteStudent(deleteId);
                    break;
                case 5:
                    System.out.println("Nhap ten sinh vien can tim kiem : ");
                    String searchName = sc.nextLine().trim();
                    studentBusiness.searchStudentByName(searchName);
                    break;
                case 6:
                    studentBusiness.filterSchoolarStudent();
                    break;
                case 7:
                    // Đã hoàn thiện chức năng sắp xếp và đổi lại tên hàm cho đúng chính tả
                    studentBusiness.sortStudentByGpaDesc();
                    break;
                case 8:
                    System.out.println("Thoat chuong trinh. Chuc ban mot ngay tot lanh!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai tu 1 - 8.");
            }
        } while (choice != 8);

        sc.close();
    }
}