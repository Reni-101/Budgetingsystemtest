import java.util.ArrayList;

public class Income
{
    // instance variables
    private int Income_ID;
    private double Income_Amount;
    private char Income_TimeFrame;
    private int Student_ID;
    
    private static boolean sampleLoaded = false;
    
    private static ArrayList<Income> incomeList = new ArrayList<>();
    
    /**
     * Constructor for objects of class Income
     */
    public Income(int Income_ID, double Income_Amount, char Income_TimeFrame, Students s)
    {
        
        this.Income_ID = Income_ID;
        this.Income_Amount = Income_Amount;
        this.Income_TimeFrame = Income_TimeFrame;
        this.Student_ID = s.getStudent_ID();  

    }
    
    public int getIncome_ID(){return Income_ID;}
    public double getIncome_Amount(){return Income_Amount;}
    public char getIncome_TimeFrame(){return Income_TimeFrame;}
    public int getStudent_ID(){return Student_ID;}
    
    public void setIncome_ID(int ID) { this.Income_ID = ID; }
    public void setIncome_Amount(double Amount) { this.Income_Amount = Amount; }
    public void setIncome_TimeFrame(char TimeFrame) { this.Income_TimeFrame = TimeFrame; }
    public void setStudent_ID(int ID) { this.Student_ID = ID; }
    
    public static ArrayList<Income> getIncomeList() {
    return incomeList;
    }
    
    public static void addIncome(Income I) {
        incomeList.add(I);
    }

  
    public static Income getIncomeByID(int id) {
        for (Income I : incomeList) {
            if (I.getIncome_ID() == id) {
                return I;
            }
        }
        return null;
    }

    public static void viewAllIncome() {
        for (Income I : incomeList) {
            Students StudentForIncome  = Students.getStudentByID(I.getStudent_ID());
            System.out.println("ID: " + I.getIncome_ID() + ", Income Amount: " + I.getIncome_Amount() + ", Income Time frame: " + I.getIncome_TimeFrame() + ", Student Name: "+ StudentForIncome.getStudent_FullName());
        }
    }

    public static boolean deleteIncome(int id) {
        Income I = getIncomeByID(id);
        if (I != null) {
            incomeList.remove(I);
            return true;
        }
        return false;
    }

    public static boolean updateIncome(int id, double newIncome_Amount, char newIncome_TimeFrame) {
        Income I = getIncomeByID(id);
        if (I != null) {
            I.setIncome_Amount(newIncome_Amount);
            I.setIncome_TimeFrame(newIncome_TimeFrame);
            return true;
        }
        return false;
    }
    
    public static int getLastIncomeID(){
        if (!incomeList.isEmpty()){
            return incomeList.get(incomeList.size() - 1).getIncome_ID();
        }
            return 0;
    }
    
    public static void loadSampleIncome(){
        if (sampleLoaded) return;
        sampleLoaded = true;
        
        new Income(2001, 1200.00, 'M', Students.getStudentByID(2501001));
        new Income(2002, 150.00, 'W', Students.getStudentByID(2501002));
        new Income(2003, 300.00, 'M', Students.getStudentByID(2501003));
        new Income(2004, 80.00, 'W', Students.getStudentByID(2501004));
        new Income(2005, 500.00, 'M', Students.getStudentByID(2501005));
        new Income(2006, 200.00, 'W', Students.getStudentByID(2501006));
        new Income(2007, 1000.00, 'M', Students.getStudentByID(2501007));
        new Income(2008, 250.00, 'W', Students.getStudentByID(2501008));
        new Income(2009, 900.00, 'M', Students.getStudentByID(2501009));
        new Income(2010, 400.00, 'W', Students.getStudentByID(2501010));
    }
}
   
