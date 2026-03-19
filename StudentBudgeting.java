import javax.swing.*;
import javax.swing.JComboBox;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import javafx.scene.control.cell.PropertyValueFactory;


public class StudentBudgeting extends Application
{
    private Stage mainStage;
    private int currentstudent;

    @Override
    public void start(Stage primaryStage) {
        Students.loadSampleStudents();
        Income.loadSampleIncome();
        Expenses.loadSampleExpenses();
        
        mainStage = primaryStage;  
        primaryStage.setTitle("Student Budgeting System");

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");
        Label StudentChecker = new Label("Students signed up: " + Students.getStudentList().size());

        loginBtn.setOnAction(e -> {
            openLoginWindow();
            primaryStage.close();
        });

        registerBtn.setOnAction(e -> {
            openRegisterWindow();
            primaryStage.close();
        });

        VBox layout = new VBox(20, loginBtn, registerBtn, StudentChecker);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openLoginWindow() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        Label idLabel = new Label("Student ID:");
        TextField idField = new TextField();

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        Label message = new Label();

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String pass = passField.getText();

                if (Students.validateLogin(id, pass)) {
                    message.setText("Login successful!");
                    currentstudent = id;
                    openDashboard();
                    loginStage.close();
                } else {
                    message.setText("Invalid ID or password.");
                }
            } catch (Exception ex) {
                message.setText("Please enter a valid ID.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            loginStage.close();
            mainStage.show();
        });

