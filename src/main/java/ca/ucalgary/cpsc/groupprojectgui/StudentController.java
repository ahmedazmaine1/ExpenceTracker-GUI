package ca.ucalgary.cpsc.groupprojectgui;

import ca.ucalgary.cpsc.groupprojectgui.objects.Expense;
import ca.ucalgary.cpsc.groupprojectgui.objects.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class is like the brain behind our student management screen.
 * It listens to what you do (like clicking buttons) and tells the computer
 * how to respond, like adding a student or showing all the money they've spent on textbooks!
 */
public class StudentController {

    // These are the parts of our screen where you enter a student's name, email, and id.
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField idField;

    // This table shows all the different expenses a student has. Neat, right?
    @FXML private TableView<Expense> expensesTable;
    @FXML private TableColumn<Expense, String> expenseTypeColumn;
    @FXML private TableColumn<Expense, Double> amountColumn;

    // Here are our buttons. They're not just pretty - they actually do stuff!
    @FXML private Button addButton;
    @FXML private Button deleteButton;

    // The dataManager is where we keep track of all our student data behind the scenes.
    private Data dataManager = new Data();

    // Think of expenseData like a magical list that automatically updates our table on the screen.
    private ObservableList<Expense> expenseData = FXCollections.observableArrayList();

    /**
     * This method sets up our screen when it first starts.
     * It's like the opening act of our play, making sure every actor knows their cues.
     */
    @FXML
    private void initialize() {
        // Tell each column what part of an expense it should show.
        expenseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Hook up our magical list to the table.
        expensesTable.setItems(expenseData);

        // Disable the delete button because we don't want you to delete things by mistake.
        deleteButton.setDisable(true);

        // But if you do select something, we're ready to let you press that delete button.
        expensesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setDisable(newSelection == null);
        });
    }

    /**
     * Here's what happens when you press the "Add Student" button.
     * It's like taking the information you put in the boxes and creating a new chapter in our student book.
     */
    @FXML
    protected void addStudent() {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            int id = Integer.parseInt(idField.getText());

            // We don't want to add a student without a name or email, that wouldn't make sense.
            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Missing data", "Please fill out all fields", Alert.AlertType.ERROR);
                return;
            }

            // Let's see if we can add this student to our secret club (aka the data manager).
            boolean success = dataManager.storeNewStudent(name, id, email);
            if (!success) {
                // Oops! Looks like this student might already be in our club.
                showAlert("Duplicate", "A student with this ID or email already exists.", Alert.AlertType.ERROR);
            } else {
                // All good! Let's clear the stage for our next act (clear the input fields).
                clearFields();
            }
        } catch (NumberFormatException e) {
            // Uh-oh, IDs should be numbers. Let's remind our users.
            showAlert("Invalid ID", "Please enter a valid ID number", Alert.AlertType.ERROR);
        }
    }

    /**
     * This is what happens when you press the "Delete Expense" button.
     * It's like telling an expense, "You're off the team," and then it's gone from the table.
     */
    @FXML
    protected void deleteExpense() {
        Expense selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
        if (selectedExpense != null) {
            // We're not mean; we make sure something is selected before we delete.
            expenseData.remove(selectedExpense);
            // We also make sure to wipe it from the data manager's memory.
            dataManager.removeExpense(selectedExpense);
        }
    }

    /**
     * If you mess up, we've got your back. This pops up a friendly (or not) message to let you know what's up.
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Once you've added a student, we'll wipe the slate clean for you. How considerate!
     */
    private void clearFields() {
        nameField.clear();
        emailField.clear();
        idField.clear();
    }

    /**
     * When you want to add an expense, we take it and not only show it on the table,
     * but also store it behind the scenes. It's like a backstage pass for your expense.
     */
    public void addExpense(Expense expense) {
        expenseData.add(expense);
        dataManager.addExpense(expense);
    }

    /**
     * Sometimes, you need to know everything about a student's spending habits.
     * This is where you can see all their expenses in one place.
     */
    public void loadStudentDetails(Student student) {
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
        idField.setText(String.valueOf(student.getId()));
        expenseData.setAll(dataManager.getExpensesForStudent(student.getId()));
    }

    // We could add more here, like an encore, updating student details or more fancy things!
}
