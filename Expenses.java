//imports the built in utility to use arraylist - this is used to store the Expense data as objects
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;

public class Expenses
{
    // instance variables
    private int Expense_ID;
    private double Expense_Amount;
    private char Expense_Category;
    private char Expense_TimeFrame;
    private int Student_ID;
    // variable to remember that the test data has loaded 

    private static boolean sampleLoaded = false;
    
    
    private static ArrayList<Expenses> expensesList = new ArrayList<>();
    
    // Constructor for objects of class Expenses

    public Expenses(int Expense_ID,double Expense_Amount,char Expense_Category,char Expense_TimeFrame, Students s)
    {
        this.Expense_ID = Expense_ID;
        this.Expense_Amount = Expense_Amount;
        this.Expense_Category = Expense_Category;
        this.Expense_TimeFrame = Expense_TimeFrame;
        this.Student_ID = s.getStudent_ID();
    }
    
    // getters for the Expenses attributes
    public int getExpense_ID(){return Expense_ID;}
    public double getExpense_Amount(){return Expense_Amount;}
    public char getExpense_Category(){return Expense_Category;}
    public char getExpense_TimeFrame(){return Expense_TimeFrame;}
    public int getStudent_ID(){return Student_ID;}
    
    // setters for the Expenses attributes
    
    public void setExpense_ID(int ID){this.Expense_ID = ID;}
    public void setExpense_Amount(double Amount){this.Expense_Amount = Amount;}
    public void setExpense_Category(char Category){ this.Expense_Category = Category;}
    public void setExpense_TimeFrame(char TimeFrame){this.Expense_TimeFrame = TimeFrame;}    
    public void setStudent_ID(int ID) { this.Student_ID = ID; }

    //adds the passed expense object into the expense list
    public static void addExpense(Expenses e) {
    expensesList.add(e);
    }
    
    // allows the expense list to be accessed outside of the expense class with this function
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
    // finds the last id in the expense list - used to 
    public static int getLastExpenseID(){
        if (!expensesList.isEmpty()){
            return expensesList.get(expensesList.size() - 1).getExpense_ID();
        }
            return 0;
    }
    // function that loads sample Expense data
    public static void loadSampleExpenses(){
        if (sampleLoaded) return; 
        sampleLoaded = true;
        if (!expensesList.isEmpty()) return;

        expensesList.add(new Expenses(3001, 50.00, 'F', 'W', Students.getStudentByID(2501001)));   // Food
        expensesList.add(new Expenses(3002, 120.00, 'T', 'M', Students.getStudentByID(2501002)));  // Transport
        expensesList.add(new Expenses(3003, 30.00, 'E', 'W', Students.getStudentByID(2501003)));   // Entertainment
        expensesList.add(new Expenses(3004, 400.00, 'R', 'M', Students.getStudentByID(2501004)));  // Rent
        expensesList.add(new Expenses(3005, 25.00, 'F', 'W', Students.getStudentByID(2501005)));
        expensesList.add(new Expenses(3006, 60.00, 'U', 'M', Students.getStudentByID(2501006)));   // Utilities
        expensesList.add(new Expenses(3007, 15.00, 'E', 'W', Students.getStudentByID(2501007)));
        expensesList.add(new Expenses(3008, 300.00, 'R', 'M', Students.getStudentByID(2501008)));
        expensesList.add(new Expenses(3009, 45.00, 'F', 'W', Students.getStudentByID(2501009)));
        expensesList.add(new Expenses(3010, 100.00, 'T', 'M', Students.getStudentByID(2501010)));
    }
    
    
    
    
    public static double convertToMonthly(double amount, char timeframe) {
    switch (timeframe) {
        case 'w': // weekly
            return amount * 52 / 12;
        case 'm': // monthly
            return amount;
        case 'y': // yearly
            return amount / 12;
        default:
            return 0; // or throw error
    }
    
    
    }
    
    
    public static double calculateMonthlyExpenses(int studentID, ArrayList<Expenses> expensesList) {
        double total = 0;

        for (Expenses exp : expensesList) {
            if (exp.getStudent_ID() == studentID) {
                total += convertToMonthly(exp.getExpense_Amount(), exp.getExpense_TimeFrame());
            }
        }

        return total;
    }

    // inside Expenses.java

    public static void saveExpenses() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("expenses.txt"));

            for (Expenses e : expensesList) {
                pw.println(e.getExpense_ID() + "," +
                       e.getExpense_Amount() + "," +
                       e.getExpense_Category() + "," +
                       e.getExpense_TimeFrame() + "," +
                       e.getStudent_ID());
            }

            pw.close();
        } catch (Exception ex) {
        System.out.println("Error saving expenses");
        }
    }

    public static void loadExpenses() {
        expensesList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File("expenses.txt"));
    
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
    
                int id = Integer.parseInt(p[0]);
                double amount = Double.parseDouble(p[1]);
                char category = p[2].charAt(0);
                char timeframe = p[3].charAt(0);
                int studentID = Integer.parseInt(p[4]);
    
                expensesList.add(new Expenses(id, amount, category, timeframe, Students.getStudentByID(studentID)));
            }

            sc.close();
        } catch (Exception e) {
            System.out.println("No saved expenses file found");
        }
    }

    

}   
        