        VBox layout = new VBox(10, idLabel, idField, passLabel, passField, loginBtn, backBtn, message);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 300);
        loginStage.setScene(scene);
        loginStage.show();
    }


    private void openRegisterWindow() {
        Stage registerStage = new Stage();
        registerStage.setTitle("Register");

        Label idLabel = new Label("Student ID:");
        TextField idField = new TextField();

        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        Label message = new Label();

        Button registerBtn = new Button("Register");
        registerBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String idlength = idField.getText();
                String name = nameField.getText();
                String pass = passField.getText();
                String email = emailField.getText();
                double monthlybudget = 0.0;
                boolean isTaken = Students.isIDTaken(id);
                if (isTaken == true){
                    message.setText("This Student ID is already taken."); 
                }else if (idlength.length() != 7){   
                    message.setText("Your Student ID is not the correct length.");
                    return;
                } else if (!email.endsWith("@study.beds.ac.uk")) {
                    message.setText("The email provided is not a bedfordshire student email.");
                    return;
                } else if (name.equals("") || idlength.equals("") || pass.equals("") || email.equals("")) {
                    message.setText("Details have been left out.");
                    return;
                }
                Students.addStudent(new Students(id, name, email, pass, monthlybudget));
                message.setText("Registration successful! You can now log in.");
                openLoginWindow();
                registerStage.close();

            } catch (Exception ex) {
                message.setText("Invalid input. Please try again.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            registerStage.close();
            mainStage.show();
        });

        VBox layout = new VBox(10, idLabel, idField, nameLabel, nameField, emailLabel, emailField, passLabel, passField, registerBtn, backBtn, message);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 350);
        registerStage.setScene(scene);
        registerStage.show();
    }
    
    private void openDashboard() {
        Stage dashStage = new Stage();
        dashStage.setTitle("Dashboard");
        Students tempstudent = Students.getStudentByID(currentstudent);
        Label welcomeMessage = new Label("Hello, " + tempstudent.getStudent_FullName() +"!");
        Button incomeBtn = new Button("Income Menu");
        Button expenseBtn = new Button("Expense Menu");
        Button profileBtn = new Button("Profile");
        Button logoutBtn = new Button("Logout");

        incomeBtn.setOnAction(e -> {
            openIncomeMenu();
            dashStage.close();
        });
        expenseBtn.setOnAction(e -> {
            openExpenseMenu();
            dashStage.close();
        });
        profileBtn.setOnAction(e -> {
            openProfileMenu();
            dashStage.close();
        });

        logoutBtn.setOnAction(e -> {
            dashStage.close();
            mainStage.show();
        });

        VBox layout = new VBox(15, welcomeMessage, incomeBtn, expenseBtn, profileBtn, logoutBtn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 300);
        dashStage.setScene(scene);
        dashStage.show();
    }

   
    private void openProfileMenu() {
        Stage profileStage = new Stage();
        profileStage.setTitle("Profile Settings");

        Students s = Students.getStudentByID(currentstudent);

        Label budgetLabel = new Label("Monthly Budget:");
        TextField budgetField = new TextField("" + s.getMonthly_Budget());
        
        Label oldpassLabel = new Label("Confirm Old Password:");
        PasswordField oldpassField = new PasswordField();

        Label passLabel = new Label("New Password:");
        PasswordField passField = new PasswordField();

        Label message = new Label();
        
        Button saveBtn = new Button("Save Changes");
        saveBtn.setOnAction(e -> {
            String currentpass = oldpassField.getText();
            if (Students.ValidateUpdatingPassword(currentstudent, currentpass)){
                try {
                    double newBudget = Double.parseDouble(budgetField.getText());
                    String newPass = passField.getText();

                    if (!newPass.isEmpty()) {
                        s.setStudent_Password(newPass);
                    }
                    s.setMonthly_Budget(newBudget);
                    message.setText("Profile updated!");
                    oldpassField.setText("");
                    passField.setText("");
                    
                } catch (Exception ex) {
                message.setText("Invalid budget value.");
                }
            } else {
                message.setText("Incorrect Password. Try again.");
            }
        });
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e ->{
            openDashboard();
            profileStage.close();    
        });
        
        VBox layout = new VBox(10, budgetLabel, budgetField, oldpassLabel, oldpassField, passLabel, passField, saveBtn, message, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 300);
        profileStage.setScene(scene);
        profileStage.show();
    }

    private void openIncomeMenu() {
        Stage incomeStage = new Stage();
        incomeStage.setTitle("Income Menu");

        Label label = new Label("Income menu");
        Button addIncomePage = new Button("Add Income");
        addIncomePage.setOnAction( e-> {
        openIncomeAddMenu();    
        incomeStage.close();         
        });
        
        Button editIncomePage = new Button("Edit Income");
        editIncomePage.setOnAction( e -> {
            openIncomeEditMenu();
            incomeStage.close(); 
        
        });
        
        Button viewIncomePage = new Button("View Income");
        viewIncomePage.setOnAction(e ->{
            openIncomeViewMenu();
            incomeStage.close();
        });
        
        Button deleteIncomePage = new Button("Delete Income");
        deleteIncomePage.setOnAction(e ->{
            openIncomeDeleteMenu();
            incomeStage.close();
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e ->{
        openDashboard();
        incomeStage.close();
        });
        VBox layout = new VBox(20, label, addIncomePage, editIncomePage, viewIncomePage, deleteIncomePage, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        incomeStage.setScene(scene);
        incomeStage.show();
    }

    private void openIncomeAddMenu(){
        Stage IncomeaddStage = new Stage();
        IncomeaddStage.setTitle("Add Income");

        Label amountLabel = new Label("Income Amount:");
        TextField amountField = new TextField();
    
        Label timeFrameLabel = new Label("Income TimeFrame:");
        ObservableList<String> timeFrameOptions = FXCollections.observableArrayList(
        "W - Weekly",
        "M - Monthly",
        "Y - Yearly"
        );
        ComboBox<String> timeFrameCombo = new ComboBox<>(timeFrameOptions);
        timeFrameCombo.setValue("M - Monthly");


        Button addButton = new Button("Add");
        Label messageLabel = new Label(); 
        addButton.setOnAction(e -> {
            try{    
                double amount = Double.parseDouble(amountField.getText());
                char timeFrame = (timeFrameCombo.getValue()).charAt(0);
                int id = Income.getLastIncomeID() + 1;
                Students wholecurrentstudent = Students.getStudentByID(currentstudent);
                Income newincome = new Income(id, amount, timeFrame, wholecurrentstudent);
                Income.addIncome(newincome);
                messageLabel.setText("Income added successfully!");
                amountField.setText("");
                timeFrameCombo.setValue("M - Monthly");
            } catch (Exception ex) {
                messageLabel.setText("Error: Please check your inputs.");
            }
        }
        );    
        Button backButton = new Button("Back");
        backButton.setOnAction(e ->{
            openIncomeMenu();
            IncomeaddStage.close();    
        }
        );
        VBox layout = new VBox(10, amountLabel, amountField, timeFrameLabel, timeFrameCombo, addButton, backButton, messageLabel);
        layout.setPadding(new Insets(20));
         Scene scene = new Scene(layout, 300, 400);
        IncomeaddStage.setScene(scene);
        IncomeaddStage.show();
    
    }


    private void openIncomeEditMenu(){
        Stage IncomeEditstage = new Stage();
        IncomeEditstage.setTitle("Edit Income");

        Label searchLabel = new Label("Enter Income ID to Edit:");
        TextField searchField = new TextField();
        searchField.setEditable(true);
        Button searchButton = new Button("Search");
        Label messageLabel = new Label();

        Label amountLabel = new Label("New Income Amount:");
        TextField amountField = new TextField();

        Label timeFrameLabel = new Label("New Income TimeFrame:");
         ObservableList<String> timeFrameOptions = FXCollections.observableArrayList(
        "W - Weekly",
        "M - Monthly",
        "Y - Yearly"
        );
        ComboBox<String> timeFrameCombo = new ComboBox<>(timeFrameOptions);
        timeFrameCombo.setValue("M - Monthly");

        Button updateButton = new Button("Update");
        updateButton.setDisable(true);
        
        
        Button backButton = new Button("Back");
        
        backButton.setOnAction( e -> {
            openIncomeMenu();
            IncomeEditstage.close();
              
        
        
        });
        
        searchButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                Income income = Income.getIncomeByID(id);
                char timeframe = income.getIncome_TimeFrame();
                if (income != null && income.getStudent_ID() == currentstudent) {
                    amountField.setText(String.valueOf(income.getIncome_Amount()));
                    if (timeframe == 'W'){
                        timeFrameCombo.setValue("W - Weekly");    
                        
                    } else if (timeframe == 'M'){
                        timeFrameCombo.setValue("M - Monthly");    
                        
                    } else if (timeframe == 'Y'){
                        timeFrameCombo.setValue("Y - Yearly");      
                    }
                    updateButton.setDisable(false);
                    searchField.setEditable(false);
                    messageLabel.setText("Income found - Edit the fields below.");
                
                } else {
                    messageLabel.setText("No income connected to you found with that ID.");
                    updateButton.setDisable(true);
                    searchField.setEditable(true);
                    searchField.setText("");
                    amountField.setText("");
                    timeFrameCombo.setValue("M - Monthly");
                    
                }
            } catch (Exception ex) {
                messageLabel.setText("Error: Enter a valid ID.");
            }
        });

        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                double newAmount = Double.parseDouble(amountField.getText());
                char newTimeFrame = (timeFrameCombo.getValue()).charAt(0);

                boolean updated = Income.updateIncome(id, newAmount, newTimeFrame);
                messageLabel.setText(updated ? "Income updated successfully!" : "Update failed.");
                if (updated){
                    updateButton.setDisable(true); 
                    searchField.setEditable(true);
                    
                }
            } catch (Exception ex) {
                messageLabel.setText("Error: Please check your inputs.");
            }
        });

        VBox layout = new VBox(10,
        searchLabel, searchField, searchButton, messageLabel,
        amountLabel, amountField,
        timeFrameLabel, timeFrameCombo,
        updateButton, backButton
        );
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 450);
        IncomeEditstage.setScene(scene);
        IncomeEditstage.show();
        
        }
    
    private void openIncomeViewMenu(){
        Stage incomeViewStage = new Stage();
        Students CurrentStudent = Students.getStudentByID(currentstudent);
        incomeViewStage.setTitle("View Incomes for Student " + CurrentStudent.getStudent_FullName());

        TableView<Income> table = new TableView<>();

        TableColumn<Income, Integer> idCol = new TableColumn<>("Income ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Income_ID"));

        TableColumn<Income, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("Income_Amount"));

        TableColumn<Income, String> timeFrameCol = new TableColumn<>("TimeFrame");
        timeFrameCol.setCellValueFactory(new PropertyValueFactory<>("Income_TimeFrame"));

        TableColumn<Income, Integer> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("Student_ID"));

        table.getColumns().addAll(idCol, amountCol, timeFrameCol, studentIdCol);

        ArrayList<Income> filtered = new ArrayList<>();
        
        for (Income I : Income.getIncomeList()) {
            if (I.getStudent_ID() == currentstudent) {
                filtered.add(I);
            }
        }

        table.getItems().addAll(filtered);
        Button backButton = new Button("Back");
        backButton.setOnAction(e ->{
            openIncomeMenu();
            incomeViewStage.close();
        });

        VBox layout = new VBox(10, table, backButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 600, 400);
        incomeViewStage.setScene(scene);
        incomeViewStage.show();

        
    }  
    
    private void openIncomeDeleteMenu(){
        Stage incomeDeleteStage = new Stage();
        incomeDeleteStage.setTitle("Delete Income");

        Label searchLabel = new Label("Enter Income ID to Delete:");
        TextField searchField = new TextField();
        Button deleteButton = new Button("Delete");
        Label messageLabel = new Label();
         
        deleteButton.setOnAction(e -> {
            Income incomeToDelete = Income.getIncomeByID(Integer.parseInt(searchField.getText()));
            if(incomeToDelete.getStudent_ID() == currentstudent) {
                try {
                    int id = Integer.parseInt(searchField.getText());
                    boolean deleted = Income.deleteIncome(id);
                    if (deleted) {
                    messageLabel.setText("Income deleted successfully!");
                    searchField.clear();
                    } else {
                    messageLabel.setText("No income found with that ID.");
                    }
                } catch (Exception ex) {
                messageLabel.setText("Error: Enter a valid ID.");
                }
            }else{
                messageLabel.setText("No validID given.");    
            }    
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            openIncomeMenu();
            incomeDeleteStage.close();
        });
        VBox layout = new VBox(10, searchLabel, searchField, deleteButton, messageLabel, backButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 200);
        incomeDeleteStage.setScene(scene);
        incomeDeleteStage.show();
        
        
    }    
    
    private void openExpenseMenu() {
        Stage expenseStage = new Stage();
        expenseStage.setTitle("Expense Menu");

        Label label = new Label("Income menu");
        Button addExpensesPage = new Button("Add Expenses");
        addExpensesPage.setOnAction( e-> {
            openExpenseAddMenu();    
            expenseStage.close();         
        });
        
        Button editExpensesPage = new Button("Edit Expenses");
        editExpensesPage.setOnAction( e -> {
            openExpenseEditMenu();
            expenseStage.close(); 
        
        });
        
        Button viewExpensesPage = new Button("View Expenses");
        viewExpensesPage.setOnAction(e ->{
            openExpenseViewMenu();
            expenseStage.close();
        });
        
        Button deleteExpensesPage = new Button("Delete Expenses");
        deleteExpensesPage.setOnAction(e ->{
            openExpenseDeleteMenu();
            expenseStage.close();
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e ->{
            openDashboard();
            expenseStage.close();
        });
        VBox layout = new VBox(20, label, addExpensesPage, editExpensesPage, viewExpensesPage, deleteExpensesPage, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        expenseStage.setScene(scene);
        expenseStage.show();
    }

    
    private void openExpenseAddMenu() {
        Stage expenseAddStage = new Stage();
        expenseAddStage.setTitle("Add Expense");

        Label amountLabel = new Label("Expense Amount:");
        TextField amountField = new TextField();

        Label categoryLabel = new Label("Expense Category:");
        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
            "F - Food",
            "T - Transport",
            "E - Entertainment",
            "R - Rent",
            "U - Utilities"
        );
        ComboBox<String> categoryCombo = new ComboBox<>(categoryOptions);
        categoryCombo.setValue("F - Food");
    
        Label timeFrameLabel = new Label("Expense TimeFrame:");
        ObservableList<String> timeFrameOptions = FXCollections.observableArrayList(
            "W - Weekly",
            "M - Monthly",
            "Y - Yearly"
        );
        ComboBox<String> timeFrameCombo = new ComboBox<>(timeFrameOptions);
        timeFrameCombo.setValue("M - Monthly");

        Button addButton = new Button("Add");
        Label messageLabel = new Label();

        addButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                char category = categoryCombo.getValue().charAt(0);
                char timeFrame = timeFrameCombo.getValue().charAt(0);
                Students wholecurrentstudent = Students.getStudentByID(currentstudent);
                int id = Expenses.getLastExpenseID() + 1;

                Expenses newExpense = new Expenses(id, amount, category, timeFrame, wholecurrentstudent);
                Expenses.addExpense(newExpense);

                messageLabel.setText("Expense added successfully!");
                amountField.clear();
                categoryCombo.setValue("F - Food");
                timeFrameCombo.setValue("M - Monthly");
    
            } catch (Exception ex) {
                messageLabel.setText("Error: Please check your inputs.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            openExpenseMenu();
            expenseAddStage.close();
        });

        VBox layout = new VBox(10, amountLabel, amountField, categoryLabel, categoryCombo,
            timeFrameLabel, timeFrameCombo, addButton, backButton, messageLabel);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 450);
        expenseAddStage.setScene(scene);
        expenseAddStage.show();
    }
    
    private void openExpenseEditMenu() {
        Stage expenseEditStage = new Stage();
        expenseEditStage.setTitle("Edit Expense");

        Label searchLabel = new Label("Enter Expense ID to Edit:");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        Label messageLabel = new Label();
    
        Label amountLabel = new Label("New Expense Amount:");
        TextField amountField = new TextField();

        Label categoryLabel = new Label("New Expense Category:");
        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
            "F - Food",
            "T - Transport",
            "E - Entertainment",
            "R - Rent",
            "U - Utilities"
        );
        ComboBox<String> categoryCombo = new ComboBox<>(categoryOptions);

        Label timeFrameLabel = new Label("New Expense TimeFrame:");
        ObservableList<String> timeFrameOptions = FXCollections.observableArrayList(
            "W - Weekly",
            "M - Monthly",
            "Y - Yearly"
        );
        ComboBox<String> timeFrameCombo = new ComboBox<>(timeFrameOptions);

        Button updateButton = new Button("Update");
        updateButton.setDisable(true);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            openExpenseMenu();
            expenseEditStage.close();
        });

        searchButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                Expenses expense = Expenses.getExpenseByID(id);

                if (expense != null && expense.getStudent_ID() == currentstudent) {
                    amountField.setText(String.valueOf(expense.getExpense_Amount()));

                    char cat = expense.getExpense_Category();
                    char tf = expense.getExpense_TimeFrame();

                    categoryCombo.setValue(
                        cat == 'F' ? "F - Food" :
                        cat == 'T' ? "T - Transport" :
                        cat == 'E' ? "E - Entertainment" :
                        cat == 'R' ? "R - Rent" :
                                  "U - Utilities"
                    );

                    timeFrameCombo.setValue(
                        tf == 'W' ? "W - Weekly" :
                        tf == 'M' ? "M - Monthly" :
                                    "Y - Yearly"
                    );

                    updateButton.setDisable(false);
                    searchField.setEditable(false);
                    messageLabel.setText("Expense found - Edit below.");

                } else {
                    messageLabel.setText("No expense found for your account.");
                    updateButton.setDisable(true);
                }

            } catch (Exception ex) {
                messageLabel.setText("Error: Enter a valid ID.");
            }
        });

        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                double newAmount = Double.parseDouble(amountField.getText());
                char newCategory = categoryCombo.getValue().charAt(0);
                char newTimeFrame = timeFrameCombo.getValue().charAt(0);

                boolean updated = Expenses.updateExpense(id, newAmount, newCategory, newTimeFrame);
    
                messageLabel.setText(updated ? "Expense updated!" : "Update failed.");

                if (updated) {
                    updateButton.setDisable(true);
                    searchField.setEditable(true);
                }

            } catch (Exception ex) {
                messageLabel.setText("Error: Check your inputs.");
            }
        });

        VBox layout = new VBox(10, searchLabel, searchField, searchButton, messageLabel,
                amountLabel, amountField, categoryLabel, categoryCombo,
                timeFrameLabel, timeFrameCombo, updateButton, backButton);

        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 350, 500);
        expenseEditStage.setScene(scene);
        expenseEditStage.show();
    }
    
    private void openExpenseViewMenu() {
        Stage expenseViewStage = new Stage();
        Students current = Students.getStudentByID(currentstudent);

        expenseViewStage.setTitle("Expenses for " + current.getStudent_FullName());

        TableView<Expenses> table = new TableView<>();

        TableColumn<Expenses, Integer> idCol = new TableColumn<>("Expense ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Expense_ID"));

        TableColumn<Expenses, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("Expense_Amount"));

        TableColumn<Expenses, Character> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("Expense_Category"));

        TableColumn<Expenses, Character> timeFrameCol = new TableColumn<>("TimeFrame");
        timeFrameCol.setCellValueFactory(new PropertyValueFactory<>("Expense_TimeFrame"));

        table.getColumns().addAll(idCol, amountCol, categoryCol, timeFrameCol);

        ArrayList<Expenses> filtered = new ArrayList<>();
        for (Expenses E : Expenses.getExpensesList()) {
            if (E.getStudent_ID() == currentstudent) {
                filtered.add(E);
            }
        }

        table.getItems().addAll(filtered);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            openExpenseMenu();
            expenseViewStage.close();
        });

        VBox layout = new VBox(10, table, backButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 600, 400);
        expenseViewStage.setScene(scene);
        expenseViewStage.show();
    }
    
    private void openExpenseDeleteMenu() {
        Stage expenseDeleteStage = new Stage();
        expenseDeleteStage.setTitle("Delete Expense");

        Label searchLabel = new Label("Enter Expense ID to Delete:");
        TextField searchField = new TextField();
        Button deleteButton = new Button("Delete");
        Label messageLabel = new Label();

        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                Expenses expense = Expenses.getExpenseByID(id);

                if (expense != null && expense.getStudent_ID() == currentstudent) {
                    boolean deleted = Expenses.deleteExpense(id);

                    if (deleted) {
                        messageLabel.setText("Expense deleted!");
                        searchField.clear();
                    } else {
                        messageLabel.setText("Delete failed.");
                    }

                } else {
                    messageLabel.setText("No expense found for your account.");
                }

            } catch (Exception ex) {
                messageLabel.setText("Error: Enter a valid ID.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            openExpenseMenu();
            expenseDeleteStage.close();
        });

        VBox layout = new VBox(10, searchLabel, searchField, deleteButton, messageLabel, backButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 250);
        expenseDeleteStage.setScene(scene);
        expenseDeleteStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

 