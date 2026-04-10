// Import Javafx modules start - this is done so built-in classes can be used to create windows, and use specific utilities like comboboxes and tables
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
// Importing ends

public class StudentBudgeting extends Application
{
    private Stage mainStage;
    private int currentstudent;
    public Label studentChecker;

    

    // method that assigns a label the length of the student array - this was to check that the ctxt file was saving and loading correctly
    

    
    @Override
    public void start(Stage primaryStage) {
        // loads sample students with income and expenses linked to each student object.
        Students.loadStudents();
        Income.loadIncome();
        Expenses.loadExpenses();
        Students.loadSampleStudents();
        Income.loadSampleIncome();
        Expenses.loadSampleExpenses();
        
        mainStage = primaryStage;  
        primaryStage.setTitle("Student Budgeting System");
        
        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        Button registerBtn = new Button("Register");
        registerBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        studentChecker = new Label("Students signed up: " + Students.getStudentList().size());
    


        // sets the click action of the login button to open the login window (by calling the function openLoginWindow().) 
        //Closes the primary stage (the main menu).
        loginBtn.setOnAction(e -> {
            openLoginWindow();
            primaryStage.close();
        });
        
        // sets the click action of the register button to open the register window (by calling the function openRegisterWindow().) 
        //Closes the primary stage (the main menu).
        registerBtn.setOnAction(e -> {
            openRegisterWindow();
            primaryStage.close();
        });
        // Adds buttons and labels in a vbox layout
        VBox layout = new VBox(20, loginBtn, registerBtn, studentChecker);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
        layout.setAlignment(Pos.CENTER);
        //sets the size of the window, shows the primary stage.
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
        // sets the click condition of the login button to attempt (or throw expection) to login
        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        loginBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String pass = passField.getText();
                // Checks whether the id of the provided student matches the password provided
                if (Students.validateLogin(id, pass)) {
                    message.setText("Login successful!");
                    //stores the current id in an integer variable, then opens the student dashboard while closing the login stage / window
                    currentstudent = id;
                    openDashboard();
                    loginStage.close();
                } else {
                    // error message if the password does not match the id (or the id is not attatched to a student)
                    message.setText("Invalid ID or password.");
                }
            } catch (Exception ex) {
                // error message if the id is not an integer message
                message.setText("Please enter a valid ID.");
            }
        });
        // sets the click condition of the back button to close the login window / stage, and open the main menu / stage.
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backBtn.setOnAction(e -> {
            loginStage.close();
            
            
            mainStage.show();


        });
        // sets the layout of this scene to be vbox, putting the labels, textboxes and buttons to fit in this layout.
        VBox layout = new VBox(10, idLabel, idField, passLabel, passField, loginBtn, backBtn, message);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
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
        //sets the click condition for the register button. on click, it attempts to register a student with the inputted data.
        Button registerBtn = new Button("Register");
        registerBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        registerBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String idlength = idField.getText();
                String name = nameField.getText();
                String pass = passField.getText();
                String email = emailField.getText();
                //0.0 is a double value used as a default value when registering a student.
                double monthlybudget = 0.0;
                // a boolean value (true or false) is found using the isIDTaken function which checks whether the new ID is present in th
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
                Students.saveStudents();

                // if registration is successfull, a new student is added to the student list, and saved to the ctxt file. Then the login window is opened.
                message.setText("Registration successful! You can now log in.");
                studentChecker.setText("Students signed up: " + Students.getStudentList().size());
                openLoginWindow();
                registerStage.close();

            } catch (Exception ex) {
                message.setText("Invalid input. Please try again.");
            }
        });
        
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backBtn.setOnAction(e -> {
            registerStage.close();
            
            mainStage.show();

        });

        VBox layout = new VBox(10, idLabel, idField, nameLabel, nameField, emailLabel, emailField, passLabel, passField, registerBtn, backBtn, message);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
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
        String checkBudget = Students.checkBudget(currentstudent, Income.getIncomeList(), Expenses.getExpensesList());
        Label budgetNotice = new Label("");
        // A label that shows whether a student is currently over or under their budget, or if they have more or less income than expenses
        if (tempstudent.getMonthly_Budget() == 0.0){
            budgetNotice.setText("Please set a budget.");
        } else if (checkBudget =="TT"){
            budgetNotice.setText("You have disposable income and are under your monthly budget.");
        
        } else if (checkBudget == "TF"){
            budgetNotice.setText("You have disposable income but are over your monthly budget!");
    
        } else if (checkBudget == "FT"){
            budgetNotice.setText("You do not have disposable income but are under your monthly budget!");
            
        } else if (checkBudget == "FF"){
            budgetNotice.setText("You do not have disposable income and are over your monthly budget!");
            
        }
        Button incomeBtn = new Button("Income Menu");
        incomeBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Button expenseBtn = new Button("Expense Menu");
        expenseBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Button profileBtn = new Button("Profile");
        profileBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        //click condition for the income button. Leads to the income menu
        incomeBtn.setOnAction(e -> {
            openIncomeMenu();
            dashStage.close();
        });
        //click condition for the expense button. Leads to the expense menu
        expenseBtn.setOnAction(e -> {
            openExpenseMenu();
            dashStage.close();
        });
        //
        profileBtn.setOnAction(e -> {
            openProfileMenu();
            dashStage.close();
        });

        logoutBtn.setOnAction(e -> {
            dashStage.close();
            
            mainStage.show();

        });

        VBox layout = new VBox(15, welcomeMessage, budgetNotice, incomeBtn, expenseBtn, profileBtn, logoutBtn);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
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
        
        // sets the click condition for the save changes button
        Button saveBtn = new Button("Save Changes");
        saveBtn.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        saveBtn.setOnAction(e -> {
            String currentpass = oldpassField.getText();
            if (Students.ValidateUpdatingPassword(currentstudent, currentpass)){
                try {
                    // Attempts to edit the password (when the old password is confirmed) and the budget. Throws an exception if there is an error.
                    double newBudget = Double.parseDouble(budgetField.getText());
                    String newPass = passField.getText();

                    if (!newPass.isEmpty()) {
                        s.setStudent_Password(newPass);
                        

                    }
                    s.setMonthly_Budget(newBudget);
                    message.setText("Profile updated!");
                    //Saves the edited student to the text file.
                    Students.saveStudents();
                    //resets the textfields.
                    oldpassField.setText("");
                    passField.setText("");
                    
                } catch (Exception ex) {
                message.setText("Invalid budget value.");
                }
            } else {
                message.setText("Incorrect Password. Try again.");
            }
        });
        //sets the click condition for the back button - opens the dashboard and closes the profle menu.
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e ->{
            openDashboard();
            profileStage.close();    
        });
        
        VBox layout = new VBox(10, budgetLabel, budgetField, oldpassLabel, oldpassField, passLabel, passField, saveBtn, message, backButton);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 300);
        profileStage.setScene(scene);
        profileStage.show();
    }

    private void openIncomeMenu() {
        Stage incomeStage = new Stage();
        incomeStage.setTitle("Income Menu");
        
        Label label = new Label("Income menu");
        // sets click condition of the add income button - leads to the add income page.
        Button addIncomePage = new Button("Add Income");
        addIncomePage.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        addIncomePage.setOnAction( e-> {
        openIncomeAddMenu();    
        incomeStage.close();         
        });
        // sets the click condition of the edit income button - leads to the edit income page.
        Button editIncomePage = new Button("Edit Income");
        editIncomePage.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        editIncomePage.setOnAction( e -> {
            openIncomeEditMenu();
            incomeStage.close(); 
        
        });
        
        // Sets the click condition of the view income button - leads to the view income page.
        Button viewIncomePage = new Button("View Income");
        viewIncomePage.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        viewIncomePage.setOnAction(e ->{
            openIncomeViewMenu();
            incomeStage.close();
        });
        // Sets the click condition of the delete income button - leads to the delete income page.
        Button deleteIncomePage = new Button("Delete Income");
        deleteIncomePage.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        deleteIncomePage.setOnAction(e ->{
            openIncomeDeleteMenu();
            incomeStage.close();
        });
        // sets the click condition of the back button - leads back to the student's dashboard.
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e ->{
        openDashboard();
        incomeStage.close();
        });
        VBox layout = new VBox(20, label, addIncomePage, editIncomePage, viewIncomePage, deleteIncomePage, backButton);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 300);
        incomeStage.setScene(scene);
        incomeStage.show();
    }

    private void openIncomeAddMenu(){
        Stage IncomeaddStage = new Stage();
        IncomeaddStage.setTitle("Add Income");

        Label amountLabel = new Label("Income Amount:");
        TextField amountField = new TextField();
        // Sets the values for the time frame combobox
        Label timeFrameLabel = new Label("Income TimeFrame:");
        ObservableList<String> timeFrameOptions = FXCollections.observableArrayList(
        "W - Weekly",
        "M - Monthly",
        "Y - Yearly"
        );
        ComboBox<String> timeFrameCombo = new ComboBox<>(timeFrameOptions);
        timeFrameCombo.setValue("M - Monthly");

        // sets the click condition of the add button - validates that the inputted data is the correct type, and with put out an error
        // if there is wrong input
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Label messageLabel = new Label(); 
        addButton.setOnAction(e -> {
            try{    
                // adds the income to the income list and saves it using the saveincome method
                double amount = Double.parseDouble(amountField.getText());
                char timeFrame = (timeFrameCombo.getValue()).charAt(0);
                int id = Income.getLastIncomeID() + 1;
                Students wholecurrentstudent = Students.getStudentByID(currentstudent);
                Income newincome = new Income(id, amount, timeFrame, wholecurrentstudent);
                Income.addIncome(newincome);
                messageLabel.setText("Income added successfully!");
                Income.saveIncome();
                amountField.setText("");
                timeFrameCombo.setValue("M - Monthly");
            } catch (Exception ex) {
                messageLabel.setText("Error: Please check your inputs.");
            }
        }
        );    
        // sets the click condition for the back button - opens the income menu and closes the income add page
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e ->{
            openIncomeMenu();
            IncomeaddStage.close();    
        }
        );
        VBox layout = new VBox(10, amountLabel, amountField, timeFrameLabel, timeFrameCombo, addButton, backButton, messageLabel);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
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
        searchButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Label messageLabel = new Label();

        Label amountLabel = new Label("New Income Amount:");
        TextField amountField = new TextField();
        // sets the options for the timeframe combobox
        Label timeFrameLabel = new Label("New Income TimeFrame:");
         ObservableList<String> timeFrameOptions = FXCollections.observableArrayList(
        "W - Weekly",
        "M - Monthly",
        "Y - Yearly"
        );
        ComboBox<String> timeFrameCombo = new ComboBox<>(timeFrameOptions);
        timeFrameCombo.setValue("M - Monthly");

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        updateButton.setDisable(true);
        
        
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        //sets the click condition for the back button - leads back to the income menu
        backButton.setOnAction( e -> {
            openIncomeMenu();
            IncomeEditstage.close();
              
        
        
        });
        // sets the click condition for the search button. if the income exists and is connected with the current student, its data is shown in the comboboxes and textfields.
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
                    // If no income corresponding to the current student is found, it resets the searchfield box.
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
        // sets the click condition of the update button. 
        updateButton.setOnAction(e -> {
            try {
                // if the editted data is of the right data types, it attempts to update using the updateincome method.
                int id = Integer.parseInt(searchField.getText());
                double newAmount = Double.parseDouble(amountField.getText());
                char newTimeFrame = (timeFrameCombo.getValue()).charAt(0);
                
                boolean updated = Income.updateIncome(id, newAmount, newTimeFrame);
                messageLabel.setText(updated ? "Income updated successfully!" : "Update failed.");
                if (updated){
                    // if the updated value is true, it saves the edit to the income ctxt file.
                    updateButton.setDisable(true); 
                    searchField.setEditable(true);
                    Income.saveIncome();
                    
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
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 450);
        IncomeEditstage.setScene(scene);
        IncomeEditstage.show();
        
        }
    
    private void openIncomeViewMenu(){
        Stage incomeViewStage = new Stage();
        Students CurrentStudent = Students.getStudentByID(currentstudent);
        incomeViewStage.setTitle("View Incomes for Student " + CurrentStudent.getStudent_FullName());
        // creates the table for viewing the income data, sets the 4 columns.
        TableView<Income> table = new TableView<>();

        TableColumn<Income, Integer> idCol = new TableColumn<>("Income ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Income_ID"));

        TableColumn<Income, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("Income_Amount"));

        TableColumn<Income, String> timeFrameCol = new TableColumn<>("TimeFrame");
        timeFrameCol.setCellValueFactory(new PropertyValueFactory<>("Income_TimeFrame"));

        TableColumn<Income, Integer> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("Student_ID"));
        /// adds the specified columns to the table view.
        table.getColumns().addAll(idCol, amountCol, timeFrameCol, studentIdCol);

        ArrayList<Income> filtered = new ArrayList<>();
        // adds incomes with the current students id to an array list. This is then used to make sure the table only shows income relevant to the current student.
        for (Income I : Income.getIncomeList()) {
            if (I.getStudent_ID() == currentstudent) {
                filtered.add(I);
            }
        }

        table.getItems().addAll(filtered);
        // sets the click condition of the back button. opens the income menu and closes the income viewing page.
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e ->{
            openIncomeMenu();
            incomeViewStage.close();
        });

        VBox layout = new VBox(10, table, backButton);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
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
        deleteButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Label messageLabel = new Label();
         // sets the click condition for the delete button.
        deleteButton.setOnAction(e -> {
            // Stores the income that corresponds to the inputted ID in an income object.
            Income incomeToDelete = Income.getIncomeByID(Integer.parseInt(searchField.getText()));
            if(incomeToDelete.getStudent_ID() == currentstudent) {
                try {
                    // attemps to delete the income with the income delete method, if "deleted" is true, the income ctxt file is saved.
                    int id = Integer.parseInt(searchField.getText());
                    boolean deleted = Income.deleteIncome(id);
                    if (deleted) {
                    messageLabel.setText("Income deleted successfully!");
                    Income.saveIncome();
                    searchField.clear();
                    } else {
                    messageLabel.setText("No income found with that ID.");
                    }
                } catch (Exception ex) {
                messageLabel.setText("Error: Enter a valid ID.");
                }
            }else{
                messageLabel.setText("No valid ID given.");    
            }    
        });
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e -> {
            openIncomeMenu();
            incomeDeleteStage.close();
        });
        VBox layout = new VBox(10, searchLabel, searchField, deleteButton, messageLabel, backButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");

        Scene scene = new Scene(layout, 300, 200);
        incomeDeleteStage.setScene(scene);
        incomeDeleteStage.show();
        
        
    }    
    
    private void openExpenseMenu() {
        Stage expenseStage = new Stage();
        expenseStage.setTitle("Expense Menu");

        Label label = new Label("Income menu");
        // Sets click condition for the Add Expenses button - opens the add expenses menu, and closes the expenses menu
        Button addExpensesPage = new Button("Add Expenses");
        addExpensesPage.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        addExpensesPage.setOnAction( e-> {
            openExpenseAddMenu();    
            expenseStage.close();         
        });
        // Sets click condition for the edit expenses button - opens the edit expenses menu, and closes the expenses menu
        Button editExpensesPage = new Button("Edit Expenses");
        editExpensesPage.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        editExpensesPage.setOnAction( e -> {
            openExpenseEditMenu();
            expenseStage.close(); 
        
        });
        // Sets click condition for the view expenses button - opens the view expenses menu, and closes the expenses menu
        Button viewExpensesPage = new Button("View Expenses");
        viewExpensesPage.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        viewExpensesPage.setOnAction(e ->{
            openExpenseViewMenu();
            expenseStage.close();
        });
        // Sets click condition for the delete expenses button - opens the delete expenses menu, and closes the expenses menu        
        Button deleteExpensesPage = new Button("Delete Expenses");
        deleteExpensesPage.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        deleteExpensesPage.setOnAction(e ->{
            openExpenseDeleteMenu();
            expenseStage.close();
        });
        // Sets click condition for the back button. opens the student's dashboard, and closes the expenses menu
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e ->{
            openDashboard();
            expenseStage.close();
        });
        VBox layout = new VBox(20, label, addExpensesPage, editExpensesPage, viewExpensesPage, deleteExpensesPage, backButton);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 300);
        expenseStage.setScene(scene);
        expenseStage.show();
    }

    
    private void openExpenseAddMenu() {
        Stage expenseAddStage = new Stage();
        expenseAddStage.setTitle("Add Expense");

        Label amountLabel = new Label("Expense Amount:");
        TextField amountField = new TextField();
        // sets the options for the expense category combobox
        Label categoryLabel = new Label("Expense Category:");
        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
            "F - Food",
            "T - Transport",
            "E - Entertainment",
            "R - Rent",
            "U - Utilities"
        );
        // sets the default value to be F for food
        ComboBox<String> categoryCombo = new ComboBox<>(categoryOptions);
        categoryCombo.setValue("F - Food");
        // sets the options for the expense timeframe
        Label timeFrameLabel = new Label("Expense TimeFrame:");
        ObservableList<String> timeFrameOptions = FXCollections.observableArrayList(
            "W - Weekly",
            "M - Monthly",
            "Y - Yearly"
        );
        // Sets the default value to be M for monthly.
        ComboBox<String> timeFrameCombo = new ComboBox<>(timeFrameOptions);
        timeFrameCombo.setValue("M - Monthly");

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Label messageLabel = new Label();
        // Sets the click condition for the add button
        addButton.setOnAction(e -> {
            try { 
                // If the inputs are valid, a new expenses object is created, and added to the expense list. This is then saved to the ctxt file.
                double amount = Double.parseDouble(amountField.getText());
                char category = categoryCombo.getValue().charAt(0);
                char timeFrame = timeFrameCombo.getValue().charAt(0);
                Students wholecurrentstudent = Students.getStudentByID(currentstudent);
                int id = Expenses.getLastExpenseID() + 1;

                Expenses newExpense = new Expenses(id, amount, category, timeFrame, wholecurrentstudent);
                Expenses.addExpense(newExpense);
                
                messageLabel.setText("Expense added successfully!");
                // values are reset.
                Expenses.saveExpenses();
                amountField.clear();
                categoryCombo.setValue("F - Food");
                timeFrameCombo.setValue("M - Monthly");
    
            } catch (Exception ex) {
                messageLabel.setText("Error: Please check your inputs.");
            }
        });
        // sets the click condition for the back button - this opens the expenses menu, and closes the add expenses page.
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e -> {
            openExpenseMenu();
            expenseAddStage.close();
        });

        VBox layout = new VBox(10, amountLabel, amountField, categoryLabel, categoryCombo,
            timeFrameLabel, timeFrameCombo, addButton, backButton, messageLabel);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
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
        searchButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Label messageLabel = new Label();
    
        Label amountLabel = new Label("New Expense Amount:");
        TextField amountField = new TextField();
        // sets the options for the expense category combobox.
        Label categoryLabel = new Label("New Expense Category:");
        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
            "F - Food",
            "T - Transport",
            "E - Entertainment",
            "R - Rent",
            "U - Utilities"
        );
        ComboBox<String> categoryCombo = new ComboBox<>(categoryOptions);
        // sets the options for the expense timeframe combobox.
        Label timeFrameLabel = new Label("New Expense TimeFrame:");
        ObservableList<String> timeFrameOptions = FXCollections.observableArrayList(
            "W - Weekly",
            "M - Monthly",
            "Y - Yearly"
        );
        ComboBox<String> timeFrameCombo = new ComboBox<>(timeFrameOptions);

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        updateButton.setDisable(true);
        // sets the click condition for the back button - closes the expense edit page and opens the expense menu.
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e -> {
            openExpenseMenu();
            expenseEditStage.close();
        });
        // sets the click condition for the search button - if the expense corresponding to the inputted id has the foreign key of the current students id, it sets
        // the comboboxes and textboxes with that expenses data.
        searchButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                Expenses expense = Expenses.getExpenseByID(id);

                if (expense != null && expense.getStudent_ID() == currentstudent) {
                    amountField.setText(String.valueOf(expense.getExpense_Amount()));

                    char cat = expense.getExpense_Category();
                    char tf = expense.getExpense_TimeFrame();
                    // Sets the combobox with the value corresponding to what the expenses category is.
                    categoryCombo.setValue(
                        cat == 'F' ? "F - Food" :
                        cat == 'T' ? "T - Transport" :
                        cat == 'E' ? "E - Entertainment" :
                        cat == 'R' ? "R - Rent" :
                                  "U - Utilities"
                    );
                    // Sets the combobox with the value corresponding to what the expense timeframe is.
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
        
        // sets the click condition for the  update button - if the editted data is in the valid data types, it attempts to update using the updateexpense method.
        
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                double newAmount = Double.parseDouble(amountField.getText());
                char newCategory = categoryCombo.getValue().charAt(0);
                char newTimeFrame = timeFrameCombo.getValue().charAt(0);
                // If updated returns true, the expense has updated.
                boolean updated = Expenses.updateExpense(id, newAmount, newCategory, newTimeFrame);
    
                messageLabel.setText(updated ? "Expense updated!" : "Update failed.");
                // If expenses is true, the expenses ctxt file is updated.
                if (updated) {
                    updateButton.setDisable(true);
                    searchField.setEditable(true);
                    Expenses.saveExpenses();
                }

            } catch (Exception ex) {
                messageLabel.setText("Error: Check your inputs.");
            }
        });

        VBox layout = new VBox(10, searchLabel, searchField, searchButton, messageLabel,
                amountLabel, amountField, categoryLabel, categoryCombo,
                timeFrameLabel, timeFrameCombo, updateButton, backButton);

        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 350, 500);
        expenseEditStage.setScene(scene);
        expenseEditStage.show();
    }
    
    private void openExpenseViewMenu() {
        Stage expenseViewStage = new Stage();
        Students current = Students.getStudentByID(currentstudent);

        expenseViewStage.setTitle("Expenses for " + current.getStudent_FullName());
        // Creates a table and the four columns.
        TableView<Expenses> table = new TableView<>();

        TableColumn<Expenses, Integer> idCol = new TableColumn<>("Expense ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Expense_ID"));

        TableColumn<Expenses, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("Expense_Amount"));

        TableColumn<Expenses, Character> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("Expense_Category"));

        TableColumn<Expenses, Character> timeFrameCol = new TableColumn<>("TimeFrame");
        timeFrameCol.setCellValueFactory(new PropertyValueFactory<>("Expense_TimeFrame"));
        // Adds the four columns to the table view.
        table.getColumns().addAll(idCol, amountCol, categoryCol, timeFrameCol);
        // adds expenses with the current students id to an array list. This is then used to make sure the table only shows expenses relevant to the current student.
        ArrayList<Expenses> filtered = new ArrayList<>();
        for (Expenses E : Expenses.getExpensesList()) {
            if (E.getStudent_ID() == currentstudent) {
                filtered.add(E);
            }
        }

        table.getItems().addAll(filtered);
        // sets the click condition for the back button - closes the expenses view page and opens the expense menu.
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e -> {
            openExpenseMenu();
            expenseViewStage.close();
        });

        VBox layout = new VBox(10, table, backButton);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
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
        deleteButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        Label messageLabel = new Label();
        // sets the click condition for the delete button - if the id is valid, and the foreign student id is the same as the current student id, then it attempts to --
        // delete.
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                Expenses expense = Expenses.getExpenseByID(id);

                if (expense != null && expense.getStudent_ID() == currentstudent) {
                    boolean deleted = Expenses.deleteExpense(id);
                    // If "deleted" is true, the expense ctxt is overwritten with the edited data.
                    if (deleted) {
                        messageLabel.setText("Expense deleted!");
                        Expenses.saveExpenses();
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
        // sets the click condition for the back button - closes the expense delete page and opens the expense menu.
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;" +  "-fx-border-color: #AEC6CF;" + "-fx-border-width: 2;" +"-fx-background-radius: 10;" + "-fx-border-radius: 10;");
        backButton.setOnAction(e -> {
            openExpenseMenu();
            expenseDeleteStage.close();
        });

        VBox layout = new VBox(10, searchLabel, searchField, deleteButton, messageLabel, backButton);
        layout.setStyle("-fx-background-color: #B5D8FF; -fx-background-radius: 10;");
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 250);
        expenseDeleteStage.setScene(scene);
        expenseDeleteStage.show();
    }
    
    public static void main(String[] args) {
        // main function, runs the primary stage.
        launch(args);

    }
}

 