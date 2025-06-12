package ca.ucalcary.cpsc.groupprojectgui;

import ca.ucalcary.cpsc.groupprojectgui.util.FileLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;

public class MainController {
    @FXML
    private static Label statusMessage;
    private static Data data;

    private static void load(File file){
        Data data= FileLoader.load(file);
        if (data==null){
            statusMessage.setText(String.format("Failed to load data from file %s%n", file));
        }else {
            System.out.printf("Loaded data successfully from file %s%n",file);
            MainController.data =data;
        }
    }
}