//imports the built in utility to use arraylist - this is used to store the Students data as objects
import java.util.ArrayList;

public class Students
{
    //
    private int Student_ID;
    private String Student_FullName;
    private String Student_Email;
    private String Student_Password;
    private double Monthly_Budget;

    private static boolean sampleLoaded = false;
    private static ArrayList<Students> studentList = new ArrayList<>();

    //Constructor for Student
    public Students(int Student_ID, String Student_FullName, String Student_Email, String Student_Password, double Monthly_Budget)
    {
        this.Student_ID = Student_ID;
        this.Student_FullName = Student_FullName;
        this.Student_Email = Student_Email;
        this.Student_Password = Student_Password;
        this.Monthly_Budget = Monthly_Budget;

    }

    // getters for Students class
    public int getStudent_ID() { return Student_ID; }
    public String getStudent_FullName() { return Student_FullName; }
    public String getStudent_Email() { return Student_Email; }
    public String getStudent_Password() { return Student_Password; }
    public double getMonthly_Budget() { return Monthly_Budget; }

    // setters for Students class
    public void setStudent_FullName(String name) { this.Student_FullName = name; }
    public void setStudent_Email(String email) { this.Student_Email = email; }
    public void setStudent_Password(String password) { this.Student_Password = password; }
    public void setMonthly_Budget(double budget) { this.Monthly_Budget = budget; }

    //function to add a passed Students object
    public static void addStudent(Students s) {
        studentList.add(s);
    }
    //returns the studentList - used in the main javafx class
    public static ArrayList<Students> getStudentList() {
    return studentList;
    }

    // Returns a student object with the given student ID
    public static Students getStudentByID(int id) {
        for (Students s : studentList) {
            if (s.getStudent_ID() == id) {
                return s;
            }
        }
        return null;
    }
    // checks whether the new created ID passed to the method as "id" is present in the studentList
    public static boolean isIDTaken(int id) {
        for (Students s : studentList) {
            if (s.getStudent_ID() == id) {
                return true;   
            }
        }
        return false;         
    }    
    // method that validates whether the password passed matches the student object
    //(with the passed studentID id)'s password are the same. returns boolean value.
    public static boolean validateLogin(int id, String password) {
        Students s = getStudentByID(id);
        if (s != null && s.getStudent_Password().equals(password)) {
            return true;
        }
        return false;
    }

    // view all students (console output)    
    public static void viewAllStudents() {
        for (Students s : studentList) {
            System.out.println("ID: " + s.getStudent_ID() +
                               ", Name: " + s.getStudent_FullName() +
                               ", Email: " + s.getStudent_Email());
        }
    }

    
    
    // deletes a student if a student exists with the id passed as a parameter.
    // Returns true if successful.
    public static boolean deleteStudent(int id) {
        Students s = getStudentByID(id);
        if (s != null) {
            studentList.remove(s);
            return true;
        }
        return false;
    }

    // updates a student if a student exists with the id passed as a parameter.
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
    
    // method called at the main menu, inserts test data into the student array list if it has not.
    public static void loadSampleStudents() {
        if (sampleLoaded) return;
        sampleLoaded = true;

        studentList.add(new Students(2501001, "Alice Johnson", "alice.johnson@study.beds.ac.uk", "pass123", 0.0));
        studentList.add(new Students(2501002, "Ben Carter", "ben.carter@study.beds.ac.uk", "qwerty", 450.0));
        studentList.add(new Students(2501003, "Chloe Smith", "chloe.smith@study.beds.ac.uk", "abc123", 600.0));
        studentList.add(new Students(2501004, "Daniel Green", "daniel.green@study.beds.ac.uk", "mypassword", 300.0));
        studentList.add(new Students(2501005, "Ella Brown", "ella.brown@study.beds.ac.uk", "brownella", 550.0));
        studentList.add(new Students(2501006, "Farah Khan", "farah.khan@study.beds.ac.uk", "farah2024", 700.0));
        studentList.add(new Students(2501007, "George Wilson", "george.wilson@study.beds.ac.uk", "wilson123", 400.0));
        studentList.add(new Students(2501008, "Hannah Lee", "hannah.lee@study.beds.ac.uk", "leeH!", 650.0));
        studentList.add(new Students(2501009, "Isaac Turner", "isaac.turner@study.beds.ac.uk", "turner321", 480.0));
        studentList.add(new Students(2501010, "Jasmine Patel", "jasmine.patel@study.beds.ac.uk", "jasminex", 520.0));
    }
    // used to update the massword if the passed password matches the student s's password.
    public static boolean ValidateUpdatingPassword(int currentid, String password){
        Students s = getStudentByID(currentid);
        if (s != null && password.equals(s.getStudent_Password())){
            return true; 
        }
        return false;
    }
    
    
    public static String checkBudget(int currentstudent, ArrayList<Income> incomeList, ArrayList<Expenses> expensesList) {  
        double monthlyIncome = Income.calculateMonthlyIncome(currentstudent, incomeList);
        Students s = getStudentByID(currentstudent);
        double monthlyExpenses = Expenses.calculateMonthlyExpenses(s.Student_ID, expensesList);
        System.out.println("Monthly Income: " + monthlyIncome);
        System.out.println("Monthly Expenses: " + monthlyExpenses);

        if (monthlyIncome >= monthlyExpenses) {
            System.out.println("Student earns more then they spend.");
            
            if (s.Monthly_Budget < monthlyExpenses){
                return "TT";
            } else {
                return "TF";
            }
        } else {
            System.out.println("Spends more than they earn.");
            if (s.Monthly_Budget < monthlyExpenses){
                return "FT";
            } else {
                return "FF";
            }
        }
        
    }



}

