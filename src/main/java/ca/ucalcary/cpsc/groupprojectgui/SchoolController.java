package ca.ucalcary.cpsc.groupprojectgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SchoolController {

    private static final String SCHOOL_FORMAT = "%n%-30s %-4s %-20s%n";
    private static final String SCHOOL_HEADER = String.format(SCHOOL_FORMAT, "Name", "ID", "PROVINCE");
    private static String SCHOOL_SEPARATOR = "";
    static {
        // Separator will be added for the length of our header in order to separate different students
        for (int i = 0; i < SCHOOL_HEADER.length(); i++) {
            SCHOOL_SEPARATOR += "-";
        }
    }

    @FXML private TextField name;
    @FXML private TextField id;
    @FXML private TextField province;
    @FXML private Button add;
    @FXML private Button delete;
}
