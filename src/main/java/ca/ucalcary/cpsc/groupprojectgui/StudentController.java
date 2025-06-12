package ca.ucalcary.cpsc.groupprojectgui;

import ca.ucalcary.cpsc.groupprojectgui.objects.Expense;
import ca.ucalcary.cpsc.groupprojectgui.objects.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentController {

    @FXML private TextField nameField;

    @FXML private TextField emailField;

    @FXML private TextField idField;

    @FXML private TableView<Expense> expensesTable;

    @FXML private TableColumn<Expense, String> expenseTypeColumn;

    @FXML private TableColumn<Expense, Double> amountColumn;
    @FXML private Button addButton;

    @FXML private Button deleteButton;


    // Assuming you have a class that manages the list of students and expenses
    private final Data dataManager = new Data(); // You need to implement this class
    private TableView<Object> expensesTableView;
    private ObservableList<Object> expenseData;
    private final String email = this.email;
    private final String id = this.id;
    private final String name = this.name;

    private static final String STUD_FORMAT = "%n%-20s %-8s %-20s%n";
    private static final String STUDENT_HEADER = String.format(STUD_FORMAT, "Name", "ID", "EMAIL");
    private static String STUD_SEPARATOR = "";
    static {
        // Separator will be added for the length of our header in order to separate different students
        for (int i = 0; i < STUDENT_HEADER.length(); i++) {
            STUD_SEPARATOR += "-";
        }
    }

    // Initializes the controller class. This method is automatically called
    // after the FXML file has been loaded.
    @FXML
    private void initialize() {
        // Configure the table columns
        expenseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type")); // Assume Expense has getType()
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount")); // Assume Expense has getAmount()

        // Load initial data into the table
        expensesTableView.setItems(expenseData);

        // Other initialization as necessary (e.g., disable delete button when nothing is selected)
        deleteButton.setDisable(true);
        expensesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setDisable(newSelection == null);
        });
    }

    // Method to add a new student based on the input fields
    @FXML
    protected void addButton() {
        String name = nameField.getText();
        String email = emailField.getText();
        int id = Integer.parseInt(idField.getText());
        // Here you would create a new Student object and add it to your data manager
        //Student student = new Student(name, id, email);
        //dataManager.storeNewStudent(student);
        dataManager.storeNewStudent(name,id,email);
        clearFields();
    }

    // Utility method to clear input fields
    private void clearFields() {
        nameField.clear();
        emailField.clear();
        idField.clear();
    }
    @FXML
    protected void DeleteButton() {
        int selectedIndex = expensesTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            expensesTableView.getItems().remove(selectedIndex);
            // Also remove the expense from your data model if necessary
        } else {

        }
    }
            // Nothing selected.

    public void addExpense(Expense expense) {
        expenseData.add(expense);
    }

    // Method to load student details into the fields based on selection in another UI component, like a list or table
//    @FXML
//    private void loadStudentDetails(Student student) {
//        nameField.setText(student.getName());
//        emailField.setText(student.getEmail());
//        idField.setText(student.getId());
//        // Assuming you can fetch expenses by student ID
//        expensesTable.setItems(dataManager.getExpensesForStudent(student.getId()));
//    }
//
//    // Implement other methods as needed, for example, to update or delete students
}
