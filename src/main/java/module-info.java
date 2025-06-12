module ca.ucalcary.cpsc.groupprojectgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.ucalgary.jwhudson.groupprojectgui to javafx.fxml;
    exports ca.ucalgary.jwhudson.groupprojectgui;
}