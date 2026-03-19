import java.util.ArrayList;

public class Expenses
{
    // instance variables - replace the example below with your own
    private int Expense_ID;
    private double Expense_Amount;
    private char Expense_Category;
    private char Expense_TimeFrame;
    private int Student_ID;
    private static boolean sampleLoaded = false;
    
    private static ArrayList<Expenses> expensesList = new ArrayList<>();
    
    /**
     * Constructor for objects of class Expenses
     */
    public Expenses(int Expense_ID,double Expense_Amount,char Expense_Category,char Expense_TimeFrame, Students s)
    {
        this.Expense_ID = Expense_ID;
        this.Expense_Amount = Expense_Amount;
        this.Expense_Category = Expense_Category;
        this.Expense_TimeFrame = Expense_TimeFrame;
        this.Student_ID = s.getStudent_ID();
    }

    public int getExpense_ID(){return Expense_ID;}
    public double getExpense_Amount(){return Expense_Amount;}
    public char getExpense_Category(){return Expense_Category;}
    public char getExpense_TimeFrame(){return Expense_TimeFrame;}
    public int getStudent_ID(){return Student_ID;}
    
    public void setExpense_ID(int ID){this.Expense_ID = ID;}
    public void setExpense_Amount(double Amount){this.Expense_Amount = Amount;}
    public void setExpense_Category(char Category){ this.Expense_Category = Category;}
    public void setExpense_TimeFrame(char TimeFrame){this.Expense_TimeFrame = TimeFrame;}    
    public void setStudent_ID(int ID) { this.Student_ID = ID; }

    
    public static void addExpense(Expenses e) {
    expensesList.add(e);
    }
    
    public static ArrayList<Expenses> getExpensesList() {
    return expensesList;
    }

// Find expense by ID
    public static Expenses getExpenseByID(int id) {
        for (Expenses e : expensesList) {
            if (e.getExpense_ID() == id) {
            return e;
            }
        }
        return null;
    }

// View all expenses (console output)
    public static void viewAllExpenses() {
        for (Expenses e : expensesList) {
            Students StudentForExpenses  = Students.getStudentByID(e.getStudent_ID());
            System.out.println("ID: " + e.getExpense_ID() + ", Amount: " + e.getExpense_Amount() + ", Category: " + e.getExpense_Category() + ", TimeFrame: " + e.getExpense_TimeFrame() + ", Student Name: " + StudentForExpenses.getStudent_FullName());
        }
    }

// Delete expense
    public static boolean deleteExpense(int id) {
        Expenses e = getExpenseByID(id);
        if (e != null) {
            expensesList.remove(e);
            return true;
        }
        return false;
    }

// Update expense details
    public static boolean updateExpense(int id, double newAmount, char newCategory, char newTimeFrame) {
        Expenses e = getExpenseByID(id);
        if (e != null) {
            e.setExpense_Amount(newAmount);
            e.setExpense_Category(newCategory);
            e.setExpense_TimeFrame(newTimeFrame);
            return true;
        }
        return false;
    }
    
    public static int getLastExpenseID(){
        if (!expensesList.isEmpty()){
            return expensesList.get(expensesList.size() - 1).getExpense_ID();
        }
            return 0;
    }
    
    public static void loadSampleExpenses(){
        if (sampleLoaded) return; 
        sampleLoaded = true;
        
        new Expenses(3001, 50.00, 'F', 'W', Students.getStudentByID(2501001));   // Food
        new Expenses(3002, 120.00, 'T', 'M', Students.getStudentByID(2501002));  // Transport
        new Expenses(3003, 30.00, 'E', 'W', Students.getStudentByID(2501003));   // Entertainment
        new Expenses(3004, 400.00, 'R', 'M', Students.getStudentByID(2501004));  // Rent
        new Expenses(3005, 25.00, 'F', 'W', Students.getStudentByID(2501005));
        new Expenses(3006, 60.00, 'U', 'M', Students.getStudentByID(2501006));   // Utilities
        new Expenses(3007, 15.00, 'E', 'W', Students.getStudentByID(2501007));
        new Expenses(3008, 300.00, 'R', 'M', Students.getStudentByID(2501008));
        new Expenses(3009, 45.00, 'F', 'W', Students.getStudentByID(2501009));
        new Expenses(3010, 100.00, 'T', 'M', Students.getStudentByID(2501010));
    }
}