import java.util.ArrayList;

public class Students
{

    private int Student_ID;
    private String Student_FullName;
    private String Student_Email;
    private String Student_Password;
    private double Monthly_Budget;

    private static boolean sampleLoaded = false;
    private static ArrayList<Students> studentList = new ArrayList<>();


    public Students(int Student_ID, String Student_FullName, String Student_Email, String Student_Password, double Monthly_Budget)
    {
        this.Student_ID = Student_ID;
        this.Student_FullName = Student_FullName;
        this.Student_Email = Student_Email;
        this.Student_Password = Student_Password;
        this.Monthly_Budget = Monthly_Budget;

    }


    public int getStudent_ID() { return Student_ID; }
    public String getStudent_FullName() { return Student_FullName; }
    public String getStudent_Email() { return Student_Email; }
    public String getStudent_Password() { return Student_Password; }
    public double getMonthly_Budget() { return Monthly_Budget; }


    public void setStudent_FullName(String name) { this.Student_FullName = name; }
    public void setStudent_Email(String email) { this.Student_Email = email; }
    public void setStudent_Password(String password) { this.Student_Password = password; }
    public void setMonthly_Budget(double budget) { this.Monthly_Budget = budget; }

    
    public static void addStudent(Students s) {
        studentList.add(s);
    }
    public static ArrayList<Students> getStudentList() {
    return studentList;
    }

    
    public static Students getStudentByID(int id) {
        for (Students s : studentList) {
            if (s.getStudent_ID() == id) {
                return s;
            }
        }
        return null;
    }

    public static boolean isIDTaken(int id) {
        for (Students s : studentList) {
            if (s.getStudent_ID() == id) {
                return true;   
            }
        }
        return false;         
    }    
    
    public static boolean validateLogin(int id, String password) {
        Students s = getStudentByID(id);
        if (s != null && s.getStudent_Password().equals(password)) {
            return true;
        }
        return false;
    }

    
    public static void viewAllStudents() {
        for (Students s : studentList) {
            System.out.println("ID: " + s.getStudent_ID() +
                               ", Name: " + s.getStudent_FullName() +
                               ", Email: " + s.getStudent_Email());
        }
    }

    
    
 
    public static boolean deleteStudent(int id) {
        Students s = getStudentByID(id);
        if (s != null) {
            studentList.remove(s);
            return true;
        }
        return false;
    }

    
    public static boolean updateStudent(int id, String newName, String newEmail, String newPassword, double newBudget) {
        Students s = getStudentByID(id);
        if (s != null) {
            s.setStudent_FullName(newName);
            s.setStudent_Email(newEmail);
            s.setStudent_Password(newPassword);
            s.setMonthly_Budget(newBudget);
            return true;
        }
        return false;
    }
    
    
    public static void loadSampleStudents() {
    if (sampleLoaded) return;  
    sampleLoaded = true;
    
    new Students(2501001, "Alice Johnson", "alice.johnson@study.beds.ac.uk", "pass123", 500.0);
    new Students(2501002, "Ben Carter", "ben.carter@study.beds.ac.uk", "qwerty", 450.0);
    new Students(2501003, "Chloe Smith", "chloe.smith@study.beds.ac.uk", "abc123", 600.0);
    new Students(2501004, "Daniel Green", "daniel.green@study.beds.ac.uk", "mypassword", 300.0);
    new Students(2501005, "Ella Brown", "ella.brown@study.beds.ac.uk", "brownella", 550.0);
    new Students(2501006, "Farah Khan", "farah.khan@study.beds.ac.uk", "farah2024", 700.0);
    new Students(2501007, "George Wilson", "george.wilson@study.beds.ac.uk", "wilson123", 400.0);
    new Students(2501008, "Hannah Lee", "hannah.lee@study.beds.ac.uk", "leeH!", 650.0);
    new Students(2501009, "Isaac Turner", "isaac.turner@study.beds.ac.uk", "turner321", 480.0);
    new Students(2501010, "Jasmine Patel", "jasmine.patel@study.beds.ac.uk", "jasminex", 520.0);
    }
    
    public static boolean ValidateUpdatingPassword(int currentid, String password){
        Students s = getStudentByID(currentid);
        if (s != null && password.equals(s.getStudent_Password())){
            return true; 
        }
        return false;
    }
    
}

